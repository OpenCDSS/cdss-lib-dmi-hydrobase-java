// HydroBase_GUI_GroundWaterQuery - Ground water data GUI

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
// HydroBase_GUI_GroundWaterQuery - Ground water data GUI similar to Other 
//	Query GUI which includes pump tests, drillers logs, wall measurements, 
//	and pumping time series
//-----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 01 May 2000	CEN, RTi	Created initial version. Modified from
//				from HBOtherGUI.java
// 05 Jun 2000	CEN, RTi	Change well measurement to query
//				struct_meas_type first rather than 
//				well_meas for initial list, then display
//				final list selection as a graphical time series
//				after querying well_meas
// 07 Jun 2000	CEN, RTi	Final detail clean up before TAC
// 16 May 2001	SAM, RTi	Change GUI to JGUIUtil.  Add finalize().
//				Change GUI strings to not be static to save
//				memory.  Change so title of graph is
//				"Well Level".
// 2002-02-20	SAM, RTi	Update to use TSViewJFrame instead of TSViewGUI.
//				Change some column widths to not waste so much
//				space.
//-----------------------------------------------------------------------------
// 2003-05-28	J. Thomas Sapienza, RTi	Initial Swing version.
// 2003-05-30	JTS, RTi		Added code to copy from the table.
// 2003-06-02	JTS, RTi		Added code so an hourglass displays when
//					sorting the table
// 2003-07-28	JTS, RTi		* Updated JWorksheet code to stop using
//					  deprecated methods.
//					* Removed old JWorksheet method of
//					  enabling copy/paste.
// 2003-09-23	JTS, RTi		Changed the export code to use 
//					the new export code in 
//					HydroBase_GUI_Util.
// 2003-12-09	JTS, RTI		Uncommented the time series code.
// 2004-01-12	SAM, RTI		* Set the title to use the application
//					  name.
//					* Comment out the help button and add
//					  tool tips to buttons.
//					* Fix wording on the Pumping Time Series
//					  warning to match the current GUI.
//					* Fix so View is enabled when more than
//					  one well measurement is selected.
// 2004-01-20	JTS, RTi		Began using the JScrollWorksheet in
//					order to use worksheet row headers.
// 2004-05-18	JTS, RTi		Added buildTSID() to properly build the
//					TSID for time series, using the usbr_id
//					or usgs_id if available.
// 2004-06-22	JTS, RTi		Table strings were moved from the 
//					DMI class to HydroBase_GUI_Util so they
//					were renamed in here.
// 2004-07-26	JTS, RTi		Users no longer have to scroll the
//					"Where" combo box.
// 2005-01-10	JTS, RTi		Converted to use InputFilters.
// 2005-02-09	JTS, RTi		* Removed the build location button and
//					  all related code.
// 					* Removed getWhereClause().
//					* Removed getOrderByClause().
//					* Where and order by clauses are now 
//					  built within the 
//					  readStructureGeolocList() 
//					  method called from this GUI.  That
//					  method will call an SQL query or
//					  stored procedure query as appropriate.
// 					* Separate table models and cell 
//					  renderers are used if a stored 
//					  procedure query was run.
//					* Removed order by combo boxes.
//					* Removed structure type check boxes.
// 2005-02-11	JTS, RTi		Converted to allow the pump test GUI
//					to open for a pump test view object or
//					the original pump test data.
// 2005-03-25	JTS, RTi		Added support for View objects.
// 2005-04-28	JTS, RTi		Added all data members to finalize().
// 2005-05-09	JTS, RTi		HydroBase_PumpTestView objects now used
//					for SP and non-SP queries.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
// 2005-06-28	JTS, RTi		Removed DMI parameters from table models
// 2005-06-29	JTS, RTi		Added support for volcanics and dillers
//					k sum data.
// 2005-11-15	JTS, RTi		Option to query the entire state at once
//					added to the district combo box.
// 2007-02-07	SAM, RTi		Remove the dependence on CWRAT.
//					Pass a JFrame to the constructor.
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import RTi.Util.GUI.DragAndDropJWorksheet;
import RTi.Util.GUI.DragAndDropListener;
import RTi.Util.GUI.DragAndDropUtil;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.GUI.JGUIUtil; 
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.ReportJFrame;
import RTi.Util.GUI.SimpleJButton; 
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.StopWatch;

import RTi.TS.DayTS;
import RTi.TS.HourTS;
import RTi.TS.IrregularTS;
import RTi.TS.MinuteTS;
import RTi.TS.MonthTS;
import RTi.TS.TS;
import RTi.TS.TSIdent;

import RTi.GRTS.TSProduct;
import RTi.GRTS.TSViewJFrame;

@SuppressWarnings("serial")
public class HydroBase_GUI_GroundWaterQuery 
extends JFrame 
implements ActionListener, DragAndDropListener, ItemListener, KeyListener, 
MouseListener, WindowListener {

/**
Strings representing the kinds of data that can be displayed.
*/
private final String 	 
	__DTYP_DRILLERS = 	"Driller's K Sum",
	__DTYP_GEOPHLOGS =	"Geophysical Logs",
	__DTYP_PUMPTESTS = 	"Pumping Tests",
	__DTYP_WELLMEAS = 	"Well Level Measurements",
	__DTYP_PUMPINGTS = 	"Pumping Time Series",
	__DTYP_VOLCANICS =	"Volcanics";

/**
Button labels.
*/
private final String 
	__BUTTON_CLOSE = "Close",
	__BUTTON_EXPORT = "Export",
	__BUTTON_GET_DATA = "Get Data",
	__BUTTON_HELP = "Help",
	__BUTTON_PRINT = "Print",
	__BUTTON_VIEW = "View";

/**
Whether the data is in the new, 20050701 and later, format.
*/
private boolean __newWellFormat = false;

/**
Reference to the dmi that is connected to the database.
*/
private HydroBaseDMI __dmi;

/**
Input filter for queries for pre-20050701 databases.
*/
private HydroBase_GUI_GroundWaterOld_InputFilter_JPanel 
	__oldFilterJPanel = null;

/**
Input filter for queries for 20050701+ databases.
*/
private HydroBase_GUI_GroundWater_InputFilter_JPanel 
	__newFilterJPanel = null;

/**
The currently-selected input filter.
*/
private InputFilter_JPanel __selectedFilterJPanel = null;

/**
Button for exporting records to a file.
*/
private SimpleJButton __exportButton;

/**
Button for printing the records.
*/
private SimpleJButton __printButton;

/**
Button for displaying detailed view of a record.
*/
private SimpleJButton __viewButton;

/**
Combo box that holds the list of water divisions and districts.
*/
private SimpleJComboBox __waterDistrictJComboBox;

/**
Combo box that holds the lists of the kind of data being displayed.
*/
private SimpleJComboBox __dataTypeJComboBox;

/**
Label for the table
*/
private JLabel		__tableJLabel;

/**
GUI textfields.
*/
private JTextField 
	__statusJTextField;

/**
Worksheet that displays the records.
*/
private DragAndDropJWorksheet __worksheet;

/**
Label for the table when a query is run.  Changed depending on the table
selected from the combo box.
*/
private String __tableLabelString = null;

/**
Constructor.
@param dmi an open and non-null DMI that will be used to get data from the 
database.
*/
public HydroBase_GUI_GroundWaterQuery(HydroBaseDMI dmi) {
	__dmi = dmi;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());

	if (__dmi.getDatabaseVersion() < HydroBaseDMI.VERSION_20050701) {
		__newWellFormat = false;
	}
	else {
		__newWellFormat = true;
	}
	
	setupGUI();
}

/**
Responds to action performed events.
@param evt the event that happened.
*/
public void actionPerformed(ActionEvent evt) {
	String routine = "actionPerformed";
	String s = evt.getActionCommand();

	if (s.equals(__BUTTON_HELP)) {
        }
        else if (s.equals(__BUTTON_CLOSE)) { 
                closeClicked();
        }
        else if (s.equals(__BUTTON_GET_DATA)) {
                submitQuery();
        }
        else if (s.equals(__BUTTON_VIEW)) {
                viewClicked();
        }
	else if (s.equals(__BUTTON_EXPORT)) {
		try {
			String[] eff = 
				HydroBase_GUI_Util.getExportFilenameAndFormat(
				this, 
				HydroBase_GUI_Util
				.getDelimitedFormatsAndExtensions());

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
			Message.printWarning (2, routine, ex);
		}	
	}
	else if (s.equals(__BUTTON_PRINT)) {
		try {
			SelectFormatTypeJDialog d = 
				new SelectFormatTypeJDialog(this, 
				HydroBase_GUI_Util.getDelimitedFormats());
			int format = d.getSelected();
			if (format == HydroBase_GUI_Util.CANCEL) {
				return;
			}			
			d.dispose();
	 		// First format the output...
			List<String> outputStrings = formatOutput(format);
	 		// Now print...
			PrintJGUI.print(this, outputStrings);
		}
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}			
	}
}

/**
Given a time series and the row in the worksheet for which the time series
was retrieved, builds the proper tsid string for placement in the proplist
to be graphed.  Takes into account whether the usgs or usbr ids are set.
@param ts the time series read for a row in the worksheet.
@param row the row in the worksheet to be graphed.
@return a time series id string to use.
*/
private String buildTSID(TS ts, int row) {
	String usbr = null;
	String usgs = null;
	TSIdent tsid = ts.getIdentifier();

	if (__newWellFormat) {

	}
	else {
		HydroBase_StructureGeolocStructMeasTypeView data = 
			(HydroBase_StructureGeolocStructMeasTypeView)
			__worksheet.getRowData(row);
		
		usbr = data.getUsbr_id();
		usgs = data.getUsgs_id();
		
		if (!DMIUtil.isMissing(usgs)) {
			tsid.setLocation(usgs);
		}
		else if (!DMIUtil.isMissing(usbr)) {
				tsid.setLocation(usbr);
		}
	}

	return tsid.toString();
}

/**
Closes the gui.
*/
private void closeClicked() {
        setVisible(false);
}

/**
Fills in the appropriate selections and sets the queryJComboBox and 
search text fields based on the current data type in the dataTypeComboBox.
*/
private void dataTypeJComboBoxClicked() {
        String routine = "dataTypeJComboBoxClicked()";
        String dtype  = __dataTypeJComboBox.getSelected().trim();

	__worksheet.clear();
	
	if (dtype.equals(__DTYP_DRILLERS)) {
		__tableLabelString = "Driller's K Sum Data:";
		__tableJLabel.setText(__tableLabelString);
		__viewButton.setEnabled(false);
		displayResults(new Vector<HydroBase_GroundWaterWellsDrillersKSum>());
	}
	else if (dtype.equals(__DTYP_GEOPHLOGS)) {
		__tableLabelString = "Geophysical Logs Data:";
		__tableJLabel.setText(__tableLabelString);
		__viewButton.setEnabled(true);
		displayResults(new Vector<HydroBase_GroundWaterWellsGeophlogs>());
	}
	else if (dtype.equals(__DTYP_PUMPINGTS)) {
		// pumping time series
		Message.printWarning(1, routine, 
			"Use the Data...Structures menu to query\n" +
			"pumping time series, which, if available, are coded\n"+
			"as diversion records for well structures.", this);
		ready();
		__viewButton.setEnabled(false);
		return;
	}
	else if (dtype.equals(__DTYP_WELLMEAS)) {
		__tableLabelString = "Well Level Measurements Data: ";
		__tableJLabel.setText(__tableLabelString);
		__viewButton.setEnabled(true);
		displayResults(new Vector<HydroBase_WellMeas>());
	}
	else if (dtype.equals(__DTYP_PUMPTESTS)) {	
		__tableLabelString = "Pumping Tests Data: ";
		__tableJLabel.setText(__tableLabelString);
		__viewButton.setEnabled(true);
		displayResults(new Vector<HydroBase_GroundWaterWellsPumpingTest>());
	}
	else if (dtype.equals(__DTYP_VOLCANICS)) {
		__tableLabelString = "Volcanics Data: ";
		__tableJLabel.setText(__tableLabelString);
		__viewButton.setEnabled(false);
		displayResults(new Vector<HydroBase_GroundWaterWellsVolcanics>());
	}
}

/**
Display the results in the table.  Determines which kind of table to 
display the results in by seeing what data type is selected from the
data type combo box.
@param results vector data from a query
*/
private <T extends DMIDataObject> void displayResults(List<T> results) {
	String routine = "displayResults";
        String dtype  = __dataTypeJComboBox.getSelected().trim();

	try {
	
	if (dtype.equals(__DTYP_DRILLERS)) {
		@SuppressWarnings("unchecked")
		HydroBase_TableModel_GroundWaterWellsDrillersKSum tm = 
			new HydroBase_TableModel_GroundWaterWellsDrillersKSum(
				(List<HydroBase_GroundWaterWellsDrillersKSum>)results,__dmi.getAquiferRef());
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
		__worksheet.setCellRenderer(cr);
		__worksheet.setModel(tm);
		__worksheet.setColumnWidths(tm.getColumnWidths());
	}
	else if (dtype.equals(__DTYP_GEOPHLOGS)) {
		@SuppressWarnings("unchecked")
		HydroBase_TableModel_GroundWaterWellsGeophlogs tm = 
			new HydroBase_TableModel_GroundWaterWellsGeophlogs(
				(List<HydroBase_GroundWaterWellsGeophlogs>)results, __dmi.getAquiferRef());
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
		__worksheet.setCellRenderer(cr);
		__worksheet.setModel(tm);
		__worksheet.setColumnWidths(tm.getColumnWidths());
	}
	else if (dtype.equals(__DTYP_PUMPTESTS)) {
		if (!__newWellFormat) {
			@SuppressWarnings("unchecked")
			HydroBase_TableModel_PumpTest tm = 
				new HydroBase_TableModel_PumpTest((List<HydroBase_GroundWaterWellsPumpingTest>)results);
			HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
			__worksheet.setCellRenderer(cr);
			__worksheet.setModel(tm);
			__worksheet.setColumnWidths(tm.getColumnWidths());
		}
		else {
			@SuppressWarnings("unchecked")
			HydroBase_TableModel_GroundWaterWellsPumpingTest tm = 
			    new HydroBase_TableModel_GroundWaterWellsPumpingTest(
			       (List<HydroBase_GroundWaterWellsPumpingTest>)results, __dmi.getAquiferRef());
			HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
			__worksheet.setCellRenderer(cr);
			__worksheet.setModel(tm);
			__worksheet.setColumnWidths(tm.getColumnWidths());
		}		
	}
	else if (dtype.equals(__DTYP_WELLMEAS)) {
		if (!__newWellFormat) {
			@SuppressWarnings("unchecked")
			HydroBase_TableModel_WellMeas tm = 
				new HydroBase_TableModel_WellMeas((List<HydroBase_StructureGeolocStructMeasTypeView>)results);
			HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
			__worksheet.setCellRenderer(cr);
			__worksheet.setModel(tm);
			__worksheet.setColumnWidths(tm.getColumnWidths());
		}
		else {
			@SuppressWarnings("unchecked")
			HydroBase_TableModel_GroundWaterWellsWellMeas tm = 
		      new HydroBase_TableModel_GroundWaterWellsWellMeas(
			      (List<HydroBase_GroundWaterWellsView>)results, __dmi.getAquiferRef());
			HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
			__worksheet.setCellRenderer(cr);
			__worksheet.setModel(tm);
			__worksheet.setColumnWidths(tm.getColumnWidths());
		}
	}
	else if (dtype.equals(__DTYP_VOLCANICS)) {
		@SuppressWarnings("unchecked")
		HydroBase_TableModel_GroundWaterWellsVolcanics tm = 
			new HydroBase_TableModel_GroundWaterWellsVolcanics(
			(List<HydroBase_GroundWaterWellsVolcanics>)results, __dmi.getAquiferRef());
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
		__worksheet.setCellRenderer(cr);
		__worksheet.setModel(tm);
		__worksheet.setColumnWidths(tm.getColumnWidths());		
	}
	
	}
	catch (Exception e) {
		Message.printWarning(2, routine, "Unable to change the table type.");
		Message.printWarning(2, routine, e);
	}	
}


/**
Called just before the drag starts.  Used to get the time series to be dragged
and put it in the Transferable object for the drag.
*/
public boolean dragAboutToStart() {
	String routine = "HydroBase_GUI_GroundWaterQuery.dragAboutToStart";

	String dtype = __dataTypeJComboBox.getSelected().trim();
	if (!dtype.equals(__DTYP_WELLMEAS)) {
		return false;
	}

	int wd = DMIUtil.MISSING_INT;
	int id = DMIUtil.MISSING_INT;
	String data_source = DMIUtil.MISSING_STRING;

	TS ts = null;
	
	if (__newWellFormat) {

	}
	else {
		HydroBase_StructureGeolocStructMeasTypeView data = 
			(HydroBase_StructureGeolocStructMeasTypeView)
			__worksheet.getRowData(
			__worksheet.getSelectedRow());
		wd = data.getWD();
		id = data.getID();
		data_source = data.getData_source();
	
	    	try {
			String wdid = HydroBase_WaterDistrict.formWDID(wd, id);
			Message.printStatus(2, "", "tsid: "
				+ wdid + "." + data_source +".WellLevel.Day");
			ts = __dmi.readTimeSeries(
				wdid + "." + data_source
				+ ".WellLevel.Day",
				null, null, null, true, null);	
		}
		catch (Exception e) {
			Message.printWarning(1, routine,
				"Error reading time series from database.");
			Message.printWarning(2, routine, e);
		}
	}

	if (ts != null) {
		if (ts instanceof MonthTS) {
			Message.printStatus(2, routine, "::MonthTS");
		}
		else if (ts instanceof DayTS) {
			Message.printStatus(2, routine, "::DayTS");
		}
		else if (ts instanceof HourTS) {
			Message.printStatus(2, routine, "::HourTS");
		}
		else if (ts instanceof MinuteTS) {
			Message.printStatus(2, routine, "::MinuteTS");
		}
		else if (ts instanceof IrregularTS) {
			Message.printStatus(2, routine, "::IrregularTS");
		}
		else {
			Message.printStatus(2, routine, 
				"Unrecognized TS type: " + ts.getClass());
		}
		__worksheet.setAlternateTransferable(ts);
	}
	else {
		Message.printStatus(2, "", "TS was null");
		ready();
		return false;
	}

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
Clean up for garbage collection.
*/
protected void finalize()
throws Throwable {
	__dmi = null;
	__oldFilterJPanel = null;
	__exportButton = null;
	__printButton = null;
	__viewButton = null;
	__waterDistrictJComboBox = null;
	__dataTypeJComboBox = null;
	__tableJLabel = null;
	__statusJTextField = null;
	__worksheet = null;
	__tableLabelString = null;
	super.finalize();
}

/**
Responsible for formatting output.
@param format format delimiter flag defined in this class
@return formatted list for exporting, printing, etc..
*/
private List<String> formatOutput(int format) {
        int size = __worksheet.getRowCount();
        if (size == 0) {
                return new Vector<String>();
        }

        char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);
        
	// Delimited.  Output in the order shown on the screen
	int colCount = __worksheet.getColumnCount();
	String s = "";			

	List<String> v = new Vector<String>();
	for (int j = 0; j < colCount; j++) {
		s += __worksheet.getColumnName(j, true) + delim;
	}
	v.add(s);

	boolean isSelected = false;
	int[] selected = __worksheet.getSelectedRows();
	int length = selected.length;

	String d = "";
        for (int i = 0; i < size; i++) {
		isSelected = false;
		s = "";
		if (length == 0) {
			isSelected = true;
		}
		else {
			for (int j = 0; j < length; j++) {
				if (selected[j] == i) {
					isSelected = true;
				}
			}
		}
		if (isSelected) {
			for (int j = 0; j < colCount; j++) {
				d = "" + __worksheet.getValueAtAsString(i, j);
				s += d + delim;
			}
			v.add(s);			
		}
	}

        return v;
}

/**
Generates a geophysical logs report.
*/
private void generateGeophlogsReport() {
	String routine = "generateGeophlogsReport";

	HydroBase_GroundWaterWellsView wellView = 
		(HydroBase_GroundWaterWellsView)__worksheet.getRowData(
		__worksheet.getSelectedRow());
	
	int well_num = wellView.getWell_num();

	List<HydroBase_GroundWaterWellsGeophlogs> v = null;

	try {
		v = __dmi.readGeophlogsListForWell_num(well_num);
	}
	catch (Exception e) {
		Message.printWarning(2, routine,"Error reading from database.");
		Message.printWarning(2, routine, e);
	}	

	List<String> report = new Vector<String>();

        PropList reportProps = new PropList("ReportJFrame.props");
        reportProps.set("TotalWidth=750");
        reportProps.set("TotalHeight=550"); 
        reportProps.set("Title=Geophysical Log Records");
        reportProps.set("DisplayFont=Courier");
        reportProps.set("DisplayStyle=" + Font.PLAIN);
        reportProps.set("DisplaySize=11");
        reportProps.set("PrintFont=Courier");
        reportProps.set("PrintStyle=" + Font.PLAIN);
        reportProps.set("PrintSize=7");
        reportProps.set("PageLength=100");

	if (v == null || v.size() == 0) {
		report.add("No Geophysical Log data for well.");
		new ReportJFrame(report, reportProps);
		return;
	}

	report.add("Geophysical Log data for Well");
	report.add("");
	report.add("WD: " + wellView.getWD());
	report.add("Well Name: " + wellView.getWell_name());
	report.add("Loc Num: " + wellView.getLoc_num());
	report.add("Site ID: " + wellView.getSite_id());

	String s = "";
	int i = wellView.getPermitno();
	if (!DMIUtil.isMissing(i) && !HydroBase_Util.isMissing(i)) {
		s += i;
	}

	s += "-";

	String temp = wellView.getPermitsuf();
	if (!DMIUtil.isMissing(temp) ) {
		s += temp;
	}
	
	s += "-";

	temp = wellView.getPermitrpl();
	if (!DMIUtil.isMissing(temp) ) {
		s += temp;
	}
	report.add("Permit Info: " + s);

	report.add("");

	boolean orig1986 = false;
	int base = 0;
	int size = v.size();
	int thickness = 0;
	int top = 0;
	HydroBase_GroundWaterWellsGeophlogs data = null;
	String aquifer = null;
	String line = null;

	line = "AQUIFER                          TOP    BASE   NET    "
		+ "ORIG. 1986";
	report.add(line);

	for (i = 0; i < size; i++) {
		data = (HydroBase_GroundWaterWellsGeophlogs)v.get(i);

		aquifer = data.getAquifer_name();
		if (DMIUtil.isMissing(aquifer) ) {
			line = "                                 ";
		}
		else {
			line = StringUtil.formatString(aquifer, "%-30.30s")	+ "   ";
		}
		
		top = data.getGlogtop();
		if (DMIUtil.isMissing(top) || HydroBase_Util.isMissing(top)) {
			line += "       ";
		}
		else {
			line += StringUtil.formatString(top, "%4d") + "   ";
		}


		base = data.getGlogbase();
		if (DMIUtil.isMissing(base) || HydroBase_Util.isMissing(base)) {
			line += "       ";
		}
		else {
			line += StringUtil.formatString(base, "%4d") + "   ";
		}

		thickness = data.getGlogthickness();
		if (DMIUtil.isMissing(thickness) || HydroBase_Util.isMissing(thickness)) {
			line += "       ";
		}
		else {
			line += StringUtil.formatString(thickness, "%4d") + "   ";
		}

		orig1986 = data.getOrig_1986();
		if (orig1986) {
			line += "Yes";
		}
		else {
			line += "No";
		}
			
		report.add(line);
	}

	new ReportJFrame(report, reportProps);
}

/**
Respond to ItemEvents.
@param evt ItemEvent object.
*/
public void itemStateChanged(ItemEvent evt) {
	if (evt.getStateChange() == ItemEvent.SELECTED 
	    && evt.getSource() == __dataTypeJComboBox) {
        	dataTypeJComboBoxClicked();
	}
}

/**
Responds to key pressed events.
@param event the KeyEvent that happened.
*/
public void keyPressed(KeyEvent event) { 
	int code = event.getKeyCode();
	if (code == KeyEvent.VK_ENTER) {
		submitQuery();
	}
}

/**
Does nothing.
*/
public void keyReleased(KeyEvent event) {}

/**
Does nothing.
*/
public void keyTyped(KeyEvent event) {}

/**
Does nothing.
*/
public void mouseClicked(MouseEvent event) {}

/**
Does nothing.
*/
public void mouseDragged(MouseEvent event) {}

/**
Responds to mouse pressed events in the input filter panel.
@param event the MouseEvent that happened.
*/
public void mousePressed(MouseEvent event) {
	int numFilters = __selectedFilterJPanel.getNumFilterGroups();
	for (int i = 0; i < numFilters; i++) {
		if (event.getSource() == __selectedFilterJPanel.getInputFilter(
			i).getInputComponent()) {
			HydroBase_GUI_Util.buildLocation(this,
				(JTextField)event.getSource());
		}
	}
}

/**
Responds to mouse released events.
@param event the MouseEvent that happened.
*/
public void mouseReleased(MouseEvent event) {
	int rowCount = __worksheet.getSelectedRowCount();
        String dtype = __dataTypeJComboBox.getSelected().trim();

	if (dtype.equals(__DTYP_DRILLERS)
	    || dtype.equals(__DTYP_VOLCANICS)) {
	    	__viewButton.setEnabled(false);
		return;
	}

	if (rowCount == 0) {
		__viewButton.setEnabled(false);
		return;
	}

	if (dtype.equals(__DTYP_WELLMEAS) && (rowCount > 0)) {
		__viewButton.setEnabled(true);
	}
	else if (!dtype.equals(__DTYP_WELLMEAS) && (rowCount == 1)) {
		__viewButton.setEnabled(true);
	}
	else {
		__viewButton.setEnabled(false);
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
Readies the GUI for user interaction.
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
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        
        // objects used throughout the GUI layout
        Insets insetsNLNN = new Insets(0,7,0,0);
        Insets insetsTLNN = new Insets(7,7,0,0);
        Insets insetsNLBR = new Insets(0,7,7,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        GridBagLayout gbl = new GridBagLayout();
        
        // North JPanel
        JPanel northJPanel = new JPanel();
        northJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", northJPanel);
        
        // North West JPanel
        JPanel northWJPanel = new JPanel();
        northWJPanel.setLayout(gbl);
        northJPanel.add("West", northWJPanel);
        
	int y=0;
        JGUIUtil.addComponent(northWJPanel, new JLabel("Query Options:"), 
                0, y++, 4, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel, new JLabel("Div/Dist:"), 
                0, y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__waterDistrictJComboBox = new SimpleJComboBox();
        JGUIUtil.addComponent(northWJPanel, __waterDistrictJComboBox, 
                1, y++, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	__waterDistrictJComboBox.addItemListener(this);

        JGUIUtil.addComponent(northWJPanel, new JLabel("Data Type:"), 
                0, y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__dataTypeJComboBox = new SimpleJComboBox();
	if (__newWellFormat) {
		__dataTypeJComboBox.add(__DTYP_DRILLERS);
		__dataTypeJComboBox.add(__DTYP_GEOPHLOGS);
	}
	__dataTypeJComboBox.add(__DTYP_PUMPTESTS);
	__dataTypeJComboBox.add(__DTYP_PUMPINGTS);
	if (__newWellFormat) {
		__dataTypeJComboBox.add(__DTYP_VOLCANICS);
	}
	__dataTypeJComboBox.add(__DTYP_WELLMEAS);
        JGUIUtil.addComponent(northWJPanel, __dataTypeJComboBox, 
                1, y++, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__oldFilterJPanel = new HydroBase_GUI_GroundWaterOld_InputFilter_JPanel(
		__dmi, this);
	__oldFilterJPanel.addEventListeners(this);
        JGUIUtil.addComponent(northWJPanel, __oldFilterJPanel,
      		0, y, 3, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__newFilterJPanel 
		= new HydroBase_GUI_GroundWater_InputFilter_JPanel(
		new HydroBaseDataStore("HydroBase","CDSS HydroBase database", __dmi,true), this, false);
	__newFilterJPanel.addEventListeners(this);
        JGUIUtil.addComponent(northWJPanel, __newFilterJPanel,
      		0, y, 3, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        SimpleJButton get = new SimpleJButton(__BUTTON_GET_DATA, this );
	get.setToolTipText ( "Query for data matching the Where/Is" );
        JGUIUtil.addComponent(northWJPanel, get, 
		3, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL,GridBagConstraints.SOUTHWEST);

        // Center JPanel for MultiList
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(gbl);
        getContentPane().add("Center", centerJPanel);        

	__tableJLabel = new JLabel();
	PropList p = 
		new PropList("HydroBase_GUI_GroundWaterQuery."
		+ "DragAndDropJWorksheet");

	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.ShowPopupMenu=true");

	JScrollWorksheet jsw = new JScrollWorksheet(0, 0, p, true);
	__worksheet = (DragAndDropJWorksheet)jsw.getJWorksheet();
	DragAndDropUtil.addDragAndDropListener(__worksheet, this);
	__worksheet.setHourglassJFrame(this);
	__worksheet.addMouseListener(this);	
	__tableLabelString = "Pumping Tests Data: ";
	__tableJLabel.setText(__tableLabelString);
	JGUIUtil.addComponent(centerJPanel, __tableJLabel, 
		1, 1, 7, 1, 1, 0, 
		insetsNLNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	JGUIUtil.addComponent(centerJPanel, jsw,
		1, 2, 7, 3, 1, 1, insetsNLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);
        
        //Bottom JPanel(Consist of two more JPanels)
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);
                                
        // Bottom Center JPanel
        JPanel bottomCJPanel = new JPanel();
        bottomCJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("Center", bottomCJPanel);

        __viewButton = new SimpleJButton(__BUTTON_VIEW, this);
	__viewButton.setToolTipText ("View selected data in detailed display.");
        bottomCJPanel.add(__viewButton);
        
        __printButton = new SimpleJButton(__BUTTON_PRINT, this);
	__printButton.setToolTipText ( "Print all data in a selected format." );
	__printButton.setEnabled(false);
        bottomCJPanel.add(__printButton);
        
        __exportButton = new SimpleJButton(__BUTTON_EXPORT, this);
	__exportButton.setToolTipText ("Export all data to a selected format.");
	__exportButton.setEnabled(false);
        bottomCJPanel.add(__exportButton);
        
        SimpleJButton close = new SimpleJButton(__BUTTON_CLOSE, this);
	close.setToolTipText ("Close this window.");
        bottomCJPanel.add(close);
        
        // Bottom South JPanel
        JPanel bottomSJPanel = new JPanel();
        bottomSJPanel.setLayout(gbl);
        bottomJPanel.add("South", bottomSJPanel);
        
        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSJPanel, __statusJTextField, 0, 1, 
                10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
                
	// set defaults, based off initial data type choice
	if (__newWellFormat) {
		__dataTypeJComboBox.select(__DTYP_GEOPHLOGS);
		__newFilterJPanel.setVisible(true);
		__oldFilterJPanel.setVisible(false);
		__selectedFilterJPanel = __newFilterJPanel;
	}
	else {
		__dataTypeJComboBox.select(__DTYP_PUMPTESTS);
		__newFilterJPanel.setVisible(false);
		__oldFilterJPanel.setVisible(true);
		__selectedFilterJPanel = __oldFilterJPanel;
	}
	__dataTypeJComboBox.addItemListener(this);
                
        // Frame settings
        setTitle("Ground Water Query");
	if (	(JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( "Ground Water Data - Query" );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() +
		" - Ground Water Data - Query" );
	}
        pack();
        setSize(700, 550);
        
        JGUIUtil.center(this);
        setVisible(true);
}

/**
Sets the gui as visible or not.
@param state if true, the gui will be visible.  If false, it will not.
*/
public void setVisible(boolean state) {
	String routine = "setVisible";
        super.setVisible(state);
	try {
        if (state) {
		__worksheet.clear();
		HydroBase_GUI_Util.setWaterDistrictJComboBox(
			__dmi, __waterDistrictJComboBox, null, true, true,true);
		if (__newWellFormat) {
			__dataTypeJComboBox.select(__DTYP_GEOPHLOGS);
			__newFilterJPanel.setVisible(true);
			__oldFilterJPanel.setVisible(false);
		}
		else {
			__dataTypeJComboBox.select(__DTYP_PUMPTESTS);
			__newFilterJPanel.setVisible(false);
			__oldFilterJPanel.setVisible(true);
		}
		dataTypeJComboBoxClicked();
		
		__viewButton.setEnabled(false);
		__printButton.setEnabled(false);
		__exportButton.setEnabled(false);
		
		// set up current choices for data type and time step
                ready();
        }
	}
	catch (Exception e) {
		Message.printWarning (2, routine, e);
	}	
}

/**
Submits a query and displays the results.
*/
private void submitQuery() {
	String routine = "submitQuery";
       	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Retrieving data...");
        String dtype = __dataTypeJComboBox.getSelected().trim();
	__worksheet.clear();
	
	StopWatch sw = new StopWatch();

	if (!__selectedFilterJPanel.checkInput(true)) {
		ready();
		return;
	}
	
	// Define the query and also the different help string which is the key
	// used in the report properties
	int resultsSize = 0;
	if (dtype.equals(__DTYP_DRILLERS)) {
		try {
		        String status = "Please Wait... Retrieving Data";
		        __statusJTextField.setText(status);
		
			sw = new StopWatch();
			sw.start();		

			List<HydroBase_GroundWaterWellsDrillersKSum>
			results = __dmi.readGroundWaterWellsDrillersKSumList(
				__selectedFilterJPanel, 
				__dmi.getWaterDistrictWhereClause(
					__waterDistrictJComboBox, null,
					true, true));
			displayResults(results);
			sw.stop();
			resultsSize = results.size();
		}
		catch (Exception e) {
			Message.printWarning (2, routine, e);
		}			
	}
	else if (dtype.equals(__DTYP_GEOPHLOGS)) {
		try {
		        String status = "Please Wait... Retrieving Data";
		        __statusJTextField.setText(status);
		
			sw = new StopWatch();
			sw.start();		

			List<HydroBase_GroundWaterWellsGeophlogs>
			results = __dmi.readGroundWaterWellsGeophlogsList(
				__selectedFilterJPanel, 
				__dmi.getWaterDistrictWhereClause(
					__waterDistrictJComboBox, null,
					true, true));
			displayResults(results);
			sw.stop();
			resultsSize = results.size();
		}
		catch (Exception e) {
			Message.printWarning (2, routine, e);
		}			
	}	
	else if (dtype.equals(__DTYP_PUMPTESTS)) {
		try {
			if (!__dmi.useStoredProcedures() 
			    && !DMIUtil.databaseHasTable(__dmi, "pump_test")) {
				Message.printWarning(1, routine,
					"Your database does not include the "
					+ "\"pump_test\" table.  Therefor, "
					+ "this query\ncannot be completed.  "
					+ "Update your database to include "
					+ "well information.");
				ready();
				return;
			}
		} catch (Exception e) {
			Message.printWarning(2, routine, e);
		}
		
		try {
		        String status = "Please Wait... Retrieving Data";
		        Message.printStatus(10, routine, status);
		        __statusJTextField.setText(status);
		
			sw = new StopWatch();
			sw.start();
			List<HydroBase_GroundWaterWellsPumpingTest>
			results = __dmi.readGroundWaterWellsPumpingTestList(
				__selectedFilterJPanel, 
				__dmi.getWaterDistrictWhereClause(
					__waterDistrictJComboBox, 
					HydroBase_GUI_Util
						._STRUCTURE_TABLE_NAME,
					true, true));
			displayResults(results);
			sw.stop();
			resultsSize = results.size();
		}
		catch (Exception e) {
			Message.printWarning (2, routine, e);
		}			
	}
	else if (dtype.equals(__DTYP_WELLMEAS)) {
		try {
			if (!__dmi.useStoredProcedures()
			    && !DMIUtil.databaseHasTable(__dmi, "well_meas")) {
				Message.printWarning(1, routine,
					"Your database does not include the "
					+ "\"pump_test\" table.  Therefor, "
					+ "this query\ncannot be completed.  "
					+ "Update your database to include "
					+ "well information.");
				ready();
				return;
			}
		} catch (Exception e) {
			Message.printWarning(2, routine, e);
		}
		
		try {
		        String status = "Please Wait... Retrieving Data";
		        Message.printStatus(10, routine, status);
		        __statusJTextField.setText(status);
		
			sw = new StopWatch();
			sw.start();		

			List<HydroBase_GroundWaterWellsView>
			results = __dmi.readGroundWaterWellsMeasTypeList(
				__selectedFilterJPanel, 
				__dmi.getWaterDistrictWhereClause(
					__waterDistrictJComboBox, 
					HydroBase_GUI_Util
						._STRUCTURE_TABLE_NAME,
					true, true));
			displayResults(results);
			sw.stop();
			resultsSize = results.size();
		}
		catch (Exception e) {
			Message.printWarning (2, routine, e);
		}			
	}
	else if (dtype.equals(__DTYP_PUMPINGTS)) {
		// pumping time series
		Message.printWarning(1, routine, 
			"Use the View Data...Structures menu to query\n" +
			"pumping time series, which if available are coded\n" +
			"as diversion records for well structures.", this);
		ready();
		return;
	}
	else if (dtype.equals(__DTYP_VOLCANICS)) {
		try {
		        String status = "Please Wait... Retrieving Data";
		        Message.printStatus(10, routine, status);
		        __statusJTextField.setText(status);
		
			sw = new StopWatch();
			sw.start();		

			List<HydroBase_GroundWaterWellsVolcanics>
			results = __dmi.readGroundWaterWellsVolcanicsList(
				__selectedFilterJPanel, 
				__dmi.getWaterDistrictWhereClause(
					__waterDistrictJComboBox, null,
					true, true));
			displayResults(results);
			sw.stop();
			resultsSize = results.size();
		}
		catch (Exception e) {
			Message.printWarning (2, routine, e);
		}			
	}

        int displayed = __worksheet.getRowCount();
	String plural = "";
	if (displayed != 1) {
		plural = "s";
	}

       	JGUIUtil.setWaitCursor(this, false);
        String status = "" + resultsSize + " record" + plural 
		+ " returned in " + sw.getSeconds() + " seconds.";
	__tableJLabel.setText(__tableLabelString + status);

        __statusJTextField.setText(status);

	// set status information
	
	if (dtype.equals(__DTYP_DRILLERS)
	    || dtype.equals(__DTYP_VOLCANICS)) {
	    	__viewButton.setEnabled(false);
	}

	if (displayed == 0) {
		__viewButton.setEnabled(false);
		__printButton.setEnabled(false);
		__exportButton.setEnabled(false);
	}
	else {
		__viewButton.setEnabled(false);
		__printButton.setEnabled(true);
		__exportButton.setEnabled(true);
	}
}

/**
Responds when the user presses the view button.
*/
private void viewClicked() {
	String routine = "viewClicked";

	String dtype = __dataTypeJComboBox.getSelected().trim();

	if (dtype.equals(__DTYP_GEOPHLOGS)) {
		generateGeophlogsReport();
	}
	else if (dtype.equals(__DTYP_PUMPTESTS)) {
		int row = __worksheet.getSelectedRow();
		new HydroBase_GUI_PumpTest(this, 
			(HydroBase_GroundWaterWellsPumpingTest)
			__worksheet.getRowData(row));		
	}
	else if (dtype.equals(__DTYP_WELLMEAS)) {
		// because user is allowed reorder these ...
		TS ts = null;
		int[] rows = __worksheet.getSelectedRows();
		int num_selected = rows.length;
		if (num_selected == 0) {
			Message.printWarning(1, routine, 
				"Must select at least 1 well to graph.");
			return;
		}

		List<TS> tslist = new Vector<TS>(num_selected);
		String wdid = null;
		String tsid = null;
		JGUIUtil.setWaitCursor(this, true);
		HydroBase_GroundWaterWellsView gwwView = null;
		HydroBase_StructureGeolocStructMeasTypeView view = null;
		// Initialize properties for the graph...
		PropList display_props = new PropList("WellLevel");
		display_props.set("InitialView", "Graph");
		display_props.set("TSViewTitleString",
			"Well Level Time Series");
		display_props.set("DisplayFont", "Courier");
		display_props.set("DisplaySize", "11");
		display_props.set("PrintFont", "Courier");
		display_props.set("PrintSize", "7");
		display_props.set("PageLength", "100");
	
		PropList props = new PropList("WellLevel");
		props.set("Product.TotalWidth", "600");
		props.set("Product.TotalHeight", "400");
		props.set("SubProduct 1.GraphType=Line");
		props.set(
			"SubProduct 1.MainTitleString=Well Level Time Series");

		int wd = DMIUtil.MISSING_INT;
		int id = DMIUtil.MISSING_INT;
		String data_source = DMIUtil.MISSING_STRING;		
			
		for (int row = 0; row < num_selected; row++) {
			if (__newWellFormat) {
				gwwView = (HydroBase_GroundWaterWellsView)
					__worksheet.getRowData(rows[row]);
				wdid = gwwView.getIdentifier();
				data_source = gwwView.getData_source();
				tsid = wdid + "." + data_source
					+ ".WellLevel.Day";
			}
			else {
				view = 
				   (HydroBase_StructureGeolocStructMeasTypeView)
					__worksheet.getRowData(rows[row]);
				wd = view.getWD();
				id = view.getID();
				data_source = view.getData_source();
				tsid = wdid + "." + data_source
					+ ".WellLevel.Day";
			}
			
			try {	
				wdid = HydroBase_WaterDistrict.formWDID(wd, id);
				Message.printStatus(1, "", "TSID: '"
					+ tsid + "'");
				
				ts = __dmi.readTimeSeries(tsid,
					null, null, null, true, null);
				if (ts == null) {
					Message.printWarning(1, routine,
						"Unable to retrieve time "
						+ "series for TSID: \""
						+ tsid +"\"");
				}

				// Always add to be a place-holder in the TS
				// product...
				tsid = buildTSID(ts, rows[row]);
				props.set("Data 1." + (row + 1) + ".TSID=" 
					+ tsid);
				props.set("Data 1." + (row + 1) 
					+ ".SymbolStyle=Plus" );
				props.set("Data 1." + (row + 1) 
					+ ".SymbolSize=5" );
				tslist.add(ts);
			} 
			catch (Exception e) {
				Message.printWarning(2, routine,
					"Error retrieving time series.");
				Message.printWarning(2, routine, e);
			}
		}
		
		JGUIUtil.setWaitCursor(this, true);

		try {	
			TSProduct tsproduct = new TSProduct(props, 
				display_props);
			tsproduct.setTSList(tslist);
			new TSViewJFrame(tsproduct);
			JGUIUtil.setWaitCursor(this, false);
		} 
		catch (Exception e) {
			Message.printWarning(2, routine, e);
			Message.printWarning(1, routine, 
				"Error displaying well level time series.");
			JGUIUtil.setWaitCursor(this, false);
		}
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
Responds to window closing events; calls closeClicked().
@param event the WindowEvent that happened.
*/
public void windowClosing(WindowEvent event) {
	closeClicked();
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

// REVISIT (SAM - 2004-02-10)
// Heads up ... in TSTool, Well level time series will list using the usgs_id 
// or usbr_id from the unpermitted_wells table.  In actuality, the well ID may 
// be a WDID or the UGGS or the USBR id.  I am waiting on some changes from
// Doug Stenzel but we may need to change the well level display to show the
// additional information.  The problem may be handling the join so as to 
// return partial records if data are not in all tables.  Put in a REVISIT so
// we can search but don't do anything right now.
