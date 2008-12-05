//-----------------------------------------------------------------------------
// HydroBase_GUI_ExemptDam - Exempt Dam Data GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 13 Oct 1997	DLG, RTi		Created initial version.
// 07 Dec 1997	SAM, RTi		Enable export/print.
// 29 Apr 1998  DLG, RTi		Updated to 1.1 event model, added
//					javadoc comments.
// 04 Apr 1999	SAM, RTi		Add HBDMI to queries.
// 2001-11-12	SAM, RTi		Change GUI to JGUIUtil.
//					Remove import *;
// 2002-02-25	SAM, RTi		Make ID text field wider.
//-----------------------------------------------------------------------------
// 2003-10-03	J. Thomas Sapienza, RTi	Initial Swing version.
// 2005-02-14	JTS, RTi		Checked all dmi calls to make sure they
//					use stored procedures.
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

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PrintJGUI;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;

/**
This class is a GUI for displaying additional exempt dam data.
*/
public class HydroBase_GUI_ExemptDam 
extends JFrame
implements ActionListener, WindowListener {

/**
Button labels.
*/
private final String	__BUTTON_HELP	= "Help",
			__BUTTON_EXPORT	= "Export",
			__BUTTON_PRINT	= "Print",
		 	__BUTTON_CLOSE	= "Close";

/**
The dmi used to communicate with the database.
*/
private HydroBaseDMI __dmi;

/**
The structure_num of the structure displayed on the GUI.
*/
private int __structureNum;

/**
GUI text fields.
*/
private JTextField
	__capacityJTextField,
	__damHeightJTextField,
	__damTypeJTextField,
	__dateAppJTextField,
	__dateConstJTextField,
	__divJTextField,
	__drainageJTextField,
	__idJTextField,
	__receiptJTextField,
	__sizeOutJTextField,
	__spillHeightJTextField,
	__spillWidthJTextField,
	__statusJTextField,
	__structureJTextField,
	__titleNumJTextField,
	__typeOutJTextField,
	__wdJTextField;

/**
The name of the structure displayed on the gui.
*/
private String __structureName;

/**
Constructor.
@param dmi the dmi to use for communicating with the database.
@param structureName the name of the structure to show on the gui.
@param structureNum the structure_num of the structure to show on the gui.
*/
public HydroBase_GUI_ExemptDam(HydroBaseDMI dmi, String structureName, 
int structureNum) {
	__dmi = dmi;
        __structureNum = structureNum;
        __structureName = structureName;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        setupGUI();

	submitAndDisplayDamQuery();

	int wd = (new Integer(__wdJTextField.getText().trim())).intValue();
	int id = (new Integer(__idJTextField.getText().trim())).intValue();
	String name = __structureName;

	String rest = "Structure Data - Nonjurisdictional Dam Detail - "
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
@param evt the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent evt) {
	String routine = "HydroBase_GUI_ExemptDam.actionPerformed";
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
			PrintJGUI.print(this, outputStrings, 8);
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
	__capacityJTextField = null;
	__damHeightJTextField = null;
	__damTypeJTextField = null;
	__dateAppJTextField = null;
	__dateConstJTextField = null;
	__divJTextField = null;
	__drainageJTextField = null;
	__idJTextField = null;
	__receiptJTextField = null;
	__sizeOutJTextField = null;
	__spillHeightJTextField = null;
	__spillWidthJTextField = null;
	__statusJTextField = null;
	__structureJTextField = null;
	__titleNumJTextField = null;
	__typeOutJTextField = null;
	__wdJTextField = null;
	__structureName = null;
	super.finalize();
}

/**
Formats output for printing or export.
@param format the format in which to format the output.
*/
private List formatOutput(int format) {
	List v = new Vector();

	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			HydroBase_GUI_Util.trimText(__structureJTextField),
			HydroBase_GUI_Util.trimText(__divJTextField),
			HydroBase_GUI_Util.trimText(__wdJTextField),
			HydroBase_GUI_Util.trimText(__idJTextField), format));
		v.add("");
		v.add("                                            "
			+ "      EXEMPT DAM DATA");
		v.add("RECEIPT:                      "
			+ HydroBase_GUI_Util.trimText(__receiptJTextField));
		v.add("DATE APPLICATION APPROVED:    "
			+ HydroBase_GUI_Util.trimText(__dateAppJTextField));
		v.add("DATE CONSTRUCTION COMP.:      "
			+ HydroBase_GUI_Util.trimText(__dateConstJTextField));
		v.add("DRAINAGE AREA:                "
			+ HydroBase_GUI_Util.trimText(__drainageJTextField));
		v.add("HEIGHT OF DAM:                "
			+ HydroBase_GUI_Util.trimText(__damHeightJTextField));
		v.add("HEIGHT OF SPILLWAY:           "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__spillHeightJTextField),
			"%-20.20s") + "SIZE OF OUTLET:        "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__sizeOutJTextField),"%s"));
		v.add("WIDTH OF SPILLWAY:            "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__spillWidthJTextField),
			"%-20.20s") + "TYPE OF OUTLET:        "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__typeOutJTextField),"%s"));
		v.add("TANK CAPACITY:                "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__capacityJTextField),
			"%-20.20s") + "TITLE NUMBER:          "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__titleNumJTextField),
			"%s"));
	}
	else {		
		char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);	
		v.add(HydroBase_GUI_Util.formatStructureHeader(format));
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			HydroBase_GUI_Util.trimText(__structureJTextField),
			HydroBase_GUI_Util.trimText(__divJTextField),
			HydroBase_GUI_Util.trimText(__wdJTextField),
			HydroBase_GUI_Util.trimText(__idJTextField), format));
		v.add("");
		v.add("RECEIPT" + delim + "DATE APPLICATION APPROVED" 
			+ delim + "DATE CONSTRUCTION COMP." + delim 
			+ "DRAINAGE AREA" + delim + "DAM TYPE" + delim 
			+ "HEIGHT OF DAM" + delim + "HEIGHT OF SPILLWAY" 
			+ delim + "WIDTH OF SPILLWAY" + delim 
			+ "SIZE OF OUTLET" + delim + "TYPE OF OUTLET" + delim 
			+ "TAKE CAPACITY" + delim + "TITLE NUMBER" + delim);
		v.add(
			HydroBase_GUI_Util.trimText(__receiptJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__dateAppJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__dateConstJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__drainageJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__damTypeJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__damHeightJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__spillHeightJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__spillWidthJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__sizeOutJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__typeOutJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__capacityJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__titleNumJTextField) 
			+ delim);
	}	

	return v;
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);

        // objects used throughout the GUI layout
        Insets insetsNLBR = new Insets(0,7,7,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        Insets insetsNNNR = new Insets(0,0,0,7);
        GridBagLayout gbl = new GridBagLayout();

        // Top JPanel
        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", topJPanel);

        // Top: West JPanel
        JPanel topWJPanel = new JPanel();
        topWJPanel.setLayout(gbl);
        topJPanel.add("West", topWJPanel);

        JLabel nameJLabel = new JLabel("Structure Name:");
        JGUIUtil.addComponent(topWJPanel, nameJLabel, 
		0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
 
        JLabel divJLabel = new JLabel("DIV:");
        JGUIUtil.addComponent(topWJPanel, divJLabel, 
		1, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JLabel wdJLabel = new JLabel("WD:");
        JGUIUtil.addComponent(topWJPanel, wdJLabel, 
		2, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JLabel idJLabel = new JLabel("ID:");
        JGUIUtil.addComponent(topWJPanel, idJLabel, 
		3, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __structureJTextField = new JTextField(16);
        __structureJTextField.setText(__structureName);
        __structureJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __structureJTextField, 
		0, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __divJTextField = new JTextField(5);
        __divJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __divJTextField, 
		1, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __wdJTextField = new JTextField(5);
        __wdJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __wdJTextField, 
		2, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __idJTextField = new JTextField(10);
        __idJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __idJTextField, 
		3, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        
        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BorderLayout());
        getContentPane().add("Center", centerJPanel);

        // Center: West JPanel
        JPanel centerWJPanel = new JPanel();
        centerWJPanel.setLayout(gbl);
        centerJPanel.add("West", centerWJPanel);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Receipt"), 
		0, 0, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __receiptJTextField = new JTextField(20);
        __receiptJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __receiptJTextField, 
		1, 0, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, 
		new JLabel("Date Application Approved:"), 
		0, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __dateAppJTextField = new JTextField(20);
        __dateAppJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __dateAppJTextField, 
		1, 1, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, 
		new JLabel("Date Construction Completed:"), 
		0, 2, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __dateConstJTextField = new JTextField(20);
        __dateConstJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __dateConstJTextField, 
		1, 2, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
 
        JGUIUtil.addComponent(centerWJPanel, new JLabel("Drainage Area:"), 
		0, 3, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __drainageJTextField = new JTextField(20);
        __drainageJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __drainageJTextField, 
		1, 3, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Dam Type:"), 
		0, 4, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __damTypeJTextField = new JTextField(20);
        __damTypeJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __damTypeJTextField, 
		1, 4, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Height of Dam:"), 
		0, 5, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __damHeightJTextField = new JTextField(20);
        __damHeightJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __damHeightJTextField, 
		1, 5, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Height of Spillway:"), 
		0, 6, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);    

        __spillHeightJTextField = new JTextField(20);
        __spillHeightJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __spillHeightJTextField, 
		1, 6, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Size of Outlet:"), 
		2, 6, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);        

        __sizeOutJTextField = new JTextField(20);
        __sizeOutJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __sizeOutJTextField, 
		3, 6, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Width of Spillway:"), 
		0, 7, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __spillWidthJTextField = new JTextField(20);
        __spillWidthJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __spillWidthJTextField, 
		1, 7, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Type of Outlet:"), 
		2, 7, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __typeOutJTextField = new JTextField(20);
        __typeOutJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __typeOutJTextField, 
		3, 7, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Tank Capacity:"), 
		0, 8, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __capacityJTextField = new JTextField(20);
        __capacityJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __capacityJTextField, 
		1, 8, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Title Number:"), 
		2, 8, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __titleNumJTextField = new JTextField(20);
        __titleNumJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __titleNumJTextField, 
		3, 8, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);

        // Bottom: South JPanel
        JPanel bottomSouthJPanel = new JPanel();
        bottomSouthJPanel.setLayout(new BorderLayout());
        bottomJPanel.add("South", bottomSouthJPanel);

        // Bottom: South: North JPanel
        JPanel bottomSouthNorthJPanel = new JPanel();
        bottomSouthNorthJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomSouthJPanel.add("North", bottomSouthNorthJPanel);

	SimpleJButton print = new SimpleJButton(__BUTTON_PRINT, this);
	print.setToolTipText("Print form data.");
        bottomSouthNorthJPanel.add(print);
	SimpleJButton export = new SimpleJButton(__BUTTON_EXPORT, this);
	export.setToolTipText("Export data to a file.");
        bottomSouthNorthJPanel.add(export);
	SimpleJButton close = new SimpleJButton(__BUTTON_CLOSE, this);
	close.setToolTipText("Close the form.");
        bottomSouthNorthJPanel.add(close);
//	SimpleJButton helpJButton = new SimpleJButton(__BUTTON_HELP, this); 
//        bottomSouthNorthJPanel.add(helpJButton);
//	helpJButton.setEnabled(false);

        // Bottom: South: South JPanel
        JPanel bottomSSJPanel = new JPanel();
        bottomSSJPanel.setLayout(gbl);
        bottomSouthJPanel.add("South", bottomSSJPanel);

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSSJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
      
        // Frame settings
        setTitle("Exempt Dam Data");
        pack(); 
        JGUIUtil.center(this);
        setVisible(true);        
}

/**
Submits a dam query and displays the results on the GUI.
*/
private void submitAndDisplayDamQuery() {
	String routine = "HydroBase_GUI_ExemptDam.submitAndDisplayDamQuery";
	JGUIUtil.setWaitCursor(this, true);

	HydroBase_StructureSmallDam data = null;
	try {
		data = __dmi.readStructureSmallDamForStructure_num(
			__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reaidng small dam "
			+ "structure data.");
		Message.printWarning(2, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	if (data == null) {
		JGUIUtil.setWaitCursor(this, false);
		return;
	}
	
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

        double curDouble = data.getDrain_area();
        if (!DMIUtil.isMissing(curDouble)) {
                __drainageJTextField.setText(StringUtil.formatString(curDouble, 
			"%6.1f").trim());
        }

        __damTypeJTextField.setText("" + data.getSmall_dam_type());

        curDouble = data.getHeight();
        if (!DMIUtil.isMissing(curDouble)) {
                __damHeightJTextField.setText(StringUtil.formatString(curDouble,
			"%7.2f").trim());
        }

        curDouble = data.getSpillway_height();
        if (!DMIUtil.isMissing(curDouble)) {
                __spillHeightJTextField.setText(StringUtil.formatString(
			curDouble, "%7.2f").trim());
        }

        curDouble = data.getSpillway_width();
        if (!DMIUtil.isMissing(curDouble)) {
                __spillWidthJTextField.setText(StringUtil.formatString(
			curDouble, "%7.2f").trim());
        }

        curDouble = data.getTank_capy();
        if (!DMIUtil.isMissing(curDouble)) {
                __capacityJTextField.setText(StringUtil.formatString(curDouble, 
			"%6.1f").trim());
        }

        curDouble = data.getOutlet_size();
        if (!DMIUtil.isMissing(curDouble)) {
                __sizeOutJTextField.setText(StringUtil.formatString(curDouble, 
			"%7.2f").trim());
        }

        __typeOutJTextField.setText("" + data.getOutlet_type());

        __titleNumJTextField.setText("" + data.getTitle_no());

        Date curDate = data.getAppl_date();
        if (!DMIUtil.isMissing(curDate)) {
		DateTime dt = new DateTime(curDate);
		dt.setPrecision(DateTime.PRECISION_DAY);
		try {
	                __dateAppJTextField.setText("" + dt.toString());
		}
		catch (Exception ex) {}
        }

        curInt = data.getReceipt();
        if (!DMIUtil.isMissing(curInt)) {
                __receiptJTextField.setText("" + curInt);
        }

        curDate = data.getCompl_date();
        if (!DMIUtil.isMissing(curDate)) {
		DateTime dt = new DateTime(curDate);
		dt.setPrecision(DateTime.PRECISION_DAY);
		try {
	                __dateConstJTextField.setText("" + dt.toString());
		}
		catch (Exception ex) {}
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
