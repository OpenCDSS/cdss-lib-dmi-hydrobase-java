// HydroBase_Wells - Class to hold data from the HydroBase wells table.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

CDSS HydroBase Database Java Library is free software:  you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CDSS HydroBase Database Java Library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CDSS HydroBase Database Java Library.  If not, see <https://www.gnu.org/licenses/>.

NoticeEnd */

// ----------------------------------------------------------------------------
// HydroBase_Wells.java - Class to hold data from the HydroBase wells table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-13	J. Thomas Sapienza, RTi	Initial version from HBWell.
// 2003-02-17	JTS, RTi		Added setPermitID methods and _permit_id
//					data member.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2004-09-22	SAM, RTi		Add well_to_parcel _cal_year and
//					_parcel_id, for current database.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

// TODO SAM 2004-09-22 This class does not follow the standard of having
// a base class and derived classes for the joined objects.

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase wells table and related parcel tables.
TODO smalers 2020-08-24 this is a bit confusing because it does not follow the normal
convention like HydroBase_Wells_WellToParcel, etc., probably because it would be a really long name?
*/
public class HydroBase_Wells extends DMIDataObject
{

protected int _well_id = DMIUtil.MISSING_INT;
protected int _div = DMIUtil.MISSING_INT;
protected int _wd = DMIUtil.MISSING_INT;
protected int _id = DMIUtil.MISSING_INT;
protected String _receipt = DMIUtil.MISSING_STRING;
protected int _permitno = DMIUtil.MISSING_INT;
protected String _permitsuf = DMIUtil.MISSING_STRING;
protected String _permitrpl = DMIUtil.MISSING_STRING;
protected String _well_name = DMIUtil.MISSING_STRING;
protected float _yield = DMIUtil.MISSING_FLOAT;
protected float _yield_apex = DMIUtil.MISSING_FLOAT;
protected Date _perm_date = DMIUtil.MISSING_DATE;
protected Date _appr_date = DMIUtil.MISSING_DATE;
protected int _tperf = DMIUtil.MISSING_INT;
protected int _bperf = DMIUtil.MISSING_INT;
protected int _depth = DMIUtil.MISSING_INT;
protected String _aquifer1 = DMIUtil.MISSING_STRING;
protected int _wd_id = DMIUtil.MISSING_INT;
protected int _flag = DMIUtil.MISSING_INT;
protected String _use1 = DMIUtil.MISSING_STRING;
protected String _use2 = DMIUtil.MISSING_STRING;
protected String _use3 = DMIUtil.MISSING_STRING;
protected int _ditches_served = DMIUtil.MISSING_INT;

// well_to_layer
protected int _layer = DMIUtil.MISSING_INT;
protected float _layer_per = DMIUtil.MISSING_INT;
protected int _est_id = DMIUtil.MISSING_INT;

// well_to_parcel
protected int _parcel = DMIUtil.MISSING_INT;
protected int _class = DMIUtil.MISSING_INT;
protected float _distance = DMIUtil.MISSING_FLOAT; // Units of FT, from Nils Babel, 2010-01-25
protected float _prorated_yield = DMIUtil.MISSING_FLOAT;
protected double _percent_yield = DMIUtil.MISSING_DOUBLE;
protected int _cal_year = DMIUtil.MISSING_INT;
protected int _parcel_id = DMIUtil.MISSING_INT;
protected int _parcel_id3 = DMIUtil.MISSING_INT; // First 3 digits of _parcel_id, which correspond to Div and WD.

// well_to_structure
protected String _ditch_id = DMIUtil.MISSING_STRING;
protected int _structure_num = DMIUtil.MISSING_INT;
protected float _ditch_cov = DMIUtil.MISSING_FLOAT;
protected int _str_wd = DMIUtil.MISSING_INT;
protected int _str_id = DMIUtil.MISSING_INT;

protected String _permit_id = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_Wells() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_well_name = null;
	_use3 = null;
	_use2 = null;
	_use1 = null;
	_receipt = null;
	_permitsuf = null;
	_permitrpl = null;
	_perm_date = null;
	_aquifer1 = null;
	_appr_date = null;
	
	super.finalize();
}

/**
Returns _appr_date
@return _appr_date
*/
public Date getAppr_date() {
	return _appr_date;
}

/**
Returns _aquifer1
@return _aquifer1
*/
public String getAquifer1() {
	return _aquifer1;
}

/**
Returns _bperf
@return _bperf
*/
public int getBperf() {
	return _bperf;
}

/**
Returns _cal_year
@return _cal_year
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _class.  This method's name does not keep with the convention because
"getClass" is a core Java method that cannot be overridden.
@return _class
*/
public int get_Class() {
	return _class;
}

/**
Returns _depth
@return _depth
*/
public int getDepth() {
	return _depth;
}

/**
Returns _distance
@return _distance
*/
public float getDistance() {
	return _distance;
}

/**
Returns _ditch_cov
@return _ditch_cov
*/
public float getDitch_cov() {
	return _ditch_cov;
}

/**
Returns _ditch_id
@return _ditch_id
*/
public String getDitch_id() {
	return _ditch_id;
}

/**
Returns _ditches_served
@return _ditches_served
*/
public int getDitches_served() {
	return _ditches_served;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _est_id
@return _est_id
*/
public int getEst_id() {
	return _est_id;
}

/**
Returns _flag
@return _flag
*/
public int getFlag() {
	return _flag;
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
}

/**
Returns _layer
@return _layer
*/
public int getLayer() {
	return _layer;
}

/**
Returns _layer_per
@return _layer_per
*/
public float getLayer_per() {
	return _layer_per;
}

/**
Returns _parcel (the old-style geoloc_num?)
@return _parcel (the old-style geoloc_num?)
*/
public int getParcel() {
	return _parcel;
}

/**
Returns _parcel_id (same as parcel_use_ts._parcel_num)
@return _parcel_id
*/
public int getParcel_id() {
	return _parcel_id;
}

/**
Returns _parcel_id3
@return _parcel_id3
*/
public int getParcel_id3() {
	return _parcel_id3;
}

/**
Returns _percent_yield
@return _percent_yield
*/
public double getPercent_yield() {
	return _percent_yield;
}

/**
Returns _perm_date
@return _perm_date
*/
public Date getPerm_date() {
	return _perm_date;
}

/**
Returns _permit_id
@return _permit_id
*/
public String getPermit_id() {
	return _permit_id;
}

/**
Returns _permitno
@return _permitno
*/
public int getPermitno() {
	return _permitno;
}

/**
Returns _permitrpl
@return _permitrpl
*/
public String getPermitrpl() {
	return _permitrpl;
}

/**
Returns _permitsuf
@return _permitsuf
*/
public String getPermitsuf() {
	return _permitsuf;
}

/**
Returns _prorated_yield
@return _prorated_yield
*/
public float getProrated_yield() {
	return _prorated_yield;
}

/**
Returns _receipt
@return _receipt
*/
public String getReceipt() {
	return _receipt;
}

/**
Returns _str_id
@return _str_id
*/
public int getStr_id() {
	return _str_id;
}

/**
Returns _str_wd
@return _str_wd
*/
public int getStr_wd() {
	return _str_wd;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _tperf
@return _tperf
*/
public int getTperf() {
	return _tperf;
}

/**
Returns _use1
@return _use1
*/
public String getUse1() {
	return _use1;
}

/**
Returns _use2
@return _use2
*/
public String getUse2() {
	return _use2;
}

/**
Returns _use3
@return _use3
*/
public String getUse3() {
	return _use3;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Returns _wd_id
@return _wd_id
*/
public int getWd_id() {
	return _wd_id;
}

/**
Returns _well_id
@return _well_id
*/
public int getWell_id() {
	return _well_id;
}

/**
Returns _well_name
@return _well_name
*/
public String getWell_name() {
	return _well_name;
}

/**
Returns _yield
@return _yield
*/
public float getYield() {
	return _yield;
}

/**
Returns _yield_apex
@return _yield_apex
*/
public float getYield_apex() {
	return _yield_apex;
}

/**
Sets _appr_date
@param appr_date value to put into _appr_date
*/
public void setAppr_date(Date appr_date) {
	_appr_date = appr_date;
}

/**
Sets _aquifer1
@param aquifer1 value to put into _aquifer1
*/
public void setAquifer1(String aquifer1) {
	_aquifer1 = aquifer1;
}

/**
Sets _bperf
@param bperf value to put into _bperf
*/
public void setBperf(int bperf) {
	_bperf = bperf;
}

/**
Sets _cal_year
@param cal_year value to put into _cal_year
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _class
@param cclass value to put into _class
*/
public void setClass(int cclass) {
	_class = cclass;
}

/**
Sets _depth
@param depth value to put into _depth
*/
public void setDepth(int depth) {
	_depth = depth;
}

/**
Sets _distance
@param distance value to put into _distance
*/
public void setDistance(float distance) {
	_distance = distance;
}

/**
Sets _ditch_cov
@param ditch_cov value to put into _ditch_cov
*/
public void setDitch_cov(float ditch_cov) {
	_ditch_cov = ditch_cov;
}

/**
Sets _ditch_id
@param ditch_id value to put into _ditch_id
*/
public void setDitch_id(String ditch_id) {
	_ditch_id = ditch_id;
}

/**
Sets _ditches_served
@param ditches_served value to put into _ditches_served
*/
public void setDitches_served(int ditches_served) {
	_ditches_served = ditches_served;
}

/**
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _est_id
@param est_id value to put into _est_id
*/
public void setEst_id(int est_id) {
	_est_id = est_id;
}

/**
Sets _flag
@param flag value to put into _flag
*/
public void setFlag(int flag) {
	_flag = flag;
}

/**
Sets _id
@param id value to put into _id
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _layer
@param layer value to put into _layer
*/
public void setLayer(int layer) {
	_layer = layer;
}

/**
Sets _layer_per
@param layer_per value to put into _layer_per
*/
public void setLayer_per(float layer_per) {
	_layer_per = layer_per;
}

/**
Sets _parcel (same as legacy geoloc_num?)
@param parcel value to put into _parcel
*/
public void setParcel(int parcel) {
	_parcel = parcel;
}

/**
Sets _parcel_id (same as parcel_use_ts.parcel_num)
@param parcel value to put into _parcel_id
*/
public void setParcel_id(int parcel_id) {
	_parcel_id = parcel_id;
}

/**
Sets _parcel_id3
@param value to put into _parcel_id3
*/
public void setParcel_id3(int parcel_id3) {
	_parcel_id3 = parcel_id3;
}

/**
Sets _percent_yield
@param percent_yield value to put into _percent_yield
*/
public void setPercent_yield(double percent_yield) {
	_percent_yield = percent_yield;
}

/**
Sets _perm_date
@param perm_date value to put into _perm_date
*/
public void setPerm_date(Date perm_date) {
	_perm_date = perm_date;
}

/**
Set full permit as concatenation of permit number, suffix and replacement
code.  
@param permitno permit number
@param permitsuf permit suffix
@param permitrpl permit replacement code
*/
public void setPermitID(int permitno, String permitsuf, String permitrpl) {
	setPermitno(permitno);
	setPermitsuf(permitsuf);
	setPermitrpl(permitrpl);

	setPermitID();
}

/**
Set full permit as concatenation of permit number, suffix and replacemdent code,
using data already set in the instance.  If all parts are missing, then an 
empty string is used for the permit id.
*/
public void setPermitID() {
	if (	_permitno == DMIUtil.MISSING_INT &&
		_permitrpl.equals(DMIUtil.MISSING_STRING) &&
		_permitsuf.equals(DMIUtil.MISSING_STRING)) {
		_permit_id = "";
	}
	else {
		_permit_id = _permitno + "_" + _permitsuf + "_" + _permitrpl;
	}
}

/**
Sets _permitno
@param permitno value to put into _permitno
*/
public void setPermitno(int permitno) {
	_permitno = permitno;
}

/**
Sets _permitrpl
@param permitrpl value to put into _permitrpl
*/
public void setPermitrpl(String permitrpl) {
	_permitrpl = permitrpl;
}

/**
Sets _permitsuf
@param permitsuf value to put into _permitsuf
*/
public void setPermitsuf(String permitsuf) {
	_permitsuf = permitsuf;
}

/**
Sets _prorated_yield
@param prorated_yield value to put into _prorated_yield
*/
public void setProrated_yield(float prorated_yield) {
	_prorated_yield = prorated_yield;
}

/**
Sets _receipt
@param receipt value to put into _receipt
*/
public void setReceipt(String receipt) {
	_receipt = receipt;
}

/**
Sets _str_id
@param str_id value to put into _str_id
*/
public void setStr_id(int str_id) {
	_str_id = str_id;
}

/**
Sets _str_wd
@param str_wd
*/
public void setStr_wd(int str_wd) {
	_str_wd = str_wd;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _tperf
@param tperf value to put into _tperf
*/
public void setTperf(int tperf) {
	_tperf = tperf;
}

/**
Sets _use1
@param use1 value to put into _use1
*/
public void setUse1(String use1) {
	_use1 = use1;
}

/**
Sets _use2
@param use2 value to put into _use2
*/
public void setUse2(String use2) {
	_use2 = use2;
}

/**
Sets _use3
@param use3 value to put into _use3
*/
public void setUse3(String use3) {
	_use3 = use3;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Sets _wd_id
@param wd_id value to put into _wd_id
*/
public void setWd_id(int wd_id) {
	_wd_id = wd_id;
}

/**
Sets _well_id
@param well_id value to put into _well_id
*/
public void setWell_id(int well_id) {
	_well_id = well_id;
}

/**
Sets _well_name
@param well_name value to put into _well_name
*/
public void setWell_name(String well_name) {
	_well_name = well_name;
}

/**
Sets _yield
@param yield value to put into _yield
*/
public void setYield(float yield) {
	_yield = yield;
}

/**
Sets _yield_apex
@param yield_apex value to put into _yield_apex
*/
public void setYield_apex(float yield_apex) {
	_yield_apex = yield_apex;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Wells {"		+ "\n" + 
		"Well_id:        " + _well_id + "\n" + 
		"Div:            " + _div + "\n" + 
		"WD:             " + _wd + "\n" + 
		"ID:             " + _id + "\n" + 
		"Receipt:        " + _receipt + "\n" + 
		"Permitno:       " + _permitno + "\n" + 
		"Permitsuf:      " + _permitsuf + "\n" + 
		"Permitrpl:      " + _permitrpl + "\n" + 
		"Well_name:      " + _well_name + "\n" + 
		"Yield:          " + _yield + "\n" +
		"Yield_apex:     " + _yield_apex + "\n" + 
		"Perm_date:      " + _perm_date + "\n" + 
		"Appr_date:      " + _appr_date + "\n" + 
		"Tperf:          " + _tperf + "\n" + 
		"Bperf:          " + _bperf + "\n" + 
		"Depth:          " + _depth + "\n" + 
		"Aquifer1:       " + _aquifer1 + "\n" + 
		"Wd_id:          " + _wd_id + "\n" + 
		"Flag:           " + _flag + "\n" + 
		"Use1:           " + _use1 + "\n" + 
		"Use2:           " + _use2 + "\n" + 
		"Use3:           " + _use3 + "\n" + 
		"Ditches_served: " + _ditches_served + "\n" +
		"Layer:          " + _layer + "\n" +
		"Layer_per:      " + _layer_per + "\n" + 
		"Est_id:         " + _est_id + "\n" + 
		"Parcel:         " + _parcel + "\n" + 
		"Class:          " + _class + "\n" + 
		"Distance:       " + _distance + "\n" + 
		"Prorated_yield: " + _prorated_yield + "\n" + 
		"Percent_yield:  " + _percent_yield + "\n" + 
		"Ditch_id:       " + _ditch_id + "\n" + 
		"Structure_num:  " + _structure_num + "\n" + 
		"Ditch_cov:      " + _ditch_cov + "\n" + 
		"Str_wd:         " + _str_wd + "\n" + 
		"Str_id:         " + _str_id + "\n}\n";
}

}
