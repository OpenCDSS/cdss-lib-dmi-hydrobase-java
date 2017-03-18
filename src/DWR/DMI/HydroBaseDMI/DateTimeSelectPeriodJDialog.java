//-----------------------------------------------------------------------------
// DateTimeSelectPeriodJDialog - GUI to assist in selecting a time period to 
//	plot.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// Notes:
//(1)Use as follows:
// 	TSLimits l = new DateTimeSelectPeriodJDialog(...).getLimits();
//	l = null if Cancel or X was selected
//	OR
//	l = TSLimits object if OK was selected
// 
//(2)PropList Use:
//	PreferredState = Maximum, Minimum, UserDefined
//	DateFormat = Y2K, US
//	DatePrecision = Year, Month, Day, Hour, Minute
//-----------------------------------------------------------------------------
// History: 
//
// 04 May 1998	DLG, RTi		Created initial class description.
// 14 May 2001	Steven A. Malers, RTi	Remove import *.  Add finalize.
//					Don't use static flags(trying to limit
//					use of static data).  Change GUI to
//					GUIUtil.  Add isMaxPeriodSelected().
// 2001-11-06	SAM, RTi		Review javadoc.  Verify that variables
//					are set to null when no longer used.
// 2002-02-25	SAM, RTi		Change so status text field background
//					is lightGray.
//-----------------------------------------------------------------------------
// 2003-03-21	J. Thomas Sapienza, RTi	Initial Swing version.
// 2003-03-27	JTS, RTi		Put the __simpleList in a JScrollPane.
//-----------------------------------------------------------------------------
// 2003-06-03	JTS, RTi		Initial Rti.Util.Time version (from
//					TSSelectPeriodJDialog in RTi.TS)
// 2004-05-05	JTS, RTi		* Time Series list now displays UNKNOWN
//					  when the year in -999.
//					* Updated some things that were still
//					  in the old AWT style.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import RTi.TS.TS;
import RTi.TS.TSLimits;
import RTi.TS.TSUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.DateTimeBuilderJDialog;

/**
The DateTimeSelectPeriodJDialog helps select a period.  It allows minimum, 
maximum, and user-defined periods to be entered.
<pre>
(1)Use as follows:
	TSLimits l = new DateTimeSelectPeriodJDialog(...).getLimits();
	l = null if Cancel or X was selected
	OR
	l = TSLimits object if OK was selected
 
(2)PropList Use:
	PreferredState = Maximum, Minimum, UserDefined
	DateFormat = Y2K, US
	DatePrecision = Year, Month, Day, Hour, Minute
*/
@SuppressWarnings("serial")
public class DateTimeSelectPeriodJDialog 
extends JDialog
implements ActionListener, ItemListener, WindowListener {

private final String __CANCEL = 	"Cancel";
private final String __OK = 		"OK";
private final String __SET_PERIOD = 	"Set Period";
private final String __MAX = 		"Maximum";
private final String __MIN = 		"Minimum";
private final String __USER = 		"UserDefined";

private	int __datePrecision;
private int __dateFormat;

private JButton	__setPeriodJButton;

private JFrame __frame;

private JRadioButton __minJRadioButton;
private JRadioButton __maxJRadioButton;
private JRadioButton __userJRadioButton;

private JTextArea __simpleList;

private JTextField __statusJTextField;
private JTextField __toJTextField;
private JTextField __fromJTextField;

private PropList __prop;

private String __buttonChoice;
private String __preferredState;

private List<TS> __ts;

private final int _NUM_COLS = 5;
private final int _NUM_ROWS = 5;

/**
DateTimeSelectPeriodJDialog constructor
@param frame JFrame object from which this dialog was instantiated
@param ts contains time series objects
@param prop PropList object
*/
public DateTimeSelectPeriodJDialog(JFrame frame, List<TS> ts, PropList prop) {
	super(frame, true);
	
	__frame = frame;
	__ts = ts;
	__prop = prop;

	setProperties();
	setupGUI();
}

/**
Responds to ActionEvents
@param evt ActionEvent object
*/
public void actionPerformed(ActionEvent evt) {
	String s = evt.getActionCommand();

	if (s.equals(__OK)) {
		getLimits(__OK);	
	}
	if (s.equals(__CANCEL)) {
		getLimits(__CANCEL);	
	}
	else if (s.equals(__SET_PERIOD)) {
		setPeriod_clicked();
	}
}

/**
Clean up before garbage collection.
@exception Throwable if there is an error.
*/
protected void finalize()
throws Throwable {
	__minJRadioButton = null;
	__maxJRadioButton = null;
	__userJRadioButton = null;
	__simpleList = null;
	__statusJTextField = null;
	__toJTextField = null;
	__fromJTextField = null;
	__setPeriodJButton = null;
	__ts = null;
	__frame = null;
	__prop = null;
	__buttonChoice = null;
	__preferredState = null;
	super.finalize();
}

/**
Closes the GUI.
@param flag __CANCEL or __OK depending upon which JButton was selected
*/
public void getLimits(String flag) {
	__buttonChoice = flag;
	getLimits();
}

/**
Closes the GUI and returns the limits.
@return the selected period as a TSLimit object if ok
was selected, null otherwise.
*/
public TSLimits getLimits() {
	DateTime start;
	DateTime end;
	TSLimits limit = null;
	
	if (__buttonChoice.equals(__OK)) {
		try {
			if (__fromJTextField.getText().trim().equals(
				"UNKNOWN")) {
				start = null;
				end = null;
			}
			else {
				start = DateTime.parse(
					__fromJTextField.getText(),
					__dateFormat);
	
				end = DateTime.parse(__toJTextField.getText(),
					__dateFormat);
			}

			limit = new TSLimits();
			limit.setDate1(start);
			limit.setDate2(end);
		}
		catch (Exception e) {
			limit = null;
		}
	}
	setVisible(false);
	dispose();
	return limit;
}

/**
Formats the TSLimits for the TS object
*/
private String getPeriodText(TS ts) {
	if (ts != null) {
		String format = "";
		DateTime start = ts.getDate1();
		start.setPrecision(__datePrecision);
		DateTime end = ts.getDate2();
		end.setPrecision(__datePrecision);

		if (start != null && end != null) {
			if (start.getYear() == -999) {
				format = "UNKNOWN";
			}
			else {
				format = start.toString(__dateFormat);
			}

			format += " - ";

			if (end.getYear() == -999) {
				format += "UNKNOWN";
			}
			else {
				format += end.toString(__dateFormat);
			}
		}
		return format;
	}
	return "";
}

/**
Sets the initial states of the GUI components
*/
private void initializeGUI() {
	String curString = "";
	String string_format = "%-40.40s  ";
	TS curTS;

	if (__ts != null) {	
		int size = __ts.size();
		if (size > 0) {
			for (int i=0; i<size; i++) {
				curTS =(TS)__ts.get(i);

				// Need a prop to set the legend.  Try to use
				// intelligent defaults for now.
				// Try using the identifier first...
				if (	(curTS.getLocation().length() > 0)&&
					(curTS.getAlias().length() > 0)) {
					curString +=
					StringUtil.formatString(
					curTS.formatExtendedLegend(
					"%L(%A), %D, %T"), string_format) +
					StringUtil.formatString(
					getPeriodText(curTS), "%-30.30s")
					+ "\n";
				}
				else if (curTS.getLocation().length() > 0) {
					curString +=
					StringUtil.formatString(
					curTS.formatExtendedLegend(
					"%L, %D, %T"), string_format) +
					StringUtil.formatString(
					getPeriodText(curTS), "%-30.30s")
					+ "\n";
				}
				else if (curTS.getAlias().length() > 0) {
					curString +=
					StringUtil.formatString(
					curTS.formatExtendedLegend(
					"%A, %D, %T"), string_format) +
					StringUtil.formatString(
					getPeriodText(curTS), "%-30.30s")
					+ "\n";
				}
				else {	// Use the full identifier...
					curString += StringUtil.formatString(
					curTS.getIdentifier().getIdentifier(),
					string_format)
					+ StringUtil.formatString(
					getPeriodText(curTS), "%-30.30s")
					+ "\n";
				}
			}
			__simpleList.setText(curString);
			__simpleList.setRows(_NUM_ROWS);
			__simpleList.setColumns(_NUM_COLS);
		}
	}

	// default the period according to the selected JRadioButton
	if (__maxJRadioButton.isSelected()) {
		setPeriodOfRecord(TSUtil.MAX_POR);
	}
	else if (__minJRadioButton.isSelected()) {
		setPeriodOfRecord(TSUtil.MIN_POR);
	}
	else if (__userJRadioButton.isSelected()) {
		setPeriodOfRecord(TSUtil.MAX_POR);
	}
}

/**
Indiate whether maximum period is selected.  This is used by query code so
that many years of blank data can be avoided.
*/
public boolean isMaxPeriodSelected() {
	return __maxJRadioButton.isSelected();
}

/**
Responds to ItemEvents
@param evt ItemEvent object
*/
public void itemStateChanged(ItemEvent evt) {
	Object o = evt.getItemSelectable();
	if (o.equals(__maxJRadioButton)) {
		setPeriodOfRecord(TSUtil.MAX_POR);
	}
	else if (o.equals(__minJRadioButton)) {
		setPeriodOfRecord(TSUtil.MIN_POR);
	}
	else if (o.equals(__userJRadioButton)) {
		setPeriodOfRecord(TSUtil.MAX_POR);
	}	
}

/**
Displays the DateBuilderGUI
*/
private void setPeriod_clicked() {
	String	routine = "DateTimeSelectPeriodJDialog.setPeriod_clicked";
	DateTime from = null;
	DateTime to = null;

	try {	
		if (__fromJTextField.getText().trim().equals("UNKNOWN")) {
			from = null;
			to = null;
		}
		else {
			from = DateTime.parse(__fromJTextField.getText(),
				__dateFormat);	

			to = DateTime.parse(__toJTextField.getText(),
				__dateFormat);
		}
	}
	catch(Exception e) {
		Message.printWarning(2, routine,
			"Exception parsing dates.");
		from = new DateTime(__datePrecision);
		to = new DateTime(__datePrecision);
	}
	
	PropList props = new PropList("DateTimeBuilderGUI properties");
	props.set("DatePrecision", __prop.getValue("DatePrecision"));
	props.set("DateFormat", __prop.getValue("DateFormat"));

	new DateTimeBuilderJDialog(__frame, __fromJTextField, 
		__toJTextField, from, to, props);
}

/**
This function determines the period(max or min)for the selected time series.
@param flag flag to determine if the period should be maximized or minimized,
TSUtil.MAX_POR, or TSUtil.MIN_POR
*/
private void setPeriodOfRecord(int flag) {
	List<TS> ts_Vector = new Vector<TS>();
	String function = "DateTimeSelectPeriodJDialog.setPeriodOfRecord()";

	if (__userJRadioButton.isSelected()) {
		__setPeriodJButton.setEnabled(true);
	}
	else {
		__setPeriodJButton.setEnabled(false);
	}

	String por = null;
	if (flag == TSUtil.MAX_POR) {
		por = "Maximum";
	}
	else if (flag == TSUtil.MIN_POR) {
		por = "Minimum";
	}

	int size = __ts.size();
	TS ts = null;
	DateTime startDate = null;
	DateTime endDate = null;
	for (int i = 0; i < size; i++) {
		ts = (TS)__ts.get(i);

		// exception catches ts objects which do not 
		// have a start and end date		
		try {
			startDate = ts.getDate1();
			endDate = ts.getDate2();
			ts_Vector.add(ts);
		} 
		catch(Exception e) {;}	
	}

	TSLimits limits = null;
	try {
		limits = TSUtil.getPeriodFromTS(ts_Vector, flag);
		startDate = (DateTime)limits.getDate1();
		startDate.setPrecision(__datePrecision);
		endDate = (DateTime)limits.getDate2();
		endDate.setPrecision(__datePrecision);

		if (startDate.getYear() == -999) {
			__fromJTextField.setText("UNKNOWN");
			__toJTextField.setText("UNKNOWN");	
		}
		else {
			__fromJTextField.setText(
				startDate.toString(__dateFormat));
			__toJTextField.setText(
				endDate.toString(__dateFormat));
		}
	}
	// exception catches periods violate max/min 
	catch(Exception e) {
		e.printStackTrace();
		Message.setTopLevel(__frame);
		Message.printWarning(1, function,
			"Cannot get " + por.toLowerCase() + " period of record" 
			+ " from the selected time series."
			+ "\n The individual periods do not overlap or "
			+ "are missing.");
	}
}

/**
Sets the properties
*/
private void setProperties() {
	String propValue;

	if (__prop == null) {
		__prop = new PropList("DateTimeSelectPeriodJDialog");
	}

        propValue =  __prop.getValue("PreferredState");
        if (propValue == null) {
                __preferredState = __MAX;                
        }
        else {	
		__preferredState = propValue;
        }
	
        propValue =  __prop.getValue("DatePrecision");
        if (propValue == null) {
                __datePrecision = DateTime.PRECISION_MINUTE;
		__prop.set("DatePrecision", "Minute");
        }
        else {	
		if (propValue.equalsIgnoreCase("Year")) {
			__datePrecision = DateTime.PRECISION_YEAR;
		}
		else if (propValue.equalsIgnoreCase("Month")) {
			__datePrecision = DateTime.PRECISION_MONTH;
		}
		else if (propValue.equalsIgnoreCase("Day")) {
			__datePrecision = DateTime.PRECISION_DAY;
		}
		else if (propValue.equalsIgnoreCase("Hour")) {
			__datePrecision = DateTime.PRECISION_HOUR;
		}
		else if (propValue.equalsIgnoreCase("Minute")) {
			__datePrecision = DateTime.PRECISION_MINUTE;
		}
		else {	
			__datePrecision = DateTime.PRECISION_MINUTE;
		}
        }

        propValue =  __prop.getValue("DateFormat");
	String dateFormat = "";
        if (propValue == null) {
		// Default to "Y2K"
		dateFormat = "Y2K";
		__prop.set("DateFormat", "Y2K");

        }
        else {	
		dateFormat = propValue;
	}
	if (dateFormat.equalsIgnoreCase("US")) {
		if (__datePrecision == DateTime.PRECISION_YEAR) {
			__dateFormat = DateTime.FORMAT_YYYY;
		}
		else if (__datePrecision == DateTime.PRECISION_MONTH) {
			__dateFormat = DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY;
		}
		else if (__datePrecision == DateTime.PRECISION_DAY) {
			__dateFormat = DateTime.FORMAT_MM_SLASH_DD;
		}
		else if (__datePrecision == DateTime.PRECISION_HOUR) {
			__dateFormat=
				DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY_HH_mm;
		}
		else if (__datePrecision == DateTime.PRECISION_MINUTE) {
			__dateFormat=
				DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY_HH_mm;
		}
        }
	else {	
		// Default or "Y2K"...
		if (__datePrecision == DateTime.PRECISION_YEAR) {
			__dateFormat = DateTime.FORMAT_YYYY;
		}
		else if (__datePrecision == DateTime.PRECISION_MONTH) {
			__dateFormat = DateTime.FORMAT_YYYY_MM;
		}
		else if (__datePrecision == DateTime.PRECISION_DAY) {
			__dateFormat = DateTime.FORMAT_YYYY_MM_DD;
		}
		else if (__datePrecision == DateTime.PRECISION_HOUR) {
			__dateFormat = DateTime.FORMAT_YYYY_MM_DD_HH;
		}
		else if (__datePrecision == DateTime.PRECISION_MINUTE) {
			__dateFormat = DateTime.FORMAT_YYYY_MM_DD_HH_mm;
		}
	}
}

private void setupGUI() {
	addWindowListener(this);

	// the following are used in the GUI layout
	Insets TNNR = new Insets(7,0,0,7);
	Insets TLNR = new Insets(7,7,0,7);
	Insets NLBR = new Insets(0,7,7,7);
	Insets TLNN = new Insets(7,7,0,0);
	GridBagLayout gbl = new GridBagLayout();

	JPanel northJPanel = new JPanel();
	northJPanel.setLayout(new BorderLayout());
	getContentPane().add("North", northJPanel);

	JPanel northWJPanel = new JPanel();
	northWJPanel.setLayout(gbl);
	northJPanel.add("Center", northWJPanel);

	JGUIUtil.addComponent(northWJPanel, 
		new JLabel("Selected Time Series:"), 
		0, 0, 2, 1, 0, 0, TLNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__simpleList = new JTextArea("", _NUM_ROWS, _NUM_COLS);
        __simpleList.setFont(new Font("Courier", Font.PLAIN, 11));
	__simpleList.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, 
		new JScrollPane(__simpleList, 
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
		0, 1, 2, 1, 1, 1, NLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

	__userJRadioButton = new JRadioButton("User defined" , false);
	__maxJRadioButton = new JRadioButton("Maximize the Period", false);
	__minJRadioButton = new JRadioButton("Minimize the Period", false);
	ButtonGroup group = new ButtonGroup();
	group.add(__userJRadioButton);
	group.add(__maxJRadioButton);
	group.add(__minJRadioButton);

	if (__preferredState.equals(__USER)) {
		__userJRadioButton.setSelected(true);
	}
	JGUIUtil.addComponent(northWJPanel, __userJRadioButton, 
		0, 2, 1, 1, 0, 0, TLNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(northWJPanel, 
		__setPeriodJButton = new SimpleJButton(__SET_PERIOD, this),
		1, 2, 1, 1, 0, 0, TNNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	if (__preferredState.equals(__MAX)) {
		__maxJRadioButton.setSelected(true);
	}
	JGUIUtil.addComponent(northWJPanel, __maxJRadioButton, 
		0, 3, 2, 1, 0, 0, TLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	if (__preferredState.equals(__MIN)) {
		__minJRadioButton.setSelected(true);
	}
	JGUIUtil.addComponent(northWJPanel, __minJRadioButton, 
		0, 4, 2, 1, 0, 0, TLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(northWJPanel, 
		new JLabel("Plot Period:"), 
		0, 5, 1, 1, 0, 0, TLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	JPanel intervalJPanel = new JPanel();
	intervalJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	JGUIUtil.addComponent(northWJPanel, intervalJPanel,
		1, 5, 1, 1, 0, 0, TNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__fromJTextField = new JTextField(15);
	__fromJTextField.setEditable(false);
	intervalJPanel.add(__fromJTextField);

	intervalJPanel.add(new JLabel(" to "));
 
	__toJTextField = new JTextField(15);
	__toJTextField.setEditable(false);
	intervalJPanel.add(__toJTextField);

	JPanel southJPanel = new JPanel();
	southJPanel.setLayout(new BorderLayout());
	getContentPane().add("South", southJPanel);

	JPanel buttonJPanel = new JPanel();
	buttonJPanel.setLayout(new FlowLayout());
	southJPanel.add("North", buttonJPanel);

	buttonJPanel.add(new SimpleJButton(__OK, this));
	buttonJPanel.add(new SimpleJButton(__CANCEL, this));
	
	JPanel statusJPanel = new JPanel();
	statusJPanel.setLayout(gbl);
	southJPanel.add("South", statusJPanel);
	
	__statusJTextField = new JTextField();
	__statusJTextField.setEditable(false);
	JGUIUtil.addComponent(statusJPanel, __statusJTextField,
		0, 0, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
	__userJRadioButton.addItemListener(this);
	__maxJRadioButton.addItemListener(this);
	__minJRadioButton.addItemListener(this);
	
	setTitle("Select Time Series Period");
	setResizable(false);
	initializeGUI();
	pack();
	setSize(550, 350);
	JGUIUtil.center(this);
	setVisible(true);
}

public void windowActivated(WindowEvent evt) {;}
public void windowClosed(WindowEvent evt) {;}

/**
Closes the GUI.
@param evt WindowEvent object
*/
public void windowClosing(WindowEvent evt)
{	getLimits(__CANCEL);
}

public void windowDeactivated(WindowEvent evt) {;}
public void windowDeiconified(WindowEvent evt) {;}
public void windowIconified(WindowEvent evt) {;}
public void windowOpened(WindowEvent evt) {;}

} // end DateTimeSelectPeriodJDialog
