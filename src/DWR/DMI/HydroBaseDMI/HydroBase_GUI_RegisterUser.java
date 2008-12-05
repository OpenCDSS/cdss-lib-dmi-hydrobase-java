//-----------------------------------------------------------------------------
// HydroBase_GUI_RegisterUser - GUI to assist in user registration.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:  
//
// 29 Apr 1998	DLG, RTi		Created initial class description.
// 18 May 1998  DLG, RTi		Added registration functionality.
// 04 Apr 1999	Steven A. Malers, RTi	Added HBDMI to queries.
// 07 Apr 1999	SAM, RTi		Update for database changes.
// 07 Sep 1999	SAM, RTi		Update messages for new and
//					re-registering users.
// 2001-11-12	SAM, RTi		Change GUI to JGUIUtil.
//-----------------------------------------------------------------------------
// 2003-04-04	J. Thomas Sapienza, RTi	Initial Swing version.
// 2005-02-02	JTS, RTi		readUserPreferences...() method no
//					longer requires Application, because
//					the user_num in the table is always
//					unique.
// 2005-02-14	JTS, RTi		Checked all dmi calls to make sure they
//					use stored procedures.
// 2005-04-28	JTS, RTi		Added finalize().
// 2007-02-08	SAM, RTi		Remove dependence on CWRAT.
//					Pass JFrame to constructor.
//					Clean up code based on Elipse feedback.
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.util.List;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.Message.Message;

public class HydroBase_GUI_RegisterUser 
extends JFrame 
implements ActionListener, KeyListener, WindowListener {

/**
OptionsUI to handle HydroBase options.
*/
private OptionsUI __options_ui;

/**
Reference to a DMI.
*/
private HydroBaseDMI __dmi;

/**
Default permissions.
*/
private final int __permissions	= -999;

/**
First name.
*/
private JTextField __firstJTextField = null;
/**
Last name.
*/
private JTextField __lastJTextField = null;
/**
Login.
*/
private JTextField __loginJTextField = null;
/**
Middle initial.
*/
private JTextField __middleJTextField = null;
/**
Status bar.
*/
private JTextField __statusJTextField = null;

/**
Password confirmation.
*/
private JPasswordField __confirmJPasswordField = null;
/**
Password.
*/
private JPasswordField __passwordJPasswordField = null;

/**
Cancel button label.
*/
private final String __BUTTON_CANCEL = "Cancel";
/**
Register button label.
*/
private final String __BUTTON_REGISTER	= "Register";
/**
Status bar ready text.
*/
private final String __READY = "Ready";

/**
HydroBase_GUI_RegisterUser constructor
@param dmi HydroBaseDMI object
@param parent JFrame that instantiated this object.
@param options_ui OptionsUI to handle 
*/
public HydroBase_GUI_RegisterUser(HydroBaseDMI dmi, JFrame parent,
		OptionsUI options_ui ) {
	__dmi = dmi;
	__options_ui = options_ui;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();
}

/**
Responds to ActionEvents
@param evt ActionEvent object
*/
public void actionPerformed(ActionEvent evt) {
	String s = evt.getActionCommand();

	if (s.equals(__BUTTON_CANCEL)) {
		closeClicked();
	}
	else if (s.equals(__BUTTON_REGISTER)) {
		registerClicked();
	}
}

/**
Checks the user input fields for validity.
@return true if fields are ok, false otherwise
*/
private boolean checkFields() {
	String 	pw 	= null,
		confirm	= null;

	// ensure that all fields have been filled out
	if (isEmpty(__lastJTextField)) {
		new ResponseJDialog(this, "Please enter your last name.",
			ResponseJDialog.OK);
		return false;
	}
	else if (isEmpty(__firstJTextField)) {
		new ResponseJDialog(this, "Please enter your first name.",
			ResponseJDialog.OK);
		return false;
	}
	else if (isEmpty(__loginJTextField)) {
		new ResponseJDialog(this, "Please enter your login.",
			ResponseJDialog.OK);
		return false;
	}
	else if (isEmpty(__passwordJPasswordField)) {
		new ResponseJDialog(this, "Please enter a password.",
			ResponseJDialog.OK);
		return false;
	}
	else if (isEmpty(__confirmJPasswordField)) {
		new ResponseJDialog(this, "Please confirm your password.",
			ResponseJDialog.OK);
		return false;
	}

	// Ensure that password and confirmation are identical...
	pw = new String(__passwordJPasswordField.getPassword());
	confirm = new String(__confirmJPasswordField.getPassword());
	if (!pw.equals(confirm)) {
		new ResponseJDialog(this, "Password and confirmation"
			+ " do not match.", ResponseJDialog.OK);
		return false;
	}

	return true;
}
  
/**
Closes the GUI
*/
private void closeClicked() {
	setVisible(false); 
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__dmi = null;
	__firstJTextField = null;
	__lastJTextField = null;
	__loginJTextField = null;
	__middleJTextField = null;
	__statusJTextField = null;
	__confirmJPasswordField = null;
	__passwordJPasswordField = null;
	super.finalize();
}

/**
Determines whether a JPasswordField contains an empty string.
@return true if the JPasswordField contains an empty string.
*/
private boolean isEmpty(JPasswordField jp) {
	return isEmpty(new String(jp.getPassword()));
}

/**
Determines whether a JTextField contains an empty string.
@return true if the JTextField contains an empty string.
*/
private boolean isEmpty(JTextField jt) {
	return isEmpty(jt.getText());
}

/**
Determines whether a string is empty.
@return true if the string is empty, false otherwise.
*/
private boolean isEmpty(String s) {
	if (s == null) {
		return true;
	}
	
	String test = s.trim();

	if (DMIUtil.isMissing(test)) {
		return true;
	}

	return false;
}

/**
Responds to KeyReleased KeyEvents.
@param event KeyEvent object
*/
public void keyPressed(KeyEvent event) {
	int i = event.getKeyCode();

	if (i == KeyEvent.VK_ENTER) {
		registerClicked();
	}
}

/**
Responds to key released events; does nothing.
@param event the KeyEvent that happened.
*/
public void keyReleased(KeyEvent event) {}

/**
Responds to key released events; does nothing.
@param event the KeyEvent that happened.
*/
public void keyTyped(KeyEvent event) {}

/**
Registers the current user.
*/
private void registerClicked() {
	String routine = "HydroBase_GUI_RegisterUser.registerClicked";
//	HBUserPreference	prefData 	= null;

	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Saving user registration information...");

	// ensure that user fields have been completed.
	if (!checkFields()) {
		ready();
		return;
	}

	String userName = __lastJTextField.getText().trim()+ ", " 
			+ __firstJTextField.getText().trim()+ " "
			+ __middleJTextField.getText().trim();
	String login = __loginJTextField.getText().trim();
	String password = 
		(new String(__passwordJPasswordField.getPassword())).trim();

	// Perform query to determine if user has registered before...

	HydroBase_UserSecurity userSecurity = null;
	try {
	userSecurity =
		__dmi.readUserSecurityForLoginPasswordApplication(
			login, password, "CWRAT");
	}
	catch (Exception e) {
		Message.printWarning(2, routine, "Error reading user "
			+ "data from database; no registration done.");
		Message.printWarning(2, routine, e);
		ready();
		return;
	}

	// User is not registered.
	if (userSecurity == null) {
		// Now make sure that the login itself is not being used
		// by someone else...
		try {
		userSecurity = 
			__dmi.readUserSecurityForLoginPasswordApplication(
				login, null, "CWRAT");
		}
		catch (Exception e) {
			Message.printWarning(2, routine, "Error reading from "
				+ "database; no registration done.");
			Message.printWarning(2, routine, e);
			ready();
			return;
		}

		// Warn user that login is already selected and to
		// select another login.
		if (userSecurity != null) {
			new ResponseJDialog(this, "Login, " + login 
				+ ", is already being used."
				+ " Please select another login.",
				ResponseJDialog.OK);
			ready();
			__loginJTextField.setText("");
			ready();
			return;
		}
		else {	
			// Insert new user record...
			int userNum = DMIUtil.MISSING_INT;
			if (__dmi.isDatabaseVersionAtLeast(
				HydroBaseDMI.VERSION_19990305)) {
				// Set user num to negative so that we can later
				// identify that this user has not synchronized
				// with the central database.
				userNum = -999;
			}
			else {	
				// SAM(1999-09-07)- Not sure what the
				// following lines do but leave in for now...
				userNum = DMIUtil.getMinRecord(__dmi, 
					"user_security", 
					"user_security.userNum") - 1;

				// Set user num to negative so that we can later
				// identify that this user has not synchronized
				// with the central database.
				if (userNum == 0) {
					userNum = -999;
				}
			}

			String dmiString = "";
			if (__dmi.isDatabaseVersionAtLeast(
				HydroBaseDMI.VERSION_19990305)) {
				HydroBase_UserSecurity us = 
					new HydroBase_UserSecurity();
				us.setUser_num(userNum);
				us.setLogin(login);
				us.setPassword(password);
				us.setPermissions("" + __permissions);
				us.setUser_name(userName);
				us.setApplication("CWRAT");
				try {
					__dmi.writeUserSecurity(us);
				}
				catch (Exception e) {	
					Message.printWarning(1, routine, 
						"Error writing user to "
						+ "database.");
					Message.printWarning(2, routine, e);
					ready();
					return;
				}			
			}
			else {	
				// Old db does not have some of the fields...
				dmiString = "Insert INTO user_security ("
					+ " userNum,"
					+ " login,"
					+ " password,"
					+ " permissions,"
					+ " userName) VALUES ("
					+ userNum + ", '"
					+ login + "', '"
					+ password + "', '"
					+ __permissions + "', '"
					+ userName + "')";
				try {
					__dmi.dmiWrite(dmiString);
				}
				catch (Exception e) {	
					Message.printWarning(1, routine, 
						"Error writing user to "
						+ "database.");
					Message.printWarning(2, routine, e);
					ready();
					return;
				}					
			}
			
			// Query user preferences from guest login...
			try {
			userSecurity = 
			__dmi.readUserSecurityForLoginPasswordApplication(
				"guest", "guest", "CWRAT");		
			}
			catch (Exception e) {
				Message.printWarning(2, routine, "Error "
					+ "reading from database; no "
					+ "registration done.");
				Message.printWarning(2, routine, e);
				ready();
				return;
			}			
			
			// Error, no preferences located for guest...
			if (userSecurity == null) {
				new ResponseJDialog(this, "Error retrieving"
					+ " guest preferences.",
					ResponseJDialog.OK);
				ready();
				return;
			}

			int guestUserNum = userSecurity.getUser_num();
			List results = null;
			try {
			results = 
			__dmi.readUserPreferencesListForUser_num(
				guestUserNum);
			}
			catch (Exception e) {
				Message.printWarning(2, routine, "Error "
					+ "reading from database; no "
					+ "registration done.");
				Message.printWarning(2, routine, e);
				ready();
				return;
			}

			// Copy new user preferences from guest preferences...
			int size = results.size();
			for (int i = 0; i < size; i++) {
				HydroBase_UserPreferences prefData = 
					(HydroBase_UserPreferences)
					results.get(i);
				if (__dmi.isDatabaseVersionAtLeast(
					HydroBaseDMI.VERSION_19990305)) {
					// New database does not have
					// appliation in preferences...
					HydroBase_UserPreferences up = 
						new HydroBase_UserPreferences();
					up.setUser_num(userNum);
					up.setPreference(
						prefData.getPreference());
					up.setPref_value(
						prefData.getPref_value());
				try {
					__dmi.writeUserPreferences(up);
				}
				catch (Exception e) {
					Message.printWarning(1, routine,
						"Error writing user preferences"
						+ " to database.");
					Message.printWarning(2, routine, e);
					ready();
					return;
				}
				}
				else {	
					// Old database...
					dmiString =
						"Insert INTO"
						+ " user_preferences "
						+ "(userNum,"
						+"application,"
						+" preference,"
						+"pref_value)"
						+ " VALUES ("
						+ userNum 
						+ ", 'CWRAT', '"
						+ prefData.getPreference()
						+ "', '"
						+ prefData.getPref_value()
						+ "')";
				try {
					__dmi.dmiWrite(dmiString);
				}
				catch (Exception e) {
					Message.printWarning(1, routine,
						"Error writing user preferences"
						+ " to database.");
					Message.printWarning(2, routine, e);
					ready();
					return;
				}						
				}
			}
		}		
	}
	else {	
		new ResponseJDialog(this, "You have already registered.", 
			ResponseJDialog.OK);
		ready();
		return;
	}

	JGUIUtil.setWaitCursor(this, false);
	// Login as the new registered user...

	new ResponseJDialog(this, "Starting new CWRAT session using the"
		+ " following login information:"
		+ "\nUser name: " + userName
 		+ "\nLogin: " + login, ResponseJDialog.OK);

	__options_ui.setupNewUser(login, password);
	closeClicked();	
}

/**
Prepares the GUI for user interaction.
*/
private void ready() {
	JGUIUtil.setWaitCursor(this, false);
	__statusJTextField.setText(__READY);
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);

	// the following are used in the GUI layout
	Insets TLBR = new Insets(7,7,7,7);
	Insets XLNR = new Insets(21,7,0,7);
	Insets NLNR = new Insets(0,7,0,7);
	Insets NLBR = new Insets(0,7,7,7);
	Insets NLBN = new Insets(0,7,7,7);
	Insets TLNN = new Insets(7,7,0,0);
	Insets NLNN = new Insets(0,7,0,0);
	GridBagLayout gbl = new GridBagLayout();

	JPanel northJPanel = new JPanel();
	northJPanel.setLayout(new BorderLayout());
	getContentPane().add("North", northJPanel);

	JPanel northWJPanel = new JPanel();
	northWJPanel.setLayout(gbl);
	northJPanel.add("West", northWJPanel);

	int y = 0;

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("You need to register only if you will "
		+ "synchronize data to/from the main database."),
		0, y++, 4, 1, 0, 1, XLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("Name:"),
		0, y, 1, 1, 0, 1, TLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__lastJTextField = new JTextField(15);	
	__lastJTextField.addKeyListener(this);
	JGUIUtil.addComponent(northWJPanel,
		__lastJTextField,
		1, y, 1, 1, 0, 1, TLNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__firstJTextField = new JTextField(15);
	__firstJTextField.addKeyListener(this);	
	JGUIUtil.addComponent(northWJPanel,
		__firstJTextField,
		2, y, 1, 1, 0, 1, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__middleJTextField = new JTextField(2);
	__middleJTextField.addKeyListener(this);
	JGUIUtil.addComponent(northWJPanel,
		__middleJTextField,
		3, y++, 1, 1, 0, 1, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("Last:"),
		1, y, 1, 1, 0, 1, NLNN, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("First:"),
		2, y, 1, 1, 0, 1, NLBN, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("Middle Initial:"),
		3, y++, 1, 1, 0, 1, NLBR, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("Login:"),
		0, y, 1, 1, 0, 1, NLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__loginJTextField = new JTextField(15);
	__loginJTextField.addKeyListener(this);
	JGUIUtil.addComponent(northWJPanel,
		__loginJTextField,
		1, y++, 1, 1, 0, 1, NLBN, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("Password:"),
		0, y, 1, 1, 0, 1, NLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__passwordJPasswordField = new JPasswordField(15);
	__passwordJPasswordField.setEchoChar('*');
	__passwordJPasswordField.addKeyListener(this);
	JGUIUtil.addComponent(northWJPanel,
		__passwordJPasswordField,
		1, y++, 1, 1, 0, 1, NLBN, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("Re-enter Password:"),
		0, y, 1, 1, 0, 1, NLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__confirmJPasswordField = new JPasswordField(15);
	__confirmJPasswordField.setEchoChar('*');
	__confirmJPasswordField.addKeyListener(this);
	JGUIUtil.addComponent(northWJPanel,
		__confirmJPasswordField,
		1, y++, 1, 1, 0, 1, NLBN, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("IMPORTANT!"),
		0, y++, 1, 1, 0, 1, XLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("NEW USERS:"),
		0, y++, 4, 1, 0, 1, NLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("After pressing Register, you must synchronize your "
		+ "preferences to the server and then contact the"),
		0, y++, 4, 1, 0, 1, TLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("CDSS administrator at cdss@state.co.us "
		+ "to obtain synchronization privileges on the server."),
		0, y++, 4, 1, 0, 1, TLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel(
		"IF YOU ARE AN EXISTING USER INSTALLING A NEW DATABASE:"),
		0, y++, 4, 1, 0, 1, TLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(northWJPanel,
		new JLabel("First register using the same name and login"
		+ " that you used previously."),
		0, y++, 4, 1, 0, 1, TLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	JGUIUtil.addComponent(northWJPanel,
		new JLabel("After pressing Register, you can synchronize your "
		+ "preferences from the server if you backed them up."),
		0, y++, 4, 1, 0, 1, TLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JPanel southJPanel = new JPanel();
	southJPanel.setLayout(new BorderLayout());
	getContentPane().add("South", southJPanel);

	JPanel buttonJPanel = new JPanel();
	buttonJPanel.setLayout(new FlowLayout());
	southJPanel.add("North", buttonJPanel);

	SimpleJButton reg = new SimpleJButton(__BUTTON_REGISTER, this);
	reg.setToolTipText("Register a user with these values.");
	buttonJPanel.add(reg);
	SimpleJButton cancel = new SimpleJButton(__BUTTON_CANCEL, this);
	cancel.setToolTipText("Discard data and return.");
	buttonJPanel.add(cancel);
	
	JPanel statusJPanel = new JPanel();
	statusJPanel.setLayout(gbl);
	southJPanel.add("South", statusJPanel);
	
	__statusJTextField = new JTextField();
	__statusJTextField.setEditable(false);
	JGUIUtil.addComponent(statusJPanel, __statusJTextField,
		0, 0, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}
	
	setTitle(app + "User Registration");
	setResizable(false);
	pack();
	JGUIUtil.center(this);
	setVisible(true);
}

/**
Responds to window activate events; does nothing.
@param evt that WindowEvent that happened.
*/
public void windowActivated(WindowEvent evt) {}

/**
Responds to window closed events; does nothing.
@param evt that WindowEvent that happened.
*/
public void windowClosed(WindowEvent evt) {}

/**
Responds to window closing events; closes the GUI
@param evt that WindowEvent that happened.
*/
public void windowClosing(WindowEvent evt) {
	closeClicked();
}

/**
Responds to window deactivated events; does nothing.
@param evt that WindowEvent that happened.
*/
public void windowDeactivated(WindowEvent evt) {}

/**
Responds to window deiconified events; does nothing.
@param evt that WindowEvent that happened.
*/
public void windowDeiconified(WindowEvent evt) {}

/**
Responds to window iconified events; does nothing.
@param evt that WindowEvent that happened.
*/
public void windowIconified(WindowEvent evt) {}

/**
Responds to window opened events; does nothing.
@param evt that WindowEvent that happened.
*/
public void windowOpened(WindowEvent evt) {}

}
