// ----------------------------------------------------------------------------
// HydroBase_RTMeas.java - Class to hold data from the HydroBase rt_meas table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-10	J. Thomas Sapienza, RTi	Initial version from 
//					HBRealtimeMeasurement.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
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
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_date_time = null;
	_unit = null;
	_flag = null;
	
	super.finalize();
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
