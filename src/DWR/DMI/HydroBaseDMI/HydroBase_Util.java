//------------------------------------------------------------------------------
// HydroBase_Util - static utility methods for HydroBase
//------------------------------------------------------------------------------
// History:
//
// 2004-02-18	Steven A. Malers, RTi	Initial version.
// 2004-02-20	SAM, RTi		* Add isAgriculturalCASSCropStatsTime
//					  SeriesDataType().
//					* Add getTimeSeriesTimeSteps() and
//					  supporting data.
// 					* Add isAgriculturalNASSCropStatsTime
//					  SeriesDataType().
// 					* Add
//					  isStructureSFUTTimeSeriesDataType().
// 2004-03-25	SAM, RTi		* Add Month to EvapPan intervals - was
//					  left out as an oversight.
// 2004-05-19	SAM, RTi		* Add isWISTimeSeriesDataType().
//					* Add WIS data types to
//					  getTimeSeriesTimeSteps().
// 2004-06-01	SAM, RTi		* Change the getTimeSeriesDataTypes()
//					  parameter "add_notes" to "add_group"
//					  since it is a prefix to the data type.
//					  This allows the StateMod code to be
//					  updated similarly.
// 2004-06-22	JTS, RTi		After a review of HydroBaseDMI,
//					moved methods in here from that class.
// 2004-08-27	SAM, RTi		* Add readTimeSeriesHeaderObjects() to
//					  facilitate listing time series for
//					  TSTool.  This might be a better
//					  candidate for the HydroBase_GUI_Util
//					  class but it is of a relatively
//					  low-level nature.
//					* Add __preferred_WDID_length and
//					  set/get methods - this is used by
//					  TSTool and other software when
//					  formatting WDIDs for display.
//					  Although the default will normally
//					  be OK, some software may want to
//					  use other than the default.
// 2005-02-16	JTS, RTi		Converted queries to use stored 
//					procedures
// 2005-02-23	JTS, RTi		* Moved in lookupDiversion*() methods
//					  from HydroBaseDMI.
//					* Moved in parseSFUT() method from
//					  from HydroBaseDMI.
// 2005-06-10	SAM, RTi		If there is an error getting diversion
//					comments, print the exception at level
//					3, to figure out why this occurs.
// 2005-07-12	SAM, RTi		Add IDivTotal monthly and yearly time
//					series data types.
// 2005-12-01	SAM, RTi		For daily temperature, return blank
//					units because the units are set in the
//					data records.
// 2006-01-16	SAM, RTi		Overload readTimeSeriesHeaderObjects
//					to take a GRLimits for spatial queries.
// 2006-04-24	SAM, RTi		Add lookupHydroBaseDMI() to find a DMI
//					instance given an InputName.
// 2006-04-25	SAM, RTi		Add fillTSIrrigationYearCarryForward()
//					to mimic the process used when
//					converting daily diversion records to
//					monthly records.
// 2006-04-27	SAM, RTi		Enable RelTotal and RelClass filling
//					with diversion comments.
// 2006-05-25	JTS, RTi		Added LOCAL.
// 2006-06-26	SAM, RTi		When processing diversion comments, more
//					gracefully handle the case where no
//					time series are available in HydroBase.
// 2006-10-31	SAM, RTi		Add CASS livestock time series.
//					Add CUPopulation HumanPopulation time
//					series (Demographics group).
// 2007-02-06	Kurt Tometich, RTi		Moved the findNearestDataPoint
//								method to TSUtil in RTiCommon.
// 2007-02-26	SAM, RTi		Update parseSFUT() to handle G:
//					Clean up code based on Eclipse feedback.
//					Reorder some code to be alphabetical.
// 2007-04-29	SAM, RTi		Add AdminFlow data type for time series.
//------------------------------------------------------------------------------
// EndHeader

package DWR.DMI.HydroBaseDMI;

import java.io.File;

import java.util.List;
import java.util.Vector;

import RTi.GR.GRLimits;

import RTi.DMI.DMIUtil;

import RTi.TS.DayTS;
import RTi.TS.MonthTS;
import RTi.TS.TS;
import RTi.TS.TSData;
import RTi.TS.TSDataFlagMetadata;
import RTi.TS.YearTS;

import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.IO.IOUtil;
import RTi.Util.IO.PropList;

import RTi.Util.Math.MathUtil;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.TimeInterval;

/**
The HydroBase_Util class contains utility methods for manipulating HydroBase data objects.
*/
public class HydroBase_Util
{

/**
Data types for getTimeSeriesDataTypes().  For example, this is used in StateView
to indicate that only station data types are being requested for the station
view.  In this case, real-time well levels should be available, but not
historical well levels (because historic are associated with WDIDs).
*/
public final static int DATA_TYPE_AGRICULTURE = 0x1;
public final static int DATA_TYPE_HARDWARE = 0x2;
public final static int DATA_TYPE_STATION_CLIMATE = 0x10;
public final static int DATA_TYPE_STATION_STREAM = 0x20;
public final static int DATA_TYPE_STATION_RESERVOIR = 0x40;
public final static int DATA_TYPE_STATION_WELL = 0x80;
public final static int DATA_TYPE_STATION_ALL = DATA_TYPE_STATION_CLIMATE |
						DATA_TYPE_STATION_STREAM |
						DATA_TYPE_STATION_RESERVOIR |
						DATA_TYPE_STATION_WELL;
public final static int DATA_TYPE_STRUCTURE_DIVERSION = 0x1000;
public final static int DATA_TYPE_STRUCTURE_RESERVOIR = 0x2000;
public final static int DATA_TYPE_STRUCTURE_WELL = 0x4000;
public final static int DATA_TYPE_STRUCTURE_ALL = DATA_TYPE_STRUCTURE_DIVERSION|
						DATA_TYPE_STRUCTURE_RESERVOIR |
						DATA_TYPE_STRUCTURE_WELL;
public final static int DATA_TYPE_WIS = 0x100000;

public final static int DATA_TYPE_DEMOGRAPHICS_POPULATION = 0x1000000;
public final static int DATA_TYPE_DEMOGRAPHICS_ALL = DATA_TYPE_DEMOGRAPHICS_POPULATION;

public final static int DATA_TYPE_ALL = DATA_TYPE_AGRICULTURE |
					DATA_TYPE_HARDWARE |
					DATA_TYPE_STATION_ALL |
					DATA_TYPE_STRUCTURE_ALL |
					DATA_TYPE_WIS |
					DATA_TYPE_DEMOGRAPHICS_ALL;

/**
The preferred length for WDID strings.  For example, TSTool uses this to know how to format WDIDs
after interactively querying from HydroBase.
*/
private static int __preferred_WDID_length = 7;

public final static String LOCAL = "local";

/**
For a list of HydroBase_StructureGeolocStructMeasType, use the WD, ID in each
data object to query the unpermitted_wells table and then set the common_id in
the objects to the USGS or USBR identifier from unpermitted_wells.  This is a
work-around until HydroBase is redesigned to better handle well data.
*/
public final static void addAlternateWellIdentifiers ( HydroBaseDMI dmi, List list )
{	HydroBase_StructureGeolocStructMeasType data;
	int size = 0;
	if ( list != null ) {
		size = list.size();
	}
	
	HydroBase_GroundWaterWellsView uws;
	for ( int i = 0; i < size; i++ ) {
		data = (HydroBase_StructureGeolocStructMeasType)list.get(i);
		try {	
			uws = dmi.readGroundWaterWellsMeasType(data.getStructure_num());
			if ( uws == null ) {
				continue;
			}
			if ( uws.getUsgs_id().length() > 0 ) {
				data.setCommon_id ( uws.getUsgs_id() );
			}
			else if ( uws.getUsbr_id().length() > 0 ) {
				data.setCommon_id ( uws.getUsbr_id() );
			}
		}
		catch ( Exception e ) {
			// ignore...
		}
	}	
}

/**
Add data flag descriptions to daily structure time series.  These will then be available for annotation of
data flags.
@param ts time series to update.
*/
public static void addDailyTSStructureDataFlagDescriptions ( TS ts )
{
    ts.addDataFlagMetadata(new TSDataFlagMetadata("*", "Measured/observed."));
    ts.addDataFlagMetadata(new TSDataFlagMetadata("U", "User-supplied."));
    // TODO SAM 2010-08-16 What about E and other values?
}

/**
Adjust the struct_meas_type SFUT identifier for the database version as follows.
If the database is at least the 20061003, then the new G: is included in the identifier
in the database, and the F: if specified is 7-digits padded to zeros.  Requests using
the old SFUT with no G: are updated as follows.
<ol>
<li>	" G:" is added to the end of the identifier.</li>
<li>	The F: (if specified as non-blank) is updated to a 7-digit value padded with zeros.</li>
</ol>
If the database is earlier than 20061003, then the new G: is NOT included in the identifier
in the database, and the F: if specified as the structure number in the district of the
structure.  Requests using the new SFUT(G) are updated as follows.
<ol>
<li>	" G:" is removed from the end of the identifier.  An attempt to strip a non-blank
		value will result in an exception.</li>
<li>	The F: (if specified as non-blank) is updated to only the structure identifier.
		An attempt to strip a WD that is different from the wd passed to this method
		will result in an exception.</li>
</ol>
@param dmi HydroBaseDMI instance that is being used for data requests.
@param wd Water district identifier, needed to adjust the F: to/from 7-digits.
@param id Structure identifier.
@param identifier The SFUT(G) identifier to adjust.
@returns An SFUT(G) identifier that can be used to query data in the database version that is being used.
*/
public static String adjustSFUTForHydroBaseVersion ( HydroBaseDMI dmi, int wd, int id, String identifier )
throws Exception
{	if ( (identifier == null) || (identifier.length() == 0) ) {
		return identifier;
	}
	String identifierNew = identifier;
	boolean needToAdjust = false;	// true if need to adjust SFUT string
	String G = "";
	String F = "";
	String[] SFUT_parts = HydroBase_Util.parseSFUT( identifier );
	if ( dmi.isVersionAtLeast(HydroBaseDMI.VERSION_20061003) ) {
		// Version that requires G: and 7-digit F: in SFUT.
		if ( identifier.indexOf("G:") < 0 ){
			// Requested identifier does not have G: so add it as blank...
			needToAdjust = true;
		}
		if ( SFUT_parts[1].length() > 0 ) {
			// Have a non-blank F: so make sure it is 7-digits
			F = SFUT_parts[1];	// Default is use all 7
			if ( F.length() < 7 ) {
				// Reformat to be 7 characters.  Assume that the WDID for the F: is
				// the same as that passed in to this method.  Make sure that the overall length is 7.
				needToAdjust = true;
				F = HydroBase_WaterDistrict.formWDID(7,""+wd,F);
			}
		}
		if ( needToAdjust ) {
			// TODO SAM 2007-02-26 Might need an SFUT object to handle its own parse/toString().
			// Reformat the identifier to include G: and 7-digit FROM...
			identifierNew = "S:" + SFUT_parts[0] +
							" F:" + F +
							" U:" + SFUT_parts[2] +
							" T:" + SFUT_parts[3] +
							" G:" + G;
		}
	}
	else {
		// HydroBase version that DOES NOT include G: and 7-digit F: in SFUT.
		// Adjust the data back to the old format, if possible.
		if ( SFUT_parts.length > 4 ) {
			needToAdjust = true;	// Simple cut - omit G below
			// Have a G: OK to cut off if blank but problems otherwise.
			if ( SFUT_parts[4].length() > 0 ) {
				throw new Exception ( "New SFUT with non-blank G: (" + identifier +
						") cannot be used with old database.");
			}
		}
		F = SFUT_parts[1];	// Default is to use F as is
		if ( F.length() >= 7 ) {
			// Reformat to just be the ID.  In this case, the WD part must agree
			// with the WD passed in to this method.
			needToAdjust = true;
			int [] wdid_parts = HydroBase_WaterDistrict.parseWDID( F );
			if ( wdid_parts[0] != wd ) {
				throw new Exception ( "Long F: WDID (" + SFUT_parts[1] +
						") has different WD than structure.  Request is not compatible with older HydroBase." );
			}
			else {
			    F = "" + wdid_parts[1];
				needToAdjust = true;
			}
		}
		if ( needToAdjust ) {
			// TODO SAM 2007-02-26 Might need an SFUT object to handle its own parse/toString().
			// Reformat the identifier to exclude G: and not use 7-digit FROM...
			identifierNew = "S:" + SFUT_parts[0] +
							" F:" + F +
							" U:" + SFUT_parts[2] +
							" T:" + SFUT_parts[3];
		}
	}
	if ( needToAdjust ) {
	    Message.printStatus(2, "", "Changed SFUT for HydroBase version, original SFUT=\"" + identifier +
	    "\" Adjusted SFUT=\"" + identifierNew + "\"" );
	}
	return identifierNew;
}

/**
Builds a location string, suitable for display in a table, given the passed-in parameters.
TODO (JTS - 2006-05-25) Combine with the other buildLocation() method, overload one, because there's 
a lot of duplicate code.
*/
public final static String buildLocation(String pm, int ts, String tdir,
int rng, String rdir, int sec, String seca, String q160, String q40, String q10)
{
	if (DMIUtil.isMissing(pm)) {
		pm = " ";
	}

	String tsS = "  ";
	if (!DMIUtil.isMissing(ts)) {
		tsS = StringUtil.formatString(ts, "%2d");
	}

	if (DMIUtil.isMissing(tdir)) {
		tdir = " ";
	}

	String rngS = "   ";
	if (!DMIUtil.isMissing(rng)) {
		rngS = StringUtil.formatString(rng, "%3d");
	}

	if (DMIUtil.isMissing(rdir)) {
		rdir = " ";
	}

	String secS = "  ";
	if (!DMIUtil.isMissing(sec)) {
		secS = StringUtil.formatString(sec, "%2d");
	}

	if (DMIUtil.isMissing(seca)) {
		seca = " ";
	}

	if (DMIUtil.isMissing(q160)) {
		q160 = "";
	}

	q160 = StringUtil.formatString(q160, "%2.2s");

	if (DMIUtil.isMissing(q40)) {
		q40 = "";
	}

	q40 = StringUtil.formatString(q40, "%2.2s");

	if (DMIUtil.isMissing(q10)) {
		q10 = "";
	}

	q10 = StringUtil.formatString(q10, "%2.2s");

	return pm + " " + tsS + tdir + " " + rngS + rdir + " " + secS + seca + " " + q160 + " " + q40 + " " + q10;
}

/**
Builds a location string, suitable for display in a table, given the passed-in parameters.
TODO (JTS - 2006-05-25) Combine with the other buildLocation() method, overload one, because there's 
a lot of duplicate code.
*/
public final static String buildLocation(String pm, String ts, String tdir,
String rng, String rdir, int sec, String seca, String q160, String q40, String q10)
{
	if (DMIUtil.isMissing(pm)) {
		pm = " ";
	}

	String tsS = " ";
	if (!DMIUtil.isMissing(ts)) {
		tsS = StringUtil.formatString(ts, "%4.4s");
	}

	if (DMIUtil.isMissing(tdir)) {
		tdir = " ";
	}

	String rngS = "   ";
	if (!DMIUtil.isMissing(rng)) {
		rngS = StringUtil.formatString(rng, "%3d");
	}

	if (DMIUtil.isMissing(rdir)) {
		rdir = " ";
	}

	String secS = "  ";
	if (!DMIUtil.isMissing(sec)) {
		secS = StringUtil.formatString(sec, "%2d");
	}

	if (DMIUtil.isMissing(seca)) {
		seca = " ";
	}

	if (DMIUtil.isMissing(q160)) {
		q160 = "";
	}

	q160 = StringUtil.formatString(q160, "%2.2s");

	if (DMIUtil.isMissing(q40)) {
		q40 = "";
	}

	q40 = StringUtil.formatString(q40, "%2.2s");

	if (DMIUtil.isMissing(q10)) {
		q10 = "";
	}

	q10 = StringUtil.formatString(q10, "%2.2s");

	return pm + " " + tsS + tdir + " " + rngS + rdir + " " + secS + seca + " " + q160 + " " + q40 + " " + q10;
}

/**
Builds a location string, suitable for display in a table, given the passed-in parameters.
TODO (JTS - 2006-05-25) Combine with the other buildLocation() method, overload one, because there's 
a lot of duplicate code.
*/
public final static String buildLocation(String pm, String ts,
String rng, int sec, String seca, String q160, String q40,
String q10) {
	if (DMIUtil.isMissing(pm)) {
		pm = " ";
	}

	ts = StringUtil.formatString(ts, "%4.4s");

	rng = StringUtil.formatString(rng, "%5.5s");

	String secS = "  ";
	if (!DMIUtil.isMissing(sec)) {
		secS = StringUtil.formatString(sec, "%2d");
	}

	if (DMIUtil.isMissing(seca)) {
		seca = " ";
	}

	if (DMIUtil.isMissing(q160)) {
		q160 = "";
	}

	q160 = StringUtil.formatString(q160, "%2.2s");

	if (DMIUtil.isMissing(q40)) {
		q40 = "";
	}

	q40 = StringUtil.formatString(q40, "%2.2s");

	if (DMIUtil.isMissing(q10)) {
		q10 = "";
	}

	q10 = StringUtil.formatString(q10, "%2.2s");

	return pm + " " + ts + " " + rng + " " + secS + seca + " " + q160 + " " + q40 + " " + q10;
}

/**
Convert a HydroBase timestep string to time series notation.
"Annual" and "Annually" are converted to "Year", "Daily" to "Day", "Monthly" to "Month".
@param hbTimestep HydroBase timestep used with Meastype.
@return time series interval or null if not found
*/
public final static String convertFromHydroBaseTimeStep ( String hbTimestep )
{
    if ( hbTimestep.equalsIgnoreCase("Annual") || hbTimestep.equalsIgnoreCase("Annually") ||
        hbTimestep.equalsIgnoreCase("Yearly") || hbTimestep.equalsIgnoreCase("Year") ) {
        return "Year";
    }
    else if ( hbTimestep.equalsIgnoreCase("Monthly") || hbTimestep.equalsIgnoreCase("Month")) {
        return "Month";
    }
    else if ( hbTimestep.equalsIgnoreCase("Daily") || (hbTimestep.equalsIgnoreCase("Day"))) {
        return "Day";
    }
    // TODO SAM 2012-05-04 Not sure what to do about "Random" since not always daily?
    else {
        return null;
    }
}

/**
Convert a data type and data interval from general time series notation to HydroBase meas_type, struct_meas_type
table convention.  For example, "StreamflowMax", "Month" will be converted to "Streamflow", "Monthly".
Specific actions are taken for station and structure data and other
data types (AgStats, irrig_ts_summary, and WIS) pass through the GUI values.
Some data types have the same output value as the input because HydroBase does not internally use the string for
queries (table design is hard-coded for data type).
@param dataType a data type from an application such as TSTool (e.g., "StreamflowMax"),
corresponding to an entry in the HydroBase meas_type or struct_meas_type table.
@param interval a generic time series notation interval (e.g., "Day", "Month").
@return The HydroBase equivalent meas_type data, as an array.  The [0] position
will contain the data type.  The [1] position will contain a sub data type,
if appropriate, or an empty string.  The [2] position will contain the data interval.  It is possible that
the HydroBase meas_type will correspond to multiple data_type because of the HydroBase design storing more than
one value per record.
*/
public final static String [] convertToHydroBaseMeasType ( String dataType, String interval )
{	String [] hb = new String[3];
	hb[0] = "";
	hb[1] = "";
	hb[2] = "";
	// General conversion on interval...
	if ( interval.equalsIgnoreCase("Day") ) {
		hb[2] = "Daily";
	}
	else if ( interval.equalsIgnoreCase("Month") ) {
		hb[2] = "Monthly";
	}
	else if ( interval.equalsIgnoreCase("Year") ) {
		hb[2] = "Annual";
	}
	else if ( interval.equalsIgnoreCase("Irregular") ) {
		// This may need to be reset below...
		hb[2] = "Real-time";
	}
	else if ( interval.equalsIgnoreCase("RealTime") ) {
		hb[2] = "Real-time";
	}

	// Conversions based on the data type...

	String sub_data_type = "";
	if ( dataType.indexOf("-") > 0 ) {
		sub_data_type = StringUtil.getToken ( dataType, "-", 0, 1 );
		dataType = StringUtil.getToken ( dataType, "-", 0, 0 );
	}

	// Order by data types and group when multiple time series are in the same table...

	if(dataType.equalsIgnoreCase("AdminFlow") || dataType.equalsIgnoreCase("AdminFlowMax") ||
		dataType.equalsIgnoreCase("AdminFlowMin") ) {
		// All these match the AdminFlow meas_type...
		hb[0] = "AdminFlow";
		if ( interval.equalsIgnoreCase("RealTime") || interval.equalsIgnoreCase("Irregular") ) {
			hb[0] = "RT_rate";
			if ( sub_data_type.length() > 0 ) {
				// Assume that the vax_field is accurate...
				hb[1] = sub_data_type;
			}
			else {
			    hb[1] = "DISCHRG";	// Assume this
			}
			hb[2] = "Real-time";
		}
	}
	else if ( dataType.equalsIgnoreCase("Battery") ) {
		hb[0] = "RT_Misc";
		hb[1] = "BATTERY";
		hb[2] = "Real-time";
	}
    else if ( dataType.equalsIgnoreCase("CropArea") ) {
        // Pass through - used with NASS crop statistics - specific handling in database
        hb[0] = dataType;
        hb[1] = "";
        hb[2] = "Annual";
    }
    else if ( dataType.equalsIgnoreCase("CropAreaAllIrrigation") ||
        dataType.equalsIgnoreCase("CropAreaDrip") ||
        dataType.equalsIgnoreCase("CropAreaFlood") ||
        dataType.equalsIgnoreCase("CropAreaFurrow") ||
        dataType.equalsIgnoreCase("CropAreaSprinkler") ) {
        // Pass through - used with irrigation summary time series - specific handling in database
        hb[0] = dataType;
        hb[1] = "";
        hb[2] = "Annual";
    }
    else if ( dataType.equalsIgnoreCase("CropAreaHarvested") ||
        dataType.equalsIgnoreCase("CropAreaPlanted")) {
        // Pass trough - used with CASS statistics - specific handling in database
        hb[0] = dataType;
        hb[1] = "";
        hb[2] = "Annual";
    }
	else if ( dataType.equalsIgnoreCase("DivClass") ) {
		hb[0] = "DivClass";
		if ( sub_data_type.length() > 0 ) {
			// Assume that the SFUT is accurate...
			hb[1] = sub_data_type;
		}
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly diversions annual...
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual ammount...
			hb[2] = "Annual";
		}
	}
	else if ( dataType.equalsIgnoreCase("DivComment") ) {
		hb[0] = "DivComment";
		hb[2] = "Annual";
	}
	else if ( dataType.equalsIgnoreCase("DivTotal") ) {
		hb[0] = "DivTotal";
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly diversions annual...
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount...
			hb[2] = "Annual";
		}
	}
	else if ( dataType.equalsIgnoreCase("EvapPan") ) {
		hb[0] = "Evap";
		// Interval converted above.
	}
	else if( dataType.equalsIgnoreCase("FrostDateL28S") || dataType.equalsIgnoreCase("FrostDateL32S") ||
		dataType.equalsIgnoreCase("FrostDateF32F") || dataType.equalsIgnoreCase("FrostDateF28F") ) {
		hb[0] = "FrostDate";
		hb[2] = "Annual";
	}
    else if( dataType.equalsIgnoreCase("HumanPopulation") ) {
       // Pass through - special table in HydroBase so just return what TSTool uses
       hb[0] = dataType;
       hb[2] = "Annual";
    }
	else if ( dataType.equalsIgnoreCase("IDivClass") ) {
		hb[0] = "IDivClass";
		if ( sub_data_type.length() > 0 ) {
			// Assume that the SFUT is accurate...
			hb[1] = sub_data_type;
		}
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly diversions annual...
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount...
			hb[2] = "Annual";
		}
	}
	else if ( dataType.equalsIgnoreCase("IDivTotal") ) {
		hb[0] = "IDivTotal";
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly diversions annual...
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount...
			hb[2] = "Annual";
		}
	}
	else if ( dataType.equalsIgnoreCase("IRelClass") ) {
		hb[0] = "IRelClass";
		if ( sub_data_type.length() > 0 ) {
			// Assume that the SFUT is accurate...
			hb[1] = sub_data_type;
		}
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly releases annual...
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount...
			hb[2] = "Annual";
		}
	}
	else if ( dataType.equalsIgnoreCase("IRelTotal") ) {
		hb[0] = "IRelTotal";
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly releases annual...
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount...
			hb[2] = "Annual";
		}
	}
    else if ( dataType.equalsIgnoreCase("LivestockHead") ) {
        // Used with CASS livestock statistics - specific handling in database
        hb[0] = dataType;
        hb[1] = "";
        hb[2] = "Annual";
    }
	else if ( dataType.equalsIgnoreCase("Release") ) {
		hb[0] = "RT_Misc";
		hb[1] = "OUTLETQ";
		hb[2] = "Real-time";
	}
	else if ( dataType.equalsIgnoreCase("NaturalFlow") ) {
		hb[0] = "Nat_flow";
		// Interval converted above.
	}
	else if ( dataType.equalsIgnoreCase("PoolElev") ) {
		hb[0] = "RT_Height";
		hb[1] = "ELEV";
		hb[2] = "Real-time";
	}
	else if ( dataType.equalsIgnoreCase("Precip") ) {
		hb[0] = "Precip";
		// Interval converted above.
		if ( interval.equalsIgnoreCase("RealTime") || interval.equalsIgnoreCase("Irregular") ) {
			hb[0] = "RT_Misc";
			hb[1] = "PRECIP";
			// Interval converted above.
			hb[2] = "Real-time";
		}
	}
	else if ( dataType.equalsIgnoreCase("RelClass") ) {
		hb[0] = "RelClass";
		if ( sub_data_type.length() > 0 ) {
			// Assume that the SFUT is accurate...
			hb[1] = sub_data_type;
		}
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly releases annual...
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount...
			hb[2] = "Annual";
		}
	}
	else if ( dataType.equalsIgnoreCase("RelComment") ) {
		hb[0] = "RelComment";
		hb[2] = "Annual";
	}
	else if ( dataType.equalsIgnoreCase("Release") ) {
		hb[0] = "RT_Misc";
		hb[1] = "OUTLETQ";
		hb[2] = "Real-time";
	}
	else if ( dataType.equalsIgnoreCase("RelTotal") ) {
		hb[0] = "RelTotal";
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly releases annual...
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount...
			hb[2] = "Annual";
		}
	}
	else if ( dataType.equalsIgnoreCase("ResEOM") ) {
		hb[0] = "ResEOM";
		// Monthly interval converted above.
	}
	else if ( dataType.equalsIgnoreCase("ResEOY") ) {
		hb[0] = "ResEOY";
		// Monthly interval converted above.
	}
	else if ( dataType.equalsIgnoreCase("ResMeasElev") || dataType.equalsIgnoreCase ( "ResMeasEvap" ) ||
		dataType.equalsIgnoreCase ( "ResMeasFill" ) || dataType.equalsIgnoreCase ( "ResMeasRelease" ) ||
		dataType.equalsIgnoreCase ( "ResMeasStorage" ) ) {
		hb[0] = "ResMeas";
		hb[2] = "Random";
	}
	else if ( dataType.equalsIgnoreCase("Snow") ) {
		hb[0] = "Snow";
		// Interval converted above.
	}
	else if ( dataType.equalsIgnoreCase("SnowCourseDepth") || dataType.equalsIgnoreCase("SnowCourseSWE") ) {
		hb[0] = "SnowCrse";
		// Interval in HydroBase is monthly even though time series will be treated as daily.
		hb[2] = "Monthly";
	}
	else if ( dataType.equalsIgnoreCase("Solar") ) {
		hb[0] = "Solar";
		// Interval converted above.
	}
	else if ( dataType.equalsIgnoreCase("Stage") ) {
		hb[0] = "RT_Misc";
		hb[1] = "GAGE_HT";
		hb[2] = "Real-time";
	}
	else if ( dataType.equalsIgnoreCase("Storage") ) {
		hb[0] = "RT_Vol";
		hb[1] = "STORAGE";
		hb[2] = "Real-time";
	}
	else if(dataType.equalsIgnoreCase("Streamflow") || dataType.equalsIgnoreCase("StreamflowMax") ||
		dataType.equalsIgnoreCase("StreamflowMin") ) {
		// All these match the Streamflow meas_type...
		hb[0] = "Streamflow";
		if ( interval.equalsIgnoreCase("RealTime") || interval.equalsIgnoreCase("Irregular") ) {
			hb[0] = "RT_rate";
			if ( sub_data_type.length() > 0 ) {
				// Assume that the vax_field is accurate...
				hb[1] = sub_data_type;
			}
			else {
			    hb[1] = "DISCHRG";	// Assume this
			}
			hb[2] = "Real-time";
		}
	}
	else if ( dataType.equalsIgnoreCase("Temp") ) {
		hb[0] = "RT_Misc";
		hb[1] = "AIRTEMP";
		// Interval converted above.
		hb[2] = "Real-time";
	}
	else if ( dataType.equalsIgnoreCase("TempMax") ) {
		hb[0] = "MaxTemp";
		// Daily interval converted above.
	}
	else if ( dataType.equalsIgnoreCase("TempMin") ) {
		hb[0] = "MinTemp";
		// Daily interval converted above.
	}
	else if ( dataType.equalsIgnoreCase("TempMeanMax") || dataType.equalsIgnoreCase("TempMeanMin") ||
		dataType.equalsIgnoreCase("TempMean") ) {
		hb[0] = "MeanTemp";
		// Monthly interval converted above.
	}
	else if ( dataType.equalsIgnoreCase("VaporPressure") ) {
		hb[0] = "VP";
		// Interval converted above.
	}
	else if ( dataType.equalsIgnoreCase("WatTemp") ) {
		hb[0] = "RT_Misc";
		hb[1] = "WATTEMP";
		hb[2] = "Real-time";
	}
	else if ( dataType.equalsIgnoreCase("WellLevel") || // Legacy - replaced by WellLevelDepth and WellLevelElev
	    dataType.equalsIgnoreCase("WellLevelDepth") || dataType.equalsIgnoreCase("WellLevelElev") ) {
		if ( interval.equalsIgnoreCase("RealTime") || interval.equalsIgnoreCase("Irregular") ) {
			hb[0] = "RT_Misc";
			hb[1] = "WELL_1";
		}
		else if (interval.equalsIgnoreCase("Day")) {
			hb[0] = "WellLevel";
			hb[2] = "Day";
		}
		else {
		    hb[0] = "WellLevel";
			hb[2] = "Random";
		}
	}
	else if ( dataType.equalsIgnoreCase("Wind") ) {
		hb[0] = "Wind";
		// Interval converted above.
	}
    else if ( dataType.toUpperCase().startsWith("WIS") ) {
        // Pass through WIS data types
        hb[0] = dataType;
        // Interval converted above.
    }

	return hb;
}

/**
Creates a Property List with information needed to fill a Time Series with a constant value.
@param inputTS Time Series to fill.
@param value Constant used to fill the Time Series.
@param start Start DateTime for filling.
@param end End DateTime for filling.
@return PropList List that contains information on filling a constant value for the given Time Series and dates.
 */
public static PropList createFillConstantPropList( TS inputTS, String value,
		String fillFlag, DateTime start, DateTime end)
{
	if( inputTS == null ) {
		return null;
	}
	if( start == null ) {
		start = inputTS.getDate1();
	}
	if( end == null ) {
		end = inputTS.getDate2();
	}
	if( fillFlag == null || fillFlag.equals("") && fillFlag.length() != 1 )
	{
		fillFlag = "Auto";
	}
	
	// Create the PropList
	PropList prop = new PropList( "List to fill TS with constant value");
	prop.add( "FillConstantValue=" + value );
	prop.add( "StartDate=" + start.toString() );
	prop.add( "EndDate=" + end.toString() );
	prop.add( "FillMethods=FillConstant" );
	prop.add( "FillFlag=" + fillFlag);
	
	return prop;
}

/**
Fill a daily diversion (DivTotal or DivClass) or reservoir (RelTotal, RelClass)
time series by carrying forward data.  This method is typically only called by internal database
API code (should be part of data retrieval process, not user-driven data filling).
The following rules are applied:
<ol>
<li>	Filling considers data in blocks of irrigation years (Nov to Oct).</li>
<li>	If an entire irrigation year is missing, no filling occurs.</li>
<li>	If an observation occurs before Oct 31 (but no value is recorded on
	Oct 31), the last observation in the irrigation year is carried to the
	end of the irrigation year.</li>
</li>	If an observation occurs after Nov 1 (but no value is recorded on
	Nov 1), zero is used at the beginning of the irrigation year, until the
	first observation in that irrigation year.</li>
<li>	HydroBase should have full months of daily data for months in which
	there was an observation.  However, do not count on this and fill all
	months of daily data, as per the rules.</li>
</ol>
@param ts Time series to fill.
@param fillDailyDivFlag a string used to flag filled data.
@exception Exception if there is an error filling the data.
*/
static public void fillTSIrrigationYearCarryForward ( DayTS ts, String fillDailyDivFlag )
throws Exception
{	String routine = "HydroBase_Util.fillTSIrrigationYearCarryForward";
	if ( ts == null ) {
		return;
	}
	if ( !(ts instanceof DayTS) ) {
		return;
	}

	DateTime FillStart_DateTime = new DateTime ( ts.getDate1() );
	DateTime FillEnd_DateTime = new DateTime ( ts.getDate2() );

	// Allocate fill flag space in the time series, if necessary...

	boolean FillDailyDivFlag_boolean = false; // Indicate whether to use flag
	if ( (fillDailyDivFlag != null) && (fillDailyDivFlag.length() > 0) ) {
		FillDailyDivFlag_boolean = true;
		// Make sure that the data flag is allocated.
		// TODO SAM 2012-05-09 Can get rid of this at some point since automatic allocation when set
		ts.allocateDataFlagSpace (
			null, // Initial flag value
			true );	// Keep old flags if already allocated
	}

	// Loop through the period in blocks of irrigation years.

	FillStart_DateTime.setMonth(11);
	FillStart_DateTime.setDay(1);
	if ( FillStart_DateTime.greaterThan(ts.getDate1()) ) {
		// Subtract a year to get the full irrigation year...
		FillStart_DateTime.addYear ( -1 );
	}

	List<String> messages = new Vector();
	DateTime date = new DateTime ( FillStart_DateTime );
	DateTime yearstart_DateTime = null;	// Fill dates for one year
	DateTime yearend_DateTime = null;
	int found_count = 0; // Count of non-missing values in year
	int missing_count = 0; // Count of missing values in a year
	double value = 0.0; // Time series data value
	double fill_value = 0.0; // Value to be used to fill data.
	TSData tsdata = new TSData(); // Needed to retrieve time series data with flags.
	for ( ; date.lessThanOrEqualTo(FillEnd_DateTime); date.addDay(1) ) {
		if ( (date.getMonth() == 11) && (date.getDay() == 1) ) {
			// Start of a new irrigation year.
			// First go through the whole year to determine if any
			// non-missing values exist.  If not, then skip the
			// entire year (leave the entire year missing).
			yearstart_DateTime = new DateTime ( date );	// Save
			yearend_DateTime = new DateTime ( date );
			yearend_DateTime.addYear ( 1 );
			yearend_DateTime.setMonth ( 10 );
			yearend_DateTime.setDay ( 31 );
			if ( Message.isDebugOn ) {
				Message.printDebug ( 2, routine,
				    "Checking for data in " + yearstart_DateTime + " to " + yearend_DateTime );
			}
			found_count = 0;
			if ( Message.isDebugOn ) {
				Message.printDebug ( 2, "", "Processing irrigation year starting at " + date );
			}
			for ( ; date.lessThanOrEqualTo(yearend_DateTime); date.addDay(1) ) {
				value = ts.getDataValue ( date );
				if ( !ts.isDataMissing(value) ) {
					// Have an observation...
					++found_count;
				}
			}
			if ( Message.isDebugOn ) {
				Message.printDebug ( 2, routine, "Found " + found_count + " days of observations." );
			}
			if ( found_count == 0 ) {
				// Just continue and process the next year...
				continue;
			}
			// Else, will repeat the year that was just checked to fill it in.
			date = new DateTime(yearstart_DateTime);
			fill_value = 0.0;
			missing_count = 0;
			for ( ; date.lessThanOrEqualTo(yearend_DateTime); date.addDay(1) ) {
				value = ts.getDataValue ( date );
				if ( ts.isDataMissing(value) ) {
					++missing_count;
					if ( FillDailyDivFlag_boolean ) {
						// Set the data flag, appending to the old value...
						tsdata = ts.getDataPoint(date,tsdata);
						ts.setDataValue ( date, fill_value, (tsdata.getDataFlag().trim() + fillDailyDivFlag), 1 );
					}
					else {
					    // No data flag...
						ts.setDataValue ( date, fill_value );
					}
				}
				else {
				    // Reset the value to be used to fill other data...
					fill_value = value;
				}
			}
			// Save messages to add to the genesis history...
			if ( missing_count > 0 ) {
				messages.add (
				"Nov 1, " + yearstart_DateTime.getYear() + " to Oct 31, " + yearend_DateTime.getYear() +
				" filled " + missing_count + " values." );
			}
			// Reset the date to the end of the irrigation year so
			// that an increment will cause a new irrigation year to be processed...
			date = new DateTime ( yearend_DateTime );
		}
	}

	// Add to the genesis...

	if ( messages.size() > 0 ) {
		ts.addToGenesis("Filled " + ts.getDate1() + " to " +
		ts.getDate2() + " using carry forward within irrigation year." );
		if ( Message.isDebugOn ) {
			// TODO SAM 2006-04-27 Evaluate whether this should always be saved in the
			// genesis.  The problem is that the genesis can get very long.
			for ( String message: messages) {
				ts.addToGenesis ( message );
			}
		}
		if ( (fillDailyDivFlag != null) && !fillDailyDivFlag.equals("") ) {
		    ts.addDataFlagMetadata(
		        new TSDataFlagMetadata(fillDailyDivFlag, "Filled within irrigation year using DWR carry-forward approach."));
		}
	}
}
	
/**
Fill a daily, monthly, or yearly diversion (DivTotal, DivClass) or reservoir (RelTotal, RelClass) time series
using diversion comment information.  Certain "not used" flags indicate a value of zero.  This method
DOES NOT change the period of record to include comments outside the original period.  It also does not
use a flag to indicate filled values.  Use the overloaded method to indicate filled values.
@param hbdmi HydroBaseDMI to use for queries.
@param ts Time series to fill.
@param date1 Starting date to fill, or null to fill all.
@param date2 Ending date to fill, or null to fill all.
@exception Exception if there is an error filling the time series (e.g., exception thrown querying HydroBase).
*/
public static void fillTSUsingDiversionComments(HydroBaseDMI hbdmi, TS ts, DateTime date1, DateTime date2)
throws Exception
{	fillTSUsingDiversionComments ( hbdmi, ts, date1, date2, null, false );
}

/**
Fill a daily, monthly, or yearly diversion (DivTotal, DivClass) or reservoir (RelTotal, RelClass) time series
using diversion comment information.  Certain "not used" flags ("A", "B", "C", "D") indicate a value of
zero for the irrigation year (Nov 1 to Oct 31).  Yearly time series are assumed
to be in irrigation year, to match the diversion comments.
@param hbdmi HydroBaseDMI to use for queries.
@param ts Time series to fill.
@param date1 Starting date to fill, or null to fill all.
@param date2 Ending date to fill, or null to fill all.
@param fillflag If specified (not null or zero length), the time series will be
updated to allow data flags and filled values will be tagged with the specified
string.  A value of "Auto" will result in a one-character fill flag, where
the flag value is set to the "not used" flag value.
@param extend_period Indicate whether the time series period should be extended
if diversion comment values are found outside the current time series period.
This will only occur if the fill dates are null (indicating that all available
data should be used, for example in general queries).
A value of true will extend the period if necessary.
@exception Exception if there is an error filling the time series (e.g., exception thrown querying HydroBase).
*/
public static void fillTSUsingDiversionComments(HydroBaseDMI hbdmi, TS ts, DateTime date1, DateTime date2,
	String fillflag, boolean extend_period )
throws Exception
{	String routine = "HydroBase_Util.fillDiversionUsingComments";

	if ( ts == null ) {
		// Nothing to fill.
		return;
	}

	TS divcomts = null;
	int interval_base = ts.getDataIntervalBase();
	int interval_mult = ts.getDataIntervalMult();
	try {
	    // Get the diversion comments as a time series, where the year in the time series is irrigation year.
		if ( ts.getDataType().equalsIgnoreCase("DivTotal") || ts.getDataType().equalsIgnoreCase("DivClass") ) {
			// Diversion comments...
			divcomts = hbdmi.readTimeSeries(
			ts.getLocation() + ".DWR.DivComment.Year", date1, date2, null, true, null);
		}
		else if(ts.getDataType().equalsIgnoreCase("RelTotal") || ts.getDataType().equalsIgnoreCase("RelClass") ) {
			// Release comments...
			divcomts = hbdmi.readTimeSeries(
			ts.getLocation() + ".DWR.RelComment.Year", date1, date2, null, true, null);
		}
	}
	catch ( NoDataFoundException e ) {
		// This is OK.  There just aren't any diversion comments in the database for this structure.
		divcomts = null;
	}
	catch(Exception e) {
		String message = "Error getting diversion comments for " +
		ts.getLocation() + ".  HydroBase/software compatibility issue?";
		Message.printWarning(2, routine, message );
		Message.printWarning ( 3, routine, e );
		throw new HydroBaseException ( message ); 
	}
	if ( divcomts == null ) {
		Message.printStatus(2, routine, "No diversion comments for " + ts.getIdentifierString() + ".  Not filling data.");
		return;
	}

	DateTime divcomdate = new DateTime(divcomts.getDate1());
	DateTime divcomend = new DateTime(divcomts.getDate2()); // Period for diversion comments
	// The following causes the precision to be set correctly...
	DateTime tsdate1 = new DateTime(ts.getDate1());
	DateTime tsdate2 = new DateTime(ts.getDate2()); // Period for time series to fill.
	String zero_flags = "ABCD"; 	// "not used" flag that indicates a zero value.

	String not_used = null; // Flag in diversion comments indicating whether diversion was used in irrigation year.
	TSData tsdata = new TSData(); // Single data point from a time series.
	int fill_count = 0; // Number of data values filled in the irrigation year (or number of years with
						// diversion comments below).
	if ( (date1 == null) && (date2 == null) && extend_period ) {
		// Loop through diversion comments once and determine whether
		// the period needs to be extended.  This should only be done if
		// the not_used flag is one of the zero values.
		int yearmin = 3000;
		int yearmax = -3000;
		for ( ; divcomdate.lessThanOrEqualTo(divcomend); divcomdate.addYear(1)) {
			tsdata = divcomts.getDataPoint(divcomdate,tsdata);
			not_used = tsdata.getDataFlag();
			if ( (not_used == null)|| (not_used.length() == 0) || (zero_flags.indexOf(not_used) < 0) ) {
				// No need to process the flag because a zero water use flag is not specified...
				continue;
			}
			++fill_count;
			// Save the min and max year.  Note that these are irrigation years since that is what is stored in
			// the diversion comment time series.
			yearmin = MathUtil.min ( yearmin, divcomdate.getYear());
			yearmax = MathUtil.max ( yearmax, divcomdate.getYear());
		}
		// If the minimum and maximum year from the above indicate that
		// the period should be longer, extend it.  Do the comparisons in calendar years.
		DateTime min_DateTime = new DateTime();
		min_DateTime.setYear ( yearmin - 1 );
		min_DateTime.setMonth ( 11 );
		min_DateTime.setDay ( 1 );
		DateTime max_DateTime = new DateTime();
		max_DateTime.setYear ( yearmax );
		max_DateTime.setMonth ( 10 );
		max_DateTime.setDay ( 31 );
		if ( ts instanceof DayTS ) {
			min_DateTime.setPrecision(DateTime.PRECISION_DAY);
			max_DateTime.setPrecision(DateTime.PRECISION_DAY);
		}
		else if ( ts instanceof MonthTS ) {
			min_DateTime.setPrecision( DateTime.PRECISION_MONTH);
			max_DateTime.setPrecision( DateTime.PRECISION_MONTH);
		}
		else if ( ts instanceof YearTS ) {
			min_DateTime.setPrecision(DateTime.PRECISION_YEAR);
			max_DateTime.setPrecision(DateTime.PRECISION_YEAR);
		}
		if ( min_DateTime.lessThan(tsdate1) || max_DateTime.greaterThan(tsdate2) ) {
			// Resize the time series, using the original date/time on an end if necessary...
			if ( min_DateTime.greaterThan(tsdate1) ) {
				min_DateTime = new DateTime ( tsdate1 );
			}
			if ( max_DateTime.lessThan(tsdate2) ) {
				max_DateTime = new DateTime ( tsdate2 );
			}
			ts.addToGenesis("Change period because all data are requested and diversion comments are available.");
			ts.changePeriodOfRecord ( min_DateTime, max_DateTime );
		}
	}

	// If at least one diversion comment was found above, allocate space for the data flags...
	// TODO SAM 2006-04-26 This won't actually be needed if no filling occurs, but optimize this later.

	boolean fillflag_boolean = false;	// Indicate whether to use flag
	boolean fillflag_auto = false;
	if ( (fill_count > 0) && (fillflag != null) && (fillflag.length() > 0)){
		fillflag_boolean = true;
		// Make sure that the data flag is allocated.
		if ( fillflag.equalsIgnoreCase("Auto") ) {
			fillflag_auto = true;
		}
		ts.allocateDataFlagSpace (
			null,	// Initial flag value
			true );	// Keep old flags if already allocated
	}
	
	DateTime tsdate;
	int iyear = 0; // Irrigation year to process.
	double dataValue = 0.0;	// Value from the time series.
	String fillflag2 = null;// String used to do filling, reflecting "Auto" value
	for ( divcomdate = new DateTime(divcomts.getDate1());
		divcomdate.lessThanOrEqualTo(divcomend); divcomdate.addYear(1)) {
		tsdata = divcomts.getDataPoint(divcomdate,tsdata);
		not_used = tsdata.getDataFlag();
		if ( (not_used == null) || (not_used.length() == 0) || (zero_flags.indexOf(not_used) < 0) ) {
			// No need to process the flag because a zero water use flag is not specified.
            continue;
		}
		// Have a valid "not used" flag to be used in filling.  The year for diversion comments time series is
		// irrigation year.
		// TODO SAM 2006-04-25 Always fill the missing values.  It is assumed that HydroBase
		// has diversion comments only for irrigation years where no detailed observations occur.  This should
		// be checked during HydroBase verification (but is not checked in this code).
		iyear = divcomdate.getYear();
		fill_count = 0;
		if ( interval_base == TimeInterval.YEAR ) {
			// Can check one date, corresponding to the diversion comment year...
			dataValue = ts.getDataValue(divcomdate);
			if ( ts.isDataMissing(dataValue) ) {
				if ( fillflag_boolean ) {
					// Set the data flag, appending to the old value...
					if ( fillflag_auto ) {
						// Automatically use the "not used" flag value...
						fillflag2 = not_used;
					}
					tsdata = ts.getDataPoint(divcomdate,tsdata);
					ts.setDataValue ( divcomdate, 0.0, (tsdata.getDataFlag().trim() + fillflag2), 1 );
				}
				else {
				    // No data flag...
					ts.setDataValue ( divcomdate, 0.0 );
				}
				++fill_count;
			}
		}
		else if ( (interval_base == TimeInterval.MONTH) || (interval_base == TimeInterval.DAY) ) {
			// Check every time series value in the irrigation year.
			tsdate1.setDay(1);
			tsdate1.setMonth(11);
			// Diversion comments are in irrigation year, which
			// corresponds to the end of the Nov-Oct period...
			tsdate1.setYear(iyear - 1);
			tsdate2.setDay(31);
			tsdate2.setMonth(10);
			tsdate2.setYear(iyear);
			// The following will work for either daily or monthly because the precision on tsdate1 is set based
			// on the time series interval (if monthly the day will be ignored)...
			for ( tsdate = new DateTime(tsdate1); tsdate.lessThanOrEqualTo(tsdate2);
				tsdate.addInterval(interval_base,interval_mult)) {
				dataValue = ts.getDataValue(tsdate);
				if ( ts.isDataMissing(dataValue)) {
					if ( fillflag_boolean ) {
						// Set the data flag, appending to the old value...
						if ( fillflag_auto ) {
							// Automatically use the "not used" flag value...
							fillflag2 = not_used;
						}
						tsdata =ts.getDataPoint(tsdate,tsdata);
						ts.setDataValue ( tsdate, 0.0, (tsdata.getDataFlag().trim() + fillflag2), 1 );
					}
					else {
					    // No data flag...
						ts.setDataValue ( tsdate, 0.0 );
					}
					++fill_count;
				}
			}
		}
		// Print/save a message for the year...
		if ( fill_count > 0 ) {
			String comment_string = "Filled " + fill_count + " values in irrigation year " +
			iyear + " (Nov " + (iyear - 1) + "-Oct " + iyear + ") with zero because not_used=\"" + not_used + "\"";
			ts.addToGenesis(comment_string);
			Message.printStatus(2, routine, comment_string);
			// Save the flags that are used
			if ( fillflag_boolean ) {
                // Set the data flag, appending to the old value...
                if ( fillflag_auto ) {
                    // Use the standard values as documented by DWR
                    ts.addDataFlagMetadata(new TSDataFlagMetadata("A","STRUCTURE NOT USABLE") );
                    ts.addDataFlagMetadata(new TSDataFlagMetadata("B","NO WATER AVAILABLE") );
                    ts.addDataFlagMetadata(new TSDataFlagMetadata("C","WATER AVAILABLE BUT NOT TAKEN") );
                    ts.addDataFlagMetadata(new TSDataFlagMetadata("D","WATER TAKEN IN ANOTHER STRUCTURE") );
                    ts.addDataFlagMetadata(new TSDataFlagMetadata("E","WATER TAKEN BUT NO DATA AVAILABLE") );
                    ts.addDataFlagMetadata(new TSDataFlagMetadata("F","NO INFORMATION AVAILABLE") );
                }
                else {
                    // User-defined value
                    ts.addDataFlagMetadata(new TSDataFlagMetadata(fillflag,"User-defined.") );
                }
            }
		}
	}
}

/**
Transforms the location elements into a standard legal location string and returns that string.
@param pm Principle Meridian
@param ts Township
@param tdir Township direction
@param rng Range
@param rdir Range direction
@param sec Section
@param seca Half section
@param q160 q160
@param q40 q40
@param q10 q10
@return a standard legal location string made from the passed-in parameters.
*/
public static String formatLegalLocation(String pm, int ts, String tdir, 
int rng, String rdir, int sec, String seca,String q160, String q40, String q10){
	List v = new Vector (10);
	v.add(pm);
	v.add("" + ts);
	v.add(tdir);
	v.add("" + rng);
	v.add(rdir);
	v.add("" + sec);
	v.add(seca);
	v.add(q160);
	v.add(q40);
	v.add(q10);
	return StringUtil.formatString (v, "%2.2s%2.2s%1.1s%3.3s%1.1s%4.4s%-1.1s%5.5s%4.4s%4.4s");
}



/**
Get the configuration file name for HydroBase.  Currently this is the same as
the main CDSS configuration file
*/
public static String getConfigurationFile() {
	return IOUtil.getApplicationHomeDir() + File.separator + "system" + File.separator + "CDSS.cfg";
}

/**
Return the name of the default HydroBase SQL Server machine.
*/
public static String getDefaultDatabaseServer ()
{	return "greenmtn.state.co.us";
}

/**
Return the default time series data type, to allow a GUI to select the default in a list.
@return the default time series data type.
@param add_group Indicates if a data group should be included before the data
type - this should be called consistent with getTimeSeriesDataTypes().
*/
public static String getDefaultTimeSeriesDataType (	HydroBaseDMI hbdmi, boolean add_group )
{	if ( add_group ) {
		return "Stream - Streamflow";
	}
	else {
	    return "Streamflow";
	}
}

/**
Get the preferred WDID length, used when formatting WDIDs from the WD and ID
parts.  The first two characters will always be for the WD.
@return the preferred WDID length.
*/
public static int getPreferredWDIDLength ()
{	return __preferred_WDID_length;
}

/**
Return a list of time series data types, suitable for listing in a graphical
interface.  For example, this list is used in TSTool and StateView to list
data types, which are then passed to the readTimeSeries(String TSID) method,
where the data type in the list matches the data type in the time series identifier.
In the future, this list may be determined from a query of HydroBase's meas_type
and/or struct_meas_type tables.  Currently, the types are hard-coded here
because they cannot cleanly be determined from HydroBase.
@param hdmi HydroBaseDMI instance for opened connection.
@param include_types A mask using DATA_TYPE_* indicating which data types to
include.  For example, for a tool that only wants to display station time
series, specify DATA_TYPE_STATION_ALL.  For a tool that wants to display only
reservoir structure time series, specify DATA_TYPE_STRUCTURE_RESERVOIR.  Types
can be combined if appropriate.
@param add_group If true, a data type group will be added before the data types.
For example, all data related to reservoirs will be prefixed with
"Reservoir - ".  This is useful for providing a better presentation to users in interfaces.
*/
public static List<String> getTimeSeriesDataTypes ( HydroBaseDMI hdmi, int include_types, boolean add_group )
{	List<String> types = new Vector();
	// Add these types exactly as they are listed in HydroBase.  If someone
	// has a problem, HydroBase should be adjusted.  Notes are shown below
	// where there may be an issue.  In all cases, documentation needs to
	// be made available to describe the data type (e.g., that "Snow" is
	// accumulated value on the ground).  Data intervals (time step) are
	// also shown.  Station types correspond to meas_type and structure
	// types correspond to struct_meas_type.
	//
	// Ideally, all the rt_meas data could be identified with a data type
	// and a "RealTime" time step (or similar).  However, RealTime is not
	// an interval recognized by the TimeInterval class and instead
	// Irregular is used.  In the future, perhaps it will be possible to get
	// an exact regular interval for real-time gages.
	//
	// A sub data type can be used instead of VAXFIELD in the time series identifier.  For example:
	//
	// Location.DataSource.DataType-SubType.Interval
	//
	// StationID.DataSource.Streamflow-DISCHRG.Irregular
	// StationID.DataSource.Streamflow-DISCHRG1.5Minute
	//
	// The sub type could be queried based on the data type.
	//
	// For crop time series from the GIS-based summary, the identifier could look like:
	//
	// WDID.CDSSGIS.CropArea-ALFALFA.Year
	//
	// For the crop time series from Agstats, it would be something like:
	//
	// County.DOA.Agstats-xxxx xxx.Year
	//
	// For SFUT, the time series identifier would look like:
	//
	// WDID.DWR.DivClass-S:F:U:T:.Month
	//
	String prefix; // A string to add at the front of the type if add_group is true
	if ( (include_types&DATA_TYPE_HARDWARE) > 0 ) {
		// RT_Misc BATTERY
		prefix = "";
		if ( add_group ) {
			prefix = "Hardware - ";
		}
		types.add ( prefix + "Battery" );
	}
	if ( (include_types&DATA_TYPE_STATION_CLIMATE) > 0 ) {
		prefix = "";
		if ( add_group ) {
			prefix = "Climate - ";
		}
		//types.addElement ( "Evap" ); // Maybe should be "EvapPan" or "PanEvap"
							// Time step:  Day and Month
		types.add ( prefix + "EvapPan" );	// Proposed
		//types.addElement ( "FrostDate" );	// Need something like
							// FrostDateF28,
							// FrostDateF32,
							// FrostDateS28,
							// FrostDateS32
							// so each time series can be manipulated.
							// Time step:  Year
		types.add ( prefix + "FrostDateF32F" );	// Proposed
		types.add ( prefix + "FrostDateF28F" );	// Proposed
		types.add ( prefix + "FrostDateL28S" );	// Proposed
		types.add ( prefix + "FrostDateL32S" );	// Proposed
		//types.add ( "MaxTemp" );	// Perhaps should have:
		//types.add ( "MinTemp" );	// "TempMax", "TempMin",
		//types.add ( "MeanTemp" );	// for daily and
							// "TempMean",
							// "TempMeanMax",
							// "TempMeanMin" for
							// monthly - it gets
							// messy.
		types.add ( prefix + "Precip" ); // Time step:  Month, Day, real-time with vax_field PRECIP
		types.add ( prefix + "Temp" ); // Proposed for use with real-time AIRTEMP vax_field
		types.add ( prefix + "TempMax" );// Proposed
		types.add ( prefix + "TempMean" );	// Proposed
		types.add ( prefix + "TempMeanMax" );	// Proposed
		types.add ( prefix + "TempMeanMin" );	// Proposed
		types.add ( prefix + "TempMin" );	// Proposed
		types.add ( prefix + "Snow" );	// Time step:  Day, Month
		//types.add ( "SnowCrse" );	// "SnowDepth" and
							// "SnowWaterEquivalent"
							// would be better, but
							// need to check how
							// depth compares to
							// "Snow" type
							// Time step:
							// Day or IrregDay?
		types.add ( prefix + "SnowCourseDepth" );// Proposed
		types.add ( prefix + "SnowCourseSWE" );	// Proposed
		types.add ( prefix + "Solar" );	// Time step:  Day
		//types.add ( "VP" );		// Maybe VaporPressure
		types.add ( prefix + "VaporPressure" );	
							// Maybe VaporPressure
							// is better.
							// Time step:  Day
		types.add ( prefix + "Wind" );	// OK but maybe
							// WindTravel or similar
							// is better (some gages
							// measure wind
							// direction)?
							// Time step:  Day
	}
	if ( (include_types&DATA_TYPE_STATION_STREAM) > 0 ) {
		// AdminFlow was added in 2007 for administrative gages - it has the
		// same data types as the main Streamflow...
		try {
		if ( hdmi.isVersionAtLeast(HydroBaseDMI.VERSION_20070502) ) {
			prefix = "";
			if ( add_group ) {
				prefix = "AdminFlow - ";
			}
			types.add ( prefix + "AdminFlow" );
			types.add ( prefix + "AdminFlowMax" );
			types.add ( prefix + "AdminFlowMin" );
		}
		}
		catch ( Exception e ) {
			// Don't add AdminFlow
		}
		prefix = "";
		if ( add_group ) {
			prefix = "Stream - ";
		}
		//types.add ( "Nat_flow" );	// Maybe "NatFlow" or
							// "NaturalFlow" is
							// better.
							// Time step:  Month
		// Removed 2007-04-29 Based on State removing from HydroBase
		//types.add ( prefix + "NaturalFlow" );
							// Proposed
		//types.add ( "RT_Rate" );	// "Streamflow" with a
							// "realtime" time step
							// would be cleaner
							// Time step:
							// RealTime?  15Minute?
		types.add ( prefix + "Stage" );	// Time step:  real-time RT_Misc GAGE_HT
		types.add ( prefix + "Streamflow" );	// Also need
		types.add ( prefix + "StreamflowMax" );
							// "StreamflowMax" and
		types.add ( prefix + "StreamflowMin" );
							// "StreamflowMin"
							// Time step:
							// Day, Month, RealTime?
		types.add ( prefix + "WatTemp" );// Water temperature:
							// RT_Misc WATTEMP
	}
	if ( (include_types&DATA_TYPE_STATION_RESERVOIR) > 0 ) {
		prefix = "";
		if ( add_group ) {
			prefix = "Reservoir - ";
		}
		types.add ( prefix + "Release" );// Time step:
							// real-time with
							// vax_field OUTLETQ
		//types.add ( "RT_Vol" )		// "ResContent",
							// "ResStorage", or
							// similar would be
							// better
							// Time step:
							// RealTime?  15Minute?
		//types.add ( "RT_Height" );	// "Stage" and
							// "PoolElev" would be
							// better - treat the
							// time step separately
							// Time step:
							// RealTime?  15Minute?
		types.add ( prefix + "PoolElev" );// Time step:
							// RT_Height with
							// vax_field ELEV
		types.add ( prefix + "Storage" );// Time step:  realtime
							// RT_Vol STORAGE
	}
	if ( (include_types&DATA_TYPE_STATION_WELL) > 0 ) {
		prefix = "";
		if ( add_group ) {
			prefix = "Well - ";
		}
		types.add ( prefix + "WellLevel (phasing out)"); // RT_Misc WELL_1
		types.add ( prefix + "WellLevelElev"); // Replaces legacy WellLevel
		// Also see WellLevel for structures.
	}
	if ( (include_types&DATA_TYPE_AGRICULTURE) > 0 ) {
		prefix = "";
		// Colorado agricultural statistics
		if ( add_group ) {
			prefix = "Agriculture/CASS - ";
		}
		types.add ( prefix + "CropAreaHarvested" );
		types.add ( prefix + "CropAreaPlanted" );
		types.add ( prefix + "LivestockHead" );
		// National agricultural statistics
		if ( add_group ) {
			prefix = "Agriculture/NASS - ";
		}
		types.add ( prefix + "CropArea" );
		// Data derived from DSS GIS work...
		if ( add_group ) {
			prefix = "Agriculture/GIS - ";
		}
		types.add ( prefix + "CropAreaAllIrrigation" );
		types.add ( prefix + "CropAreaFlood" );
		types.add ( prefix + "CropAreaDrip" );
		types.add ( prefix + "CropAreaFurrow" );
		types.add ( prefix + "CropAreaSprinkler" );
							// This is something
							// new, which will
							// support StateDMI and
							// TSTool -
							// irrig_summary_ts
							// table
	}
	if ( (include_types&DATA_TYPE_DEMOGRAPHICS_POPULATION) > 0 ) {
		prefix = "";
		if ( add_group ) {
			prefix = "Demographics - ";
		}
		types.add ( prefix + "HumanPopulation" );
	}
	if ( (include_types&DATA_TYPE_STRUCTURE_DIVERSION) > 0 ) {
		prefix = "";
		if ( add_group ) {
			prefix = "Diversion - ";
		}
		types.add ( prefix + "DivClass" );// These are probably
		types.add ( prefix + "DivComment" );// OK - will use
		types.add ( prefix + "DivTotal" );// SFUT as the sub data
							// type.
		types.add ( prefix + "IDivClass" );
		types.add ( prefix + "IDivTotal" );
	}
	if ( (include_types&DATA_TYPE_STRUCTURE_RESERVOIR) > 0 ) {
		prefix = "";
		if ( add_group ) {
			prefix = "Reservoir - ";
		}
		types.add ( prefix + "IRelClass" );
		types.add ( prefix + "IRelTotal" );
		types.add ( prefix + "RelClass" );
		types.add ( prefix + "RelComment" );
		types.add ( prefix + "RelTotal" );
		types.add ( prefix + "ResEOM" );
		types.add ( prefix + "ResEOY" );
		//types.add ( "ResMeas" );	// Would be better as
							// "ResMeasFill",
							// "ResMeasRelease",
							// "ResMeasElev",
							// "ResMeasEvap"
		types.add ( prefix + "ResMeasElev" );
							// Add to evaluate...
		types.add ( prefix + "ResMeasEvap" );
		types.add ( prefix + "ResMeasFill" );
		types.add ( prefix + "ResMeasRelease" );
		types.add ( prefix + "ResMeasStorage" );
	}
	if ( (include_types&DATA_TYPE_STRUCTURE_WELL) > 0 ) {
		prefix = "";
		if ( add_group ) {
			prefix = "Well - ";
		}
		types.add ( prefix + "WellLevel (phasing out)" );
		types.add ( prefix + "WellLevelDepth" );
		types.add ( prefix + "WellLevelElev" ); // Replaces legacy WellLevel
		// TODO SAM 2012-07-02 Also somehow need to hook in "WellPumping"
	}
	if ( (include_types&DATA_TYPE_WIS) > 0 ) {
		prefix = "";
		if ( add_group ) {
			prefix = "WIS - ";
		}
		types.add ( prefix + "WISPointFlow" );
		types.add ( prefix + "WISNaturalFlow" );
		types.add ( prefix + "WISDeliveryFlow" );
		types.add ( prefix + "WISGainLoss" );
		types.add ( prefix + "WISPriorityDiversion" );
		types.add ( prefix + "WISDeliveryDiversion" );
		types.add ( prefix + "WISRelease" );
		types.add ( prefix + "WISTribNaturalFlow" );
		types.add ( prefix + "WISTribDeliveryFlow" );

		// Time series identifiers are of the form:
		//
		// wdid:NN.DWR.WISPointFlow.Day
	}
	// Now sort the list...
	types = StringUtil.sortStringList ( types );
	// Remove duplicates - for example, this may occur for WellLevel because
	// it is listed with stations and structures...
	StringUtil.removeDuplicates ( types, true, true );
	return types;
}

/**
Return the data units for a time series data type.  The units should ideally be
in meas_type; however, some time series tables store more than one time series
data type and the units are constant.  If units are variable and are stored in
time series data records, return blank.
@return the data units for a time series data type.
@param hbdmi Reserved for future use (can pass as null).
@param data_type the time series data type, using time series data conventions
(e.g., use "StreamflowMax" and "Month" rather than "Streamflow", "Monthly", which is not specific enough).
@param interval Data interval for time series.
*/
public static String getTimeSeriesDataUnits ( HydroBaseDMI hbdmi, String data_type, String interval )
{	String ACRE = "ACRE", ACFT = "ACFT", CFS = "CFS", DAY = "DAY",
		FT = "FT", HEAD = "HEAD",
		IN = "IN", KM = "KM",
		KPA = "KPA", MJM2 = "MJ/M2", PERSONS = "PERSONS",
		UNKNOWN = "", VOLT = "VOLT";
	if ( data_type.equalsIgnoreCase("AdminFlow") ) {
		if ( interval.equalsIgnoreCase("Month") ) {
			return ACFT;
		}
		else if ( interval.equalsIgnoreCase("Day") || interval.equalsIgnoreCase("RealTime") ||
			interval.equalsIgnoreCase("Irregular")) {
			return CFS;
		}
	}
	else if ( data_type.equalsIgnoreCase("AdminFlowMax") && interval.equalsIgnoreCase("Month") ) {
		return CFS;
	}
	else if ( data_type.equalsIgnoreCase("AdminFlowMin") && interval.equalsIgnoreCase("Month") ) {
		return CFS;
	}
	else if ( data_type.startsWith("CropArea") ) {
		return ACRE;
	}
	else if ( data_type.equalsIgnoreCase("Battery") ) {
		return VOLT;
	}
	else if (data_type.equalsIgnoreCase("DivClass") || data_type.equalsIgnoreCase("DivTotal") ||
		data_type.equalsIgnoreCase("IDivClass") || data_type.equalsIgnoreCase("IDivTotal") ) {
		if ( interval.equalsIgnoreCase("Month") ) {
			return ACFT;
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			return ACFT;
		}
		else if ( interval.equalsIgnoreCase("Day") ) {
			return CFS;
		}
	}
	else if ( data_type.equalsIgnoreCase("DivComment") ) {
		// Units are for the acreage
		return ACRE;
	}
	else if ( data_type.equalsIgnoreCase("EvapPan") ) {
		return IN;
	}
	else if( data_type.equalsIgnoreCase("FrostDateL28S") || data_type.equalsIgnoreCase("FrostDateL32S") ||
		data_type.equalsIgnoreCase("FrostDateF32F") || data_type.equalsIgnoreCase("FrostDateF28F") ) {
		return DAY;
	}
	else if ( data_type.equalsIgnoreCase("HumanPopulation") ) {
		return PERSONS;
	}
	else if ( data_type.equalsIgnoreCase("LivestockHead") ) {
		return HEAD;
	}
	else if ( data_type.equalsIgnoreCase("NaturalFlow") ) {
		return ACFT;
	}
	else if ( data_type.equalsIgnoreCase("PoolElev") ) {
		return FT;
	}
	else if ( data_type.equalsIgnoreCase("Precip") ) {
	    // TODO SAM 2012-05-15 Is this true - need this for web services
		// Units are in data records and can be IN or mm...
		return "";
	}
	else if ( data_type.equalsIgnoreCase("RelClass") || data_type.equalsIgnoreCase("RelTotal") ||
		data_type.equalsIgnoreCase("IRelClass") || data_type.equalsIgnoreCase("IRelTotal") ) {
		if ( interval.equalsIgnoreCase("Month") ) {
			return ACFT;
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			return ACFT;
		}
		else if ( interval.equalsIgnoreCase("Day") ) {
			return CFS;
		}
	}
	else if ( data_type.equalsIgnoreCase("RelComment") ) {
		// Units are for the acreage
		return ACRE;
	}
	else if ( data_type.equalsIgnoreCase("Release") ) {
		return CFS;
	}
	else if ( data_type.equalsIgnoreCase("ResEOM") ) {
		return ACFT;
	}
	else if ( data_type.equalsIgnoreCase("ResEOY") ) {
		return ACFT;
	}
	else if ( data_type.equalsIgnoreCase("ResMeasElev") ) {
		return FT;
	}
	else if ( data_type.equalsIgnoreCase ( "ResMeasEvap" ) ) {
		return ACFT;
	}
	else if ( data_type.equalsIgnoreCase ( "ResMeasFill" ) ) {
		return ACFT;
	}
	else if ( data_type.equalsIgnoreCase ( "ResMeasRelease") ) {
		return ACFT;
	}
	else if ( data_type.equalsIgnoreCase ( "ResMeasStorage" ) ) {
		return ACFT;
	}
	else if ( data_type.equalsIgnoreCase("Snow") || data_type.equalsIgnoreCase("SnowCourseDepth") ||
		data_type.equalsIgnoreCase("SnowCourseSWE") ) {
		return IN;
	}
	else if ( data_type.equalsIgnoreCase("Solar") ) {
		return MJM2;
	}
	else if ( data_type.equalsIgnoreCase("Stage") ) {
		return FT;
	}
	else if ( data_type.equalsIgnoreCase("Storage") ) {
		return ACFT;
	}
	else if ( data_type.equalsIgnoreCase("Streamflow") ) {
		if ( interval.equalsIgnoreCase("Month") ) {
			return ACFT;
		}
		else if ( interval.equalsIgnoreCase("Day") || interval.equalsIgnoreCase("RealTime") ||
			interval.equalsIgnoreCase("Irregular")) {
			return CFS;
		}
	}
	else if ( data_type.equalsIgnoreCase("StreamflowMax") &&
		interval.equalsIgnoreCase("Month") ) {
		return CFS;
	}
	else if ( data_type.equalsIgnoreCase("StreamflowMin") &&
		interval.equalsIgnoreCase("Month") ) {
		return CFS;
	}
	else if(data_type.equalsIgnoreCase("TempMax") || data_type.equalsIgnoreCase("TempMin") ||
		data_type.equalsIgnoreCase("TempMean") || data_type.equalsIgnoreCase("TempMeanMax") ||
		data_type.equalsIgnoreCase("TempMeanMin") ) {
		// Units can't be detected until records are read...
		return "";
	}
	else if ( data_type.equalsIgnoreCase("VaporPressure") ) {
		return KPA;
	}
	else if ( data_type.equalsIgnoreCase("WellLevel") || // Legacy
	    data_type.equalsIgnoreCase("WellLevelElev") || // New conventions
	    data_type.equalsIgnoreCase("WellLevelDepth")) {
		return FT;
	}
	else if ( data_type.equalsIgnoreCase("WatTemp") ) {
		return UNKNOWN;
	}
	else if ( data_type.equalsIgnoreCase("Wind") ) {
		return KM;
	}
	else if ( data_type.regionMatches(true,0,"WIS",0,3) ) {
		// Water information sheet data types start with "WIS"...
		return CFS;
	}
	return UNKNOWN;
}

/**
Return the time steps to be displayed for a time series data type.  The time
step should ideally be in meas_type; however, some time series tables store
more than one time series data type and the time steps in meas_type are not appropriate for displays.
@return the time steps that are available for a time series data type.
@param hbdmi Reserved for future use.
@param data_type the time series data type, using time series data conventions
(e.g., use "StreamflowMax" rather than "Streamflow", which is ambiguous).
@param include_types A mask using DATA_TYPE_* indicating which data types to
include.  For example, for a tool that only wants to display station time
series, specify DATA_TYPE_STATION_ALL.  For a tool that wants to display only
reservoir structure time series, specify DATA_TYPE_STRUCTURE_RESERVOIR.  Types
can be combined if appropriate.  For this method, the inlude_types are only
used in cases where a data type is listed as both a station and structure (e.g.,
WellLevel) but the interval is different for each case (e.g., WellLevel
Irregular for station, WellLevel Day for structure).
*/
public static List<String> getTimeSeriesTimeSteps (	HydroBaseDMI hbdmi, String data_type, int include_types )
{	String Month = "Month";
	String Day = "Day";
	String Year = "Year";
	String Irregular = "Irregular";
	List<String> v = new Vector();
	// Alphabetize by data type, as much as possible...
	if ( data_type.equalsIgnoreCase("AdminFlow") ) {
		v.add ( Day );
		v.add ( Month );
		v.add ( Irregular );  // Real-time
	}
	else if ( data_type.equalsIgnoreCase("AdminFlowMax") ||
		data_type.equalsIgnoreCase("AdminFlowMin") ) {
		v.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("Agstats") ) {
		v.add ( Year );
	}
	else if ( data_type.startsWith("CropArea") ) {
		v.add ( Year );
	}
	else if ( data_type.equalsIgnoreCase("Battery") ) {
		v.add ( Irregular );
	}
	else if(data_type.equalsIgnoreCase("DivClass") || data_type.equalsIgnoreCase("DivTotal") ||
		data_type.equalsIgnoreCase("IDivClass") || data_type.equalsIgnoreCase("IDivTotal") ) {
		v.add ( Day );
		v.add ( Month );
		v.add ( Year );
	}
	else if ( data_type.equalsIgnoreCase("EvapPan") ) {
		v.add ( Day );
		v.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("DivComment") ) {
		v.add ( Year );
	}
	else if( data_type.equalsIgnoreCase("FrostDateL28S") || data_type.equalsIgnoreCase("FrostDateL32S") ||
		data_type.equalsIgnoreCase("FrostDateF32F") || data_type.equalsIgnoreCase("FrostDateF28F") ) {
		v.add ( Year );
	}
    else if ( data_type.equalsIgnoreCase("HumanPopulation") ) {
        v.add ( Year );
    }
    else if ( data_type.equalsIgnoreCase("LivestockHead") ) {
        v.add ( Year );
    }
	else if ( data_type.equalsIgnoreCase("NaturalFlow") ) {
		v.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("PoolElev") ) {
		v.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("Precip") ) {
		v.add ( Day );
		v.add ( Month );
		v.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("RelClass") || data_type.equalsIgnoreCase("RelTotal") ||
		data_type.equalsIgnoreCase("IRelClass") || data_type.equalsIgnoreCase("IRelTotal") ) {
		v.add ( Day );
		v.add ( Month );
		v.add ( Year );
	}
	else if ( data_type.equalsIgnoreCase("RelComment") ) {
		v.add ( Year );
	}
	else if ( data_type.equalsIgnoreCase("Release") ) {
		v.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("ResEOM") ) {
		v.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("ResEOY") ) {
		v.add ( Year );
	}
	else if ( data_type.equalsIgnoreCase("ResMeasElev") || data_type.equalsIgnoreCase ( "ResMeasEvap" ) ||
		data_type.equalsIgnoreCase ( "ResMeasFill" ) || data_type.equalsIgnoreCase ( "ResMeasRelease") ||
		data_type.equalsIgnoreCase ( "ResMeasStorage") ) {
		v.add ( Day );
	}
	else if ( data_type.equalsIgnoreCase("Snow") ) {
		// Snow accumulation - on the ground
		v.add ( Day );
		v.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("SnowCourseDepth") || data_type.equalsIgnoreCase("SnowCourseSWE") ) {
		// Although the data are stored in a monthly table, the values are generally recorded to the day
		v.add ( Day );
	}
	else if ( data_type.equalsIgnoreCase("Solar") ) {
		v.add ( Day );
	}
	else if ( data_type.equalsIgnoreCase("Stage") ) {
		v.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("Storage") ) {
		v.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("Streamflow") ) {
		v.add ( Day );
		v.add ( Month );
		//v.add ( RealTime );
		v.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("StreamflowMax") ||
		data_type.equalsIgnoreCase("StreamflowMin") ) {
		v.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("Temp") ) {
		v.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("TempMax") ) {
		v.add ( Day );
	}
	else if(data_type.equalsIgnoreCase("TempMean") || data_type.equalsIgnoreCase("TempMeanMax") ||
		data_type.equalsIgnoreCase("TempMeanMin") ) {
		v.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("TempMin") ) {
		v.add ( Day );
	}
	else if ( data_type.equalsIgnoreCase("VaporPressure") ) {
		v.add ( Day );
	}
	else if ( data_type.equalsIgnoreCase("WatTemp") ) {
		v.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("WellLevel") ) {
	    // Legacy, replaced by WellLevelElev
		if ( (include_types&DATA_TYPE_STRUCTURE_WELL) != 0 ) {
			v.add ( Day );
		}
		if ( (include_types&DATA_TYPE_STATION_WELL) != 0 ) {
			v.add ( Irregular );
		}
	}
	else if ( data_type.equalsIgnoreCase("WellLevelDepth")) {
        if ( (include_types&DATA_TYPE_STRUCTURE_WELL) != 0 ) {
            v.add ( Day );
        }
	}
    else if ( data_type.equalsIgnoreCase("WellLevelElev") ) {
        // Replaces legacy "WellLevel"
        if ( (include_types&DATA_TYPE_STATION_WELL) != 0 ) {
            v.add ( Irregular );
        }
        if ( (include_types&DATA_TYPE_STRUCTURE_WELL) != 0 ) {
            v.add ( Day );
        }
    }
	else if ( data_type.equalsIgnoreCase("Wind") ) {
		v.add ( Day );
	}
	else if ( data_type.regionMatches(true,0,"WIS",0,3) ) {
		// Water information sheet data types start with "WIS"...
		v.add ( Day );
	}
	return v;
}

/**
Indicate whether a time series data type is for CASS agricultural crop statistics.
@param hbdmi An instance of HydroBaseDMI.  The data type is checked to see
whether it is "CropAreaHarvested" or "CropAreaPlanted".
@param data_type A HydroBase data type.  If the data_type has a "-",
then the token after the dash is compared.
@return true if the data type is for agricultural crop statistics.
*/
public static boolean isAgriculturalCASSCropStatsTimeSeriesDataType ( HydroBaseDMI hbdmi, String data_type )
{	if ( data_type.indexOf("-") >= 0) {
		data_type = StringUtil.getToken(data_type,"-",0,1) ;
	}
	if ( data_type.equalsIgnoreCase("CropAreaHarvested") ||
		data_type.equalsIgnoreCase("CropAreaPlanted") ) {
		return true;
	}
	else {
	    return false;
	}
}

/**
Indicate whether a time series data type is for CASS agricultural livestock statistics.
@param hbdmi An instance of HydroBaseDMI.  The data type is checked to see whether it is "LivestockHead".
@param data_type A HydroBase data type.  If the data_type has a "-", then the token after the dash is compared.
@return true if the data type is for agricultural livestock statistics.
*/
public static boolean isAgriculturalCASSLivestockStatsTimeSeriesDataType ( HydroBaseDMI hbdmi, String data_type )
{	if ( data_type.indexOf("-") >= 0) {
		data_type = StringUtil.getToken(data_type,"-",0,1) ;
	}
	if ( data_type.equalsIgnoreCase("LivestockHead") ) {
		return true;
	}
	else {
	    return false;
	}
}

/**
Indicate whether a time series data type is for NASS agricultural crop statistics.
@param hbdmi An instance of HydroBaseDMI.  The data type is checked to see whether it is "CropArea" or "CropArea".
@param data_type A HydroBase data type.  If the data_type has a "-", then the token after the dash is compared.
@return true if the data type is for agricultural crop statistics.
*/
public static boolean isAgriculturalNASSCropStatsTimeSeriesDataType ( HydroBaseDMI hbdmi, String data_type )
{	if ( data_type.indexOf("-") >= 0) {
		data_type = StringUtil.getToken(data_type,"-",0,1) ;
	}
	if ( data_type.equalsIgnoreCase("CropArea") || data_type.equalsIgnoreCase("CropArea") ) {
		return true;
	}
	else {
	    return false;
	}
}

/**
Indicate whether a time series data type is for CUPopulation.
@param hbdmi An instance of HydroBaseDMI.  The data type is checked to see whether it is "HumanPopulation".
@param dataType A HydroBase data type.  If the data_type has a "-",
then the token after the dash is compared.
@return true if the data type is for CUPopulation.
*/
public static boolean isCUPopulationTimeSeriesDataType ( HydroBaseDMI hbdmi, String dataType )
{	if ( dataType.indexOf("-") >= 0) {
		dataType = StringUtil.getToken(dataType,"-",0,1).trim();
	}
	if ( dataType.equalsIgnoreCase("HumanPopulation") ) {
		return true;
	}
	else {
	    return false;
	}
}

/**
Indicate whether a time series data type is for a groundwater well time series.
@param hbdmi An instance of HydroBaseDMI. 
@param data_type A HydroBase data type.  Currently, the string is compared
against the hard-coded strings "WellLevel", "WellLevelElev", and "WellLevelDepth"
*/
public static boolean isGroundWaterWellTimeSeriesDataType (HydroBaseDMI hbdmi, String data_type )
{	if (data_type.equalsIgnoreCase("WellLevel") || data_type.equalsIgnoreCase("WellLevelDepth") ||
        data_type.equalsIgnoreCase("WellLevelElev") ) {
		return true;
	}
	return false;
}

/**
Indicate whether a time series data type is for irrigation summary time series.
@param hbdmi An instance of HydroBaseDMI.  The data type is checked to see
whether it is "CropAreaAllIrrigation", "CropAreaDrip", "CropAreaFlood", "CropAreaFurrow", or "CropAreaSprinkler".
@param data_type A HydroBase data type.  If the data_type has a "-", then the token after the dash is compared.
@return true if the data type is for irrigation summary time series.
*/
public static boolean isIrrigSummaryTimeSeriesDataType ( HydroBaseDMI hbdmi, String data_type )
{	if ( data_type.indexOf("-") >= 0) {
		data_type = StringUtil.getToken(data_type,"-",0,1) ;
	}
	if ( data_type.equalsIgnoreCase("CropAreaAllIrrigation") || data_type.equalsIgnoreCase("CropAreaDrip") ||
		data_type.equalsIgnoreCase("CropAreaFlood") || data_type.equalsIgnoreCase("CropAreaFurrow") ||
		data_type.equalsIgnoreCase("CropAreaSprinkler") ) {
		return true;
	}
	else {
	    return false;
	}
}

/**
Indicate whether a time series data type is for a station.
@param hbdmi An instance of HydroBaseDMI.  The global distinct MeasType data from the instance are checked.
@param data_type A HydroBase data type.  If it matches the data type in
meas_type.meas_type, true will be returned.  If the data_type has a "-", then
the token before the dash is compared.
*/
public static boolean isStationTimeSeriesDataType (	HydroBaseDMI hbdmi, String data_type )
{	if ( data_type.indexOf("-") >= 0) {
		data_type = StringUtil.getToken(data_type,"-",0,0) ;
	}
	List global_mt = hbdmi.getMeasType();
	int size = global_mt.size();
	HydroBase_MeasType mt;
	for ( int i = 0; i < size; i++ ) {
		mt = (HydroBase_MeasType)global_mt.get(i);
		if ( data_type.equalsIgnoreCase(mt.getMeas_type()) ) {
			return true;
		}
	}
	return false;
}

/**
Indicate whether a time series data type is for a structure that has SFUT (one of the "Class" data types).
@param hbdmi An instance of HydroBaseDMI.  The global distinct StructMeasType data from the instance are checked.
@param data_type A HydroBase data type.  If it matches the data type in
struct_meas_type.meas_type, true will be returned.  If the data_type has a "-",
then the token before the dash is compared.
*/
public static boolean isStructureSFUTTimeSeriesDataType(HydroBaseDMI hbdmi, String data_type )
{	if ( data_type.indexOf("-") >= 0) {
		data_type = StringUtil.getToken(data_type,"-",0,0) ;
	}
	if ( StringUtil.indexOfIgnoreCase(data_type,"Class",0) >= 0 ) {
		return true;
	}
	return false;
}

/**
Indicate whether a time series data type is for a structure.  The result is
inclusive of isStructureSFUTTimeSeriesDataType().  In other words, call the
isStructureSFUTTimeSeriesDataType() first to match SFUT data types and then call this more general method.
@param hbdmi An instance of HydroBaseDMI.  The global distinct StructMeasType
data from the instance are checked.
@param data_type A HydroBase data type.  If it matches the data type in struct_meas_type.meas_type,
true will be returned.  If the data_type has a "-", then the token before the dash is compared.
*/
public static boolean isStructureTimeSeriesDataType ( HydroBaseDMI hbdmi, String data_type )
{	if ( data_type.indexOf("-") >= 0) {
		data_type = StringUtil.getToken(data_type,"-",0,0) ;
	}
	List<HydroBase_StructMeasType> global_mt = hbdmi.getStructMeasType();
	int size = global_mt.size();
	HydroBase_StructMeasType mt;
	// Check the list of struct_meas_type.meas_type...
	for ( int i = 0; i < size; i++ ) {
		mt = global_mt.get(i);
		if ( data_type.equalsIgnoreCase(mt.getMeas_type()) ) {
			return true;
		}
	}
	return false;
}

/**
Indicate whether a time series data type is for a WIS time series.
@param hbdmi An instance of HydroBaseDMI. 
@param data_type A HydroBase data type.  Currently, the string is compared
against the hard-coded strings "WISDeliveryFlow", "WISPriorityDiversion", "WISDeliveryDiversion",
"WISGainLoss", "WISNaturalFlow", "WISPointFlow", "WISRelease", "WISTribNaturalFlow", "WISTribDeliveryFlow".
*/
public static boolean isWISTimeSeriesDataType (	HydroBaseDMI hbdmi, String data_type )
{	if ( data_type.equalsIgnoreCase("WISPointFlow") ||
		data_type.equalsIgnoreCase("WISNaturalFlow") ||
		data_type.equalsIgnoreCase("WISDeliveryFlow") ||
		data_type.equalsIgnoreCase("WISGainLoss") ||
		data_type.equalsIgnoreCase("WISPriorityDiversion") ||
		data_type.equalsIgnoreCase("WISDeliveryDiversion") ||
		data_type.equalsIgnoreCase("WISRelease") ||
		data_type.equalsIgnoreCase("WISTribNaturalFlow") ||
		data_type.equalsIgnoreCase("WISTribDeliveryFlow") ) {
		return true;
	}
	return false;
}

/**
Returns the String name for the diversion source abbreviation using global
data.  This is the "S" in SFUT.  Return an empty string if not found.<p>
@return the String name for the diversion source abbreviation.  This is the
"S" in SFUT.  Return an empty string if not found.
@param source Source abbreviation.
*/
public static String lookupDiversionCodingSource ( String source )
{
	if ( source == null ) {
		return "";
	}
	else if ( source.equals ( "1" ) ) {
		return "NATURAL STREAM FLOW";
	}
	else if ( source.equals ( "2" ) ) {
		return "RESERVOIR STORAGE";
	}
	else if ( source.equals ( "3" ) ) {
		return "GROUND WATER (WELLS)";
	}
	else if ( source.equals ( "4" ) ) {
		return "TRANSBASIN";
	}
	else if ( source.equals ( "5" ) ) {
		return "NON-STREAM SOURCES";
	}
	else if ( source.equals ( "6" ) ) {
		return "COMBINED";
	}
	else if ( source.equals ( "7" ) ) {
		return "TRANSDISTRICT";
	}
	else if ( source.equals ( "8" ) ) {
		return "RE-USED";
	}
	else if ( source.equals ( "9" ) ) {
		return "MULTIPLE";
	}
	else if ( source.equalsIgnoreCase( "R" ) ) {
		return "REMEASURED AND REDIVERTED";
	}
	else {
	    return "";
	}
}

/**
Returns the String name for the diversion type abbreviation in global data.  
This is the  "T" in SFUT.  Return an empty string if not found.
@return the String name for the diversion type abbreviation.  This is the
"T" in SFUT.  Return an empty string if not found.
@param type Type abbreviation.
*/
public static String lookupDiversionCodingType ( String type )
{
	if ( type == null ) {
		return "";
	}
	else if ( type.equals ( "0" ) ) {
		return "ADMINISTRATIVE RECORD ONLY";
	}
	else if ( type.equals ( "1" ) ) {
		return "EXCHANGE";
	}
	else if ( type.equals ( "2" ) ) {
		return "TRADE";
	}
	else if ( type.equals ( "3" ) ) {
		return "CARRIER";
	}
	else if ( type.equals ( "4" ) ) {
		return "ALTERNATIVE POINT OF DIVERSION";
	}
	else if ( type.equals ( "5" ) ) {
		return "RE-USED";
	}
	else if ( type.equals ( "6" ) ) {
		return "REPLACEMENT TO RIVER";
	}
	else if ( type.equals ( "7" ) ) {
		return "RELEASED TO RIVER";
	}
	else if ( type.equals ( "8" ) ) {
		return "RELEASED TO SYSTEM";
	}
	else if ( type.equals ( "9" ) ) {
		return "USER-SUPPLIED INFORMATION";
	}
	else if ( type.equalsIgnoreCase( "A" ) ) {
		return "AUGMENTED";
	}
	else if ( type.equalsIgnoreCase( "G" ) ) {
		return "GEOTHERMAL";
	}
	else if ( type.equalsIgnoreCase( "S" ) ) {
		return "RESERVOIR SUBSTITUTION";
	}
	else {
	    return "";
	}
}

/**
Lookup the water division for a district.
@param dmi HydroBaseDMI instance, which will have as global data water district information.
@param wd water district to look up.
@return the division for the district, or -1 if not found. 
*/
public static int lookupDivisionForWaterDistrict ( HydroBaseDMI dmi, int district )
{
	List<HydroBase_WaterDistrict> wdList = dmi.getWaterDistricts();
	int wdListSize = 0;
	if ( wdList != null ) {
		wdListSize = wdList.size();
	}
	HydroBase_WaterDistrict wd;
	for ( int i = 0; i < wdListSize; i++ ) {
		wd = wdList.get(i);
		if ( wd.getWD() == district ) {
			return wd.getDiv();
		}
	}
	return -1;
}

/**
Lookup a HydroBaseDMI instance using the input name.  This is useful, for example, when finding a HydroBaseDMI
instance matching at time series identifier that includes the input name.
@return the HydroBaseDMI instance with an input name that matches the requested
input name.  The first match is found.
@param hydroBaseDMIList List of HydroBaseDMI to search.
@param inputName HydroBaseDMI input name to find.
*/
public static HydroBaseDMI lookupHydroBaseDMI ( List<HydroBaseDMI> hydroBaseDMIList, String inputName )
{	if ( hydroBaseDMIList == null ) {
		return null;
	}
	for ( HydroBaseDMI dmi : hydroBaseDMIList ) {
		if ( dmi == null ) {
			continue;
		}
		if ( dmi.getInputName().equalsIgnoreCase(inputName) ) {
			return dmi;
		}
	}
	return null;
}

/**
Parse an SFUT string into its parts.  The string to parse has the format
"S: F: U: T: G:" where data fields may be present after each :.  The G: format
was added in HydroBase 20061003.  The F: in this version is a 7-digit WDID
indicating the "from" structure.  In earlier versions, it is the ID part of the
WDID, and the WDID is the same as the structure itself (can only record data movement within a water district).
@return An array of strings, each of which is one of the SFUT fields, without
the leading "S:", "F:", "U:", "T:" or "G:".  The parts are guaranteed to be non-null, but may be blank strings.
@param sfut_string An SFUT string to parse.
@throws Exception if an error occurs.
*/
public static String[] parseSFUT ( String sfut_string )
throws Exception {	
	String sfut_parts[] = new String[5];
	sfut_parts[0] = "";
	sfut_parts[1] = "";
	sfut_parts[2] = "";
	sfut_parts[3] = "";
	sfut_parts[4] = "";
	String routine = "HydroBase_Util.parseSFUT";

	if ( (sfut_string.indexOf("S:") >= 0) && (sfut_string.indexOf("F:") >= 0) &&
		(sfut_string.indexOf("U:") >= 0) && (sfut_string.indexOf("T:") >= 0) ) {
		// Full-style SFUT string like:  S:1 F:xxx U:xxx T:xxx G:xxx
		// Do not check for G: above because it is being phased in.  First break by spaces..
		String sfut0 = sfut_string + " ";
		List sfut = StringUtil.breakStringList ( sfut0, " ", StringUtil.DELIM_SKIP_BLANKS );
		int sfut_size = sfut.size();
		if ( sfut_size < 4 ) {
			Message.printWarning ( 2, routine, "SFUT must have at least 4 parts" );
			return sfut_parts;
		}
		// Now break each field by ':' and use the second part.  Do not handle more than five parts.
		List<String> sfut2;
		for ( int is = 0; (is < sfut_size) && (is < 5); is++ ) {
			sfut2 = StringUtil.breakStringList ( sfut.get(is) + ":", ":", 0 );
			sfut_parts[is] = sfut2.get(1);
			if ( sfut_parts[is] == null ) {
				sfut_parts[is] = "";
			}
		}
	}
	else {
	    // Assume the simple format of: xx:xx:xx:xx
		String sfut0 = sfut_string + ":";
		List<String> sfut = StringUtil.breakStringList ( sfut0, ":", 0 );
		if ( sfut.size() < 4 ) {
			Message.printWarning ( 2, routine, "SFUT must have 4 parts" );
			return sfut_parts;
		}
		sfut_parts[0] = sfut.get(0);
		sfut_parts[1] = sfut.get(1);
		sfut_parts[2] = sfut.get(2);
		sfut_parts[3] = sfut.get(3);
		if ( sfut.size() > 4 ) {
			// G: at end, for database after 20061003
			sfut_parts[4] = sfut.get(4);
		}
	}
	return sfut_parts;
}

/**
Reads configuration props from the given configuration file.
@param configurationFile the path to the CDSS configuration file to read.
@return a PropList generated from the values in the configuration file.
*/
public static PropList readConfiguration(String configurationFile) 
throws Exception {
	String routine = "HydroBase_Util.readConfiguration";

	if (!IOUtil.fileExists(configurationFile)) {
		throw new Exception("Configuration file \"" + configurationFile + "\" does not exist.");
	}

	PropList configurationProps = new PropList("ConfigurationProps");
	configurationProps.setPersistentName(configurationFile);
	try {
		configurationProps.readPersistent();
	}
	catch (Exception e) {
		Message.printWarning(2, routine, 
			"Configuration Properties could not be read from file: " + "'" + configurationFile + "'.");
		Message.printWarning(3, routine, e);
	}

	return configurationProps;
}

/**
Read a list of objects that contain time series header information.  This is used, for example, to populate the
time series list area of TSTool and to get a list of time series for the TSTool readHydroBase(Where...) command.
@param hbdmi The HydroBaseDMI instance to use for queries.
@param data_type The data type for time series, either from the TSTool
data type choice for HydroBase, or a simple string.
@param time_step The time step for time series, either from the TSTool
time step choice, or a simple string "Day", "Month", etc.
@param ifp An InputFilter_JPanel instance to retrieve where clause information from.
@return a list of objects for the data type.
@exception if there is an error reading the data.
*/
public static List readTimeSeriesHeaderObjects ( HydroBaseDMI hbdmi, String data_type, String time_step,
	InputFilter_JPanel ifp )
throws Exception
{	return readTimeSeriesHeaderObjects ( hbdmi, data_type, time_step, ifp, null );
}

/**
Read a list of objects that contain time series header information.  This is used, for example, to populate the
time series list area of TSTool and to get a list of time series for the TSTool readHydroBase(Where...) command.
@param hbdmi The HydroBaseDMI instance to use for queries.
@param data_type The data type for time series, either from the TSTool
data type choice for HydroBase, or a simple string.
@param time_step The time step for time series, either from the TSTool
time step choice, or a simple string "Day", "Month", etc.
@param ifp An InputFilter_JPanel instance to retrieve where clause information from.
@param grlimits GRLimits indicating the extent of the map to query.  Specify as null to query all data.
@return a list of objects for the data type.
@exception if there is an error reading the data.
*/
public static List readTimeSeriesHeaderObjects ( HydroBaseDMI hbdmi, String data_type, String time_step,
	InputFilter_JPanel ifp, GRLimits grlimits )
throws Exception
{	String routine = "HydroBase_Util.readTimeSeriesHeaderObjects", message;
	List tslist = null;
	String [] hb_mt = HydroBase_Util.convertToHydroBaseMeasType ( data_type, time_step );
	String meas_type = hb_mt[0];
	String vax_field = hb_mt[1];
	String hbtime_step = hb_mt[2];
	String dataSource = null;
	int size = 0;
	
	Message.printStatus(2, routine, "Reading HydroBase time series header objects for HydroBase meas_type=\"" +
	       meas_type + "\", HydroBase timeStep=\"" + hbtime_step + "\".");

	// Get the specific where clauses from the input filter...
	if ( (ifp instanceof HydroBase_GUI_StationGeolocMeasType_InputFilter_JPanel) ||
		(ifp instanceof HydroBase_GUI_StructureGeolocStructMeasType_InputFilter_JPanel) ||
		(ifp instanceof HydroBase_GUI_AgriculturalCASSCropStats_InputFilter_JPanel) ||
		(ifp instanceof HydroBase_GUI_AgriculturalCASSLivestockStats_InputFilter_JPanel) ||
		(ifp instanceof HydroBase_GUI_AgriculturalNASSCropStats_InputFilter_JPanel) ||
		(ifp instanceof HydroBase_GUI_CUPopulation_InputFilter_JPanel) ||
		(ifp instanceof HydroBase_GUI_StructureIrrigSummaryTS_InputFilter_JPanel) ||
		(ifp instanceof HydroBase_GUI_SheetNameWISFormat_InputFilter_JPanel) ) {
	}
	// Special cases first and then general lists...
	if ( HydroBase_Util.isAgriculturalCASSCropStatsTimeSeriesDataType ( hbdmi, data_type ) ) {
		try {
		    tslist = hbdmi.readAgriculturalCASSCropStatsList (
				ifp,	// From input filter
				null,		// county
				null,		// commodity
				null,		// practice
				null,		// date1
				null,		// date2,
				true );		// Distinct
		}
		catch ( Exception e ) {
			message = "Error getting CASS agricultural crop time series list from HydroBase (" + e + ").";
			Message.printWarning ( 3, routine, message );
			Message.printWarning ( 3, routine, e );
			throw new Exception ( message );
		}
	}
	else if(HydroBase_Util.isAgriculturalCASSLivestockStatsTimeSeriesDataType ( hbdmi,data_type ) ) {
		try {
		    tslist = hbdmi.readAgriculturalCASSLivestockStatsList (
				ifp,	// From input filter
				null,		// county
				null,		// commodity
				null,		// type
				null,		// date1
				null,		// date2,
				true );		// Distinct
		}
		catch ( Exception e ) {
			message = "Error getting CASS agricultural livestock time series list from HydroBase (" + e + ").";
			Message.printWarning ( 3, routine, message );
			Message.printWarning ( 3, routine, e );
			throw new Exception ( message );
		}
	}
	else if(HydroBase_Util.isCUPopulationTimeSeriesDataType ( hbdmi,data_type ) ) {
		try {
		    tslist = hbdmi.readCUPopulationList (
				ifp,	// From input filter
				null,		// county
				null,		// commodity
				null,		// type
				null,		// date1
				null,		// date2,
				true );		// Distinct
		}
		catch ( Exception e ) {
			message = "Error getting CU Population time series list from HydroBase (" + e + ").";
			Message.printWarning ( 3, routine, message );
			Message.printWarning ( 3, routine, e );
			throw new Exception ( message );
		}
	}
	else if(HydroBase_Util.isIrrigSummaryTimeSeriesDataType ( hbdmi, data_type ) ) {
		try {
		    // Structure GIS crop areas...
			tslist = hbdmi.readStructureIrrigSummaryTSList (
				ifp,
				null,	// orderby
				-999,	// structure_num
				-999,	// wd
				-999,	// id
				null,	// str_name
				null,	// landuse
				null,	// start
				null,	// end
				true);	// distinct
			List v = new Vector();
			int size2 = tslist.size();
			HydroBase_StructureView view = null;
			for (int i = 0; i < size2; i++) {
				view = (HydroBase_StructureView)tslist.get(i);
				v.add(new HydroBase_StructureIrrigSummaryTS(view));
			}
			tslist = v;
		}
		catch ( Exception e ) {
			message = "Error getting irrigation summary crop area list from HydroBase (" + e + ").";
			Message.printWarning ( 3, routine, message );
			Message.printWarning ( 3, routine, e );
			throw new Exception ( message );
		}
	}
	else if ( HydroBase_Util.isAgriculturalNASSCropStatsTimeSeriesDataType ( hbdmi, data_type ) ) {
		try {
		    // NASS crop statistics...
			tslist = hbdmi.readAgriculturalNASSCropStatsList (
				ifp,	// From input filter
				null,		// county
				null,		// commodity
				null,		// date1
				null,		// date2,
				true );		// Distinct
		}
		catch ( Exception e ) {
			message = "Error getting NASS crop area list from HydroBase (" + e + ").";
			Message.printWarning ( 3, routine, message );
			Message.printWarning ( 3, routine, e );
			throw new Exception ( message );
		}
	}
	else if(HydroBase_Util.isStationTimeSeriesDataType(hbdmi, meas_type)){
		try {
		    // Note multiple vax_field and data sources can be returned below.  Only specify the
			// vax_field when it is necessary to do so to get the proper list back...
			tslist = hbdmi.readStationGeolocMeasTypeList(ifp, null, grlimits, meas_type, hbtime_step,
				vax_field, dataSource, null, false);
			// Convert HydroBase data to make it more consistent with how TSTool handles time series...
			if ( tslist != null ) {
				size = tslist.size();
			}
			if ( size == 0 ) {
				Message.printWarning ( 3, routine, "Found 0 time series for meas_type=\""+ meas_type +
				"\" time_step=\""+ hbtime_step + "\" vax_field=\""+ vax_field + "\" data_source=\""+ dataSource +"\"");
			}
			HydroBase_StationView view = null;
			String data_units = HydroBase_Util.getTimeSeriesDataUnits ( hbdmi, data_type, time_step );
//			if (hbdmi.useStoredProcedures()) {
			List v = tslist;
			tslist = new Vector();
			for ( int i = 0; i < size; i++ ) {
				view = (HydroBase_StationView)v.get(i);
				// Set to the value used in TSTool...
				if ( view.getVax_field().length() > 0 ){
					view.setMeas_type( data_type + "-" + view.getVax_field() );
				}
				else {
				    view.setMeas_type ( data_type);
				}
				view.setTime_step ( time_step );
				view.setData_units ( data_units );
				// TODO (JTS - 2005-04-06) THIS SHOULDn"T BE DONE ONCE WE CAN RECOMPILE TSTOOL!!!				
				tslist.add(new HydroBase_StationGeolocMeasType(view));
			}
		}
		catch ( Exception e ) {
			message = "Error getting station time series list from HydroBase (" + e + ").";
			Message.printWarning ( 3, routine, message );
			Message.printWarning ( 3, routine, e );
			throw new Exception ( message );
		}
	}
	else if(HydroBase_Util.isStructureTimeSeriesDataType(hbdmi, meas_type)){
		try {
		    // Note multiple SFUT and data sources can be returned below...
			tslist = hbdmi.readStructureGeolocStructMeasTypeList(ifp, meas_type, hbtime_step);
			// Convert HydroBase data to make it more consistent with how TSTool handles time series...
			if ( tslist != null ) {
				size = tslist.size();
			}
			HydroBase_StructMeasTypeView view;
			String data_units = HydroBase_Util.getTimeSeriesDataUnits (hbdmi, data_type, time_step );
			List v = tslist;
			tslist = new Vector();			
			for ( int i = 0; i < size; i++ ) {
				view = (HydroBase_StructMeasTypeView)v.get(i);
				// Set to the value used in TSTool...
				if ( view.getIdentifier().length() > 0){
					// Merged SFUT...
					view.setMeas_type(data_type + "-" + view.getIdentifier());
				}
				else {
				    view.setMeas_type( data_type);
				}
				view.setTime_step(time_step);
				view.setData_units ( data_units );
				// TODO (JTS - 2005-04-06) THIS SHOULDn"T BE DONE ONCE WE CAN RECOMPILE TSTOOL!!!
				tslist.add(new HydroBase_StructureGeolocStructMeasType(view));
			}
			if ( (data_type.equals("WellLevel") || data_type.equals("WellLevelDepth") || data_type.equals("WellLevelElev")) &&
			    time_step.equalsIgnoreCase("Day")){
				// Add the common identifiers to the normal data.  This is a work-around until HydroBase is redesigned.
				// TODO SAM 2004-01-13
				HydroBase_Util.addAlternateWellIdentifiers ( hbdmi, tslist );
			}
		}
		catch ( Exception e ) {
			message = "Error getting structure time series list from HydroBase (" + e + ").";
			Message.printWarning ( 3, routine, message );
			Message.printWarning ( 3, routine, e );
			throw new Exception ( message );
		}
	}
	else if (meas_type.equalsIgnoreCase("WellLevel")|| data_type.equals("WellLevelDepth") ||
	    data_type.equals("WellLevelElev")) {
		try {
			tslist = hbdmi.readGroundWaterWellsMeasTypeList(ifp, null);
			// Convert HydroBase data to make it more consistent with how TSTool handles time series...
			if ( tslist != null ) {
				size = tslist.size();
			}
			HydroBase_GroundWaterWellsView view;
			String data_units = HydroBase_Util.getTimeSeriesDataUnits ( hbdmi, data_type, time_step );

			List v = tslist;
			tslist = new Vector();			
			for ( int i = 0; i < size; i++ ) {
				view = (HydroBase_GroundWaterWellsView)v.get(i);
				// Set to the value used in TSTool...
			    view.setMeas_type( data_type);
				view.setTime_step(time_step);
				view.setData_units ( data_units );
				tslist.add(view);
			}
		}
		catch ( Exception e ) {
			message = "Error getting well level time series list from HydroBase (" + e + ").";
			Message.printWarning ( 3, routine, message );
			Message.printWarning ( 3, routine, e );
			throw new Exception ( message );
		}
	}
	// XJTSX
	else if ( HydroBase_Util.isWISTimeSeriesDataType ( hbdmi, data_type) ) {
		try {
		    // Use the selected sheet name from the input filter, if present...
			tslist = hbdmi.readWISSheetNameWISFormatListDistinct(ifp);
			int listSize = tslist.size();
			List v = new Vector();
			for (int i = 0; i < listSize; i++) {
				v.add(tslist.get(i));
			}
			tslist = v;
		}
		catch ( Exception e ) {
			message = "Error getting WIS time series list from HydroBase.";
			Message.printWarning ( 2, routine, message );
			Message.printWarning ( 2, routine, e );
			throw new Exception ( message );
		}
	}
	return tslist;
}

/**
Set the preferred WDID length, used when formatting WDIDs from the WD and ID
parts.  The first two characters will always be for the WD.
@param the preferred WDID length.
*/
public static void setPreferredWDIDLength ( int preferred_WDID_length )
{	__preferred_WDID_length = preferred_WDID_length;
}

}