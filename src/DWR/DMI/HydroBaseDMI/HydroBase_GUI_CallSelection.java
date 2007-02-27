//------------------------------------------------------------------------------
// HydroBase_GUI_CallSelection - JDialog with a multiple selection list to 
//	select the
//	call chronology records for graphing. Available records are defined
//	according to the time interval which is displayed here, but defined
//	in the HBCallChronologyGUI;
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// History:
//
// 10 Jun 1998	Chad G. Bierbaum , RTi	Created initial class description.
// 04 Apr 1999	Steven A. Malers, RTi	Changed deprecated JList.addItem()call
//					to add().
// 12 Apr 1999	SAM, RTi		Change HourTS to DayTS.  This should
//					significantly improve performance
//					because Chad had a 1-hour time series!
//					If the State decides to allow more than
//					one call per day, go back to HourTS.
//					Actually had to rework quite a bit.
//					Chad was processing all the calls up
//					front regardless if they are being
//					plotted.  This can significantly impact
//					performance.  Change so the time series
//					are only processed if selected.
//					Change precision on dates to days.
//					Change from dialog to frame so windows
//					can be layered better.
// 15 May 2001	SAM, RTi		Update to new RTi graphing package, but
//					do not make final until a better period
//					of record plot can be enabled.
//					Add finalize().  Change GUI to GUIUtil.
//					Fix bug where some administration
//					numbers for the graph were not being
//					found because the double admin numbers
//					did not equate.  Enable the key listener
//					and Microsoft-style mouse events.
//					Remove "Select All" button.
// 2001-11-01	SAM, RTi		Use RTi graphing for calls.  Add more
//					code to clean up memory.  Get rid of
//					commented-out code to use MultiJList.
//					In-line some code that is called only
//					once.  Change so senior calls are
//					numbered with the smaller number now
//					that the period of record graph handles
//					the time series order.  Change so if a
//					warning occurs, the message is shown
//					using the Call Chronology GUI as the
//					parent Frame.
// 2002-02-20	SAM, RTi		Update to use TSViewFrame instead of
//					TSViewGUI.
// 2002-02-25	SAM, RTi		Change the status text area to have gray
//					background.
//------------------------------------------------------------------------------
// 2003-03-31	J. Thomas Sapienza, RTi	Initial swing version from old code.
// 2003-04-02	JTS, RTi		* Put the list within a scrollpane.
//					* Was displaying the wrong name and
//					  admin number data; fixed.
//					* Misc other bugfixes and documentation
//					  done.
// 2003-04-04	JTS, RTi		Changed GUIUtil to JGUIUtil.
// 2003-12-09	JTS, RTi		Uncommented and enabled time series
//					code.
// 2004-01-12	SAM, RTi		* Set the title to use the app name.
//					* Comment out the help button.
//					* Add tool tips for the buttons.
// 2005-04-12	JTS, RTi		MutableJList changed to SimpleJList.
// 2005-04-27	JTS, RTi		Added all data members to finalize().
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.GRTS.TSViewJFrame;

import RTi.TS.DayTS;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJList;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.TimeInterval;

/**
JDialog with a multiple selection list to select the
call chronology records for graphing.  Available records are defined
according to the time interval which is displayed here, but defined
in the HBCallChronologyGUI.
*/
public class HydroBase_GUI_CallSelection 
extends JDialog
implements ActionListener, KeyListener, WindowListener {

/**
The GUI that opened this one.
*/
private HydroBase_GUI_CallsQuery __parent = null;

/**
The label for the form's list.
*/
private	JLabel __listJLabel;

/**
Textfield for holding the from date.
*/
private JTextField __fromJTextField;
/**
Status bar.
*/
private JTextField __statusJTextField;
/**
Textfield for holding the to date.
*/
private JTextField __toJTextField;

/**
The list in which data is displayed.
*/
private SimpleJList __list;

/**
Label for the cancel button.
*/
private final String __BUTTON_CANCEL = 		"Cancel";
/**
Label for the graph button.
*/
private final String __BUTTON_GRAPH = 		"Graph";
/**
Label for the help button.
*/
private final String __BUTTON_HELP = 		"Help";

/**
Vector that holds the results that are displayed.
*/
private Vector __results = null;

/**
HydroBase_GUI_CallSelection constructor
*/
public HydroBase_GUI_CallSelection(HydroBaseDMI dmi, 
HydroBase_GUI_CallsQuery parent, Vector results) {
	// don't want a modal dialog
	super (parent, false);

	__parent = parent;
	__results = results;
	setupGUI();
}

/**
Responds to ActionEvents
@param evt ActionEvent object.
*/
public void actionPerformed(ActionEvent evt) {
	String s = evt.getActionCommand();
	if (s.equals(__BUTTON_CANCEL)) {
		closeClicked();
	}
	else if (s.equals(__BUTTON_GRAPH)) {
		graphClicked();
	}
	else if (s.equals(__BUTTON_HELP)) {
	}
}

/**
This function is responsible for closing the GUI.  Overrides the super class.
*/
protected void closeClicked() {
	dispose();
	setVisible(false);
}

/**
Clean up for garbage collection.
*/
protected void finalize()
throws Throwable {
	__parent = null;
	__listJLabel = null;
	__fromJTextField = null;
	__statusJTextField = null;
	__toJTextField = null;
	__list = null;
	__results = null;

	super.finalize();
}

/**
This function initializes a TS object for the specified call.
@param wd Water district for call of interest.
@param id Structure identifier for call of interest.
@param adminNum Administration number for call of interest.
@param value Value to fill time series for graphing.
@return a time series with values "value" for days that the call is in
effect or null if the data cannot be found.
*/
private DayTS getTimeSeries(int wd, int id, double adminNum, double value) {
	String routine = "HydroBase_GUI_CallSelection.getTimeSeries";

	int size = __results.size();

	if (Message.isDebugOn) {
		Message.printDebug(1, routine,
			"Getting call time series for " + wd + " " + id 
			+ " " + adminNum);
	}

	// The calls are ordered by admin number and date so if the admin
	// number is the same we are dealing with the same time series.
	// Locate the calls for the time series of interest.
	HydroBase_Calls call = null;
	boolean found = false;
	int i;
	// The following check does not work since non-zero admin numbers have
	// been converted to/from strings.  Convert to strings for the
	// comparison...
	String dataStringAdminNum = null;
	String stringAdminNum = StringUtil.formatString(adminNum,"%11.5f");
	for (i = 0; i < size; i++) {
		call = (HydroBase_Calls)__results.elementAt(i);
		dataStringAdminNum = StringUtil.formatString(
			call.getAdminno(), "%11.5f");
		if ((call.getWD()== wd) && (call.getID()== id)
			&& stringAdminNum.equals(dataStringAdminNum)) {
			found = true;
			break;
		}
	}

	if (found == false) {
		// Did not find the data...
		Message.printWarning(2, routine, "Did not find data for call.");
		return null;
	}

	// Set up default dates from the text field.  These are originally
	// passed in from the HBCallChronologyGUI...
	DateTime from = null;
	DateTime to = null;

	try {	
		from = DateTime.parse(__fromJTextField.getText(),
                            DateTime.FORMAT_YYYY_MM_DD);

		to = DateTime.parse(__toJTextField.getText(),
                            DateTime.FORMAT_YYYY_MM_DD);
	} catch (Exception e) {
		Message.printWarning(2, routine,
			"Exception parsing dates.  Default to current date.");
		from = new DateTime(DateTime.PRECISION_DAY |
				DateTime.DATE_CURRENT);
		from.addDay(-2);
		to = new DateTime(DateTime.PRECISION_DAY |
				DateTime.DATE_CURRENT);
	}

	// Initialize the time series...

	DayTS ts = null;
	try {
	ts = new DayTS();
	// Always set the period to equal the user selected period.  Fill in the
	// data later based on calls.
	ts.setDate1(new DateTime(from));
	ts.setDate2(new DateTime(to));
	ts.setDataUnits("BOOLEAN");
	ts.setDataInterval(TimeInterval.DAY, 1);
	ts.allocateDataSpace();
	ts.setLocation(call.getStrname());
	ts.setDescription(call.getStr_name());
//	ts.setDescription(StringUtil.formatString(call.getAdminno(), 
//		"%11.5f")+ ", " + HydroBase_WaterDistrict.formWDID(wd,id));

	// i set above.  Loop through the sorted call data until the WD, ID,
	// and Admin Number do not match the requested value.  Do admin number
	// comparisons based on strings because the round-off may cause a
	// number to not agree when compared as double...
	DateTime endDate = null;
	DateTime startDate = null;
	for (; i < size; i++) {
		call = (HydroBase_Calls)__results.elementAt(i);
		dataStringAdminNum = StringUtil.formatString(
			call.getAdminno(),"%11.5f");
		if ((call.getWD() != wd) || (call.getID() != id)
			|| !dataStringAdminNum.equals(stringAdminNum)) {
			// Different so must be different call...
			break;
		}
		// Now fill in the active records for the calls data.  To
		// increase performance, only process dates that are within
		// the graph period.
    		if (DMIUtil.isMissing(call.getDate_time_set())) {
			// Problem here. We need to set some warning.
			Message.printWarning(1, routine,
				"Error.  Null set date for call.  Skipping.");
			continue; 
       		}
		startDate = new DateTime(call.getDate_time_set(),
			DateTime.PRECISION_DAY);
		if (startDate.greaterThan(to)) {
			// Past end of period so no need to process...
			continue;
		}
		else if (startDate.lessThan(from)) {
			// Start date is less than the from date.  Just set it
			// to the from date...
			startDate = new DateTime(from);
		}
		// Now set the end date.  If the call is active or the end date
		// is in the future, the end date is "to".  If the end date
		// is before "from" no need to process.  Otherwise the end
		// date is OK.
	        if (DMIUtil.isMissing(call.getDate_time_released())) {
			// Assume that we are still active for this call
                	endDate = new DateTime(to);
       		}
		else {	
			endDate = new DateTime(call.getDate_time_released(),
				DateTime.PRECISION_DAY);
			if (endDate.lessThan(from)) {
				continue;
			}
			else if (endDate.greaterThan(to)) {
				endDate = new DateTime(to);
			}
		}
		// Now fill in the time series for the period where the call
		// was in effect...
		while (startDate.lessThanOrEqualTo(endDate)) {
			ts.setDataValue(startDate, value);
			startDate.addInterval(TimeInterval.DAY, 1);
		}
	}
	startDate = null;
	endDate = null;
	}
	catch (Exception e) {
		Message.printWarning(2, routine, "Error getting call "
			+ "time series.");
		Message.printWarning(2, routine, e);
		return null;
	}

	return ts;
}

/**
This function formats a HydroBase_Calls object for the JList. 
@param call the HydroBase_Calls object.
@param count Count to use for label
@return returns a formated String to add to the appropriate JList component.
*/
private String getJListString(HydroBase_Calls call, int count) {
        String listString = "";
 
 	String wds = "";
        int wdi = call.getWD();
        if (!DMIUtil.isMissing(wdi)) {
		wds = "" + wdi;
        }

	String ids = "";
        int idi = call.getID();
        if (!DMIUtil.isMissing(idi)) {
		ids = "" + idi;
        }

	// Make sure the commas are included above the field width so they
	// don't get clobbered but still immediately follow the field...
        listString += HydroBase_WaterDistrict.formWDID(wds, ids)+ ", ";
        listString += "(" + StringUtil.formatString(count,"%3d")+ ")" +
			StringUtil.formatString(call.getStr_name().trim()
			+ ", ", "%-26.26s");
	listString += StringUtil.formatString(call.getDcr_amt().trim()
			+ ", ", "%-20.20s");
	listString += StringUtil.formatString(call.getAdminno(), "%11.5f");
        return listString;
}

/**
This function generates the graph.
*/
private void graphClicked() {
	String	function = "HydroBase_GUI_CallSelection.graphClicked";

	Object[] objects = __list.getSelectedValues();
	int size = objects.length;
	String[] strings = new String[size];
	for (int i = 0; i < size; i++) {
		strings[i] = (String)objects[i];
	}

	if (size == 0) {
		Message.printWarning(1, function, "You must select one or "
			+ "more calls.", __parent);
		return;
	}

	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Please Wait... Displaying View");

	// add the TS objects to a vector for displaying and update
	Vector tsVector = new Vector(size);

	int value = 1;
	Vector tokens = null;
	int[] wdid_tokens = null;
	double adminNum;

	// The list processed here has already been sorted with senior calls
	// first...
	PropList tsview_props = new PropList("tsview");
	for (int i = 0; i < size; i++) {
		try {
		// Split out the fields that we need to look up the time
		// series...
		tokens = StringUtil.breakStringList(strings[i], ",", 0);
		wdid_tokens = HydroBase_WaterDistrict.parseWDID(
			(String)tokens.elementAt(0));
		adminNum = StringUtil.atod(
			((String)tokens.elementAt(tokens.size()- 1)).trim());
		// With the RTi graphing, it automatically puts the time series
		// in the right order for a period graph so use "1" as the data
		// value, indicating the call is on.
		DayTS ts = getTimeSeries(wdid_tokens[0], 
			wdid_tokens[1], adminNum, value);
		if (ts == null) {
			Message.printWarning(1, function,
				"Error setting up time series for \"" 
				+ strings[i] + "\".  Not graphing.");
			continue;
		}
		if (Message.isDebugOn) {
			Message.printDebug(10, function,
			 	"Using new calls time series for list item: "
				+ strings[i]);
		}

		// update the legends
		String legend = ts.getDescription();
		legend += ", ";
		legend += ts.getLocation();
		ts.setLegend(legend);
		legend = null;

		tsVector.addElement(ts);
		// Set the symbol to be a plus size of 1 pixel to force the
		// line to be seen.
		tsview_props.set("Data 1." + tsVector.size()
			+ ".SymbolStyle", "Plus");
		tsview_props.set("Data 1." + tsVector.size()
			+ ".SymbolSize", "3");
		}
		catch (Exception e) {
			Message.printWarning(1, function,
				"Error setting up time series for \"" 
				+ strings[i] + "\".  Not graphing.");
			Message.printWarning(2, function, e);
			return;
		}
	}

	try {	
		// Set properties common to all views...
		tsview_props.set("CalendarType", "WaterYear");
		tsview_props.set("TotalWidth", "750");
		tsview_props.set("TotalHeight", "550");
		tsview_props.set("DisplayFont", "Courier");
		tsview_props.set("DisplaySize", "11");
		tsview_props.set("PrintFont", "Courier");
		tsview_props.set("PrintSize", "7");
		tsview_props.set("PageLength", "100");
		tsview_props.set("TitleString", "Call Chronology");
		tsview_props.set("TSViewTitleString", "Call Chronology");
		tsview_props.set("UseCommentsForHeader", "true");
		// Now display the correct initial view...
		tsview_props.set("InitialView", "Graph");
		tsview_props.set("GraphType", "PeriodOfRecord");
		tsview_props.set("EnableSummary", "false");
		// Need to spend time on this later getting it to display
		// symbols...
		tsview_props.set("EnableReferenceGraph", "false");
		new TSViewJFrame(tsVector, tsview_props);
		tsview_props = null;
	}
	catch (Exception e) {
		Message.printWarning(1, function,
		"Error graphing call chronology.");
		Message.printWarning(2, function, e);
	}

	ready();
}

/**
Respond to key pressed; does nothing.
@param event the KeyEvent that happened.
*/
public void keyPressed(KeyEvent event) {}

/**
Respond to key released events.
@param event the KeyEvent that happened.
*/
public void keyReleased(KeyEvent event) {
	int code = event.getKeyCode();

	if (code == KeyEvent.VK_ENTER) {
		// enter key in "is" field has same effect as "Graph"
		// button
		graphClicked();
	}
}

/**
Respond to key typed events; does nothing.
@param event the KeyEvent that happened.
*/
public void keyTyped(KeyEvent event) {}

/**
Reset the GUI for user interaction.
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

	// the following are used in the GUI layout
        Insets insetsNLNR = new Insets(0,0,0,0);
	GridBagLayout gbl = new GridBagLayout();

        // Top JPanel
       	JPanel topJPanel = new JPanel();
        topJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
       	getContentPane().add("North", topJPanel);

        topJPanel.add(new JLabel("Plot Period:"));

       	__fromJTextField = new JTextField(20);
        __fromJTextField.setEditable(false);
        topJPanel.add(__fromJTextField);
  
       	topJPanel.add(new JLabel("to"));

        __toJTextField = new JTextField(20);
       	__toJTextField.setEditable(false);
       	topJPanel.add(__toJTextField);

        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(gbl);
        getContentPane().add("Center", centerJPanel);

	int y = 0;
        JGUIUtil.addComponent(centerJPanel,
        	new JLabel("Information below includes the WDID, " +
		"(legend index) structure name, decree,"),
		1, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        JGUIUtil.addComponent(centerJPanel,
        	new JLabel("and administration number."),
		1, ++y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __listJLabel = new JLabel();
        JGUIUtil.addComponent(centerJPanel, __listJLabel, 
		1, ++y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __list = new SimpleJList();
	__list.setFont(new Font("Courier", Font.PLAIN, 11));
	__list.addKeyListener(this);

        JGUIUtil.addComponent(centerJPanel, new JScrollPane(__list), 
		1, ++y, 1, 1, 1, 1, insetsNLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

	JPanel southJPanel = new JPanel();
	southJPanel.setLayout(new BorderLayout());
	getContentPane().add("South", southJPanel);

	JPanel buttonJPanel = new JPanel();
	buttonJPanel.setLayout(new FlowLayout());
	southJPanel.add("North", buttonJPanel);

	SimpleJButton b = new SimpleJButton(__BUTTON_GRAPH, this);
	b.setToolTipText("Graph the calls for selected structures and rights.");
	buttonJPanel.add(b);
	b = new SimpleJButton(__BUTTON_CANCEL, this);
	b.setToolTipText ( "Cancel the graph." );
	buttonJPanel.add(b);
	
	JPanel statusJPanel = new JPanel();
	statusJPanel.setLayout(gbl);
	southJPanel.add("South", statusJPanel);
	
	__statusJTextField = new JTextField(
		"Select the calls to graph from the list.");
	__statusJTextField.setEditable(false);
	JGUIUtil.addComponent(statusJPanel, __statusJTextField,
		0, 0, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
	if (	(JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( "Call Chronology Data - Select Calls for Graph" );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() +
		" - Call Chronology Data - Select Calls for Graph" );
	}
	setResizable(false);

	// Set the date JTextFields;
	__fromJTextField.setText(__parent.getFromDate().toString(
		DateTime.FORMAT_YYYY_MM_DD));
	__toJTextField.setText(__parent.getToDate().toString(
		DateTime.FORMAT_YYYY_MM_DD));

	// Set the unique items in the list(defined by unique wd, id, and
	// admin number)...

	HydroBase_Calls call = null;
	int size = 0;
	int wd = 0;
	int id = 0;
	double adminNum = 0.0;
	if (__results != null) {
		size =__results.size();
	}
	// Do the following once to get a count on the number of calls for use
	// in the legends...
	for (int i = 0; i < size; i++) {
		call = (HydroBase_Calls)__results.elementAt(i);
		if ((call.getWD()!= wd) || (call.getID()!= id) ||
		    (call.getAdminno() != adminNum)) {
			// New record...
			// Save for next check...
			wd = call.getWD();
			id = call.getID();
			adminNum = call.getAdminno();
		}
	}
	// Now actually process for the list.  Put the most senior at the
	// top(process the list backwards)...
	wd = 0;
	id = 0;
	adminNum = 0.0;
	for (int i = 0; i < size; i++) {
		call = (HydroBase_Calls)__results.elementAt(size - i - 1);
		if ((call.getWD()!= wd) || (call.getID()!= id) ||
	            (call.getAdminno() != adminNum)) {
			// New record...
			__list.add(getJListString(call, (i + 1)));
			// Save for next check...
			wd = call.getWD();
			id = call.getID();
			adminNum = call.getAdminno();
		}
	}

	__listJLabel.setText("Rights that have call history, sorted by "
		+ "adminstration number (" + __list.getItemCount() 
		+ " records returned):");
	
	pack();
	setSize(550, 350);
	JGUIUtil.center(this);
	setVisible(true);
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
Responds to window closing events; closes the gui.
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
