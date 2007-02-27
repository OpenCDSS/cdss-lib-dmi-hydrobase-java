// ----------------------------------------------------------------------------
// HydroBase_FrostDates.java - Class to hold data from the HydroBase 
//	frost_dates table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-11	J. Thomas Sapienza, RTi	Initial version from
//					HBStationFrostDatesRecord.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.

// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase frost_dates table.
*/
public class HydroBase_FrostDates 
extends DMIDataObject {

protected int _meas_num = 	DMIUtil.MISSING_INT;
protected int _cal_year = 	DMIUtil.MISSING_INT;
protected int _station_num = 	DMIUtil.MISSING_INT;
protected Date _l28s = 		DMIUtil.MISSING_DATE;
protected Date _f28f = 		DMIUtil.MISSING_DATE;
protected Date _l32s = 		DMIUtil.MISSING_DATE;
protected Date _f32f = 		DMIUtil.MISSING_DATE;

/**
Constructor.
*/
public HydroBase_FrostDates() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_l28s = null;
	_f28f = null;
	_l32s = null;
	_f32f = null;
	super.finalize();
}

/**
Returns _cal_year
@return _cal_year
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _f28f
@return _f28f
*/
public Date getF28f() {
	return _f28f;
}

/**
Returns _f32f
@return _f32f
*/
public Date getF32f() {
	return _f32f;
}

/**
Returns _l28s
@return _l28s
*/
public Date getL28s() {
	return _l28s;
}

/**
Returns _l32s
@return _l32s
*/
public Date getL32s() {
	return _l32s;
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
Sets _cal_year
@param cal_year value to put into _cal_year
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _f28f
@param f28f value to put into _f28f
*/
public void setF28f(Date f28f) {
	_f28f = f28f;
}

/**
Sets _f32f
@param f32f value to put into _f32f
*/
public void setF32f(Date f32f) {
	_f32f = f32f;
}

/**
Sets _l28s
@param l28s value to put into _l28s
*/
public void setL28s(Date l28s) {
	_l28s = l28s;
}

/**
Sets _l32s
@param l32s value to put into _l32s
*/
public void setL32s(Date l32s) {
	_l32s = l32s;
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
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_FrostDates {"		+ "\n" + 
		"Meas_num:    " + _meas_num + "\n" + 
		"Cal_year:    " + _cal_year + "\n" + 
		"Station_num: " + _station_num + "\n" + 
		"L28s:        " + _l28s + "\n" + 
		"F28f:        " + _f28f + "\n" + 
		"L32s:        " + _l32s + "\n" + 
		"F32f:        " + _f32f + "\n}\n";
}

}
