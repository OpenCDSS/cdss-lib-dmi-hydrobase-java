// ----------------------------------------------------------------------------
// HydroBase_CUPenmanMonteith.java - Class to hold data from 
//		the HydroBase cu_penman_monteith table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-06	J. Thomas Sapienza, RTi	Initial version from HBCUPenmanMonteith.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2005-02-28	JTS, RTi		Added method_desc.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase cu_penman_monteith table.  
A penman monteith
crop coefficient curve requires data from several tables in order to 
define the object.  This is different from most other HydroBase objects.
CU_Blaney_Criddle and CU_mod_hargreaves are similar.
*/
public class HydroBase_CUPenmanMonteith 
extends DMIDataObject {

// From crop
protected int _cropnum = 		DMIUtil.MISSING_INT;
protected String _cropname = 		DMIUtil.MISSING_STRING;

// From cu_penman_monteith
protected int _method_num = 		DMIUtil.MISSING_INT;
protected int _growthstage_no = 	DMIUtil.MISSING_INT;
protected int _curve_value = 		DMIUtil.MISSING_INT;
protected float _cropgrowcoeff = 	DMIUtil.MISSING_FLOAT;

protected String _method_desc = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_CUPenmanMonteith() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_cropname = null;
	_method_desc = null;

	super.finalize();
}

/**
Returns _cropgrowcoeff
@return _cropgrowcoeff
*/
public float getCropgrowcoeff() {
	return _cropgrowcoeff;
}

/**
Returns _cropnum
@return _cropnum
*/
public int getCropnum() {
	return _cropnum;
}

/**
Returns _cropname
@return _cropname
*/
public String getCropname() {
	return _cropname;
}

/**
Returns _curve_value
@return _curve_value
*/
public int getCurve_value() {
	return _curve_value;
}

/**
Returns _growthstage_no
@return _growthstage_no
*/
public int getGrowthstage_no() {
	return _growthstage_no;
}

/**
Returns _method_desc
@return _method_desc
*/
public String getMethod_desc() {
	return _method_desc;
}

/**
Returns _method_num
@return _method_num
*/
public int getMethod_num() {
	return _method_num;
}


/**
Sets _cropgrowcoeff
@param cropgrowcoeff value to put in _cropgrowcoeff
*/
public void setCropgrowcoeff(float cropgrowcoeff) {
	 _cropgrowcoeff = cropgrowcoeff;
}

/**
Sets _cropnum
@param cropnum value to put in _cropnum
*/
public void setCropnum(int cropnum) {
	 _cropnum = cropnum;
}

/**
Sets _cropname
@param cropname value to put in _cropname
*/
public void setCropname(String cropname) {
	 _cropname = cropname;
}

/**
Sets _curve_value
@param curve_value value to put in _curve_value
*/
public void setCurve_value(int curve_value) {
	 _curve_value = curve_value;
}

/**
Sets _growthstage_no
@param growthstage_no value to put in _growthstage_no
*/
public void setGrowthstage_no(int growthstage_no) {
	 _growthstage_no = growthstage_no;
}

/**
Sets _method_desc
@param method_desc value to put in _method_desc
*/
public void setMethod_desc(String method_desc) {
	_method_desc = method_desc;
}

/**
Sets _method_num
@param method_num value to put in _method_num
*/
public void setMethod_num(int method_num) {
	 _method_num = method_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_CUPenmanMonteith {"		+ "\n" + 
		"Cropnum:        " + _cropnum		+ "\n" +
		"Cropname:       '" + _cropname 	+ "'\n" +
		"Method_num:     " + _method_num 	+ "\n" +
		"Growthstage_no: " + _growthstage_no 	+ "\n" +
		"Curve_value:    " + _curve_value 	+ "\n" +
		"Cropgrowcoeff:  " + _cropgrowcoeff 	+ "\n}\n";
}

}
