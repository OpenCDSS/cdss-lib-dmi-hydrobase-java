package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the the HydroBase structure, geoloc, and struct_meas_type tables.
*/
public class HydroBase_StructureGeolocStructMeasType 
extends HydroBase_StructureGeoloc {

protected int _meas_num = DMIUtil.MISSING_INT;
protected int _structure_num = DMIUtil.MISSING_INT;
protected String _meas_type = DMIUtil.MISSING_STRING;
protected String _time_step = DMIUtil.MISSING_STRING;
protected int _start_year = DMIUtil.MISSING_INT;
protected int _end_year = DMIUtil.MISSING_INT;
protected String _identifier = DMIUtil.MISSING_STRING;
protected String _transmit = DMIUtil.MISSING_STRING;
protected int _meas_count = DMIUtil.MISSING_INT;
protected String _data_source = DMIUtil.MISSING_STRING;
protected Date _modified = DMIUtil.MISSING_DATE;
protected int _user_num = DMIUtil.MISSING_INT;
protected String _str_name = DMIUtil.MISSING_STRING;
// TODO SAM 2013-04-04 Why are the following wd and id redundant with the parent class?
protected int _id = DMIUtil.MISSING_INT;
protected int _wd = DMIUtil.MISSING_INT;
// Not in HydroBase but is used by software.
protected String _data_units = DMIUtil.MISSING_STRING;							

// from unpermitted wells:
protected String _usgs_id = DMIUtil.MISSING_STRING;
protected String _usbr_id = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_StructureGeolocStructMeasType() {
	super();
}

/**
Copy constructor to fill in all the values of this object from a corresponding
view.  Used during unit tests.  
*/
public HydroBase_StructureGeolocStructMeasType(HydroBase_StructureGeolocStructMeasTypeView view)
{
	_meas_num = view._meas_num;
	_structure_num = view._structure_num;
	_meas_type = view._meas_type;
	_time_step = view._time_step;
	_start_year = view._start_year;
	_end_year = view._end_year;
	_identifier = view._identifier;
	_transmit = view._transmit;
	_meas_count = view._meas_count;
	_data_source = view._data_source;
	_modified = view._modified;
	_user_num = view._user_num;
	_str_name = view._str_name;
	_id = view._id;
	_wd = view._wd;
	_wdid = view._wdid;
	_structure_num = view._structure_num;
	_geoloc_num = view._geoloc_num;
	_div = view._div;
	_wd = view._wd;
	_id = view._id;
	_str_name = view._str_name;
	_modified = view._modified;
	_user_num = view._user_num;
	_aquifer_num = view._aquifer_num;
	_stream_num = view._stream_num;
	_str_mile = view._str_mile;
	_geoloc_num = view._geoloc_num;
	_utm_x = view._utm_x;
	_utm_y = view._utm_y;
	_latdecdeg = view._latdecdeg;
	_longdecdeg = view._longdecdeg;
	_pm = view._pm;
	_ts = view._ts;
	_tdir = view._tdir;
	_tsa = view._tsa;
	_rng = view._rng;
	_rdir = view._rdir;
	_rnga = view._rnga;
	_sec = view._sec;
	_seca = view._seca;
	_q160 = view._q160;
	_q40 = view._q40;
	_q10 = view._q10;
	_coordsns = view._coordsns;
	_coordsns_dir = view._coordsns_dir;
	_coordsew = view._coordsew;
	_coordsew_dir = view._coordsew_dir;
	_div = view._div;
	_wd = view._wd;
	_id = view._id;
	_county = view._county;
	_topomap = view._topomap;
	_cty = view._cty;
	_huc = view._huc;
	_elev = view._elev;
	_loc_type = view._loc_type;
	_feature_type = view._feature_type;
	_accuracy = view._accuracy;
	_st = view._st;
	_stream_num = view._stream_num;
	_str_mile = view._str_mile;
	_loc_description = view._loc_description;
	_spotter_version = view._spotter_version;
	_modified = view._modified;
	_user_num = view._user_num;
	_strname = view._strname;
	_rolodex_num = view._rolodex_num;
	_full_name = view._full_name;
	_usgs_id = view._usgs_id;
	_usbr_id = view._usbr_id;
	_data_units = view._data_units;
}	

/**
Copy constructor to fill in all the values of this object from a corresponding view.  
*/
public HydroBase_StructureGeolocStructMeasType(HydroBase_StructMeasTypeView view)
{
	_meas_num = view._meas_num;
	_structure_num = view._structure_num;
	_meas_type = view._meas_type;
	_time_step = view._time_step;
	_start_year = view._start_year;
	_end_year = view._end_year;
	_identifier = view._identifier;
	_transmit = view._transmit;
	_meas_count = view._meas_count;
	_data_source = view._data_source;
	_modified = view._modified;
	_user_num = view._user_num;
	_str_name = view._str_name;
	_id = view._id;
	_wd = view._wd;
	_wdid = view._wdid;
	_str_type = view._str_type;
	_STRTYPE = view._strtype;
	_structure_num = view._structure_num;
	_geoloc_num = view._geoloc_num;
	_div = view._div;
	_wd = view._wd;
	_id = view._id;
	_str_name = view._str_name;
	_modified = view._modified;
	_user_num = view._user_num;
	_aquifer_num = view._aquifer_num;
	_stream_num = view._stream_num;
	_str_mile = view._str_mile;
	_geoloc_num = view._geoloc_num;
	_utm_x = view._utm_x;
	_utm_y = view._utm_y;
	_latdecdeg = view._latdecdeg;
	_longdecdeg = view._longdecdeg;
	_pm = view._pm;
	_ts = view._ts;
	_tdir = view._tdir;
	_tsa = view._tsa;
	_rng = view._rng;
	_rdir = view._rdir;
	_rnga = view._rnga;
	_sec = view._sec;
	_seca = view._seca;
	_q160 = view._q160;
	_q40 = view._q40;
	_q10 = view._q10;
	_coordsns = view._coordsns;
	_coordsns_dir = view._coordsns_dir;
	_coordsew = view._coordsew;
	_coordsew_dir = view._coordsew_dir;
	_div = view._div;
	_wd = view._wd;
	_id = view._id;
	_county = view._county;
	_topomap = view._topomap;
	_cty = view._cty;
	_huc = view._huc;
	_elev = view._elev;
	_loc_type = view._loc_type;
	_feature_type = view._feature_type;
	_accuracy = view._accuracy;
	_st = view._st;
	_stream_num = view._stream_num;
	_str_mile = view._str_mile;
	_loc_description = view._loc_description;
	_spotter_version = view._spotter_version;
	_modified = view._modified;
	_user_num = view._user_num;
	_strname = view._strname;
	_rolodex_num = view._rolodex_num;
	_full_name = view._full_name;
	_data_units = view._data_units;
}	

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_data_source = null;
	_identifier = null;
	_meas_type = null;
	_modified = null;
	_str_name = null;
	_time_step = null;
	_transmit = null;
	_data_units = null;
	_usgs_id = null;
	_usbr_id = null;
	
	super.finalize();
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
Returns _end_year
@return _end_year
*/
public int getEnd_year() {
	return _end_year;
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
}

/**
Returns _identifier
@return _identifier
*/
public String getIdentifier() {
	return _identifier;
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
Returns _start_year
@return _start_year
*/
public int getStart_year() {
	return _start_year;
}

/**
Returns _str_name
@return _str_name
*/
public String getStr_name() {
	return _str_name;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
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
Returns _usbr_id
@return _usbr_id
*/
public String getUsbr_id() {
	return _usbr_id;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Returns _usgs_id
@return _usgs_id
*/
public String getUsgs_id() {
	return _usgs_id;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
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
Sets _end_year
@param end_year value to put into _end_year
*/
public void setEnd_year(int end_year) {
	_end_year = end_year;
}

/**
Sets _id
@param id value to put into _id
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _identifier
@param identifier value to put into _identifier
*/
public void setIdentifier(String identifier) {
	_identifier = identifier;
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
Sets _start_year
@param start_year value to put into _start_year
*/
public void setStart_year(int start_year) {
	_start_year = start_year;
}

/**
Sets _str_name
@param str_name value to put into _str_name
*/
public void setStr_name(String str_name) {
	_str_name = str_name;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
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
Sets _usbr_id
@param usbr_id value to put into _usbr_id
*/
public void setUsbr_id(String usbr_id) {
	_usbr_id = usbr_id;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/**
Sets _usgs_id
@param usgs_id value to put into _usgs_id
*/
public void setUsgs_id(String usgs_id) {
	_usgs_id = usgs_id;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_StructureGeolocStructMeasType {"	+ "\n" + 
		"Meas_num:      " + _meas_num + "\n" + 
		"Structure_num: " + _structure_num + "\n" + 
		"Meas_type:     " + _meas_type + "\n" + 
		"Time_step:     " + _time_step + "\n" + 
		"Start_year:    " + _start_year + "\n" + 
		"End_year:      " + _end_year + "\n" + 
		"Identifier:    " + _identifier + "\n" + 
		"Transmit:      " + _transmit + "\n" + 
		"Meas_count:    " + _meas_count + "\n" + 
		"Data_source:   " + _data_source + "\n" + 
		"Data_units:    " + _data_units + "\n" + 
		"Modified:      " + _modified + "\n" + 
		"User_num:      " + _user_num + "\n" + 
		"Str_name:      " + _str_name + "\n" + 
		"ID:            " + _id + "\n" + 
		"WD:            " + _wd + "\n}\n" +
		super.toString();
}

}