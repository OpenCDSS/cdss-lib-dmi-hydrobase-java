// ----------------------------------------------------------------------------
// HydroBase_Report_StructureSummary - Class for creating structure summary
//	reports.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-20	J. Thomas Sapienza, RTi	Initial version.
// 2003-02-21	JTS, RTi		Javadoc'd, reviewed, revised, cleaned.
// 2003-02-24	JTS, RTi		- Added finalize() method.
//					- Converted underscore section headers
//					  to dashes.
//					- Removed unnecessary constructor.
//					- Combined createSummaryForWDID() and
//					  createSummary()
// 2003-02-25	JTS, RTi		Converted TSDate to DateTime.
// 2003-03-13	JTS, RTi		Added constructor that takes a 
//					structure num.
// 2003-03-27	JTS, RTi		* Added check for null AND empty result
//					  sets being returned from queries.
//					* Corrected a wrong value being 
//					  displayed (getStr_name() replaced with
//					  getStrname())
//					* Action comment was being truncated 
//					  in some reports, stopped that.
// 2003-12-09	JTS, RTi		Changed the annual amounts query in
//					response to changes in how the dmi
//					joins tables together.
// 2003-01-08	JTS, RTI		Monthly data was being printed out
//					in year order (January - December) 
//					when it should be printed out from
//					November through October.
// 2004-05-05	JTS, RTi		Revised irrigated acres query to reflect
//					changes made to HydroBaseDMI.
// 2004-05-10	JTS, RTi		Corrected error in formatting decreed
//					volume amounts.
// 2004-05-11	JTS, RTi		* Corrected error in report generation
//					  for irrigated acres summary that was
//					  causing every line to be the same as
//					  the first.
//					* Added ACRES UNKNOWN column to 
//					  irrigated acres summary.
//					* Irrigated acres summary report was
//					  actually GIS irrigated acres report, 
//					  so the second was replaced with the
//					  first.
//					* Added a new irrigated acres summary
//					  report.
// 2004-07-08	JTS, RTi		* Added a water rights net query report.
//					* Added more diversion comment support
//					  to diversion summary.
// 2004-07-12	SAM, RTi		* Minor change.  There are actually
//					  "Release comments" that are stored
//					  with the diversion comments so change
//					  some wording slightly.
// 2005-02-11	JTS, RTi		readStructureGeolocForStructure_num()
//					was changed to use stored procedures,
//					requiring changes here (such as using
//					the structure view object in places).
// 2005-02-16	JTS, RTi		Converted queries to use stored
//					procedures.
// 2005-03-24 	JTS, RTi		Converted to support View objects.
// 2005-03-28	JTS, RTi		Further work to support View objects.
// 2005-04-28	JTS, RTi		Added all data members to finalize().
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
// 2005-07-13	JTS, RTi		Changed the logic of the report 
//					slightly when it comes to diversion
//					summary information based on suggestions
//					from Doug Stenzel:  
//					* Comments are no longer shown right 
//					   under to the year they are for.
//					* Infrequent years are marked as such
//					  and shown in the summary.
//					* Infrequent year data is used for
//					  averages and totals if it is 
//					  available and if data doesn't exist
//					  for a normal year. 
//					* Zeroes are shown now instead of being
//					  displayed as blanks.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import RTi.DMI.DMIUtil;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.TimeUtil;

/**
Class for creating structure summary reports.  This class will generate a
Vector of Strings.  Each String is a separate line in the report.  This class
was mostly taken from old libHBDMI code, though changes were made to accommodate
the new HydroBaseDMI design. 
*/
public class HydroBase_Report_StructureSummary {

/**
This value is used to do a diversion annual amount query. 
*/
private final int __DIVERSION = 0;

/**
This value is used to do a reservoir annual amount query.
*/
private final int __RESERVOIR = 1;

/**
This String defines the line used as a breaking point within the report.
*/
private final String __breakLine = 
	  "-----------------------------------------------"
	+ "-----------------------------------------------"
	+ "-----------------------------------------------";

/**
Strings to tell whether to do diversion releases or release totals.
*/
private final String
	__DIV_TOTAL = "DivTotal",
	__REL_TOTAL = "RelTotal";

/**
Reference to an open DMI to be used for querying HydroBase.
*/
private HydroBaseDMI __dmi = null;

/**
The structure for which the report will be generated.  This object is filled
in the createSummaryForWDID method.
*/
private HydroBase_StructureView __view = null;

/**
The StructureView object that matches the structure object.
*/
private HydroBase_StructureView __structureView;

/**
This String holds the rolodex number associated with the structure.  Rolodex_num
values are actually integers, but this is used so that if the value of the 
integer rolodex_num is missing the String "NONE" will be used instead.
*/
private String __rolodexNum = null;

/**
This is the list that holds all the lines of the report.
*/
private List<String> __reportList = null;

/**
Constructor.  Once this constructor is called, the summary is generated
and ready to be retrieved with the getReport() method.
@param dmi a reference to an already-open DMI that can be used to connect
to HydroBase.
@param wd the wd for the structure to summarize.
@param id the id for the structure to summarize.
@exception Exception if the dmi value is null or is not open.
*/
public HydroBase_Report_StructureSummary(HydroBaseDMI dmi, int wd, int id)
throws Exception {
	if (dmi == null) {
		throw new Exception("HydroBase_Report_StructureSummary was "
			+ "passed a null DMI object -- cannot do anything "
			+ "without a valid DMI object.");
	}
	if (!dmi.isOpen()) {
		throw new Exception("HydroBase_Report_StructureSummary was "
			+ "passed a closed DMI object -- cannot do anything "
			+ "without an open DMI connection.");
	}
	__dmi = dmi;
	__reportList = new ArrayList<String>();
	createSummaryForWDID(wd, id);
}

/**
Constructor.  Once this constructor is called, the summary is generated
and ready to be retrieved with the getReport() method.
@param dmi a reference to an already-open DMI that can be used to connect
to HydroBase.
@param structure_num the number of the structure for which to report.
@exception Exception if the dmi value is null or is not open.
*/
public HydroBase_Report_StructureSummary(HydroBaseDMI dmi, int structure_num)
throws Exception {
	if (dmi == null) {
		throw new Exception("HydroBase_Report_StructureSummary was "
			+ "passed a null DMI object -- cannot do anything "
			+ "without a valid DMI object.");
	}
	if (!dmi.isOpen()) {
		throw new Exception("HydroBase_Report_StructureSummary was "
			+ "passed a closed DMI object -- cannot do anything "
			+ "without an open DMI connection.");
	}
	__dmi = dmi;
	int[] WDID = __dmi.readWDIDForStructure_num(structure_num);
	__reportList = new ArrayList<String>();
	createSummaryForWDID(WDID[0], WDID[1]);
}

/**
Appends the annual amount query results to the report.
@param results list containing the results from an annual amount record query.
@param comments the comments for the structure
@param TYPE - _DIVERSION if querying for diversion records,
_RESERVOIR if querying for reservoir records
@exception Exception if an error occurs
*/
private void appendAnnualAmtResults(List<HydroBase_AnnualAmt> results, List<HydroBase_DiversionComment> comments, int TYPE,
String measTypeString)
throws Exception {	
	__reportList.add("");
	__reportList.add("");

        // For now, create a temporary measurement type since we only
        // use totals in this class...
	HydroBase_StructMeasType measType = new HydroBase_StructMeasType();
	measType.setMeas_type(measTypeString);

	List<String> QInfoResults = formatMonthlyDiversionForQINFO(results, measType);
	if (QInfoResults == null) {
		return;
	}
	
        int size = QInfoResults.size();  
        for (int i=0; i<size; i++) {
                __reportList.add(QInfoResults.get(i).toString());
        }

        List<String> QInfoComments = formatStructureSummaryComments(comments, measType);
	if (QInfoComments == null) {
		return;
	}
	
        size = QInfoComments.size();  
        for (int i=0; i<size; i++) {
                __reportList.add(QInfoComments.get(i).toString());
        }	
}

/**
Appends the annual amount query results to the report.
@param results Vector containing the results from an annual amount record query.
@param comments the comments for the structure
@param TYPE - _DIVERSION if querying for diversion records,
_RESERVOIR if querying for reservoir records
@param measTypeString __DIV_TOTAL or __REL_TOTAL.
@exception Exception if an error occurs
*/
private void appendComments(List<HydroBase_DiversionComment> comments, int TYPE, String measTypeString)
throws Exception {	
	__reportList.add("");
	__reportList.add("");

        // For now, create a temporary measurement type since we only
        // use totals in this class...
        HydroBase_StructMeasType measType = new HydroBase_StructMeasType();
	measType.setMeas_type(measTypeString);

	List<String> QInfoComments = formatStructureSummaryComments(comments, measType);
	if (QInfoComments == null) {
		return;
	}
	
        int size = QInfoComments.size();  
        for (int i=0; i<size; i++) {
                __reportList.add(QInfoComments.get(i));
        }	

/*
        Vector QInfoResults = formatCommentsForQINFO(comments, measType);
	if (QInfoResults == null) {
		return;
	}
	
        int size = QInfoResults.size();  
        for (int i = 0; i < size; i++) {
                __reportVector.add(QInfoResults.elementAt(i).toString());
        }
*/
}

/**
Appends the equipment query results to the report.
@param results list containing the results from the equipment query.
@exception Exception if an error occurs
*/
private void appendEquipmentResults(HydroBase_Equipment data)
throws Exception {	
        String qInfoString;
        String device = data.getMeas_device().trim();
        String recorder = data.getRecorder().trim();

        if (!(device.equals("")) && !(recorder.equals(""))) {
                qInfoString = "MEASURING DEVICE/RECORDER: " 
			+ device + "/" + recorder;
        }
        else if (!(device.equals(""))) {
                qInfoString = "MEASURING DEVICE/RECORDER: " 
			+ device + recorder;
        }
        else if (!(recorder.equals(""))) {
                qInfoString = "MEASURING DEVICE/RECORDER: " 
			+ device + recorder;
        }
        else {	
		qInfoString = "";
        }

        __reportList.add(qInfoString);
}

/**
Append the irrigated acres query results to the report.
@param results list containing the results from the irrigated acres query.
@exception Exception if an error occurs
*/
private void appendIrrigatedAcresResults(List<HydroBase_StructureView> results)
throws Exception {	
	__reportList.add("");
    double curDouble;
	HydroBase_StructureView data = null;
    int curInt;
	String curString;
	List<Object> v = null;
 
	__reportList.add("                        IRRIGATED ACRES FROM "
		+ "GIS DATA -- BY CROP, YEAR, AND IRRIGATION METHOD");
	__reportList.add("                                                   "
		+ "                                        ACRES       "
		+ "ACRES TOTAL");
	__reportList.add("                                 ACRES       "
		+ "ACRES       ACRES       ACRES       ACRES     TOTAL       "
		+ "ALL CROPS");
	__reportList.add("  YEAR  CROP                     FLOOD       "
		+ "FURROW      SPRINKLER   DRIP        UNKNOWN   (FOR CROP)  "
		+ "(FOR YEAR)");
	__reportList.add("---------------------------------------------"
		+ "------------------------------------------------------"
		+ "-------------");

	if (results == null || results.isEmpty()) {
		__reportList.add("");	
		return;
	}

	int size = results.size();
	String format="%6.6s%22.22s%12.12s%12.12s%12.12s%12.12s%12.12s%12.12s%12.12s";

	double sum = 0;
	double diff = 0;

	int[] years = new int[size];
	double[] acres = new double[size];

	for (int i = 0; i < size; i++) {
		data = results.get(i);
		years[i] = data.getCal_year();
		acres[i] = data.getAcres_total();
	}

	List<String> formattedOutput = new ArrayList<String>();
	for (int i = 0; i < size; i++) {
		data = results.get(i);
		v = new ArrayList<Object>();
		sum = 0;

		curInt = data.getCal_year();
		if (!DMIUtil.isMissing(curInt)) {
			v.add(StringUtil.formatString(curInt, "%4d"));
		}
		else {
			v.add("");
		}

		curString = data.getLand_use();
		if (!DMIUtil.isMissing(curString)) {
			v.add(StringUtil.formatString(curString, "%-20.20s"));
		}
		else {
			v.add("");
		}

		curDouble = data.getAcres_by_flood();
		if (!DMIUtil.isMissing(curDouble)) {
			sum += curDouble;
			v.add(StringUtil.formatString(curDouble, "%10.3f"));
		}
		else {
			v.add("");
		}
	
		curDouble = data.getAcres_by_furrow();
		if (!DMIUtil.isMissing(curDouble)) {
			sum += curDouble;
			v.add(StringUtil.formatString(curDouble, "%10.3f"));
		}
		else {
			v.add("");
		}

		curDouble = data.getAcres_by_sprinkler();
		if (!DMIUtil.isMissing(curDouble)) {
			sum += curDouble;
			v.add(StringUtil.formatString(curDouble, "%10.3f"));
		}
		else {
			v.add("");
		}

		curDouble = data.getAcres_by_drip();
		if (!DMIUtil.isMissing(curDouble)) {
			sum += curDouble;
			v.add(StringUtil.formatString(curDouble, "%10.3f"));
		}
		else {
			v.add("");
		}

		curDouble = data.getAcres_total();
		
		if (!DMIUtil.isMissing(curDouble)) {
			diff = curDouble - sum;

			// cover up any rounding errors caused by lack of 
			// precision in the numbers ...
			if (diff <= .001 && diff > 0) {
				diff = 0;
			}
			if (diff >= -.001 && diff < 0) {
				diff = 0;
			}

			v.add(StringUtil.formatString(diff, "%10.3f"));
			v.add(StringUtil.formatString(curDouble, "%10.3f"));
		}
		else {
			v.add("");
			v.add("");
		}
		v.add(determineIrrigLineTotal ( results, curInt ) );
		formattedOutput.add(StringUtil.formatString(v, format));
	}
	// Sort the results, which will be by year and crop type.  This used to happen but
	// the stored procedure order by is not working somehow.
	StringUtil.addListToStringList(__reportList,
		StringUtil.sortStringList(formattedOutput) );
	__reportList.add("");
}                 

/**
Appends the irrigated acres summary query results to the report.
@param results list containing the results from the irrigated acres summary query.
@exception Exception if an error occurs
*/
private void appendIrrigatedAcresSummaryResults(List<HydroBase_StructureView> results)
throws Exception {	
	__reportList.add("");
        double curDouble;
        int curInt;
        List<Object> v1 = new ArrayList<Object>();
        List<Object> v2 = new ArrayList<Object>();
        List<Object> v3 = new ArrayList<Object>();

	__reportList.add("        IRRIGATED ACRES SUMMARY -- TOTALS FROM "
		+ "VARIOUS SOURCES");
	__reportList.add("-----------------------------------------------"
		+ "----------------------------");
 
	if (results != null && !results.isEmpty()) {
		HydroBase_StructureView view = results.get(0);
		
		String format = "%-35.35s%10.10s%12.12s%4.4s";

		v1.add("GIS Total (Acres):");
		curDouble = view.getTia_gis();
		if (!DMIUtil.isMissing(curDouble)) {
			v1.add(StringUtil.formatString(curDouble,"%10.1f"));
		}
		else {	
			v1.add("");
		}

		v1.add("Reported: ");
		curInt = view.getTia_gis_calyear();
		if (!DMIUtil.isMissing(curInt)) {
			v1.add(StringUtil.formatString(curInt, "%4d"));
		}
		else { 
			v1.add("");
		}
		__reportList.add(StringUtil.formatString(v1,format));

		v2.add("Diversion Comments Total (Acres):");
		curDouble = view.getTia_div();
		if (!DMIUtil.isMissing(curDouble)) {
			v2.add(StringUtil.formatString(curDouble,"%10.1f"));
		}
		else {	
			v2.add("");
		}
		
		v2.add("Reported: ");
		curInt = view.getTia_div_calyear();
		if (!DMIUtil.isMissing(curInt)) {
			v2.add(StringUtil.formatString(curInt, "%4d"));
		}
		else {	
			v2.add("");
		}
		__reportList.add(StringUtil.formatString(v2, format));

		v3.add("Structure Total (Acres):");
		curDouble = view.getTia_struct();
		if (!DMIUtil.isMissing(curDouble)) {
			v3.add(StringUtil.formatString(curDouble,"%10.1f"));
		}
		else {	
			v3.add("");
		}

		v3.add("Reported: ");
		curInt = view.getTia_struct_calyear();
		if (!DMIUtil.isMissing(curInt)) {
			v3.add(StringUtil.formatString(curInt, "%4d"));
		}
		else {	
			v3.add("");
		}

		__reportList.add(StringUtil.formatString(v3, format));
	}

	__reportList.add("");
}                 

/**
Appends to the report the query results from the rolodex query.
@param results Vector containing the results from the rolodex query.
@exception Exception if an error occurs
*/
private void appendRolodexResults(HydroBase_Rolodex data)
throws Exception {
	String curString;
	String title;

        curString = data.getTitle();
        if (curString.equals("")) {
                title = "";
        }
	else {	
		title = " (" + curString + ")";
        }
        __reportList.add(
		"CONTACT: " + data.getFull_name() + " " + title);


        curString = data.getAddress1();          
        if (!(curString.equals(""))) {
		__reportList.add("ADDRESS 1: " + curString);
        }

        curString = data.getAddress2();          
        if (!(curString.equals(""))) {
                __reportList.add("ADDRESS 2: " + curString);
        }

        String city = data.getCity();
        String state = data.getST();
	String zipCode = data.getZip();
        if (!(city.equals("")) && !(state.equals(""))) {
		__reportList.add("CITY/STATE/ZIP: " + city + " "
			+ state + " " + zipCode);
        }
}

/**
Appends to the report the structure/location query results.
@param results Vector containing the results from the structure/location query.
@exception Exception if an error occurs
*/
private void appendStructureViewResults(HydroBase_StructureView data) 
throws Exception {	
	double curDouble;
	int curInt;
	long curLong;;
	String qInfoString = "";
	String rng;
	String sec;
	String ts;

        __reportList.add(StringUtil.centerString(
		"STRUCTURE SUMMARY FOR:  " + data.getStr_name(), 80));
	__reportList.add("");

        qInfoString += "WATER DISTRICT: ";
        curInt = data.getWD();
        if (!DMIUtil.isMissing(curInt)) {
		qInfoString += + curInt;
        }
	__reportList.add(qInfoString);

        qInfoString = "ID NUMBER: ";
        curInt = data.getID();
        if (!DMIUtil.isMissing(curInt)) {
		qInfoString += curInt;
        }
	__reportList.add(qInfoString);
 
        qInfoString = "WATER SOURCE: " + data.getStrname(); 
        qInfoString += " AT STREAM MILE: ";
        curDouble = data.getStr_mile();
        if (!DMIUtil.isMissing(curDouble)) {
        	qInfoString += StringUtil.formatString(curDouble,"%.2f");
        }
	__reportList.add(qInfoString);

        qInfoString = "LOCATION: ";

        curInt = data.getTS();
        if (DMIUtil.isMissing(curInt)) {
                        ts = "";
	}
	else {	
		ts = "" + curInt;
        }
        curInt = data.getRng();
        if (DMIUtil.isMissing(curInt)) {
                rng = "";
        }
	else {	
		rng = "" + curInt;
        }
        curInt = data.getSec();
        if (DMIUtil.isMissing(curInt)) {
                sec = "";
        }
	else {	
		sec = "" + curInt;
        }
	qInfoString += ts + data.getTdir() 
                        + " " + rng +  data.getRdir()
                        + " " + sec
                        + " " + data.getQ160() 
                        + " " + data.getQ40() 
                        + " " + data.getQ10() 
                        + " IN " + data.getCounty() + " COUNTY";
	__reportList.add(qInfoString);

	// This is only valid for older databases...
       	qInfoString = "TOTAL IRRIGATED ACRES: ";
	if (!__dmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_19990305)) {
		// Older database has total in the structure table...
		curDouble = DMIUtil.MISSING_DOUBLE;
//        	curDouble = data.getXtia();
        	if (!DMIUtil.isMissing(curDouble)) {
                	qInfoString += curDouble;
        	}
	}
	else {	
		qInfoString += "See irrigated acres summary.";
	}
	__reportList.add(qInfoString);
 
	qInfoString = "STRUCTURE TYPE: ";
	if (__structureView != null) {
		qInfoString += __dmi.getStructureTypeDescription(
			__structureView.getStr_type());
	}		
	else {
		qInfoString += "NO LOCATION INFORMATION AVAILABLE";
	}
	__reportList.add(qInfoString);

	qInfoString = "CIU (CURRENTLY IN USE): ";
	String desc = null;
	if (__view != null) {
		desc = __dmi.lookupCIUDescription(__view.getCiu());
	}

	if (desc != null) {
		qInfoString += desc;
	}
	__reportList.add(qInfoString);

	qInfoString = "IS TRANSBASIN: ";
	if (__view != null && __view.getTransbsn() == 1) {
		qInfoString += "YES";
	}
	__reportList.add(qInfoString);
 
        qInfoString = "ESTIMATED CAPACITY: ";
        curDouble = data.getEst_capacity();
        if (!DMIUtil.isMissing(curDouble)) {
                qInfoString += StringUtil.formatString(curDouble, "%10.4f")
			+ " " + data.getEst_unit();
        }
	__reportList.add(qInfoString);

        qInfoString = "DECREED CAPACITY (SUM OF ABSOLUTE NET AMOUNT RIGHTS): ";
        curDouble = data.getDcr_capacity();
        if (!DMIUtil.isMissing(curDouble)) {
                qInfoString += StringUtil.formatString(curDouble, "%10.4f")
			+ " " + data.getDcr_unit();
        } 
	__reportList.add(qInfoString);

	// Need for later queries...
 
        curLong = data.getRolodex_num();
        if (DMIUtil.isMissing(curLong)) {
                __rolodexNum = "NONE";
        }
	else {	
		__rolodexNum = "" + curLong;
        }
}

/**
Appends to the report the water right net query results.
@param results Vector containing the results from the water right net query.
@exception Exception if an error occurs
*/
private void appendWaterRightNetResults(List<HydroBase_NetAmts> results)
throws Exception {	
        Date curDate;    
        double curDouble;
	HydroBase_NetAmts data;
	int curInt;
	String curString;
	List<Object> netFields = new ArrayList<Object>(10);

        __reportList.add(StringUtil.centerString (
		"WATER RIGHTS NET AMOUNT INFORMATION", 110) );

	// NOTE:
	// the rate and volume columns were sized down in order to save
	// space when printed landscape.  All fields technically should
	// be formatted as 12.4, but some are being shrunk to be 10.4 
	// following a max() on the field values in the database to see 
	// how large the values get.

	String format = "%-12.12s%-11.11s%-11.11s%-11.11s%-6.6s%-10.10s"
		+ "%-5.5s%-11.11s%-13.13s%-11.11s%-13.13s"
		+ "%-11.11s%-11.11s%-30.30s";
	
        netFields.add("");
        netFields.add("");
        netFields.add("");
        netFields.add("");
        netFields.add("ORDER");
        netFields.add("PRIOR");
        netFields.add("ADJ");
        netFields.add("RATE");
        netFields.add("VOL");
        netFields.add("RATE");
        netFields.add("VOL");
        netFields.add("RATE");
        netFields.add("VOL");
        netFields.add("USE");
	__reportList.add(StringUtil.formatString(netFields,format) );

        netFields.clear();
        netFields.add("ADMIN NO");
        netFields.add("ADJ DATE");
        netFields.add("PADJ DATE");
        netFields.add("APRO DATE");
        netFields.add("NO");
        netFields.add("CASE NO");
        netFields.add("TYPE");
        netFields.add("ABS (CFS)");
        netFields.add("ABS (AF)");
        netFields.add("COND (CFS)");
        netFields.add("COND (AF)");
        netFields.add("APEX (CFS)");
        netFields.add("APEX (AF)");
        netFields.add("TYPE");

	__reportList.add(StringUtil.formatString(netFields, format));
	__reportList.add(__breakLine + "----------");

        int size = results.size();

        for (int i = 0; i < size; i++) {
                data = results.get(i);  
                netFields.clear();
         
                curDouble = data.getAdmin_no();      
                if (DMIUtil.isMissing(curDouble)) {
                        curString = ""; }
                else {	
			curString = StringUtil.formatString(curDouble,"%11.5f");
                }
                netFields.add(curString);

                curDate = data.getAdj_date();
                if (DMIUtil.isMissing(curDate)) {
                        curString = "";
                }
		else {	
			curString = formatDate(curDate);
                }
                netFields.add(curString);
         
                 curDate = data.getPadj_date();
                if (DMIUtil.isMissing(curDate)) {
                        curString = "";
                }
		else {	
			curString = formatDate(curDate);
                }
                netFields.add(curString);

                curDate =  data.getApro_date();  
                if (DMIUtil.isMissing(curDate)) {
                        curString = "";
                }
		else {	
			curString = formatDate(curDate);
                }
                netFields.add(curString);

		curInt = data.getOrder_no();
		if (DMIUtil.isMissing(curInt)) {
			curString = "";
		}
		else {
			curString = StringUtil.formatString(curInt, "%5d");
		}
		netFields.add(curString);

                netFields.add(data.getPri_case_no());

		netFields.add(data.getAdj_type());

                curDouble = data.getNet_rate_abs();
                if (DMIUtil.isMissing(curDouble)) {
                        curString = "";
                }
		else {	
			curString = StringUtil.formatString(curDouble,"%10.4f");
                }
		netFields.add(curString);

                curDouble = data.getNet_vol_abs();
                if (DMIUtil.isMissing(curDouble)) {
                        curString = "";
                }
		else {	
			curString = StringUtil.formatString(curDouble,"%12.4f");
                }
		netFields.add(curString);

                curDouble = data.getNet_rate_cond();
                if (DMIUtil.isMissing(curDouble)) {
                        curString = "";
                }
		else {	
			curString = StringUtil.formatString(curDouble,"%10.4f");
                }
		netFields.add(curString);

                curDouble = data.getNet_vol_cond();
                if (DMIUtil.isMissing(curDouble)) {
                        curString = "";
                }
		else {	
			curString = StringUtil.formatString(curDouble,"%12.4f");
                }
		netFields.add(curString);

                curDouble = data.getNet_rate_apex();
                if (DMIUtil.isMissing(curDouble)) {
                        curString = "";
                }
		else {	
			curString = StringUtil.formatString(curDouble,"%10.4f");
                }
		netFields.add(curString);

                curDouble = data.getNet_vol_apex();
                if (DMIUtil.isMissing(curDouble)) {
                        curString = "";
                }
		else {	
			curString = StringUtil.formatString(curDouble,"%10.4f");
                }
		netFields.add(curString);

		netFields.add(data.getUse());
         
		__reportList.add(StringUtil.formatString(netFields, format));
        }
}

/**
Appends to the report the water right transaction query results.
@param results Vector containing the results from the water right transaction query.
@exception Exception if an error occurs
*/
private void appendWaterRightTransactionResults(List<HydroBase_Transact> results)
throws Exception {	
        Date curDate;    
        double curDouble;
	HydroBase_Transact data;
	String curString;
	List<Object> transFields = new ArrayList<Object>(10);

        __reportList.add(StringUtil.centerString(
		"WATER RIGHTS TRANSACTION INFORMATION", 110));

	String format = "%-12.12s%-11.11s%-11.11s%-12.12s%-11.11s%-11.11s" +
			"%-14.14s%-30.30s %-11.11s";

        transFields.add("");
        transFields.add("");
        transFields.add("");
        transFields.add("");
        transFields.add("DECREED");
        transFields.add("DECREED");
        transFields.add(" ADJUDICATION");
        transFields.add("");
        transFields.add("");
	__reportList.add(StringUtil.formatString(transFields,format));

        transFields.clear();
        transFields.add("ADMIN NO");
        transFields.add("ADJ DATE");
        transFields.add("APPRO DATE");
        transFields.add("COURT NO");
        transFields.add("RATE (CFS)");    
        transFields.add("VOL. (AF)");
        transFields.add(" TYPE");
        transFields.add("USES");
        transFields.add("COMMENT");
	__reportList.add(StringUtil.formatString(transFields, format) );
	__reportList.add(__breakLine);

	// Reset so comments don't get truncated...

	format = "%-12.12s%-11.11s%-11.11s%-12.12s%-11.11s%-11.11s" +
			" %-14.14s%-30.30s %s";

        int size = results.size();

	String adj = null;
	String temp = null;
	int count = 0;

        for (int i = 0; i < size; i++) {
                data = results.get(i);  
                transFields.clear();
         
                curDouble = data.getAdmin_no();      
                if (DMIUtil.isMissing(curDouble)) {
                        curString = ""; }
                else {	
			curString = StringUtil.formatString(curDouble,"%11.5f");
                }
                transFields.add(curString);

                curDate = data.getAdj_date();
                if (DMIUtil.isMissing(curDate)) {
                        curString = "";
                }
		else {	
			curString = formatDate(curDate);
                }
                transFields.add(curString);
         
                curDate =  data.getApro_date();  
                if (DMIUtil.isMissing(curDate)) {
                        curString = "";
                }
		else {	
			curString = formatDate(curDate);
                }
                transFields.add(curString);

                transFields.add(data.getCase_no());
         
                curDouble = data.getRate_amt();
                if (DMIUtil.isMissing(curDouble)) {
                        curString = "";
                }
		else {	
			curString = StringUtil.formatString(curDouble,"%7.2f");
                }
                transFields.add(curString);

                curDouble = data.getVol_amt();
                if (DMIUtil.isMissing(curDouble)) {
                        curString = "";
                }
		else {	
			curString = StringUtil.formatString(curDouble,"%7.2f");
                }
                transFields.add(curString);

		adj = "";
		count = 0;

		temp = data.getAdj_type();
		if (!DMIUtil.isMissing(temp)) {
			count++;
			adj += temp;
		}

		temp = data.getStatus_type();
		if (!DMIUtil.isMissing(temp)) {
			if (!temp.equals("A")) {
				count++;
				if (count > 0) {
					adj += ",";
				}
				adj += temp;
			}
		}
		
		temp = data.getTransfer_type();
		if (!DMIUtil.isMissing(temp)) {
			count++;
			if (count > 0) {
				adj += ",";
			}
			adj += temp;
		}
		
		temp = data.getAssoc_type();
		if (!DMIUtil.isMissing(temp)) {
			count++;
			if (count > 0) {
				adj += ",";
			}
			adj += temp;
		}
		
		temp = data.getAband();
		if (!DMIUtil.isMissing(temp)) {
			count++;
			if (count > 0) {
				adj += ",";
			}
			adj += temp;
		}

		transFields.add(adj);
		
                transFields.add(data.getUse());
                transFields.add(data.getAction_comment());

		__reportList.add(
			StringUtil.formatString(transFields, format) );
        }
}

/**
This method sets up the internal wd and id variables and also queries 
HydroBase for the structure for which to summarize based on those variables.
@param wd the wd of the structure to summarize.
@param id the id of the structure to summarize.
@exception Exception if an error occurs
*/
private void createSummaryForWDID(int wd, int id)
throws Exception {
	if (DMIUtil.isMissing(wd)) {
		throw new Exception("Invalid wd value (" + wd + ")");
	}
	if (DMIUtil.isMissing(id)) {
		throw new Exception("Invalid wd value (" + id + ")");
	}

	List<String> v = new ArrayList<String>(1);
	v.add(HydroBase_WaterDistrict.formWDID(wd, id));

	__view = __dmi.readStructureViewForWDID(wd, id);

	if (__view == null) {
		// Nothing to summarize...
		return;
	}

	__structureView = __dmi.readStructureViewForStructure_num(
		__view.getStructure_num());

        __rolodexNum = "";
        submitStructureGeolocQuery();

        submitEquipmentQuery();

	submitRolodexQuery();
        __reportList.add("");
        __reportList.add("");

        // submit water rights net query...
        __reportList.add("");
        __reportList.add("");

        // submit water rights transaction query...
        submitWaterRightTransactionQuery();
        __reportList.add("");
        __reportList.add("");

	// submit water rights net query
	submitWaterRightNetQuery();
	__reportList.add("");
	__reportList.add("");

        // submit irrigated acreage summary query...
        submitIrrigatedAcresSummaryQuery();
        __reportList.add("");
        __reportList.add("");

        // submit irrigated acreage query...
        submitIrrigatedAcresQuery();
        __reportList.add("");
        __reportList.add("");

	if (__view != null && __view.getStr_type().equals("R")) {
		// reservoir
	        submitAnnualAmtQuery(__RESERVOIR, __DIV_TOTAL);
	        submitAnnualAmtQuery(__RESERVOIR, __REL_TOTAL);
	}		
	else {
	        submitAnnualAmtQuery(__DIVERSION, __DIV_TOTAL);
	}

	// Add so reports can be concatenated...
        __reportList.add("");
        __reportList.add("");
}

/**
Cleans up member variables.
*/
public void finalize() 
throws Throwable {
	__dmi = null;
	__view = null;
	__structureView = null;
	__rolodexNum = null;
	__reportList = null;

	super.finalize();
}

/**
For a diversion summary report, searches through the provided list and sees
if there are any non-infrequent data with the given year.  If so, returns true.
If no infreq data with the given year are found, returns false.
@param v a list HydroBase_AnnualAmt data to look through.
@param year the year to search for in non-infreq data.
@return true if any of the non-infrequent data have the given year, false if not.
*/
private boolean findMatchingNonInfreqYear(List<HydroBase_AnnualAmt> v, int year) {
	HydroBase_AnnualAmt a = null;

	int size = v.size();

	for (int i = 0; i < size; i++) {
		a = v.get(i);
		if (a.getQuality() != null && a.getQuality().equalsIgnoreCase("I")) {
		    	// skip
		}
		else {
			if (a.getIrr_year() == year) {
				return true;
			}
		}
	}
	return false;
}

/**
Given a list of HydroBase_AnnualAmt or HydroBase_AnnualWC, format as per 
the legacy QINFO summary.
@return list of strings containing the diversion records as a QINFO
summary matrix.
@param comments list of diversion comments.
@param measType HBStructMeasType object
@exception Exception if an error occurs
*/
public List<String> formatCommentsForQINFO(List<HydroBase_DiversionComment> comments, HydroBase_StructMeasType measType)
throws Exception {	
	boolean hasSFUT;
	boolean yearPrinted = false;
	boolean isRes = false;

	if (comments == null) {
		return null;
	}

	int size = comments.size();
	if (size < 1) {
		return null;
	}

	String id = measType.getIdentifier();
	if (id.startsWith( "S") ) {
		hasSFUT = true;
	}
	else if (id.equals( "" ) ) {
		hasSFUT = false;
	}
	else {
		hasSFUT = false;
	}

	// The output period needs to be the overlapping period of the
	// diversion records and comments.  Do everything in terms of
	// irrigation year since that is what the diversion and comment records
	// use...
	int iyear = 0;
	int iyear1 = 9999;
	int iyear2 = 0;
	HydroBase_DiversionComment comment = null;
	if (size > 0) {
		comment = comments.get(0);
		iyear = comment.getIrr_year();
		if (iyear < iyear1) {
			iyear1 = iyear;
		}
		comment=comments.get(size-1);
		iyear = comment.getIrr_year();
		if (iyear > iyear2) {
			iyear2 = iyear;
		}
	}

	// Size the output vector for the number of records + comments + enough
	// for header/footer lines...
	List<String> s = new ArrayList<String>(size + 20);

	// Get the measurement type(it is assumed that all the records have the
	// same type) so that we can modify the header accordingly...
	String record_type = measType.getMeas_type();

	// Add the header...
	if (record_type.startsWith("Rel")) {
		isRes = true;
		// Reservoir records...
		if (hasSFUT) {
			s.add("                               RESERVOIR "
				+ "SUMMARY IN ACRE FEET FOR INDIVIDUAL CLASSES "
				+ "OF WATER");
		}
		else {	
			s.add("                               RESERVOIR "
				+ "SUMMARY IN ACRE FEET - TOTAL THROUGH "
				+ "STRUCTURE");
		}
	}
	else if (record_type.startsWith("Div")) {
		// Diversion records...
		if (hasSFUT) {
			s.add("                               DIVERSION "
				+ "SUMMARY IN ACRE FEET FOR INDIVIDUAL CLASSES "
				+ "OF WATER");
		}
		else {	
			s.add("                               DIVERSION "
				+ "SUMMARY IN ACRE FEET - TOTAL THROUGH "
				+ "STRUCTURE");
		}
	}

	if (hasSFUT) {
		s.add("YEAR S FROM UT  FDU   LDU  DWC  MAXQ    NOV    "
			+ "DEC    JAN    FEB    MAR    APR    MAY    JUN    "
			+ "JUL    AUG    SEP    OCT   TOTAL");
		s.add("------------------------------------------------"
			+ "---------------------------------------------------"
			+ "-----------------------------");
	}
	else {	
		s.add("YEAR  FDU   LDU  DWC  MAXQ & DAY    NOV    "
			+ "DEC    JAN    FEB    MAR    APR    MAY    JUN    "
			+ "JUL    AUG    SEP    OCT   TOTAL");
		s.add("------------------------------------------------"
			+ "---------------------------------------------------"
			+ "-------------------------");
	}

	// Now loop trough the records and format each year...	
	boolean have_diversion_comment = false;
	int comment_year = 0;	
	int j;		
	String comment_string;	
	String not_used;	

	// Old, looped through diversion records...
	//for (int iv = 0; iv < size; iv++) {
	// New, loop through years and output diversion records and comments...
	for (iyear = iyear1; iyear <= iyear2; iyear++) {
		// See if there are any comments for this irrigation year...
		// Loop through all of them each time...
		have_diversion_comment = false;
		for (j = 0; j < size; j++) {
			comment = comments.get(j);
			comment_year = comment.getIrr_year();
			if (comment_year == iyear) {
				have_diversion_comment = true;
				// Found a comment that matches the year being
				// processed.
				// Can conceivably have more than one comment
				// in a year so don't break!...
				comment_string = comment.getDiver_comment();
				not_used = comment.getNot_used();
				if (comment_string.length() > 0) {
					// i.e., NOT NULL
					// Have a specific comment.
					if (!yearPrinted) {
						s.add("" + iyear);
					}
					s.add("  " + comment_string);
					continue;
				}
				else if (not_used.equals("")) {
					// i.e., IS NULL
					continue;
				}
				else if (not_used.equalsIgnoreCase("A")) {
					yearPrinted = false;
					s.add(
					iyear + "  Structure is not usable");
				}
				else if (not_used.equalsIgnoreCase("B")) {
					yearPrinted = false;
					s.add(
					iyear + "  No water is available");
				}
				else if (not_used.equalsIgnoreCase("C")) {
					yearPrinted = false;
					s.add(iyear +
					"  Water available, but not taken");
				}
				else if (not_used.equalsIgnoreCase("D")) {
					yearPrinted = false;
					s.add(iyear +
					"  Water taken in another structure");
				}
				else if (not_used.equalsIgnoreCase("E")) {
					yearPrinted = false;
					s.add(iyear +
					"  Water taken but no data available");
				}
				else if (not_used.equalsIgnoreCase("F")) {
					yearPrinted = false;
					s.add(iyear +
					"  No information available");
				}
			}
		}
		if (!have_diversion_comment) {
			// Print a place-holder so each year is represented
			// in the output...
			s.add(iyear + "  No diversion record or comment available");
		}
	}
	// Now add the footer...
	if (hasSFUT) {
		s.add("--------------------------------------------"
			+ "-----------------------------------------------"
			+ "-------------------------------------");
	}
	else {	
		s.add("--------------------------------------------"
			+ "-----------------------------------------------"
			+ "---------------------------------");
	}

	if (!hasSFUT) {
	String div = "diversion";
	if (isRes) {
		div = "reservoir";
	}
	s.add("        The above summary lists total monthly " + div + "s and "
		+ div + " comments, if available.");
	}
	else {	
		// SFUT is not supported in this report yet...
		s.add("This report type is supported only for "
			+ "total through structure.");
	}
	// Return the output...
	return s;
}

/**
Simple routine to format a date as YYYY-MM-DD.
@return String Date formatted as a string.
@param date Date to format.
@exception Exception if an error occurs
*/
public String formatDate(Date date)
throws Exception {
	if (date == null) {
		return "";
	}
	String s = new DateTime(date).toString( DateTime.FORMAT_YYYY_MM_DD );
	return s;
}

/**
Formats the comments section of a Structure Summary.
@param comments the comments to print, sorted by year.  
@param measType the measType of the report (a HydroBase_StructMeasType object).
@return a list of Strings for the report.
*/
public List<String> formatStructureSummaryComments(List<HydroBase_DiversionComment> comments, HydroBase_StructMeasType measType)
throws Exception {	
	// Comments are now mainly tied to the structure so don't even involve
	// the struct_measType...

	// Although we are using a HydroBase_DiversionComment record, this
	// is used for Reservoir comments as well.
	HydroBase_DiversionComment comment = null;

	int csize = 0;
	if (comments != null) {
		csize = comments.size();
	}

	// it is assumed the comments Vector is sorted by year with the earliest
	// year first and the latest year last.  Thus the following code works
	// properly.
	int iyear = 0;
	int iyear1 = Integer.MAX_VALUE;
	int iyear2 = Integer.MIN_VALUE;
	if (csize > 0) {
		comment = comments.get(0);
		iyear = comment.getIrr_year();
		if (iyear < iyear1) {
			iyear1 = iyear;
		}
		comment=comments.get(csize-1);
		iyear = comment.getIrr_year();
		if (iyear > iyear2) {
			iyear2 = iyear;
		}
	}

	List<String> s = new ArrayList<String>();

	// Get the measurement type(it is assumed that all the records have the
	// same type) so that the header can be displayed accordingly...
	String record_type = measType.getMeas_type();

	// Add the header...
	if (record_type.startsWith("Rel")) {
		// Reservoir records...
		s.add("");
		s.add("");
		s.add("                               RESERVOIR COMMENTS");
	}
	else if (record_type.startsWith("Div")) {
		// Diversion records...
		s.add("");
		s.add("");
		s.add("                               DIVERSION COMMENTS");
	}
	
	s.add("YEAR  COMMENTS");
	s.add("------------------------------------------------");

	double acres_irrig = 0;
	String comment_string = null;
	String not_used = null;

	// See if there are any comments for this irrigation year...
	for (int j = 0; j < csize; j++) {
		comment = comments.get(j);
		iyear = comment.getIrr_year();

		// Can conceivably have more than one comment
		// in a year so don't break!...
		comment_string = comment.getDiver_comment();
		not_used = comment.getNot_used();
		acres_irrig = comment.getAcres_irrig();
		if (comment_string.length() > 0) {
			// i.e., NOT NULL,  Have a specific comment.
			s.add("" + iyear);
			if (!DMIUtil.isMissing(acres_irrig)
			    && !DMIUtil.isMissing(not_used)) {
				comment_string += " (ACRES IRRIG = "
					+ StringUtil.formatString(
					acres_irrig, "%10.3f").trim()
					+ ", NOT USED = " + not_used + ")";
			}
			else if (!DMIUtil.isMissing(acres_irrig)) {
			    	comment_string += " (ACRES IRRIG = "
					+ StringUtil.formatString(acres_irrig, 
					"%10.3f") .trim() + ")";
			}
			else if (!DMIUtil.isMissing(not_used)) {
				comment_string += " (NOT USED = " + not_used 
					+ ")";
			}
			s.add("  " + comment_string);
			continue;
		}
		else if (not_used.equals("")) {
			// i.e., IS NULL
			continue;
		}
		else if (not_used.equalsIgnoreCase("A")) {
			s.add(iyear + "  Structure is not usable");
		}
		else if (not_used.equalsIgnoreCase("B")) {
			s.add(iyear + "  No water is available");
		}
		else if (not_used.equalsIgnoreCase("C")) {
			s.add(iyear + "  Water available, but not taken");
		}
		else if (not_used.equalsIgnoreCase("D")) {
			s.add(iyear + "  Water taken in another structure");
		}
		else if (not_used.equalsIgnoreCase("E")) {
			s.add(iyear + "  Water taken but no data available");
		}
		else if (not_used.equalsIgnoreCase("F")) {
			s.add(iyear + "  No information available");
		}
	}

	s.add("");
	s.add("Note: Diversion comments and reservoir comments may be "
		+ "shown for a structure, if both are available.");	

	return s;
}

/**
Given a list of HydroBase_AnnualAmt or HydroBase_AnnualWC (which extends HydroBase_AnnualAmt),
format similar to the legacy QINFO summary.
@return list of strings containing the diversion records similar to a QINFO
summary matrix.
@param v list of HydroBase_AnnualAmt or HydroBase_AnnualWC objects.  It 
is assumed that these are sorted by irrigation year.
@param measType HBStructMeasType object
@exception Exception if an error occurs
*/
public List<String> formatMonthlyDiversionForQINFO(List<HydroBase_AnnualAmt> v, HydroBase_StructMeasType measType)
throws Exception {	
	boolean hasSFUT = false;
	boolean isRes = false;

	if (v == null || v.size() == 0) {
		return null;
	}
	
	int size = v.size();

	// sort all the HydroBase_AnnualAmt items according to irr_year (the
	// default sort order)
	java.util.Collections.sort(v);

	String id = measType.getIdentifier();
	if (id.startsWith( "S")) {
		hasSFUT = true;
	}
	else {
		hasSFUT = false;
	}

	// The output period needs to be the overlapping period of the
	// diversion records.  Do everything in terms of
	// irrigation year since that is what the diversion records use...
	int iyear = 0;
	HydroBase_AnnualAmt r = v.get(0);
	r = v.get(size - 1);

	// Size the output vector for the number of records + enough
	// for header/footer lines...
	List<String> s = new ArrayList<String>(size + 20);

	// Get the measurement type(it is assumed that all the records have the
	// same type) so that the header can be modified accordingly...
	String record_type = measType.getMeas_type();

	// Add the header...
	if (record_type.startsWith("Rel")) {
		isRes = true;
		// Reservoir records...
		if (hasSFUT) {
			s.add("                               RESERVOIR "
				+ "SUMMARY IN ACRE FEET FOR INDIVIDUAL CLASSES "
				+ "OF WATER");
		}
		else {	
			s.add("                               RESERVOIR "
				+ "SUMMARY IN ACRE FEET - TOTAL THROUGH "
				+ "STRUCTURE");
		}
	}
	else if (record_type.startsWith("Div")) {
		// Diversion records...
		isRes = false;
		if (hasSFUT) {
			s.add("                               DIVERSION "
				+ "SUMMARY IN ACRE FEET FOR INDIVIDUAL CLASSES "
				+ "OF WATER");
		}
		else {	
			s.add("                               DIVERSION "
				+ "SUMMARY IN ACRE FEET - TOTAL THROUGH "
				+ "STRUCTURE");
		}
	}
	if (hasSFUT) {
		s.add("YEAR S FROM UT  FDU   LDU  DWC  MAXQ    NOV    "
			+ "DEC    JAN    FEB    MAR    APR    MAY    JUN    "
			+ "JUL    AUG    SEP    OCT   TOTAL");
		s.add("------------------------------------------------"
			+ "---------------------------------------------------"
			+ "-----------------------------");
	}
	else {	
		s.add("YEAR  FDU   LDU  DWC  MAXQ & DAY    NOV    "
			+ "DEC    JAN    FEB    MAR    APR    MAY    JUN    "
			+ "JUL    AUG    SEP    OCT   TOTAL");
		s.add("------------------------------------------------"
			+ "---------------------------------------------------"
			+ "-------------------------");
	}

	// Now loop trough the records and format each year...	
	boolean have_diversion_record = false;
	boolean yearExists = false;
	Date dt;	
	double d;	
	double maxq_total = 0.0;	
	double[] dataTotals = new double[13];		
	HydroBase_AnnualWC rsfut = null;	
	int[] non_dupes = new int[13];
	int dwc_count = 0;
	int dwc_total = 0;
	int fdu_total = 0;
	int fdu_count = 0;
	int i;	
	int j;		
	int ldu_total = 0;
	int ldu_count = 0;
	int maxq_count = 0;
	int maxq_date_total = 0;
	String dwc_string;
	String fdu_string;
	String from = "";
	String from_string;	
	String fs;	
	String ldu_string;
	String infreq = null;
	String maxq_string;
	String maxqdate_string;
	String source = "";
	String source_string;	
	String type = "";
	String type_string;	
	String use = "";
	String use_string;	
	String[] val_string = new String[13];	
	List<Object> c = null;
	
	int[] monthOrder = new int[12];
	monthOrder[0] = 11;
	monthOrder[1] = 12;
	monthOrder[2] = 1;
	monthOrder[3] = 2;
	monthOrder[4] = 3;
	monthOrder[5] = 4;
	monthOrder[6] = 5;
	monthOrder[7] = 6;
	monthOrder[8] = 7;
	monthOrder[9] = 8;
	monthOrder[10] = 9;
	monthOrder[11] = 10;
	
	// initialize dataTotals
	for (i = 0; i < 13; i++) {
		dataTotals[i] = 0.0;
		non_dupes[i] = 0;
	}

	// New, loop through years and output diversion records ...
	for (int ii = 0; ii < v.size(); ii++) {
		iyear = v.get(ii).getIrr_year();
		// First locate the diversion record for the irrigation year...
		yearExists = false;
		have_diversion_record = true;

	if (have_diversion_record) {
		// Output the record (code not indented!)...
		// Allocate a new array to format this record...
		c = new ArrayList<Object>(22);
		if (hasSFUT) {
			rsfut = (HydroBase_AnnualWC)(v.get(ii));
			// For generic lookups...
			r = rsfut;
			source = rsfut.getS();
			from = "" + rsfut.getF();
			use = rsfut.getU();
			type = rsfut.getT();
		}
		else {	
			r = v.get(ii);
		}

		// Now start adding the records fields.  Start with the irr year ...

		// Old, got the year here...
		if (DMIUtil.isMissing(iyear)) {
			// Do not even process row...
			continue;
		}
		else {	
			c.add(new Integer(iyear));
		}

		if (!DMIUtil.isMissing(r.getQuality()) 
		    && r.getQuality().equalsIgnoreCase("I")) {
		    	infreq = "*";
			yearExists = findMatchingNonInfreqYear(v, iyear);
		}
		else {
			yearExists = false;
			infreq = " ";
		}

		c.add(infreq);

		// Add the SFUT if appropriate (retrieved the values above)...

		if (hasSFUT) {
			source_string = " ";
			if (source.length() > 0) {
				source_string =
				StringUtil.formatString(source,"%1.1s");
			}
			c.add(source_string);
			from_string = "     ";
			if (from.length() > 0) {
				from_string =
				StringUtil.formatString(from,"%-5.5s");
			}
			c.add(from_string);
			use_string = " ";
			if (use.length() > 0) {
				use_string =
				StringUtil.formatString(use,"%1.1s");
			}
			c.add(use_string);
			type_string = " ";
			if (type.length() > 0) {
				type_string =
				StringUtil.formatString(type,"%1.1s");
			}
			c.add(type_string);
		}

		// Next is the FDU...

		dt = r.getFdu();
		fdu_string = "     ";
		if (!DMIUtil.isMissing(dt)) {
			fdu_string = TimeUtil.formatTimeString(dt,"%m/%d");
			if (!yearExists) {
				fdu_total += TimeUtil.dayOfYear(dt);
				++fdu_count;
			}
		}
		c.add(fdu_string);

		// Next is LDU...

		dt = r.getLdu();
		ldu_string = "     ";
		if (!DMIUtil.isMissing(dt)) {
			ldu_string = TimeUtil.formatTimeString(dt,"%m/%d");
			if (!yearExists) {
				ldu_total += TimeUtil.dayOfYear(dt);
				++ldu_count;
			}
		}
		c.add(ldu_string);

		// Next is DWC...

		i = r.getDwc();
		dwc_string = "   ";
		if (!DMIUtil.isMissing(i)) {
			dwc_string = StringUtil.formatString(i, "%3d");
			if (!yearExists) {
				dwc_total += i;
				dwc_count++;
			}
		}
		c.add(dwc_string);

		// Next is MAXQ...

		d = r.getMaxq();
		maxq_string = "     ";
		if (!DMIUtil.isMissing(d)) {
			if (!yearExists) {
				maxq_total += d;
				++maxq_count;
			}
			if (d < 10.0) {
				maxq_string = StringUtil.formatString(
						d, "%5.2f");
			}
			else if (d < 100.0) {
				maxq_string = StringUtil.formatString(
						d, "%5.1f");
			}
			else {	
				maxq_string = StringUtil.formatString(
						d, "%5.0f");
			}
		}
		c.add(maxq_string);

		// Next is MAXQ date...

		// Do not do when SFUT because it makes the output too wide...
		if (!hasSFUT) {
			dt = r.getMaxq_date();
			maxqdate_string = "     ";
			if (!DMIUtil.isMissing(dt)) {
				maxqdate_string =
					TimeUtil.formatTimeString(dt,"%m/%d");
				if (!yearExists) {
					maxq_date_total += TimeUtil.dayOfYear(
						dt);	
				}
			}
			c.add(maxqdate_string);
		}

		// Now loop through the data values, including total...

		for (j = 0; j < 13; j++) {
			if (j < 12) {
				d = r.getAmt(monthOrder[j]);
				val_string[j] = "      ";
			}
			else {	
				d = r.getAnn_amt();
				val_string[j] = "       ";
			}

			if (!DMIUtil.isMissing(d)) {
				if (!yearExists) {
					dataTotals[j] += d;
					non_dupes[j]++;
				}
				if (j < 12) {
					if (d == 0.0) {
						val_string[j] = 
							StringUtil.formatString(
							d, "%6.0f");
					}
					else if (d < 10.0) {
						val_string[j] = 
							StringUtil.formatString(
							d, "%6.2f");
					}
					else if (d < 100.0) {
						val_string[j] = 
							StringUtil.formatString(
							d, "%6.1f");
					}
					else {	
						val_string[j] = 
							StringUtil.formatString(
							d, "%6.0f");
					}
				}
				else {
					if (d == 0.0) {
						val_string[j] = 
							StringUtil.formatString(
							d, "%7.0f");
					}
					else if (d < 10.0) {
						val_string[j] = 
							StringUtil.formatString(
							d, "%7.2f");
					}
					else if (d < 100.0) {
						val_string[j] = 
							StringUtil.formatString(
							d, "%7.1f");
					}
					else {	
						val_string[j] = 
							StringUtil.formatString(
							d, "%7.0f");
					}
				}
			}
			c.add(val_string[j]);
		}

		// Now format the string and add it to the output...

		if (hasSFUT) {
			// Have S F U T and no MAXQ Date
			fs = StringUtil.formatString(c,
				"%4d%1.1s%1.1s %5.5s%1.1s%1.1s %5.5s %5.5s "
				+ "%3.3s "
				+ "%5.5s %6.6s %6.6s "
				+ "%6.6s %6.6s %6.6s %6.6s %6.6s %6.6s %6.6s "
				+ "%6.6s %6.6s %6.6s "
				+ "%7.7s");
		}
		else {	
			fs = StringUtil.formatString(c,
				"%4d%1.1s%5.5s %5.5s %3.3s %5.5s %5.5s %6.6s "
				+ "%6.6s "
				+ "%6.6s %6.6s %6.6s "
				+ "%6.6s %6.6s %6.6s %6.6s %6.6s %6.6s %6.6s "
				+ "%7.7s");
		}
		s.add(fs);
		} // End have_diversion_record
	}

	// Now add the footer...
	if (hasSFUT) {
		s.add("--------------------------------------------"
			+ "-----------------------------------------------"
			+ "-------------------------------------");
	}
	else {	
		s.add("--------------------------------------------"
			+ "-----------------------------------------------"
			+ "---------------------------------");
	}

	if (!hasSFUT) {
		// Code not indented

		// For now, we only want to put totals for the total through
		// structure, but leave for now because we might want to allow
		// totals if we ever separate the SFUT records by class...
	c = new ArrayList<Object>(22);
	
	// fdu string
	
	fdu_string = "     ";
	if ((fdu_total > 0) && (fdu_count > 0)) {
		int ave_days = fdu_total/fdu_count;
		int month_day[] =
			TimeUtil.getMonthAndDayFromDayOfYear(1995, ave_days);
		fdu_string = StringUtil.formatString(month_day[0], "%02d") +"/"+
				StringUtil.formatString(month_day[1],"%02d");
		month_day = null;
	}
	c.add(fdu_string);
	
	// ldu string
	
	ldu_string = "     ";
	if ((ldu_total > 0) && (ldu_count > 0)) {
		int ave_days = ldu_total/ldu_count;
		int month_day[] =
			TimeUtil.getMonthAndDayFromDayOfYear(1995, ave_days);
		ldu_string = StringUtil.formatString(month_day[0],"%02d") +"/"+
				StringUtil.formatString(month_day[1],"%02d");
		month_day = null;
	}
	c.add(ldu_string);
	
	// dwc string
	
	dwc_string = "   ";
	int dwcAve = 0;
	if (!DMIUtil.isMissing(dwc_total) && (dwc_total > 0) 
	    && (dwc_count > 0)){
		dwcAve = dwc_total / dwc_count;
		dwc_string = StringUtil.formatString(dwcAve, "%3d");
	}
	c.add(dwc_string);
	
	// maxq string
	
	maxq_string = "     ";
	if ((maxq_total > 0.0) && (maxq_count > 0)) {
		double maxq_ave = maxq_total/(double)maxq_count;
		if (maxq_ave < 10.0) {
			maxq_string = StringUtil.formatString(maxq_ave,
			"%5.2f");
		}
		else if (maxq_ave < 100.0) {
			maxq_string = StringUtil.formatString(maxq_ave,
			"%5.1f");
		}
		else {	
			maxq_string = StringUtil.formatString(maxq_ave,
			"%5.0f");
		}
	}
	c.add(maxq_string);
	
	// maxq date string
	
	maxqdate_string = "     ";
	if ((maxq_total > 0) && (maxq_count > 0)) {
		int ave_days = maxq_date_total/maxq_count;
		int month_day[] = TimeUtil.getMonthAndDayFromDayOfYear(
					1995, ave_days);
		maxqdate_string = StringUtil.formatString(month_day[0],"%02d") +
					"/" +
				StringUtil.formatString(month_day[1],"%02d");
		month_day = null;
	}
	c.add(maxqdate_string);
	
	// Loop through and average the values...
	for (j = 0; j < 13; j++) {
		if (non_dupes[j] > 0) {
			dataTotals[j] /= (double)non_dupes[j];
		}
	}
	
	for (j = 0; j < 13; j++) {
		if (	!DMIUtil.isMissing(dataTotals[j]) &&
			(dataTotals[j] > 0.0)) {
			if (j < 12) {	// monthly values columns
				if (dataTotals[j] < 10.0) {
					val_string[j] = StringUtil.formatString(
							dataTotals[j],
							"%6.2f");
				}
				else if (dataTotals[j] < 100.0) {
					val_string[j] = StringUtil.formatString(
							dataTotals[j],
							"%6.1f");
				}
				else {	
					val_string[j] = StringUtil.formatString(
							dataTotals[j],
							"%6.0f");
				}
			}
			else {	// total column
				if (dataTotals[j] < 10.0) {
					val_string[j] = StringUtil.formatString(
							dataTotals[j],
							"%7.2f");
				}
				else if (dataTotals[j] < 100.0) {
					val_string[j] = StringUtil.formatString(
							dataTotals[j],
							"%7.1f");
				}
				else {	
					val_string[j] = StringUtil.formatString(
							dataTotals[j],
							"%7.0f");
				}
			}
		}
		c.add(val_string[j]);
	}
	
	fs = StringUtil.formatString(c,
		"AVE: %5.5s %5.5s %3.3s %5.5s %5.5s %6.6s %6.6s %6.6s %6.6s "
		+ "%6.6s %6.6s %6.6s %6.6s "
		+ "%6.6s %6.6s %6.6s %6.6s %7.7s");
	s.add(fs);
	
	String plural = "s";
	String pad = "";
	if (size == 1) {	
		plural = "";
		pad = " ";
	}

	String div = "diversion";
	String verb = "diverted";
	String pad2 = "        ";
	if (isRes) {
		div = "reservoir release";
		verb = "released";
		pad2 = "";
	}
	
	if ((dwc_total > 0) && (dataTotals[12] > 0.0)) {
		// Print the average flow during the period...
		s.add(StringUtil.formatString(size,"%3d") +
			" year" + plural 
			+ " with " + div + " records                       "
			+ "                                    " + pad2 + pad
			+ "Average Flow = " +
			// Convert the average ACFT/day to ave flow...
			StringUtil.formatString(
			(dataTotals[12])/
			(double)dwcAve/1.9835, "%6.2f") + " CFS" );
	}
	else {	
		s.add(StringUtil.formatString(size,"%3d") +
		" years with records");
	}

	// Print a message about the totals...
	s.add("");
	s.add("Notes:  The average considers all years with " + div
		+ " records, even if no water is " + verb + ".");
	s.add("        The above summary lists total monthly " + div + "s.");
	s.add("        * = Infrequent data.  All other values are derived "
		+ "from daily records.");
	s.add("        Average values include infrequent data if infrequent "
		+ "data are the only data for the year.");

	} // if (!hasSFUT)
	else {	
		// SFUT is not supported in this report yet...
		s.add("This report type is supported only for " +
		"total through structure.");
	}
	
	// Return the output...
	return s;
}

/**
Returns the generated report.
@return the generated report.
*/
public List<String> getReport() {
	return __reportList;
}

/**
Constructs and submits the structural annual amount record query.
@param TYPE _DIVERSION if querying for diversion records,
_RESERVOIR if querying for reservoir records
@param String type __DIV_TOTAL or __REL_TOTAL.
@exception Exception if an error occurs
*/
private void submitAnnualAmtQuery(final int TYPE, String measTypeString)
throws Exception {	
	String noRecords = "";
	
        if (TYPE == __DIVERSION) {
                noRecords ="No annual amount records to display for diversions";
        }
	else {	
		noRecords = "";
        }

        List<HydroBase_StructMeasTypeView> v = null;
	if (__view != null) {
		v = __dmi.readStructMeasTypeListForStructure_numMeas_type(
			__view.getStructure_num(), measTypeString);

		if (measTypeString.equals(__DIV_TOTAL)) {
			List<HydroBase_StructMeasTypeView> v2 = __dmi
			       .readStructMeasTypeListForStructure_numMeas_type(
				__view.getStructure_num(), "IDivTotal");
			for (int i = 0; i < v2.size(); i++) {
				v.add(v2.get(i));
			}
		}
		else if (measTypeString.equals(__REL_TOTAL)) {
			List<HydroBase_StructMeasTypeView> v2 = __dmi
			       .readStructMeasTypeListForStructure_numMeas_type(
				__view.getStructure_num(), "IRelTotal");
			for (int i = 0; i < v2.size(); i++) {
				v.add(v2.get(i));
			}
		}
	}

	List<HydroBase_AnnualAmt> results = new ArrayList<HydroBase_AnnualAmt>();

	HydroBase_StructMeasTypeView smtv = null;
	for (int i = 0; i < v.size(); i++) {
		smtv = v.get(i);
		List<HydroBase_AnnualAmt> v2 = __dmi.readAnnualAmtList(smtv.getMeas_num(),null, null);
		for (int j = 0; j < v2.size(); j++) {
			results.add(v2.get(j));
		}
	}

	List<HydroBase_DiversionComment> comments = null;

	if (__view != null) {
		comments = __dmi.readDiversionCommentListForStructure_num(
			__view.getStructure_num());	
	}

	if (results.size() == 0) {
		if (comments == null || comments.size() == 0) {
			__reportList.add(noRecords);
		}
		else {
			appendComments(comments, TYPE, measTypeString);
		}
        }
	else {
		appendAnnualAmtResults(results, comments, TYPE, measTypeString);
	}
}

/**
Constructs and submits the equipment query.
@exception Exception if an error occurs
*/
private void submitEquipmentQuery()
throws Exception {	
        String noRecords = "MEASURING DEVICE/RECORDER: ";

        // results Vector contains the reservoir query results
        HydroBase_Equipment e = null;
	if (__view != null) {
		e = __dmi.readEquipmentForStructure_num(
			__view.getStructure_num());
	}
 
        if ( e != null) { 
		appendEquipmentResults(e);            
        }
	else {	
		__reportList.add(noRecords);
        }

}

/**
Constructs and submits the irrigated acres query.
@exception Exception if an error occurs
*/
private void submitIrrigatedAcresQuery()
throws Exception {	
    if (!__dmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_19990202)) {
		throw new Exception("Irrigation summary not available in database.");
	}

    // TODO SAM 2010-07-15 The order by is not used by the stored procedure so
    // sort the results during formatting.
    List<String> order = new ArrayList<String>();
	order.add("irrig_summary_ts.cal_year");
	order.add("irrig_summary_ts.land_use");
	List<HydroBase_StructureView> results = null;
	
	if (__view != null) {
		results = __dmi.readStructureIrrigSummaryTSList(
			null,		// panel
			order,
			__view.getStructure_num(), 
			DMIUtil.MISSING_INT,	// wd
			DMIUtil.MISSING_INT,	// id
			null,		// structure name
			null,		// land use
			null,		// date 1
			null,		// date 2
			false);		// distinct?
	}
		
    if (results == null || (results.size() == 0)) {
		__reportList.add("                        IRRIGATED "
			+ "ACRES FROM "
			+ "GIS DATA -- BY CROP, YEAR, AND IRRIGATION METHOD");
		__reportList.add("No GIS irrigated acres records to display");
    }
	else {	
		appendIrrigatedAcresResults(results);            
    }
}

/**
Constructs and submits the irrigated acres summary query.
@exception Exception if an error occurs
*/
private void submitIrrigatedAcresSummaryQuery()
throws Exception {		
	String noRecords = "No irrigated acre summary records to display";

	List<HydroBase_StructureView> results = null;
	
	if (__view != null) {
		results = __dmi.readStructureIrrigSummaryListForStructure_num(
			__view.getStructure_num());
	}
	if (results == null || (results.size() == 0)) {
		__reportList.add(noRecords);
	}
	else {	
		appendIrrigatedAcresSummaryResults(results);
        }
}

/**
Constructs and submits the Rolodex query.
@exception Exception if an error occurs
*/
private void submitRolodexQuery()
throws Exception {	
        String noRecords = "No rolodex records to display";

        // results Vector contains the reservoir query results
	if (__rolodexNum.equals("NONE")) {
		__reportList.add(noRecords);
	}
	else {
		Integer I = new Integer(__rolodexNum);
	        HydroBase_Rolodex r = __dmi.readRolodexForRolodex_num(
			I.intValue());
			
	        if (r != null) { 
			appendRolodexResults(r);            
	        }
		else {	
			__reportList.add(noRecords);
	        }
	}
}

/**
Constructs and submits the structure location query.
@exception Exception if an error occurs
*/
private void submitStructureGeolocQuery() 
throws Exception {
	String noRecords = "No structure location records to display";

	if (__structureView != null) {
		appendStructureViewResults(__structureView);
	}
	else {	
		__reportList.add(noRecords);
        }
}

/**
Constructs and submits the Water Right Net query.
@exception Exception if an error occurs
*/
private void submitWaterRightNetQuery()
throws Exception {	
        String noRecords = "No water right net amounts records to display";

        List<HydroBase_NetAmts> results = null;
	if (__view != null) {
	Message.printStatus(2, "", "-----------");
		results = __dmi.readNetAmtsList(
			__view.getStructure_num(), DMIUtil.MISSING_INT, 
			DMIUtil.MISSING_INT, false, "72");
	Message.printStatus(2, "", "-----------");
	}
 
	if (results == null || (results.size() == 0)) {
		__reportList.add(noRecords);
        }
	else {	
		appendWaterRightNetResults(results);    
        }
}

/**
Constructs and submits the Water Right transaction query.
@exception Exception if an error occurs
*/
private void submitWaterRightTransactionQuery()
throws Exception {	
        String noRecords = "No water right transaction records to display";

        List<HydroBase_Transact> results = null;
	if (__view != null) {
		results = __dmi.readTransactListForStructure_num(
			__view.getStructure_num());	
	}
 
	if (results == null || (results.size() == 0)) {
		__reportList.add(noRecords);
        }
	else {	
		appendWaterRightTransactionResults(results);    
        }
}

/**
Determines the crops total to print for an irrig summary ts line.  Add up the total for
all crops in the year.
@param results the list of results being displayed.
@param year the total number of acres for the year.
@return the total value to be printed.
*/
private String determineIrrigLineTotal( List<HydroBase_StructureView> results, int year )
{
	double total = 0.0;
	for ( HydroBase_StructureView v: results ) {
		if ( (v.getCal_year() == year) && (v.getAcres_total() > 0.0) ) {
			total += v.getAcres_total();
		}
	}
	return StringUtil.formatString(total, "%10.3f");
}

}