// ----------------------------------------------------------------------------
// HydroBase_Report_Transaction - Class for generating Water Right Transaction
//	reports.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-26	J. Thomas Sapienza, RTi	Initial version.
// 2003-02-27	JTS, RTi		Javadoc'd, reviewed, cleaned.
// 2003-03-03	JTS, RTi		Changed getOrderVector to public static.
// 2003-04-03	JTS, RTi		Use types are now retrieved with the 
//					dmi's "getUseTypes()" method, rather
//					than being requeried everytime.  The
//					use types should have already been 
//					initialized in the dmi by the calling
//					application with the readGlobalData()
//					method.
// 2004-05-10	JTS, RTi		Corrected error in formatting Q* fields.
// 2005-02-11	JTS, RTi		Converted to use the new version of
//					readTransactList() that can call either
//					SQL or stored procedure versions of 
//					the queries.
// 2005-02-23	JTS, RTi		Removed getOrderVector.
// 2005-04-28	JTS, RTi		Added finalize().
// 2005-07-11	JTS, RTi		Stopped using transact.x* fields.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import RTi.DMI.DMIUtil;

import RTi.Util.IO.IOUtil;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;

/**
Class for generating water right transaction reports.
*/
public class HydroBase_Report_Transaction {

/**
Value for doing an extended water rights report (id, admin#)
*/
public static final int EXTENDED_1 = 1001;

/**
Value for doing an extended water rights report (stream#, id, admin#)
*/
public static final int EXTENDED_2 = 1002;

/**
Value for doing an extended water rights report (name, admin#)
*/
public static final int EXTENDED_3 = 1003;

/**
Value for doing an extended water rights report (location)
*/
public static final int EXTENDED_4 = 1004;

/**
Value for doing an extended water rights report (admin#, name)
*/
public static final int EXTENDED_5 = 1005;

/**
Value for doing an extended water rights report (stream#, admin#, id)
*/
public static final int EXTENDED_6 = 1006;

/**
Value for doing a standard water rights report (id, admin#)
*/
public static final int STANDARD_1 = 1;

/**
Value for doing a standard water rights report (stream#, id, admin#)
*/
public static final int STANDARD_2 = 2;

/**
Value for doing a standard water rights report (name, admin#)
*/
public static final int STANDARD_3 = 3;

/**
Value for doing a standard water rights report (location)
*/
public static final int STANDARD_4 = 4;

/**
Value for doing a standard water rights report (admin#, name)
*/
public static final int STANDARD_5 = 5;

/**
Value for doing a standard water rights report (stream#, admin#, id)
*/
public static final int STANDARD_6 = 6;

/**
Format string for extended water rights reports.
*/
public static final String EXTENDED_WATER_RIGHTS_FORMAT =
	"%-3.3s %-5.5s %-24.24s %-10.10s %3.3s %-20.20s %-2.2s %-6.6s " +
	"%-2.2s  %-4.4s%4.4s %1.1s  %-10.10s %-12.12s %1.1s %-8.8s %-10.10s " +
	"%-10.10s %-10.10s %-2.2s %-12.12s %-9.9s %-9.9s %-1.1s %-7.7s " +
	"%-7.7s %-9.9s %-10.10s";
/**
Format string for standard water rights reports.
*/
// first 3 lines for Standard and extended should stay the same
public static final String STANDARD_WATER_RIGHTS_FORMAT =
	"%-3.3s %-5.5s %-24.24s %-10.10s %3.3s %-20.20s %-2.2s %-6.6s " +
	"%-2.2s  %-4.4s%4.4s %1.1s  %-10.10s %-12.12s %1.1s %-8.8s %-10.10s " +
	"%-10.10s %-10.10s %-2.2s %-12.12s %-9.9s %-9.9s %-1.1s %-7.7s " +
	"%-65.65s";

/**
Administration number from the previous right processed when generating 
reports.
*/
private double __lastAdminNum = DMIUtil.MISSING_DOUBLE;

/**
Reference to an open DMI to be used for querying HydroBase.
*/
private HydroBaseDMI __dmi = null;

/**
The input filter JPanel that was passed in to the control the querying.
*/
private HydroBase_GUI_Transact_InputFilter_JPanel __filterPanel;

/**
Contains the type of the last report created.
*/
private int __lastReportType = DMIUtil.MISSING_INT;

/**
The district where clause passed in from the GUI from calling
getWaterDistrictWhereClause().
*/
private String[] __districtWhere = null;

/**
This is the Vector that holds all the lines of the report.
*/
private List __reportVector = null;

/**
This is the Vector that holds the results of the query that will be used
to fill out the reports.
*/
private List __results = null;

/**
Vector of results from a query of the str_ype table.
*/
private List __strtypesVector = null;

/**
Vector of results from a query of the use table.
*/
private List __useVector = null;

/**
Constructor.  The constructor queries the database for the results that will
be used in the report and generates the appropriate kind of report, which can
be retrieved with getReport().  
Additional reports for the same dataset can be created by calling the
createReport() method.
@param dmi an open HydroBaseDMI object.
@param reportType the type of report to first generate.
@param filterPanel the filter panel to use to control querying.
@param districtWhere the two-element array from 
HydroBaseDMI.getWaterDistrictWhereClause().
@throws Exception if an invalid DMI or reportType is passed in, or if some
other error occurs.
*/
public HydroBase_Report_Transaction(HydroBaseDMI dmi, int reportType, 
HydroBase_GUI_Transact_InputFilter_JPanel filterPanel, String[] districtWhere)
throws Exception {
	// __results is not reset in the call to initialize so that the result
	// set from the query can be used in multiple reports.
	__results = null;
	initialize();

	if (dmi == null) {
		throw new Exception("HydroBase_Report_Transaction was "
			+ "passed a null DMI object -- cannot do anything "
			+ "without a valid DMI object.");
	}
	if (!dmi.isOpen()) {
		throw new Exception("HydroBase_Report_Transaction was "
			+ "passed a closed DMI object -- cannot do anything "
			+ "without an open DMI connection.");
	}

	// save member variable copies of the passed-in parameters
	__dmi = dmi;
	__filterPanel = filterPanel;
	__districtWhere = districtWhere;	
	__reportVector = new Vector();

	// query the str_type and use tables for all records.
	__strtypesVector = __dmi.readDssStructureTypeList();
	__useVector = __dmi.getUseTypes();

	createReport(reportType);
}

/**
Creates an adj type String from data within a HydroBase_Transact object.
@param n the HydroBase_Transact object from which to create an adj type String.
@return an adj type String, created by:
adj_type + "," + status_type + "," + transfer_type + "," + assoc_type
+ "," + aband
@throws Exception if an error occurs.
*/
private String createStringAdjType(HydroBase_Transact n)
throws Exception {
	String finalAdjType = new String();

	String adj_type = n.getAdj_type();
	if (adj_type != null) {
		finalAdjType += adj_type;
	}

	String status_type = n.getStatus_type();
	if (status_type != null && status_type.length()>0) {
		if (!status_type.equals("A"))
			finalAdjType += "," + status_type;
	}

	String transfer_type = n.getTransfer_type();
	if (transfer_type != null && transfer_type.length()>0)
	{
		finalAdjType += "," + transfer_type;
	}

	String assoc_type = n.getAssoc_type();
	if (assoc_type != null && assoc_type.length()>0) {
		finalAdjType += "," + assoc_type;
	}

	String aband = n.getAband();
	if (aband != null && aband.length()>0) {
		finalAdjType += "," + aband;
	}

	return finalAdjType;
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

	if (strType == null) {
		return newStrType;
	}

	int nstrType = strType.length();

	String code = null;

	for (int j = 0; j < nstrType; j++) {
		code = new String(String.valueOf(strType.charAt(j)));
		if (code.trim().length()> 0) {
			newStrType += getStructureTypeReportCode(code);
		}
	}
	return newStrType;
}

/**
Creates a transaction report that can be retrieved with getReport().
@param reportType the type of report to create.
@throws Exception if an error occurs or an invalid reportType is passed in.
*/
public void createReport(int reportType)
throws Exception {
	initialize();
	HydroBase_Transact node = null;
	// check to see if the database needs requeried
	if (__results == null) {
		// if the results are null, the database always needs
		// requeried
		__results = __dmi.readTransactList(
			__filterPanel, __districtWhere, null, reportType);
	}
	else if (__lastReportType == reportType 
		|| __lastReportType == (reportType + 1000)
		|| __lastReportType == (reportType - 1000)) {
			// they have the same order clauses in the query,
			// no need to requery the database for a new order
	}
	else {
		// the query has a different set of order clauses than the
		// last one executed, so the database needs requeried
		__results = __dmi.readTransactList(
			__filterPanel, __districtWhere, null, reportType);
	}

	// save this report type as the last kind for which a report was
	// created
	__lastReportType = reportType;
	
	// if no records were pulled back, print nothing
	int size = __results.size();
	if (size == 0) {
		return;
	}

	// create the header for the report
	__reportVector = getTransactionReportHeader(reportType);
	
	String comment = "";

	// loop through each record and print its information
	for (int i=0; i<size; i++) {
		node = (HydroBase_Transact)__results.get(i);
		// print first line - always
		__reportVector.add(getTransactionLine(node, reportType));
	
		if (reportType >= EXTENDED_1) {		
			// print the comment(if available)for extended 
			// reports on the second line
			comment = node.getAction_comment();
			if (!DMIUtil.isMissing(comment)) {
				__reportVector.add(
					"                             " 
					+ comment);
			}		
		}
	}
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__dmi = null;
	__filterPanel = null;
	IOUtil.nullArray(__districtWhere);
	__reportVector = null;
	__results = null;
	__strtypesVector = null;
	__useVector = null;
	super.finalize();
}

/**
Gets a data line for a transaction report.
@param n the HydroBase_Transact object with which to populate the line of data.
@param reportType the type of report in which this line of data will be used.
@return a line of data for a transaction report.
@throws Exception if an invalid reportType is passed in or if an error occurs.
*/
private String getTransactionLine(HydroBase_Transact n, int reportType)
throws Exception {
	int maxUseLength = 10;
	List v = new Vector(100, 10);
	boolean isExtended = false;
	if (reportType >= EXTENDED_1) {
		isExtended = true;
	}	

	v.add(format(n.getWD()));
	v.add(format(n.getID()));
	v.add(n.getWr_name());

	// create structure type string
	v.add(createStructureType(n.getStrtype()));

	v.add(format(n.getWr_stream_no()));
	v.add(n.getWd_stream_name());
	v.add(format(n.getCty()));

	String q10 = new String(n.getQ10());
	String q40 = new String(n.getQ40());
	String q160 = new String(n.getQ160());
	if (DMIUtil.isMissing(q10)) {
		q10 = "  ";
	}
	if (DMIUtil.isMissing(q40)) {
		q40 = "  ";
	}
	if (DMIUtil.isMissing(q160)) {
		q160 = "  ";
	}
	v.add(q10 + q40 + q160);
	v.add(format(n.getSec()));
	v.add(n.getTS() + " " + n.getTdir());
	v.add(n.getRng() + " " + n.getRdir());
	v.add(n.getPM());
	v.add(translateNewToReport(n.getUse(), maxUseLength));

	if (DMIUtil.isMissing(n.getRate_amt())) {
		v.add(format(n.getVol_amt(), "%12.4f"));
		v.add(getUnit("A"));
	}
	else {
		v.add(format(n.getRate_amt(), "%12.4f"));
		v.add(getUnit("C"));
	}

	v.add(createStringAdjType(n));

	if (!DMIUtil.isMissing(n.getAdj_date())) {
		v.add((new DateTime(n.getAdj_date())).
			toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY));
	}
	else {
		v.add("");
	}

	if (!DMIUtil.isMissing(n.getPadj_date())) {
		v.add((new DateTime(n.getPadj_date())).
			toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY));
	}
	else {
		v.add("");
	}

	if (!DMIUtil.isMissing(n.getApro_date())) {
		v.add((new DateTime(n.getApro_date())).
			toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY));
	}
	else {
		v.add("");
	}

	v.add(format(n.getOrder_no()));

	double admin_no = n.getAdmin_no();

	if (admin_no == __lastAdminNum) {
		v.add(new String(format(admin_no, "%11.5f")+ "*"));
	}
	else {
		v.add(new String(format(admin_no, "%11.5f")));
	}
	// keep track of the last admin number
	__lastAdminNum = admin_no;

	v.add(n.getPrior_no());
	v.add(n.getCase_no());
	v.add(n.getAug_role());

	// create alter ID
	if (n.getTransfer_type()!= null) {
		v.add(format(n.getTran_wd())+ format(n.getTran_id()));
	}
	else if (n.getAssoc_type()!= null) {
		v.add(format(n.getAssoc_wd())+ format(n.getAssoc_id()));
	}
	else {
		v.add("");
	}

	if (isExtended) {
		v.add(format(n.getPlan_wd())+ format(n.getPlan_id()));
		v.add(n.getLast_due_dil());
		if (n.getAction_update()!= null) {
			v.add((new DateTime(n.getAction_update())).
			toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY));
		}
		else {	
			v.add("");
		}
	}
	else {
		v.add(n.getAction_comment());
	}

	if (isExtended) {
		return StringUtil.formatString(v, 
			EXTENDED_WATER_RIGHTS_FORMAT);
	}
	else {
		return StringUtil.formatString(v, 
			STANDARD_WATER_RIGHTS_FORMAT);
	}	
}

/**
Returns a header for a transaction report.
@param reportType the type of report for which to return a header.
@return a Vector of values that will be assigned to __reportVector to be
the first values in the report vector.
@throws Exception if an error occurs.
*/
private List getTransactionReportHeader(int reportType)
throws Exception {
	List v = new Vector(17, 1);
	List report = new Vector();

	boolean isExtended = false;
	if (reportType >= EXTENDED_1) {
		isExtended = true;
	}
	
//	This was the original code:
//	propval = _props.getValue("Region");
//	String region = "";
//	if (propval != null)
//		region = propval;
// 	but region was never actually set in the old code, so it remains an
// 	empty string here.
	String region = "";

	String typeString = null;
	String topHeader = null;
	String dividerLine = null;
	if (isExtended) {
		typeString = "E X T E N D E D";
		topHeader =
			"%3.3s %5.5s %-24.24s %-10.10s %-24.24s %25.25s " +
			"%-10.10s %12.12s %1.1s %8.8s %-10.10s %-10.10s " +
			"%-10.10s %2.2s %-12.12s %-9.9s %-9.9s %-1.1s %-7.7s "+
			"%7.7s %9.9s %10.10s";
		dividerLine = 
			"--- ----- ------------------------ ---------- " +
			"------------------------ ------------------------- " +
			"---------- ------------ - -------- ---------- " +
			"---------- ---------- -- ------------ --------- " +
			"--------- - ------- ------- --------- ----------";
	}
	else {
		typeString = "S T A N D A R D";
		topHeader =
			"%3.3s %5.5s %-24.24s %-10.10s %-24.24s %25.25s " +
			"%-10.10s %12.12s %1.1s %8.8s %-10.10s %-10.10s " +
			"%-10.10s %2.2s %-12.12s %-9.9s %-9.9s %-1.1s %-7.7s "+
			"%-65.65s";
		dividerLine = 
			"--- ----- ------------------------ ---------- " +
			"------------------------ ------------------------- " +
			"---------- ------------ - -------- ---------- " +
			"---------- ---------- -- ------------ --------- " +
			"--------- - ------- ----------------------------" +
			"-------------------------------------";
	}


	// 
	// explanation of codes header
	//
	report.add(
		"                              START --> W A T E R  " +
		"R I G H T S  R E P O R T");
	report.add(
		"                                           " + 
		((new DateTime(new Date())).
		toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY)));
	report.add("");
	report.add("");
	report.add("");
	report.add("");
	report.add(
		"                                Title:         " +
		region);
	report.add("");
	report.add("");
	report.add("");
	report.add("");
	report.add("");
	report.add("");
	report.add("");
	report.add(
		"---------------------------------------------" +
		"------------------------------" +
		"-------------------------------------------" +
		"--------------------------------");
	report.add("");
	report.add("");
	report.add("");
	report.add("         E X P L A N A T I O N  O F  " +
		"C O D E S");
	report.add("");
	report.add("");
	report.add(
		"         Struct Type: D ditch, E seep, " +
		"L pipeline, M mine, O other, P pump, R reservoir, " +
		"S spring, W well, Z power plant, * means more than " +
		"three structure types are decreed");
	report.add("");
	report.add("");
	report.add(
		"         Use Codes:   A augmentation, B basin " +
		"export, C commercial, D domestic, E evaporation, " +
		"F fire, f forest, G geothermal, H household use " +
		"only, I irrigation,");
	report.add("");
	report.add(
		"                      K snowmaking, M municipal, " +
		"m minimum streamflow, N industrial, O other, P " +
		"fishery, p power generation, R recreation, r " +
		"recharge, S stock,");
	report.add("");
	report.add(
		"                      W wildlife, X all beneficial " +
		"uses, * means more than three uses are decreed");
	report.add("");
	report.add("");
	report.add(
	"         Adj Type:    AB abandoned, AP alternate " +
	"point, C conditional, CA conditional made absolute, " +
	"EX exchange, O original, S supplemental, TF " +
	"transfer from, TT transfer to");
	report.add("");
	report.add("");
	report.add(
		"         Admin Number is a number developed by " +
		"DWR to provide a simple and efficient method of " +
		"ranking decrees in order of seniority.");
	report.add("");
	report.add("");
	report.add("");
	report.add("");
	report.add("");
	report.add("");

	//
	// column header
	//
	report.add(region + "    " +
		((new DateTime(new Date())).
		toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY))+
		"                                                   " + 
		"                      " + typeString +
		"   W A T E R   R I G H T S   R E P O R T");
	report.add("");

	// NOTE:
	// some of the column titles are left justified, some are right
	// justified, some are centered(yea!).  Right and left
	// justication are handled with the format string.  To center,
	// I have specified left justification
	// in the format string and added spaces preceeding
	// the word itself in the following adds.  
	//

	// add column titles, line 1
	v.add("WD");
	v.add("ID");
	v.add("NAME OF STRUCTURE");
	v.add("STRUCTURE");
	v.add("-------- STREAM --------");
	v.add("--- L O C A T I O N  ---");
	v.add("  USE");
	v.add("DECREED");
	v.add("U");
	v.add("ADJ");
	v.add("   ADJ");
	v.add("PREV ADJ");
	v.add("  APPROP");
	v.add("O");
	v.add("   ADMIN");
	v.add(" PRIOR");
	v.add("COURT");
	v.add("P");
	v.add(" ALTER");
	if (isExtended) {
		v.add("PLAN");
		v.add("LAST");
		v.add("LAST");
	}
	else {
		v.add("COMMENTS");
	}

	report.add(StringUtil.formatString(v, topHeader));

	// add column titles, line 2
	v.clear();
	v.add("");
	v.add("");
	v.add("");
	v.add("TYPE");
	v.add(" #   NAME");
	v.add("Cty Q-Q-Q Sec TS  Rng  PM");
	v.add(" CODE");
	v.add("AMOUNT");
	v.add("");
	v.add("TYP");
	v.add("   DATE");
	v.add("   DATE");
	v.add("   DATE");
	v.add("#");
	v.add("   NUMBER");
	v.add("  NO");
	v.add("CASE");
	v.add("A");
	v.add("  ID");
	if (isExtended) {
		v.add("ID");
		v.add("DILIGENCE");
		v.add("UPDATE");
	}
	else {
		v.add("");
	}

	report.add(StringUtil.formatString(v, topHeader));
	
	// add ---
	report.add(dividerLine);
	return report;
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
	if (DMIUtil.isMissing(d)) {
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
@throws Exception if an error occurs
*/
private String format(int i)
throws Exception {
	if (DMIUtil.isMissing(i)) {
		return "";
	}
	else {	
		return "" + i;
	}
}

/**
Returns the report that was generated.
@return the report that was generated.
*/
public List getReport() {
	return __reportVector;
}

/**
Returns the structure type report code given a structure type (abbreviation)
@param structureType A structure type string from the str_type table.
@return The structure type report code given a structure type (abbreviation)
@throws Exception if an error occurs.
*/
private String getStructureTypeReportCode(String structureType)
throws Exception {
	HydroBase_DssStructureType hbst;

	if (__strtypesVector == null) {
		return "";
	}

	int size = __strtypesVector.size();

	for (int i = 0; i < size; i++) {
		hbst = (HydroBase_DssStructureType)(__strtypesVector.get(i));

		if (hbst.getStr_type().equalsIgnoreCase(structureType)) {
			return hbst.getRpt_code();
		}
	}
	return "";
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
	else if (s.equalsIgnoreCase("A")) {
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
the records returned from the query the constructor executed )is not 
reinitialized by this method.  This is so that multiple reports can be 
generated from a single HydroBase_Report_Transaction object without having 
to requery the database every time, provided the ORDER BY clauses are the same
for each report.
*/
private void initialize() {
	__reportVector = null;	
	__lastAdminNum = DMIUtil.MISSING_DOUBLE;
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
	while(start < len) {
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
