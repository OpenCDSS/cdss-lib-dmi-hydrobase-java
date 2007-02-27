// ----------------------------------------------------------------------------
// HydroBase_AnnualAmt.java - Class to hold data from 
//	the HydroBase annual_amt table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-17	J. Thomas Sapienza, RTi	Initial version from 
//					HBStructureAnnualAmountRecord
// 2003-02-20	JTS, RTi		Added getAmt(double) method.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2003-12-08	SAM, RTi		Fix bug wher setAmt_jun was setting
//					_amt_jun = _amt_jun (no effect!).
// 2003-12-09	JTS, RTI		Now extends DMIDataObject.
// 2005-07-13	JTS, RTi		Now implements Comparable, but only
//					sorts for now on irr_year.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase annual_amt table.  
This class is also the base class for HydroBase_AnnualWC.
*/
public class HydroBase_AnnualAmt 
extends DMIDataObject 
implements Comparable {

protected int _meas_num = DMIUtil.MISSING_INT;
protected int _irr_year = DMIUtil.MISSING_INT;
protected int _structure_num = DMIUtil.MISSING_INT;
protected String _quality = DMIUtil.MISSING_STRING;
protected Date _fdu = DMIUtil.MISSING_DATE;
protected Date _ldu = DMIUtil.MISSING_DATE;
protected int _dwc = DMIUtil.MISSING_INT;
protected double _maxq = DMIUtil.MISSING_DOUBLE;
protected Date _maxq_date = DMIUtil.MISSING_DATE;
protected int _nobs = DMIUtil.MISSING_INT;
protected int _nus = DMIUtil.MISSING_INT;
protected double _amt_nov = DMIUtil.MISSING_DOUBLE;
protected double _amt_dec = DMIUtil.MISSING_DOUBLE;
protected double _amt_jan = DMIUtil.MISSING_DOUBLE;
protected double _amt_feb = DMIUtil.MISSING_DOUBLE;
protected double _amt_mar = DMIUtil.MISSING_DOUBLE;
protected double _amt_apr = DMIUtil.MISSING_DOUBLE;
protected double _amt_may = DMIUtil.MISSING_DOUBLE;
protected double _amt_jun = DMIUtil.MISSING_DOUBLE;
protected double _amt_jul = DMIUtil.MISSING_DOUBLE;
protected double _amt_aug = DMIUtil.MISSING_DOUBLE;
protected double _amt_sep = DMIUtil.MISSING_DOUBLE;
protected double _amt_oct = DMIUtil.MISSING_DOUBLE;
protected double _ann_amt = DMIUtil.MISSING_DOUBLE;
protected String _unit = DMIUtil.MISSING_STRING;
protected String _func = DMIUtil.MISSING_STRING;
protected int _div = DMIUtil.MISSING_INT;
protected int _wd = DMIUtil.MISSING_INT;
protected int _id = DMIUtil.MISSING_INT;
protected Date _modified = DMIUtil.MISSING_DATE;
protected int _user_num = DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_AnnualAmt() {
	super();
}

/**
Compares against another annual amt object for sorting with 
java.util.Collections.
*/
public int compareTo(Object o) {
	HydroBase_AnnualAmt a = (HydroBase_AnnualAmt)o;

	if (DMIUtil.isMissing(_irr_year) && DMIUtil.isMissing(a._irr_year)) {
		return 0;
	}
	else if (DMIUtil.isMissing(_irr_year)) {
		return -1;
	}
	else if (DMIUtil.isMissing(a._irr_year)) {
		return 1;
	}

	if (_irr_year < a._irr_year) {
		return -1;
	}
	else if (_irr_year == a._irr_year) {
		//
	}
	else {
		return 1;
	}

	if (_quality == null && a._quality == null) {
		return 0;
	}
	else if (_quality == null) {
		// want non-Infreq ones to sort after Infreq ones in the 
		// structure summary report
		return 1;
	}
	else if (a._quality == null) {
		return -1;
	}
	else {
		return _quality.compareTo(a._quality);
	}
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_quality = null;
	_fdu = null;
	_ldu = null;
	_maxq_date = null;
	_unit = null;
	_func = null;
	_modified = null;
	super.finalize();
}

/**
Returns the amount for a certain calendar month (1 - 12)
@return the amount for a certain calendar month (1 - 12)
@exception Exception if an invalid month was supplied.
*/
public double getAmt(int month) 
throws Exception {
	if (month < 1 || month > 12) {
		throw new Exception ("Invalid month: " + month + " (should be "
			+ "between 1 and 12, inclusive)");
	}
	switch (month) {
		case 1:	return getAmt_jan();
		case 2:	return getAmt_feb();
		case 3:	return getAmt_mar();
		case 4:	return getAmt_apr();
		case 5:	return getAmt_may();
		case 6:	return getAmt_jun();	
		case 7:	return getAmt_jul();
		case 8:	return getAmt_aug();
		case 9:	return getAmt_sep();
		case 10:return getAmt_oct();
		case 11:return getAmt_nov();
		case 12:return getAmt_dec();
	}
	// this case will never be reached, but the compile complains
	// if there is no return statement here.
	return -1;
}

/**
Returns _amt_apr
@return _amt_apr
*/
public double getAmt_apr() {
	return _amt_apr;
}

/**
Returns _amt_aug
@return _amt_aug
*/
public double getAmt_aug() {
	return _amt_aug;
}

/**
Returns _amt_dec
@return _amt_dec
*/
public double getAmt_dec() {
	return _amt_dec;
}

/**
Returns _amt_feb
@return _amt_feb
*/
public double getAmt_feb() {
	return _amt_feb;
}

/**
Returns _amt_jan
@return _amt_jan
*/
public double getAmt_jan() {
	return _amt_jan;
}

/**
Returns _amt_jul
@return _amt_jul
*/
public double getAmt_jul() {
	return _amt_jul;
}

/**
Returns _amt_jun
@return _amt_jun
*/
public double getAmt_jun() {
	return _amt_jun;
}

/**
Returns _amt_mar
@return _amt_mar
*/
public double getAmt_mar() {
	return _amt_mar;
}

/**
Returns _amt_may
@return _amt_may
*/
public double getAmt_may() {
	return _amt_may;
}

/**
Returns _amt_nov
@return _amt_nov
*/
public double getAmt_nov() {
	return _amt_nov;
}

/**
Returns _amt_oct
@return _amt_oct
*/
public double getAmt_oct() {
	return _amt_oct;
}

/**
Returns _amt_sep
@return _amt_sep
*/
public double getAmt_sep() {
	return _amt_sep;
}

/**
Returns _ann_amt
@return _ann_amt
*/
public double getAnn_amt() {
	return _ann_amt;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
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
Returns _func
@return _func
*/
public String getFunc() {
	return _func;
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
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
Returns _maxq
@return _maxq
*/
public double getMaxq() {
	return _maxq;
}

/**
Returns _maxq_date
@return _maxq_date
*/
public Date getMaxq_date() {
	return _maxq_date;
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
Returns _nobs
@return _nobs
*/
public int getNobs() {
	return _nobs;
}

/**
Returns _nus
@return _nus
*/
public int getNus() {
	return _nus;
}

/**
Returns _quality
@return _quality
*/
public String getQuality() {
	return _quality;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _unit
@return _unit
*/
public String getUnit() {
	return _unit;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Sets the amount for the given calendar month.  This can be used instead of the
setAmt_MMM() methods.  An exception is thrown if the number passed in 
as the calendar month to set is less than 1 or greater than 12.
@param calendarMonth the calendar month (1: jan, 12: dec) for which to set 
the value
@param value the value to set the calendar month to.
@exception Exception if an invalid calendarMonth was provided.
*/
public void setAmountForIrrigationMonth(int calendarMonth, int value) 
throws Exception {
	switch (calendarMonth) {
		case 1: _amt_jan = value;	break;
		case 2: _amt_feb = value;	break;
		case 3: _amt_mar = value;	break;
		case 4: _amt_apr = value;	break;
		case 5: _amt_may = value;	break;
		case 6: _amt_jun = value;	break;
		case 7: _amt_jul = value;	break;
		case 8: _amt_aug = value;	break;
		case 9: _amt_sep = value;	break;
		case 10:_amt_oct = value;	break;
		case 11:_amt_nov = value;	break;
		case 12:_amt_dec = value;	break;
		default:
			throw new Exception("Invalid calendar month (" 
				+ calendarMonth + ") in "
				+ "call to setAmountForIrrigationMonth.");
	}
}

/**
Sets _amt_apr
@param amt_apr value to put into _amt_apr
*/
public void setAmt_apr(double amt_apr) {
	_amt_apr = amt_apr;
}

/**
Sets _amt_aug
@param amt_aug value to put into _amt_aug
*/
public void setAmt_aug(double amt_aug) {
	_amt_aug = amt_aug;
}

/**
Sets _amt_dec
@param amt_dec value to put into _amt_dec
*/
public void setAmt_dec(double amt_dec) {
	_amt_dec = amt_dec;
}

/**
Sets _amt_feb
@param amt_feb value to put into _amt_feb
*/
public void setAmt_feb(double amt_feb) {
	_amt_feb = amt_feb;
}

/**
Sets _amt_jan
@param amt_jan value to put into _amt_jan
*/
public void setAmt_jan(double amt_jan) {
	_amt_jan = amt_jan;
}

/**
Sets _amt_jul
@param amt_jul value to put into _amt_jul
*/
public void setAmt_jul(double amt_jul) {
	_amt_jul = amt_jul;
}

/**
Sets _amt_jun
@param amt_jun value to put into _amt_jun
*/
public void setAmt_jun(double amt_jun) {
	_amt_jun = amt_jun;
}

/**
Sets _amt_mar
@param amt_mar value to put into _amt_mar
*/
public void setAmt_mar(double amt_mar) {
	_amt_mar = amt_mar;
}

/**
Sets _amt_may
@param amt_may value to put into _amt_may
*/
public void setAmt_may(double amt_may) {
	_amt_may = amt_may;
}

/**
Sets _amt_nov
@param amt_nov value to put into _amt_nov
*/
public void setAmt_nov(double amt_nov) {
	_amt_nov = amt_nov;
}

/**
Sets _amt_oct
@param amt_oct value to put into _amt_oct
*/
public void setAmt_oct(double amt_oct) {
	_amt_oct = amt_oct;
}

/**
Sets _amt_sep
@param amt_sep value to put into _amt_sep
*/
public void setAmt_sep(double amt_sep) {
	_amt_sep = amt_sep;
}

/**
Sets _ann_amt
@param ann_amt value to put into _ann_amt
*/
public void setAnn_amt(double ann_amt) {
	_ann_amt = ann_amt;
}

/**
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
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
Sets _func
@param func value to put into _func
*/
public void setFunc(String func) {
	_func = func;
}

/**
Sets _id
@param id value to put into _id
*/
public void setID(int id) {
	_id = id;
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
Sets _maxq
@param maxq value to put into _maxq
*/
public void setMaxq(double maxq) {
	_maxq = maxq;
}

/**
Sets _maxq_date
@param maxq_date value to put into _maxq_date
*/
public void setMaxq_date(Date maxq_date) {
	_maxq_date = maxq_date;
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
Sets _nobs
@param nobs value to put into _nobs
*/
public void setNobs(int nobs) {
	_nobs = nobs;
}

/**
Sets _nus
@param nus value to put into _nus
*/
public void setNus(int nus) {
	_nus = nus;
}

/**
Sets _quality
@param quality value to put into _quality
*/
public void setQuality(String quality) {
	_quality = quality;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _unit
@param unit value to put into _unit
*/
public void setUnit(String unit) {
	_unit = unit;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_AnnualAmt {"		+ "\n" + 
		"Meas_num:      " + _meas_num + "\n" + 
		"Irr_year:      " + _irr_year + "\n" + 
		"Structure_num: " + _structure_num + "\n" + 
		"Quality:       " + _quality + "\n" + 
		"Fdu:           " + _fdu + "\n" + 
		"Ldu:           " + _ldu + "\n" + 
		"Dwc:           " + _dwc + "\n" + 
		"Maxq:          " + _maxq + "\n" + 
		"Maxq_date:     " + _maxq_date + "\n" + 
		"Nobs:          " + _nobs + "\n" + 
		"Nus:           " + _nus + "\n" + 
		"Amt_nov:       " + _amt_nov + "\n" + 
		"Amt_dec:       " + _amt_dec + "\n" + 
		"Amt_jan:       " + _amt_jan + "\n" + 
		"Amt_feb:       " + _amt_feb + "\n" + 
		"Amt_mar:       " + _amt_mar + "\n" + 
		"Amt_apr:       " + _amt_apr + "\n" + 
		"Amt_may:       " + _amt_may + "\n" + 
		"Amt_jun:       " + _amt_jun + "\n" + 
		"Amt_jul:       " + _amt_jul + "\n" + 
		"Amt_aug:       " + _amt_aug + "\n" + 
		"Amt_sep:       " + _amt_sep + "\n" + 
		"Amt_oct:       " + _amt_oct + "\n" + 
		"Ann_amt:       " + _ann_amt + "\n" + 
		"Unit:          " + _unit + "\n" + 
		"Func:          " + _func + "\n" + 
		"Div:           " + _div + "\n" + 
		"WD:            " + _wd + "\n" + 
		"ID:            " + _id + "\n" + 
		"Modified:      " + _modified + "\n" + 
		"User_num:      " + _user_num + "\n}\n" + 
		super.toString();
}

}
