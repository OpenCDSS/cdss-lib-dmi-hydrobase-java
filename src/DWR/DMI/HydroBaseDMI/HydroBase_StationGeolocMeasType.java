// ----------------------------------------------------------------------------
// HydroBase_StationGeolocMeasType.java - Class to hold data from the 
//	HydroBase station, geoloc and meas_type
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-11	J. Thomas Sapienza, RTi	Initial version from
//					HBStationLocationMeasurementType.
// 2003-02-19	JTS, RTi		Added some fields and methods to support
//					older database versions.
// 2003-02-24	JTS, RTi		* Corrected error in finalize() so that 
//					  super.finalize() gets called.
//					* Reworked so that it extends 
//					  HydroBase_StationGeoloc.
// 2003-11-30	Steven A. Malers, RTi	Add _data_units - SAM is proposing
// 					that this be added to HydroBase - it
//					is used by TSTool.
// 2005-03-01	JTS, RTi		Added view copy constructor.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase station, meas_type and geoloc tables
*/
public class HydroBase_StationGeolocMeasType 
extends HydroBase_StationGeoloc {

// Meas Type
protected int _meas_num = 		DMIUtil.MISSING_INT;
protected String _meas_type = 		DMIUtil.MISSING_STRING;
protected String _time_step = 		DMIUtil.MISSING_STRING;
protected int _start_year = 		DMIUtil.MISSING_INT;
protected int _end_year = 		DMIUtil.MISSING_INT;
protected String _vax_field = 		DMIUtil.MISSING_STRING;
protected String _transmit = 		DMIUtil.MISSING_STRING;
protected int _meas_count = 		DMIUtil.MISSING_INT;
protected String _data_source = 	DMIUtil.MISSING_STRING;
protected String _data_units = 		DMIUtil.MISSING_STRING;	
							// Not in HydroBase but
							// is used by software.

/**
Constructor.
*/
public HydroBase_StationGeolocMeasType() {
	super();
}

/**
Copy constructor to fill in all the values of this object from a corresponding
view.  Used during unit tests.
*/
public HydroBase_StationGeolocMeasType(HydroBase_StationView view) {
	super();

	if (view == null) {
		return;
	}

	_station_num = view._station_num;
	_geoloc_num = view._geoloc_num;
	_station_name = view._station_name;
	_station_id = view._station_id;
	_nesdis_id = view._nesdis_id;
	_drain_area = view._drain_area;
	_contr_area = view._contr_area;
	_source = view._source;
	_abbrev = view._abbrev;
	_transbsn = view._transbsn;
	_modified = view._modified;
	_user_num = view._user_num;
	_transmnt = view._transmnt;
	_transmit = view._transmit;
	_cooperator_id = view._cooperator_id;

	_utm_x = view._utm_x;
	_utm_y = view._utm_y;
	_latdecdeg = view._latdecdeg;
	_longdecdeg = view._longdecdeg;
	_div = view._div;
	_wd = view._wd;
	_county = view._county;
	_topomap = view._topomap;
	_cty = view._cty;
	_huc = view._huc;
	_elev = view._elev;
	_loc_type = view._loc_type;
	_accuracy = view._accuracy;
	_st = view._st;

	_meas_num = view._meas_num;
	_meas_type = view._meas_type;
	_time_step = view._time_step;
	_start_year = view._start_year;
	_end_year = view._end_year;
	_vax_field = view._vax_field;
	_transmit = view._transmit;
	_meas_count = view._meas_count;
	_data_source = view._data_source;
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_meas_type = null;
	_time_step = null;
	_vax_field = null;
	_transmit = null;	
	_data_source = null;
	_data_units = null;

	super.finalize();
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

// meas type

/**
Returns _data_source
@return _data_source
*/
public String getData_source() {
	return _data_source;
}

/**
Returns _data_units
@return _data_units
*/
public String getData_units() {
	return _data_units;
}

/**
Returns _end_year
@return _end_year
*/
public int getEnd_year() {
	return _end_year;
}

/**
Returns _meas_count
@return _meas_count
*/
public int getMeas_count() {
	return _meas_count;
}

/**
Returns _meas_num
@return _meas_num
*/
public int getMeas_num() {
	return _meas_num;
}

/**
Returns _meas_type
@return _meas_type
*/
public String getMeas_type() {
	return _meas_type;
}

/**
Returns _start_year
@return _start_year
*/
public int getStart_year() {
	return _start_year;
}

/**
Returns _time_step
@return _time_step
*/
public String getTime_step() {
	return _time_step;
}

/**
Returns _transmit
@return _transmit
*/
public String getTransmit() {
	return _transmit;
}

/**
Returns _vax_field
@return _vax_field
*/
public String getVax_field() {
	return _vax_field;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

// meas type

/**
Sets _data_source
@param data_source value to put into _data_source
*/
public void setData_source(String data_source) {
	_data_source = data_source;
}

/**
Sets _data_units
@param data_units value to put into _data_units
*/
public void setData_units(String data_units) {
	_data_units = data_units;
}

/**
Sets _end_year
@param end_year value to put into _end_year
*/
public void setEnd_year(int end_year) {
	_end_year = end_year;
}

/**
Sets _meas_count
@param meas_count value to put into _meas_count
*/
public void setMeas_count(int meas_count) {
	_meas_count = meas_count;
}

/**
Sets _meas_num
@param meas_num value to put into _meas_num
*/
public void setMeas_num(int meas_num) {
	_meas_num = meas_num;
}

/**
Sets _meas_type
@param meas_type value to put into _meas_type
*/
public void setMeas_type(String meas_type) {
	_meas_type = meas_type;
}

/**
Sets _start_year
@param start_year value to put into _start_year
*/
public void setStart_year(int start_year) {
	_start_year = start_year;
}

/**
Sets _time_step
@param time_step value to put into _time_step
*/
public void setTime_step(String time_step) {
	_time_step = time_step;
}

/**
Sets _transmit
@param transmit value to put into _transmit
*/
public void setTransmit(String transmit) {
	_transmit = transmit;
}

/**
Sets _vax_field
@param vax_field value to put into _vax_field
*/
public void setVax_field(String vax_field) {
	_vax_field = vax_field;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_StationGeolocMeasType {"		+ "\n" + 
		"Modified:     " + _modified + "\n" + 
		"User_num:     " + _user_num + "\n" + 
		"Meas_num:    " + _meas_num + "\n" +
		"Meas_type:   " + _meas_type + "\n" +
		"Time_step:   " + _time_step + "\n" +
		"Start_year:  " + _start_year + "\n" +
		"End_year:    " + _end_year + "\n" +
		"Vax_field:   " + _vax_field + "\n" +
		"Transmit:    " + _transmit + "\n" +
		"Meas_count:  " + _meas_count + "\n" +
		"Data_source: " + _data_source + "\n" +
		"Data_units:  " + _data_units + "\n" +
		
		super.toString();
}

}
