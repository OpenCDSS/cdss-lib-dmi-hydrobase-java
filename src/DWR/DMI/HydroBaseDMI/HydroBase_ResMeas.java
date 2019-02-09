// HydroBase_ResMeas - Class to hold data from the HydroBase res_meas table.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

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

// ----------------------------------------------------------------------------
// HydroBase_ResMeas.java - Class to hold data from the HydroBase 
//	res_meas table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-10	J. Thomas Sapienza, RTi	Initial version from 
//					HBReservoirMeasurement.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase res_meas table.
*/
public class HydroBase_ResMeas 
extends DMIDataObject {

protected int _meas_num = 		DMIUtil.MISSING_INT;
protected Date _date_time = 		DMIUtil.MISSING_DATE;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected double _gage_height = 	DMIUtil.MISSING_DOUBLE;
protected double _storage_amt = 	DMIUtil.MISSING_DOUBLE;
protected double _fill_amt = 		DMIUtil.MISSING_DOUBLE;
protected double _release_amt = 	DMIUtil.MISSING_DOUBLE;
protected double _evap_loss_amt = 	DMIUtil.MISSING_DOUBLE;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_ResMeas() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_date_time = null;
	_modified = null;
	
	super.finalize();
}

/**
Returns _date_time
@return _date_time
*/
public Date getDate_time() {
	return _date_time;
}

/**
Returns _evap_loss_amt
@return _evap_loss_amt
*/
public double getEvap_loss_amt() {
	return _evap_loss_amt;
}

/**
Returns _fill_amt
@return _fill_amt
*/
public double getFill_amt() {
	return _fill_amt;
}

/**
Returns _gage_height
@return _gage_height
*/
public double getGage_height() {
	return _gage_height;
}

/**
Returns _meas_num
@return _meas_num
*/
public int getMeas_num() {
	return _meas_num;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _release_amt
@return _release_amt
*/
public double getRelease_amt() {
	return _release_amt;
}

/**
Returns _storage_amt
@return _storage_amt
*/
public double getStorage_amt() {
	return _storage_amt;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Sets _date_time
@param date_time value to put into _date_time
*/
public void setDate_time(Date date_time) {
	_date_time = date_time;
}

/**
Sets _evap_loss_amt
@param evap_loss_amt value to put into _evap_loss_amt
*/
public void setEvap_loss_amt(double evap_loss_amt) {
	_evap_loss_amt = evap_loss_amt;
}

/**
Sets _fill_amt
@param fill_amt value to put into _fill_amt
*/
public void setFill_amt(double fill_amt) {
	_fill_amt = fill_amt;
}

/**
Sets _gage_height
@param gage_height value to put into _gage_height
*/
public void setGage_height(double gage_height) {
	_gage_height = gage_height;
}

/**
Sets _meas_num
@param meas_num value to put into _meas_num
*/
public void setMeas_num(int meas_num) {
	_meas_num = meas_num;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _release_amt
@param release_amt value to put into _release_amt
*/
public void setRelease_amt(double release_amt) {
	_release_amt = release_amt;
}

/**
Sets _storage_amt
@param storage_amt value to put into _storage_amt
*/
public void setStorage_amt(double storage_amt) {
	_storage_amt = storage_amt;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_ResMeas {"		+ "\n" + 
		"Meas_num:      " + _meas_num + "\n" +
		"Date_time:     " + _date_time + "\n" +
		"Structure_num: " +_structure_num + "\n" +
		"Gage_height:   " + _gage_height + "\n" +
		"Storage_amt:   " + _storage_amt + "\n" +
		"Fill_amt:      " + _fill_amt + "\n" +
		"Release_amt:   " + _release_amt + "\n" +
		"Evap_loss_amt: " + _evap_loss_amt + "\n" +
		"Modified:      " + _modified + "\n" +
		"User_num:      " + _user_num + "\n}\n";
	
}

}
