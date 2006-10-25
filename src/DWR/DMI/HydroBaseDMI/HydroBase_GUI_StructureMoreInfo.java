//-----------------------------------------------------------------------------
// HydroBase_GUI_StructureMoreInfo - Structure Data GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 18 Aug 1997	Darrell L. Gillmeister,	Created initial version.
//		RTi
// 01 Oct 1997	DLG, RTi		Added querying functionality	
// 02 Dec 1997	Steven A. Malers, RTi	Enable full export and print.
// 16 Dec 1997	DLG, RTi		Added court case, map file, and general 
//					comment query.
// 10 Feb 1998	DLG, RTi		Moved stream mile display to
//					HBLocationDataGUI. Updated to 1.1 event
//					model.
// 04 Apr 1999	SAM, RTi		Add HBDMI to queries.
// 02 Sep 1999	SAM, RTi		Updated because of changes to general
//					comments in the database.
// 2001-11-12	SAM, RTi		Change GUI to JGUIUtil.
// 2002-02-25	SAM, RTi		Make the ID field wider.
//-----------------------------------------------------------------------------
// 2003-04-09	J. Thomas Sapienza, RTi	Began initial Swing version.
// 2003-05-30	JTS, RTi		Added code to copy from the table.
// 2003-06-02	JTS, RTi		Added code so an hourglass displays when
//					sorting the table
// 2003-10-01	JTS, RTi		Continued work on the GUI after a bit
//					of a hiatus.
// 2003-12-10	JTS, RTi		Modified the GUI so that more than one
//					court case or map file can be displayed
//					per structure.
// 2004-01-08	JTS, RTi		* Changed column sizes in worksheets.
//					* Changed field sizes.
//					* Got export and print working.
// 2004-01-21	JTS, RTi		Changed to use the new JWorksheet method
//					of displaying a row count column.
// 2004-08-11	JTS, RTi		Fixed a bug related to getting data
//					for the date installed field after
//					the row count column was removed.
// 2005-02-11	JTS, RTi		Converted 
//					readStructureGeolocForStructure_num()
//					to Stored procedures, and had to make
//					changes in here because that method now
//					returns a HydroBase_StructureView.
// 2005-02-15	JTS, RTi		Converted all queries to use stored
//					procedures.
// 2005-03-24	JTS, RTi		Added support for Structure View objects
// 2005-04-12	JTS, RTi		MutableJList changed to SimpleJList.
// 2005-04-28	JTS, RTi		Added all data members to finalize().
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
// 2005-06-09	JTS, RTi		Added mapfile, courtcase, and notes
//					to the exported data.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;

import RTi.Util.GUI.Generic_CellRenderer;
import RTi.Util.GUI.Generic_TableModel;
import RTi.Util.GUI.GenericWorksheetData;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.SimpleJList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;

/**
GUI to display more info about a Structure.
*/
public class HydroBase_GUI_StructureMoreInfo 
extends JFrame 
implements ActionListener, WindowListener {

/**
Button labels.
*/
private final String
	__BUTTON_CLOSE = "Close",
	__BUTTON_EXPORT = "Export",
	__BUTTON_HELP = "Help",
	__BUTTON_PRINT = "Print";

/**
DMI object.
*/
private HydroBaseDMI __dmi;

/**
GUI buttons.
*/
private JButton 
	__closeJButton,
	__exportJButton,
	__helpJButton,
	__printJButton;

/**
Textfields to display data.
*/
private JTextField 
	__ciuCodeJTextField,
	__decreedCapacityJTextField,
	__divJTextField,
	__estimatedCapacityJTextField,
	__idJTextField,
	__statusJTextField,
	__structureTypeJTextField,
	__structureNameJTextField,
	__wdJTextField;

/**
Equipment list.
*/
private JWorksheet __equipmentWorksheet;

/**
Court case list.
*/
private JWorksheet __courtCaseWorksheet;

/**
Map file list.
*/
private JWorksheet __mapFileWorksheet;

/**
Mutable JLists.
*/
private SimpleJList 
	__notesJList,
	__structureAKAJList;

/**
Structure number.
*/
private int __structureNum;

/**
Structure name.
*/
private String __structureName;

/**
Constructor.  This is a query-by-example constructor for the specified
structure name and number.
@param dmi an open HydroBaseDMI object.
@param structureName the name of the structure to display
@param structureNum the number of the structure to display
*/
public HydroBase_GUI_StructureMoreInfo(HydroBaseDMI dmi, String structureName, 
int structureNum) {
        // set member variables
        __dmi = dmi;
        __structureName = structureName;
	__structureNum = structureNum;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        // set up the GUI objects
        setupGUI();
        submitAndDisplayStructureGeolocQuery();            
	submitAndDisplayEquipmentQuery();            
        submitAndDisplayStructureAKAQuery();
	submitAndDisplayGeneralCommentQuery();

	JGUIUtil.setWaitCursor(this, false);
	__statusJTextField.setText("Finished retrieving data.");

	int wd = (new Integer(__wdJTextField.getText().trim())).intValue();
	int id = (new Integer(__idJTextField.getText().trim())).intValue();
	String name = __structureNameJTextField.getText().trim();

	String rest = "Structure Data - More Structure Information - "
		+ HydroBase_WaterDistrict.formWDID(wd, id)
		+ " (" + name + ")";
	if (	(JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( rest );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() +
		" - " + rest);
	}						
}

/**
Responds to action performed events.
@param event the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent event) {
	String routine = "HydroBase_GUI_StructureMoreInfo.actionPerformed";
        String actionCommand = event.getActionCommand();

        if (actionCommand.equals(__BUTTON_CLOSE)) {
		closeClicked();
        }
        else if (actionCommand.equals(__BUTTON_EXPORT)) {
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
			Vector outputStrings = formatOutput(format);
 			// Now export, letting the user decide the file...
			HydroBase_GUI_Util.export(this, eff[0], outputStrings);
		} 
		catch (Exception ex) {
			Message.printWarning(1, routine, "Error in export.");
			Message.printWarning (2, routine, ex);
		}
        }
        else if (actionCommand.equals(__BUTTON_HELP)) {
        }
        else if (actionCommand.equals(__BUTTON_PRINT)) {
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
			Vector outputStrings = formatOutput(format);
	 		// Now print...
			PrintJGUI.print(this, outputStrings);
		}
		catch (Exception ex) {
			Message.printWarning(1, routine, "Error while "
				+ "printing.");
			Message.printWarning (2, routine, ex);
		}	
        }
}

/**
Responsible for closing the GUI.
*/
public void closeClicked() {
	setVisible(false);	
	dispose();
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__dmi = null;
	__closeJButton = null;
	__exportJButton = null;
	__helpJButton = null;
	__printJButton = null;
	__ciuCodeJTextField = null;
	__decreedCapacityJTextField = null;
	__divJTextField = null;
	__estimatedCapacityJTextField = null;
	__idJTextField = null;
	__statusJTextField = null;
	__structureTypeJTextField = null;
	__structureNameJTextField = null;
	__wdJTextField = null;
	__equipmentWorksheet = null;
	__courtCaseWorksheet = null;
	__mapFileWorksheet = null;
	__notesJList = null;
	__structureAKAJList = null;
	__structureName = null;
	super.finalize();
}

/**
Formats output according to the desired format.
@param format the desired format.
*/
private Vector formatOutput(int format) {
	Vector v = new Vector();
	int size = __equipmentWorksheet.getRowCount();

	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
	        // For now these are the same, but when we do the full structure
	        // summary enhancement, the equipment will have to be split out.
	        v.add(HydroBase_GUI_Util.formatStructureHeader(
	                __structureNameJTextField.getText(),
	                __divJTextField.getText(),
	                __wdJTextField.getText(),
	                __idJTextField.getText(), format));
	        v.add("");
	        v.add("STRUCTURE TYPE: " + StringUtil.formatString(
	        	HydroBase_GUI_Util.trimText(__structureTypeJTextField),
			"%-20.20s")
			+ "CIU CODE: " 
			+ HydroBase_GUI_Util.trimText(__ciuCodeJTextField));
	        v.add("ESTIMATED CAP.: " + StringUtil.formatString(
	        	HydroBase_GUI_Util.trimText(
			__estimatedCapacityJTextField), "%-20.20s")
			+ "DECREED CAP.: " 
			+ HydroBase_GUI_Util.trimText(
			__decreedCapacityJTextField));
	        if (__structureAKAJList.getItemCount() == 0) {
	                v.add("STRUCTURE AKA :");
	        }
	        else {  
			// Add the first one...
	                v.add("STRUCTURE AKA : " 
				+ __structureAKAJList.getItem(0));
	                // Now add the others...
	                for (int i = 1; i < __structureAKAJList.getItemCount();
	                        i++) {
	                        v.add("                " 
					+ __structureAKAJList.getItem(i));
	                }
	        }
	        // Now do equipment...
	        v.add("");
	        v.add("                                            "
			+ "           EQUIPMENT");
	        v.add("INSTALLED      MEAS. DEVICE           RECORDER");
	        v.add("________________________________________"
			+ "________________________________________"
			+ "________________________________________");

	        Vector list = null;
	        // Need to skip the first item because it is the header
	        // information...

		String s0 = null;
		String s1 = null;
		String s2 = null;
		String s3 = null;
		String s4 = null;
	        for (int i = 0; i < size; i++) {
			s0 = __equipmentWorksheet.getValueAtAsString(i, 0);
			s1 = __equipmentWorksheet.getValueAtAsString(i, 1);
			s2 = __equipmentWorksheet.getValueAtAsString(i, 2);
			
	                v.add(StringUtil.formatString(s0, "%-15.15s")
	                	+ StringUtil.formatString(s1, "%-20.20s")
				+ "   " + StringUtil.formatString(s2, 
				"%-20.20s"));
	        }
		v.add("");

		v.add("FILING DATE     FILING NUMBER 1       "
			+ "FILING NUMBER 2           TITLE        "
			+ "SUPPLEMENTAL STATEMENT");
		size = __mapFileWorksheet.getRowCount();
		for (int i = 0; i < size; i++) {
			s0 = __mapFileWorksheet.getValueAtAsString(i, 0);
			s1 = __mapFileWorksheet.getValueAtAsString(i, 1);
			s2 = __mapFileWorksheet.getValueAtAsString(i, 2);
			s3 = __mapFileWorksheet.getValueAtAsString(i, 3);
			s4 = __mapFileWorksheet.getValueAtAsString(i, 4);
			
			v.add(s0 + "   " + s1 + "    " + s2 + "   "
				+ s3 + "   " + s4 + "   ");
		}
		v.add("");

		v.add("CASE NO      BOOK NO       PAGE NO       "
			+ "COMMENTS      DECREE SUMMARY");
		size = __courtCaseWorksheet.getRowCount();
		for (int i = 0; i < size; i++) {
			s0 = __courtCaseWorksheet.getValueAtAsString(i, 0);
			s1 = __courtCaseWorksheet.getValueAtAsString(i, 1);
			s2 = __courtCaseWorksheet.getValueAtAsString(i, 2);
			s3 = __courtCaseWorksheet.getValueAtAsString(i, 3);
			s4 = __courtCaseWorksheet.getValueAtAsString(i, 4);

			v.add(s0 + "   " + s1 + "   " + s2 + "   "
				+ s3 + "   " + s4 + "   ");
		}
		v.add("");

		v.add("NOTES");
		size = __notesJList.getItemCount();
		for (int i = 0; i < size; i++) {
			s0 = (String)__notesJList.getItem(i);
			v.add(s0);
		}
		
        }
        else {  
		char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);
	        v.add(HydroBase_GUI_Util.formatStructureHeader(format));
	        v.add(HydroBase_GUI_Util.formatStructureHeader(
	                __structureNameJTextField.getText(),
	                __divJTextField.getText(),
	                __wdJTextField.getText(),
	                __idJTextField.getText(), format));
	        v.add("");
	        v.add("STRUCTURE TYPE" + delim + "CIU CODE" + delim 
			+ "ESTIMATED CAP" + delim + "DECREED CAP" + delim 
			+ "STRUCTURE AKA" + delim);
	        String aka = "";
	        for (int i = 0; i < __structureAKAJList.getItemCount();
	                i++) {
	                if (i != 0) {
	                        aka = aka + ",";
	                }
	                aka = aka + __structureAKAJList.getItem(i);
	        }
	        v.add(__structureTypeJTextField.getText() + delim
	        	+ __ciuCodeJTextField.getText() + delim
	        	+ __estimatedCapacityJTextField.getText() + delim
	        	+ __decreedCapacityJTextField.getText() + delim
	        	+ aka + delim);
	        // Now add equipment...
	        v.add("");
	        v.add("INSTALLED" + delim + "MEAS DEVICE" + delim
	        	+ "RECORDER" + delim);
	        Vector list;

		String s0 = null;
		String s1 = null;
		String s2 = null;
		String s3 = null;
		String s4 = null;
	        for (int i = 0; i < size; i++) {
			s0 = __equipmentWorksheet.getValueAtAsString(i, 0);
			s1 = __equipmentWorksheet.getValueAtAsString(i, 1);
			s2 = __equipmentWorksheet.getValueAtAsString(i, 2);
			
	                v.add(s0 + delim + s1 + delim + s2 + delim);
	        }
		v.add("");

		v.add("FILING DATE" + delim + "FILING NUMBER 1" + delim
			+ "FILING NUMBER 2" + delim + "TITLE" + delim
			+ "SUPPLEMENTAL STATEMENT");
		size = __mapFileWorksheet.getRowCount();
		for (int i = 0; i < size; i++) {
			s0 = __mapFileWorksheet.getValueAtAsString(i, 0);
			s1 = __mapFileWorksheet.getValueAtAsString(i, 1);
			s2 = __mapFileWorksheet.getValueAtAsString(i, 2);
			s3 = __mapFileWorksheet.getValueAtAsString(i, 3);
			s4 = __mapFileWorksheet.getValueAtAsString(i, 4);
			
			v.add(s0 + delim + s1 + delim + s2 + delim
				+ s3 + delim + s4 + delim);
		}
		v.add("");

		v.add("CASE NO" + delim + "BOOK NO" + delim + "PAGE NO"
			+ delim + "COMMENTS" + delim + "DECREE SUMMARY");
		size = __courtCaseWorksheet.getRowCount();
		for (int i = 0; i < size; i++) {
			s0 = __courtCaseWorksheet.getValueAtAsString(i, 0);
			s1 = __courtCaseWorksheet.getValueAtAsString(i, 1);
			s2 = __courtCaseWorksheet.getValueAtAsString(i, 2);
			s3 = __courtCaseWorksheet.getValueAtAsString(i, 3);
			s4 = __courtCaseWorksheet.getValueAtAsString(i, 4);

			v.add(s0 + delim + s1 + delim + s2 + delim
				+ s3 + delim + s4 + delim);
		}
		v.add("");

		v.add("NOTES");
		size = __notesJList.getItemCount();
		for (int i = 0; i < size; i++) {
			s0 = (String)__notesJList.getItem(i);
			v.add(s0 + delim);
		}
	}
        return v;
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	String routine = "HydroBase_GUI_StructureMoreInfo.setupGUI";

        addWindowListener(this);

        Insets insetsTLNN = new Insets(7,7,0,0);
        Insets insetsNLNR = new Insets(0,7,0,7);
        Insets insetsNLBR = new Insets(0,7,7,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        Insets insetsNNNR = new Insets(0,0,0,7);
        Insets insetsNLNN = new Insets(0,7,0,0);
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        // Top JPanel
        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", topJPanel);

        // Top: West JPanel
        JPanel topLeftJPanel = new JPanel();
        topLeftJPanel.setLayout(gbl);
        topJPanel.add("West", topLeftJPanel);

        JGUIUtil.addComponent(topLeftJPanel, new JLabel("Structure Name:"),
		0, 0, 1, 1, 0, 0, insetsTLNR, gbc.NONE, gbc.WEST);
  
        JGUIUtil.addComponent(topLeftJPanel, new JLabel("DIV:"),
		1, 0, 1, 1, 0, 0, insetsTLNR, gbc.NONE, gbc.WEST);

        JGUIUtil.addComponent(topLeftJPanel,  new JLabel("WD:"),
		2, 0, 1, 1, 0, 0, insetsTLNR, gbc.NONE, gbc.WEST);

        JGUIUtil.addComponent(topLeftJPanel, new JLabel("ID:"),
		3, 0, 1, 1, 0, 0, insetsTLNR, gbc.NONE, gbc.WEST);

        __structureNameJTextField = new JTextField(__structureName.length()
		+ 5);
        __structureNameJTextField.setText(__structureName);
        __structureNameJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __structureNameJTextField,
		0, 1, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);

        __divJTextField = new JTextField(4);
        __divJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __divJTextField,
		1, 1, 1, 1, 0, 0, insetsNLBR, gbc.NONE, gbc.WEST);

        __wdJTextField = new JTextField(4);
        __wdJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __wdJTextField,
		2, 1, 1, 1, 0, 0, insetsNLBR, gbc.NONE, gbc.WEST);

        __idJTextField = new JTextField(10);
        __idJTextField.setEditable(false); 
        JGUIUtil.addComponent(topLeftJPanel, __idJTextField,
		3, 1, 1, 1, 0, 0, insetsNLBR, gbc.NONE, gbc.WEST);

        // Center
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BorderLayout());
        getContentPane().add("Center", centerJPanel);

        // Center: West JPanel
        JPanel centerWJPanel = new JPanel();
        centerWJPanel.setLayout(gbl);
        centerJPanel.add("West", centerWJPanel);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Structure Type:"),
		0, 2, 1, 1, 0, 0, insetsTLNR, gbc.NONE, gbc.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("CIU Code:"),
		1, 2, 1, 1, 0, 0, insetsTLNR, gbc.NONE, gbc.WEST);

   	JGUIUtil.addComponent(centerWJPanel, new JLabel("Estimated Capacity:"),
		2, 2, 1, 1, 0, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Decreed Capacity:"),
		3, 2, 1, 1, 0, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);

        __structureTypeJTextField = new JTextField(15);
        __structureTypeJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, 
		__structureTypeJTextField,
		0, 3, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);

        __ciuCodeJTextField = new JTextField(3);
        __ciuCodeJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __ciuCodeJTextField,
		1, 3, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);
   
        __estimatedCapacityJTextField = new JTextField(10);
        __estimatedCapacityJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __estimatedCapacityJTextField,
		2, 3, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);

        __decreedCapacityJTextField = new JTextField(10);
        __decreedCapacityJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __decreedCapacityJTextField,
		3, 3, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);

        // Center: South JPanel
        JPanel centerSJPanel = new JPanel();
        centerSJPanel.setLayout(gbl);
        centerJPanel.add("South", centerSJPanel);

        JGUIUtil.addComponent(centerSJPanel, new JLabel("Structure AKA:"),
		1, 1, 1, 1, 0, 0, insetsTLNR, gbc.NONE, gbc.WEST);

        JGUIUtil.addComponent(centerSJPanel, new JLabel("Equipment:"),
		2, 1, 1, 1, 0, 0, insetsTLNR, gbc.NONE, gbc.WEST);

        __structureAKAJList = new SimpleJList();

	PropList p = new PropList("HydroBase_GUI_StructureMoreInfo"
		+ ".JWorksheet");
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
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.ShowPopupMenu=true");
	p.add("JWorksheet.SelectionMode=ExcelSelection");

	int[] widths = null;
	JScrollWorksheet jsw = null;
	try {
		HydroBase_TableModel_Equipment tm = new
			HydroBase_TableModel_Equipment(new Vector());
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	
		jsw = new JScrollWorksheet(cr, tm, p);
		__equipmentWorksheet = jsw.getJWorksheet();

		__equipmentWorksheet.removeColumn(0);
		__equipmentWorksheet.removeColumn(1);

		widths = tm.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(2, routine, e);
		jsw = new JScrollWorksheet(0, 0, p);
		__equipmentWorksheet = jsw.getJWorksheet();
	}
	
	__equipmentWorksheet.setPreferredScrollableViewportSize(
		new Dimension(100, 60));
	__equipmentWorksheet.setHourglassJFrame(this);

        JGUIUtil.addComponent(centerSJPanel, 
		new JScrollPane(__structureAKAJList),
		1, 2, 1, 1, 1, 1, insetsNLBR, gbc.BOTH, gbc.WEST);
        JGUIUtil.addComponent(centerSJPanel, jsw,
		2, 2, 1, 1, 1, 1, insetsNLBR, gbc.BOTH, gbc.WEST);
	
         // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);

        // Bottom: North JPanel
        JPanel bottomNorthJPanel = new JPanel();
        bottomNorthJPanel.setLayout(gbl);
        bottomJPanel.add("North", bottomNorthJPanel);

	int[] mapFileWidths = null;
	Vector mapFileVector = submitMapfileQuery();
	JScrollWorksheet mapFileJSW = null;
	try {
		Generic_TableModel model = null;
		if (mapFileVector == null || mapFileVector.size() == 0) {
			model = new Generic_TableModel(5);
			model.setColumnClass(0, String.class);
			model.setColumnClass(1, Integer.class);
			model.setColumnClass(2, String.class);
			model.setColumnClass(3, String.class);
			model.setColumnClass(4, String.class);
		}
		else {
			model = new Generic_TableModel(mapFileVector);
		}

		model.setColumnInformation(0, "FILING\nDATE", "%20s", 8);
		model.setColumnInformation(1, "FILING\nNUMBER 1", "%8d", 7);
		model.setColumnInformation(2, "FILING\nNUMBER 2", "%8s", 7);
		model.setColumnInformation(3, "\nTITLE", "%20s", 10);
		model.setColumnInformation(4, "SUPPLEMENTAL\nSTATEMENT", "%20s",
			20);

		Generic_CellRenderer renderer = new Generic_CellRenderer(model);
		mapFileJSW = new JScrollWorksheet(renderer, model, p);
		__mapFileWorksheet = mapFileJSW.getJWorksheet();		
		mapFileWidths = renderer.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error setting up generic "
			+ "table information.");
		Message.printWarning(2, routine, e);
	}
	__mapFileWorksheet.setPreferredScrollableViewportSize(
		new Dimension(100, 60));
	__mapFileWorksheet.setHourglassJFrame(this);

	int[] courtCaseWidths = null;
	Vector courtCaseVector = submitCourtCaseQuery();
	JScrollWorksheet courtCaseJSW = null;
	try {
		Generic_TableModel model = null;
		if (courtCaseVector == null || courtCaseVector.size() == 0) {
			model = new Generic_TableModel(5);
			model.setColumnClass(0, String.class);
			model.setColumnClass(1, String.class);
			model.setColumnClass(2, String.class);
			model.setColumnClass(3, String.class);
			model.setColumnClass(4, String.class);
		}
		else {
			model = new Generic_TableModel(courtCaseVector);
		}

		model.setColumnInformation(0, "CASE\nNO", "%20d", 5);
		model.setColumnInformation(1, "BOOK\nNO","%20d",  4);
		model.setColumnInformation(2, "PAGE\nNO","%20d",  4);
		model.setColumnInformation(3, "\nCOMMENTS","%20d",  20);
		model.setColumnInformation(4, "\nDECREE SUMMARY","%20d", 20);  
	
		Generic_CellRenderer renderer = new Generic_CellRenderer(model);
		courtCaseJSW = new JScrollWorksheet(renderer, model, p);
		__courtCaseWorksheet = courtCaseJSW.getJWorksheet();
		courtCaseWidths = renderer.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error setting up generic "
			+ "table information.");
		Message.printWarning(2, routine, e);	
	}
	__courtCaseWorksheet.setPreferredScrollableViewportSize(
		new Dimension(100, 60));
	__courtCaseWorksheet.setHourglassJFrame(this);

        JGUIUtil.addComponent(centerSJPanel, new JLabel("Mapfile Information:"),
		1, 5, 1, 1, 0, 0, insetsTLNR, gbc.NONE, gbc.WEST);
	JGUIUtil.addComponent(centerSJPanel, mapFileJSW,
		1, 6, 1, 1, 0, 0, 0, 0, 0, 0,
		gbc.BOTH, gbc.WEST);
        JGUIUtil.addComponent(centerSJPanel, 
		new JLabel("Court Case Information:"),
		2, 5, 1, 1, 0, 0, insetsTLNR, gbc.NONE, gbc.WEST);
	JGUIUtil.addComponent(centerSJPanel, courtCaseJSW,
		2, 6, 1, 1, 0, 0, 0, 0, 0, 0,
		gbc.BOTH, gbc.WEST);
	

        JGUIUtil.addComponent(bottomNorthJPanel, new JLabel("Notes:"),
		0, 0, 1, 1, 0, 0, insetsTLNN, gbc.NONE, gbc.NORTHEAST);

	__notesJList = new SimpleJList();
        JGUIUtil.addComponent(bottomNorthJPanel, new JScrollPane(__notesJList),
		1, 0, 4, 1, 0, 0, insetsTLNR, gbc.BOTH, gbc.WEST);

        // Bottom: South JPanel
        JPanel bottomSouthJPanel = new JPanel();
        bottomSouthJPanel.setLayout(new BorderLayout());
        bottomJPanel.add("South", bottomSouthJPanel);
 
        // Bottom: South: North JPanel
        JPanel bottomSouthNorthJPanel = new JPanel();
        bottomSouthNorthJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomSouthJPanel.add("North", bottomSouthNorthJPanel);

        __printJButton = new JButton(__BUTTON_PRINT);
        __printJButton.setEnabled(true);
        __printJButton.addActionListener(this);
	__printJButton.setToolTipText("Print form data.");
        bottomSouthNorthJPanel.add(__printJButton);

        __exportJButton = new JButton(__BUTTON_EXPORT);
        __exportJButton.setEnabled(true);
        __exportJButton.addActionListener(this);
	__exportJButton.setToolTipText("Export data to a file.");
        bottomSouthNorthJPanel.add(__exportJButton);

        __closeJButton = new JButton(__BUTTON_CLOSE);
	__closeJButton.setToolTipText("Close form.");
        __closeJButton.addActionListener(this);
        bottomSouthNorthJPanel.add(__closeJButton);

//        __helpJButton = new JButton(__BUTTON_HELP);
//	__helpJButton.setEnabled(false);
//        __helpJButton.addActionListener(this);
//        bottomSouthNorthJPanel.add(__helpJButton);

        // Bottom: South: South JPanel
        JPanel bottomSSJPanel = new JPanel();
        bottomSSJPanel.setLayout(gbl);
        bottomSouthJPanel.add("South", bottomSSJPanel);
        GridBagConstraints gbcBSS = new GridBagConstraints();

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSSJPanel, __statusJTextField,
		0, 1, 10, 1, 1, 0, gbcBSS.HORIZONTAL, gbcBSS.WEST);

	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}

        setTitle(app + "More Structure Info");
        pack();
	setSize(getWidth() + 235, getHeight());
	JGUIUtil.center(this);

	show();

	if (widths != null) {
		__equipmentWorksheet.setColumnWidths(widths);
	}
	if (mapFileWidths != null) {
		__mapFileWorksheet.setColumnWidths(mapFileWidths);
	}
	if (courtCaseWidths != null) {
		__courtCaseWorksheet.setColumnWidths(courtCaseWidths);
	}
}

/**
Submits a court case query and displays the results in the gui.
*/
private Vector submitCourtCaseQuery() {
	JGUIUtil.setWaitCursor(this, true);
	String routine = "HydroBase_GUI_StructureMoreInfo"
		+ ".submitAndDisplayCourtCaseQuery";

	Vector results = null;
	try {
		results=__dmi.readCourtCaseListForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return null;
	}

	if (results == null || results.size() == 0) {
		JGUIUtil.setWaitCursor(this, false);
		return null;
	}	

	Vector v = new Vector();

	for (int i = 0; i < results.size(); i++) {
		HydroBase_CourtCase data = 
			(HydroBase_CourtCase)results.elementAt(i);
		GenericWorksheetData d = new GenericWorksheetData(5);
		
		d.setValueAt(0, data.getCase_no());
		d.setValueAt(1, data.getCase_no_book());
		d.setValueAt(2, data.getCase_no_page());
		d.setValueAt(3, data.getCase_no_comment());
		d.setValueAt(4, data.getDecree_summary());
		v.add(d);
	}
	
	JGUIUtil.setWaitCursor(this, false);

	return v;
}

/**
Submits an equipment query and displays the results in the gui.
*/
private void submitAndDisplayEquipmentQuery() {
	JGUIUtil.setWaitCursor(this, true);
	String routine = "HydroBase_GUI_StructureMoreInfo"
		+ ".submitAndDisplayEquipmentQuery";
	
	HydroBase_Equipment equipment = null;
	try {
		equipment = __dmi.readEquipmentForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	if (equipment == null) {
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	Vector results = new Vector();
	results.add(equipment);

	__equipmentWorksheet.setData(results);
}

/**
Submits a general comment query and displays the results in the gui.
*/
private void submitAndDisplayGeneralCommentQuery() {
	JGUIUtil.setWaitCursor(this, true);
	String routine = "HydroBase_GUI_StructureMoreInfo"
		+ ".submitAndDisplayGeneralCommentQuery";

	Vector results = null;

	try {
		results = __dmi.readGeneralCommentListForStructure_num(
			__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	if (results == null || results.size() == 0) {
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	HydroBase_GeneralComment data = null;
	String curString = null;
	Date date_entered = null;
	DateTime dt = null;
	String listLine = null;
	
	for (int i = 0; i < results.size(); i++) {
		data = (HydroBase_GeneralComment)results.elementAt(i);

		if (__dmi.isDatabaseVersionAtLeast(__dmi.VERSION_19990305)) {
			// New database.  Allow multiple comments per structure.
			date_entered = data.getDate_entered();
			dt = new DateTime(date_entered);
			dt.setPrecision(dt.PRECISION_DAY);
			
			try {
				listLine = DMIUtil.formatDateTime(__dmi, dt) 
					+ ": " + data.getComment();
			}
			catch (Exception e) {
				Message.printWarning(2, routine, e);
				listLine = ": " + data.getComment();
			}
			__notesJList.add(listLine);
		}
		else {
			curString = data.getGenl_comment();
			if (!DMIUtil.isMissing(curString)) {
				__notesJList.add(curString);
			}

			curString = data.getNotes();
			if (!DMIUtil.isMissing(curString)) {
				__notesJList.add(curString);
			}
		}
	}

	JGUIUtil.setWaitCursor(this, false);
}

/**
Submits a map file query and displays the results in the gui.
*/
private Vector submitMapfileQuery() {
	JGUIUtil.setWaitCursor(this, true);
	String routine = "HydroBase_GUI_StructureMoreInfo."
		+ "submitMapfileQuery";
		
	Vector results = null;	
	try {
		results = __dmi.readMapfileListForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return null;
	}

	if (results == null || results.size() == 0) {
		JGUIUtil.setWaitCursor(this, false);
		return null;
	}

	Vector v = new Vector();
	for (int i = 0; i < results.size(); i++) {
		GenericWorksheetData d = new GenericWorksheetData(5);
		HydroBase_Mapfile data =(HydroBase_Mapfile)results.elementAt(i);
		
		// store date as a string
		if (data.getMap_file_date() == null) {
			d.setValueAt(0, "");
		}
		else {
			DateTime dt = new DateTime(data.getMap_file_date());
			dt.setPrecision(dt.PRECISION_DAY);
			d.setValueAt(0, dt.toString());
		}
		
		d.setValueAt(1, new Integer(data.getMapfile_num()));
		d.setValueAt(2, data.getMap_suffix());
		d.setValueAt(3, data.getMap_supp_stmt());
		d.setValueAt(4, data.getMap_file());

		v.add(d);
	}
	
	JGUIUtil.setWaitCursor(this, false);

	return v;
}

/**
Submits a structure aka query and displays the results in the gui.
*/
private void submitAndDisplayStructureAKAQuery() {
	String routine = "HydroBase_GUI_StructureMoreInfo."
		+ "submitAndDisplayStructureAKAQuery";
	JGUIUtil.setWaitCursor(this, true);

	Vector v = null;
	try {
		v = __dmi.readStructureAKAListForStructure_num(__structureNum);
	}
	catch (Exception e) {	
		Message.printWarning(1, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	if (v == null || v.size() == 0) {
		JGUIUtil.setWaitCursor(this, false);
		return;
	}
	
        int size = v.size();
	HydroBase_StructureAKA data = null;
        for (int i = 0; i < size; i++) {
                data = (HydroBase_StructureAKA)v.elementAt(i);
                __structureAKAJList.add("" + data.getStructure_aka_name());
        }     

	JGUIUtil.setWaitCursor(this, false);
}

/**
Submits a structure geoloc query and displays the results in the gui.
*/
private void submitAndDisplayStructureGeolocQuery() {
	submitAndDisplaySPStructureViewQuery();
}

/**
Submits and structure view query and displays the results in the gui.
*/
private void submitAndDisplaySPStructureViewQuery() {
	String routine = "HydroBase_GUI_StructureMoreInfo"
		+ ".submitAndDisplaySPStructureViewQuery";
	JGUIUtil.setWaitCursor(this, true);

	HydroBase_StructureView data = null;
	try {
		data = __dmi.readStructureViewForStructure_num(
			__structureNum);
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

        __structureTypeJTextField.setText(
		__dmi.getStructureTypeDescription(
		data.getStr_type()));        
        __structureNameJTextField.setText("" + data.getStr_name());
 
        int curInt = data.getDiv();
        if (!DMIUtil.isMissing(curInt)) {
                __divJTextField.setText("" + curInt);
        }

        curInt = data.getWD();
        if (!DMIUtil.isMissing(curInt)) {
                __wdJTextField.setText("" + curInt);
        }

        curInt = data.getID();
        if (!DMIUtil.isMissing(curInt)) {
                __idJTextField.setText("" + curInt); 
        }

        __ciuCodeJTextField.setText("" + data.getCiu());

        double curDouble = data.getEst_capacity();
        if (!DMIUtil.isMissing(curDouble)) {
                __estimatedCapacityJTextField.setText(
			StringUtil.formatString(
			curDouble, "%7.2f").trim()
			+ " " + data.getEst_unit());
        }

        curDouble = data.getDcr_capacity();
        if (!DMIUtil.isMissing(curDouble)) {
                __decreedCapacityJTextField.setText(
			StringUtil.formatString(
			curDouble, "%7.2f").trim() + " " 
			+ data.getDcr_unit());
        }
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
