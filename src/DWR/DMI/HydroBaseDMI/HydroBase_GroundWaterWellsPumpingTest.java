// ----------------------------------------------------------------------------
// HydroBase_GroundWaterWellsPumpingTest.java - Class to hold data from 
//	the HydroBase pump_test table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-10	J. Thomas Sapienza, RTi	Initial version from HBPumpTest.
// 2003-02-14	JTS, RTi		Added getLocation method.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2004-06-22	JTS, RTi		Now uses HydroBase_Util for
//					formatLegalLocation().
// 2005-04-28	JTS, RTi		Added all data members to finalize().
// 2005-06-02	JTS, RTi		Now extends 
//					HydroBase_GroundWaterWellsView
// 2005-06-29	JTS, RTi		Renamed from HydroBase_PumpTest.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase pump_test table.
*/
public class HydroBase_GroundWaterWellsPumpingTest 
extends HydroBase_GroundWaterWellsView {

protected int _structure_num = 	DMIUtil.MISSING_INT;
protected double _tswl = 	DMIUtil.MISSING_DOUBLE;
protected double _tfwl = 	DMIUtil.MISSING_DOUBLE;
protected double _testq = 	DMIUtil.MISSING_DOUBLE;
protected double _testtime = 	DMIUtil.MISSING_DOUBLE;
protected int _trans = 		DMIUtil.MISSING_INT;
protected String _k = 		DMIUtil.MISSING_STRING;
protected String _storativity = DMIUtil.MISSING_STRING;
protected String _leakance = 	DMIUtil.MISSING_STRING;
protected int _toptestint = 	DMIUtil.MISSING_INT;
protected int _basetestint = 	DMIUtil.MISSING_INT;
protected double _drawdown = 	DMIUtil.MISSING_DOUBLE;
protected Date _testdate = 	DMIUtil.MISSING_DATE;
protected String _ptsource = 	DMIUtil.MISSING_STRING;
protected String _pttype = 	DMIUtil.MISSING_STRING;
protected int _ptmon = 		DMIUtil.MISSING_INT;
protected int _ptobs = 		DMIUtil.MISSING_INT;
protected int _ptmultiple = 	DMIUtil.MISSING_INT;
protected double _sp_cap = 	DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_GroundWaterWellsPumpingTest() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_k = null;
	_storativity = null;
	_leakance = null;
	_testdate = null;
	_ptsource = null;
	_pttype = null;
	_pm = null;
	_rdir = null;
	_seca = null;
	_q160 = null;
	_q40 = null;
	_q10 = null;

	super.finalize();
}

/**
Returns _basetestint
@return _basetestint
*/
public int getBasetestint() {
	return _basetestint;
}

/**
Returns _drawdown
@return _drawdown
*/
public double getDrawdown() {
	return _drawdown;
}

/**
Returns _k
@return _k
*/
public String getK() {
	return _k;
}

/**
Returns _leakance
@return _leakance
*/
public String getLeakance() {
	return _leakance;
}

/**
Returns the location (an amalgation of pm, ts, tdir, rng, rdir, sec, seca,
q160, q40 and q10).
@return the location
*/
public String getLocation() 
throws Exception {
	return HydroBase_Util.formatLegalLocation(_pm, _ts, _tdir, _rng, 
		_rdir, _sec, _seca, _q160, _q40, _q10);
}
	

/**
Returns _ptmon
@return _ptmon
*/
public int getPtmon() {
	return _ptmon;
}

/**
Returns _ptmultiple
@return _ptmultiple
*/
public int getPtmultiple() {
	return _ptmultiple;
}

/**
Returns _ptobs
@return _ptobs
*/
public int getPtobs() {
	return _ptobs;
}

/**
Returns _ptsource
@return _ptsource
*/
public String getPtsource() {
	return _ptsource;
}

/**
Returns _pttype
@return _pttype
*/
public String getPttype() {
	return _pttype;
}

/**
Returns _sp_cap
@return _sp_cap
*/
public double getSp_cap() {
	return _sp_cap;
}

/**
Returns _storativity
@return _storativity
*/
public String getStorativity() {
	return _storativity;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _testdate
@return _testdate
*/
public Date getTestdate() {
	return _testdate;
}

/**
Returns _testq
@return _testq
*/
public double getTestq() {
	return _testq;
}

/**
Returns _testtime
@return _testtime
*/
public double getTesttime() {
	return _testtime;
}

/**
Returns _toptestint
@return _toptestint
*/
public int getToptestint() {
	return _toptestint;
}

/**
Returns _trans
@return _trans
*/
public int getTrans() {
	return _trans;
}

/**
Returns _tfwl
@return _tfwl
*/
public double getTfwl() {
	return _tfwl;
}

/**
Returns _tswl
@return _tswl
*/
public double getTswl() {
	return _tswl;
}

/**
Sets _basetestint
@param basetestint value to be put into _basetestint
*/
public void setBasetestint(int basetestint) {
	_basetestint = basetestint;
}

/**
Sets _drawdown
@param drawdown value to be put into _drawdown
*/
public void setDrawdown(double drawdown) {
	_drawdown = drawdown;
}

/**
Sets _k
@param k value to be put into _k
*/
public void setK(String k) {
	_k = k;
}

/**
Sets _leakance
@param leakance value to be put into _leakance
*/
public void setLeakance(String leakance) {
	_leakance = leakance;
}

/**
Sets _ptmon
@param ptmon value to be put into _ptmon
*/
public void setPtmon(int ptmon) {
	_ptmon = ptmon;
}

/**
Sets _ptmultiple
@param ptmultiple value to be put into _ptmultiple
*/
public void setPtmultiple(int ptmultiple) {
	_ptmultiple = ptmultiple;
}

/**
Sets _ptobs
@param ptobs value to be put into _ptobs
*/
public void setPtobs(int ptobs) {
	_ptobs = ptobs;
}

/**
Sets _ptsource
@param ptsource value to be put into _ptsource
*/
public void setPtsource(String ptsource) {
	_ptsource = ptsource;
}

/**
Sets _pttype
@param pttype value to be put into _pttype
*/
public void setPttype(String pttype) {
	_pttype = pttype;
}

/**
Sets _sp_cap
@param sp_cap value to be put into _sp_cap
*/
public void setSp_cap(double sp_cap) {
	_sp_cap = sp_cap;
}

/**
Sets _storativity
@param storativity value to be put into _storativity
*/
public void setStorativity(String storativity) {
	_storativity = storativity;
}

/**
Sets _structure_num
@param storativity value to be put into _structure_num
*/
public void setStructure_num(int storativity) {
	_structure_num = storativity;
}

/**
Sets _testdate
@param testdate value to be put into _testdate
*/
public void setTestdate(Date testdate) {
	_testdate = testdate;
}

/**
Sets _testq
@param testq value to be put into _testq
*/
public void setTestq(double testq) {
	_testq = testq;
}

/**
Sets _testtime
@param testtime value to be put into _testtime
*/
public void setTesttime(double testtime) {
	_testtime = testtime;
}

/**
Sets _toptestint
@param toptestint value to be put into _toptestint
*/
public void setToptestint(int toptestint) {
	_toptestint = toptestint;
}

/**
Sets _trans
@param trans value to be put into _trans
*/
public void setTrans(int trans) {
	_trans = trans;
}

/**
Sets _tfwl
@param tfwl value to be put into _tfwl
*/
public void setTfwl(double tfwl) {
	_tfwl = tfwl;
}

/**
Sets _tswl
@param tswl value to be put into _tswl
*/
public void setTswl(double tswl) {
	_tswl = tswl;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_GroundWaterWellsPumpingTest {"	+ "\n" + 
		"Structure_num: " + _structure_num + "\n" + 
		"Tswl:          " + _tswl + "\n" + 
		"Tfwl:          " + _tfwl + "\n" + 
		"Testq:         " + _testq + "\n" + 
		"Testtime:      " + _testtime + "\n" + 
		"Trans:         " + _trans + "\n" + 
		"K:             " + _k + "\n" + 
		"Storativity:   " + _storativity + "\n" + 
		"Leakance:      " + _leakance + "\n" + 
		"Toptestint:    " + _toptestint + "\n" + 
		"Basetestint:   " + _basetestint + "\n" + 
		"Drawdown:      " + _drawdown + "\n" + 
		"Testdate:      " + _testdate + "\n" + 
		"Ptsource:      " + _ptsource + "\n" + 
		"Pttype:        " + _pttype + "\n" + 
		"Ptmon:         " + _ptmon + "\n" + 
		"Ptobs:         " + _ptobs + "\n" + 
		"Ptmultiple:    " + _ptmultiple + "\n" + 
		"Sp_cap:        " + _sp_cap + "\n";
}

}
