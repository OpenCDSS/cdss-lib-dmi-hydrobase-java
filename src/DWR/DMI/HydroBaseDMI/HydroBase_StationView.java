// ----------------------------------------------------------------------------
// HydroBase_StationView.java - Class to hold data from the HydroBase 
//	station and geoloc
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2005-02-09	J. Thomas Sapienza, RTi	Initial version.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase station and geoloc tables.  
This class is the base class for:
HydroBase_CUClimWtsStationView
*/
public class HydroBase_StationView 
extends HydroBase_Station {

// from station
protected String _cooperator_id = 	DMIUtil.MISSING_STRING;

// from Geoloc
protected double _utm_x = 		DMIUtil.MISSING_DOUBLE;
protected double _utm_y = 		DMIUtil.MISSING_DOUBLE;
protected double _latdecdeg = 		DMIUtil.MISSING_DOUBLE;
protected double _longdecdeg = 		DMIUtil.MISSING_DOUBLE;
protected int _div = 			DMIUtil.MISSING_INT;
protected int _wd = 			DMIUtil.MISSING_INT;
protected String _county = 		DMIUtil.MISSING_STRING;
protected String _topomap = 		DMIUtil.MISSING_STRING;
protected int _cty = 			DMIUtil.MISSING_INT;
protected String _huc = 		DMIUtil.MISSING_STRING;
protected double _elev = 		DMIUtil.MISSING_DOUBLE;
protected String _loc_type = 		DMIUtil.MISSING_STRING;
protected int _accuracy = 		DMIUtil.MISSING_INT;
protected String _st = 			DMIUtil.MISSING_STRING;

// from MeasType
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

/**
Construct and initialize to empty strings and missing data.
*/
public HydroBase_StationView() {
	super();
}

/**
Finalize before garbage collection.
@exception Throwable if an error occurs.
*/
protected void finalize ()
throws Throwable
{	
	_county = null;
	_topomap = null;
	_loc_type = null;
	_st = null;
	_modified = null;
	_huc = null;
	_meas_type = null;
	_time_step = null;
	_vax_field = null;
	_transmit = null;
	_data_source = null;
	_data_units = null;

	super.finalize();
}

/**
Returns _accuracy
@return _accuracy
*/
public int getAccuracy() {
	return _accuracy;
}

/**
Returns _cooperator_id
@return _cooperator_id
*/
public String getCooperator_id() {
	return _cooperator_id;
}

/**
Returns _county
@return _county
*/
public String getCounty() {
	return _county;
}

/**
Returns _cty
@return _cty
*/
public int getCty() {
	return _cty;
}

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
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _elev
@return _elev
*/
public double getElev() {
	return _elev;
}

/**
Returns _elev.  Elevation is used instead of elev in older db versions.
@return _elev
*/
public double getElevation() {
	return _elev;
}

/**
Returns _end_year
@return _end_year
*/
public int getEnd_year() {
	return _end_year;
}

/**
Returns _geoloc_num
@return _geoloc_num
*/
public int getGeoloc_num() {
	return _geoloc_num;
}

/**
Returns _huc
@return _huc
*/
public String getHUC() {
	return _huc;
}

/**
Returns _latdecdeg
@return _latdecdeg
*/
public double getLatdecdeg() {
	return _latdecdeg;
}

/**
Returns _loc_type
@return _loc_type
*/
public String getLoc_type() {
	return _loc_type;
}

/**
Returns _longdecdeg
@return _longdecdeg
*/
public double getLongdecdeg() {
	return _longdecdeg;
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
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _st
@return _st
*/
public String getST() {
	return _st;
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
Returns _topomap
@return _topomap
*/
public String getTopomap() {
	return _topomap;
}

/**
Returns _transmit
@return _transmit
*/
public String getTransmit() {
	return _transmit;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Returns _utm_x
@return _utm_x
*/
public double getUtm_x() {
	return _utm_x;
}

/**
Returns _utm_y
@return _utm_y
*/
public double getUtm_y() {
	return _utm_y;
}

/**
Returns _vax_field
@return _vax_field
*/
public String getVax_field() {
	return _vax_field;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}


/**
Sets _accuracy
@param accuracy value to put into _accuracy
*/
public void setAccuracy(int accuracy) {
	_accuracy = accuracy;
}

/**
Sets _cooperator_id
@param cooperator_id value to put into _cooperator_id
*/
public void setCooperator_id(String cooperator_id) {
	_cooperator_id = cooperator_id;
}

/**
Sets _county
@param county value to put into _county
*/
public void setCounty(String county) {
	_county = county;
}

/**
Sets _cty
@param cty value to put into _cty
*/
public void setCty(int cty) {
	_cty = cty;
}

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
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _elev
@param elev value to put into _elev
*/
public void setElev(double elev) {
	_elev = elev;
}

/**
Sets _elev.  Elevation is used instead of elev in older versions of the database
@param elevation value to put into _elev
*/
public void setElevation(double elevation) {
	_elev = elevation;
}

/**
Sets _end_year
@param end_year value to put into _end_year
*/
public void setEnd_year(int end_year) {
	_end_year = end_year;
}

/**
Sets _geoloc_num
@param geoloc_num value to put into _geoloc_num
*/
public void setGeoloc_num(int geoloc_num) {
	_geoloc_num = geoloc_num;
}

/**
Sets _huc
@param huc value to put into _huc
*/
public void setHUC(String huc) {
	_huc = huc;
}

/**
Sets _latdecdeg
@param latdecdeg value to put into _latdecdeg
*/
public void setLatdecdeg(double latdecdeg) {
	_latdecdeg = latdecdeg;
}

/**
Sets _loc_type
@param loc_type value to put into _loc_type
*/
public void setLoc_type(String loc_type) {
	_loc_type = loc_type;
}

/**
Sets _longdecdeg
@param longdecdeg value to put into _longdecdeg
*/
public void setLongdecdeg(double longdecdeg) {
	_longdecdeg = longdecdeg;
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
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _st
@param st value to put into _st
*/
public void setST(String st) {
	_st = st;
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
Sets _topomap
@param topomap value to put into _topomap
*/
public void setTopomap(String topomap) {
	_topomap = topomap;
}

/**
Sets _transmit
@param transmit value to put into _transmit
*/
public void setTransmit(String transmit) {
	_transmit = transmit;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/**
Sets _utm_x
@param utm_x value to put into _utm_x
*/
public void setUtm_x(double utm_x) {
	_utm_x = utm_x;
}

/**
Sets _utm_y
@param utm_y value to put into _utm_y
*/
public void setUtm_y(double utm_y) {
	_utm_y = utm_y;
}

/**
Sets _vax_field
@param vax_field value to put into _vax_field
*/
public void setVax_field(String vax_field) {
	_vax_field = vax_field;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Returns a string representation of this object.
@return a string representation of this object.
*/
public String toString() {
	return "HydroBase_View{" 		+ "\n" + 
		"Geoloc_num:      " + _geoloc_num + "\n" +
		"UTM_X:           " + _utm_x + "\n" +
		"UTM_Y:           " + _utm_y + "\n" +
		"Latdecdeg:       " + _latdecdeg + "\n" +
		"Longdecdeg:      " + _longdecdeg + "\n" +
		"Div:             " + _div + "\n" +
		"WD:              " + _wd + "\n" +
		"County:          " + _county + "\n" +
		"Topomap:         " + _topomap + "\n" +
		"Cty:             " + _cty + "\n" +
		"HUC:             " + _huc + "\n" +
		"Elev:            " + _elev + "\n" +
		"Loc_type:        " + _loc_type + "\n" +
		"Accuracy:        " + _accuracy + "\n" +
		"ST:              " + _st + "\n" +
		"Modified:        " + _modified + "\n" +
		"User_num:        " + _user_num + "\n}\n";
	
}

}
