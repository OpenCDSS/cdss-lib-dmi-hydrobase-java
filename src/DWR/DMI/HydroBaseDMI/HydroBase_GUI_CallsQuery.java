// HydroBase_GUI_CallsQuery - CallChronology GUI

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
// HydroBase_GUI_CallsQuery - CallChronology GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 05 Aug 1997	DLG, RTi 		Created initial class description.
// 29 Sep 1997 	DLG, RTi		Added submit query
// 08 Dec 1997	SAM, RTi		Enable print, export.
// 11 Feb 1998  DLG, RTi		Submits query for default interval when
//					showing the GUI. Updated to java
//					1.1 event model.
// 22 Apr 1998  DLG, RTi		Maded changes specified by 
//					crdss-00467.txt
// 23 Apr 1998  DLG, RTi		Added javadoc comments.
// 29 May 1998  CGB, RTi		Added simple graphing capabilities
//					An additional method needs to be defined
//					which will retrieve selected strings
//					from the lists based on column id!!
//					This will help clean up this class.
// 04 Apr 1999	Steven A. Malers, RTi	Add HBDMI to queries.
// 15 Apr 1999	SAM, RTi		Add a refresh button.
// 2001-11-12	SAM, RTi		Change GUI to GUIUtil.
// 2002-02-25	SAM, RTi		Remove a status message.  Pass the
//					call data to HBReleaseCallGUI so that
//					it can be easily displayed.
//-----------------------------------------------------------------------------
// 2003-03-28	J. Thomas Sapienza, RTi	Began moving the GUI to Swing.
// 2003-03-31	JTS, RTi		Continued moving the GUI to Swing.
// 2003-04-02	JTS, RTi		* Corrected miscellaneous problems in 
//					  in formatting output data.
//					* Changed ResponseDialogs to 
//					  ResponseJDialogs.
// 2003-04-04	JTS, RTi		Changed GUIUtil to JGUIUtil.
// 2003-04-07	JTS, RTi		Added code to enable/disable buttons
//					as different things are selected.
// 2003-05-30	JTS, RTi		Added code to copy from the table.
// 2003-06-02	JTS, RTi		Added code so an hourglass displays when
//					sorting the tables
// 2003-07-28	JTS, RTi		* Updated JWorksheet code to stop using
//					  deprecated methods.
//					* Removed old JWorksheet method of
//					  enabling copy/paste.
// 2003-09-23	JTS, RTi		Changed the export code to use 
//					the new export code in 
//					HydroBase_GUI_Util.
// 2004-01-12	SAM, RTi		* Add tool tips to important components.
//					* Comment out the help button.
// 2004-01-20	JTS, RTi		Began using the JScrollWorksheet in
//					order to use worksheet row headers.
// 2004-06-14	JTS, RTi		Time series can now be dragged out of
//					the worksheets and into a time series
//					product properties window.
// 2004-07-20	JTS, RTi		When reactivating a released calls, 
//					users can now choose to cancel out
//					of the operation, to blank the release
//					comments or to leave the release
//					comments.  
// 2005-02-14	JTS, RTi		Converted queries to use stored 
//					procedure queries.
// 2005-02-23	JTS, RTi		readCallsListForCall_num() was changed
//					to readCallsForCall_num().
// 2005-04-12	JTS, RTi		Added button for editing call comments.
// 2005-04-25	JTS, RTi		Turned dmiWrite() calls into dmi calls.
// 2005-04-26	JTS, RTi		* Moved Set Comments button position.
//					* Release comments can only be edited
//					  now if the call has been released.
//					* Renamed "Set Comments" button to
//					  "Edit Comments".
// 					* Added a "Copy Call" button.
// 2005-04-27	JTS, RTi		Added all data members to finalize().
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
// 2005-06-28	JTS, RTi		Removed DMI parameters from table models
// 2005-08-03	JTS, RTi		Returned record totals are returned 
//					properly now for people who limit the
//					returned records to only certain WDs.
// 2005-08-15	JTS, RTi		Copying a call now creates a copied
//					object of the call to use in the copy
//					call GUI, rather than using the object
//					being displayed in the worksheet.
// 2007-02-07	SAM, RTi		Remove dependences on CWRAT.
//					Just pass a JFrame as the parent.
//					Add GeoViewUI to the constructor to handle map interaction.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.DragAndDropJWorksheet;
import RTi.Util.GUI.DragAndDropListener;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.DateTimeBuilderJDialog;
import RTi.Util.Time.StopWatch;

/**
GUI for doing call chronology work.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_CallsQuery 
extends JFrame 
implements ActionListener, DragAndDropListener, MouseListener, WindowListener {

/**
The Calls gui mode.
*/
public final static int CALLS = 0;
/**
The edit calls gui mode.
*/
public final static int EDIT_CALLS = 1;

/**
Reference to each kind of worksheet.
*/
// TODO SAM 2007-02-26 Evaluate if needed
//private final int
//	__MAIN_WORKSHEET = 0,
//	__TRIB_WORKSHEET = 1;

/**
Misc strings.
*/
private final String    
		__MAIN_LABEL = 		"Main Stem Calls:", 
                __TRIB_LABEL = 		"Tributary Calls:", 
		__BUTTON_COPY_CALL = 	"Copy Call",
		__BUTTON_EDIT_COMMENTS = "Edit Comments",
		__BUTTON_GRAPH = 	"Graph",
		__BUTTON_PRINT = 	"Print",
		__BUTTON_HELP = 	"Help",
		__BUTTON_EXPORT = 	"Export",
		__BUTTON_CLOSE = 	"Close",
		__BUTTON_SET_CALL = 	"Set Call",
		__BUTTON_SET_PERIOD = 	"Set Period",
		__BUTTON_REFRESH = 	"Refresh",
		__BUTTON_RELEASE_CALL =	"Release Call",
		__BUTTON_REACTIVATE =	"Reactivate Released Call",
		__BUTTON_REMOVE_CALL = 	"Remove Call";		

/**
The worksheet that displays main stem data.
*/
private DragAndDropJWorksheet __mainTable;

/**
The worksheet that displays tributary data.
*/
private DragAndDropJWorksheet __tribTable;

/**
The parent frame running the application.
*/
private JFrame __parent = null;

/**
The GeoViewUI instance for map interaction.
*/
private GeoViewUI __geoview_ui = null;

/**
Reference to an open and non-null DMI object.
*/
private HydroBaseDMI __dmi;

/**
The count of the number of records being displayed currently on the GUI, 
filtered for the user's districts.
*/
private int __filteredRecordCount = 0;

/**
The mode the GUI is is, either CALLS or EDIT_CALLS.
*/
private int __guiMode;

/**
The worksheet that was last-clicked in (for telling which one time series
should be dragged from).
*/
// TODO SAM 2007-02-26 Evaluate if needed
//private int __lastClicked = -1;

/**
The label that accompanies the main table.
*/
private JLabel __mainJLabel;

/**
The label that accompanies the tributary table.
*/
private JLabel __tribJLabel;

/**
The textfield that displays the from date.
*/
private JTextField __fromJTextField;

/**
The status bar.
*/
private JTextField __statusJTextField;

/**
The textfield that displays the to date.
*/
private JTextField __toJTextField;

/**
The HydroBase_GUI_SetCall gui that this gui can open.
*/
private HydroBase_GUI_SetCall __setCallGUI;

/**
The date builder property list.
*/
private PropList __dateProps;

/**
GUI buttons.
*/
private SimpleJButton 
	__copyCallJButton,
	__editCommentsJButton,
	__exportJButton,
	__graphJButton,
	__printJButton,
	__reactivateJButton,
	__releaseJButton,
	__removeJButton,
	__setJButton;

/**
Constructor.
@param parent the parent JFrame object, to position windows.
@param geoview_ui The GeoViewUI instance for handling maps.
@param dmi an open and non-null HydroBaseDMI object.
@param flag CALLS, or EDIT_CALLS
*/
public HydroBase_GUI_CallsQuery(JFrame parent, GeoViewUI geoview_ui, HydroBaseDMI dmi, 
int flag) {
	__parent = parent;
	__geoview_ui = geoview_ui;
	__dmi = dmi;
	__guiMode = flag;	
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        setupGUI();
}

/**
Handles the events using the ActionListener.
@param event ActionEvent object
*/
public void actionPerformed(ActionEvent event) {
	String routine = "HydroBase_GUI_CallsQuery.actionPerformed";
	String s = event.getActionCommand();

        if (s.equals(__BUTTON_CLOSE)) {
		closeClicked();
        }
	else if (s.equals(__BUTTON_COPY_CALL)) {
		copyCall();
	}
	else if (s.equals(__BUTTON_GRAPH)) {
		graphClicked();
	}
	else if (s.equals(__BUTTON_EDIT_COMMENTS)) {
		editComments();
	}	
	else if (s.equals(__BUTTON_EXPORT)) {
		try {
			String[] eff = 
				HydroBase_GUI_Util.getExportFilenameAndFormat(
				this, 
				HydroBase_GUI_Util.getFormatsAndExtensions());

			if (eff == null) {
				return ;
			}

			int format = new Integer(eff[1]).intValue();
	 		// First format the output...
			List<String> outputStrings = formatOutput(format);
 			// Now export, letting the user decide the file...
			HydroBase_GUI_Util.export(this, eff[0], outputStrings);
		} 
		catch (Exception ex) {
			Message.printWarning(1, routine,"Error in export.");
			Message.printWarning(2, routine, ex);
		}
	}
        else if (s.equals(__BUTTON_HELP)) {
        }
	else if (s.equals(__BUTTON_PRINT)) {
		try {
			SelectFormatTypeJDialog d = 
				new SelectFormatTypeJDialog(this, 
				HydroBase_GUI_Util.getFormats());
			int format = d.getSelected();
			if (format == HydroBase_GUI_Util.CANCEL) {
				return;
			}			
			d.dispose();
	 		// First format the output...
			List<String> outputStrings = formatOutput(format);
			outputStrings.add("");
			outputStrings.add("PRINTED AT: " 
				+ (new DateTime(DateTime.DATE_CURRENT))
				.toString(DateTime.FORMAT_YYYY_MM_DD_HH_mm));
	 		// Now print...
			PrintJGUI.print(this, outputStrings, 8);
		}
		catch (Exception ex) {
			Message.printWarning (1, routine, ex);
		}			
	}
	else if (s.equals(__BUTTON_REFRESH)) {
		refresh();
	}
        else if (s.equals(__BUTTON_SET_PERIOD)) {
                setIntervalClicked();
        }
        else if (s.equals(__BUTTON_RELEASE_CALL)) {
		releaseCallClicked();	
        }
        else if (s.equals(__BUTTON_SET_CALL)) {
		setCallClicked();
        }
        else if (s.equals(__BUTTON_REMOVE_CALL)) {
		removeClicked();
        }
        else if (s.equals(__BUTTON_REACTIVATE)) {
		reactivateClicked();
        }
}

/**
Checks the status of the form in regard to the number of rows that are selected
and enables or disables the graph, print, export, release, remove and 
reactivate buttons as necessary.
*/
private void checkAndSetButtons() {
	int mainRows = __mainTable.getSelectedRowCount();
	int tribRows = __tribTable.getSelectedRowCount();

	if (mainRows == 0 && tribRows == 0) {	
		__graphJButton.setEnabled(false);
//		__printJButton.setEnabled(false);
//		__exportJButton.setEnabled(false);
	}
	else {
		__graphJButton.setEnabled(true);
//		__printJButton.setEnabled(true);
//		__exportJButton.setEnabled(true);
	}

	if ((mainRows == 1 && tribRows == 0) ||
	    (tribRows == 1 && mainRows == 0)) {
		__releaseJButton.setEnabled(true);
		__removeJButton.setEnabled(true);
		__reactivateJButton.setEnabled(true);
		__editCommentsJButton.setEnabled(true);
		__copyCallJButton.setEnabled(true);
	}
	else {
		__releaseJButton.setEnabled(false);
		__removeJButton.setEnabled(false);
		__reactivateJButton.setEnabled(false);
		__editCommentsJButton.setEnabled(false);
		__copyCallJButton.setEnabled(false);
	}
}

/**
Closes the GUI. 
*/
protected void closeClicked() {
	setVisible(false);
}

/**
Copies a call.
*/
private void copyCall() {
	HydroBase_Calls call = null;
	int size = __mainTable.getSelectedRows().length;
	int row = 0;
	if (size > 0) {
		row = __mainTable.getSelectedRow();
		call = (HydroBase_Calls)__mainTable.getRowData(row);
	}
	else {
		row = __tribTable.getSelectedRow();
		call = (HydroBase_Calls)__tribTable.getRowData(row);
	}

	HydroBase_Calls copyCall = new HydroBase_Calls(call);
	copyCall.setDate_time_released(null);
	copyCall.setRelease_comments("");

	new HydroBase_GUI_SetCall(this, __parent, __geoview_ui, __dmi, copyCall);
}

/**
Displays the query results within the appropriate JWorksheet object
(i.e., main stem vs. tributary).  It takes the results of the query and
determines which records belong to the main stem and which belong to the
tributaries.  Each kind of record goes in a separate Vector and these Vectors
are used to populate the table model for the appropriate worksheet.
@param results Vector containing HBCalls results.
*/
private void displayResults(List<HydroBase_Calls> results, String divisions, List<String> divVector) {
	String routine = "HydroBase_GUI_CallsQuery.displayResults";
	if (results == null || results.size() == 0) {		
		return;
	}

        // Display results
        int main = 0;
        int trib = 0;
        int size = results.size();

	int[] divs = new int[divVector.size()];
	for (int i = 0; i < divVector.size(); i++) {
		divs[i] = StringUtil.atoi(((String)divVector.get(i)));
	}
	
	boolean inDivs = false;
	int div = 0;
	List<HydroBase_Calls> mainItems = new Vector<HydroBase_Calls>();
	List<HydroBase_Calls> tribItems = new Vector<HydroBase_Calls>();
	HydroBase_Calls data = null;
        for (int i = 0; i < size; i++) {
                data = results.get(i);
		div = data.getDiv();
		inDivs = false;

		for (int j = 0; j < divs.length; j++) {
			if (div == divs[j]) {
				inDivs = true;
				j = 999;
			}
		}

		if (!inDivs) {
			continue;
		}
         
                // if getTribTo returns 0 add to main stem List, otherwise
                // add it to the tributary List.
                if (data.getStrtribto() == 0) {
                        mainItems.add(data);
                        main++;
                }
                else {	
			tribItems.add(data);
                        trib++;
                }
        }

	String mainRecords = "record";
        if (main > 1) {
		mainRecords += "s";
        }
        __mainJLabel.setText(__MAIN_LABEL + " (" + main 
		+ " " + mainRecords + " returned " + divisions + "):");
	

	String tribRecords = "record";
        if (trib > 1) {
		tribRecords += "s";
        }
	__tribJLabel.setText(__TRIB_LABEL + " (" + trib 
		+ " " + tribRecords + " returned " + divisions + "):");

	__filteredRecordCount = main + trib;

	// Set up the data, table model and cell renderer for the 
	// main table
	try {
        	__mainTable.clear();
		HydroBase_TableModel_Calls tm = 
			new HydroBase_TableModel_Calls(mainItems);
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
		__mainTable.setCellRenderer(cr);
		__mainTable.setModel(tm);
		__mainTable.setColumnAlignment(7, JWorksheet.RIGHT);
		__mainTable.setColumnWidths(tm.getColumnWidths());
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Unable to build table for "
			+ "main");
		Message.printWarning(2, routine, e);
	}

	// Set up the data, table model and cell renderer for the 
	// tributary table
	try {
        	__tribTable.clear();
		HydroBase_TableModel_Calls tm = 
			new HydroBase_TableModel_Calls(tribItems);
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
		__tribTable.setCellRenderer(cr);
		__tribTable.setModel(tm);
		__tribTable.setColumnAlignment(7, JWorksheet.RIGHT);
		__tribTable.setColumnWidths(tm.getColumnWidths());
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Unable to build table for "
			+ "tributary");
		Message.printWarning(2, routine, e);
	}		

	checkAndSetButtons();
}

/**
Called just before the drag starts.  Used to get the time series to be dragged
and put it in the Transferable object for the drag.
*/
public boolean dragAboutToStart() {
	return true;
}

public void dragUnsuccessful(int action) {}
public void dragSuccessful(int action) {}
public void dropAllowed() {}
public void dropExited() {}
public void dropNotAllowed() {}
public void dropSuccessful() {}
public void dropUnsuccessful() {}
public void dragStarted() {}

/**
Called when the user chooses to edit the comments for a call.
*/
private void editComments() {
	boolean main = true;
	HydroBase_Calls call = null;
	int size = __mainTable.getSelectedRows().length;
	int row = 0;
	if (size > 0) {
		row = __mainTable.getSelectedRow();
		call = (HydroBase_Calls)__mainTable.getRowData(row);
	}
	else {
		main = false;
		row = __tribTable.getSelectedRow();
		call = (HydroBase_Calls)__tribTable.getRowData(row);
	}

	boolean released = true;
	Date d = call.getDate_time_released();
	if (DMIUtil.isMissing(d)) {
		released = false;
	}
	boolean changed = (new HydroBase_GUI_CallsComments(this, __dmi,
		call.getCall_num(), call.getSet_comments(),
		call.getRelease_comments(), call.getDistricts_affected(),
		released)).changed();

	if (changed) {
		refresh();
	}

	if (main) {
		__mainTable.selectRow(row);
	}
	else {
		__tribTable.selectRow(row);
	}
	checkAndSetButtons();
}

/**
Cleans up when the object is finalized.
*/
public void finalize()
throws Throwable {
	__mainTable = null;
	__tribTable = null;
	__parent = null;
	__dmi = null;
	__mainJLabel = null;
	__tribJLabel = null;
	__fromJTextField = null;
	__statusJTextField = null;
	__toJTextField = null;
	__setCallGUI = null;
	__dateProps = null;
	__copyCallJButton = null;
	__editCommentsJButton = null;
	__exportJButton = null;
	__graphJButton = null;
	__printJButton = null;
	__reactivateJButton = null;
	__releaseJButton = null;
	__removeJButton = null;
	__setJButton = null;
	super.finalize();
}

/**
Formats the data in a HydroBase_Calls object into a line of data for the 
screen or summary report.
@param call the HydroBase_Calls object for which to format its data.
@param delim the delim to use for spacing between formatted output values.
@param fullPad whether to pad out the field to its maximum length (e.g., 
format of "%-16.16s") or only print out the existing characters (e.g., format
of "%-16s").  True for screen view and summary reports, false otherwise.
@return a string suitable to be placed in a Vector for the report.
*/
private String formatLine(HydroBase_Calls call, char delim, boolean fullPad) {
	String routine = "HydroBase_GUI_CallsQuery.formatLine";
	String line = "";
	String s = "";
	String format = "";
	
	try {
	// start date
	Date dt = call.getDate_time_set();
	if (DMIUtil.isMissing(dt)) {
		s = " ";
	}
	else {
		s = (new DateTime(dt)).toString(
			DateTime.FORMAT_YYYY_MM_DD_HH_mm);
	}
	if (fullPad) {
		format = "%-16.16s";
		line += StringUtil.formatString(s, format) + delim;
	}
	else {
		format = "%-16s";
		line += StringUtil.formatString(s, format).trim() + delim;
	}
	
	// end date
	dt = call.getDate_time_released();
	if (DMIUtil.isMissing(dt)) {
		s = "ACTIVE";
	}
	else {
		s = (new DateTime(dt)).toString(
			DateTime.FORMAT_YYYY_MM_DD_HH_mm);	
	}
	if (fullPad) {
		format = "%-16.16s";
		line += StringUtil.formatString(s, format) + delim;
	}
	else {
		format = "%-16s";
		line += StringUtil.formatString(s, format).trim() + delim;
	}

	// str name
	if (fullPad) {
		format = "%-24.24s";
		line += StringUtil.formatString(call.getStrname(), format) 
			+ delim;
	}
	else {
		format = "%-24s";
		line += StringUtil.formatString(
			call.getStrname(), format).trim() + delim;
	}	

	// wdid
	String wds = null;
	String ids = null;
	int wd = call.getWD();
	if (DMIUtil.isMissing(wd)) {
		wds = "";
	}
	else {
		wds = "" + wd;
	}			
	int id = call.getID();
	if (DMIUtil.isMissing(id)) {
		ids = "";
	}
	else {
		ids = "" + id;
	}	

	if (fullPad) {
		line += StringUtil.formatString(
			HydroBase_WaterDistrict.formWDID(wds, ids), "%-7d") 
			+ delim;
	}
	else {
		line += StringUtil.formatString(
			HydroBase_WaterDistrict.formWDID(wds, ids), 
			"%-7d").trim()
			+ delim;
	}

	// str_name
	if (fullPad) {
		format = "%-40.40s";
		line += StringUtil.formatString(call.getStr_name(), format) 
			+ delim;
	}
	else {
		format = "%-40s";
		line += StringUtil.formatString(call.getStr_name(), 
			format).trim() + delim;
	}	
	
	// apro date
	if (fullPad) {
		format = "%-10.10s";
	}
	else {
		format = "%-10s";
	}
	line += StringUtil.formatString(call.getApro_date(), format) + delim;
	
	// admin no
	line += StringUtil.formatString(call.getAdminno(), "%-11.5f") + delim;

	// decree amt
	if (fullPad) {
		format = "%-20.20s";
		line += StringUtil.formatString(call.getDcr_amt(), format) 
			+ delim;
	}
	else {
		format = "%-20s";
		line += StringUtil.formatString(call.getDcr_amt(), 
			format).trim() + delim;
	}	

	// districts affected
	if (fullPad) {
		format = "%-18.18s";
		line += StringUtil.formatString(call.getDistricts_affected(), 
			format) + delim;
	}
	else {
		format = "%-18s";
		line += StringUtil.formatString(call.getDistricts_affected(), 
			format).trim() + delim;
	}

	// set comments
	if (fullPad) {
		format = "%-30.30s";
		line += StringUtil.formatString(call.getSet_comments(), format) 
			+ delim;
	}
	else {
		format = "%-30s";
		line += StringUtil.formatString(call.getSet_comments(), 
			format).trim() + delim;
	}

	// release comments
	if (fullPad) {
		format = "%-30.30s";
		line += StringUtil.formatString(call.getRelease_comments(), 
			format);
	}
	else {
		format = "%-30s";
		line += StringUtil.formatString(call.getRelease_comments(), 
			format).trim();
	}

	}
	catch (Exception e) {
		Message.printWarning(2, routine, e);
		line = "ERROR FORMATTING LINE";
	}
	return line;
}

/**
Responsible for formatting output.
@param format format delimiter flag defined in this class
@return returns a formatted Vector for exporting, printing, etc..
*/
public List<String> formatOutput(int format) {
	int size = 0;
	HydroBase_Calls call = null;
	List<String> v = new Vector<String>();

	
	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
		// Just echo what is on the screen...
		if (__guiMode == CALLS) {
			v.add("CALLS FOR TIME PERIOD:  " +
				__fromJTextField.getText()+ " TO " +
				__toJTextField.getText());
		}
		size = __mainTable.getRowCount();
		if (size > 0) {
			v.add("");
			v.add("MAIN STEM CALLS");
			v.add(
				"START DATE       END DATE         WATER "
				+ "SOURCE             WDID    STRUCTURE "
				+ "NAME                           APPRO "
				+ "DATE ADMIN NO    DEC AMT              "
				+ "DISTRICTS AFFECTED SET COMMENTS       "
				+ "            RELEASE COMMENTS");
			v.add(
				"_____________________________________________"
				+ "___________________________________________"
				+ "___________________________________________"
				+ "_______________________________________");
			// Now do the list...
			for (int i = 0; i < size; i++) {
				call = (HydroBase_Calls)
					__mainTable.getRowData(i);
				v.add(formatLine(call, ' ', true));
			}
		}
		size = __tribTable.getRowCount();
		if (size > 0) {
			v.add("");
			v.add("TRIBUTARY CALLS");
			v.add(
				"START DATE       END DATE         WATER "
				+ "SOURCE             WDID    STRUCTURE "
				+ "NAME                           APPRO "
				+ "DATE ADMIN NO    DEC AMT              "
				+ "DISTRICTS AFFECTED SET COMMENTS       "
				+ "            RELEASE COMMENTS");
			v.add(
				"_____________________________________________"
				+ "___________________________________________"
				+ "___________________________________________"
				+ "_______________________________________");
			// Now do the list...
			for (int i = 0; i < size; i++) {
				call = (HydroBase_Calls)
					__tribTable.getRowData(i);
				v.add(formatLine(call, ' ', true));
			}
		}
	}
	else {	
		int[] selected = null;
		int length;
		boolean isSelected = false;
		char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);	
		if (__guiMode == CALLS) {
		}		
		if (__guiMode == CALLS) {
			v.add("CALLS FOR TIME PERIOD");
			v.add("START" + delim + "END" + delim);
			v.add(__fromJTextField.getText().trim() + delim +
				__toJTextField.getText().trim() + delim);
		}
		size = __mainTable.getRowCount();
		if (size > 1) {
			selected = __mainTable.getSelectedRows();
			length = selected.length;	
			v.add("");
			v.add("MAIN STEM CALLS" + delim);
			v.add("");
			v.add("START DATE" + delim + "END DATE" + delim 
				+ "WATER SOURCE" + delim + "WDID" + delim 
				+ "STRUCTURE NAME" + delim + "APPRO DATE" +delim
				+ "ADMIN NO" + delim + "DCR AMT" + delim
				+ "DISTRICTS AFFECTED" + delim + "SET COMMENTS" 
				+ delim + "RELEASE COMMENTS" + delim);
			for (int i = 0; i < size; i++) {
				isSelected = false;
				if (length == 0) {
					isSelected = true;
				}
				else {
					for (int j = 0; j < length; j++) {
						if (i == selected[j]) {
							isSelected = true;
						}
					}
				}
				if (isSelected) {
					call = (HydroBase_Calls)
						__mainTable.getRowData(i);
					v.add(formatLine(call, delim, 
						false));
				}
			}
		}
		size = __tribTable.getRowCount();
		if (size > 1) {
			selected = __mainTable.getSelectedRows();
			length = selected.length;	
			v.add("");
			v.add("TRIBUTARY STEM CALLS" + delim);
			v.add("");
			v.add("START DATE" + delim + "END DATE" + delim 
				+ "WATER SOURCE" + delim + "WDID" + delim 
				+ "STRUCTURE NAME" + delim + "APPRO DATE" +delim
				+ "ADMIN NO" + delim + "DCR AMT" + delim
				+ "DISTRICTS AFFECTED" + delim + "SET COMMENTS" 
				+ delim + "RELEASE COMMENTS" + delim);
			for (int i = 0; i < size; i++) {
				isSelected = false;
				if (length == 0) {
					isSelected = true;
				}
				else {
					for (int j = 0; j < length; j++) {
						if (i == selected[j]) {
							isSelected = true;
						}
					}
				}
				if (isSelected) {		
					call = (HydroBase_Calls)
						__tribTable.getRowData(i);
					v.add(formatLine(call, delim, 
						false));
				}
			}
		}
	}
	return v;
}

/**
Returns selected call number if selected, DMIUtil.MISSING_INT otherwise
@return selected call number if a call is selected.  Otherwise, a 
DMIUtil.MISSING_INT is returned.
*/
private int getSelectedCall() {
	String selectCall = "Must select a call.";
	String selectOne = "Can select only one call at a time.";
	JWorksheet selectedTable;

	// check for if both list were selected
	int[] mainSelect = __mainTable.getSelectedRows();
        int[] tribSelect = __tribTable.getSelectedRows();
	if (mainSelect.length > 0 && tribSelect.length > 0) {
		new ResponseJDialog(this, 
			selectOne, selectOne, 
			ResponseJDialog.OK).response();
		return DMIUtil.MISSING_INT;
	}

	// determine which list is selected
	int[] index = null;
	if (mainSelect.length > 0) {
		index = mainSelect;
		selectedTable = __mainTable;
	}
	else if (tribSelect.length > 0) {
		index = tribSelect;
		selectedTable = __tribTable;
	}
	else {
		new ResponseJDialog(this, selectCall, selectCall, 
			ResponseJDialog.OK).response();
		return DMIUtil.MISSING_INT;
	}

	// more than one call was selected
	if (index.length > 1) {
		new ResponseJDialog(this, selectOne, selectOne,
			ResponseJDialog.OK).response();
		return DMIUtil.MISSING_INT;
	}

	// get the wdid value and extract the wd
	HydroBase_Calls call = 
		(HydroBase_Calls)selectedTable.getRowData(index[0]);

	int wd = call.getWD();	

	// user does not have permission to modify the selected call
	if (!__dmi.hasPermission("" + wd)) {
		new ResponseJDialog(this, "Invalid Permissions",
			"You do not have permissions"
			+ " to modify this call.", 
			ResponseJDialog.OK).response();
		return DMIUtil.MISSING_INT;
	}

	return call.getCall_num();
}

/**
Returns the fromJTextField parsed to DateTime object.
@return the fromJTextField value parsed into a DateTime object.
*/
public DateTime getFromDate() {
	DateTime from = null;
	try {
		from = DateTime.parse(__fromJTextField.getText(),
               		DateTime.FORMAT_YYYY_MM_DD_HH_mm);
	}
	catch (Exception e) {}

	return from;
}

/**
Returns the toJTextField parsed to DateTime object.
@return the toJTextField value parsed into a DateTime object.
*/
public DateTime getToDate() {
	DateTime to = null;
	try {
		to = DateTime.parse(__toJTextField.getText(),
               		DateTime.FORMAT_YYYY_MM_DD_HH_mm);
	}
	catch (Exception e) {}

	return to;
}

/**
This function displays the desired view for the selected station if
data are present in the MuiltiList object. Sets up the TS, properties,
and legends for the graph.
*/
private void graphClicked() {
	// The sets up a query result set which orders by admin number and date.
	List<HydroBase_Calls> results = submitQuery(true);

	if (results == null) {
		return;
	}

	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Please Wait... Retrieving data");
	new HydroBase_GUI_CallSelection(__dmi, this, results);

	ready();   
}

/**
Initializes date members
*/
private void initialize() {
	__dateProps = new PropList("Calls GUI properties");
	__dateProps.set("DatePrecision", "Minute" );
	__dateProps.set("DateFormat", "Y2K");
}

/**
Listener for mouse clicked events; does nothing.
@param e the MouseEvent that happened.
*/
public void mouseClicked(MouseEvent e) {}

/**
Listener for mouse entered events; does nothing.
@param e the MouseEvent that happened.
*/
public void mouseEntered(MouseEvent e) {}

/**
Listener for mouse exited events; does nothing.
@param e the MouseEvent that happened.
*/
public void mouseExited(MouseEvent e) {}

/**
Listener for mouse pressed events.
@param e the MouseEvent that happened.
*/
public void mousePressed(MouseEvent e) {
// uncomment the two lines below and then clicking in one table will 
// delselect all the selected cells in the other table.
	if (e.getComponent() == __mainTable) {
//		 TODO SAM 2007-02-26 Evaluate if needed
		//__lastClicked = __MAIN_WORKSHEET;
//		__tribTable.deselectAll();
	}
	else if (e.getComponent() == __tribTable) {
//		 TODO SAM 2007-02-26 Evaluate if needed
		//__lastClicked = __TRIB_WORKSHEET;
//		__mainTable.deselectAll();
	}
	else {
		// TODO SAM 2007-02-26 Evaluate if needed
		//__lastClicked = -1;
	}
}

/**
Listener for mouse released events; does nothing.
@param e the MouseEvent that happened.
*/
public void mouseReleased(MouseEvent e) {
	checkAndSetButtons();
}

/**
Reactivates the selected call by setting end date to DMIUtil.MISSING_DATE.
*/
private void reactivateClicked() {
	String	routine = "HydroBase_GUI_CallsQuery.reactivateClicked";

	int callNum = getSelectedCall();
	DateTime archiveDate = null;
	try {
		archiveDate = new DateTime(DateTime.DATE_CURRENT 
			| DateTime.PRECISION_MINUTE);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error creating DateTime "
			+ "object; routine aborted.");
		Message.printWarning(2, routine, e);
		return;
	}
	
	if (callNum == DMIUtil.MISSING_INT) {
		return;
	}

	int response=new ResponseJDialog(this,
		"Reactivate Call",
		"Clear the previous release comment?",
		ResponseJDialog.YES|ResponseJDialog.NO|ResponseJDialog.CANCEL)
		.response();

	boolean clearComment = true;

	if (response == ResponseJDialog.CANCEL) {
		return;
	}
	else if (response == ResponseJDialog.NO) {
		clearComment = false;
	}

	List<String> whereClause = new Vector<String>();
	// determine if the selected call has a release date
	whereClause.add("calls.call_num = " + callNum);
	HydroBase_Calls call = null;
	try {
		call = __dmi.readCallsForCall_num(callNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading "
			+ "HydroBase_Calls object from database.");
		Message.printWarning(2, routine, e);
		return;
	}

	if (DMIUtil.isMissing(call.getDate_time_released())) {
		Message.printWarning(1, routine, 
			"Cannot reactivate a call for which "
			+ "the release date has not been set.");
		return;
	}	

	try {
		if (clearComment) {
			__dmi.updateCallsArchiveDateReleaseCommentsForCall_num(
				archiveDate, callNum);
		}
		else {
			__dmi.updateCallsArchiveDateForCall_num(archiveDate,
				callNum);
		}	
		// submit call query to reflect changes
		submitQuery(false);		
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error writing new "
			+ "archive_date value to database for call.");
		Message.printWarning(2, routine, e);
	}
}

/**
Displays the Release Call GUI
*/
private void releaseCallClicked() {
	String	routine = "HydroBase_GUI_CallsQuery.releaseCallClicked";

	int callNum = getSelectedCall();
	if (!DMIUtil.isMissing(callNum)) {
		// ensure that the release date is null
		HydroBase_Calls call = null;
		try {
			call =__dmi.readCallsForCall_num(callNum);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading "
				+ "HydroBase_Calls object from database.");
			Message.printWarning(2, routine, e);
			return;
		}

		if (!DMIUtil.isMissing(call.getDate_time_released())) {
			Message.printWarning(1, routine,
				"This call has already been released.");
			return;
		}

		JGUIUtil.setWaitCursor(this, true);	        
		new HydroBase_GUI_ReleaseCall(__dmi, this, callNum, call);
		JGUIUtil.setWaitCursor(this, false);
	}
}

/**
Removes a call from by setting the deleted_flag to 'Y'
*/
private void removeClicked() {
	// initialize variables
	String routine = "HBCallChronology.removeClicked()";
	
	int callNum = getSelectedCall();

	DateTime archiveDate = null;
	try {
		archiveDate = new DateTime(DateTime.DATE_CURRENT 
			| DateTime.PRECISION_MINUTE);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error creating DateTime "
			+ "object; routine aborted.");
		Message.printWarning(2, routine, e);
		return;
	}
	
	if (!DMIUtil.isMissing(callNum)) {
		int response=new ResponseJDialog(this,
			"Remove Call",
			"This will mark the call "
			+ "as removed in the database and\nyou will not be able"
			+ " to display it later. Continue?", 
			ResponseJDialog.YES|ResponseJDialog.NO).response();

		if (response == ResponseJDialog.NO) {
			return;
		}
		else {	
			try {
			       	__dmi.updateCallsDeletedArchiveDateForCall_num(
					"Y", archiveDate, callNum);
				// submit call query to reflect changes
				submitQuery(false);		
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error "
					+ "writing new archive_date value to "
					+ "database for call.");
				Message.printWarning(2, routine, e);
			}				
		}
	}
}

/**
Resets the GUI for user interaction.
*/
private void ready() {
	__statusJTextField.setText("Ready");
	JGUIUtil.setWaitCursor(this, false);
}

/**
Refreshes the worksheets.
*/
private void refresh() {
	try {	
		submitQuery(false);
	}
	catch (Exception e) {
		String routine = "HydroBase_GUI_CallsQuery.refresh()";
		Message.printWarning(1, routine,
			"Error refreshing call chronology");
	}
}

/**
Displays the set Call GUI
*/
private void setCallClicked() {
	JGUIUtil.setWaitCursor(this, true);
	if (__setCallGUI == null) {
		__setCallGUI = new HydroBase_GUI_SetCall(this, __parent, __geoview_ui, __dmi);
	}	
	else {	
		__setCallGUI.setVisible(true);
	}
	JGUIUtil.setWaitCursor(this, false);
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);
	
        // objects used throughout the GUI layout
        Insets insetsNLNR = new Insets(0,7,0,7);
        Insets insetsNLBR = new Insets(0,7,7,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        GridBagLayout gbl = new GridBagLayout();

        // Top JPanel
       	JPanel topJPanel = new JPanel();
        topJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
       	getContentPane().add("North", topJPanel);

        topJPanel.add(new JLabel("Time Period:"));

       	__fromJTextField = new JTextField(20);
        __fromJTextField.setEditable(false);
        topJPanel.add(__fromJTextField);
  
       	topJPanel.add(new JLabel("to"));

        __toJTextField = new JTextField(20);
       	__toJTextField.setEditable(false);
       	topJPanel.add(__toJTextField);

	SimpleJButton b = new SimpleJButton(__BUTTON_SET_PERIOD, this);
	b.setToolTipText (
		"Set the period used to query for call data." );
        topJPanel.add(b);

        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(gbl);
        getContentPane().add("Center", centerJPanel);
        
        __mainJLabel = new JLabel(__MAIN_LABEL);
        JGUIUtil.addComponent(centerJPanel, __mainJLabel, 
		1, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	PropList p = 
		new PropList("HydroBase_GUI_CallsQuery.DragAndDropJWorksheet");

/*
	p.add("JWorksheet.CellFont=Courier");
	p.add("JWorksheet.CellStyle=Plain");
	p.add("JWorksheet.CellSize=11");
	p.add("JWorksheet.HeaderFont=Arial");
	p.add("JWorksheet.HeaderStyle=Plain");
	p.add("JWorksheet.HeaderSize=11");
	p.add("JWorksheet.HeaderBackground=LightGray");
	p.add("JWorksheet.RowColumnPresent=true");
	p.add("JWorksheet.RowColumnBackground=LightGray");
*/
	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.AllowCopy=true");
	p.add("JWorksheet.ShowPopupMenu=true");

	JScrollWorksheet mainJSW = new JScrollWorksheet(0, 0, p, true);
	__mainTable = (DragAndDropJWorksheet)mainJSW.getJWorksheet();
	__mainTable.setHourglassJFrame(this);
	__mainTable.addMouseListener(this);

        JGUIUtil.addComponent(centerJPanel, mainJSW,
		1, 2, 1, 1, 1, 1, insetsNLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);
 
        __tribJLabel = new JLabel(__TRIB_LABEL);
        JGUIUtil.addComponent(centerJPanel, __tribJLabel, 
		1, 3, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
 
 	JScrollWorksheet tribJSW = new JScrollWorksheet(0, 0, p, true);
	__tribTable = (DragAndDropJWorksheet)tribJSW.getJWorksheet();
	__tribTable.setHourglassJFrame(this);
	__tribTable.addMouseListener(this);
 
        JGUIUtil.addComponent(centerJPanel, tribJSW,
		1, 4, 1, 1, 1, 1, insetsNLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);
    
        // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);
	
	if (__guiMode == EDIT_CALLS) {
	        // Bottom JPanel: North
        	JPanel bottomNJPanel = new JPanel();
	        bottomNJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        	bottomJPanel.add("North", bottomNJPanel);
	
	        bottomNJPanel.add(__setJButton = 
			new SimpleJButton(__BUTTON_SET_CALL, this));
        	bottomNJPanel.add(__releaseJButton = 
			new SimpleJButton(__BUTTON_RELEASE_CALL, this));
		bottomNJPanel.add(__copyCallJButton =
			new SimpleJButton(__BUTTON_COPY_CALL, this));
		bottomNJPanel.add(__removeJButton = 
			new SimpleJButton(__BUTTON_REMOVE_CALL, this));
		bottomNJPanel.add(__reactivateJButton =
			new SimpleJButton(__BUTTON_REACTIVATE, this));
		bottomNJPanel.add(__editCommentsJButton =
			new SimpleJButton(__BUTTON_EDIT_COMMENTS, this));

		if (!__dmi.canWriteToDatabase()) {
			__releaseJButton.setEnabled(false);
			__removeJButton.setEnabled(false);
			__reactivateJButton.setEnabled(false);
			__editCommentsJButton.setEnabled(false);
			__copyCallJButton.setEnabled(false);
		}
	}
	else {
		__releaseJButton = new SimpleJButton(__BUTTON_RELEASE_CALL, 
			this);
		__copyCallJButton = new SimpleJButton(__BUTTON_COPY_CALL,
			this);			
		__setJButton = new SimpleJButton(__BUTTON_SET_CALL, 
			this);
		__removeJButton = new SimpleJButton(__BUTTON_REMOVE_CALL, 
			this);
		__reactivateJButton = new SimpleJButton(__BUTTON_REACTIVATE, 
			this);
		__editCommentsJButton = new SimpleJButton(
			__BUTTON_EDIT_COMMENTS, this);
	}
	__releaseJButton.setToolTipText("Release a call.");
	__setJButton.setToolTipText("Set a new call.");
	__reactivateJButton.setToolTipText("Reactivate a call.");
	__removeJButton.setToolTipText("Remove a call from the "
		+ "database.");
	__editCommentsJButton.setToolTipText("Change the districts affected "
		+ "and comments for a call.");
	__copyCallJButton.setToolTipText("Copy the call data to a new call "
		+ "with different dates.");

        // Bottom JPanel: Center
        JPanel bottomCJPanel = new JPanel();
        bottomCJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("Center", bottomCJPanel);

        bottomCJPanel.add(__graphJButton = 
		new SimpleJButton(__BUTTON_GRAPH, this));
	__graphJButton.setToolTipText (
		"<HTML>Graph selected calls to show when calls have been on." +
		"<BR>This is useful when a long period is specified.</HTML>");
        bottomCJPanel.add(__printJButton = 
		new SimpleJButton(__BUTTON_PRINT, this));
	__printJButton.setToolTipText (
		"Print call data in delimited or summary format." );
        bottomCJPanel.add(__exportJButton = 
		new SimpleJButton(__BUTTON_EXPORT, this));
	__exportJButton.setToolTipText (
		"Export call data in delimited or summary format." );
	b = new SimpleJButton(__BUTTON_CLOSE, this);
        bottomCJPanel.add(b);
	b.setToolTipText ( "Close this window." );
	b = new SimpleJButton(__BUTTON_REFRESH, this);
        bottomCJPanel.add( b );
	b.setToolTipText (
	"Refresh the contents of this window to reflect recent changes." );

        // Bottom JPanel: South
        JPanel bottomSJPanel = new JPanel();
        bottomSJPanel.setLayout(gbl);
        bottomJPanel.add("South", bottomSJPanel);

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // frame settings

	if (__guiMode == CALLS) {
		if (	(JGUIUtil.getAppNameForWindows() == null) ||
			JGUIUtil.getAppNameForWindows().equals("") ) {
			setTitle ( "Call Chronology Data - Query" );
		}
		else {	setTitle( JGUIUtil.getAppNameForWindows() +
			" - Call Chronology Data - Query" );
		}
	}
	else {	if (	(JGUIUtil.getAppNameForWindows() == null) ||
			JGUIUtil.getAppNameForWindows().equals("") ) {
			setTitle ( "Administration - Edit Calls" );
		}
		else {	setTitle( JGUIUtil.getAppNameForWindows() +
			" - Administration - Edit Calls" );
		}
	}
	initialize();

        pack();
        setSize(700,500);
        JGUIUtil.center(this);
        setVisible(true);
}

/**
This function instantiates a DateTimeBuilderJDialog object.
*/
private void setIntervalClicked() {
	String 	routine = "HydroBase_GUI_CallsQuery.setIntervalClicked()";
	DateTime from = null;
	DateTime to = null;

	try {	
		from = DateTime.parse(__fromJTextField.getText(),
                            DateTime.FORMAT_YYYY_MM_DD_HH_mm );

		to = DateTime.parse(__toJTextField.getText(),
                            DateTime.FORMAT_YYYY_MM_DD_HH_mm);
	}
	catch (Exception e) {
		Message.printWarning(2, routine,
			"Exception parsing dates.");
		from = new DateTime(DateTime.PRECISION_MINUTE);
		to = new DateTime(DateTime.PRECISION_MINUTE);
	}
	new DateTimeBuilderJDialog(this, __fromJTextField, __toJTextField, 
		from, to, __dateProps);
	submitQuery(false);
}

/**
This function show/hides the HydroBase_GUI_CallsQuery Frame.
@param state true if showing the Frame, false otherwise.
*/
public void setVisible(boolean state) {
	super.setVisible(state);
        if (state) {
                // clear list objects    
                __mainTable.clear();
                __tribTable.clear();

                __statusJTextField.setText("");
                __mainJLabel.setText(__MAIN_LABEL);
                __tribJLabel.setText(__TRIB_LABEL);

                // load user preference data
                String[] interval = __dmi.lookupInterval();
       	        __fromJTextField.setText(interval[0]);
               	__toJTextField.setText(interval[1]);
		if (submitQuery(false) == null) {
			super.setVisible(false);
		}
        }     
}

/**
This function sets the to textfield given a DateTime.
@param to the date to set into the to textfield.
*/
public void setToDate(DateTime to) {
	String s = to.toString(DateTime.FORMAT_YYYY_MM_DD_HH_mm );
	__toJTextField.setText(s); 
}

/**
This function performs a Call Chronology query. Returns the result set vector.
*/
public List<HydroBase_Calls> submitQuery(boolean orderByAdmin) {
        String routine = "HydroBase_GUI_CallsQuery.submitQuery()";

        List<String> orderBy = new Vector<String>();
	// if orderByNet then add an additional element to the orderBy clause
	// this is required for pulling unique rights
	if (orderByAdmin == true) {
		orderBy.add("adminno" + " desc");
	}

        // perform query
	JGUIUtil.setWaitCursor(this, true);
        String tempString = "Please Wait... Retrieving Data";
        __statusJTextField.setText(tempString);
              
        // add division where clause
        List<String> divVector = HydroBase_GUI_Util.getDivisions(__dmi);
        int size = divVector.size();
	if (size == 1 && divVector.get(0).toString().equals("NONE")) {
		Message.printStatus(2, routine, 
			"No division preference found in the "
			+ "user preference table.");
		ready();
	 	return null;
	}

	List<String> divOrClause_Vector = new Vector<String>(size);
        for (int curElement = 0; curElement < size; curElement++) {
                divOrClause_Vector.add("calls.div = "+ divVector.get(curElement));
        }
        // build where clause for the divisions
        List<String> whereClause = new Vector<String>();
        whereClause.add(DMIUtil.getOrClause(divOrClause_Vector));

	// start and end dates
	DateTime startDate = null;
	DateTime endDate = null;
	
	try {	
		startDate = DateTime.parse(__fromJTextField.getText(), 
			DateTime.FORMAT_YYYY_MM_DD_HH_mm);
		endDate = DateTime.parse(__toJTextField.getText(), 
				DateTime.FORMAT_YYYY_MM_DD_HH_mm);
		startDate.setPrecision(DateTime.PRECISION_MINUTE);
		endDate.setPrecision(DateTime.PRECISION_MINUTE);

	} catch (Exception e) {
		Message.printWarning(1, routine, "Error parsing date string.");
		Message.printWarning(2, routine, e);
	}

	StopWatch sw = new StopWatch();
	sw.start();
	
        // results Vector contains the reservoir query results
	List<HydroBase_Calls> results = null;
	try {
	        results = __dmi.readCallsListForSetReleaseDates(startDate, 
			endDate, __guiMode);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading calls list "
			+ "from database.");
		Message.printWarning(2, routine, e);
		ready();
		return null;
	}

	int numRecords = 0;
	if (results.size() > 0) {
		numRecords = results.size();
	}
	sw.stop();

	String divisions = "for division";
	if (divVector.size() > 1) {
		divisions += "s";
	}

	for (int i = 0; i < divVector.size(); i++) {
		if (i > 0) {
			divisions += ",";
		}
		divisions += " " + divVector.get(i);
	}
	
	// clear the lists unless we are a query for graphing.
	if (!orderByAdmin) {
       		__mainJLabel.setText(__MAIN_LABEL);
        	__tribJLabel.setText(__TRIB_LABEL);
        	__mainTable.clear();
        	__tribTable.clear();

		if (numRecords > 0) {
        		displayResults(results, divisions, divVector);
        	}
		else {        	
			__mainJLabel.setText(__MAIN_LABEL + " (0 records "
				+ "returned " + divisions + "):");
	                __tribJLabel.setText(__TRIB_LABEL + " (0 records "
				+ "returned " + divisions + "):");
		}
	}

	if (__filteredRecordCount > 0) {
		__printJButton.setEnabled(true);
		__exportJButton.setEnabled(true);
	}
	else {
		__printJButton.setEnabled(false);
		__exportJButton.setEnabled(false);
	}

	// form records syntax 
	String records = "record";
        if (__filteredRecordCount != 1) {
		records += "s";
        }
         
        tempString = "Call Chronology query completed. " 
		+ __filteredRecordCount + " " + records + " returned in " 
		+ sw.getSeconds() + " seconds.";
        __statusJTextField.setText(tempString);
	JGUIUtil.setWaitCursor(this, false);

        return results;
}

/**
Handles window activated events.
@param evt the WindowEvent that happened.
*/
public void windowActivated(WindowEvent evt) {}

/**
Handles window closed events.
@param evt the WindowEvent that happened.
*/
public void windowClosed(WindowEvent evt) {}

/**
Handles window closing events; calls closeClicked;
@param evt the WindowEvent that happened.
*/
public void windowClosing(WindowEvent evt) {
	closeClicked();
}

/**
Handles window deactivated events.
@param evt the WindowEvent that happened.
*/
public void windowDeactivated(WindowEvent evt) {}

/**
Handles window deiconified events.
@param evt the WindowEvent that happened.
*/
public void windowDeiconified(WindowEvent evt) {}

/**
Handles window iconified events.
@param evt the WindowEvent that happened.
*/
public void windowIconified(WindowEvent evt) {}

/**
Handles window opened events.
@param evt the WindowEvent that happened.
*/
public void windowOpened(WindowEvent evt) {}

}
