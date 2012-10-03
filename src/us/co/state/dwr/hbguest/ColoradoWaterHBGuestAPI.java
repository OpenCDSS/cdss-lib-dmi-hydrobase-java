package us.co.state.dwr.hbguest;

import java.util.List;
import java.util.Vector;

import javax.xml.ws.Holder;

import DWR.DMI.HydroBaseDMI.HydroBase_StationGeolocMeasType;
import DWR.DMI.HydroBaseDMI.HydroBase_StructMeasTypeView;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.Message.Message;
import RTi.Util.String.StringUtil;
import RTi.Util.Time.StopWatch;

//import java.util.List;
//import java.util.Vector;

//import javax.xml.ws.Holder;

//import DWR.DMI.HydroBaseDMI.HydroBase_StationGeolocMeasType;
//import RTi.TS.DayTS;
//import RTi.TS.HourTS;
//import RTi.TS.IrregularTS;
//import RTi.TS.TS;
//import RTi.TS.TSIdent;
//import RTi.Util.Message.Message;
//import RTi.Util.String.StringUtil;
//import RTi.Util.Time.DateTime;
//import RTi.Util.Time.TimeInterval;

/**
API methods to simplify interaction with the ColoradoWaterHBGuest web services.
These methods (and private data) are currently static because only one server is available for
ColoradoWaterHBGuest.  If multiple servers become available and support multiple instances of web service
sessions, make this a class that needs to be instantiated, or put the cache in a hashtable or other data
structure that is keyed to the server.
*/
public class ColoradoWaterHBGuestAPI
{
    
/**
Name of this class, for messaging.
*/
private static String __class = "ColoradoWaterHBGuestAPI";

/**
Read the list of distinct station data types.
@param service the web service instance.
@param useCache Indicate whether the cache should be used if available.
*/
public static List<String> readDistinctStationDataTypeList ( ColoradoWaterHBGuestService service,
    boolean useCache )
{   String routine = __class + "readDistinctStationDataTypeList";
    // Check to see if the cache is available
    List<String> dataTypesCached = null;
    if ( useCache ) {
        // Have a cache so use it
        // If the data types are cached, then return them...
        dataTypesCached = service.getStationDataTypeListCache();
        if ( (dataTypesCached != null) && (dataTypesCached.size() != 0) ) {
            // Return the cached data types
            Message.printStatus(2, routine, "Got distinct list of station data types from datatype cache.");
            return dataTypesCached;
        }
    }
    List<HydroBase_StationGeolocMeasType> measTypesCached = new Vector();
    List<HydroBase_StationGeolocMeasType> measTypes = new Vector();
    if ( useCache ) {
        measTypesCached = service.getStationMeasTypeListCache();
        measTypes = measTypesCached;
    }
    // TODO SAM 2010-05-18 Carve this out into separate code to read station meas types to cache
    // If here try to use the cached station meas types.  If not available, query
    if ( (measTypesCached == null) || (measTypesCached.size() == 0) ) {
        // Need to read the data (and will save in the cache at end).
        Message.printStatus(2, routine, "Getting list of stations for all divisions from web service request.");
        // Loop through the divisions and read the station MeasType objects and create a cache
        int nStations = 0;
        StopWatch swDiv = new StopWatch();
        StopWatch swTotal = new StopWatch();
        for ( int div = 1; div <= 7; div++ ) {
            swDiv.clearAndStart();
            Holder<HbStatusHeader> status = new Holder<HbStatusHeader>();
            ArrayOfStation stationArray = service.getColoradoWaterHBGuestSoap12().getHBGuestStationByDIV( div,
                service.getAuthentication(), status );
            // Check for error
            if ( (status.value != null) && (status.value.getError() != null) ) {
                throw new RuntimeException ( "Error getting stations for division " + div + " (" +
                    status.value.getError().getErrorCode() + ": " + status.value.getError().getExceptionDescription() + ")." );
            }
            nStations += stationArray.getStation().size();
            swDiv.stop();
            swTotal.add ( swDiv );
            Message.printStatus(2, routine,
                "Retrieved " + stationArray.getStation().size() + " stations for division " + div + " in " + 
                swDiv.getSeconds() + " seconds.");
            // getStation() actually returns List<Station>
            swDiv.clearAndStart();
            int numMeasTypeDiv = 0;
            for ( Station station : stationArray.getStation() ) {
                // Now read the meas types for the station
                Holder<HbStatusHeader> status2 = new Holder<HbStatusHeader>();
                ArrayOfStationMeasType stationMeasTypeArray =
                    service.getColoradoWaterHBGuestSoap12().getHBGuestStationMeasTypeByStationNum(
                        station.getStationNum(), service.getAuthentication(), status2 );
                // Check for error
                if ( (status2.value != null) && (status2.value.getError() != null) ) {
                    throw new RuntimeException ( "Error getting meas types for station_num " + station.getStationNum() + " (" +
                        status.value.getError().getErrorCode() + ": " + status.value.getError().getExceptionDescription() + ")." );
                }
                // Loop through the list of measurement types for the station
                numMeasTypeDiv += stationMeasTypeArray.getStationMeasType().size();
                for ( StationMeasType smt: stationMeasTypeArray.getStationMeasType() ) {
                    HydroBase_StationGeolocMeasType newStationMeasType = new HydroBase_StationGeolocMeasType ();
                    // TODO SAM 2010-05-18 Would need to join to Geoloc somehow
                    // TODO SAM 2010-05-18 Finish transferring attributes
                    // Station attributes...
                    newStationMeasType.setDiv( station.getDiv() );
                    newStationMeasType.setStation_id( station.getStationId() );
                    newStationMeasType.setStation_name( station.getStationName() );
                    newStationMeasType.setStation_num( station.getStationNum() );
                    newStationMeasType.setWD ( station.getWd() );
                    // MeasType attributes
                    newStationMeasType.setMeas_type(smt.getMeasType() );
                    // Add to the full list of cached meas types
                    measTypes.add ( newStationMeasType );
                }
            }
            swDiv.stop();
            swTotal.add ( swDiv );
            Message.printStatus(2, routine,
                "Retrieved " + numMeasTypeDiv + " measTypes for division " + div + " stations in " + 
                swDiv.getSeconds() + " seconds.");
        }
        Message.printStatus(2, routine, "Retrieved " + nStations + " stations and " + measTypes.size() +
            " station meas types total in " + swTotal.getSeconds() + " seconds." );
        service.setStationMeasTypeListCache(measTypes);
    }
    // Now get the data types from the meastypes...
    List<String> dataTypes = new Vector();
    if ( (measTypes != null) && (measTypes.size() > 0) ) {
        // Loop through the measTypes and get the unique list of data types
        String dt;
        for ( HydroBase_StationGeolocMeasType mt: measTypes ) {
            dt = mt.getMeas_type();
            // Add to the list if unique...
            boolean found = false;
            for ( String dataType : dataTypes ) {
                if ( dataType.equals(dt) ) {
                    found = true;
                    break;
                }
            }
            if ( !found ) {
                // Add to the list
                dataTypes.add(dt);
            }
        }
        dataTypes = StringUtil.sortStringList(dataTypes);
    }
    // Save the MeasType and DataType caches
    if ( useCache ) {
        service.setStationMeasTypeListCache ( measTypes );
        service.setStationDataTypeListCache ( dataTypes );
    }
    return dataTypes;
}

/**
Read a list of time series, as objects suitable for the TSTool time series list.
@return list of HydroBase_StationGeolocMeasType, in order to represent the station attributes available
from the web service.
*/
/*
public static List<HydroBase_StationGeolocMeasType> readTimeSeriesHeaderObjects ( ColoradoWaterHBGuest service,
    int wdReq, int divReq,
    String abbrevReq, String stationNameReq, String dataProviderReq, String dataTypeReq,
    String timestepReq, DateTime readStart, DateTime readEnd, boolean readData )
throws Exception
{   List<HydroBase_StationGeolocMeasType> tslist = new Vector();
    // Used below...
    String readStartString = null;
    if ( readStart != null ) {
        readStartString = readStart.toString();
    }
    String readEndString = null;
    if ( readEnd != null ) {
        readEndString = readEnd.toString();
    }
    // Get the list of matching transmitting stations...
    Holder<SmsStatusHeader> status = new Holder<SmsStatusHeader>();
    ArrayOfStation stationArray =
        service.getColoradoWaterSMSSoap12().getSMSTransmittingStations(divReq, wdReq, abbrevReq, status);
    // Check for error
    if ( (status.value != null) && (status.value.getError() != null) ) {
        throw new RuntimeException ( "Error getting transmitting stations (" +
            status.value.getError().getErrorCode() + ": " + status.value.getError().getExceptionDescription() + ")." );
    }
    // Loop through the stations (a bit odd that the method to return the list is singular)
    for ( Station station : stationArray.getStation() ) {
        // Get the list of variables that match the request
        // This is a list, each item which is a list of variables for a station
        String abbrev = station.getAbbrev();
        String stationName = station.getStationName();
        String dataProvider = station.getDataProviderAbbrev();
        int wd = station.getWd();
        int div = station.getDiv();
        String utmXs = station.getUTMX();
        String utmYs = station.getUTMY();
        Holder<HbStatusHeader> status2 = new Holder<HbStatusHeader>();
        ArrayOfStationVariables array =
            service.getColoradoWaterSMSSoap12().getSMSTransmittingStationVariables(divReq, wdReq, abbrev, status2 );
        // Check for error
        if ( (status2.value != null) && (status2.value.getError() != null) ) {
            throw new RuntimeException ( "Error getting transmitting station variables (" +
                status2.value.getError().getErrorCode() + ": " + status2.value.getError().getExceptionDescription() + ")." );
        }
        // Not sure how to check for error (is an exception thrown?)
        for ( StationVariables stationVariables : array.getStationVariables() ) {
            // Each variables list has the variables for a station
            String variable = stationVariables.getVariable();
            if ( variable.equalsIgnoreCase(dataTypeReq) ) {
                // Have a matching variable (data type)
                // Get the provisional time series for the station and data type (variable).
                // Specify the aggregation interval if specified (otherwise get the raw data).
                / * Don't get the data because this is a performance hit
                ArrayOfStreamflowTransmission dataRecords =
                    service.getColoradoWaterSMSSoap12().getSMSProvisionalData(abbrev, variable,
                    readStartString, readEndString, aggregation );
                int dataRecordsSize = 0;
                if ( dataRecords != null ) {
                    dataRecordsSize = dataRecords.getStreamflowTransmission().size();
                }
                * /
                // Define the time series and add to the list
                HydroBase_StationGeolocMeasType mt = new HydroBase_StationGeolocMeasType();
                // FIXME SAM 2009-11-20 What are the data units?
                mt.setAbbrev(abbrev);
                mt.setMeas_type(variable);
                mt.setStation_name(stationName);
                mt.setTime_step(timestepReq);
                mt.setData_source(dataProvider);
                mt.setWD ( wd );
                mt.setDiv ( div );
                mt.setData_units( lookupDataUnitsForVariable(variable));
                if ( StringUtil.isDouble(utmXs) ) {
                    mt.setUtm_x(Double.parseDouble(utmXs));
                }
                if ( StringUtil.isDouble(utmYs) ) {
                    mt.setUtm_x(Double.parseDouble(utmYs));
                }
                tslist.add ( mt );
             }
        }
    }
    return tslist;
}
*/

/**
Read a single time series.
*/
/*
public static TS readTimeSeries ( ColoradoWaterSMS service, String tsidentString,
    DateTime readStart, DateTime readEnd, boolean readData )
throws Exception
{   String routine = "ColoradoWaterSMS.readTimeSeries";
    TS ts = null;
    
    TSIdent tsident = new TSIdent ( tsidentString );
    String aggregation = tsident.getInterval();
    int intervalBase = tsident.getIntervalBase();
    if ( intervalBase == TimeInterval.IRREGULAR ) {
        aggregation = null; // raw data
    }
    String abbrevReq = tsident.getLocation();
    String dataType = tsident.getType();
    int div = -1; // Relying on abbrev to match the station
    int wd = -1; // Relying on abbrev to match the station
    // Get the list of matching transmitting stations...
    Holder<SmsStatusHeader> status = new Holder<SmsStatusHeader>();
    ArrayOfStation stationArray =
        service.getColoradoWaterSMSSoap12().getSMSTransmittingStations(div,wd, abbrevReq,status);
    // Check for error
    if ( (status.value != null) && (status.value.getError() != null) ) {
        throw new RuntimeException ( "Error getting transmitting stations (" +
            status.value.getError().getErrorCode() + ": " + status.value.getError().getExceptionDescription() + ")." );
    }
    // Leave this in to allow wild-carding of abbreviation parts, etc., but it will result in
    // a performance hit.
    // Loop through the stations (a bit odd that the method to return the list is singular)
    for ( Station station : stationArray.getStation() ) {
        // Get the list of variables that match the request
        // This is a list, each item which is a list of variables for a station
        String abbrev = station.getAbbrev();
        String dataProvider = station.getDataProviderAbbrev();
        Holder<SmsStatusHeader> status2 = new Holder<SmsStatusHeader>();
        ArrayOfStationVariables array =
            service.getColoradoWaterSMSSoap12().getSMSTransmittingStationVariables(div, wd, abbrev, status2 );
        // Check for error
        if ( (status2.value != null) && (status2.value.getError() != null) ) {
            throw new RuntimeException ( "Error getting transmitting station variables (" +
                status2.value.getError().getErrorCode() + ": " + status2.value.getError().getExceptionDescription() + ")." );
        }
        // Not sure how to check for error (is an exception thrown?)
        for ( StationVariables stationVariables : array.getStationVariables() ) {
            // Each variables list has the variables for a station
            String variable = stationVariables.getVariable();
            if ( variable.equalsIgnoreCase(dataType) ) {
                // Have a matching variable (data type)
                // Get the provisional time series for the station and data type (variable).
                // Define the time series and add to the list
                // FIXME SAM 2009-11-20 What are the data units?
                if ( intervalBase == TimeInterval.IRREGULAR ) {
                    ts = new IrregularTS();
                }
                else if ( intervalBase == TimeInterval.DAY ) {
                    ts = new DayTS();
                }
                else if ( intervalBase == TimeInterval.HOUR ) {
                    ts = new HourTS();
                }
                else {
                    throw new IllegalArgumentException ( "The interval for \"" + tsidentString + "\" is not supported.");
                }
                //tslist.add ( ts );
                // Set the identifier information
                ts.setIdentifier(tsident);
                ts.setDataUnits( lookupDataUnitsForVariable(variable));
                ts.setDataUnitsOriginal( lookupDataUnitsForVariable(variable));
                if ( readData ) {
                    // Dates are used below.  Don't require for !readData because this may break some
                    // discovery reads that depend on run-time date/times.
                    if ( readStart == null ) {
                        throw new IllegalArgumentException ( "Start date/time has not been specified." );
                    }
                    // Remove time zone if set
                    readStart = new DateTime(readStart);
                    if ( intervalBase == TimeInterval.HOUR ) {
                        // The date string actually needs to have minutes
                        // TODO SAM 2010-03-03 Remove this code and similar for readEnd if the
                        // State corrects the handling of dates to allow YYYY-MM-DD HH
                        readStart.setPrecision(DateTime.PRECISION_MINUTE );
                        readStart.setMinute(0);
                    }
                    readStart.setTimeZone("");
                    String readStartString = readStart.toString();
                    if ( readEnd == null ) {
                        throw new IllegalArgumentException ( "End date/time has not been specified." );
                    }
                    readEnd = new DateTime(readEnd);
                    if ( intervalBase == TimeInterval.HOUR ) {
                        // The date string actually needs to have minutes
                        readEnd.setPrecision(DateTime.PRECISION_MINUTE );
                        readEnd.setMinute(59);
                    }
                    readEnd.setTimeZone("");
                    String readEndString = readEnd.toString();
                    Message.printStatus (2, routine, "Reading time series \"" + tsidentString + "\" for readStart=" +
                        readStartString + " readEnd=" + readEndString );
                    // Specify the aggregation interval if specified (otherwise get the raw data).
                    Holder<SmsStatusHeader> status3 = new Holder<SmsStatusHeader>();
                    Holder<SmsDisclaimerHeader> disclaimer = new Holder<SmsDisclaimerHeader>();
                    ArrayOfStreamflowTransmission dataRecords =
                        service.getColoradoWaterSMSSoap12().getSMSProvisionalData(abbrev, variable,
                        readStartString, readEndString, aggregation, disclaimer, status3 );
                    // Check for error
                    if ( (status3.value != null) && (status3.value.getError() != null) ) {
                        throw new RuntimeException ( "Error getting provisional data (" +
                            status3.value.getError().getErrorCode() + ": " + status3.value.getError().getExceptionDescription() + ")." );
                    }
                    int dataRecordsSize = 0;
                    if ( dataRecords != null ) {
                        dataRecordsSize = dataRecords.getStreamflowTransmission().size();
                    }
                    Message.printStatus(2, routine, "Number of data records = " + dataRecordsSize );
                    // Set the period from the first and last data records
                    if ( dataRecordsSize > 0 ) {
                        // Have some data records to process...
                        StreamflowTransmission dataRecord1 = dataRecords.getStreamflowTransmission().get(0);
                        //Message.printStatus(2, routine, "First record transDateTime=" + dataRecord1.getTransDateTime());
                        ts.setDate1(parseTransmissionDateTime(dataRecord1.getTransDateTime(),0));
                        ts.setDate1Original(ts.getDate1());
                        StreamflowTransmission dataRecord2 = dataRecords.getStreamflowTransmission().get(
                            dataRecords.getStreamflowTransmission().size() - 1);
                        ts.setDate2(parseTransmissionDateTime(dataRecord2.getTransDateTime(),0));
                        ts.setDate2Original(ts.getDate2());
                        // Transfer the data from the data records to the time series.
                        ts.allocateDataSpace();
                        String transDateTime;
                        double amount;
                        String transFlag;
                        int resultCount; // Number of values aggregated (use if limiting missing?)
                        DateTime date;
                        int datePrecision = ts.getDate1().getPrecision();
                        for ( StreamflowTransmission dataRecord : dataRecords.getStreamflowTransmission() ) {
                            // Date format is m/d/yyyy hh:mm:ss am/pm
                            transDateTime = dataRecord.getTransDateTime();
                            amount = dataRecord.getAmount();
                            transFlag = dataRecord.getTransFlag();
                            resultCount = dataRecord.getResultCount();
                            if ( Message.isDebugOn ) {
                                Message.printDebug(10, routine, "transDateTime=" + transDateTime +
                                " amount=" + amount + " transFlag=" + transFlag + " resultCount=" + resultCount);
                            }
                            date = parseTransmissionDateTime ( transDateTime, datePrecision );
                            ts.setDataValue(date, amount, transFlag, 0);
                        }
                    }
                }
            }
        }
    }
    return ts;
}
*/

/**
Read a list of structure meas type objects corresponding to the query criteria.
*/
public static List<HydroBase_StructMeasTypeView> readStructureGeolocStructMeasTypeList( ColoradoWaterHBGuest service,
    InputFilter_JPanel ifp, String measType, String timeStep )
{   // Call the web service to get the list of meastype for the location.
    List<HydroBase_StructMeasTypeView> measTypeList = new Vector();
    return measTypeList;
}



}