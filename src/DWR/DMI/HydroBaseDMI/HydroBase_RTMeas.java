// HydroBase_RTMeas - Class to hold data from the HydroBase rt_meas table.

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

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the the HydroBase rt_meas table.
*/
public class HydroBase_RTMeas 
extends DMIDataObject {

protected int _meas_num = 	DMIUtil.MISSING_INT;
protected Date _date_time = 	DMIUtil.MISSING_DATE;
protected int _station_num = 	DMIUtil.MISSING_INT;
protected double _amt = 	DMIUtil.MISSING_DOUBLE;
protected String _unit = 	DMIUtil.MISSING_STRING;
protected String _flag = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_RTMeas() {
	super();
}

/**
Returns _amt
@return _amt
*/
public double getAmt() {
	return _amt;
}

/**
Returns _date_time
@return _date_time
*/
public Date getDate_time() {
	return _date_time;
}

/**
Returns _flag
@return _flag
*/
public String getFlag() {
	return _flag;
}

/**
Returns _meas_num
@return _meas_num
*/
public int getMeas_num() {
	return _meas_num;
}

/**
Returns _station_num
@return _station_num
*/
public int getStation_num() {
	return _station_num;
}

/**
Returns _unit
@return _unit
*/
public String getUnit() {
	return _unit;
}

/**
Sets _amt
@param amt value to put into _amt
*/
public void setAmt(double amt) {
	_amt = amt;
}

/**
Sets _date_time
@param date_time value to put into _date_time
*/
public void setDate_time(Date date_time) {
	_date_time = date_time;
}

/**
Sets _flag
@param flag value to put into _flag
*/
public void setFlag(String flag) {
	_flag = flag;
}

/**
Sets _meas_num
@param meas_num value to put into _meas_num
*/
public void setMeas_num(int meas_num) {
	_meas_num = meas_num;
}

/**
Sets _station_num
@param station_num value to put into _station_num
*/
public void setStation_num(int station_num) {
	_station_num = station_num;
}

/**
Sets _unit
@param unit value to put into _unit
*/
public void setUnit(String unit) {
	_unit = unit;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_RTMeas {"		+ "\n" + 
		"Meas_num:    " + _meas_num + "\n" +
		"Date_time:   " + _date_time + "\n" +
		"Station_num: " + _station_num + "\n" +
		"Amt:         " + _amt + "\n" +
		"Unit:        " + _unit + "\n" +
		"Flag:        " + _flag + "\n}\n";
}

}
