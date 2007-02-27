// ----------------------------------------------------------------------------
// HydroBase_GroundWaterWellsDrillersKSum.java - Class to hold data from the 
//	HydroBase drillers_k_sum table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-07	J. Thomas Sapienza, RTi	Initial version from HBDrillerLog.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2005-04-27	JTS, RTi		Added all data members to finalize().
// 2005-06-29	JTS, RTi		* Changed structure_num to well_num.
//					* Now extends GroundWaterWellsView.
// 2005-06-29	JTS, RTi		Renamed from HydroBase_DrillersKSum
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase drillers_k_sum table.
*/
public class HydroBase_GroundWaterWellsDrillersKSum 
extends HydroBase_GroundWaterWellsView {

protected int _well_num =	DMIUtil.MISSING_INT;
protected int _dunctop = 	DMIUtil.MISSING_INT;
protected int _duncbase = 	DMIUtil.MISSING_INT;
protected int _dunctopbasek = 	DMIUtil.MISSING_INT;
protected int _dcontop = 	DMIUtil.MISSING_INT;
protected int _d500 = 		DMIUtil.MISSING_INT;
protected int _dcon500k = 	DMIUtil.MISSING_INT;
protected int __2d500 = 	DMIUtil.MISSING_INT;
protected int _d1000 = 		DMIUtil.MISSING_INT;
protected int _d500to1000k = 	DMIUtil.MISSING_INT;
protected int __2d1000 = 	DMIUtil.MISSING_INT;
protected int __1500 = 		DMIUtil.MISSING_INT;
protected int _d1000_1500k = 	DMIUtil.MISSING_INT;
protected int __2d1500 = 	DMIUtil.MISSING_INT;
protected int _d2000 = 		DMIUtil.MISSING_INT;
protected int _d1500_2000k = 	DMIUtil.MISSING_INT;
protected int __2d2000 = 	DMIUtil.MISSING_INT;
protected int _d2500 = 		DMIUtil.MISSING_INT;
protected int _d2000_2500 = 	DMIUtil.MISSING_INT;
protected int __2d2500 = 	DMIUtil.MISSING_INT;
protected int _d3000 = 		DMIUtil.MISSING_INT;
protected int __2d3000 =	DMIUtil.MISSING_INT;
protected int _d2500_3000 = 	DMIUtil.MISSING_INT;
protected int _d3500 = 		DMIUtil.MISSING_INT;
protected int _d3000_3500k = 	DMIUtil.MISSING_INT;
protected int __2d3500 = 	DMIUtil.MISSING_INT;
protected int _d4000 = 		DMIUtil.MISSING_INT;
protected int _d3500_4000k = 	DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_GroundWaterWellsDrillersKSum() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	super.finalize();
}

/**
Returns _well_num
@return _well_num
*/
public int getWell_num() {
	return _well_num;
}

/**
Returns _dunctop
@return _dunctop
*/
public int getDunctop () {
	return _dunctop;
}

/**
Returns _duncbase
@return _duncbase
*/
public int getDuncbase() {
	return _duncbase;
}

/**
Returns _dunctopbasek
@return _dunctopbasek
*/
public int getDunctopbasek() {
	return _dunctopbasek;
}

/**
Returns _dcontop
@return _dcontop
*/
public int getDcontop() {
	return _dcontop;
}

/**
Returns _d500
@return _d500
*/
public int getD500() {
	return _d500;
}

/**
Returns _dcon500k
@return _dcon500k
*/
public int getDcon500k() {
	return _dcon500k;
}

/**
Returns __2d500
@return __2d500
*/
public int get_2d500() {
	return __2d500;
}

/**
Returns _d1000
@return _d1000
*/
public int getD1000() {
	return _d1000;
}

/**
Returns _d500to1000k
@return _d500to1000k
*/
public int getD500to1000k() {
	return _d500to1000k;
}

/**
Returns __2d1000
@return __2d1000
*/
public int get_2d1000() {
	return __2d1000;
}

/**
Returns __1500
@return __1500
*/
public int get_1500() {
	return __1500;
}

/**
Returns _d1000_1500k
@return _d1000_1500k
*/
public int getD1000_1500k() {
	return _d1000_1500k;
}

/**
Returns __2d1500
@return __2d1500
*/
public int get_2d1500() {
	return __2d1500;
}

/**
Returns _d2000
@return _d2000
*/
public int getD2000() {
	return _d2000;
}

/**
Returns _d1500_2000k
@return _d1500_2000k
*/
public int getD1500_2000k() {
	return _d1500_2000k;
}

/**
Returns __2d2000
@return __2d2000
*/
public int get_2d2000() {
	return __2d2000;
}

/**
Returns _d2500
@return _d2500
*/
public int getD2500() {
	return _d2500;
}

/**
Returns _d2000_2500
@return _d2000_2500
*/
public int getD2000_2500() {
	return _d2000_2500;
}

/**
Returns __2d2500
@return __2d2500
*/
public int get_2d2500() {
	return __2d2500;
}

/**
Returns _d3000
@return _d3000
*/
public int getD3000() {
	return _d3000;
}

/**
Returns __2d3000
@return __2d3000
*/
public int get_2d3000() {
	return __2d3000;
}

/**
Returns _d2500_3000
@return _d2500_3000
*/
public int getD2500_3000() {
	return _d2500_3000;
}

/**
Returns _d3500
@return _d3500
*/
public int getD3500() {
	return _d3500;
}

/**
Returns _d3000_3500k
@return _d3000_3500k
*/
public int getD3000_3500k() {
	return _d3000_3500k;
}

/**
Returns __2d3500
@return __2d3500
*/
public int get_2d3500() {
	return __2d3500;
}

/**
Returns _d4000
@return _d4000
*/
public int getD4000() {
	return _d4000;
}

/**
Returns _d3500_4000k
@return _d3500_4000k
*/
public int getD3500_4000k() {
	return _d3500_4000k;
}

/**
Sets _well_num
@param well_num value to put into _well_num
*/
public void setWell_num(int well_num) {
	_well_num = well_num;
}

/**
Sets _dunctop
@param dunctop value to put into _dunctop
*/
public void setDunctop (int dunctop) {
	_dunctop = dunctop;
}

/**
Sets _duncbase
@param duncbase value to put into _duncbase
*/
public void setDuncbase(int duncbase) {
	_duncbase = duncbase;
}

/**
Sets _dunctopbasek
@param dunctopbasek value to put into _dunctopbasek
*/
public void setDunctopbasek(int dunctopbasek) {
	_dunctopbasek = dunctopbasek;
}

/**
Sets _dcontop
@param dcontop value to put into _dcontop
*/
public void setDcontop(int dcontop) {
	_dcontop = dcontop;
}

/**
Sets _d500
@param d500 value to put into _d500
*/
public void setD500(int d500) {
	_d500 = d500;
}

/**
Sets _dcon500k
@param dcon500k value to put into _dcon500k
*/
public void setDcon500k(int dcon500k) {
	_dcon500k = dcon500k;
}

/**
Sets __2d500
@param _2d500 value to put into __2d500
*/
public void set_2d500(int _2d500) {
	__2d500 = _2d500;
}

/**
Sets _d1000
@param d1000 value to put into _d1000
*/
public void setD1000(int d1000) {
	_d1000 = d1000;
}

/**
Sets _d500to1000k
@param d500to1000k value to put into _d500to1000k
*/
public void setD500to1000k(int d500to1000k) {
	_d500to1000k = d500to1000k;
}

/**
Sets __2d1000
@param _2d1000 value to put into __2d1000
*/
public void set_2d1000(int _2d1000) {
	__2d1000 = _2d1000;
}

/**
Sets __1500
@param _1500 value to put into __1500
*/
public void set_1500(int _1500) {
	__1500 = _1500;
}

/**
Sets _d1000_1500k
@param d1000_1500k value to put into _d1000_1500k
*/
public void setD1000_1500k(int d1000_1500k) {
	_d1000_1500k = d1000_1500k;
}

/**
Sets __2d1500
@param _2d1500 value to put into __2d1500
*/
public void set_2d1500(int _2d1500) {
	__2d1500 = _2d1500;
}

/**
Sets _d2000
@param d2000 value to put into _d2000
*/
public void setD2000(int d2000) {
	_d2000 = d2000;
}

/**
Sets _d1500_2000k
@param d1500_2000k value to put into _d1500_2000k
*/
public void setD1500_2000k(int d1500_2000k) {
	_d1500_2000k = d1500_2000k;
}

/**
Sets __2d2000
@param _2d2000 value to put into __2d2000
*/
public void set_2d2000(int _2d2000) {
	__2d2000 = _2d2000;
}

/**
Sets _d2500
@param d2500 value to put into _d2500
*/
public void setD2500(int d2500) {
	_d2500 = d2500;
}

/**
Sets _d2000_2500
@param d2000_2500 value to put into _d2000_2500
*/
public void setD2000_2500(int d2000_2500) {
	_d2000_2500 = d2000_2500;
}

/**
Sets __2d2500
@param _2d2500 value to put into __2d2500
*/
public void set_2d2500(int _2d2500) {
	__2d2500 = _2d2500;
}

/**
Sets _d3000
@param d3000 value to put into _d3000
*/
public void setD3000(int d3000) {
	_d3000 = d3000;
}

/**
Sets __2d3000
@param _2d3000 value to put into __2d3000
*/
public void set_2d3000(int _2d3000) {
	__2d3000 = _2d3000;
}

/**
Sets _d2500_3000
@param d2500_3000 value to put into _d2500_3000
*/
public void setD2500_3000(int d2500_3000) {
	_d2500_3000 = d2500_3000;
}

/**
Sets _d3500
@param d3500 value to put into _d3500
*/
public void setD3500(int d3500) {
	_d3500 = d3500;
}

/**
Sets _d3000_3500k
@param d3000_3500k value to put into _d3000_3500k
*/
public void setD3000_3500k(int d3000_3500k) {
	_d3000_3500k = d3000_3500k;
}

/**
Sets __2d3500
@param _2d3500 value to put into __2d3500
*/
public void set_2d3500(int _2d3500) {
	__2d3500 = _2d3500;
}

/**
Sets _d4000
@param d4000 value to put into _d4000
*/
public void setD4000(int d4000) {
	_d4000 = d4000;
}

/**
Sets _d3500_4000k
@param d3500_4000k value to put into _d3500_4000k
*/
public void setD3500_4000k(int d3500_4000k) {
	_d3500_4000k = d3500_4000k;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_GroundWaterWellsDrillersKSum {"		+ "\n" +
		"Well_num: " + _well_num + "\n" + 
		"Dunctop:       " + _dunctop  + "\n" + 
		"Duncbase:      " + _duncbase  + "\n" + 
		"Dunctopbasek:  " + _dunctopbasek  + "\n" + 
		"Dcontop:       " + _dcontop  + "\n" + 
		"D500:          " + _d500  + "\n" + 
		"Dcon500k:      " + _dcon500k  + "\n" + 
		"_2d500:        " + __2d500  + "\n" + 
		"D1000:         " + _d1000  + "\n" + 
		"D500to1000k:   " + _d500to1000k  + "\n" + 
		"_2d1000:       " + __2d1000  + "\n" + 
		"_1500:         " + __1500  + "\n" + 
		"D1000_1500k:   " + _d1000_1500k  + "\n" + 
		"_2d1500:       " + __2d1500  + "\n" + 
		"D2000:         " + _d2000  + "\n" + 
		"D1500_2000k:   " + _d1500_2000k  + "\n" + 
		"_2d2000:       " + __2d2000  + "\n" + 
		"D2500:         " + _d2500  + "\n" + 
		"D2000_2500:    " + _d2000_2500  + "\n" + 
		"_2d2500:       " + __2d2500  + "\n" + 
		"D3000:         " + _d3000  + "\n" + 
		"_2d3000:       " + __2d3000  + "\n" + 
		"D2500_3000:    " + _d2500_3000  + "\n" + 
		"D3500:         " + _d3500  + "\n" + 
		"D3000_3500k:   " + _d3000_3500k  + "\n" + 
		"_2d3500:       " + __2d3500  + "\n" + 
		"D4000:         " + _d4000  + "\n" + 
		"D3500_4000k:   " + _d3500_4000k  + "\n}\n";
}

}
