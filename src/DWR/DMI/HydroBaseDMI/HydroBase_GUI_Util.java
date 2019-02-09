// HydroBase_GUI_Util - GUI utilities class that is used by all the GUI applications

//------------------------------------------------------------------------------
// HydroBase_GUI_Util - Main class that is used by all the GUI applications.
//	It controls user preferences and general methods that can be called
//	by any form.
//------------------------------------------------------------------------------
//  Copyright: See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
// 2003-02-28	J. Thomas Sapienza, RTi	Initial version
// 2003-03-03	JTS, RTi		* Continued adding methods from the old
//					  code.
//					* Organized, cleaned, javadoc'd, 
//					  reviewed and revised code.
// 2003-03-04	JTS, RTi		Added code so that all fields in a query
//					are preceded by their table's name.
// 2003-03-05	JTS, RTi		SetGlobalData moved to HydroBaseDMI.
// 2003-03-11	JTS, RTi		Added getValue().
// 2003-03-12	JTS, RTi		* Added code to save user preferences
//					  back to the database.
//					* Cleaned up some old RE VISITs
// 2003-03-13	JTS, RTi		Added the setText methods for use by
//					many GUI forms.
// 2003-03-20	JTS, RTi		Revised af/er SAM's review.
// 2003-03-24	JTS, RTi		Moved in some header-formatting code.
// 2003-03-25	JTS, RTi		* Removed references to local dmi
//					* Moved user preferences code out
//					* Made methods static
// 2003-05-21	JTS, RTi		Reworked setWaterDistrictJComboBox so 
//					that if the user has selected the 
//					default water division and has a value 
//					set in the database preferences under
//					WD.DistrictDefault, the JComboBox will
//					select that district after the box has
//					been populated.  Otherwise, it will try
//					to select the active water division.
// 2003-09-23	JTS, RTi		* Added the getXXXFormatsAndExtensions
//					  methods.  
//					* Added the getXXXFilenameAndFormat
//					  methods.
//					* Added the export() code from the 
//					  old ExportGUI.
// 2003-09-30	JTS, RTi		Added trimText() methods.
// 2003-11-18	JTS, RTi		Added parseWD().
// 2003-11-25	SAM, RTi		* Add getTimeSeriesDataTypes() and
//					  getDefaultTimeSeriesDataType().
//					* Add convertToHydroBaseMeasType().
//					* Add isStationTimeSeriesDataType().
//					* Add isStructureTimeSeriesDataType().
//					* Add getTimeSeriesDataUnits().
//					* Add getTimeSeriesDataTypes().
// 2003-12-04	JTS, RTi		Added generateDivisions().
// 2003-12-17	SAM, RTi		* Add DATA_TYPE_* definitions to use as
//					  a mask with with the
//					  getTimeSeriesDataTypes() method and
//					  overload this method to use the mask.
//					  This is needed for greater flexibility
//					  getting a list for different tools.
// 2004-01-13	SAM, RTi		* Add addAlternateWellIdentifiers().
// 2004-02-08	SAM, RTi		* Change Agriculture data type group to
//					  include prefix from whether the data
//					  came from GIS or AgStats.
//					* Add isAgriculturalCropStats
//					  TimeSeriesDataType().
//					* Add isIrrigSummaryTimeSeriesDataType()
// 2004-02-11	JTS, RTi		Removed Summary export type.
// 2004-02-20	SAM, RTi		* Move isAgriculturalCropStatsTimeSeries
//					  DataType() to HydroBase_Util.
//					* Move getTimeSeries*() and related code
//					  to HydroBase_Util since this code is
//					  not really specific to GUIs.
//					* Move all other non-GUI code to
//					  HydroBase_Util.
//					* Add the create*InputFilterJPanel()
//					  methods - pulled out of TSTool.  These
//					  methods return reusable JPanels for
//					  input filters, possibly useful for
//					  StateView.
//					* Add createStructureGeoloc
//					  InputFilterPanel().
//					* Add the landuse/crop type to the input
//					  filter for irrig_summary_ts.
// 2004-05-19	SAM, RTi		* Add createWISInputFilterJPanel().
// 2004-06-22	JTS, RTi		Following a review of HydroBaseDMI,
//					moved in things from that class such
//					as table strings.
// 2004-07-30	JTS, RTi		Added
//			      		getTimeSeriesDataTypeAndIntervalFor
//					AppLayerType().
// 2004-08-28	SAM, RTi		Use specific HydroBase instances of
//					InputFilter_JPanel instead of the base
//					class.  Therefore, remove the
//					create*InputFilter methods.
// 2005-01-10	JTS, RTi		Added 
//					  getInputFilterGeoLocationWhereClause()
// 2005-01-31	JTS, RTi		Added getLocationFromInputFilterJPanel()
// 2005-02-01	JTS, RTi		* Added getSPFlexParameters()
//					* Added getSPFlexParametersTriplet()
// 2005-02-09	JTS, RTi		* Removed static strings that are
//					  no longer used.
//					* Removed getOrderByStrings().
//					* Removed getOrderBySyntax().
//					* Removed setSortOptions().
//					* Removed setWhereOptions().
//					* Removed getWhereStrings().
//					* Removed getStructureWhereClause().
//					* Removed getWaterRightsWhereClause().
// 2005-02-11	JTS, RTi		* Removed 
//					  getInputFilterGeoLocationWhereClause()
//					* Removed
//					  getLocationFromInputFilterJPanel()
// 2005-04-25	JTS, RTi		Added support for map query limits in
//					stored procedure queries.
// 2005-07-07	JTS, RTi		* Added support for querying All 
//					  Divisions at once.
//					* Added support for querying Division 8.
//					* Overloaded setWaterDistrictJComboBox.
// 2005-11-16	JTS, RTi		Recommented setWaterDistrictJComboBox()
//					because previous comments were 
//					unclear and insufficient.
// 2006-10-30	SAM, RTi		Add CASS livestock support.
//					Add CUPopulation support.
//------------------------------------------------------------------------------
// EndHeader

package DWR.DMI.HydroBaseDMI;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.GIS.GeoView.PLSSLocation;
import RTi.GIS.GeoView.PLSSLocationJDialog;

import RTi.GR.GRLimits;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleFileFilter;
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.IOUtil;
import RTi.Util.IO.SecurityCheck;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;

/**
Main class that is used by all the GUI applications.  It controls 
user preferences and general methods that can be called by any form.
*/
public class HydroBase_GUI_Util {

/**
Reference to a cancel when doing export and print type selection.
*/
public final static int CANCEL = -1;
/**
Reference to tab-delimited export and print type.
*/
public final static int TAB_DELIMITED = 0;
/**
Reference to comma-delimited export and print type.
*/
public final static int COMMA_DELIMITED = 1;
/**
Reference to semicolon-delimited export and print type.
*/
public final static int SEMICOLON_DELIMITED = 2;
/**
Reference to pipe-delimited export and print type.
*/
public final static int PIPE_DELIMITED = 3;
/**
Reference to screen view export and print type.
*/
public final static int SCREEN_VIEW = 4;
/**
Reference to qinfo report type.
*/
public final static int QINFO = 6;

/**
Used to choose to do a water right location query.
*/
public final static int WATER_RIGHT = 0;

/**
Used to choose to do a structure location query.
*/
public final static int STRUCTURE = 1;

/**
Used to choose to do a Well application query.
*/
public final static int WELL_APPLICATION = 2;

/**
Used to do a ground water location query
*/
public final static int GROUND_WATER = 3;

/**
The number of parameter triplets that SPFlex can handle.
*/
private final static int __NUM_TRIPLETS = HydroBaseDMI.getSPFlexMaxParameters();

/**
The user number of the user for which preferences were read from the database.
*/
// TODO SAM 2007-02-26 Evaluate whether needed.
//private static int __userNum = DMIUtil.MISSING_INT;

/**
The active water division.
*/
private static String __activeWaterDivision = "DEFAULT";

/**
Label string that appears by the query results table.
*/
public final static String LIST_LABEL = "Query Results List:";	

// TODO SAM 2006-10-30 Need to clarify what these are really used for... are they identifiers for
// windows?  Table names should not be used in the applications!
/**
Strings used by GUI objects.  These strings are used to separate database-
dependent table names, field names, etc from the GUI objects.
*/
protected static String 
	_ADMIN_NUM_SHORT, _AUG, _LEGAL_LOCATION, _NET_AMOUNTS,
	_NET_AMOUNTS_TABLE_NAME, _TRANS, _TRANS_TABLE_NAME,
	_LOCATION, _PRIORITY_NO, _STRUCTURE_TYPE, _USE, _WATER_SOURCE, 
	_ABAND_RIGHTS, _ALT_POINTS_WR, _AUG_PLAN_WR, _EXCHANGES, 
	_RIGHTS_AUG_PLAN, _CASE_NO, _STATUS_TYPE, _STRUCTURE,
	_STRUCTURE_TABLE_NAME,
	_GEOLOC,
	_GEOLOC_TABLE_NAME, _WELL_APPLICATION, _WELL_APPLICATION_TABLE_NAME,
	_STATION_TABLE_NAME, _PLSS_LOCATION, _PLSS_LOCATION_LABEL,
	_MEASTYPE_TABLE_NAME, _CASS_TABLE_NAME, _CASS_LIVESTOCK_TABLE_NAME,
	_NASS_TABLE_NAME, _ALL_DIVISIONS, _DIVISION_8;

/**
Assigns string values to variables.  This is not done statically so that
strings can be versioned and assigned based on the proper version at run-time.
*/
static {
//	if (getDatabaseVersion()) {
// From the Water Rights Query GUI
	_ADMIN_NUM_SHORT = "Admin. Number";
	_LEGAL_LOCATION = "Legal Location";
	_NET_AMOUNTS = "Net Amounts";
	_NET_AMOUNTS_TABLE_NAME = "net_amts";
	_TRANS = "Transaction List";
	_TRANS_TABLE_NAME = "transact";

	_LOCATION = "Location";	
	_PRIORITY_NO = "Priority Number";//*
	_STRUCTURE_TYPE = "Structure Type";//*
	_USE = "Use";//*
	_WATER_SOURCE = "Water Source";//*
	_ABAND_RIGHTS = "Abandoned Rights by Status Type (C, A, or CA)";	//*
	_ALT_POINTS_WR = "Alt. Points by Water Right Name";//*
	_AUG_PLAN_WR = "Aug. Plans by Water Right Name";	
	_EXCHANGES = "Exchanges by Water Right Name";//*
	_RIGHTS_AUG_PLAN = "Rights associated with an Aug. Plan";	
	_CASE_NO = "Rights associated with Case No.";//*
	_STATUS_TYPE = "Status Type (C, A, or CA)";	

// From the Structure Query GUI
	_STRUCTURE = "Structure";
	_STRUCTURE_TABLE_NAME = "structure";

// From the Station GUI
	_GEOLOC = "Geoloc";
	_GEOLOC_TABLE_NAME = "geoloc";
	_MEASTYPE_TABLE_NAME = "meas_type";
	_STATION_TABLE_NAME = "station";

// From the Well Application GUI
	_WELL_APPLICATION = "Well Application";
	_WELL_APPLICATION_TABLE_NAME = "well_application";

	_CASS_TABLE_NAME = "Agricultural_CASS_Crop_stats";
	_CASS_LIVESTOCK_TABLE_NAME = "Agricultural_CASS_Livestock_stats";
	_NASS_TABLE_NAME = "Agricultural_NASS_Crop_stats";

// The following are used for constraining queries by PLSS Location.
// _PLSS_LOCATION is the label used internally in the InputFilters to refer to a location constraint
	_PLSS_LOCATION = "PLSS_Location";
// _PLSS_LOCATION_LABEL is what the user sees in the InputFilters on the GUI.
	_PLSS_LOCATION_LABEL = "PLSS Location";

	_ALL_DIVISIONS = "Entire State";
	_DIVISION_8 = "Division 8: Designated Basin";
}	

/**
Adds a triplet to the parameter list in the first "where" triplet position that is available.
TODO SAM 2016-08-17 Why is the first position in the array, [0], always skipped and therefore -999?
@param parameter the parameter list that has already been set up.
@param triplet the triplet to put in the parameter list.
*/
public static void addTriplet(String[] parameters, String[] triplet) {
	for (int i = 0; i < __NUM_TRIPLETS; i++) {
	    // Find first open slot for "where" and fill with the provided information.
		if (parameters[1 + (i * 3)].equals("-999")) {
			parameters[1 + (i * 3)] = triplet[0];
			parameters[2 + (i * 3)] = triplet[1];
			parameters[3 + (i * 3)] = triplet[2];
			return;
		}
	}
    // Trying to add a where more than the maximum that can be handled by SPFlex
    String message = "Trying to add where clause (" + triplet[0] + " " + triplet[1] + " " +
        triplet[2] + ") to SPFlex - exceeding limit of " + __NUM_TRIPLETS;
    throw new RuntimeException ( message );
}

/**
Appends "AND" to the String s if s is not zero length.  If s is null, a null
value will be returned.  This function pertains to database queries and is
used when building where clauses for GUIs.  Used by getLocationSyntax().
@param s String to append "AND" to.
@return returns the string s appended accordingly.
*/
private static String appendAnd(String s) {
	if (s == null) {
		return null;
	}

	s = s.trim();

	if (!s.equals("")) {
		s += " AND ";
	}		
	return s;
}

/**
Builds a location string and puts it in the given text field.  If the text
field already contains a location string, that string is edited instead.
@param textField the textField for which to create a location string or edit an existing one.
*/
public static void buildLocation(JFrame frame, JTextField textField) {
	String routine = "HydroBase_GUI_Util.buildLocation";
	try {
		PLSSLocation location = null;
		if (textField.getText().trim().equals("")) {
			location = (new PLSSLocationJDialog(frame, true)).response();
			if (location != null) {
				textField.setText(location.toString(true, false));
			}
		}
		else {
			location = PLSSLocation.parse(textField.getText(),true);
			location = (new PLSSLocationJDialog(frame, location, true)).response();
			if (location != null) {
				textField.setText(location.toString(true, false));
			}
		}
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Unable to create GUI for building legal location.");
		Message.printWarning(2, routine, e);
	}
}

/**
Exports a Vector of strings to a file.
@param parent the JFrame on which hourglass information should be displayed.
@param filename the name of the file to which to write.
@param strings a non-null list of Strings, each element of which will be another line in the file.
*/
public static void export(JFrame parent, String filename, List<String> strings)
throws Exception {
	String routine = "HydroBase_GUI_Util.export";
	// First see if we can write the file given the security settings...
	if (!SecurityCheck.canWriteFile(filename)) {
		Message.printWarning(1, routine, "Cannot save \"" + filename + "\".");
		throw new Exception( "Security check failed - unable to write \"" + filename + "\"");
	}

	JGUIUtil.setWaitCursor(parent, true);

	// Create a new FileOutputStream wrapped with a DataOutputStream for writing to a file.
	PrintWriter oStream = null;
	try {
		oStream = new PrintWriter( new FileWriter(filename));

		// Write each element of the strings list to a file.
		String linesep = System.getProperty("line.separator");
		for (int i = 0; i < strings.size(); i++) {
			oStream.print( strings.get(i) + linesep);
		}
	}
	catch (Exception IOError) {
		JGUIUtil.setWaitCursor(parent, false);
		throw new Exception ( "Trouble opening or writing to file \"" + filename + "\"." );
	}
	finally {
		if ( oStream != null ) {
			oStream.close();
		}
	}

	JGUIUtil.setWaitCursor(parent, false);
}

/**
Given an array of stored procedure parameters, this will fill in missing 
information, which includes the viewNumber to run, the order by information, and the mapQueryLimit information.<p>
<b>NOTE:</b> currently order by and mapquery information are not filled in.
@param parameters the array of stored procedure parameters to fill in.
@param viewNumber the internal number of the SPFlex view to run.
@param orderBys the order by statement that should be taken into account with
the query.  If no ordering is to be done with the query, this should be less than or equal to 0.
@param mapQueryLimits the mapquerylimits to use in limiting the geographic area of the query.
*/
protected static void fillSPParameters(String[] parameters, String viewNumber, int orderBy, GRLimits mapQueryLimits)
{
	parameters[0] = viewNumber;
	if (orderBy > 0) {
		parameters[parameters.length -2] = "" + orderBy;
	}
	else {
		parameters[parameters.length - 2] = "-999";
	}
	parameters[parameters.length - 1] = "CDSS";

	int FIRST_PARAMETER = 1; // Skip 0th element, which is the stored procedure number

	if (mapQueryLimits == null) {
		return;
	}

	String queryString = "" 
		+ mapQueryLimits.getLeftX() + ","
		+ mapQueryLimits.getRightX() + ","
		+ mapQueryLimits.getBottomY() + ","
		+ mapQueryLimits.getTopY();

	for (int i = FIRST_PARAMETER; i <= FIRST_PARAMETER + (__NUM_TRIPLETS * 3); i += 3) {
	    	if (parameters[i].equals("-999")) {
			// Missing parameter, so fit the map query limits in this position.
			parameters[i] = "utm_bounds";
			parameters[i + 1] = "EQ";
			parameters[i + 2] = queryString;
			return;
		}
	}

	Message.printWarning(2, "fillSPParameters", "There were no free "
		+ "parameter positions into which Map Query Limits "
		+ "(utm_bounds) could be placed.  They will not be used in the query.");
}

/**
Format a structure header string used in output.
@param format the format that specifies which delimiter will be used.
@return the structure header string used in output.
*/
public static String formatStructureHeader (int format) {
	char delim = getDelimiterForFormat (format);
	return "STRUCTURE NAME" + delim + "DIV" + delim + "WD" + delim + "ID" + delim;
}

/**
Format a structure header string used in output.
@param structure_name the name of the structure 
@param div the name of the div
@param wd the wd of the structure
@param id the id of the structure
@return the structure header string used in output.
*/
public static String formatStructureHeader(String structure_name, String div, String wd, String id) {
	return formatStructureHeader(structure_name, div, wd, id, QINFO);
}

/**
Format a structure header string used in output.
@param structure_name the name of the structure 
@param div the name of the div
@param wd the wd of the structure
@param id the id of the structure
@return the structure header string used in output.
*/
public static String formatStructureHeader(String structure_name, String div, String wd, String id, int format) {
	if ((format == QINFO) || (format == SCREEN_VIEW)) {
		return 	"STRUCTURE NAME: " 
			+ StringUtil.formatString (structure_name, "%-40.40s")
			+ "  DIV: " + StringUtil.formatString (div, "%-2.2s")
			+ "  WD: " + StringUtil.formatString (wd, "%-2.2s")
			+ "  ID: " + StringUtil.formatString (id, "%-5.5s");
	}
	else {	
		// delimited...
		char delim = getDelimiterForFormat (format);
		return "" + structure_name + delim + div + delim + wd + delim + id + delim;
	}
} 

/**
Generates the Divisions as specified in the display preferences and returns them in a list.
@param dmi the dmi to use for talking to the database.
@return a list with the divisions in it.
*/
public static List<String> generateDivisions(HydroBaseDMI dmi) {
        // retrieve divisions
        String divisions = dmi.getPreferenceValue("WD." + getActiveWaterDivision() + ".DivisionSelect");
	if (divisions == null) {
		// Try the old-style...
        divisions = dmi.getPreferenceValue("WD.DivisionSelect");
	}

	List<String> v = new ArrayList<String>();

    StringTokenizer st = new StringTokenizer(divisions.trim(),",");
    // add divisions to the Choice component
	String curToken = null;
        while (st.hasMoreTokens()) {
            curToken = st.nextToken();
            v.add("Division " + curToken);
        }

	return v;
}

/**
This function generates the water Districts as specified in the user
preferences WD.ActiveDivision.DistrictSelect property.
@return a list of water districts according to user preferences.
*/
public static List<String> generateWaterDistricts(HydroBaseDMI dmi) {
	return generateWaterDistricts(dmi, false);
}

/**
This function generates the water Districts as specified in the user
preferences WD.ActiveDivision.DistrictSelect property.
@return a Vector of water districts according to user preferences.
TODO (JTS - 2004-06-03) is this stuff working properly?  For the load wis sheet, this method was not
returning enough values, so we added the ignore parameter (for now).  How
did this work in the old code?
*/
public static List<String> generateWaterDistricts(HydroBaseDMI dmi, boolean ignore) {
	if (ignore) {
		// preferences have not been set so default...
        String districts = "1,2,3,4,5,6,7,8,9,23,48,49,64,65,76,80,10,11," +
        	"12,13,14,15,16,17,18,19,66,67,79,20,21,22,24,25,26,27,35," +
        	"28,40,41,42,59,60,61,62,63,68,73,36,37,38,39,45,50,51,52," +
        	"53,70,72,43,44,47,54,55,56,57,58,29,30,31,32,33,34,46,69," +
        	"71,77,78,";       
    	List<String> v = StringUtil.breakStringList(districts.trim(), ",", StringUtil.DELIM_SKIP_BLANKS);
        return v;		
	}
	
    String districts = dmi.getPreferenceValue("WD." + getActiveWaterDivision() + ".DistrictSelect");
	if (districts == null || districts.equals("")) {
		// Old-style...
		districts = dmi.getPreferenceValue("WD.DistrictSelect");
	}

	if ((districts == null)  || districts.equals("") || districts.equals("NONE")) {
		// preferences have not been set so default...
    	districts = "1,2,3,4,5,6,7,8,9,23,48,49,64,65,76,80,10,11," +
    		"12,13,14,15,16,17,18,19,66,67,79,20,21,22,24,25,26,27,35," +
    		"28,40,41,42,59,60,61,62,63,68,73,36,37,38,39,45,50,51,52," +
    		"53,70,72,43,44,47,54,55,56,57,58,29,30,31,32,33,34,46,69," +
    		"71,77,78,";       
		dmi.setPreferenceValue("WD." + getActiveWaterDivision() + ".DistrictSelect",districts);
	}
	List<String> v = StringUtil.breakStringList(districts.trim(), ",", StringUtil.DELIM_SKIP_BLANKS);
    return v;
}

/**
Return the active water division as "DIV1", "DIV2", "DIV3", "DIV4", "DIV5",
"DIV6", "DIV7", or "DEFAULT".  This is used to look up the map and other properties.
@return the active water division.
*/
public static String getActiveWaterDivision() {	
	return __activeWaterDivision;
}

/**
Given a list of aquifers, returns a String that is suitable for display as a Tooltip.
@param aquifiers a list of HydroBase_Aquifer.  Can be null.
@return a String that can be used as a tool tip.  Will never be null.
*/
protected static String getAquiferListForToolTip(List<HydroBase_Aquifer> aquifers) {
	if (aquifers == null || aquifers.size() == 0) {
		return "";
	}

	HydroBase_Aquifer a = null;
	int size = aquifers.size();
	StringBuffer buffer = new StringBuffer();
	for (int i = 0; i < size; i++) {
		a = aquifers.get(i);
		buffer.append("  " + a.getAquifer_code());
		buffer.append(" - ");
		buffer.append(a.getAquifer_name());
		buffer.append("<br>");
	}
	
	return buffer.toString();
}
	
/**
Returns the kind of character delimiter for a given delimited format.  If an
unrecognized format type is passed in, a pipe ('|') will be returned.
@return the kind of character delimiter for a given delimited format.
*/
public static char getDelimiterForFormat(int format) {
	if (format == TAB_DELIMITED) {
		return '\t';
	}
	else if (format == COMMA_DELIMITED) {
		return ',';
	}
	else if (format == SEMICOLON_DELIMITED) {
		return ';';
	}
	else if (format == PIPE_DELIMITED) {
		return '|';
	}
	else {
		return '\t';
	}
}

/**
Returns a list of all the names of the delimited formats.
@return a list of all the names of the delimited formats.
*/
public static List<String> getDelimitedFormats() {
	List<String> v = new ArrayList<String>();
	v.add("Tab-Delimited");
	v.add("Comma-Delimited");
	v.add("Semicolon-Delimited");
	v.add("Pipe-Delimited");
	return v;
}

/**
Returns a list of all the delimited formats and their extensions.  The format 
of the list is a list of 2-element String arrays.  The first element is the
English description of the delimited format and the second element is the filename extension.
@return a list of all the delimited formats and their extensions.
*/
public static List<String []> getDelimitedFormatsAndExtensions() {
	List<String []> v = new ArrayList<String []>();
	String[] s = new String[2];
	s[0] = "Tab-Delimited";
	s[1] = "txt";
	v.add(s);
	s = new String[2];
	s[0] = "Comma-Delimited";
	s[1] = "csv";	
	v.add(s);
	s = new String[2];
	s[0] = "Semicolon-Delimited";
	s[1] = "txt";
	v.add(s);
	s = new String[2];
	s[0] = "Pipe-Delimited";
	s[1] = "txt";
	v.add(s);

	return v;
}

/**
This function returns a list of selected Divisions based on the user preferences.
@return a Vector of Divisions.
*/
public static List<String> getDivisions(HydroBaseDMI dmi) {
    String divString = dmi.getPreferenceValue("WD." + getActiveWaterDivision() + ".DivisionSelect");       

	if (divString == null || divString.equals("")) {
		// Old-style...
		divString = dmi.getPreferenceValue("WD.DivisionSelect");       
	}
	if ((divString == null)  || divString.equals("") || divString.equals("NONE")) {
		// Preferences have not been set so default to guest...
		divString = "1,2,3,4,5,6,7,";
		//setValue("WD.DivisionSelect", divString);
		dmi.setPreferenceValue("WD." + getActiveWaterDivision() + ".DivisionSelect", divString);
	}
	List<String> divVector = StringUtil.breakStringList(divString.trim(), ",", StringUtil.DELIM_SKIP_BLANKS);
    return divVector;
}

/**
Returns the filename and format that a used selects from a File chooser dialog that is labeled with "Export".
@param parent the parent JFrame on which the file chooser will appear.
@param formats a list of the valid formats for the file chooser.
@return a String array where the first element is the selected filename and the
second element is the type of file selected.
*/
public static String[] getExportFilenameAndFormat(JFrame parent,List<String []> formats){
	return getFilenameAndFormat(parent, "Export", formats);
}

/**
Returns the filename and format type of a file selected from a file chooser.
Also sets the last selected file dialog directory to whatever directory the
file is located in, if the file selection was approved (i.e., Cancel was not pressed).
@param parent the JFrame on which the file chooser appears.
@param title the title of the file chooser.
@param formats a list of the valid formats for the file chooser.
@return a two-element String array where the first element is the name of the
file and the second element is the type of format selected (in String format, though it's actually an integer).
*/
private static String[] getFilenameAndFormat(JFrame parent, String title, List<String []> formats) {	
	JGUIUtil.setWaitCursor(parent, true);
	String dir = JGUIUtil.getLastFileDialogDirectory();
	JFileChooser fc = null;
	if (dir != null) {
		fc = new JFileChooser(dir);
	}
	else {
		fc = new JFileChooser();
	}
	fc.setDialogTitle(title);
	String[] s = null;
	SimpleFileFilter sff = null;
	SimpleFileFilter first = null;
	int count = 0;
	for (int i = 0; i < formats.size(); i++) {
		s = formats.get(i);
		if (!s[1].equals("") && !s[0].equals("")) {
			sff = new SimpleFileFilter(s[1], s[0]);
			fc.addChoosableFileFilter(sff);
			if (count == 0) {
				first = sff;
				count++;
			}
		}
	}
		
	fc.setAcceptAllFileFilterUsed(false);
	fc.setFileFilter(first);		
	fc.setDialogType(JFileChooser.SAVE_DIALOG);

	JGUIUtil.setWaitCursor(parent, false);
	int returnVal = fc.showSaveDialog(parent);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
		String[] ret = new String[2];
		String filename = fc.getCurrentDirectory() + File.separator + fc.getSelectedFile().getName();
		JGUIUtil.setLastFileDialogDirectory("" + fc.getCurrentDirectory());
		sff = (SimpleFileFilter)fc.getFileFilter();

		// this will always return a one-element vector
		List<String> extensionV = sff.getFilters();

		String extension = extensionV.get(0);
		
		String desc = sff.getShortDescription();
		int type = -1;
		for (int i = 0; i < formats.size(); i++) {
			s = formats.get(i);
			if (desc.equals(s[0])) {
				type = i;
				i = formats.size() + 1;
			}
		}

		ret[0] = IOUtil.enforceFileExtension(filename, extension);
		ret[1] = "" + type;

		return ret;
	}
	else {
		return null;
	}
}

/**
Returns the list of possible export formats for use in a 
SelectFormatTypeJDialog.  Note that the order in which these are added to the
Vector MUST match the number of the formats above (*_DELIMITED).  E.g., 
Tab-delimited must be at the 0th position because it has a defined value of 0 in TAB_DELIMITED.
@return the list of export formats in a Vector.
*/
public static List<String> getFormats() {
	List<String> v = new ArrayList<String>();
	v.add("Tab-Delimited");
	v.add("Comma-Delimited");
	v.add("Semicolon-Delimited");
	v.add("Pipe-Delimited");
	v.add("Screen View");

	return v;
}

/**
Returns a list of all the output formats and their extensions.  The format of 
the list is a list of 2-element String arrays.  The first element is the 
English description of the format, and the second element is the filename extension.
@return a list of all the output formats and their extensions.
*/
public static List<String []> getFormatsAndExtensions() {
	List<String []> v = new ArrayList<String []>();
	String[] s = new String[2];
	s[0] = "Tab-Delimited";
	s[1] = "txt";
	v.add(s);
	s = new String[2];
	s[0] = "Comma-Delimited";
	s[1] = "csv";	
	v.add(s);
	s = new String[2];
	s[0] = "Semicolon-Delimited";
	s[1] = "txt";
	v.add(s);
	s = new String[2];
	s[0] = "Pipe-Delimited";
	s[1] = "txt";
	v.add(s);
	s = new String[2];
	s[0] = "Screen View";
	s[1] = "txt";
	v.add(s);

	return v;
}

/**
Builds the syntax for a location where clause. 
The isString syntax expects the following format in order:<br>
<ol>
   <li>ts</li>
   <li>tdir</li>
   <li>rng</li>
   <li>rdir</li>
   <li>sec</li>
   <li>q160</li>
   <li>q40</li>
   <li>q10</li>
</ol>
@param isString comma-deliminated location String 
@param tableName the name of the table in which this will be used as a query
@param form HydroBase_GUI_Util.WATER_RIGHT is passed if 
querying HydroBase_NetAmts or HydroBase_Transact , 
HydroBase_GUI_Util.STRUCTURE is passed if querying HydroBase_StructureGeoloc.
@return string to add as a where clause or null if where clause is not to be added to the query.
*/
public static String getLocationSyntax(String isString, String tableName, int form)
throws Exception { 
	isString = isString.trim();

	// check for wild card first
	if (isString.equals("*") || isString.equals("%")) {
		return null;
	}
	
	PLSSLocation location = new PLSSLocation(isString, true);
    String whereFormat = "";
	String pm = location.getPM();
    int ts = location.getTownship();
    String tdir = location.getTownshipDirection();
    int rng = location.getRange();
    String rdir = location.getRangeDirection();
	int sec = location.getSection();
	String half = location.getHalfSection();
	String q160 = location.getQ();
	String q40 = location.getQQ();
	String q10 = location.getQQQ();

	if (!PLSSLocation.unset(pm)) {
        whereFormat = tableName + "." + "pm Like '%" + pm  + "%'";
	}

	String s = null;

    // ts and tdir
   	if (form == HydroBase_GUI_Util.WATER_RIGHT) {
		s = "";
		if (!PLSSLocation.unset(ts)) {
			s = "" + ts;
		}
		if (!PLSSLocation.unset(tdir)) {
			s += " " + tdir;
		}
		if (!s.trim().equals("")) {
			whereFormat = appendAnd(whereFormat);
			whereFormat += " " + tableName + "." + "ts Like '%" + s + "%'";
		}
   	}
	else if (form == HydroBase_GUI_Util.STRUCTURE 
	   	|| form == HydroBase_GUI_Util.WELL_APPLICATION 
		|| form == HydroBase_GUI_Util.GROUND_WATER) {
		if (!PLSSLocation.unset(ts)) {
			whereFormat = appendAnd(whereFormat);
            whereFormat += tableName + "." + "ts = " + ts;
		}
		if (!PLSSLocation.unset(tdir)) {
			whereFormat = appendAnd(whereFormat);
            whereFormat += tableName + "." + "tdir Like '%" + tdir + "%'";
		}
	}

	if (form == HydroBase_GUI_Util.WATER_RIGHT) {
		s = "";
		if (!PLSSLocation.unset(rng)) {
			s = "" + rng;
		}
		if (!PLSSLocation.unset(rdir)) {
			s += " " + rdir;
		}
		if (!s.trim().equals("")) {
			whereFormat = appendAnd(whereFormat);
			whereFormat += " " + tableName + "." + "rng Like '%" + s + "%'";
		}
   	}
   	else if (form == HydroBase_GUI_Util.STRUCTURE 
		|| form == HydroBase_GUI_Util.WELL_APPLICATION 
		|| form == HydroBase_GUI_Util.GROUND_WATER) {
		if (!PLSSLocation.unset(rng)) {
			whereFormat = appendAnd(whereFormat);
            whereFormat += tableName + "." + "rng = " + rng;
		}
		if (!PLSSLocation.unset(rdir)) {
			whereFormat = appendAnd(whereFormat);
            whereFormat += tableName + "." + "rdir Like '%" + rdir + "%'";
		}
	}

	if (!PLSSLocation.unset(sec)) {
		whereFormat = appendAnd(whereFormat);
		whereFormat += tableName + "." + "sec = " + sec;
	}

	if (!PLSSLocation.unset(half)) {
		whereFormat = appendAnd(whereFormat);
		whereFormat += tableName + "." + "seca Like '%" + half + "%'";
	}

    if (!PLSSLocation.unset(q160)){ 
		whereFormat = appendAnd(whereFormat);
   		whereFormat += tableName + "." + "q160 Like '" + q160 + "'";
	}

	if (!PLSSLocation.unset(q40)) {
		whereFormat = appendAnd(whereFormat);
		whereFormat += tableName + "." + "q40 Like '" + q40 + "'";
	}

	if (!PLSSLocation.unset(q10)) {
		whereFormat = appendAnd(whereFormat);
		whereFormat += tableName + "." + "q40 Like '" + q10 + "'";
	}

	// check if whereFormat is empty. if so, return null so that records will be returned
	if (whereFormat.trim().equals("")) {
		return null;
	}

    return whereFormat;
}	

/**
Builds order by clauses from the fields selected in an InputFilter_JPanel.
@param panel the panel from which to get the fields that are selected for querying.
@param spflex if true then the order by is being built for an SPFlex method.
If false the order by is being built for an SQL query.
*/
public static List<String> getOrderBysFromInputFilter_JPanel( InputFilter_JPanel panel, boolean spflex) {
	InputFilter filter = null;
	int nfg = 0;
	if (panel != null) {
		nfg = panel.getNumFilterGroups();
	}
	List<String> orderBys = new ArrayList<String>();

	for (int i = 0; i < nfg; i++) {
		filter = panel.getInputFilter(i);	
		if (filter.getWhereLabel().trim().equals("")) {
			continue;
		}
		else if (filter.getWhereLabel().trim().equals(_PLSS_LOCATION_LABEL)) {
	    	continue;
		}
		else {
			if (spflex) {
				orderBys.add(filter.getWhereInternal2());
			}
			else {
				orderBys.add(filter.getWhereInternal());
			}
		}
	}
	return orderBys;
}

/**
Returns a list of all the names of the screen formats.
@return a list of all the names of the screen formats.
*/
public static List<String> getScreenFormats() {
	List<String> v = new ArrayList<String>();
	v.add("");
	v.add("");
	v.add("");
	v.add("");
	v.add("Screen View");

	return v;
}

/**
Returns a list of all the screen formats and their extensions.  The format of
the list is a list of String arrays.  The first element of the array is the
English description of the format and the second element is the filename extension.
@return a list of all the screen formats and their extensions.
*/
public static List<String []> getScreenFormatsAndExtensions() {
	List<String []> v = new ArrayList<String []>();
	String[] s = new String[2];
	s[0] = "";
	s[1] = "";
	v.add(s);
	s = new String[2];
	s[0] = "";
	s[1] = "";
	v.add(s);
	s = new String[2];
	s[0] = "";
	s[1] = "";
	v.add(s);
	s = new String[2];
	s[0] = "";
	s[1] = "";
	v.add(s);
	s = new String[2];
	s[0] = "Screen View";
	s[1] = "txt";
	v.add(s);

	return v;
}

/**
Returns the filename and format that a used selects from a File chooser dialog that is labeled with "Save As".
@param parent the parent JFrame on which the file chooser will appear.
@param formats a list of the valid formats for the file chooser.
@return a String array where the first element is the selected filename and the
second element is the type of file selected.
*/
public static String[] getSaveFilenameAndFormat(JFrame parent, List<String []> formats) {
	return getFilenameAndFormat(parent, "Save As", formats);
}

/**
Builds a SPFlex parameter array from an InputFilter_JPanel and the second 
element of the value returned from HydroBaseDMI.getWaterDistrictWhereClause().
@param panel the InputFilter_JPanel that contains the values for which to
run the query.  It can be null, in which case no InputFilter values will 
be included in the parameter list.
@param districtWhere the second element of the array return from 
HydroBaseDMI.getWaterDistrictWhereClause().  It will look like either:<p><pre>
	"DIV X"
or
	"WD X"
</pre>
where X is the Division or Water District for which to restrict the query.<p>
This parameter can currently be null, in which case it will not be included in the parameter list.
@return a 21-element array containing the parameters for the SPFlex query.
See HydroBaseDMI.runSPFlex() for more information.
@throws Exception if there is an error building the parameter array.
*/
public static String[] getSPFlexParameters(InputFilter_JPanel panel, String[] districtWhere) 
throws Exception {
	InputFilter filter = null;
	int nfg = 0;
	
	if (panel != null) {
		nfg = panel.getNumFilterGroups();
	}

	int num = (__NUM_TRIPLETS * 3) + 3;

	String[] triplet = null;
	String[] parameters = new String[num];

	int count = 1;

	for (int i = 0; i < num; i++) {
		parameters[i] = "-999";
	}

	// first put the information for limiting the water district or 
	// division in the parameter array.  The comparator is always "EQ".

	if (districtWhere != null) {	
		if (districtWhere[1].equals(HydroBase_GUI_Util._ALL_DIVISIONS)){
			parameters[count++] = "div";
			parameters[count++] = "NE";
			parameters[count++] = "-1";
		}
		else if (districtWhere[1].equals( HydroBase_GUI_Util._DIVISION_8)) {
		    parameters[count++] = "div";
			parameters[count++] = "EQ";
			parameters[count++] = "8";
		}
		else {
			int index = districtWhere[1].indexOf(" ");
			String field = districtWhere[1].substring(0, index);
			String value = districtWhere[1].substring(index).trim();
			
			parameters[count++] = field;
			parameters[count++] = "EQ";
			parameters[count++] = value;
		}
	}
	
	//for ( int i = 0; i < parameters.length; i++ ) {
	//	Message.printStatus(2,"getSPFlexParameters","Parameter[" + i + "] = \"" + parameters[i] + "\" after adding district where" );
	//}

	// Loop through all the InputFilters and put their values into 
	// the array.  getSPFlexParametersTriplet() will build an array 
	// with the field to query, the SPFlex comparator, and the value to query against.
	for (int ifg = 0; ifg < nfg; ifg++) {
		filter = panel.getInputFilter(ifg);
		//Message.printStatus(2,"getSPFlexParameters","filter="+filter.getWhereLabel()+";"+panel.getOperator(ifg)+";"+filter.getInputInternal().trim());
		triplet = getSPFlexParametersTriplet(filter, panel.getOperator(ifg));
		if (triplet != null) {
			// non-null triplets contain values and can be put into the array
			parameters[count++] = triplet[0];
			parameters[count++] = triplet[1];
			parameters[count++] = triplet[2];
		}
		else {
			//Message.printStatus(2,"getSPFlexParameters","triplet=null for input filter[" + ifg + "]");
			// null triplets mean the empty InputFilter was
			// selected.  "-999" is used as filler in the array
			// and was initialized above.  Don't add anything new and count is not incremented
		}
		//for ( int iParam = 0; iParam < parameters.length; iParam++ ) {
		//	Message.printStatus(2,"getSPFlexParameters","Parameter[" + iParam + "] = \"" + parameters[iParam] + "\" after processing filter group [" + ifg + "]" );
		//}
	}

	return parameters;
}

/**
Builds an array containing the SPFlex information for a query from an InputFilter.
@param filter the filter for which to build the SPFlex information.
@param op the operator selected for the InputFilter in the panel.
@return a three-element array containing:<p>
<b>0</b> - the field on which to constrain the query<br>
<b>1</b> - the SPFlex operator to apply between the field and the value<br>
<b>2</b> - the value against which to query<br>
<i>null</i> will be returned if no field was selected in the InputFilter.
@throws Exception if an error occurs.
*/
public static String[] getSPFlexParametersTriplet(InputFilter filter, String op) 
throws Exception {
	String[] triplet = new String[3];
	// Get the selected filter for the filter group...
	if (filter.getWhereLabel().trim().equals("")) {
		// Blank indicates that the filter should be ignored...
		return null;
	}

	// Get the input type...
	int inputType = filter.getInputType();
	if ( filter.getChoiceTokenType() > 0 ) {
		inputType = filter.getChoiceTokenType();
	}
	
	// Get the internal where.

	// Note:
	// getWhereInternal2() should always be used for stored procedure
	// SPFlex parameter building from InputFilters.  InputFilters can have
	// two where_internal values defined.  Typically, the first one is
	// used for non-stored procedure queries and the second one is used
	// for stored procedure queries.  However, some InputFilters are only
	// used with Stored Procedures, and so they have a where_internal 
	// set, but not a where_internal_2.  getWhereInternal2() handles this
	// by return where_internal_2, unless it is null.  In that case,
	// where_internal will be returned.
	triplet[0] = filter.getWhereInternal2();

	// Get the user input...
	triplet[2] = filter.getInputInternal().trim();

	if (op.equalsIgnoreCase(InputFilter.INPUT_BETWEEN)) {
		// TODO - need to enable in InputFilter_JPanel.
	}
	else if (op.equalsIgnoreCase( InputFilter.INPUT_CONTAINS)) {
		triplet[1] = "CN";
	}
	else if (op.equalsIgnoreCase(InputFilter.INPUT_ENDS_WITH)) {
		triplet[1] = "EW";
	}
	else if (op.equalsIgnoreCase(InputFilter.INPUT_EQUALS)){
		if (inputType == StringUtil.TYPE_STRING) {
			triplet[1] = "MA";
		}
		else {	
			triplet[1] = "EQ";
		}
	}
	else if (op.equalsIgnoreCase(InputFilter.INPUT_GREATER_THAN)) {
		triplet[1] = "GT";
	}
	else if (op.equalsIgnoreCase(InputFilter.INPUT_GREATER_THAN_OR_EQUAL_TO)) {
	    	triplet[1] = "GE";
	}
	else if (op.equalsIgnoreCase(InputFilter.INPUT_LESS_THAN)) {
		triplet[1] = "LT";
	}
	else if (op.equalsIgnoreCase(InputFilter.INPUT_LESS_THAN_OR_EQUAL_TO)) {
		triplet[1] = "LE";
	}
	else if (op.equalsIgnoreCase(InputFilter.INPUT_MATCHES)) {
		triplet[1] = "MA";
	}
	else if (op.equalsIgnoreCase(InputFilter.INPUT_ONE_OF)){
		// TODO - need to enable in InputFilter_JPanel
	}
	else if (op.equalsIgnoreCase(InputFilter.INPUT_STARTS_WITH)) {
		triplet[1] = "SW";
	}
	else {	
		throw new Exception("Unrecognized operator \"" + op + "\"...skipping..." );
	}

	return triplet;
}

/**
Returns the list of tables that a GUI can use to populate its list of possible tables to query.
@param gui the GUI for which to return the table SimpleJComboBox contents.
i.e., call this method with a gui parameter of 'this'
@return a list of Strings, each of which is a table name.  This method is
guaranteed to return a non-null list.
*/
public static List<String> getTableStrings(Object gui) {
	List<String> v = new ArrayList<String>();
	if (gui instanceof HydroBase_GUI_WaterRightsQuery) {
		v.add(_NET_AMOUNTS);
		v.add(_TRANS);
	}
	return v;
}

/**
Returns the time series data type and interval for a given app layer type.
@param appLayerType the kind of app layer to check.
@return a 2-element String array, the first element of which is the data type
and the second is the interval, or null if the app layer type is not found.
*/
public static String[] getTimeSeriesDataTypeAndIntervalForAppLayerType(String appLayerType) {
	String[] values = new String[2];
	if (appLayerType.equalsIgnoreCase("Streamflow")) {
		values[0] = "Streamflow";
		values[1] = "Month";
		return values;
	}
	else if (appLayerType.equalsIgnoreCase("Precipitation")) {
		values[0] = "Precip";
		values[1] = "Month";
		return values;
	}
	else if (appLayerType.equalsIgnoreCase("Temperature")) {
		values[0] = "Temp";
		values[1] = "Month";
		return values;
	}

	return null;
}

/**
Returns the user specified date using Y2K format
@param date user specified date String ( may or may not be Y2K format )
@return returns a String in Y2K format (e.g., 1998-01-05 01:00 );
*/
public static String getUserDate(String date)  {
	String y2k_date 	= null;
	DateTime user_DateTime 	= null;
	
	// value may be one of two formats, use the DateTime.parse()
	// to determine which one is specified.
	try {
		user_DateTime = DateTime.parse(date, DateTime.FORMAT_YYYY_MM_DD_HH_mm);
		y2k_date = date;
	}
	// exception detects that the date is not Y2K compliant, 
	// parse according to the old format and convert to Y2K.
	catch(Exception e1) {
		try {
			user_DateTime = DateTime.parse(date, DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY_HH_mm );

			y2k_date = user_DateTime.toString(DateTime.FORMAT_YYYY_MM_DD_HH_mm);
		}
		// parse String according to '/', ' ', and ':'
		catch (Exception e2) {
			try {
				List<String> v = StringUtil.breakStringList(date, "/ :", 0);
				y2k_date = StringUtil.formatString((String)v.get(2), "%04d")
					+ "-"
					+ StringUtil.formatString((String)v.get(0), "%02d")
					+ "-"
					+ StringUtil.formatString((String)v.get(1), "%02d")
					+ " "
					+ StringUtil.formatString((String)v.get(3), "%02d")
					+ ":"
					+ StringUtil.formatString((String)v.get(4), "%02d");
			}
			// unrecognized format, default to current system time.
			catch(Exception e3){
				y2k_date = new DateTime().toString(DateTime.FORMAT_YYYY_MM_DD_HH_mm);
			}
		}
	}
	return y2k_date;
}

/**
Create a list of where clauses give an InputFilter_JPanel.  The InputFilter
instances that are managed by the InputFilter_JPanel must have been defined with
the database table and field names in the internal (non-label) data.
@return a Vector of where clauses, each of which can be added to a DMI statement.
@param dmi The DMI instance being used, which may be checked for specific formatting.
@param panel The InputFilter_JPanel instance to be converted.  If null, an empty list will be returned.
*/
public static List<String> getWhereClausesFromInputFilter (HydroBaseDMI dmi, InputFilter_JPanel panel) 
throws Exception {
	return getWhereClausesFromInputFilter(dmi, panel, null, 0);
}

/**
Create a list of where clauses give an InputFilter_JPanel.  The InputFilter
instances that are managed by the InputFilter_JPanel must have been defined with
the database table and field names in the internal (non-label) data.
@return a list of where clauses, each of which can be added to a DMI statement.
@param dmi The DMI instance being used, which may be checked for specific formatting.
@param panel The InputFilter_JPanel instance to be converted.  If null, an empty list will be returned.
@param tableName the name of the table that should be used for Location
query information.  For Water Rights queries.
@param form the form for which to generate a where clause.  For Water Rights queries.
*/
public static List<String> getWhereClausesFromInputFilter (HydroBaseDMI dmi,
InputFilter_JPanel panel, String tableName, int form) 
throws Exception {
	// Loop through each filter group.  There will be one where clause per filter group.

	if (panel == null) {
		return new ArrayList<String>();
	}

	int nfg = panel.getNumFilterGroups ();
	InputFilter filter;
	List<String> where_clauses = new ArrayList<String>();
	String where_clause="";	// A where clause that is being formed.
	for ( int ifg = 0; ifg < nfg; ifg++ ) {
		filter = panel.getInputFilter ( ifg );	
		if (filter.getWhereLabel().equals(_PLSS_LOCATION_LABEL)) {
			where_clause = getLocationSyntax(filter.getInputInternal().trim(), tableName,form);
		}		
		else {
			where_clause = DMIUtil.getWhereClauseFromInputFilter(dmi, filter,panel.getOperator(ifg));
		}
		if (where_clause != null) {
			where_clauses.add(where_clause);
		}
	}
	return where_clauses;
}

/**
This function takes a String consisting of the water district number and name 
and returns the water district number.  The format of the districtNumber_name 
String consist of at most a two digit number in the left most portion of the 
string followed by a space and a dash which separates the number from the 
water district name.  In short, the water district number may always be found 
in the first two positions of the districtNumber_name String.  if the 
districtNumber_name begins with "Division" then a division number is present 
therefore return the Division number preceed by -99.
@return The water district number and name.
@param districtNumber_name Water district name.
@deprecated Use HydroBase_WaterDistrict.parseWD() or
HydroBase_WaterDistrict.parseWDID(). 
2005-04-19 still deprecated
*/
public static String parseWD (String districtNumber_name) {
	String parsedValue;
	String wd = districtNumber_name.trim();

	if (wd.startsWith("Division")) {
		parsedValue = "-99" + wd.substring(9);
	}
	else {	
		if (wd.length() < 2) {
			return wd;
		}
		parsedValue = wd.substring(0, 2);
	}
	return parsedValue.trim();
}

/**
Set the active water division as "DIV1", "DIV2", "DIV3", "DIV4", "DIV5",
"DIV6", "DIV7", or "DEFAULT".  This is used to look up the map and other properties.
@param div the active water division.
*/
public static void setActiveWaterDivision(String div) {
	__activeWaterDivision = div;
}

/**
Sets the text in the given text field from the specified Date.  If the value
of the Date matches DMIUtil.MISSING_DATE, the text field will be set to "".
This is a utility function used by all methods that insert data into textfields
so as to avoid X-hundred if statements.
@param t the JTextField into which the value should be placed.
@param dt the Date value to place into the JTextField.
*/
public static void setText(JTextField t, Date dt) {
	String s = "";
	if (!DMIUtil.isMissing(dt)) {
		s = "" + dt;
	}
	setText(t, s);
}

/**
Sets the text in the given text field from the specified double.  If the value
of the double matches DMIUtil.MISSING_DOUBLE, the text field will be set to "".
The double will be formatted with the specified format by StringUtil.formatString.
This is a utility function used by all methods that insert data into text fields
so as to avoid X-hundred if statements.
@param t the JTextField into which the value should be placed.
@param d the double value to place into the JTextField.
@param format the format with which to format the double.
*/
public static void setText(JTextField t, double d, String format) {
	String s = "";
	if (!DMIUtil.isMissing(d)) {
		s = StringUtil.formatString(d, format);
	}
	s = s.trim();
	setText(t, s);
}

/**
Sets the text in the given text field from the specified int.  If the value
of the int matches DMIUtil.MISSING_INT, the text field will be set to "".
This is a utility function used by all methods that insert data into textfields
so as to avoid X-hundred if statements.
@param t the JTextField into which the value should be placed.
@param i the int value to place into the JTextField.
*/
public static void setText(JTextField t, int i) {
	String s = "";
	if (!DMIUtil.isMissing(i)) {
		s = "" + i;
	}
	setText(t, s);
}

/**
Sets the text in the given text field from the specified String.  If the value
of the String matches DMIUtil.MISSING_String or if it is null
, the text field will be set to "".
This is a utility function used by all methods that insert data into textfields
so as to avoid X-hundred if statements.
Also sets the font for the text field (currently Helvetica 11 point plain).
@param t the JTextField into which the value should be placed.
@param s the String to place into the JTextField.
*/
public static void setText(JTextField t, String s) {
	if (s == null) {
		s = "";
	}
	if (DMIUtil.isMissing(s)) {
		s = "";
	}
	t.setText(s);
}

/**
@deprecated use the new one
*/
public static String setWaterDistrictJComboBox(SimpleJComboBox comboBox,
boolean removeAll, String wd, HydroBaseDMI dmi)
throws Exception {
	return setWaterDistrictJComboBox(dmi, comboBox, wd, removeAll, false, false);
}		

/**
This function generates the Divisions and water Districts as specified in
the display preferences and adds them to the SimpleJComboBox component. The districts are indented.
@param dmi the dmi to use for filling the combo box.
@param comboBox SimpleJComboBox object to add the water districts to.
@param wd Water district that MUST be included in the list (later add "*"?).  Can be null.
@param removeAll true if the SimpleJComboBox component is to be cleared prior to adding the water districts.
@param includeDivision8 if true, then Division 8 ("Designated Basin") will be included in the list.
@param includeAllDivisions if true, then "All Divisions" will be included in the list.
@return the Water District name of the one that MUST be included in the list, or "" if a null wd value was passed in.
@throws Exception if an error occurs.
*/
public static String setWaterDistrictJComboBox(HydroBaseDMI dmi, 
SimpleJComboBox comboBox, String wd, boolean removeAll, boolean includeDivision8, boolean includeAllDivisions)
throws Exception {
	String spacer = "  ";
	String requested = null;
	
	int wdNum = -1;
	if (wd != null) {
		wdNum = (new Integer(wd)).intValue();
	}

    // clear all the water districts
    if (removeAll) {
        comboBox.removeAll();
    }

    // retrieve water districts
    List<String> divisions = getDivisions(dmi);
    List<String> districts = generateWaterDistricts(dmi);

	if (divisions == null) {
		divisions = new ArrayList<String>();
	}

	if (districts == null) {
		districts = new ArrayList<String>();
	}

	// If specified, make sure that the requested district is in the list (no leading 0)...
	if (wd != null) {
		// Get the division for the water district...
		String wdString = "" + StringUtil.atoi(wd);
		int div = dmi.lookupWaterDivisionForDistrict(StringUtil.atoi(wd));
		String divString = "" + div;
		
		int size = districts.size();

		// iterate through the districts and see if the requested one is in the list.  

		boolean found = false;
		for (int i = 0; i < size; i++) {
			if (wdString.equals(districts.get(i))) {
				found = true;
				break;
			}
		}
		
		// If the requested district was not found, add it at the top of the list.
		if (!found) {
			districts.add(0,wdString);
		}

		size = divisions.size();

		// iterate through the divisions and see if the requested one is in the list.

		found = false;
		for (int i = 0; i < size; i++) {
			if (divString.equals(divisions.get(i))) {
				found = true;
				break;
			}
		}

		// If the requested division was not found, add it at the top of the list.
		if (!found) {
			divisions.add(0,divString);
		}
	}

	// There should be at least one division and one district in each 
	// list, otherwise an error of some sort occurred.

	if (divisions.size() == 0 || districts.size() == 0) {
		return "";
	}

	// Populate the combo box

	HydroBase_WaterDistrict currentDistrict = null;
	int currentWD = -1;
	int numDistricts = -1;
	int div = -1;
	int divSize = divisions.size();
	int j = -1;
	int k = -1;
	int prefWD = -1;
	List<HydroBase_WaterDistrict> waterDivisionDistricts = null;
	
	// Ensure that the divisions are in order.
	Collections.sort(divisions);
	
	// Iterate through all the divisions.
	
	for (int i = 0; i < divSize; i++) {
		// add the name of the division to the combo box
		div = StringUtil.atoi(divisions.get(i));
		comboBox.add(HydroBase_WaterDivision.getDivisionName(div));

		// get the list of water districts in the given division.
		waterDivisionDistricts = dmi.lookupWaterDistrictsForDivision(div);
		numDistricts = waterDivisionDistricts.size();

		// iterate through all the districts in the current division 
		for (j = 0; j < numDistricts; j++) {
			currentDistrict = (HydroBase_WaterDistrict)waterDivisionDistricts.get(j);
			currentWD = currentDistrict.getWD();
			
			// loop through all the districts that the user has specified they want to see.
			for (k = 0; k < districts.size(); k++) {
				prefWD = Integer.parseInt(districts.get(k).toString());

				// If the # of the current district matches the one passed in to this method, the requested
				// water district has been found.  Form its name so that it can be returned to the calling code.
				if (currentWD == wdNum && requested == null) {
					requested = spacer + currentWD + " - " + currentDistrict.getWd_name();
				}

				// if the # of the current district matches one of the ones the user wishes to see, add it
				// to the combo box and break out of the "k" loop.
				if (currentWD == prefWD) {
					comboBox.add(spacer + currentWD + " - " + currentDistrict.getWd_name());
					break;
				}

			}
		}
	}

	if (requested == null) {
		// it was not matched above
		requested = "";
	}
	
	// Make sure to add Division 8 at the bottom of the list if it is requested.
	if (includeDivision8) {
		comboBox.add(_DIVISION_8);
	}

	// Make sure to add the "Entire State" option at the bottom of the list if it is requested.
	if (includeAllDivisions 
	    && dmi.getDatabaseVersion() >= HydroBaseDMI.VERSION_20050701) {
		comboBox.add(_ALL_DIVISIONS);
	}

	// first check to see if a specific district has been set as a default district
	String s = dmi.getPreferenceValue("WD.DistrictDefault");

	if (s.equals(_ALL_DIVISIONS)) {
		comboBox.selectIgnoreCase(s);	
		return requested;
	}
	else if (s != null && !s.trim().equals("")) {
		comboBox.selectIgnoreCase(spacer + s);	
		return requested;
	}

	// select the default district according to user preferences
	String sdiv = getActiveWaterDivision();
	if (sdiv.equals("NONE") || sdiv.equals("DEFAULT")) {
		// no default district has been specified and no water 
		// division is active, so just set the first item in the list to be selected by default.
		comboBox.select(0);
	}
	else {
		if (sdiv.startsWith("DIV") && sdiv.length() > 3) {
			String num = sdiv.substring(3, 4);
			String property = HydroBase_WaterDivision.getDivisionName(StringUtil.atoi(num));		
			comboBox.select(property);
		}
	}

	return requested;
}

/**
Pulls out the string stored in a text field, checking if it is null and trimming it if it is not.
@return a String representing the data in the text field.
*/
public static String trimText(JTextField t) {
	String s = t.getText();
	if (s == null) {
		return "";
	}
	return s.trim();
}

/**
Pulls out the currently-selected String in a SimpleJComboBox 
checking if it is null and trimming it if it is not.
@return a String representing the data in the combo box.
*/
public static String trimText(SimpleJComboBox j) {
	String s = j.getSelected();
	if (s == null) {
		return "";
	}
	return s.trim();
}

}

/* REVISIT 
SAM - 2004-01-13 
	- printing dialog for selecting the format is ugly.  
	- CSV exporting in calls what not what was expected.  Probably need a
	  CSV export that includes header information and one that doesn't.
	  Right now we'll wait on feedback for this one.

JTS - 2004-01-19
	HydroBase_GUI_PumpTest - can't do screen or summary exports

*/
