// ----------------------------------------------------------------------------
// HydroBase_SpecialData.java - Class to hold data from the HydroBase 
//	special_data table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-11	J. Thomas Sapienza, RTi	Initial version from HBSpecialData.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase special_data table.
*/
public class HydroBase_SpecialData 
extends DMIDataObject {

protected int _wis_num = 	DMIUtil.MISSING_INT;
protected Date _set_date = 	DMIUtil.MISSING_DATE;
protected String _identifier = 	DMIUtil.MISSING_STRING;
protected String _meas_type = 	DMIUtil.MISSING_STRING;
protected String _meas_desc = 	DMIUtil.MISSING_STRING;
protected Date _archive_date = 	DMIUtil.MISSING_DATE;
protected String _user_post = 	DMIUtil.MISSING_STRING;
protected String _time_step = 	DMIUtil.MISSING_STRING;
protected double _sp_value = 	DMIUtil.MISSING_DOUBLE;
protected String _units = 	DMIUtil.MISSING_STRING;
protected String _comments = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_SpecialData() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_set_date = null;
	_identifier = null;
	_meas_type = null;
	_meas_desc = null;
	_archive_date = null;
	_user_post = null;
	_time_step = null;
	_units = null;
	_comments = null;
	super.finalize();
}

/**
Returns _archive_date
@return _archive_date
*/
public Date getArchive_date() {
	return _archive_date;
}

/**
Returns _comments
@return _comments
*/
public String getComments() {
	return _comments;
}

/**
Returns _identifier
@return _identifier
*/
public String getIdentifier() {
	return _identifier;
}

/**
Returns _meas_desc
@return _meas_desc
*/
public String getMeas_desc() {
	return _meas_desc;
}

/**
Returns _meas_type
@return _meas_type
*/
public String getMeas_type() {
	return _meas_type;
}

/**
Returns _set_date
@return _set_date
*/
public Date getSet_date() {
	return _set_date;
}

/**
Returns _sp_value
@return _sp_value
*/
public double getSp_value() {
	return _sp_value;
}

/**
Returns _time_step
@return _time_step
*/
public String getTime_step() {
	return _time_step;
}

/**
Returns _units
@return _units
*/
public String getUnits() {
	return _units;
}

/**
Returns _user_post
@return _user_post
*/
public String getUser_post() {
	return _user_post;
}

/**
Returns _wis_num
@return _wis_num
*/
public int getWis_num() {
	return _wis_num;
}

/**
Sets _archive_date
@param archive_date value to put into _archive_date
*/
public void setArchive_date(Date archive_date) {
	_archive_date = archive_date;
}

/**
Sets _comments
@param comments value to put into _comments
*/
public void setComments(String comments) {
	_comments = comments;
}

/**
Sets _identifier
@param identifier value to put into _identifier
*/
public void setIdentifier(String identifier) {
	_identifier = identifier;
}

/**
Sets _meas_desc
@param meas_desc value to put into _meas_desc
*/
public void setMeas_desc(String meas_desc) {
	_meas_desc = meas_desc;
}

/**
Sets _meas_type
@param meas_type value to put into _meas_type
*/
public void setMeas_type(String meas_type) {
	_meas_type = meas_type;
}

/**
Sets _set_date
@param set_date value to put into _set_date
*/
public void setSet_date(Date set_date) {
	_set_date = set_date;
}

/**
Sets _sp_value
@param sp_value value to put into _sp_value
*/
public void setSp_value(double sp_value) {
	_sp_value = sp_value;
}

/**
Sets _time_step
@param time_step value to put into _time_step
*/
public void setTime_step(String time_step) {
	_time_step = time_step;
}

/**
Sets _units
@param units value to put into _units
*/
public void setUnits(String units) {
	_units = units;
}

/**
Sets _user_post
@param user_post value to put into _user_post
*/
public void setUser_post(String user_post) {
	_user_post = user_post;
}

/**
Sets _wis_num
@param wis_num value to put into _wis_num
*/
public void setWis_num(int wis_num) {
	_wis_num = wis_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_SpecialData {"		+ "\n" + 
		"Wis_num:      " + _wis_num + "\n" + 
		"Set_date:     " + _set_date + "\n" + 
		"Identifier:   " + _identifier + "\n" + 
		"Meas_type:    " + _meas_type	 + "\n" + 
		"Meas_desc:    " + _meas_desc + "\n" + 
		"Archive_date: " + _archive_date + "\n" + 
		"User_post:    " + _user_post + "\n" + 
		"Time_step:    " + _time_step + "\n" + 
		"Sp_value:     " + _sp_value + "\n" + 
		"Units:        " + _units + "\n" + 
		"Comments:     " + _comments + "\n}\n";
}

}
