//-----------------------------------------------------------------------------
// HydroBase_GUI_EditCallsBypass - edit calls bypass GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 09 Sep 1997	DLG, RTi 		Created initial class description.
// 02 Mar 1998 DLG, RTi			Updated to java 1.1 event model.
// 2001-11-12	SAM, RTi		Change GUI to GUIUtil.
//					Remove import *.
//-----------------------------------------------------------------------------
// 2003-04-01	J. Thomas Sapienza, RTi	Moved to Swing.
// 2003-04-02	JTS, RTi		Cleaned up bugs, got working.
// 2003-04-04	JTS, RTi		Changed GUIUtil to JGUIUtil.
// 2005-02-15	JTS, RTi		Converted most queries to use stored
//					procedures.
// 2005-02-21	JTS, RTi		The __structures Vector could now
//					contain HydroBase_StructureView objects,
//					so the code was changed appropriately.
// 2005-04-12	JTS, RTi		MutableJList changed to SimpleJList.
// 2005-04-25	JTS, RTi		Call new version of formatDateTime()
//					suitable for use with stored procedures.
// 2005-04-28	JTS, RTi		* Added all data members to finalize().
//					* Added super.finalize() to finalize().
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
// 2005-08-03	JTS, RTi		* The structures list is sorted now.
//					* Added a FindInJListJDialog to search
//					  in the structure list.
// 2006-01-11	JTS, RTi		* Values in the decreed amount field are
//					  now formatted to 4 decimal places.
// 2006-01-20	JTS, RTi		When a structure is chosen from the 
//					structure GUI, the list scrolls to 
//					ensure the row the structure is on is
//					visible.
// 2007-02-07	SAM, RTi		Remove dependence on CWRAT.
//					Initialize with a JFrame.
//					Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.FindInJListJDialog;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJList;
import RTi.Util.GUI.SimpleJMenuItem;

import RTi.Util.String.StringUtil;

import RTi.Util.Message.Message;

import RTi.Util.Time.DateTime;

@SuppressWarnings("serial")
public class HydroBase_GUI_EditCallsBypass 
extends JFrame 
implements ActionListener, MouseListener, ListSelectionListener, WindowListener{

/**
GUI strings.
*/
private final static String 
	__BUTTON_CANCEL = 	"Cancel",
	__BUTTON_OK = 		"OK",
	__MENU_FIND_STRUCTURE = "Find Structure";

/**
The HydroBase_GUI_SetCall object that opened this gui.
*/
private HydroBase_GUI_SetCall __parent;

/**
The main application parent, necessary to position the structure query window.
*/
private JFrame __main_parent;

/**
Reference to a dmi object.
*/
private HydroBaseDMI __dmi;

/**
The number of the currently-selected row.
*/
private int __curStructureRow;

/**
The cancel button.
*/
private JButton __cancelJButton;
/**
The ok button.
*/
private JButton	__okJButton;

/**
The label for the calls list.
*/
private JLabel __callJLabel;

/**
Popup menu on the structure list.
*/
private JPopupMenu __structureJPopupMenu = null;

/**
Adjudication date text field.
*/
private JTextField __adjDateJTextField;

/**
Administrative number text field.
*/
private JTextField __adminJTextField;

/**
Appropriation date text field.
*/
private JTextField __aproDateJTextField;

/**
Available calls text field.
*/
private JTextField __callsJTextField;

/**
Decreed amount text field.
*/
private JTextField __decreedJTextField;

/**
Previous adjucation number text field.
*/
private JTextField __priorNumJTextField;

/**
Status bar.
*/
private JTextField __statusJTextField;

/**
List of calls.
*/
private SimpleJList __callsJList;

/**
List of structures.
*/
private SimpleJList __structureJList;

/**
List of HydroBase_Calls objects.
*/
private List<HydroBase_NetAmts> __calls;

/**
List of HydroBase_Structure objects, or HydroBase_StructureView objects
if using stored procedures..
*/
private List<HydroBase_StructureView> __structures;

/**
List of JTextFields.
*/
private List<JTextField> __textFieldVector;

private JLabel __structureJLabel = null;

private final String __BUTTON_SELECT_STRUCTURE 
	= "Select Structure from Database";

private JButton __selectStructureJButton = null;

// GeoViewUI instance to handle map interactions
private GeoViewUI __geoview_ui = null;

/**
Structure query gui that this gui opens.
*/
private HydroBase_GUI_StructureQuery __callStructureQueryGUI = null;

/**
Constructor.
@param dmi a HydroBaseDMI object.
@param main_parent the main application JFrame, to be used as
the parent when a structure query window is displayed.
@param parent the calling JFrame that instantiated this.
@param textField_Vector a Vector of text field objects.
*/
public HydroBase_GUI_EditCallsBypass(HydroBaseDMI dmi, 
JFrame main_parent, HydroBase_GUI_SetCall parent, List<JTextField> textField_Vector) {
	__main_parent = main_parent;
	__parent = parent;
	__dmi = dmi;
	__textFieldVector = textField_Vector;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();
}

/**
Responds to action performed events.
@param evt the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent evt) {
	String command = evt.getActionCommand();

	if (command.equals(__BUTTON_OK)) {
		okClicked();
	}
	else if (command.equals(__BUTTON_CANCEL)) {
		closeClicked();
	}
	else if (command.equals(__BUTTON_SELECT_STRUCTURE)) {
		structureQueryClicked();
	}	
    	else if (command.equals(__MENU_FIND_STRUCTURE)) {
		new FindInJListJDialog(this, (JList)__structureJList, 
			"Find Structure");
	}	
}

/**
Responds to the __callJList LIST_SELECT event and populates the appropriate
objects with the information pertaining to them.
*/
private void callsJListClicked() {
	// return if no items are present in the the __callsJList object
	int rows = __callsJList.getSelectedSize();
	if (rows == 0) {
		__okJButton.setEnabled(false);
		return;
	}
	else {
		__okJButton.setEnabled(true);
	}
	
	// get the selected item from the __callsJList object
	String selectedItem = ((String)__callsJList.getSelectedValue()).trim();
	__callsJTextField.setText(selectedItem);

	HydroBase_NetAmts netAmt = (HydroBase_NetAmts)__calls.get(__callsJList.getSelectedIndex());

	Date curDate = netAmt.getApro_date();
	if (!DMIUtil.isMissing(curDate)) {
		__aproDateJTextField.setText((new DateTime(curDate)).toString(
			DateTime.FORMAT_YYYY_MM_DD));
	}
        
	double curDouble = netAmt.getAdmin_no();
	if (!DMIUtil.isMissing(curDouble)) {
		__adminJTextField.setText(
			StringUtil.formatString(curDouble, "%11.5f"));
	}
        
	curDouble = netAmt.getNet_abs();
	if (!DMIUtil.isMissing(curDouble)) {
		String unit = netAmt.getUnit();
		if (unit.equalsIgnoreCase("C")) {
			unit = HydroBase_GUI_SetCall.CFS;
		}
		else if (unit.equalsIgnoreCase("A")) {
			unit = HydroBase_GUI_SetCall.AF;
		}
		else {	
			unit = "";
		}

		__decreedJTextField.setText(
			(StringUtil.formatString(curDouble, "%7.4f")
			+ " " + unit).trim());
	}       

	curDate = netAmt.getAdj_date();
	if (!DMIUtil.isMissing(curDate)) {
		__adjDateJTextField.setText((new DateTime(curDate)).toString(
			DateTime.FORMAT_YYYY_MM_DD));	
	}
		
	__priorNumJTextField.setText(netAmt.getPri_case_no());
}

/**
Clears the text in the available call fields.
*/
private void clearCallFields() {
	__adminJTextField.setText("");
	__aproDateJTextField.setText("");
	__decreedJTextField.setText("");		
	__adjDateJTextField.setText("");		
	__priorNumJTextField.setText("");
	__callsJTextField.setText("");		
} 

/**
Closes the GUI.
*/
private void closeClicked() {
	setVisible(false);

	// de-select selected a call if selected
	int index = __structureJList.getSelectedIndex();
	if (index != -1) {
		__callsJList.removeAll();
	}
}

/**
Displays the WIS structures.
@param results contains results from the net amts query.
*/
private void displayWaterRightNetResults(List<HydroBase_NetAmts> results) {
	String routine = "HydroBase_GUI_EditCallsBypass.displayWaterRight"
		+ "NetResults";
	// initialize variables
	int size = results.size();

	// get the priority preference display from user preferences
	String display = __dmi.getPreferenceValue("General.CallingRight");

	String curString = "";
	double curDouble;
	Date curDate;
	HydroBase_NetAmts netAmt;

	for (int i = 0; i < size; i++) {
		netAmt = results.get(i);
		curString = "";
		// display based on user preferences
		if (display.equals(HydroBase_GUI_Options.PRIORITY_NUMBER)) {
			curString = netAmt.getPri_case_no();
		}
		else if (display.equals(HydroBase_GUI_Options.ADJ_DATE)) {
			curDate = netAmt.getAdj_date();
			if (DMIUtil.isMissing(curDate)) {
	                	curString = "";
			}
			else {
				try {
	                    		curString = DMIUtil.formatDateTime(
						__dmi, new DateTime(curDate));
				}
				catch (Exception e) {
					Message.printWarning(1, routine, 
						"Error while formatting date.");
					Message.printWarning(1, routine, e);
					curString = "";
				}					
			}
		}
                else if (display.equals(HydroBase_GUI_Options.APPRO_DATE)) {
			curDate = netAmt.getApro_date();
			if (DMIUtil.isMissing(curDate)) {
		  		curString = "";
			}
			else {
				try {
					curString =DMIUtil.formatDateTime(__dmi,
						new DateTime(curDate));
				}
				catch (Exception e) {
					Message.printWarning(1, routine, 
						"Error while formatting date.");
					Message.printWarning(1, routine, e);
					curString = "";
				}					
			}
                }
                else if (display.equals(HydroBase_GUI_Options.ADMIN_NO)) {
			curDouble = netAmt.getAdmin_no();
			if (DMIUtil.isMissing(curDouble)) {
	                    	curString = "";       
			}
			else {
				curString = StringUtil.formatString(
					curDouble, "%11.5f");
			}
                }	
                else if (display.equals(HydroBase_GUI_Options.DECREED_AMT)) {
			curDouble = netAmt.getNet_rate_abs();
			if (DMIUtil.isMissing(curDouble)) {
	                    	curString = "";
			}
			else {
				curString = StringUtil.formatString(
					curDouble, "%11.5f");
			}                
		}	

		__callsJList.add(curString.trim());
		__calls.add(netAmt);
	}
}

/**
Displays the WIS Structures.
*/
private void displayWISStructures() {
	String routine = "HydroBase_GUI_EditCallsBypass.displayWISStructures";
	__structureJList.removeAll();

	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Retrieving structure list...");
	__structures = __dmi.getWISStructuresVector();	
	if (__structures == null) {
		try {
			__structures = __dmi.readWISStructuresList();
		}
		catch (Exception e) {	
			Message.printWarning(1, routine, "Error reading "
				+ "structures from database.");
			Message.printWarning(2, routine, e);
			ready();
			return;		
		}
	}
	if (__structures == null) {
		JGUIUtil.setWaitCursor(this, false);
		__statusJTextField.setText("No structures to list.");
		return;
	}
	
	HydroBase_StructureView view = null;
	String curString = "";
	String name = "";
	String id = "";
	String wd = "";
	int curInt;
	List<String> data = new Vector<String>();
	
	if (!__structures.isEmpty()) {
		int size = __structures.size();
		for (int i = 0; i < size; i++) {
			view = __structures.get(i);
			name = "" + view.getStr_name().trim();
			curInt = view.getWD();
			if (DMIUtil.isMissing(curInt)) {
				wd = "";
			}
			else {
				wd = "" + curInt;
			}

			curInt = view.getID();
			if (DMIUtil.isMissing(curInt)) {
				id = "";
			}
			else {
				id = "" + curInt;
			}
			curString = "" 
				+ HydroBase_WaterDistrict.formWDID(wd, 
				id);
			data.add(curString + " - " + name);	
		}
	}

	java.util.Collections.sort(data);
	java.util.Collections.sort(__structures);
	__structureJList.setListData(data);

	__parent.setHaveGeneratedBYPASSStructures(true);
	JGUIUtil.setWaitCursor(this, false);
	__statusJTextField.setText("Ready");
}

/**
Cleans up.
*/
public void finalize()
throws Throwable {
	__parent = null;
	__dmi = null;
	__cancelJButton = null;
	__okJButton = null;
	__callJLabel = null;
	__adjDateJTextField = null;
	__adminJTextField = null;
	__aproDateJTextField = null;
	__callsJTextField = null;
	__decreedJTextField = null;
	__priorNumJTextField = null;
	__statusJTextField = null;
	__callsJList = null;
	__structureJList = null;
	__calls = null;
	__structures = null;
	__textFieldVector = null;
	__structureJPopupMenu = null;
	super.finalize();
}

/**
Returns the JTextField object at the requested location within the 
__textFieldVector object.
@param element the location within the vector.
@return the JTextField object at the specified location.
*/
private JTextField getJTextField(int element) {
	return((JTextField)__textFieldVector.get(element));
}

/**
Handle mouse clicked event.
*/
public void mouseClicked(MouseEvent event) {}

/**
Handle mouse entered event.
*/
public void mouseEntered(MouseEvent event) {}

/**
Handle mouse exited event.
*/
public void mouseExited(MouseEvent event) {}

/**
Handle mouse pressed event.
*/
public void mousePressed(MouseEvent event) {
	int mods = event.getModifiers();
	Component c = event.getComponent();
	
	if (c.equals(__structureJList) && (__structureJList.getItemCount() > 0)
	    && ((mods & MouseEvent.BUTTON3_MASK) != 0)) {
		__structureJPopupMenu.show(
			event.getComponent(), event.getX(), event.getY());
	}
}

/**
Handle mouse released event; does nothing.
@param event the MouseEvent that happened.
*/
public void mouseReleased(MouseEvent event) {}

/**
Responds to the ok button being clicked.  Information set with this GUI is
posted in the appropriate objects in the parent JFrame.
*/
private void okClicked() {
	String routine = "HydroBase_GUI_EditCallsBypass.okClicked()";
	int call_index = __callsJList.getSelectedIndex();

	// must select a call
	if (__callsJTextField.getText().trim().equals("")) {
		Message.printWarning(1, routine, "Must select a call.");
		return;
	}
	
	String tempString = "Please wait...Updating Structure Data";
	JGUIUtil.setWaitCursor(this, true);
	Message.printStatus(2, routine, tempString);
	__statusJTextField.setText(tempString);

	// set the BYPASS information
	__parent.setBYPASSInfo(
		(HydroBase_NetAmts)__calls.get(call_index));
	// post information in the appropriate JTextFields 
	// on the __parent object	
	JTextField text = getJTextField(HydroBase_GUI_SetCall.ADMIN_NO);
	text.setText(__adminJTextField.getText().trim());

	text = getJTextField(HydroBase_GUI_SetCall.APPRO_DATE);
	text.setText(__aproDateJTextField.getText().trim());

	text = getJTextField(HydroBase_GUI_SetCall.DECREED_AMT);
	text.setText(__decreedJTextField.getText().trim());

	text = getJTextField(HydroBase_GUI_SetCall.ADJ_DATE);
	text.setText(__adjDateJTextField.getText().trim());

	text = getJTextField(HydroBase_GUI_SetCall.PRIORITY_NUMBER);
	text.setText(__priorNumJTextField.getText().trim());

	text = getJTextField(HydroBase_GUI_SetCall.CALL);
	text.setText(__callsJTextField.getText().trim());

	tempString = "Bypass call from " 
		+ ((String)__structureJList.getSelectedItem()).trim();
	text = getJTextField(HydroBase_GUI_SetCall.SET_COMMENT);
	text.setText(tempString);

	__parent.setCallDescriptionJLabel(true, 
		((String)__structureJList.getSelectedItem()).trim());

	tempString = "Finished updating structure data.";
	JGUIUtil.setWaitCursor(this, false);
	Message.printStatus(2, routine, tempString);
	__statusJTextField.setText(tempString);
	closeClicked();
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
public void setupGUI() {
	addWindowListener(this);

	// objects used throughout the GUI layout
	Insets insetsNLNR = new Insets(0,7,0,7);
        Insets insetsNLBR = new Insets(0,7,7,7);
       	GridBagLayout gbl = new GridBagLayout();

	// Top JPanel
	JPanel topJPanel = new JPanel();
       	topJPanel.setLayout(new BorderLayout());
	getContentPane().add("Center", topJPanel);

	// Top: West JPanel
	JPanel topWJPanel = new JPanel();
       	topWJPanel.setLayout(gbl);
       	topJPanel.add("West", topWJPanel);

	int y = 0;

    	JGUIUtil.addComponent(topWJPanel, 
		new JLabel("Bypass Structure from Water Information Sheets:"),
		0, y, 3, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__callJLabel = new JLabel("Available Calls:");
    	JGUIUtil.addComponent(topWJPanel, __callJLabel, 
		3, y, 2, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	y++;

	__structureJLabel = new JLabel("[No Structure currently selected]");
	JGUIUtil.addComponent(topWJPanel, __structureJLabel, 
		0, y, 3, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__structureJPopupMenu = new JPopupMenu("Structures");
	__structureJPopupMenu.add(new SimpleJMenuItem(
		__MENU_FIND_STRUCTURE, __MENU_FIND_STRUCTURE, this));

	y++;

	__structureJList = new SimpleJList();
	__structureJList.addListSelectionListener(this);
	__structureJList.addMouseListener(this);
	__structureJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JGUIUtil.addComponent(topWJPanel, new JScrollPane(__structureJList),
		0, y, 3, 2, 1, 1, insetsNLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);	
	
	__callsJTextField = new JTextField();
	__callsJTextField.setEditable(false);
    	JGUIUtil.addComponent(topWJPanel, __callsJTextField, 
		3, y, 2, 1, 1, 0, insetsNLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);	

	y++;

	__callsJList = new SimpleJList();
	__callsJList.addListSelectionListener(this);
	__callsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JGUIUtil.addComponent(topWJPanel, new JScrollPane(__callsJList), 
		3, y, 2, 1, 1, 1, insetsNLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);	

	y++;

	__selectStructureJButton = new JButton(__BUTTON_SELECT_STRUCTURE);
	__selectStructureJButton.addActionListener(this);
	__selectStructureJButton.setToolTipText("Read data from database.");
	JGUIUtil.addComponent(topWJPanel, __selectStructureJButton,
		0, y, 2, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	y++;

    	JGUIUtil.addComponent(topWJPanel, new JLabel("Admin. No."), 
		0, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);	

    	JGUIUtil.addComponent(topWJPanel, new JLabel("Appro. Date"), 
		1, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);	

    	JGUIUtil.addComponent(topWJPanel, new JLabel("Decreed Amt."), 
		2, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);	

    	JGUIUtil.addComponent(topWJPanel, new JLabel("Adjudication. Date"), 
		3, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);	

    	JGUIUtil.addComponent(topWJPanel, new JLabel("Priority Number"), 
		4, y, 2, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	y++;

	__adminJTextField = new JTextField(10);
	__adminJTextField.setEditable(false);		
    	JGUIUtil.addComponent(topWJPanel, __adminJTextField, 
		0, y, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);	

	__aproDateJTextField = new JTextField(15);		
	__aproDateJTextField.setEditable(false);
    	JGUIUtil.addComponent(topWJPanel, __aproDateJTextField, 
		1, y, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);	

	__decreedJTextField = new JTextField(15);	
	__decreedJTextField.setEditable(false);	
    	JGUIUtil.addComponent(topWJPanel, __decreedJTextField, 
		2, y, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);	

	__adjDateJTextField = new JTextField(15);	
	__adjDateJTextField.setEditable(false);	
    	JGUIUtil.addComponent(topWJPanel, __adjDateJTextField, 
		3, y, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);	

	__priorNumJTextField = new JTextField(15);
	__priorNumJTextField.setEditable(false);		
    	JGUIUtil.addComponent(topWJPanel, __priorNumJTextField, 
		4, y, 2, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);	
		               
	// Bottom JPanel
	JPanel bottomJPanel = new JPanel();
	bottomJPanel.setLayout(new BorderLayout());
	getContentPane().add("South", bottomJPanel);

	// Bottom: North JPanel
	JPanel bottomNJPanel = new JPanel();
	bottomNJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
       	bottomJPanel.add("North", bottomNJPanel);

	__okJButton = new JButton(__BUTTON_OK);
	__okJButton.setToolTipText("Accept changes.");
	__okJButton.addActionListener(this);
	__okJButton.setEnabled(false);
	bottomNJPanel.add(__okJButton);

	__cancelJButton = new JButton(__BUTTON_CANCEL);
	__cancelJButton.setToolTipText("Discard changes.");
	__cancelJButton.addActionListener(this);
	bottomNJPanel.add(__cancelJButton);

	// Bottom JPanel: South
	JPanel bottomSJPanel = new JPanel();
	bottomSJPanel.setLayout(gbl);
       	bottomJPanel.add("South", bottomSJPanel);

        __statusJTextField = new JTextField();
	__statusJTextField.setEditable(false);
       	JGUIUtil.addComponent(bottomSJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// frame settings
	String rest = "Calls - Bypass Structure Data";
	if ((JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("")) {
		setTitle(rest);
	}
	else {	
		setTitle(JGUIUtil.getAppNameForWindows() + " - " + rest);
	}									
	
	setTitle("Bypass Structure");
	pack();
	setSize(getWidth(), getHeight() + 20);
	JGUIUtil.center(this);
	setVisible(true);
}

/**
Shows/hides the GUI
@param state true if it should be made visible, false if it should be made
invisible.
*/
public void setVisible(boolean state) {
	super.setVisible(state);
	if (state) {
		Message.setTopLevel(this);
	
		// clear objects and reset member variables
		__curStructureRow = -999;
		__callsJList.removeAll();

		clearCallFields();
		
		// If the structureJList has more than 0 items at this point,
		// this is the 2+ time that this GUI was made visible.  
		// WISStructures won't be displayed (see a few lines down),
		// so the calls list will be filled out to look good.
		if (__structureJList.getItemCount() > 0) {
			__structureJList.select(0);
		}

		String display=__dmi.getPreferenceValue("General.CallingRight");
		__callJLabel.setText("Available Calls (using " + display +"):");

		// display the WIS Structures
		if (!__parent.haveGeneratedBypassStructures()) {
			__calls = new Vector<HydroBase_NetAmts>();
			__structures = new Vector<HydroBase_StructureView>();
			// REVISIT (JTS - 2005-08-03)
			// in future this may be changed so that something 
			// is done similar to the main calls list and other
			// structures are displayed too.
			displayWISStructures();
		}
	}
}

/**
Called when the select structure button is pressed.  Performs a Structure query.
*/
private void structureQueryClicked() {
	String routine = "HydroBase_GUI_EditCallsBypass.structureQueryClicked";

	if (__callStructureQueryGUI == null) {
		try {
			__callStructureQueryGUI = 
				new HydroBase_GUI_StructureQuery(__dmi, 
				__main_parent, __geoview_ui, true);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Couldn't open "
				+ "HydroBase_GUI_StructureQuery.");
			Message.printWarning(2, routine, e);
			return;
		}
	}
	else {
		__callStructureQueryGUI.setVisible(true);
	}
	__callStructureQueryGUI.displayStructsOnly(this);
}

/**
Called when the structure window is closed. 
*/
public void structureWindowClosed(HydroBase_StructureView newStruct) {
	String routine = "HydroBase_GUI_SetCall.structureWindowClosed";

	// get the selected structure from the structure GUI
	if (__callStructureQueryGUI == null) {
		Message.printWarning(1, routine, "GUI is null");
		return;
	}

	if (newStruct != null) {
		structureAddition(newStruct);
	}
	else {
		// new_struct is null
		Message.printWarning(1, routine, 
			"Not adding a structure to the call list because " +
			"you must select a single structure.");
	}
}

private void structureAddition(HydroBase_StructureView newCall) {
	HydroBase_StructureView view = null;
	String routine = "HydroBase_GUI_SetCall.structureAddition()";
	int insertIndex = 0;
	String new_name = newCall.getStr_name();
	int new_wd = newCall.getWD(), old_wd;

	String wd;
	String id;
	String name;
	int comparison;
	if (__structures == null) {
		__structures = new Vector<HydroBase_StructureView>(1);
		__structures.add(newCall);
	}
	else {
		int size = __structures.size();

		for (int i = 0; i < size; i++) {
			view = (HydroBase_StructureView)
				__structures.get(i);
			old_wd = view.getWD();
			if (new_wd > old_wd) {
				insertIndex++;
				continue;
			}
			if (new_wd < old_wd) 
				break;
				// must be only 1 in this wd, insert

			// otherwise, we have found the appropriate wd, now
			// let's insert based on the name
			name = "" + view.getStr_name().trim();

			comparison = name.compareTo(new_name);
			if (comparison == 0) {
				Message.printWarning(1, routine, 
					"Can't add structure \"" + new_name 
					+ "\" because it already exists "
					+ "in current list.");
				return;
			}
			else if (comparison < 0) {
				insertIndex++;
			}
			else {
				// insertion point has been found
				break;
			}
		}

		__structures.add(insertIndex,newCall );
	}

	// get water district
	int curInt = newCall.getWD();
	if (DMIUtil.isMissing(curInt)) {
		wd = "";
	}
	else {
		wd = "" + curInt;
	}

	// structure id
        curInt = newCall.getID();
        if (DMIUtil.isMissing(curInt)) {
        	id = "";
       	}
       	else {
       		id = "" + curInt;
       	}

       	String curString = HydroBase_WaterDistrict.formWDID(wd, id);

	__structureJList.add(curString + " - " + new_name, insertIndex);	
	__structureJList.select(insertIndex);
	__structureJList.ensureIndexIsVisible(insertIndex);
	structureListClicked();
}

/**
Responds to the __structureJList LIST_SELECT event and constructs and submits
a query based on the structure selected in the object.
*/
private void structureListClicked() {
	String routine = "HBEditCalls.structureListClicked()";

	// return if no items are present in the __structureJList object
	if (__structureJList.getItemCount() == 0) {
		return;
	}
	
	// return if the same row is selected
	int index = __structureJList.getSelectedIndex();
	if (index == __curStructureRow) {
		return;
	}
	__curStructureRow = index;

	// clear out all available call information
	clearCallFields();
	__calls.clear();
	__callsJList.removeAll();

	String tempString = 
		"Please Wait...Retrieving Water Right Net Amounts Data";
	JGUIUtil.setWaitCursor(this, true);
	Message.printStatus(2, routine, tempString);
	__statusJTextField.setText(tempString);

	// get the unique structure_num from the selected structure
	if (index == -1) {
		// nothing was selected -- the only selected row was
		// DEselected
		__structureJLabel.setText("[No Structure currently selected]");
		__callsJList.clearSelection();
		ready();
		return;
	}

	List<HydroBase_NetAmts> results = null;

	HydroBase_StructureView view = __structures.get(index);

	__structureJLabel.setText("" + __structureJList.getSelectedItem());

	try {
		results = __dmi.readNetAmtsList(
			view.getStructure_num(), -999, -999, 
			false, "72");
	}
	catch (Exception e) {
		Message.printWarning(1, routine, 
			"Error reading from the database.");
		Message.printWarning(1, routine, e);
		results = null;
	}
	
	if (results != null) {
		if (!results.isEmpty()) {
			displayWaterRightNetResults(results);
		}
	}

	tempString = "Finished Retrieving Water Right Net Amounts Data.";
	JGUIUtil.setWaitCursor(this, false);
	Message.printStatus(2, routine, tempString);
	__statusJTextField.setText(tempString);
}

/**
Responds when a new value is selected on the JLists.
@param e the ListSelectionEvent that happened.
*/
public void valueChanged(ListSelectionEvent e) {
	Object o = e.getSource();

	if (o.equals(__structureJList)) {
		structureListClicked();
	}	
	else if (o.equals(__callsJList)) {
		callsJListClicked();
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
