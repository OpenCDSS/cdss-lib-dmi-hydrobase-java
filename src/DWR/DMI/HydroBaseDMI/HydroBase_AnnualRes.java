// ----------------------------------------------------------------------------
// HydroBase_AnnualRes.java - Class to hold data from the HydroBase 
//	annual_res table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-12-08	Steven A. Malers , RTi	Initial version from 
//					HydroBase_ResMeas.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase annual_res table.
*/
public class HydroBase_AnnualRes 
extends DMIDataObject {

protected int _meas_num = DMIUtil.MISSING_INT;
protected int _irr_year = DMIUtil.MISSING_INT;
protected int _structure_num = DMIUtil.MISSING_INT;
protected Date _fdu = DMIUtil.MISSING_DATE;
protected Date _ldu = DMIUtil.MISSING_DATE;
protected int _dwc = DMIUtil.MISSING_INT;
protected double _ann_amt = DMIUtil.MISSING_DOUBLE;
protected Date _modified = DMIUtil.MISSING_DATE;
protected int _user_num = DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_AnnualRes() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_fdu = null;
	_ldu = null;
	_modified = null;
	
	super.finalize();
}

/**
Returns _ann_amt
@return _ann_amt
*/
public double getAnn_amt() {
	return _ann_amt;
}

/**
Returns _dwc
@return _dwc
*/
public int getDwc() {
	return _dwc;
}

/**
Returns _fdu
@return _fdu
*/
public Date getFdu() {
	return _fdu;
}

/**
Returns _irr_year
@return _irr_year
*/
public int getIrr_year() {
	return _irr_year;
}

/**
Returns _ldu
@return _ldu
*/
public Date getLdu() {
	return _ldu;
}

/**
Returns _meas_num
@return _meas_num
*/
public int getMeas_num() {
	return _meas_num;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Sets _ann_amt
@param ann_amt value to put into _ann_amt
*/
public void setAnn_amt(double ann_amt) {
	_ann_amt = ann_amt;
}

/**
Sets _dwc
@param dwc value to put into _dwc
*/
public void setDwc(int dwc) {
	_dwc = dwc;
}

/**
Sets _fdu
@param fdu value to put into _fdu
*/
public void setFdu(Date fdu) {
	_fdu = fdu;
}

/**
Sets _irr_year
@param irr_year value to put into _irr_year
*/
public void setIrr_year(int irr_year) {
	_irr_year = irr_year;
}

/**
Sets _ldu
@param ldu value to put into _ldu
*/
public void setLdu(Date ldu) {
	_ldu = ldu;
}

/**
Sets _meas_num
@param meas_num value to put into _meas_num
*/
public void setMeas_num(int meas_num) {
	_meas_num = meas_num;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_ResMeas {"		+ "\n" + 
		"Meas_num:      " + _meas_num + "\n" +
		"Irr_year:      " + _irr_year + "\n" +
		"Structure_num: " +_structure_num + "\n" +
		"FDU:           " + _fdu + "\n" +
		"LDU:           " + _ldu + "\n" +
		"DWC:           " + _dwc + "\n" +
		"Ann_amt:       " + _ann_amt + "\n" +
		"Modified:      " + _modified + "\n" +
		"User_num:      " + _user_num + "\n}\n";
	
}

}
