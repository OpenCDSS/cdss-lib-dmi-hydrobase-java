// ----------------------------------------------------------------------------
// HydroBase_ResEOM.java - Class to hold data from the HydroBase res_eom table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-10	J. Thomas Sapienza, RTi	Initial version from
//					HBReservoirEOMStorageRecord.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the the HydroBase res_eom table.
*/
public class HydroBase_ResEOM 
extends DMIDataObject {

protected int _meas_num = 	DMIUtil.MISSING_INT;
protected int _structure_num = 	DMIUtil.MISSING_INT;
protected int _cal_year = 	DMIUtil.MISSING_INT;
protected String _cal_mon = 	DMIUtil.MISSING_STRING;
protected int _cal_mon_num = 	DMIUtil.MISSING_INT;
protected double _total_af = 	DMIUtil.MISSING_DOUBLE;
protected String _data_src = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_ResEOM() {
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
	_data_src = null;
	
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
Returns _data_src
@return _data_src
*/
public String getData_src() {
	return _data_src;
}

/**
Returns _meas_num
@return _meas_num
*/
public int getMeas_num() {
	return _meas_num;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _total_af
@return _total_af
*/
public double getTotal_af() {
	return _total_af;
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
Sets _data_src
@param data_src value to put into _data_src
*/
public void setData_src(String data_src) {
	_data_src = data_src;
}

/**
Sets _meas_num
@param meas_num value to put into _meas_num
*/
public void setMeas_num(int meas_num) {
	_meas_num = meas_num;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _total_af
@param total_af value to put into _total_af
*/
public void setTotal_af(double total_af) {
	_total_af = total_af;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_ResEOM {"		+ "\n" + 
		"Meas_num:      " + _meas_num + "\n" + 
		"Structure_num: " + _structure_num + "\n" + 
		"Cal_year:      " + _cal_year + "\n" + 
		"Cal_mon:       " + _cal_mon + "\n" + 
		"Cal_mon_num:   " + _cal_mon_num + "\n" + 
		"Total_af:      " + _total_af + "\n" + 
		"Data_src:      " + _data_src + "\n}\n";
	
}

}
