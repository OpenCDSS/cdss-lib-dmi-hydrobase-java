//-----------------------------------------------------------------------------
// HydroBase_GUI_OwnerContact - Owner/Contact Information GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
// 21 Aug 1997 DLG, RTi 		Created initial version.
// 22 Aug 1997 DLG, RTi 		Modified GUI layout
// 02 Dec 1997 SAM, RTi			Implement working export and print.
// 29 Apr 1998 DLG, RTi			Updated to 1.1 event model, added
//					javadoc comments.
// 31 Mar 1999 CEN, RTi			Modified zip code handling since
//					zip code extension has been removed
//					and zip is now a character string
// 02 Sep 1999	SAM, RTi		Change so set visible after query
//					because of Java refresh problem.
//					Remove import *.  Add join to rolodex
//					and contact query so structure number
//					is used.  Otherwise, structures with
//					no contact information return a huge
//					number of records. 
// 2001-11-12	SAM, RTi		Change GUI to JGUIUtil. Don't use static
//					data for internal strings.
// 2002-02-25	SAM, RTi		Make ID field wider.
//-----------------------------------------------------------------------------
// 2003-10-01	J. Thomas Sapienza, RTi	Initial Swing version.
// 2003-10-02	JTS, RTi		* Javadoc'd.
//					* Added finalize().
// 2004-01-20	JTS, RTi		Changed to use the new JWorksheet method
//					of displaying a row count column.
// 2004-07-26	JTS, RTi		Address information is displayed again
//					in the text fields.
// 2005-02-14	JTS, RTi		Checked all dmi calls to make sure they
//					use stored procedures.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
This class is a gui for displaying information on structure owners and 
contacts.
*/
public class HydroBase_GUI_OwnerContact 
extends JFrame
implements ActionListener, WindowListener {

/**
Button labels.
*/
private final String
	__BUTTON_CLOSE	= "Close",
	__BUTTON_EXPORT= "Export",
	__BUTTON_HELP	= "Help",
	__BUTTON_PRINT	= "Print";

/**
The dmi used to retrieve data from the database.
*/
private HydroBaseDMI __dmi;

/**
Numbers stored and used in queries.
*/
private int 
	__rolodexNum,
	__structureNum;

/**
Text fields to hold data.
*/
private JTextField       
	__address1JTextField,
	__address2JTextField,
	__bondingJTextField,
	__cityJTextField,
	__divJTextField,
	__firstJTextField,
	__idJTextField,
	__lastJTextField,
	__licenseJTextField,
	__middleJTextField,
	__nameJTextField,
	__noteJTextField,
	__orgJTextField,
	__prefixJTextField,
	__stateJTextField,
	__statusJTextField,
	__structureJTextField,
	__suffixJTextField,
	__titleJTextField,
	__typeJTextField,
	__wdJTextField,
	__zipCodeJTextField;
	
/**
Worksheet for storing the list of contacts.
*/
private JWorksheet __worksheet;

/**
The name of the structure for which the owner/contacts are being shown.
*/
private String __structureName;

/**
Constructor.
@param dmi the dmi used to retrieve data from the database.
@param structureName the name of the structure for which to display 
owner/contact information.
@param structureNum the number of the structure for which to display
owner/contact information.
*/
public HydroBase_GUI_OwnerContact(HydroBaseDMI dmi, String structureName, 
int structureNum) {
        __dmi = dmi;
        __structureNum = structureNum;
        __structureName = structureName;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        setupGUI();

	submitAndDisplayPersonDetailsQuery();
	submitAndDisplayRolodexQuery();
	submitAndDisplayContactQuery();

	if (!__wdJTextField.getText().equals("")) {
		int wd = (new Integer(__wdJTextField.getText().trim()))
			.intValue();
		int id = (new Integer(__idJTextField.getText().trim()))
			.intValue();
		String name = __structureJTextField.getText().trim();
	
		String rest = "Structure Data - Owner/Contact Data - "
			+ HydroBase_WaterDistrict.formWDID(wd, id)
			+ " (" + name + ")";
		if ((JGUIUtil.getAppNameForWindows() == null) 
		    || JGUIUtil.getAppNameForWindows().equals("")) {
			setTitle(rest);
		}
		else {	
			setTitle(JGUIUtil.getAppNameForWindows() 
				+ " - " + rest);
		}					
	}
	else {
		setTitle(JGUIUtil.getAppNameForWindows() 
			+ "Structure Data - Owner/Contact Data");
	}	
}

/**
Responds to action performed events.
@param evt the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent evt) {
	String routine = "HydroBase_GUI_OwnerContact.actionPerformed";
	String s = evt.getActionCommand();

        if (s.equals(__BUTTON_CLOSE)) {
		closeClicked();
        }
        else if (s.equals(__BUTTON_EXPORT)) {
		try {
			String[] eff = 
				HydroBase_GUI_Util.getExportFilenameAndFormat(
				this, 
				HydroBase_GUI_Util.getFormatsAndExtensions());

			if (eff == null) {
				return ;
			}

			int format = new Integer(eff[1]).intValue();
	 		// First format the output...
			List outputStrings = formatOutput(format);
 			// Now export, letting the user decide the file...
			HydroBase_GUI_Util.export(this, eff[0], outputStrings);
		} 
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}		
        }
        else if (s.equals(__BUTTON_HELP)) {
        }
        else if (s.equals(__BUTTON_PRINT)) {   
		try {
			SelectFormatTypeJDialog d = 
				new SelectFormatTypeJDialog(this, 
				HydroBase_GUI_Util.getFormats());
			int format = d.getSelected();
			if (format == HydroBase_GUI_Util.CANCEL) {
				return;
			}
			d.dispose();
	 		// First format the output...
			List outputStrings = formatOutput(format);
	 		// Now print...
			PrintJGUI.print(this, outputStrings);
		}
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}			
        }
}

/**
Closes the GUI.
*/
private void closeClicked() {
	setVisible(false);
	dispose();
}

/**
Cleans up member variables.
*/
public void finalize() 
throws Throwable {
	__dmi = null;
	__address1JTextField = null;
	__address2JTextField = null;
	__bondingJTextField = null;
	__cityJTextField = null;
	__divJTextField = null;
	__firstJTextField = null;
	__idJTextField = null;
	__lastJTextField = null;
	__licenseJTextField = null;
	__middleJTextField = null;
	__nameJTextField = null;
	__noteJTextField = null;
	__orgJTextField = null;
	__prefixJTextField = null;
	__stateJTextField = null;
	__statusJTextField = null;
	__structureJTextField = null;
	__suffixJTextField = null;
	__titleJTextField = null;
	__typeJTextField = null;
	__wdJTextField = null;
	__zipCodeJTextField = null;
	__worksheet = null;
	__structureName = null;
	super.finalize();
}

/**
Formats output for export or printing.
@param format the format in which to format the output.
*/
public List formatOutput(int format) {
	List v = new Vector(10, 5);

	int size = __worksheet.getRowCount();
	String s0 = null;
	String s1 = null;
	String s2 = null;
	String s3 = null;

	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
		// For now only show the owner type that is shown on the
		// screen.  Multiple listing is an enhancement...
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			HydroBase_GUI_Util.trimText(__structureJTextField),
			HydroBase_GUI_Util.trimText(__divJTextField),
			HydroBase_GUI_Util.trimText(__wdJTextField),
			HydroBase_GUI_Util.trimText(__idJTextField), format));
		v.add("");
		v.add("                                          "
			+ "                     OWNER/CONTACT DATA");
		v.add("FULLNAME:       " 
			+ HydroBase_GUI_Util.trimText(__prefixJTextField) + " "
			+ HydroBase_GUI_Util.trimText(__firstJTextField) + " "
			+ HydroBase_GUI_Util.trimText(__middleJTextField) + " "
			+ HydroBase_GUI_Util.trimText(__lastJTextField) + " "
			+ HydroBase_GUI_Util.trimText(__suffixJTextField));
		v.add("NAME:           " 
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__nameJTextField),
			"%-40.40s") + " TYPE: "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__typeJTextField),
			"%-15.15s") + " NOTE: " 
			+ HydroBase_GUI_Util.trimText(__noteJTextField));
		v.add("TITLE:          " 
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__titleJTextField),
			"%-40.40s") + " PROF. LICENSE: "
			+ HydroBase_GUI_Util.trimText(__licenseJTextField));
		v.add("ORGANIZATION:   " 
			+ HydroBase_GUI_Util.trimText(__orgJTextField));
		v.add("BONDING CO.:    " 
			+ HydroBase_GUI_Util.trimText(__bondingJTextField));
		v.add("ADDRESS:        " 
			+ HydroBase_GUI_Util.trimText(__address1JTextField));
		v.add("                " 
			+ HydroBase_GUI_Util.trimText(__address2JTextField));
		v.add("CITY:           " 
			+ HydroBase_GUI_Util.trimText(__cityJTextField));
		v.add("STATE:          " 
			+ HydroBase_GUI_Util.trimText(__stateJTextField));
		v.add("ZIP:            " 
			+ HydroBase_GUI_Util.trimText(__zipCodeJTextField));
		v.add("");
		v.add("                                        "
			+ "                       CONTACT MEANS");
		v.add("                       TRY    AREA");
		v.add("CONTACT MEANS         FIRST   CODE    "
			+ "NUMBER        OTHER");
		v.add("________________________________________"
			+ "___________________________________________"
			+ "_____________________________________");
		// Skip the header...
		for (int i = 0; i < size; i++) {
			s0 = (String)__worksheet.getValueAt(i, 0);
			s1 = (String)__worksheet.getValueAt(i, 1);
			s2 = (String)__worksheet.getValueAt(i, 2);
			s3 = (String)__worksheet.getValueAt(i, 3);
			v.add(
				StringUtil.formatString(s0.trim(), "%-15.15s") 
				+ "         "
				+ StringUtil.formatString(s1.trim(), "%-1.1s") 
				+ "     "
				+ StringUtil.formatString(s2.trim(), "%-20.20s")
				+ "     "
				+ s3.trim());
		}
	}
	else {	
		char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);	
		v.add(HydroBase_GUI_Util.formatStructureHeader(format));
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			HydroBase_GUI_Util.trimText(__structureJTextField),
			HydroBase_GUI_Util.trimText(__divJTextField),
			HydroBase_GUI_Util.trimText(__wdJTextField),
			HydroBase_GUI_Util.trimText(__idJTextField),format));
		v.add("");
		// For now display the first contact only...
		String phone = "";
		for (int i = 0; i < size; i++) {
			s0 = (String)__worksheet.getValueAt(i, 0);
			s1 = (String)__worksheet.getValueAt(i, 1);
			s2 = (String)__worksheet.getValueAt(i, 2);
			s3 = (String)__worksheet.getValueAt(i, 3);
			v.add(s0 + delim + s1 + delim + s2 + delim 
				+ s3 + delim);
		}		
		v.add("TYPE" + delim + "FULLNAME" + delim + "TITLE" 
			+ delim + "ADDRESS1" + delim + "ADDRESS2" + delim 
			+ "CITY" + delim + "ST" + delim + "ZIP" + delim 
			+ "PHONE NUMBER" + delim);
		String zip_code = HydroBase_GUI_Util.trimText(
			__zipCodeJTextField);
		v.add(StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__typeJTextField),
			"%-15.15s") + delim
			+ HydroBase_GUI_Util.trimText(__nameJTextField) + delim
			+ HydroBase_GUI_Util.trimText(__titleJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__address1JTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__address2JTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__cityJTextField) + delim
			+ HydroBase_GUI_Util.trimText(__stateJTextField) + delim
			+ zip_code + delim
			+ phone + delim);
	}	

	return v;
}

/**
Sets up the GUI objects.
*/
private void setupGUI() {
	String routine = "HydroBase_GUI_OwnerContact.setupGUI";
	addWindowListener(this);	

        Insets insetsNLNN = new Insets(0,7,0,0);      
        Insets insetsNNNR = new Insets(0,0,0,7);      
        Insets insetsTLBR = new Insets(7,7,7,7); 
        Insets insetsNLBR = new Insets(0,7,7,7);     
        Insets insetsNLNR = new Insets(0,7,0,7);     

        GridBagLayout gbl = new GridBagLayout();
                                        
        // Top JPanel
        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", topJPanel);

        // Top: West JPanel
        JPanel topLeftJPanel = new JPanel();
        topLeftJPanel.setLayout(gbl);
        topJPanel.add("West", topLeftJPanel);

        JGUIUtil.addComponent(topLeftJPanel, new JLabel("Structure Name:"), 
		0, 0, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST); 
        
        JGUIUtil.addComponent(topLeftJPanel, new JLabel("DIV:"), 
		1, 0, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
     
        JGUIUtil.addComponent(topLeftJPanel, new JLabel("WD:"), 
		2, 0, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(topLeftJPanel, new JLabel("ID:"), 
		3, 0, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __structureJTextField = new JTextField(" " + __structureName + "   ");
        __structureJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __structureJTextField, 
		0, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
       
        __divJTextField = new JTextField(5);
        __divJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __divJTextField, 
		1, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __wdJTextField = new JTextField(5);
        __wdJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __wdJTextField, 
		2, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __idJTextField = new JTextField(10);
        __idJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __idJTextField, 
		3, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);              

        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BorderLayout());
        getContentPane().add("Center", centerJPanel);

        // Center: West JPanel
        JPanel centerWJPanel = new JPanel();
        centerWJPanel.setLayout(gbl);
        centerJPanel.add("West", centerWJPanel);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Fullname:"), 
		0, 2, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        JPanel nameJPanel = new JPanel();
        nameJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));  
        JGUIUtil.addComponent(centerWJPanel, nameJPanel, 
		1, 2, 5, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __prefixJTextField = new JTextField(3);
        __prefixJTextField.setEditable(false);
        nameJPanel.add(__prefixJTextField);

        __firstJTextField = new JTextField(15);
        __firstJTextField.setEditable(false);
        nameJPanel.add(__firstJTextField);

        __middleJTextField = new JTextField(3);
        __middleJTextField.setEditable(false);
        nameJPanel.add(__middleJTextField);

        __lastJTextField = new JTextField(20);
        __lastJTextField.setEditable(false);
        nameJPanel.add(__lastJTextField);

        __suffixJTextField = new JTextField(3);
        __suffixJTextField.setEditable(false);
        nameJPanel.add(__suffixJTextField);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Name:"), 
		0, 3, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);        

        __nameJTextField = new JTextField();
        __nameJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __nameJTextField, 
		1, 3, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __typeJTextField = new JTextField(10);
	__typeJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __typeJTextField, 
		2, 3, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
 
        __noteJTextField = new JTextField(30);
        __noteJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __noteJTextField, 
		3, 3, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Title:"), 
		0, 4, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);        

        __titleJTextField = new JTextField(25);
        __titleJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __titleJTextField, 
		1, 4, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);
                                                                   
        JGUIUtil.addComponent(centerWJPanel, new JLabel("Prof. License #:"), 
		2, 4, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.EAST);        

        __licenseJTextField = new JTextField();
        __licenseJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __licenseJTextField, 
		3, 4, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Organization:"), 
		0, 5, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);        

        __orgJTextField = new JTextField();
        __orgJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __orgJTextField, 
		1, 5, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
      
        JGUIUtil.addComponent(centerWJPanel, new JLabel("Bonding Co.:"), 
		0, 6, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);        

        __bondingJTextField = new JTextField(25);
        __bondingJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __bondingJTextField, 
		1, 6, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
                                
        JGUIUtil.addComponent(centerWJPanel, new JLabel("Address:"), 
		0, 7, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);        
                
        __address1JTextField = new JTextField(25);
        __address1JTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __address1JTextField, 
		1, 7, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __address2JTextField = new JTextField(25);
        __address2JTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __address2JTextField, 
		1, 8, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
            
        JGUIUtil.addComponent(centerWJPanel, new JLabel("City:"), 
		0, 9, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);        

        __cityJTextField = new JTextField(25);
        __cityJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __cityJTextField, 
		1, 9, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
                                                
        JGUIUtil.addComponent(centerWJPanel, new JLabel("State:"), 
		0, 10, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);        

        __stateJTextField = new JTextField(10);
        __stateJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __stateJTextField, 
		1, 10, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("ZIP:"), 
		0, 11, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);        

        JPanel zipJPanel = new JPanel();
        zipJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JGUIUtil.addComponent(centerWJPanel, zipJPanel, 
		1, 11, 2, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __zipCodeJTextField = new JTextField(15);
        __zipCodeJTextField.setEditable(false);
        zipJPanel.add(__zipCodeJTextField);

        // Bottom: South: North JPanel
        JPanel bottomSouthNorthJPanel = new JPanel();
        GridBagLayout gblBSN = new GridBagLayout();
        bottomSouthNorthJPanel.setLayout(gblBSN);

	PropList p = new PropList("HydroBase_GUI_OwnerContact.JWorksheet");
	/*
	p.add("JWorksheet.CellFont=Courier");
	p.add("JWorksheet.CellStyle=Plain");
	p.add("JWorksheet.CellSize=11");
	p.add("JWorksheet.HeaderFont=Arial");
	p.add("JWorksheet.HeaderStyle=Plain");
	p.add("JWorksheet.HeaderSize=11");
	p.add("JWorksheet.HeaderBackground=LightGray");
	p.add("JWorksheet.RowColumnPresent=false");
	*/
	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.ShowPopupMenu=true");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.SelectionMode=ExcelSelection");

	int[] widths = null;
	JScrollWorksheet jsw = null;
	try {
		HydroBase_TableModel_Contact tm = new
			HydroBase_TableModel_Contact(new Vector());
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	
		jsw = new JScrollWorksheet(cr, tm, p);
		__worksheet = jsw.getJWorksheet();

		widths = tm.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(2, routine, e);
		jsw = new JScrollWorksheet(0, 0, p);
		__worksheet = jsw.getJWorksheet();
	}
	__worksheet.setPreferredScrollableViewportSize(null);
	__worksheet.setHourglassJFrame(this);
	
        //JGUIUtil.addComponent(bottomSouthNorthJPanel, 
        JGUIUtil.addComponent(centerWJPanel,
		new JScrollPane(__worksheet), 
		2, 6, 2, 5, 1, 1, insetsTLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

        // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);

        // Bottom: South JPanel
        JPanel bottomSouthJPanel = new JPanel();
        bottomSouthJPanel.setLayout(new BorderLayout());
        bottomJPanel.add("South", bottomSouthJPanel);

        // Bottom: South: Center JPanel
        JPanel bottomSouthCenterJPanel = new JPanel();
        bottomSouthCenterJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomSouthJPanel.add("Center", bottomSouthCenterJPanel);

	SimpleJButton print = new SimpleJButton(__BUTTON_PRINT, this);
	print.setToolTipText("Print form data.");
        bottomSouthCenterJPanel.add(print);
	SimpleJButton export = new SimpleJButton(__BUTTON_EXPORT, this);
	export.setToolTipText("Export data to a file.");
        bottomSouthCenterJPanel.add(export);
	SimpleJButton close = new SimpleJButton(__BUTTON_CLOSE, this);
	close.setToolTipText("Close form.");
        bottomSouthCenterJPanel.add(close);
//	SimpleJButton helpJButton = new SimpleJButton(__BUTTON_HELP, this);
//      bottomSouthCenterJPanel.add(helpJButton);
//	helpJButton.setEnabled(false);

        // Bottom: South: South JPanel
        JPanel bottomSouthSouthJPanel = new JPanel();
        bottomSouthSouthJPanel.setLayout(gbl);
        bottomSouthJPanel.add("South", bottomSouthSouthJPanel);

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);;
        JGUIUtil.addComponent(bottomSouthSouthJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // Frame settings
        setTitle("Owner/Contact Data");
        pack();        
        JGUIUtil.center(this);
        setVisible(true);

	if (widths != null) {
		__worksheet.setColumnWidths(widths);
	}
}

/**
Submits a query for contact data and displays the data in the GUI.
*/
private void submitAndDisplayContactQuery() {
	String routine = "HydroBase_GUI_OwnerContact.submitAndDisplayContactQuery";
	JGUIUtil.setWaitCursor(this, true);
	
	List results = null;
	try {
		results = __dmi.readContactListForRolodex_num(__rolodexNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	if (results == null) {
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	__worksheet.setData(results);
	JGUIUtil.setWaitCursor(this, false);
}            

/**
Submits a query for person details data and shows the data in the GUI.
*/
private void submitAndDisplayPersonDetailsQuery() {
	JGUIUtil.setWaitCursor(this, true);
	String routine = "HydroBase_GUI_OwnerContact"
		+ ".submitAndDisplayPersonDetailsQuery";

	HydroBase_PersonDetails data = null;
	try {
		data = __dmi.readPersonDetailsForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	if (data == null) {
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	String curString = data.getType();
	if (!DMIUtil.isMissing(curString)) {
		__typeJTextField.setText(curString);
	}

        // Place data into the appropriate instantiated component object
	int curInt = data.getWD();
	if (!DMIUtil.isMissing(curInt)) {
		__wdJTextField.setText("" + curInt);
	}

	curInt = data.getID();
	if (!DMIUtil.isMissing(curInt)) {
		__idJTextField.setText("" + curInt);           
	}
 
	curInt = data.getDiv();
	if (!DMIUtil.isMissing(curInt)) {
		__divJTextField.setText("" + curInt);            
	}

	curString = data.getNote();
	if (!DMIUtil.isMissing(curString)) {
	        __noteJTextField.setText(curString);
	}

	__rolodexNum = data.getRolodex_num();

	JGUIUtil.setWaitCursor(this, false);
}

/**
Submits a query for rolodex data and shows the data in the GUI.
*/
private void submitAndDisplayRolodexQuery() {
	String routine = "HydroBase_GUI_OwnerContact"
		+ ".submitAndDisplayRolodexQuery";
	JGUIUtil.setWaitCursor(this, true);

	HydroBase_Rolodex data = null;
	try {
		data = __dmi.readRolodexForRolodex_numStructure_num(
			__rolodexNum, __structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	if (data == null) {
		JGUIUtil.setWaitCursor(this, false);
		return;
	}
       
        __nameJTextField.setText("" + data.getFull_name());
        __prefixJTextField.setText("" + data.getPrefix());
        __firstJTextField.setText("" + data.getFirst_name());
        __middleJTextField.setText("" + data.getMiddle_name());
        __lastJTextField.setText("" + data.getLast_name());
        __suffixJTextField.setText("" + data.getSuffix());
        __titleJTextField.setText("" + data.getTitle());
        __licenseJTextField.setText("" + data.getLic_no());
        __orgJTextField.setText("" + data.getOrg_name());
        __bondingJTextField.setText("" + data.getBond_co_name());            
        __address1JTextField.setText("" + data.getAddress1());           
        __address2JTextField.setText("" + data.getAddress2());
        __cityJTextField.setText("" + data.getCity());           
        __stateJTextField.setText("" + data.getST());    
        __zipCodeJTextField.setText("" + data.getZip());

	JGUIUtil.setWaitCursor(this, false);
}        

public void windowActivated(WindowEvent evt) {;}
public void windowClosed(WindowEvent evt) {;}

public void windowClosing(WindowEvent evt) {
	closeClicked();
}
public void windowDeactivated(WindowEvent evt) {;}
public void windowDeiconified(WindowEvent evt) {;}
public void windowIconified(WindowEvent evt) {;}
public void windowOpened(WindowEvent evt) {;}

}
