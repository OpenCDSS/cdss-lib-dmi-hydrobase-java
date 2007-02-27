// ----------------------------------------------------------------------------
// HydroBase_CUModHargreaves.java - Class to hold data from 
//	the HydroBase cu_mod_hargreaves table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-06	J. Thomas Sapienza, RTi	Initial version from 
//					HBCUModifiedHargreaves.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2005-02-28	JTS, RTi		Added method_desc
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase cu_mod_hargreaves table.  
A Mod Hargreaves crop
coefficient curve requires data from several tables in order to define the
object.  This is different from most other HydroBase obects.  cu_penman_monteith
and cu_blaney_criddle are similar.
*/
public class HydroBase_CUModHargreaves 
extends DMIDataObject {

// From cucrop
protected int _cropnum = DMIUtil.MISSING_INT;
protected String _cropname = DMIUtil.MISSING_STRING;

// From cu_mod_hargreaves
protected int _method_num = DMIUtil.MISSING_INT;
protected int _cover_10 = DMIUtil.MISSING_INT;
protected int _cover_80 = DMIUtil.MISSING_INT;
protected int _firstcut = DMIUtil.MISSING_INT;
protected int _maturity = DMIUtil.MISSING_INT;
protected int _secondcut = DMIUtil.MISSING_INT;
protected int _harvest = DMIUtil.MISSING_INT;
protected float _k1 = DMIUtil.MISSING_FLOAT;
protected float _k2 = DMIUtil.MISSING_FLOAT;
protected float _k3 = DMIUtil.MISSING_FLOAT;

protected String _method_desc = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_CUModHargreaves() {
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
Returns _cover_10
@return _cover_10
*/
public int getCover_10() {
	return _cover_10;
}

/**
Returns _cover_80
@return _cover_80
*/
public int getCover_80() {
	return _cover_80;
}

/**
Returns _firstcut
@return _firstcut
*/
public int getFirstcut() {
	return _firstcut;
}

/**
Returns _harvest
@return _harvest
*/
public int getHarvest() {
	return _harvest;
}

/**
Returns _k1
@return _k1
*/
public float getK1() {
	return _k1;
}

/**
Returns _k2
@return _k2
*/
public float getK2() {
	return _k2;
}

/**
Returns _k3
@return _k3
*/
public float getK3() {
	return _k3;
}

/**
Returns _maturity
@return _maturity
*/
public int getMaturity() {
	return _maturity;
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
Returns _secondcut
@return _secondcut
*/
public int getSecondcut() {
	return _secondcut;
}

/**
Sets _cropnum
*/
public void setCropnum(int cropnum) {
	_cropnum = cropnum;
}

/**
Sets cropname
@param cropname value to put in _cropname
*/
public void setCropname(String cropname) {
	_cropname = cropname;
}

/**
Sets _cover_10
@param cover_10 Value to put in _cover_10
*/
public void setCover_10(int cover_10) {
	 _cover_10 = cover_10;
}

/**
Sets _cover_80
@param cover_80 Value to put in _cover_80
*/
public void setCover_80(int cover_80) {
	 _cover_80 = cover_80;
}

/**
Sets _firstcut
@param firstcut Value to put in _firstcut
*/
public void setFirstcut(int firstcut) {
	 _firstcut = firstcut;
}

/**
Sets _harvest
@param harvest Value to put in _harvest
*/
public void setHarvest(int harvest) {
	 _harvest = harvest;
}

/**
Sets _k1
@param k1 Value to put in _k1
*/
public void setK1(float k1) {
	 _k1 = k1;
}

/**
Sets _k2
@param k2 Value to put in _k2
*/
public void setK2(float k2) {
	 _k2 = k2;
}

/**
Sets _k3
@param k3 Value to put in _k3
*/
public void setK3(float k3) {
	 _k3 = k3;
}

/**
Sets _maturity
@param maturity Value to put in _maturity
*/
public void setMaturity(int maturity) {
	 _maturity = maturity;
}

/**
Sets _method_desc
@param method_desc value to put into _method_desc
*/
public void setMethod_desc(String method_desc) {
	_method_desc = method_desc;
}

/**
Sets _method_num
@param method_num Value to put in _method_num
*/
public void setMethod_num(int method_num) {
	 _method_num = method_num;
}

/**
Sets _secondcut
@param secondcut Value to put in _secondcut
*/
public void setSecondcut(int secondcut) {
	 _secondcut = secondcut;
}


/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_CUModHargreaves {"		+ "\n" + 
		"Cropnum:    " + _cropnum	+ "\n" +
		"Cropname:   '" + _cropname	+ "'\n" + 
		"Method_num: " + _method_num	+ "\n" +
		"Cover_10:   " + _cover_10	+ "\n" +
		"Cover_80:   " + _cover_80	+ "\n" +
		"Firstcut:   " + _firstcut	+ "\n" +
		"Maturity:   " + _maturity	+ "\n" +
		"Secondcut:  " + _secondcut	+ "\n" +
		"Harvest:    " + _harvest	+ "\n" +
		"K1:         " + _k1		+ "\n" +
		"K2:         " + _k2		+ "\n" +
		"K3:         " + _k3 		+ "\n}\n";

}

}
