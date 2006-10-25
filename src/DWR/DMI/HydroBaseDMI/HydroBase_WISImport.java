// ----------------------------------------------------------------------------
// HydroBase_WISImport.java - Class to hold data from the HydroBase 
//	wis_import table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-12	J. Thomas Sapienza, RTi	Initial version from HBWISImport.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase wis_import table.
*/
public class HydroBase_WISImport 
extends DMIDataObject {

protected int _wis_num = 		DMIUtil.MISSING_INT;
protected int _wis_row = 		DMIUtil.MISSING_INT;
protected String _column = 		DMIUtil.MISSING_STRING;
protected int _end_time = 		DMIUtil.MISSING_INT;
protected int _time_offset = 		DMIUtil.MISSING_INT;
protected int _meas_num = 		DMIUtil.MISSING_INT;
protected String _import_method = 	DMIUtil.MISSING_STRING;
protected int _import_wis_num = 	DMIUtil.MISSING_INT;
protected String _import_identifier = 	DMIUtil.MISSING_STRING;
protected String _import_column = 	DMIUtil.MISSING_STRING;
protected String _import_meas_desc = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_WISImport() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_column = null;
	_import_method = null;
	_import_identifier = null;
	_import_column = null;
	_import_meas_desc = null;
	
	super.finalize();
}

/**
Returns _column
@return _column
*/
public String getColumn() {
	return _column;
}

/**
Returns _end_time
@return _end_time
*/
public int getEnd_time() {
	return _end_time;
}

/**
Returns _import_column
@return _import_column
*/
public String getImport_column() {
	return _import_column;
}

/**
Returns _import_identifier
@return _import_identifier
*/
public String getImport_identifier() {
	return _import_identifier;
}

/**
Returns _import_meas_desc
@return _import_meas_desc
*/
public String getImport_meas_desc() {
	return _import_meas_desc;
}

/**
Returns _import_method
@return _import_method
*/
public String getImport_method() {
	return _import_method;
}

/**
Returns _import_wis_num
@return _import_wis_num
*/
public int getImport_wis_num() {
	return _import_wis_num;
}

/**
Returns _meas_num
@return _meas_num
*/
public int getMeas_num() {
	return _meas_num;
}

/**
Returns _time_offset
@return _time_offset
*/
public int getTime_offset() {
	return _time_offset;
}

/**
Returns _wis_num
@return _wis_num
*/
public int getWis_num() {
	return _wis_num;
}

/**
Returns _wis_row
@return _wis_row
*/
public int getWis_row() {
	return _wis_row;
}

/**
Sets _column
@param column value to put into _column
*/
public void setColumn(String column) {
	_column = column;
}

/**
Sets _end_time
@param end_time value to put into _end_time
*/
public void setEnd_time(int end_time) {
	_end_time = end_time;
}

/**
Sets _import_column
@param import_column value to put into _import_column
*/
public void setImport_column(String import_column) {
	_import_column = import_column;
}

/**
Sets _import_identifier
@param import_identifier value to put into _import_identifier
*/
public void setImport_identifier(String import_identifier) {
	_import_identifier = import_identifier;
}

/**
Sets _import_meas_desc
@param import_meas_desc value to put into _import_meas_desc
*/
public void setImport_meas_desc(String import_meas_desc) {
	_import_meas_desc = import_meas_desc;
}

/**
Sets _import_method
@param import_method value to put into _import_method
*/
public void setImport_method(String import_method) {
	_import_method = import_method;
}

/**
Sets _import_wis_num
@param import_wis_num value to put into _import_wis_num
*/
public void setImport_wis_num(int import_wis_num) {
	_import_wis_num = import_wis_num;
}

/**
Sets _meas_num
@param meas_num value to put into _meas_num
*/
public void setMeas_num(int meas_num) {
	_meas_num = meas_num;
}

/**
Sets _time_offset
@param time_offset value to put into _time_offset
*/
public void setTime_offset(int time_offset) {
	_time_offset = time_offset;
}

/**
Sets _wis_num
@param wis_num value to put into _wis_num
*/
public void setWis_num(int wis_num) {
	_wis_num = wis_num;
}

/**
Sets _wis_row
@param wis_row value to put into _wis_row
*/
public void setWis_row(int wis_row) {
	_wis_row = wis_row;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_WISImport {"		+ "\n" + 
		"Wis_num:           " + _wis_num + "\n" + 
		"Wis_row:           " + _wis_row + "\n" + 
		"Column:            " + _column + "\n" + 
		"End_time:          " + _end_time + "\n" + 
		"Time_offset:       " + _time_offset + "\n" + 
		"Meas_num:          " + _meas_num + "\n" + 
		"Import_method:     " + _import_method + "\n" + 
		"Import_wis_num:    " + _import_wis_num + "\n" + 
		"Import_identifier: " + _import_identifier + "\n" + 
		"Import_column:     " + _import_column + "\n" + 
		"Import_meas_desc:  " + _import_meas_desc + "\n}\n";
}

}
