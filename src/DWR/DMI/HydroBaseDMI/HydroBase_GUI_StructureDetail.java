// HydroBase_GUI_StructureDetail - This GUI is responsible for displaying available
// time series and periods for structure historic data.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2025 Colorado Department of Natural Resources

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

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import RTi.DMI.DMIUtil;

import RTi.GRTS.TSViewJFrame;

import RTi.TS.DayTS;
import RTi.TS.HourTS;
import RTi.TS.IrregularTS;
import RTi.TS.MinuteTS;
import RTi.TS.MonthTS;
import RTi.TS.TS;
import RTi.TS.TSData;
import RTi.TS.TSLimits;
import RTi.TS.TSUtil;

import RTi.Util.GUI.DragAndDropSimpleJList;
import RTi.Util.GUI.DragAndDropListener;
import RTi.Util.GUI.DragAndDropUtil;
import RTi.Util.GUI.JGUIUtil; 
import RTi.Util.GUI.ReportJFrame;
import RTi.Util.GUI.ResponseJDialog; 
import RTi.Util.GUI.SimpleJButton; 

import RTi.Util.IO.PropList;

import RTi.Util.Math.MathUtil;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.DateTimeBuilderJDialog;
import RTi.Util.Time.TimeUtil;
import RTi.Util.Time.YearType;

/**
Display a list of diversion time series for the structure.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_StructureDetail 
extends JFrame 
implements ActionListener, DragAndDropListener, ListSelectionListener,
MouseListener, WindowListener {

/**
The name of the class.
*/
public final static String CLASS = "HydroBase_GUI_StructureDetail";

/**
Various flags.
*/
private final int 
	__MONTHLY_WC = 		0,
	__DAILY_WC = 		1,
	__TOTAL__MONTHLY = 	4,
	__TOTAL_DAILY = 	5,
	__BOTH_LISTS = 		0,
	__MONTH_LIST =		1,
	__DAY_LIST =		2;
  
/**
Labels.
*/
private final String     
	__BUTTON_DIVERSION_REPORT =	"Diversion Report",
        __BUTTON_GRAPH =		"Graph",
        __BUTTON_TABLE =		"Table",
	__BUTTON_SET_PERIOD = 		"Set Period",
        __BUTTON_SUMMARY = 		"Summary",
	__BUTTON_CLOSE = 		"Close",
	__BUTTON_ORIGINAL_PERIOD = 	"Original Period",
	__BUTTON_HELP = 		"Help",
        __ANNUAL = 			"Annual",
        __MONTH = 			"Month",
        __MONTHLY = 			"Monthly",
        __DAY = 			"Day",
        __DAILY = 			"Daily";			

/**
Used in calculating the stats for the annual water diversion report.
*/
private double 
	__annualSFD = 0.0,
	__annualAF = 0.0;

/**
Lists for displaying the time series lists.
*/
private DragAndDropSimpleJList 
	__dayJList,
	__monthJList;

/**
The last List that was clicked on.
*/
private DragAndDropSimpleJList __lastJList;
	
/**
The dmi used to communicate with the database.
*/
private HydroBaseDMI __dmi;

/**
Structure view for which to display data in the gui.
*/
private HydroBase_StructureView __structureView;

/**
Used in calculating the stats for the annual water diversion report.
*/
private int __annualDays = 0;

/**
Textfields for holding gui data.
*/
private JTextField
	__divJTextField,
	__fromJTextField,
	__idJTextField,
	__nameJTextField,
	__statusJTextField,
	__toJTextField,
	__wdJTextField;

/**
Prop lists.
*/
private PropList 
	__dateProps,
	__prop;
			
/**
Button for setting the period to the original period.
*/
private SimpleJButton __originalJButton;

/**
GUI buttons.
*/
private SimpleJButton 
	__graphJButton = null,
	__reportJButton = null,
	__summaryJButton = null,
	__tableJButton = null;

/**
Lists to hold internal data.
*/
private List<HydroBase_StructMeasTypeView> 
	__allMeasTypes,
	__measType;

/**
Constructor.
@param dmi the dmi with which data will be read from the database.
@param structureView the HydroBase_StructureView object for which to 
show data.
*/
public HydroBase_GUI_StructureDetail(HydroBaseDMI dmi, 
HydroBase_StructureView structureView) {
        __dmi = dmi;
        __structureView = structureView;

	initialize();
}

/**
Responds to actionPerformed events.
@param evt the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent evt) {
	String command = evt.getActionCommand().trim();
        
	if (command.equals(__BUTTON_DIVERSION_REPORT)) {
		List<TS> tsList = getTSVector(__BUTTON_DIVERSION_REPORT);
		if (tsList == null) {
			return;
		}
		
		int size = tsList.size();
		List<List<String>> results = new ArrayList<List<String>>();
		for (int i = 0; i < size; i++) {
			try {
				results.add(createDiversionReport((DayTS)tsList.get(i)));
			}
			catch (Exception e) {
				Message.printWarning(2, "", "Could not create diversion report.");
				Message.printWarning(2, "", e);
			}
		}

		size = results.size();

		int size2 = 0;
		List<String> report = new ArrayList<String>();
		List<String> v = null;
		for (int i = 0; i < size; i++) { // For each time series
			v = results.get(i);
			size2 = v.size();
			for (int j = 0; j < size2; j++) {
				report.add(v.get(j));
			}
		}

		PropList props = new PropList ( "" );
		props.set ( "TotalHeight=600" );
		props.set ( "TotalWidth=1000" );
		String title = HydroBase_WaterDistrict.formWDID(
				__structureView.getWD(),
				__structureView.getID()) +
				" - Annual Water Diversion Report";
		props.set ( "Title=" + title );
		new ReportJFrame(report, props);
	}
        if (command.equals(__BUTTON_GRAPH)) {
                getData(__BUTTON_GRAPH);
        }
        else if (command.equals(__BUTTON_TABLE)) {
                getData(__BUTTON_TABLE);
        }
        else if (command.equals(__BUTTON_SUMMARY)) {
                getData(__BUTTON_SUMMARY);
        }
        else if (command.equals(__BUTTON_CLOSE)) {
                closeClicked();
        }       
        else if (command.equals(__BUTTON_SET_PERIOD)) {
		setPeriod_clicked();
        }                                                       
        else if (command.equals(__BUTTON_ORIGINAL_PERIOD)) {
                setOriginalPeriodText();        
                __originalJButton.setEnabled(false);
        }
}

/**
Add item to a JList for time series data.
@param data HydroBase_StructMeasTypeView object.
@param label JLabel string (e.g., "Total Diversion").  If given as null,
data.getIdentifier() will be used (e.g., for SFUT).
@param list AWT JList object.
*/
private void addListItem(HydroBase_StructMeasTypeView data, String label, 
DragAndDropSimpleJList list) {
	String routine = CLASS + ".addListItem";

        // set the HydroBase_StructMeasTypeView object information
        HydroBase_StructMeasTypeView dataClone = 
		new HydroBase_StructMeasTypeView(data);

        String id = getTSIdentifier(data);

        dataClone.setTSIdentifier(id);

	TS ts = null;
	try {	// Read the time series for the HydroBase_StructMeasTypeView
		// (header only, no data)...
        	ts = __dmi.readTimeSeries(id, null, null, null, false, null);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, 
			"Error reading time series from database.");
		Message.printWarning(2, routine, e);
		return;
	}

	if (ts == null) {
		Message.printStatus(1, "", "No time series was read!");
		return;
	}

        dataClone.setTimeSeries(ts);

        __measType.add(dataClone);

	StringBuffer buffer = new StringBuffer();
        buffer.append(__measType.size() + ")");

	if (label == null) {
		buffer.append(" " 
			+ data.getMeas_type() + "-" + data.getIdentifier());
	}
	else {	
		buffer.append(" " + label);
	}
        buffer.append(", " + getPoRFraction(data));
	list.add(buffer.toString());
}

/**
Closes the gui.
*/
private void closeClicked() {
	setVisible(false);
	dispose();
}

/**
Creates an annual water diversion report for the given time series.
@param ts the daily time series for which to create the diversion report.  Cannot be null.
@return a list of strings, each of which is a line in a report for the given TS.
@throws Exception if there is an error creating the report.
*/
private List<String> createDiversionReport(DayTS ts) 
throws Exception {
	// The default output.  In this case, we produce a matrix like
	// the one produced by the legacy TSPrintSummaryMatrix.
	// However, since we now limit the formatting to only daily
	// time series and the data are already in a daily time step,
	// we do not have to change interval.

	// Get information for report...

	int[] wdid = HydroBase_WaterDistrict.parseWDID(ts.getLocation());
	int wd = wdid[0];
	int div = __dmi.lookupWaterDivisionForDistrict(wd);
	String[] sfut = null;
	String typeString = "";
	String useString = "";
	String fromStructure = "";
	String sourceString = "";

//	Message.printStatus(1, "", "ID: " + ts.getIdentifier());
//	Message.printStatus(1, "", "Scen: " + ts.getIdentifier().getScenario());
	
	if (!ts.getIdentifier().toString().equals("")) {
		// Have SFUT to process.
		sfut = HydroBase_Util.parseSFUT(ts.getIdentifier().toString());
		sourceString = HydroBase_Util.lookupDiversionCodingSource( sfut[0]);
			
		useString = __dmi.lookupUseDefinitionForXUse(sfut[2]);
		typeString = HydroBase_Util.lookupDiversionCodingType( sfut[3]);
		// Need to query the FROM structure.  Do here for now
		// but might want to do when the time series is first
		// queried...
		fromStructure = sfut[1];
		if (fromStructure != "") {	
			HydroBase_StructureView o = null;
			if ( fromStructure.length() < 7 ) {
				// Assume old database where F does not contain the WD...
				o = __dmi.readStructureViewForWDID(wd,StringUtil.atoi(fromStructure));
			}
			else { // Assume new database where F does contain the WD...
				int [] wdid_parts = HydroBase_WaterDistrict.parseWDID(fromStructure);
				o = __dmi.readStructureViewForWDID(wdid_parts[0],wdid_parts[1]);
			}
			if (o == null) {}
			else {
				HydroBase_StructureView structure = o;
				fromStructure = structure.getStr_name();
			}
		}
	}

	if (useString == null) {
		useString = "";
	}
	if (typeString == null) {
		typeString = "";
	}
	if (sourceString == null) {
		sourceString = "";
	}

	// Need to query the current structure.  Do here for now but
	// might want to do when the time series is first queried...
	String structureName = "";
	int streamNum = 0;
	String streamString = "";
	HydroBase_StructureView o = __dmi.readStructureViewForWDID(wd, wdid[1]);
	if (o == null) {}
	else {
		HydroBase_StructureView structure = o;
		structureName = structure.getStr_name();
		int wdWaterNum = structure.getWdwater_num();
		
		List<HydroBase_WDWater> v = __dmi.readWDWaterListForWDWater_num(wdWaterNum);
		if (v != null && v.size() > 0) {
			HydroBase_WDWater wdwater = v.get(0);
			streamNum = wdwater.getStrno();
			streamString = wdwater.getStrname();
		}	
	}

	int id = StringUtil.atoi(ts.getIdentifier().getLocation().substring(2));

	// For now always set to what is traditionally used...
	String dataFormat93 = "%9.3f";
	String dataFormat92 = "%9.2f";

	// Print the body of the summary...

	// Need to check the data type to determine if it is an average
	// or a total.  For now, make some guesses based on the units...

	List<String> strings = new ArrayList<String>();
	strings.add("");

	// Now transfer the daily data into a summary matrix, which
	// looks like:
	//
	// Day Month....
	// 1
	// ...
	// 31
	// statistics
	//
	// Repeat for each year.
	
	// Adjust the start and end dates to be on full years for the
	// calendar that is requested...

	DateTime startDate = new DateTime(ts.getDate1());
	DateTime endDate = new DateTime(ts.getDate2());

	// Need to adjust for the irrigation year to make sure
	// that the first month is Nov and the last is Oct...
	if (startDate.getMonth() < 11) {
		// Need to shift to include the previous
		// irrigation year...
		startDate.addYear(-1);
	}

	// Always set the start month to Nov...
	startDate.setMonth(11);
	startDate.setDay(1);
	if (endDate.getMonth() > 11) {
		// Need to include the next irrigation year...
		endDate.addYear(1);
	}
	
	// Always set the end month to Oct...
	endDate.setMonth(10);
	endDate.setDay(31);

	// The year that is printed in the summary is actually
	// later than the calendar for the Nov month...
	int monthToStart = 11;
	int monthToEnd = 10;

	// Reuse for each month that is printed.
	double[][] data = new double[31][12];
	String[][] dataFlag = new String[31][12];

	// Now loop through the time series and transfer to the proper
	// location in the matrix.  Since days are vertical, we cannot
	// print any results until we have completed a month...
	DateTime date = new DateTime(startDate, DateTime.DATE_FAST);
	StringBuffer buffer = null;
	// We have adjusted the dates above, so we always start in
	// column 0 (first day of first month in year)...
	int column = 0;
	int row = 0;
	double missing = ts.getMissing();
	int numDaysInMonth = 0;
	int day = 0;
	int month = 0;
	String tempString = null;

	int dataIntervalBase = ts.getDataIntervalBase();
	int dataIntervalMult = ts.getDataIntervalMult();

	int rowDay = 0;
	int columnMonth = 0;
	int irow = 0;
	int icolumn = 0;
	int year = 0;
	int numValidDaysInMonth = 0;
	
	TSData tsdata = new TSData(); // Used to retrieve data flag
	for (; date.lessThanOrEqualTo(endDate);
		date.addInterval(dataIntervalBase, dataIntervalMult)) {
		// Figure out if this is a new year.  If so, we reset
		// the headers, etc...
		day = date.getDay();
		month = date.getMonth();
		if (day == 1) {
			if (month == monthToStart) {
				// Reset the data array...
				for (irow = 0; irow < 31; irow++){
					for (icolumn = 0; icolumn < 12; 
					    icolumn++){
						data[irow][icolumn] = missing;
						dataFlag[irow][icolumn] = " ";
					}
				}
			}
			numDaysInMonth = TimeUtil.numDaysInMonth(
				month, date.getYear());
		}

		// Save the data value for later use in output and
		// statistics.  Allow missing data values to be saved...
		data[row][column] = ts.getDataValue(date);
		dataFlag[row][column] = ts.getDataPoint(date,tsdata).getDataFlag().trim();
		if (dataFlag[row][column].equals("")) {
			dataFlag[row][column] = " ";
		}

		// Check to see if at the end of the year.  If so,
		// print out one year's values...
		if ((month == monthToEnd) && (day == numDaysInMonth)) {
			// Print the header for the year...
			strings.add("");
			strings.add("");

			// Irrigation year...
			// For diversions, always print out the standard report
			strings.add(
				"DIVISION OF WATER RESOURCES         " 
				+ "                      " 
				+ "STATE OF COLORADO" 
				+ "                                " 
				+ "OFFICE OF THE STATE ENGINEER");
			strings.add("");
			strings.add("DIVISION " 
				+ StringUtil.formatString(div,"%-6d") 
				+ " DISTRICT " 
				+ StringUtil.formatString(wd,"%-2d")
				+ "                         " 
				+ "ANNUAL WATER DIVERSION REPORT" 
				+ "                               " 
				+ " IRRIGATION YEAR:  " + date.getYear());
			strings.add(
				"                                    " 
				+ "                                    " 
				+ "                                    " 
				+ "  (11/01/" + (date.getYear() - 1) 
				+ " - 10/31/" + date.getYear() + ")");
			strings.add("");

			// Format the source...
			if (sfut == null) {
				tempString = "";
			}
			else if (sourceString.equals("")) {
				tempString = "SOURCE:";
			}
			else {	
				tempString = "SOURCE:  " 
					+ StringUtil.formatString(
					sourceString, "%-35.35s") 
					+ "(" + StringUtil.formatString(
					sfut[0], "%1.1s") + ")";
			}
			
			strings.add("STRUCTURE NAME:  "
				+ StringUtil.formatString(structureName, 
				"%-27.27s") + "(" + 
				StringUtil.formatString(id,"%5d") + ")"
				+ "           " + tempString);
				
			if (sfut == null) {
				tempString =
					"         +++ TOTAL WATER" 
					+ " THROUGH STRUCTURE +++";
			}
			else if (fromStructure.equals(""))  {
				tempString = "FROM  :";
			}
			else {	
				tempString = "FROM  :  " 
					+ StringUtil.formatString(
					fromStructure, "%-31.31s")
					+ "(" + StringUtil.formatString(
					sfut[1], "%5.5s") + ")";
			}

			strings.add("SOURCE STREAM :  "
				+ StringUtil.formatString(streamString,
				"%-29.29s") + "(" 
				+ StringUtil.formatString(streamNum,
				"%3d") + ")           " 
				+ tempString);

			if (sfut == null) {
				tempString = "";
			}
			else if (useString.equals("")) {
				tempString = "USE   :";
			}
			else {	
				tempString = "USE   :  " 
					+ StringUtil.formatString(
					useString, "%-35.35s") + "(" 
					+ sfut[2] + ")";
			}
			
			strings.add("                              " 
				+ "                                " 
				+ tempString);

			if (sfut == null ) {
				tempString = "";
			}
			else if (typeString.equals("") ) {
				tempString = "TYPE  :";
			}
			else {	
				tempString = "TYPE  :  "  
					+ StringUtil.formatString(
					typeString, "%-35.35s") + "(" 
					+ sfut[3] +")";
			}

			strings.add("                              " 
				+ "                                " 
				+ tempString);
			strings.add("");
			strings.add(
				"  Day            Nov       Dec       Jan"
				+ "       Feb       Mar       Apr       May"
				+ "       Jun       Jul       Aug       Sep "
				+ "      Oct      ");
			strings.add("  ----------------------------"
				+ "---------------------------------------"
				+ "---------------------------------------"
				+ "------------------------");

			// Now print the summary for the year...
			for (irow = 0; irow < 31; irow++) {
				rowDay = irow + 1;
				for (icolumn = 0; icolumn < 12;
				    icolumn++) {
					columnMonth = monthToStart + icolumn;
					if (icolumn == 0) {
						// Allocate a new buffer
						// and print the day for all 12
						// months...
						buffer = new StringBuffer();
						buffer.append(
							StringUtil.formatString(
							rowDay, 
							"   %2d|      "));
					}

					// Print the daily value...
					// Figure out if the day is
					// valid for the month.  The
					// date is for the end of the
					// year (last month) from the
					// loop.
					year = date.getYear();
					if (columnMonth > 10) {
						--year;
					}

					numValidDaysInMonth 
						= TimeUtil.numDaysInMonth(
						columnMonth, year);

					if (rowDay > numValidDaysInMonth) {
						buffer.append("          ");
					}
					else if (ts.isDataMissing(
					    data[irow][icolumn])) {
						buffer.append("          ");
					}
					else {	
						buffer.append(
							StringUtil.formatString(
							data[irow][icolumn],
							dataFormat93) 
							+ dataFlag
							[irow][icolumn]);
					}

					if (icolumn == 11) {
						// Have processed the
						// last month in the
						// year print the row...
						buffer.append(
							StringUtil.formatString(
							rowDay, "|%2d"));
						strings.add(
							buffer.toString());
					}
				}
			}

			strings.add(
				"     --------------------------------------"
				+ "-------------------------------------------"
				+ "-----------------------------------------"
				+ "-----");

			// Now to do the statistics.  Loop through each
			// column...
			// First check to see if all stats should be
			// printed (can be dangerous if we add new
			// statistics)..

			strings = StringUtil.addListToStringList(strings,
				formatOutputStats(ts, data, "TOTAL SFD",
				dataFormat92));

			// Also add count...
			strings = StringUtil.addListToStringList(strings,
				formatOutputStats(ts, data, "DAYS USED", 
				"%9.0f"));

			strings = StringUtil.addListToStringList(strings,
				formatOutputStats(ts, data, "AVG SFD  ",
				dataFormat92));
			strings.add("");
			
			strings = StringUtil.addListToStringList(strings,
				formatOutputStats(ts, data, "TOTAL AF ",
				dataFormat92));

			// Print the totals...
			strings.add("");
			strings.add("ANNUAL TOTAL SFD:       "
				+ StringUtil.formatString(__annualSFD, 
				"%10.2f") + "  SFD");
			strings.add("TOTAL DAYS USED:        "
				+ StringUtil.formatString(__annualDays, "%10d")
				+ "  DAYS" 
				+ "                                " 
				+ "* Indicates Observed data," 
				+ "  U Indicates User supplied data.");
			strings.add("ANNUAL TOTAL ACRE FEET: "
				+ StringUtil.formatString(__annualAF, "%10.2f")
				+ "  AF" + "                                  " 
				+ "All other data are interpreted from " 
				+ "the previous observed value.");
			column = -1;	// Will be incremented in next
					// step.
		}
		
		if (day == numDaysInMonth) {
			// Reset to the next column...
			++column;
			row = 0;
		}
		else {	
			++row;
		}
	}

	return strings;
}

/**
Does nothing.
*/
public void dragUnsuccessful(int action) {}

/**
Does nothing.
*/
public void dragSuccessful(int action) {}

/**
Does nothing.
*/
public void dropAllowed() {}

/**
Does nothing.
*/
public void dropExited() {}

/**
Does nothing.
*/
public void dropNotAllowed() {}

/**
Does nothing.
*/
public void dropSuccessful() {}

/**
Does nothing.
*/
public void dropUnsuccessful() {}

/**
Does nothing.
*/
public void dragStarted() {}

/**
Called when a drag is about to start in order that a time series can be read
from the database and made the transferable object.
@return true if the data was read successfully from the database and can be
dragged, or false if the drag should not take place.
*/
public boolean dragAboutToStart() {
	String routine = "HydroBase_GUI_StructureDetail.dragAboutToStart";

	if (__lastJList == null) {
		// shouldn't happen, but just check to make sure
		return false;
	}

        // from and to dates must exist. 
        String from = __fromJTextField.getText();
        String to = __toJTextField.getText();
        if (from == null || to == null) {
		Message.printWarning(1, routine,	
			"No output period was specified.");
                return false;
        }
        else if (from.equals("")|| to.equals("")) {
		Message.printWarning(1, routine,	
			"No output period was specified.");
                return false;
        }

	// start and end dates
	DateTime start = null;
	DateTime end = null;
	try {	
		start = DateTime.parse(from);
		end = DateTime.parse(to);

	} catch (Exception e) {}

	// selected should be just element long, but if it's not only the
	// first element will be used anyways.
	int listType = -1;
	if (__lastJList == __monthJList) {
		listType = __MONTH_LIST;
	}
	else if (__lastJList == __dayJList) {
		listType = __DAY_LIST;
	}

	List<HydroBase_StructMeasTypeView> selected = getSelectedStructMeasType(listType);

	String id = null;
	String meas_type = null;
	TS ts = null;
	TS measTypeTS = null;
	List<String> errorList = new ArrayList<String>();

	if (selected.size() == 0) {
		// this shouldn't happen, most likely.
		return false;
	}
	
	HydroBase_StructMeasTypeView view = null;
	view = (HydroBase_StructMeasTypeView)selected.get(0);
        id = view.getTSIdentifier();
       	measTypeTS = view.getTimeSeries();
	int index = id.indexOf("DivClass");
	if (index == -1) {
		index = id.indexOf("RelClass");
	}

	if (index > -1) {
		String temp = id.substring(0, index);
		temp += view.getMeas_type() + "-" 
			+ view.getIdentifier();
		String temp2 = id.substring(index);
		index = temp2.indexOf(".");
		temp2 = temp2.substring(index);
		temp += temp2;
		id = temp;
	}
		
	try {
                ts = __dmi.readTimeSeries(id, start, end, 
			measTypeTS.getDataUnits(), true, null);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, 
			"Error reading time series from database.");
		Message.printWarning(2, routine, e);
		return false;
	}

	if (ts == null) {
		errorList.add(id);
	}	

	// Set the extended legend for the time series, if a water
	// class time series...

	meas_type = view.getMeas_type();
	if (meas_type.equals("DivClass") || meas_type.equals("RelClass")) {
		if (ts != null && ts.getIdentifier().getScenario().length()> 0){
			ts.setExtendedLegend("%L %Z, %U");
		}
	}

        // this clause prints warnings for all data_sources which
        // did not return any records.
        int size = errorList.size();
        if (size > 0) {
                String errorString = "The following data type(s)"
                        + " returned zero records:";
                for (int i = 0; i < size; i++) {
                        errorString += "\n" 
                                + (String)errorList.get(i);
                }
                Message.printWarning(1, routine, errorString);
		return false;
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
	
		__lastJList.setAlternateTransferable(ts);
	}
	else {
		Message.printStatus(2, "", "TS was null");
		ready();
		return false;
	}

	return true;
}

/**
Format the output statistics row for the annual water diversion report given 
the data array.
@param ts the time series for which the report is being made and in which 
report the data from this method will be placed.
@param data the data array containing time series information to compute the
statistics from.
@param label the label next to which the data will be placed on the report.
@param dataFormat the StringUtil.formatString() format in which to format the
data.
*/
private List<String> formatOutputStats(DayTS ts, double[][] data, String label, 
String dataFormat) {
	double[] array = new double[31];
	double stat = 0.0;
	int column = 0;
	int numNotMissing = 0;
	int row = 0;
	StringBuffer buffer = null;

	// Statistics are done by column...

	if (label.startsWith("DAYS USED")) {
		__annualDays = 0;
	}
	if (label.startsWith("TOTAL SFD")) {
		__annualSFD = 0.0;
	}
	if (label.startsWith("TOTAL AF")) {
		__annualAF = 0.0;
	}

	for (column = 0; column < 12; column++) {
		if (column == 0) {
			buffer = new StringBuffer();
			// Label needs to be 9 characters...
			buffer.append(label + "  ");
		}
		
		// Extract the non-missing values...
		numNotMissing = 0;
		for (row = 0; row < 31; row++) {
			// For diversions, only use non-zero data as
			// values...
			if (!ts.isDataMissing(data[row][column]) 
			    && (data[row][column] > 0.0)) {
				++numNotMissing;
			}
		}
		
		if (numNotMissing > 0) {
			// Transfer to an array...
			array = new double[numNotMissing];
			numNotMissing = 0;
			for (row = 0; row < 31; row++) {
				// For diversions, only use non-zero data as
				// values...
				if (!ts.isDataMissing(data[row][column]) 
				    && (data[row][column] > 0.0)) {
					array[numNotMissing] 
						= data[row][column];
					++numNotMissing;
				}
			}
			
			stat = 0.0;
			try {	
				if (label.startsWith ("DAYS USED")) {
					__annualDays += numNotMissing;
					stat = numNotMissing;
				}
				else if (label.startsWith("Min")) {
					stat = MathUtil.min ( array );
				}
				else if (label.startsWith("Max")) {
					stat = MathUtil.max ( array );
				}
				else if (label.startsWith("AVG SFD")) {
					stat = MathUtil.mean ( array );
				}
				else if (label.startsWith("TOTAL SFD")) {
					stat = MathUtil.sum(array);
					__annualSFD += stat;
				}
				else if (label.startsWith("TOTAL AF")) {
					// SFD * conversion to AF
					stat = MathUtil.sum(array) * 1.9835;
					__annualAF += stat;
				}
			}
			catch (Exception e) {}
			
			buffer.append(StringUtil.formatString(stat, 
				" " + dataFormat));
		}
		else {	
			buffer.append("          ");
		}
	}

	List<String> strings = new ArrayList<String>();
	strings.add(buffer.toString());
	return strings;
}

/**
Retrieve ALL measurement types for the current structure and store them in
__allMeasTypes.
*/
private void getAllMeasurementTypes() {
	String routine = CLASS + ".getAllMeasurementTypes";
	try { 
		__allMeasTypes = __dmi.readStructMeasTypeListForStructure_num(
			__structureView.getStructure_num(), false);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
		__allMeasTypes = new ArrayList<HydroBase_StructMeasTypeView>();
	}
}

/**
Displays the requested time series data in the desired format.
@param flag the desired format of the data to display, __BUTTON_GRAPH or 
__BUTTON_TABLE).
*/
private void getData(String flag) {
        String routine = CLASS + ".getData()";

	JGUIUtil.setWaitCursor(this, true);
	List<TS> tsList = getTSVector(flag);

        int size = 0;
	
	if (tsList != null) {
		size = tsList.size();
	}

        if (size == 0) {
		ready();
		return;
	}

	// use the Time Series at zero to set the properties
	try {	
		// Set properties common to all views...
		PropList tsviewProps = new PropList("tsview");
		tsviewProps.set("CalendarType", "" + YearType.WATER);
		tsviewProps.set("TotalWidth", "600");
		tsviewProps.set("TotalHeight", "550");
		tsviewProps.set("DisplayFont", "Courier");
		tsviewProps.set("DisplaySize", "11");
		tsviewProps.set("PrintFont", "Courier");
		tsviewProps.set("PrintSize", "7");
		tsviewProps.set("PageLength", "100");
		String title = HydroBase_WaterDistrict.formWDID(
				__structureView.getWD(),
				__structureView.getID()) + " Time Series";
		tsviewProps.set("TitleString", title);
		tsviewProps.set("TSViewTitleString", title);
		tsviewProps.set("UseCommentsForHeader", "true");
		tsviewProps.set("Table.UseExtendedLegend", "true");
		// Now display the correct initial view...
		if (flag.equals(__BUTTON_GRAPH)) {
			tsviewProps.set("InitialView", "Graph");
			new TSViewJFrame(tsList, tsviewProps);
		}
		else if (flag.equals(__BUTTON_TABLE)) {
			// If daily data and more than one time series
			// print a warning...
			boolean doit = true;
			if ((JGUIUtil.selectedSize(__dayJList) > 1)) {
				/*
				int response =
					new ResponseJDialog(this,
					"Continue?", "Daily data tabular "
					+ "output for multiple "
					+ "times series can be slow.  "
					+ "Continue?",
					ResponseJDialog.YES |
					ResponseJDialog.NO).response();
				if (response == ResponseJDialog.NO) {
					doit = false;
				}
				*/
			}
			if ((JGUIUtil.selectedSize(__dayJList) == 1)) {
				TS ts0 = (TS)tsList.get(0);
				if (ts0 == null) {
					ready();
					return;
				}
				/*
				DateTime d1 = ts0.getDate1();
				DateTime d2 = ts0.getDate2();
				if ((d2.getYear() - d1.getYear()) > 10) {
					int response =
						new ResponseJDialog(this,
						"Continue?", "Daily data "
						+ "tabular output for > 10 "
						+ "years of data can be slow."
						+ "  Continue?",
						ResponseJDialog.YES |
						ResponseJDialog.NO).response();
					if (response == ResponseJDialog.NO) {
						doit = false;
					}
				}
				*/
			}
			if (doit) {
				tsviewProps.set("InitialView", "Table");
				new TSViewJFrame(tsList, tsviewProps);
			}
		}
       		else if (flag.equals(__BUTTON_SUMMARY)) {
			tsviewProps.set("InitialView", "Summary");
			new TSViewJFrame(tsList, tsviewProps);
		}
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error displaying data.");
	}
        ready();
}

/**
Return a String of information indicating the the period of record and the
number of measurements available.
@param data HydroBase_StructMeasTypeView object.
@return the following formatted String:  PoR, meas Count/Total Count.
*/
private String getPoRFraction(HydroBase_StructMeasTypeView data) {
        String porFraction = data.getStart_year() + "-" + data.getEnd_year()
                        + ", " + data.getMeas_count()
                        + "/" + getTotalCount(data);
        return porFraction;
}

/**
Examine which list items are selected and returns a Vector of 
HydroBase_StructMeasType objects which reflect this selection.
@return the selected types as a Vector of HydroBase_StructMeasType objects, 
null if none are selected.
*/
private List<HydroBase_StructMeasTypeView> getSelectedStructMeasType() {
	return getSelectedStructMeasType(__BOTH_LISTS);
}
	
/**
Examine which list items are selected and returns a Vector of 
HydroBase_StructMeasType objects that reflect this selection.
@param list the list from which to return objects.  One of __BOTH_LISTS, 
__MONTH_LIST or __DAY_LIST.
@return the selected types as a Vector of HydroBase_StructMeasType objects, 
null if none are selected.
*/
private List<HydroBase_StructMeasTypeView> getSelectedStructMeasType(int list) {
	HydroBase_StructMeasTypeView data;
        if (__measType.size() == 0) {
                return null;
        }
        
        List<HydroBase_StructMeasTypeView> selected = new ArrayList<HydroBase_StructMeasTypeView>();
	// For each selected item, get the index and use to select the
	// data object...
	int[] selectedIndices = null;
	int selectedSize = 0;
	
	if (list == __BOTH_LISTS || list == __MONTH_LIST) {
		selectedIndices = __monthJList.getSelectedIndices();
		selectedSize = selectedIndices.length;
		for (int i = 0; i < selectedSize; i++) {
	               	data = __measType.get(
				selectedIndices[i]);
	                       selected.add(data);
		}
	}
	
	if (list == __BOTH_LISTS || list == __DAY_LIST) {
		selectedIndices = __dayJList.getSelectedIndices();
		selectedSize = selectedIndices.length;
		int monthJListSize = __monthJList.getModel().getSize();
		for (int i = 0; i < selectedSize; i++) {
	               	data = (HydroBase_StructMeasTypeView)
				__measType.get(
				monthJListSize + selectedIndices[i]);
			selected.add(data);
		}
	}	
        return selected;
}

/**
Return a list of HydroBase_StructMeasTypeView objects for the current structure 
limited by the flag.
@param flag flag for returning a specific type of record(s).
@param dataType used for Total. Can be either "DivTotal" or "RelTotal".
@return a list of HydroBase_StructMeasTypeView objects according to the flag.
*/
private List<HydroBase_StructMeasTypeView> getStructureMeasType(int flag, String dataType) {
	String routine = CLASS + ".getStructureMeasType";
        
        // if query returned no records, return null
        if (__allMeasTypes == null) {
		if (Message.isDebugOn) {
			Message.printDebug(10, routine,"No records available.");
		}
                return null;
        }

	HydroBase_StructMeasTypeView data = null;
	List<HydroBase_StructMeasTypeView> v = new ArrayList<HydroBase_StructMeasTypeView>();
        int size = __allMeasTypes.size();
	int i = 0;

	if (Message.isDebugOn) {
		Message.printDebug(10, routine, "Checking " + size + " nodes.");
                for (i = 0; i < size; i++) {
                        data = __allMeasTypes.get(i);
			Message.printDebug(10, routine, "Available:  \""
				+ data.getMeas_type() + "\", \""
				+ data.getTime_step() + "\"");
		}
	}
		
        if (flag == __MONTHLY_WC) {
                for (i = 0; i < size; i++) {
                        data = __allMeasTypes.get(i);
                        if (data.getMeas_type().equalsIgnoreCase("DivClass")
				&& data.getTime_step().equalsIgnoreCase(__ANNUAL)){
                                        v.add(data);
                        }
                        else if (data.getMeas_type().equalsIgnoreCase("RelClass")
				&&
                               data.getTime_step().equalsIgnoreCase(__ANNUAL)){
                                        v.add(data);
                        }
                }
        }
        else if (flag == __DAILY_WC) {
                for (i = 0; i < size; i++) {
                        data = __allMeasTypes.get(i);
                        if (data.getMeas_type().equalsIgnoreCase("DivClass")
                                && 
				data.getTime_step().equalsIgnoreCase(__DAILY)) {
                                v.add(data);
                        }
                        if (data.getMeas_type().equalsIgnoreCase("RelClass")
                                && 
				data.getTime_step().equalsIgnoreCase(__DAILY)) {
                                v.add(data);
                        }
                }
        }
        else if (flag == __TOTAL__MONTHLY) {
                for (i = 0; i < size; i++) {
                        data = __allMeasTypes.get(i);
                        if (data.getMeas_type().equalsIgnoreCase(dataType)
				&&
				data.getTime_step().equalsIgnoreCase(__ANNUAL)){
                                v.add(data);
                        }
                        else if (data.getMeas_type().equalsIgnoreCase(dataType)
				&&
				data.getTime_step().equalsIgnoreCase(__MONTHLY))
				{
                                v.add(data);
                        }
                }
        }
        else if (flag == __TOTAL_DAILY) {
                for (i = 0; i < size; i++) {
                        data = __allMeasTypes.get(i);
                        if (data.getMeas_type().equalsIgnoreCase(dataType)
                                && 
				data.getTime_step().equalsIgnoreCase(__DAILY)) {
                                v.add(data);
                        }
                }
        }

        if (v.size() == 0) {
                v = null;
        }

        return v;
}

/**
Returns the total possible number of records that could occur during a given period.
@param data the HydroBase_StructMeasTypeView record for which to return the total count.
@return the total possible number of records that could occur during a given period.
*/
private int getTotalCount(HydroBase_StructMeasTypeView data) {
        int total = DMIUtil.MISSING_INT;
        String time_step = data.getTime_step();

        if (time_step.equalsIgnoreCase(__ANNUAL)) {
                total = (data.getEnd_year() - data.getStart_year() + 1) * 12;
                                
        }
        else if (time_step.equalsIgnoreCase(__MONTHLY)) {
                total = (data.getEnd_year() - data.getStart_year() + 1) * 12;
                                
        }
        else if (time_step.equalsIgnoreCase(__DAILY)) {
                total = TimeUtil.numDaysInMonths(1, 
                        data.getStart_year(), 12, data.getEnd_year());
        }

        return total;
}

// REVISIT SAM 2004-12-01 - seems like a method like this should be in the
// HydroBase_Util class
/**
Constructs a TSIdentifier String given a HydroBase_StructMeasTypeView object.
The identifiers adhere to the identifiers in the TSTool HydroBase Input Type
appendix.
@param data HydroBase_StructMeasTypeView object for which to create a 
TSIdentifier String.
@return the TSIdentifier String.
*/
private String getTSIdentifier(HydroBase_StructMeasTypeView data) {
	String id = null;
        String time_step = data.getTime_step();
        String sfut = data.getIdentifier();
	
	__structureView.getStructure_num();
 
	// Convert to standard TS package intervals...

        if (time_step.equalsIgnoreCase(__ANNUAL)) {
                time_step = __MONTH;
        }
        else if (time_step.equalsIgnoreCase(__MONTHLY)) {
                time_step = __MONTH;
        }
        else if (time_step.equalsIgnoreCase(__DAILY)) {
                time_step = __DAY;
        }

	// If the data type is DivClass or RelClass, then identifier has the
	// SFUT...

        if ( !sfut.trim().equals("")) {
		// Need to add the SFUT to the data type...
        	sfut = "-" + sfut;
        }
        
	String wdid = null;

	wdid = HydroBase_WaterDistrict.formWDID(
		__structureView.getWD(), __structureView.getID());

        id = wdid + "." + data.getData_source() + "." 
		+ data.getMeas_type() + sfut + "." + time_step;

        return id;
}

/**
Returns the list of selected time series.  These time series are the ones
selected on the GUI.
@param flag the button that was pushed which caused the event for which the
list of time series should be returned.
@return a list of time series, or null if there was a problem getting the
time series.
*/
private List<TS> getTSVector(String flag) {
	String routine = "HydroBase_GUI_StructureDetail.getTSVector";

        HydroBase_StructMeasTypeView view = null;
        List<TS> tsVector = new ArrayList<TS>(10);
        List<String> errorList = new ArrayList<String>(10);

        // from and to dates must exist. 
        String from = __fromJTextField.getText();
        String to = __toJTextField.getText();
        if (from == null || to == null) {
		Message.printWarning(1, routine,"No output period was specified.");
		JGUIUtil.setWaitCursor(this, false);
                return null;
        }
        else if (from.equals("")|| to.equals("")) {
		Message.printWarning(1, routine,"No output period was specified.");
		JGUIUtil.setWaitCursor(this, false);
                return null;
        }

        __statusJTextField.setText("Retrieving Data...");

	// start and end dates
	DateTime start = null;
	DateTime end = null;
	
	try {	
		start = DateTime.parse(from);
		end = DateTime.parse(to);

	} 
	catch (Exception e) {}

	List<HydroBase_StructMeasTypeView> selected = getSelectedStructMeasType();
        
        if (selected == null) {
                new ResponseJDialog(this, "Error", 
			"Select the data to retrieve",
			ResponseJDialog.OK);
                ready();
		JGUIUtil.setWaitCursor(this, false);
                return null;
        }
        // check for compatible units if not a summary
        else if ( !flag.equals(__BUTTON_SUMMARY) &&
		!flag.equals(__BUTTON_DIVERSION_REPORT) ) {
                int size = selected.size();
                List<TS> v = new ArrayList<TS>(10);
                for (int i = 0; i < size; i++) {      
                        view = selected.get(i);
                        v.add(view.getTimeSeries());  
                }		
	}

        int size = selected.size();
	String meas_type = null;
	TS ts = null;
	TS measTypeTS = null;
	String id = null;
	int index = -1;
	String temp = null;
	String temp2 = null;
        for (int i = 0; i < size; i++) {      
                view = selected.get(i);
       	        id = view.getTSIdentifier();
		index = id.indexOf("DivClass");
		if (index == -1) {
			index = id.indexOf("RelClass");
		}

		if (index > -1) {
			temp = id.substring(0, index);
			temp += view.getMeas_type() + "-" 
				+ view.getIdentifier();
			temp2 = id.substring(index);
			index = temp2.indexOf(".");
			temp2 = temp2.substring(index);
			temp += temp2;
			id = temp;
		}
                measTypeTS = view.getTimeSeries();

		try {	PropList HydroBase_props = new PropList ("");
			if ( flag.equals(__BUTTON_DIVERSION_REPORT) ) {
				// To match the printed records, DO NOT
				// fill by carrying forward.
				HydroBase_props.set ( "FillDailyDiv=False" );
			}
			// Never fill with diversion comments.  Users will need
			// to use TSTool or StateDMI for this.
			// REVISIT SAM 2006-04-26
			// Recommend adding a checkbox to allow users to turn
			// this on/off (default on).
			// REVISIT SAM 2006-04-26
			// Also there is an issue that the period in the
			// display is only for the observations and does not
			// include the extended period that includes diversion
			// comments.  Need to evaluate what to do.
			HydroBase_props.set ( "FillUsingDivComments=False" );
			// Else use defaults for time series displays...
	                ts = __dmi.readTimeSeries ( id, start, end, 
				measTypeTS.getDataUnits(), true,
				HydroBase_props );
		}
		catch (Exception e) {
			Message.printWarning(1, routine, 
				"Error reading time series from database.");
			Message.printWarning(2, routine, e);
		}

                // add the time series to the Vector
                if (ts != null) {
                        tsVector.add(ts);
                }
                else {	
			errorList.add(id);
                }

		// Set the extended legend for the time series, if a water
		// class time series...

		meas_type = view.getMeas_type();

		if (meas_type.equals("DivClass") 
			|| meas_type.equals("RelClass")) {
			if (ts != null 
				&& ts.getIdentifier().getScenario().length()>0){
				ts.setExtendedLegend("%L %Z, %U");
			}
		}
        }

        // this clause prints warnings for all data_sources which
        // did not return any records.
        size = errorList.size();
        if (size > 0) {
                String errorString = "The following data type(s)"
                        + " returned zero records:";
                for (int i = 0; i < size; i++) {
                        errorString += "\n" 
                                + (String)errorList.get(i);
                }
                Message.printWarning(1, routine, errorString, this);
        }

	return tsVector;
}

/**
Sets up some GUI information.
*/
private void initialize() {
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	// set graphing properties
        __prop = new PropList("TSView.props");
        __prop.set("HelpKey=" + __BUTTON_HELP);
        __prop.set("TotalWidth=500");
        __prop.set("TotalHeight=560");
	__prop.set("TotalGraphWidth=300");
	__prop.set("TotalGraphHeight=400");
        __prop.set("TitleString=");
	__prop.set("ExtendedLegend=true");
	__prop.set("TSPerPlot=10");
        __prop.set("XAxisJLabelString=Date");

        __measType = new ArrayList<HydroBase_StructMeasTypeView>(10);

	// set date properties
	__dateProps = new PropList("Calls DateTimeBuilderJDialog properties");
	__dateProps.set("DatePrecision", "Day");
	__dateProps.set("DateFormat", "Y2K");

	setupGUI();
}

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
Determines which list was clicked on.
@param event the MouseEvent that happened.
*/
public void mousePressed(MouseEvent event) {
	Object o = event.getSource();

	if (o == __monthJList) {
		__lastJList = __monthJList;
	}
	else if (o == __dayJList) {
		__lastJList = __dayJList;
	}
	else {
		__lastJList = null;
	}

	if (__monthJList.getSelectedSize() == 0 
	    && __dayJList.getSelectedSize() > 0) {
		__reportJButton.setEnabled(true);
	}
	else {
		__reportJButton.setEnabled(false);
	}
}

/**
Does nothing.
*/
public void mouseReleased(MouseEvent event) {}

/**
Sets the GUI ready for user interaction.
*/
private void ready() {
	JGUIUtil.setWaitCursor(this, false);
        __statusJTextField.setText("Ready");
}

// REVISIT SAM 2004-12-01 can this be made private and renamed to something
// like setPeriodTextFieldsFromData()?
/**
Sets the output period in the __fromJTextField and __toJTextField by inspecting
the range of the selected time series.  This method is called when the display
is first initialized and also when the list of selected time series changes. 
*/
protected void setOriginalPeriodText()
{	// Get the list of selected HydroBase_StructMeasType...

	List<HydroBase_StructMeasTypeView> selected = getSelectedStructMeasType();

	String to = "";
	String from = "";

        if (selected == null) {
		__tableJButton.setEnabled(false);
		__summaryJButton.setEnabled(false);
		__graphJButton.setEnabled(false);
		// No time series are selected so don't change the period...
                return;
        }

        int size = selected.size();
	if (size == 0) {
		__tableJButton.setEnabled(false);
		__summaryJButton.setEnabled(false);
		__graphJButton.setEnabled(false);
	}	
        else if (size > 0) {
		__tableJButton.setEnabled(true);
		__summaryJButton.setEnabled(true);
		__graphJButton.setEnabled(true);
                                
		List<TS> tsList = new ArrayList<TS>(10);
                for (int i = 0; i < size; i++) {
			// Get the time series (headers only) for the
			// HydroBase_StructMeasType that are selected...
                        tsList.add(selected.get(i).getTimeSeries());
                }

                try {	// Get the limits from the time series (headers only).
			// The dates from HydroBase will be irrigation year only
			// (no months)...
			TSLimits limits = TSUtil.getPeriodFromTS(tsList, TSUtil.MAX_POR);
                        int start = limits.getDate1().getYear();
                        int end = limits.getDate2().getYear();
			// Adjust the period so that calendar years are used and
			// month and day are used for the period.  This requires
			// converting from the irrigation year...
                        from = (start - 1) + "-11-01";
                        to = end + "-10-31";
                }
                catch (Exception e) {;}
		// TODO SAM 2004-12-01 why is nothing done in the catch?
        }

        __fromJTextField.setText(from);
        __toJTextField.setText(to);
}

/**
Responds to the set Period Button
*/
private void setPeriod_clicked() {
	String routine = CLASS + ".setPeriod_clicked";
	DateTime from = null;
	DateTime to = null;

	try {	from = DateTime.parse(__fromJTextField.getText(),
                            DateTime.FORMAT_YYYY_MM_DD);
		to = DateTime.parse(__toJTextField.getText(),
                            DateTime.FORMAT_YYYY_MM_DD);
	}
	catch (Exception e) {			
		Message.printWarning(2, routine,
			"Exception parsing dates.");
		Message.printWarning(2, routine, e);
		from = new DateTime(DateTime.PRECISION_DAY);
		to = new DateTime(DateTime.PRECISION_DAY);
	}
	new DateTimeBuilderJDialog(this, __fromJTextField, 
			__toJTextField, from, to, __dateProps);

	__originalJButton.setEnabled(true);
}

/**
Sets up the GUI.
*/
private void setupGUI() {
        String routine = CLASS + ".init()";
                                                        
        addWindowListener(this);

       	String name = __structureView.getStr_name();

        getAllMeasurementTypes();
        
	int curInt = __structureView.getDiv();
	
	String div = "";
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                div = "" + curInt;
        }
        
	curInt = __structureView.getWD();
	
	String wd = "";
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                wd = "" + curInt;
        }
        
        curInt = __structureView.getID();

	String id = "";
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                id = "" + curInt;
        }
        
        // the following are used in the GUI layout
        GridBagLayout gbl = new GridBagLayout();
        Insets NLBR = new Insets(0,7,7,7);
        Insets NLNR = new Insets(0,7,0,7);
        Insets TLNR = new Insets(7,7,0,7);
        
	JPanel mainIDJPanel = new JPanel();
	mainIDJPanel.setLayout(new BorderLayout());
        JPanel idJPanel = new JPanel();
        idJPanel.setLayout(gbl);
	mainIDJPanel.add("West", idJPanel);
	getContentPane().add("North", mainIDJPanel);
                
        JGUIUtil.addComponent(idJPanel, new JLabel("Structure Name:"), 
                0, 0, 1, 1, 0, 0, TLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
  
        JGUIUtil.addComponent(idJPanel, new JLabel("DIV:"), 
                1, 0, 1, 1, 0, 0, TLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(idJPanel,  new JLabel("WD:"), 
                2, 0, 1, 1, 0, 0, TLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(idJPanel, new JLabel("ID:"), 
                3, 0, 1, 1, 0, 0, TLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __nameJTextField = new JTextField();
        __nameJTextField.setText(" " + name + "     ");
        __nameJTextField.setEditable(false);
        JGUIUtil.addComponent(idJPanel, __nameJTextField, 
                0, 1, 1, 1, 0, 0, NLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __divJTextField = new JTextField(4);
        __divJTextField.setEditable(false);
        __divJTextField.setText(div);
        JGUIUtil.addComponent(idJPanel, __divJTextField, 
                1, 1, 1, 1, 0, 0, NLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __wdJTextField = new JTextField(4);
        __wdJTextField.setEditable(false);
        __wdJTextField.setText(wd);
        JGUIUtil.addComponent(idJPanel, __wdJTextField, 
                2, 1, 1, 1, 0, 0, NLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __idJTextField = new JTextField(10);
        __idJTextField.setEditable(false); 
        __idJTextField.setText(id);
        JGUIUtil.addComponent(idJPanel, __idJTextField, 
                3, 1, 1, 1, 0, 0, NLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        
        // Top West JPanel - for lists
        JPanel listJPanel = new JPanel();
        listJPanel.setLayout(gbl);
	getContentPane().add("Center", listJPanel);
        
        JGUIUtil.addComponent(listJPanel, new JLabel(
		"Monthly Data (Count, Data Type/SFUT, Period, "
		+ "Available):"), 
                0, 2, 2, 1, 0, 0, NLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	__monthJList = new DragAndDropSimpleJList(
		DragAndDropUtil.ACTION_COPY, DragAndDropUtil.ACTION_NONE);

        // MONTHLY TIME SERIES
        List<HydroBase_StructMeasTypeView> v = getStructureMeasType(__TOTAL__MONTHLY, "DivTotal");

	HydroBase_StructMeasTypeView o;
        if (v != null) {
                o = v.get(0);
		addListItem(o, "DivTotal", __monthJList);
        }

        v = getStructureMeasType(__TOTAL__MONTHLY, "RelTotal");
        if (v != null) {
                o = v.get(0);
		addListItem(o, "RelTotal", __monthJList);
        }

        v = getStructureMeasType(__TOTAL__MONTHLY, "ResEOM");
        if (v != null) {
		// REVISIT (JTS - 2004-05-17)
		// sam was going to look at the following -- does it 
		// ever show up?  Postponed in favor of getting the 
		// above two cases working.
                o = v.get(0);
		addListItem(o, "Reservoir End of Month", __monthJList);
        }	

        // get the annual water color and add the list items
        // and periods of record accordingly
        v = getStructureMeasType(__MONTHLY_WC, null);
	int nmonthlyWC = 0;
        if (v != null) {
                int size = v.size();
		nmonthlyWC = size;
		sortAndAddVectorData(__monthJList, v);
        }

        JGUIUtil.addComponent(listJPanel,
		new JScrollPane(__monthJList),
                0, 3, 2, 1, 1, 1, NLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

        JGUIUtil.addComponent(listJPanel, new JLabel(
		"Daily Data (Count, Data Type/SFUT, Period, "
		+ "Available):"), 
                3, 2, 2, 1, 0, 0, NLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	__dayJList = new DragAndDropSimpleJList(
		DragAndDropUtil.ACTION_COPY, DragAndDropUtil.ACTION_NONE);
        JGUIUtil.addComponent(listJPanel,
		new JScrollPane(__dayJList),
                3, 3, 2, 1, 1, 1, NLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

        //
        // DAILY TIME SERIES
        // 
        v = getStructureMeasType(__TOTAL_DAILY, "DivTotal");
        if (v != null) {
                o = v.get(0);
		addListItem(o, "DivTotal", __dayJList);
        }

        v = getStructureMeasType(__TOTAL_DAILY, "RelTotal");
        if (v != null) {
                o = v.get(0);
		addListItem(o, "RelTotal", __dayJList);
        }

        // get the daily water color and add the list items
        // and periods of record accordingly
        v = getStructureMeasType(__DAILY_WC, null);
	int ndailyWC = 0;
        if (v != null) {
                int size = v.size();
		ndailyWC = size;
		sortAndAddVectorData(__dayJList, v);
        }

	// Create a panel at the bottom for the period, buttons, and status
	// JTextField...

        // Period JPanel
        JPanel periodJPanel = new JPanel();
        periodJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        periodJPanel.add(new JLabel("Output Period:"));
        
        __fromJTextField = new JTextField(20);
        __fromJTextField.setEditable(false);
        periodJPanel.add(__fromJTextField);

        periodJPanel.add(new JLabel("to"));

        __toJTextField = new JTextField(20);
        __toJTextField.setEditable(false);
        periodJPanel.add(__toJTextField);
        
	SimpleJButton set = new SimpleJButton(__BUTTON_SET_PERIOD, this);
	set.setToolTipText("Set date period.");
        periodJPanel.add(set);

        __originalJButton = new SimpleJButton(__BUTTON_ORIGINAL_PERIOD, this);
        __originalJButton.setEnabled(false);
	__originalJButton.setToolTipText("Set period to original period.");
        periodJPanel.add(__originalJButton);

        // Button JPanel
        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

	__graphJButton = new SimpleJButton(__BUTTON_GRAPH, this); 
	__graphJButton.setToolTipText("Graph time series.");
        buttonJPanel.add(__graphJButton);
	__graphJButton.setEnabled(false);
	__summaryJButton = new SimpleJButton(__BUTTON_SUMMARY, this);
	__summaryJButton.setToolTipText("Create summary of time series.");
        buttonJPanel.add(__summaryJButton);
	__summaryJButton.setEnabled(false);
	__tableJButton = new SimpleJButton(__BUTTON_TABLE, this);
	__tableJButton.setToolTipText("Show time series in tabular form.");
        buttonJPanel.add(__tableJButton);
	__tableJButton.setEnabled(false);
	__reportJButton = new SimpleJButton(__BUTTON_DIVERSION_REPORT, this);
	__reportJButton.setToolTipText("Show annual water diversion report for "
		+ "daily data.");
	buttonJPanel.add(__reportJButton);
	__reportJButton.setEnabled(false);
	SimpleJButton close = new SimpleJButton(__BUTTON_CLOSE, this);
	close.setToolTipText("Close form.");
        buttonJPanel.add(close);

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);

        // South South JPanel
        JPanel southJPanel = new JPanel();
	southJPanel.setLayout(new BorderLayout());
        southJPanel.add("North", periodJPanel);
        southJPanel.add("Center", buttonJPanel);
        southJPanel.add("South", __statusJTextField);
        getContentPane().add("South", southJPanel);

	String rest = "Structure Data - Diversion Coding - "
			+ HydroBase_WaterDistrict.formWDID(
			__structureView.getWD(), __structureView.getID())
			+ " (" + name + ") - Select Time Series";

	if (	(JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( rest );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() +
		" - " + rest);
	}			

        pack();	
	setSize(getWidth(), 500);
        JGUIUtil.center(this);
        setOriginalPeriodText();

	if ((ndailyWC == 0) && (nmonthlyWC == 0)) {
        	// Nothing to display.
		Message.printWarning(1, routine,
			"The structure has no diversion coding or reservoir " +
			"release time series to display.\n" +
			"See the Structure Summary for infrequent data and " +
			"diversion comments.");
		closeClicked();
		return;
	}
        setVisible(true);
	__monthJList.addListSelectionListener(this);
	__monthJList.addMouseListener(this);
	__dayJList.addListSelectionListener(this);
	__dayJList.addMouseListener(this);
	DragAndDropUtil.addDragAndDropListener(__monthJList, this);
	DragAndDropUtil.addDragAndDropListener(__dayJList, this);
}

/**
Sorts TS data and adds it to the given list by meas_type.
@param list the list to which data will be added.
@param data the Vector of data to go through.
*/
private void sortAndAddVectorData(DragAndDropSimpleJList list, List<HydroBase_StructMeasTypeView> data) {
	HydroBase_StructMeasTypeView o = null;
	int size = data.size();
	List<String> v = new ArrayList<String>();
	
	for (int i = 0; i < size; i++) {
		o = data.get(i);
		v.add(o.getMeas_type());
	}

	int[] order = new int[size];
	StringUtil.sortStringList(v, StringUtil.SORT_ASCENDING, order, true, true);
		
	for (int i = 0; i < size; i++) {
		o = data.get(order[i]);
		addListItem(o, null, list);
	}	
}

/**
Responds to selection changes in the lists.
*/
public void valueChanged(ListSelectionEvent e) {
	setOriginalPeriodText();
}

public void windowActivated(WindowEvent evt) {;}
public void windowClosed(WindowEvent evt) {;}
public void windowClosing(WindowEvent evt) {
        closeClicked();
}
public void windowDeactivated(WindowEvent evt) {;}
public void windowDeiconified(WindowEvent evt) {;}
public void windowOpened(WindowEvent evt) {;}
public void windowIconified(WindowEvent evt) {;}

}
