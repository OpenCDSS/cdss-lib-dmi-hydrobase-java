//-----------------------------------------------------------------------------
// HydroBase_GUI_WISImportWizard - Adds Import sources to the 
//	HydroBase_GUI_WISBuilder template.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History: 
//
// 11 Jul 1998	CGB, RTi		Created initial class description.
// 03 Apr 1999	SAM, RTi		Add HBDMI to all queries.
// 2001-11-12	SAM, RTi		Change GUI to JGUIUtil.
//					Remove import *.
//-----------------------------------------------------------------------------
// 2003-11-28	J. Thomas Sapienza, RTi	Initial Swing version.
// 2003-12-01	JTS, RTi		Completed initial version.
// 2004-01-21	JTS, RTi		Changed to use the new JWorksheet method
//					of displaying a row count column.
// 2005-03-09	JTS, RTi		HydroBase_SheetName 	
//					  -> HydroBase_WISSheetName.
// 2005-04-12	JTS, RTi		MutableJList changed to SimpleJList.
// 2005-04-28	JTS, RTi		Added finalize().
// 2005-05-03	JTS, RTi		Table model now takes a dmi reference.
// 2005-05-09	JTS, RTi		Only HydroBase_StationView objects are
//					returned from station queries now.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
// 2005-06-28	JTS, RTi		Removed DMI parameter from table model.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWizard;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.SimpleJList;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
This class is a wizard for choosing what import data to use in a WIS.
*/
public class HydroBase_GUI_WISImportWizard 
extends JWizard 
implements ItemListener, ListSelectionListener, MouseListener {

/**
Class name.
*/
private final static String __CLASS = "HydroBase_GUI_WISImportWizard";

/**
Static data to hold military times from 0000 to 2300.
*/
private final static int[] __MIL_TIMES = new int[25];

/**
Used to refer to the kind of worksheet that is currently being displayed.
*/
private final int 
	__REAL_TIME = 0,
	__WIS = 2;

/**
General purpose Strings.
*/
public final static String
	FRAME_TITLE = 	"WIS Import Selection Wizard",
	RT_TITLE = 	"Real Time Satellite",
	WIS_TITLE = 	"Water Information Sheet",
	RT = 		"Real Time Satellite Data",
	WIS = 		"Water Information Sheet Data",
	CURRENT = 	"Most Recent Transmission",
	AVERAGE = 	"Average",
	AVE = 		"AVE",
	MINIMUM = 	"Minimum",
	MIN = 		"Min",
	MAXIMUM = 	"Maximum",
	MAX = 		"Max";

/**
Whether to ignore item state changes or not.
*/
private boolean __ignoreStateChange = true;

/**
Whether the wis has been retrieved or not.
*/
private boolean __retrievedWIS;

/**
Card layout for the wizard.
*/
private	CardLayout __wizardCard;

/**
The dmi to use for communicating with the database.
*/
private HydroBaseDMI __dmi;

/**
The WISBuilder GUI that instantiated this wizard.
*/
private HydroBase_GUI_WISBuilder __parentGUI;

/**
Column and row in which the import is being built on the WISBuilder.
*/
private	int
	__col,
	__row;

/**
The type of worksheet currently being displayed.
*/
private int __type = -1;

/**
JPanel for the wizard.
*/
private	JPanel __wizardJPanel;

/**
Worksheets for displaying tabular data.
*/
private JWorksheet
	__rtWorksheet,
	__wisWorksheet;

/**
Data source list.
*/
private SimpleJList __sourceList;

/**
GUI combo boxes.
*/
private SimpleJComboBox	
	__rtEndSimpleJComboBox,
	__rtOffsetSimpleJComboBox,
	__rtWDSimpleJComboBox,
	__wisDataSimpleJComboBox,
	__wisNameSimpleJComboBox;

/**
Vectors to hold data objects.
*/
private	List
	__infoVector,
	__wisVector;

/**
Static initialization of the military times values.
*/
static {
	int value = 0;
	for (int i = 0; i < 25; i++) {
		__MIL_TIMES[i] = value;
		value += 100;
	}	
}
	
/**
Constructor.
@param dmi the DMI with which to communicate with the database.
@param parent_gui the parent WISBuilder GUI that instantiated this GUI.
@param station_num the station_num for to build the import.
@param row the row of the cell for the import.
@param col the col of the cell for the import.
@param wisImport the wisImport to build.
*/
public HydroBase_GUI_WISImportWizard(HydroBaseDMI dmi, 
HydroBase_GUI_WISBuilder parent_gui, long station_num, int row, int col, 
HydroBase_WISImport wisImport) {
	super(); 

	__dmi = dmi;
	__parentGUI = parent_gui;
	__row = row;
	__col = col;
	__infoVector = new Vector();
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();
}

/**
Responds to button press action events.
@param event the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent event) {
	String action = event.getActionCommand();

	if (action.equals(BUTTON_CANCEL)) {
		cancelClicked();
	}
	else if (action.equals(BUTTON_BACK)) {
		backClicked();
	}
	else if (action.equals(BUTTON_NEXT)) {
		nextClicked();
	}
	else if (action.equals(BUTTON_FINISH)) {
		finishClicked();
	}
}

/**
Called when the back button is pressed.
@return true.
*/
protected boolean backClicked() {
	super.backClicked();
	__type = -1;
	showJPanel();
	return true;
}

/**
Called when the cancel button is pressed.
@return true.
*/
protected boolean cancelClicked() {
	super.setVisible(false);
	return true;
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__wizardCard = null;
	__dmi = null;
	__parentGUI = null;
	__wizardJPanel = null;
	__rtWorksheet = null;
	__wisWorksheet = null;
	__sourceList = null;
	__rtEndSimpleJComboBox = null;
	__rtOffsetSimpleJComboBox = null;
	__rtWDSimpleJComboBox = null;
	__wisDataSimpleJComboBox = null;
	__wisNameSimpleJComboBox = null;
	__infoVector = null;
	__wisVector = null;
	super.finalize();
}

/**
Responds when the OK button is pressed.  Sets the import information in the
falling GUI.
@return true if the finish button could be pressed successfully.  False if 
the action could not complete.
*/
protected boolean finishClicked() {
	int curRow = 0;
	int offset = DMIUtil.MISSING_INT;
	int end = DMIUtil.MISSING_INT;
        int meas_num = DMIUtil.MISSING_INT;
	int importWISNum = DMIUtil.MISSING_INT;
	String importMethod = "AVE";
	String importIdentifier = DMIUtil.MISSING_STRING;
	String importColumn = DMIUtil.MISSING_STRING;
	String importMeas_desc = DMIUtil.MISSING_STRING;

	String dataSource = ((String)__sourceList.getSelectedItem()).trim();
	HydroBase_WISImport wisImport = new HydroBase_WISImport();

        if (dataSource.equals(WIS)) {
		curRow = __wisWorksheet.getSelectedRow();
		if (curRow == -1) {
			new ResponseJDialog(this, "Must select a row"
				+ " label in Step 4.", 
				ResponseJDialog.OK).response();
			setReady(__statusJTextField);
			return false;
		}

		// start with identification
		importIdentifier = "WIS.";
                importIdentifier += __wisWorksheet.getValueAt(curRow, 1);
                importWISNum = ((Integer)
			__wisWorksheet.getValueAt(curRow, 2)).intValue();
		importColumn = __wisDataSimpleJComboBox.getSelected().trim();
	}
        else if (dataSource.equals(RT)) {
		curRow = __rtWorksheet.getSelectedRow();
		if (curRow == -1) {
			new ResponseJDialog(this, "Must select a station"
				+ " in Step 5.", 
				ResponseJDialog.OK).response();
			setReady(__statusJTextField);		
			return false;
		}

		HydroBase_StationView view = 
			(HydroBase_StationView)
			__rtWorksheet.getRowData(curRow);
                meas_num = view.getMeas_num();
	
		String endString = __rtEndSimpleJComboBox.getSelected().trim();
		if (!(endString.equals(CURRENT))) {
			end = StringUtil.atoi(endString);
			String offString = 
				__rtOffsetSimpleJComboBox.getSelected().trim();
			offset =  StringUtil.atoi(offString);
		}
	}

	// Fill in the WISImport fields.
	wisImport.setColumn(HydroBase_GUI_WISBuilder.getColumnType(__col));
	wisImport.setEnd_time(end);
	wisImport.setTime_offset(offset);
	wisImport.setMeas_num(meas_num);

	// For now, hardwire the parameter for determining the method.
	wisImport.setImport_method(importMethod);
	wisImport.setImport_wis_num(importWISNum);
	wisImport.setImport_identifier(importIdentifier);
	wisImport.setImport_column(importColumn);
	wisImport.setImport_meas_desc(importMeas_desc);

	__parentGUI.updateCellImport(__row, __col, wisImport);

	cancelClicked();
	setReady(__statusJTextField);

	return true;
}

/**
Generates the list of wis sheets.
*/
private void generateWISSheets() {
	String routine = __CLASS + ".generateWISSheets";
	// returned if a list of wis has already been retrieved
	if (__retrievedWIS) {
		return;
	}

        JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Retrieving wis...");
	__wisNameSimpleJComboBox.removeAll();

	// get the water districts as selected from user preferences
	List wd = HydroBase_GUI_Util.generateWaterDistricts(__dmi);

	List wds = new Vector();
	// initialize variables
	if (wd != null && wd.size() > 0) {
		int size = wd.size();
		for (int count = 0; count < size; count++) {
			wds.add("" +  wd.get(count));
		}
	}
	
	List results = null;
	try {
		results = __dmi.readWISSheetNameList(wds, true);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading sheet names.");
		Message.printWarning(2, routine, e);
	}

	if (results == null || results.size() == 0) {
		setReady(__statusJTextField);
		return;
	}

	// display sheets associated with the selected water districts using
	// the most "current" format (i.e., most recent effective_date)
	// the following loop exploits the order of the data and places
	// the sheet name and most recent date into the SimpleJComboBox object 
	// and Vector accordingly.
	int size = results.size();
	__wisVector.clear();
	HydroBase_WISSheetName wis = null;
	String recentSheet = null;
	String curSheet = null;
	for (int count = 0; count < size; count++) {
		wis = (HydroBase_WISSheetName)results.get(count);

		curSheet = wis.getSheet_name();
		
		if (!curSheet.equals(recentSheet)) {
			__wisNameSimpleJComboBox.add(curSheet);
			__wisVector.add(wis);
			recentSheet = curSheet;
		}
	}

	__retrievedWIS = true;
	setReady(__statusJTextField);
	
	String pref = __dmi.getPreferenceValue("WIS.WISdefault");
	if (pref == null || pref.equalsIgnoreCase("NONE")) {
		__wisNameSimpleJComboBox.select(0);
	}
	else {
		__wisNameSimpleJComboBox.select(pref);
	}
}

/**
Returns the current information string based on the state and index.
@return the current information string based on the state and index.
*/
private String getInfoString() {
	String s = "";

	// return the correct info string based on wizard step.
	int i = getWizardStep();
	s = (String)__infoVector.get(i - 1);

	return s;
}

/**
Initializes data members.
*/
private void initialize() {	   
	__wisVector = new Vector();
	__retrievedWIS = false;

	initializeGUI();
}

/**
Shows the correct panel based on the type of wizard.
*/
private void initializeGUI() {	   
       	__wizardCard.show(__wizardJPanel, "Step 1");
	setTotalSteps(2);
	setWizardStep(1);
	super.setVisible(true);
	super.setInfoString(getInfoString());
}

/**
Responds to item state changed events.
@param event the event that happened.
*/
public void itemStateChanged(ItemEvent event) {
	if (__ignoreStateChange) {
		return;
	}

	Object o = event.getItemSelectable();

	if (event.getStateChange() != ItemEvent.SELECTED) {
		return;
	}

	__ignoreStateChange = true;
	if (o.equals(__rtWDSimpleJComboBox)) {
		submitRTQuery();
	}
	// enable and disable the offset choice based on the user
	// selecting a choice of the Real Time End time.
	else if (o.equals(__rtEndSimpleJComboBox)) {
		String s = __rtEndSimpleJComboBox.getSelected();
		if (s.equals(CURRENT)) {
			__rtOffsetSimpleJComboBox.setEnabled(false);
			__rtOffsetSimpleJComboBox.setEditable(false);
		}
		else {
			__rtOffsetSimpleJComboBox.setEnabled(true);
			__rtOffsetSimpleJComboBox.setEditable(true);
		}
		submitRTQuery();
	}
	else if (o.equals(__rtOffsetSimpleJComboBox)) {
		submitRTQuery();
	}

	else if (o.equals(__wisNameSimpleJComboBox)) {
		submitWISQuery();
	}
	else if (o.equals(__wisDataSimpleJComboBox)) {
		submitWISQuery();
	}
	
	__ignoreStateChange = false;
}

/**
Responds to mouse clicks.  If double-clicking on the list, treats it as a 
call to nextClicked().
@param event the MouseEvent that happened.
*/
public void mouseClicked(MouseEvent event) {
	if (event.getSource() == __sourceList) {
		if (event.getClickCount() > 1) {
			nextClicked();
		}
	}
}

/**
Does nothing.
*/
public void mouseEntered(MouseEvent event) {}

/**
Does nothing.
*/
public void mouseExited(MouseEvent event) {}

/**
Responds to mouse presses.  If pressing on a worksheet, will determine whether
the finish button needs to be enabled or not.
@param event the MouseEvent that happened.
*/
public void mousePressed(MouseEvent event) {
	if (event.getSource() == __sourceList) {
		return;
	}

	if (__type > -1) {
		setButtonState();	
	}
}

/**
Responds to mouse releases.  If released on a worksheet, will determine whether
the finish button needs to be enabled or not.
@param event the MouseEvent that happened.
*/
public void mouseReleased(MouseEvent event) {
	if (event.getSource() == __sourceList) {
		return;
	}

	if (__type > -1) {
		setButtonState();
	}
}

/**
Called when the next button is pressed.
@return false if there was a problem going to the next screen.  Otherwise, true.
*/
protected boolean nextClicked() {
	String 	s;
	int	step;

	// initialize variables
	s = ((String)__sourceList.getSelectedItem());
	step = getWizardStep();

	// ensure that a data source is selected
	int row = -1;
	if (step == 1) {
		if (s == null) {
			new ResponseJDialog(this, "Must select an "
				+ "Import Source.",
				ResponseJDialog.OK).response();
			return false;
		}
		
		// automatically generate queries using defaults
		if (s.equals(RT)) {
			__type = __REAL_TIME;
			submitRTQuery();
			row = __rtWorksheet.getSelectedRow();
		}
		else if (s.equals(WIS)) {
			__type = __WIS;
			submitWISQuery();
			row = __wisWorksheet.getSelectedRow();
		}	
	}

	// this should be here so that the wizard step is incremented only if
	// an error does not occur.
	super.nextClicked();

	if (row == -1) {
		setFinishEnabled(false);
	}
	else {
		setFinishEnabled(true);
	}
	
	showJPanel();
	return true;
}
 
/**
Determines, based on the current worksheet and whether any of its rows are
selected, whether to enable or disable the finish button.
*/
private void setButtonState() {
	int row = -1;
	if (__type == __REAL_TIME) {
		row = __rtWorksheet.getSelectedRow();
	}
	else if (__type == __WIS) {
		row = __wisWorksheet.getSelectedRow();
	}
	if (row == -1) {
		setFinishEnabled(false);
	}
	else {
		setFinishEnabled(true);
	}
}

/**
Shows the appropriate wizard panel.
*/
private void showJPanel() {
	int step = getWizardStep();
	String panel = "";
	
	if (step == 1) {
		panel = "Step 1";
	}
	else if (step == 2) {
		String s = ((String)__sourceList.getSelectedItem()).trim();
		panel = "Step 2 " + s;
	}
	else if (step == 3) {
		panel = "Step 3";
	}

     	__wizardCard.show(__wizardJPanel, panel);
	super.setInfoString(getInfoString());
}

/**
Sets the GUI ready.
@param tf the status text field.
*/
private void setReady(JTextField tf) {
	if (tf != null) {
		tf.setText("Ready");
	}
	JGUIUtil.setWaitCursor(this, false);
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	String routine = __CLASS + ".setupGUI";
	// objects to be used in the GUI layout
	GridBagLayout gbl = new GridBagLayout();
	Insets TNNN = new Insets(7,0,0,0);
	Insets NLNR = new Insets(0,7,0,7);
	Insets TLNR = new Insets(7,7,0,7);
	__wizardCard = new CardLayout();

        __wizardJPanel = new JPanel();
        __wizardJPanel.setLayout(__wizardCard);
	getContentPane().add("Center", __wizardJPanel);

	//---------------------------------------------------------------------
	// Step 1 JPanel
	//---------------------------------------------------------------------
	String info = "The WIS Import Selection Wizard takes you through the "
		+ "process of\nimporting a Data Source into the Water "
		+ "Information Sheet. Select from\nthe available Data Sources "
		+ "displayed in the list and then proceed.\n";
	__infoVector.add(info);

	JPanel step1JPanel = new JPanel();
	step1JPanel.setLayout(gbl);
	__wizardJPanel.add("Step 1", step1JPanel);
	
	JGUIUtil.addComponent(step1JPanel, 
		new JLabel("Available Import Sources:"), 
		0, 0, 1, 1, 0, 0, NLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);

	__sourceList = new SimpleJList();
	__sourceList.addMouseListener(this);
	__sourceList.add(RT);
	__sourceList.add(WIS);
	__sourceList.addListSelectionListener(this);
	JGUIUtil.addComponent(step1JPanel, new JScrollPane(__sourceList), 
		0, 1, 1, 1, 1, 0, NLNR, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);

	//---------------------------------------------------------------------
	// Step 2: RT JPanel
	//---------------------------------------------------------------------
	info = "Select a Data Source from the list and then proceed.\n";
	__infoVector.add(info);

	JPanel step2_RTJPanel = new JPanel();
	step2_RTJPanel.setLayout(gbl);
	__wizardJPanel.add("Step 2 " + RT, step2_RTJPanel);
	
	JGUIUtil.addComponent(step2_RTJPanel, 
		new JLabel("Step 1: Select a desired Reference Time"), 
		0, 0, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__rtEndSimpleJComboBox = new SimpleJComboBox();
	__rtEndSimpleJComboBox.add(CURRENT);
	int to = __MIL_TIMES.length - 1;
	String s = null;
	for (int i = 0; i < to; i++) {
		s = new Integer(__MIL_TIMES[i]).toString();
		if (s.length() == 3) {
			s = "0" + s;
		}
		else if (s.length() == 1) {
			s = "000" + s;
		}
		__rtEndSimpleJComboBox.add(s);
	}
	__rtEndSimpleJComboBox.select(0);	// select the first item
	JGUIUtil.addComponent(step2_RTJPanel, 
		__rtEndSimpleJComboBox, 
		1, 0, 1, 1, 0, 0, NLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
	__rtEndSimpleJComboBox.addItemListener(this);

	JGUIUtil.addComponent(step2_RTJPanel, 
		new JLabel("Step 2: Number of hours to include in the average"),
		0, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__rtOffsetSimpleJComboBox = new SimpleJComboBox();
	for (int i = 0; i < __MIL_TIMES.length; i++) {
		s = new Integer(__MIL_TIMES[i]).toString();
		if (s.length() == 3) {
			s = "0" + s;
		}		
		else if (s.length() == 1) {
			s = "000" + s;
		}		
		__rtOffsetSimpleJComboBox.add(s);
	}
	__rtOffsetSimpleJComboBox.select(0);	// select the first item
	JGUIUtil.addComponent(step2_RTJPanel, 
		__rtOffsetSimpleJComboBox, 
		1, 1, 1, 1, 0, 0, NLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
	__rtOffsetSimpleJComboBox.setEnabled(false);
	__rtOffsetSimpleJComboBox.setEditable(false);
	__rtOffsetSimpleJComboBox.addItemListener(this);

	JGUIUtil.addComponent(step2_RTJPanel, 
		new JLabel("Step 3: Select the desired Water "
		+ "Divisision/District"), 
		0, 2, 1, 1, 0, 0, TNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__rtWDSimpleJComboBox = new SimpleJComboBox();
	__rtWDSimpleJComboBox.addItemListener(this);

	// set the items from user preferences for division/district
	try {
		HydroBase_GUI_Util.setWaterDistrictJComboBox(__dmi,
			__rtWDSimpleJComboBox, null, true, false, false);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error setting up "
			+ "water district combo box.");
		Message.printWarning(2, routine, e);
	}

	JGUIUtil.addComponent(step2_RTJPanel, 
		__rtWDSimpleJComboBox, 
		1, 2, 1, 1, 0, 0, TLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

	JGUIUtil.addComponent(step2_RTJPanel, 
		new JLabel("Step 4: Select the correct Station"), 
		0, 3, 2, 1, 0, 0, TNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	PropList p = new PropList("HydroBase_GUI_WISImportWizard.JWorksheet");
/*
	p.add("JWorksheet.CellFont=Courier");
	p.add("JWorksheet.CellStyle=Plain");
	p.add("JWorksheet.CellSize=11");
	p.add("JWorksheet.HeaderFont=Arial");
	p.add("JWorksheet.HeaderStyle=Plain");
	p.add("JWorksheet.HeaderSize=11");
	p.add("JWorksheet.HeaderBackground=LightGray");
	p.add("JWorksheet.RowColumnPresent=false");
	p.add("JWorksheet.RowColumnBackground=LightGray");
*/
	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.SelectionMode=SingleRowSelection");
	p.add("JWorksheet.ShowPopupMenu=true");	

	int[] rtWidths = null;
	JScrollWorksheet rtJSW = null;
	try {
		HydroBase_TableModel_WISImportWizard tm = 
			new HydroBase_TableModel_WISImportWizard(new Vector(), 
			HydroBase_TableModel_WISImportWizard
			.STATION_GEOLOC_MEAS_TYPE);
		rtWidths = tm.getColumnWidths();
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
		
		rtJSW = new JScrollWorksheet(cr, tm, p);
		__rtWorksheet = rtJSW.getJWorksheet();

		for (int i = 0; i < 4; i++) {
			__rtWorksheet.removeColumn(i);
		}
		for (int i = 6; i < 10; i++) {
			__rtWorksheet.removeColumn(i);
		}		
	}
	catch (Exception e) {
		rtJSW = new JScrollWorksheet(0, 0, p);
		__rtWorksheet = rtJSW.getJWorksheet();
	}
	__rtWorksheet.setPreferredScrollableViewportSize(null);
	__rtWorksheet.setHourglassJFrame(this);
	__rtWorksheet.addMouseListener(this);

	JGUIUtil.addComponent(step2_RTJPanel, rtJSW,
		0, 4, 2, 1, 1, 1, NLNR, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);

	//---------------------------------------------------------------------
	// Step 2: WIS JPanel
	//---------------------------------------------------------------------
	JPanel step2_WISJPanel = new JPanel();
	step2_WISJPanel.setLayout(gbl);
	__wizardJPanel.add("Step 2 " + WIS, step2_WISJPanel);
	
	JGUIUtil.addComponent(step2_WISJPanel, 
		new JLabel("Step 1: Select the desired WIS"), 
		0, 0, 1, 1, 0, 0, TNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	__wisNameSimpleJComboBox = new SimpleJComboBox();
	__wisNameSimpleJComboBox.add("Select a Water Information Sheet ");
	__wisNameSimpleJComboBox.addItemListener(this);
	JGUIUtil.addComponent(step2_WISJPanel, 
		__wisNameSimpleJComboBox, 
		1, 0, 1, 1, 1, 0, TLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

	JGUIUtil.addComponent(step2_WISJPanel, 
		new JLabel("Step 2: Select a desired Data Type"), 
		0, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	__wisDataSimpleJComboBox = new SimpleJComboBox();
	__wisDataSimpleJComboBox.add(HydroBase_GUI_WIS.POINT_FLOW);
	__wisDataSimpleJComboBox.add(HydroBase_GUI_WIS.NATURAL_FLOW);
	__wisDataSimpleJComboBox.add(HydroBase_GUI_WIS.DELIVERY_FLOW);
	__wisDataSimpleJComboBox.add(HydroBase_GUI_WIS.GAIN_LOSS);
	__wisDataSimpleJComboBox.add(HydroBase_GUI_WIS.TRIB_NATURAL);
	__wisDataSimpleJComboBox.add(HydroBase_GUI_WIS.TRIB_DELIVERY);
	__wisDataSimpleJComboBox.add(HydroBase_GUI_WIS.RELEASES);
	__wisDataSimpleJComboBox.add(HydroBase_GUI_WIS.PRIORITY_DIV);
	__wisDataSimpleJComboBox.add(HydroBase_GUI_WIS.DELIVERY_DIV);
	__wisDataSimpleJComboBox.select(0);	// select the first item
	__wisDataSimpleJComboBox.addItemListener(this);
	JGUIUtil.addComponent(step2_WISJPanel, 
		__wisDataSimpleJComboBox, 
		1, 1, 1, 1, 0, 0, NLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

	JGUIUtil.addComponent(step2_WISJPanel, 
		new JLabel("Step 3: Select the correct Row Label"), 
		0, 2, 2, 1, 0, 0, TNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	int[] wisWidths = null;
	JScrollWorksheet wisJSW = null;
	try {
		HydroBase_TableModel_WISImportWizard tm = 
			new HydroBase_TableModel_WISImportWizard(new Vector(),
			HydroBase_TableModel_WISImportWizard.WIS_FORMAT);
		wisWidths = tm.getColumnWidths();
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
		
		wisJSW = new JScrollWorksheet(cr, tm, p);
		__wisWorksheet = wisJSW.getJWorksheet();
		
		for (int i = 0; i < 6; i++) {
			__wisWorksheet.removeColumn(i);
		}
	}
	catch (Exception e) {
		wisJSW = new JScrollWorksheet(0, 0, p);
		__wisWorksheet = wisJSW.getJWorksheet();
	}
	__wisWorksheet.setPreferredScrollableViewportSize(null);
	__wisWorksheet.setHourglassJFrame(this);
	__wisWorksheet.addMouseListener(this);

	JGUIUtil.addComponent(step2_WISJPanel, wisJSW, 
		0, 4, 2, 1, 1, 1, NLNR, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);

	// Frame settings
	pack();
	setSize(650, 530);
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}
	setTitle(app + FRAME_TITLE);
	JGUIUtil.center(this);
	initialize();

	if (rtWidths != null) {
		__rtWorksheet.setColumnWidths(rtWidths);
	}
	if (wisWidths != null) {
		__wisWorksheet.setColumnWidths(wisWidths);
	}

	__ignoreStateChange = false;
	setNextEnabled(false);
}

/**
Shows the wizard and changes to the necessary cell information.
@param station_num the unique station_num for the import
@param row the row number of the cell in the builder gui.
@param col the column number of the cell in the builder gui.
@param wisImport the wis import object to fill in.
*/
public void setVisible(long station_num, int row, int col, 
HydroBase_WISImport wisImport) {
	__row = row;
	__col = col;
        setVisible(true);
}

/**
Shows or hides the GUI.
@param state if true, show the GUI.  If false, hide it.
*/
public void setVisible(boolean state) {
	if (state) {
		setWizardStep(1);
		showJPanel();
	}
	super.setVisible(state);
}

/**
Submits an RT query.
*/
private void submitRTQuery() {
	String routine = __CLASS + ".submitRTQuery";

	// initialize variables
	__rtWorksheet.clear();

        JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Forming station list...");

	// no water districts are available
	if (__rtWDSimpleJComboBox.getItemCount() == 0) {
		setReady(__statusJTextField);
		return;
	}

	// Build the water district where clause from the user choice(s).
	String[] district = null;
	try {
		district = __dmi.getWaterDistrictWhereClause(
			__rtWDSimpleJComboBox, 
			HydroBase_GUI_Util._GEOLOC_TABLE_NAME, false, false);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error getting where clause.");
		Message.printWarning(2, routine, e);
	}

	if (district == null) {
		setReady(__statusJTextField);
		return;
	}

	List results = null;
	try {
		results = __dmi.readStationGeolocMeasTypeList(null,
			district, null, "%RT_rate%", null, null, null, "1",
			true);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading station geoloc "
			+ "meas type data.");
		Message.printWarning(2, routine, e);
	}
	      
	if (results == null || results.size() == 0) {
		setReady(__statusJTextField);
		return;
	}

	__rtWorksheet.setData(results);
	
	setReady(__statusJTextField);
}


/**
Submits a WIS query.
*/
private void submitWISQuery() {
	String routine = __CLASS + ".submitWISQuery";
	generateWISSheets();
 
	// initialize variables
	__wisWorksheet.clear();

        JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Forming row label list...");

	int wisNum = ((HydroBase_WISSheetName)__wisVector.get(
		__wisNameSimpleJComboBox.getSelectedIndex())).getWis_num();

	List results = null;
	try {
		results = __dmi.readWISFormatList(wisNum, 
			HydroBase_GUI_WIS.STRING, null);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading wis_format "
			+ "data.");
		Message.printWarning(2, routine, e);
	}

	if (results == null || results.size() == 0) {
		setReady(__statusJTextField);
		return;
	}

	__wisWorksheet.setData(results);

	setReady(__statusJTextField);
}

/**
Responds to list selection events.
@param event the ListSelectionEvent that happened.
*/
public void valueChanged(ListSelectionEvent event) {
	if (__sourceList.getSelectedIndex() < 0) {
		setNextEnabled(false);
		return;
	}
	setNextEnabled(true);
	String s = ((String)__sourceList.getSelectedItem());
	String t = "";
	if (s.equals(RT)) {
		t = RT_TITLE;
	}
	else if (s.equals(WIS)) {
		t = WIS_TITLE;
	}
	setTitle(FRAME_TITLE + " - " + t);
}

/**
Responds to window closing events.
@param event the WindowEvent that happened.
*/
public void windowClosing(WindowEvent event) {
	cancelClicked();
}

}
