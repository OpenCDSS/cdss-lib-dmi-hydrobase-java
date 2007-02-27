//------------------------------------------------------------------------------
// HydroBase_CUBlaneyCriddle - data structure to hold data from the HydroBase 
//				cu_blaney_criddle table, with joins to crop and
//				cu_method
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// History:
// 
// 2002-12-22	Steven A. Malers, RTi	Initial version.
// 2003-01-05	SAM, RTi		Update based on changes to the DMI
//					package.
// 2003-02-23	SAM, RTi		Change cropgrowthcoeff to cropgrowcoeff
//					as per the true field definition.
// 2003-02-25	J. Thomas Sapienza, RTi	Fleshed out toString() method.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
This class provides storage for data from the HydroBase cu_blaney_criddle table,
with joins to crop and cu_method.  A Blaney-Criddle crop coefficient curve
requires data from several tables in order to define the object.  This is 
different from most other HydroBase objects.  cu_penman_monteith and 
cu_mod_hargreaves are similar. 
The data and methods in this class correspond to the table entities described
in the HydroBase data dictionary.
*/
public class HydroBase_CUBlaneyCriddle 
extends DMIDataObject {

// Cropchar table data
protected String _curve_type =		DMIUtil.MISSING_STRING;
protected int _curve_value =		DMIUtil.MISSING_INT;
protected double _cropgrowcoeff =	DMIUtil.MISSING_DOUBLE;

// cu_method table
protected String _method_desc =		DMIUtil.MISSING_STRING;

// crop table
protected String _cropname =		DMIUtil.MISSING_STRING;
protected int _cropnum = 		DMIUtil.MISSING_INT;

/**
Construct and initialize to empty strings and missing data.
*/
public HydroBase_CUBlaneyCriddle() {
	super();
}

/**
Finalize before garbage collection.
@exception Throwable if an error occurs.
*/
protected void finalize ()
throws Throwable {
	_method_desc = null;
	_cropname = null;
	_curve_type = null;
	super.finalize();
}

/**
Return _cropgrowcoeff
@return _cropgrowcoeff
*/
public double getCropgrowcoeff() {
	return _cropgrowcoeff;
}

/**
Return _cropname
@return _cropname
*/
public String getCropname() {
	return _cropname;
}

/**
Returns _cropnum
@return _cropnum
*/
public int getCropnum() {
	return _cropnum;
}

/**
Return _curve_type
@return _curve_type
*/
public String getCurve_type() {
	return _curve_type;
}

/**
Return _curve_value
@return _curve_value
*/
public int getCurve_value() {
	return _curve_value;
}

/**
Return _method_desc
@return _method_desc
*/
public String getMethod_desc() {
	return _method_desc;
}

/**
Set _cropgrowcoeff.
@param cropgrowcoeff the value for _cropgrowcoeff.
*/
public void setCropgrowcoeff (double cropgrowcoeff) {
	_cropgrowcoeff = cropgrowcoeff;
}

/**
Set _cropname.
@param cropname the value for _cropname.
*/
public void setCropname (String cropname) {
	_cropname = cropname;
}

/**
Sets _cropnum
@param cropnum the value for _cropnum
*/
public void setCropnum(int cropnum) {
	_cropnum = cropnum;
}

/**
Set _curve_type.
@param curve_type the value for _curve_type.
*/
public void setCurve_type (String curve_type) {
	_curve_type = curve_type;
}

/**
Set _curve_value.
@param curve_value the value for _curve_value.
*/
public void setCurve_value (int curve_value) {
	_curve_value = curve_value;
}

/**
Set _method_desc.
@param method_desc the value for _method_desc.
*/
public void setMethod_desc (String method_desc) {
	_method_desc = method_desc;
}

public String toString() {
	return "HydroBase_CUBLaneyCriddle {"		+ "\n" + 
		"Curve_type:    " + _curve_type + "\n" + 
		"Curve_value:   " + _curve_value + "\n" + 
		"Cropgrowcoeff: " + _cropgrowcoeff + "\n" + 
		"Method_desc:   " + _method_desc + "\n" + 
		"Cropname:      " + _cropname + "\n}\n";
}

}
