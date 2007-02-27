//-----------------------------------------------------------------------------
// HydroBase_GUI_Options - Options GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// Notes:   
// This class contains functionality related to the Options GUI.
//-----------------------------------------------------------------------------
// History:
//
// 23 Jul 1997	DLG, RTi	Created initial class description.
// 29 Aug 1997	DLG, RTi 	Added set/get user preferences
// 08 Oct 1997	DLG, RTi	Added export deliminator option
// 24 Feb 1998	DLG, RTi	Merged ALL user options into a single gui in 
//				response to CRDSS-00395. Also updated to java
//				1.1 event model.
// 14 May 1998	DLG, RTi	Added getDate.
// 03 Apr 1999	SAM, RTi	Add HBDMI to queries.
// 07 Apr 1999	SAM, RTi	Update for database changes.  Change so that
//				the list of districts for the default district
//				is set in a separate dialog.
// 27 Apr 2000	CEN, RTi	Added user level preference.
// 17 May 2000	CEN, RTi	gray out user level preference, for now.
// 11 Jul 2000	CEN, RTi	Adding General-List structures for calls 
//				using WIS structures or allowing any structure
//				from the database selection.
// 18 Jul 2000	CEN, RTi	Added descriptive labels to several tabs
// 24 May 2001	SAM, RTi	Enable Windows-like shift/ctrl/click selection
//				on district list.  Change GUI to JGUIUtil.
//				Add finalize().
// 15 Jul 2001	SAM, RTi	Add options to turn diversion coding on/off in
//				WIS and to select a map for the interface.
//				Re-enable the map tab.  Change some strings to
//				not be static to save some memory.  Remove old
//				map code that is not used.  Add an Apply button.
//				Add a checkState()method - it does not do
//				anything right now but need to rework so that
//				"OK" and "Apply" are disabled until something
//				in the GUI changes.
// 2002-03-04	SAM, RTi	Add to WIS the "SaveCarryForwardDiversionCoding"
//				option.
// 2002-04-30	SAM, RTi	Change Map.Default.GeoViewProject property to
//				key off the active water division.  Change the
//				WD.DivisionSelect and WD.DistrictSelect
//				properties to key off the active division.
// 2002-06-11	SAM, RTi	Remove SaveCarryForwardDiversionCoding
//				property - will always save complete record.
//-----------------------------------------------------------------------------
// 2003-03-25	J. Thomas Sapienza, RTi	Move to Swing.
// 2003-03-26	JTS, RTi		* Continue working on updating GUI.
//					* Enabled ...GUI_SelectDefaultDistrict
//					  and ...GUI_TSDateBuilder
//					* Fixed some hourglass issues
//					* Cleared up pressing RE-VISITs
//					* Implemented a new List Model for the
//					  district and division lists.
// 2003-03-27	JTS, RTi		Worked on the logic behind how the new
//					list model should work with the lists.
// 2003-03-28	JTS, RTi		Title of form now shows the program name
//					and the form name.
// 2003-04-04	JTS, RTi		* Changed JGUIUtil to JGUIUtil.
//					* Moved from TSDate to DateTime.
// 2003-04-07	JTS, RTi		Began adding code to selectively enable
//					OK and Apply buttons if any values have
//					changed.
// 2003-04-08	JTS, RTi		* Finished adding the above code.
//					* Misc revisions, commenting.
// 2003-05-22	JTS, RTi		Added a new constructor so the Options
//					GUI can be started up invisibly.
// 2004-01-08	JTS, RTi		Corrected bug that was resulting in
//					the proper districts and divisions
//					not being highlighted at startup.
// 2004-07-20	JTS, RTi		Commented out the toggle for doing
//					WIS calculations automatically or not.
// 2004-07-26	JTS, RTi		* Text now refers to "StateView/CWRAT"
//					  when changing passwords.
//					* Added titles to all the dialog boxes.
// 2005-02-14	JTS, RTi		Converted all queries to use stored
//					procedures.
// 2005-03-09	JTS, RTi		HydroBase_SheetName 	
//					  -> HydroBase_WISSheetName.
// 2005-04-12	JTS, RTi		MutableJList changed to SimpleJList.
// 2005-04-25	JTS, RTi		Converted dmiWrite to use the DMI.
// 2005-11-16	JTS, RTi		Saving of options, in particular the
//					default district, was severely buggy
//					and was corrected.
// 2007-02-08	SAM, RTi		Remove dependence on CWRAT.
//					Just pass a JFrame in the constructor.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.File;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JFileChooserFactory;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJList;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleFileFilter;
import RTi.Util.GUI.SimpleJButton;
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.DateTimeBuilderJDialog;

/**
This JFrame implements a tabbed panel that lets the user specify options for
use with StateView and CWRAT.  The options are edited as follows:
<ol>
<li>	User selects tabs and enters values in the editable components.  This
	may trigger database queries, data checks, and confirmation.
	However, preferences are not actually saved yet.</li>
<li>	If OK is pressed, then
	setUserPreferences()
	and
	__dmi.saveUserPreferences()
	are called.</li>
<li>	For the former, the __dmi.setPreferenceValue(property,value)is called 
	for each of the properties in the panels.  If the preference is 
	different from the preference stored in the proplist, then a flag
	is set with the preference marking it as having been changed at 
	run time.
	</li>
<li>	From above, when the __dmi.savePreferneces()method is called, each 
	of the preferences in the proplist that have changed will be written
	to the database.</li>
</ol>
The bottom line is that no preferences get changed until the OK or Apply
buttons are pressed.
*/
public class HydroBase_GUI_Options extends JFrame implements 
ActionListener, WindowListener, KeyListener, 
ListSelectionListener
{

/**
Whether setupGUI() has completed (used to avoid lots of null pointer exceptions
when checkChanged() gets called.
*/
private boolean __doneWithSetup = false;

/**
Whether the program is currently inside the generateDistricts() method, to avoid
lots of pointless calls to checkChanged().
*/
private boolean __generatingDistricts = false;

/**
Used when setting up the GUI to ignore any actions that might call 
"checkChanged", such as when lists and combo boxes are being populated.
*/
private boolean __ignoreChanges = false;

/**
GeoViewUI interface for map interaction.
*/
private GeoViewUI __geoview_ui = null;

/**
OptionsUI interface for options interaction.
*/
private OptionsUI __options_ui = null;

/**
Reference to an open and non-null HydroBaseDMI object.
*/
private HydroBaseDMI __dmi = null;

/**
Apply button
*/
private JButton __applyJButton = null;
/**
Cancel button
*/
private JButton __cancelJButton = null;
/**
Change button
*/
private JButton __changeJButton = null;

/**
OK Button
*/
private JButton __okJButton = null;
/**
Set interval button 
*/
private JButton __setIntervalJButton = null;
/**
wd button
*/
private JButton __wdJButton = null;
/**
WIS button
*/
private JButton __wisJButton = null;

/**
Confirm password field
*/
private JPasswordField __confirmPWJPasswordField = null;
/**
New password field.
*/
private JPasswordField __newPWJPasswordField = null;
/**
Old password field
*/
private JPasswordField __oldPWJPasswordField = null;

/**
past hour radio button -- in the same group as time interval
*/
private JRadioButton __pastHoursJRadioButton = null;
/**
Time interval radio button -- in the same group as past hours
*/
private JRadioButton __timeIntervalJRadioButton = null;

/**
Bypass off radio button -- paired with bypass on
*/
private JRadioButton __bypassOffJRadioButton = null;
/**
Bypass on radio button -- paired with bypass off
*/
private JRadioButton __bypassOnJRadioButton = null;

/**
Call list using wis radio button -- paired with call list any struct
*/
private JRadioButton __callListUsingWISJRadioButton = null;
/**
Call list any struct radio button -- paired with call list using wis
*/
private JRadioButton __callListAnyStructJRadioButton = null;

/**
Auto check box -- paired with manual 
*/
//private JRadioButton __autoJRadioButton = null;

/**
Manual check box -- paired with auto
*/
//private JRadioButton __manualJRadioButton = null;

/**
Div coding no check box -- paired with div coding yes
*/
private JRadioButton __divCodingNoJRadioButton = null;
/**
Div coding yes check box -- paired with div coding no
*/
private JRadioButton __divCodingYesJRadioButton = null;

/**
Default district jtext field
*/
private JTextField __defaultDistrictJTextField = null;
/**
Default GVP text field
*/
private JTextField __defaultGVPJTextField = null;
/**
End time text field
*/
private JTextField __endTimeJTextField = null;
/**
Past hours text field
*/
private JTextField __pastHoursJTextField = null;
/**
start time text field
*/
private JTextField __startTimeJTextField = null;
/**
Status text field
*/
private JTextField __statusJTextField = null;

/**
Division list.
*/
private SimpleJList __divisionJList = null;
/**
District list.
*/
private SimpleJList __districtJList = null;

/**
Date properties.
*/
private PropList __dateProps = null;

/**
Button for selecting the default district
*/
private SimpleJButton	__defaultDistrictJButton = null;

/**
Priority combo box.
*/
private SimpleJComboBox __priorityJComboBox = null;
/**
User level combo box.
*/
private SimpleJComboBox __userLevelJComboBox = null;
/**
Wis combo box.
*/
private SimpleJComboBox __wisJComboBox = null;

/**
Export format strings.
*/
public final static String	
	SCREEN = 	"SCREEN",	
	QINFO = 	"Summary",
	TAB = 		"[TAB]",
	SEMICOLON = 	";",
	COMMA = 	",",
	PIPE = 		"|";

/**
Priority preference display.
*/
public final static String 	
	ADJ_DATE = 		"Adj. Date",
	PRIORITY_NUMBER	= 	"Priority Number",
	APPRO_DATE = 		"Appro. Date",
	ADMIN_NO = 		"Admin. Number",
	DECREED_AMT = 		"Decreed Amount";

/**
User level preference.
*/
public final static String 	
	VIEW_ONLY = 	"View Only",
	POWER_USER = 	"Advanced User",
	ADMINISTRATOR = "Administrator",
	OTHER_USER = 	"Other";

/**
General-purpose strings.
*/
private final static String	
	//__HELP_STRING = 	"CWRAT.HydroBase_GUI_Options",
	__NONE = 		"NONE",
	__TIME_INTERVAL = 	"Time Period",
        __MAP = 		"Map",
        __WATER_DISTRICT = 	"Water District Filter",
        __GENERAL = 		"General",
        //__NETWORK = 		"Network Settings",
	__ADMIN = 		"Administration";

/**
Password strings.
*/
private final static String
	__OLD_PW = 	"Old Password",
	__NEW_PW = 	"New Password",
        __CONFIRM_PW = 	"Confirm Password";
	
/**
Misc Strings
*/
private final static String 
	__BUTTON_APPLY = 			"Apply",
	__BUTTON_GENERATE_LIST = 		"Generate List",
	__BUTTON_HELP = 			"Help",
	__BUTTON_OK = 				"OK",
	__BUTTON_PAST = 			"Past",
	__BUTTON_PERIOD = 			"Period",
	__BUTTON_CANCEL = 			"Cancel",
	__BUTTON_CHANGE = 			"Change",
	__BUTTON_SET_DEFAULT_DISTRICT = 	"Set Default District",
	__BUTTON_SELECT_DESELECT_ALL = 		"Select/Deselect All",
	__BUTTON_SET_PERIOD = 			"Set Period",
	__BUTTON_BROWSE_FOR_DEFAULT_GVP = 	"BrowseForDefaultGVP";

/**
Constructor.
@param parent the CWRATMainJFrame object that instantiated this one.
@param geoview_ui GeoViewUI for map interaction.
@param options_ui OptionsUI for options interaction.
@param dmi an open, non-null DMI.
@throws Exception if an error occurs.
*/
public HydroBase_GUI_Options(JFrame parent, GeoViewUI geoview_ui,
		OptionsUI options_ui, HydroBaseDMI dmi) 
throws Exception {
	this(parent, geoview_ui, options_ui, dmi, true);
}

/**
Constructor.
@param parent the CWRATMainJFrame object that instantiated this one.
@param geoview_ui GeoViewUI for map interaction.
@param options_ui OptionsUI for options interaction.
@param dmi an open, non-null DMI.
@param visible whether the gui should be visible at creation time
@throws Exception if an error occurs.
*/
public HydroBase_GUI_Options(JFrame parent, GeoViewUI geoview_ui,
		OptionsUI options_ui, HydroBaseDMI dmi, 
boolean visible) 
throws Exception {
	if (dmi == null) {
		throw new Exception ("null HydroBaseDMI object passed to "
			+ "HydroBase_GUI_Options constructor.");
	}
	if (!dmi.isOpen()) {
		throw new Exception ("unopened HydroBaseDMI object passed to "
			+ "HydroBase_GUI_Options constructor.");
	}
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        __dmi = dmi;
	__geoview_ui = geoview_ui;
	__options_ui = options_ui;

	setupGUI(visible);
}

/**
This function responds to ActionEvents.
@param evt ActionEvent object.
*/
public void actionPerformed(ActionEvent evt) {
	String command = evt.getActionCommand();
	String routine = "HydroBase_GUI_Options.actionPerformed";

	if (command.equals(__BUTTON_BROWSE_FOR_DEFAULT_GVP)) {
		browseForGVP();
	}
	else if (command.equals(__BUTTON_APPLY)) {
		applyClicked();
	}
	else if (command.equals(__BUTTON_CANCEL)) {
		closeClicked();
	}
	else if (command.equals(__BUTTON_CHANGE)) {
		try {
			changeClicked();
		}
		catch (Exception e) {
			Message.printWarning(1, routine, e);
		}
	}
	else if (command.equals(__BUTTON_GENERATE_LIST)) {
		try {
			generateWIS();
		}
		catch (Exception e) {
			Message.printWarning(1, routine, e);
		}
	}
	else if (command.equals(__BUTTON_HELP)) {
	}
	else if (command.equals(__BUTTON_OK)) {
		okClicked();
	}
	else if (command.equals(__BUTTON_PAST)) {
		__setIntervalJButton.setEnabled(false);
		checkChanged();
	}
	else if (command.equals(__BUTTON_PERIOD)) {
		__setIntervalJButton.setEnabled(true);
		checkChanged();
	}
	else if (command.equals(__BUTTON_SET_DEFAULT_DISTRICT)) {
		new HydroBase_GUI_SelectDefaultDistrict(this, __dmi);
		// Display the contents of the selection...
		String default_district =
			HydroBase_GUI_SelectDefaultDistrict
			.getDefaultDistrict();
		int index = default_district.indexOf("(");
		if (index > -1) {
			default_district = default_district.substring(0, index);
		}
		__defaultDistrictJTextField.setText(default_district);
		checkChanged();
	}
	else if (command.equals(__BUTTON_SELECT_DESELECT_ALL)) {
		selectClicked();
	}
	else if (command.equals(__BUTTON_SET_PERIOD)) {
		setPeriodClicked();
	}
	else {
		checkChanged();
	}
}

/**
This function is called when the apply JButton is pressed and from the
okClicked() method.
*/
private void applyClicked() {
	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Please Wait...applying preferences...");
	setUserPreferences();
	__dmi.saveUserPreferences();
	JGUIUtil.setWaitCursor(this, false);
	__statusJTextField.setText("Finished applying preferences.");
}

/**
Browse for a default GVP file.  When the file is selected, set in the
__defaultGVPJTextField.
*/
private void browseForGVP() {
	JGUIUtil.setWaitCursor(this, true);
	String lastDirectorySelected = JGUIUtil.getLastFileDialogDirectory();
	JFileChooser fc = null;
	if (lastDirectorySelected != null) {
		fc = JFileChooserFactory.createJFileChooser(
			lastDirectorySelected);
//		fc = new JFileChooser(lastDirectorySelected);
	}
	else {
		fc = JFileChooserFactory.createJFileChooser();
//		fc = new JFileChooser();
	}

	fc.setDialogTitle("Select GeoView Project File");
	SimpleFileFilter gvp = new SimpleFileFilter("gvp", 
		"GeoView Project Files");
	fc.addChoosableFileFilter(gvp);
	fc.setAcceptAllFileFilterUsed(false);
	fc.setFileFilter(gvp);
	fc.setDialogType(JFileChooser.OPEN_DIALOG);	
	
	JGUIUtil.setWaitCursor(this, false);
	int retVal = fc.showOpenDialog(this);
	if (retVal != JFileChooser.APPROVE_OPTION) {
		return;
	}

	String currDir = (fc.getCurrentDirectory()).toString();

	if (!currDir.equalsIgnoreCase(lastDirectorySelected)) {
		JGUIUtil.setLastFileDialogDirectory(currDir);
	}
	String fileName = currDir + File.separator 
		+ fc.getSelectedFile().getName();

	__defaultGVPJTextField.setText(fileName);
	checkChanged();
}

/**
This method responds to the Change JButton Action Event.
*/
public void changeClicked() 
throws Exception {
	// initialize variables
	String oldPW = new String(__oldPWJPasswordField.getPassword());
	String newPW = new String(__newPWJPasswordField.getPassword());
	String confirmPW = new String(__confirmPWJPasswordField.getPassword());
	String login = __dmi.getUserLogin();
	String notFound = "Password not found on remote connection."
			+ " Cannot change password.";

	// check for nulls and empty Strings
	if (!isValidPassword(oldPW, __OLD_PW)) {
		return;
	}
	if (!isValidPassword(newPW, __NEW_PW)) {
		return;
	}
	if (!isValidPassword(confirmPW, __CONFIRM_PW)) {
		return;
	}

	// ok to trim the password Strings now
	oldPW = oldPW.trim();
	newPW = newPW.trim();
	confirmPW = confirmPW.trim();
	
	// ensure that the newPW and the confirmPW are the same
	if (!confirmPW.equals(newPW)) {
		new ResponseJDialog(this, "Invalid Password",
			"New Password and Confirm "
			+ "Password must be the same.", ResponseJDialog.OK);
		return;
	}

	// ensure that old password and new password are not the same.
	if (oldPW.equals(newPW)) {
		new ResponseJDialog(this, "Invalid Password",
			"Old password and new password "
		 	+ "are the same. Password change aborted.",
			ResponseJDialog.OK);
		return;
	}
	
	JGUIUtil.setWaitCursor(this, true);

	// determine if user has permissions to change password.
	if (!__dmi.canWriteToDatabase()) {
		new ResponseJDialog(this, "Invalid Password",
			"User does not have permissions "
			+ "to change password.", ResponseJDialog.OK);
		ready();
		return;
	}

	// confirm old password before changing password
	// query the HOST database and determine if password
	// for current user is the same as the one that has been
	// typed in. Passwords between host and local database
	// MUST remain in sync with one another.
	HydroBase_UserSecurity us = 
		__dmi.readUserSecurityForLoginPasswordApplication(
		login, oldPW, "CWRAT");

	// password not found on host connection
	if (us == null) {
		new ResponseJDialog(this, "Password not Found",
			notFound, ResponseJDialog.OK);
		ready();
		return;
	}

	// check that login and new password are unique on the HOST connection
	// as we cannot rely on user_num since the LOCAL connection does
	// not know about other users. A unique record on the HOST connection
	// occurs for login, password fields. If a record is returned then
	// a conflict occurs meaning that this combination is already being
	// used.
	us = 
		__dmi.readUserSecurityForLoginPasswordApplication(
		login, newPW, "CWRAT");

	if (us != null) {
		new ResponseJDialog(this, "Invalid Password",
			"Login and Password"
			+ " combination already exist. Select another"
			+ " password.", ResponseJDialog.OK);
		ready();
		return;
	}

	// old password found on HOST connection and new password and login 
	// are unique. First update the password on HOST connection to the 
	// new password.
	if (__dmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_19990305)) {
		// Application in user_security...
		__dmi.updateUserSecurityPasswordForLoginPasswordApplication(
			newPW,login, oldPW, "CWRAT");		
	}
	else {	
		// No application in user_security...
		__dmi.updateUserSecurityPasswordForLoginPasswordApplication(
			newPW, login, oldPW, null);
	}
	// when it comes time to start writing to the database.

	// Update the password on LOCAL connection to the new password.
	JGUIUtil.setWaitCursor(this, false);
	__oldPWJPasswordField.setText("");
	__newPWJPasswordField.setText("");
	__confirmPWJPasswordField.setText("");
	new ResponseJDialog(this, "Password Changed",
		"Password successfully changed.",
		ResponseJDialog.OK);
       	__dmi.setUserPassword(newPW);
}

/**
Checks to see whether any of the values have changed from the user's 
preferences in the database.  If they have, the OK and APPLY buttons are 
enabled.  If they have not, the OK and APPLY buttons are disabled.
*/
public void checkChanged() {
	if (__ignoreChanges) {
		return;
	}
	boolean changed = false;
	if (!__doneWithSetup) {
		return;
	}
	String s = "";
	s = __pastHoursJTextField.getText();
	if (!s.equalsIgnoreCase(__dmi.getPreferenceValue("Time.PastValue"))) {
		changed = true;
	}
	s = __startTimeJTextField.getText();
	if (!s.equalsIgnoreCase(__dmi.getPreferenceValue(
		"Time.IntervalStart"))){
		changed = true;
	}
	s = __endTimeJTextField.getText();
	if (!s.equalsIgnoreCase(__dmi.getPreferenceValue("Time.IntervalEnd"))){
		changed = true;
	}
	
        if (__pastHoursJRadioButton.isSelected()) {
		if (!__dmi.getPreferenceValue("Time.PastFlag").equals("1") ||
		    !__dmi.getPreferenceValue("Time.IntervalFlag").equals("0")){
			changed = true;	
		}
        }
        else {	
		if (!__dmi.getPreferenceValue("Time.PastFlag").equals("0") ||
		    !__dmi.getPreferenceValue("Time.IntervalFlag").equals("1")){
			changed = true;	
		}
        }

        //---------------------------------------------------------------------
        // Map Tab
        //---------------------------------------------------------------------
	s = __dmi.getPreferenceValue("Map."
		+ HydroBase_GUI_Util.getActiveWaterDivision() 
		+ ".GeoViewProject");
	if (!s.equalsIgnoreCase(__defaultGVPJTextField.getText())) {
		changed = true;
	}

        //---------------------------------------------------------------------
        // Water District Filter Tab
        //---------------------------------------------------------------------
	// Old style...
	s = listToString(__divisionJList);
	if (!s.equals(__dmi.getPreferenceValue("WD."
		+ HydroBase_GUI_Util.getActiveWaterDivision() 
		+ ".DivisionSelect"))) {
		changed = true;
	}
	s = listToString(__districtJList);
	if (!s.equals(__dmi.getPreferenceValue("WD."
		+ HydroBase_GUI_Util.getActiveWaterDivision()
		+ ".DistrictSelect"))) {
		changed = true;
	}
	
	s = __defaultDistrictJTextField.getText();

	// trim the WD preface from the selection ...
	if (s.equals(HydroBase_GUI_Util._ALL_DIVISIONS)) {
		// keep as is
	}
	else if (!s.equals(__NONE)) {
		s = s.substring(5).trim();
	}

	if (!s.equals(__dmi.getPreferenceValue("WD.DistrictDefault"))) {
		changed = true;
	}

        //---------------------------------------------------------------------
        // General Tab
        //---------------------------------------------------------------------
	s = __dmi.getPreferenceValue("General.CallingRight");
	if (s != null) {
		if (!s.equalsIgnoreCase(
			((String)__priorityJComboBox.getSelectedItem()).
				trim())) {
			changed = true;
		}
	}
	
	String userLevel = 
		((String)__userLevelJComboBox.getSelectedItem()).trim();
	if (userLevel.equals(VIEW_ONLY)) {
		if (!__dmi.getPreferenceValue("General.UserLevel").equals("1")){
			changed = true;
		}		
	}
	else if (userLevel.equals(POWER_USER)) {
		if (!__dmi.getPreferenceValue("General.UserLevel").equals("2")){
			changed = true;
		}
	}
	else if (userLevel.equals(ADMINISTRATOR)) {
		if (!__dmi.getPreferenceValue("General.UserLevel").equals("3")){
			changed = true;
		}		
	}
	else {
		if (!__dmi.getPreferenceValue("General.UserLevel").equals("4")){
			changed = true;
		}		
	}
	
	if (__bypassOnJRadioButton.isSelected()) {
		if (!__dmi.getPreferenceValue("General.BypassCall").
			equals("1")){
			changed = true;
		}
	}
	else {	
		if (!__dmi.getPreferenceValue("General.BypassCall").
			equals("0")){
			changed = true;
		}	
	}

	if (__callListUsingWISJRadioButton.isSelected()) {
		if (!__dmi.getPreferenceValue("General.CallStruct").
			equals("FromWIS")) {
			changed = true;
		}
	}
	else {
		if (!__dmi.getPreferenceValue("General.CallStruct").
			equals("FromQuery")) {
			changed = true;
		}	
	} 

        //---------------------------------------------------------------------
        // WIS Tab
        //---------------------------------------------------------------------
	s = __dmi.getPreferenceValue("WIS.WISdefault");
	String wis = (String)__wisJComboBox.getSelectedItem();
	if (s != null && wis != null) {
		if (!s.equalsIgnoreCase(wis.trim())) {
			changed = true;
		}
	}
	/*
	if (__autoJRadioButton.isSelected()) {
		if (!__dmi.getPreferenceValue("WIS.CalculateAuto").
			equals("1")) {
			changed = true;
		}
	}
	else {	
		if (!__dmi.getPreferenceValue("WIS.CalculateAuto").
			equals("0")) {
			changed = true;
		}	
	}
	*/
	/*
	if (__manualJRadioButton.isSelected()) {
		if (!__dmi.getPreferenceValue("WIS.CalculateMan").
			equals("1")) {
			changed = true;
		}
	}
	else {	
		if (!__dmi.getPreferenceValue("WIS.CalculateMan").
			equals("0")) {
			changed = true;
		}
	}
	*/

	// Only need to check one...
	if (__divCodingYesJRadioButton.isSelected()) {
		if (!__dmi.getPreferenceValue("WIS.EnableDiversionCoding").
			equals("1")){ 
			changed = true;
		}
	}
	else {	
		if (!__dmi.getPreferenceValue("WIS.EnableDiversionCoding").
			equals("0")){ 
			changed = true;
		}	
	}

	if (changed) {
		__okJButton.setEnabled(true);
		__applyJButton.setEnabled(true);
	}
	else {
		__okJButton.setEnabled(false);
		__applyJButton.setEnabled(false);
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
Clean up for garbage collection.
*/
protected void finalize()
throws Throwable {
	__dmi = null;
	__applyJButton = null;
	__cancelJButton = null;
	__changeJButton = null;
	__okJButton = null;
	__setIntervalJButton = null;
	__wdJButton = null;
	__wisJButton = null;
	__confirmPWJPasswordField = null;
	__newPWJPasswordField = null;
	__oldPWJPasswordField = null;
	__pastHoursJRadioButton = null;
	__timeIntervalJRadioButton = null;
	__bypassOffJRadioButton = null;
	__bypassOnJRadioButton = null;
	__callListUsingWISJRadioButton = null;
	__callListAnyStructJRadioButton = null;
	__divCodingNoJRadioButton = null;
	__divCodingYesJRadioButton = null;
	__defaultDistrictJTextField = null;
	__defaultGVPJTextField = null;
	__endTimeJTextField = null;
	__pastHoursJTextField = null;
	__startTimeJTextField = null;
	__statusJTextField = null;
	__divisionJList = null;
	__districtJList = null;
	__dateProps = null;
	__defaultDistrictJButton = null;
	__priorityJComboBox = null;
	__userLevelJComboBox = null;
	__wisJComboBox = null;
	super.finalize();
}

/**
Adds the water districts to the __districtJList object depending on the 
selected division in the __divisionJList object.
*/
private void generateDistricts() {
	__generatingDistricts = true;
	String routine = "HydroBase_GUI_Options.generateDistricts";
        // Clear all the water districts after determining which ones
	// are currently selected.
	Object districts[] = __districtJList.getSelectedValues();
        __districtJList.removeAll();
                
        // get the selected rows in the division list. The division number is 
        // 1 greater than the selected row. 
        int divisions[] = __divisionJList.getSelectedIndices();
                                
        // loop over all the selected rows in the division list
	int size = divisions.length;
	
	HydroBase_WaterDistrict data;
	Vector v;
	int vsize;
	int wd;	
	String star = "";
	try {
        for (int count = 0; count < size; count++) {
		v = __dmi.lookupWaterDistrictsForDivision((divisions[count]+1));
		vsize = v.size();
		for (int i = 0; i < vsize; i++) {
			star = " ";
			data = (HydroBase_WaterDistrict)v.elementAt(i);
			wd = data.getWD();
			// Place an(*)before available districts found
			// in the database.
			if (__dmi.isWaterDistrictAvailable(wd)) {
				star = "*";
			}
			else {	
				star = " ";
			}
			__districtJList.add(star + " WD " + wd + " - " 
				+ data.getWd_name() + " (Div "
				+ (divisions[count] + 1)+ ")");			
               	}
        }        

	// The list was built with autoUpdate = false, so an update must be
	// forced after it changes (more efficient)
	__districtJList.update();

	// re-select previously selected water districts
	size = districts.length;
	int index;
	for (int count = 0; count < size; count++) {
		index = __districtJList.indexOf(districts[count]);
		if (index > -1) {
			__districtJList.select(index);
		}
	}

	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error in looking up " 
			+ "water districts");
		Message.printWarning(1, routine, e);
	}
	__generatingDistricts = false;
}

/**
Retrieves user specified date.
@param pref preference keyword
@return returns the user date as Y2K format.
*/
private String getDate(String pref) {
	String value = __dmi.getPreferenceValue(pref);
	return HydroBase_GUI_Util.getUserDate(value);
}

/**
Selects the Divisions according to user preferences.  The district list is NOT
de-selected prior to selecting the user-preferred district(s).  A conflict could
arise if a new user logs in who has different district(s) selected.  Presently,
if this occurs, the instance of this class is destroyed and a new onw is 
instantiated for the new user.  This circumvents districts for 2 users being
displayed.
*/
public void getDistricts() {
	// initialize variables
        String dis = __dmi.getPreferenceValue("WD." 
		+ HydroBase_GUI_Util.getActiveWaterDivision()
		+ ".DistrictSelect");
	Vector v = StringUtil.breakStringList(dis.trim(), ",", 0);
        String curItem = "";             
	int numRows = __districtJList.getItemCount();
	int size = v.size();
        // loop over all the tokens
        for (int i = 0; i < size; i++) {
        	String curToken = (String)v.elementAt(i);
		// loop over all rows in the __districtJList object
		for (int curRow = 0; curRow < numRows; curRow++) {
			curItem = (String)__districtJList.getItem(curRow);

	                // select the district if the curToken equals the
        	        // curRow in the __districtJList. 
			// The substring begins at the index = 5 and ends at
			// index 7, which are the bounds wd number as a string.
	                if (curToken.equals(curItem.substring(5,7).trim())) {
        	        	__districtJList.select(curRow);
                	}
        	}        
        }
}

/**
Selects the divisions according to user preferences.  The division list is NOT
de-selected prior to selecting the user-preferred division(s).  A conflict could
arise if a new user logs in who has different division(s) selected.  Presently,
if this occurs, the instance of this class is destroyed and a new one is 
instantiated for the new user.  This circumvents divisions for 2 users being
displayed.
*/
public void getDivisions() {
        String div = __dmi.getPreferenceValue("WD." 
		+ HydroBase_GUI_Util.getActiveWaterDivision()
		+ ".DivisionSelect");
	if (div == null) {
		// Old style...
        	div = __dmi.getPreferenceValue("WD.DivisionSelect");
	}

	int[] selected = null;

	if (div.equalsIgnoreCase("NONE")) {
		selected = new int[0];
	}
	else {
		Vector v = StringUtil.breakStringList(div.trim(), ",", 0);
	
		int size = v.size();
		selected = new int[size];
	        for (int i = 0; i < size; i++) {
			String s = (String)v.elementAt(i);
			if (s != null && s != "" && !s.equals(__NONE)) {
				int n = (Integer.parseInt(s) - 1);
	        		selected[i] = n;
			}
	        }
	}

	__divisionJList.setSelectedIndices(selected);
	
	// generate a list of water districts based 
	// upon the selected divisions
	generateDistricts();
}

/**
Performs a query on the sheet_name table and displays sheets according to 
selected water districts.
@throws Exception if an error occurs
*/
private void generateWIS() 
throws Exception {
        // initialize variables
        Vector whereClause = new Vector(10, 5);
	Vector orderBy = new Vector(10, 5);
       
        // process query
	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Please Wait...Retrieving WIS list..."); 
	__wisJComboBox.removeAll();
                              
        // results Vector contains the query results
	String wd = getWD();
	if (wd != null) {
		whereClause.addElement(wd);
	}
	orderBy.addElement("sheet_name");
        Vector results = __dmi.readWISSheetNameDistinctList(-1);

	__wisJComboBox.add(__NONE);
        if (results.size() > 0 && results != null) {
      	        int size = results.size(); 
               	for (int i = 0; i < size; i++) {
                       	HydroBase_WISSheetName data = 
				(HydroBase_WISSheetName)results.elementAt(i);
                        __wisJComboBox.add(data.getSheet_name());
       	        }
        }

	JGUIUtil.setWaitCursor(this, false);
        __statusJTextField.setText("Finished retrieving WIS list.");
}

/**
Returns the list of selected water districts in the water districts filter.
@return the list of selected water districts in the water districts filter.
*/
public String [] getSelectedDistricts() {
	Vector v = __districtJList.getSelectedItems();
	int size = v.size();
	String arr[] = new String[size];
	for (int i = 0; i < size; i++) {
		arr[i] = (String)v.elementAt(i);
	}
	return arr;
}

/**
Returns the wd syntax for where clauses according to Districts selected in the
user preferences.  This must return wd(s) as selected in the __districtJList
as the preferences are not update until OK is selected.
@Return the wd syntax for where clauses according to Districts selected in the
user preferences.
*/
private String getWD() {
	// initialize variables
	String wdWhere = null;
	Vector select = __districtJList.getSelectedItems(); 
                               
	//concatenate 'wd = ' for each element in the Vector
	if (select != null) {
		int size = select.size();
		Vector orClause = new Vector(size, 5);
		for (int i = 0; i < size; i++) {
        		orClause.addElement("wd = " 
				+ ((String)select.elementAt(i)).
				   substring(5,7).trim());
		}
		wdWhere = DMIUtil.getOrClause(orClause);
	}

	return wdWhere;
}

/**
Retrieves user preferences and displays the values within the appropriate 
component.
*/
public void getUserPreferences() {
	String prefValue = null;
	__ignoreChanges = true;
	//---------------------------------------------------------------------
        // Time Interval Tab
        //---------------------------------------------------------------------
        __pastHoursJTextField.setText(
		__dmi.getPreferenceValue("Time.PastValue"));
        __startTimeJTextField.setText(getDate("Time.IntervalStart"));
        __endTimeJTextField.setText(getDate("Time.IntervalEnd"));

        if (__dmi.getPreferenceValue("Time.PastFlag").equals("1")) {
		__pastHoursJRadioButton.setSelected(true);
        }
        else {	
		__pastHoursJRadioButton.setSelected(false);
        }
        if (__dmi.getPreferenceValue("Time.IntervalFlag").equals("1")) {
		__timeIntervalJRadioButton.setSelected(true);
        }
        else {	
		__timeIntervalJRadioButton.setSelected(false);
        }

        //---------------------------------------------------------------------
        // Map Tab
        //---------------------------------------------------------------------
	prefValue = __dmi.getPreferenceValue("Map." +
		HydroBase_GUI_Util.getActiveWaterDivision()+ ".GeoViewProject");
	if (prefValue != null) {
		__defaultGVPJTextField.setText(prefValue);
	}

        //---------------------------------------------------------------------
        // Water District Filter Tab
        //---------------------------------------------------------------------

	getDivisions();
	getDistricts();

        //---------------------------------------------------------------------
        // General Tab
        //---------------------------------------------------------------------
	__priorityJComboBox.select(
		__dmi.getPreferenceValue("General.CallingRight"));
	if (__dmi.getPreferenceValue("General.BypassCall").equals("1")) {
		__bypassOnJRadioButton.setSelected(true);	
	}
	else {
		__bypassOffJRadioButton.setSelected(true);	
	}

	String callStruct = __dmi.getPreferenceValue("General.CallStruct");
	if (callStruct.equals("FromWIS")) {
		__callListUsingWISJRadioButton.setSelected(true);
	}
	else {
		__callListAnyStructJRadioButton.setSelected(true);
	}

	String userLevel = __dmi.getPreferenceValue("General.UserLevel");
	if (userLevel.equals("1")) {
		__userLevelJComboBox.select(VIEW_ONLY);	
	}
	else if (userLevel.equals("2")) {
		__userLevelJComboBox.select(POWER_USER);	
	}
	else if (userLevel.equals("3")) {
		__userLevelJComboBox.select(ADMINISTRATOR);	
	}
	else {
		__userLevelJComboBox.select(OTHER_USER);	
	}

        //---------------------------------------------------------------------
        // WIS Tab
        //---------------------------------------------------------------------
	__wisJComboBox.removeAll();
	__wisJComboBox.add(__dmi.getPreferenceValue("WIS.WISdefault"));
	// This should really be one preference!!!
/*	
        if (__dmi.getPreferenceValue("WIS.CalculateAuto").equals("1")) {        
		__autoJRadioButton.setSelected(true);
		__manualJRadioButton.setSelected(false);
        }
        else {	
		__autoJRadioButton.setSelected(false);
		__manualJRadioButton.setSelected(true);
        }
*/	
/*
        if (__dmi.getPreferenceValue("WIS.CalculateMan").equals("1")) {
		__manualJRadioButton.setSelected(true);
        }
        else {	
		__manualJRadioButton.setSelected(false);
        }
*/
	prefValue = __dmi.getPreferenceValue("WIS.EnableDiversionCoding");
	if ((prefValue != null) && prefValue.equals("1")) {
		__divCodingYesJRadioButton.setSelected(true);
		__divCodingNoJRadioButton.setSelected(false);
	}
	else {	
		__divCodingYesJRadioButton.setSelected(false);
		__divCodingNoJRadioButton.setSelected(true);
	}
	__ignoreChanges = false;

	__okJButton.setEnabled(false);
	__applyJButton.setEnabled(false);
}

/**
Initializes data members.
*/
private void initialize() {
	__dateProps = new PropList("Calls DateTimeBuilderJDialog properties");
	__dateProps.set("DatePrecision", "Minute" );
	__dateProps.set("DateFormat", "Y2K");
}

/**
Determines if a String is a valid password.  The criteria that the potential
password must meet are:
<ol>
<li>Not null</li>
<li>Not empty (i.e., "")</li>
</ol>
@param pw the password to check
@param flag the type of password -- either __OLD_PW, __NEW_PW or __CONFIRM_PW
@return true if the password is valid, otherwise false.
*/
private boolean isValidPassword(String pw, String flag) {
	if (pw == null) {
		new ResponseJDialog(this, "Invalid Password",
			flag + " cannot be null.",
			ResponseJDialog.OK);
		return false;
	}
	else if (pw.trim().equals("")) {
		new ResponseJDialog(this, "Invalid Password", 
			flag + " cannot be"
			+ " an empty String.", ResponseJDialog.OK);
		return false;
	}
	return true;
}

/**
Respond to ItemEvents; does nothing.
@param evt ItemEvent object.
*/
//public void itemStateChanged(ItemEvent evt) {}

/**
Responds to key pressed events.
@param evt the KeyEvent that happened.
*/
public void keyPressed(KeyEvent evt) {
	String routine = "HydroBase_GUI_Options.keyPressed";
	int code = evt.getKeyCode();
	// enter key changes password.
	if (code == KeyEvent.VK_ENTER) {
		Component c = evt.getComponent();
		if (	c.equals(__oldPWJPasswordField)||
			c.equals(__newPWJPasswordField)||
			c.equals(__confirmPWJPasswordField)) {
			try {
				changeClicked();
			}
			catch (Exception e) {
				Message.printWarning(1, routine, e);
			}
		}
		else if (c == __defaultGVPJTextField) {
			okClicked();
		}
	}
	else if (code == KeyEvent.VK_SHIFT) {
		JGUIUtil.setShiftDown(true);
	}
	else if (code == KeyEvent.VK_CONTROL) {
		JGUIUtil.setControlDown(true);
	}
}

/**
Responds to key released events.
@param event the KeyEvent that happened.
*/
public void keyReleased(KeyEvent event) {
	int code = event.getKeyCode();
	if (code == KeyEvent.VK_SHIFT) {
		JGUIUtil.setShiftDown(false);
	}
	else if (code == KeyEvent.VK_CONTROL) {
		JGUIUtil.setControlDown(false);
	}
	checkChanged();
}

/**
Responds to key typed events; does nothing.
@param event the KeyEvent that happened.
*/
public void keyTyped(KeyEvent event) {}

/**
Turns the division or district jlist into a string suitable for use in 
comparing to or setting as a user preference.
@param list the list object from which to read objects and turn into a 
user preference String.
@return a String that is in the format usable for comparing to or using as
a user preference.
*/
private String listToString(SimpleJList list) {
	String routine = "HydroBase_GUI_Options.listToString";
	// initialize variables
	String selected = "";
	String curItem = "";

        // loop over all the list items
	int size = list.getItemCount();
        for (int curRow = 0; curRow < size; curRow++) {
            	// add to the selectedListItem String if the current row is 
		// selected while separating each item with a "," character.
            	if (list.isSelectedIndex(curRow)) {
			if (list == __divisionJList) {
				try {
				curItem = (String)list.getItem(curRow);
				int num = 
				      HydroBase_WaterDivision.getDivisionNumber(
					curItem);
				if (num != DMIUtil.MISSING_INT) {
					selected += num + ",";
				}
				}
				catch (Exception e) {
					Message.printWarning(1, routine, 
						"Error getting division "
						+ "number");
					Message.printWarning(1, routine, e);
				}
			}
			else if (list == __districtJList) {
                		curItem =
				((String)list.getItem(curRow))
					.substring(5,7).trim()+",";
                		selected += curItem;
			}
				           
        	}
        }

	return selected;
}

/**
This function is called when the ok JButton is pressed.  It applies the
property changes and then closes the window.
*/ 
public void okClicked() {
	JGUIUtil.setWaitCursor(this, true);
	applyClicked();
	closeClicked();
	JGUIUtil.setWaitCursor(this, false);
}

/**
Readies the GUI for user interaction.
*/
public void ready() {
	JGUIUtil.setWaitCursor(this, false);
	__statusJTextField.setText("Ready");
}

/**
This function sets the selected list item user preferences changes made 
in either the __divisionJList or __districtJList objects.
*/
private void saveListSelections(SimpleJList list, String keyword) {
        __dmi.setPreferenceValue(keyword, listToString(list));
}

/**
Responds to the select/deselect event.
*/
public void selectClicked() {
	// initialize variables
	boolean allSelected = true;
	int numRows = __districtJList.getItemCount();
		    
	JGUIUtil.setWaitCursor(this, true);
	
        // check to see if all rows are selected. If not,
        // then set allSelected flag to false.
        for (int curRow = 0; curRow < numRows; curRow++) {

		// if the current row is not selected then
                // the test fails. no point in checking the 
                // remaining rows.
 		if (!(__districtJList.isSelectedIndex(curRow))) {
			allSelected = false;
			break;
                }                     
	}
     
     	if (allSelected) {
		__districtJList.clearSelection();
	}
	else {
		__districtJList.selectAll();
	}
	JGUIUtil.setWaitCursor(this, false);
}

/**
Instantiates an instance of DateTimeBuilderJDialog for user
to specify start and end dates.
*/
private void setPeriodClicked() {
	String 	routine = "HydroBase_GUI_Options.setPeriodClicked()";

	DateTime from = null;
	DateTime to = null;

	try {	
		from = DateTime.parse(__startTimeJTextField.getText(),
                            DateTime.FORMAT_YYYY_MM_DD_HH_mm );

		to = DateTime.parse(__endTimeJTextField.getText(),
                            DateTime.FORMAT_YYYY_MM_DD_HH_mm);
	}
	catch (Exception e) {
		Message.printWarning(1, routine,
			"Exception parsing dates.");
		from = new DateTime(DateTime.PRECISION_MINUTE |
			DateTime.DATE_CURRENT);
		to = new DateTime(DateTime.PRECISION_MINUTE |
			DateTime.DATE_CURRENT);
	}
	new DateTimeBuilderJDialog(this, __startTimeJTextField, 
			__endTimeJTextField, from, to, __dateProps);
	checkChanged();
}

/**
Sets up the GUI objects.
@param visible whether the gui should be visible at creation time
*/
private void setupGUI(boolean visible) {
	Message.setTopLevel(this);
	addWindowListener(this);

        // objects to be used in the GUI layout
	Insets TNNN = new Insets(7,0,0,0);
        Insets NLNR = new Insets(0,7,0,7);
        Insets NLBR = new Insets(0,7,7,7);
	Insets NLBN = new Insets(0,7,7,0);
	Insets NLNN = new Insets(0,7,0,0);
        Insets TLBN = new Insets(7,7,7,0);
        Insets TLNN = new Insets(7,7,0,0);
	Insets TLNR = new Insets(7,7,0,7);
	Insets TNNR = new Insets(7,0,0,7);
        GridBagLayout gbl = new GridBagLayout();

        //---------------------------------------------------------------------
        // timeJPanel
        //---------------------------------------------------------------------
	JPanel timeJPanel = new JPanel();
        timeJPanel.setLayout(new BorderLayout());
        getContentPane().add(timeJPanel);
	ButtonGroup timeGroup = new ButtonGroup();

        JPanel timeNJPanel = new JPanel();
        timeNJPanel.setLayout(gbl);
        timeJPanel.add("North", timeNJPanel);

        JGUIUtil.addComponent(timeNJPanel, new JLabel(
		"The time period indicated below is used as a default for "
		+ "display features (e.g., real-time data graphs)."),
		0, 0, 4, 1, 0, 0, TNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
	
	JGUIUtil.addComponent(timeNJPanel, new JLabel(
		"Default Time Period:"),
		0, 1, 1, 1, 0, 0, TLBN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __pastHoursJRadioButton = new JRadioButton(__BUTTON_PAST, true);
	__pastHoursJRadioButton.addActionListener(this);
        JGUIUtil.addComponent(timeNJPanel, __pastHoursJRadioButton, 
		0, 2, 1, 1, 0, 0, TNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __pastHoursJTextField = new JTextField();	
	__pastHoursJTextField.addKeyListener(this);
        JGUIUtil.addComponent(timeNJPanel, __pastHoursJTextField, 
		1, 2, 1, 1, 0, 0, TLNR, GridBagConstraints.EAST, GridBagConstraints.WEST);

	JGUIUtil.addComponent(timeNJPanel, new JLabel("hours"), 
		2, 2, 1, 1, 0, 0, TNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __timeIntervalJRadioButton = new JRadioButton(__BUTTON_PERIOD, false);
	__timeIntervalJRadioButton.addActionListener(this);
        JGUIUtil.addComponent(timeNJPanel, __timeIntervalJRadioButton, 
		0, 3, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);

	timeGroup.add(__pastHoursJRadioButton);
	timeGroup.add(__timeIntervalJRadioButton);

        JGUIUtil.addComponent(timeNJPanel, new JLabel("Start Time:"), 
		1, 4, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __startTimeJTextField = new JTextField(25);
	__startTimeJTextField.setEditable(false);	
        JGUIUtil.addComponent(timeNJPanel, __startTimeJTextField, 
		2, 4, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(timeNJPanel, new JLabel("End Time:"), 
		1, 5, 1, 1, 0, 0, TNNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __endTimeJTextField = new JTextField(25);
	__endTimeJTextField.setEditable(false);
        JGUIUtil.addComponent(timeNJPanel, __endTimeJTextField, 
		2, 5, 1, 1, 0, 0, TNNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__setIntervalJButton = new JButton(__BUTTON_SET_PERIOD);
	__setIntervalJButton.setEnabled(false);
	__setIntervalJButton.addActionListener(this);
        JGUIUtil.addComponent(timeNJPanel, __setIntervalJButton, 
		3, 5, 1, 1, 0, 0, TNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        //---------------------------------------------------------------------
        // mapJPanel
        //---------------------------------------------------------------------
	JPanel mapJPanel = new JPanel();
        mapJPanel.setLayout(new BorderLayout());
        getContentPane().add(mapJPanel);

        JPanel mapNJPanel = new JPanel();
        mapNJPanel.setLayout(gbl);
        mapJPanel.add("North", mapNJPanel);

        JGUIUtil.addComponent(mapNJPanel, 
		new JLabel("Default Map (GeoView) Project File (enter "
		+ "NONE to disable map):"), 
		0, 0, 1, 1, 0, 0, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__defaultGVPJTextField = new JTextField(50);
	__defaultGVPJTextField.setEditable(true);	// To null out.
	__defaultGVPJTextField.addKeyListener(this);
        JGUIUtil.addComponent(mapNJPanel, __defaultGVPJTextField, 
		0, 1, 1, 1, 0, 0, TNNN, GridBagConstraints.EAST, GridBagConstraints.WEST);

        JGUIUtil.addComponent(mapNJPanel, new SimpleJButton("Browse...",
		__BUTTON_BROWSE_FOR_DEFAULT_GVP,this),
		1, 1, 1, 1, 0, 0, TNNN, GridBagConstraints.EAST, GridBagConstraints.WEST);
	JGUIUtil.addComponent(mapNJPanel,
		new JLabel("This property is saved for the division that was "
		+ "selected during login."),
		0, 2, 1, 1, 0, 0, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        //---------------------------------------------------------------------
        // wdJPanel
        //---------------------------------------------------------------------
	JPanel wdJPanel = new JPanel();
        wdJPanel.setLayout(new BorderLayout());
        getContentPane().add(wdJPanel);

	JPanel wdNJPanel = new JPanel();
        wdNJPanel.setLayout(new BorderLayout());
        wdJPanel.add("North", wdNJPanel);

	JPanel wdWJPanel = new JPanel();
        wdWJPanel.setLayout(gbl);
        wdNJPanel.add("West", wdWJPanel);

        JGUIUtil.addComponent(wdWJPanel, 
		new JLabel("Select water divisions/districts to configure "
		+ "database query defaults."),
		0, 0, 3, 1, 0, 0, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(wdWJPanel, 
		new JLabel("Districts for a division are not "
		+ "automatically selected when a division is selected."), 
		0, 1, 3, 1, 0, 0, NLBN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JPanel wdCJPanel = new JPanel();
        wdCJPanel.setLayout(gbl);
        wdJPanel.add("Center", wdCJPanel);

        JGUIUtil.addComponent(wdCJPanel, new JLabel("Division"), 
		0, 0, 1, 1, 1, 0, TLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(wdCJPanel, new JLabel(
		"District (* indicates available in current database)"), 
		1, 0, 1, 1, 1, 0, TLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	String[] names = HydroBase_WaterDivision.getDivisionNames();
        __divisionJList = new SimpleJList(names);
	__divisionJList.setInverseListSelection(true);
	__divisionJList.setPrototypeCellValue(
		"Division 7: San Juan/Dolores River   ");
	__divisionJList.setAutoUpdate(false);
        __divisionJList.setSelectionMode(
		ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	__divisionJList.addListSelectionListener(this);

        JGUIUtil.addComponent(wdCJPanel, new JScrollPane(__divisionJList), 
		0, 1, 1, 1, 1, 1, NLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

        __districtJList = new SimpleJList();
	__districtJList.setInverseListSelection(true);
	__districtJList.clearSelection();
	__districtJList.setPrototypeCellValue(
		"* WD 8 - South Platter Cheesman to Denver Gage (Div 2)    ");
	__districtJList.setAutoUpdate(false);
	__districtJList.setSelectionMode(
		ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	__districtJList.addListSelectionListener(this);
	__districtJList.addKeyListener(this);
        JGUIUtil.addComponent(wdCJPanel, new JScrollPane(__districtJList), 
		1, 1, 1, 1, 1, 1, NLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

        __wdJButton = new JButton(__BUTTON_SELECT_DESELECT_ALL);
	__wdJButton.addActionListener(this);
        JGUIUtil.addComponent(wdCJPanel, __wdJButton, 
		1, 2, 0, 0, 0, 0, NLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JPanel lowerWDJPanel = new JPanel();
	lowerWDJPanel.setLayout(gbl);

        JGUIUtil.addComponent(lowerWDJPanel, 
		new JLabel("Default District:"), 
		0, 0, 1, 1, 0, 0, TLBN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__defaultDistrictJTextField = new JTextField(30);
	String pref_district = __dmi.getPreferenceValue("WD.DistrictDefault");
	// add a( WD)as a preface to the default district choice
	if (pref_district.equals(HydroBase_GUI_Util._ALL_DIVISIONS)) {
		// keep as is
	}
	else if (!pref_district.equals(__NONE)) {
		pref_district = "  WD " + pref_district;
	}
	__defaultDistrictJTextField.setText(pref_district);
	__defaultDistrictJTextField.setEnabled(false);
        JGUIUtil.addComponent(lowerWDJPanel, __defaultDistrictJTextField,
		1, 0, 2, 1, 1, 0, TLBN, GridBagConstraints.EAST, GridBagConstraints.WEST);

	__defaultDistrictJButton = new SimpleJButton("Set...",
		__BUTTON_SET_DEFAULT_DISTRICT, this);
        JGUIUtil.addComponent(lowerWDJPanel, __defaultDistrictJButton,
		3, 0, 1, 1, 0, 0, TLBN, GridBagConstraints.EAST, GridBagConstraints.WEST);

	wdJPanel.add("South", lowerWDJPanel);

        //---------------------------------------------------------------------
        // generalJPanel
        //---------------------------------------------------------------------
	JPanel generalJPanel = new JPanel();
	generalJPanel.setLayout(new BorderLayout());
	getContentPane().add(generalJPanel);

	JPanel generalNJPanel = new JPanel();
	generalNJPanel.setLayout(new BorderLayout());
	generalJPanel.add("North", generalNJPanel);

	JPanel generalWJPanel = new JPanel();
	generalWJPanel.setLayout(gbl);
	generalNJPanel.add("West", generalWJPanel);

	String application = "CWRAT";
	if (__options_ui.getViewOnly()) {
		application = "StateView";
	}

	TitledBorder generalTitle = BorderFactory.createTitledBorder(
		"Change Password for StateView/CWRAT");
	generalWJPanel.setBorder(generalTitle);

        JGUIUtil.addComponent(generalWJPanel, new JLabel("User Level:"), 
		0, 0, 1, 1, 1, 1, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__userLevelJComboBox = new SimpleJComboBox();
	__userLevelJComboBox.add(VIEW_ONLY);
	__userLevelJComboBox.add(POWER_USER);	
	__userLevelJComboBox.add(ADMINISTRATOR);
	__userLevelJComboBox.add(OTHER_USER);	
	__userLevelJComboBox.addActionListener(this);
	__userLevelJComboBox.setEnabled(true);	
        JGUIUtil.addComponent(generalWJPanel, __userLevelJComboBox, 
		1, 0, 1, 1, 1, 1, TNNN, GridBagConstraints.EAST, GridBagConstraints.WEST);

        JGUIUtil.addComponent(generalWJPanel, 
		new JLabel("Change password for " + application + " login"), 
		0, 1, 1, 1, 0, 0, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(generalWJPanel, 
		new JLabel("Old Password:"), 
		0, 2, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__oldPWJPasswordField = new JPasswordField(20);
	__oldPWJPasswordField.setEchoChar('*');
	__oldPWJPasswordField.addKeyListener(this);
        JGUIUtil.addComponent(generalWJPanel, __oldPWJPasswordField, 
		1, 2, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.WEST);

        JGUIUtil.addComponent(generalWJPanel, 
		new JLabel("New Password:"), 
		0, 3, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__newPWJPasswordField = new JPasswordField(20);
	__newPWJPasswordField.setEchoChar('*');
	__newPWJPasswordField.addKeyListener(this);
        JGUIUtil.addComponent(generalWJPanel, __newPWJPasswordField, 
		1, 3, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.WEST);

        JGUIUtil.addComponent(generalWJPanel, 
		new JLabel("Confirm new password:"), 
		0, 4, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__confirmPWJPasswordField = new JPasswordField(20);
	__confirmPWJPasswordField.setEchoChar('*');
	__confirmPWJPasswordField.addKeyListener(this);
        JGUIUtil.addComponent(generalWJPanel, __confirmPWJPasswordField, 
		1, 4, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.WEST);

	__changeJButton = new JButton(__BUTTON_CHANGE);
	__changeJButton.addActionListener(this);
        JGUIUtil.addComponent(generalWJPanel, __changeJButton,
		1, 5, 1, 1, 0, 0, TNNN, GridBagConstraints.EAST, GridBagConstraints.WEST);

        //---------------------------------------------------------------------
        // administrationJPanel
        //---------------------------------------------------------------------
	JPanel administrationJPanel = new JPanel();
	administrationJPanel.setLayout(new BorderLayout());
	getContentPane().add(administrationJPanel);

	JPanel wisNJPanel = new JPanel();
	wisNJPanel.setLayout(new BorderLayout());
	administrationJPanel.add("North", wisNJPanel);

	JPanel wisWJPanel = new JPanel();
	wisWJPanel.setLayout(gbl);
	wisNJPanel.add("Center", wisWJPanel);
	TitledBorder wisTitle = BorderFactory.createTitledBorder(
		"Water Information Sheet");	
	wisWJPanel.setBorder(wisTitle);

        JGUIUtil.addComponent(wisWJPanel, 
		new JLabel("Default WIS:"), 
		0, 0, 1, 1, 0, 0, TLBN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__wisJComboBox = new SimpleJComboBox();
	__wisJComboBox.addActionListener(this);
        JGUIUtil.addComponent(wisWJPanel, __wisJComboBox,
		1, 0, 2, 1, 1, 0, TLBN, GridBagConstraints.EAST, GridBagConstraints.WEST);

	__wisJButton = new JButton(__BUTTON_GENERATE_LIST);
	__wisJButton.addActionListener(this);
        JGUIUtil.addComponent(wisWJPanel, __wisJButton,
		3, 0, 1, 1, 0, 0, TLBN, GridBagConstraints.NONE, GridBagConstraints.WEST);

/*
        JGUIUtil.addComponent(wisWJPanel, 
		new JLabel("Perform WIS Calculations:"), 
		0, 1, 1, 1, 0, 0, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	ButtonGroup calcGroup = new ButtonGroup();

	__autoJRadioButton = new JRadioButton("Automatically", false);
	__autoJRadioButton.addActionListener(this);
        JGUIUtil.addComponent(wisWJPanel, __autoJRadioButton, 
		1, 2, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__manualJRadioButton = new JRadioButton("Manually", true);
	__manualJRadioButton.addActionListener(this);
        JGUIUtil.addComponent(wisWJPanel, __manualJRadioButton,
		2, 2, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	calcGroup.add(__autoJRadioButton);
	calcGroup.add(__manualJRadioButton);
*/

        JGUIUtil.addComponent(wisWJPanel,
		new JLabel("Enable Diversion Coding:"), 
		0, 3, 1, 1, 0, 0, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	ButtonGroup codingGroup = new ButtonGroup();

	__divCodingNoJRadioButton = new JRadioButton("No", true);
	__divCodingNoJRadioButton.addActionListener(this);
        JGUIUtil.addComponent(wisWJPanel, __divCodingNoJRadioButton, 
		1, 4, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__divCodingYesJRadioButton = new JRadioButton("Yes", false);
	__divCodingYesJRadioButton.addActionListener(this);
        JGUIUtil.addComponent(wisWJPanel, __divCodingYesJRadioButton,
		2, 4, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	codingGroup.add(__divCodingNoJRadioButton);
	codingGroup.add(__divCodingYesJRadioButton);

	JPanel lowerJPanel = new JPanel();
	lowerJPanel.setLayout(gbl);
	
	TitledBorder wratTitle = BorderFactory.createTitledBorder(
		"Water Rights Administration Settings");
	lowerJPanel.setBorder(wratTitle);

        JGUIUtil.addComponent(lowerJPanel, 
		new JLabel("Display Calling Right as:"), 
		0, 0, 1, 1, 1, 1, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__priorityJComboBox = new SimpleJComboBox();
	__priorityJComboBox.add(ADJ_DATE);
	__priorityJComboBox.add(APPRO_DATE);	
	__priorityJComboBox.add(ADMIN_NO);
	__priorityJComboBox.add(DECREED_AMT);	
	__priorityJComboBox.add(PRIORITY_NUMBER);
	__priorityJComboBox.addActionListener(this);
        JGUIUtil.addComponent(lowerJPanel, __priorityJComboBox, 
		1, 0, 1, 1, 0, 0, TNNN, GridBagConstraints.EAST, GridBagConstraints.WEST);
	// list structures for calls
	JGUIUtil.addComponent(lowerJPanel, 
		new JLabel("Initial Structure List for Calls:"),
		0, 1, 1, 1, 1, 1, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	ButtonGroup callStructGroup = new ButtonGroup();

	__callListUsingWISJRadioButton = new JRadioButton(
		"Using WIS Structures", false);
	__callListUsingWISJRadioButton.addActionListener(this);
	JGUIUtil.addComponent(lowerJPanel, __callListUsingWISJRadioButton, 
		1, 1, 1, 1, 1, 1, TNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__callListAnyStructJRadioButton = new JRadioButton(
		"Using Previous Calls", true);
	__callListAnyStructJRadioButton.addActionListener(this);
	JGUIUtil.addComponent(lowerJPanel, __callListAnyStructJRadioButton, 
		1, 2, 1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
	
	callStructGroup.add(__callListUsingWISJRadioButton);
	callStructGroup.add(__callListAnyStructJRadioButton);

	JGUIUtil.addComponent(lowerJPanel, 
		new JLabel("Bypass Call Functionality:"),
		0, 3, 1, 1, 1, 1, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	ButtonGroup bypassGroup = new ButtonGroup();

	__bypassOnJRadioButton = new JRadioButton("On", false);
	__bypassOnJRadioButton.addActionListener(this);
	JGUIUtil.addComponent(lowerJPanel, __bypassOnJRadioButton, 
		1, 3, 1, 1, 1, 1, TNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__bypassOffJRadioButton = new JRadioButton("Off", true);
	__bypassOffJRadioButton.addActionListener(this);
	JGUIUtil.addComponent(lowerJPanel, __bypassOffJRadioButton, 
		1, 4, 1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);

	bypassGroup.add(__bypassOnJRadioButton);
	bypassGroup.add(__bypassOffJRadioButton);

	JPanel wisSJPanel = new JPanel();
	wisSJPanel.setLayout(new BorderLayout());
	administrationJPanel.add("South", wisSJPanel);

	wisSJPanel.add("South", lowerJPanel);

        //---------------------------------------------------------------------
        // Add all JPanels to the Tab JPanel
        //---------------------------------------------------------------------
	JTabbedPane tab = new JTabbedPane();
	if (!__options_ui.getViewOnly()) {
        	tab.addTab(__ADMIN, administrationJPanel);
	}	
	tab.addTab(__GENERAL, generalJPanel);
	tab.addTab(__MAP, mapJPanel);
	tab.addTab(__TIME_INTERVAL , timeJPanel);
	tab.addTab(__WATER_DISTRICT, wdJPanel);
	tab.setSelectedIndex(0);
        getContentPane().add("Center", tab);
	
        //---------------------------------------------------------------------
        // JButton JPanel(shared between all Tabs)
        //---------------------------------------------------------------------
        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", buttonJPanel);

        JPanel buttonNJPanel = new JPanel();
        buttonNJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonJPanel.add("North", buttonNJPanel);

        __okJButton = new JButton(__BUTTON_OK);
	__okJButton.setToolTipText("Save changes to database and close form.");
	__okJButton.addActionListener(this);
        buttonNJPanel.add(__okJButton);

        __cancelJButton = new JButton(__BUTTON_CANCEL);
	__cancelJButton.addActionListener(this);
	__cancelJButton.setToolTipText("Discard changes and close form.");
        buttonNJPanel.add(__cancelJButton);

        __applyJButton = new JButton(__BUTTON_APPLY);
	__applyJButton.addActionListener(this);
	__applyJButton.setToolTipText("Save changes to database.");
        buttonNJPanel.add(__applyJButton);

        JPanel buttonSJPanel = new JPanel();
        buttonSJPanel.setLayout(gbl);
        buttonJPanel.add("South", buttonSJPanel);

	__statusJTextField = new JTextField();
        __statusJTextField.setBackground(Color.lightGray);
	__statusJTextField.setEditable(false);
	JGUIUtil.addComponent(buttonSJPanel, __statusJTextField,
			0, 0, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.WEST); 

        // frame settings

	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}
	
        setTitle(app + "Options");
	pack();
	__doneWithSetup = true;
	setSize(600, 450);
        setResizable(false);
	JGUIUtil.center(this);
        setVisible(visible);
	initialize();
}

/**
This function sets user preferences as specified within the components.
The preferences are only saved in memory.  When the application closes the
preferences will be saved if the user is allowed to do so.
*/
public void setUserPreferences() {
	//---------------------------------------------------------------------
        // Time Interval Tab
        //---------------------------------------------------------------------
        __dmi.setPreferenceValue("Time.PastValue", 
		__pastHoursJTextField.getText());
        __dmi.setPreferenceValue("Time.IntervalStart", 
		__startTimeJTextField.getText()); 
        __dmi.setPreferenceValue("Time.IntervalEnd", 
		__endTimeJTextField.getText());           
        if (__pastHoursJRadioButton.isSelected()) {
		__dmi.setPreferenceValue("Time.PastFlag","1");
		__dmi.setPreferenceValue("Time.IntervalFlag","0");
        }
        else {	
		__dmi.setPreferenceValue("Time.PastFlag","0");
		__dmi.setPreferenceValue("Time.IntervalFlag","1");
        }

        //---------------------------------------------------------------------
        // Map Tab
        //---------------------------------------------------------------------

	String mapPref = __dmi.getPreferenceValue("Map."
		+ HydroBase_GUI_Util.getActiveWaterDivision()
		+ ".GeoViewProject");
	String mapText = __defaultGVPJTextField.getText().trim();

	__dmi.setPreferenceValue("Map." 
		+ HydroBase_GUI_Util.getActiveWaterDivision()
		+ ".GeoViewProject", __defaultGVPJTextField.getText());
	// Open the GeoView Project file if the preference has changed...
	if (!mapPref.equalsIgnoreCase(mapText)) {
/*	
	REVISIT (JTS - 2005-11-18)
	this code was not working properly -- it was always triggering as 
	the preference being modified
	if (__dmi.preferenceIsModified("Map." 
		+ HydroBase_GUI_Util.getActiveWaterDivision() 
		+ ".GeoViewProject")) {
*/
		__geoview_ui.openGVP(__defaultGVPJTextField.getText());
	}

        //---------------------------------------------------------------------
        // Water District Filter Tab
        //---------------------------------------------------------------------
        saveListSelections(__divisionJList, "WD." +
		HydroBase_GUI_Util.getActiveWaterDivision()+ ".DivisionSelect");
        saveListSelections(__districtJList, "WD." +
		HydroBase_GUI_Util.getActiveWaterDivision()+ ".DistrictSelect");

	// Now get the default from the test field...
	//String pref_district = __districtJComboBox.getSelectedItem();
	// The following is the same code as in HBSelectDefaultDistrictGUI!!!
	String pref_district = __defaultDistrictJTextField.getText();

	// trim the WD preface from the selection ...
	if (pref_district.equals(HydroBase_GUI_Util._ALL_DIVISIONS)) {
		// keep as is
	}
	else if (!pref_district.equals(__NONE)) {
		pref_district = pref_district.substring(5).trim();
	}
	__dmi.setPreferenceValue("WD.DistrictDefault", pref_district);
        
        //---------------------------------------------------------------------
        // General Tab
        //---------------------------------------------------------------------
	__dmi.setPreferenceValue("General.CallingRight", 
		((String)__priorityJComboBox.getSelectedItem()).trim());
	String userLevel = 
		((String)__userLevelJComboBox.getSelectedItem()).trim();
	if (userLevel.equals(VIEW_ONLY)) {
		__dmi.setPreferenceValue("General.UserLevel", "1");
	}
	else if (userLevel.equals(POWER_USER)) {
		__dmi.setPreferenceValue("General.UserLevel", "2");
	}
	else if (userLevel.equals(ADMINISTRATOR)) {
		__dmi.setPreferenceValue("General.UserLevel", "3");
	}
	else {
		__dmi.setPreferenceValue("General.UserLevel", "4");
	}
	__options_ui.enableMenusBasedOnUserLevel(
		__dmi.getPreferenceValue("General.UserLevel"));
	if (__bypassOnJRadioButton.isSelected()) {
		__dmi.setPreferenceValue("General.BypassCall", "1");
	}
	else {	
		__dmi.setPreferenceValue("General.BypassCall", "0");
	}

	if (__callListUsingWISJRadioButton.isSelected()) {
		__dmi.setPreferenceValue("General.CallStruct", "FromWIS");
	}
	else {
		__dmi.setPreferenceValue("General.CallStruct", "FromQuery");
	} 
	    
        //---------------------------------------------------------------------
        // WIS Tab
        //---------------------------------------------------------------------
	__dmi.setPreferenceValue("WIS.WISdefault", 
		((String)__wisJComboBox.getSelectedItem()).trim());
/*		
	if (__autoJRadioButton.isSelected()) {
		__dmi.setPreferenceValue("WIS.CalculateAuto", "1");
	}
	else {	
		__dmi.setPreferenceValue("WIS.CalculateAuto", "0");
	}
*/
/*
	if (__manualJRadioButton.isSelected()) {
		__dmi.setPreferenceValue("WIS.CalculateMan", "1");
	}
	else {	
		__dmi.setPreferenceValue("WIS.CalculateMan", "0");
	}
*/


	// Only need to check one...
	if (__divCodingYesJRadioButton.isSelected()) {
		__dmi.setPreferenceValue("WIS.EnableDiversionCoding", "1");
	}
	else {	
		__dmi.setPreferenceValue("WIS.EnableDiversionCoding", "0");
	}
}

/**
Shows/hides the GUI.
@param state if true, the GUI is shown.  If false, it is hidden.
*/
public void setVisible(boolean state) {
	super.setVisible(state);
	if (state) {		
 		getUserPreferences();                 
	}	
}

/**
Responds to value changed events.
@param evt the ListSelectionEvent that happened.
*/
public void valueChanged(ListSelectionEvent evt) {
	Object o = evt.getSource();
	if (o.equals(__divisionJList)) {
		generateDistricts();		
	}
	if (!__generatingDistricts) {
		checkChanged();
	}
}

/**
Responds to window activated events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowActivated(WindowEvent evt) {}

/**
Responds to window closed events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowClosed(WindowEvent evt) {}

/**
Responds to window closing events.
@param evt the WindowEvent that happened.
*/
public void windowClosing(WindowEvent evt) {
	closeClicked();
}

/**
Responds to window deactivated events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowDeactivated(WindowEvent evt) {}

/**
Responds to window deiconified events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowDeiconified(WindowEvent evt) {}

/**
Responds to window iconified events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowIconified(WindowEvent evt) {}

/**
Responds to window opened events; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowOpened(WindowEvent evt) {}

}
