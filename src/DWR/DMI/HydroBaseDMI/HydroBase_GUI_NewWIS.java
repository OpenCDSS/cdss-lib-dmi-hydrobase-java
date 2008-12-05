//-----------------------------------------------------------------------------
// HydroBase_GUI_NewWIS - GUI for creating a new wis.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History: 
//
// 16 Sep 1997	DLG, RTi 		Created initial class description.
// 19 Nov 1997	DLG, RTi		Updated all functionality
// 02 Mar 1998	DLG, RTi		Updated to java 1.1 event model.
// 03 Apr 1999	SAM, RTi		Add HBDMI for all queries.
// 21 May 1999	Steven A. Malers, RTi	Remove HBData.TIME_ZONE_DATUM reference
//					and clean up code.
// 2001-11-12	SAM, RTi		Change GUI to GUIUtil.
//-----------------------------------------------------------------------------
// 2003-12-04	J. Thomas Sapienza, RTi	Initial Swing version.
// 2005-02-15	JTS, RTi		Converted queries to stored procedures.
// 2005-03-09	JTS, RTi		HydroBase_SheetName 	
//					  -> HydroBase_WISSheetName.
// 2005-04-25	JTS, RTi		Turned dmiWrite() calls into dmi calls.
// 2005-04-28	JTS, RTi		Added finalize().
// 2005-05-23	JTS, RTi		Uses HydroBase_WaterDistrict now for
// 					parseWD().
// 2005-05-25	JTS, RTi		Converted queries that pass in a 
//					String date to pass in DateTimes 
//					instead.
// 2007-02-08	SAM, RTi		Remove dependence on CWRAT.
//					Pass JFrame to the constructor.
//					Add GeoViewUI for map interactions.
//					Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleJButton;
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;

public class HydroBase_GUI_NewWIS 
extends JFrame 
implements ActionListener, ItemListener, KeyListener, WindowListener {

/**
Class name.
*/
public final static String CLASS = "HydroBase_GUI_NewWIS";

/**
Button labels.
*/
private final String
	__BUTTON_CANCEL = 	"Cancel",
	__BUTTON_OK = 		"OK";

/**
Parent JFrame running the application.
*/
private JFrame __parent;

/**
GeoViewUI instance for map interactions.
*/
private GeoViewUI __geoview_ui = null;

/**
DMI to communicate with the database.
*/
private HydroBaseDMI __dmi;

/**
GUI for loading a wis.
*/
private HydroBase_GUI_LoadWIS __loadWISGUI;

/**
GUI text fields.
*/
private JTextField
	__sheetNameJTextField,
	__statusJTextField;

/**
GUI buttons.
*/
private SimpleJButton
	__cancelJButton,
	__okJButton;

/**
GUI combo boxes.
*/
private SimpleJComboBox		
	__associatedJComboBox,
	__streamJComboBox;

/**
Vector of HydroBase_Stream objects.
*/
private List __streamVector;


/**
Constructor.
@param parent the parent JFrame running the application.
@param geoview_ui GeoViewUI for map interactions.
@param dmi the dmi used to communicate with the database.
@param loadWISGUI the parent GUI for loading a WIS.
*/
public HydroBase_GUI_NewWIS(JFrame parent, GeoViewUI geoview_ui,
		HydroBaseDMI dmi, HydroBase_GUI_LoadWIS loadWISGUI) {
	__parent = parent;
	__geoview_ui = geoview_ui;
        __dmi = dmi;
        __loadWISGUI = loadWISGUI;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();
}

/**
Responds to button action events.
@param event that ActionEvent that happened.
*/
public void actionPerformed(ActionEvent event) {
	String command = event.getActionCommand();

        if (command.equals(__BUTTON_CANCEL)) {
                closeClicked();
        }        
        else if (command.equals(__BUTTON_OK)) {
                okClicked();
        }        
}      

/**
Closes and disposes of the GUI.
*/
private void closeClicked() {
        super.setVisible(false);
        dispose();
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__parent = null;
	__dmi = null;
	__loadWISGUI = null;
	__sheetNameJTextField = null;
	__statusJTextField = null;
	__cancelJButton = null;
	__okJButton = null;
	__associatedJComboBox = null;
	__streamJComboBox = null;
	__streamVector = null;
	super.finalize();
}

/**
Generates a list of streams based on the water district the sheet is most 
closely associated with.
*/
private void generateStreamList() {
	String routine = CLASS + ".generateStreamList";

	// initialize variables
	__streamVector = new Vector();
	__streamJComboBox.removeAll();

	if (__associatedJComboBox.getItemCount() == 0) {
		return;
	}

        JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Retrieving stream list...");

	String swd = HydroBase_WaterDistrict.parseWD(
		__associatedJComboBox.getSelected());
	int wd = (new Integer(swd)).intValue();

	List results = null;
	try {
		results = __dmi.readStreamListForWDStr_trib_to(wd, 
			DMIUtil.MISSING_INT);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading stream list "
			+ "from database.");
		Message.printWarning(2, routine, e);
		ready();
		return;
	}

	int size = results.size();
	if (results != null && size > 0) {
		HydroBase_Stream data = null;
		for (int i = 0; i < size; i++) {
			data = (HydroBase_Stream)results.get(i);
			__streamJComboBox.add(data.getStream_name().trim());
			__streamVector.add(data);
		}
	}

	ready();
}

/**
Responds to item events on the associate combobox and generates a new stream
list.
@param event the ItemEvent that happened.
*/
public void itemStateChanged(ItemEvent event) {
	if (event.getStateChange() != ItemEvent.SELECTED) {
		return;
	}

	Object o = event.getItemSelectable();;
	if (o.equals(__associatedJComboBox)) {
		generateStreamList();
	}
}

/**
Responds to key press events, treating enters the same as pressing the OK
button.
@param event the KeyEvent that happened.
*/
public void keyPressed(KeyEvent event) {
	int code = event.getKeyCode();
	
	// enter key is the same as selecting ok
	if (code == KeyEvent.VK_ENTER) {
		okClicked();
	}
}

/**
Does nothing.
*/
public void keyReleased(KeyEvent event) {}

/**
Does nothing.
*/
public void keyTyped(KeyEvent event) {}

/**
Called when the OK button is clicked or enter is pressed.
*/
private void okClicked() {
	String routine = CLASS + ".okClicked()";
        String sheetName = __sheetNameJTextField.getText();
	
	String wd = HydroBase_WaterDistrict.parseWD(
		__associatedJComboBox.getSelected());

	// perform sheet name checks
        if (sheetName == null || sheetName.equals("")) {
		new ResponseJDialog(this, "A Sheet Name must be entered.", 
			ResponseJDialog.OK).response();
		ready();
		return;
        }
	else if (DMIUtil.checkSingleQuotes(sheetName)) {
		new ResponseJDialog(this, "Cannot have single quotes in "
			+ "the Sheet Name.", 
			ResponseJDialog.OK).response();
		ready();
		return;
	} 

	sheetName = sheetName.trim();

	// get the current system day
	DateTime currentDate = new DateTime(
		DateTime.DATE_CURRENT | DateTime.PRECISION_MINUTE);
//	currentDate.shiftTimeZone(DateTime.getLocalTimeZoneAbbr());
	DateTime effectiveDate = null;
	try {
		effectiveDate = new DateTime(currentDate);
		effectiveDate.setPrecision(DateTime.PRECISION_DAY);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error formatting date time.");
		Message.printWarning(2, routine, e);
		ready();
		return;
	}

	// save new sheet		
	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Please Wait...Saving new sheet name");

	// perform query to check sheetName against a sheet which
	// may have been archived on the same date as the current day
    List results = null;
	try {
	        results = __dmi.readWISSheetNameList(DMIUtil.MISSING_INT, 
			DMIUtil.MISSING_INT, sheetName, null, true);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading sheet names "
			+ "from database.");
		Message.printWarning(2, routine, e);
		ready();
		return;
	}

	HydroBase_WISSheetName data = null;
	String sql = null;

	if (results != null && results.size() > 0) {
		// compare effective date for element 0 against the current 
		// day, since effectiveDate was ordered via DESC		
		data = (HydroBase_WISSheetName)results.get(0);
		DateTime recentDate = new DateTime(data.getEffective_date());

		int wis_num = data.getWis_num();
		Message.printStatus(2, routine, "Comparing current System day: "
			+ currentDate.toString(DateTime.FORMAT_Y2K_SHORT)
			+ "\nto most recent effective date: " 
			+ recentDate.toString(DateTime.FORMAT_Y2K_SHORT)
			+ "\nfor " + sheetName);
		int r = 0;
		if (recentDate.equals(currentDate,
			DateTime.PRECISION_DAY)) {
			r = new ResponseJDialog(this, "Previous formats for "
				+ "Sheet " + sheetName + " archived on " 
				+ recentDate.toString(DateTime.FORMAT_Y2K_SHORT)
				+ " will be lost. Continue?", 
				ResponseJDialog.YES 
				| ResponseJDialog.NO).response();
		}
		// return if no is selected
		if (r == ResponseJDialog.NO) {	
			ready();
			return;
		}
		// delete the all records associated with this sheetName and 
		// currentDay from Sheet_Name, WIS_Format, WIS_import, 
		// and WIS_Formula
		else {
			Message.printStatus(10, routine, 
				"Deleting previous saved formats");

			try {
				__dmi.deleteWISForWis_num(wis_num);
			}
			catch (Exception e){ 
				Message.printWarning(1, routine, 
					"Error deleting wis records "
					+ "from database.");
				Message.printWarning(2, routine, e);
				ready();
				return;
			}				

			/*
			try {
			        __dmi.deleteWISFormatForWis_num(wis_num);     
			}
			catch (Exception e) {
				Message.printWarning(1, routine, 
					"Error deleting wis_format records "
					+ "from database.");
				Message.printWarning(2, routine, e);
				ready();
				return;
			}

			try {
			        __dmi.deleteWISImportForWis_num(wis_num);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, 
					"Error deleting wis_import records "
					+ "from database.");
				Message.printWarning(2, routine, e);
				ready();
				return;
			}

			try {
			        __dmi.deleteWISFormulaForWis_num(wis_num);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, 
					"Error deleting wis_formula records "
					+ "from database.");
				Message.printWarning(2, routine, e);
				ready();
				return;
			}				

			try {
			        __dmi.deleteWISCommentsForWis_num(wis_num);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, 
					"Error deleting wis_comments records "
					+ "from database.");
				Message.printWarning(2, routine, e);
				ready();
				return;
			}				

			try {
			        __dmi
				.deleteWISSheetNameForSheet_nameEffectiveDate(
					sheetName, effectiveDate);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, 
					"Error deleting sheet_name records "
					+ "from database.");
				Message.printWarning(2, routine, e);
				ready();
				return;
			}
			*/
		}
	}
	
	sql = null;
	
	// if database is ODBC_ACCESS perform another sheet_name query from 
	// to determine the max wis_num.
	if (__dmi.getDatabaseEngine().equals("Access")) {
		int wis_num = DMIUtil.getMaxRecord(__dmi, "Sheet_name", 
			"wis_num") + 1;
		// REVISIT (JTS - 2004-06-18)
		// use the DMI!!
		try {
	        	sql = "INSERT INTO Sheet_Name (wis_num, sheet_name, "
				+ "effective_date, wd) VALUES (" 
				+ wis_num + ", '" + sheetName + "', " 
				+ DMIUtil.formatDateTime(__dmi, effectiveDate)
				+ ", " + wd + ")";           		
			__dmi.dmiWrite(sql);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, 
				"Error writing to sheet_name "
				+ "table.");
			Message.printWarning(2, routine, e);
			ready();
			return;
		}
	}
	else {		
		HydroBase_WISSheetName sn = new HydroBase_WISSheetName();
		sn.setSheet_name(sheetName);
		sn.setEffective_date(effectiveDate.getDate());
		sn.setWD(StringUtil.atoi(wd));
		try {
			__dmi.writeWISSheetName(sn);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, 
				"Error writing to sheet_name "
				+ "table.");
			Message.printWarning(2, routine, e);
			ready();
			return;
		}		
       	}

	// get the wis_num of the newly archived sheet using
	// sheet_name and effective date
	results = null;
	try {
		results = __dmi.readWISSheetNameList(DMIUtil.MISSING_INT, 
			DMIUtil.MISSING_INT, sheetName, effectiveDate);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading from sheet_name"
			+ " table.");
		Message.printWarning(2, routine, e);
		ready();
		return;
	}
	if (results != null && results.size() > 0) {
		data = (HydroBase_WISSheetName)results.get(0);

		// now that the new sheet_name record is entered and valid,
		// enter a matching wis_comments record (so that the
		// sheet can actually be used).
		HydroBase_WISComments wc = new HydroBase_WISComments();
		wc.setWis_num(data.getWis_num());
		wc.setSet_date(currentDate.getDate());
		wc.setArchive_date(currentDate.getDate());
		wc.setComment("");
		try {
			__dmi.writeWISComments(wc);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error writing to "
				+ "wis_comments table.");
			Message.printWarning(2, routine, e);
		}


	        String tempString = "Please Wait...Loading new WIS";
        	__statusJTextField.setText(tempString);

		HydroBase_Stream stream = null;
		if (__streamVector == null || __streamVector.size() == 0) {
			stream = null;
		}
		else {
			stream = (HydroBase_Stream)__streamVector.get(
				__streamJComboBox.getSelectedIndex());
		}

	        new HydroBase_GUI_WISBuilder(__parent, __geoview_ui, __dmi, data, stream);
	
		JGUIUtil.setWaitCursor(this, false);
	        tempString = "Finished loading new WIS.";
	        __statusJTextField.setText(tempString);

		// hide the load information gui, while disposing of this gui
	        __loadWISGUI.cancelClicked();
	        closeClicked();
	}
	else {
		ready();
		return;
	}
}

/**
Sets the GUI ready for user interaction.
*/
private void ready() {
	__statusJTextField.setText("Ready");
	JGUIUtil.setWaitCursor(this, false);
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);
        
        // objects to be used in the GridBagLayouts        
        Insets insetsTLNN = new Insets(7,7,0,0);
        Insets insetsTNNR = new Insets(7,0,0,7);  
        GridBagLayout gbl = new GridBagLayout();

        // North JPanel
        JPanel northJPanel = new JPanel();
        northJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", northJPanel);

        // North West JPanel
        JPanel northWJPanel = new JPanel();
        northWJPanel.setLayout(gbl);
        northJPanel.add("West", northWJPanel);

        JGUIUtil.addComponent(northWJPanel, 
		new JLabel("New Water Information Sheet Name:"),
		0, 0, 1, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
                
        __sheetNameJTextField = new JTextField(25);
	__sheetNameJTextField.addKeyListener(this);
        JGUIUtil.addComponent(northWJPanel, __sheetNameJTextField, 
		1, 0, 1, 1, 0, 0, insetsTNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel, 
		new JLabel("Sheet most closely associated with:"), 
		0, 1, 1, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
                
        __associatedJComboBox = new SimpleJComboBox();
	__associatedJComboBox.addItemListener(this);
        JGUIUtil.addComponent(northWJPanel, __associatedJComboBox, 
		1, 1, 1, 1, 0, 0, insetsTNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel, new JLabel("Main Stem:"), 
		0, 2, 1, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __streamJComboBox = new SimpleJComboBox();
        JGUIUtil.addComponent(northWJPanel, __streamJComboBox, 
		1, 2, 1, 1, 0, 0, insetsTNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
                                 
        // South JPanel
        JPanel southJPanel = new JPanel();
        southJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", southJPanel);
        
        // South North JPanel
        JPanel southNJPanel = new JPanel();
        southNJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        southJPanel.add("North", southNJPanel);
         
        __okJButton = new SimpleJButton("OK", this);
	__okJButton.setToolTipText("Accept entry and create new WIS.");
        southNJPanel.add(__okJButton);

        __cancelJButton = new SimpleJButton("Cancel", this);
	__cancelJButton.setToolTipText("Cancel entry and do not create a new "
		+ "WIS");
        southNJPanel.add(__cancelJButton);

        // South JPanel: South
        JPanel southSJPanel = new JPanel();
        southSJPanel.setLayout(gbl);
        southJPanel.add("South", southSJPanel);

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(southSJPanel, __statusJTextField, 
		0, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}
	
        setTitle(app + "New Water Information Sheet");
        setVisible(true);
}

/**
Shows or hides the GUI.
@param state whether to show the GUI (true) or hide it (false).
*/
public void setVisible(boolean state) {
	String routine = "HydroBase_GUI_NewWIS.setVisible";
        if (state) {
                Message.setTopLevel(this);
                __sheetNameJTextField.setText("");
		__associatedJComboBox.removeAll();
		HydroBase_WaterDistrict wd = null;
		String name = null;
		int w = -1;
		String s = null;
		List v = HydroBase_GUI_Util.generateWaterDistricts(__dmi);
		for (int i = 0; i < v.size(); i++) {	
			s = (String)v.get(i);	
			w = (Integer.decode(s)).intValue();
			try {
				wd = __dmi.lookupWaterDistrictForWD(w);
				name = wd.getWd_name();
			}
			catch (Exception e) {
				Message.printWarning(2, routine, e);
			}
			__associatedJComboBox.add("" + w + " - "+ name);
		}
		v = HydroBase_GUI_Util.generateDivisions(__dmi);
		for (int i = 0; i < v.size(); i++) {	
			s = (String)v.get(i);	
			__associatedJComboBox.add(s);
		}
		if (__associatedJComboBox.getItemCount() > 0) {
			__associatedJComboBox.select(0);
		}
		pack();
		setSize(getWidth() + 150, getHeight());
		JGUIUtil.center(this);
		generateStreamList();
        }
        super.setVisible(state);
}

/**
Does nothing.
*/
public void windowActivated(WindowEvent event) {}

/**
Does nothing.
*/
public void windowClosed(WindowEvent event) {}

/**
Closes the window.
@param event the WindowEvent that happened.
*/
public void windowClosing(WindowEvent event) {
	closeClicked();
}

/**
Does nothing.
*/
public void windowDeactivated(WindowEvent event) {}

/**
Does nothing.
*/
public void windowDeiconified(WindowEvent event) {}

/**
Does nothing.
*/
public void windowIconified(WindowEvent event) {}

/**
Does nothing.
*/
public void windowOpened(WindowEvent event) {}

}
