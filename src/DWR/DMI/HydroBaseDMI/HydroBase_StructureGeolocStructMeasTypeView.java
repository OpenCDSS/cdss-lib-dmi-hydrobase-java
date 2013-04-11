package DWR.DMI.HydroBaseDMI;

import java.util.Date;

import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase struct_meas_type table.
*/
public class HydroBase_StructureGeolocStructMeasTypeView 
extends HydroBase_Geoloc { // HydroBase_Geoloc extends DMIDataObject

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

// from Structure
protected String _str_name = DMIUtil.MISSING_STRING;
protected int _id = DMIUtil.MISSING_INT;
protected int _wd = DMIUtil.MISSING_INT;
protected String _wdid = DMIUtil.MISSING_STRING;

// from unpermitted wells
protected String _usbr_id = DMIUtil.MISSING_STRING;
protected String _usgs_id = DMIUtil.MISSING_STRING;

protected String _common_id = DMIUtil.MISSING_STRING;
protected String _data_units = DMIUtil.MISSING_STRING;	

/**
Constructor.
*/
public HydroBase_StructureGeolocStructMeasTypeView() {
	super();
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
	_usbr_id = null;	
	_usgs_id = null;
	_common_id = null;
	_data_units = null;

	super.finalize();
}

/**
Returns _common_id
@return _common_id
*/
public String getCommon_id() {
	return _common_id;
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
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Returns _usbr_id
@return _usbr_id
*/
public String getUsbr_id() {
	return _usbr_id;
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
Returns _wdid
@return _wdid
*/
public String getWDID() {
    return _wdid;
}

/**
Sets _common_id
@param common_id the value to which _common_id will be set
*/
public void setCommon_id(String common_id) {
	_common_id = common_id;
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
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/**
Sets _usbr_id
@param usbr_id value to put into _usbr_id
*/
public void setUsbr_id(String usbr_id) {
	_usbr_id = usbr_id;
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
Sets _wdid
@param wdid value to put into _wdid
*/
public void setWD(String wdid) {
    _wdid = wdid;
}

}