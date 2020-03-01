// HydroBase_GUI_WISDiversionCoding - Detail for diversion SFUT

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
// HydroBase_GUI_WISDiversionCoding - Detail for diversion SFUT
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2001-07-02	Steven A. Malers, RTi	Initial version.  Copy and modify
//					HBFlowDataGUI.
// 2001-07-15	SAM, RTi		Change HBWIS_SFUTDetailGUI to
//					HBWISDiversionCodingGUI.  Pass in
//					existing records at construction.
// 2002-02-20	SAM, RTi		Change TSViewGUI to TSViewJFrame.
// 2002-03-03	SAM, RTi		Hopefully final push to finish RGDSS
//					deliverables.  Focus on feature cleanup
//					while documenting.
// 2002-03-31	SAM, RTi		Support W code from automatic edits.
// 2002-06-19	SAM, RTi		Add more comments at the top of the
//					dialog to help users.
//-----------------------------------------------------------------------------
// 2003-11-17	J. Thomas Sapienza, RTi	Initial Swing version.
// 2004-01-21	JTS, RTi		Began using the JScrollWorksheet in
//					order to use worksheet row headers.
// 2004-05-12	JTS, RTi		Corrected error in the positions of
//					the combo boxes resulting from the move
//					to the new Worksheet version.
// 2004-06-06	JTS, RTi		Uncommented and updated the code for
//					viewing the history.
// 2004-06-09	JTS, RTi		Added the flag to set whether the data
//					can be edited or not.
// 2005-02-15	JTS, RTi		Converted queries to use stored
//					procedures.
// 2005-03-09	JTS, RTi		HydroBase_SheetName 	
//					  -> HydroBase_WISSheetName.
// 2005-05-25	JTS, RTi		Converted queries that pass in a 
//					String date to pass in DateTimes 
//					instead.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.JWorksheet_CellAttributes;
import RTi.Util.GUI.JWorksheet_TableModelListener;
import RTi.Util.GUI.ReportJFrame;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;

/**
This class is a gui for displaying diversion coding information.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_WISDiversionCoding 
extends JFrame
implements ActionListener, JWorksheet_TableModelListener, WindowListener {

/**
Class name.
*/
private final static String __CLASS = "HydroBase_GUI_WISDiversionCoding";

/**
Strings that will fill the combo boxes.
REVISIT - probably doesn't need to be protected.
*/
protected final String 
	_OBS_U = 		"U - User supplied",
	_OBS_AST = 		"* - Measured/observed",
	_OBS_CARRY_FORWARD = 	"C - Carry forward",
	_OBS_WIS = 		"W - Assigned by WIS",
	_OBSC_U = 		"U",
	_OBSC_AST = 		"*",
	_OBSC_CARRY_FORWARD = 	"C",
	_OBSC_WIS = 		"W",
	_SOURCE_1 = 		"1 - Natural stream flow",
	_SOURCE_2 =		"2 - Reservoir storage",
	_SOURCE_3 =		"3 - Ground water (wells)",
	_SOURCE_4 =		"4 - Transbasin",
	_SOURCE_5 =		"5 - Non-stream sources (springs/seepage)",
	_SOURCE_6 =		"6 - Combined (non-additive)",
	_SOURCE_7 =		"7 - Transdistrict (same basin)",
	_SOURCE_8 =		"8 - Re-used",
	_SOURCE_9 =		"9 - Multiple",
	_SOURCE_R =		"R - Remeasured and rediverted",
	_TYPE_0 =		"0 - Administrative record only",
	_TYPE_1 =		"1 - Exchange",
	_TYPE_2 =		"2 - Trade",
	_TYPE_3 = 		"3 - Carrier",
	_TYPE_4 = 		"4 - Alternative point of diversion",
	_TYPE_5 = 		"5 - Re-used",
	_TYPE_6 = 		"6 - Replacement to river",
	_TYPE_7 = 		"7 - Released to river",
	_TYPE_8 = 		"8 - Released to system",
	_TYPE_A = 		"A - Augmented",
	_TYPE_G = 		"G - Geothermal",
	_TYPE_S = 		"S - Reservoir substitution",
	_USE_0 = 		"0 - Storage",
	_USE_1 =	 	"1 - Irrigation",
	_USE_2 = 		"2 - Municipal",
	_USE_3 =		"3 - Commercial",
	_USE_4 =		"4 - Industrial",
	_USE_5 =		"5 - Recreation",
	_USE_6 =		"6 - Fishery",
	_USE_7 =		"7 - Fire",
	_USE_8 =		"8 - Domestic",
	_USE_9 =		"9 - Stock",
	_USE_A =	 	"A - Augmentation",
	_USE_B =	 	"B - Export from basin",
	_USE_E = 		"E - Evaporation",
	_USE_G =		"G - Geothermal",
	_USE_H =		"H - In house use",
	_USE_K =		"K - Snow making",
	_USE_M = 		"M - Minimum flow",
	_USE_P = 		"P - Power generation",
	_USE_Q = 		"Q - Other",
	_USE_R = 		"R - Recharge",
	_USE_S =		"S - Export from state",
	_USE_T =		"T - Transmountain export",
	_USE_W =		"W - Wildlife";

/**
Button labels.
*/
private final String
	__BUTTON_ADD_ROW = 		"Add Row",
	__BUTTON_DEL_ROW = 		"Delete Row",
	__BUTTON_CANCEL = 		"Cancel",
	__BUTTON_GRAPH = 		"Graph",
	__BUTTON_HELP = 		"Help",
	__BUTTON_OK = 			"OK",
	__BUTTON_VIEW_HISTORY = 	"View History";

/**
Whether the diversion coding data can be edited or not.
*/
private boolean __editable = false;

/**
The date that the diversion coding is for.
*/
private DateTime __detailDateTime = null;

/**
The dmi used to communicate with the database.
*/
private HydroBaseDMI __dmi;

/**
The wis gui that opened this gui.
*/
private HydroBase_GUI_WIS __wisGUI = null;

/**
The table model for the table.
*/
private HydroBase_TableModel_WISDiversionCoding __tableModel = null;

/**
The day of the month for which diversion coding is being done.
*/
private int __day = 0;

/**
Used to identify the structure the diversion coding is done for.
*/
private int 
	__id,
	__wd;

/**
The location within the wis of the cell for which this diversion coding is
made.
*/
private int 
	__wisCol = 0,
	__wisRow = 0;

/**
Status bar.
*/
private JTextField __statusJTextField = null;

/**
The worksheet containing the diversion coding.
*/
private JWorksheet __worksheet = null;

/**
The cell attributes to be used for grey cells.
*/
private JWorksheet_CellAttributes __ca;

/**
The contents of the cell in the wis.
*/
private String __wisCellContents = "";

/**
Previous diversion coding records for the cell being edited.
*/
private List<HydroBase_WISDailyWC> __previousRecords;

/**
Constructor.
@param dmi HydrobaseDMI object
@param wisGUI HydroBase_GUI_WIS object.
@param detailDateTime The date that the detail is for.
@param wisRow WIS row in WIS for edit.
@param wisCol WIS row in WIS for edit.
@param previousRecords Previous diversion coding records for the cell being
edited.
@param editable whether the data can be edited or not.
*/
public HydroBase_GUI_WISDiversionCoding(HydroBaseDMI dmi, 
HydroBase_GUI_WIS wisGUI, HydroBase_WISSheetName wis, DateTime detailDateTime, 
int wisRow, int wisCol, List<HydroBase_WISDailyWC> previousRecords, boolean editable) {
	__dmi = dmi;
	__wisRow = wisRow;
	__wisCol = wisCol;
	__wisGUI = wisGUI;
	__detailDateTime = detailDateTime;
	__previousRecords = previousRecords;
	__editable = editable;
	if (__previousRecords == null) {
		__previousRecords = new Vector<HydroBase_WISDailyWC>();
	}
	__wisCellContents = __wisGUI.getCellContents(__wisRow, __wisCol - 1);
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();
}

/**
Handle ActionListener events.
@param event ActionEvent object.
*/
public void actionPerformed(ActionEvent event) {
	String command = event.getActionCommand();

	if (command.equals(__BUTTON_ADD_ROW)) {
		addRowClicked(false);
	}
	else if (command.equals(__BUTTON_DEL_ROW)) {
		int row = __worksheet.getSelectedRow();
		if (row == (__worksheet.getRowCount() - 1)) {
			return;
		}
		if (row > -1) {
			__worksheet.deleteRow(row);
		}
		__worksheet.setDirty(true);
	}
	else if (command.equals(__BUTTON_CANCEL)) {
		cancelClicked();
	}
	else if (command.equals(__BUTTON_GRAPH)) {
		graphClicked();
	}
	else if (command.equals(__BUTTON_HELP)) { 
	}
	else if (command.equals(__BUTTON_OK)) {
		if (!__worksheet.stopEditing()) {
			return;
		}
		okClicked();
	}
	else if (command.equals(__BUTTON_VIEW_HISTORY)) {
		viewHistoryClicked();
	}
}

/**
Adds a row to the diversion coding.
@param totalRow if true, the row being added is the total row.  Otherwise, 
it's a data row.
*/
private void addRowClicked(boolean totalRow) {
	HydroBase_WISDailyWC wc = new HydroBase_WISDailyWC();
	String wis_column = "";		
	if (__wisCol == HydroBase_GUI_WIS.PRIORITY_DIV_COL) {
		wis_column = "PD";
	}
	else if (__wisCol == HydroBase_GUI_WIS.DELIVERY_DIV_COL) {
		wis_column = "DD";
	}
	else if (__wisCol == HydroBase_GUI_WIS.RELEASES_COL) {
		wis_column = "RR";
	}
	wc.setWis_column(wis_column);
	wc.setWis_num(__wisGUI.getWISNumber());
	wc.setWD(__wd);
	wc.setID(__id);
	wc.setCal_year(__detailDateTime.getYear());
	wc.setCal_mon(__detailDateTime.getMonth());		
	wc.setStructure_num(__id);
	if (!totalRow) {
		wc.setObservationForDay(__day, "*");
	}
	int rows = __worksheet.getRowCount();
	__worksheet.insertRowAt(wc, (rows - 1));
	__worksheet.setCellAttributes((rows - 1), 0, __ca);
	if (!totalRow) {
		__worksheet.setDirty(true);
	}
}

/**
Cancels the GUI, closing without saving any changes.
*/
private void cancelClicked() {
	setVisible(false);
	dispose();
}

/**
Clean up before garbage collection.
*/
protected void finalize()
throws Throwable {
	__detailDateTime = null;
	__dmi = null;
	__wisGUI = null;
	__tableModel = null;
	__statusJTextField = null;
	__worksheet = null;
	__wisCellContents = null;
	__previousRecords = null;
	__ca = null;
	super.finalize();
}

/**
Return the values in the editor as a Vector of HydroBase_WISDailyWC.
All values are returned, whether modified or not.  Use isModified() to determine
whether data have been modified for the cell.
@return list of HydroBase_WISDailyWC or null if no data.
*/
public List<HydroBase_WISDailyWC> getDiversionCoding() {
	@SuppressWarnings("unchecked")
	List<HydroBase_WISDailyWC> v = (List<HydroBase_WISDailyWC>)__worksheet.getAllData();
	List<HydroBase_WISDailyWC> v2 = new Vector<HydroBase_WISDailyWC>();
	for (int i = 0; i < (v.size() - 1); i++) {
		v2.add(v.get(i));
	}
	return v2;
}

/**
Return the total amount from the grid.
*/
public double getTotalAmount() {
	return __tableModel.getTotalAmount();
}

/**
Returns the column of the cell for which this diversion coding gui is open.
@return the column of the cell for which this diversion coding gui is open.
*/
public int getWis_col() {
	return __wisCol;
}

/**
Returns the row of the cell for which this diversion coding gui is open.
@return the row of the cell for which this diversion coding gui is open.
*/
public int getWis_row() {
	return __wisRow;
}

/**
Does nothing.
*/
private void graphClicked() {}

/**
Indicate whether the data have been modified.
@return true if the data have been changed by the user.
*/
public boolean isModified() {
	return __worksheet.isDirty();
}

/**
Close the GUI, saving data if necessary.
*/
private void okClicked() {
	if (!__worksheet.stopEditing()) {
		return;
	}
	// Save the data...
	__worksheet.refresh();
	// First check to see that data are error-free...
	// OK to save to the main GUI and close the GUI...
	__wisGUI.setDiversionCoding(this);
	cancelClicked();
}


/** 
Sets up the GUI.
*/
private void setupGUI() {
	String routine = __CLASS + ".setupGUI";
	addWindowListener(this);
	// Listen for closing of main WIS window...
	__wisGUI.addWindowListener(this);

        // objects used throughout the GUI layout
        Insets insetsTLBR = new Insets(2, 2, 2, 2);
        Insets insetsNLNR = new Insets(0, 2, 0, 2);
	Insets insetsNLBR = new Insets(0, 2, 2, 2);
        GridBagLayout gbl = new GridBagLayout();

        // Center JPanel
        JPanel center = new JPanel();
        center.setLayout(gbl);
        getContentPane().add("Center", center);

	// Add the total from the main sheet so we can compare...

	int y = 0;
	if (__wisCellContents.trim().equals("")) {
		JGUIUtil.addComponent(center, new JLabel(
			"Diversion coding total from WIS cell:  NONE"),
			0, y, 3, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	}
	else {	
		JGUIUtil.addComponent(center, new JLabel(
			"Diversion coding total from WIS cell:  " 
			+ __wisCellContents),
			0, y, 3, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	}

        JGUIUtil.addComponent(center, new JLabel(
		"Source:  enter code or right-click on selected cell to "
		+ "see choices."),
		0, ++y, 3, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(center, new JLabel(
		"From:  enter structure identifier (no WD), if appropriate,"
		+ " for source."),
		0, ++y, 3, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(center, new JLabel(
		"Use:  enter code or right-click on selected cell to see "
		+ "choices."),
		0, ++y, 3, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(center, new JLabel(
		"Type:  enter code or right-click on selected cell to see "
		+ "choices."),
		0, ++y, 3, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(center, new JLabel(
		"Obs:  enter code or right-click on selected cell to see "
		+ "choices.  W indicates default assigned by WIS.  C "
		+ "indicates that values are carried forward."),
		0, ++y, 3, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(center, new JLabel(
		"The total amount from this dialog will override the WIS value"
		+ " if OK is pressed."),
		0, ++y, 3, 1, 1, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(center, new JLabel(
		"See the DWR Water Commissioner Manual for diversion coding "
		+ "information."),
		0, ++y, 3, 1, 1, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// Declare now so it can be used in the grid...
        __statusJTextField = new JTextField();

	PropList p =new PropList("HydroBase_GUI_WISDiversionCoding.JWorksheet");
	/*
	p.add("JWorksheet.CellFont=Courier");
	p.add("JWorksheet.CellStyle=Plain");
	p.add("JWorksheet.CellSize=11");
	p.add("JWorksheet.HeaderFont=Arial");
	p.add("JWorksheet.HeaderStyle=Plain");
	p.add("JWorksheet.HeaderSize=11");
	p.add("JWorksheet.HeaderBackground=LightGray");
	p.add("JWorksheet.RowColumnPresent=false");
	*/
	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.ShowPopupMenu=true");
	p.add("JWorksheet.SelectionMode=SingleRowSelection");

	int widths[] = null;
	JScrollWorksheet jsw = null;
	try {
		__day = __detailDateTime.getDay();
		__tableModel = new HydroBase_TableModel_WISDiversionCoding(
			__previousRecords, __day);
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(
			__tableModel);
		jsw = new JScrollWorksheet(cr, __tableModel, p);
		__worksheet = jsw.getJWorksheet();
	
		__tableModel.setWorksheet(__worksheet);
		__tableModel.setEditable(__editable);
		__tableModel.addTableModelListener(this);

		setupWorksheetColumns();

		widths = __tableModel.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(2, routine, e);
		jsw = new JScrollWorksheet(0, 0, p);
		__worksheet = jsw.getJWorksheet();
	}
	__worksheet.setPreferredScrollableViewportSize(null);
	__worksheet.setHourglassJFrame(this);

	__ca = new JWorksheet_CellAttributes();
	__ca.backgroundColor = Color.gray;

	int lastRow = __worksheet.getRowCount() - 1;
	for (int i = 0; i < 7; i++) {
		__worksheet.setCellAttributes(lastRow, i, __ca);
	}

	int nrows = lastRow + 1;
	for (int i = 0; i < nrows; i++) {
		__worksheet.setCellAttributes(i, 0, __ca);
	}

        JGUIUtil.addComponent(center, jsw,
		0, ++y, 3, 1, 1, 1, insetsTLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);
    
        // Bottom JPanel
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        getContentPane().add("South", bottom);

        // Bottom South JPanel
        JPanel bottomSouth = new JPanel();
        bottomSouth.setLayout(new BorderLayout());
        bottom.add("South", bottomSouth);

        // Bottom South North JPanel
        JPanel bottomSouthNorth = new JPanel();
        bottomSouthNorth.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomSouth.add("North", bottomSouthNorth);

	SimpleJButton addRowButton = new SimpleJButton(__BUTTON_ADD_ROW, this);
	addRowButton.setToolTipText("Add row.");
	if (__editable) {
	        bottomSouthNorth.add(addRowButton);
	}

	SimpleJButton delRowButton = new SimpleJButton(__BUTTON_DEL_ROW, this);
	delRowButton.setToolTipText("Delete selected row.");
	if (__editable) {
	        bottomSouthNorth.add(delRowButton);
	}

	SimpleJButton viewHistoryButton = new SimpleJButton(
		__BUTTON_VIEW_HISTORY, this);
	viewHistoryButton.setToolTipText("View a history of the diversion "
		+ "coding for the current and previous irrigation year.");
        bottomSouthNorth.add(viewHistoryButton);

	// SAMX - enable later...too much of a pain right now to go from
	// diversion records to time series with SFUT, etc.
	//SimpleJButton graphButton = new SimpleJButton(__BUTTON_GRAPH, this);
	//graphButton.setEnabled(false);
        //bottomSouthNorth.add(graphButton);

	SimpleJButton okButton = new SimpleJButton(__BUTTON_OK, this);
	okButton.setToolTipText("Accept values and return.");
        bottomSouthNorth.add(okButton);

	SimpleJButton cancelButton = new SimpleJButton(__BUTTON_CANCEL, this);
	cancelButton.setToolTipText("Discard changes and return.");
        bottomSouthNorth.add(cancelButton);

//	SimpleJButton helpButton = new SimpleJButton(__BUTTON_HELP, this);
//        bottomSouthNorth.add(helpButton);
//	helpButton.setEnabled(false);

        // Bottom South South JPanel
        JPanel bottomSouthSouth = new JPanel();
        bottomSouthSouth.setLayout(gbl);
        bottomSouth.add("South", bottomSouthSouth);

        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSouthSouth, __statusJTextField, 
		0, 1, 10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	pack();

	String rowLabel = __wisGUI.getRow_label(__wisRow);
	String colLabel = __wisGUI.getColumnLabel(__wisCol - 1);
	colLabel = StringUtil.replaceString(colLabel, "\n", " ");
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null) {
		app = "";
	}
	else {
		app += " - ";	
	}
        setTitle(app + "Diversion Coding for " + rowLabel + " (" +
		__detailDateTime.toString(DateTime.FORMAT_YYYY_MM_DD)
		+ " - " + colLabel+ ")");
        setSize(900, 350);
	JGUIUtil.center(this);
        setVisible(true);

	if (widths != null) {
		__worksheet.setColumnWidths(widths);
	}

	__worksheet.setDirty(false);

	try {
		int[] wdid = HydroBase_WaterDistrict.parseWDID(
			__wisGUI.getIdentifier(__wisRow));
		__wd = wdid[0];
		__id = wdid[1];		
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Could not parse WDID from '"
			+ __wisGUI.getIdentifier(__wisRow) + "'.  Errors will "
			+ "occur when trying to save.");
		Message.printWarning(2, routine, e);
		__id = -1;
	}
}

/**
Sets up the combo boxes for the worksheet columns.
*/
private void setupWorksheetColumns() {
	List<String> source = new Vector<String>();
	source.add(_SOURCE_1);
	source.add(_SOURCE_2);
	source.add(_SOURCE_3);
	source.add(_SOURCE_4);
	source.add(_SOURCE_5);
	source.add(_SOURCE_6);
	source.add(_SOURCE_7);
	source.add(_SOURCE_8);
	source.add(_SOURCE_9);
	source.add(_SOURCE_R);
	__worksheet.setColumnJComboBoxValues(1, source);

	List<String> use = new Vector<String>();
	use.add(_USE_0);
	use.add(_USE_1);
	use.add(_USE_2);
	use.add(_USE_3);
	use.add(_USE_4);
	use.add(_USE_5);
	use.add(_USE_6);
	use.add(_USE_7);
	use.add(_USE_8);
	use.add(_USE_9);
	use.add(_USE_A);
	use.add(_USE_B);
	use.add(_USE_E);
	use.add(_USE_G);
	use.add(_USE_H);
	use.add(_USE_K);
	use.add(_USE_M);
	use.add(_USE_P);
	use.add(_USE_Q);
	use.add(_USE_R);
	use.add(_USE_S);
	use.add(_USE_T);
	use.add(_USE_W);
	__worksheet.setColumnJComboBoxValues(3, use);

	List<String> type = new Vector<String>();
	type.add(_TYPE_0);
	type.add(_TYPE_1);
	type.add(_TYPE_2);
	type.add(_TYPE_3);
	type.add(_TYPE_4);
	type.add(_TYPE_5);
	type.add(_TYPE_6);
	type.add(_TYPE_7);
	type.add(_TYPE_8);
	type.add(_TYPE_A);
	type.add(_TYPE_G);
	type.add(_TYPE_S);
	__worksheet.setColumnJComboBoxValues(4, type);
		
	List<String> obs = new Vector<String>();
	obs.add(_OBS_U);
	obs.add(_OBS_AST);
	obs.add(_OBS_CARRY_FORWARD);
	obs.add(_OBS_WIS);
	__worksheet.setColumnJComboBoxValues(5, obs);	
}

/**
Shows or hides the GUI.
@param state true if showing the GUI, false if hiding it.
*/
public synchronized void setVisible(boolean state) {
	super.setVisible(state);
}

/**
Called when a value changes in the worksheet.  
From JWorksheet_TableModelListener.
@param row the row in which the value changed.
@param col the column in which the value changed.
@param value the new value.
*/
public void tableModelValueChanged(int row, int col, Object value) {
	__worksheet.setDirty(true);
}

/**
View a history of the diversion coding for the current and previous irrigation
year.
*/
private void viewHistoryClicked() {
	String routine = "HydroBase_GUI_WISDiversion.viewHistoryClicked";
	// Get a list of records for the last couple of years...
	String rowLabel = __wisGUI.getRow_label(__wisRow);
	String colLabel = __wisGUI.getColumnLabel(__wisCol);
	DateTime start_DateTime = new DateTime(__detailDateTime);
	start_DateTime.addYear(-1);
	String wis_column = "";
	
	if (__wisCol == HydroBase_GUI_WIS.RELEASES_COL) {
		wis_column = "RR";
	}
	else if (__wisCol == HydroBase_GUI_WIS.PRIORITY_DIV_COL) {
		wis_column = "PD";
	}
	else if (__wisCol == HydroBase_GUI_WIS.DELIVERY_DIV_COL) {
		wis_column = "DD";
	}
	
	String wdid_string = __wisGUI.getIdentifier(__wisRow);
	int wd = -1;
	int id = -1;
	
	if (wdid_string.equals("")) {
		wd = DMIUtil.MISSING_INT;
		id = DMIUtil.MISSING_INT;
	}
	else {	
		// Parse out the WD and ID.
		try {
			int[] parts = 
				HydroBase_WaterDistrict.parseWDID(wdid_string);
			if (parts != null) {
				wd = parts[0];
				id = parts[1];
			}
		}
		catch (Exception e) {
			Message.printWarning(2, routine, 
				"Error parsing WDID: '" + wdid_string + "'");
			Message.printWarning(2, routine, e);
		}
	}

	List<HydroBase_WISDailyWC> records = null;
	try {
		records = __dmi.readWISDailyWCList(
			__wisGUI.getWISNumber(), wis_column, wd, id,
			start_DateTime, null, DMIUtil.MISSING_INT, null, null, 
			2);
	}
	catch (Exception e) {
		Message.printWarning(2, routine, 
			"Error reading from database.");
		Message.printWarning(2, routine, e);
	}

	// Now format the output...

	List<String> report_Vector = new Vector<String>(100);
	report_Vector.add("");
	report_Vector.add("WIS Diversion Coding History");
	report_Vector.add("");
	report_Vector.add("Water District:  " + wd);
	report_Vector.add("Structure:  " + id + " (" 
		+ __wisGUI.getRow_label(__wisRow) + ")");

	report_Vector.add("");
	report_Vector.add("Diversion coding currently being edited for "
		+ __detailDateTime.toString(DateTime.FORMAT_YYYY_MM_DD));
	report_Vector.add("");
	report_Vector.add("                        Source  " 
		+ "  From     Use     Type    Obs       Value");
	report_Vector.add("                        ------  " 
		+ "--------  ------  ------  ------  -----------");

	String date_string = 
		__detailDateTime.toString(DateTime.FORMAT_YYYY_MM_DD);
	String wis_date_string = "";

	int nrows = __worksheet.getRowCount();
	for (int row = 0; row < nrows; row++) {
		wis_date_string = "  current ";
		report_Vector.add("                        " 
			+ __worksheet.getValueAtAsString(row, 1, "%6.6s")
			+ "  " + StringUtil.formatString(
			__worksheet.getValueAtAsString(row, 2, "%8d"), "%8.8s")
			+ "  " 
			+ __worksheet.getValueAtAsString(row, 3, "%6.6s")
			+ "  " 
			+ __worksheet.getValueAtAsString(row, 4, "%6.6s")
			+ "  " 
			+ __worksheet.getValueAtAsString(row, 5, "%6.6s")
			+ "  " + StringUtil.formatString(
			__worksheet.getValueAtAsString(row, 6, "%6.2f")
			,"%6.6s")
			+ " CFS");
	}
/*
	report_Vector.add("                                       "
		+ "                           "
		+ StringUtil.formatString(
		StringUtil.formatString(getTotalAmount(), "%6.2f"), "%6.6s")
		+ " CFS");
*/
	report_Vector.add("");
	report_Vector.add("");
	report_Vector.add("Diversion coding history in HydroBase:");
	report_Vector.add("");
	report_Vector.add("            WIS");
	report_Vector.add("Date        Save Date   Source  " 
		+ "  From     Use     Type    Obs       Value");
	report_Vector.add("----------  ----------  ------  " 
		+ "--------  ------  ------  ------  -----------");

	// First list the current contents of the dialog...

	// Now loop through the historic records, which are in ascending order
	// by date...

	int size = 0;
	if (records != null) {
		size = records.size();
	}
	DateTime d = new DateTime(__detailDateTime);
	HydroBase_WISDailyWC record = null;
	boolean date_printed = false;
	HydroBase_WISComments wis_comment = null;
	List<HydroBase_WISComments> wis_comments = null;
	DateTime wis_set_DateTime = null;
	// First loop through days...
	for (; d.greaterThanOrEqualTo(start_DateTime); d.addDay(-1)) {
		// Now loop through the records.  Because we don't know how
		// mgetCal_monany records how the records will be combined, 
		// loop through all of them for each day...
		date_printed = false;
		for (int i = (size - 1); i >= 0; i--) {
			record = records.get(i);
			if ( record.getCal_year() != d.getYear() || record.getCal_mon() != d.getMonth()
				|| DMIUtil.isMissing( record.getAmountForDay(d.getDay())) || HydroBase_Util.isMissing(record.getAmountForDay(d.getDay()))) {
				continue;
			}

			if (date_printed) {
				date_string = "          ";
				wis_date_string = "          ";
			}
			else {	
				date_string = d.toString(DateTime.FORMAT_YYYY_MM_DD);
				date_printed = true;
				// Get the WIS save date...
				try {	
					wis_comments = __dmi.readWISCommentsList( __wisGUI.getWISNumber(), d);

					if ( (wis_comments == null) || (wis_comments.size()== 0)) {
						wis_date_string = "          ";
					}
					else {	
						wis_comment = wis_comments.get(0);
						wis_set_DateTime = new DateTime( wis_comment.getSet_date());
						wis_date_string = wis_set_DateTime.toString( DateTime.FORMAT_YYYY_MM_DD);
					}
				}
				catch (Exception e) {
					wis_date_string = "          ";
				}
			}
			report_Vector.add(
				date_string + "  "
				+ wis_date_string + "  "
				+ StringUtil.formatString( record.getS(),"%6.6s") + "  "
				+ StringUtil.formatString( record.getF(),"%8.8s") + "  "
				+ StringUtil.formatString( record.getU(),"%6.6s") + "  "
				+ StringUtil.formatString( record.getT(),"%6.6s") + "  "
				+ StringUtil.formatString( record.getObservationForDay( d.getDay()),"%6.6s") + "  "
				+ StringUtil.formatString( record.getAmountForDay( d.getDay()), "%6.6s") + " " + record.getUnit());
		}
	}

	// Use the standard report display...

	PropList reportProp = new PropList("Structure summary");
        reportProp.set("TotalWidth=750");
        reportProp.set("TotalHeight=550"); 
        reportProp.set("Title=Diversion Coding History for " 
		+ rowLabel + " (" + colLabel + " - last 2 irrigation years)");
        reportProp.set("DisplayFont=Courier");
        reportProp.set("DisplayStyle=" + Font.PLAIN);
        reportProp.set("DisplaySize=11");
        reportProp.set("PrintFont=Courier");
        reportProp.set("PrintStyle=" + Font.PLAIN);
        reportProp.set("PrintSize=7");
        reportProp.set("PageLength=100");
	new ReportJFrame(report_Vector, reportProp);
}

/**
Does nothing.
*/
public void windowActivated(WindowEvent event) {}

/**
Closes the window.
@param event the WindowEvent that happened.
*/
public void windowClosed(WindowEvent event) {
	if (event.getComponent().equals(__wisGUI)) {
		// WIS GUI is closing so need to close the detail without
		// saving edits...
		cancelClicked();
	}
}

/**
Closes the window. 
@param event the WindowEvent that happened.
*/
public void windowClosing(WindowEvent event) {
	if (event.getComponent().equals(this)) {
		cancelClicked();
	}
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
