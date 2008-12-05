//-----------------------------------------------------------------------------
// HydroBase_GUI_AdminNumCalculator - GUI allowing user to 
//	* calculate the admin number associated with a prior adjudication date 
//		and apropriation date OR 
//	* calculate the apropriation date associated with an admin number
//		and prior adjudication date
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History: 
//
// 11 Jan 1999	CEN, RTi 	Created initial class description.
// 22 Jan 1999	SAM, RTi	Changed so that HBAdminitrationNumber is used
//				instead of HBWaterRightNet.  The former is more
//				robust.
// 02 Jun 1999	SAM, RTi	Clean up to minimize imports.
// 23 May 2001	SAM, RTi	Add a graph of admin number versus time to
//				help people understand its value.  Change GUI
//				to GUIUtil.  Add finalize().  Change static
//				data to non-static to improve memory use.
//				Change IO to IOUtil.
// 2002-02-20	SAM, RTi	Update to use TSViewFrame instead of TSViewGUI.
//				Disable reference graph since data are 2 points
//				and zooming will cause plot to not show.
//-----------------------------------------------------------------------------
// 2003-04-08 	J. Thomas Sapienza, RTi	Initial Swing version.
// 2003-12-08	JTS, RTi		* Cleaned up.
//					* Converted to using DateTime.
//					* Enabled graphing.
// 2005-04-27	JTS, RTi		Added all data members to finalize().
// 2007-02-07	SAM, RTi		Remove dependency on CWRAT package.
//					Just pass a JFrame as the parent.
//					Also clean up the code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

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

import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.GRTS.TSViewJFrame;

import RTi.TS.TS;
import RTi.TS.TSUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.DateTimeBuilderJDialog;

/**
This class is a GUI for allowing the user to do calculation on administration
numbers.
*/
public class HydroBase_GUI_AdminNumCalculator 
extends JDialog
implements ActionListener, KeyListener, WindowListener {

/**
Name of the class.
*/
public final static String CLASS = "HydroBase_GUI_AdminNumCalculator";

/**
Button labels.
*/
private final String 
	__BUTTON_CALCULATE_ADMIN_NUM = 	"Calculate Admin #",
	__BUTTON_CALCULATE_DATE = 	"Calculate Date",
	__BUTTON_CLOSE = 		"Close",
	__BUTTON_GRAPH = 		"Graph",
	__BUTTON_HELP = 		"Help",
	__BUTTON_CLEAR_PRIOR_DATE = 	"Clear Prior Date",
	__BUTTON_CLEAR_PRIOR_DATE2 = 	"Clear Prior Date2",
	__BUTTON_SET_PRIOR_DATE = 	"Set Prior Date",
	__BUTTON_SET_PRIOR_DATE2 = 	"Set Prior Date2",
	__BUTTON_SET_APRO_DATE = 	"Set Apro Date",
	__BUTTON_SET = 			"Set...",
	__BUTTON_CLEAR = 		"Clear";

/**
Parent frame that instantiated this gui.
*/
private JFrame __parent;

/**
DateTimes used for calculating things.
*/
private DateTime 
	__aproDate = null,
	__priorDate = null,
	__priorDate2 = null;

/**
References to types of actions the calculator can handle.
*/
private final int 
	__ADMIN_NUM = 0,
	__DATES = 1;

/**
GUI buttons.
*/
private JButton 
	__closeJButton,
	__graphJButton;

/**
GUI labels.
*/
private JLabel 
	__adminNumJLabel,
	__aproDateJLabel;

/**
GUI text fields.
*/
private JTextField 
	__adminNumJTextField,
	__aproDateJTextField,
	__priorDateJTextField,
	__priorDate2JTextField;

/**
DateTimeBuilderJDialog property list.
*/
private PropList __dateProps;

/**
Constructor.
@param dmi an open HydroBaseDMI object.
@param parent the JFrame that instantiated this object.
*/
public HydroBase_GUI_AdminNumCalculator(HydroBaseDMI dmi, JFrame parent)
{
        super(parent, false);

        __parent = parent;

	setupGUI();
}

/**
Responds to action events.
@param evt the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent evt) {
	String command = evt.getActionCommand();
	String routine = "HydroBase_GUI_AdminNumCalculator.actionPerformed";
	
	if (command.equals(__BUTTON_CALCULATE_ADMIN_NUM)) {
		calculateClicked(__ADMIN_NUM);
	}  
	else if (command.equals(__BUTTON_CALCULATE_DATE)) {
		calculateClicked(__DATES);
	}  
	else if (command.equals(__BUTTON_CLOSE)) {
        	closeClicked();
	}
	else if (command.equals(__BUTTON_GRAPH)) {
        	graphClicked();
	}
	else if (command.equals(__BUTTON_HELP)){ 
	}
	else if (command.equals(__BUTTON_SET_PRIOR_DATE2)) {
		new DateTimeBuilderJDialog(__parent,
			__priorDate2JTextField,
			__priorDate2, __dateProps);
		try {
			__priorDate2 = 
				DateTime.parse(__priorDate2JTextField.getText(),
					DateTime.FORMAT_YYYY_MM_DD);
		} 
		catch (Exception e) {
			Message.printWarning(2, routine, "Error parsing date");
			Message.printWarning(2, routine, e);
			__priorDate2 = new DateTime(DateTime.DATE_CURRENT);
		}
	}
	else if (command.equals(__BUTTON_CLEAR_PRIOR_DATE2)) {
		__priorDate2JTextField.setText("");
	}
	else if (command.equals(__BUTTON_SET_PRIOR_DATE)) {
		new DateTimeBuilderJDialog(__parent, 
			__priorDateJTextField,
			__priorDate, __dateProps);
		try {
			__priorDate = 
				DateTime.parse(__priorDateJTextField.getText(), 
					DateTime.FORMAT_YYYY_MM_DD);
		} 
		catch (Exception e) {
			Message.printWarning(2, routine, "Error parsing date");
			Message.printWarning(2, routine, e);
			__priorDate = new DateTime(DateTime.DATE_CURRENT);
		}			
	}
	else if (command.equals(__BUTTON_CLEAR_PRIOR_DATE)) {
		__priorDateJTextField.setText("");
	}
	else if (command.equals(__BUTTON_SET_APRO_DATE)) {
		new DateTimeBuilderJDialog(__parent,
			__aproDateJTextField,
			__aproDate, __dateProps);
		try {
			__aproDate = 
				DateTime.parse(__aproDateJTextField.getText(), 
					DateTime.FORMAT_YYYY_MM_DD);
		} 
		catch (Exception e) {
			Message.printWarning(2, routine, "Error parsing date");
			Message.printWarning(2, routine, e);
			__aproDate = new DateTime(DateTime.DATE_CURRENT);
		}						
	}
}      

/**
Close the GUI.
*/
private void closeClicked() {
	super.setVisible(false);
	dispose();
}

/**
Clean up for garbage collection.
*/
protected void finalize()
throws Throwable {
	__parent = null;
	__aproDate = null;
	__priorDate = null;
	__priorDate2 = null;
	__closeJButton = null;
	__graphJButton = null;
	__adminNumJLabel = null;
	__aproDateJLabel = null;
	__adminNumJTextField = null;
	__aproDateJTextField = null;
	__priorDateJTextField = null;
	__priorDate2JTextField = null;
	__dateProps = null;
	super.finalize();
}

/**
Graph whole number administration number versus time.
Only the end points of the time series are needed.
*/
private void graphClicked() {
	String routine = CLASS + ".graphClicked";
	TS ts = null;
	try {	
		ts = TSUtil.newTimeSeries(
			"AdministrationNumbers..AdminNum.Irregular",
			true);
		ts.setIdentifier("AdministrationNumbers..AdminNum.Day");
		ts.setDate1(DateTime.parse("1849-12-31"));
		ts.setDate2(DateTime.parse("2123-10-15"));
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Unable to display graph.");
		Message.printWarning(2, routine, e);
		return;
	}
	ts.setDataType("AdminNum");
	ts.setDescription("Whole Administration Numbers");
	ts.allocateDataSpace();
	// Fill in the data
	DateTime start = new DateTime(ts.getDate1());
	DateTime end = new DateTime(ts.getDate2());
	HydroBase_AdministrationNumber an = null;
	try {	
		an = new HydroBase_AdministrationNumber(start);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Unable to display graph.");
		Message.printWarning(2, routine, e);
		return;
	}
	try {	
		an.setAppropriationDate(start);
		ts.setDataValue(start, an.getAdminNumber());
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error setting data value.");
		Message.printWarning(2, routine, e);
		;// Skip setting data.
	}
	try {	
		an.setAppropriationDate(end);
		ts.setDataValue(end, an.getAdminNumber());
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error setting data value.");
		Message.printWarning(2, routine, e);
		;// Skip setting data.
	}
	start = null;
	end = null;
	an = null;
	// Now graph...
	try {	
		PropList tsview_props = new PropList("tsview");
		tsview_props.set("TotalWidth", "750");
		tsview_props.set("TotalHeight", "550");
		tsview_props.set("DisplayFont", "Courier");
		tsview_props.set("DisplaySize", "11");
		tsview_props.set("PrintFont", "Courier");
		tsview_props.set("PrintSize", "7");
		tsview_props.set("PageLength", "100");
		String title =
			"Whole Administration Numbers vs. Appropriation Date";
		tsview_props.set("TitleString", title);
		tsview_props.set("TSViewTitleString", title);
		// Now display the correct initial view...
		tsview_props.set("InitialView", "Graph");
		// Only show whole numbers...
		tsview_props.set("YAxisPrecision", "0");
		tsview_props.set("XAxisJLabelString", "Appropriation Date");
		tsview_props.set("EnableTable", "false");
		tsview_props.set("EnableReferenceGraph", "false");
		List ts_Vector = new Vector(1);
		ts_Vector.add(ts);
		JGUIUtil.setWaitCursor(this, true);
		new TSViewJFrame(ts_Vector, tsview_props);
		JGUIUtil.setWaitCursor(this, false);
		ts_Vector = null;
		tsview_props = null;
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Unable to display graph.");
		Message.printWarning(2, routine, e);
	}
	ts = null;
}

/**
Initializes data members.
*/
private void initialize() {
	__dateProps = new PropList("DateTimeBuilder properties list");
	__dateProps.set("DatePrecision", "Day");
	__dateProps.set("DateFormat", "Y2K");

	__aproDate = new DateTime(DateTime.DATE_CURRENT | 
		DateTime.PRECISION_DAY);
	__priorDate = new DateTime(DateTime.DATE_CURRENT | 
		DateTime.PRECISION_DAY);
	__priorDate2 = new DateTime(DateTime.DATE_CURRENT |
		DateTime.PRECISION_DAY);

        setVisible(true);
}
   
/**
Responds to key pressed events.
@param evt the KeyEvent that happened.
*/
public void keyPressed(KeyEvent evt) {
	int code = evt.getKeyCode();

	// enter key is the same as ok
	if (code == KeyEvent.VK_ENTER) {
       		calculateClicked(__ADMIN_NUM);
	}
	// F1 envokes help
	else if (code == KeyEvent.VK_F1) {
	}
}

/**
Responds to key released events; does nothing.
@param event the KeyEvent that happened.
*/
public void keyReleased(KeyEvent event) {;}

/**
Responds to key typed events; does nothing.
@param event the KeyEvent that happened.
*/
public void keyTyped(KeyEvent event) {;}

/**
Determines user permissions and login validation.
*/
private void calculateClicked(int type) {
        // initialize variables
        String routine = CLASS + ".calculateClicked()";

	// computing admin number
	if (type == __ADMIN_NUM) {
		DateTime priorDate = null;
		DateTime aproDate = null;
		try {
			if (__priorDateJTextField.getText().trim().length() 
				!= 0) {
				__priorDate = 
					DateTime.parse(
					__priorDateJTextField.getText(),
					DateTime.FORMAT_YYYY_MM_DD);
				priorDate = 
					DateTime.parse(
					__priorDateJTextField.getText(), 
					DateTime.FORMAT_YYYY_MM_DD);
			}
		} 
		catch (Exception e) {
			Message.printWarning(2, routine, "Error parsing date");
			Message.printWarning(2, routine, e);
			priorDate = new DateTime(DateTime.DATE_CURRENT);
		}	
		
		try {
			if (__aproDateJTextField.getText().trim().length()!= 0){
				__aproDate = 
					DateTime.parse(
					__aproDateJTextField.getText(), 
					DateTime.FORMAT_YYYY_MM_DD);
				aproDate = 
					DateTime.parse(
					__aproDateJTextField.getText(), 
					DateTime.FORMAT_YYYY_MM_DD);
			}
			else {
				new ResponseJDialog(__parent, "An apropriation"
					+ " date must be provided.");
				return;
			}
		} catch (Exception e) {
			Message.printWarning(2, routine, "Error parsing date");
			Message.printWarning(2, routine, e);
			aproDate = new DateTime(DateTime.DATE_CURRENT);
		}								

		try {	
			HydroBase_AdministrationNumber adminNum = 
				new HydroBase_AdministrationNumber(
				aproDate, priorDate);
			__adminNumJLabel.setText(adminNum.toString());
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error calculating "
				+ "administration number from dates!");
			Message.printWarning(2, routine, e);
		}
	}
	else {	
		// computing apropriation date
		String adminNum = __adminNumJTextField.getText().trim();
		if (adminNum.length() == 0) {
			new ResponseJDialog(__parent,
				"An administration number must be provided.");
			return;
		}
		DateTime priorDate2 = null;

		if (__priorDate2JTextField.getText().trim().length()!= 0)
			priorDate2 = new DateTime(__priorDate2);
			
		try {	
			HydroBase_AdministrationNumber results =
				new HydroBase_AdministrationNumber(
				StringUtil.atod(adminNum), priorDate2);

			DateTime aproDate = results.getAppropriationDate();
			__aproDateJLabel.setText(aproDate.toString());

			DateTime priorDate = results.getPriorAdjudicationDate();
			if (priorDate != null) {
				__priorDate2JTextField.setText(
					priorDate.toString());
				try {
					__priorDate2 = 
						DateTime.parse(
						__priorDate2JTextField
						.getText(),
						DateTime.FORMAT_YYYY_MM_DD);
				} 
				catch (Exception e) {
					Message.printWarning(1, routine,
						"Error calculating dates from "
						+ "administration number " 
						+ adminNum);
					Message.printWarning(2, routine, e);
				}
			}
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error calculating " 
				+ "dates from administration number " 
				+ adminNum);
			Message.printWarning(2, routine, e);
		}		
	}
}                    

/**
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);
                
        // used in the GridBagLayouts
        Insets NLNN = new Insets(0,40,0,0);
        Insets NLNR = new Insets(0,7,0,7);
        Insets NNNR = new Insets(0,0,0,7);
        Insets TLNN = new Insets(7,7,0,0);
        GridBagLayout gbl = new GridBagLayout();

        // North JPanel
        JPanel northJPanel = new JPanel();
        northJPanel.setLayout(gbl);
        getContentPane().add("North", northJPanel);
        
	int y=0;
	// start calculating admin number area ...
        JGUIUtil.addComponent(northJPanel, 
		new JLabel("Calculate administration number:"),
		0, y++, 2, 1, 0, 0, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(northJPanel, new JLabel("Appropriation date:"),
		1, y, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
	__aproDateJTextField = new JTextField(10);
	__aproDateJTextField.setEditable(false);
        JGUIUtil.addComponent(northJPanel, __aproDateJTextField,
		2, y, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
	SimpleJButton temp = 
		new SimpleJButton(__BUTTON_SET, __BUTTON_SET_APRO_DATE, this);
	temp.setToolTipText("Set the appropriation date.");
        JGUIUtil.addComponent(northJPanel, temp,
		3, y++, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northJPanel, 
		new JLabel("Prior adjudication date:"),
		1, y, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
	__priorDateJTextField = new JTextField(10);
	__priorDateJTextField.setEditable(false);
        JGUIUtil.addComponent(northJPanel, __priorDateJTextField,
		2, y, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
	temp = new SimpleJButton(__BUTTON_SET, __BUTTON_SET_PRIOR_DATE, this);
	temp.setToolTipText("Set the prior adjudication date.");
        JGUIUtil.addComponent(northJPanel, temp,
		3, y, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
	temp = new SimpleJButton(__BUTTON_CLEAR,__BUTTON_CLEAR_PRIOR_DATE,this);
	temp.setToolTipText("Clear the prior adjudication date.");
        JGUIUtil.addComponent(northJPanel, temp,
		4, y, 1, 1, 0, 0, NLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(northJPanel, new JLabel("(optional)"),
		1, y++, 1, 1, 0, 0, 30, 40, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northJPanel, new JLabel("Administration number:"),
		1, y, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
	__adminNumJLabel = new JLabel("     ");
        JGUIUtil.addComponent(northJPanel, __adminNumJLabel,
		2, y, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	temp = new SimpleJButton(__BUTTON_CALCULATE_ADMIN_NUM,
		__BUTTON_CALCULATE_ADMIN_NUM, this);
	temp.setToolTipText("Calculate the administration number.");
        JGUIUtil.addComponent(northJPanel, temp,		
		3, y++, 2, 1, 0, 0, NNNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	//
	// start calculate apropriation date area ...
	//
        JGUIUtil.addComponent(northJPanel, 
		new JLabel("Calculate apropriation date:"),
		0, y++, 2, 1, 0, 0, TLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(northJPanel, new JLabel("Administration number:"),
		1, y, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
        __adminNumJTextField = new JTextField(10);
	__adminNumJTextField.addKeyListener(this);
        JGUIUtil.addComponent(northJPanel, __adminNumJTextField, 
		2, y++, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northJPanel, 
		new JLabel("Prior adjudication date:"),
		1, y, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
	__priorDate2JTextField = new JTextField(10);
	__priorDate2JTextField.setEditable(false);
        JGUIUtil.addComponent(northJPanel, __priorDate2JTextField,
		2, y, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
	temp = new SimpleJButton(__BUTTON_SET, __BUTTON_SET_PRIOR_DATE2, this);
	temp.setToolTipText("Set the prior adjudication date.");
        JGUIUtil.addComponent(northJPanel, temp,		
		3, y, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
	temp = new SimpleJButton(__BUTTON_CLEAR, __BUTTON_CLEAR_PRIOR_DATE2,
		this);
	temp.setToolTipText("Clear the prior adjudication date.");
        JGUIUtil.addComponent(northJPanel, temp,
		4, y, 1, 1, 0, 0, NLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(northJPanel, 
		new JLabel("(provide if available)"),
		1, y++, 1, 1, 0, 0, 30, 40, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northJPanel, 
		new JLabel("Appropriation date:"),
		1, y, 1, 1, 0, 0, NLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
	__aproDateJLabel = new JLabel("     ");
        JGUIUtil.addComponent(northJPanel, __aproDateJLabel,
		2, y, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	temp = 	new SimpleJButton(__BUTTON_CALCULATE_DATE, 
		__BUTTON_CALCULATE_DATE, this);
	temp.setToolTipText("Calculate the date.");
        JGUIUtil.addComponent(northJPanel, temp,
		3, y++, 2, 1, 0, 0, NNNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        
        // South JPanel
        JPanel southJPanel = new JPanel();
        southJPanel.setLayout(new FlowLayout());
        getContentPane().add("South", southJPanel);
                
        __graphJButton = new JButton(__BUTTON_GRAPH);
	__graphJButton.setToolTipText("Graph the administration numbers over "
		+ "time.");
	__graphJButton.addActionListener(this);
        southJPanel.add(__graphJButton);

        __closeJButton = new JButton(__BUTTON_CLOSE);
	__closeJButton.setToolTipText ("Close this window.");	
	__closeJButton.addActionListener(this);
        southJPanel.add(__closeJButton);

//        __helpJButton = new JButton(__BUTTON_HELP);
//	__helpJButton.addActionListener(this);
//	__helpJButton.setEnabled(false);
//        southJPanel.add(__helpJButton);
        
        // frame settings        
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}	
        setTitle(app + "Administration Number Calculator");
        pack();
	JGUIUtil.center(this);
        setResizable(false);    	
    	initialize();
}

/**
Does nothing.
*/
public void windowActivated(WindowEvent evt) {}

/**
Does nothing.
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
Does nothing.
*/
public void windowDeactivated(WindowEvent evt) {}

/**
Does nothing.
*/
public void windowDeiconified(WindowEvent evt) {}

/**
Does nothing.
*/
public void windowIconified(WindowEvent evt) {}

/**
Does nothing.
*/
public void windowOpened(WindowEvent evt) {}

}
