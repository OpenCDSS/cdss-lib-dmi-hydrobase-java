//-----------------------------------------------------------------------------
// HydroBase_GUI_StationQuery - Station Query GUI
//-----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 10 Mar 1997  DLG, RTi        Created initial version.
// 06 Apr 1998  DLG, RTi        Commented out location and data types from the
//                              the MultiList object.
// 28 Apr 1998  CEN, RTi        Rearrange layout: remove filter checkboxes; 
//				add pulldown selections for data type, time
//				step, and auxiliary data type.  The logic
//				has changed so that there is more filtering
//				initially, so that the resulting list contains
//				more specific station data.  From there, 
//				multiple stations may be selected to be 
//				displayed in a graph form, summary, etc.  
//				Previously, one station only could be selected
//				and then the resulting list was dynamically
//				created on potential options.
// 04 May 1998  CEN, RTi        Change strings in display views; alphabetize
//				all choices; change label on list; add "start",
//				"end", "drain area", and "cont. area" columns
//				to multilist.
// 05 May 1998  CEN, RTi        added members for data type, modified data
//				type, and time step.  These are set whenever
//				a user selects "get data".  That way, we can
//				always know what was selected the last time
//				the station list was created.
// 04 Apr 1999	SAM, RTi	Add HBDMI to queries.
// 14 Apr 1999	SAM, RTi	Add query time message to output.  Take some
//				obsolete code for a different data type GUIs
//				so the code is simple.  Change the Display View
//				from a list to buttons.  Add warning when
//				summary cannot be created.
// 15 Apr 1999	SAM, RTi	Add frost dates.
// 02 Sep 1999	SAM, RTi	Fix bug where district choice was not getting
//				updated after preferences where changed.
// 10 Apr 2001	SAM, RTi	Change to RTi graphing tool.  Change GUI to
//				GUIUtil.  Change so daily precip is total, not
//				average.  Change so monthly mean/max/min
//				temperatures work.  Add finalize().  Change
//				some data flags to non-static to save memory.
// 2001-10-05	SAM, RTi	Enable new map interface.  Change so print and
//				export buttons are class members so they can be
//				enabled and disabled depending on the state of
//				the interface.  Add UTM X, Y to the table
//				output.
//				NO TIME RIGHT NOW BUT WHY ARE THERE
//				displayResults()AND
//				displayStationLocationResults()METHODS?  The
//				structure query does not have.  The methods
//				probably need to be combined.  Remove unused
//				code(old station data type checkboxes).
// 2001-12-13	SAM, RTi	Use updated GeoView classes that are named to
//				support Swing and NoSwing code.
// 2002-02-20	SAM, RTi	Replace TSViewGUI with TSViewJFrame.
// 2002-05-07	SAM, RTi	Fix - warning message when selecting on map
//				used wrong count.
// 2002-08-19	SAM, RTi	Change so a point click on the map does NOT
//				result in a query.  Update so the window is
//				automatically displayed if not visible when
//				a map select is made.
//-----------------------------------------------------------------------------
// 2003-03-17	J. Thomas Sapienza, RTi	Initial work on moving to SWING version.
// 2003-03-20	JTS, RTi		Post-snowstorm, work continues on SWING.
// 2003-03-21	JTS, RTi		Implemented __locationGUI.
// 2003-03-24	JTS, RTi		* Fixed status bar update and hourglass
//					  cursor issues.
//					* Renamed from HydroBase_GUI_Station to
//					  HydroBase_GUI_StationQuery.
// 2003-03-25	JTS, RTi		* Removed references to
//					  HydroBase_GUI_Util parent object.
// 2003-03-27	JTS, RTi		* Corrected error in database query that
//					  resulted in too many records being
//					  returned.
//					* Changed query to a non-distinct one.
//					* Corrected error that resulted in 
//					  duplicate data types appearing
// 2003-03-28	JTS, RTi		Title is now set with the program name
//					as well as the form name.
// 2003-04-04	JTS, RTi		Changed GUIUtil to JGUIUtil.
// 2003-04-07	JTS, RTi		Fixed when things should be enabled
//					or disabled based on how many records
//					are selected.
// 2003-05-15	JTS, RTi		* Opened up the GeoViewJPanel code.
//					* Added a CWRATMainJFrame parent (for
//					  isMapVisible()).
// 2003-05-30	JTS, RTi		Added code to copy from the table
// 2003-06-02	JTS, RTi		Added code so an hourglass displays when
//					sorting the table
// 2003-07-28	JTS, RTi		* Updated JWorksheet code to stop using
//					  deprecated methods.
//					* Removed old JWorksheet method of
//					  enabling copy/paste.
// 2003-09-23	JTS, RTi		Changed the export code to use 
//					the new export code in 
//					HydroBase_GUI_Util.
// 2003-12-02	JTS, RTi		* Data types are now queried from the
//					  database.
//					* Time steps are now queried from the
//					  database.
//					* Removed the data type modifier
//					  combo box.
//					* Time series are finally read from
//					  the database.
// 2004-01-07	JTS, RTi		* Corrected bug in label for 
//					  number of records reach. 
//					* Changed how the data types are 
//					  determined for a query.
//					* Corrected bug so that formatOutput()
//					  behaves like the original code.
//					* Removed getMeasType() method.
// 2004-01-21	JTS, RTi		Changed to use the new JWorksheet method
//					of displaying a row count column.
// 2004-02-20	SAM, RTi		* Some methods in HydroBase_GUI_Util
//					  were moved to HydroBase_Util.
// 2004-06-22	JTS, RTi		Table strings were moved from the 
//					DMI class to HydroBase_GUI_Util so they
//					were renamed in here.
// 2004-07-26	JTS, RTi		* Users no longer have to scroll the
//					  "Where" combo box.
//					* Cache the most-recently dragged time 
//					  series.
// 2004-07-30	JTS, RTi		Added support for the Precipitation
//					and Temperature GeoView layers.
// 2005-01-10	JTS, RTi		Converted to use InputFilters.
// 2005-02-09	JTS, RTi		* Removed getWhereClause().
//					* Removed getOrderByClause().
//					* Where and order by clauses are now 
//					  built within the 
//					  readStationGeolocMeasTypeList() 
//					  method called from this GUI.  That
//					  method will call an SQL query or
//					  stored procedure query as appropriate.
// 					* Separate table models and cell 
//					  renderers are used if a stored 
//					  procedure query was run.
//					* Removed order by combo boxes.
// 2005-02-11	JTS, RTi		* Converted code to work on either data
//					  objects returned from an SQL query or
//					  from a stored procedure.
//					* Removed lookupStation().
// 2005-04-28	JTS, RTi		Added all data members to finalize().
// 2005-05-09	JTS, RTi		Only uses HydroBase_StationView objects.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
// 2005-11-15	JTS, RTi		Option to query the entire state at once
//					added to the district combo box.
// 2007-02-08	SAM, RTi		Remove dependence on CWRAT.
//					Pass a JFrame to the constructor.
//					Use GeoViewUI to link to map interface.
//					Clean up code based on Eclipse feedback.
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import RTi.GIS.GeoView.GeoRecord;
import RTi.GIS.GeoView.GeoViewListener;

import RTi.GR.GRLimits;
import RTi.GR.GRPoint;
import RTi.GR.GRShape;

import RTi.GRTS.TSViewJFrame;

import RTi.TS.TS;
import RTi.TS.TSLimits;
import RTi.TS.TSUtil;

import RTi.Util.GUI.DragAndDropListener;
import RTi.Util.GUI.DragAndDropUtil;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.DragAndDropJWorksheet;
import RTi.Util.GUI.ReportJFrame;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleJButton;
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.StopWatch;

public class HydroBase_GUI_StationQuery 
extends JFrame 
implements ActionListener, GeoViewListener, ItemListener, KeyListener,
MouseListener, WindowListener, DragAndDropListener {

/**
Miscellaneous Strings.
*/
private final String	
	__HELP_STRING =      	"CWRAT.HydroBase_GUI_StationQuery",
	__REPORT_HELP_STRING = 	"CWRAT.StationSummary",
	__SUMMARY = 		"Time Series Summary",
	__GRAPH = 		"Time Series Graph",
	__TABLE = 		"Time Series Table",
	//__DTYP_EVAP = 		"Evaporation",
	__DTYP_FROSTD = 	"Frost Dates",
	__DTYP_TEMP = 		"Temp",
	__DTYP_NATFLOW = 	"Natural Flow",
	__DTYP_PTPX = 		"Precip",
	//__DTYP_RTH = 		"Height",
	//__DTYP_RTM =	 	"Misc.",
	//__DTYP_RTR =	 	"Rate",
	//__DTYP_RTV = 		"Volume",
	//__DTYP_SNOW =		 "Snow",
	//__DTYP_SNOWC = 		"Snow Course",
	//__DTYP_SOLAR = 		"Solar",
	__DTYP_STREAM = 	"Streamflow",
	//__DTYP_VP = 		"Vapor Pressure",
	//__DTYP_WIND = 		"Wind",
	__TIMES_DAILY = 	"Day",
	__TIMES_MONTHLY = 	"Month",
	__TIMES_ANNUAL = 	"Year",
	//__TIMES_REAL = 		"Real-time",
	//__MOD_TOTAL = 		"Total",
	//__MOD_MEAN = 		"Mean",
	//__MOD_AVG = 		"Average",
	//__MOD_RUN = 		"Run",
	//__MOD_MIN = 		"Minimum",
	//__MOD_MAX = 		"Maximum",		
	__BUTTON_CLOSE = 	"Close",
	__BUTTON_EXPORT = 	"Export",
	__BUTTON_GET_DATA = 	"Get Data",
	__BUTTON_HELP = 	"Help",
	__BUTTON_PRINT = 	"Print",
	__BUTTON_SELECT_ON_MAP ="Select On Map";

/**
Whether the query being run is one in which location from a GeoView needs
to be considered.
*/
private boolean __geoViewSelectQuery = false;

/**
GeoViewUI interface, for map interaction.
*/
private GeoViewUI __geoview_ui = null;

/**
Query limits for the map.
*/
private GRLimits __mapQueryLimits = null;

/**
Reference to DMI object.
*/
private HydroBaseDMI __dmi;

/**
The filter panel for the query options.
*/
private HydroBase_GUI_Station_InputFilter_JPanel __filterJPanel = null;

/**
Label for the JTable.
*/
private JLabel __tableJLabel;

/**
Status text field.
*/
private JTextField __statusJTextField;

/**
Table in which the data is displayed.
*/
private DragAndDropJWorksheet __worksheet;

/**
GUI Buttons
*/
private SimpleJButton 
	__tsGraphJButton,
	__tsSummaryJButton,
	__tsTableJButton,
	__printJButton,
	__exportJButton,
	__selectOnMapJButton;

/**
GUI combo boxes.
*/
private SimpleJComboBox 
	__dataTypeJComboBox,
	__timeStepJComboBox,
	__waterDistrictJComboBox;
/**
String for holding the selected data type.
*/
private String __dtypeJComboBoxString;

/**
String for holding the selected time step.
*/
private String __timestepJComboBoxString;

/**
Vector of results from the queries that are executed.
*/
private List __results = null;

/**
Create the interface and make visible.
@param dmi non-null and open HydroBaseDMI object
@param parent Calling JFrame, to allow window positioning.
@param geoview_ui GeoViewUI for map interaction.
@throws Exception if an error occurs
*/
public HydroBase_GUI_StationQuery(HydroBaseDMI dmi, JFrame parent,
		GeoViewUI geoview_ui )
throws Exception {
	this(dmi, parent, geoview_ui, true);
}

/**
Constructor.
@param dmi non-null and open HydroBaseDMI object
@param parent Calling JFrame, to allow window positioning.
@param geoview_ui GeoViewUI for map interaction.
@param isVisible Indicates if the GUI should be visible at creation.
@throws Exception if an error occurs.
*/
public HydroBase_GUI_StationQuery(HydroBaseDMI dmi, JFrame parent,
GeoViewUI geoview_ui, boolean isVisible) 
throws Exception {
	__geoview_ui = geoview_ui;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());	
	if (dmi == null) {
		throw new Exception ("Null dmi passed to "
			+ "HydroBase_GUI_StationQuery "
			+ "constructor.  DMI object must be open and "
			+ "connected before passing in.");
	}
	if (!dmi.isOpen()) {
		throw new Exception ("Unopened dmi passed to "
			+ "HydroBase_GUI_StationQuery "
			+ "constructor.  DMI object must be open and "
			+ "connected before passing in.");
	}
	
	__dmi = dmi;
	setupGUI(isVisible);
}

/**
This routine is responsible for handling ActionEvents for all 
gui objects which use an ActionEventListener.
@param evt ActionEvent object.
*/
public void actionPerformed(ActionEvent evt) {
	String s = evt.getActionCommand();
	String routine = "HydroBase_GUI_StationQuery.actionPerformed()";

	if (s.equals(__BUTTON_CLOSE)) { 
                closeClicked();
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
			if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
		 		// First format the output...
				List outputStrings = formatOutput(format);
	 			// Now export, letting the user decide 
				// the file...
				HydroBase_GUI_Util.export(this, eff[0], 
					outputStrings);
			}
			else {
                		char delim = HydroBase_GUI_Util
					.getDelimiterForFormat(format);	
				__worksheet.saveToFile(eff[0], "" + delim);
			}			
		} 
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}	
        }
        else if (s.equals(__BUTTON_GET_DATA)) {
		try {
	                submitQuery();
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading data.");
			Message.printWarning(2, routine, e);
		}
        }
        else if (s.equals(__BUTTON_HELP)) {
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
			List outputStrings = formatOutput(format);
	 		// Now print...
			PrintJGUI.print(this, outputStrings, 8);
		}
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}
        }
        else if (__BUTTON_SELECT_ON_MAP.equals(s)) {
		selectOnMap();
        }
        else if (s.equals(__SUMMARY) || s.equals(__GRAPH) || s.equals(__TABLE)){
		viewJComboBoxClicked(s);
        }
}

/**
Clear the worksheet and disable buttons.
*/
private void clearWorksheet() {
	__worksheet.clear();  
	// Disable displays until the user picks something...
	__tsGraphJButton.setEnabled(false);
	__tsSummaryJButton.setEnabled(false);
	__tsTableJButton.setEnabled(false);
	__selectOnMapJButton.setEnabled(false);
	__printJButton.setEnabled(false);
	__exportJButton.setEnabled(false);
}

/**
Closes the GUI.
*/
protected void closeClicked() {
        setVisible(false);
}

/**
Fill in appropriate selections for the time step
and data type modifier choices.
*/
private void dataTypeJComboBoxClicked() {
	// Clear the list since it is no longer compatible...
	clearWorksheet();

        // initialize variables
        String dtype  = parseDataType(__dataTypeJComboBox.getSelected().trim());

	// let's set the time step options and data type modifier options
	__timeStepJComboBox.removeAll();
	List v = HydroBase_Util.getTimeSeriesTimeSteps(__dmi, dtype,
		HydroBase_Util.DATA_TYPE_STATION_ALL);
	__timeStepJComboBox.setData(v);
	if (__timeStepJComboBox.getItemCount() > 0) {
		__timeStepJComboBox.select(0);	
	}
}

/**
Display the results of the query in the spreadsheet.  
*/
private void displayResults()
throws Exception {
	
	HydroBase_TableModel_StationView tm = 
		new HydroBase_TableModel_StationView(__results, __dmi);
	HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);

	tm.setDataType(parseDataType(
		__dataTypeJComboBox.getSelected().trim()));
	tm.setTimeStep(__timeStepJComboBox.getSelected().trim());
	__worksheet.setCellRenderer(cr);
	__worksheet.setModel(tm);	
	__worksheet.setColumnWidths(tm.getColumnWidths());
}

/**
Called just before the drag starts.  Used to get the time series to be dragged
and put it in the Transferable object for the drag.
*/
public boolean dragAboutToStart() {
	String routine = "HydroBase_GUI_StationQuery.dragAboutToStart";

	String station_id = null;
	String data_source = null;

	Object o = __worksheet.getRowData(__worksheet.getSelectedRow());
	HydroBase_StationView data 
		= (HydroBase_StationView)o;
	station_id = data.getStation_id();
	data_source = data.getData_source();

	DateTime date1 = null;
	DateTime date2 = null;

	String timestep = "";
	// Create a TS identifier string to query the
	// database...
       	timestep = __timestepJComboBoxString;

	String id = 
		  station_id + "." 
		+ data_source + "." 
		+ parseDataType(__dataTypeJComboBox.getSelected()) + "."
		+ timestep;
	id = id.trim();

	TS ts = null;
	Message.printStatus(1, routine,
		"Getting TS \"" + id + "\" for " + date1 + " to " + date2);

    	try {
	    	ts = __dmi.readTimeSeries(id, date1, 
			date2, null, true, null);
	}
	catch (Exception e) {
		Message.printWarning(1, routine,
			"Error reading time series from database.");
		Message.printWarning(2, routine, e);
	}

	if (ts != null) {
	/*
		if (ts instanceof MonthTS) {
			Message.printStatus(1, routine, "::MonthTS");
		}
		else if (ts instanceof DayTS) {
			Message.printStatus(1, routine, "::DayTS");
		}
		else if (ts instanceof HourTS) {
			Message.printStatus(1, routine, "::HourTS");
		}
		else if (ts instanceof MinuteTS) {
			Message.printStatus(1, routine, "::MinuteTS");
		}
		else if (ts instanceof IrregularTS) {
			Message.printStatus(1, routine, "::IrregularTS");
		}
		else {
			Message.printStatus(1, routine, 
				"Unrecognized TS type: " + ts.getClass());
		}
		*/
		__worksheet.setAlternateTransferable(ts);
	}
	else {
		Message.printStatus(1, "", "TS was null");
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
Enable layers in the map that are appropriate for the structure view.  If a map
is being used, disable the layers that are not a "Streamflow", "Climate",
or, "BaseLayer" AppLayerType.
*/
private void enableMapLayers() {
	if (__geoview_ui.isMapVisible()) {
		JGUIUtil.setWaitCursor(this, true);
		
		__statusJTextField.setText(
			"Updating map to show only station layers.");
		Message.printStatus(1, "",
			"Turning on station GIS layer types.");
			
		List enabledAppLayerTypes = new Vector();
		enabledAppLayerTypes.add("Climate");
		enabledAppLayerTypes.add("Streamflow");
		enabledAppLayerTypes.add("Temperature");
		enabledAppLayerTypes.add("Precipitation");
		
		// Base layers are always visible...
		enabledAppLayerTypes.add("BaseLayer");
		__geoview_ui.getGeoViewJPanel().enableAppLayerTypes(
			enabledAppLayerTypes, false);
		enabledAppLayerTypes = null;

		// We want this GUI to listen to the map...
		// SAMX do this in the constructor now
		//__parent.getGeoViewJPanel()
		//	.getGeoView().addGeoViewListener(this);
		__statusJTextField.setText(
			"Map shows base layers and station layers.  Ready.");
		ready();
	}
}

/**
Clean up for garbage collection.
*/
protected void finalize()
throws Throwable {
	__mapQueryLimits = null;
	__dmi = null;
	__filterJPanel = null;
	__tableJLabel = null;
	__statusJTextField = null;
	__worksheet = null;
	__tsGraphJButton = null;
	__tsSummaryJButton = null;
	__tsTableJButton = null;
	__printJButton = null;
	__exportJButton = null;
	__selectOnMapJButton = null;
	__dataTypeJComboBox = null;
	__timeStepJComboBox = null;
	__waterDistrictJComboBox = null;
	__dtypeJComboBoxString = null;
	__timestepJComboBoxString = null;
	__results = null;

	super.finalize();
}

/**
Responsible for formatting output.
@param format format delimiter flag defined in this class
@return ormatted Vector for exporting, printing, etc..
*/
private List formatOutput(int format) {
	List v = new Vector(50, 50);
        int numCols = __worksheet.getColumnCount();
        int numRows = __worksheet.getRowCount();
        String rowString;

        char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);
       
       	rowString = "";
       	for (int j = 0; j < numCols; j++) {
		rowString += __worksheet.getColumnName(j, true) + "" + delim;
	}
	v.add(rowString);
       
       	int selected = __worksheet.getSelectedRowCount();
       
       	String s = null;
	boolean useComma = false;
	if (delim == ',') {
		useComma = true;
	}
        for (int i = 0; i < numRows; i++) {
		if ((selected > 0 && __worksheet.rowIsSelected(i))
			|| selected == 0) {
	                rowString = "";
	                for (int j = 0; j < numCols; j++) {
				s = __worksheet.getValueAtAsString(i, j);
				if (useComma && s.indexOf(",") > -1) {
					s = "\"" + s + "\"";
				}
	                        rowString += s + delim;
	                }
	                v.add(rowString);
		}
        }
        return v;
}

/**
Handle the label redraw event from another GeoView (likely a ReferenceGeoView).
Do not do anything here because we assume that application code is setting
the labels.
@param record Feature being draw.
*/
public String geoViewGetLabel(GeoRecord record) {
	return null;
}

/**
Handle the mouse motion event from another GeoView (likely a ReferenceGeoView).
Does nothing.
@param devpt Coordinates of mouse in device coordinates(pixels).
@param datapt Coordinates of mouse in data coordinates.
*/
public void geoViewMouseMotion(GRPoint devpt, GRPoint datapt) {}

/**
Do nothing.  REVISIT - Should this do the same as a select?
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected Vector of selected GeoRecord.  Currently ignored.
*/
public void geoViewInfo(GRShape devlimits, GRShape datalimits,
		List selected) {}

public void geoViewInfo(GRPoint devlimits, GRPoint datalimits, 
		List selected) {}

public void geoViewInfo(GRLimits devlimits, GRLimits datalimits,
		List selected) {}

/**
If a selection is made from the map, query the database for region that was
selected.  It is assumed that this GUI is created in the main application
even if it is invisible.  This will ensure that the method is called as a
listener from the GeoView.
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected Vector of selected GeoRecord.  Currently ignored.
*/
public void geoViewSelect(GRShape devlimits, GRShape datalimits,
		List selected, boolean append) {
	String routine = "geoViewSelect";

	// Figure out which app layer types are selected.  If one that is
	// applicable to this GUI, execute a query...

	List appLayerTypes = __geoview_ui.getGeoViewJPanel(
		).getLegendJTree().getSelectedAppLayerTypes(true);
	int size = appLayerTypes.size();
	String app_layer_type;
	boolean view_needed = false;

	// only the very first layer is checked
	
	if (size == 0) {
		// no layers selected
		return;
	}
	
	app_layer_type = (String)appLayerTypes.get(0);

	String[] vals = null;
	
	vals = HydroBase_GUI_Util
		.getTimeSeriesDataTypeAndIntervalForAppLayerType(
		app_layer_type);

	if (vals == null) {
		return;
	}

	if (app_layer_type.equalsIgnoreCase("Streamflow")) {
		// Make sure the Streamflow data type is selected...
		if (!parseDataType(__dataTypeJComboBox.getSelected())
			.equals(vals[0])) {
			selectDataType(vals[0]);
			// Refresh other choices...
			dataTypeJComboBoxClicked();
			__timeStepJComboBox.select(vals[1]);
		}
		view_needed = true;
	}
	else if (app_layer_type.equalsIgnoreCase("Precipitation")) {
		if (!parseDataType(__dataTypeJComboBox.getSelected())
			.equals(vals[0])) {
			selectDataType(vals[0]);
			// Refresh other choices...
			dataTypeJComboBoxClicked();
			__timeStepJComboBox.select(vals[1]);
		}
		view_needed = true;
	}
	else if (app_layer_type.equalsIgnoreCase("Temperature")) {
		if (!parseDataType(__dataTypeJComboBox.getSelected())
			.equals(vals[0])) {
			selectDataType(vals[0]);
			// Refresh other choices ...
			dataTypeJComboBoxClicked();
			__timeStepJComboBox.select(vals[1]);
		}
		view_needed = true;
	}

	if (!view_needed) {
		// Just return with no action...
		return;
	}

	if (!isVisible()) {
		//return;
		// If not visible, set to visible if the selected map layers
		// apply to this window...
		setVisible(true);
	}

	// Now do the query...

	if (devlimits.type == GRShape.LIMITS) {
		__mapQueryLimits = (GRLimits)datalimits;
	}
	else if (devlimits.type == GRShape.POINT) {
		// Currently do not allow a point to query...
		//__mapQueryLimits = new GRLimits(
		//((GRPoint)datalimits).x,
		//((GRPoint)datalimits).y,
		//((GRPoint)datalimits).x,
		//((GRPoint)datalimits).y);
		return;
	}

	try {
		__geoViewSelectQuery = true;
		submitQuery();
		__geoViewSelectQuery = false;
		__mapQueryLimits = null;
	}
	catch (Exception e) {
		Message.printWarning(2, routine, "Error submitting query");
		Message.printWarning(2, routine, e);
	}
}

public void geoViewSelect(GRPoint devlimits, GRPoint datalimits, 
		List selected, boolean append) {
	geoViewSelect((GRShape)devlimits, (GRShape)datalimits, selected, 
		append);
}

public void geoViewSelect(GRLimits devlimits, GRLimits datalimits,
		List selected, boolean append) {
	geoViewSelect((GRShape)devlimits, (GRShape)datalimits, selected, 
		append);
}

/**
Handle the zoom event from the GeoView map interface.
This resets the data limits for this GeoView to those specified (if not
null) and redraws the GeoView.  In this class it does nothing.
@param devlimits Limits of zoom in device coordinates(pixels).
@param datalimits Limits of zoom in data coordinates.
*/
public void geoViewZoom(GRShape devlimits, GRShape datalimits) {}

public void geoViewZoom (GRLimits devlim, GRLimits datalim ) {}

/**
Returns a Vector with the visible App Layer Type.
@return a Vector with the visible App Layer Type.
*/
private List getVisibleAppLayerType() {
	List appLayerTypes = new Vector(2);
	if (	parseDataType(__dataTypeJComboBox.getSelected()).equals(
			__DTYP_STREAM) ||
		parseDataType(__dataTypeJComboBox.getSelected()).equals(
			__DTYP_NATFLOW)) {
		appLayerTypes.add("Streamflow");
	}
	else if (parseDataType(__dataTypeJComboBox.getSelected()).equals(
		__DTYP_TEMP)
		|| parseDataType(__dataTypeJComboBox.getSelected())
		.startsWith("Temp")) {
		appLayerTypes.add("Temperature");
	}
	else if (parseDataType(__dataTypeJComboBox.getSelected()).equals(
		__DTYP_PTPX)) {
		appLayerTypes.add("Precipitation");
	}
	else {	
		// For now assume climate(don't support natural flow, etc.)...
		appLayerTypes.add("Climate");
	}
	return appLayerTypes;
}

/**
Display a station summary.
@param tsVector Vector of time series to display.
*/
private void getSummary(List tsVector) {
	String  routine = "getSummary";

	if (tsVector == null) {
		return;
	}
	if (tsVector.size() < 1) {
		return;
	}

        __statusJTextField.setText("Please Wait...generating Summary...");

        PropList props = new PropList("Summary");
        props.set("Format", "Summary");
        props.set("CalendarType", "WaterYear");
        props.set("Format", "Summary");
	// Put this in to get the format that Ray wants...
        props.set("UseCommentsForHeader", "true");
        props.set("PrintHeader", "true");
        props.set("PrintComments", "true");
        props.set("PrintMinStats", "true");
        props.set("PrintMaxStats", "true");
        props.set("PrintMeanStats", "true");
        props.set("PrintNotes", "true");

        PropList reportProp = new PropList("ReportJFrame.props");
        reportProp.set("HelpKey=" + __REPORT_HELP_STRING);
        reportProp.set("TotalWidth=750");
        reportProp.set("TotalHeight=550"); 
        reportProp.set("Title=" + __SUMMARY);
        reportProp.set("DisplayFont=Courier");
        reportProp.set("DisplayStyle=" + Font.PLAIN);
        reportProp.set("DisplaySize=11");
        reportProp.set("PrintFont=Courier");
        reportProp.set("PrintStyle=" + Font.PLAIN);
        reportProp.set("PrintSize=7");
        reportProp.set("PageLength=100");

        // display the summary

        List summary = null;
	boolean frost = false;
	try {   
		// If frost dates, get the time series using special code.
		// Otherwise, use the generic code...
		TS ts = (TS)tsVector.get(0);
		if (ts == null) {
			return;
		}
		if (ts.getDataType().regionMatches(true,0,"Frost",0,5)) {
			// Frost date time series...
			frost = true;
		}
		if (frost) {
			// Summary uses dates from previous query...
			/*
			REVISIT (JTS - 2003-03-17)
			No HBFrostDatesYearTS in the new HydroBaseDMI
			HBFrostDatesYearTS fts =(HBFrostDatesYearTS)ts;
			summary = fts.formatOutput(tsVector,0,0);
			*/
		}
		else {	
			summary = TSUtil.formatOutput(tsVector, props);
		}
               	new ReportJFrame(summary, reportProp);
        }
	catch(Exception e) {
		Message.printWarning(1, routine,		
			"Error creating time series summary.");
        }
}

/**
Responds to itemStateChanged events.
@param evt the ItemEvent that happened.
*/
public void itemStateChanged(ItemEvent evt) {
        Object o = evt.getItemSelectable();
                
        if (o.equals(__dataTypeJComboBox)) {
                dataTypeJComboBoxClicked();
        }
        else if (o.equals(__timeStepJComboBox)) {
                timeStepJComboBoxClicked();
        }
}

/**
Responds to key pressed events.
@param event the KeyEvent that happened.
*/
public void keyPressed(KeyEvent event) {
	String routine = "HydroBase_GUI_StationQuery.keyPressed";
        int code = event.getKeyCode();
        
        if (code == KeyEvent.VK_ENTER) {
		try {
	                submitQuery();
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading data.");
			Message.printWarning(2, routine, e);
		}
        }
}

/**
Responds to the key released events.  Does nothing.
@param event the KeyEvent that happened.
*/
public void keyReleased(KeyEvent event) {}
/**
Responds to key typed events.  Does nothing.
@param event the KeyEvent that happened.
*/
public void keyTyped(KeyEvent event) {}

/**
Does nothing.
*/
public void mouseClicked(MouseEvent event) {}

/**
Does nothing.
*/
public void mouseEntered(MouseEvent event) {}

/**
Does nothing.
*/
public void mouseExited(MouseEvent event) {}

/**
Responds to mouse pressed events.
@param event the mouse event that happened.
*/
public void mousePressed(MouseEvent event) {
	Object ob = event.getComponent();
	
        if (ob.equals(__worksheet)) {
                int row = __worksheet.getSelectedRow();
		// Selected data so enable...
		if (row > -1) {
			// Frost dates only have a summary...
			if (parseDataType(__dataTypeJComboBox.getSelected())
				.equals(__DTYP_FROSTD)) {
				__tsSummaryJButton.setEnabled(true);
			}
			else {	
				__tsGraphJButton.setEnabled(true);
				__tsSummaryJButton.setEnabled(true);
				__tsTableJButton.setEnabled(true);
				if (__geoview_ui.isMapVisible()) {
					__selectOnMapJButton.setEnabled(true);
				}
				else {
					__selectOnMapJButton.setEnabled(false);
				}
			}
		}
        }
	else {	
		int numFilters = __filterJPanel.getNumFilterGroups();
		for (int i = 0; i < numFilters; i++) {
			if (event.getSource() == __filterJPanel.getInputFilter(
				i).getInputComponent()) {
				HydroBase_GUI_Util.buildLocation(this,
					(JTextField)event.getSource());
			}
		}
	}	
}

/**
Responds to mouse released events.  Does nothing.
@param event the mouse event that happened.
*/
public void mouseReleased(MouseEvent event) {
	int rows = __worksheet.getSelectedRowCount();
	if (rows > 0) {
		__tsGraphJButton.setEnabled(true);
		__tsSummaryJButton.setEnabled(true);
		__tsTableJButton.setEnabled(true);
		if (__geoview_ui.isMapVisible()) {
			__selectOnMapJButton.setEnabled(true);
		}
		else {
			__selectOnMapJButton.setEnabled(false);
		}
	}
	else {
		__tsGraphJButton.setEnabled(false);
		__tsSummaryJButton.setEnabled(false);
		__tsTableJButton.setEnabled(false);		
		__selectOnMapJButton.setEnabled(false);
	}	

}

/**
Parses out the data type from a string.  A simple utility method used throughout
this class.
@param type the data string
@return the data type from the string
*/
private String parseDataType(String type) {
	return StringUtil.getToken(type, "-", 0, 1).trim();
}

/**
Readies the GUI for user interaction.
*/
private void ready() {
	if (__worksheet.getRowCount() > 0) {
		__statusJTextField.setText("Select one or more stations "
			+ "and then press Time Series buttons or select "
			+ "other choices.");
	}
	else {	
		__statusJTextField.setText("Select a location, data type, etc. "
			+ "and then press Get Data.");
	}
	JGUIUtil.setWaitCursor(this, false);
}

/**
Selects a row from the data type combo box based on the data type that should
be selected.
@param dataType the data type that should be selected.
*/
private void selectDataType(String dataType) {
	int count = __dataTypeJComboBox.getItemCount();

	String item = null;
	for (int i = 0; i <count; i++) {
		item = __dataTypeJComboBox.getItem(i);
		if (item.endsWith(dataType)) {
			__dataTypeJComboBox.select(item);
			return;
		}
	}
}

/**
Sets up the GUI.
@param isVisible, whether the GUI is visible at creation.
*/
private void setupGUI(boolean isVisible) {
	Message.setTopLevel(this);

        addWindowListener(this);
        addKeyListener(this);
        
        // objects used throughout the GUI layout
        Insets insetsNNNR = new Insets(0,0,0,7);
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
        
        JGUIUtil.addComponent(northWJPanel, new JLabel("Query Options:"), 
                0, 0, 1, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
        
        JGUIUtil.addComponent(northWJPanel, new JLabel(
		"Div/Dist:"), 
                0, 1, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
        
        __waterDistrictJComboBox = new SimpleJComboBox();
        JGUIUtil.addComponent(northWJPanel, __waterDistrictJComboBox, 
                1, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
	JGUIUtil.addComponent(northWJPanel, new JLabel("Data Type:"), 
		0, 2, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__dataTypeJComboBox = new SimpleJComboBox();
	List v = HydroBase_Util.getTimeSeriesDataTypes(__dmi,
		HydroBase_Util.DATA_TYPE_STATION_ALL, true);
	__dataTypeJComboBox.setData(v);
	__dataTypeJComboBox.select(HydroBase_Util.getDefaultTimeSeriesDataType(
		__dmi, true));
	__dataTypeJComboBox.addItemListener(this);
	JGUIUtil.addComponent(northWJPanel, __dataTypeJComboBox, 
		1, 2, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel, new JLabel("Time Step:"), 
		0, 3, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
        
        __timeStepJComboBox = new SimpleJComboBox();
        __timeStepJComboBox.addItemListener(this);
        JGUIUtil.addComponent(northWJPanel, __timeStepJComboBox, 
		1, 3, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        SimpleJButton get = new SimpleJButton(__BUTTON_GET_DATA, this);
	get.setToolTipText("Read data from database.");
        JGUIUtil.addComponent(northWJPanel, get, 
		5, 4, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH);

	__filterJPanel = new HydroBase_GUI_Station_InputFilter_JPanel(__dmi);
	__filterJPanel.addEventListeners(this);
        JGUIUtil.addComponent(northWJPanel, __filterJPanel,
		0, 4, 4, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(gbl);
        getContentPane().add("Center", centerJPanel);        
        
        __tableJLabel = new JLabel(HydroBase_GUI_Util.LIST_LABEL);
        JGUIUtil.addComponent(centerJPanel, __tableJLabel, 1, 1, 
                7, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
	PropList p = new PropList("HydroBase_GUI_StationQuery"
		+ ".DragAndDropJWorksheet");

	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.ShowPopupMenu=true");

	JScrollWorksheet jsw = new JScrollWorksheet(0, 0, p, true);
	__worksheet = (DragAndDropJWorksheet)jsw.getJWorksheet();
//	__worksheet = new DragAndDropJWorksheet(0, 0, p);
	DragAndDropUtil.addDragAndDropListener(__worksheet, this);
	__worksheet.setHourglassJFrame(this);
	__worksheet.addMouseListener(this);
//	JGUIUtil.addComponent(centerJPanel, jsw,
	JGUIUtil.addComponent(centerJPanel, new JScrollPane(__worksheet),
		1, 2, 7, 3, 1, 1, insetsNLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);
        
        //Bottom JPanel(Consist of three more JPanels)
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);
        
        // Bottom: North JPanel
        JPanel bottomNorthJPanel = new JPanel();
        bottomNorthJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("North", bottomNorthJPanel);
        
        JPanel displayJPanel = new JPanel();
        displayJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomNorthJPanel.add(displayJPanel);

	__tsGraphJButton = new SimpleJButton(__GRAPH, __GRAPH, this);
	__tsGraphJButton.setToolTipText("Graph selected time series.");
	displayJPanel.add(__tsGraphJButton);
	__tsSummaryJButton = new SimpleJButton(__SUMMARY, __SUMMARY, this);
	__tsSummaryJButton.setToolTipText("Display summaries for selected "
		+ "time series.");
	displayJPanel.add(__tsSummaryJButton);
	__tsTableJButton = new SimpleJButton(__TABLE, __TABLE, this);
	__tsTableJButton.setToolTipText("Display selected time series in "
		+ "tabular form.");
	displayJPanel.add(__tsTableJButton);
        
        // Bottom Center JPanel
        JPanel bottomCJPanel = new JPanel();
        bottomCJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("Center", bottomCJPanel);
 
        __selectOnMapJButton = new SimpleJButton(__BUTTON_SELECT_ON_MAP,
		__BUTTON_SELECT_ON_MAP, this);
	__selectOnMapJButton.setToolTipText("Select selected stations on "
		+ "GIS map.");
        __selectOnMapJButton.setEnabled(false);	// Until data selected
        bottomCJPanel.add(__selectOnMapJButton);

	__printJButton = new SimpleJButton(__BUTTON_PRINT, this);
	__printJButton.setToolTipText("Print selected data.  If no records "
		+ " are selected, all data are printed.");
	__printJButton.setEnabled(false);
	bottomCJPanel.add(__printJButton);
        
	__exportJButton = new SimpleJButton(__BUTTON_EXPORT, this);
	__exportJButton.setToolTipText("Export selected data to a file.  "
		+ "If no records are selected, all data are exported.");
	__exportJButton.setEnabled(false);
        bottomCJPanel.add(__exportJButton);
        
        SimpleJButton close = new SimpleJButton(__BUTTON_CLOSE, this);
	close.setToolTipText("Close form.");
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
	__dataTypeJComboBox.select(__DTYP_STREAM); // default upon create
	dataTypeJComboBoxClicked();

        // Frame settings
        setTitle("Station Data Query");
	if (	(JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( "Station Data - Query" );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() +
		" - Station Data - Query" );
	}		
	// set the items for from user preferences for division/district
	// Now set this in setVisible...
        pack();

	// Disable displays until the user picks something...
	clearWorksheet();

        setSize(700, 500);
        JGUIUtil.center(this);

	// We want this GUI to listen to the map.  If no map is used no
	// listener calls will be generated...
	__geoview_ui.getGeoViewJPanel().getGeoView().addGeoViewListener(this);

        setVisible(isVisible);
}

/**
Show/hide the frame, resetting the choices.
@param state true if showing the frame, false if hiding it.
*/
public void setVisible(boolean state) {
	String routine = "HydroBase_GUI_StationQuery.setVisible";
	if (state) {
                clearWorksheet();
                __tableJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);
// REVISIT (JTS - 2005-01-10)
// select station id for the filter
		__dataTypeJComboBox.select(__DTYP_STREAM);
		dataTypeJComboBoxClicked();

		// set up current choices for data type and time step
        	__dtypeJComboBoxString = 
			parseDataType(__dataTypeJComboBox.getSelected().trim());
        	__timestepJComboBoxString = 
			__timeStepJComboBox.getSelected();
		if (__timestepJComboBoxString != null) {
			__timestepJComboBoxString = 
				__timestepJComboBoxString.trim();
		}
                ready();
		try {
		HydroBase_GUI_Util.setWaterDistrictJComboBox(__dmi, 
			__waterDistrictJComboBox, null, true, false, true);
		}
		catch (Exception e) {
			Message.printWarning (2, routine, e);
		}		
		if (__worksheet.getRowCount()> 0) {
			__printJButton.setEnabled(true);
			__exportJButton.setEnabled(true);
		}
		else {	
			__printJButton.setEnabled(false);
			__exportJButton.setEnabled(false);
		}
		if (__geoview_ui.isMapVisible() && (__worksheet.getRowCount() > 0) 
			&& __geoview_ui.getGeoViewJPanel().hasAppLayerType(
		    	getVisibleAppLayerType())) {
        		__selectOnMapJButton.setEnabled(true);
		}
		else {	
			__selectOnMapJButton.setEnabled(false);
		}
		enableMapLayers();
        }
        super.setVisible(state);
}

/**
Select the items in the list on the map.  This is done by putting together a
Vector of String, each of which has "wd,id".  These field names are set in the
GeoView Project as AppJoinField="wd,id".
*/
private void selectOnMap() {
	int size = __worksheet.getRowCount();
	List idlist = new Vector(size);
        __statusJTextField.setText(
		"Selecting and zooming to stations on map.  Please wait...");
	JGUIUtil.setWaitCursor(this, true);
	int rows[] = __worksheet.getSelectedRows();
	HydroBase_StationView view = null;

	boolean all = false;
	if (rows == null || rows.length == 0) {
		all = true;
	}
	else {	
		size = rows.length;
	}

	int row = 0;

	for (int i = 0; i < size; i++) {
		if (all) {
			row = i;
		}
		else {
			row = rows[i];
		}
		
		view = (HydroBase_StationView)
			__worksheet.getRowData(row);
		idlist.add(view.getStation_id());
	}
	// Select the features, specifying the AppLayerType corresponding to the
	// currently selected data type, and zoom to the selected shapes...
	List matching_features =__geoview_ui.getGeoViewJPanel().selectAppFeatures(
			getVisibleAppLayerType(),
			idlist, true, .05, .05);
	int matches = 0;
	if (matching_features != null) {
		matches = matching_features.size();
	}
	if (matches == 0) {
		Message.printWarning(1,"HydroBase_GUI_StationQuery.selectOnMap",
		"No matching features were found in map data.\n" +
		"This may be because the location data are incomplete or\n" +
		"because no suitable GIS data layers are being used.");
	}
	else if (matches != idlist.size()) {
		Message.printWarning(1,"HydroBase_GUI_StationQuery.selectOnMap",
		"" + matches + " matching features out of " + idlist.size()+
		" records were found in map data.\n" +
		"This may be because of incomplete location data.");
	}
        __statusJTextField.setText(
	"Map is zoomed to selected stations.  Ready.");
	JGUIUtil.setWaitCursor(this, false);
	idlist = null;
}

/**
Submits a query and display the results.
*/
private void submitQuery() 
throws Exception {
        // initialize variables
        String routine = "HydroBase_GUI_StationQuery.submitQuery()";
        __tableJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);

	// set these when user clicks on getDataClicked because these
	// are the appropriate choices for whatever is displayed then, in
	// the station list.  Otherwise, the user could select other data
	// types, for instance, and then select a station without selecting
	// "get data" and the previous values for data would be used.
        __dtypeJComboBoxString = 
		parseDataType(__dataTypeJComboBox.getSelected().trim());
        __timestepJComboBoxString = __timeStepJComboBox.getSelected();
	if (__timestepJComboBoxString != null) {
		__timestepJComboBoxString = 
			__timestepJComboBoxString.trim();
	}
	else {
		new ResponseJDialog(this, "No time step available",
			"The data type for which to query has no associated "
			+ "time steps.  The query will not be executed.",
			ResponseJDialog.OK).response();
		return;
	}

	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Retrieving data...");

	// the following is not really necessary, because the filter currently
	// accepts no numeric data, but it is good to have in for the future.
	if (!__filterJPanel.checkInput(true)) {
		ready();
		return;
	}

	// set meas fields
	String measType = __dtypeJComboBoxString;

	// Make the structure window go to the top so that it is obvious what is going on.
	setVisible(true);
	toFront();

        String status = "Please Wait... Retrieving Data";
        Message.printStatus(10, routine, status);
        __statusJTextField.setText(status);

	StopWatch sw = new StopWatch();
	try {
	       String [] hbMt = HydroBase_Util.convertToHydroBaseMeasType(
                        measType, __timestepJComboBoxString);
        	String meas_type = hbMt[0];
        	String vax_field = hbMt[1];
        	String time_step = hbMt[2];

		sw.start();
		List v = __dmi.readStationGeolocMeasTypeList(
				__filterJPanel, 
				__dmi.getWaterDistrictWhereClause(
					__waterDistrictJComboBox, 
					HydroBase_GUI_Util._GEOLOC_TABLE_NAME,
					true, true),	
				__mapQueryLimits, meas_type, time_step, 
				vax_field, null, null, false);
		__results = v;
		displayResults();
		sw.stop();			
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading station geoloc "
			+ "meas type data.");
		Message.printWarning (2, routine, e);
	}

        int displayed = __worksheet.getRowCount();
	String plural = "";
	if (displayed != 1) {
		plural = "s";
	}

        status = "" + __results.size() + " record" + plural + " returned in " 
		+ sw.getSeconds() + " seconds";
	if (__geoViewSelectQuery) {
		status += ", queried using map coordinates.";
		__geoViewSelectQuery = false;
	}
	else {
		status += ".";
	}

        __statusJTextField.setText(status);
	
        // set the record display       
	plural = "s";
	if (displayed == 1) {
		plural = "";
	}
        __tableJLabel.setText("Stations for selected data type"
		+ " (" + displayed + " station" + plural + " returned in " 
                + sw.getSeconds() + " seconds):");		

        ready();
                                                                                
	if (displayed == 0) {
		__tsGraphJButton.setEnabled(false);
		__tsSummaryJButton.setEnabled(false);
		__tsTableJButton.setEnabled(false);
		__selectOnMapJButton.setEnabled(false);
		__printJButton.setEnabled(false);
		__exportJButton.setEnabled(false);
	}
	else {
		__tsGraphJButton.setEnabled(false);
		__tsSummaryJButton.setEnabled(false);
		__tsTableJButton.setEnabled(false);
		__selectOnMapJButton.setEnabled(false);

		__printJButton.setEnabled(true);
		__exportJButton.setEnabled(true);
	}		
                        
        ready();
}

/**
Fills in the appropriate selections for the data type modified choices.
*/
private void timeStepJComboBoxClicked() {
	// Clear the list since it is no longer compatible...
	clearWorksheet();
}

/**
This routine displays the desired view for the selected station if
data is present in the MuiltiList object.  This used to be triggered by an
item state event from a list of display views but now the button label is
passed in.
@param viewJComboBox String corresponding to button that was pressed.
*/
private void viewJComboBoxClicked(String viewJComboBox) {
	int dl = 20;	// Debug level for this routine,

        // initialize variables
	String routine = "HydroBase_GUI_StationQuery.displayInfo()";
	PropList props = new PropList("TSView.props");
	props.set("HelpKey=" + __HELP_STRING);
	props.set("TotalWidth=500");
	props.set("TotalHeight=560");
	props.set("TotalGraphWidth=300");
	props.set("TotalGraphHeight=400");
	props.set("TitleString=");
	props.set("XAxisJLabelString=Date");
	props.set("ExtendedLegend=true");
	props.set("TSPerPlot=10");
	props.set("DisplayStationsCombined", "true");
   
        String display  = viewJComboBox;
        List selectedResults = new Vector(10, 10);
        int[] rows = __worksheet.getSelectedRows();
 
 	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Please Wait... Displaying View");

        // nothing has been selected. post warning and return.
        if (rows.length == 0) {
                Message.printWarning(1, routine, "Select a station from the"
                        + " list before displaying additional information.");
                ready();
                return;
        }

	// collect all selected rows into a new vector, selectedResults

	for (int i = 0; i < rows.length; i++) {
		selectedResults.add(__worksheet.getRowData(rows[i]));
	}
	// Retrieve user specified start/end dates for display

	TSLimits limits = null;
	if (display.equals(__GRAPH) || display.equals(__TABLE) 
	    || display.equals(__SUMMARY)) {
		TS ts;
		PropList periodProps = new PropList(
			"DateTimeSelectPeriodJDialog.props");
		periodProps.set("PreferredState", "Maximum");
		// Set the date precision based on the time step seleted in the
		// GUI...
        	String tstep  = __timeStepJComboBox.getSelected();
		if (tstep != null) {
			tstep = tstep.trim();
		}
		if (tstep.equals(__TIMES_MONTHLY)) {
			periodProps.set("DatePrecision", "Month");
		}
		else if (tstep.equals(__TIMES_DAILY)) {
			periodProps.set("DatePrecision", "Day");
		}
		else if (tstep.equals(__TIMES_ANNUAL)) {
			periodProps.set("DatePrecision", "Year");
		}
		periodProps.set("DateFormat", "Y2K");
		List tsV = new Vector(selectedResults.size(), 1);
		String alias;
		String location;
		for (int i = 0; i < selectedResults.size(); i++) {
			HydroBase_StationView data = 
				(HydroBase_StationView)
				selectedResults.get(i);

			ts = new TS();
			if (data.getStation_id().length() == 0) {
				// Identifier is missing, so use the
				// abbreviation...
				location = data.getAbbrev();
				alias = location;
			}
			else {	
				location = "" + data.getStation_id();
				alias = data.getAbbrev();
			}
			ts.setLocation(location);
			ts.setAlias(alias);
			ts.setDescription(data.getStation_name());
			DateTime date1 = new DateTime(
				DateTime.PRECISION_YEAR);
			date1.setYear(data.getStart_year());
			date1.setMonth(1);
			date1.setDay(1);
			ts.setDate1(date1); 
			DateTime date2 = new DateTime(	
				DateTime.PRECISION_YEAR);
			date2.setYear(data.getEnd_year());
			date2.setMonth(12);
			date2.setDay(31);
			ts.setDate2(date2); 
			tsV.add(ts);
		}

		// Modal dialog...
		DateTimeSelectPeriodJDialog selector = 
			new DateTimeSelectPeriodJDialog(this, tsV, periodProps);
		limits = selector.getLimits();
		if (limits == null) {
			// Cancelled...
			ready();
			return;
		}
		boolean isMaxPeriod = selector.isMaxPeriodSelected();

		DateTime date1 = null;
		DateTime date2 = null;
		if (isMaxPeriod) {
			// Just get all the data but don't specifically set
			// the output period (otherwise performance may be
			// slower)...
			date1 = null;
			date2 = null;
		}
		else {	
			date1 = limits.getDate1();
			date2 = limits.getDate2();
		}
		tsV.clear();
		String timestep = "";
		String id = null;
		for (int i = 0; i < selectedResults.size(); i++) {
			HydroBase_StationView data = 
				(HydroBase_StationView)
				selectedResults.get(i);
        		timestep = __timestepJComboBoxString;

			id = data.getStation_id() + "."
				+ data.getData_source() + "." 
				+ parseDataType(__dataTypeJComboBox
				.getSelected()) + "."
				+ timestep;
			id = id.trim();

			ts = null;
			if (Message.isDebugOn) {
				Message.printDebug(dl, routine,
					"Getting TS \"" + id
					+ "\" for " + date1 + " to " + date2);
			}

		    	try {
			    	ts = __dmi.readTimeSeries(id, date1, 
					date2, null, true, null);
			}
			catch (Exception e) {
				Message.printWarning(1, routine,
					"Error reading time series "
					+ "#" + (i + 1) + " from "
					+ "database.");
				Message.printWarning(2, routine, e);
			}

			if (ts != null) {
				tsV.add(ts);
			}
		}
		if (tsV.size() == 0) {
			ready();
			return;
		}
		props.set("DataUnits",((TS)tsV.get(0)).getDataUnits());
		props.set("YAxisJLabelString", 
			((TS)tsV.get(0)).getDataUnits());
		try {	
			// Set properties common to all views...
			PropList tsview_props = new PropList("tsview");
			tsview_props.set("CalendarType", "WaterYear");
			tsview_props.set("TotalWidth", "600");
			tsview_props.set("TotalHeight", "400");
			tsview_props.set("DisplayFont", "Courier");
			tsview_props.set("DisplaySize", "11");
			tsview_props.set("PrintFont", "Courier");
			tsview_props.set("PrintSize", "7");
			tsview_props.set("PageLength", "100");
        		String dtype = parseDataType(
				__dataTypeJComboBox.getSelected().trim());
			String ttstep = __timeStepJComboBox.getSelected();
			if (ttstep != null) {
				ttstep = ttstep.trim();
			}
			String title = dtype + " (" + ttstep + ")";
			tsview_props.set("TitleString", title);
			tsview_props.set("TSViewTitleString", title);
			tsview_props.set("UseCommentsForHeader", "true");
			// Now display the correct initial view...
			if (display.equals(__GRAPH)) {
				tsview_props.set("InitialView", "Graph");
				new TSViewJFrame(tsV, tsview_props);
			}
			else if (display.equals(__TABLE)) {
				// If daily data and more than one time series
				// print a warning...
				boolean doit = true;
				if (doit) {
					tsview_props.set("InitialView",
						"Table");
					new TSViewJFrame(tsV, tsview_props);
				}
			}
        		else if (display.equals(__SUMMARY)) {
				if (dtype.equals(__DTYP_FROSTD)) {
                			getSummary(tsV);
				}
				else {	
					tsview_props.set("InitialView",
						"Summary");
					tsview_props.set("TotalWidth", 
						"1000");
					new TSViewJFrame(tsV, tsview_props);
				}
			}
			// Clean up...
			props = null;
			tsview_props = null;
		}
		catch(Exception e) {
			Message.printWarning(1, "HydroBase_GUI_StationQuery",
			"Error displaying data.");
		}
	}

        ready();
}

/**
Responds to the window closing event.
@param evt the window event that happened.
*/
public void windowClosing(WindowEvent evt) {
        closeClicked();
}

/**
Responds to the window activated event. Does nothing.
@param evt the window event that happened.
*/
public void windowActivated(WindowEvent evt) {}

/**
Responds to the window closed event.  Does nothing.
@param evt the window event that happened.
*/
public void windowClosed(WindowEvent evt) {}

/**
Responds to the window deactivated event.  Does nothing.
@param evt the window event that happened.
*/
public void windowDeactivated(WindowEvent evt) {}

/**
Responds to window deiconified events.  Does nothing.
@param evt the window event that happened.
*/
public void windowDeiconified(WindowEvent evt) {}

/**
Responds to window opened events.  Does nothing.
@param evt the window event that happened.
*/
public void windowOpened(WindowEvent evt) {}

/**
Responds to window iconified events.  Does nothing.
@param evt the window event that happened.
*/
public void windowIconified(WindowEvent evt) {}

}

// REVISIT (SAM -- 2005-01-12)
// with the ability to sort the column headings, I wonder if we even need
// the sort options.  We will bring this up to the state along with other
// issues.  
