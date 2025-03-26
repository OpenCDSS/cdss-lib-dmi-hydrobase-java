// HydroBase_Util - static utility methods for HydroBase

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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
Data types for getTimeSeriesDataTypes().
For example, this is used in StateView to indicate that only station data types are being requested for the station view.
In this case, real-time well levels should be available,
but not historical well levels (because historic are associated with WDIDs).
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
 * Legacy missing integer value.
 */
public static int MISSING_INT = -999;

/**
The preferred length for WDID strings.
For example, TSTool uses this to know how to format WDIDs after interactively querying from HydroBase.
*/
private static int __preferred_WDID_length = 7;

public final static String LOCAL = "local";

/**
For a list of HydroBase_StructureGeolocStructMeasType, use the WD,
ID in each data object to query the unpermitted_wells table and then set the common_id in
the objects to the USGS or USBR identifier from unpermitted_wells.
This is a work-around until HydroBase is redesigned to better handle well data.
*/
public final static void addAlternateWellIdentifiers ( HydroBaseDMI dmi, List<HydroBase_StructureGeolocStructMeasType> list ) {
	HydroBase_StructureGeolocStructMeasType data;
	int size = 0;
	if ( list != null ) {
		size = list.size();
	}

	HydroBase_GroundWaterWellsView uws;
	for ( int i = 0; i < size; i++ ) {
		data = list.get(i);
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
			// Ignore.
		}
	}
}

/**
Add data flag descriptions to daily structure time series.
These will then be available for annotation of data flags.
@param ts time series to update.
*/
public static void addDailyTSStructureDataFlagDescriptions ( TS ts ) {
    ts.addDataFlagMetadata(new TSDataFlagMetadata("*", "Measured/observed."));
    ts.addDataFlagMetadata(new TSDataFlagMetadata("U", "User-supplied."));
    // TODO SAM 2010-08-16 What about E and other values?
}

/**
Adjust the struct_meas_type SFUT identifier for the database version as follows.
If the database is at least the 20061003, then the new G: is included in the identifier in the database,
and the F: if specified is 7-digits padded to zeros.
Requests using the old SFUT with no G: are updated as follows.
<ol>
<li>	" G:" is added to the end of the identifier.</li>
<li>	The F: (if specified as non-blank) is updated to a 7-digit value padded with zeros.</li>
</ol>
If the database is earlier than 20061003, then the new G: is NOT included in the identifier in the database,
and the F: if specified as the structure number in the district of the structure.
Requests using the new SFUT(G) are updated as follows.
<ol>
<li>	" G:" is removed from the end of the identifier.
		An attempt to strip a non-blank value will result in an exception.</li>
<li>	The F: (if specified as non-blank) is updated to only the structure identifier.
		An attempt to strip a WD that is different from the wd passed to this method will result in an exception.</li>
</ol>
@param dmi HydroBaseDMI instance that is being used for data requests.
@param wd Water district identifier, needed to adjust the F: to/from 7-digits.
@param id Structure identifier.
@param identifier The SFUT(G) identifier to adjust.
@returns An SFUT(G) identifier that can be used to query data in the database version that is being used.
*/
public static String adjustSFUTForHydroBaseVersion ( HydroBaseDMI dmi, int wd, int id, String identifier )
throws Exception {
	if ( (identifier == null) || (identifier.length() == 0) ) {
		return identifier;
	}
	String identifierNew = identifier;
	boolean needToAdjust = false;	// True if need to adjust SFUT string.
	String G = "";
	String F = "";
	String[] SFUT_parts = HydroBase_Util.parseSFUT( identifier );
	if ( dmi.isVersionAtLeast(HydroBaseDMI.VERSION_20061003) ) {
		// Version that requires G: and 7-digit F: in SFUT.
		if ( identifier.indexOf("G:") < 0 ){
			// Requested identifier does not have G: so add it as blank.
			needToAdjust = true;
		}
		if ( SFUT_parts[1].length() > 0 ) {
			// Have a non-blank F: so make sure it is 7-digits.
			F = SFUT_parts[1];	// Default is use all 7
			if ( F.length() < 7 ) {
				// Reformat to be 7 characters.
				// Assume that the WDID for the F: is the same as that passed in to this method.
				// Make sure that the overall length is 7.
				needToAdjust = true;
				F = HydroBase_WaterDistrict.formWDID(7,""+wd,F);
			}
		}
		if ( needToAdjust ) {
			// TODO SAM 2007-02-26 Might need an SFUT object to handle its own parse/toString().
			// Reformat the identifier to include G: and 7-digit FROM.
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
			needToAdjust = true;	// Simple cut - omit G below.
			// Have a G: OK to cut off if blank but problems otherwise.
			if ( SFUT_parts[4].length() > 0 ) {
				throw new Exception ( "New SFUT with non-blank G: (" + identifier +
						") cannot be used with old database.");
			}
		}
		F = SFUT_parts[1];	// Default is to use F as is.
		if ( F.length() >= 7 ) {
			// Reformat to just be the ID.  In this case, the WD part must agree with the WD passed in to this method.
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
			// Reformat the identifier to exclude G: and not use 7-digit FROM.
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
TODO (JTS - 2006-05-25) Combine with the other buildLocation() method, overload one,
because there's a lot of duplicate code.
*/
public final static String buildLocation(String pm, int ts, String tdir,
int rng, String rdir, int sec, String seca, String q160, String q40, String q10) {
	if (DMIUtil.isMissing(pm)) {
		pm = " ";
	}

	String tsS = "  ";
	if (!DMIUtil.isMissing(ts) && !HydroBase_Util.isMissing(ts)) {
		tsS = StringUtil.formatString(ts, "%2d");
	}

	if (DMIUtil.isMissing(tdir)) {
		tdir = " ";
	}

	String rngS = "   ";
	if (!DMIUtil.isMissing(rng) && !HydroBase_Util.isMissing(rng)) {
		rngS = StringUtil.formatString(rng, "%3d");
	}

	if (DMIUtil.isMissing(rdir)) {
		rdir = " ";
	}

	String secS = "  ";
	if (!DMIUtil.isMissing(sec) && !HydroBase_Util.isMissing(sec)) {
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
TODO (JTS - 2006-05-25) Combine with the other buildLocation() method, overload one,
because there's a lot of duplicate code.
*/
public final static String buildLocation(String pm, String ts, String tdir,
String rng, String rdir, int sec, String seca, String q160, String q40, String q10) {
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
	if (!DMIUtil.isMissing(sec) && !HydroBase_Util.isMissing(sec)) {
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
TODO (JTS - 2006-05-25) Combine with the other buildLocation() method, overload one,
because there's a lot of duplicate code.
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
	if (!DMIUtil.isMissing(sec) && !HydroBase_Util.isMissing(sec)) {
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
public final static String convertFromHydroBaseTimeStep ( String hbTimestep ) {
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
Convert a data type and data interval from general time series notation to HydroBase meas_type,
struct_meas_type table convention.
For example, "StreamflowMax", "Month" will be converted to "Streamflow", "Monthly".
Specific actions are taken for station and structure data and other data types
(AgStats, irrig_ts_summary, and WIS) pass through the GUI values.
Some data types have the same output value as the input because HydroBase does not internally use the string for queries
(table design is hard-coded for data type).
@param dataType a data type from an application such as TSTool (e.g., "StreamflowMax"),
corresponding to an entry in the HydroBase meas_type or struct_meas_type table.
@param interval a generic time series notation interval (e.g., "Day", "Month").
@return The HydroBase equivalent meas_type data, as an array.  The [0] position will contain the data type.
The [1] position will contain a sub data type, if appropriate, or an empty string.
The [2] position will contain the data interval.
It is possible that the HydroBase meas_type will correspond to multiple data_type because of the
HydroBase design storing more than one value per record.
*/
public final static String [] convertToHydroBaseMeasType ( String dataType, String interval ) {
	String [] hb = new String[3];
	hb[0] = "";
	hb[1] = "";
	hb[2] = "";
	// General conversion on interval.
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
		// This may need to be reset below.
		hb[2] = "Real-time";
	}
	else if ( interval.equalsIgnoreCase("RealTime") ) {
		hb[2] = "Real-time";
	}

	// Conversions based on the data type.

	String sub_data_type = "";
	if ( dataType.indexOf("-") > 0 ) {
		sub_data_type = StringUtil.getToken ( dataType, "-", 0, 1 );
		dataType = StringUtil.getToken ( dataType, "-", 0, 0 );
	}

	// Order by data types and group when multiple time series are in the same table.

	if(dataType.equalsIgnoreCase("AdminFlow") || dataType.equalsIgnoreCase("AdminFlowMax") ||
		dataType.equalsIgnoreCase("AdminFlowMin") ) {
		// All these match the AdminFlow meas_type.
		hb[0] = "AdminFlow";
		if ( interval.equalsIgnoreCase("RealTime") || interval.equalsIgnoreCase("Irregular") ) {
			hb[0] = "RT_rate";
			if ( sub_data_type.length() > 0 ) {
				// Assume that the vax_field is accurate.
				hb[1] = sub_data_type;
			}
			else {
			    hb[1] = "DISCHRG";	// Assume this.
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
        // Pass through - used with NASS crop statistics - specific handling in database.
        hb[0] = dataType;
        hb[1] = "";
        hb[2] = "Annual";
    }
    else if ( dataType.equalsIgnoreCase("CropAreaAllIrrigation") ||
        dataType.equalsIgnoreCase("CropAreaDrip") ||
        dataType.equalsIgnoreCase("CropAreaFlood") ||
        dataType.equalsIgnoreCase("CropAreaFurrow") ||
        dataType.equalsIgnoreCase("CropAreaSprinkler") ) {
        // Pass through - used with irrigation summary time series - specific handling in database.
        hb[0] = dataType;
        hb[1] = "";
        hb[2] = "Annual";
    }
    else if ( dataType.equalsIgnoreCase("CropAreaHarvested") ||
        dataType.equalsIgnoreCase("CropAreaPlanted")) {
        // Pass trough - used with CASS statistics - specific handling in database.
        hb[0] = dataType;
        hb[1] = "";
        hb[2] = "Annual";
    }
	else if ( dataType.equalsIgnoreCase("DivClass") ) {
		hb[0] = "DivClass";
		if ( sub_data_type.length() > 0 ) {
			// Assume that the SFUT is accurate.
			hb[1] = sub_data_type;
		}
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly diversions annual.
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount.
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
			// HydroBase calls monthly diversions annual.
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount.
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
       // Pass through - special table in HydroBase so just return what TSTool uses.
       hb[0] = dataType;
       hb[2] = "Annual";
    }
	else if ( dataType.equalsIgnoreCase("IDivClass") ) {
		hb[0] = "IDivClass";
		if ( sub_data_type.length() > 0 ) {
			// Assume that the SFUT is accurate.
			hb[1] = sub_data_type;
		}
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly diversions annual.
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount.
			hb[2] = "Annual";
		}
	}
	else if ( dataType.equalsIgnoreCase("IDivTotal") ) {
		hb[0] = "IDivTotal";
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly diversions annual.
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount.
			hb[2] = "Annual";
		}
	}
	else if ( dataType.equalsIgnoreCase("IRelClass") ) {
		hb[0] = "IRelClass";
		if ( sub_data_type.length() > 0 ) {
			// Assume that the SFUT is accurate.
			hb[1] = sub_data_type;
		}
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly releases annual.
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount.
			hb[2] = "Annual";
		}
	}
	else if ( dataType.equalsIgnoreCase("IRelTotal") ) {
		hb[0] = "IRelTotal";
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly releases annual.
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount.
			hb[2] = "Annual";
		}
	}
    else if ( dataType.equalsIgnoreCase("LivestockHead") ) {
        // Used with CASS livestock statistics - specific handling in database.
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
			// Assume that the SFUT is accurate.
			hb[1] = sub_data_type;
		}
		if ( interval.equalsIgnoreCase("Month") ) {
			// HydroBase calls monthly releases annual.
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount.
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
			// HydroBase calls monthly releases annual.
			hb[2] = "Annual";
		}
		else if ( interval.equalsIgnoreCase("Year") ) {
			// To get only the annual amount.
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
		// All these match the Streamflow meas_type.
		hb[0] = "Streamflow";
		if ( interval.equalsIgnoreCase("RealTime") || interval.equalsIgnoreCase("Irregular") ) {
			hb[0] = "RT_rate";
			if ( sub_data_type.length() > 0 ) {
				// Assume that the vax_field is accurate.
				hb[1] = sub_data_type;
			}
			else {
			    hb[1] = "DISCHRG";	// Assume this.
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
Fill a daily diversion (DivTotal or DivClass) or reservoir (RelTotal, RelClass) time series by carrying forward data.
This method is typically only called by internal database API code
(should be part of data retrieval process, not user-driven data filling)
because it supplies data values that are used to compute historical averages.
Prior to 2019-09-07 any value was used to fill.
Subsequent to this date only zero can be used to fill values because filling non-zero values occurs via DWR processes.
The following rules are applied:
<ol>
<li> Operates on daily data.</li>
<li> Filling considers data in blocks of irrigation years (Nov 1 to Oct 31).</li>
<li> If an entire irrigation year is missing, no filling occurs.</li>
<li> Start of year:  If an observation occurs after Nov 1 (but no value is recorded on Nov 1),
     zero is used at the beginning of the irrigation year, until the first observation in that irrigation year.</li>
<li> Within year:  Missing values that follow an observed zero are filled with zero until the next observed value.</li>
<li> End of year:  If an observation occurs before Oct 31 (but no value is recorded on Oct 31),
     the last observation in the irrigation year is carried to the end of the irrigation year - but only if ZERO.</li>
<li> HydroBase should have full irrigation years of daily data for months in which there was an observation.
     However, do not count on this and fill all months of daily data, as per the rules.</li>
<li> Any filled values are flagged with 'c' by default, or user-supplied flag.</li>
</ol>
@param ts Time series to fill.
@param fillDailyDivFlag a string used to flag filled data.
@exception Exception if there is an error filling the data.
*/
static public void fillTSIrrigationYearCarryForward ( DayTS ts, String fillDailyDivFlag )
throws Exception {
	String routine = HydroBase_Util.class.getSimpleName() + ".fillTSIrrigationYearCarryForward";
	if ( ts == null ) {
		return;
	}
	if ( !(ts instanceof DayTS) ) {
		return;
	}

	DateTime FillStart_DateTime = new DateTime ( ts.getDate1() );
	DateTime FillEnd_DateTime = new DateTime ( ts.getDate2() );

	boolean FillDailyDivFlag_boolean = false; // Indicate whether to use flag.
	if ( (fillDailyDivFlag != null) && (fillDailyDivFlag.length() > 0) ) {
		FillDailyDivFlag_boolean = true;
	}

	// Loop through the period in blocks of irrigation years.

	FillStart_DateTime.setMonth(11);
	FillStart_DateTime.setDay(1);
	if ( FillStart_DateTime.greaterThan(ts.getDate1()) ) {
		// Subtract a year to get the full irrigation year.
		FillStart_DateTime.addYear ( -1 );
	}

	List<String> messages = new ArrayList<>();
	DateTime date = new DateTime ( FillStart_DateTime );
	DateTime yearstart_DateTime = null;	// Fill dates for one year.
	DateTime yearend_DateTime = null;
	int found_count = 0; // Count of non-missing values in year.
	int missing_count = 0; // Count of missing values in a year.
	double value = 0.0; // Time series data value.
	double fill_value = 0.0; // Value to be used to fill data, if carry-forward of zero or non-zero.
	double fillZero = 0.0; // Exact zero value used for filling with zero.
	TSData tsdata = new TSData(); // Needed to retrieve time series data with flags.
	for ( ; date.lessThanOrEqualTo(FillEnd_DateTime); date.addDay(1) ) {
		if ( (date.getMonth() == 11) && (date.getDay() == 1) ) {
			// Start of a new irrigation year.
			// First go through the whole year to determine if any non-missing values exist.
			// If not, then skip the entire year (leave the entire year missing).
			yearstart_DateTime = new DateTime ( date );	// Save to iterate again later.
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
				Message.printDebug ( 2, routine, "Processing irrigation year starting at " + date );
			}
			for ( ; date.lessThanOrEqualTo(yearend_DateTime); date.addDay(1) ) {
				value = ts.getDataValue ( date );
				if ( !ts.isDataMissing(value) ) {
					// Have an observation.
					if ( Message.isDebugOn && (found_count == 0) ) {
						Message.printDebug ( 2, routine, "Found first non-missing value at at " + date );
					}
					++found_count;
				}
			}
			if ( Message.isDebugOn ) {
				Message.printDebug ( 2, routine, "Found " + found_count + " days of observations." );
			}
			if ( found_count == 0 ) {
				// Just continue and process the next year.
				// Reset the date to the end of the irrigation year so that an increment
				// will cause a new irrigation year to be processed.
				date = new DateTime ( yearend_DateTime );
				continue;
			}
			// Else, will repeat the year that was just checked to fill it in.
			date = new DateTime(yearstart_DateTime);
			fill_value = 0.0;
			missing_count = 0;
			for ( ; date.lessThanOrEqualTo(yearend_DateTime); date.addDay(1) ) {
				value = ts.getDataValue ( date );
				if ( ts.isDataMissing(value) && (fill_value > -.000001) && (fill_value < .000001) ) {
					// Only fill if the carry-forward fill value is zero.
					++missing_count;
					if ( FillDailyDivFlag_boolean ) {
						// Set the data flag, appending to the old value.
						tsdata = ts.getDataPoint(date,tsdata);
						// Used when non-zero carry forward was allowed.
						//ts.setDataValue ( date, fill_value, (tsdata.getDataFlag().trim() + fillDailyDivFlag), 1 );
						ts.setDataValue ( date, fillZero, (tsdata.getDataFlag().trim() + fillDailyDivFlag), 1 );
					}
					else {
					    // No data flag.
						// Used when non-zero carry forward was allowed.
						//ts.setDataValue ( date, fill_value );
						ts.setDataValue ( date, fillZero );
					}
				}
				// Check for filling with zero elsewhere in the code.
				else {
					fill_value = value;
				}
			}
			// Save messages to add to the genesis history.
			if ( missing_count > 0 ) {
				messages.add (
				"Nov 1, " + yearstart_DateTime.getYear() + " to Oct 31, " + yearend_DateTime.getYear() +
				" filled " + missing_count + " values by carrying forward observation, flagged with 'c'." );
			}
			// Reset the date to the end of the irrigation year so
			// that an increment will cause a new irrigation year to be processed.
			date = new DateTime ( yearend_DateTime );
		}
	}

	// Add to the genesis.

	if ( messages.size() > 0 ) {
		ts.addToGenesis("Filled " + ts.getDate1() + " to " +
		ts.getDate2() + " using carry forward within irrigation year because HydroBase daily data omit empty months." );
		if ( Message.isDebugOn ) {
			// TODO SAM 2006-04-27 Evaluate whether this should always be saved in the genesis.
			// The problem is that the genesis can get very long.
			for ( String message: messages) {
				ts.addToGenesis ( message );
			}
		}
		if ( (fillDailyDivFlag != null) && !fillDailyDivFlag.equals("") ) {
		    ts.addDataFlagMetadata(
		        new TSDataFlagMetadata(fillDailyDivFlag, "Filled within irrigation year using DWR carry-forward approach because HydroBase daily data omits empty months."));
		}
	}
}

/**
Fill a daily, monthly, or yearly diversion (DivTotal, DivClass) or reservoir
(RelTotal, RelClass) time series using diversion comment information.
Certain "not used" flags indicate a value of zero.
This method DOES NOT change the period of record to include comments outside the original period.
It also does not use a flag to indicate filled values.  Use the overloaded method to indicate filled values.
@param hbdmi HydroBaseDMI to use for queries.
@param ts Time series to fill.
@param date1 Starting date to fill, or null to fill all.
@param date2 Ending date to fill, or null to fill all.
@exception Exception if there is an error filling the time series (e.g., exception thrown querying HydroBase).
*/
public static void fillTSUsingDiversionComments(HydroBaseDMI hbdmi, TS ts, DateTime date1, DateTime date2)
throws Exception {
	fillTSUsingDiversionComments ( hbdmi, ts, date1, date2, null, null, false );
}

/**
Fill a daily, monthly, or yearly diversion (DivTotal, DivClass) or reservoir
(RelTotal, RelClass) time series using diversion comment information.
Certain "not used" flags ("A", "B", "C", "D") indicate a value of zero for the irrigation year (Nov 1 to Oct 31).
Yearly time series are assumed to be in irrigation year, to match the diversion comments.
@param hbdmi HydroBaseDMI to use for queries.
@param ts Time series to fill.
@param date1 Starting date to fill, or null to fill all.
@param date2 Ending date to fill, or null to fill all.
@param fillflag If specified (not null or zero length),
the time series will be updated to allow data flags and filled values will be tagged with the specified string.
A value of null or "Auto" will result in a one-character fill flag,
where the flag value is set to the "not used" flag value.
@param fillFlagDesc description for fillflag, used in report legends.
@param extend_period Indicate whether the time series period should be extended
if diversion comment values are found outside the current time series period.
This will only occur if the fill dates are null (indicating that all available data should be used,
for example in general queries).
A value of true will extend the period if necessary.
@exception Exception if there is an error filling the time series (e.g., exception thrown querying HydroBase).
*/
public static void fillTSUsingDiversionComments(HydroBaseDMI hbdmi, TS ts, DateTime date1, DateTime date2,
	String fillflag, String fillFlagDesc, boolean extend_period )
throws Exception {
	String routine = HydroBase_Util.class.getSimpleName() + ".fillDiversionUsingComments";

	if ( ts == null ) {
		// Nothing to fill.
		return;
	}

	TS divcomts = null;
	int interval_base = ts.getDataIntervalBase();
	int interval_mult = ts.getDataIntervalMult();
	try {
	    // Get the diversion comments as a time series, where the year in the time series is irrigation year.
		if ( ts.getDataType().equalsIgnoreCase("DivTotal") || ts.getDataType().toUpperCase().startsWith("DIVCLASS") ) {
			// Diversion comments.
			divcomts = hbdmi.readTimeSeries(
			ts.getLocation() + ".DWR.DivComment.Year", date1, date2, null, true, null);
		}
		else if(ts.getDataType().equalsIgnoreCase("RelTotal") || ts.getDataType().toUpperCase().startsWith("RELCLASS") ) {
			// Release comments.
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
	DateTime divcomend = new DateTime(divcomts.getDate2()); // Period for diversion comments.
	// The following causes the precision to be set correctly.
	DateTime tsdate1 = new DateTime(ts.getDate1());
	DateTime tsdate2 = new DateTime(ts.getDate2()); // Period for time series to fill.
	String zero_flags = "ABCD"; 	// "not used" flag that indicates a zero value.

	String not_used = null; // Flag in diversion comments indicating whether diversion was used in irrigation year.
	TSData tsdata = new TSData(); // Single data point from a time series.
	// Number of data values filled in the irrigation year (or number of years with diversion comments below).
	int fill_count = 0;
	StringBuilder usedCommentFlags = new StringBuilder(); // Flags that are actually encountered.
	if ( (date1 == null) && (date2 == null) && extend_period ) {
		// Loop through diversion comments once and determine whether the period needs to be extended.
		// This should only be done if the not_used flag is one of the zero values.
		int yearmin = 3000;
		int yearmax = -3000;
		for ( ; divcomdate.lessThanOrEqualTo(divcomend); divcomdate.addYear(1)) {
			tsdata = divcomts.getDataPoint(divcomdate,tsdata);
			not_used = tsdata.getDataFlag();
			if ( (not_used == null)|| (not_used.length() == 0) || (zero_flags.indexOf(not_used) < 0) ) {
				// No need to process the flag because a zero water use flag is not specified.
				continue;
			}
			if ( usedCommentFlags.indexOf(not_used) < 0 ) {
				usedCommentFlags.append(not_used);
			}
			++fill_count;
			// Save the min and max year.
			// Note that these are irrigation years since that is what is stored in the diversion comment time series.
			yearmin = MathUtil.min ( yearmin, divcomdate.getYear());
			yearmax = MathUtil.max ( yearmax, divcomdate.getYear());
		}
		// If the minimum and maximum year from the above indicate that the period should be longer, extend it.
		// Do the comparisons in calendar years.
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
			// Resize the time series, using the original date/time on an end if necessary.
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

	// Check how to handle flags and set some booleans to optimize code.
	boolean doFillFlag = false;
	boolean doFillFlagAuto = false;
	if ( (fillflag != null) && !fillflag.isEmpty() ) {
		doFillFlag = true;
		if ( fillflag.equalsIgnoreCase("Auto") ) {
			doFillFlagAuto = true;
		}
	}

	DateTime tsdate;
	int iyear = 0; // Irrigation year to process.
	double dataValue = 0.0;	// Value from the time series.
	String fillflag2 = fillflag; // String used to do filling, reflecting "Auto" value.
	TSData divcomTsData = new TSData();
	for ( divcomdate = new DateTime(divcomts.getDate1());
		divcomdate.lessThanOrEqualTo(divcomend); divcomdate.addYear(1)) {
		divcomTsData = divcomts.getDataPoint(divcomdate,tsdata);
		not_used = divcomTsData.getDataFlag();
		if ( (not_used == null) || (not_used.length() == 0) || (zero_flags.indexOf(not_used) < 0) ) {
			// No need to process the flag because a zero water use diversion comment flag is not specified.
            continue;
		}
		// Have a valid "not used" flag to be used in filling.  The year for diversion comments time series is irrigation year.
		// TODO SAM 2006-04-25 Always fill the missing values.
		// It is assumed that HydroBase has diversion comments only for irrigation years where no detailed observations occur.
		// This should be checked during HydroBase verification (but is not checked in this code).
		iyear = divcomdate.getYear();
		fill_count = 0;
		if ( interval_base == TimeInterval.YEAR ) {
			// Can check one date, corresponding to the diversion comment year.
			dataValue = ts.getDataValue(divcomdate);
			if ( ts.isDataMissing(dataValue) ) {
				if ( doFillFlag ) {
					// Set the data flag, appending to the old value.
					if ( doFillFlagAuto ) {
						// Automatically use the "not used" flag value.
						fillflag2 = not_used;
					}
					tsdata = ts.getDataPoint(divcomdate,tsdata);
					ts.setDataValue ( divcomdate, 0.0, (tsdata.getDataFlag().trim() + fillflag2), 1 );
				}
				else {
				    // No data flag.
					ts.setDataValue ( divcomdate, 0.0 );
				}
				++fill_count;
			}
		}
		else if ( (interval_base == TimeInterval.MONTH) || (interval_base == TimeInterval.DAY) ) {
			// Check every time series value in the irrigation year.
			tsdate1.setDay(1);
			tsdate1.setMonth(11);
			// Diversion comments are in irrigation year, which corresponds to the end of the Nov-Oct period.
			tsdate1.setYear(iyear - 1);
			tsdate2.setDay(31);
			tsdate2.setMonth(10);
			tsdate2.setYear(iyear);
			// The following will work for either daily or monthly because the precision on tsdate1 is set based
			// on the time series interval (if monthly the day will be ignored).
			for ( tsdate = new DateTime(tsdate1); tsdate.lessThanOrEqualTo(tsdate2);
				tsdate.addInterval(interval_base,interval_mult)) {
				dataValue = ts.getDataValue(tsdate);
				if ( ts.isDataMissing(dataValue)) {
					if ( doFillFlag ) {
						// Set the data flag, appending to the old value.
						if ( doFillFlagAuto ) {
							// Automatically use the "not used" flag value.
							fillflag2 = not_used;
						}
						tsdata = ts.getDataPoint(tsdate,tsdata);
						ts.setDataValue ( tsdate, 0.0, (tsdata.getDataFlag().trim() + fillflag2), 1 );
					}
					else {
					    // No data flag.
						ts.setDataValue ( tsdate, 0.0 );
					}
					++fill_count;
				}
			}
		}
		// Print/save a message for the year.
		if ( fill_count > 0 ) {
			String comment_string = "Filled " + fill_count + " values in irrigation year " +
			iyear + " (Nov " + (iyear - 1) + "-Oct " + iyear + ") with zero because diversion comment not_used=\"" + not_used + "\"";
			if ( doFillFlag ) {
				comment_string += ", flagged with " + fillflag2;
			}
			ts.addToGenesis(comment_string);
			Message.printStatus(2, routine, comment_string);
			// Save the flags that are used.
			if ( doFillFlag ) {
                // Set the data flag, appending to the old value.
				if ( (fillFlagDesc != null) && !fillFlagDesc.isEmpty() ) {
					// Description is provided
					if ( fillFlagDesc.equalsIgnoreCase("Auto") ) {
	                    // Use the standard values as documented by DWR.
	                    ts.addDataFlagMetadata(new TSDataFlagMetadata("A","Diversion comment - STRUCTURE NOT USABLE") );
	                    ts.addDataFlagMetadata(new TSDataFlagMetadata("B","Diversion comment - NO WATER AVAILABLE") );
	                    ts.addDataFlagMetadata(new TSDataFlagMetadata("C","Diversion comment - WATER AVAILABLE BUT NOT TAKEN") );
	                    ts.addDataFlagMetadata(new TSDataFlagMetadata("D","Diversion comment - WATER TAKEN IN ANOTHER STRUCTURE") );
	                    // The above should only be used
	                    ts.addDataFlagMetadata(new TSDataFlagMetadata("E","Diversion comment - WATER TAKEN BUT NO DATA AVAILABLE") );
	                    ts.addDataFlagMetadata(new TSDataFlagMetadata("F","Diversion comment - NO INFORMATION AVAILABLE") );
					}
					else {
						// User-defined flag description same for all values.
						for ( int i = 0; i < usedCommentFlags.length(); i++ ) {
							ts.addDataFlagMetadata(new TSDataFlagMetadata(""+usedCommentFlags.charAt(i),fillFlagDesc) );
						}
					}
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
	List<Object> v = new ArrayList<>(10);
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
Get the configuration file name for HydroBase.
Currently this is the same as the main CDSS configuration file
*/
public static String getConfigurationFile() {
	return IOUtil.getApplicationHomeDir() + File.separator + "system" + File.separator + "CDSS.cfg";
}

/**
Return the name of the default HydroBase SQL Server machine.
*/
// TODO smalers 2023-03-22 remove this when confirmed that StateDMI, etc., don't use.
/*
public static String getDefaultDatabaseServer ()
{	return "greenmtn.state.co.us";
}
*/

/**
Return the default time series data type, to allow a GUI to select the default in a list.
@return the default time series data type.
@param add_group Indicates if a data group should be included before the data type.
This should be called consistent with getTimeSeriesDataTypes().
*/
public static String getDefaultTimeSeriesDataType (	HydroBaseDMI hbdmi, boolean add_group ) {
	if ( add_group ) {
		return "Stream - Streamflow";
	}
	else {
	    return "Streamflow";
	}
}

/**
Get the preferred WDID length, used when formatting WDIDs from the WD and ID parts.
The first two characters will always be for the WD.
@return the preferred WDID length.
*/
public static int getPreferredWDIDLength () {
	return __preferred_WDID_length;
}

/**
Return a list of time series data types, suitable for listing in a graphical interface.
For example, this list is used in TSTool and StateView to list data types,
which are then passed to the readTimeSeries(String TSID) method,
where the data type in the list matches the data type in the time series identifier.
In the future, this list may be determined from a query of HydroBase's meas_type and/or struct_meas_type tables.
Currently, the types are hard-coded here because they cannot cleanly be determined from HydroBase.
@param hdmi HydroBaseDMI instance for opened connection.
@param include_types A mask using DATA_TYPE_* indicating which data types to include.
For example, for a tool that only wants to display station time series, specify DATA_TYPE_STATION_ALL.
For a tool that wants to display only reservoir structure time series, specify DATA_TYPE_STRUCTURE_RESERVOIR.
Types can be combined if appropriate.
@param add_group If true, a data type group will be added before the data types.
For example, all data related to reservoirs will be prefixed with
"Reservoir - ".  This is useful for providing a better presentation to users in interfaces.
*/
public static List<String> getTimeSeriesDataTypes ( HydroBaseDMI hdmi, int include_types, boolean add_group ) {
	List<String> types = new ArrayList<>();
	// Add these types exactly as they are listed in HydroBase.
	// If someone has a problem, HydroBase should be adjusted.
	// Notes are shown below where there may be an issue.
	// In all cases, documentation needs to be made available to describe the data type
	// (e.g., that "Snow" is accumulated value on the ground).
	// Data intervals (time step) are also shown.
	// Station types correspond to meas_type and structure types correspond to struct_meas_type.
	//
	// Ideally, all the rt_meas data could be identified with a data type and a "RealTime" time step (or similar).
	// However, RealTime is not an interval recognized by the TimeInterval class and instead Irregular is used.
	// In the future, perhaps it will be possible to get an exact regular interval for real-time gages.
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
	String prefix; // A string to add at the front of the type if add_group is true.
	/* TODO smalers 2022-03-24 as of this date the real-time data are not read from HydroBase since have web services.
	if ( (include_types&DATA_TYPE_HARDWARE) > 0 ) {
		// RT_Misc BATTERY
		prefix = "";
		if ( add_group ) {
			prefix = "Hardware - ";
		}
		types.add ( prefix + "Battery" );
	}
	*/
	if ( (include_types&DATA_TYPE_STATION_CLIMATE) > 0 ) {
		prefix = "";
		if ( add_group ) {
			prefix = "Climate - ";
		}
		//types.addElement ( "Evap" ); // Maybe should be "EvapPan" or "PanEvap".
							// Time step:  Day and Month
		types.add ( prefix + "EvapPan" );
		//types.addElement ( "FrostDate" );	// Need something like:
							// FrostDateF28,
							// FrostDateF32,
							// FrostDateS28,
							// FrostDateS32
							// so each time series can be manipulated.
							// Time step:  Year
		types.add ( prefix + "FrostDateF32F" );
		types.add ( prefix + "FrostDateF28F" );
		types.add ( prefix + "FrostDateL28S" );
		types.add ( prefix + "FrostDateL32S" );
		//types.add ( "MaxTemp" );	// Perhaps should have:
		//types.add ( "MinTemp" );	// "TempMax", "TempMin",
		//types.add ( "MeanTemp" );	// for daily and
							// "TempMean",
							// "TempMeanMax",
							// "TempMeanMin" for
							// monthly - it gets
							// messy.
		types.add ( prefix + "Precip" ); // Time step:  Month, Day, real-time with vax_field PRECIP
		// TODO smalers 2022-03-24 as of this date the real-time data are not read from HydroBase since have web services.
		//types.add ( prefix + "Temp" ); // Proposed for use with real-time AIRTEMP vax_field
		types.add ( prefix + "TempMax" );
		types.add ( prefix + "TempMean" );
		types.add ( prefix + "TempMeanMax" );
		types.add ( prefix + "TempMeanMin" );
		//types.add ( prefix + "TempMin" );	// Proposed
		types.add ( prefix + "Snow" );	// Time step:  Day, Month
		//types.add ( "SnowCrse" );	// "SnowDepth" and
							// "SnowWaterEquivalent"
							// would be better, but
							// need to check how
							// depth compares to
							// "Snow" type
							// Time step:
							// Day or IrregDay?
		types.add ( prefix + "SnowCourseDepth" );
		types.add ( prefix + "SnowCourseSWE" );
		types.add ( prefix + "Solar" );	// Time step:  Day
		types.add ( prefix + "VaporPressure" );
							// Time step:  Day
		types.add ( prefix + "Wind" );	// OK but maybe
							// WindTravel or similar
							// is better (some gages
							// measure wind
							// direction)?
							// Time step:  Day
	}
	if ( (include_types&DATA_TYPE_STATION_STREAM) > 0 ) {
		// AdminFlow was added in 2007 for administrative gages.
		// It has the same data types as the main Streamflow.
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
			// Don't add AdminFlow.
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
		// TODO smalers 2022-03-24 as of this date the real-time data are not read from HydroBase since have web services.
		//types.add ( prefix + "Stage" );	// Time step:  real-time RT_Misc GAGE_HT
		types.add ( prefix + "Streamflow" );
		types.add ( prefix + "StreamflowMax" );
		types.add ( prefix + "StreamflowMin" );
							// Time step:
							// Day, Month, RealTime?
		// TODO smalers 2022-03-24 as of this date the real-time data are not read from HydroBase since have web services.
		//types.add ( prefix + "WatTemp" );// Water temperature:
							// RT_Misc WATTEMP
	}
	if ( (include_types&DATA_TYPE_STATION_RESERVOIR) > 0 ) {
		prefix = "";
		if ( add_group ) {
			prefix = "Reservoir - ";
		}
		// TODO smalers 2022-03-24 as of this date the real-time data are not read from HydroBase since have web services.
		//types.add ( prefix + "Release" );// Time step:
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
		// TODO smalers 2022-03-24 as of this date the real-time data are not read from HydroBase since have web services.
		//types.add ( prefix + "PoolElev" );// Time step:
							// RT_Height with
							// vax_field ELEV
		// TODO smalers 2022-03-24 as of this date the real-time data are not read from HydroBase since have web services.
		//types.add ( prefix + "Storage" );// Time step:  realtime
							// RT_Vol STORAGE
	}
	if ( (include_types&DATA_TYPE_STATION_WELL) > 0 ) {
		prefix = "";
		if ( add_group ) {
			prefix = "Well - ";
		}
		// TODO smalers 2022-03-24 as of this date the real-time data are not read from HydroBase since have web services.
		//types.add ( prefix + "WellLevel (phasing out)"); // RT_Misc WELL_1
		//types.add ( prefix + "WellLevelElev"); // Replaces legacy WellLevel.
		// Also see WellLevel for structures.
	}
	if ( (include_types&DATA_TYPE_AGRICULTURE) > 0 ) {
		prefix = "";
		// Colorado agricultural statistics.
		if ( add_group ) {
			prefix = "Agriculture/CASS - ";
		}
		types.add ( prefix + "CropAreaHarvested" );
		types.add ( prefix + "CropAreaPlanted" );
		types.add ( prefix + "LivestockHead" );
		// National agricultural statistics.
		if ( add_group ) {
			prefix = "Agriculture/NASS - ";
		}
		types.add ( prefix + "CropArea" );
		// Data derived from DSS GIS work.
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
							// table.
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
		types.add ( prefix + "DivClass" );// These are probably.
		types.add ( prefix + "DivComment" );// OK - will use
		types.add ( prefix + "DivTotal" );// SFUT as the sub data type.
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
							// Add to evaluate.
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
		// TODO smalers 2022-03-24 as of this date the real-time data are not read from HydroBase since have web services.
		//types.add ( prefix + "WellLevel (phasing out)" );
		types.add ( prefix + "WellLevelDepth" );
		types.add ( prefix + "WellLevelElev" ); // Replaces legacy WellLevel.
		// TODO SAM 2012-07-02 Also somehow need to hook in "WellPumping".
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
	// Remove duplicates - for example, this may occur for WellLevel because it is listed with stations and structures.
	StringUtil.removeDuplicates ( types, true, true );
	return types;
}

/**
Return the data units for a time series data type.
The units should ideally be in meas_type; however,
some time series tables store more than one time series data type and the units are constant.
If units are variable and are stored in time series data records, return blank.
@return the data units for a time series data type.
@param hbdmi Reserved for future use (can pass as null).
@param data_type the time series data type, using time series data conventions
(e.g., use "StreamflowMax" and "Month" rather than "Streamflow", "Monthly", which is not specific enough).
@param interval Data interval for time series.
*/
public static String getTimeSeriesDataUnits ( HydroBaseDMI hbdmi, String data_type, String interval ) {
	String ACRE = "ACRE", ACFT = "ACFT", CFS = "CFS", DAY = "DAY",
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
	    // TODO SAM 2012-05-15 Is this true - need this for web services.
		// Units are in data records and can be IN or mm.
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
		// Units are for the acreage.
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
		// Units can't be detected until records are read.
		return "";
	}
	else if ( data_type.equalsIgnoreCase("VaporPressure") ) {
		return KPA;
	}
	else if ( data_type.equalsIgnoreCase("WellLevel") || // Legacy.
	    data_type.equalsIgnoreCase("WellLevelElev") || // New conventions.
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
		// Water information sheet data types start with "WIS".
		return CFS;
	}
	return UNKNOWN;
}

/**
Return the time steps to be displayed for a time series data type.
The time step should ideally be in meas_type; however,
some time series tables store more than one time series data type and the time steps in meas_type are not appropriate for displays.
@return the time steps that are available for a time series data type.
@param hbdmi Reserved for future use.
@param data_type the time series data type, using time series data conventions
(e.g., use "StreamflowMax" rather than "Streamflow", which is ambiguous).
@param include_types A mask using DATA_TYPE_* indicating which data types to include.
For example, for a tool that only wants to display station time series, specify DATA_TYPE_STATION_ALL.
For a tool that wants to display only reservoir structure time series, specify DATA_TYPE_STRUCTURE_RESERVOIR.
Types can be combined if appropriate.
For this method, the inlude_types are only used in cases where a data type is listed as both a station and structure
(e.g., WellLevel) but the interval is different for each case
(e.g., WellLevel Irregular for station, WellLevel Day for structure).
*/
public static List<String> getTimeSeriesTimeSteps (	HydroBaseDMI hbdmi, String data_type, int include_types ) {
	String Month = "Month";
	String Day = "Day";
	String Year = "Year";
	//String Irregular = "Irregular";
	List<String> timesteps = new ArrayList<>();
	// Alphabetize by data type, as much as possible.
	if ( data_type.equalsIgnoreCase("AdminFlow") ) {
		timesteps.add ( Day );
		timesteps.add ( Month );
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
		// as per correspondence with Doug Stenzel.
		//v.add ( Irregular );  // Real-time.
	}
	else if ( data_type.equalsIgnoreCase("AdminFlowMax") ||
		data_type.equalsIgnoreCase("AdminFlowMin") ) {
		timesteps.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("Agstats") ) {
		timesteps.add ( Year );
	}
	else if ( data_type.startsWith("CropArea") ) {
		timesteps.add ( Year );
	}
	else if ( data_type.equalsIgnoreCase("Battery") ) {
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
		// as per correspondence with Doug Stenzel.
		//v.add ( Irregular );
	}
	else if(data_type.equalsIgnoreCase("DivClass") || data_type.equalsIgnoreCase("DivTotal") ||
		data_type.equalsIgnoreCase("IDivClass") || data_type.equalsIgnoreCase("IDivTotal") ) {
		timesteps.add ( Day );
		timesteps.add ( Month );
		timesteps.add ( Year );
	}
	else if ( data_type.equalsIgnoreCase("EvapPan") ) {
		timesteps.add ( Day );
		timesteps.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("DivComment") ) {
		timesteps.add ( Year );
	}
	else if( data_type.equalsIgnoreCase("FrostDateL28S") || data_type.equalsIgnoreCase("FrostDateL32S") ||
		data_type.equalsIgnoreCase("FrostDateF32F") || data_type.equalsIgnoreCase("FrostDateF28F") ) {
		timesteps.add ( Year );
	}
    else if ( data_type.equalsIgnoreCase("HumanPopulation") ) {
        timesteps.add ( Year );
    }
    else if ( data_type.equalsIgnoreCase("LivestockHead") ) {
        timesteps.add ( Year );
    }
	else if ( data_type.equalsIgnoreCase("NaturalFlow") ) {
		timesteps.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("PoolElev") ) {
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
		// as per correspondence with Doug Stenzel.
		//timesteps.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("Precip") ) {
		timesteps.add ( Day );
		timesteps.add ( Month );
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
		// as per correspondence with Doug Stenzel.
		//v.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("RelClass") || data_type.equalsIgnoreCase("RelTotal") ||
		data_type.equalsIgnoreCase("IRelClass") || data_type.equalsIgnoreCase("IRelTotal") ) {
		timesteps.add ( Day );
		timesteps.add ( Month );
		timesteps.add ( Year );
	}
	else if ( data_type.equalsIgnoreCase("RelComment") ) {
		timesteps.add ( Year );
	}
	else if ( data_type.equalsIgnoreCase("Release") ) {
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
		// as per correspondence with Doug Stenzel.
		//timesteps.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("ResEOM") ) {
		timesteps.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("ResEOY") ) {
		timesteps.add ( Year );
	}
	else if ( data_type.equalsIgnoreCase("ResMeasElev") || data_type.equalsIgnoreCase ( "ResMeasEvap" ) ||
		data_type.equalsIgnoreCase ( "ResMeasFill" ) || data_type.equalsIgnoreCase ( "ResMeasRelease") ||
		data_type.equalsIgnoreCase ( "ResMeasStorage") ) {
		timesteps.add ( Day );
	}
	else if ( data_type.equalsIgnoreCase("Snow") ) {
		// Snow accumulation - on the ground
		timesteps.add ( Day );
		timesteps.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("SnowCourseDepth") || data_type.equalsIgnoreCase("SnowCourseSWE") ) {
		// Although the data are stored in a monthly table, the values are generally recorded to the day
		timesteps.add ( Day );
	}
	else if ( data_type.equalsIgnoreCase("Solar") ) {
		timesteps.add ( Day );
	}
	else if ( data_type.equalsIgnoreCase("Stage") ) {
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
		// as per correspondence with Doug Stenzel.
		//timesteps.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("Storage") ) {
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
		// as per correspondence with Doug Stenzel.
		//timesteps.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("Streamflow") ) {
		timesteps.add ( Day );
		timesteps.add ( Month );
		//v.add ( RealTime );
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
		// as per correspondence with Doug Stenzel.
		//v.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("StreamflowMax") ||
		data_type.equalsIgnoreCase("StreamflowMin") ) {
		timesteps.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("Temp") ) {
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
		// as per correspondence with Doug Stenzel.
		//timesteps.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("TempMax") ) {
		timesteps.add ( Day );
	}
	else if(data_type.equalsIgnoreCase("TempMean") || data_type.equalsIgnoreCase("TempMeanMax") ||
		data_type.equalsIgnoreCase("TempMeanMin") ) {
		timesteps.add ( Month );
	}
	else if ( data_type.equalsIgnoreCase("TempMin") ) {
		timesteps.add ( Day );
	}
	else if ( data_type.equalsIgnoreCase("VaporPressure") ) {
		timesteps.add ( Day );
	}
	else if ( data_type.equalsIgnoreCase("WatTemp") ) {
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
		//timesteps.add ( Irregular );
	}
	else if ( data_type.equalsIgnoreCase("WellLevel") ) {
	    // Legacy, replaced by WellLevelElev
		if ( (include_types&DATA_TYPE_STRUCTURE_WELL) != 0 ) {
			timesteps.add ( Day );
		}
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
		//if ( (include_types&DATA_TYPE_STATION_WELL) != 0 ) {
		//	timesteps.add ( Irregular );
		//}
	}
	else if ( data_type.equalsIgnoreCase("WellLevelDepth")) {
        if ( (include_types&DATA_TYPE_STRUCTURE_WELL) != 0 ) {
            timesteps.add ( Day );
        }
	}
    else if ( data_type.equalsIgnoreCase("WellLevelElev") ) {
        // Replaces legacy "WellLevel"
		// TODO smalers 2022-03-24 as of this date don't allow real-time data queries out of HydroBase,
        //if ( (include_types&DATA_TYPE_STATION_WELL) != 0 ) {
        //    timesteps.add ( Irregular );
        //}
        if ( (include_types&DATA_TYPE_STRUCTURE_WELL) != 0 ) {
            timesteps.add ( Day );
        }
    }
	else if ( data_type.equalsIgnoreCase("Wind") ) {
		timesteps.add ( Day );
	}
	else if ( data_type.regionMatches(true,0,"WIS",0,3) ) {
		// Water information sheet data types start with "WIS"...
		timesteps.add ( Day );
	}
	return timesteps;
}

/**
Indicate whether a time series data type is for CASS agricultural crop statistics.
@param hbdmi An instance of HydroBaseDMI.
The data type is checked to see whether it is "CropAreaHarvested" or "CropAreaPlanted".
@param data_type A HydroBase data type.
If the data_type has a "-", then the token after the dash is compared.
@return true if the data type is for agricultural crop statistics.
*/
public static boolean isAgriculturalCASSCropStatsTimeSeriesDataType ( HydroBaseDMI hbdmi, String data_type ) {
	if ( data_type.indexOf("-") >= 0) {
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
public static boolean isAgriculturalCASSLivestockStatsTimeSeriesDataType ( HydroBaseDMI hbdmi, String data_type ) {
	if ( data_type.indexOf("-") >= 0) {
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
public static boolean isAgriculturalNASSCropStatsTimeSeriesDataType ( HydroBaseDMI hbdmi, String data_type ) {
	if ( data_type.indexOf("-") >= 0) {
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
@param dataType A HydroBase data type.
If the data_type has a "-", then the token after the dash is compared.
@return true if the data type is for CUPopulation.
*/
public static boolean isCUPopulationTimeSeriesDataType ( HydroBaseDMI hbdmi, String dataType ) {
	if ( dataType.indexOf("-") >= 0) {
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
public static boolean isGroundWaterWellTimeSeriesDataType (HydroBaseDMI hbdmi, String data_type ) {
	if (data_type.equalsIgnoreCase("WellLevel") || data_type.equalsIgnoreCase("WellLevelDepth") ||
        data_type.equalsIgnoreCase("WellLevelElev") ) {
		return true;
	}
	return false;
}

/**
Indicate whether a time series data type is for irrigation summary time series.
@param hbdmi An instance of HydroBaseDMI.
The data type is checked to see whether it is "CropAreaAllIrrigation", "CropAreaDrip", "CropAreaFlood",
"CropAreaFurrow", or "CropAreaSprinkler".
@param data_type A HydroBase data type.  If the data_type has a "-", then the token after the dash is compared.
@return true if the data type is for irrigation summary time series.
*/
public static boolean isIrrigSummaryTimeSeriesDataType ( HydroBaseDMI hbdmi, String data_type ) {
	if ( data_type.indexOf("-") >= 0) {
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
Determines whether an int is missing.
This uses the legacy HydroBase missing value MISSING_INT.
@param value the int to be checked
@return true if the int is missing, false if not
*/
public static boolean isMissing(int value) {
	if ( value == MISSING_INT ) {
		return true;
	}
	return false;
}

/**
Determines whether a double is missing.
This uses the legacy HydroBase missing value -999.0.
@param value the double to be checked
@return true if the double is missing, false if not
*/
public static boolean isMissing(double value) {
	if ( (value >= -999.1) && (value <= -998.9) ) {
		return true;
	}
	return false;
}

/**
Determines whether a float is missing.
This uses the legacy HydroBase missing value -999.0.
@param value the float to be checked
@return true if the float is missing, false if not
*/
public static boolean isMissing(float value) {
	if ( (value >= -999.1) && (value <= -998.9) ) {
		return true;
	}
	return false;
}

/**
Indicate whether a time series data type is for a station.
@param hbdmi An instance of HydroBaseDMI.  The global distinct MeasType data from the instance are checked.
@param data_type A HydroBase data type.
If it matches the data type in meas_type.meas_type, true will be returned.
If the data_type has a "-", then the token before the dash is compared.
*/
public static boolean isStationTimeSeriesDataType (	HydroBaseDMI hbdmi, String data_type ) {
	if ( data_type.indexOf("-") >= 0) {
		data_type = StringUtil.getToken(data_type,"-",0,0) ;
	}
	List<HydroBase_MeasType> global_mt = hbdmi.getMeasType();
	int size = global_mt.size();
	HydroBase_MeasType mt;
	for ( int i = 0; i < size; i++ ) {
		mt = global_mt.get(i);
		if ( data_type.equalsIgnoreCase(mt.getMeas_type()) ) {
			return true;
		}
	}
	return false;
}

/**
Indicate whether a time series data type is for a structure that has SFUT (one of the "Class" data types).
@param hbdmi An instance of HydroBaseDMI.  The global distinct StructMeasType data from the instance are checked.
@param data_type A HydroBase data type.
If it matches the data type in struct_meas_type.meas_type, true will be returned.
If the data_type has a "-", then the token before the dash is compared.
*/
public static boolean isStructureSFUTTimeSeriesDataType(HydroBaseDMI hbdmi, String data_type ) {
	if ( data_type.indexOf("-") >= 0) {
		data_type = StringUtil.getToken(data_type,"-",0,0) ;
	}
	if ( StringUtil.indexOfIgnoreCase(data_type,"Class",0) >= 0 ) {
		return true;
	}
	return false;
}

/**
Indicate whether a time series data type is for a structure.
The result is inclusive of isStructureSFUTTimeSeriesDataType().
In other words, call the isStructureSFUTTimeSeriesDataType() first to match SFUT data types and then call this more general method.
@param hbdmi An instance of HydroBaseDMI.
The global distinct StructMeasType data from the instance are checked.
@param data_type A HydroBase data type.
If it matches the data type in struct_meas_type.meas_type,
true will be returned.  If the data_type has a "-", then the token before the dash is compared.
*/
public static boolean isStructureTimeSeriesDataType ( HydroBaseDMI hbdmi, String data_type ) {
	if ( data_type.indexOf("-") >= 0) {
		data_type = StringUtil.getToken(data_type,"-",0,0) ;
	}
	List<HydroBase_StructMeasType> global_mt = hbdmi.getStructMeasType();
	int size = global_mt.size();
	HydroBase_StructMeasType mt;
	// Check the list of struct_meas_type.meas_type.
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
@param data_type A HydroBase data type.
Currently, the string is compared against the hard-coded strings "WISDeliveryFlow",
"WISPriorityDiversion", "WISDeliveryDiversion",
"WISGainLoss", "WISNaturalFlow", "WISPointFlow", "WISRelease", "WISTribNaturalFlow", "WISTribDeliveryFlow".
*/
public static boolean isWISTimeSeriesDataType (	HydroBaseDMI hbdmi, String data_type ) {
	if ( data_type.equalsIgnoreCase("WISPointFlow") ||
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
Returns the String name for the diversion source abbreviation using global data.
This is the "S" in SFUT.  Return an empty string if not found.<p>
@return the String name for the diversion source abbreviation.
This is the "S" in SFUT.  Return an empty string if not found.
@param source Source abbreviation.
*/
public static String lookupDiversionCodingSource ( String source ) {
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
@return the String name for the diversion type abbreviation.
This is the "T" in SFUT.  Return an empty string if not found.
@param type Type abbreviation.
*/
public static String lookupDiversionCodingType ( String type ) {
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
public static int lookupDivisionForWaterDistrict ( HydroBaseDMI dmi, int district ) {
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
Lookup a HydroBaseDMI instance using the input name.
This is useful, for example, when finding a HydroBaseDMI instance matching at time series
identifier that includes the input name.
@return the HydroBaseDMI instance with an input name that matches the requested input name.  The first match is found.
@param hydroBaseDMIList List of HydroBaseDMI to search.
@param inputName HydroBaseDMI input name to find.
*/
public static HydroBaseDMI lookupHydroBaseDMI ( List<HydroBaseDMI> hydroBaseDMIList, String inputName ) {
	if ( hydroBaseDMIList == null ) {
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
Parse an SFUT string into its parts.
The string to parse has the format "S: F: U: T: G:" where data fields may be present after each :.
The G: format was added in HydroBase 20061003.
The F: in this version is a 7-digit WDID indicating the "from" structure.
In earlier versions, it is the ID part of the WDID,
and the WDID is the same as the structure itself (can only record data movement within a water district).
@return An array of strings, each of which is one of the SFUT fields,
without the leading "S:", "F:", "U:", "T:" or "G:".
The parts are guaranteed to be non-null, but may be blank strings.
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
		// Do not check for G: above because it is being phased in.  First break by spaces.
		String sfut0 = sfut_string + " ";
		List<String> sfut = StringUtil.breakStringList ( sfut0, " ", StringUtil.DELIM_SKIP_BLANKS );
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
			// G: at end, for database after 20061003.
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
	String routine = HydroBase_Util.class.getSimpleName() + ".readConfiguration";

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
Reads the database for the data necessary to fill groundwater well and groundwater meas type information.
This method is called by TSTool to provide a catalog of well time series.
@param panel the panel of InputFilters that hold the query constraints.
@param districtWhere the value returned by getWaterDistrictWhereClause().
@return a list of HydroBase_StructureGeolocStructMeasTypeView objects.
@throws Exception if there is an error running the query.
*/
public static List<HydroBase_GroundWaterWellsView> readGroundWaterWellsViewTSCatalogList(
	HydroBaseDMI hbdmi, InputFilter_JPanel panel, String dataType, String timeStep)
throws Exception {
	List<HydroBase_GroundWaterWellsView> tslist = hbdmi.readGroundWaterWellsMeasTypeList(panel, null);
	// Convert HydroBase data to make it more consistent with how TSTool handles time series.
	int size = 0;
	if ( tslist != null ) {
		size = tslist.size();
	}
	HydroBase_GroundWaterWellsView view;
	String data_units = getTimeSeriesDataUnits ( hbdmi, dataType, timeStep );

	for ( int i = 0; i < size; i++ ) {
		view = tslist.get(i);
		// Set to the value used in TSTool.
	    view.setMeas_type( dataType);
		view.setTime_step(timeStep);
		view.setData_units ( data_units );
		tslist.add(view);
	}
	return tslist;
}

/**
Read the station, geoloc and meas_type tables for a record that has the given meas_type.<p>
This is used by TSTool to provide a catalog of time series.
@param panel an InputFilter_JPanel containing what to limit the query by.
Specify null if no panel contains query limits.
@param districtWhere the value returned by getWaterDistrictWhereClause().
@param mapQueryLimits the GRLimits defining the geographical area for which to query.
@param meas_type the meas_type for which to return the record (specify null to read all).
@param time_step the time_step for which to return the record (specify null to read all).
@param vax_field the vax_field for which to return the record (specify null to read all).
@param data_source the data_source for which to return the record (specify null to read all).
@param transmit the transmit value to read for (specify null to read all).
@return a list of HydroBase_StationGeolocMeasType or HydroBase_StationView objects.
@throws Exception if an error occurs.
*/
public static List<HydroBase_StationGeolocMeasType> readStationGeolocMeasTypeCatalogList(
	HydroBaseDMI hbdmi, InputFilter_JPanel panel,
	String data_type, String time_step, GRLimits grlimits )
throws Exception {
	String routine = HydroBase_Util.class.getSimpleName() + ".readStationGeolocMeasTypeCatalogList";
	String [] hb_mt = HydroBase_Util.convertToHydroBaseMeasType ( data_type, time_step );
	String meas_type = hb_mt[0];
	String vax_field = hb_mt[1];
	String hbtime_step = hb_mt[2];
	String dataSource = null;
    // Note multiple vax_field and data sources can be returned below.
	// Only specify the vax_field when it is necessary to do so to get the proper list back.
	List<HydroBase_StationView> tslist0 = hbdmi.readStationGeolocMeasTypeList(panel, null, grlimits, meas_type, hbtime_step,
		vax_field, dataSource, null, false);
	// Convert HydroBase data to make it more consistent with how TSTool handles time series.
	int size = 0;
	if ( tslist0 != null ) {
		size = tslist0.size();
	}
	if ( size == 0 ) {
		Message.printWarning ( 3, routine, "Found 0 time series for meas_type=\""+ meas_type +
			"\" time_step=\""+ hbtime_step + "\" vax_field=\""+ vax_field + "\" data_source=\""+ dataSource +"\"");
	}
	HydroBase_StationView view = null;
	String data_units = getTimeSeriesDataUnits ( hbdmi, data_type, time_step );
	List<HydroBase_StationGeolocMeasType> tslist = new ArrayList<>();
	for ( int i = 0; i < size; i++ ) {
		view = tslist0.get(i);
		// Set to the value used in TSTool.
		if ( view.getVax_field().length() > 0 ){
			view.setMeas_type( data_type + "-" + view.getVax_field() );
		}
		else {
		    view.setMeas_type ( data_type);
		}
		view.setTime_step ( time_step );
		view.setData_units ( data_units );
		tslist.add(new HydroBase_StationGeolocMeasType(view));
	}
	return tslist;
}

/**
Read the structure and irrig_summary_ts tables for all data with the matching criteria.<p>
This is called by:<ul>
<li>TSTool</li>
</ul>
This code was pulled from HydroBase_Util.readTimeSeriesHeaderObjects in order
to implement code that properly uses generics.
*/
public static List<HydroBase_StructureIrrigSummaryTS> readStructureIrrigSummaryTSCatalogList(
	HydroBaseDMI hbdmi, InputFilter_JPanel panel, List<String> orderby_clauses,
	int structure_num, int wd, int id, String str_name,
	String land_use, DateTime req_date1, DateTime req_date2, boolean distinct )
throws Exception {
	// Structure GIS crop areas.
	List<HydroBase_StructureView> tslist0 = hbdmi.readStructureIrrigSummaryTSList (
		panel,
		null,	// orderby
		MISSING_INT, // structure_num
		MISSING_INT, // wd
		MISSING_INT, // id
		null, // str_name
		null, // landuse
		null, // start
		null, // end
		true); // distinct
	List<HydroBase_StructureIrrigSummaryTS> tslist = new ArrayList<>();
	for ( HydroBase_StructureView view : tslist0 ) {
		tslist.add(new HydroBase_StructureIrrigSummaryTS(view));
	}
	return tslist;
}

/**
Read a list of objects that contain time series header information.
This is used, for example, to populate the time series list area of TSTool and to
get a list of time series for the TSTool readHydroBase(Where...) command.
@param hbdmi The HydroBaseDMI instance to use for queries.
@param data_type The data type for time series, either from the TSTool data type choice for HydroBase, or a simple string.
@param time_step The time step for time series, either from the TSTool time step choice, or a simple string "Day", "Month", etc.
@param ifp An InputFilter_JPanel instance to retrieve where clause information from.
@return a list of objects for the data type.
@exception if there is an error reading the data.
*/
public static List readTimeSeriesHeaderObjects ( HydroBaseDMI hbdmi, String data_type, String time_step,
	InputFilter_JPanel ifp )
throws Exception {
	return readTimeSeriesHeaderObjects ( hbdmi, data_type, time_step, ifp, null );
}

// TODO sam 2017-03-14 this is messy because a variety of class types are returned.
// The code has now been split but leave for now until the ReadHydroBase command can be updated.
/**
Read a list of objects that contain time series header information.
This is used, for example, to populate the time series list area of TSTool and to get a
list of time series for the TSTool ReadHydroBase(Where...) command.
@param hbdmi The HydroBaseDMI instance to use for queries.
@param data_type The data type for time series,
either from the TSTool data type choice for HydroBase, or a simple string.
@param time_step The time step for time series,
either from the TSTool time step choice, or a simple string "Day", "Month", etc.
@param ifp An InputFilter_JPanel instance to retrieve where clause information from.
@param grlimits GRLimits indicating the extent of the map to query.  Specify as null to query all data.
@deprecated call the individual methods to read time series so that generics can be implemented
@return a list of objects for the data type.
@exception if there is an error reading the data.
*/
public static List readTimeSeriesHeaderObjects ( HydroBaseDMI hbdmi, String data_type, String time_step,
	InputFilter_JPanel ifp, GRLimits grlimits )
throws Exception {
	String routine = HydroBase_Util.class.getSimpleName() + ".readTimeSeriesHeaderObjects";
	List tslist = null; // This can contain different classes
	String [] hb_mt = HydroBase_Util.convertToHydroBaseMeasType ( data_type, time_step );
	String meas_type = hb_mt[0];
	//String vax_field = hb_mt[1];
	String hbtime_step = hb_mt[2];
	//String dataSource = null;
	//int size = 0;

	Message.printStatus(2, routine, "Reading HydroBase time series header objects for HydroBase meas_type=\"" +
	       meas_type + "\", HydroBase timeStep=\"" + hbtime_step + "\".");

	// Special cases first and then general lists.
	if ( HydroBase_Util.isAgriculturalCASSCropStatsTimeSeriesDataType ( hbdmi, data_type ) ) {
		throw new Exception("Need to update code to call readAgriculturalCASSCropStatsList");
	}
	else if(HydroBase_Util.isAgriculturalCASSLivestockStatsTimeSeriesDataType ( hbdmi,data_type ) ) {
		throw new Exception("Need to update code to call readAgriculturalCASSLivestockStatsList");
	}
	else if ( HydroBase_Util.isAgriculturalNASSCropStatsTimeSeriesDataType ( hbdmi, data_type ) ) {
		throw new Exception("Need to update code to call readAgriculturalNASSCropStatsList");
	}
	else if(HydroBase_Util.isCUPopulationTimeSeriesDataType ( hbdmi,data_type ) ) {
		throw new Exception("Need to update code to call readCUPopulationList");
	}
	else if(HydroBase_Util.isIrrigSummaryTimeSeriesDataType ( hbdmi, data_type ) ) {
		throw new Exception("Need to update code to call readStructureIrrigSummaryTSCatalogList");
	}
	else if(HydroBase_Util.isStationTimeSeriesDataType(hbdmi, meas_type)){
		throw new Exception("Need to update code to call readStationGeolocMeasTypeTSCatalogList");
	}
	else if(HydroBase_Util.isStructureTimeSeriesDataType(hbdmi, meas_type)){
		throw new Exception("readStructureGeolocStructMeasTypeCatalogList");
	}
	else if (meas_type.equalsIgnoreCase("WellLevel")|| data_type.equals("WellLevelDepth") ||
	    data_type.equals("WellLevelElev")) {
		throw new Exception("Need to update code to call readGroundWaterWellsViewTSCatalogList");
	}
	else if ( HydroBase_Util.isWISTimeSeriesDataType ( hbdmi, data_type) ) {
		// TODO smalers 2019-06-02 WIS are obsolete but keep around for now in case they are resurrected in some form.
		throw new Exception("Need to update code to call readStructureIrrigSummaryTSCatalogList");
	}
	return tslist;
}

/**
Set the preferred WDID length, used when formatting WDIDs from the WD and ID parts.
The first two characters will always be for the WD.
@param the preferred WDID length.
*/
public static void setPreferredWDIDLength ( int preferred_WDID_length ) {
	__preferred_WDID_length = preferred_WDID_length;
}

/**
Set time series properties from an HydroBase_StationGeolocMeasType instance.
This allows the properties to be retrieved after the initial query.
@param ts time series to update
@param hbsta station data to use for time series properties
*/
public static void setTimeSeriesProperties ( TS ts, HydroBase_StationGeolocMeasType hbsta ) {
    // Use the same names as the database view columns, same order as view.
    ts.setProperty("station_num", (DMIUtil.isMissing(hbsta.getStation_num()) || HydroBase_Util.isMissing(hbsta.getStation_num()))? null : Integer.valueOf(hbsta.getStation_num()));
    ts.setProperty("geoloc_num", (DMIUtil.isMissing(hbsta.getGeoloc_num()) || HydroBase_Util.isMissing(hbsta.getGeoloc_num())) ? null : Integer.valueOf(hbsta.getGeoloc_num()));
    ts.setProperty("station_name", hbsta.getStation_name());
    ts.setProperty("station_id", hbsta.getStation_id());
    ts.setProperty("cooperator_id", hbsta.getCooperator_id());
    ts.setProperty("nesdis_id", hbsta.getNesdis_id());
    ts.setProperty("drain_area", (DMIUtil.isMissing(hbsta.getDrain_area()) || HydroBase_Util.isMissing(hbsta.getDrain_area())) ? Double.NaN : Double.valueOf(hbsta.getDrain_area()));
    ts.setProperty("contr_area", (DMIUtil.isMissing(hbsta.getContr_area()) || HydroBase_Util.isMissing(hbsta.getContr_area())) ? Double.NaN : Double.valueOf(hbsta.getContr_area()));
    ts.setProperty("source", hbsta.getSource());
    ts.setProperty("abbrev", hbsta.getAbbrev());
    ts.setProperty("transbsn", (DMIUtil.isMissing(hbsta.getTransbsn()) || HydroBase_Util.isMissing(hbsta.getTransbsn())) ? null : Integer.valueOf(hbsta.getTransbsn()));
    ts.setProperty("meas_num", (DMIUtil.isMissing(hbsta.getMeas_num()) || HydroBase_Util.isMissing(hbsta.getMeas_num())) ? null : Integer.valueOf(hbsta.getMeas_num()));
    ts.setProperty("meas_type", hbsta.getMeas_type());
    ts.setProperty("time_step", hbsta.getTime_step());
    ts.setProperty("start_year", (DMIUtil.isMissing(hbsta.getStart_year()) || HydroBase_Util.isMissing(hbsta.getStart_year())) ? null : Integer.valueOf(hbsta.getStart_year()));
    ts.setProperty("end_year", (DMIUtil.isMissing(hbsta.getEnd_year()) || HydroBase_Util.isMissing(hbsta.getEnd_year())) ? null : Integer.valueOf(hbsta.getEnd_year()));
    ts.setProperty("vax_field", hbsta.getVax_field());
    ts.setProperty("transmit", hbsta.getTransmit());
    ts.setProperty("meas_count", (DMIUtil.isMissing(hbsta.getMeas_count()) || HydroBase_Util.isMissing(hbsta.getMeas_count())) ? null : Integer.valueOf(hbsta.getMeas_count()));
    ts.setProperty("data_source", hbsta.getData_source());
    ts.setProperty("UTM_X", (DMIUtil.isMissing(hbsta.getUtm_x()) || HydroBase_Util.isMissing(hbsta.getUtm_x())) ? Double.NaN : Double.valueOf(hbsta.getUtm_x()));
    ts.setProperty("UTM_Y", (DMIUtil.isMissing(hbsta.getUtm_y()) || HydroBase_Util.isMissing(hbsta.getUtm_y())) ? Double.NaN : Double.valueOf(hbsta.getUtm_y()));
    ts.setProperty("longdecdeg", (DMIUtil.isMissing(hbsta.getLongdecdeg()) || HydroBase_Util.isMissing(hbsta.getLongdecdeg())) ? Double.NaN : Double.valueOf(hbsta.getLongdecdeg()));
    ts.setProperty("latdecdeg", (DMIUtil.isMissing(hbsta.getLatdecdeg()) || HydroBase_Util.isMissing(hbsta.getLatdecdeg())) ? Double.NaN : Double.valueOf(hbsta.getLatdecdeg()));
    ts.setProperty("div", (DMIUtil.isMissing(hbsta.getDiv()) || HydroBase_Util.isMissing(hbsta.getDiv())) ? null : Integer.valueOf(hbsta.getDiv()));
    ts.setProperty("wd", (DMIUtil.isMissing(hbsta.getWD()) || HydroBase_Util.isMissing(hbsta.getWD())) ? null : Integer.valueOf(hbsta.getWD()));
    ts.setProperty("county", hbsta.getCounty());
    ts.setProperty("topomap", hbsta.getTopomap());
    ts.setProperty("cty", (DMIUtil.isMissing(hbsta.getCty()) || HydroBase_Util.isMissing(hbsta.getCty())) ? null : Integer.valueOf(hbsta.getCty()));
    ts.setProperty("huc", hbsta.getHUC());
    ts.setProperty("elev", (DMIUtil.isMissing(hbsta.getElev()) || HydroBase_Util.isMissing(hbsta.getElev())) ? Double.NaN : Double.valueOf(hbsta.getElev()));
    ts.setProperty("loc_type", hbsta.getLoc_type());
    ts.setProperty("accuracy", (DMIUtil.isMissing(hbsta.getAccuracy()) || HydroBase_Util.isMissing(hbsta.getAccuracy())) ? null : Integer.valueOf(hbsta.getAccuracy()));
    ts.setProperty("st", hbsta.getST());
}

/**
Set time series properties from an HydroBase_StationView instance.
This allows the properties to be retrieved after the initial query.
@param ts time series to update
@param hbsta station data to use for time series properties
*/
public static void setTimeSeriesProperties ( TS ts, HydroBase_StationView hbsta ) {
    // Use the same names as the database view columns, same order as view.
    ts.setProperty("station_num", (DMIUtil.isMissing(hbsta.getStation_num()) || HydroBase_Util.isMissing(hbsta.getStation_num())) ? null : Integer.valueOf(hbsta.getStation_num()));
    ts.setProperty("geoloc_num", (DMIUtil.isMissing(hbsta.getGeoloc_num()) || HydroBase_Util.isMissing(hbsta.getGeoloc_num())) ? null : Integer.valueOf(hbsta.getGeoloc_num()));
    ts.setProperty("station_name", hbsta.getStation_name());
    ts.setProperty("station_id", hbsta.getStation_id());
    ts.setProperty("cooperator_id", hbsta.getCooperator_id());
    ts.setProperty("nesdis_id", hbsta.getNesdis_id());
    ts.setProperty("drain_area", (DMIUtil.isMissing(hbsta.getDrain_area()) || HydroBase_Util.isMissing(hbsta.getDrain_area())) ? Double.NaN : Double.valueOf(hbsta.getDrain_area()));
    ts.setProperty("contr_area", (DMIUtil.isMissing(hbsta.getContr_area()) || HydroBase_Util.isMissing(hbsta.getContr_area())) ? Double.NaN : Double.valueOf(hbsta.getContr_area()));
    ts.setProperty("source", hbsta.getSource());
    ts.setProperty("abbrev", hbsta.getAbbrev());
    ts.setProperty("transbsn", (DMIUtil.isMissing(hbsta.getTransbsn()) || HydroBase_Util.isMissing(hbsta.getTransbsn())) ? null : Integer.valueOf(hbsta.getTransbsn()));
    ts.setProperty("meas_num", (DMIUtil.isMissing(hbsta.getMeas_num()) || HydroBase_Util.isMissing(hbsta.getMeas_num())) ? null : Integer.valueOf(hbsta.getMeas_num()));
    ts.setProperty("meas_type", hbsta.getMeas_type());
    ts.setProperty("time_step", hbsta.getTime_step());
    ts.setProperty("start_year", (DMIUtil.isMissing(hbsta.getStart_year()) || HydroBase_Util.isMissing(hbsta.getStart_year())) ? null : Integer.valueOf(hbsta.getStart_year()));
    ts.setProperty("end_year", (DMIUtil.isMissing(hbsta.getEnd_year()) || HydroBase_Util.isMissing(hbsta.getEnd_year())) ? null : Integer.valueOf(hbsta.getEnd_year()));
    ts.setProperty("vax_field", hbsta.getVax_field());
    ts.setProperty("transmit", hbsta.getTransmit());
    ts.setProperty("meas_count", (DMIUtil.isMissing(hbsta.getMeas_count()) || HydroBase_Util.isMissing(hbsta.getMeas_count())) ? null : Integer.valueOf(hbsta.getMeas_count()));
    ts.setProperty("data_source", hbsta.getData_source());
    ts.setProperty("UTM_X", (DMIUtil.isMissing(hbsta.getUtm_x()) || HydroBase_Util.isMissing(hbsta.getUtm_x())) ? Double.NaN : Double.valueOf(hbsta.getUtm_x()));
    ts.setProperty("UTM_Y", (DMIUtil.isMissing(hbsta.getUtm_y()) || HydroBase_Util.isMissing(hbsta.getUtm_y())) ? Double.NaN : Double.valueOf(hbsta.getUtm_y()));
    ts.setProperty("longdecdeg", (DMIUtil.isMissing(hbsta.getLongdecdeg()) || HydroBase_Util.isMissing(hbsta.getLongdecdeg())) ? Double.NaN : Double.valueOf(hbsta.getLongdecdeg()));
    ts.setProperty("latdecdeg", (DMIUtil.isMissing(hbsta.getLatdecdeg()) || HydroBase_Util.isMissing(hbsta.getLatdecdeg())) ? Double.NaN : Double.valueOf(hbsta.getLatdecdeg()));
    ts.setProperty("div", (DMIUtil.isMissing(hbsta.getDiv()) || HydroBase_Util.isMissing(hbsta.getDiv())) ? null : Integer.valueOf(hbsta.getDiv()));
    ts.setProperty("wd", (DMIUtil.isMissing(hbsta.getWD()) || HydroBase_Util.isMissing(hbsta.getWD())) ? null : Integer.valueOf(hbsta.getWD()));
    ts.setProperty("county", hbsta.getCounty());
    ts.setProperty("topomap", hbsta.getTopomap());
    ts.setProperty("cty", (DMIUtil.isMissing(hbsta.getCty()) || HydroBase_Util.isMissing(hbsta.getCty())) ? null : Integer.valueOf(hbsta.getCty()));
    ts.setProperty("huc", hbsta.getHUC());
    ts.setProperty("elev", (DMIUtil.isMissing(hbsta.getElev()) || HydroBase_Util.isMissing(hbsta.getElev())) ? Double.NaN : Double.valueOf(hbsta.getElev()));
    ts.setProperty("loc_type", hbsta.getLoc_type());
    ts.setProperty("accuracy", (DMIUtil.isMissing(hbsta.getAccuracy()) || HydroBase_Util.isMissing(hbsta.getAccuracy())) ? null : Integer.valueOf(hbsta.getAccuracy()));
    ts.setProperty("st", hbsta.getST());
}

/**
Set time series properties from an HydroBase_StructureStructMeasType instance.
This allows the properties to be retrieved after the initial query.
@param ts time series to update
@param str_mt_v structure data to use for time series properties
@param sfutg2 SFUTG2 diversion coding, used to set SFUTG2 parts as individual properties to facilitate processing
*/
public static void setTimeSeriesProperties ( TS ts, HydroBase_StructMeasTypeView str_mt_v,
    HydroBase_SFUTG2 sfutg2 ) {
    // Use the same names as the database view columns, same order as view.
    ts.setProperty("div", (DMIUtil.isMissing(str_mt_v.getDiv()) || HydroBase_Util.isMissing(str_mt_v.getDiv())) ? null : Integer.valueOf(str_mt_v.getDiv()));
    ts.setProperty("wd", (DMIUtil.isMissing(str_mt_v.getWD()) || HydroBase_Util.isMissing(str_mt_v.getWD())) ? null : Integer.valueOf(str_mt_v.getWD()));
    ts.setProperty("id", (DMIUtil.isMissing(str_mt_v.getID()) || HydroBase_Util.isMissing(str_mt_v.getID())) ? null : Integer.valueOf(str_mt_v.getID()));
    ts.setProperty("wdid", DMIUtil.isMissing(str_mt_v.getWDID()) ? null : str_mt_v.getWDID());
    ts.setProperty("str_name", str_mt_v.getStr_name());
    ts.setProperty("str_type", str_mt_v.getStr_type());
    ts.setProperty("strtype", str_mt_v.getSTRTYPE());
    ts.setProperty("UTM_X", (DMIUtil.isMissing(str_mt_v.getUtm_x()) || HydroBase_Util.isMissing(str_mt_v.getUtm_x())) ? Double.NaN : Double.valueOf(str_mt_v.getUtm_x()));
    ts.setProperty("UTM_Y", (DMIUtil.isMissing(str_mt_v.getUtm_y()) || HydroBase_Util.isMissing(str_mt_v.getUtm_y())) ? Double.NaN : Double.valueOf(str_mt_v.getUtm_y()));
    ts.setProperty("longdecdeg", (DMIUtil.isMissing(str_mt_v.getLongdecdeg()) || HydroBase_Util.isMissing(str_mt_v.getLongdecdeg())) ? Double.NaN : Double.valueOf(str_mt_v.getLongdecdeg()));
    ts.setProperty("latdecdeg", (DMIUtil.isMissing(str_mt_v.getLatdecdeg()) || HydroBase_Util.isMissing(str_mt_v.getLatdecdeg())) ? Double.NaN : Double.valueOf(str_mt_v.getLatdecdeg()));
    ts.setProperty("pm", str_mt_v.getPM());
    ts.setProperty("ts", (DMIUtil.isMissing(str_mt_v.getTS()) || HydroBase_Util.isMissing(str_mt_v.getTS())) ? null : Integer.valueOf(str_mt_v.getTS()));
    ts.setProperty("tdir", str_mt_v.getTdir());
    ts.setProperty("tsa", str_mt_v.getTsa());
    ts.setProperty("rng", (DMIUtil.isMissing(str_mt_v.getRng()) || HydroBase_Util.isMissing(str_mt_v.getRng())) ? null : Integer.valueOf(str_mt_v.getRng()));
    ts.setProperty("rdir", str_mt_v.getRdir());
    ts.setProperty("rnga", str_mt_v.getRnga());
    ts.setProperty("sec", (DMIUtil.isMissing(str_mt_v.getSec()) || HydroBase_Util.isMissing(str_mt_v.getSec())) ? null : Integer.valueOf(str_mt_v.getSec()));
    ts.setProperty("seca", str_mt_v.getSeca());
    ts.setProperty("q160", str_mt_v.getQ160());
    ts.setProperty("q40", str_mt_v.getQ40());
    ts.setProperty("q10", str_mt_v.getQ10());
    ts.setProperty("coordsns", (DMIUtil.isMissing(str_mt_v.getCoordsns()) || HydroBase_Util.isMissing(str_mt_v.getCoordsns())) ? null : Integer.valueOf(str_mt_v.getCoordsns()));
    ts.setProperty("coordsns_dir", str_mt_v.getCoordsns_dir());
    ts.setProperty("coordsew", (DMIUtil.isMissing(str_mt_v.getCoordsew()) || HydroBase_Util.isMissing(str_mt_v.getCoordsew())) ? null : Integer.valueOf(str_mt_v.getCoordsew()));
    ts.setProperty("coordsew_dir", str_mt_v.getCoordsew_dir());
    ts.setProperty("county", str_mt_v.getCounty());
    ts.setProperty("topomap", str_mt_v.getTopomap());
    ts.setProperty("cty", (DMIUtil.isMissing(str_mt_v.getCty()) || HydroBase_Util.isMissing(str_mt_v.getCty())) ? null : Integer.valueOf(str_mt_v.getCty()));
    ts.setProperty("huc", str_mt_v.getHUC());
    ts.setProperty("elev", (DMIUtil.isMissing(str_mt_v.getElev()) || HydroBase_Util.isMissing(str_mt_v.getElev())) ? Double.NaN : Double.valueOf(str_mt_v.getElev()));
    ts.setProperty("loc_type", str_mt_v.getLoc_type());
    ts.setProperty("st", str_mt_v.getST());
    ts.setProperty("str_mile", (DMIUtil.isMissing(str_mt_v.getStr_mile()) || HydroBase_Util.isMissing(str_mt_v.getStr_mile())) ? null : Double.valueOf(str_mt_v.getStr_mile()));
    ts.setProperty("meas_num", (DMIUtil.isMissing(str_mt_v.getMeas_num()) || HydroBase_Util.isMissing(str_mt_v.getMeas_num())) ? null : Integer.valueOf(str_mt_v.getMeas_num()));
    ts.setProperty("structure_num", (DMIUtil.isMissing(str_mt_v.getStructure_num()) || HydroBase_Util.isMissing(str_mt_v.getStructure_num())) ? null : Integer.valueOf(str_mt_v.getStructure_num()));
    ts.setProperty("meas_type", str_mt_v.getMeas_type());
    ts.setProperty("time_step", str_mt_v.getTime_step());
    ts.setProperty("start_year", (DMIUtil.isMissing(str_mt_v.getStart_year()) || HydroBase_Util.isMissing(str_mt_v.getStart_year())) ? null : Integer.valueOf(str_mt_v.getStart_year()));
    ts.setProperty("end_year", (DMIUtil.isMissing(str_mt_v.getEnd_year()) || HydroBase_Util.isMissing(str_mt_v.getEnd_year())) ? null : Integer.valueOf(str_mt_v.getEnd_year()));
    ts.setProperty("identifier", str_mt_v.getIdentifier());
    if ( sfutg2 != null ) {
        ts.setProperty("identifier_S", sfutg2.getSource());
        ts.setProperty("identifier_F", sfutg2.getFrom());
        ts.setProperty("identifier_U", sfutg2.getUse());
        ts.setProperty("identifier_T", sfutg2.getType());
        ts.setProperty("identifier_G", sfutg2.getGroup());
        ts.setProperty("identifier_2", sfutg2.getTo());
    }
    ts.setProperty("transmit", str_mt_v.getTransmit());
    ts.setProperty("meas_count", (DMIUtil.isMissing(str_mt_v.getMeas_count()) || HydroBase_Util.isMissing(str_mt_v.getMeas_count())) ? null : Integer.valueOf(str_mt_v.getMeas_count()));
    ts.setProperty("data_source", str_mt_v.getData_source());
}

/**
Set time series properties from an HydroBase_StructureStructMeasType instance.
This allows the properties to be retrieved after the initial query.
@param ts time series to update
@param str_mt_v structure data to use for time series properties
*/
public static void setTimeSeriesProperties ( TS ts, HydroBase_StructureGeolocStructMeasType str_mt_v ) {
    // Use the same names as the database view columns, same order as view.
    // Below same as HydroBase_StructMeasType.
    ts.setProperty("div", (DMIUtil.isMissing(str_mt_v.getDiv()) || HydroBase_Util.isMissing(str_mt_v.getDiv())) ? null : Integer.valueOf(str_mt_v.getDiv()));
    ts.setProperty("wd", (DMIUtil.isMissing(str_mt_v.getWD()) || HydroBase_Util.isMissing(str_mt_v.getWD())) ? null : Integer.valueOf(str_mt_v.getWD()));
    ts.setProperty("id", (DMIUtil.isMissing(str_mt_v.getID()) || HydroBase_Util.isMissing(str_mt_v.getID())) ? null : Integer.valueOf(str_mt_v.getID()));
    ts.setProperty("wdid", DMIUtil.isMissing(str_mt_v.getWDID()) ? null : str_mt_v.getWDID());
    ts.setProperty("str_name", str_mt_v.getStr_name());
    ts.setProperty("str_type", str_mt_v.getStr_type());
    ts.setProperty("strtype", str_mt_v.getSTRTYPE());
    ts.setProperty("UTM_X", (DMIUtil.isMissing(str_mt_v.getUtm_x()) || HydroBase_Util.isMissing(str_mt_v.getUtm_x())) ? Double.NaN : Double.valueOf(str_mt_v.getUtm_x()));
    ts.setProperty("UTM_Y", (DMIUtil.isMissing(str_mt_v.getUtm_y()) || HydroBase_Util.isMissing(str_mt_v.getUtm_y())) ? Double.NaN : Double.valueOf(str_mt_v.getUtm_y()));
    ts.setProperty("longdecdeg", (DMIUtil.isMissing(str_mt_v.getLongdecdeg()) || HydroBase_Util.isMissing(str_mt_v.getLongdecdeg())) ? Double.NaN : Double.valueOf(str_mt_v.getLongdecdeg()));
    ts.setProperty("latdecdeg", (DMIUtil.isMissing(str_mt_v.getLatdecdeg()) || HydroBase_Util.isMissing(str_mt_v.getLatdecdeg())) ? Double.NaN : Double.valueOf(str_mt_v.getLatdecdeg()));
    ts.setProperty("pm", str_mt_v.getPM());
    ts.setProperty("ts", (DMIUtil.isMissing(str_mt_v.getTS()) || HydroBase_Util.isMissing(str_mt_v.getTS())) ? null : Integer.valueOf(str_mt_v.getTS()));
    ts.setProperty("tdir", str_mt_v.getTdir());
    ts.setProperty("tsa", str_mt_v.getTsa());
    ts.setProperty("rng", (DMIUtil.isMissing(str_mt_v.getRng()) || HydroBase_Util.isMissing(str_mt_v.getRng())) ? null : Integer.valueOf(str_mt_v.getRng()));
    ts.setProperty("rdir", str_mt_v.getRdir());
    ts.setProperty("rnga", str_mt_v.getRnga());
    ts.setProperty("sec", (DMIUtil.isMissing(str_mt_v.getSec()) || HydroBase_Util.isMissing(str_mt_v.getSec())) ? null : Integer.valueOf(str_mt_v.getSec()));
    ts.setProperty("seca", str_mt_v.getSeca());
    ts.setProperty("q160", str_mt_v.getQ160());
    ts.setProperty("q40", str_mt_v.getQ40());
    ts.setProperty("q10", str_mt_v.getQ10());
    ts.setProperty("coordsns", (DMIUtil.isMissing(str_mt_v.getCoordsns()) || HydroBase_Util.isMissing(str_mt_v.getCoordsns())) ? null : Integer.valueOf(str_mt_v.getCoordsns()));
    ts.setProperty("coordsns_dir", str_mt_v.getCoordsns_dir());
    ts.setProperty("coordsew", (DMIUtil.isMissing(str_mt_v.getCoordsew()) || HydroBase_Util.isMissing(str_mt_v.getCoordsew())) ? null : Integer.valueOf(str_mt_v.getCoordsew()));
    ts.setProperty("coordsew_dir", str_mt_v.getCoordsew_dir());
    ts.setProperty("county", str_mt_v.getCounty());
    ts.setProperty("topomap", str_mt_v.getTopomap());
    ts.setProperty("cty", (DMIUtil.isMissing(str_mt_v.getCty()) || HydroBase_Util.isMissing(str_mt_v.getCty())) ? null : Integer.valueOf(str_mt_v.getCty()));
    ts.setProperty("huc", str_mt_v.getHUC());
    ts.setProperty("elev", (DMIUtil.isMissing(str_mt_v.getElev()) || HydroBase_Util.isMissing(str_mt_v.getElev())) ? Double.NaN : Double.valueOf(str_mt_v.getElev()));
    ts.setProperty("loc_type", str_mt_v.getLoc_type());
    ts.setProperty("st", str_mt_v.getST());
    ts.setProperty("str_mile", (DMIUtil.isMissing(str_mt_v.getStr_mile()) || HydroBase_Util.isMissing(str_mt_v.getStr_mile())) ? null : Double.valueOf(str_mt_v.getStr_mile()));
    ts.setProperty("meas_num", (DMIUtil.isMissing(str_mt_v.getMeas_num()) || HydroBase_Util.isMissing(str_mt_v.getMeas_num())) ? null : Integer.valueOf(str_mt_v.getMeas_num()));
    ts.setProperty("structure_num", (DMIUtil.isMissing(str_mt_v.getStructure_num()) || HydroBase_Util.isMissing(str_mt_v.getStructure_num())) ? null : Integer.valueOf(str_mt_v.getStructure_num()));
    ts.setProperty("meas_type", str_mt_v.getMeas_type());
    ts.setProperty("time_step", str_mt_v.getTime_step());
    ts.setProperty("start_year", (DMIUtil.isMissing(str_mt_v.getStart_year()) || HydroBase_Util.isMissing(str_mt_v.getStart_year())) ? null : Integer.valueOf(str_mt_v.getStart_year()));
    ts.setProperty("end_year", (DMIUtil.isMissing(str_mt_v.getEnd_year()) || HydroBase_Util.isMissing(str_mt_v.getEnd_year())) ? null : Integer.valueOf(str_mt_v.getEnd_year()));
    ts.setProperty("identifier", str_mt_v.getIdentifier());
    ts.setProperty("transmit", str_mt_v.getTransmit());
    ts.setProperty("meas_count", (DMIUtil.isMissing(str_mt_v.getMeas_count()) || HydroBase_Util.isMissing(str_mt_v.getMeas_count())) ? null : Integer.valueOf(str_mt_v.getMeas_count()));
    ts.setProperty("data_source", str_mt_v.getData_source());
}

/**
Set time series properties from an HydroBase_StructureStructMeasType instance.
This allows the properties to be retrieved after the initial query.
@param ts time series to update
@param hbstr structure data to use for time series properties
*/
public static void setTimeSeriesProperties ( TS ts, HydroBase_StructureView hbstr ) {
    // Use the same names as the database view columns, same order as view.
    // Below same as HydroBase_StructMeasType.
    ts.setProperty("div", (DMIUtil.isMissing(hbstr.getDiv()) || HydroBase_Util.isMissing(hbstr.getDiv())) ? null : Integer.valueOf(hbstr.getDiv()));
    ts.setProperty("wd", (DMIUtil.isMissing(hbstr.getWD()) || HydroBase_Util.isMissing(hbstr.getWD())) ? null : Integer.valueOf(hbstr.getWD()));
    ts.setProperty("id", (DMIUtil.isMissing(hbstr.getID()) || HydroBase_Util.isMissing(hbstr.getID())) ? null : Integer.valueOf(hbstr.getID()));
    ts.setProperty("wdid", DMIUtil.isMissing(hbstr.getWDID()) ? null : hbstr.getWDID());
    ts.setProperty("str_name", hbstr.getStr_name());
    ts.setProperty("str_type", hbstr.getStr_type());
    ts.setProperty("strtype", hbstr.getSTRTYPE());
    ts.setProperty("UTM_X", (DMIUtil.isMissing(hbstr.getUtm_x()) || HydroBase_Util.isMissing(hbstr.getUtm_x())) ? Double.NaN : Double.valueOf(hbstr.getUtm_x()));
    ts.setProperty("UTM_Y", (DMIUtil.isMissing(hbstr.getUtm_y()) || HydroBase_Util.isMissing(hbstr.getUtm_y())) ? Double.NaN : Double.valueOf(hbstr.getUtm_y()));
    ts.setProperty("longdecdeg", (DMIUtil.isMissing(hbstr.getLongdecdeg()) || HydroBase_Util.isMissing(hbstr.getLongdecdeg())) ? Double.NaN : Double.valueOf(hbstr.getLongdecdeg()));
    ts.setProperty("latdecdeg", (DMIUtil.isMissing(hbstr.getLatdecdeg()) || HydroBase_Util.isMissing(hbstr.getLatdecdeg())) ? Double.NaN : Double.valueOf(hbstr.getLatdecdeg()));
    ts.setProperty("pm", hbstr.getPM());
    ts.setProperty("ts", (DMIUtil.isMissing(hbstr.getTS()) || HydroBase_Util.isMissing(hbstr.getTS())) ? null : Integer.valueOf(hbstr.getTS()));
    ts.setProperty("tdir", hbstr.getTdir());
    ts.setProperty("tsa", hbstr.getTsa());
    ts.setProperty("rng", (DMIUtil.isMissing(hbstr.getRng()) || HydroBase_Util.isMissing(hbstr.getRng())) ? null : Integer.valueOf(hbstr.getRng()));
    ts.setProperty("rdir", hbstr.getRdir());
    ts.setProperty("rnga", hbstr.getRnga());
    ts.setProperty("sec", (DMIUtil.isMissing(hbstr.getSec()) || HydroBase_Util.isMissing(hbstr.getSec())) ? null : Integer.valueOf(hbstr.getSec()));
    ts.setProperty("seca", hbstr.getSeca());
    ts.setProperty("q160", hbstr.getQ160());
    ts.setProperty("q40", hbstr.getQ40());
    ts.setProperty("q10", hbstr.getQ10());
    ts.setProperty("coordsns", (DMIUtil.isMissing(hbstr.getCoordsns()) || HydroBase_Util.isMissing(hbstr.getCoordsns())) ? null : Integer.valueOf(hbstr.getCoordsns()));
    ts.setProperty("coordsns_dir", hbstr.getCoordsns_dir());
    ts.setProperty("coordsew", (DMIUtil.isMissing(hbstr.getCoordsew()) || HydroBase_Util.isMissing(hbstr.getCoordsew())) ? null : Integer.valueOf(hbstr.getCoordsew()));
    ts.setProperty("coordsew_dir", hbstr.getCoordsew_dir());
    ts.setProperty("county", hbstr.getCounty());
    ts.setProperty("topomap", hbstr.getTopomap());
    ts.setProperty("cty", (DMIUtil.isMissing(hbstr.getCty()) || HydroBase_Util.isMissing(hbstr.getCty())) ? null : Integer.valueOf(hbstr.getCty()));
    ts.setProperty("huc", hbstr.getHUC());
    ts.setProperty("elev", (DMIUtil.isMissing(hbstr.getElev()) || HydroBase_Util.isMissing(hbstr.getElev())) ? Double.NaN : Double.valueOf(hbstr.getElev()));
    ts.setProperty("loc_type", hbstr.getLoc_type());
    ts.setProperty("st", hbstr.getST());
    ts.setProperty("str_mile", (DMIUtil.isMissing(hbstr.getStr_mile()) || HydroBase_Util.isMissing(hbstr.getStr_mile())) ? null : Double.valueOf(hbstr.getStr_mile()));
    ts.setProperty("structure_num", (DMIUtil.isMissing(hbstr.getStructure_num()) || HydroBase_Util.isMissing(hbstr.getStructure_num())) ? null : Integer.valueOf(hbstr.getStructure_num()));
}

/**
Set time series properties from an HydroBase_GroundWaterWellsView instance.
This allows the properties to be retrieved after the initial query.
@param ts time series to update
@param well well data to use for time series properties
*/
public static void setTimeSeriesProperties ( TS ts, HydroBase_GroundWaterWellsView well ) {
    // Use the same names as the database view columns, same order as view
    // tsid_loc is not in the original data but is a derived value based on other data.
    String tsloc = ts.getIdentifier().getLocation();
    ts.setProperty("tsid_loc", DMIUtil.isMissing(tsloc)? null : tsloc);
    if ( tsloc.toUpperCase().startsWith("LL:") ) {
        // TODO SAM 2013-02-10 This is needed because of bad well data in HydroBase,
    	// which triggers an artificial identifier starting with LL: followed by latitude and longitude.
        // The tsid_loc_file property without the colon is useful for filenames since the colon will be
        // problematic on Windows systems.
        ts.setProperty("tsid_loc_file", DMIUtil.isMissing(tsloc)? null : tsloc.replace(":", "") );
    }
    else {
        ts.setProperty("tsid_loc_file", DMIUtil.isMissing(tsloc)? null : tsloc );
    }
    ts.setProperty("well_num", (DMIUtil.isMissing(well.getWell_num()) || HydroBase_Util.isMissing(well.getWell_num())) ? null : Integer.valueOf(well.getWell_num()));
    ts.setProperty("well_name", well.getWell_name());
    ts.setProperty("div", (DMIUtil.isMissing(well.getDiv()) || HydroBase_Util.isMissing(well.getDiv())) ? null : Integer.valueOf(well.getDiv()));
    ts.setProperty("wd", (DMIUtil.isMissing(well.getWD()) || HydroBase_Util.isMissing(well.getWD())) ? null : Integer.valueOf(well.getWD()));
    ts.setProperty("id", (DMIUtil.isMissing(well.getID()) || HydroBase_Util.isMissing(well.getID())) ? null : Integer.valueOf(well.getID()));
    ts.setProperty("receipt", well.getReceipt());
    ts.setProperty("permitno", (DMIUtil.isMissing(well.getPermitno()) || HydroBase_Util.isMissing(well.getPermitno())) ? null : Integer.valueOf(well.getPermitno()));
    ts.setProperty("permitsuf", well.getPermitsuf());
    ts.setProperty("permitrpl", well.getPermitrpl());
    ts.setProperty("locnum", well.getLoc_num());
    ts.setProperty("Site_ID", well.getSite_id());
    ts.setProperty("basin", well.getBasin());
    ts.setProperty("md", well.getMD());
    ts.setProperty("well_depth", (DMIUtil.isMissing(well.getWell_depth()) || HydroBase_Util.isMissing(well.getWell_depth())) ? Double.NaN : Double.valueOf(well.getWell_depth()));
    ts.setProperty("aquifer1", well.getAquifer1());
    ts.setProperty("aquifer2", well.getAquifer2());
    ts.setProperty("aquifer_comment", well.getAquifer_comment());
    ts.setProperty("tperf", (DMIUtil.isMissing(well.getTperf()) || HydroBase_Util.isMissing(well.getTperf())) ? null : Integer.valueOf(well.getTperf()));
    ts.setProperty("bperf", (DMIUtil.isMissing(well.getBperf()) || HydroBase_Util.isMissing(well.getBperf())) ? null : Integer.valueOf(well.getBperf()));
    ts.setProperty("yield", (DMIUtil.isMissing(well.getYield()) || HydroBase_Util.isMissing(well.getYield())) ? Double.NaN : Double.valueOf(well.getYield()));
    ts.setProperty("bedrock_elev", (DMIUtil.isMissing(well.getBedrock_elev()) || HydroBase_Util.isMissing(well.getBedrock_elev())) ? Double.NaN : Double.valueOf(well.getBedrock_elev()));
    ts.setProperty("sat_1965", (DMIUtil.isMissing(well.getSat_1965()) || HydroBase_Util.isMissing(well.getSat_1965())) ? Double.NaN : Double.valueOf(well.getSat_1965()));
    ts.setProperty("remarks1", well.getRemarks1());
    ts.setProperty("remarks2", well.getRemarks2());
    ts.setProperty("well_meas_num", (DMIUtil.isMissing(well.getWell_meas_num()) || HydroBase_Util.isMissing(well.getWell_meas_num())) ? null : Integer.valueOf(well.getWell_meas_num()));
    ts.setProperty("meas_type", well.getMeas_type());
    ts.setProperty("time_step", well.getTime_step());
    ts.setProperty("start_year", (DMIUtil.isMissing(well.getStart_year()) || HydroBase_Util.isMissing(well.getStart_year())) ? null : Integer.valueOf(well.getStart_year()));
    ts.setProperty("end_year", (DMIUtil.isMissing(well.getEnd_year()) || HydroBase_Util.isMissing(well.getEnd_year())) ? null : Integer.valueOf(well.getEnd_year()));
    ts.setProperty("meas_count", (DMIUtil.isMissing(well.getMeas_count()) || HydroBase_Util.isMissing(well.getMeas_count())) ? null : Integer.valueOf(well.getMeas_count()));
    ts.setProperty("identifier", well.getIdentifier());
    ts.setProperty("data_source_id", well.getData_source_id());
    ts.setProperty("data_source", well.getData_source());
    ts.setProperty("UTM_X", (DMIUtil.isMissing(well.getUtm_x()) || HydroBase_Util.isMissing(well.getUtm_x())) ? Double.NaN : Double.valueOf(well.getUtm_x()));
    ts.setProperty("UTM_Y", (DMIUtil.isMissing(well.getUtm_y()) || HydroBase_Util.isMissing(well.getUtm_y())) ? Double.NaN : Double.valueOf(well.getUtm_y()));
    ts.setProperty("longdecdeg", (DMIUtil.isMissing(well.getLongdecdeg()) || HydroBase_Util.isMissing(well.getLongdecdeg())) ? Double.NaN : Double.valueOf(well.getLongdecdeg()));
    ts.setProperty("latdecdeg", (DMIUtil.isMissing(well.getLatdecdeg()) || HydroBase_Util.isMissing(well.getLatdecdeg())) ? Double.NaN : Double.valueOf(well.getLatdecdeg()));
    ts.setProperty("pm", well.getPM());
    ts.setProperty("ts", (DMIUtil.isMissing(well.getTS()) || HydroBase_Util.isMissing(well.getTS())) ? null : Integer.valueOf(well.getTS()));
    ts.setProperty("tdir", well.getTdir());
    ts.setProperty("tsa", well.getTsa());
    ts.setProperty("rng", (DMIUtil.isMissing(well.getRng()) || HydroBase_Util.isMissing(well.getRng())) ? null : Integer.valueOf(well.getRng()));
    ts.setProperty("rdir", well.getRdir());
    ts.setProperty("rnga", well.getRnga());
    ts.setProperty("sec", (DMIUtil.isMissing(well.getSec()) || HydroBase_Util.isMissing(well.getSec())) ? null : Integer.valueOf(well.getSec()));
    ts.setProperty("seca", well.getSeca());
    ts.setProperty("q160", well.getQ160());
    ts.setProperty("q40", well.getQ40());
    ts.setProperty("q10", well.getQ10());
    ts.setProperty("coordsns", (DMIUtil.isMissing(well.getCoordsns()) || HydroBase_Util.isMissing(well.getCoordsns())) ? null : Integer.valueOf(well.getCoordsns()));
    ts.setProperty("coordsns_dir", well.getCoordsns_dir());
    ts.setProperty("coordsew", (DMIUtil.isMissing(well.getCoordsew()) || HydroBase_Util.isMissing(well.getCoordsew())) ? null : Integer.valueOf(well.getCoordsew()));
    ts.setProperty("coordsew_dir", well.getCoordsew_dir());
    ts.setProperty("county", well.getCounty());
    ts.setProperty("topomap", well.getTopomap());
    ts.setProperty("cty", (DMIUtil.isMissing(well.getCty()) || HydroBase_Util.isMissing(well.getCty())) ? null : Integer.valueOf(well.getCty()));
    ts.setProperty("huc", well.getHUC());
    ts.setProperty("elev", (DMIUtil.isMissing(well.getElev()) || HydroBase_Util.isMissing(well.getElev())) ? Double.NaN : Double.valueOf(well.getElev()));
    ts.setProperty("elev_accuracy", (DMIUtil.isMissing(well.getElev_accuracy()) || HydroBase_Util.isMissing(well.getElev_accuracy())) ? null : Double.valueOf(well.getElev_accuracy()));
    ts.setProperty("loc_type", well.getLoc_type());
    ts.setProperty("loc_accuracy", (DMIUtil.isMissing(well.getLoc_accuracy()) || HydroBase_Util.isMissing(well.getLoc_accuracy())) ? null : Integer.valueOf(well.getLoc_accuracy()));
    ts.setProperty("st", well.getST());
    ts.setProperty("stream_num", (DMIUtil.isMissing(well.getStream_num()) || HydroBase_Util.isMissing(well.getStream_num())) ? null : Integer.valueOf(well.getStream_num()));
    ts.setProperty("str_mile", (DMIUtil.isMissing(well.getStr_mile()) || HydroBase_Util.isMissing(well.getStr_mile())) ? null : Double.valueOf(well.getStr_mile()));
    ts.setProperty("spotter_version", well.getSpotter_version());
    ts.setProperty("DSS_aquifer1", well.getDSS_aquifer1());
    ts.setProperty("DSS_aquifer2", well.getDSS_aquifer2());
    ts.setProperty("DSS_aquifer_comment", well.getDSS_aquifer_comment());
}

}