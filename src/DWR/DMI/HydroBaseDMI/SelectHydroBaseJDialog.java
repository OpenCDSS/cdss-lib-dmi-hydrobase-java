//-----------------------------------------------------------------------------
// SelectHydroBaseJDialog - general login dialog for HydroBaseDMI connections
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History: 
//
// 2002-10-04	J. Thomas Sapienza, RTi	Initial version from HBLoginGUI.java.
//					Changed to use Swing.
// 2002-10-07	JTS, RTi		Made modal.
// 2002-10-14	Steven A. Malers, RTi	Review and clean up for general use.
//					In particular:
//					* Alphabetize methods.
//					* Rename DBLoginJDialog to
//					  SelectHydroBaseJDialog.
//					* for JPasswordField, use getPassword()
//					  instead of getText().
//					* Pass data in via a PropList.
//					* Actually create the HydroBaseDMI here.
//					  The calling code can retrieve and use
//					  if not null.
//					* Combine the intialize methods and
//					  get the event handlers working.
//					* Change the connection from a Choice to
//					  a JComboBox to avoid lightweight/
//					  heavyweight issues.
// 2002-10-24	SAM, RTi		Remove the dbhost information as
//					arguments.  For now hard-code the
//					information and later will decide how
//					to limit to appropriate selections.
// 2003-01-05	SAM, RTi		Update based on changes to the DMI
//					package.
// 2003-02-11	SAM, RTi		Fix to make this work with updated
//					StateDMI.
// 2003-03-26	JTS, RTi		* Changed __hostnameJComboBox into a 
//					  SimpleJComboBox so that pressing 
//					  return in it sends an OK to the 
//					  dialog.
//					* Added code to check whether the 
//					  dialog was cancelled out of.
// 2003-04-04	JTS, RTi		Changed GUIUtil to JGUIUtil.
// 2003-04-29	SAM, RTi		Add hbserver.riverside.com as a SQL
//					Server machine if in test mode.
// 2003-09-24	JTS, RTi		Add dillon.riverside.com as a SQL
//					Server machine if in test mode.
// 2004-01-08	JTS, RTi		The list of available Local PC DSNs
//					is now limited to those that have the
//					phrase "HydroBase" (case-insensitive)
//					in them.
// 2004-05-26	JTS, RTi		Now uses SQLServer2000 instead of
//					SQLServer7.
// 2004-07-19	JTS, RTi		Added the static datasource that can
//					be specified to default the connections.
// 2004-07-26	JTS, RTi		Added the static div that can be
//					specified to default the connections.
// 2005-02-01	JTS, RTi		Added the checkbox to select whether 
//					to use the CDSS login or not.
// 2005-03-04	JTS, RTi		Commented out the help button.
// 2005-06-01	JTS, RTi		* When Access databases are selected,
//					  the checkbox for stored procedures is
//					  unchecked.
//					* Changed "2005" to "Use Stored 
//					  Procedures"
// 2005-06-03	JTS, RTi		Exception warnings are now printed at 3.
// 2005-06-08	JTS, RTi		Modified the dialog in many ways:
//					* database servers are read from a 
//					  configuration file.
//					* databases on servers are read and 
//					  cached from the servers.  
// 					* if invalid connection settings are
//					  entered, OK is diabled.
//					* removed unnecessary methods.
//					* added finalize().
//					* cleaned up the variable definition
//					  section.
//					* cleaned up javadocs
// 2005-07-27	JTS, RTi		Now using port 21784 (instead of 1433)
//					for all SQL Server transactions.
// 2005-08-03	JTS, RTi		Added a check for old configuration
//					files that force local to appear as the
//					host.
// 2007-02-26	SAM, RTi		Make stored procedures the default unless
//					running in test mode.
//					Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.text.JTextComponent;

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

import java.io.File;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import RTi.DMI.DMIUtil;
import RTi.DMI.GenericDMI;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJButton;
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.IOUtil;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
This SelectHydroBaseJDialog class provides the user with an interface to
select a HydroBase host and database name (or ODBC connection if used with Access).
Several interface components can optionally be displayed to allow user login and
password and selection of a water division.  A summary of the components and behavior is:
<pre>
Login:              Optional user login name (system login is defaulted
                    internally) - default is "guest"
Password:           Optional user password - default is "guest"
Connection:         Database connection (local or remote)
Database Hostname:  If connection is "local" this will be "localpc"
                    If connection is "remote" this will be a server machine.
Database Name:      The name of the database to which to connect.
Model:              Optional model to use to configure menus (used with StateDMI).
Water Division:     Optional water division used to control map and
                    interface - default is "DEFAULT" (used with StateView).
</pre>
*/
public class SelectHydroBaseJDialog extends JDialog
implements ActionListener, KeyListener, ItemListener, WindowListener {

/**
The default ODBC DSN that has been set.  If this is not null, then when the
dialog opens the dialog will be set to make a local connection with this DSN as the default value.
*/
private static String __defaultOdbcDsn = null;

/**
The default division number that has been set.  If this is not null, then
whenever a division is to be selected, it will be set to this (if valid).
*/
private static String __defaultDiv = null;

/**
If the constructor indicates that water districts should be listed, these
strings are used to display district names.
*/
public final String 
	DIV1 = "DIV1 - South Platte",
	DIV2 = "DIV2 - Arkansas",
	DIV3 = "DIV3 - Rio Grande",
	DIV4 = "DIV4 - Gunnison",
	DIV5 = "DIV5 - Colorado",
	DIV6 = "DIV6 - Yampa/White",
	DIV7 = "DIV7 - San Juan",
	DIV_DEFAULT = "DEFAULT";

/**
Button labels.
*/
private final String
	__BUTTON_CANCEL = "Cancel",
	__BUTTON_OK = "OK";

/**
Name of the default ODBC DSN name.
*/
private final String __DEFAULT_ODBC_DSN = "HydroBase";

/**
The String that the Databases should start with.
*/
private final String __HYDROBASE = "HydroBase";

/**
Default hostname when making a local Microsoft Access connection.
*/
private final String __LOCALPC = "localpc";

/**
Default string that shows up if a server has no database that match HydroBase*.
*/
private final String __NO_DATABASES = "[No HydroBase databases available]";

/**
Strings that specify how to connect, remote or local.
*/
private final String	
	__LOCAL = "Use Access Database",
	__REMOTE = "Use SQL Server Database";

/**
Whether the Dialog was closed with the cancel button.
*/
private boolean __cancelled = false;

/**
Whether to ignore action events or not.
*/
private boolean __ignoreAction = false;

/**
Models to show if __showModels = true.
*/
private String __MODEL_StateCU = "StateCU";
private String __MODEL_StateMod = "StateMod";

/**
Whether to display the models for users to select from - used by StateDMI since it
has separate StateCU and StateMod looks.
*/
private boolean __showModels = false;

/**
Whether to display the water divisions for users to select from.
*/
private boolean __showWaterDivisions = false;

/**
Whether the user name / password combo should be entered and validated.
*/
private boolean __validateLogin = false;

/**
A hashtable for typing server names to Vectors of the databases available in the server.
*/
private Hashtable __databaseNames = null;

/**
An existing HydroBaseDMI instance which can be used.
*/
private HydroBaseDMI __hbdmi = null;

/**
The checkbox to click to choose whether to use stored procedures or not.
*/
private JCheckBox __useSPJCheckBox = null;

/**
Label for the combo box from which ODBC DSNs are chosen.
*/
private JLabel __odbcDSNsJLabel = null;

/**
Label for the combo box from which database names are chosen.
*/
private JLabel __databaseNamesJLabel = null;

/**
The field into which the user's password is entered.
*/
private JPasswordField __passwordJPasswordField = null;

/**
The textfield for entering the user name into.
*/
private JTextField __loginJTextField = null;

/**
The status bar text field.
*/
private JTextField __statusJTextField = null;

/**
The properties read from \cdss\system\CDSS.cfg
*/
private PropList __configurationProps = null;

/**
GUI buttons.
*/
private SimpleJButton 
	__okJButton = null,
	__cancelJButton = null;
			
/**
The combo box for choosing to do a remote or local connection.
*/
private SimpleJComboBox	__connectionJComboBox = null;

/**
The combo box from which databases are selected.
*/
private SimpleJComboBox __databaseNamesJComboBox = null;

/**
The combo box from which the name of the host computer is selected.
*/
private SimpleJComboBox	__hostnameJComboBox = null;

/**
The combo box for choosing the model.
*/
private SimpleJComboBox	__modelJComboBox = null;

/**
The combo box for choosing the water division to use by default.
*/
private SimpleJComboBox	__waterDivisionJComboBox = null;

/**
The combo box for choosing from the list of available DSNs.
*/
private SimpleJComboBox	__odbcDSNJComboBox = null;

/**
Used when a HydroBaseDMI instance is passed into this dialog, this is the 
host that the HydroBaseDMI is already connected to.
*/
private String __dbhost = null;

/**
The default database to select from the database list.
*/
private String __defaultDatabaseName = __HYDROBASE;

/**
The default server that will be selected.
*/
private String __defaultServerName = null;

/**
The server that was last chosen from the list of servers.
*/
private String __lastSelectedServer = null;

/**
The division that the user selected to use.
*/
private String __selectedDivision = null;

/**
The model that the user selected to use.
*/
private String __selectedModel = __MODEL_StateCU;

/**
A list of available ODBC DSNs.
*/
private List __available_OdbcDsn = null;

/**
The names of the servers that can appear in the server combo box.
*/
private List __serverNames = null;

/**
The username that was automatically detected by trying to create a connection to a database.
*/
private String __detectedUsername = null;

/**
The password that was automatically detected by trying to create a connection to a database.
*/
private String __detectedPassword = null;

/**
The port that was automatically detected by trying to create a connection to a database.
*/
private int __detectedPort = -1;

/**
Constructor.  Constructs and displays a SelectHydroBaseJDialog.
@param parent Calling class.  
@param hbdmi HydroBaseDMI that is currently being used.  If the result of the
SelectHydroBase dialog "OK", then whatever HydroBaseDMI that is in effect will
be available to the calling code (it will be null if the login failed).
@param props Properties for the selection ("ValidateLogin"=true|false,
"ShowWaterDivisions"=true|false, "DatabaseHost"=localhost, "ArchiveDatabaseHost"=remoteHost).
*/
public SelectHydroBaseJDialog(JFrame parent, HydroBaseDMI hbdmi, PropList props)
{	
	super(parent, true);

	JGUIUtil.setWaitCursor(this, true);
	JGUIUtil.setWaitCursor(parent, true);
	readConfigurationFile();

	__databaseNames = new Hashtable();
	
	try {	
		initialize(parent, hbdmi, props);
	}
	catch (Exception e) {
		Message.printWarning(3, "SelectHydroBaseJDialog", e);
	}

	JGUIUtil.setWaitCursor(this, false);
	JGUIUtil.setWaitCursor(parent, false);
}

/**
Responds to action events.
@param event the event that happened.
*/
public void actionPerformed(ActionEvent event) {
	String command = event.getActionCommand();
	
	// actions are ignored only when the __hostnameJComboBox is being
	// repopulated via add() calls, otherwise each add() would result
	// in an actionPerformed() being fired.
	if (__ignoreAction) {
		return;
	}

	if (command.equals(__BUTTON_OK)) {
		okClicked();
	}  
	else if (command.equals(__BUTTON_CANCEL)) {
        	cancelClicked();
	}
	else if (event.getSource() == __hostnameJComboBox) {
		findDatabaseNames();
		__lastSelectedServer = __hostnameJComboBox.getSelected();
	}
	else if (event.getSource() == __useSPJCheckBox) {
		// rebuild the hashtable because there is a chance that hashed
		// database names for a server are no longer valid when 
		// the stored procedure option is turned off and back on.
		__databaseNames = new Hashtable();
		findDatabaseNames();
	}
}      

/**
Close the dialog without transferring any settings to the internal data.
*/
private void cancelClicked() {
	// The HydroBaseDMI is the one that was passed into the constructor (no need to do anything).  
	__cancelled = true;
	closeDialog();
}

/**
Check the database version.  If it is not recognized, print a warning for the user.
@param hbdmi HydroBaseDMI instance to check version.
*/
private void checkDatabaseVersion(HydroBaseDMI hbdmi) {
	String message, routine = "SelectHydroBaseJDialog.checkDatabaseVersion";
	long dbversion = hbdmi.getDatabaseVersion();
	if (dbversion == 0) {
		message = "The HydroBase version (" + dbversion 
			+ ") is not recognized.  " + IOUtil.getProgramName() 
			+ " may not run correctly.";
		Message.printWarning(1, routine, message);
	}
	else if (dbversion < HydroBaseDMI.VERSION_LATEST) {
		message = "The database design version (" + dbversion 
			+ ") is not the latest version\n" 
			+ "recognized by the software (" 
			+ HydroBaseDMI.VERSION_LATEST + ").\n" 
			+ IOUtil.getProgramName() 
			+ " is backward-compatible where possible.";
		Message.printWarning(2, routine, message);
	}
}

/**
Checks a server with the given hostname for the databases running on it and populate the displayed list
of database names.
@param server the server to check.
*/
private void checkServerForDatabaseNames(String server)
{	String routine = getClass().getName() + ".checkServerForDatabaseNames";
	String[] usernames = new String[3];
	String[] passwords = new String[3];
	int[] ports = new int[3];

	if (!__useSPJCheckBox.isSelected()) {
		usernames[0] = "crdss";	
		passwords[0] = "crdss3nt";    
		ports[0] = 5758;
		
		usernames[1] = "crdss";	
		passwords[1] = "crdss3nt";    
		ports[1] = 21784;

		usernames[2] = "crdss";	
		passwords[2] = "crdss3nt";    
		ports[2] = 1433;
	}
	else {
		usernames[0] = "cdss";
		passwords[0] = "cdss%tools";
		ports[0] = 5758;
		
		usernames[1] = "cdss";
		passwords[1] = "cdss%tools";
		ports[1] = 21784;
		
		usernames[2] = "cdss";	
		passwords[2] = "cdss%tools";  
		ports[2] = 1433;
	}

	GenericDMI dmi = null;

	for ( int i = 0; i < ports.length; i++) {
		Message.printStatus(2, routine, "Checking for databases on server \"" +
			server + "\" using port " + ports[i] );
		try {
			dmi = new GenericDMI("SQLServer", server, "master", ports[i], usernames[i], passwords[i]);
			dmi.open();
		}
		catch (Exception e) {
			// If there is an exception then the DMI was not opened, so there is no need to close it here.
			dmi = null;
		}

		if (dmi != null) {
			__detectedUsername = usernames[i];
			__detectedPassword = passwords[i];
			__detectedPort = ports[i];
			break;
			// the DMI is closed below after database information is read from it.
		}
	}

	try {
		if (dmi == null) {
			// error -- throw an exception to hit the catch below.
			throw new Exception("Unable to get DMI connection using available options.");
		}
	
		Connection c = dmi.getConnection();
		DatabaseMetaData dmd = c.getMetaData();
		ResultSet catalogs = dmd.getCatalogs();
		List v = DMIUtil.processResultSet(catalogs);
		dmi.close();
		__databaseNamesJComboBox.removeAllItems();
		List v2 = null;
		int size = v.size();
		Message.printStatus(2, routine, "Have " + size + " database names for server \"" + server +
			"\" using port " + ports[1]);
		if (size == 0) {
			// No database found -- this should NEVER happen for a valid HydroBase server ...
			v.add(__NO_DATABASES);
			__databaseNamesJComboBox.add(__NO_DATABASES);
			__databaseNames.put(server, v);
			ok(false);
			return;
		}
		
		String s = null;
		int count = 0;
		List v3 = new Vector();
		for ( int i = 0; i < size; i++) {
			v2 = (List)v.get(i);
			if ( v2.size() < 1 ) {
				Message.printWarning(3, routine, "Record [" + i +
					"] database catalog has zero columns - no database name.");
			}
			else {
				s = (String)v2.get(0);
				s = s.trim();
	
				// only add those database that start with "HydroBase"
				if (StringUtil.startsWithIgnoreCase(s, __HYDROBASE)) {
					v3.add(s);
					__databaseNamesJComboBox.add(s);
					count++;
				}
			}
		}

		if (count == 0) {
			// no databases were found that started with "HydroBase"
			v3.add(__NO_DATABASES);
			__databaseNamesJComboBox.add(__NO_DATABASES);
			ok(false);
		}
		else {
			ok(true);
		}
		__databaseNames.put(server, v3);
	}
	catch (Exception e) {
		Message.printWarning(3, routine, "Error getting database names (" + e + ").");
		Message.printWarning(3,routine,e);
		__databaseNamesJComboBox.removeAllItems();
		List v = new Vector();
		v.add(__NO_DATABASES);
		__databaseNamesJComboBox.add(__NO_DATABASES);
		ok(false);
		__databaseNames.put(server, v);
	}
}

/**
Close the dialog and dispose of graphical resources.
*/
private void closeDialog() {
	super.setVisible(false);
	dispose();
}

/**
Handle a change in the connection choice (ie, from remote to local, or vice versa).
@param connection If null, the code is assumed to be called after a graphical
interface interaction.  If specified, the connection is forced to select as if
the user had done so (used in initialization).
*/
private void connectionSelected(String connection) {
	if (connection != null) {
		try {	
			__connectionJComboBox.setSelectedItem(connection);
		}
		catch (Exception e) {
			// This should NOT happen.
			Message.printWarning(2, "SelectHydrobaseJDialog",
				"Unable to select \"" + connection + "\" in list.");
		}
	}

	// If it is not local, disable the ODBC Data Source name

	if (((String)__connectionJComboBox.getSelectedItem()).equals(__REMOTE)){
		// Will use a remote database, so set to the default for 
		// the main server to make sure that they are consistent.
		// Enable the Database Hostname.
		__hostnameJComboBox.setEnabled(true);
		__hostnameJComboBox.removeAllItems();
		__ignoreAction = true;

		// Populate the list from the available choices
		if ((__serverNames == null) || (__serverNames.size() == 0)) {
			__hostnameJComboBox.addItem("None");
			ok(false);
		}
		else {	
			// add all the servers from the server name vectors,
			// and select the one that matches the hbdmi's server (if the dmi is not null).
			String server = null;
			if (__hbdmi != null) {
				server = __hbdmi.getDatabaseServer();
			}

			int size = __serverNames.size();
			String s = null;
			for (int i = 0; i < size; i++) {
				s = (String)__serverNames.get(i);
				__hostnameJComboBox.addItem(s);

				// make sure the dmi's server is the selected one, if it is in the list
				if (__hbdmi != null && server != null && server.equalsIgnoreCase(s)) {
					__hostnameJComboBox.setSelectedIndex(i);
				}
			}
		}

		__hostnameJComboBox.select(__defaultServerName);
		__lastSelectedServer = __defaultServerName;
		__ignoreAction = false;

		// populate the database names given the server name
		findDatabaseNames();		
		
		// DSN is just "HydroBase" and cannot be changed...
		__odbcDSNJComboBox.removeAllItems();
		__odbcDSNJComboBox.addItem(__DEFAULT_ODBC_DSN);
		__odbcDSNJComboBox.setEnabled(false);
		
		__odbcDSNsJLabel.setVisible(false);
		__odbcDSNJComboBox.setVisible(false);
		__databaseNamesJLabel.setVisible(true);
		__databaseNamesJComboBox.setVisible(true);
	}
	else {	
		// Local database - allow the ODBC name to be selected.
		// Change the host back to local and disable
		__hostnameJComboBox.setEnabled(false);
		__ignoreAction = true;
		__hostnameJComboBox.removeAllItems();
		__hostnameJComboBox.addItem(__LOCALPC);
		__ignoreAction = false;
		
		// ODBC must be selected...
		__odbcDSNJComboBox.setEnabled(true);
		__odbcDSNJComboBox.removeAllItems();
		
		if (__available_OdbcDsn == null || __available_OdbcDsn.size() == 0) {
			__odbcDSNJComboBox.addItem("Unable to Determine");
		}
		else {	
			int size = __available_OdbcDsn.size();
			String odbc = null;
			String dmiName = null;
			if (__hbdmi != null) {
				dmiName = __hbdmi.getDatabaseName();
			}
			for (int i = 0; i < size; i++) {
				odbc = (String)__available_OdbcDsn.get(i);
				__odbcDSNJComboBox.addItem(odbc);

				// Try to default to the existing DMI selection
				if (__hbdmi != null && dmiName != null && dmiName.equalsIgnoreCase(odbc)) {
					__odbcDSNJComboBox.setSelectedIndex(i);
				}
			}
		}

		if (__defaultOdbcDsn != null) {
			__odbcDSNJComboBox.select(__defaultOdbcDsn);
		}

		__okJButton.setEnabled(true);
		__odbcDSNsJLabel.setVisible(true);
		__odbcDSNJComboBox.setVisible(true);
		__databaseNamesJLabel.setVisible(false);
		__databaseNamesJComboBox.setVisible(false);
	}
}

/**
Cleans up member variables.
*/
public void finalize() 
throws Throwable {
	__databaseNames = null;
	__hbdmi = null;
	__useSPJCheckBox = null;
	__odbcDSNsJLabel = null;
	__databaseNamesJLabel = null;
	__passwordJPasswordField = null;
	__loginJTextField = null;
	__statusJTextField = null;
	__configurationProps = null;
	__okJButton = null;
	__cancelJButton = null;
	__connectionJComboBox = null;
	__databaseNamesJComboBox = null;
	__hostnameJComboBox = null;
	__waterDivisionJComboBox = null;
	__odbcDSNJComboBox = null;
	__dbhost = null;
	__defaultDatabaseName = null;
	__defaultServerName = null;
	__lastSelectedServer = null;
	__selectedDivision = null;
	__available_OdbcDsn = null;
	__serverNames = null;

	super.finalize();
}

/**
Finds the names of the databases that correspond to the currently-selected
database, and populates the database name combo box with them.
*/
private void findDatabaseNames() {
	String s = __hostnameJComboBox.getSelected().trim();

	// convert local and localpc to the actual name of the machine.
	if (s.equalsIgnoreCase("local") || s.equalsIgnoreCase("localpc")) {
		s = IOUtil.getProgramHost();
	}
	
	// First check to see if the database names have been cached from the
	// database server.  If so, use the cached copy rather than going out to the server again.
	List v = (List)__databaseNames.get(s);
	if (v != null) {
		int size = v.size();
		__databaseNamesJComboBox.removeAllItems();
		for (int i = 0; i < size; i++) {
			s = (String)v.get(i);
			__databaseNamesJComboBox.add(s);
		}
		
		if (size == 0 || (size == 1 && s.equals(__NO_DATABASES))) {
			ok(false);
		}
		else {
			ok(true);
		}
	
		if (__databaseNamesJComboBox.contains(__defaultDatabaseName)) {
			__databaseNamesJComboBox.select(__defaultDatabaseName);
		}
		return;
	}
	
	// Determine whether currently-selected server is the localhost.
	boolean local = false;
	if (s.equalsIgnoreCase(IOUtil.getProgramHost())) {
		local = true;
	}
		
	if (local) {
		// For local hosts, do a quick check to see if a SQL Server
		// instance is running -- see if port 5758 (newer) or 21784 (older) can be locked by
		// Java.  If it can, then the port is open and SQL Server is
		// probably (99.99999% of the time) not running.  Otherwise,
		// SQL Server is probably (99.99999% of the time) up on that port.
		if ( IOUtil.isPortOpen(5758) && IOUtil.isPortOpen(21784) ) {
			__databaseNamesJComboBox.removeAllItems();
			__databaseNamesJComboBox.add(__NO_DATABASES);
			ok(false);
			return;
		}
		// TODO SAM 2009-05-22 Why not just use the following always? speed?
		else {
			checkServerForDatabaseNames(s);
		}
	}
	else {
		// Check database names on server
		checkServerForDatabaseNames(s);
	}

	if (__databaseNamesJComboBox.contains(__defaultDatabaseName)) {
		__databaseNamesJComboBox.select(__defaultDatabaseName);
	}
}

/**
Returns the HydroBaseDMI associated with this dialog, which is either the
original HydroBaseDMI or a new one as the result of current input.
@return the HydroBaseDMI associated with this dialog.
*/
public HydroBaseDMI getHydroBaseDMI() {
	return __hbdmi;
}

/**
Returns the default username for this dialog.
@return the default username for this dialog.
*/
public static String getDefaultLogin() {
	return "guest";
}

/**
Returns the default password for this dialog.
@return the default password for this dialog.
*/
public static String getDefaultPassword() {
	return "guest";
}

/**
Returns the model selected in the dialog.
@return the model selected in the dialog.
*/
public String getSelectedModel() {
	return __selectedModel;
}

/**
Returns the division selected in the dialog.
@return the division selected in the dialog.
*/
public String getSelectedDivision() {
	return __selectedDivision;
}

/**
Initialize the dialog components.
*/
private void initialize(JFrame parent, HydroBaseDMI hbdmi, PropList props) {
	__hbdmi = hbdmi;

	String prop_value = props.getValue("ValidateLogin");

	if (prop_value != null) {
		if (prop_value.equalsIgnoreCase("true")) {
			__validateLogin = true;
		}
		else {	
			__validateLogin = false;
		}
	}
	
	prop_value = props.getValue("ShowModels");
	if (prop_value != null) {
		if (prop_value.equalsIgnoreCase("true")) {
			__showModels = true;
		}
		else {	
			__showModels = false;
		}
	}
	
	prop_value = props.getValue("ShowWaterDivisions");
	if (prop_value != null) {
		if (prop_value.equalsIgnoreCase("true")) {
			__showWaterDivisions = true;
		}
		else {	
			__showWaterDivisions = false;
		}
	}

	// For now hard-code.  Later need to find a way to put in a Jar so that
	// "outside" users do not see the remote host...
	// TODO (JTS - 2005-06-08)
	// I don't understand the above comment, but perhaps it's something 
	// that should be done in the future.

	// Database host.  If a local database, then an ODBC data source name 
	// is enabled.  If remote, the ODBC will be handled internally and 
	// displayed in the dialog as "HydroBase".
	__dbhost  = null;	
	
	String connection = __LOCAL;
	String userLogin = "";
	String userPassword = "";
	
	// Set the information that is needed in the dialog for initialization
	// below.  For the database connection, mainly need to select the
	// connection type so that other information can cascade...

	if (__hbdmi == null) {
		// This is the first time a database connection has been defined.
		if (__serverNames.size() == 0) {
			// Assume a local only connection
			connection = __LOCAL;
		}
		else {	
			// Remote hosts are available so set as the default
			connection = __REMOTE;
		}
		
		userLogin = getDefaultLogin();
		userPassword = getDefaultPassword();
	}
	else {	
		// A previous connection is in effect.  Use the host information from that connection.
		__dbhost = __hbdmi.getDatabaseServer();
		
		if (__dbhost.equalsIgnoreCase(__LOCALPC)) {
			connection = __LOCAL;
		}
		else {	
			connection = __REMOTE;
			
			// Add the dbhost to the remote hosts list if it is
			// not already in - that way it can be selected
			// automatically when this dialog is viewed repeatedly
			
			boolean found = false;
			int size = __serverNames.size();
			String host = null;

			for (int i = 0; i < size; i++ ) {
				host = (String)__serverNames.get(i);
				if (host.equalsIgnoreCase(__dbhost)) {
					found = true;
					break;
				}
			}

			if (!found) {
				// the dbhost was not found in the host list, so add to the list and sort
				__serverNames.add(__dbhost);
				__serverNames = StringUtil.sortStringList( __serverNames);
			}

			__defaultServerName = __dbhost;
			__defaultDatabaseName = __hbdmi.getDatabaseName();
		}

		userLogin = __hbdmi.getUserLogin();
		userPassword = __hbdmi.getUserPassword();
		__selectedDivision =HydroBase_GUI_Util.getActiveWaterDivision();
	}

	addWindowListener(this);
                
	// used in the GridBagLayouts
	Insets LR_insets = new Insets(0,7,0,7);
	Insets LTB_insets = new Insets(7,7,0,0);
	Insets RTB_insets = new Insets(7,0,0,7);
	GridBagLayout gbl = new GridBagLayout();

	// North JPanel
	JPanel northJPanel = new JPanel();
	northJPanel.setLayout(new BorderLayout());
	getContentPane().add("North", northJPanel);

	// North West JPanel
	JPanel northWJPanel = new JPanel();
	northWJPanel.setLayout(gbl);
	northJPanel.add("West", northWJPanel);

	int y = -1;	// Vertical position of components.

	// Always create the components so that they can be assigned default
	// values.  However, only actually add to the interface if they are
	// needed for interactive use...

	// Login
	if (__validateLogin) {
        	JGUIUtil.addComponent(northWJPanel, new JLabel("Login (1):"), 
			0, ++y, 1, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.EAST);
	}
	
    __loginJTextField = new JTextField(25);
	__loginJTextField.addKeyListener(this);
	__loginJTextField.setText(userLogin);
	if (__validateLogin) {
        JGUIUtil.addComponent(northWJPanel, __loginJTextField, 
		1, y, 1, 1, 0, 0, RTB_insets, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	}

	// Password
	if (__validateLogin) {
        JGUIUtil.addComponent(northWJPanel, new JLabel("Password:"), 
		0, ++y, 1, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.EAST);
	}
	
	__passwordJPasswordField = new JPasswordField(25);
	__passwordJPasswordField.setEchoChar('*');
	__passwordJPasswordField.setText(userPassword);
	__passwordJPasswordField.addKeyListener(this);
	if (__validateLogin) {
		JGUIUtil.addComponent(northWJPanel, __passwordJPasswordField, 
			1, y, 1, 1, 0, 0, RTB_insets, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	}

	// Always add the connection

	JLabel dataBaseJLabel = new JLabel("Connection:");
       	JGUIUtil.addComponent(northWJPanel, dataBaseJLabel, 
		0, ++y, 1, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.EAST);
		
	__connectionJComboBox = new SimpleJComboBox();
	
	if (__serverNames.size() == 0) {
		// Only the local connection is available
		__connectionJComboBox.addItem(__LOCAL);
		__connectionJComboBox.setSelectedIndex(0);
	}
	else {	
		// Local and remote hosts (remote will be the default)
		__connectionJComboBox.addItem(__LOCAL);
		__connectionJComboBox.addItem(__REMOTE);
		__connectionJComboBox.setSelectedIndex(1);
	}
	
   	JGUIUtil.addComponent(northWJPanel, __connectionJComboBox, 
	1, y, 1, 1, 0, 0, RTB_insets, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		
	// Add a listener so can enable/disable the data source name:
	__connectionJComboBox.addItemListener(this);
	__connectionJComboBox.addKeyListener(this);

	// Add the host name (only enabled if using a remote connection)

	// Define because below set invisible if an Applet
	JLabel hostname_JLabel = new JLabel("Database Hostname:");
   	JGUIUtil.addComponent(northWJPanel, hostname_JLabel,
		0, ++y, 1, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.EAST);

	// The contents will be set in "connectionSelected"
	__hostnameJComboBox = new SimpleJComboBox ();
	__hostnameJComboBox.setEditable(true);
	__hostnameJComboBox.addKeyListener(this);
	__hostnameJComboBox.addActionAndKeyListeners(this, this);
    JGUIUtil.addComponent(northWJPanel, __hostnameJComboBox, 
		1, y, 1, 1, 0, 0, RTB_insets, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// Add the data source (only enabled if using a local database).
	// Get the data source names from the system.

    List available_OdbcDsn = DMIUtil.getDefinedOdbcDsn(true);
	int size = available_OdbcDsn.size();
	String s = null;
	__available_OdbcDsn = new Vector();

	// Only add DSNs that have "HydroBase" in the name.
	for (int i = 0; i < size; i++) {
		s = (String)available_OdbcDsn.get(i);
		if (StringUtil.indexOfIgnoreCase(s, __DEFAULT_ODBC_DSN, 0)> -1){
			__available_OdbcDsn.add(s);
		}
	}
	
	if (__hbdmi != null) {
		__available_OdbcDsn.add(__dbhost);
	}

	// Make sure the default ODBC DSN is in the list.
	size = __available_OdbcDsn.size();
	if (__defaultOdbcDsn != null) {
		boolean found = false;
		for (int i = 0; i < size; i++) {
			s = (String)__available_OdbcDsn.get(i);
			if (s.equals(__defaultOdbcDsn)) {
				found = true;
			}
		}

		if (!found) {
			__available_OdbcDsn.add(__defaultOdbcDsn);
		}
	}
	
	size = __available_OdbcDsn.size();
	String longest_odbc = "Unable to Determine";
	String odbc;
	for (int i = 0; i < size; i++) {
		odbc = (String)__available_OdbcDsn.get(i);
		if (odbc.length() > longest_odbc.length()) {
			longest_odbc = odbc;
		}
	}
	
	// Add a couple of spaces to try to account for variable font...
	longest_odbc += "          ";

	// Items will be added to this in "connectionSelected"
	__odbcDSNsJLabel = new JLabel("Database ODBC DSNs:");
   	JGUIUtil.addComponent(northWJPanel, __odbcDSNsJLabel, 
		0, ++y, 1, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.EAST);
		
	__odbcDSNJComboBox = new SimpleJComboBox();
	__odbcDSNJComboBox.setEditable(true);
	__odbcDSNJComboBox.addKeyListener(this);
	__odbcDSNJComboBox.addItemListener(this);
	// Set to the longest available ODBC DSN so switching between remote and
	// local does not hide components...
	__odbcDSNJComboBox.setPrototypeDisplayValue(longest_odbc);
   	JGUIUtil.addComponent(northWJPanel, __odbcDSNJComboBox,
		1, y, 1, 1, 0, 0, RTB_insets, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__databaseNamesJLabel = new JLabel("Database Names:");
   	JGUIUtil.addComponent(northWJPanel, __databaseNamesJLabel, 
		0, y, 1, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.EAST);
		
	__databaseNamesJComboBox = new SimpleJComboBox();
	__databaseNamesJComboBox.setEditable(true);
	__databaseNamesJComboBox.addKeyListener(this);
	__databaseNamesJComboBox.addItemListener(this);
	// Set to the longest available ODBC DSN so switching between remote and
	// local does not hide components...
	__databaseNamesJComboBox.setPrototypeDisplayValue(longest_odbc);
   	JGUIUtil.addComponent(northWJPanel, __databaseNamesJComboBox,
		1, y, 1, 1, 0, 0, RTB_insets, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
	__odbcDSNsJLabel.setVisible(false);
	__odbcDSNJComboBox.setVisible(false);
	
	if (__showModels) {
		JGUIUtil.addComponent(northWJPanel,	new JLabel("Model (for menu configuration):"),
		0, ++y, 1, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.EAST);
		// Always add the full list of choices...
		__modelJComboBox = new SimpleJComboBox(false);	// Not editable
		__modelJComboBox.addItem(__MODEL_StateCU);
		__modelJComboBox.addItem(__MODEL_StateMod);
		__modelJComboBox.select(__MODEL_StateCU);
		__modelJComboBox.setToolTipText ( "Menus will be configured appropriate for the model.  "
				+ "Use the File menu to switch between models.");
		__modelJComboBox.addItemListener(this);
   		JGUIUtil.addComponent(northWJPanel, __modelJComboBox, 
			1, y, 1, 1, 0, 0, RTB_insets, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	}	
	
	if (__showWaterDivisions) {
   		JGUIUtil.addComponent(northWJPanel,	new JLabel("Water Division (2):"),
		0, ++y, 1, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.EAST);
	}

	// Always add the full list of choices...
	__waterDivisionJComboBox = new SimpleJComboBox();
	__waterDivisionJComboBox.addKeyListener(this);
	__waterDivisionJComboBox.addItem(DIV1);
	__waterDivisionJComboBox.addItem(DIV2);
	__waterDivisionJComboBox.addItem(DIV3);
	__waterDivisionJComboBox.addItem(DIV4);
	__waterDivisionJComboBox.addItem(DIV5);
	__waterDivisionJComboBox.addItem(DIV6);
	__waterDivisionJComboBox.addItem(DIV7);
	__waterDivisionJComboBox.addItem(DIV_DEFAULT);
	
	if (__showWaterDivisions) {
   		JGUIUtil.addComponent(northWJPanel, __waterDivisionJComboBox, 
		1, y, 1, 1, 0, 0, RTB_insets, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	}
	
	try {	
		// For now select the default - in the future pass in the
		// default from the application in a constructor property.
		// TODO (JTS - 2005-06-08) still interested in doing this?
		if (__defaultDiv != null) {
			__waterDivisionJComboBox.setSelectedItem(__defaultDiv);
		}
		else {
			__waterDivisionJComboBox.setSelectedItem(DIV_DEFAULT);
		}
	}
	catch (Exception e) {
		__waterDivisionJComboBox.setSelectedItem(DIV_DEFAULT);
	}
	
	// Add a listener so we can enable/disable the data source name:
	__waterDivisionJComboBox.addItemListener(this);

	// Useful instructions as footnotes.

	if (__validateLogin) {
      	JGUIUtil.addComponent(northWJPanel, new JLabel(
			"(1) Use \"guest\" for the login and password for general use."), 
			0, ++y, 2, 1, 0, 0, LR_insets, GridBagConstraints.NONE, GridBagConstraints.WEST);
       	JGUIUtil.addComponent(northWJPanel, new JLabel(
			"State of Colorado users who update HydroBase have accounts."), 
			0, ++y, 2, 1, 0, 0, LR_insets, GridBagConstraints.NONE, GridBagConstraints.WEST);
	}
	
	if (__showWaterDivisions) {
       	JGUIUtil.addComponent(northWJPanel, new JLabel(
			"(2) Select a water division to optimize map displays and menu choices."),
			0, ++y, 2, 1, 0, 0, LR_insets, GridBagConstraints.NONE, GridBagConstraints.WEST);
       	JGUIUtil.addComponent(northWJPanel, new JLabel(
			"The value may be reset if using a local database with only one division."),
			0, ++y, 2, 1, 0, 0, LR_insets, GridBagConstraints.NONE, GridBagConstraints.WEST);
	}

	// Hide the database choice if running an applet (default to remote)...

	if (IOUtil.isApplet()) {
		dataBaseJLabel.setVisible(false);
		__odbcDSNJComboBox.setVisible(false);		
		__odbcDSNsJLabel.setVisible(false);
		hostname_JLabel.setVisible(false);
		__hostnameJComboBox.setVisible(false);		
	}

	// South JPanel
	JPanel southJPanel = new JPanel();
	southJPanel.setLayout(new BorderLayout());
	getContentPane().add("South", southJPanel);

	// South North JPanel
	JPanel southNJPanel = new JPanel();
	southNJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	southJPanel.add("North", southNJPanel);

	__okJButton = new SimpleJButton(__BUTTON_OK, __BUTTON_OK,this);
	__okJButton.addKeyListener(this);
	southNJPanel.add(__okJButton);

	__cancelJButton = new SimpleJButton(__BUTTON_CANCEL, __BUTTON_CANCEL, this);
	__cancelJButton.addKeyListener(this);
	southNJPanel.add(__cancelJButton);

//	__helpJButton = new SimpleJButton("Help","Help",this);
//	__helpJButton.addKeyListener(this);
//	__helpJButton.setEnabled ( false );
//	southNJPanel.add(__helpJButton);

	// Always create the checkbox but only show it if in test mode.
	__useSPJCheckBox = new JCheckBox((String)null);
	__useSPJCheckBox.setSelected(true);
	if ( IOUtil.testing() ) {
			// Actually show the checkbox.  Otherwise it is invisible but selected.
		__useSPJCheckBox.setToolTipText("<html>Check this box to log into the "
				+ "database as the 'cdss' user.<br>Unchecking this box will "
				+ "cause the 'crdss' login to be used.</html>");
		JLabel tempLabel = new JLabel(" Use Stored Procedures:");
		tempLabel.setToolTipText("<html>Check this box to log into the "
				+ "database as the 'cdss' user.<br>Unchecking this box will "
				+ "cause the 'crdss' login to be used.</html>");
		southNJPanel.add(tempLabel);
		southNJPanel.add(__useSPJCheckBox);
		__useSPJCheckBox.addActionListener(this);
	}

	// South South JPanel
	JPanel southSJPanel = new JPanel();
	southSJPanel.setLayout(gbl);
	southJPanel.add("South", southSJPanel);

	__statusJTextField = new JTextField();
	__statusJTextField.setEditable(false);
	JGUIUtil.addComponent(southSJPanel, __statusJTextField, 
		0, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
	// frame settings        
	setTitle("Select HydroBase");
	if (__validateLogin) {
		__statusJTextField.setText( "Enter login information and select the HydroBase to open.");
	}
	else {	
		__statusJTextField.setText("Select the HydroBase to open.");
	}

	pack();
	JGUIUtil.center(this);
	setResizable(false);

	// Force the dialog fields to refresh to react to the initial settings...
	if (__defaultOdbcDsn != null) {
		__connectionJComboBox.select(__LOCAL);
		connectionSelected(__LOCAL);
	}	
	else {
		connectionSelected(connection);
	}

	setWaterDivisionFromLogin();
	__loginJTextField.requestFocus();
	__loginJTextField.selectAll();
	select(__loginJTextField);
	select(__passwordJPasswordField);
	select(__hostnameJComboBox);
	select(__odbcDSNJComboBox);	
	setVisible(true);

	addKeyListener(this);
}
   
/**
Handle item state changed events.
*/
public void itemStateChanged(ItemEvent event) {
	Object objectID = event.getItemSelectable();
		
	if (objectID == __connectionJComboBox) {
		connectionSelected(null);
		if (__connectionJComboBox.getSelectedItem().equals(__LOCAL)) {
			setWaterDivisionFromLogin();
		}
	}
	else if (objectID == __odbcDSNJComboBox) {
		if (__connectionJComboBox.getSelectedItem().equals(__LOCAL)) {
			setWaterDivisionFromLogin();
			__useSPJCheckBox.setSelected(false);
			__useSPJCheckBox.setEnabled(false);
		}
		else {
			__useSPJCheckBox.setSelected(true);
			__useSPJCheckBox.setEnabled(true);
		}
	}
	else if (objectID == __modelJComboBox) {
		__selectedModel = __modelJComboBox.getSelected();
	}
}

/**
Responds to Key Press events.
@param even the key press that happened.
*/
public void keyPressed(KeyEvent event) {
	int code = event.getKeyCode(); 
	// enter key is the same as ok
	if (code == KeyEvent.VK_ENTER) {
		if (__hostnameJComboBox.getSelected().trim().equalsIgnoreCase(__lastSelectedServer)) {
			okClicked();
		}
		else {
			findDatabaseNames();
			__lastSelectedServer=__hostnameJComboBox.getSelected();
		}
	}
	else if (code == KeyEvent.VK_ESCAPE) {
		cancelClicked();
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
Sets the OK button to enabled or disabled.
@param ok if true, the ok button is enabled.  If false, it is disabled.
*/
private void ok(boolean ok) {
	__okJButton.setEnabled(ok);
}

/**
Use the information in the dialog to try to instantiate a new HydroBaseDMI
instance.  If successful, save the new HydroBaseDMI information and close the dialog.
*/
private void okClicked() {
	String routine = "SelectHydroBaseJDialog.okClicked";

	JGUIUtil.setWaitCursor(this, true);

	__statusJTextField.setText("Connecting to database ...");
	JGUIUtil.forceRepaint(__statusJTextField);

	HydroBaseDMI hbdmi = null;
	try {		
		hbdmi = openDatabase();
	}
	catch (Exception e) {
		Message.printWarning(3, routine, e);
		// Enough messages should be in openDatabase().
		hbdmi = null;
	}
	
	if (hbdmi != null) {
		checkDatabaseVersion(hbdmi);
		
		// Save the connection.  The calling code can retrieve this to store for additional queries.
		__hbdmi = hbdmi;

		__selectedDivision = __waterDivisionJComboBox.getSelected();
		
		String div = null;
		if (__selectedDivision.equals(DIV_DEFAULT)) {
			div = DIV_DEFAULT;
		}
		else {
			// trim everything off after the DIVX (e.g., DIVX - Blah blah, trim off " - Blah blah"
			div = __selectedDivision.substring(0, 4);
		}
		
		HydroBase_GUI_Util.setActiveWaterDivision(div);
		
		JGUIUtil.setWaitCursor(this, false);
		closeDialog ();
	}
	
	// else... User has been warned about problems in openDatabase() and
	// needs to cancel or get it right.
	JGUIUtil.setWaitCursor(this, false);
}

/**
Attempt to use the information in the dialog to try to instantiate a new HydroBaseDMI instance.
@return HydroBaseDMI instance for the new connection, or null if the connection failed.
*/
private HydroBaseDMI openDatabase() 
throws Exception {
	String routine = "SelectHydroBaseJDialog.openDataBase";
	String message = null;

	// Fill information in a new HydroBaseDMI instance.  Instantiating using
	// no arguments defaults the system login and system password to the correct values.

	HydroBaseDMI hbdmi = null;

	// Save this information in case it is needed elsewhere.

	String OdbcDsn =((String)__odbcDSNJComboBox.getSelectedItem()).trim();
	String host = ((String)__hostnameJComboBox.getSelected()).trim();
	String databaseName = ((String)__databaseNamesJComboBox.getSelected()).trim();

	// Sometimes due to an older configuration file the following will occur:
	if (host.equalsIgnoreCase("local")) {
		host = IOUtil.getProgramHost();
	}

	if (__connectionJComboBox.getSelectedItem().equals(__LOCAL)) {
		// Local database.  Use the specified ODBC name.
		hbdmi = new HydroBaseDMI( "Access", OdbcDsn, null, null);
	}
	else {	
		// Remote database.  Use the specified host but default the
		// other information.  The database name is defaulted internally.

		/*
		Message.printStatus(2, "", "Logging in with:");
		Message.printStatus(2, "", "  Port:     " + __detectedPort);
		Message.printStatus(2, "", "  Username: " + __detectedUsername);
		Message.printStatus(2, "", "  Password: " + __detectedPassword);
		*/

		if (!__useSPJCheckBox.isSelected()) {
			hbdmi = new HydroBaseDMI("SQLServer", host, databaseName, __detectedPort,
				__detectedUsername, __detectedPassword);
		}
		else {
			hbdmi = new HydroBaseDMI("SQLServer",
				host, databaseName, __detectedPort,	__detectedUsername, __detectedPassword, true);
		}	
	}

	// Open the selected database connection

	try {	
		if (__connectionJComboBox.getSelectedItem().equals(__LOCAL)) {
             message = "Establishing local connection...";
		}
		else {	
			message = "Establishing remote connection...";
		}
		__statusJTextField.setText(message);
		hbdmi.open();
	}
	catch (Exception e) {
		Message.printWarning(2, "SelectHydrobaseJDialog.openDatabase", e);
		if (__connectionJComboBox.getSelectedItem().equals(__REMOTE)) {
			message = "Cannot connect to the remote database on \"" + host + "\"."
			+ "\nOne possible cause is that the computer is not "
			+ "connected to the Internet.  The host name may also be incorrect.";
		}
		else {	
			message = "Cannot connect to the local database.\n"
				+ "The ODBC data source name may be incorrect\n"
				+ "or the database file may not exist.";
		}
		
		Message.printWarning(1, routine, message);
		__statusJTextField.setText( "HydroBase connection failed.  Ready");
		hbdmi = null;
		return null;
	}

	// Evaluate the user login and password...

	String userLogin = "";
	String userPassword = "";
	if (__validateLogin) {
		// Validation is requested so use the the values from the dialog...
		userLogin = __loginJTextField.getText().trim();
		userPassword = new String( __passwordJPasswordField.getPassword()).trim();
		// Need to check the HydroBase "user_security" table.
		// For now just let the user login.
	}
	else {	
		// Default to guest...
		userLogin = getDefaultLogin();
		userPassword = getDefaultPassword();
	}
	
	// Note that these are not used to make the connection but can be used later for validation, etc.
	hbdmi.setUserLogin(userLogin);
	hbdmi.setUserPassword(userPassword);

	// Need to enable reading the "user_preferences" table to set user preferences after login.

	return hbdmi;
}

/**
Reads the configuration file.
*/
private void readConfigurationFile() {
	__configurationProps = new PropList("Config");
	__configurationProps.setPersistentName(	IOUtil.getApplicationHomeDir() +
		File.separator + "system" + File.separator + "CDSS.cfg");
	try {
		__configurationProps.readPersistent();
	}
	catch (Exception e) {
		// ignore -- probably a file not found error, in which 
		// the result is the same as an empty file: an empty proplist.
	}

	String serverNames = __configurationProps.getValue( "HydroBase.ServerNames");
	if (serverNames == null) {
		__serverNames = new Vector();
		if (IOUtil.testing()) {
			__serverNames.add("hbserver");
		}
		else {
			__serverNames.add("greenmtn.state.co.us");
		}

		// TODO SAM 2009-05-21 Why is the following a "not"
		// if SQL Server is running locally (eg, MSDE), 
		// add the local machine to the list of servers that can be connected to.
		if (!IOUtil.isPortOpen(5758) || !IOUtil.isPortOpen(21784)) {
			__serverNames.add("local");
		}
	}
	else {	
		__serverNames = StringUtil.breakStringList(serverNames, ",", 0);
	}

	int size = __serverNames.size();
	String s = null;
	for (int i = 0; i < size; i++) {
		s = (String)__serverNames.get(i);
		if (s.equalsIgnoreCase("local")) {
			s = IOUtil.getProgramHost();
		}
		s = s.toLowerCase().trim();
		__serverNames.set(i,s);
	}

	__serverNames = StringUtil.sortStringList(__serverNames);

	String defaultServerName = __configurationProps.getValue( "HydroBase.DefaultServerName");
	if (defaultServerName == null) {
		if (IOUtil.testing()) {
			__defaultServerName = "hbserver";
		}
		else {
			__defaultServerName = "greenmtn.state.co.us";
		}
	}
	else {
		__defaultServerName = defaultServerName;
	}

	String defaultDatabaseName = __configurationProps.getValue( "HydroBase.DefaultDatabaseName");
	if (defaultDatabaseName == null) {
		__defaultDatabaseName = __HYDROBASE;
	}
	else {
		__defaultDatabaseName = defaultDatabaseName;
	}
}

/**
Used when tabbing between fields to select the text in a JTextField.
@param textField the text field in which to select text.
*/
private void select(JTextField textField) {
	textField.setCaretPosition(0);
	textField.selectAll();
}

/**
Used when tabbing between fields to select the text in a JPasswordField.
@param passwordField the passwordField in which to select all the text.
*/
private void select(JPasswordField passwordField) {
	passwordField.setCaretPosition(0);
	passwordField.selectAll();
}

/**
Used when tabbing between fields to select the text in a Combo Box.
@param comboBox the combo box in which to select all the text.
*/
private void select(SimpleJComboBox comboBox) {
	if (!comboBox.isEditable()) {
		return;
	}
	((JTextComponent)(comboBox.getEditor()).getEditorComponent()).setCaretPosition(0);
	((JTextComponent)(comboBox.getEditor()).getEditorComponent()).selectAll();
}

/**
Sets the default ODBC DSN that will be selected when the dialog is opened.
If the default ODBC DSN is not null, then when this dialog opens it will
be set up to make a local connection with the default ODBC DSN set as the default selection.
@param defaultOdbcDsn the default ODBC DSN to connect to.
*/
public static void setDefaultOdbcDsn(String defaultOdbcDsn) {
	__defaultOdbcDsn = defaultOdbcDsn;
}

/**
Sets the number of the division that will be defaulted to whenever possible.
@param div the number of the  division to be selected.
*/
public static void setDefaultDiv(String div) {
	try {
		int i = (Integer.decode(div)).intValue();
		switch (i) {
			case 1:
				__defaultDiv = "DIV1 - South Platte";
				break;
			case 2:
				__defaultDiv = "DIV2 - Arkansas";
				break;
			case 3:
				__defaultDiv = "DIV3 - Rio Grande";
				break;
			case 4:
				__defaultDiv = "DIV4 - Gunnison";
				break;
			case 5:
				__defaultDiv = "DIV5 - Colorado";
				break;
			case 6:
				__defaultDiv = "DIV6 - Yampa/White";
				break;
			case 7:
				__defaultDiv = "DIV7 - San Juan";	
				break;
			default:
				__defaultDiv = null;
				break;
		}
	}
	catch (Exception e) {
		__defaultDiv = null;
	}
}

/**
Select the water division based on the login information.  If a remote
database is selected, the default water division is selected.  If a local
database is selected, the ODBC DSN in searched for an indication of which division is being accessed.
*/
private void setWaterDivisionFromLogin() {
	if (__connectionJComboBox.getSelectedItem().equals(__REMOTE)) {
		if (__defaultDiv != null) {
			__waterDivisionJComboBox.setSelectedItem(__defaultDiv);
		}
		else {
			if (__selectedDivision != null) {
				__waterDivisionJComboBox.setSelectedPrefixItem(__selectedDivision);
			}
			else {
				__waterDivisionJComboBox.setSelectedItem( DIV_DEFAULT);
			}
		}
	}
	else {	
		// Local database.  See if the data source name has any indication of the water division.
		String dsn = __odbcDSNJComboBox.getSelected().trim();
		String water_division = DIV_DEFAULT;
		if (StringUtil.indexOfIgnoreCase(dsn,"Div1",0) >= 0) {
			water_division = DIV1;
		}
		else if (StringUtil.indexOfIgnoreCase(dsn,"Div2",0) >= 0) {
			water_division = DIV2;
		}
		else if (StringUtil.indexOfIgnoreCase(dsn,"Div3",0) >= 0) {
			water_division = DIV3;
		}
		else if (StringUtil.indexOfIgnoreCase(dsn,"Div4",0) >= 0) {
			water_division = DIV4;
		}
		else if (StringUtil.indexOfIgnoreCase(dsn,"Div5",0) >= 0) {
			water_division = DIV5;
		}
		else if (StringUtil.indexOfIgnoreCase(dsn,"Div6",0) >= 0) {
			water_division = DIV6;
		}
		else if (StringUtil.indexOfIgnoreCase(dsn,"Div7",0) >= 0) {
			water_division = DIV7;
		}
		if (__showWaterDivisions) {
			if (__defaultDiv != null) {
				__waterDivisionJComboBox.setSelectedItem(__defaultDiv);		
			}
			else {
				__waterDivisionJComboBox.setSelectedItem ( water_division );
			}
		}
	}
}

/**
Whether the user pressed cancel to close the dialog or not.
@return true if the user pressed cancel to close the dialog.
*/
public boolean wasCancelled() {
	return __cancelled;
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
Cancels the dialog login.
*/
public void windowClosing(WindowEvent event) {
	cancelClicked();
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