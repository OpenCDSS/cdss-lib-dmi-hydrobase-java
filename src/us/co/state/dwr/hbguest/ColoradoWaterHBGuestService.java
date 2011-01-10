package us.co.state.dwr.hbguest;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.xml.ws.Holder;

import DWR.DMI.HydroBaseDMI.HydroBaseDMI;
import DWR.DMI.HydroBaseDMI.HydroBase_StationGeolocMeasType;
import DWR.DMI.HydroBaseDMI.HydroBase_StructMeasTypeView;
import DWR.DMI.HydroBaseDMI.HydroBase_StructureGeolocStructMeasType;
import DWR.DMI.HydroBaseDMI.HydroBase_Util;
import DWR.DMI.HydroBaseDMI.HydroBase_WaterDistrict;
import DWR.DMI.HydroBaseDMI.HydroBase_WaterDivision;
import RTi.TS.DayTS;
import RTi.TS.TS;
import RTi.TS.TSIdent;
import RTi.TS.TSUtil;
import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.Message.Message;
import RTi.Util.String.StringUtil;
import RTi.Util.Time.DateTime;
import RTi.Util.Time.StopWatch;

/**
This class extends the auto-generated ColoradoWaterHBGuest SOAP web service class and adds
caches and methods to access the caches.  In this way a service object and its associated caches can
be reused and separate caches can exist for different services (for example if different versions of
the services and back-end databases are available).  Because the caches are non-static and could get
large, care should be taken to reuse the service object as much as possible.  This class has public get methods
for data and private read methods to utilize the SOAP API.  In this way, the API is more service-like with
get methods.  HydroBase package objects are returned in some cases to facilitate use in existing code such
as TSTool, although the ColoradoWaterHBGuest object types will be evaluated with use.
*/
public class ColoradoWaterHBGuestService extends ColoradoWaterHBGuest
{
    
/**
Constructor.
*/
public ColoradoWaterHBGuestService ()
{
    super();
    // Turn on whether to dump requests...
    //System.setProperty( "com.sun.xml.ws.transport.http.HttpAdapter.dump", "true" );
}

/**
Class name, to optimize messaging.
*/
private String __class = getClass().getName();
    
/**
Authentication object to use when using the service.  The authentication information is primarily
used to know which user/application is accessing the service.  However, because the service is
read-only, there is currently no need to make the user provide credentials when using the software.
*/
private HBAuthenticationHeader __authenticationHeader = null;

/**
Cache of distinct station data types from the service (e.g., "Streamflow").
*/
private List<String> __stationDataTypeListCache = new Vector();

/**
Singleton for the once instance of this class.  In the future a more complex approach will be used
to allow multiple instances, for example pointing to different service providers (e.g., different
versions of the database).
*/
private static ColoradoWaterHBGuestService __serviceSingleton = new ColoradoWaterHBGuestService();

/**
Cache of station measType objects.
*/
private List<HydroBase_StationGeolocMeasType> __stationMeasTypeListCache = new Vector();

/**
Cache of distinct structure data types (e.g., "DivTotal"), 
*/
private List<String> __structureDataTypeListCache = new Vector();

/**
Cache of HydroBase_StructureGeolocStructMeasType objects, with the key being MeasType + "-" + interval + "-" + wd,
where the interval is generic "Year" and NOT the internal HydroBase interval like "Annual".
Combine the lists if getting information for more than one district.
*/
private Hashtable<String,List<HydroBase_StructureGeolocStructMeasType>> __structureGeolocMeasTypeByWDListCache = new Hashtable();

/**
Cache of HydroBase water districts from the service.
*/
private List<HydroBase_WaterDistrict> __waterDistrictListCache = new Vector();

/**
Cache of HydroBase water divisions from the service.
*/
private List<HydroBase_WaterDivision> __waterDivisionListCache = new Vector();

/**
Filter a List of HydroBase_StructureGeolocStructMeasType according to the input filter.  This is used
in queries.  The District and Division have already been accounted for.
*/
private void filterStructureGeolocMeasType ( List<HydroBase_StructureGeolocStructMeasType>mtList,
    InputFilter_JPanel ifp )
{
    List<InputFilter> constraintList1 = ifp.getInputFilters("Structure ID");
    Message.printStatus(2, "", "Have " + constraintList1.size() + " Structure ID constraints");
    List<InputFilter> constraintList2 = ifp.getInputFilters("Structure Name");
    Message.printStatus(2, "", "Have " + constraintList2.size() + " Structure Name constraints");
    String [] tokens; // tokens in input filter constraint
    String valueString; // Value in constraint (string)
    int valueInt; // Value in constraint (integer)
    String operatorString; // operator string in constraint
    HydroBase_StructureGeolocStructMeasType mt;
    for ( int i = 0; i < mtList.size(); i++ ) {
        mt = mtList.get(i);
        /*
        for ( InputFilter constraint : constraintList1 ) {
            tokens = constraint.split(";");
            valueString = tokens[1];
            valueInt = Integer.parseInt ( valueString );
            operatorString = tokens[0];
            if ( !InputFilter.matches(mt.getID(), operatorString, valueInt ) ) {
                // Data record does not match so remove from list and continue comparing objects.
                mtList.remove(mt);
                continue;
            }
        }
        */
        /* 
        for ( InputFilter constraint : constraintList2 ) {
            // TODO SAM 2010-08-16 Need to get the operator dynamically
            operatorString = "Contains";
            if ( !constraint.matches(mt.getStr_name(), operatorString, true ) ) {
                // Data record does not match so remove from list and continue comparing objects.
                mtList.remove(i);
                --i;
                continue;
            }
        }
        */
    }
}

/**
Get the authentication header.
*/
public HBAuthenticationHeader getAuthentication ()
{
    if ( __authenticationHeader == null ) {
        __authenticationHeader = new HBAuthenticationHeader();
        __authenticationHeader.setToken ("WirQg1zN"); // OK to put here as purpose is to track use
        // User ID is defaulted to 0
        //Message.printStatus(2, "", "UserID=" + __authenticationHeader.getUserID() );
    }
    return __authenticationHeader;
}

// TODO SAM 2010-07-21 Need to evaluate how to manage multiple instances (e.g., using a hash with the
// root URL as the key?).
/**
Return the singleton instance of this class.
*/
public static ColoradoWaterHBGuestService getService()
{
    return __serviceSingleton;
}

/**
Return the cached list of station data types, for all meas types.
*/
public List<String> getStationDataTypeListCache ()
{
    return __stationDataTypeListCache;
}

/**
Return the cached list of station measType objects, for all time series.
*/
public List<HydroBase_StationGeolocMeasType> getStationMeasTypeListCache ()
{
    return __stationMeasTypeListCache;
}

/**
Return the cached list of structure data types, for all meas types.
*/
public List<String> getStructureDataTypeList ()
{
    return __structureDataTypeListCache;
}

/**
Determine the hash key for the StructureGeolocMeasTypeList caches.
*/
private String getStructureGeolocMeasTypeByWDListCacheKey ( String dataType, String timeStep, int wd )
{   // Make sure that the water district is zero padded.
    String wdString = "" + wd;
    if ( wd < 10 ) {
        wdString = "0" + wd;
    }
    return dataType.toUpperCase() + "-" + timeStep.toUpperCase() + "-" + wdString;
}

/**
This essentially matches HydroBase; however, only time series that have been tested are enabled.
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
public List<String> getTimeSeriesDataTypes ( int include_types, boolean add_group )
{   List<String> types = new Vector();
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
    /* Not yet supported
    if ( (include_types&HydroBase_Util.DATA_TYPE_HARDWARE) > 0 ) {
        // RT_Misc BATTERY
        prefix = "";
        if ( add_group ) {
            prefix = "Hardware - ";
        }
        types.add ( prefix + "Battery" );
    }
    */
    /* Not yet supported
    if ( (include_types&HydroBase_Util.DATA_TYPE_STATION_CLIMATE) > 0 ) {
        prefix = "";
        if ( add_group ) {
            prefix = "Climate - ";
        }
        //types.addElement ( "Evap" ); // Maybe should be "EvapPan" or "PanEvap"
                            // Time step:  Day and Month
        types.add ( prefix + "EvapPan" );   // Proposed
        //types.addElement ( "FrostDate" ); // Need something like
                            // FrostDateF28,
                            // FrostDateF32,
                            // FrostDateS28,
                            // FrostDateS32
                            // so each time series can be manipulated.
                            // Time step:  Year
        types.add ( prefix + "FrostDateF32F" ); // Proposed
        types.add ( prefix + "FrostDateF28F" ); // Proposed
        types.add ( prefix + "FrostDateL28S" ); // Proposed
        types.add ( prefix + "FrostDateL32S" ); // Proposed
        //types.add ( "MaxTemp" );  // Perhaps should have:
        //types.add ( "MinTemp" );  // "TempMax", "TempMin",
        //types.add ( "MeanTemp" ); // for daily and
                            // "TempMean",
                            // "TempMeanMax",
                            // "TempMeanMin" for
                            // monthly - it gets
                            // messy.
        types.add ( prefix + "Precip" ); // Time step:  Month, Day, real-time with vax_field PRECIP
        types.add ( prefix + "Temp" ); // Proposed for use with real-time AIRTEMP vax_field
        types.add ( prefix + "TempMax" );// Proposed
        types.add ( prefix + "TempMean" );  // Proposed
        types.add ( prefix + "TempMeanMax" );   // Proposed
        types.add ( prefix + "TempMeanMin" );   // Proposed
        types.add ( prefix + "TempMin" );   // Proposed
        types.add ( prefix + "Snow" );  // Time step:  Day, Month
        //types.add ( "SnowCrse" ); // "SnowDepth" and
                            // "SnowWaterEquivalent"
                            // would be better, but
                            // need to check how
                            // depth compares to
                            // "Snow" type
                            // Time step:
                            // Day or IrregDay?
        types.add ( prefix + "SnowCourseDepth" );// Proposed
        types.add ( prefix + "SnowCourseSWE" ); // Proposed
        types.add ( prefix + "Solar" ); // Time step:  Day
        //types.add ( "VP" );       // Maybe VaporPressure
        types.add ( prefix + "VaporPressure" ); 
                            // Maybe VaporPressure
                            // is better.
                            // Time step:  Day
        types.add ( prefix + "Wind" );  // OK but maybe
                            // WindTravel or similar
                            // is better (some gages
                            // measure wind
                            // direction)?
                            // Time step:  Day
    }
    */
    /* Not yet supported
    if ( (include_types&HydroBase_Util.DATA_TYPE_STATION_STREAM) > 0 ) {
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
        //types.add ( "Nat_flow" ); // Maybe "NatFlow" or
                            // "NaturalFlow" is
                            // better.
                            // Time step:  Month
        // Removed 2007-04-29 Based on State removing from HydroBase
        //types.add ( prefix + "NaturalFlow" );
                            // Proposed
        //types.add ( "RT_Rate" );  // "Streamflow" with a
                            // "realtime" time step
                            // would be cleaner
                            // Time step:
                            // RealTime?  15Minute?
        types.add ( prefix + "Stage" ); // Time step:  real-time RT_Misc GAGE_HT
        types.add ( prefix + "Streamflow" );    // Also need
        types.add ( prefix + "StreamflowMax" );
                            // "StreamflowMax" and
        types.add ( prefix + "StreamflowMin" );
                            // "StreamflowMin"
                            // Time step:
                            // Day, Month, RealTime?
        types.add ( prefix + "WatTemp" );// Water temperature:
                            // RT_Misc WATTEMP
    }
    */
    /*
    if ( (include_types&HydroBase_Util.DATA_TYPE_STATION_RESERVOIR) > 0 ) {
        prefix = "";
        if ( add_group ) {
            prefix = "Reservoir - ";
        }
        types.add ( prefix + "Release" );// Time step:
                            // real-time with
                            // vax_field OUTLETQ
        //types.add ( "RT_Vol" )        // "ResContent",
                            // "ResStorage", or
                            // similar would be
                            // better
                            // Time step:
                            // RealTime?  15Minute?
        //types.add ( "RT_Height" );    // "Stage" and
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
    */
    /* Not yet supported
    if ( (include_types&HydroBase_Util.DATA_TYPE_STATION_WELL) > 0 ) {
        prefix = "";
        if ( add_group ) {
            prefix = "Well - ";
        }
        types.add ( prefix + "WellLevel");// Also see WellLevel
                            // for structures.
                            // RT_Misc WELL_1
    }
    */
    /* Not yet supported
    if ( (include_types&HydroBase_Util.DATA_TYPE_AGRICULTURE) > 0 ) {
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
    */
    /* Not yet supported
    if ( (include_types&HydroBase_Util.DATA_TYPE_DEMOGRAPHICS_POPULATION) > 0 ) {
        prefix = "";
        if ( add_group ) {
            prefix = "Demographics - ";
        }
        types.add ( prefix + "HumanPopulation" );
    }
    */
    if ( (include_types&HydroBase_Util.DATA_TYPE_STRUCTURE_DIVERSION) > 0 ) {
        prefix = "";
        if ( add_group ) {
            prefix = "Diversion - ";
        }
        //types.add ( prefix + "DivClass" );// These are probably
        //types.add ( prefix + "DivComment" );// OK - will use
        types.add ( prefix + "DivTotal" );// SFUT as the sub data type.
        //types.add ( prefix + "IDivClass" );
        //types.add ( prefix + "IDivTotal" );
    }
    if ( (include_types&HydroBase_Util.DATA_TYPE_STRUCTURE_RESERVOIR) > 0 ) {
        prefix = "";
        if ( add_group ) {
            prefix = "Reservoir - ";
        }
        //types.add ( prefix + "IRelClass" );
        //types.add ( prefix + "IRelTotal" );
        //types.add ( prefix + "RelClass" );
        //types.add ( prefix + "RelComment" );
        //types.add ( prefix + "RelTotal" );
        //types.add ( prefix + "ResEOM" );
        //types.add ( prefix + "ResEOY" );
        //types.add ( prefix + "ResMeasElev" );
        //types.add ( prefix + "ResMeasEvap" );
        //types.add ( prefix + "ResMeasFill" );
        //types.add ( prefix + "ResMeasRelease" );
        //types.add ( prefix + "ResMeasStorage" );
    }
    /* Not yet supported
    if ( (include_types&HydroBase_Util.DATA_TYPE_STRUCTURE_WELL) > 0 ) {
        prefix = "";
        if ( add_group ) {
            prefix = "Well - ";
        }
        types.add ( prefix + "WellLevel" );
                            // Also somehow need to
                            // hook in "WellPumping"
    }
    */
    /* Not yet supported
    if ( (include_types&HydroBase_Util.DATA_TYPE_WIS) > 0 ) {
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
    */
    // Now sort the list...
    types = StringUtil.sortStringList ( types );
    // Remove duplicates - for example, this may occur for WellLevel because
    // it is listed with stations and structures...
    StringUtil.removeDuplicates ( types, true, true );
    return types;
}

/**
Return the list of time series header objects used to list available time series (such as in TSTool).
If possible, information from the cache is returned.  Otherwise, a new query is performed and the cache
is updated.
@param service the web service being used
@param dataType the data type being queried (e.g., "DivTotal" or "Diversion).
@param timeStep the time step being queried as per TS conventions (e.g., "Month" NOT HydroBase "Monthly").
@param ifp the input filter panel that provides additional filter criteria
@return a list of time series header objects suitable for listing time series
*/
public List<HydroBase_StructureGeolocStructMeasType> getTimeSeriesHeaderObjects (
        String dataType, String timeStep, InputFilter_JPanel ifp )
{   String routine = __class + ".getTimeSeriesHeaderObjects";
    List<HydroBase_StructureGeolocStructMeasType> tslist = new Vector(); // List that matches the input request
    // If data type has "-", assume something like "Diversion - DivTotal" and need second part
    // TODO SAM 2010-08-13 Evaluate moving this to calling code
    dataType = dataType.split("-")[1].trim();
    if( isStructureTimeSeriesDataType(dataType)){
        // District or division is required from the input filter to improve performance
        // The input string is something like "Equals;1" so get from the last part.
        List<String> inputDistrict = ifp.getInput("District", null, false, null );
        int district = -1;
        if ( inputDistrict.size() > 0 ) {
            String districtString = inputDistrict.get(0).split(";")[1];
            district = Integer.parseInt ( districtString );
        }
        List<String> inputDivision = ifp.getInput("Division", null, false, null );
        int div = -1;
        if ( inputDivision.size() > 0 ) {
            String divString = inputDivision.get(0).split(";")[1];
            div = Integer.parseInt ( divString );
        }
        if ( (district < 0) && (div < 0) ) {
            Message.printWarning ( 3, routine,
                "You must specify a district or division as a Where in the input filter." );
            return tslist;
        }
        // Get the cached objects if available.  If not, read them.  Optimize a bit by handling divisions
        // and districts separately.  District takes precedence over division
        List<Integer> districtList = new Vector();
        if ( district > 0 ) {
            districtList.add(new Integer(district));
        }
        else if ( div > 0 ) {
            // Get the list of districts to process.  Could process with a division call but try this first
            // since the code can be reused
            List<HydroBase_WaterDistrict> wdList = HydroBase_WaterDistrict.lookupWaterDistrictsForDivision(
                getWaterDistrictList(), div );
            for ( HydroBase_WaterDistrict wd: wdList ) {
                districtList.add(new Integer(wd.getWD()));
            }
        }
        for ( Integer districtInList : districtList ) {
            String key = getStructureGeolocMeasTypeByWDListCacheKey(dataType, timeStep, districtInList);
            List<HydroBase_StructureGeolocStructMeasType> cacheList =
                __structureGeolocMeasTypeByWDListCache.get(key );
            List<HydroBase_StructureGeolocStructMeasType> dataList = null;
            if ( cacheList == null ) {
                // No data have been read for the district and measType so do it.  This will actually read
                // all timesteps also and populate multiple hash tables, however, it will only return the
                // timestep of interest
                dataList = readStructureGeolocMeasTypeList(div, districtInList, dataType, timeStep, true);
            }
            else {
                dataList = cacheList;
                Message.printStatus(2, routine, "Got cached data using key \"" + key + "\" size=" + cacheList.size() );
            }
            // Add to the returned list - this ensures that the cache list itself does not get manipulated
            tslist.addAll( dataList );
        }
        // Now further filter the list based on the parameters and input filter.  Remove items that do not
        // match the constraints.
        filterStructureGeolocMeasType ( tslist, ifp );
    }
    else {
        Message.printWarning(3, routine, "Data type \"" + dataType + "\" is not a supported HBGuest type." );
    }
    return tslist;
}

/**
Return the time steps to be displayed for a time series data type.  The time
step should ideally be in meas_type; however, some time series tables store
more than one time series data type and the time steps in meas_type are not appropriate for displays.
@return the time steps that are available for a time series data type.
@param service Reserved for future use.
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
public List<String> getTimeSeriesTimeSteps ( String data_type, int include_types )
{   String Month = "Month";
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
        if ( (include_types&HydroBase_Util.DATA_TYPE_STRUCTURE_WELL) != 0 ) {
            v.add ( Day );
        }
        if ( (include_types&HydroBase_Util.DATA_TYPE_STATION_WELL) != 0 ) {
            v.add ( Irregular );
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
Return the list of water districts.  If available, the cached list is returned.  If not, a service get occurs.
*/
public List<HydroBase_WaterDistrict> getWaterDistrictList ()
{
    if ( __waterDistrictListCache.size() == 0 ) {
        // Not available so read
        __waterDistrictListCache = readWaterDistrictList ();
    }
    return __waterDistrictListCache;
}

/**
Return the list of water divisions.  If available, the cached list is returned.  If not, a service get occurs.
*/
public List<HydroBase_WaterDivision> getWaterDivisionList ()
{
    if ( __waterDivisionListCache.size() == 0 ) {
        // Not available so read
        __waterDivisionListCache = readWaterDivisionList ();
    }
    return __waterDivisionListCache;
}

/**
Indicate whether a time series data type is for a structure.  The result is
inclusive of isStructureSFUTTimeSeriesDataType().  In other words, call the
isStructureSFUTTimeSeriesDataType() first to match SFUT data types and then call this more general method.
@param service An instance of ColoradoWaterHBGuest, for authentication.  The global distinct StructMeasType
data from the instance are checked.
@param dataTypeToCheck A HydroBase data type.  If it matches the data type in struct_meas_type.meas_type,
true will be returned.  If the data_type has a "-", then the token before the dash is compared.
*/
public boolean isStructureTimeSeriesDataType ( String dataTypeToCheck )
{   if ( dataTypeToCheck.indexOf("-") >= 0) {
        // String is like "Diversion - DivTotal" so data type is part after dash
        dataTypeToCheck = StringUtil.getToken(dataTypeToCheck,"-",0,1).trim();
    }
    // This should match the data type to check if a structure
    List<String> dataTypes = getTimeSeriesDataTypes ( HydroBase_Util.DATA_TYPE_STRUCTURE_ALL, false );
    // Check the list of struct_meas_type.meas_type...
    for ( String dataType: dataTypes ) {
        Message.printStatus(2, "", "Comparing \"" + dataTypeToCheck + "\" against \"" + dataType + "\".");
        if ( dataType.equalsIgnoreCase(dataTypeToCheck) ) {
            return true;
        }
    }
    return false;
}

/**
Get a HydroBase_StructureGeolocStructMeasType instance for a WDID.  This is used to set metadata during
a time series read.  If necessary, the cache for the water district meas types will be read.
@param measType structure data type (measType) of interest (e.g., "DivTotal").
@param timeStep time series time step (e.g., "Month" NOT HydroBase "Monthly")
@param wd water district
@param id structure identifier within water district
@return the matching HydroBase_StructureGeolocStructMeasType instance, or null if not matched.
*/
private HydroBase_StructureGeolocStructMeasType
    getStructureGeolocMeasTypeForWDID ( String measType, String timeStep, int wd, int id )
{
    // First get the cache
    List<HydroBase_StructureGeolocStructMeasType> cacheList =
        __structureGeolocMeasTypeByWDListCache.get(getStructureGeolocMeasTypeByWDListCacheKey(
            measType, timeStep, wd));
    if ( cacheList == null ) {
        // Read the data and initialize the cache
        cacheList = readStructureGeolocMeasTypeList( -1, wd, measType, timeStep, true );
    }
    // Next loop through the returned list and find the specific ID match
    for ( HydroBase_StructureGeolocStructMeasType mt: cacheList ) {
        if ( mt.getID() == id ) {
            return mt;
        }
    }
    return null;
}

/**
Helper method to create a HydroBase_StructureGeolocStructMeasType object from a web service
StructureGeolocMeasType object.
@param sgmt service object
@return HydroBase_StructureGeolocStructMeasType instance constructed from service object
*/
private HydroBase_StructureGeolocStructMeasType newHydroBase_StructureGeolocMeasType (
    StructureGeolocMeasType sgmt, String measType, String timeStep )
{   HydroBase_StructureGeolocStructMeasType hbstruct = new HydroBase_StructureGeolocStructMeasType();
    hbstruct.setDiv(sgmt.getDiv());
    hbstruct.setWD(sgmt.getWd());
    hbstruct.setID(sgmt.getId());
    hbstruct.setStr_name(sgmt.getStrName());
    hbstruct.setCounty(sgmt.getCounty());
    hbstruct.setST(sgmt.getSt());
    hbstruct.setHUC(sgmt.getHuc());
    // Meastype
    hbstruct.setStart_year(sgmt.getStartYear());
    hbstruct.setEnd_year(sgmt.getEndYear());
    hbstruct.setMeas_type(sgmt.getMeasType());
    // FIXME SAM 2010-08-13 Need to not hard-code this
    hbstruct.setData_source("DWR");
    // Meas count not available?
    hbstruct.setMeas_num(sgmt.getMeasNum());
    // This comes from the calling code, in particular because HydroBase Annual corresponds to
    // Year and Month data
    hbstruct.setTime_step(timeStep);
    return hbstruct;
}

/**
Read and return the structure list for a division.
@param div the division to read
@param returnMeasType if true, return HydroBase_StructMeasTypeView instances, in preparation for joining
with MeasType data (only value recognized).  If false, return a list of HydroBase_Structure (envisioned).
@return the water division list
*/
private List<HydroBase_StructMeasTypeView> readStructureList ( int div, boolean returnMeasType )
{   String routine = __class + ".readStructureList";
    Holder<HbStatusHeader> status = new Holder<HbStatusHeader>();
    StopWatch sw = new StopWatch();
    sw.start();
    ArrayOfStructure structureArray = getColoradoWaterHBGuestSoap12().getHBGuestStructureByDIV(
        div, getAuthentication(), status );
    sw.stop();
    // Check for error
    if ( (status.value != null) && (status.value.getError() != null) ) {
        throw new RuntimeException ( "Error getting water divisions (" +
            status.value.getError().getErrorCode() + ": " + status.value.getError().getExceptionDescription() + ")." );
    }
    Message.printStatus(2, routine,
        "Retrieved " + structureArray.getStructure().size() + " structures for water division " + div + " in " +
        sw.getSeconds() + " seconds.");
    List<HydroBase_StructMeasTypeView> structList = new Vector();
    for ( Structure structure : structureArray.getStructure() ) {
        HydroBase_StructMeasTypeView hbstruct = new HydroBase_StructMeasTypeView();
        hbstruct.setWD(structure.getWd());
        hbstruct.setID(structure.getId());
        hbstruct.setStr_name(structure.getStrName());
        structList.add ( hbstruct );
    }
    return structList;
}

/**
Read HydroBase_StructureGeolocStructMeasType objects using web services.  The list for a water district and
measType combination are read.  This method should only be called if reading every time or the cache does not
exist and needs to be initialized.
@param div division of interest (-1 to ignore constraint)
@param wd water district of interest (-1 to ignore constraint)
@param measType structure measType to return (e.g., "DivTotal")
@param timeStep time series time step as per TS conventions (e.g, "Month" NOT HydroBase "Monthly")
@param cacheIt if true, cache the result, false to not cache (slower but use less memory long-term) - caches
are saved by measType-timeStep-wd combination
*/
private List<HydroBase_StructureGeolocStructMeasType> readStructureGeolocMeasTypeList(
    int div, int wd, String measType, String timeStep, boolean cacheIt )
{   String routine = __class + ".readStructureGeolocMeasTypeList";
    Holder<HbStatusHeader> status = new Holder<HbStatusHeader>();
    StopWatch sw = new StopWatch();
    sw.start();
    if ( measType == null ) {
        measType = ""; // Web service does not like null
    }
    List<HydroBase_StructureGeolocStructMeasType> returnList = new Vector();
    if ( wd > 0 ) {
        // Read data for one water district
        ArrayOfStructureGeolocMeasType sgmtArray = getColoradoWaterHBGuestSoap12().getHBGuestStructureGeolocMeasTypeByWD(
            wd, measType, getAuthentication(), status );
        sw.stop();
        // Check for error
        if ( (status.value != null) && (status.value.getError() != null) ) {
            throw new RuntimeException ( "Error getting StructureGeolocMeasType for wd=" + wd +
                " measType=\"" + measType + "\" (" + status.value.getError().getErrorCode() + ": " +
                status.value.getError().getExceptionDescription() + ")." );
        }
        Message.printStatus(2, routine,
            "Retrieved " + sgmtArray.getStructureGeolocMeasType().size() + " StructureGeolocMeasType for WD=" + wd +
            " MeasType=\"" + measType + "\" in " + sw.getSeconds() + " seconds.");
        // Loop through once and get the list of unique timesteps
        List<String> timeStepList = new Vector();
        boolean found;
        String timeStepUpper;
        if ( cacheIt ) {
            for ( StructureGeolocMeasType sgmt : sgmtArray.getStructureGeolocMeasType() ) {
                found = false;
                timeStepUpper = HydroBase_Util.convertFromHydroBaseTimeStep(sgmt.getTimeStep()).toUpperCase();
                for ( String timeStepInList: timeStepList ) {
                    if ( timeStepInList.equals(timeStepUpper) ) {
                        found = true;
                        break;
                    }
                }
                if ( !found ) {
                    timeStepList.add ( timeStepUpper );
                    if ( timeStepUpper.equals("YEAR") ) {
                        // Also add "MONTH" because HydroBase Annual refers to monthly and annual values
                        timeStepList.add ( "MONTH" );
                    }
                }
            }
            // Initialize each cache
            for ( String timeStepInList: timeStepList ) {
                String key = getStructureGeolocMeasTypeByWDListCacheKey(measType, timeStepInList, wd);
                Message.printStatus ( 2, routine, "Initializing cache with key \"" + key + "\"." );
                __structureGeolocMeasTypeByWDListCache.put(key, new Vector() );
            }
        }
        // Loop through the results (by water district and division) and add to the appropriate cached list
        // Sometimes multiple records are returned because the results are a join with owners (multiple owners)
        // Therefore check the results against the previous record and only add if not the same
        List<HydroBase_StructureGeolocStructMeasType> cacheList;
        String cacheTimeStep;
        StructureGeolocMeasType sgmtPrev = null;
        for ( StructureGeolocMeasType sgmt : sgmtArray.getStructureGeolocMeasType() ) {
            // Only the timestep varies by the record since the measType and district were passed in
            // sgmt.getTimeStep() will return "Daily" or "Annual"
            if ( (sgmtPrev != null) && (sgmt.getWd() == sgmtPrev.getWd()) &&
                (sgmt.getId() == sgmtPrev.getId()) && sgmt.getTimeStep().equals(sgmtPrev.getTimeStep()) ) {
                // Critical information is the same as the previous record so skip
                continue;
            }
            cacheTimeStep = HydroBase_Util.convertFromHydroBaseTimeStep(sgmt.getTimeStep());
            cacheList = __structureGeolocMeasTypeByWDListCache.get (getStructureGeolocMeasTypeByWDListCacheKey(
                measType, cacheTimeStep, wd));
            cacheList.add ( newHydroBase_StructureGeolocMeasType( sgmt, measType, cacheTimeStep ) );
            if ( cacheTimeStep.equalsIgnoreCase("YEAR") ) {
                // Also need to save for "MONTH" since in the same HydroBase "Annual" MeasType
                cacheTimeStep = "MONTH";
                cacheList = __structureGeolocMeasTypeByWDListCache.get (getStructureGeolocMeasTypeByWDListCacheKey(
                    measType, cacheTimeStep, wd));
                cacheList.add ( newHydroBase_StructureGeolocMeasType( sgmt, measType, "Month" ) );
            }
            sgmtPrev = sgmt;
        }
        if ( cacheIt ) {
            // Logging...
            for ( String timeStepInList: timeStepList ) {
                String key = getStructureGeolocMeasTypeByWDListCacheKey(measType, timeStepInList, wd);
                cacheList = __structureGeolocMeasTypeByWDListCache.get(key);
                Message.printStatus ( 2, routine,
                    "After reading data, cache for key \"" + key + "\" has size=" + cacheList.size() );
            }
        }
        // Add to the returned list only the requested list
        String key = getStructureGeolocMeasTypeByWDListCacheKey(measType, timeStep, wd);
        cacheList = __structureGeolocMeasTypeByWDListCache.get(key);
        if ( cacheList != null ) {
            Message.printStatus ( 2, routine, "Returning cache for key \"" + key + "\" size=" +
                cacheList.size() + "." );
            returnList.addAll ( cacheList );
        }
        else {
            Message.printStatus ( 2, routine, "Problem?  No cache for key \"" + key + "\"." );
        }
    }
    return returnList;
}

/**
Read time series for the requested time series identifier.
*/
public TS readTimeSeries ( String tsidentString, DateTime readStart, DateTime readEnd, String units,
    boolean readData )
throws Exception
{   String routine = __class + ".readTimeSeries";
    TS ts = TSUtil.newTimeSeries(tsidentString, true);
    TSIdent tsident = new TSIdent(tsidentString);
    String dataType = tsident.getType();
    String timeStep = tsident.getInterval();
    int [] wdidParts = HydroBase_WaterDistrict.parseWDID(tsident.getLocation());
    ts.setIdentifier(tsident);
    // Lookup the StructureGeolocMeasType instance - used for description and default period (the available)
    HydroBase_StructureGeolocStructMeasType hbstruct =
        getStructureGeolocMeasTypeForWDID ( dataType, timeStep, wdidParts[0], wdidParts[1] );
    if ( hbstruct == null ) {
        String message =
            "Unable to get StructureGeolocMeasType information for WDID \"" + tsident.getLocation() + "\" - " +
                "can't read time series \"" + tsidentString + "\".";
        Message.printWarning(3, routine, message);
        throw new RuntimeException ( message );
    }
    // Default the dates to 1900 to current time
    // TODO SAM 2010-08-16 Need to set date to available from hbstruct.start/end year
    if ( readStart == null ) {
        int startYear = hbstruct.getStart_year();
        if ( startYear <= 0 ) {
            // Default to 1900
            startYear = 1900; // This is irrigation year
        }
        --startYear; // To get to previous calendar year
        if ( timeStep.equalsIgnoreCase("Month")) {
            readStart = DateTime.parse("" + startYear + "-11");
        }
        else if ( timeStep.equalsIgnoreCase("Day")) {
            readStart = DateTime.parse("" + startYear + "-11-01");
        }
        else if ( timeStep.equalsIgnoreCase("Year")) {
            readStart = DateTime.parse("" + startYear, DateTime.FORMAT_YYYY);
        }
    }
    if ( readEnd == null ) {
        int endYear = hbstruct.getEnd_year();
        if ( endYear <= 0 ) {
            // Default to current year
            DateTime current = new DateTime(DateTime.DATE_CURRENT);
            endYear = current.getYear(); // This is irrigation year
            if ( (current.getMonth() >= 11) && !timeStep.equalsIgnoreCase("Year") ) {
                ++endYear; // To include full irrigation year that extends into next year
            }
        }
        if ( timeStep.equalsIgnoreCase("Month")) {
            readEnd = DateTime.parse("" + endYear + "-10");
        }
        else if ( timeStep.equalsIgnoreCase("Day")) {
            readEnd = DateTime.parse("" + endYear + "-10-31");
        }
        else if ( timeStep.equalsIgnoreCase("Year")) {
            readEnd = DateTime.parse ("" + endYear, DateTime.FORMAT_YYYY );
        }
    }
    // TODO SAM 2010-08-15 Why do we need to specify dates?  Should be able to get all data
    ts.setDate1 ( readStart );
    ts.setDate1Original ( readStart );
    ts.setDate2 ( readEnd );
    ts.setDate2Original ( readEnd);
    ts.allocateDataSpace();
    ts.allocateDataFlagSpace("", false);
    ts.setDataUnits(HydroBase_Util.getTimeSeriesDataUnits(null, dataType, timeStep));
    ts.setDataUnitsOriginal(ts.getDataUnits());
    ts.setDescription ( hbstruct.getStr_name() );
    
    Holder<HbStatusHeader> status = new Holder<HbStatusHeader>();
    
    StopWatch sw = new StopWatch();
    sw.start();
    if ( dataType.equalsIgnoreCase("DivTotal") ) {
        String wdid = HydroBase_WaterDistrict.formWDID(7, wdidParts[0], wdidParts[1]);
        if ( timeStep.equalsIgnoreCase("Year") && readData ) {
            ArrayOfStructureAnnuallyTS ytsArray = getColoradoWaterHBGuestSoap12().getHBGuestStructureAnnuallyTSByMeasNum(
                hbstruct.getMeas_num(), (short)readStart.getYear(), (short)readEnd.getYear(), getAuthentication(), status );
            sw.stop();
            // Check for error
            if ( (status.value != null) && (status.value.getError() != null) ) {
                throw new RuntimeException ( "Error getting StructureAnnuallyTS for WDID=" + wdid +
                    " (" + status.value.getError().getErrorCode() + ": " +
                    status.value.getError().getExceptionDescription() + ")." );
            }
            Message.printStatus(2, routine,
                "Retrieved " + ytsArray.getStructureAnnuallyTS().size() + " StructureAnnuallyTS for wdid=\"" +
                wdid + "\" " + readStart.getYear() + " to " + readEnd.getYear() + " in " +
                sw.getSeconds() + " seconds.");
            // Transfer the data
            DateTime date = new DateTime(DateTime.DATE_FAST|DateTime.PRECISION_MONTH);
            for ( StructureAnnuallyTS yts : ytsArray.getStructureAnnuallyTS() ) {
                date.setYear(yts.getIrrYear());
                ts.setDataValue(date,yts.getAnnAmt());
            }
            ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
            ts.addToGenesis ( "Annual values are for irrigation year Nov to Oct." );
        }
        else if ( timeStep.equalsIgnoreCase("Month") && readData ) {
            ArrayOfStructureMonthlyTS mtsArray = null;
            // Read using the Measnum, which allows direct access to the time series of interest.
            mtsArray = getColoradoWaterHBGuestSoap12().getHBGuestStructureMonthlyTSByMeasNum(
                hbstruct.getMeas_num(), (short)readStart.getYear(), (short)readEnd.getYear(), getAuthentication(), status );
            // TODO Evaluate whether the following should be used in any cases
            // The following returns all meas types, DivTotal and classes, which is a performance hit
            //mtsArray = getColoradoWaterHBGuestSoap12().getHBGuestStructureMonthlyTSByWDID(
            //    wdid, (short)readStart.getYear(), (short)readEnd.getYear(), getAuthentication(), status );
            sw.stop();
            // Check for error
            if ( (status.value != null) && (status.value.getError() != null) ) {
                throw new RuntimeException ( "Error getting StructureMonthlyTS for WDID=" + wdid +
                    " (" + status.value.getError().getErrorCode() + ": " +
                    status.value.getError().getExceptionDescription() + ")." );
            }
            Message.printStatus(2, routine,
                "Retrieved " + mtsArray.getStructureMonthlyTS().size() + " StructureMonthlyTS for wdid=\"" +
                wdid + "\" " + readStart.getYear() + " to " + readEnd.getYear() + " in " +
                sw.getSeconds() + " seconds.");
            // Transfer the data
            DateTime date = new DateTime(DateTime.DATE_FAST|DateTime.PRECISION_MONTH);
            for ( StructureMonthlyTS mts : mtsArray.getStructureMonthlyTS() ) {
                date.setMonth(mts.getCalMonth());
                date.setYear(mts.getCalYear());
                ts.setDataValue(date,mts.getAmt());
            }
            ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
        }
        else if ( timeStep.equalsIgnoreCase("Day") && readData ) {
            ArrayOfStructureDailyTS dtsArray = getColoradoWaterHBGuestSoap12().getHBGuestStructureDailyTSByMeasNum(
                hbstruct.getMeas_num(), readStart.toString(), readEnd.toString(), getAuthentication(), status );
            sw.stop();
            // Check for error
            if ( (status.value != null) && (status.value.getError() != null) ) {
                throw new RuntimeException ( "Error getting StructureDailyTS for WDID=" + wdid +
                    " (" + status.value.getError().getErrorCode() + ": " +
                    status.value.getError().getExceptionDescription() + ")." );
            }
            Message.printStatus(2, routine,
                "Retrieved " + dtsArray.getStructureDailyTS().size() + " StructureDailyTS for wdid=\"" +
                wdid + "\" " + readStart.getYear() + " to " + readEnd.getYear() + " in " +
                sw.getSeconds() + " seconds.");
            // Transfer the data - do simple string parse of date to improve performance
            // (should work as long as WS spec does not change)
            DateTime date = new DateTime(DateTime.DATE_FAST|DateTime.PRECISION_DAY);
            String dateString;
            String[] dateParts;
            for ( StructureDailyTS dts : dtsArray.getStructureDailyTS() ) {
                dateString = dts.getMeasDate();
                dateParts = dateString.split("/");
                // Expected date format is MM/DD/YYYY, but can be 1 or 2 digit month and day
                Message.printStatus(2,routine,dateString);
                date.setMonth(Integer.parseInt(dateParts[0]));
                date.setDay(Integer.parseInt(dateParts[1]));
                date.setYear(Integer.parseInt(dateParts[2]));
                ts.setDataValue(date,dts.getAmt(),dts.getObs(),0);
            }
            ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
            HydroBase_Util.addDailyTSStructureDataFlagDescriptions ( ts );
            // Also fill daily diversions as per the accepted DWR approach because HydroBase does not
            // store filled daily data
            HydroBase_Util.fillTSIrrigationYearCarryForward((DayTS)ts, "C" );
        }
    }
    else {
        throw new Exception ( "Data type \"" + dataType + "\" is not supported." );
    }
    return ts;
}

/**
Read a list of objects that contain time series header information.  This is used, for example, to populate the
time series list area of TSTool and to get a list of time series for the TSTool ReadColoradoWaterHBGuest(Where...)
command.
@param service The ColoradoWaterHBGuest instance to use for queries.
@param data_type The data type for time series, matching MeasType values from HydroBase
(e.g., "Streamflow", "DivTotal").
@param time_step The time step for time series, either from the TSTool
time step choice, or a simple string "Day", "Month", etc.
@param div division to query, in particular for structures, which are cached by division.
@param ifp An InputFilter_JPanel instance to retrieve where clause information from.
@return a list of objects for the data type.
@exception if there is an error reading the data.
*/
private List XreadTimeSeriesHeaderObjects ( String data_type, String time_step, int div, InputFilter_JPanel ifp )
throws Exception
{   String routine = "ColoradoWaterHBGuestAPI.readTimeSeriesHeaderObjects", message;
    List tsMeasTypeList = null;
    String dataSource = null;
    int size = 0;
    String meas_type = data_type; // Try to avoid conversion like in HydroBase
    String hbtime_step = time_step; // Try to avoid conversion like in HydroBase
    Message.printStatus(2, routine, "Reading HydroBase time series header objects for HydroBase meas_type=\"" +
           meas_type + "\", HydroBase timeStep=\"" + hbtime_step + "\".");
    HydroBaseDMI hbdmi = null; // Try to use HydroBase utility methods for hard-coded lookups
                                // Don't need database connection

    if ( HydroBase_Util.isStationTimeSeriesDataType(null, data_type)){
        /*
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
//          if (hbdmi.useStoredProcedures()) {
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
        */
    }
    else if( isStructureTimeSeriesDataType(data_type)){
        try {
            // Note multiple SFUT and data sources can be returned below...
            /*
            tsMeasTypeList = readStructureGeolocStructMeasTypeList ( meas_type, hbtime_step, ifp );
            // Convert HydroBase data to make it more consistent with how TSTool handles time series...
            if ( tsMeasTypeList != null ) {
                size = tsMeasTypeList.size();
            }
            HydroBase_StructMeasTypeView view;
            String data_units = HydroBase_Util.getTimeSeriesDataUnits (hbdmi, data_type, time_step );
            List v = tsMeasTypeList;
            tsMeasTypeList = new Vector();          
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
                tsMeasTypeList.add(new HydroBase_StructureGeolocStructMeasType(view));
            }
            if ( data_type.equals("WellLevel") && time_step.equalsIgnoreCase("Day")){
                // Add the common identifiers to the normal data.  This is a work-around until HydroBase is redesigned.
                // TODO SAM 2004-01-13
                HydroBase_Util.addAlternateWellIdentifiers ( hbdmi, tsMeasTypeList );
            }
            */
        }
        catch ( Exception e ) {
            message = "Error getting structure time series list from ColoradoWaterHBGuest (" + e + ").";
            Message.printWarning ( 3, routine, message );
            Message.printWarning ( 3, routine, e );
            throw new Exception ( message );
        }
    }
    return tsMeasTypeList;
}

/**
Read and return the water district list.
@param service the web service from which to read the water districts.
@return the water district list
*/
private List<HydroBase_WaterDistrict> readWaterDistrictList ()
{   String routine = __class + ".readWaterDistrictList";
    Holder<HbStatusHeader> status = new Holder<HbStatusHeader>();
    ArrayOfWaterDistrict districtArray = getColoradoWaterHBGuestSoap12().getHBGuestWaterDistrict(
        getAuthentication(), status );
    // Check for error
    if ( (status.value != null) && (status.value.getError() != null) ) {
        throw new RuntimeException ( "Error getting water districts (" +
            status.value.getError().getErrorCode() + ": " + status.value.getError().getExceptionDescription() + ")." );
    }
    Message.printStatus(2, routine,
        "Retrieved " + districtArray.getWaterDistrict().size() + " water districts." );
    List<HydroBase_WaterDistrict> waterDistrictList = new Vector();
    for ( WaterDistrict district : districtArray.getWaterDistrict() ) {
        waterDistrictList.add(new HydroBase_WaterDistrict(district.getDiv(), district.getWd(), district.getWdName()));
    }
    return waterDistrictList;
}

/**
Read and return the water division list.
@param service the web service from which to read the water divisions.
@return the water division list
*/
private List<HydroBase_WaterDivision> readWaterDivisionList ()
{   String routine = __class + ".readWaterDivisionList";
    Holder<HbStatusHeader> status = new Holder<HbStatusHeader>();
    ArrayOfWaterDivision divisionArray = getColoradoWaterHBGuestSoap12().getHBGuestWaterDivision(
        getAuthentication(), status );
    // Check for error
    if ( (status.value != null) && (status.value.getError() != null) ) {
        throw new RuntimeException ( "Error getting water divisions (" +
            status.value.getError().getErrorCode() + ": " + status.value.getError().getExceptionDescription() + ")." );
    }
    Message.printStatus(2, routine,
        "Retrieved " + divisionArray.getWaterDivision().size() + " water divisions." );
    List<HydroBase_WaterDivision> waterDivisionList = new Vector();
    for ( WaterDivision division : divisionArray.getWaterDivision() ) {
        waterDivisionList.add(new HydroBase_WaterDivision(division.getDiv(), division.getDivName()));
    }
    return waterDivisionList;
}

/**
Set the cache of station data types.
*/
public void setStationDataTypeListCache ( List<String> dataTypes )
{
    __stationDataTypeListCache = dataTypes;
}

/**
Set the cache of station measTypes.
*/
public void setStationMeasTypeListCache ( List<HydroBase_StationGeolocMeasType> variables )
{
    __stationMeasTypeListCache = variables;
}

}