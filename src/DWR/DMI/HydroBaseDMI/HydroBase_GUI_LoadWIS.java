// HydroBase_GUI_LoadWIS - Dialog to assist in Building/Loading a Water Information Sheet

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

CDSS HydroBase Database Java Library is free software:  you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CDSS HydroBase Database Java Library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CDSS HydroBase Database Java Library.  If not, see <https://www.gnu.org/licenses/>.

NoticeEnd */

//-----------------------------------------------------------------------------
// HydroBase_GUI_LoadWIS - Dialog to assist in 
// Building/Loading a Water Information Sheet
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file. 
//-----------------------------------------------------------------------------
// History: 
//
// 16 Sep 1997	DLG, RTi 		Created initial class description.
// 19 Nov 1997	DLG, RTi 		Added functionality for __mode =
//					WIS_BUILDER.
// 28 Apr 1998	DLG, RTi		Added javadoc comments, updated to 1.1
//					event model.
// 03 Apr 1999	SAM, RTi		Add HBDMI to all queries.
// 21 May 1999	Steven A. Malers, RTi	Remove HBData.TIME_ZONE_DATUM.  Clean
//					up code.
// 08 Jul 2001	SAM, RTi		Select the first sheet in the list so
//					the user does not have to when loading
//					sheets for the builder.  Add
//					finalize().  Change GUI to JGUIUtil.
//					Use HBGUIApp.generateWaterDistricts()
//					instead of HBGUIApp.getWDVector().
// 15 Jul 2001	SAM, RTi		Add some StopWatch's to see why the load
//					takes so long in the builder.
// 2002-06-17	SAM, RTi		When listing sheet names, optionally
//					show the count of sheets and the range
//					of dates for the sheets.  Add the
//					Delete button to help with testing.
//-----------------------------------------------------------------------------
// 2003-04-08	J. Thomas Sapienza, RTi	Began the initial Swing version.
// 2003-04-09	JTS, RTi		Completed the initial Swing version.
// 2003-10-08	JTS, RTI		Cleaned up, javadoc'd more.
// 2003-11-19	JTS, RTi		Can now open a builder GUI.
// 2003-11-28	JTS, RTi		Added reference to the parent object.
// 2003-12-02	JTS, RTi		Changed the order in which districts
//					are listed.
// 2003-12-04	JTS, RTi		Opened the new wis GUI.
// 2004-05-13	JTS, RTi		Checkbox for opening a read-only WIS
//					is now a specific button.
// 2005-02-14	JTS, RTi		Converted MOST queries to stored
//					procedures.
// 2005-02-22	JTS, RTi		The delete code now calls two  
//					methods in the DMI in order to support
//					stored procedures.
// 2005-03-09	JTS, RTi		HydroBase_SheetName 	
//					  -> HydroBase_WISSheetName.
// 2005-04-12	JTS, RTi		MutableJList changed to SimpleJList.
// 2005-04-25	JTS, RTi		* Corrected errors resulting from 
//					  transition to stored procedures.
// 					* Call new version of formatDateTime()
//					  suitable for use with stored 
//					  procedures.
// 2005-04-28	JTS, RTi		Added all data members to finalize().
// 2006-04-11	JTS, RTi		All calls to readWISSheetNameList()
//					now pass a "true" to make sure the 
//					query results are sorted by 
//					effective date.
// 2007-02-08	SAM, RTi		Remove dependence on CWRAT.
//					Pass JFrame to constructor.
//					Add GoeViewUI to handle map interactions.
//					Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJList;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleJButton;
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.IOUtil;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;

/**
This class is a GUI for choosing the water information sheet to display on 
the WIS gui.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_LoadWIS 
extends JFrame
implements ActionListener, ItemListener, MouseListener, WindowListener {

/**
Modes in which this GUI can be instantiated.
*/
public final static int 
	WIS_BUILDER = 	0,
	DISTRICT_WIS = 	1,
	DIVISION_WIS = 	2;

/**
Button labels.
*/
private final String	
	__BUTTON_CANCEL = 		"Cancel",
	__BUTTON_DELETE = 		"Delete",
	__BUTTON_EDIT = 		"Edit",
	__BUTTON_HELP = 		"Help",
	__BUTTON_NEW = 			"New",
	__BUTTON_OPEN = 		"Open",
	__BUTTON_OPEN_READ_ONLY =	"Open Read Only";

/**
Whether to ignore calls to itemStateChanged or not.
*/
private boolean __ignoreItemStateChanged = false;

/**
Whether any additional wisses should be opened in readonly mode.
*/
private boolean __readonly = false;

/**
The parent JFrame running the application, for window positioning
*/
private JFrame __parent = null;

/**
GeoViewUI for map interactions.
*/
private GeoViewUI __geoview_ui = null;

/**
DMI for communicating with the database.
*/
private HydroBaseDMI __dmi;

/**
GUI for creating a new wis.
*/
private HydroBase_GUI_NewWIS __newWISGUI;

/**
The mode in which the GUI was opened.
*/
private int __mode;

/**
Status bar.
*/
private JTextField __statusJTextField;

/**
The list for displaying the sheet name dates.
*/
private SimpleJList<String> __historyList;

/**
Combo box to hold the sheet names.
*/
private SimpleJComboBox __sheetNameComboBox;

/**
Lists of objects.
*/
private List<HydroBase_WISSheetName> __wisVector;
//private List<HydroBase_WISComments> __sheetDatesVector;
//private List<HydroBase_WISSheetName> __sheetDatesVector;
//TODO SAM 2017-03-15 Confusing - the following list seems to mix the above two object types
private List<Object> __sheetDatesVector;

/**
Constructor.
@param parent JFrame that instantiates this class, for window positioning.
@param geoview_ui GeoViewUI for map interactions.
@param dmi an open and connected dmi object with which to communicate with
the database.
@param mode mode in which this dialog may be instantiated as follows:
WIS_BUILDER, DISTRICT_WIS, DIVISION_WIS
*/
public HydroBase_GUI_LoadWIS(JFrame parent, GeoViewUI geoview_ui,
HydroBaseDMI dmi, int mode) {
	__parent = parent;
	__geoview_ui = geoview_ui;
	__dmi = dmi;
        __mode = mode;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();
}

/**
Constructor.
@param parent JFrame that instantiates this class, for window positioning.
@param geoview_ui GeoViewUI for map interactions.
@param dmi an open and connected dmi object with which to communicate with
the database.
@param mode mode in which this dialog may be instantiated as follows:
WIS_BUILDER, DISTRICT_WIS, DIVISION_WIS
*/
public HydroBase_GUI_LoadWIS(JFrame parent, GeoViewUI geoview_ui,
		HydroBaseDMI dmi, int mode, boolean readonly) {
	__parent = parent;
	__geoview_ui = geoview_ui;
	__dmi = dmi;
        __mode = mode;
	__readonly = readonly;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();
}

/**
Responds to Action Events
@param event ActionEvent object
*/
public void actionPerformed(ActionEvent event) {
	String s = event.getActionCommand();

        if (s.equals(__BUTTON_CANCEL)) {
                cancelClicked();
        }       
        else if (s.equals(__BUTTON_DELETE)) {
		deleteClicked();
        }       
        else if (s.equals(__BUTTON_OPEN)) {
                okClicked(true);
        }
	else if (s.equals(__BUTTON_OPEN_READ_ONLY)) {
		okClicked(false);
	}
        else if (s.equals(__BUTTON_EDIT)) {
                editClicked();
        }        
        else if (s.equals(__BUTTON_NEW)) {
                newClicked();
        }        
        else if (s.equals(__BUTTON_HELP)) {
        }        
}

/**
Closes the GUI
*/
public void cancelClicked() {
	super.setVisible(false);
}

/**
Respond to the Delete button ACTION_EVENT.  Prompt the user to verify that they
want to delete a sheet and then do it.  This is useful for testing and
maintenance.
*/
private void deleteClicked() {
	String routine = "HydroBase_GUI_LoadWIS.deleteClicked";
	String message = "";

	try {
        // initialize variables
	String sheetName = "";
	// Parse out by a -
	sheetName = StringUtil.getToken(
		(String)__sheetNameComboBox.getSelectedItem(),
		"-", 0, 0).trim();
        // return if a date has not been selected
        int index = __historyList.getSelectedIndex();
        if (index == -1) {
                Message.printWarning(1, routine, "Must select a date.");
                return;
        }
	String sheetDateString = (String)__historyList.getSelectedItem();

        if (sheetDateString.regionMatches(true,0,"New",0,3)) {
                Message.printWarning(1, routine,
		"Cannot delete new sheet (does not exist yet).");
                return;
        }

	// Verify that user wants to delete...

	int x = new ResponseJDialog(this, "Delete WIS?",
		"Deleting a sheet is only recommended to clear out the current"
		+ " day\nor to delete a corrupt sheet.\n\n"
		+ "Delete the \"" + sheetName + "\" WIS for "
		+ sheetDateString + "?",
		ResponseJDialog.YES | ResponseJDialog.NO).response();
	if (x == ResponseJDialog.NO) {
		return;
	}
	DateTime sDateTime = null;
	try {	
		sDateTime = DateTime.parse(sheetDateString);
	}
	catch (Exception e) {
		Message.printWarning(1, routine,
			"Unable to delete WIS \"" + sheetName + "\" for date "
			+ sheetDateString);
		return;
	}
	if (sDateTime == null) {
		Message.printWarning(1, routine,
			"Unable to delete WIS \"" + sheetName + "\" for date "
			+ sheetDateString);
		return;
	}
	// Delete the WIS data, comments, and diversion coding.  First
	// get the sheet number.
	List<HydroBase_WISSheetName> v = __dmi.readWISSheetNameList(-999, -999, sheetName, null,true);

	// Loop through and find the WIS that has the nearest effective
	// before the selected date...
	int size = v.size();
       	HydroBase_WISSheetName wis = null;
	DateTime d;
	for (int i = (size - 1); i >= 0; i--) {
       		wis = v.get(i);
		d = new DateTime(wis.getEffective_date());
		if (d.lessThanOrEqualTo(sDateTime)) {
			break;
		}
		wis = null;
	}
	if (wis == null) {
		Message.printWarning(1, routine,
			"Unable to delete WIS \"" + sheetName + "\" for date "
			+ sheetDateString + " (can't find WIS).");
		return;
	}

	__dmi.deleteWISForWis_numSet_date(wis.getWis_num(), sDateTime);
	__dmi.deleteWISForWis_num(wis.getWis_num());

	__historyList.remove(index);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error deleting WIS.");
		Message.printWarning(2, routine, e);
	}

        message = "Finished deleting WIS.";
        Message.printStatus(1, routine, message);
        __statusJTextField.setText(message);
	// Leave the GUI visible.
}

/**
Responds to the edit jbutton action event and loads the selected wis.
*/
private void editClicked() {
        // initialize variables
        String function = "HBLoadInformationSheet.editClicked()";
        String sheetName = (String)__sheetNameComboBox.getSelectedItem();
        int index = __historyList.getSelectedIndex();

        if ((sheetName == null)|| sheetName.equals("")) {
                Message.printWarning(1, function,
			"A Water Information Sheet must be selected");
                return;
        }
        else if (index == -1) {
                Message.printWarning(1, function, "A date must be selected.");
                return;
        } 

	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Please Wait...Loading WIS");
 
      	HydroBase_WISSheetName data = (HydroBase_WISSheetName)__sheetDatesVector.get(index);

        new HydroBase_GUI_WISBuilder(__parent, __geoview_ui, __dmi, data);

	JGUIUtil.setWaitCursor(this, false);
        __statusJTextField.setText("Ready");
	cancelClicked();
}

/**
Clean up for garbage collection.
*/
protected void finalize()
throws Throwable {
	__parent = null;
	__dmi = null;
	__newWISGUI = null;
	__statusJTextField = null;
	__historyList = null;
	__sheetNameComboBox = null;
	__wisVector = null;
	__sheetDatesVector = null;
	super.finalize();
}

/**
Generates the SimpleJList items for all of the archived dates for the selected
sheetName as determined by the __sheetNameComboBox object.
*/
private void generateDates() {
	String routine = "HydroBase_GUI_LoadWIS.generateDates";
        String newRecord = "New Administrative Record for: ";
        // return if no sheet names exist
        if (__sheetNameComboBox.getItemCount() == 0) {
                return;
        }

	DateTime dt = null;
        int sheetIndex = __sheetNameComboBox.getSelectedIndex();
	
	int vsize = __wisVector.size();
	if (sheetIndex >= vsize) {
		return;
	}
        HydroBase_WISSheetName data = __wisVector.get(sheetIndex);
	String sheetName = data.getSheet_name();
        String tempString = "Please Wait...Retrieving dates for " + sheetName;
        __statusJTextField.setText(tempString);
 
        // process WIS query to get all the wis_nums associated with 
        // the currently selected sheet name
	JGUIUtil.setWaitCursor(this, true);
	try {
		List<HydroBase_WISSheetName> results = __dmi.readWISSheetNameList(-999, -999, sheetName,null,
		true);
	int size2 = 0;
	List<HydroBase_WISComments> results2 = null;

        __sheetDatesVector.clear();
        __historyList.removeAll();
	if (!results.isEmpty()) {
		if (__dmi.useStoredProcedures()) {
			List<String> rows = new Vector<String>();
	                int size = results.size();
	                for (int curRow = 0; curRow < size; curRow++) {
	                        data = results.get(curRow);
				results2 = __dmi.readWISCommentsList(
					data.getWis_num(), null, 42);
	
				if (!results2.isEmpty()) {
		                        size2 = results2.size();
		                        for (int i = 0; i < size2; 
						i++) {
		                                HydroBase_WISComments 
							commentsData = results2.get(i);
		                                dt = new DateTime(
							commentsData
							.getSet_date());
						rows.add(dt.toString(
							DateTime
							.FORMAT_Y2K_SHORT));
	                                	__sheetDatesVector.add(
							commentsData);
					}
	                        }
	                }
			__historyList.setListData(rows);
		}
		else {
			List<String> rows = new Vector<String>();
	                int size = results.size(); 
	                List<String> wis_numVector = new Vector<String>(size);
	                for (int curRow = 0; curRow < size; curRow++) {
	                        data = results.get(curRow);
	                        wis_numVector.add(""+ data.getWis_num());
	                }
			List<HydroBase_WISComments> resultsWISComments = __dmi.readWISCommentsList(wis_numVector, null);
	
	                // add the dates for the selected sheet into the 
			// __historyList object
			if (!resultsWISComments.isEmpty()) {
	                        size = results.size();
	                        for (int curRow = 0; curRow < size; curRow++) {
	                                HydroBase_WISComments commentsData = resultsWISComments.get(curRow);
	                                dt = new DateTime(
						commentsData.getSet_date());
					rows.add(dt.toString(
						DateTime.FORMAT_Y2K_SHORT));
	                                __sheetDatesVector.add(
						commentsData);
	                        }
	                }
			__historyList.setListData(rows);
		}
        }
	}
	catch (Exception e) {
		Message.printWarning(1, routine,"Error reading from database.");
		Message.printWarning(1, routine, e);
		ready();
		return;
	}


        // get the current day as a String and compare to the most recent day in
        // the __historyList object
        dt = new DateTime(DateTime.DATE_CURRENT);
        if (__historyList.getItemCount() > 0) {
                String mostRecentDay =((String)__historyList.getItem(0)).trim();

                // the current day has not been archived, insert a new item
                // in the __historyList object and in the sheetDatesVector
                if (!mostRecentDay.equals(
			dt.toString(DateTime.FORMAT_Y2K_SHORT))) {
                        __historyList.add(newRecord
				+ dt.toString(DateTime.FORMAT_Y2K_SHORT), 0);
                        __sheetDatesVector.add(0,new HydroBase_WISComments());
                }
        }
        else {
        	// no other dates exist to compare today's date against
                __historyList.add(newRecord 
			+ dt.toString(DateTime.FORMAT_Y2K_SHORT), 0);
                __sheetDatesVector.add(new HydroBase_WISComments());
        }
         
	// now select the first item in the list.
        if (__historyList.getItemCount() > 0) {
		 __historyList.select(0);
	}

	JGUIUtil.setWaitCursor(this, false);
        __statusJTextField.setText("Ready");
}

/**
Generates the SimpleJList items for all the effective dates for the selected
sheetName as determined by the __sheetNameComboBox object.
*/
private void generateFormatDates() {
        // return if no sheet names exist
        if (__sheetNameComboBox.getItemCount() == 0) {
                return;
        }
	
	String routine = "HydroBase_GUI_LoadWIS.generateFormatDates";

        int sheetIndex = __sheetNameComboBox.getSelectedIndex();
        HydroBase_WISSheetName data = __wisVector.get(sheetIndex);
        String sheetName = data.getSheet_name();
        __historyList.removeAll();
        __sheetDatesVector.clear();

        // process WIS query to get all the wis_nums associated with 
        // the currently selected sheet name
	JGUIUtil.setWaitCursor(this, true);
        String tempString = "Please Wait...Retrieving dates for " + sheetName;
        __statusJTextField.setText(tempString);
        Message.printStatus(1, routine, tempString);
        

	try {

		List<HydroBase_WISSheetName> results = __dmi.readWISSheetNameList(-999, -999, sheetName,
		null, true);
	
	if (!results.isEmpty()) {
                int size = results.size(); 

                // add the dates for the selected sheet into the __historyList
		// object
                for (int i = 0; i < size; i++) {
		        data = results.get(i);
                        DateTime date = new DateTime(data.getEffective_date());
                        __historyList.add(date.toString(
				DateTime.FORMAT_Y2K_SHORT));
                        __sheetDatesVector.add(data);
                }
        }
	}
	catch (Exception e) {
		Message.printWarning(1, routine,"Error reading from database.");
		Message.printWarning(1, routine, e);
		ready();
		return;
	}
	// now select the first item in the list.
        if (__historyList.getItemCount()> 0) {
		 __historyList.select(0);
	}
         
	JGUIUtil.setWaitCursor(this, false);
        __statusJTextField.setText("Ready");
}

/**
This function returns the wd syntax for where clauses according
to Districts selected in the user preferences.
@return a list of all the districts as strings.
*/
private List<String> getWD() {
	List<String> v = HydroBase_GUI_Util.generateWaterDistricts(__dmi, true);

	List<String> results = new Vector<String>();

	if (!v.isEmpty()) {
		int size = v.size();
		for (int i = 0; i < size; i++) {
			results.add("" + v.get(i).toString());
		}
	}
	return results;
}

/**
Perform a query on the Sheet_Name table and display the results in the sheet
name choice.
*/
private void getWISSheets() {
	String routine = "HydroBase_GUI_LoadWIS.getWISSheets";
	__ignoreItemStateChanged = true;

        __sheetNameComboBox.removeAll();

	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Please Wait...Retrieving WIS list...");
                              
        // results Vector contains the query results
        List<String> wds = getWD();
	try {
	// UNCONVERTED TO STORED PROCEDURES (2005-02-14)
		List<HydroBase_WISSheetName> results = __dmi.readWISSheetNameDistinctList(wds);
	java.util.Collections.sort(results);

        // display the results of the query within the appropriate GUI objects
	DateTime first = null;
	DateTime last = null;
	int nsheets = 0;
	HydroBase_WISSheetName data = null;
	String name = null;

	// this is so that the districts are listed in proper numerical
	// order (not just blindly in alphabetical order)
	List<HydroBase_WISSheetName> outOfOrder = new Vector<HydroBase_WISSheetName>();
	List<HydroBase_WISSheetName> newResults = new Vector<HydroBase_WISSheetName>();

	if (!results.isEmpty()) {
                int size = results.size(); 
		int len = 0;
                for (int i = 0; i < size; i++) {
                        data = results.get(i);

			name = data.getSheet_name().trim();

			if (name.startsWith("District ")) {
				// figure out the length of the district
				// number's number
				len = name.length() - 9;
				if (len == 1) {
					// this will be one of the misfiled
					// ones (like "District 2")
					outOfOrder.add(data);
				}
				else {
					newResults.add(data);
				}
			}
			else {
				newResults.add(data);
			}
		}
	}

	if (!outOfOrder.isEmpty()) {
		int entryPoint = 0;
		for (int i = 0; i < newResults.size(); i++) {
			data = newResults.get(i);
			name = data.getSheet_name().trim();
			if (name.startsWith("District ")) {
				entryPoint = i;
				i = newResults.size() + 1;
			}
		}
		for (int i = outOfOrder.size() - 1; i >= 0; i--) {
			newResults.add(entryPoint,outOfOrder.get(i));
		}
	}

	if (!newResults.isEmpty()) {
                int size = newResults.size(); 
		int vsize = 0;
		int wis_num = 0;	
		HydroBase_WISSheetName sheetName = null;
		List<HydroBase_WISSheetName> sheets = null;
		List<HydroBase_WISComments> wis_comments = null;
		List<HydroBase_WISComments> v = null;
                for (int i = 0; i < size; i++) {
                        data = newResults.get(i);

			name = data.getSheet_name();
			// First get the wis_num using the sheet_name as
			// the unique value...
			sheets = __dmi.readWISSheetNameList(
				DMIUtil.MISSING_INT, DMIUtil.MISSING_INT,
				name, null, true);

			nsheets = sheets.size();
			if (nsheets > 0) {
				wis_comments = new Vector<HydroBase_WISComments>();
				for (int j = 0; j < nsheets; j++) {
					sheetName = sheets.get(j);
					wis_num = sheetName.getWis_num();

					if (DMIUtil.isMissing(wis_num)) {
						continue;
					}
			
					v = __dmi.readWISCommentsList(wis_num, null);
					vsize = v.size();

					for (int k = 0; k < vsize; k++) {
						wis_comments.add(v.get(k));
					}

				}

				java.util.Collections.sort(wis_comments);

				nsheets = wis_comments.size();
				if (nsheets > 0) {
					last = new DateTime(
						wis_comments.get(
						nsheets - 1).getSet_date(),
						DateTime.PRECISION_DAY);
					first = new DateTime(wis_comments.get(0).getSet_date(),
						DateTime.PRECISION_DAY);
					__sheetNameComboBox.add(
						name + " - " + nsheets 
							+ " WIS " + first  
							+ "..." + last);
				}
				else {	
					__sheetNameComboBox.add(
						name + " - No sheets saved");
				}
			}
			else {	
				__sheetNameComboBox.add(
					name + " - No sheets saved");
			}
			__wisVector.add(data);
		}
        }
	}
	catch (Exception e) {
		Message.printWarning(1, routine,"Error reading from database.");
		Message.printWarning(1, routine, e);
		ready();
		return;
	}	
	// select the default wis according to preferences
	String def = __dmi.getPreferenceValue("WIS.WISdefault");
	if (__sheetNameComboBox.contains(def)) {
		__sheetNameComboBox.select(def);
	}
	else {
		__sheetNameComboBox.select(0);
	}

	__ignoreItemStateChanged = false;
	JGUIUtil.setWaitCursor(this, false);
        __statusJTextField.setText("Ready");
}

/**
Responds to ItemEvents
@param event ItemEvent object
*/
public void itemStateChanged(ItemEvent event) {
	if (__ignoreItemStateChanged) {
		return;
	}
	
	if (event.getStateChange() != ItemEvent.SELECTED) {
		return;
	}
	
	Object o = event.getItemSelectable();
	
        if (o == __sheetNameComboBox) {
		sheetNameSelected();
        }
}

/**
Responds to MouseEvents.  If the list is double-clicked on the WIS will be
either opened in editable mode or opened to be edited, determining on whether
the WIS or the WIS builder is using the dialog.
@param event MouseEvent object
*/
public void mouseClicked(MouseEvent event) {
	int count = event.getClickCount();
        if (count >= 2) {
		if (__mode == DISTRICT_WIS || __mode == DIVISION_WIS) {
			okClicked(true);
		}
		else if (__mode == WIS_BUILDER) {
			editClicked();
		}	
        }
}

/**
Responds to mouse entered events; does nothing.
@param event the MouseEvent that happened.
*/
public void mouseEntered(MouseEvent event) {}

/**
Responds to mouse exited events; does nothing.
@param event the MouseEvent that happened.
*/
public void mouseExited(MouseEvent event) {}

/**
Responds to mouse pressed events; does nothing.
@param event the MouseEvent that happened.
*/
public void mousePressed(MouseEvent event) {}

/**
Responds to mouse released events; does nothing.
@param event the MouseEvent that happened.
*/
public void mouseReleased(MouseEvent event) {}

/**
Prompts user for new water information sheet information.
*/
private void newClicked() {
	if (__newWISGUI == null) {	
	        __newWISGUI = new HydroBase_GUI_NewWIS(__parent, __geoview_ui, __dmi, this);
	}
	else {	
		__newWISGUI.setVisible(true);
	}
}

/**
Responds to the OK JButton event and displays the selected water information
sheet.  This doesn't apply to the WIS builder.
*/
private void okClicked(boolean editable) {
        String routine = "HBLoadInfomationSheetGUI.okClicked";

	try {
        int index = __historyList.getSelectedIndex();
        int numItems = __historyList.getItemCount();
        String date = null;
	String sheetName = "";

	// Parse out by a -
	sheetName = StringUtil.getToken(
		(String)__sheetNameComboBox.getSelectedItem(),
		"-", 0, 0).trim();

        boolean isNewSheet = false;
 
        // return if a date has not been selected
        if (index == -1) {
                Message.printWarning(1, routine, "Must select a date.");
                return;
        }

        HydroBase_WISComments data = 
		(HydroBase_WISComments)__sheetDatesVector.get(index);
	String status = null;
	Date setDate = null;
	Date archiveDate = null;
	Date recentFormat = null;
	Date recentWIS = null;

	boolean previousData = false;

	int oldFormatWISNum = DMIUtil.MISSING_INT;

        // new administrative record is selected. get the data for the most
	// recent data(next row in the history list). if no row is available,
	// load a sheet with no data. The check for MISSING_DATE will catch the
	// item corresponding to a new adminstration record as determined in
	// generateDates(). if this item is added to the __historyList object
	// then an empty HydroBase_WISComments object is added at element 0 in 
	// the __sheetDatesVector.
	HydroBase_WISSheetName sheetData = null;
//System.out.println("Archive date: " + data.getArchive_date());	
        if (DMIUtil.isMissing(data.getArchive_date())) {
                // get the most recent wis_num for the selected sheet name.
                // most recent is determined via the effective date in
		// Sheet_Name table.

        	List<HydroBase_WISSheetName> results = __dmi.readWISSheetNameList(-999, -999, 
			sheetName, null, true);

                // element 0 is the most recent date since the order of the
		// results is descending by effective date. get the effective
		// date.
                sheetData = (HydroBase_WISSheetName)results.get(0);
                recentFormat = sheetData.getEffective_date();
         
                // only item in the list is the new administrative record item
                // will need to load a sheet using the wis_num of the currently
		// selected sheet name.
                if (numItems == 1) {
//System.out.println("NUM ITEMS 1");		
                        setDate = null;
                        archiveDate = null;
			Message.printStatus(1, routine,
				"Loading with no previous data.");
                        status = "";
                        isNewSheet = true;
                }
                // load the data from the most recently saved date. however,
		// the format may be more recent than the most recently
		// archived WIS_Data. if so, load the most
                // recent WIS_format with empty data.
		else {	
			previousData = true;
			data = (HydroBase_WISComments)
				__sheetDatesVector.get(index+1);
                        recentWIS = data.getArchive_date();
                        // format is more recent than the next WIS
                        if (recentWIS.before(recentFormat)) {
                                setDate = null;
                                archiveDate = null;
				Message.printStatus(1, routine, "Loading a "
					+"newer format with no previous data.");
                                status = "";
                                isNewSheet = true;
				previousData = false;
                        }
                        else {	
				setDate = data.getSet_date();
				Message.printStatus(1, routine, "Copying "
					+ "data from previous date.");
                                status = "Copied from adminstrative data for: ";
                                DateTime tsSet = new DateTime(setDate);
                                status += tsSet.toString(
					DateTime.FORMAT_Y2K_SHORT);
                                isNewSheet = true;
                        }
                }
        }
        else {	
		// check for most recent format           
                recentWIS = data.getArchive_date();

                List<HydroBase_WISSheetName> results = __dmi.readWISSheetNameList(-999, -999, 
			sheetName, null, true);
                sheetData = results.get(0);
                recentFormat = sheetData.getEffective_date();
               	if (recentWIS.before(recentFormat)) {
	                setDate = data.getSet_date();
                       	archiveDate = null;
			Message.printStatus(1, routine, "Loading a newer "
				+ "format with no previous data.");
                        status = "";
                      	isNewSheet = false;
			previousData = true;
			oldFormatWISNum = data.getWis_num();
               	}
		else {	
			sheetData.setWis_num(data.getWis_num());
	                setDate = data.getSet_date();
        	        archiveDate = data.getArchive_date();
	                status = "Archived on: ";
        	        DateTime tsArchive = new DateTime(archiveDate);
                	status += tsArchive.toString(DateTime.FORMAT_Y2K_SHORT);
	                isNewSheet = false;
		}
        }
	
        // format the setDate
        if (setDate != null) {
                DateTime tsDate = new DateTime(setDate);
		date = tsDate.toString(DateTime.FORMAT_YYYY_MM_DD_HH_mm);
        }
        else {
                date = null;
        }


Message.printStatus(1, "", "      mode: " + __mode);
Message.printStatus(1, "", "      date: " + date);
Message.printStatus(1, "", "      status: " + status);
Message.printStatus(1, "", "      sheetname: " + sheetName);
Message.printStatus(1, "", "      isNewSheet: " + isNewSheet);
Message.printStatus(1, "", "      previousData: " + previousData);
Message.printStatus(1, "", "      oldFormatWISNum: " + oldFormatWISNum);

	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Please Wait...Displaying WIS ");
	new HydroBase_GUI_WIS(__parent, __geoview_ui, __dmi, sheetData, __mode, date, 
		status, sheetName, isNewSheet, previousData, editable,
		oldFormatWISNum);
		
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error displaying WIS.");
		Message.printWarning(2, routine, e);
	}

	JGUIUtil.setWaitCursor(this, false);
        Message.printStatus(1, routine, "Finished Displaying WIS.");
        __statusJTextField.setText("Finished Displaying WIS.");

        // hide this GUI object
	if (!IOUtil.testing()) {
		// except for when doing testing, because it's VERY useful
		// to open one WIS after another rapidly.
	        cancelClicked();
	}
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
	addWindowListener(this);

        // objects used throughout the GUI layout
        Insets insetsNLNR = new Insets(0,7,0,7);
        Insets insetsTLNN = new Insets(7,7,0,0);
        Insets insetsTNNR = new Insets(7,0,0,7);
        GridBagLayout gbl = new GridBagLayout();

        // Top JPanel
        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(gbl);
        getContentPane().add("North", topJPanel);

        JGUIUtil.addComponent(topJPanel, new JLabel("Sheet Name:"), 
		0, 0, 1, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
                
        __sheetNameComboBox = new SimpleJComboBox();
	__sheetNameComboBox.addItemListener(this);
        JGUIUtil.addComponent(topJPanel, __sheetNameComboBox, 
		1, 0, 1, 1, 1, 0, insetsTNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(gbl);
        getContentPane().add("Center", centerJPanel);
         
        JGUIUtil.addComponent(centerJPanel, new JLabel("Date:"), 
		0, 1, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
                
        __historyList = new SimpleJList<String>();
	__historyList.addMouseListener(this);
        JGUIUtil.addComponent(centerJPanel, new JScrollPane(__historyList),
		0, 2, 2, 1, 1, 1, insetsNLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JGUIUtil.addComponent(centerJPanel, buttonJPanel, 
		0, 3, 2, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST); 
 
 	SimpleJButton openReadOnly = new SimpleJButton(__BUTTON_OPEN_READ_ONLY,
		this);
	openReadOnly.setToolTipText("Accept selection and open WIS in "
		+ "read-only mode.");
        SimpleJButton open = new SimpleJButton(__BUTTON_OPEN, this);
	open.setToolTipText("Accept selection and open WIS.");
        SimpleJButton edit = new SimpleJButton(__BUTTON_EDIT, this);
	edit.setToolTipText("Edit existing WIS.");
        SimpleJButton newWIS = new SimpleJButton(__BUTTON_NEW, this);
	newWIS.setToolTipText("Create a new WIS.");

        if (__mode != WIS_BUILDER) {
		buttonJPanel.add(openReadOnly);
		if (!__readonly) {		
	                buttonJPanel.add(open);
		}
        }
         
        if (__mode == WIS_BUILDER) {
                buttonJPanel.add(edit);
                buttonJPanel.add(newWIS);
        }

        if (!__dmi.canWriteToDatabase()) {
                newWIS.setEnabled(false);
        }
	
	SimpleJButton cancel = new SimpleJButton(__BUTTON_CANCEL, this);
	cancel.setToolTipText("Close dialog and return.");
        buttonJPanel.add(cancel);
//        buttonJPanel.add(new SimpleJButton(__BUTTON_HELP, this));
	if (IOUtil.testing()) {
		if (__dmi.canWriteToDatabase()) {
			// Add a delete button to allow deleting a sheet.  Use 
			// this to completely clear a current sheet or a 
			// corrupt old sheet.
			SimpleJButton del = new SimpleJButton(__BUTTON_DELETE, 
				this);
			del.setToolTipText("Delete existing WIS.");
	        	buttonJPanel.add(del);
		}
	}
                                  
        // South JPanel
        JPanel southJPanel = new JPanel();
        southJPanel.setLayout(gbl);
        getContentPane().add("South", southJPanel);
                                
        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(southJPanel, __statusJTextField, 
		0, 0, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}

        // frame settings        
        if (__mode == WIS_BUILDER) {      
                setTitle(app + "Build Water Information Sheet");
        }
	else {	
		setTitle(app + "Load Information Sheet");
        }
        pack();
        setSize(525, 400);
	JGUIUtil.center(this);
        setVisible(true);
}

/**
This function shows/hides GUI
@param state true if showing the dialog, false if hiding it.
*/
public void setVisible(boolean state) {
	if (state) {
                // reset member variables
                __wisVector = new Vector<HydroBase_WISSheetName>();
                __sheetDatesVector = new Vector<Object>();

                // generate a list of WIS sheets and show dates for the 
                // top most sheet in the list.
                if (__mode == DISTRICT_WIS || __mode == DIVISION_WIS) {
                        getWISSheets();          
                        generateDates();
                }
                else if (__mode == WIS_BUILDER) {
                        getWISSheets();
                        generateFormatDates();
                }
        }
        super.setVisible(state);
}

/**
Responds to the __sheetNameComboBox action event and generates dates according
to the __mode in which this gui was instantiated.
*/
private void sheetNameSelected() {
	if (__mode == DISTRICT_WIS || __mode == DIVISION_WIS) {      
	        generateDates();
        }
        else if (__mode == WIS_BUILDER) {
	        generateFormatDates();
        }
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
Closes the GUI
@param event WindowEvent object
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
