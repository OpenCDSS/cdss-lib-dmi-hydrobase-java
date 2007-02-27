// ----------------------------------------------------------------------------
// HydroBase_SnowCrse.java - Class to hold data from the HydroBase 
//	snow_crse table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-11	J. Thomas Sapienza, RTi	Initial version from
//					HBStationSnowCourseRecord.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase snow_crse table.
*/
public class HydroBase_SnowCrse 
extends DMIDataObject {

protected int _meas_num = 	DMIUtil.MISSING_INT;
protected int _cal_year = 	DMIUtil.MISSING_INT;
protected int _m_num = 		DMIUtil.MISSING_INT;
protected int _m_type = 	DMIUtil.MISSING_INT;
protected int _station_num = 	DMIUtil.MISSING_INT;
protected int _cal_mon_num = 	DMIUtil.MISSING_INT;
protected String _cal_mon = 	DMIUtil.MISSING_STRING;
protected String _day = 	DMIUtil.MISSING_STRING;
protected String _unit = 	DMIUtil.MISSING_STRING;
protected int _depth = 		DMIUtil.MISSING_INT;
protected double _swe = 	DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_SnowCrse() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_cal_mon = null;
	_day = null;
	_unit = null;
	
	super.finalize();
}

/**
Returns _cal_mon
@return _cal_mon
*/
public String getCal_mon() {
	return _cal_mon;
}

/**
Returns _cal_mon_num
@return _cal_mon_num
*/
public int getCal_mon_num() {
	return _cal_mon_num;
}

/**
Returns _cal_year
@return _cal_year
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _day
@return _day
*/
public String getDay() {
	return _day;
}

/**
Returns _depth
@return _depth
*/
public int getDepth() {
	return _depth;
}

/**
Returns _m_num
@return _m_num
*/
public int getM_num() {
	return _m_num;
}

/**
Returns _m_type
@return _m_type
*/
public int getM_type() {
	return _m_type;
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
Returns _swe
@return _swe
*/
public double getSwe() {
	return _swe;
}

/**
Returns _unit
@return _unit
*/
public String getUnit() {
	return _unit;
}

/**
Sets _cal_mon
@param cal_mon value to put into _cal_mon
*/
public void setCal_mon(String cal_mon) {
	_cal_mon = cal_mon;
}

/**
Sets _cal_mon_num
@param cal_mon_num value to put into _cal_mon_num
*/
public void setCal_mon_num(int cal_mon_num) {
	_cal_mon_num = cal_mon_num;
}

/**
Sets _cal_year
@param cal_year value to put into _cal_year
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _day
@param day value to put into _day
*/
public void setDay(String day) {
	_day = day;
}

/**
Sets _depth
@param depth value to put into _depth
*/
public void setDepth(int depth) {
	_depth = depth;
}

/**
Sets _m_num
@param m_num value to put into _m_num
*/
public void setM_num(int m_num) {
	_m_num = m_num;
}

/**
Sets _m_type
@param m_type value to put into _m_type
*/
public void setM_type(int m_type) {
	_m_type = m_type;
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
Sets _swe
@param swe value to put into _swe
*/
public void setSwe(double swe) {
	_swe = swe;
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
	return "HydroBase_SnowCrse {"		+ "\n" + 
		"Meas_num:    " + _meas_num + "\n" +
		"Cal_year:    " + _cal_year + "\n" +
		"M_num:       " + _m_num + "\n" +
		"M_type:      " + _m_type + "\n" +
		"Station_num: " + _station_num + "\n" +
		"Cal_mon_num: " + _cal_mon_num + "\n" +
		"Cal_mon:     " + _cal_mon + "\n" +
		"Day:         " + _day + "\n" +
		"Unit:        " + _unit + "\n" +
		"Depth:       " + _depth + "\n" +
		"Swe:         " + _swe + "\n}\n";
}

}
