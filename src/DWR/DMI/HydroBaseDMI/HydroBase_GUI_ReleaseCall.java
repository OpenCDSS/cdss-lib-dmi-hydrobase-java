//-----------------------------------------------------------------------------
// HydroBase_GUI_ReleaseCall - GUI to assist in releasing a call.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History: 
//
// 22 Apr 1998	DLG, RTi		Created initial class description
// 04 Apr 1999	Steven A. Malers, RTi	Add HBDMI to queries.
// 21 May 1999	SAM, RTi		Remove HBData.TIME_ZONE_DATUM reference
//					and clean up code some.
// 2001-11-12	SAM, RTi		Change GUI to GUIUtil.  Don't use static
//					for private strings.
// 2002-03-02	SAM, RTi		Add more call information to the dialog
//					so it is easier to see what is being
//					released.  Set the date textfield to
//					lightgray.  Change the "Archive" button
//					to OK.
//------------------------------------------------------------------------------
// 2003-03-31	J. Thomas Sapienza, RTi	Initial Swing version.
// 2003-04-04	JTS, RTi		Changed GUIUtil to JGUIUtil.
// 2004-07-13	JTS, RTi		Date time precision for the release
//					date is now to minutes, not seconds.
// 2005-02-14	JTS, RTi		Converted all queries to use stored
//					procedures.
// 2005-02-23	JTS, RTi		readCallsListForCall_num() was changed
//					to readCallsForCall_num().
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

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
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.DateTimeBuilderJDialog;

/**
GUI to assist in releasing a call.
*/
public class HydroBase_GUI_ReleaseCall 
extends JFrame 
implements ActionListener, WindowListener {

/**
Reference to a dmi
*/
private HydroBaseDMI __dmi = null;

/**
The HydroBase_GUI_CallsQuery object that opened this gui.
*/
private HydroBase_GUI_CallsQuery __parent = null;

/**
The call being operated on.
*/
private HydroBase_Calls __call = null;

/**
The number of the call.
*/
private int __callNum = DMIUtil.MISSING_INT;

/**
Release comment text field.
*/
private JTextField __releaseCommentJTextField = null;
/**
Release date text field.
*/
private JTextField __releaseDateJTextField = null;
/**
Status bar.
*/
private	JTextField __statusJTextField = null;

/**
Prop list for date.
*/
private PropList __dateProps = null;

/**
Label for cancel button.
*/
private final String __BUTTON_CANCEL = "Cancel";
/**
Label for help button.
*/
private final String __BUTTON_HELP = "Help";
/**
Label for OK button.
*/
private final String __BUTTON_OK = "OK";
/**
Label for release date button.
*/
private final String __BUTTON_RELEASE_DATE = 	"Set Release Date/Time";

/**
HydroBase_GUI_ReleaseCall constructor.
@param dmi HydroBaseDMI object
@param parent HydroBase_GUI_CallsQuery object that opened this GUI
@param callNum unique call number to release
@param callData Full HydroBase_Calls instance.
*/
public HydroBase_GUI_ReleaseCall(HydroBaseDMI dmi, 
HydroBase_GUI_CallsQuery parent, int callNum, HydroBase_Calls callData) {
	__dmi = dmi;
	__parent = parent;
	__callNum = callNum;
	__call = callData;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();
}

/**
Reponds to ActionEvents
@param evt ActionEvent object
*/
public void actionPerformed(ActionEvent evt) {
	String s = evt.getActionCommand();

	if (s.equals(__BUTTON_OK)) {
		releaseCallClicked();
	}
	else if (s.equals(__BUTTON_CANCEL)) {
		closeClicked();
	}
	else if (s.equals(__BUTTON_HELP)) {
	}
	else if (s.equals(__BUTTON_RELEASE_DATE)) {
		releaseDateClicked();
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
Cleans up.
*/
public void finalize() 
throws Throwable {
	__call = null;
	__releaseCommentJTextField = null;
	__releaseDateJTextField = null;
	__statusJTextField = null;
	__dateProps = null;
	super.finalize();
}

/**
Responds to the __releaseDate_Button ACTION_EVENT and instantiates a 
DateTimeBuilderJDialog object
*/
private void releaseDateClicked() {
	String 	routine = "HydroBase_GUI_ReleaseCall.releaseDateClicked()";
	DateTime from = null;

	try {	
		from = DateTime.parse(__releaseDateJTextField.getText(),
                            DateTime.FORMAT_YYYY_MM_DD_HH_mm);
	}
	catch (Exception e) {
		Message.printWarning(2, routine,
			"Exception parsing date.");
		from = new DateTime(DateTime.PRECISION_MINUTE);
	}
	new DateTimeBuilderJDialog(this, __releaseDateJTextField, 
			from, __dateProps);
}

/**
Responds to the __release_Button ACTION_EVENT and updates a record in the calls 
table by releasing the call
*/
private void releaseCallClicked() {
	String routine = "HydroBase_GUI_ReleaseCall.releaseCallClicked";

	DateTime archiveDate = null;
	try {
		archiveDate = new DateTime(DateTime.DATE_CURRENT);
		archiveDate.setPrecision(DateTime.PRECISION_MINUTE);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Unable to form archive "
			+ "date.");
		Message.printWarning(1, routine, e);
		return;
	}

	// ensure that the release date is not greater than the set date
	DateTime releaseDate = null;
	
	try {
		releaseDate = DateTime.parse(__releaseDateJTextField.getText(),
			DateTime.FORMAT_YYYY_MM_DD_HH_mm);
		releaseDate.setPrecision(DateTime.PRECISION_MINUTE);
	}
	catch (Exception e) {}

	DateTime setDate = new DateTime(__call.getDate_time_set());
	if (releaseDate.lessThan(setDate)) {
		new ResponseJDialog(this, 
			"Release Date too Early",
			"Release date cannot be less"
			+ " than the set date.", ResponseJDialog.OK).response();
		return;
	}

	// check for the presence of single quotes
	if (__releaseCommentJTextField.getText().indexOf("'") > -1) {
		new ResponseJDialog(this, "Cannot have single quotes "
				+ "in the Release Comments. ",
				ResponseJDialog.OK);
		return;
	}
	 
	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Please Wait...Releasing Call");
	
	DateTime relDateTime = null;
	try {
		relDateTime = DateTime.parse(__releaseDateJTextField.getText(),
				DateTime.FORMAT_YYYY_MM_DD_HH_mm);
	}
	catch (Exception e) {;}

	DateTime dateTimeReleased = null;
	try {
		dateTimeReleased = new DateTime(relDateTime);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Unable to form released "
			+ "date.");
		Message.printWarning(1, routine, e);
		ready();
		return;
	}
	
	String release_comments = __releaseCommentJTextField.getText().trim();
	if (release_comments.length() == 0) {
		release_comments = " ";
	}

	try {
      	__dmi.updateCallsDateTimeReleasedReleaseCommentsArchiveDateForCall_num(
		dateTimeReleased, release_comments, archiveDate, __callNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Unable to release call.");
		Message.printWarning(1, routine, e);
	}

	__parent.submitQuery(false);

	// If we are past the defined time than reset in the
	// CallChronologyGUI relDateTime
	DateTime curDate;
	curDate =  __parent.getToDate();
	if (curDate != null) {
		if (relDateTime.greaterThan(curDate)) {
			__parent.setToDate(relDateTime);
		}
	}

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
Sets up the GUI.
*/
private void setupGUI() {
	String routine = "HydroBase_GUI_ReleaseCall.setupGUI";
	addWindowListener(this);

	// objects used throughout the GUI layout
	Insets insetsTLNR = new Insets(7,7,0,7);
	Insets insetsTLNN = new Insets(7,7,0,0);
       	GridBagLayout gbl = new GridBagLayout();
	
	JPanel northJPanel = new JPanel();
	getContentPane().add("North", northJPanel);

	JPanel releaseJPanel = new JPanel();
	releaseJPanel.setLayout(gbl);
	northJPanel.add("West", releaseJPanel);
	
	int y = 0;
	JGUIUtil.addComponent(releaseJPanel, 
		new JLabel("Structure: " + __call.getStrname()),
		0, y, 3, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	Date date = __call.getApro_date();
	String dateString = "";
	if (!DMIUtil.isMissing(date)) {
		try {
			dateString = (new DateTime(date)).toString(
				DateTime.FORMAT_YYYY_MM_DD);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Unable to create "
				+ "dateString.");
		}
	}
	JGUIUtil.addComponent(releaseJPanel, 
		new JLabel("Calling Right: " +
		StringUtil.formatString(
		__call.getAdminno(),"%11.5f")+
		", Appropriation Date=" + dateString),
		0, ++y, 3, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(releaseJPanel, 
		new JLabel("Set Comments: " + __call.getSet_comments()),
		0, ++y, 3, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	date = __call.getDate_time_set();
	dateString = "";
	if (!DMIUtil.isMissing(date)) {
		try {
			dateString = DMIUtil.formatDateTime(
				__dmi, new DateTime(date));
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Unable to create "
				+ "dateString.");
		}
	}
	JGUIUtil.addComponent(releaseJPanel, 
		new JLabel("Set Date/Time: " + dateString),
		0, ++y, 3, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(releaseJPanel, 
		new JLabel("Release Date/Time:"),
		0, ++y, 1, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__releaseDateJTextField = new JTextField(17);
	__releaseDateJTextField.setEditable(false);
	JGUIUtil.addComponent(releaseJPanel, 
		__releaseDateJTextField,
		1, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	SimpleJButton releaseDate = new SimpleJButton(__BUTTON_RELEASE_DATE, 
		this);
	releaseDate.setToolTipText("Set the time for the release.");
	JGUIUtil.addComponent(releaseJPanel, releaseDate,
		2, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.BOTH, GridBagConstraints.EAST);

	JGUIUtil.addComponent(releaseJPanel, 
		new JLabel("Release Comment:"),
		0, ++y, 1, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__releaseCommentJTextField = new JTextField(15);
	if (!DMIUtil.isMissing(__call.getRelease_comments())) {
		__releaseCommentJTextField.setText(
			__call.getRelease_comments());
	}
	JGUIUtil.addComponent(releaseJPanel, 
		__releaseCommentJTextField,
		1, y, 2, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JPanel buttonJPanel = new JPanel();
	buttonJPanel.setLayout(new FlowLayout());
	getContentPane().add("Center", buttonJPanel);
	SimpleJButton ok = new SimpleJButton(__BUTTON_OK, this);
	ok.setToolTipText("Accept values and return.");
	buttonJPanel.add(ok);
	SimpleJButton cancel = new SimpleJButton(__BUTTON_CANCEL, this);
	cancel.setToolTipText("Discard values and return.");
	buttonJPanel.add(cancel);

	JPanel southSJPanel = new JPanel();
	getContentPane().add("South", southSJPanel);
	southSJPanel.setLayout(gbl);
	__statusJTextField = new JTextField();
	__statusJTextField.setEditable(false);
	__statusJTextField.setText(
		"Please verify the release date/time and comments");
	JGUIUtil.addComponent(southSJPanel, __statusJTextField, 
		0, 0, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	pack();
	setResizable(false);

	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}
	setTitle(app + "Release Call");
	JGUIUtil.center(this);
	setVisible(true);
}

/**
Show/hides the GUI.
@param state true if showing the GUI, false otherwise
*/
public void setVisible(boolean state) {
	String routine = "HydroBase_GUI_ReleaseCall.setVisible";
	if (state) {
		// set date properties
		__dateProps=new PropList("Calls DateTimeBuilderGUI properties");
		__dateProps.set("DatePrecision", "Minute" );
		__dateProps.set("DateFormat", "Y2K");

		// get the current system time and diplay it
		__releaseDateJTextField.setText(
			(new DateTime(DateTime.DATE_CURRENT)).toString(
			DateTime.FORMAT_YYYY_MM_DD_HH_mm));

		// get the call record which corresponds to
		// the _call_num
		List whereClause = new Vector();
		whereClause.add("call_num = " + __callNum);
		try {
			__call = __dmi.readCallsForCall_num(__callNum);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Unable to read call "
				+ "from database.");
			Message.printWarning(1, routine, e);
		}
	}
	super.setVisible(state);
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
Responds to window closing events; calls closeClicked.
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
