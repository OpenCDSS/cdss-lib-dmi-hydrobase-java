//-----------------------------------------------------------------------------
// HydroBase_GUI_SetCall - GUI to assist in setting a call.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
// 03 Nov 1997 DLG, RTi 		Created initial class description.
// 10 Feb 1998 DLG, RTi			Updated to java 1.1 event model.
// 22 Apr 1998 DLG, RTi			Maded changes specified by 
//					crdss-00467.txt
// 04 Apr 1999	Steven A. Malers, RTi	Add HBDMI to queries.
// 07 Sep 1999	SAM, RTi		Change setVisible()to not be
//					synchronized.  Remove import *.
// 2001-11-12	SAM, RTi		Change GUI to GUIUtil.
//					Don't use static for private strings.
// 2002-03-01	SAM, RTi		Add a search popup to the list of
//					structures.  Order the water rights when
//					queried.  Make a few minor cosmetic
//					changes to make the GUI easier to use.
//-----------------------------------------------------------------------------
// 2003-03-31	J. Thomas Sapienza, RTi	Began moving the GUI to Swing.
// 2003-04-01	JTS, RTi		Finished moving the GUI to Swing.
// 2003-04-02	JTS, RTi		Got the GUI into a usable state.
// 2003-04-02	JTS, RTi		* Strings are trimmed before being 
//					  inserted in any fields.
//					* Appropriation date was not being 
//					  displayed; fixed.
// 2003-04-04	JTS, RTi		Changed GUIUtil to JGUIUtil.
// 2004-06-15	JTS, RTi		Lists are now populated by calls to
//					setListData rather than adding each
//					individual item.
// 2004-07-13	JTS, RTi		Date checks for set dates in the
//					future or past were being done with
//					minute precision.  Changed them to
//					day.
// 2005-02-11	JTS, RTi		Added support for structure view data
//					that would be returned from stored
//					procedures.
// 2005-02-21	JTS, RTi		The __structures Vector could now
//					contain HydroBase_StructureView objects,
//					so the code was changed appropriately.
// 2005-04-12	JTS, RTi		MutableJList changed to SimpleJList.
// 2005-04-28	JTS, RTi		* Added all data members to finalize().
//					* Added super.finalize() to finalize().
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
// 2005-07-28	JTS, RTi		The net amounts Vector was not being
//					created properly for Bypass Structures.
// 2006-01-11	JTS, RTi		Bypass Calls GUI now gets passed the
//					CWRATMainJFrame.
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

import java.util.Collections;
import java.util.Date;
import java.util.Vector;

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

import DWR.DMI.CWRAT.CWRATMainJFrame;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.FindInJListJDialog;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleJButton;
import RTi.Util.GUI.SimpleJList;
import RTi.Util.GUI.SimpleJMenuItem;

import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.DateTimeBuilderJDialog;

/**
GUI to assist in setting a call.
*/
public class HydroBase_GUI_SetCall 
extends JFrame 
implements ActionListener, MouseListener, ListSelectionListener, WindowListener{

/**
Corresponds to the position of the Admin no JTextField in the __textFields 
Vector.
*/
public final static int ADMIN_NO = 		0;
/**
Corresponds to the position of the appropriation date JTextField in the 
__textFields Vector.
*/
public final static int APPRO_DATE = 		1;
/**
Corresponds to the position of the decreed amt JTextField in the __textFields 
Vector.
*/
public final static int DECREED_AMT = 		2;
/**
Corresponds to the position of the adj date JTextField in the __textFields 
Vector.
*/
public final static int ADJ_DATE = 		3;
/**
Corresponds to the position of the priority number JTextField in the 
__textFields Vector.
*/
public final static int PRIORITY_NUMBER = 	4;
/**
Corresponds to the position of the call JTextField in the __textFields 
Vector.
*/
public final static int CALL = 			5;
/**
Corresponds to the position of the set comment JTextField in the __textFields 
Vector.
*/
public final static int SET_COMMENT = 		6;

/**
GUI strings.
*/
private final String 
	__BUTTON_CANCEL = 		"Cancel",
	__BUTTON_HELP = 		"Help",
	__BUTTON_OK = 			"OK",
	__BYPASS = 			"BYPASS",
	__MENU_FIND_STRUCTURE = 	"Find Structure",
	__STRUCTURE_QUERY =    	  	"Select Structure from Database",
	__SET_DATE = 			"Set Date/Time",
	__STRUCT_LABEL = 		"Structure List (from";

/**
Acre-feet string.
*/
public final static String AF = 	"AF";

/**
Cubic feet/second string.
*/
public final static String CFS = 	"CFS";


private final String
	__FROM_WIS = " Water Information Sheets):",
	__FROM_CALLS = " Call Information):";

/**
Whether to display WIS calls or not.
*/
private boolean __displayWISCalls = false;

/**
If the bypass structures have been generated yet or not.
*/
private boolean __haveGeneratedBypassStructures = false;

/**
The parent CWRAT application frame.
*/
private CWRATMainJFrame __cwratParent;

/**
Reference to a dmi.
*/
private HydroBaseDMI __dmi = null;

/**
The call being used to populate the initial data on this form.
*/
private HydroBase_Calls __call = null;

/**
Structure query gui that this gui opens.
*/
private HydroBase_GUI_StructureQuery __callStructureQueryGUI = null;

/**
The edit calls bypass gui that this gui opens.
*/
private HydroBase_GUI_EditCallsBypass __bypassGUI = null;

/**
The parent gui that opens this gui.
*/
private HydroBase_GUI_CallsQuery __parent = null;

/**
the water district water data object used by this gui.
*/
private HydroBase_WDWater __waterDistrictWaterData = null;

/**
The labels for the calls list.
*/
private JLabel 
	__callJLabel = null,
	__callJLabel2 = null;

/**
The label for the call description.
*/
private JLabel __callDescriptionJLabel = null;

/**
The labels for the structure list.
*/
private JLabel 
	__structureListJLabel = null,
	__structureListJLabel2 = null;

/**
GUI text fields.
*/
private JTextField 
	__adjDateJTextField = null,
	__adminJTextField = null,
	__affectedJTextField = null,
	__aproDateJTextField = null,
	__callsJTextField = null,
	__decreedJTextField = null,
	__priorNumJTextField = null,
	__setDateJTextField = null,
	__setCommentJTextField = null,
	__statusJTextField = null;

/**
Calls list.
*/
private SimpleJList __callList = null;

/**
Structure list.
*/
private SimpleJList __structureList = null;

/**
Popup menu on the structure list.
*/
private JPopupMenu __structureJPopupMenu = null;

/**
Prop list for dates.
*/
private PropList __dateProps = null;

/**
The ok button.
*/
private SimpleJButton __okJButton = null;

/**
When doing a call copy, this contains the location parsed out of the set 
comment.
*/
private String __bypassStructure = null;

/**
The calls for the gui.
*/
private Vector __calls = null;
/**
The net water rights for the gui.
*/
private Vector __netWaterRights = null;
/**
The structures for the gui.
*/
private Vector __structures = null;
/**
Vector of the gui's textfields.
*/
private Vector __textFields_Vector = null;

/**
Constructor.
@param parent HydroBase_GUI_CallsQuery object
@param cwratParent the parent CWRAT JFrame object.
@param dmi HydroBaseDMI object.
*/
public HydroBase_GUI_SetCall(HydroBase_GUI_CallsQuery parent,
CWRATMainJFrame cwratParent, HydroBaseDMI dmi) {
	__parent = parent; 
	__cwratParent = cwratParent;
	__dmi = dmi;
	__call = null;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();

	if (__structureList.getItemCount() > 0) {
		__structureList.select(0);
		structureListClicked();

		if (__callList.getItemCount() > 0) {
			__callList.select(0);
			callsClicked();
		}
	}
}

/**
Constructor for doing copy calls.
@param parent HydroBase_GUI_CallsQuery object
@param cwratParent the parent CWRAT JFrame object.
@param dmi HydroBaseDMI object.
*/
public HydroBase_GUI_SetCall(HydroBase_GUI_CallsQuery parent,
CWRATMainJFrame cwratParent, HydroBaseDMI dmi, HydroBase_Calls call) {
	__parent = parent; 
	__cwratParent = cwratParent;
	__dmi = dmi;
	__call = call;

	String s = __call.getSet_comments();
	if (s != null) {
		s = s.trim();
		int index = StringUtil.indexOfIgnoreCase(s, "Bypass call from", 
			0);
		if (index >= 0) {
			try {
				__bypassStructure = s.substring(17 + index);
			}
			catch (Exception e) {
				__bypassStructure = null;
				Message.printWarning(2, "", 
					"Could not parse location "
					+ "from set comments: '" 
					+ __call.getSet_comments() + "'");
			}
		}
	}

	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();

	if (__bypassStructure == null) {
		fillCopyData();
	}
	else {
		String hold = new String(__bypassStructure);
		fillBypassCopyData();
		setCallDescriptionJLabel(true, hold);
	}
}

/**
This routine handles the events using the ActionListener.
@param event ActionEvent object
*/
public void actionPerformed(ActionEvent event) {
        String s = event.getActionCommand();
	String routine = "HydroBase_GUI_SetCall.actionPerformed";

	if (s.equals(__BUTTON_OK)) {
		setCallClicked();
	}
	else if (s.equals(__BUTTON_CANCEL)) {
		closeClicked();
	}
    	else if (s.equals(__BUTTON_HELP)) {
	}
    	else if (s.equals(__MENU_FIND_STRUCTURE)) {
		new FindInJListJDialog(this, (JList)__structureList, 
			"Find Structure");
	}
	else if (s.equals(__SET_DATE)) {
		setDateClicked();
	}
	else if (s.equals(__STRUCTURE_QUERY)) {
		structureQueryClicked();
	}
}

/**
Responds to the __callList LIST_SELECT event.  It populates the appropriate
objects with the information pertaining to the selected item from the available
calls list.
*/
private void callsClicked() {
	// initialize variables
	String routine = "HydroBase_GUI_SetCall.callsClicked()";

	// return if no items are present in the the __callList object
	int rows = __callList.getSelectedSize();
	if (rows == 0) {
		__okJButton.setEnabled(false);
		return;
	}
	else {
		__okJButton.setEnabled(true);
	}	

	// get the selected item from the __callList object
	String selectedItem = ((String)__callList.getSelectedItem()).trim();
	__callsJTextField.setText(selectedItem);
	
	// check if BYPASS Structure is selected
	if (selectedItem.equals(__BYPASS)) {
		if (__bypassGUI == null) {
			__bypassGUI = new HydroBase_GUI_EditCallsBypass(
				__dmi, __cwratParent, this, 
				__textFields_Vector);
		}
		else {
			__bypassGUI.setVisible(true);
		}
		return;
	}

	if (__callList.getSelectedIndex() > __netWaterRights.size()) {
		// this is probably caused by a Copy Call action which 
		// resulted in no valid values being found.  
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	HydroBase_NetAmts netAmt =(HydroBase_NetAmts)__netWaterRights.elementAt(
		__callList.getSelectedIndex());

	Date curDate = netAmt.getApro_date();
	if (!DMIUtil.isMissing(curDate)) {
		__aproDateJTextField.setText((new DateTime(curDate)).toString(
			DateTime.FORMAT_YYYY_MM_DD));	
	}
        
	double curDouble = netAmt.getAdmin_no();
	if (!DMIUtil.isMissing(curDouble)) {
		__adminJTextField.setText(StringUtil.formatString(
			curDouble, "%11.5f").trim());
	}
        
	curDouble = netAmt.getNet_abs();
	String unit = "";
	if (!DMIUtil.isMissing(curDouble)) {
		unit = netAmt.getUnit();
		if (unit.equalsIgnoreCase("C")) {
			unit = CFS;
		}
		else if (unit.equalsIgnoreCase("A")) {
			unit = AF;
		}
		else {
			unit = "";
		}

		__decreedJTextField.setText(StringUtil.formatString(
			curDouble, "%11.4f").trim() + " " + unit);
	}       

	curDate = netAmt.getAdj_date();
	if (!DMIUtil.isMissing(curDate)) {
		__adjDateJTextField.setText((new DateTime(curDate)).toString(
			DateTime.FORMAT_YYYY_MM_DD));
	}
		
	__priorNumJTextField.setText(netAmt.getPri_case_no());
}

/**
Determines if the set date is for current system day.
@return false if user wants to change set date, true otherwise.
*/
private boolean checkSetDate() {
	DateTime curDateTime = new DateTime(DateTime.DATE_CURRENT);
	curDateTime.setPrecision(DateTime.PRECISION_DAY);
	DateTime setDateTime = null;

	try {
		setDateTime = DateTime.parse(__setDateJTextField.getText(),
			DateTime.FORMAT_YYYY_MM_DD_HH_mm);
		setDateTime.setPrecision(DateTime.PRECISION_DAY);
	}
	catch (Exception e) {}

	int response;
	if (setDateTime.greaterThan(curDateTime)) {
		response = new ResponseJDialog(this, 
			"Date/Time is in Future",
			"Attempting to set a call"
			+ " for a future date. Continue?", 
			ResponseJDialog.YES|ResponseJDialog.NO).response();
		if (response == ResponseJDialog.NO) {
			return false;
		}
	}
	else if (setDateTime.lessThan(curDateTime)) {
		response = new ResponseJDialog(this,
			"Date/Time is in Past",
			"Attempting to set a call"
			+ " for a previous date. Continue?", 
			ResponseJDialog.YES|ResponseJDialog.NO).response();
		if (response == ResponseJDialog.NO) {
			return false;
		}
	}
	return true;
}

/**
This routine clears out the text of all the available call fields.
@param isSet true if setting a new call, false otherwise
*/
private void clearCallFields(boolean isSet) {
	__adminJTextField.setText("");
	__aproDateJTextField.setText("");
	__decreedJTextField.setText("");
	__adjDateJTextField.setText("");
	__priorNumJTextField.setText("");
	__setCommentJTextField.setText("");
	__affectedJTextField.setText("");
	__callsJTextField.setText("");

	try {		
		if (isSet) {
			__callList.removeAll();
		}
	} 
	catch (Exception e) {}
}

/**
Closes the GUI.
*/
private void closeClicked() {
	setVisible(false);
}

/**
This routine displays the net water rights associated with the selected
structure.
@param results contains HydroBase_NetAmts results
*/
private void displayWaterRightNetResults(Vector results, boolean includeBypass){
	int size = results.size();
	String display = __dmi.getPreferenceValue("General.CallingRight");

	String curString = "";
	String unit = "";
	Date curDate;
	double curDouble;
	HydroBase_NetAmts netAmt;
	Vector v = new Vector();
	for (int i=0; i<size; i++) {
		netAmt = (HydroBase_NetAmts)results.elementAt(i);

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
	                    	curString = DMIUtil.formatDateTime(__dmi,
					new DateTime(curDate));
				}
				catch (Exception e) {
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
	                    	curString = DMIUtil.formatDateTime(__dmi,
					new DateTime(curDate));
				}
				catch (Exception e) {
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
			curDouble = netAmt.getNet_abs();
			if (DMIUtil.isMissing(curDouble)) {
	                    	curString = "";                  
			}
			else {
				// we have so set the units
				unit = netAmt.getUnit();
				if (unit.equalsIgnoreCase("C")) {
					unit = CFS;
				}
				else if (unit.equalsIgnoreCase("A")) {
					unit = AF;
				}
				else {
					unit = "";
				}

				curString = StringUtil.formatString(
					curDouble, "%11.4f")+ " " + unit;
			}                
		}	
		v.add(curString.trim());
		__netWaterRights.addElement(netAmt);
	}
	
	if (__dmi.getPreferenceValue("General.BypassCall").equals("1")
	    && includeBypass) {
		v.add(__BYPASS);
	}

	__callList.setListData(v);
	__netWaterRights.addElement(new HydroBase_NetAmts());
}

/**
This routine displays the Structures(either WIS Structures or any 
database structure, depending on the User Preference "General.CallStruct")
*/
private void displayResults() {
	String routine = "HydroBase_GUI_SetCall.displayResults";
	__structureList.removeAll();
	__calls = new Vector();
	__structures = new Vector();
	

	String callStruct = __dmi.getPreferenceValue("General.CallStruct");

	if (callStruct.equals("FromQuery") 
	    || callStruct.equalsIgnoreCase("NONE")) {
		__calls = getPreviousCalls();
		Collections.sort(__calls);	
		__displayWISCalls = false;
		displayCalls();
	}
	else { 
		try {
			if (__dmi.getWISStructuresVector() == null) {
				__dmi.readWISStructuresList();	
			}
			__structures = __dmi.getWISStructuresVector();
			__displayWISCalls = true;
			displayStructures();
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading "
				+ "structures from "
				+ "database; none will be displayed.");
			Message.printWarning(1, routine, e);
		}
	}
}

/**
This routine displays the WIS Structures
*/
private void displayStructures() {
	HydroBase_StructureView view = null;
	int curInt;
	String curString;
	String name;

	String wd;
	String id;
	
	if (!__structures.isEmpty()) {
		int size = __structures.size();

		Vector v = new Vector();
		for (int i = 0; i < size; i++) {
			view = (HydroBase_StructureView)
				__structures.elementAt(i);
			name = "" + view.getStr_name();

			// get water district
			curInt = view.getWD();
			if (DMIUtil.isMissing(curInt)) {
				wd = "";
			}
			else {
				wd = "" + curInt;
			}

			// structure id
        		curInt = view.getID();
        		if (DMIUtil.isMissing(curInt)) {
                		id = "";
        		}
        		else {
                		id = "" + curInt;
        		}

        		curString = HydroBase_WaterDistrict.formWDID(wd,
				id);

			v.add(curString + " - " + name);	

		}
		__structureList.setListData(v);
	}
}

/**
This routine displays the calls
*/
private void displayCalls() {
	String curString;
	String name;
	HydroBase_Calls	call;
	int curInt;

	String wd, id;
	if (!__calls.isEmpty()) {
		int size = __calls.size();

		Vector v = new Vector();
		for (int i = 0; i < size; i++) {
			call = (HydroBase_Calls)__calls.elementAt(i);
			name = "" + call.getStr_name();

			// get water district
			curInt = call.getWD();
			if (DMIUtil.isMissing(curInt)) {
				wd = "";
			}
			else {
				wd = "" + curInt;
			}

			// structure id
        		curInt = call.getID();
        		if (DMIUtil.isMissing(curInt)) {
                		id = "";
        		}
        		else {
                		id = "" + curInt;
        		}

        		curString = HydroBase_WaterDistrict.formWDID(wd, id);

			v.add(curString + " - " + name);	
		}
		__structureList.setListData(v);
	}
}

/**
When copying a bypass call, this fills in the information on the form for
the bypassed call.
*/
private void fillBypassCopyData() {
	int index = structureAddition(__call);
	if (index <= -100) {
		// Special case that happens when the call is already in 
		// the list.  The returned index is specified as:
		// 	[the index of the already-present call] * -1 
		//	- 100
		// So if the call was found in the list at position 12,
		// the returned index would be -112.
		index *= -1;
		index -= 100;
		__structureList.select(index);
		structureListClicked();
		__structureList.ensureIndexIsVisible(index);
	}	
	else {
		__structureList.ensureIndexIsVisible(index);
	}

	int matchIndex = __structureList.indexOf(__bypassStructure, 0);

	boolean possibleError = false;
	int struct_num = -1;

	if (matchIndex == -1) {
		Message.printWarning(2, "", 
			"Bypass structure parsed from "
			+ "set comment '" + __bypassStructure 
			+ "' could not be found in the structure "
			+ "list.");
		Message.printStatus(2, "", 
			"Attempting to find structure_num in database by "
			+ "parsing WDID.");
		possibleError = true;

		struct_num = findStructureNumForWDID(__bypassStructure);

		if (struct_num == -1 || DMIUtil.isMissing(struct_num)) {
			__bypassStructure = null;
			return;
		}
	}

	__bypassStructure = null;

	if (!possibleError) {
		HydroBase_StructureView view = null;
		HydroBase_Calls call = null;
	
		if (__displayWISCalls) {
			view = (HydroBase_StructureView)
				__structures.elementAt(matchIndex);
			struct_num = view.getStructure_num();
		}
		else {	
			call = (HydroBase_Calls)__calls.elementAt(matchIndex);
			struct_num = call.getStructure_num();
		}
	}

	Vector results = null;
	try {
		results = __dmi.readNetAmtsList(struct_num, -999, -999, false, 
			"72");	
	}
	catch (Exception e) {
		Message.printWarning(1, "", "Error reading net amts from "
			+ "database");
		Message.printWarning(1, "", e);
		results = new Vector();
	}
	
	matchIndex = -1;

	__netWaterRights = new Vector();

	double adminno = __call.getAdminno();
	if (!results.isEmpty()) {
		displayWaterRightNetResults(results, false);
		HydroBase_NetAmts na = null;
		int size = results.size();
		for (int i = 0; i < size; i++) {
			na = (HydroBase_NetAmts)results.elementAt(i);
			__netWaterRights.add(na);
			if (na.getAdmin_no() == adminno) {
				matchIndex = i;
			}
		}
	}

	if (matchIndex < 0) {
		Message.printWarning(2, "", "Could not locate adminno "
			+ adminno + " in the calls list.");
	}
	else {		
		__callList.select(matchIndex);
		callsClicked();
		__callList.ensureIndexIsVisible(matchIndex);
	}

	__affectedJTextField.setText("" + __call.getDistricts_affected());
	__setCommentJTextField.setText("" + __call.getSet_comments());

	__okJButton.setEnabled(true);
}

/**
When copying a normal call, this fills in the information for the call.
*/
private void fillCopyData() {
	int index = structureAddition(__call);
	if (index <= -100) {
		// Special case that happens when the call is already in 
		// the list.  The returned index is specified as:
		// 	[the index of the already-present call] * -1 
		//	- 100
		// So if the call was found in the list at position 12,
		// the returned index would be -112.
		index *= -1;
		index -= 100;
		__structureList.select(index);
		structureListClicked();
		__structureList.ensureIndexIsVisible(index);
	}	
	else {
		__structureList.ensureIndexIsVisible(index);
	}

	HydroBase_NetAmts netAmt = null;
	double admin_no = __call.getAdminno();

	int size = __netWaterRights.size();
	for (int i = 0; i < size; i++) {
		netAmt = (HydroBase_NetAmts)__netWaterRights.elementAt(i);
		if (netAmt.getAdmin_no() == admin_no) {
			index = i;
			i = size + 1;
		}
	}
	
	if (index >= 0) {
		__callList.select(index);
		callsClicked();
		__callList.ensureIndexIsVisible(index);
	}
	else {
	
		// do nothing
	}

	__affectedJTextField.setText("" + __call.getDistricts_affected());
	__setCommentJTextField.setText("" + __call.getSet_comments());

	__okJButton.setEnabled(true);
}

/**
Cleans up member variables.
*/
public void finalize() 
throws Throwable {
	__cwratParent = null;
	__dmi = null;
	__call = null;
	__callStructureQueryGUI = null;
	__bypassGUI = null;
	__parent = null;
	__waterDistrictWaterData = null;
	__callJLabel = null;
	__callJLabel2 = null;
	__structureListJLabel = null;
	__structureListJLabel2 = null;
	__adjDateJTextField = null;
	__adminJTextField = null;
	__affectedJTextField = null;
	__aproDateJTextField = null;
	__callsJTextField = null;
	__decreedJTextField = null;
	__priorNumJTextField = null;
	__setDateJTextField = null;
	__setCommentJTextField = null;
	__statusJTextField = null;
	__callList = null;
	__structureList = null;
	__structureJPopupMenu = null;
	__dateProps = null;
	__okJButton = null;
	__calls = null;
	__netWaterRights = null;
	__structures = null;
	__textFields_Vector = null;
	super.finalize();
}

/**
Finds the structure number that corresponds to a bypass structure string 
containing a WDID.
@param bypassStructure the bypass structure string to parse the WDID out of
and for which to return the structure_num.
@return the matching structure_num, or -1 if it could not be found.
*/
private int findStructureNumForWDID(String bypassStructure) {
	int index = bypassStructure.indexOf("-");

	if (index == -1) {
		return -1;
	}

	String s = bypassStructure.substring(0, index).trim();
	try {
		int[] wdid = HydroBase_WaterDistrict.parseWDID(s);
	
		HydroBase_StructureView sv = __dmi.readStructureViewForWDID(
			wdid[0], wdid[1]);

		if (sv == null) {
			return -1;
		}
		else {
			return sv.getStructure_num();
		}
	}
	catch (Exception e) {
		Message.printWarning(2, "", "Error reading structure with wdid "
			+ s + " from database.");
		return -1;
	}
}

/**
Given a bypass string, returns the structure name from the text.
@param bypassText the text to parse.
@return the structure name.
*/
private String findStructureName(String bypassText) {
	int structure_num = findStructureNumForWDID(bypassText);

	int index = bypassText.indexOf("-");
	String wdid = null;

	if (index != -1) {
		wdid = bypassText.substring(0, index).trim();
	}


	if (structure_num == -1 || DMIUtil.isMissing(structure_num)) {
		return bypassText;
	}

	try {
		HydroBase_StructureView sv 
			= __dmi.readStructureViewForStructure_num(
			structure_num);
		if (sv == null) {
			return bypassText;
		}
		else {
			return wdid + " - " + sv.getStr_name();
		}
	}
	catch (Exception e) {}

	return bypassText;
}

/**
Returns the apro date from the selected call.
@return the apro date from the selected call.
*/
private Date getAproDate() {
	int index = __callList.getSelectedIndex();	
	HydroBase_NetAmts netAmt = (HydroBase_NetAmts)
		__netWaterRights.elementAt(index);
	return netAmt.getApro_date();
}

/**
Returns the admin number from the selected call.
@return the admin number from the selected call.
*/
private String getAdminNumber() {
	int index = __callList.getSelectedIndex();	
	HydroBase_NetAmts netAmt = (HydroBase_NetAmts)
		__netWaterRights.elementAt(index);
	return "" + netAmt.getAdmin_no();
}

/**
Returns the net number from the selected call.
@return the net number from the selected call.
*/
private String getNetNumber() {
	int index = __callList.getSelectedIndex();	
	HydroBase_NetAmts netAmt = (HydroBase_NetAmts)
		__netWaterRights.elementAt(index);
	return "" + netAmt.getNet_num();
}

/**
This routine performs a call chronology query.  Returns the result set vector
*/
private Vector getPreviousCalls() {
	String routine = "HydroBase_GUI_SetCall.getPreviousCalls";
	Vector orderBy = new Vector();
	orderBy.addElement("wd");
	orderBy.addElement("str_name");
	
	// add division where clause
	Vector divVector = HydroBase_GUI_Util.getDivisions(__dmi);
	try {
		return __dmi.readCallsListForDiv(divVector, true);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading previous " 
			+ "calls.");
		Message.printWarning(1, routine, e);
		return null;
	}
}

/**
This routine returns the __haveGeneratedBypassStructures variable.
@return true if BYPASS Structures have been generated, false otherwise.
*/
public boolean haveGeneratedBypassStructures() {
	return __haveGeneratedBypassStructures;
}

/**
This routine returns true if the __BYPASS variable appeares in 
the __callsJTextField.
@return true if __BYPASS is selected, false otherwise.
*/
private boolean isBypassSelected() {
	// if bypass exist in the __callsJTextField then no BYPASS Structure
	// has been selected yet.
	if (__callsJTextField.getText().trim().equals(__BYPASS)) {
		new ResponseJDialog(this, "Invalid Call",
			__BYPASS 
			+ " is not a valid call", ResponseJDialog.OK);
		return true; 
	}
	return false;
}

/**
This routine initializes member variables.
*/
public void initialize() {
	// instantiate objects
	__displayWISCalls = true;
	__calls = new Vector();
	__netWaterRights = new Vector();
	__structures = new Vector();
	__textFields_Vector = new Vector();

	// allocate elements in the Vector object
	for (int i = 0; i < 7; i++) {
		__textFields_Vector.addElement(new JTextField());
	}

	// set the Elements in the Vector object to the
	// appropriate JTextField object
	__textFields_Vector.setElementAt(__adminJTextField, ADMIN_NO);
	__textFields_Vector.setElementAt(__aproDateJTextField, APPRO_DATE);
	__textFields_Vector.setElementAt(__decreedJTextField, DECREED_AMT);
	__textFields_Vector.setElementAt(__adjDateJTextField, ADJ_DATE);
	__textFields_Vector.setElementAt(__priorNumJTextField, PRIORITY_NUMBER);
	__textFields_Vector.setElementAt(__callsJTextField, CALL);	
	__textFields_Vector.setElementAt(__setCommentJTextField, SET_COMMENT);

	// set date properties
	__dateProps = new PropList("Calls TSDateBuilderGUI properties");
	__dateProps.set("DatePrecision", "Minute" );
	__dateProps.set("DateFormat", "Y2K");
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
	
	if (c.equals(__structureList) && (__structureList.getItemCount() > 0)
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
Readies the GUI for user interaction.
*/
public void ready() {
	JGUIUtil.setWaitCursor(this, false);
	__statusJTextField.setText("Ready");
}

/**
This routine adds an HydroBase_NetAmts object to the _wdNet object
at the location corresponding to the BYPASS Structure.
*/
public void setBYPASSInfo(HydroBase_NetAmts data) {
	__netWaterRights.setElementAt(data,  __netWaterRights.size() - 1);
}

/**
Responds to the __set_Button ACTION_EVENT and inserts a new record  
into the calls table
*/
private void setCallClicked() {
	HydroBase_NetAmts netAmt = null;
	HydroBase_StructureView view = null;
	HydroBase_Calls call = null;
	
	// initialize variables
	String routine = "HBEditCalls.setCallClicked()";
	String tempString = "";
	String deleted = "N";
	
	// issue warning for no structure selected	
	int structureIndex = __structureList.getSelectedIndex();
	if (structureIndex == -1) {
		new ResponseJDialog(this, "Select a Structure",
			"Must select a structure"
			+ " before setting a call.", ResponseJDialog.OK);
		return;
	}

	// issue warning for not having permissions to set the selected call.
	// This is determined via stucture wd.		
	String wd = "";
	if (__displayWISCalls) {
		wd = "" + ((HydroBase_StructureView)
			__structures.elementAt(structureIndex)).getWD();
	}
	else {
		wd = "" + ((HydroBase_Calls)
			__calls.elementAt(structureIndex)).getWD();
	}

	if (!__dmi.hasPermission(wd)) {
		new ResponseJDialog(this, "Invalid Permissions",
			"You do not have permissions"
			+ " to set a call for this structure.",
			ResponseJDialog.OK);
		return;		
	}

	// issue warning for no call selected	
	String callString = __callsJTextField.getText().trim();
	if (callString.equals("")) {
		new ResponseJDialog(this, "Select a Call",
			"Must select a call"
			+ " before setting a call.", ResponseJDialog.OK);
		return;
	}

	// issue a Warning for __BYPASS selected
	if (isBypassSelected()) {
		return;
	}

	// issue a warning is setting a call for a date which is not
	// today. allow user to override.
	if (!checkSetDate()) {
		return;
	}

	// check for the presence of single quotes
	if (__setCommentJTextField.getText().trim().indexOf("'") > -1) {
		new ResponseJDialog(this, "No Single Quotes in Comments",
			"Cannot have single quotes "
			+ "in the set comments.", ResponseJDialog.OK);
		return;
	}
	if (__affectedJTextField.getText().trim().indexOf("'") > -1) {
		new ResponseJDialog(this, "No Single Quotes in Comments",
			"Cannot have single quotes "
			+ "in the districts affected. ", ResponseJDialog.OK);
		return;
	}

	// issue warning if the selected structure did not pull a
	// HydroBase_WDWater object.
	if (__waterDistrictWaterData == null) {
		new ResponseJDialog(this, "Cannot location WD Water Object",
			"Unable to locate an"
			+ " the associated Water Distict Water"
			+ " object for this structure.", ResponseJDialog.OK);
		return;
	}

	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Please Wait...Setting New Call");
	
	// get data for the currently selected structure/call from the
	// __structures/__calls object
	String id = "";
	String div = "";
	String structure_name = "";
	String structure_num = "";
	String wdwater_num = "";
	if (__displayWISCalls) {
		view = (HydroBase_StructureView)	
			__structures.elementAt(structureIndex);
		structure_num = "" + view.getStructure_num();
		wdwater_num = "" + view.getWdwater_num();
		wd = "" + view.getWD();
		id = "" + view.getID();
		div = "" + view.getDiv();
		structure_name = "" + view.getStr_name();
	}
	else {
		call = (HydroBase_Calls)__calls.elementAt(structureIndex);
		structure_num = "" + call.getStructure_num();
		wdwater_num = "" + call.getWdwater_num();
		wd = "" + call.getWD();
		id = "" + call.getID();
		div = "" + call.getDiv();
		structure_name = "" + call.getStr_name();
	}

	// now get the data from the selected net amounts object and user 
	// entered data.
	String adminno = getAdminNumber();
	String net_num = getNetNumber();
	DateTime setDateTime = null;
	try {
		setDateTime = DateTime.parse(__setDateJTextField.getText(),
			DateTime.FORMAT_YYYY_MM_DD_HH_mm);
	}
	catch (Exception e) {}

	String date_time_set = null;
	DateTime date_time_set_DT = null;
	String archive_date = null;
	DateTime archive_date_DT = null;
	String apro_date = null;
	DateTime apro_date_DT = null;
	try {
		date_time_set = DMIUtil.formatDateTime(__dmi, setDateTime);
		date_time_set_DT = new DateTime(setDateTime);
		archive_date = DMIUtil.formatDateTime(__dmi, new DateTime(
			DateTime.PRECISION_MINUTE | DateTime.DATE_CURRENT));
		archive_date_DT = new DateTime(
			DateTime.PRECISION_MINUTE | DateTime.DATE_CURRENT);
		apro_date = DMIUtil.formatDateTime(__dmi,
			new DateTime(getAproDate(), DateTime.PRECISION_MINUTE));
		apro_date_DT = 
			new DateTime(getAproDate(), DateTime.PRECISION_MINUTE);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error formatting date.");
		Message.printWarning(1, routine, e);
	}

	String date_time_released = "";
	String set_comments = __setCommentJTextField.getText().trim();
	if (set_comments.length()== 0) {
		set_comments = " ";
	}

	String release_comments = "";
 	String districts_affected = __affectedJTextField.getText().trim();
	if (districts_affected.length()== 0) {
		districts_affected = " ";
	}

	String dcr_amt = __decreedJTextField.getText().trim();
	if (dcr_amt.length()== 0) {
		dcr_amt = " ";
	}

	// now get the data from the water distict water object. this is a
	// single 1 to 1 object which is pulled when the structure is selected
	String trib_num = "" + __waterDistrictWaterData.getStrtribto();
	String stream_name = "" + __waterDistrictWaterData.getStrname();

	// if using Access, need to determine the call_num
	int call_num = DMIUtil.MISSING_INT;
	// REVISIT (JTS - 2004-06-18)
	// use the DMI!!
	if (__dmi.getDatabaseEngine().equals("Access")) {
		tempString = "INSERT INTO calls (";
		tempString += "call_num, ";
	tempString += "structure_num, net_num, wdwater_num, date_time_set, "
		+ " deleted, date_time_released, adminno, set_comments,"
		+ " release_comments, div, districts_affected, archive_date, "
		+ "str_name, wd, id, strname, strtribto, dcr_amt, apro_date)"
		+ " VALUES (";
		tempString += call_num + ", ";
	tempString += structure_num + ", " + net_num + ", " + wdwater_num + ", "
		+ date_time_set + ", '"	+ deleted + "', NULL, "
		+ adminno + ", '" + set_comments + "', " 
		+ "NULL, " + div + ",'" +  districts_affected 
		+ "', "	+ archive_date + ", '" + structure_name + "', " + wd 
		+ ", " + id + ", '" + stream_name + "', " + trib_num + ", '" 
		+ dcr_amt + "', " + apro_date + ")";
	try {
	      	__dmi.dmiWrite(tempString);     
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Set Call failed when trying "
			+ "to write to the database.");
		Message.printWarning(1, routine, e);
	}		
	}
	else {
		HydroBase_Calls newCall = new HydroBase_Calls();
		newCall.setStructure_num(StringUtil.atoi(structure_num));
		newCall.setNet_num(StringUtil.atoi(net_num));
		newCall.setWdwater_num(StringUtil.atoi(wdwater_num));
		newCall.setDate_time_set(date_time_set_DT.getDate());
		newCall.setDate_time_released(null);
		newCall.setDeleted(deleted);
		newCall.setAdminno(StringUtil.atod(adminno));
		newCall.setSet_comments(set_comments);
		newCall.setRelease_comments(null);
		newCall.setDiv(StringUtil.atoi(div));
		newCall.setDistricts_affected(districts_affected);
		newCall.setArchive_date(archive_date_DT.getDate());
		newCall.setStr_name(structure_name);
		newCall.setWD(StringUtil.atoi(wd));
		newCall.setID(StringUtil.atoi(id));
		newCall.setStrname(stream_name);
		newCall.setStrtribto(StringUtil.atoi(trib_num));
		newCall.setDcr_amt(dcr_amt);
		newCall.setApro_date(apro_date_DT.getDate());
	try {
	      	__dmi.writeCalls(newCall);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Set Call failed when trying "
			+ "to write to the database.");
		Message.printWarning(1, routine, e);
	}		
	}
	__parent.submitQuery(false);

	// If we are past the defined time than reset in the 
	// HydroBase_GUI_CallsQuery
	DateTime curDate = __parent.getToDate();
	if (curDate != null) {
		if (setDateTime.greaterThan(curDate)) {
			__parent.setToDate(setDateTime);
		}
	}

	closeClicked();
	JGUIUtil.setWaitCursor(this, false);
}

/**
Sets the call description JLabel based on the bypass information (if any).  
This is protected so that it can be called from the bypass GUI.
@param bypass whether the call is a bypass call or not.
@param text the text to display.
*/
protected void setCallDescriptionJLabel(boolean bypass, String text) {
	if (bypass) {
		__callDescriptionJLabel.setText("Call for BYPASS "
			+ findStructureName(text));
	}
	else {
		__callDescriptionJLabel.setText("Call for " 
			+ text);
	}
}

/**
Sets the label for the call based on whether it is a bypass call, what the
priority preference is, etc.
*/
private void setCallJLabel() {
	// get the priority preference display from user preferences
	String display = "using " 
		+ __dmi.getPreferenceValue("General.CallingRight");
	if (__bypassStructure != null) {
		display = "for bypass structure";
		setCallDescriptionJLabel(true, __bypassStructure);
	}
	__callJLabel.setText("Available Calls" + " (" + display + "):");

	if (__bypassStructure != null) {
		__callJLabel2.setText(findStructureName(__bypassStructure));
	}
	else {
		String sel = (String)__structureList.getSelectedValue();

		if (sel == null) {
			sel = "[No Structure currently selected]";
		}

		__callJLabel2.setText("" + sel);
	}
}

/**
Responds to the __setDateJBUtton ACTION_EVENT and instantiates a 
DateTimeBuilderJDialog object.
*/
private void setDateClicked() {
	String 	routine = "HydroBase_GUI_SetCall.setDateClicked()";
	DateTime from = null;

	try {
		from = DateTime.parse(__setDateJTextField.getText(),
			DateTime.FORMAT_YYYY_MM_DD_HH_mm );
	}
	catch (Exception e) {
		Message.printWarning(2, routine,
			"Exception parsing date.");
		from = new DateTime(DateTime.PRECISION_MINUTE);
	}
	new DateTimeBuilderJDialog(this, __setDateJTextField, from,__dateProps);
}

/**
This routine sets the __haveGeneratedBypassStructures variable.
@param state true if BYPASS Structures have been generated, false otherwise.
*/
public void setHaveGeneratedBYPASSStructures(boolean state) {
	__haveGeneratedBypassStructures = state;
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);

	// objects used throughout the GUI layout
	int buffer = 2;
        Insets insetsTLBR = new Insets(buffer,buffer,buffer,buffer);
	Insets insetsNLNR = new Insets(0,buffer,0,buffer);
        Insets insetsNLBR = new Insets(0,buffer,buffer,buffer);
	Insets insetsTLNR = new Insets(buffer,buffer,0,buffer);
       	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();

    	// Center JPanel
       	JPanel centerJPanel = new JPanel();
	centerJPanel.setLayout(new BorderLayout());
	getContentPane().add("Center", centerJPanel);

    	// Center: West JPanel
       	JPanel centerWJPanel = new JPanel();
	centerWJPanel.setLayout(gbl);
	centerJPanel.add("West", centerWJPanel);

	int y = 0;

	__structureListJLabel = new JLabel(__STRUCT_LABEL);
    	JGUIUtil.addComponent(centerWJPanel, __structureListJLabel,
		0, y, 3, 1, 1, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);

	__callJLabel = new JLabel("Available Calls:");
    	JGUIUtil.addComponent(centerWJPanel, __callJLabel, 
		3, y, 2, 1, 1, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);

	y++;
	
	__structureListJLabel2 = new JLabel("");
    	JGUIUtil.addComponent(centerWJPanel, __structureListJLabel2,
		0, y, 3, 1, 1, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);
	
	__callJLabel2 = new JLabel("");
	if (__bypassStructure != null) {
		__callJLabel2.setText(findStructureName(__bypassStructure));
	}
    	JGUIUtil.addComponent(centerWJPanel, __callJLabel2, 
		3, y, 2, 1, 1, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);

	y++;

	__structureList = new SimpleJList();
	__structureList.addMouseListener(this);
	__structureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	__structureList.addListSelectionListener(this);
    	JGUIUtil.addComponent(centerWJPanel, new JScrollPane(__structureList),
		0, y, 3, 2, 1, 1, insetsNLBR, gbc.BOTH, gbc.WEST);	

	// Add a popup menu to be added with the structure list to add
	// search capabilities...
	__structureJPopupMenu = new JPopupMenu("Structures");
	__structureJPopupMenu.add(new SimpleJMenuItem(
		__MENU_FIND_STRUCTURE, __MENU_FIND_STRUCTURE, this));
	//getContentPane().add(__structureJPopupMenu);
	
	__callsJTextField = new JTextField();
	__callsJTextField.setEditable(false);
    	JGUIUtil.addComponent(centerWJPanel, __callsJTextField, 
		3, y, 2, 1, 1, 0, insetsNLNR, gbc.BOTH, gbc.WEST);	

	y++;

	__callList = new SimpleJList();
	__callList.addListSelectionListener(this);
	__callList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JGUIUtil.addComponent(centerWJPanel, new JScrollPane(__callList), 
		3, y, 2, 1, 1, 1, insetsNLNR, gbc.BOTH, gbc.WEST);	

	y++;

	SimpleJButton query = new SimpleJButton(__STRUCTURE_QUERY, this);
	query.setToolTipText("Read data from database.");
    	JGUIUtil.addComponent(centerWJPanel, query, 
		0, 4, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);	

	y++;

	__callDescriptionJLabel = new JLabel("");

    	JGUIUtil.addComponent(centerWJPanel, __callDescriptionJLabel,
		0, y, 3, 1, 0, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);	

	y++;

    	JGUIUtil.addComponent(centerWJPanel,
		new JLabel("Administration Number:"),
		0, y, 1, 1, 0, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);	

    	JGUIUtil.addComponent(centerWJPanel, new JLabel("Appropriation Date:"),
		1, y, 1, 1, 0, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);	

    	JGUIUtil.addComponent(centerWJPanel, new JLabel("Decreed Amount:"),
		2, y, 1, 1, 0, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);	
 
    	JGUIUtil.addComponent(centerWJPanel, new JLabel("Adjudication Date:"), 
		3, y, 1, 1, 0, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);	

    	JGUIUtil.addComponent(centerWJPanel, new JLabel("Priority Number:"), 
		4, y, 2, 1, 0, 0, insetsNLNR, gbc.HORIZONTAL, gbc.WEST);	

	y++;

	__adminJTextField = new JTextField(10);
	__adminJTextField.setEditable(false);
    	JGUIUtil.addComponent(centerWJPanel, __adminJTextField, 
		0, y, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);	

	__aproDateJTextField = new JTextField(15);
	__aproDateJTextField.setEditable(false);
    	JGUIUtil.addComponent(centerWJPanel, __aproDateJTextField, 
		1, y, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);	

	__decreedJTextField = new JTextField(15);
	__decreedJTextField.setEditable(false);
	JGUIUtil.addComponent(centerWJPanel, __decreedJTextField, 
		2, y, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);	

	__adjDateJTextField = new JTextField(15);
	__adjDateJTextField.setEditable(false);
    	JGUIUtil.addComponent(centerWJPanel, __adjDateJTextField, 
		3, y, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);	

	__priorNumJTextField = new JTextField(15);
	__priorNumJTextField.setEditable(false);
    	JGUIUtil.addComponent(centerWJPanel, __priorNumJTextField, 
		4, y, 2, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);	

	y++;

    	JGUIUtil.addComponent(centerWJPanel,
		new JLabel("Date/Time that Call is Set:"), 
		0, y, 1, 1, 0, 0, insetsNLNR, gbc.NONE, gbc.EAST);	

	__setDateJTextField = new JTextField(15);
	__setDateJTextField.setEditable(false);
    	JGUIUtil.addComponent(centerWJPanel, __setDateJTextField, 
		1, y, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.EAST);	

	SimpleJButton set = new SimpleJButton(__SET_DATE, this);
	set.setToolTipText("Set date for call.");
    	JGUIUtil.addComponent(centerWJPanel, set,
		2, y, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);	

	y++;

	JGUIUtil.addComponent(centerWJPanel, new JLabel("Districts Affected:"), 
		0, y, 1, 1, 0, 0, insetsNLNR, gbc.NONE, gbc.EAST);	

	__affectedJTextField = new JTextField();
    	JGUIUtil.addComponent(centerWJPanel, __affectedJTextField, 
		1, y, 1, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.WEST);	

	y++;

    	JGUIUtil.addComponent(centerWJPanel, new JLabel("Set Comment:"), 
		0, y, 1, 1, 0, 0, insetsNLNR, gbc.NONE, gbc.EAST);	
 
	__setCommentJTextField = new JTextField();			
   	JGUIUtil.addComponent(centerWJPanel, __setCommentJTextField, 
		1, y, 2, 1, 0, 0, insetsNLBR, gbc.HORIZONTAL, gbc.EAST);	
		               
	// Bottom JPanel
	JPanel bottomJPanel = new JPanel();
	bottomJPanel.setLayout(new BorderLayout());
       	getContentPane().add("South", bottomJPanel);

	// Bottom: North JPanel
	JPanel bottomNJPanel = new JPanel();
	bottomNJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
       	bottomJPanel.add("North", bottomNJPanel);

	__okJButton = new SimpleJButton(__BUTTON_OK, this);
	__okJButton.setToolTipText("Accept values and return.");
	bottomNJPanel.add(__okJButton);
	SimpleJButton cancel = new SimpleJButton(__BUTTON_CANCEL, this);
	cancel.setToolTipText("Discard values and return.");
	bottomNJPanel.add(cancel);

	// Bottom JPanel: South
	JPanel bottomSJPanel = new JPanel();
	bottomSJPanel.setLayout(gbl);
       	bottomJPanel.add("South", bottomSJPanel);
        __statusJTextField = new JTextField();
	__statusJTextField.setEditable(false);
       	JGUIUtil.addComponent(bottomSJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, gbc.HORIZONTAL, gbc.WEST);
		
	// frame settings
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}
	
	setTitle(app + "Set Call");
	pack();
	
	// initiailize variables	
	initialize();

	JGUIUtil.center(this);

	// display the WIS Structures
	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Retrieving structure list...");
	displayResults();
	ready();

	__okJButton.setEnabled(false);
	setVisible(true);
}


/**
This routine show/hides the HydroBase_GUI_SetCall JFrame.
@param state true if showing the JFrame, false otherwise.
*/
public void setVisible(boolean state) {
	JGUIUtil.setWaitCursor(this, false);
	if (state)  {
		// clear all GUI objects and reset member variables
		__callList.removeAll();
		clearCallFields(true);
		__haveGeneratedBypassStructures = false;
		__statusJTextField.setText("");

		setCallJLabel();

		if (__displayWISCalls) {
			__structureListJLabel.setText(__STRUCT_LABEL 
				+ __FROM_WIS);
		}
		else {	
			__structureListJLabel.setText(__STRUCT_LABEL 
				+ __FROM_CALLS);
		}
		
		// get the current system time and diplay it
		DateTime date = new DateTime(DateTime.DATE_CURRENT);
		__setDateJTextField.setText(date.toString(
			DateTime.FORMAT_YYYY_MM_DD_HH_mm));

		if (__structureList.getItemCount() > 0) {
			__structureList.select(0);
			structureListClicked();
	
			if (__callList.getItemCount() > 0) {
				__callList.select(0);
				callsClicked();
			}
		}			
	}
	super.setVisible(state);
}

/**
Reponds to the __structureList LIST_SELECT event and constructs and submits a 
HydroBase_NetAmts query based on the structure selected in the __structureList 
object.
*/
private void structureListClicked() {
	// first retrieve the net amounts information
	// initialize variables
	Vector orderBy = new Vector();
	String routine = "HBEditCalls.structureListClicked()";

	if (__bypassStructure == null) {
		setCallJLabel();
	}

	// return if no items are present in the __structureList object
	if (__structureList.getItemCount() == 0) {
		return;
	}

	int index = __structureList.getSelectedIndex();
	// clear out all available call information
	clearCallFields(false);
	__netWaterRights.removeAllElements();
	__callList.removeAll();

	if (index == -1) {
		// nothing is selected in the list.
		// This can actually happen after a user has chosen a 
		// structure from the structure query gui and then 
		// returns to the set call gui. 
		// nothing was selected -- the only selected row was
		// DEselected
		JGUIUtil.setWaitCursor(this, false);
		__structureListJLabel2.setText("Currently-selected structure: " 
			+ "[No Structure currently selected]");
		return;		
	}

	String tempString = 
		"Please Wait...Retrieving Water Right Net Amounts Data";
	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText(tempString);

	// get the unique structure_num from the selected structure
	int struct_num = -1;
	int wdWaterNumber = -1;
	HydroBase_StructureView view = null;
	HydroBase_Calls call = null;

	if (__displayWISCalls) {
		view = (HydroBase_StructureView)
			__structures.elementAt(index);
		struct_num = view.getStructure_num();
		wdWaterNumber = view.getWdwater_num();
	}
	else {	
		call = (HydroBase_Calls)__calls.elementAt(index);
		struct_num = call.getStructure_num();
		wdWaterNumber = call.getWdwater_num();
	}

	String sel = (String)__structureList.getSelectedValue();

	if (sel == null) {
		sel = "[No Structure currently selected]";
	}

	__structureListJLabel2.setText("Currently-selected structure: " + sel);
	setCallDescriptionJLabel(false, sel);

	// add where clauses and order by clauses
	// Order by admin number.  This should result in a reasonable sort for
	// the other display options also.
	String display = __dmi.getPreferenceValue("General.CallingRight");
	if (display.equals(HydroBase_GUI_Options.PRIORITY_NUMBER)) {
		orderBy.addElement("net_amts.pri_case_no");
	}
	else if (display.equals(HydroBase_GUI_Options.ADJ_DATE)) {
		orderBy.addElement("net_amts.adj_date");
	}
	else if (display.equals(HydroBase_GUI_Options.APPRO_DATE)) {
		orderBy.addElement("net_amts.apro_date");
	}
	else if (display.equals(HydroBase_GUI_Options.ADMIN_NO)) {
		orderBy.addElement("net_amts.admin_no");
	}
	else if (display.equals(HydroBase_GUI_Options.DECREED_AMT)) {
		// Don't know for sure if it is going to be flow or volume so
		// don't sort.  This option is probably never going to be used
		// because the user needs to see an admin number, etc. to make
		// a decision.
	}

	Vector results = null;
	try {
		results = __dmi.readNetAmtsList(struct_num, -999, -999, false, 
			"72");	
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading net amts from "
			+ "database");
		Message.printWarning(1, routine, e);
		results = new Vector();
	}
	
	if (!results.isEmpty()) {
		displayWaterRightNetResults(results, true);
	}

	tempString = "Finished Retrieving Water Right Net Amounts Data.";
	__statusJTextField.setText(tempString);

	// now get the Water District Water object which maps to this structure
	__waterDistrictWaterData = null;

	tempString = "Please Wait...Retrieving Water District Water Data";
	
	__statusJTextField.setText(tempString);

	// add where clauses and order by clauses
	try {
		results = __dmi.readWDWaterListForWDWater_num(wdWaterNumber);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading wd water data "
			+ "from database.");
		Message.printWarning(1, routine, e);
		results = new Vector();
	}
	
	if (results.isEmpty()) {
		Message.printWarning(1, routine,
			" No record available for wd_water.wdwater_num = "
			+  wdWaterNumber);
	}

	else {	
		if (results.size() > 1) {
			Message.printWarning(1, routine,
			" Multiple records found for wd_water.wdwater_num = "
			+  wdWaterNumber + ", using first available.");
		}

		__waterDistrictWaterData = 
			(HydroBase_WDWater)results.elementAt(0);
	}

	tempString = "Finished Retrieving Water District Water Data.";
	JGUIUtil.setWaitCursor(this, false);
	__statusJTextField.setText(tempString);
}

/**
Adds a HydroBase_Calls object to the list of structures.
@param newCall the object to add.
@return the index of the new call
*/
private int structureAddition(HydroBase_Calls newCall) {
	HydroBase_Calls	call = null;
	String routine = "HydroBase_GUI_SetCall.structureAddition()";
	int insertIndex = 0;
	String new_name = newCall.getStr_name();
	int new_wd = newCall.getWD(), old_wd;

	String wd;
	String id;
	String name;
	int comparison;
	if (__calls == null) {
		__calls = new Vector(1);
		__calls.addElement(newCall);
	}
	else {
		int size = __calls.size();

		for (int i = 0; i < size; i++) {
			call = (HydroBase_Calls)__calls.elementAt(i);
			old_wd = call.getWD();
			if (new_wd > old_wd) {
				insertIndex++;
				continue;
			}
			if (new_wd < old_wd)  {
				// must be only 1 in this wd, insert
				break;
			}

			// otherwise, we have found the appropriate wd, now
			// let's insert based on the name
			name = "" + call.getStr_name().trim();

			comparison = name.compareTo(new_name);
			if (comparison == 0) {
			/*
				Message.printWarning(1, routine, 
					"Can't add structure \"" + new_name 
					+ "\" because it already exists " +
					"in current list.");
			*/
				return (-100 - i);
			}
			else if (comparison < 0) {
				insertIndex++;	
			}
			else	{
				// we have found our insert point
				break;
			}
		}
		__calls.insertElementAt(newCall, insertIndex);
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
	__structureList.add(curString + " - " + new_name, insertIndex);	
	__structureList.select(insertIndex);
	structureListClicked();

	return insertIndex;
}

/**
Adds a new HydroBase_StructureGeoloc object to the list of structures.
@param newCall the object to add.
*/
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
		__structures = new Vector(1);
		__structures.addElement(newCall);
	}
	else {
		int size = __structures.size();

		for (int i = 0; i < size; i++) {
			view = (HydroBase_StructureView)
				__structures.elementAt(i);
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
			else if (comparison < 0)
				insertIndex++;	
			else	{
				// we have found our insert point
				break;
			}
		}

		__structures.insertElementAt(newCall, insertIndex);
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

	__structureList.add(curString + " - " + new_name, insertIndex);	
	__structureList.select(insertIndex);
	structureListClicked();
}

/**
Responds to when the structure is clicked.
*/
private void structureQueryClicked() {
	String routine = "HydroBase_GUI_SetCall.structureQueryClicked";

	if (__callStructureQueryGUI == null) {
		try {
			__callStructureQueryGUI = 
				new HydroBase_GUI_StructureQuery(__dmi, 
				__cwratParent, true);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Couldn't open "
				+ "HydroBase_GUI_StructureQuery.");
			Message.printWarning(1, routine, e);
			return;
		}
	}
	else {
		__callStructureQueryGUI.setVisible(true);
	}
	__callStructureQueryGUI.displayStructsOnly(this);
}

/**
Called when the structure gui window is closed.  Adds a new struct to the list
if one was selected.
@param newStruct the structure that was selected on the structure gui window.
*/
public void structureWindowClosed(HydroBase_StructureView newStruct) {
	String routine = "HydroBase_GUI_SetCall.structureWindowClosed";

	// get the selected structure from the structure GUI
	if (__callStructureQueryGUI == null) {
		Message.printWarning(1, routine, "GUI is null");
		return;
	}

	if (newStruct != null) {
		String callStruct = 
			__dmi.getPreferenceValue("General.CallStruct");
		if (callStruct.equals("FromQuery")) {
			// transfer contents of returned HBStructureLocation
			// to HydroBase_Calls structure
			HydroBase_Calls newCallFromStructure = 
				new HydroBase_Calls();
			newCallFromStructure.setWdwater_num(
				newStruct.getWdwater_num());
			newCallFromStructure.setStructure_num(
				newStruct.getStructure_num());
			newCallFromStructure.setWD(
				newStruct.getWD());
			newCallFromStructure.setID(
				newStruct.getID());
			newCallFromStructure.setStr_name(
				newStruct.getStr_name());
			newCallFromStructure.setDiv(
				newStruct.getDiv());
	
			HydroBase_Geoloc geoloc = null;
			try {
				geoloc = __dmi.readGeolocForGeoloc_num(
					newStruct.getGeoloc_num());
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error reading"
					+ " from geoloc");
				Message.printWarning(1, routine, e);
				geoloc = null;
			}
			if (geoloc != null) {
				HydroBase_Stream stream = null;
				try {
					stream = __dmi.readStreamForStream_num(
						geoloc.getStream_num());
				}
				catch (Exception e) {
					Message.printWarning(1, routine, 
						"Error reading from stream.");
					Message.printWarning(1, routine, e);
					stream = null;
				}
				if (stream != null) {
					newCallFromStructure.setStrtribto(
						(int)stream.getStr_trib_to());
					Message.printStatus(1, routine,
						"Setting trib to " + 
						stream.getStr_trib_to());
				}
			}
			// finally, add the structure to our list of calls, both
			// internally and visually
			structureAddition(newCallFromStructure);
		}
		else { 
			structureAddition(newStruct);
		}

	}
	else {
		// new_struct is null
		Message.printWarning(1, routine, 
			"Not adding a structure to the call list because " +
			"you must select a single structure.");
	}
}

/**
Responds when a new value is selected on the JLists.
@param e the ListSelectionEvent that happened.
*/
public void valueChanged(ListSelectionEvent e) {
	Object o = e.getSource();

	if (__structureList == o) {
		structureListClicked();
	}
	else if (__callList == o) {
		callsClicked();
	}
}

/**
Responds to window activated event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowActivated(WindowEvent evt) {}

/**
Responds to window closed event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowClosed(WindowEvent evt) {}

/**
Responds to window closing event.
@param evt the WindowEvent that happened.
*/
public void windowClosing(WindowEvent evt) {
	closeClicked();
}

/**
Responds to window deactivated event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowDeactivated(WindowEvent evt) {}

/**
Responds to window deiconified event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowDeiconified(WindowEvent evt) {}

/**
Responds to window iconified event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowIconified(WindowEvent evt) {}

/**
Responds to window opened event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowOpened(WindowEvent evt) {}

}
