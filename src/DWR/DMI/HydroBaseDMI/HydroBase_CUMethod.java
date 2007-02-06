//------------------------------------------------------------------------------
// HydroBase_CUMethod - data structure to hold data from the the HydroBase 
//	cu_method table
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// Notes:	(1)	This class has no knowledge of the database itself
//			(aside from its own data members), and there is no
//			knowledge of the connection with the database.
//		(2)	This class only holds information from the CU_method
//			table.
//------------------------------------------------------------------------------
// History:
// 
// 2002-11-25	Steven A. Malers, RTi	Initial version.
// 2003-01-05	SAM, RTi		Update based on changes to the DMI
//					package.
// 2003-02-25	J. Thomas Sapienza, RTi	Fleshed out toString() method.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.io.IOException;

import java.util.Date;
import java.util.Vector;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
This class provides storage for data from the HydroBase cu_method table.
The data and methods in this class correspond to the table entities described
in the HydroBase data dictionary.
*/
public class HydroBase_CUMethod 
extends DMIDataObject {

// Cropchar table data

protected int _method_num = 	DMIUtil.MISSING_INT;
protected int _cropnum = 	DMIUtil.MISSING_INT;
protected String _method_desc =	DMIUtil.MISSING_STRING;

/**
Construct and initialize to empty strings and missing data.
*/
public HydroBase_CUMethod() {
	super();
}

/**
Finalize before garbage collection.
@exception Throwable if an error occurs.
*/
protected void finalize ()
throws Throwable {
	_method_desc = null;
	super.finalize();
}

/**
Return _cropnum
@return _cropnum
*/
public int getCropnum() {
	return _cropnum;
}

/**
Return _method_desc
@return _method_desc
*/
public String getMethod_desc() {
	return _method_desc;
}

/**
Return _method_num
@return _method_num
*/
public int getMethod_num() {
	return _method_num;
}

/**
Set _cropnum.
@param cropnum the value for _cropnum.
*/
public void setCropnum (int cropnum) {
	_cropnum = cropnum;
}

/**
Set _method_desc.
@param method_desc the value for _method_desc.
*/
public void setMethod_desc (String method_desc) {
	_method_desc = method_desc;
}

/**
Set _method_num.
@param method_num the value for _method_num.
*/
public void setMethod_num (int method_num) {
	_method_num = method_num;
}

public String toString() {
	return "HydroBase_CUMethod{" 			+ "\n" + 
		"Method_num:  " + _method_num + "\n" + 
		"Cropnum:     " + _cropnum + "\n" + 
		"Method_desc: " + _method_desc + "\n}\n";
}

}