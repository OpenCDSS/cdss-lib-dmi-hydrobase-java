package us.co.state.dwr.hbguest;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.xml.ws.Holder;

import DWR.DMI.HydroBaseDMI.HydroBase_GroundWaterWellsView;
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
import RTi.Util.Time.TimeUtil;

// TODO SAM 2012-05-09 Might move the contents of this class to the ColoradoWaterHBGuest data store so as
// to not commingle the auto-generated code with the data store code

/**
<p>
This class extends the auto-generated ColoradoWaterHBGuest SOAP web service class and adds
caches and methods to access the caches.  In this way a service object and its associated caches can
be reused and separate caches can exist for different services (for example if different versions of
the services and back-end databases are available).  Because the caches are non-static and could get
large, care should be taken to reuse the service object as much as possible.  This class has public get methods
for data and private read methods to utilize the SOAP API.  In this way, the API is more service-like with
get methods.  HydroBase package objects are returned in some cases to facilitate use in existing code such
as TSTool, although the ColoradoWaterHBGuest object types will be evaluated with use.
</p>
<p>
The ColoradoWaterHBGuest SOAP API in some cases provides methods to query lists of "meas types" by water district
or division (join of station or structure, geoloc, meastype) - this is utilized by TSTool's UI to list
time series for the user and generally includes enough useful metadata; however, in some cases information such
as units may not be accessible.  The API also provides methods to read specific time series identified by
a WDID or "meas_num".  Because web services incur a performance hit and in particular querying time series data
can be a big hit, this data store attempts to optimize performance by caching the "meas type" query results.
HydroBase meas type strings can be different from TSTool for a couple of reasons:  1) the HydroBase string is not
clear enough (e.g., station "Evap" does not indicate what type of evaporation), 2) The HydroBase "meas type"
refers to a table that has multiple values (e.g., Structure "ResMeas" has multiple values).
</p>
<p>
In order to have reasonable performance, this data store caches the meas type list results using a string key
of the form "DATATYPE-INTERVAL-WD".  The WD corresponds to the water district query parameter for the SOAP API methods.
The DataType corresponds to the TSTool data type (NOT the HydroBase measType).  This may result in some redundant
caches (list of time series may be the essentially the same except that the data object "data type" is that of
TSTool, not overlapping HydroBase meas type like "ResMeas"), but it makes data management logic more
straightforward.  Caches are initialized in a lazy fashion.  If a request for a division or water district is made,
the corresponding water district caches are initialized.  Subsequent queries will be used as needed.  For example,
if a request is made to list a meas-type and the list has been cached, it is returned immediately.  If a time
series is requested, the cache is checked for a meas type object and the corresponding meas_num can be used
for queries.  This works well with structure queries because structure identifiers include the WD and consequently
the relationship between individual time series and meas type cache is clear.  However, this does not work when the
time series query is based on meas_num (e.g., stations and groundwater wells).  In this latter case when requesting
a specific time series, the approach is to search the cache in memory for the time series (for example to
match the station identifier).  If found, then sufficient information is available to query the time series.  If
not found, additional water districts are queried for meas type data until the time series is matched.  This is
not ideal because if a time series is queried for a water district that has not been read, intervening water
district lists have to be read in order to search the lists.  This could be improved if the SOAP API is updated
to allow station and groundwater wells time series to be read by specific ID (then the corresponding WD mead type
list can be read and cached to facilitate possible subsequent queries).
</p>
*/
public class ColoradoWaterHBGuestService extends ColoradoWaterHBGuest
{
    
/**
Constructor.
*/
public ColoradoWaterHBGuestService ( String wsdlLocation )
throws MalformedURLException
{
    super( wsdlLocation );
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
private List<String> __stationDataTypeListCache = new Vector<String>();

/**
Singleton for the once instance of this class.  In the future a more complex approach will be used
to allow multiple instances, for example pointing to different service providers (e.g., different
versions of the database).
*/
//private static ColoradoWaterHBGuestService __serviceSingleton = new ColoradoWaterHBGuestService();

/**
Cache of station measType objects.
*/
private List<HydroBase_StationGeolocMeasType> __stationMeasTypeListCache = new Vector<HydroBase_StationGeolocMeasType>();

/**
Cache of distinct structure data types (e.g., "DivTotal"), 
*/
private List<String> __structureDataTypeListCache = new Vector<String>();

/**
Cache of HydroBase_StationGeolocMeasType objects, with the key being MeasType + "-" + interval + "-" + wd,
where, for example, the interval is generic "Year" and NOT the internal HydroBase interval like "Annual".
Combine the lists if getting information for more than one district.
*/
private Hashtable<String,List<HydroBase_StationGeolocMeasType>> __stationGeolocMeasTypeByWDListCache
	= new Hashtable<String,List<HydroBase_StationGeolocMeasType>>();

/**
Cache of HydroBase_StructureGeolocStructMeasType objects, with the key being MeasType + "-" + interval + "-" + wd,
where, for example, the interval is generic "Year" and NOT the internal HydroBase interval like "Annual".
Combine the lists if getting information for more than one district.
*/
private Hashtable<String,List<HydroBase_StructureGeolocStructMeasType>> __structureGeolocMeasTypeByWDListCache
	= new Hashtable<String,List<HydroBase_StructureGeolocStructMeasType>>();

/**
Cache of HydroBase_GroundWaterWellView objects, with the key being MeasType + "-" + interval + "-" + wd,
where the interval is generic "Day" and NOT the internal HydroBase interval like "Daily".
Combine the lists if getting information for more than one district.
*/
private Hashtable<String,List<HydroBase_GroundWaterWellsView>> __groundWaterWellsMeasTypeByWDListCache
	= new Hashtable<String,List<HydroBase_GroundWaterWellsView>>();

/**
Cache of HydroBase water districts from the service.
*/
private List<HydroBase_WaterDistrict> __waterDistrictListCache = new Vector<HydroBase_WaterDistrict>();

/**
Cache of HydroBase water divisions from the service.
*/
private List<HydroBase_WaterDivision> __waterDivisionListCache = new Vector<HydroBase_WaterDivision>();

/**
Filter a List of HydroBase_GroundWaterWellsView according to the input filter.  This is used
in queries.  The District and Division have already been accounted for.
TODO SAM 2012-05-07 Currently no additional filtering is done
*/
private void filterGroundWaterWellsMeasType ( List<HydroBase_GroundWaterWellsView>mtList,
    InputFilter_JPanel ifp )
{
    List<InputFilter> constraintList1 = ifp.getInputFilters("Structure ID");
    Message.printStatus(2, "", "Have " + constraintList1.size() + " Structure ID constraints");
    List<InputFilter> constraintList2 = ifp.getInputFilters("Structure Name");
    Message.printStatus(2, "", "Have " + constraintList2.size() + " Structure Name constraints");
    //String [] tokens; // tokens in input filter constraint
    //String valueString; // Value in constraint (string)
    //int valueInt; // Value in constraint (integer)
    //String operatorString; // operator string in constraint
    //HydroBase_GroundWaterWellsView mt;
    for ( int i = 0; i < mtList.size(); i++ ) {
        //mt = mtList.get(i);
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
Filter a List of HydroBase_StationGeolocStructMeasType according to the input filter.  This is used
in queries.  The District and Division have already been accounted for.
TODO SAM 2012-05-07 Currently no additional filtering is done
*/
private void filterStationGeolocMeasType ( List<HydroBase_StationGeolocMeasType>mtList,
    InputFilter_JPanel ifp )
{
    List<InputFilter> constraintList1 = ifp.getInputFilters("Structure ID");
    Message.printStatus(2, "", "Have " + constraintList1.size() + " Structure ID constraints");
    List<InputFilter> constraintList2 = ifp.getInputFilters("Structure Name");
    Message.printStatus(2, "", "Have " + constraintList2.size() + " Structure Name constraints");
    //String [] tokens; // tokens in input filter constraint
    //String valueString; // Value in constraint (string)
    //int valueInt; // Value in constraint (integer)
    //String operatorString; // operator string in constraint
    //HydroBase_StationGeolocMeasType mt;
    for ( int i = 0; i < mtList.size(); i++ ) {
        //mt = mtList.get(i);
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
Filter a List of HydroBase_StructureGeolocStructMeasType according to the input filter.  This is used
in queries.  The District and Division have already been accounted for.
TODO SAM 2012-05-07 Currently no additional filtering is done
*/
private void filterStructureGeolocMeasType ( List<HydroBase_StructureGeolocStructMeasType>mtList,
    InputFilter_JPanel ifp )
{
    List<InputFilter> constraintList1 = ifp.getInputFilters("Structure ID");
    Message.printStatus(2, "", "Have " + constraintList1.size() + " Structure ID constraints");
    List<InputFilter> constraintList2 = ifp.getInputFilters("Structure Name");
    Message.printStatus(2, "", "Have " + constraintList2.size() + " Structure Name constraints");
    //String [] tokens; // tokens in input filter constraint
    //String valueString; // Value in constraint (string)
    //int valueInt; // Value in constraint (integer)
    //String operatorString; // operator string in constraint
    //HydroBase_StructureGeolocStructMeasType mt;
    for ( int i = 0; i < mtList.size(); i++ ) {
        //mt = mtList.get(i);
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
/*
public static ColoradoWaterHBGuestService getService()
{
    return __serviceSingleton;
}
*/

/**
Determine the hash key for the GroundWaterWellsMeasTypeList caches.
*/
private String getGroundWaterWellsMeasTypeByWDListCacheKey ( String dataType, String timeStep, int wd )
{   // Make sure that the water district is zero padded.
    String wdString = "" + wd;
    if ( wd < 10 ) {
        wdString = "0" + wd;
    }
    return dataType.toUpperCase() + "-" + timeStep.toUpperCase() + "-" + wdString;
}

/**
Get a HydroBase_GroundWaterWellsView instance for a WDID.  This is used to set metadata during
a time series read.  If necessary, the cache for the water district meas types will be read.
@param dataType structure data type (measType) of interest (e.g., "DivTotal").
@param timeStep time series time step (e.g., "Month" NOT HydroBase "Monthly")
@param identifier unique well identifier
@return the matching HydroBase_GroundWaterWellsView instance, or null if not matched.
*/
private HydroBase_GroundWaterWellsView
    getGroundWaterWellsMeasTypeForIdentifier ( String identifier, String dataSource, String dataType, String timeStep )
{
    // Check for nulls to simplify logic below
    if ( identifier == null ) {
        identifier = "";
    }
    if ( dataSource == null ) {
        dataSource = "";
    }
    // Because well identifiers don't include the water district, have to search all caches to find the
    // matching identifier.
    // Loop through all the water districts, this will be a performance hit if the requested well is in a
    // large-number water district and the water district has not been cached.
    boolean useCache = true;
    for ( HydroBase_WaterDistrict wd : getWaterDistrictList() ) {
        String cacheKey = getGroundWaterWellsMeasTypeByWDListCacheKey(dataType, timeStep, wd.getWD());
        List<HydroBase_GroundWaterWellsView> cacheList = __groundWaterWellsMeasTypeByWDListCache.get(cacheKey);
        if ( cacheList == null ) {
            // Read the data for the water district and initialize the cache
            cacheList = readGroundWaterWellsMeasTypeList( wd.getWD(), dataType, timeStep, useCache );
        }
        // Next loop through the returned list and find the specific match
        for ( HydroBase_GroundWaterWellsView mt: cacheList ) {
            String mtIdentifier = mt.getIdentifier();
            String source = mt.getData_source();
            // Only compare the data items that are not empty
            // In other words, don't compare station_id unless both have something to compare
            if ( ((mtIdentifier != null) && !mtIdentifier.equals("") && mtIdentifier.equalsIgnoreCase(identifier)) && // Matched identifier
                ((source != null) && !source.equals("") && source.equalsIgnoreCase(dataSource)) ) { // Matched source
                return mt;
            }
        }
    }
    return null;
}

/**
Lookup the HydroBase measType from the TSTool data type.  This is used in cases where the TSTool data type
is different for clarity/usability (e.g., TSTool "EvapPan" versus HydroBase "Evap") and in cases where HydroBase
has multiple data types stored in one reacord (e.g., TSTool "ResMeasStorage" versus HydroBase "ResMeas").
This method can be called regardless of whether the time series is for a station, structure, etc.
@param dataType TSTool data type
@return HydroBase measType
*/
private String getHydroBaseMeasTypeFromDataType ( String dataType )
{   String measTypeHydroBase = dataType;
    // Station data...
    if ( dataType.equalsIgnoreCase("EvapPan") ) {
        // TSTool more specific to avoid confusion with reservoir evaporation, etc.
        measTypeHydroBase = "Evap";
    }
    else if ( dataType.toUpperCase().startsWith("FROSTDATE") ) {
        // Replace TSTool FrostDateXXXX with FrostDate since HydroBase has multiple data types in one table
        measTypeHydroBase = "FrostDate";
    }
    else if ( dataType.toUpperCase().startsWith("SNOWCOURSE") ) {
        // Replace TSTool SnowCourseXXXX with SnowCrse since HydroBase has multiple data types in one table
        measTypeHydroBase = "SnowCrse";
    }
    else if ( dataType.equalsIgnoreCase("TempMax") ) {
        // TSTool reverse order for consistency and readability.
        measTypeHydroBase = "MaxTemp";
    }
    else if ( dataType.equalsIgnoreCase("TempMean") ) {
        // TSTool reverse order for consistency and readability.
        measTypeHydroBase = "MeanTemp";
    }
    else if ( dataType.equalsIgnoreCase("TempMin") ) {
        // TSTool reverse order for consistency and readability.
        measTypeHydroBase = "MinTemp";
    }
    else if ( dataType.equalsIgnoreCase("VaporPressure") ) {
        // TSTool more specific for readability.
        measTypeHydroBase = "VP";
    }
    // Structure data...
    else if ( dataType.toUpperCase().startsWith("RESMEAS") ) {
        // Replace TSTool ResMeasXXXX with ResMeas since HydroBase has multiple data types in one table
        measTypeHydroBase = "ResMeas";
    }
    return measTypeHydroBase;
}

/**
Return the cached list of station data types, for all meas types.
*/
public List<String> getStationDataTypeListCache ()
{
    return __stationDataTypeListCache;
}

/**
Determine the hash key for the StationGeolocMeasTypeList caches.
*/
private String getStationGeolocMeasTypeByWDListCacheKey ( String dataType, String timeStep, int wd )
{   // Make sure that the water district is zero padded.
    String wdString = "" + wd;
    if ( wd < 10 ) {
        wdString = "0" + wd;
    }
    return dataType.toUpperCase() + "-" + timeStep.toUpperCase() + "-" + wdString;
}

/**
Get a HydroBase_StationGeolocMeasType instance for a station ID.  This is used to set metadata during
a time series read.  If necessary, the cache for the water district meas types will be read.
@param stationID station identifier within water district
@param dataSource data source (provider) for data (e.g., "NOAA", "DWR")
@param dataType station data type of interest (e.g., "AdminFlow"), used by TSTool (not internal HydroBase).
@param timeStep time series time step (e.g., "Month" NOT HydroBase "Monthly")
@return the matching HydroBase_StructureGeolocStructMeasType instance, or null if not matched.
*/
private HydroBase_StationGeolocMeasType
    getStationGeolocMeasTypeForStationID ( String stationID, String dataSource, String dataType, String timeStep )
{   // Check for nulls to simplify logic below
    if ( stationID == null ) {
        stationID = "";
    }
    if ( dataSource == null ) {
        dataSource = "";
    }
    // Because station identifiers don't include the water district, have to search all caches to find the
    // matching identifier.
    // Looping through all the water districts
    boolean useCache = true;
    for ( HydroBase_WaterDistrict wd : getWaterDistrictList() ) {
        String cacheKey = getStationGeolocMeasTypeByWDListCacheKey(dataType, timeStep, wd.getWD());
        List<HydroBase_StationGeolocMeasType> cacheList =
            __stationGeolocMeasTypeByWDListCache.get(cacheKey);
        if ( cacheList == null ) {
            // Read the data for the water district and initialize the cache
            cacheList = readStationGeolocMeasTypeList( wd.getWD(), dataType, timeStep, useCache );
        }
        // Next loop through the returned list and find the specific match
        for ( HydroBase_StationGeolocMeasType mt: cacheList ) {
            String abbrev = mt.getAbbrev();
            String station_id = mt.getStation_id();
            String source = mt.getData_source();
            // Only compare the data items that are not empty
            // In other words, don't compare station_id unless both have something to compare
            if ( (((station_id != null) && !station_id.equals("") && station_id.equalsIgnoreCase(stationID)) ||
                ((abbrev != null) && !abbrev.equals("") && abbrev.equalsIgnoreCase(stationID))) && // Matched ID or abbrev
                ((source != null) && !source.equals("") && source.equalsIgnoreCase(dataSource)) ) { // Matched source
                return mt;
            }
        }
    }
    return null;
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
Get a HydroBase_StructureGeolocStructMeasType instance for a WDID.  This is used to set metadata during
a time series read.  If necessary, the cache for the water district meas types will be read.
@param wd water district
@param id structure identifier within water district
@param measType structure data type (measType) of interest (e.g., "DivTotal").
@param timeStep time series time step (e.g., "Month" NOT HydroBase "Monthly")
@return the matching HydroBase_StructureGeolocStructMeasType instance, or null if not matched.
*/
private HydroBase_StructureGeolocStructMeasType
    getStructureGeolocMeasTypeForWDID ( int wd, int id, String measType, String timeStep )
{
    // First get the cache
    String cacheKey = getStructureGeolocMeasTypeByWDListCacheKey( measType, timeStep, wd );
    List<HydroBase_StructureGeolocStructMeasType> cacheList =
        __structureGeolocMeasTypeByWDListCache.get(cacheKey);
    if ( cacheList == null ) {
        // Read the data and initialize the cache
        cacheList = readStructureGeolocMeasTypeList( -1, wd, measType, timeStep, true );
        if ( cacheList == null ) {
            cacheList = new Vector<HydroBase_StructureGeolocStructMeasType>();
        }
        __structureGeolocMeasTypeByWDListCache.put(cacheKey,cacheList);
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
{   List<String> types = new Vector<String>();
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
    if ( (include_types&HydroBase_Util.DATA_TYPE_STATION_CLIMATE) > 0 ) {
        prefix = "";
        if ( add_group ) {
            prefix = "Climate - ";
        }
        types.add ( prefix + "EvapPan" ); // Time step:  Day and Month, consistent with HydroBaseDMI
        types.add ( prefix + "FrostDateF32F" ); // Time step:  Year, consistent with HydroBaseDMI
        types.add ( prefix + "FrostDateF28F" );
        types.add ( prefix + "FrostDateL28S" );
        types.add ( prefix + "FrostDateL32S" );
        types.add ( prefix + "Precip" ); // Time step:  Month, Day, consistent with HydroBaseDMI
        types.add ( prefix + "TempMax" );// Time step:  Month, Day, consistent with HydroBaseDMI
        types.add ( prefix + "TempMean" ); // Time step:  Month, Day, consistent with HydroBaseDMI
        types.add ( prefix + "TempMin" ); // Time step:  Month, Day, consistent with HydroBaseDMI
        types.add ( prefix + "Snow" ); // Time step:  Month, Day, consistent with HydroBaseDMI
        types.add ( prefix + "SnowCourseDepth" ); // Time step:  Day, consistent with HydroBaseDMI
        types.add ( prefix + "SnowCourseSWE" ); // Time step:  Day, consistent with HydroBaseDMI
        types.add ( prefix + "Solar" ); // Time step:  Day, consistent with HydroBaseDMI
        types.add ( prefix + "VaporPressure" ); // Time step:  Day, consistent with HydroBaseDMI
        types.add ( prefix + "Wind" ); // Time step:  Day, consistent with HydroBaseDMI
    }
    if ( (include_types&HydroBase_Util.DATA_TYPE_STATION_STREAM) > 0 ) {
        // AdminFlow was added in 2007 for administrative gages - it has the
        // same data types as the main Streamflow...
        try {
            prefix = "";
            if ( add_group ) {
                prefix = "AdminFlow - ";
            }
            types.add ( prefix + "AdminFlow" ); // Time step:  Month, Day, consistent with HydroBaseDMI
            // TODO SAM 2012-05-14 Need to enable
            //types.add ( prefix + "AdminFlowMax" ); // Time step:  Month, Day, consistent with HydroBaseDMI
            //types.add ( prefix + "AdminFlowMin" ); // Time step:  Month, Day, consistent with HydroBaseDMI
        }
        catch ( Exception e ) {
            // Don't add AdminFlow
        }
        prefix = "";
        if ( add_group ) {
            prefix = "Stream - ";
        }
        types.add ( prefix + "Streamflow" ); // Time step:  Month, Day, consistent with HydroBaseDMI
        // TODO SAM 2012-05-15 not supported?
        //types.add ( prefix + "StreamflowMax" ); // Time step:  Month, Day, consistent with HydroBaseDMI
        //types.add ( prefix + "StreamflowMin" ); // Time step:  Month, Day, consistent with HydroBaseDMI
    }
    if ( (include_types&HydroBase_Util.DATA_TYPE_AGRICULTURE) > 0 ) {
        prefix = "";
        /* Not yet supported
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
        */
        // Data derived from DSS GIS work...
        if ( add_group ) {
            prefix = "Agriculture/GIS - ";
        }
        // irrig_summary_ts table
        types.add ( prefix + "CropAreaAllIrrigation" );
        types.add ( prefix + "CropAreaFlood" );
        types.add ( prefix + "CropAreaDrip" );
        types.add ( prefix + "CropAreaFurrow" );
        types.add ( prefix + "CropAreaSprinkler" );
    }
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
        types.add ( prefix + "IDivTotal" );
    }
    if ( (include_types&HydroBase_Util.DATA_TYPE_STRUCTURE_RESERVOIR) > 0 ) {
        prefix = "";
        if ( add_group ) {
            prefix = "Reservoir - ";
        }
        //types.add ( prefix + "IRelClass" );
        types.add ( prefix + "IRelTotal" );
        //types.add ( prefix + "RelClass" );
        //types.add ( prefix + "RelComment" );
        types.add ( prefix + "RelTotal" );
        types.add ( prefix + "ResEOM" );
        types.add ( prefix + "ResEOY" );
        types.add ( prefix + "ResMeasElev" );
        types.add ( prefix + "ResMeasEvap" );
        types.add ( prefix + "ResMeasFill" );
        types.add ( prefix + "ResMeasRelease" );
        types.add ( prefix + "ResMeasStorage" );
    }
    if ( (include_types&HydroBase_Util.DATA_TYPE_STRUCTURE_WELL) > 0 ) {
        prefix = "";
        if ( add_group ) {
            prefix = "Well - ";
        }
        // Also somehow need to hook in "WellPumping"
        types.add ( prefix + "WellLevelDepth" );
        types.add ( prefix + "WellLevelElev" );
    }
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
@param dataType the data type being queried (e.g., "DivTotal" or "Diversion), with no note.
@param timeStep the time step being queried as per TS conventions (e.g., "Month" NOT HydroBase "Monthly").
@param ifp the input filter panel that provides additional filter criteria
@return a list of time series header objects suitable for listing time series
*/
public List getTimeSeriesHeaderObjects ( String dataType, String timeStep, InputFilter_JPanel ifp )
{   String routine = __class + ".getTimeSeriesHeaderObjects";
    // Get the water district and division if necessary.
    boolean wdRequired = true; // Currently required on all queries
    List<Integer> districtList = new Vector<Integer>(); // Districts to query from cache
    int district = -1;
    int div = -1;
    if ( wdRequired ) {
        // District or division is required from the input filter to improve performance
        // The input string is something like "Equals;1" so get from the last part.
        List<String> inputDistrict = ifp.getInput("District", null, false, null );
        if ( inputDistrict.size() > 0 ) {
            String districtString = inputDistrict.get(0).split(";")[1];
            district = Integer.parseInt ( districtString );
        }
        List<String> inputDivision = ifp.getInput("Division", null, false, null );
        if ( inputDivision.size() > 0 ) {
            String divString = inputDivision.get(0).split(";")[1];
            div = Integer.parseInt ( divString );
        }
        if ( (district < 0) && (div < 0) ) {
            Message.printWarning ( 3, routine,
                "You must specify a district or division as a Where in the input filter." );
            return new Vector();
        }
        // Get the cached objects if available.  If not, read them.  Optimize a bit by handling divisions
        // and districts separately.  District takes precedence over division
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
    }
    // Currently want to use caches, unless that becomes a problem.
    // Caches are necessary to have reasonable performance
    boolean useCache = true;
    if( isStationTimeSeriesDataType(dataType)){
        List<HydroBase_StationGeolocMeasType> tslist = new Vector<HydroBase_StationGeolocMeasType>(); // List that matches the input request
        for ( Integer districtInList : districtList ) {
            String cacheKey = getStationGeolocMeasTypeByWDListCacheKey(dataType, timeStep, districtInList);
            List<HydroBase_StationGeolocMeasType> cacheList = __stationGeolocMeasTypeByWDListCache.get(cacheKey );
            List<HydroBase_StationGeolocMeasType> dataList = null;
            if ( cacheList == null ) {
                // No data have been read for the district and measType so do it.  This will actually read
                // all timesteps also and populate multiple hash tables, however, it will only return the
                // timestep of interest
                dataList = readStationGeolocMeasTypeList( districtInList, dataType, timeStep, useCache );
            }
            else {
                dataList = cacheList;
                Message.printStatus(2, routine, "Got cached data using key \"" + cacheKey + "\" size=" + cacheList.size() );
            }
            // Add to the returned list - this ensures that the cache list itself does not get manipulated
            tslist.addAll( dataList );
        }
        // Now further filter the list based on the parameters and input filter.  Remove items that do not
        // match the constraints.
        filterStationGeolocMeasType ( tslist, ifp );
        return tslist;
    }
    // Well is a structure but has special handling so put before the structure code
    else if ( (dataType.equalsIgnoreCase("WellLevelElev") || dataType.equalsIgnoreCase("WellLevelDepth"))
        && timeStep.equalsIgnoreCase("Day") ){
        List<HydroBase_GroundWaterWellsView> tslist = new Vector<HydroBase_GroundWaterWellsView>(); // List that matches the input request
        for ( Integer districtInList : districtList ) {
            String key = getGroundWaterWellsMeasTypeByWDListCacheKey(dataType, timeStep, districtInList);
            List<HydroBase_GroundWaterWellsView> cacheList = __groundWaterWellsMeasTypeByWDListCache.get(key );
            List<HydroBase_GroundWaterWellsView> dataList = null;
            if ( cacheList == null ) {
                // No data have been read for the district and measType so do it.  This will actually read
                // all timesteps also and populate multiple hash tables, however, it will only return the
                // timestep of interest
                dataList = readGroundWaterWellsMeasTypeList( districtInList, dataType, timeStep, useCache );
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
        filterGroundWaterWellsMeasType ( tslist, ifp );
        return tslist;
    }
    else if( isStructureTimeSeriesDataType(dataType)){
        List<HydroBase_StructureGeolocStructMeasType> tslist = new Vector<HydroBase_StructureGeolocStructMeasType>(); // List that matches the input request
        for ( Integer districtInList : districtList ) {
            String key = getStructureGeolocMeasTypeByWDListCacheKey(dataType, timeStep, districtInList);
            List<HydroBase_StructureGeolocStructMeasType> cacheList =
                __structureGeolocMeasTypeByWDListCache.get(key );
            List<HydroBase_StructureGeolocStructMeasType> dataList = null;
            if ( cacheList == null ) {
                // No data have been read for the district and measType so do it.  This will actually read
                // all timesteps also and populate multiple hash tables, however, it will only return the
                // timestep of interest
                dataList = readStructureGeolocMeasTypeList(div, districtInList, dataType, timeStep, useCache);
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
        return tslist;
    }
    else {
        Message.printWarning(3, routine, "Data type \"" + dataType + "\" is not a supported HBGuest type." );
    }
    return new Vector();
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
    List<String> v = new Vector<String>();
    // Alphabetize by data type, as much as possible...
    if ( data_type.equalsIgnoreCase("AdminFlow") ) {
        v.add ( Day );
        v.add ( Month );
        //v.add ( Irregular ); // Real-time - use ColoradoWaterSMS
    }
    else if ( data_type.equalsIgnoreCase("AdminFlowMax") ||
        data_type.equalsIgnoreCase("AdminFlowMin") ) {
        v.add ( Day );
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
        // v.add ( Irregular ); // Real-time - use ColoradoWaterSMS
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
        //v.add ( Irregular ); // Use ColoradoWaterSMS
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
        //v.add ( Month );
    }
    //else if(data_type.equalsIgnoreCase("TempMean") || data_type.equalsIgnoreCase("TempMeanMax") ||
    //    data_type.equalsIgnoreCase("TempMeanMin") ) {
    //    v.add ( Month );
    //}
    else if ( data_type.equalsIgnoreCase("TempMean") ) {
        //v.add ( Day );
        v.add ( Month );
    }
    else if ( data_type.equalsIgnoreCase("TempMin") ) {
        v.add ( Day );
        //v.add ( Month );
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
    else if ( data_type.equalsIgnoreCase("WellLevelElev") || data_type.equalsIgnoreCase("WellLevelDepth")) {
        // Implemented for GroundWaterWells
        if ( (include_types&HydroBase_Util.DATA_TYPE_STRUCTURE_WELL) != 0 ) {
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
Indicate whether a time series data type is for a station.
@param dataTypeToCheck A HydroBase data type.
If the data_type has a "-", then the token before the dash is compared.
*/
public boolean isStationTimeSeriesDataType ( String dataTypeToCheck )
{   if ( dataTypeToCheck.indexOf("-") >= 0) {
        // String is like "Stream - Streamflow" so data type is part after dash
        dataTypeToCheck = StringUtil.getToken(dataTypeToCheck,"-",0,1).trim();
    }
    // This should match the data type to check if a station
    List<String> dataTypes = getTimeSeriesDataTypes ( HydroBase_Util.DATA_TYPE_STATION_ALL, false );
    for ( String dataType: dataTypes ) {
        //Message.printStatus(2, "", "Comparing \"" + dataTypeToCheck + "\" against \"" + dataType + "\".");
        if ( dataType.equalsIgnoreCase(dataTypeToCheck) ) {
            return true;
        }
    }
    return false;
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
        //Message.printStatus(2, "", "Comparing \"" + dataTypeToCheck + "\" against \"" + dataType + "\".");
        if ( dataType.equalsIgnoreCase(dataTypeToCheck) ) {
            return true;
        }
    }
    return false;
}

/**
Helper method to create a HydroBase_GroundWaterWellsView object from a web service
GroundWaterWellsMeasType object.
@param mt web service object
@param measType the measType to use with TSTool and other software, which may be different from the
HydroBase measType, needed, for example because HydroBase measType may not be unique enough
(ResMeas vs. ResMeasElev)
@param timeStep the timeStep to use with TSTool and other software, which is likely different than
HydroBase; for example use "Day" instead of "Daily"
@return HydroBase_StructureGeolocStructMeasType instance constructed from service object
*/
private HydroBase_GroundWaterWellsView newHydroBase_GroundWaterWellsView (
    GroundWaterWellsMeasType mt, String measType, String timeStep, String units )
{   HydroBase_GroundWaterWellsView hbwell = new HydroBase_GroundWaterWellsView();
    // Passed in
    hbwell.setData_units(units);
    // From Structure
    hbwell.setDiv(mt.getDiv());
    hbwell.setWD(mt.getWd());
    hbwell.setID(mt.getId());
    //hbwell.setStr_name(?);
    hbwell.setWell_name(mt.getWellName());
    hbwell.setCounty(mt.getCounty());
    //hbwell.setST(mt.getSt());
    hbwell.setHUC(mt.getHuc());
    // Meastype
    if ( mt.getStartYear() != null ) {
        hbwell.setStart_year(mt.getStartYear());
    }
    if ( mt.getEndYear() != null ) {
        hbwell.setEnd_year(mt.getEndYear());
    }
    hbwell.setMeas_type(measType);
    // FIXME SAM 2010-08-13 Need to not hard-code this
    hbwell.setData_source("DWR");
    // Meas count not available?
    //hbwell.setMeas_num(mt.getMeasNum());
    // This comes from the calling code, in particular because HydroBase Annual corresponds to
    // Year and Month data
    hbwell.setTime_step(timeStep);
    // from Geoloc
    hbwell.setPM(mt.getPm());
    if ( mt.getTs() != null ) {
        hbwell.setTS(mt.getTs());
    }
    hbwell.setTdir(mt.getTdir());
    hbwell.setTsa(mt.getTsa());
    if ( mt.getRng() != null ) {
        hbwell.setRng(mt.getRng());
    }
    hbwell.setRdir(mt.getRdir());
    hbwell.setRnga(mt.getRnga());
    if ( mt.getSec() != null ) {
        hbwell.setSec(mt.getSec());
    }
    hbwell.setSeca(mt.getSeca());
    hbwell.setQ160(mt.getQ160());
    hbwell.setQ40(mt.getQ40());
    hbwell.setQ10(mt.getQ10());
    BigDecimal d = mt.getLongdecdeg();
    if ( d != null ) {
        hbwell.setLongdecdeg(d.doubleValue());
    }
    d = mt.getLatdecdeg();
    if ( d != null ) {
        hbwell.setLatdecdeg(d.doubleValue());
    }
    d = mt.getUTMX();
    if ( d != null ) {
        hbwell.setUtm_x(d.doubleValue());
    }
    d = mt.getUTMY();
    if ( d != null ) {
        hbwell.setUtm_y(d.doubleValue());
    }
    // From new groundwater well data ...
    hbwell.setWell_num(mt.getWellNum());
    hbwell.setCounty(mt.getCounty());
    //hbwell.setSt(mt.getSt());
    hbwell.setTopomap(mt.getTopomap());
    if ( mt.getCty() != null ) {
        hbwell.setCty(mt.getCty());
    }
    hbwell.setHUC(mt.getHuc());
    if ( mt.getElev() != null ) {
        hbwell.setElev(mt.getElev());
    }
    //hbwell.setElev_accuracy(mt.getElevAccuracy());
    hbwell.setLoc_type(mt.getLocTypeDesc());
    if ( mt.getCoordsns() != null ) {
        hbwell.setCoordsns(mt.getCoordsns());
    }
    hbwell.setCoordsns_dir(mt.getCoordsnsDir());
    if ( mt.getCoordsew() != null ) {
        hbwell.setCoordsew(mt.getCoordsew());
    }
    hbwell.setCoordsew_dir(mt.getCoordsewDir());

    hbwell.setWell_name(mt.getWellName());
    hbwell.setReceipt(mt.getReceipt());
    if ( mt.getPermitno() != null ) {
        hbwell.setPermitno(mt.getPermitno());
    }
    hbwell.setPermitsuf(mt.getPermitsuf());
    hbwell.setPermitrpl(mt.getPermitrpl());
    hbwell.setMD(mt.getMd());
    hbwell.setBasin(mt.getBasin());
    hbwell.setAquifer1(mt.getAquifer1());
    hbwell.setAquifer2(mt.getAquifer2());
    if ( mt.getTperf() != null ) {
        hbwell.setTperf(mt.getTperf());
    }
    if ( mt.getBperf() != null ) {
        hbwell.setBperf(mt.getBperf());
    }
    if ( mt.getYield() != null ) {
        hbwell.setYield(mt.getYield());
    }
    hbwell.setAquifer_comment(mt.getAquiferComment());
    if ( mt.getBedrockElev() != null ) {
        hbwell.setBedrock_elev(mt.getBedrockElev());
    }
    if ( mt.getSat1965() != null ) {
        hbwell.setSat_1965(mt.getSat1965());
    }
    hbwell.setRemarks1(mt.getRemarks1());
    hbwell.setRemarks2(mt.getRemarks2());
    hbwell.setData_source(mt.getDataSource());
    hbwell.setData_source_id(mt.getDataSourceId());
    //hbwell.setData_units(mt.getData_units);
    hbwell.setLocnum(mt.getLocnum());
    hbwell.setSite_id(mt.getSiteID());
    if ( mt.getWellDepth() != null ) {
        hbwell.setWell_depth(mt.getWellDepth());
    }
    //hbwell.setAccuracy(mt.getAccuracy());
    if ( mt.getStreamNum() != null ) {
        hbwell.setStream_num(mt.getStreamNum());
    }
    if ( mt.getStrMile() != null ) {
        hbwell.setStr_mile(mt.getStrMile());
    }
    hbwell.setSpotter_version(mt.getSpotterVersion());
    if ( mt.getStartYear() != null ) {
        hbwell.setStart_year(mt.getStartYear());
    }
    if ( mt.getEndYear() != null ) {
        hbwell.setEnd_year(mt.getEndYear());
    }
    //hbwell.setStructure_num(mt.getStructure_num());
    //hbwell.setUsgs_id(mt.getUsgs_id());
    //hbwell.setUsbr_id(mt.getUsbr_id());
    hbwell.setWell_meas_num(mt.getWellMeasNum());
    //hbwell.setMeas_type(mt.getMeasType()); // Set above with requested value
    hbwell.setTime_step(mt.getTimeStep());
    if ( mt.getMeasCount() != null ) {
        hbwell.setMeas_count(mt.getMeasCount());
    }
    hbwell.setIdentifier(mt.getIdentifier());
    //hbwell.setLog_depth(mt.getLog_depth());
    //hbwell.setLog_type(mt.getLog_type());
    //hbwell.setLog_swl(mt.getLog_swl());
    //hbwell.setLog_date(mt.getLog_date());

    hbwell.setDSS_aquifer1(mt.getDSSAquifer1());
    hbwell.setDSS_aquifer2(mt.getDSSAquifer2());
    hbwell.setDSS_aquifer_comment(mt.getDSSAquiferComment());
    return hbwell;
}

/**
Helper method to create a HydroBase_StationGeolocMeasType object from a web service StationGeolocMeasType object.
@param sgmt web service object
@param measType the measType to use with TSTool and other software, which may be different from the
HydroBase measType, needed, for example because HydroBase measType may not be unique enough
(AdminFlow vs. AdminFlowMax)
@param timeStep the timeStep to use with TSTool and other software, which is likely different than
HydroBase; for example use "Day" instead of "Daily"
@return HydroBase_StructureGeolocStructMeasType instance constructed from service object
*/
private HydroBase_StationGeolocMeasType newHydroBase_StationGeolocMeasType (
    StationGeolocMeasType sgmt, String measType, String timeStep )
{   HydroBase_StationGeolocMeasType hbsta = new HydroBase_StationGeolocMeasType();
    if ( sgmt.getDiv() != null ) {
        hbsta.setDiv(sgmt.getDiv());
    }
    if ( sgmt.getWd() != null ) {
        hbsta.setWD(sgmt.getWd());
    }
    hbsta.setAbbrev(sgmt.getAbbrev());
    hbsta.setStation_id(sgmt.getStationId());
    hbsta.setStation_name(sgmt.getStationName());
    // Geoloc...
    hbsta.setCounty(sgmt.getCounty());
    hbsta.setST(sgmt.getSt());
    hbsta.setHUC(sgmt.getHuc());
    BigDecimal d = sgmt.getLongdecdeg();
    if ( d != null ) {
        hbsta.setLongdecdeg(d.doubleValue());
    }
    d = sgmt.getLatdecdeg();
    if ( d != null ) {
        hbsta.setLatdecdeg(d.doubleValue());
    }
    d = sgmt.getUTMX();
    if ( d != null ) {
        hbsta.setUtm_x(d.doubleValue());
    }
    d = sgmt.getUTMY();
    if ( d != null ) {
        hbsta.setUtm_y(d.doubleValue());
    }
    // Meastype
    if ( sgmt.getStartYear() != null ) {
        hbsta.setStart_year(sgmt.getStartYear());
    }
    if ( sgmt.getEndYear() != null ) {
        hbsta.setEnd_year(sgmt.getEndYear());
    }
    hbsta.setMeas_type(measType);
    if ( sgmt.getMeasCount() != null ) {
        hbsta.setMeas_count(sgmt.getMeasCount());
    }
    // FIXME SAM 2012-050-15 does not seem consistent with HydroBase
    // For now set data source to be same as source
    // Data source is what we want to use for time series.
    //hbsta.setData_source(sgmt.getData_source()); // Use this for the time series
    hbsta.setData_source(sgmt.getSource());
    hbsta.setSource(sgmt.getSource()); // This is for the station
    // Meas count not available?
    hbsta.setMeas_num(sgmt.getMeasNum());
    // This comes from the calling code, in particular because HydroBase Annual corresponds to
    // Year and Month data
    hbsta.setTime_step(timeStep);
    return hbsta;
}

/**
Helper method to create a HydroBase_StructureGeolocStructMeasType object from a web service
StructureGeolocMeasType object.
@param sgmt web service object
@param measType the measType to use with TSTool and other software, which may be different from the
HydroBase measType, needed, for example because HydroBase measType may not be unique enough
(ResMeas vs. ResMeasElev)
@param timeStep the timeStep to use with TSTool and other software, which is likely different than
HydroBase; for example use "Day" instead of "Daily"
@return HydroBase_StructureGeolocStructMeasType instance constructed from service object
*/
private HydroBase_StructureGeolocStructMeasType newHydroBase_StructureGeolocMeasType (
    StructureGeolocMeasType sgmt, String measType, String timeStep )
{   HydroBase_StructureGeolocStructMeasType hbstruct = new HydroBase_StructureGeolocStructMeasType();
    hbstruct.setDiv(sgmt.getDiv());
    hbstruct.setWD(sgmt.getWd());
    hbstruct.setID(sgmt.getId());
    hbstruct.setStr_name(sgmt.getStrName());
    // Geoloc
    hbstruct.setCounty(sgmt.getCounty());
    hbstruct.setST(sgmt.getSt());
    hbstruct.setHUC(sgmt.getHuc());
    BigDecimal d = sgmt.getLongdecdeg();
    if ( d != null ) {
        hbstruct.setLongdecdeg(d.doubleValue());
    }
    d = sgmt.getLatdecdeg();
    if ( d != null ) {
        hbstruct.setLatdecdeg(d.doubleValue());
    }
    d = sgmt.getUTMX();
    if ( d != null ) {
        hbstruct.setUtm_x(d.doubleValue());
    }
    d = sgmt.getUTMY();
    if ( d != null ) {
        hbstruct.setUtm_y(d.doubleValue());
    }
    // Meastype
    hbstruct.setStart_year(sgmt.getStartYear());
    hbstruct.setEnd_year(sgmt.getEndYear());
    hbstruct.setMeas_type(measType);
    // FIXME SAM 2010-08-13 Need to not hard-code this, but does not seem to be available from web service
    hbstruct.setData_source("DWR");
    // Meas count not available?
    hbstruct.setMeas_num(sgmt.getMeasNum());
    // This comes from the calling code, in particular because HydroBase Annual corresponds to
    // Year and Month data
    hbstruct.setTime_step(timeStep);
    return hbstruct;
}

/**
Read HydroBase_GroundWaterWellsView objects using web services.  The list for a water district and
measType combination are read.  This method should only be called if reading every time or the cache does not
exist and needs to be initialized.  Caches are initialized for both WellLevelEleve and WellLevelDepth data types
(takes more memory but is consistent with design since data type is used from object later).
@param wd water district of interest (-1 to ignore constraint)
@param dataTypeReq requested data type from TSTool ("WellLevelElev" or "WellLevelDepth")
@param timeStep time series time step as per TS conventions (e.g, "Day" NOT HydroBase "Daily")
@param cacheIt if true, cache the result, false to not cache (slower but use less memory long-term) - caches
are saved by measType-timeStep-wd combination
*/
private List<HydroBase_GroundWaterWellsView> readGroundWaterWellsMeasTypeList(
    int wd, String dataTypeReq, String timeStep, boolean cacheIt )
{   String routine = __class + ".readGroundWaterWellsMeasTypeList";
    Holder<HbStatusHeader> status = new Holder<HbStatusHeader>();
    StopWatch sw = new StopWatch();
    sw.start();
    String dataType1 = "WellLevelElev";
    String dataType2 = "WellLevelDepth";
    List<HydroBase_GroundWaterWellsView> returnList = new Vector<HydroBase_GroundWaterWellsView>();
    if ( wd > 0 ) {
        // Read data for one water district
        ArrayOfGroundWaterWellsMeasType mtArray = getColoradoWaterHBGuestSoap12().getHBGuestGroundWaterWellsMeasTypeByWD(
            wd, getAuthentication(), status );
        sw.stop();
        // Check for error
        if ( (status.value != null) && (status.value.getError() != null) ) {
            throw new RuntimeException ( "Error getting GroundWaterWellsMeasType for wd=" + wd +
                " dataType=\"" + dataTypeReq + "\" (" + status.value.getError().getErrorCode() + ": " +
                status.value.getError().getExceptionDescription() + ")." );
        }
        Message.printStatus(2, routine,
            "Retrieved " + mtArray.getGroundWaterWellsMeasType().size() + " GroundWaterWellsMeasType for WD=" + wd +
            " MeasType=\"" + dataTypeReq + "\" in " + sw.getSeconds() + " seconds.");
        // Loop through once and get the list of unique timesteps so a cache can be created for each
        List<String> timeStepList = new Vector<String>();
        //boolean found;
        String hbTimeStep = "";
        //String timeStepUpper;
        if ( cacheIt ) {
            // Initialize each cache
            String cacheKey = getStructureGeolocMeasTypeByWDListCacheKey(dataType1, "Day", wd);
            Message.printStatus ( 2, routine, "Initializing cache with key \"" + cacheKey + "\"." );
            __groundWaterWellsMeasTypeByWDListCache.put(cacheKey, new Vector<HydroBase_GroundWaterWellsView>() );
            cacheKey = getStructureGeolocMeasTypeByWDListCacheKey(dataType2, "Day", wd);
            Message.printStatus ( 2, routine, "Initializing cache with key \"" + cacheKey + "\"." );
            __groundWaterWellsMeasTypeByWDListCache.put(cacheKey, new Vector<HydroBase_GroundWaterWellsView>() );
        }
        // Loop through the results (by water district and division) and add to the appropriate cached list
        // Sometimes multiple records are returned because the results are a join with owners (multiple owners)
        // Therefore check the results against the previous record and only add if not the same
        List<HydroBase_GroundWaterWellsView> cacheList;
        String cacheTimeStep;
        //GroundWaterWellsMeasType mtPrev = null;
        for ( GroundWaterWellsMeasType mt : mtArray.getGroundWaterWellsMeasType() ) {
            // Only the timestep varies by the record since the measType and district were passed in
            // sgmt.getTimeStep() will return "Daily" or "Annual"
            /* TODO SAM 2012-05-07 Not sure this is relevant
            if ( (mtPrev != null) && (mt.getWd() == mtPrev.getWd()) &&
                (mt.getId() == mtPrev.getId()) && mt.getTimeStep().equals(mtPrev.getTimeStep()) ) {
                // Critical information is the same as the previous record so skip
                continue;
            }
            */
            hbTimeStep = mt.getTimeStep();
            if ( hbTimeStep.equalsIgnoreCase("Random") ) {
                // For reservoir measurements (Day) and well level (also day?)
                cacheTimeStep = "Day";
            }
            else {
                cacheTimeStep = HydroBase_Util.convertFromHydroBaseTimeStep(hbTimeStep);
            }
            cacheList = __groundWaterWellsMeasTypeByWDListCache.get (getGroundWaterWellsMeasTypeByWDListCacheKey(
                dataType1, cacheTimeStep, wd));
            cacheList.add ( newHydroBase_GroundWaterWellsView( mt, dataType1, cacheTimeStep, "FT" ) );
            cacheList = __groundWaterWellsMeasTypeByWDListCache.get (getGroundWaterWellsMeasTypeByWDListCacheKey(
                dataType2, cacheTimeStep, wd));
            cacheList.add ( newHydroBase_GroundWaterWellsView( mt, dataType2, cacheTimeStep, "FT" ) );
            //mtPrev = mt;
        }
        if ( cacheIt ) {
            // Logging...
            for ( String timeStepInList: timeStepList ) {
                String cacheKey = getGroundWaterWellsMeasTypeByWDListCacheKey(dataType1, timeStepInList, wd);
                cacheList = __groundWaterWellsMeasTypeByWDListCache.get(cacheKey);
                Message.printStatus ( 2, routine,
                    "After reading data, cache for key \"" + cacheKey + "\" has size=" + cacheList.size() );
                cacheKey = getGroundWaterWellsMeasTypeByWDListCacheKey(dataType2, timeStepInList, wd);
                cacheList = __groundWaterWellsMeasTypeByWDListCache.get(cacheKey);
                Message.printStatus ( 2, routine,
                    "After reading data, cache for key \"" + cacheKey + "\" has size=" + cacheList.size() );
            }
        }
        // Add to the returned list only the requested list
        String cacheKey = getGroundWaterWellsMeasTypeByWDListCacheKey(dataTypeReq, timeStep, wd);
        cacheList = __groundWaterWellsMeasTypeByWDListCache.get(cacheKey);
        if ( cacheList != null ) {
            Message.printStatus ( 2, routine, "Returning cache for key \"" + cacheKey + "\" size=" +
                cacheList.size() + "." );
            returnList.addAll ( cacheList );
        }
        else {
            Message.printStatus ( 2, routine, "Problem?  No cache for key \"" + cacheKey + "\"." );
        }
    }
    return returnList;
}

/**
Read HydroBase_StationGeolocStructMeasType objects using web services.  The list for a water district and
measType combination are read.  This method should only be called if reading every time or the cache does not
exist and needs to be initialized.  Important, use the getTimeSeriesHeaderObjects() to retrieve the list for
the application, which will check for a cached item first.
@param wd water district of interest (-1 to ignore constraint)
@param dataType time series data type as per TSTool (e.g., "Streamflow") - may be slightly different
for HydroBase query
@param timeStep time series time step as per TS conventions (e.g, "Month" NOT HydroBase "Monthly")
@param useCache if true, cache the result, false to not cache (slower but use less memory long-term) - caches
are saved by measType-timeStep-wd combination
@return the list of HydroBase_StationGeolocMeasType, guaranteed to be non-null
*/
private List<HydroBase_StationGeolocMeasType> readStationGeolocMeasTypeList(
    int wd, String dataType, String timeStep, boolean useCache )
{   String routine = __class + ".readStationGeolocMeasTypeList";
    Holder<HbStatusHeader> status = new Holder<HbStatusHeader>();
    StopWatch sw = new StopWatch();
    sw.start();
    if ( dataType == null ) {
        dataType = ""; // Web service does not like null
    }
    List<HydroBase_StationGeolocMeasType> returnList = new Vector<HydroBase_StationGeolocMeasType>();
    // Only used for query but all else uses more specific data type
    String measTypeHydroBase = getHydroBaseMeasTypeFromDataType(dataType);
    if ( wd > 0 ) {
        // Read data for one water district
        // This query does NOT take into account the requested time step so multiple intervals may be returned
        // for a data type (e.g., "Precip" will have "Daily" and "Monthly" time series).  In these cases,
        // caches are returned for each interval, using the requested data type
        // This may take more memory but keeps the lists separate... and there is potential that the list for
        // one interval actually is different from other intervals
        ArrayOfStationGeolocMeasType sgmtArray = getColoradoWaterHBGuestSoap12().getHBGuestStationGeolocMeasTypeByWD(
            wd, measTypeHydroBase, getAuthentication(), status );
        sw.stop();
        // Check for error
        if ( (status.value != null) && (status.value.getError() != null) ) {
            throw new RuntimeException ( "Error getting StationGeolocMeasType for wd=" + wd +
                " measType(HydroBase)=\"" + dataType + "\" (" + measTypeHydroBase + ") (" +
                status.value.getError().getErrorCode() + ": " +
                status.value.getError().getExceptionDescription() + ")." );
        }
        Message.printStatus(2, routine,
            "Retrieved " + sgmtArray.getStationGeolocMeasType().size() + " StationGeolocMeasType for WD=" + wd +
            " MeasType(HydroBase)=\"" + dataType + "\" (" + measTypeHydroBase + ") in " + sw.getSeconds() +
            " seconds.");
        // Loop through once and get the list of unique timesteps so a cache can be created for each.
        // This is necessary because, for example, requesting "Precip" meastypes for a WD may return
        // day and month intervals
        List<String> timeStepList = new Vector<String>();
        boolean found;
        String hbTimeStep;
        String timeStepUpper;
        if ( useCache ) {
            for ( StationGeolocMeasType sgmt : sgmtArray.getStationGeolocMeasType() ) {
                found = false;
                hbTimeStep = sgmt.getTimeStep();
                if ( hbTimeStep.equalsIgnoreCase("Random") ) {
                    // For reservoir measurements (Day) and well level (also day?)
                    timeStepUpper = timeStep.toUpperCase();
                }
                else {
                    timeStepUpper = HydroBase_Util.convertFromHydroBaseTimeStep(hbTimeStep).toUpperCase();
                }
                // Check the timesteps that previously have been added to the list
                for ( String timeStepInList: timeStepList ) {
                    if ( timeStepInList.equals(timeStepUpper) ) {
                        found = true;
                        break;
                    }
                }
                if ( !found ) {
                    timeStepList.add ( timeStepUpper );
                }
            }
            // Initialize a cache for each timestep
            for ( String timeStepInList: timeStepList ) {
                String key = getStationGeolocMeasTypeByWDListCacheKey(dataType, timeStepInList, wd);
                Message.printStatus ( 2, routine, "Initializing cache with key \"" + key + "\"." );
                __stationGeolocMeasTypeByWDListCache.put(key, new Vector<HydroBase_StationGeolocMeasType>() );
            }
        }
        // Loop through the results (by water district and division) and add to the appropriate cached list.
        // There are a lot of lookups to get the appropriate cache
        List<HydroBase_StationGeolocMeasType> cacheList;
        String cacheTimeStep;
        for ( StationGeolocMeasType sgmt : sgmtArray.getStationGeolocMeasType() ) {
            hbTimeStep = sgmt.getTimeStep();
            if ( hbTimeStep.equalsIgnoreCase("Random") ) {
                // TODO SAM 2012-05-16 Is this clause relevant here? copied code from structures
                // For reservoir measurements (Day) and well level (also day?)
                cacheTimeStep = "Day";
            }
            else {
                cacheTimeStep = HydroBase_Util.convertFromHydroBaseTimeStep(hbTimeStep);
            }
            String cacheKey = getStationGeolocMeasTypeByWDListCacheKey(dataType, cacheTimeStep, wd);
            cacheList = __stationGeolocMeasTypeByWDListCache.get (cacheKey);
            if ( cacheList == null ) {
                // Create a new list
                cacheList = new Vector<HydroBase_StationGeolocMeasType>();
            }
            //Message.printStatus(2,routine,"Adding stationMeasType to key \"" + key + "\" for dataType=\"" +
            //    dataType + "\", timeStep=\"" + cacheTimeStep + "\"" );
            cacheList.add ( newHydroBase_StationGeolocMeasType( sgmt, dataType, cacheTimeStep ) );
        }
        if ( useCache ) {
            // Logging...
            for ( String timeStepInList: timeStepList ) {
                String key = getStationGeolocMeasTypeByWDListCacheKey(dataType, timeStepInList, wd);
                cacheList = __stationGeolocMeasTypeByWDListCache.get(key);
                Message.printStatus ( 2, routine,
                    "After reading data, cache for key \"" + key + "\" has size=" + cacheList.size() );
            }
        }
        // Add to the returned list only that which matches the requested parameters
        String cacheKey = getStationGeolocMeasTypeByWDListCacheKey(dataType, timeStep, wd);
        cacheList = __stationGeolocMeasTypeByWDListCache.get(cacheKey);
        if ( cacheList != null ) {
            Message.printStatus ( 2, routine, "Returning cache for key \"" + cacheKey + "\" size=" +
                cacheList.size() + "." );
            returnList.addAll ( cacheList );
        }
        else {
            Message.printStatus ( 2, routine, "Problem?  No cache for key \"" + cacheKey + "\"." );
        }
    }
    return returnList;
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
    List<HydroBase_StructMeasTypeView> structList = new Vector<HydroBase_StructMeasTypeView>();
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
    List<HydroBase_StructureGeolocStructMeasType> returnList = new Vector<HydroBase_StructureGeolocStructMeasType>();
    // Only use measTypeHydroBase for query but all else uses more specific data type
    String measTypeHydroBase = getHydroBaseMeasTypeFromDataType ( measType );
    if ( wd > 0 ) {
        // Read data for one water district
        ArrayOfStructureGeolocMeasType sgmtArray = getColoradoWaterHBGuestSoap12().getHBGuestStructureGeolocMeasTypeByWD(
            wd, measTypeHydroBase, getAuthentication(), status );
        sw.stop();
        // Check for error
        if ( (status.value != null) && (status.value.getError() != null) ) {
            throw new RuntimeException ( "Error getting StructureGeolocMeasType for wd=" + wd +
                " measType=\"" + measTypeHydroBase + "\" (" + status.value.getError().getErrorCode() + ": " +
                status.value.getError().getExceptionDescription() + ")." );
        }
        Message.printStatus(2, routine,
            "Retrieved " + sgmtArray.getStructureGeolocMeasType().size() + " StructureGeolocMeasType for WD=" + wd +
            " MeasType=\"" + measType + "\" in " + sw.getSeconds() + " seconds.");
        // Loop through once and get the list of unique timesteps so a cache can be created for each
        List<String> timeStepList = new Vector<String>();
        boolean found;
        String hbTimeStep;
        String timeStepUpper;
        if ( cacheIt ) {
            for ( StructureGeolocMeasType sgmt : sgmtArray.getStructureGeolocMeasType() ) {
                found = false;
                hbTimeStep = sgmt.getTimeStep();
                if ( hbTimeStep.equalsIgnoreCase("Random") ) {
                    // For reservoir measurements (Day) and well level (also day?)
                    timeStepUpper = timeStep.toUpperCase();
                }
                else {
                    timeStepUpper = HydroBase_Util.convertFromHydroBaseTimeStep(hbTimeStep).toUpperCase();
                }
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
                __structureGeolocMeasTypeByWDListCache.put(key, new Vector<HydroBase_StructureGeolocStructMeasType>() );
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
            hbTimeStep = sgmt.getTimeStep();
            if ( hbTimeStep.equalsIgnoreCase("Random") ) {
                // For reservoir measurements (Day) and well level (also day?)
                cacheTimeStep = "Day";
            }
            else {
                cacheTimeStep = HydroBase_Util.convertFromHydroBaseTimeStep(hbTimeStep);
            }
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
Read time series for the requested time series identifier.  The data type in the TSID should match the TSTool
convention, not the HydroBase measType string.
@param tsidentString time series identifier string, as per ColoradoWaterHBGuest data store documentation
@param readStart starting date/time for period to read
@param readEnd ending date/time for period to read
@param units requested data units (currently ignored - no conversion occurs)
@param readData if true read the data records; if false initialize the time series header information but
don't read data records
*/
public TS readTimeSeries ( String tsidentString, DateTime readStart, DateTime readEnd, String units,
    boolean readData )
throws Exception
{   String routine = __class + ".readTimeSeries";
    TS ts = TSUtil.newTimeSeries(tsidentString, true);
    TSIdent tsident = new TSIdent(tsidentString);
    String dataType = tsident.getType();
    String dataSource = tsident.getSource();
    String timeStep = tsident.getInterval();
    String locID = tsident.getLocation();
    int [] wdidParts = null;
    ts.setIdentifier(tsident);
    // Lookup the StructureGeolocMeasType instance - used for description and default period (the available)
    // Start and end years from data
    int dataStartYear = -999;
    int dataEndYear = -999;
    String description = "";
    HydroBase_StationGeolocMeasType hbsta = null;
    HydroBase_StructureGeolocStructMeasType hbstruct = null;
    HydroBase_GroundWaterWellsView hbwell = null;
    // Set the metadata
    boolean setPropertiesFromMetadata = true;
    // Replace TSTool data type with HydroBase MeasType (differences are mainly for usability/uniqueness)
    // Station types...
    boolean isStation = false;
    if ( isStationTimeSeriesDataType(dataType) ) {
        // Station data type
        isStation = true;
        // The following will initialize caches if necessary
        hbsta = getStationGeolocMeasTypeForStationID ( locID, dataSource, dataType, timeStep );
        if ( hbsta == null ) {
            String message =
                "Unable to get StationGeolocMeasType information for ID \"" + locID + "\" - " +
                    "can't read time series \"" + tsidentString + "\".";
            Message.printWarning(3, routine, message);
            throw new RuntimeException ( message );
        }
        dataStartYear = hbsta.getStart_year();
        dataEndYear = hbsta.getEnd_year();
        description = hbsta.getStation_name();
        if ( setPropertiesFromMetadata ) {
            HydroBase_Util.setTimeSeriesProperties ( ts, hbsta );
        }
    }
    else if ( dataType.equalsIgnoreCase("WellLevelElev") || dataType.equalsIgnoreCase("WellLevelDepth")) {
        // GroundWaterWell data type
        hbwell = getGroundWaterWellsMeasTypeForIdentifier ( locID, dataSource, dataType, timeStep );
        if ( hbwell == null ) {
            String message =
                "Unable to get GroundWaterWellsView information for identifier \"" + locID + "\" - " +
                    "can't read time series \"" + tsidentString + "\".";
            Message.printWarning(3, routine, message);
            throw new RuntimeException ( message );
        }
        dataStartYear = hbwell.getStart_year();
        dataEndYear = hbwell.getEnd_year();
        description = hbwell.getWell_name() + " (datum " + StringUtil.formatString(hbwell.getElev(),"%.1f") + " FT)";
        if ( setPropertiesFromMetadata ) {
            HydroBase_Util.setTimeSeriesProperties ( ts, hbwell );
        }
    }
    else if ( isStructureTimeSeriesDataType(dataType) ) {
        // Structure data type
        wdidParts = HydroBase_WaterDistrict.parseWDID(locID);
        hbstruct = getStructureGeolocMeasTypeForWDID ( wdidParts[0], wdidParts[1], dataType, timeStep );
        if ( hbstruct == null ) {
            String message =
                "Unable to get StructureGeolocMeasType information for WDID \"" + locID + "\" - " +
                    "can't read time series \"" + tsidentString + "\".";
            Message.printWarning(3, routine, message);
            throw new RuntimeException ( message );
        }
        dataStartYear = hbstruct.getStart_year();
        dataEndYear = hbstruct.getEnd_year();
        description = hbstruct.getStr_name();
        if ( setPropertiesFromMetadata ) {
            HydroBase_Util.setTimeSeriesProperties ( ts, hbstruct );
        }
    }
    // Default the dates to 1900 to current time
    if ( readStart == null ) {
        int startYear = dataStartYear;
        if ( startYear <= 0 ) {
            // Default to 1900
            startYear = 1900; // This is irrigation year
        }
        if ( !isStation ) {
            --startYear; // HydroBase has irrigation year so decrement to get to previous calendar year
        }
        if ( timeStep.equalsIgnoreCase("Month")) {
            if ( isStation ) {
                // Apparently also need day
                readStart = DateTime.parse("" + startYear + "-01-01");
            }
            else {
                readStart = DateTime.parse("" + startYear + "-11");
            }
        }
        else if ( timeStep.equalsIgnoreCase("Day")) {
            if ( isStation ) {
                // Apparently also need day
                readStart = DateTime.parse("" + startYear + "-01-01");
            }
            else {
                readStart = DateTime.parse("" + startYear + "-11-01");
            }
        }
        else if ( timeStep.equalsIgnoreCase("Year")) {
            readStart = DateTime.parse("" + startYear, DateTime.FORMAT_YYYY);
        }
    }
    if ( readEnd == null ) {
        int endYear = dataEndYear;
        if ( endYear <= 0 ) {
            // Default to current year
            DateTime current = new DateTime(DateTime.DATE_CURRENT);
            endYear = current.getYear(); // This is irrigation year
            if ( (current.getMonth() >= 11) && !timeStep.equalsIgnoreCase("Year") ) {
                ++endYear; // To include full irrigation year that extends into next year
            }
        }
        if ( timeStep.equalsIgnoreCase("Month")) {
            if ( isStation ) {
                readEnd = DateTime.parse("" + endYear + "-12-31");
            }
            else {
                readEnd = DateTime.parse("" + endYear + "-10");
            }
        }
        else if ( timeStep.equalsIgnoreCase("Day")) {
            if ( isStation ) {
                readEnd = DateTime.parse("" + endYear + "-12-31");
            }
            else {
                readEnd = DateTime.parse("" + endYear + "-10-31");
            }
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
    String dataUnits = HydroBase_Util.getTimeSeriesDataUnits(null, dataType, timeStep);
    // Special case because above call does not handle Precip and temperature (there is a reason)
    if ( dataType.equalsIgnoreCase("Precip") ) {
        dataUnits = "IN";
    }
    if ( dataType.equalsIgnoreCase("TempMax") || dataType.equalsIgnoreCase("TempMean") ||
        dataType.equalsIgnoreCase("TempMin")) {
        dataUnits = "F";
    }
    ts.setDataUnits(dataUnits);
    ts.setDataUnitsOriginal(dataUnits);
    ts.setDescription ( description );
    
    Holder<HbStatusHeader> status = new Holder<HbStatusHeader>();
    
    StopWatch sw = new StopWatch();
    sw.start();
    // Whether the time series data type, interval, etc. are handled; 
    // however, there may still be errors (no data in period, etc.) that lead to exceptions
    boolean tsIsHandled = false;
    if ( dataType.equalsIgnoreCase("AdminFlow") ||
        dataType.equalsIgnoreCase("EvapPan") ||
        dataType.equalsIgnoreCase("FrostDateF28F") ||
        dataType.equalsIgnoreCase("FrostDateF32F") ||
        dataType.equalsIgnoreCase("FrostDateL28S") ||
        dataType.equalsIgnoreCase("FrostDateL32S") ||
        dataType.equalsIgnoreCase("Precip") ||
        dataType.equalsIgnoreCase("Snow") ||
        dataType.equalsIgnoreCase("SnowCourseDepth") || // Use SnowCourse query
        dataType.equalsIgnoreCase("SnowCourseSWE") || // Use SnowCourse query
        dataType.equalsIgnoreCase("Solar") ||
        dataType.equalsIgnoreCase("Streamflow") ||
        dataType.equalsIgnoreCase("TempMax") ||
        dataType.equalsIgnoreCase("TempMean") ||
        dataType.equalsIgnoreCase("TempMin") ||
        dataType.equalsIgnoreCase("VaporPressure") ||
        dataType.equalsIgnoreCase("Wind") ) {
        // Station queries...
        if ( timeStep.equalsIgnoreCase("Year") && readData ) {
            if ( (dataType.equalsIgnoreCase("FrostDateF28F") ||
                dataType.equalsIgnoreCase("FrostDateF32F") ||
                dataType.equalsIgnoreCase("FrostDateL28S") ||
                dataType.equalsIgnoreCase("FrostDateL32S")) ) {
                boolean doF28F = false;
                boolean doF32F = false;
                boolean doL32S = false;
                boolean doL28S = false;
                if ( dataType.equalsIgnoreCase("FrostDateF28F") ) {
                    doF28F = true;
                }
                else if ( dataType.equalsIgnoreCase("FrostDateF32F") ) {
                    doF32F = true;
                }
                else if ( dataType.equalsIgnoreCase("FrostDateL28S") ) {
                    doL28S = true;
                }
                else if ( dataType.equalsIgnoreCase("FrostDateL32S") ) {
                    doL32S = true;
                }
                // Get data from frost dates time series
                tsIsHandled = true;
                ArrayOfFrostDates ytsArray = getColoradoWaterHBGuestSoap12().getHBGuestFrostDates(
                    hbsta.getMeas_num(), (short)readStart.getYear(), (short)readEnd.getYear(), getAuthentication(), status );
                sw.stop();
                // Check for error
                if ( (status.value != null) && (status.value.getError() != null) ) {
                    throw new RuntimeException ( "Error getting FrostDates for ID=" + locID +
                        " (" + status.value.getError().getErrorCode() + ": " +
                        status.value.getError().getExceptionDescription() + ")." );
                }
                Message.printStatus(2, routine,
                    "Retrieved " + ytsArray.getFrostDates().size() + " StructureAnnuallyTS for ID=\"" +
                    locID + "\" " + readStart.getYear() + " to " + readEnd.getYear() + " in " +
                    sw.getSeconds() + " seconds.");
                // Transfer the data
                DateTime date = new DateTime(DateTime.DATE_FAST|DateTime.PRECISION_MONTH);
                String dateString = null;
                String [] dateParts;
                for ( FrostDates yts : ytsArray.getFrostDates() ) {
                    date.setYear(yts.getCalYear());
                    if ( doF28F ) {
                        dateString = yts.getF28F();
                    }
                    else if ( doF32F ) {
                        dateString = yts.getF32F();
                    }
                    else if ( doL28S ) {
                        dateString = yts.getL28S();
                    }
                    else if ( doL32S ) {
                        dateString = yts.getL32S();
                    }
                    if ( (dateString != null) && !dateString.trim().equals("") ) {
                        dateParts = dateString.split("/");
                        // Expected date format is MM/DD/YYYY, but can be 1 or 2 digit month and day
                        //Message.printStatus(2,routine,"Date=\"" + dateString + " value=" + dts.getAmt() +
                        //    " flag=" + dts.getObs() );
                        date.setMonth(Integer.parseInt(dateParts[0]));
                        date.setDay(Integer.parseInt(dateParts[1]));
                        date.setYear(Integer.parseInt(dateParts[2]));
                        // Parse the date string
                        ts.setDataValue(date,TimeUtil.dayOfYear(date));
                    }
                }
                ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
                ts.addToGenesis ( "Frost date is day of year (1-366) relative to Jan 1." );
            }
        }
        else if ( timeStep.equalsIgnoreCase("Month") && readData ) {
            if ( dataType.equalsIgnoreCase("AdminFlow") ||
                dataType.equalsIgnoreCase("EvapPan") ||
                dataType.equalsIgnoreCase("Precip") ||
                dataType.equalsIgnoreCase("Snow") ||
                dataType.equalsIgnoreCase("Streamflow") ||
                dataType.equalsIgnoreCase("TempMax") ||
                dataType.equalsIgnoreCase("TempMean") ||
                dataType.equalsIgnoreCase("TempMin") ) {
                // Structure time series
                tsIsHandled = true;
                ArrayOfStationTS mtsArray = null;
                // Read using the Measnum, which allows direct access to the time series of interest.
                Holder<WSDisclaimerHeader> disclaimer = new Holder<WSDisclaimerHeader>();
                mtsArray = getColoradoWaterHBGuestSoap12().getHBGuestStationTS( hbsta.getMeas_num(),
                    readStart.toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY),
                    readEnd.toString(DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY),
                    getAuthentication(), disclaimer, status );
                sw.stop();
                // Check for error
                if ( (status.value != null) && (status.value.getError() != null) ) {
                    throw new RuntimeException ( "Error getting StationTS for ID=" + locID +
                        ", measNum=" + hbsta.getMeas_num() + ", readStart=" + readStart +
                        ", readEnd=" + readEnd + " (" + status.value.getError().getErrorCode() + ": " +
                        status.value.getError().getExceptionDescription() + ")." );
                }
                Message.printStatus(2, routine,
                    "Retrieved " + mtsArray.getStationTS().size() + " StationTS for ID=\"" +
                    locID + "\" " + readStart.getYear() + " to " + readEnd.getYear() + " in " +
                    sw.getSeconds() + " seconds.");
                // Transfer the data
                DateTime date = new DateTime(DateTime.DATE_FAST|DateTime.PRECISION_MONTH);
                String dateString;
                String [] dateParts;
                Double amt;
                String flag = "";
                String flagA, flagB;
                double missing = ts.getMissing();
                for ( StationTS mts : mtsArray.getStationTS() ) {
                    flag = "";
                    dateString = mts.getMeasDate();
                    //Message.printStatus(2,routine,dateString);
                    dateParts = dateString.split("/");
                    // Expected date format is MM/DD/YYYY, but can be 1 or 2 digit month and day
                    //Message.printStatus(2,routine,"Date=\"" + dateString + " value=" + dts.getAmt() +
                    //    " flag=" + dts.getObs() );
                    date.setMonth(Integer.parseInt(dateParts[0]));
                    date.setDay(Integer.parseInt(dateParts[1]));
                    date.setYear(Integer.parseInt(dateParts[2]));
                    //Message.printStatus(2, routine, "Date=" + date + " value=" + mts.getAmt());
                    flagA = mts.getFlagA();
                    flagB = mts.getFlagB();
                    amt = mts.getAmt();
                    if ( (flagA != null) && !flagA.equals("") ) {
                        flag = flagA;
                    }
                    if ( (flagB != null) && !flagB.equals("") ) {
                        flag += "/" + flagB;
                    }
                    if ( amt == null ) {
                        if ( !flag.equals("") ) {
                            ts.setDataValue(date,missing,flag,0);
                        }
                    }
                    else {
                        if ( flag.equals("") ) {
                            // Don't set flag because flags (even all blank) my still trigger some display behavior
                            ts.setDataValue(date,amt);
                        }
                        else {
                            ts.setDataValue(date,amt,flag,0);
                        }
                    }
                }
                ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
            }
        }
        else if ( timeStep.equalsIgnoreCase("Day") && readData ) {
            if (dataType.equalsIgnoreCase("AdminFlow") ||
                dataType.equalsIgnoreCase("EvapPan") ||
                dataType.equalsIgnoreCase("Precip") ||
                dataType.equalsIgnoreCase("Snow") ||
                dataType.equalsIgnoreCase("Solar") ||
                dataType.equalsIgnoreCase("Streamflow") ||
                dataType.equalsIgnoreCase("TempMax") ||
                dataType.equalsIgnoreCase("TempMean") ||
                dataType.equalsIgnoreCase("TempMin") ||
                dataType.equalsIgnoreCase("VaporPressure") ||
                dataType.equalsIgnoreCase("Wind") ) {
                // Station time series
                tsIsHandled = true;
                ArrayOfStationTS dtsArray = null;
                // Read using the Measnum, which allows direct access to the time series of interest.
                Holder<WSDisclaimerHeader> disclaimer = new Holder<WSDisclaimerHeader>();
                dtsArray = getColoradoWaterHBGuestSoap12().getHBGuestStationTS( hbsta.getMeas_num(),
                    readStart.toString(), readEnd.toString(), getAuthentication(), disclaimer, status );
                sw.stop();
                // Check for error
                if ( (status.value != null) && (status.value.getError() != null) ) {
                    throw new RuntimeException ( "Error getting StationTS for ID=" + locID +
                        " (" + status.value.getError().getErrorCode() + ": " +
                        status.value.getError().getExceptionDescription() + ")." );
                }
                Message.printStatus(2, routine,
                    "Retrieved " + dtsArray.getStationTS().size() + " StationTS for ID=\"" +
                    locID + "\" " + readStart + " to " + readEnd + " in " +
                    sw.getSeconds() + " seconds.");
                // Transfer the data - do simple string parse of date to improve performance
                // (should work as long as WS spec does not change)
                DateTime date = new DateTime(DateTime.DATE_FAST|DateTime.PRECISION_DAY);
                String dateString;
                String[] dateParts;
                String flag = "", flagA = "", flagB = "";
                Double amt = null;
                double missing = ts.getMissing();
                for ( StationTS dts : dtsArray.getStationTS() ) {
                    dateString = dts.getMeasDate();
                    dateParts = dateString.split("/");
                    // Expected date format is MM/DD/YYYY, but can be 1 or 2 digit month and day
                    //Message.printStatus(2,routine,"Date=\"" + dateString + " value=" + dts.getAmt() +
                    //    " flagA=" + dts.getFlagA() + ", flagB=" + dts.getFlagB() );
                    flag = "";
                    flagA = dts.getFlagA();
                    flagB = dts.getFlagB();
                    if ( (flagA != null) && !flagA.equals("") ) {
                        flag = flagA;
                    }
                    // TODO SAM 2012-06-28 Need to evaluate whether 
                    if ( (flagB != null) && !flagB.equals("") ) {
                        if ( flag.equals("") ) {
                            // Use flag B
                            flag = flagB;
                        }
                        else {
                            // Append to previous
                            flag = flag + flagB;
                        }
                    }
                    date.setMonth(Integer.parseInt(dateParts[0]));
                    date.setDay(Integer.parseInt(dateParts[1]));
                    date.setYear(Integer.parseInt(dateParts[2]));
                    // Set the data flag
                    amt = dts.getAmt();
                    if ( amt == null ) {
                        if ( !flag.equals("") ) {
                            // Flag indicates something so set with missing
                            ts.setDataValue(date,missing,flag,0);
                        }
                    }
                    else {
                        // Have a value so set
                        if ( flag.equals("") ) {
                            ts.setDataValue(date,amt);
                        }
                        else {
                            ts.setDataValue(date,amt,flag,0);
                        }
                    }
                }
                ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
            }
            else if ( dataType.equalsIgnoreCase("SnowCourseDepth") ||
                dataType.equalsIgnoreCase("SnowCourseSWE") ) {
                // Snow course time series
                tsIsHandled = true;
                boolean doDepth = false; // false means do SWE
                if ( dataType.equalsIgnoreCase("SnowCourseDepth") ) {
                    doDepth = true;
                }
                ArrayOfSnowCourse dtsArray = null;
                // Read using the Measnum, which allows direct access to the time series of interest.
                //Holder<WSDisclaimerHeader> disclaimer = new Holder<WSDisclaimerHeader>();
                dtsArray = getColoradoWaterHBGuestSoap12().getHBGuestSnowCourse(
                    hbsta.getMeas_num(), readStart.toString(), readEnd.toString(), getAuthentication(), status );
                sw.stop();
                // Check for error
                if ( (status.value != null) && (status.value.getError() != null) ) {
                    throw new RuntimeException ( "Error getting SnowCourse for ID=" + locID +
                        " (" + status.value.getError().getErrorCode() + ": " +
                        status.value.getError().getExceptionDescription() + ")." );
                }
                Message.printStatus(2, routine,
                    "Retrieved " + dtsArray.getSnowCourse().size() + " SnowCourse for ID=\"" +
                    locID + "\" " + readStart + " to " + readEnd + " in " +
                    sw.getSeconds() + " seconds.");
                // Transfer the data - do simple string parse of date to improve performance
                // (should work as long as WS spec does not change)
                DateTime date = new DateTime(DateTime.DATE_FAST|DateTime.PRECISION_DAY);
                String dateString;
                String[] dateParts;
                String flag = "";
                Double value;
                Short depth;
                for ( SnowCourse dts : dtsArray.getSnowCourse() ) {
                    dateString = dts.getMeasDate();
                    flag = "";
                    if ( (dateString != null) && !dateString.trim().equals("") ) {
                        dateParts = dateString.split("/");
                        // Expected date format is MM/DD/YYYY, but can be 1 or 2 digit month and day
                        //Message.printStatus(2,routine,"Date=\"" + dateString + " value=" + dts.getAmt() +
                        //    " flag=" + dts.getObs() );
                        date.setMonth(Integer.parseInt(dateParts[0]));
                        date.setDay(Integer.parseInt(dateParts[1]));
                        date.setYear(Integer.parseInt(dateParts[2]));            
                        // Set the data flag
                        flag = dts.getFlag();
                        value = null;
                        if ( doDepth ) {
                            depth = dts.getDepth();
                            //Message.printStatus(2,routine,"For date " + date + " snow course depth=" + depth);
                            if ( depth != null ) {
                                if ( depth >= 0 ) {
                                    value = new Double(depth);
                                }
                            }
                        }
                        else {
                            BigDecimal swe = dts.getSnowWaterEquiv();
                            if ( (swe != null) && (swe.doubleValue() >= 0.0) ) {
                                 value = swe.doubleValue();
                            }
                        }
                        if ( value != null ) {
                            ts.setDataValue(date,value,flag,0);
                        }
                    }
                }
                ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
            }
        }
    }
    else if ( (dataType.equalsIgnoreCase("WellLevelElev") || dataType.equalsIgnoreCase("WellLevelDepth"))
        && timeStep.equalsIgnoreCase("Day") && readData ) {
        boolean readElev = false; // use to handle elevation vs. depth
        if ( dataType.equalsIgnoreCase("WellLevelElev") ) {
            readElev = true;
        }
        tsIsHandled = true;
        ArrayOfGroundWaterWellsWellMeasTS dtsArray = getColoradoWaterHBGuestSoap12().getHBGuestGroundWaterWellsWellMeasTS(
            hbwell.getWell_num(), readStart.toString(), readEnd.toString(), getAuthentication(), status );
        sw.stop();
        // Check for error
        if ( (status.value != null) && (status.value.getError() != null) ) {
            throw new RuntimeException ( "Error getting GroundWaterWellsWellMeasTS for wellNum=" +
                hbwell.getWell_num() + " (" + status.value.getError().getErrorCode() + ": " +
                status.value.getError().getExceptionDescription() + ")." );
        }
        Message.printStatus(2, routine,
            "Retrieved " + dtsArray.getGroundWaterWellsWellMeasTS().size() + " GroundWaterWellsWellMeasTS for wellNum=\"" +
            hbwell.getWell_num() + "\" " + readStart.getYear() + " to " + readEnd.getYear() + " in " +
            sw.getSeconds() + " seconds.");
        // Transfer the data - do simple string parse of date to improve performance
        // (should work as long as WS spec does not change)
        DateTime date = new DateTime(DateTime.DATE_FAST|DateTime.PRECISION_DAY);
        String dateString;
        String[] dateParts;
        Double value;
        for ( GroundWaterWellsWellMeasTS dts : dtsArray.getGroundWaterWellsWellMeasTS() ) {
            dateString = dts.getMeasDate();
            dateParts = dateString.split("/");
            // Expected date format is MM/DD/YYYY, but can be 1 or 2 digit month and day
            date.setMonth(Integer.parseInt(dateParts[0]));
            date.setDay(Integer.parseInt(dateParts[1]));
            date.setYear(Integer.parseInt(dateParts[2]));
            // Set the data flag
            value = null;
            if ( readElev ) {
                value = dts.getWlElev();
            }
            else {
                value = dts.getWlDepth();
            }
            if ( value != null ) {
                ts.setDataValue(date,value);
            }
        }
        ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
    }
    else if ( dataType.equalsIgnoreCase("DivTotal") ||
        dataType.equalsIgnoreCase("IDivTotal") ||
        dataType.equalsIgnoreCase("RelTotal") ||
        dataType.equalsIgnoreCase("IRelTotal") ||
        dataType.equalsIgnoreCase("ResEOM") ||
        dataType.equalsIgnoreCase("ResEOY") ||
        dataType.equalsIgnoreCase("ResMeasElev") ||
        dataType.equalsIgnoreCase("ResMeasEvap") ||
        dataType.equalsIgnoreCase("ResMeasFill") ||
        dataType.equalsIgnoreCase("ResMeasRelease") ||
        dataType.equalsIgnoreCase("ResMeasStorage") ) {
        // Structure queries...
        String wdid = HydroBase_WaterDistrict.formWDID(7, wdidParts[0], wdidParts[1]);
        // Because the web service API uses irrigation year for some methods, get one more year on either end to
        // make sure that the user's requested period (calendar) is covered.  This should not impact anything
        // because the period for the time series is set and memory allocated in previous code.  It just means
        // that a few records may be ignored because they are outside the time series period.
        int irrigationYearStart = readStart.getYear() - 1;
        int irrigationYearEnd = readEnd.getYear() + 1;
        if ( timeStep.equalsIgnoreCase("Year") && readData ) {
            if ( (dataType.equalsIgnoreCase("DivTotal") ||
                dataType.equalsIgnoreCase("IDivTotal") ||
                dataType.equalsIgnoreCase("RelTotal") ||
                dataType.equalsIgnoreCase("IRelTotal") ||
                dataType.equalsIgnoreCase("ResEOM") ||
                dataType.equalsIgnoreCase("ResEOY")) ) {
                // Get data from structure time series
                tsIsHandled = true;
                ArrayOfStructureAnnuallyTS ytsArray = getColoradoWaterHBGuestSoap12().getHBGuestStructureAnnuallyTSByMeasNum(
                    hbstruct.getMeas_num(), (short)irrigationYearStart, (short)irrigationYearEnd, getAuthentication(), status );
                sw.stop();
                // Check for error
                if ( (status.value != null) && (status.value.getError() != null) ) {
                    throw new RuntimeException ( "Error getting StructureAnnuallyTS for WDID=" + wdid +
                        " (" + status.value.getError().getErrorCode() + ": " +
                        status.value.getError().getExceptionDescription() + ")." );
                }
                Message.printStatus(2, routine,
                    "Retrieved " + ytsArray.getStructureAnnuallyTS().size() + " StructureAnnuallyTS for wdid=\"" +
                    wdid + "\" irrigation years " + irrigationYearStart + " to " + irrigationYearEnd + " in " +
                    sw.getSeconds() + " seconds.");
                // Transfer the data
                DateTime date = new DateTime(DateTime.DATE_FAST|DateTime.PRECISION_MONTH);
                Double annAmt;
                for ( StructureAnnuallyTS yts : ytsArray.getStructureAnnuallyTS() ) {
                    date.setYear(yts.getIrrYear());
                    annAmt = yts.getAnnAmt();
                    if ( annAmt != null ) {
                        ts.setDataValue(date,annAmt);
                    }
                }
                ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
                ts.addToGenesis ( "Annual values are for irrigation year Nov to Oct." );
            }
        }
        else if ( timeStep.equalsIgnoreCase("Month") && readData ) {
            if ( (dataType.equalsIgnoreCase("DivTotal") ||
                dataType.equalsIgnoreCase("IDivTotal") ||
                dataType.equalsIgnoreCase("RelTotal") ||
                dataType.equalsIgnoreCase("IRelTotal") ||
                dataType.equalsIgnoreCase("ResEOM") ||
                dataType.equalsIgnoreCase("ResEOY")) ) {
                // Structure time series
                tsIsHandled = true;
                ArrayOfStructureMonthlyTS mtsArray = null;
                // Read using the Measnum, which allows direct access to the time series of interest.
                mtsArray = getColoradoWaterHBGuestSoap12().getHBGuestStructureMonthlyTSByMeasNum(
                    hbstruct.getMeas_num(), (short)irrigationYearStart, (short)irrigationYearEnd, getAuthentication(), status );
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
                    wdid + "\" irrigation years " + irrigationYearStart + " to " + irrigationYearEnd + " in " +
                    sw.getSeconds() + " seconds.");
                // Transfer the data
                DateTime date = new DateTime(DateTime.DATE_FAST|DateTime.PRECISION_MONTH);
                Double amt;
                for ( StructureMonthlyTS mts : mtsArray.getStructureMonthlyTS() ) {
                    date.setMonth(mts.getCalMonth());
                    date.setYear(mts.getCalYear());
                    //Message.printStatus(2, routine, "Date=" + date + " value=" + mts.getAmt());
                    amt = mts.getAmt();
                    if ( amt != null ) {
                        ts.setDataValue(date,amt);
                    }
                }
                ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
            }
        }
        else if ( timeStep.equalsIgnoreCase("Day") && readData ) {
            if (dataType.equalsIgnoreCase("DivTotal") ||
                dataType.equalsIgnoreCase("IDivTotal") ||
                dataType.equalsIgnoreCase("RelTotal") ||
                dataType.equalsIgnoreCase("IRelTotal") ) {
                // Structure time series
                tsIsHandled = true;
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
                String obs;
                Double amt;
                double missing = ts.getMissing();
                for ( StructureDailyTS dts : dtsArray.getStructureDailyTS() ) {
                    dateString = dts.getMeasDate();
                    dateParts = dateString.split("/");
                    // Expected date format is MM/DD/YYYY, but can be 1 or 2 digit month and day
                    //Message.printStatus(2,routine,"Date=\"" + dateString + " value=" + dts.getAmt() +
                    //    " flag=" + dts.getObs() );
                    date.setMonth(Integer.parseInt(dateParts[0]));
                    date.setDay(Integer.parseInt(dateParts[1]));
                    date.setYear(Integer.parseInt(dateParts[2]));
                    obs = dts.getObs();
                    amt = dts.getAmt();
                    // Set the data flag
                    if ( amt == null ) {
                        if ( (obs != null) && !obs.equals("") ) {
                            // Have flag but missing value
                            ts.setDataValue(date,missing,obs,0);
                        }
                    }
                    else {
                        // Have a data value
                        if ( (obs == null) || obs.equals("") ) {
                            ts.setDataValue(date,amt);
                        }
                        else {
                            ts.setDataValue(date,amt,obs,0);
                        }
                    }
                }
                ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
                HydroBase_Util.addDailyTSStructureDataFlagDescriptions ( ts );
                // Also fill daily diversions as per the accepted DWR approach because HydroBase does not
                // store filled daily data
                if (dataType.equalsIgnoreCase("DivTotal") ||
                    dataType.equalsIgnoreCase("DivClass") ||
                    dataType.equalsIgnoreCase("RelTotal") ||
                    dataType.equalsIgnoreCase("RelClass") ) {
                    // Use "c" consistent with the default when reading from HydroBase
                    HydroBase_Util.fillTSIrrigationYearCarryForward((DayTS)ts, "c" );
                }
            }
            else if ( dataType.equalsIgnoreCase("ResMeasElev") ||
                dataType.equalsIgnoreCase("ResMeasEvap") ||
                dataType.equalsIgnoreCase("ResMeasFill") ||
                dataType.equalsIgnoreCase("ResMeasRelease") ||
                dataType.equalsIgnoreCase("ResMeasStorage") ) {
                // ResMeas time series
                // Set some booleans to speed processing - only one will be set to true
                boolean doReasMeasElev = false;
                boolean doReasMeasEvap = false;
                boolean doReasMeasFill = false;
                boolean doReasMeasRelease = false;
                boolean doReasMeasStorage = false;
                if ( dataType.equalsIgnoreCase("ResMeasElev") ) {
                    doReasMeasElev = true;
                }
                else if ( dataType.equalsIgnoreCase("ResMeasEvap") ) {
                    doReasMeasEvap = true;
                }
                else if ( dataType.equalsIgnoreCase("ResMeasFill") ) {
                    doReasMeasFill = true;
                }
                else if ( dataType.equalsIgnoreCase("ResMeasRelease") ) {
                    doReasMeasRelease = true;
                }
                else if ( dataType.equalsIgnoreCase("ResMeasStorage") ) {
                    doReasMeasStorage = true;
                }
                tsIsHandled = true;
                ArrayOfStructureResMeas dtsArray = getColoradoWaterHBGuestSoap12().getHBGuestStructureResMeas(
                    wdid, readStart.toString(), readEnd.toString(), getAuthentication(), status );
                //getHBGuestStructureDailyTSByMeasNum(
                //    hbstruct.getMeas_num(), readStart.toString(), readEnd.toString(), getAuthentication(), status );
                sw.stop();
                // Check for error
                if ( (status.value != null) && (status.value.getError() != null) ) {
                    throw new RuntimeException ( "Error getting StructureDailyTS for WDID=" + wdid +
                        " (" + status.value.getError().getErrorCode() + ": " +
                        status.value.getError().getExceptionDescription() + ")." );
                }
                Message.printStatus(2, routine,
                    "Retrieved " + dtsArray.getStructureResMeas().size() + " StructureMeasType for wdid=\"" +
                    wdid + "\" " + readStart + " to " + readEnd + " in " +
                    sw.getSeconds() + " seconds.");
                // Transfer the data - do simple string parse of date to improve performance
                // (should work as long as WS spec does not change)
                DateTime date = new DateTime(DateTime.DATE_FAST|DateTime.PRECISION_DAY);
                String dateString;
                String[] dateParts;
                Double value;
                for ( StructureResMeas dts : dtsArray.getStructureResMeas() ) {
                    dateString = dts.getDateTime();
                    dateParts = dateString.split("/");
                    // Expected date format is MM/DD/YYYY, but can be 1 or 2 digit month and day
                    //Message.printStatus(2,routine,dateString);
                    date.setMonth(Integer.parseInt(dateParts[0]));
                    date.setDay(Integer.parseInt(dateParts[1]));
                    date.setYear(Integer.parseInt(dateParts[2]));
                    // No data flag but no need to set value if missing
                    value = null;
                    if ( doReasMeasElev ) {
                        value = dts.getGageHeight();
                    }
                    else if ( doReasMeasEvap ) {
                        value = dts.getEvapLossAmt();
                    }
                    else if ( doReasMeasFill ) {
                        value = dts.getFillAmt();
                    }
                    else if ( doReasMeasRelease ) {
                        value = dts.getReleaseAmt();
                    }
                    else if ( doReasMeasStorage ) {
                        value = dts.getStorageAmt();
                    }
                    if ( value != null ) {
                        ts.setDataValue(date,value);
                    }
                }
                ts.addToGenesis ( "Read from ColoradoWaterHBGuest web service for " + readStart + " to " + readEnd );
            }
        }
    }
    if ( readData && !tsIsHandled ) {
        // tsIsHandled only gets set to true if readData=true
        throw new Exception ( "Reading HBGuest time series for data type \"" + dataType +
            "\" and timestep \"" + timeStep + "\" is not supported." );
    }
    return ts;
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
    List<HydroBase_WaterDistrict> waterDistrictList = new Vector<HydroBase_WaterDistrict>();
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
    List<HydroBase_WaterDivision> waterDivisionList = new Vector<HydroBase_WaterDivision>();
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