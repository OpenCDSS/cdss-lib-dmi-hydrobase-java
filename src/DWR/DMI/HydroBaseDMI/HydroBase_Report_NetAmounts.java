// HydroBase_Report_NetAmounts - Class for generating Water Right reports.

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

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import RTi.DMI.DMIUtil;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;

/**
Class for generating water right reports.
*/
public class HydroBase_Report_NetAmounts {

/**
Value to specify to do a admin summary report.
*/
public static final int ADMIN_SUMMARY = 1;

/**
Value to specify to do a admin stream report.
*/
public static final int ADMIN_STREAM = 2;

/**
Value to specify to do a priority summary report.
*/
public static final int PRIORITY_SUMMARY = 3;

/**
Value to specify to do a priority stream report.
*/
public static final int PRIORITY_STREAM = 4;

/**
Value to specify to do a stream report.
*/
public static final int STREAM = 5;

/**
Value to specify to do a tabulation report.
*/
public static final int TABULATION = 6;


/**
Format string for an administrative summary report.
*/
public static final String ADMINISTRATIVE_SUMMARY_FORMAT = 
	"%-11.11s %-2.2s %9.9s%1.1s%5.5s %-24.24s %-2.2s "
	+ "%10.10s %10.10s %11.11s %11.11s %11.11s %11.11s";
/**
Format string for an alphabetical report.
*/
public static final String NET_AMTS_ALPHABETICAL_FORMAT = 
	"%-11.11s %-3.3s %9.9s %5.5s%1.1s%-24.24s %-2.2s %-10.10s "
	+ "%-10.10s %-80.80s";
/**
Format string for a priority summary report.
*/
public static final String PRIORITY_SUMMARY_FORMAT = 
	"%-12.12s %-5.5s %-24.24s %-9.9s %-5.5s %-3.3s %-4.4s " 
	+ "%-80.80s %-6.6s";	
/**
Format string for a tabulation report.
*/
public static final String TABULATION_FORMAT =
 	"%-24.24s %-3.3s %-20.20s %-2.2s %-24.24s %-3.3s %9.9s %10.10s " 
	+ "%-9.9s %-1.1s %-10.10s %-10.10s %-10.10s %-2.2s %-11.11s %4.4s " 
	+ "%-8.8s %4.4s";
/**
Format string for a tabulation report locations.
*/
public static final String TABULATION_LOCATION_FORMAT =
	"%2.2s %2.2s %2.2s %2.2s %2.2s%2.2s  %2.2s%2.2s %1.1s";

/**
Whether the current report is about to print a new stream node or not.
*/
private boolean __isNewStreamNode = true;

/**
Value into which absaf is accumulated.
*/
private double __absaf = DMIUtil.MISSING_DOUBLE;

/**
Value into which abscfs is accumulated.
*/
private double __abscfs = DMIUtil.MISSING_DOUBLE;

/**
Value into which condaf is accumulated.
*/
private double __condaf = DMIUtil.MISSING_DOUBLE;

/**
Value into which condcfs is accumulated.
*/
private double __condcfs = DMIUtil.MISSING_DOUBLE;

/**
Administration number from the previous right processed when generating 
administration reports.
*/
private double __lastAdminNum = DMIUtil.MISSING_DOUBLE;

/**
The previous absaf value.
*/
private double __prevAbsaf = DMIUtil.MISSING_DOUBLE;

/**
The previosu abscfs value.
*/
private double __prevAbscfs = DMIUtil.MISSING_DOUBLE;

/**
The previous condaf value.
*/
private double __prevCondaf = DMIUtil.MISSING_DOUBLE;

/**
The previous condcfs value.
*/
private double __prevCondcfs = DMIUtil.MISSING_DOUBLE;


/**
Hashtable to hold sums by station id value.
*/
private Hashtable<String,Double> __sums = null;

/**
Hashtable to hold AF sums by station id value.
*/
private Hashtable<String,Double> __sumsAF = null;

/**
Reference to an open DMI to be used for querying HydroBase.
*/
private HydroBaseDMI __dmi = null;

/**
The input filter jpanel that was passed in to the control the querying.
*/
private HydroBase_GUI_NetAmts_InputFilter_JPanel __filterPanel;

/**
The last type of report that was generated with this object.
*/
private int __lastReportType = DMIUtil.MISSING_INT;

/**
The district where clause passed in from the GUI from calling
getWaterDistrictWhereClause().
*/
private String[] __districtWhere = null;

/**
The name of the stream currently being operated on.
*/
private String __streamName = DMIUtil.MISSING_STRING;

/**
This is the list that holds all the lines of the report.
*/
private List<String> __reportList = null;

/**
This is the list that holds the results of the query that will be used
to fill out the reports.
*/
private List<HydroBase_NetAmts> __results = null;

/**
List of results from a query of the str_type table.
*/
private List<HydroBase_DssStructureType> __strtypesList = null;

/**
Vector of results from a query of the use table.
*/
private List<HydroBase_Use> __useVector = null;

/**
Constructor.  The constructor queries the database for the results that will
be used in the report and generates the appropriate kind of report, which can
be retrieved with getReport().  
Additional reports for the same dataset can be created without re-querying the 
database by calling one of the create*Report() methods.
@param dmi an open HydroBaseDMI object.
@param reportType the type of report to first generate.
@param filterPanel the filter panel to use to control querying.
@param districtWhere the two-element array from 
HydroBaseDMI.getWaterDistrictWhereClause().
@throws Exception if an invalid DMI or reportType is passed in, or if some
other error occurs.
*/
public HydroBase_Report_NetAmounts(HydroBaseDMI dmi, int reportType, 
HydroBase_GUI_NetAmts_InputFilter_JPanel filterPanel, String[] districtWhere)
throws Exception {
	// __results is not reset in the call to initialize so that the result
	// set from the query can be used in multiple reports.
	__results = null;
	initialize();

	if (dmi == null) {
		throw new Exception("HydroBase_Report_NetAmounts was "
			+ "passed a null DMI object -- cannot do anything "
			+ "without a valid DMI object.");
	}
	if (!dmi.isOpen()) {
		throw new Exception("HydroBase_Report_NetAmounts was "
			+ "passed a closed DMI object -- cannot do anything "
			+ "without an open DMI connection.");
	}

	// save member variable copies of the local variables.
	__dmi = dmi;
	__filterPanel = filterPanel;
	__districtWhere = districtWhere;
	
	// build the report specified in the parameter list
	createReport(reportType);
}

/**
Generic method that will create the specified report.  Can be used instead
of calling the specified createAdminReport, createPriorityReport,
createTransStreamReport, and createNetTabulationReport methods.
@param reportType the kind of report to generate
@throws Exception if an error occurs or if an invalid report type is passed in.
*/
public void createReport(int reportType)
throws Exception {
	switch(reportType) {
		case ADMIN_SUMMARY:
			createAdminReport(reportType);
			break;
		case ADMIN_STREAM:
			createAdminReport(reportType);
			break;
		case PRIORITY_SUMMARY:
			createPriorityReport(reportType);
			break;
		case PRIORITY_STREAM:
			createPriorityReport(reportType);
			break;
		case STREAM:
			createTransStreamReport();
			break;
		case TABULATION:		
			createNetTabulationReport();
			break;
		default:
			throw new Exception("Bad reportType passed to "
				+ "createReport(): "
				+ reportType);		
	}
}

/**
Create a priority report, either a Priority List or a Priority List by Stream.
@param type the kind of report to generate
@throws Exception if an error occurs.
*/
public void createPriorityReport(int type)
throws Exception {
	// set up variables
	initialize();
	String lastStreamName = new String();
	boolean	byStream = false;
	HydroBase_NetAmts node = null;
	// check to see if the database needs to be queried	
	if (__results == null) {
		// the database always needs to be queried if there is
		// no result set
		__results = __dmi.readNetAmtsList(__filterPanel, 
			__districtWhere, null, type);
	}
	else if (type == PRIORITY_SUMMARY) {
		if (	__lastReportType == ADMIN_SUMMARY ||
			__lastReportType == PRIORITY_SUMMARY ||
			__lastReportType == TABULATION) {
			// they use the same query, don't need to requery
		}
		else {
			// the last query used a different "ORDER BY" clause
			// than the query that needs to be run now
			__results = __dmi.readNetAmtsList(__filterPanel, 
				__districtWhere, null, type);
		}
	}
	else if (type == PRIORITY_STREAM) {
		if (	__lastReportType == ADMIN_STREAM ||
			__lastReportType == PRIORITY_STREAM) {
			// they use the same query, don't need to requery
		}
		else {
			// the last query used a different "ORDER BY" clause
			// than the query that needs to be run now
			__results = __dmi.readNetAmtsList(__filterPanel, 
				__districtWhere, null, type);
		}
	}
	else {
		// this should never happen, but just in case ...
		throw new Exception("Invalid type (" + type + ") passed " 
			+ "to createPriorityReport");
	}

	if (type == PRIORITY_STREAM) {
		byStream = true;
	}
	
	// save the current report type as the last type of report generated
	__lastReportType = type;
	
	// nothing gets printed if there are no records in the query
	int numNodes = __results.size();
	if (numNodes == 0) {
		return;
	}
	
	// print header if not by stream
	if (!byStream) {
		__isNewStreamNode = false;		
		__reportList = getNetAdminReportHeader(byStream);
	}
	else {
		__reportList = new ArrayList<String>();
	}

	// get the list of use codes from the database.  This is a small and
	// fast query, so it can be done every time a report is generated.
	__useVector = __dmi.getUseTypes();

	// loop through all the records in the results and print out the
	// data associated with them
	for (int i = 0; i < numNodes; i++) {
		node = __results.get(i);

		if (byStream) {
			// if the current record has a different stream name
			// from the last record, add a spacer and print out
			// the section header for the new stream name
			if (!node.getWd_stream_name().equals(lastStreamName)) {
				if (i>0) {
					__reportList.add("");
					__reportList.add("");
				}

				// get a new header
				__streamName = node.getWd_stream_name();
				__isNewStreamNode = true;

				List<String> v = getNetAdminReportHeader(byStream);
				int size = v.size();
				for (int j = 0; j < size; j++) {
					__reportList.add(v.get(j));
				}
			}
			else {
				__isNewStreamNode = false;
			}
			lastStreamName = node.getWd_stream_name();
		}
		// get the actual line of data for this record and add
		// it to the report vector
		String line = getNetAdminLine(node);
		if (line.length() > 0) {
			__reportList.add(line);
		}
	}

	// add footer
	__reportList.add("* Alternate Point");
}

/**
Create a tabulation report.
@throws Exception if an error occurs.
*/
public void createNetTabulationReport()
throws Exception {
	// set up variables
	initialize();
	HydroBase_NetAmts node = null;

	// see if the database needs to be requeried
	if (__results == null) {
		// if the results vector is null, the database has to be 
		// requeried
		__results = __dmi.readNetAmtsList(__filterPanel, 
			__districtWhere, null, TABULATION);
	}
	else if (	__lastReportType == TABULATION ||
			__lastReportType == ADMIN_SUMMARY ||
			__lastReportType == PRIORITY_SUMMARY) {
		// they have the same query -- no need to requery
	}
	else {
		// the last query had a different "ORDER BY" clause than
		// the one that needs to be run now
		__results = __dmi.readNetAmtsList(__filterPanel, 
			__districtWhere, null, TABULATION);
	}

	// save the current report type as the last one executed
	__lastReportType = TABULATION;
	
	// nothing gets printed if there are no records in the query
	int size = __results.size();
	if (size == 0) {
		return;
	}

	// query the use and str_type table for all records.  These are small
	// and fast queries, so they can be run every time a report is generated.
	__useVector = __dmi.getUseTypes();
	__strtypesList = __dmi.readDssStructureTypeList();
	
	// get the header for the report
	__reportList = getNetTabulationReportHeader();

	// loop through all the records and print their information
	for (int i = 0; i < size; i++) {
		node = (HydroBase_NetAmts)__results.get(i);
		__reportList.add(getNetTabulationLine(node, (i + 1)));
		// Garbage collect to try to prevent problems...
		if ((i%500) == 0) {
			node = null;
			System.gc();
		}
	}

}

/**
Create Structure type for standard water rights report and 
water rights tabulation.
@param strType the str_type to create the structure type for
@return the structure type
@throws Exception if an error occurs
*/
private String createStructureType(String strType)
throws Exception {
	String newStrType = "";

	if ( strType == null || DMIUtil.isMissing(strType) ) {
		return newStrType;
	}

	int nstrType = strType.length();

	String code = null;

	for (int j = 0; j < nstrType; j++) {
		code = String.valueOf(strType.charAt(j));
		if (code.trim().length() > 0) {
			newStrType += getStructureTypeReportCode(code);
		}
	}
	return newStrType;
}

/**
Create a administrative summary list report.
@param type the kind of report to make, either an Administrative Summary or an
Administrative Summary by stream.
@throws Exception if an error occurs.
*/
public void createAdminReport(int type)
throws Exception {
	// initialize variables
	initialize();
	String lastStreamName = "";
	boolean	byStream = false;
	HydroBase_NetAmts node = null;

	// see if the database needs to be requeried
	if (__results == null) {
		// if the result set is null, the database always 
		// needs to be requeried
		__results = __dmi.readNetAmtsList(__filterPanel, 
			__districtWhere, null, type);
	}
	if (type == ADMIN_SUMMARY) {
		if (	__lastReportType == PRIORITY_SUMMARY ||
			__lastReportType == ADMIN_SUMMARY ||
			__lastReportType == TABULATION) {
			// they have the same query, no need to requery
		}
		else {
			// the last query had a different "ORDER BY" clause
			__results = __dmi.readNetAmtsList(__filterPanel, 
				__districtWhere, null, type);
		}
	} 
	else if (type == ADMIN_STREAM) {
		if (	__lastReportType == ADMIN_STREAM ||
			__lastReportType == PRIORITY_STREAM) {
			// they have the same query, no need to requery
		}
		else {
			// the last query had a different "ORDER BY" clause
			__results = __dmi.readNetAmtsList(__filterPanel, 
				__districtWhere, null, type);
		}
	}
	else {
		// this should never happen
		throw new Exception("Invalid type (" + type + ") passed " 
			+ "to createAdminReport");
	}

	if (type == ADMIN_STREAM) {
		byStream = true;
	}

	// save this report type as the last kind for which a report
	//w as generated
	__lastReportType = type;

	// if nothing was returned from the database query, print nothing
	int numNodes = __results.size();
	if (numNodes == 0) {
		return;
	}
	
	if (!byStream) {
		__reportList = getTransAdminReportHeader(byStream);
	} else {
		__reportList = new ArrayList<String>();
	}

	// loop through each record and print out its information
	for (int i = 0; i < numNodes; i++) {
		node = __results.get(i);

		if (byStream) {
			// if the current record has a different stream name
			// from the last record, add a spacer and print out
			// the section header for the new stream name
			if (!node.getWd_stream_name().equals(lastStreamName)) {
				__streamName = node.getWd_stream_name();
				__isNewStreamNode = true;
				if (i > 0) {
					__reportList.add("");
					__reportList.add("");
				}

				List<String> v = getTransAdminReportHeader(byStream);
				int size = v.size();
				for (int j = 0; j < size; j++) {
					__reportList.add(v.get(j));
				}
			}
			else {
				__isNewStreamNode = false;
			}
			lastStreamName = node.getWd_stream_name();
		}
		__reportList.add(getTransAdminLine(node));
	}

	// add footer
	__reportList.add("* Alternate Point");
}

/**
Create stream alphabetical report.
@throws Exception if an error occurs.
*/
public void createTransStreamReport()
throws Exception {
	initialize();
	String lastStreamName = "";
	HydroBase_NetAmts node = null;

	// check to see if the database needs to be queried
	if (__results == null) {
		// if the results are null, the database always needs
		// to be queried
		__results = __dmi.readNetAmtsList(__filterPanel, 	
			__districtWhere, null, STREAM);
	}
	else if (__lastReportType == STREAM) {
		// they have the same query, no need to requery
	} 
	else {
		// the last query had a different "ORDER BY" clause
		__results = __dmi.readNetAmtsList(__filterPanel, 
			__districtWhere, null, STREAM);
	}

	// save this report type as the last kind for which a report was
	// created
	__lastReportType = STREAM;

	// if nothing was returned from the query, print nothing
	int numNodes = __results.size();
	if (numNodes == 0) {
		return;
	}

	__reportList = new ArrayList<String>(numNodes);
	__isNewStreamNode = true;

	// loop through each record and print out its information
	for (int i = 0; i < numNodes; i++) {
		node = __results.get(i);

		if (!node.getWd_stream_name().equals(lastStreamName)) {
			// if the current record has a different stream name
			// from the last record, add a spacer and print out
			// the section header for the new stream name		
			__isNewStreamNode = true;
			if (i > 0) {
				__reportList.add("");
				__reportList.add("");
			}

			// get a new header
			__streamName = node.getWd_stream_name();
			__isNewStreamNode = true;
			List<String> v = getTransStreamReportHeader();
			int size = v.size();
			for (int j = 0; j < size; j++) {
				__reportList.add(v.get(j));
			}
		}
		else {	
			__isNewStreamNode = false;
		}

		lastStreamName = node.getWd_stream_name();
		__reportList.add(getTransStreamLine(node));
	}

	// add footer
	__reportList.add(new String("* Alternate Point"));
}

/**
Format a double with the specified number of decimal places.
@param d double value
@param format desired format (e.g., "%10.2f")
@return formatted String
@throws Exception if an error occurs.
*/
private String format(double d, String format) 
throws Exception {
	if (DMIUtil.isMissing(d) || HydroBase_Util.isMissing(d)) {
		return "";
	}
	else {	
		return StringUtil.formatString(d, format);
	}
}

/**
Formats an int type.
@param i int value
@return formatted String
@throws Exception if an error occurs.
*/
private String format(int i)
throws Exception {
	if (DMIUtil.isMissing(i) || HydroBase_Util.isMissing(i)) {
		return "";
	}
	else {
		return "" + i;
	}
}

/**
Create a single line for a Priority List or Priority List by Stream report.
@param n the HydroBase_NetAmts object for which to build the report line.
@throws Exception if an error occurs.
*/
private String getNetAdminLine(HydroBase_NetAmts n)
throws Exception {
 	int maxUseLength = 3;
	double cumulTotal = DMIUtil.MISSING_DOUBLE;
	List<String> v = new ArrayList<String>(35);
	double admin_no = n.getAdmin_no();

	// if this has the same admin num as the last record that was
	// added to the report, add an asterisk after its admin no
	if (admin_no == __lastAdminNum) {
		v.add(format(admin_no, "%11.5f")+ "*");
	}
	else {
		v.add(format(admin_no, "%11.5f"));
	}

	v.add(format(n.getID()));
	v.add(n.getWr_name());
	v.add(n.getPri_case_no());

	boolean isCFS = true;
	String unit = n.getUnit();

	if (unit.startsWith("C")) {
		double nra = n.getNet_rate_abs();	
		if ( (DMIUtil.isMissing(nra)) || HydroBase_Util.isMissing(nra) ||
			 ( (nra == 0) && 
			  (!n.getApex().equalsIgnoreCase("Y")))){
			// this station gets skipped
// REVIIST
// should skipped stations insert * for the next station (since the admin_no
// will match?) I don't think so and so the admin_no reset is done:
			return "";
		}
		v.add(format(nra, "%7.4f"));
		v.add(getUnit(unit)); 
		cumulTotal = nra;
	}
	else {
		double nva = n.getNet_vol_abs();
		if ( DMIUtil.isMissing(nva) || HydroBase_Util.isMissing(nva) ||
			(nva == 0 &&
			!n.getApex().equalsIgnoreCase("Y"))) {
// REVIIST
// should skipped stations insert * for the next station (since the admin_no
// will match?) I don't think so and so the admin_no reset is done:
			// this station gets skipped
			return "";
		}
		v.add(format(nva, "%7.4f"));
		v.add(getUnit(unit)); 
		cumulTotal = nva;
		isCFS = false;
	}

	v.add(translateNewToOld(n.getUse(), maxUseLength));
	v.add(n.getAction_comment());

	//
	// now calculate the cumulative total for that id
	//
	if (DMIUtil.isMissing(cumulTotal) || HydroBase_Util.isMissing(cumulTotal)) {
		cumulTotal = 0;
	}
	String ID = "" + n.getID();

	if (isCFS) {
		Double D = (Double)__sums.get(ID);
		if (D != null) {
			cumulTotal += D.doubleValue();
		}
		__sums.put(ID, Double.valueOf(cumulTotal));
	}
	else {
		Double D = (Double)__sumsAF.get(ID);
		if (D != null) {
			cumulTotal += D.doubleValue();
		}
		__sumsAF.put(ID, Double.valueOf(cumulTotal));
	}

	v.add(format(cumulTotal, "%6.2f"));

	// Keep track of the last admin number.
	__lastAdminNum = admin_no;

	return StringUtil.formatString(v, PRIORITY_SUMMARY_FORMAT);
}

/**
Returns a header for a Priority List or Priority List by Stream report.
@param byStream whether the report is By Stream (true) or not
@throws Exception if an error occurs.
*/
private List<String> getNetAdminReportHeader(boolean byStream)
throws Exception {
	List<String> report = new ArrayList<String>(4);
	String formatHeader = 
		"%-12.12s %-5.5s %-24.24s %-9.9s %-9.9s " 
		+ "%-4.4s %-80.80s %-6.6s";

	// set up the hash tables - this should only be done once
	if (__sums == null) {
		int numNodes = __results.size();
		__sums = new Hashtable<String,Double>(numNodes);
		__sumsAF = new Hashtable<String,Double>(numNodes);
	}

	// get the stream name
	String stream_name = "";
	if (!DMIUtil.isMissing(__streamName)) {
		stream_name = __streamName;
	}

	if (byStream) {
		report.add(" PRIORITY LIST BY STREAM - " + stream_name);
	}
	else {
		report.add("                           " 
			+ "PRIORITY LIST");
	}

	// add column titles
	report.add("");
	List<String> v = new ArrayList<String>(20);
	v.add("AdminNumber");
	v.add(" ID");
	v.add("STRUCTURE NAME");
	v.add("COURT NO");
	v.add("AMOUNT");
	v.add("USES");
	v.add("COMMENTS");
	v.add("TOTAL");
	report.add(StringUtil.formatString(v, formatHeader));

	// add ---
	report.add(
		"------------ ----- ------------------------ " 
		+ "--------- --------- ---- -------------------------------" 
		+ "------------------------------------------------- ------");

	return report;
}

/**
Returns a line of data for a Tabulation report.
@param n The HydroBase_NetAmts object for which to generate the report line.
@param count the position of the HydroBase_NetAmts object within the __results
list (base-1)
@throws Exception if an error occurs.
*/
private String getNetTabulationLine(HydroBase_NetAmts n, int count)
throws Exception {
	List<Object> v = new ArrayList<Object>(35);
	int maxUseLength = 3;
	double rate_abs;
	double rate_apex;
	double rate_cond;
	double vol_abs;
	double vol_apex;
	double vol_cond;	

	// Format the right for a water rights tabulation...
	v.add(n.getWr_name());
	v.add(createStructureType(n.getXstrtype()));
	v.add(n.getWd_stream_name());
	v.add(Integer.valueOf(n.getWD()));
	
	// create location string
	List<Object> vl = new ArrayList<Object>(7);
	vl.add(n.getQ10());
	vl.add(n.getQ40());
	vl.add(n.getQ160());
	vl.add(format(n.getSec()));
	vl.add(n.getTS());
	vl.add(n.getTdir());
	vl.add(n.getRng());
	vl.add(n.getRdir());
	vl.add(n.getPM());
	v.add(StringUtil.formatString(vl, TABULATION_LOCATION_FORMAT));
	v.add(translateNewToReport(n.getUse(), maxUseLength));

	// The units determine which values we output and only output
	// if at least one of the values are non-zero..
	String unit = n.getUnit();
	if (unit.equals("A")) {
		// Volumes...
		vol_abs  = n.getNet_vol_abs();
		vol_cond = n.getNet_vol_cond();
		vol_apex = n.getNet_vol_apex();
		/*
		if (	((vol_abs  <= 0.0) || DMIUtil.isMissing(vol_abs))  &&
			((vol_cond <= 0.0) || DMIUtil.isMissing(vol_cond)) &&
			((vol_apex <= 0.0) || DMIUtil.isMissing(vol_apex))) {
			continue;
		}
		*/

		// Only print if non-missing...
		if (!DMIUtil.isMissing(vol_abs) && !HydroBase_Util.isMissing(vol_abs)) {
			if (vol_abs == 0) {
				v.add("");		
			}
			else {
				v.add(format(vol_abs, "%9.3F"));
			}
		}
		else {	
			v.add("");
		}
		
		if (!DMIUtil.isMissing(vol_cond) && !HydroBase_Util.isMissing(vol_cond)) {
			if (vol_cond == 0) {
				v.add("");
			}
			else {
				v.add(format(vol_cond, "%9.3F"));
			}
		}
		else {	
			v.add("");
		}
		
		if (!DMIUtil.isMissing(vol_apex) && !HydroBase_Util.isMissing(vol_apex)) {
			if (vol_apex == 0) {
				v.add("");
			}
			else {
				v.add(format(vol_apex, "%9.3F"));
			}
		}
		else {	
			v.add("");
		}

	}
	else {	
		// Rates...
		rate_abs  = n.getNet_rate_abs();
		rate_cond = n.getNet_rate_cond();
		rate_apex = n.getNet_rate_apex();
		/*
		if (	((rate_abs  <= 0.0) || DMIUtil.isMissing(rate_abs))  &&
			((rate_cond <= 0.0) || DMIUtil.isMissing(rate_cond)) &&
			((rate_apex <= 0.0) || DMIUtil.isMissing(rate_apex))) {
			continue;
		}
		*/

		if (!DMIUtil.isMissing(rate_abs) && !HydroBase_Util.isMissing(rate_abs)) {
			if (rate_abs == 0) {
				v.add("");
			}
			else {
				v.add(format(rate_abs, "%9.3F"));
			}
		}
		else {	
			v.add("");
		}
		
		if (!DMIUtil.isMissing(rate_cond) && !HydroBase_Util.isMissing(rate_cond)) {
			if (rate_cond == 0) {
				v.add("");
			}
			else {
				v.add(format(rate_cond, "%9.3F"));
			}
		}
		else {	
			v.add("");
		}
			
		if (!DMIUtil.isMissing(rate_apex) && !HydroBase_Util.isMissing(rate_apex)) {
			if (rate_apex == 0) {
				v.add("");
			}
			else {
				v.add(format(rate_apex, "%9.3F"));
			}
		}
		else {	
			v.add("");
		}
	}

	v.add(unit); 

	if (!DMIUtil.isMissing(n.getAdj_date())) {
		v.add((new DateTime(n.getAdj_date())).toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY));
	}
	else {	
		v.add("");
	}

	if (!DMIUtil.isMissing(n.getPadj_date())) {
		v.add((new DateTime(n.getAdj_date())).toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY));
	}
	else {	
		v.add("");
	}

	if (!DMIUtil.isMissing(n.getApro_date())) {
		v.add((new DateTime(n.getApro_date())).toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY));
	}
	else {	
		v.add("");
	}

	// add order number
	if (n.getOrder_no() == 0) {
		v.add("");
	}
	else {	
		v.add(format(n.getOrder_no()));
	}

	double curAdminNum = n.getAdmin_no();
	if (curAdminNum == __lastAdminNum) {
		v.add(format(curAdminNum, "%11.5f")+ "*");
	}
	else {	
		v.add(format(curAdminNum, "%11.5f"));
	}

	v.add(format(n.getID()));
	v.add(n.getPri_case_no());
	v.add(format(count));

	// Keep track of the last admin number because it affects the output...
	__lastAdminNum = curAdminNum;

	return StringUtil.formatString(v, TABULATION_FORMAT);
}

/**
Returns a header for a tabulation report.
@throws Exception if an error occurs.
*/
private List<String> getNetTabulationReportHeader()
throws Exception {
	List<String> report = new ArrayList<String>(4);
	String formatTopHeader = "%-24s%53.53s%-48.48s%-24.24s";
	List<Object> fv = new ArrayList<Object>(4);

	fv.add("");
	fv.add(" ");
	fv.add("Tabulation Report");
	fv.add((new DateTime(new Date())).toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY));
	report.add(StringUtil.formatString(fv,
	formatTopHeader));
	report.add("");

	// add column titles, line 1
	List<String> v = new ArrayList<String>(17);
	v.add("Name of Structure");
	v.add("Typ");
	v.add("Name of Source");
	v.add("WD");
	v.add("- - L O C A T I O N  - -");
	v.add("Use");
	v.add("Net Abs");
	v.add("Net Cond");
	v.add("AltP/Exch");
	v.add("U");
	v.add("Adj Date");
	v.add("P Adj Date");
	v.add("Appro Date");
	v.add("Or");
	v.add("AdminNumber");
	v.add("ID#");
	v.add("Pr#/C#");
	v.add("Line");

	report.add(StringUtil.formatString(v, TABULATION_FORMAT));
	report.add("");
	return report;
}

/**
Returns the structure type report code given a structure type (abbreviation)
@param structureType A structure type string from the str_type table.
@return The structure type report code given a structure type (abbreviation)
@throws Exception if an error occurs.
*/
public String getStructureTypeReportCode ( String structureType )
throws Exception {
	HydroBase_DssStructureType hbst;

	if (__strtypesList == null) {
		return "";
	}

	int size = __strtypesList.size();

	for (int i = 0; i < size; i++) {
		hbst = __strtypesList.get(i);

		if (hbst.getStr_type().equalsIgnoreCase(structureType)) {
			return  hbst.getRpt_code();
		}
	}
	return "";
}

/**
Builds a report line for a Administrative Summary or Administrative Summary 
by Stream report.
@param n the HydroBase_NetAmts object for which to create the report line.
@throws Exception if an error occurs.
*/
private String getTransAdminLine(HydroBase_NetAmts n)
throws Exception {
	List<Object> v = new ArrayList<Object>(35);
	if (__isNewStreamNode) {
		__abscfs = DMIUtil.MISSING_DOUBLE;
		__condcfs = DMIUtil.MISSING_DOUBLE;
		__absaf = DMIUtil.MISSING_DOUBLE;
		__condaf = DMIUtil.MISSING_DOUBLE;
		__prevAbscfs = DMIUtil.MISSING_DOUBLE;
		__prevCondcfs = DMIUtil.MISSING_DOUBLE;
		__prevAbsaf = DMIUtil.MISSING_DOUBLE;
		__prevCondaf = DMIUtil.MISSING_DOUBLE;
		__isNewStreamNode = false;
	}
	double curAdminNum = n.getAdmin_no();

	// if this has the same admin num as the last record that was
	// added to the report, add an asterisk after its admin no	
	if (curAdminNum == __lastAdminNum) {
		v.add(format(curAdminNum, "%11.5f") + "*");
	}
	else {
		v.add(format(curAdminNum, "%11.5f"));
	}

	// keep track of the last admin number
	__lastAdminNum = curAdminNum;
	v.add(format(n.getOrder_no()));
	v.add(n.getPri_case_no());
	if (n.getApex().equalsIgnoreCase("Y")) {
		v.add("*");
	}
	else {
		v.add(" ");
	}
	v.add(format(n.getID()));
	v.add(n.getWr_name());
	v.add(n.getUnit());

	String unit = n.getUnit();
	if (unit.equalsIgnoreCase("C"))
	{
		if (DMIUtil.isMissing(n.getNet_rate_abs()) || HydroBase_Util.isMissing(n.getNet_rate_abs())) {
			v.add("");
		}
		else {
			v.add(format(n.getNet_rate_abs(), "%10.3f"));
			if (DMIUtil.isMissing(__abscfs) || HydroBase_Util.isMissing(__abscfs)) {
				__abscfs = n.getNet_rate_abs();
			}
			else {
				__abscfs += n.getNet_rate_abs();
			}
		}
	}
	else {
		if (DMIUtil.isMissing(n.getNet_vol_abs()) || HydroBase_Util.isMissing(n.getNet_vol_abs())) {
			v.add("");
		}
		else {
			v.add(format(n.getNet_vol_abs(), "%10.3f"));
			if (DMIUtil.isMissing(__absaf) || HydroBase_Util.isMissing(__absaf)) {
				__absaf = n.getNet_vol_abs();
			}
			else {
				__absaf += n.getNet_vol_abs();
			}
		}
	}
	
	if (unit.equalsIgnoreCase("C")) {
		if (DMIUtil.isMissing(n.getNet_rate_cond()) || HydroBase_Util.isMissing(n.getNet_rate_cond())) {
			v.add("");
		}
		else {
			v.add(format(n.getNet_rate_cond(), "%10.3f"));
			if (DMIUtil.isMissing(__condcfs) || HydroBase_Util.isMissing(__condcfs)) {
				__condcfs = n.getNet_rate_cond();
			}
			else {
				__condcfs += n.getNet_rate_cond();
			}
		}
	}
	else {	// units = AF
		if (DMIUtil.isMissing(n.getNet_vol_cond()) || HydroBase_Util.isMissing(n.getNet_vol_cond())) {
			v.add("");
		}
		else {
			v.add(format(n.getNet_vol_cond(),"%10.3f"));
			if (DMIUtil.isMissing(__condaf) || HydroBase_Util.isMissing(__condaf)) {
				__condaf = n.getNet_vol_cond();
			}
			else {
				__condaf += n.getNet_vol_cond();
			}
		}
	}

	//
	// ABS/CFS
	//
	if (DMIUtil.isMissing(__abscfs) || HydroBase_Util.isMissing(__abscfs)) {
		v.add("");
	}
	else {
		if (__abscfs != __prevAbscfs) {
			v.add(format(__abscfs, "%11.2F"));
		}
		else {
			v.add("\"");
		}
		__prevAbscfs = __abscfs;
	}
	//
	// COND/CFS
	//
	if (DMIUtil.isMissing(__condcfs) || HydroBase_Util.isMissing(__condcfs)) {
		v.add("");
	}
	else {
		if (__condcfs != __prevCondcfs) {
			v.add(format(__condcfs, "%11.2F"));
		}
		else {
			v.add("\"");
		}
		__prevCondcfs = __condcfs;
	}
	//
	// ABS/AF
	//
	if (DMIUtil.isMissing(__absaf) || HydroBase_Util.isMissing(__absaf)) {
		v.add("");
	}
	else {
		if (__absaf != __prevAbsaf) {
			v.add(format(__absaf, "%11.2F"));
		}
		else {
			v.add("\"");
		}
		__prevAbsaf = __absaf;
	}
	//
	// COND/AF
	//
	if (DMIUtil.isMissing(__condaf) || HydroBase_Util.isMissing(__condaf)) {
		v.add("");
	}
	else {
		if (__condaf != __prevCondaf) {
			v.add(format(__condaf, "%11.2F"));
		}
		else {
			v.add("\"");
		}
		__prevCondaf = __condaf;
	}
	return StringUtil.formatString(v, ADMINISTRATIVE_SUMMARY_FORMAT);

}

/**
Returns a header for an Administrative Summary or Administrative Summary by 
Stream report.
@param byStream whether to return a header for an Administrative Summary by
Stream report (true) or for an Administrative Summary report (false).
@throws Exception if an error occurs.
*/
private List<String> getTransAdminReportHeader(boolean byStream) {
	List<String> report = new ArrayList<String>(4);
	String formatTopHeader = 
		"%-11.11s %-2.2s %-9.9s %-5.5s %-24.24s %-2.2s " 
		+ "%-20.20s %-47.47s";
	String formatHeader = 
		"%-10.10s %3.3s %-9.9s %5.5s %-21.21s %5.5s %-9.9s " 
		+ "%-11.11s %-11.11s %-11.11s %-11.11s %-11.11s";

	String stream_name = "";
	if (!DMIUtil.isMissing(__streamName) ) {
		stream_name = __streamName;
	}

	if (byStream) {
		report.add(" ADMINISTRATIVE SUMMARY LIST BY STREAM - " 
			+  stream_name + "          " 
			+ ((new DateTime(new Date())).
			toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY)));
	} 
	else {
		report.add("                                          "
			+ "ADMINISTRATIVE SUMMARY LIST" 
			+ "                  " + ((new DateTime(new Date())).
			toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY)));
	}

	report.add("");
	List<Object> v = new ArrayList<Object>(20);
	v.add("ADMIN");
	v.add("ORD");
	v.add("PRIORITY");
	v.add("");
	v.add("WATER RIGHT");
	v.add("");
	v.add("-------- NET --------");
	v.add("------------------ CUMULATIVE ------------------");
	report.add(StringUtil.formatString(v, formatTopHeader));

	v.clear();
	v.add("    #");
	v.add("#");
	v.add("       #");
	v.add("ID");
	v.add("NAME");
	v.add("UNITS");
	v.add(" ABSOLUTE");
	v.add("CONDITIONAL");
	v.add("   ABS/CFS");
	v.add("  COND/CFS");
	v.add("   ABS/AF");
	v.add("   COND/AF");
	report.add(StringUtil.formatString(v, formatHeader));
	report.add("----------- -- --------- ----- " 
		+ "------------------------ -- ---------- ---------- " 
		+ "----------- ----------- ----------- -----------");

	return report;
}

/**
Gets a line of data for a Stream Alphabetical report.
@param n the HydroBase_NetAmts object for which to build a report line.
@throws Exception if an error occurs.
*/
private String getTransStreamLine(HydroBase_NetAmts n)
throws Exception {
	List<Object> v = new ArrayList<Object>(35);
	double admin_no = n.getAdmin_no();

	// if this has the same admin num as the last record that was
	// added to the report, add an asterisk after its admin no	
	if (admin_no == __lastAdminNum) {
		v.add(new String(format(admin_no, "%11.5f") + "*"));
	}
	else {
		v.add(new String(format(admin_no, "%11.5f")));
	}

	v.add(format(n.getOrder_no()));
	v.add(n.getPri_case_no());
	v.add(format(n.getID()));
	if (n.getApex().equalsIgnoreCase("Y")) {
		v.add("*");
	}
	else {
		v.add(" ");
	}
	v.add(n.getWr_name());
	String unit = n.getUnit();
	v.add(unit);
	if (unit.startsWith("C"))
	{
		v.add(format(n.getNet_rate_abs(), "%10.3f"));
		v.add(format(n.getNet_rate_cond(), "%10.3f"));
	}
	else {
		v.add(format(n.getNet_vol_abs(), "%10.3f"));
		v.add(format(n.getNet_vol_cond(), "%10.3f"));
	}
	v.add(n.getAction_comment());

	// keep track of the last admin number
	__lastAdminNum = admin_no;

	return StringUtil.formatString(v, NET_AMTS_ALPHABETICAL_FORMAT);
}

/**
Returns a report header for a Stream Alphabetical report.
@throws Exception if an error occurs.
*/
private List<String> getTransStreamReportHeader()
throws Exception {
	List<String> report = new ArrayList<String>(4);
	String formatTopHeader = 
		"%-11.11s %-3.3s %-9.9s %-5.5s %-24.24s %-2.2s " 
		+ "%-22.22s %-80.80s";
	String formatHeader = 
		"%-11.11s %-3.3s %-9.9s %5.5s %-21.21s %-5.5s %-9.9s " 
		+ "%-11.11s %-80.80s";

	// get the stream name
	String stream_name = "";
	if (!DMIUtil.isMissing(__streamName) ) {
		stream_name = __streamName;
	}

	report.add(" STREAM ALPHABETICAL LIST - " + stream_name);
	report.add("");

	List<Object> v = new ArrayList<Object>(20);

	v.add("ADMIN");
	v.add("ORD");
	v.add("PRIORITY");
	v.add("");
	v.add("WATER RIGHT");
	v.add("");
	v.add("-------- NET --------");
	v.add("");
	report.add(StringUtil.formatString(v, formatTopHeader));

	v.clear();
	v.add("    #");
	v.add("  #");
	v.add("       #");
	v.add("ID");
	v.add("NAME");
	v.add("UNITS");
	v.add(" ABSOLUTE");
	v.add("CONDITIONAL");
	v.add("COMMENT");
	report.add(StringUtil.formatString(v, formatHeader));

	// add ---
	report.add("----------- --- --------- ----- " 
		+ "------------------------ -- ---------- ---------- " 
		+ "--------------------------------------------------" 
		+ "------------------------------");

	return report;
}

/**
Returns the report that was generated.
@return the report that was generated.
*/
public List<String> getReport() {
	return __reportList;
}

/**
Determines the display units.
@param s Unit to convert.  Possible values are "C" and "A".  If a null is
passed in, an empty string will be returned.
@return Display units
@throws Exception if a unit was passed in that is not recognized.
*/
private String getUnit(String s)
throws Exception {
	String unit = "";
	if (s == null) {
		return unit;
	}
	else if (s.equalsIgnoreCase("C")) {
		unit = "CFS";
	}
	else if (s.equalsIgnoreCase("CFS")) {
		unit = "CFS";
	}
	else if (s.equalsIgnoreCase("A")) {
		unit = "AF";
	} 
	else if (s.equalsIgnoreCase("AF")) {
		unit = "AF";
	}
	else {
		throw new Exception("Unknown unit '" + s + "' passed in to "
			+ "getUnit()");
	}
	return unit;
}

/**
Initializes the member variables.  Note that __results (the Vector in which
the records returned from the query the constructor executed) is not 
reinitialized by this method.  This is so that multiple reports can be 
generated from a single HydroBase_Report_NetAmounts object without having 
to requery the database every time.
*/
private void initialize() {
	__reportList = null;	
	__isNewStreamNode = true;
	__sums = null;
	__sumsAF = null;
	__streamName = DMIUtil.MISSING_STRING;
	__useVector = null;
	__lastAdminNum = DMIUtil.MISSING_DOUBLE;
	__abscfs = DMIUtil.MISSING_DOUBLE;
	__condcfs = DMIUtil.MISSING_DOUBLE;
	__absaf = DMIUtil.MISSING_DOUBLE;
	__condaf = DMIUtil.MISSING_DOUBLE;
	__prevAbscfs = DMIUtil.MISSING_DOUBLE;
	__prevCondcfs = DMIUtil.MISSING_DOUBLE;
	__prevAbsaf = DMIUtil.MISSING_DOUBLE;
	__prevCondaf = DMIUtil.MISSING_DOUBLE;	
}

/**
Looks up the position within the Vector of results from the Use query at 
which the given Use type occurs.
@param s the use type for which to return the Vector position.
@return the position within the Vector of results from the Use query at
which the given Use type occurs.
@throws Exception if an error occurs.
*/
private int lookupUsingUse(String s) 
throws Exception {
	if (__useVector == null) {
		return DMIUtil.MISSING_INT;
	}
	int size = __useVector.size();
	for (int i = 0; i < size; i++) {
		HydroBase_Use u = (HydroBase_Use)__useVector.get(i);
		if (u.getUse().equalsIgnoreCase(s)) {
			return i;
		}
	}
	return DMIUtil.MISSING_INT;
}

/**
Accepts a String in which to determine the old use type ("xuse")
via three char String segments.
<b>These are not typically not the codes used in reports(for that, use the
translateNewToReport()member function.</b>;
@param newUse New use type (3-character string segments).
@param maxLength Maximum length of the return String.
@return Old use type.
*/
private String translateNewToOld(String newUse, int maxLength)
throws Exception {	
	// initialize variables
	String old = "";
	String use = "";

	if (newUse == null) {
		return old;
	}

	int len = newUse.length();
	int offset = 3;

	int start = 0;
	int end = start + offset;
	while (start < len) {
		use = newUse.substring(start, end);

		int index = lookupUsingUse(use);
		if (!DMIUtil.isMissing(index) && !HydroBase_Util.isMissing(index)) {
			old += __useVector.get(index).getXuse();
		}

		if (old.length() == (maxLength)) {
			old += "*";
			break;
		}

		start = end;
		end = start + offset;
	} 

	return old;
}

/**
Accepts a String in which to determine the use type for reports to match
legacy reports.
@param newUse New use type (3-character string segments).
@param maxLength Maximum length of the return String.
@return Use type to use with reports.
*/
private String translateNewToReport(String newUse, int maxLength)
throws Exception {
	// initialize variables
	String report = "";
	String use = "";
	if (newUse == null) {
		return report;
	}
	int len = newUse.length();
	int offset = 3;

	int start = 0;
	int end = start + offset;
	while (start < len) {
		use = newUse.substring(start, end);

		int index = lookupUsingUse(use); 
		if (index >= 0) {
			report += ((HydroBase_Use)
				__useVector.get(index)).getRpt_code();
		}

		if (report.length() == (maxLength)) {
			report += "*";
			break;
		}

		start = end;
		end = start + offset;
	} 

	return report;
}

}
