// HydroBase_WellMeas - Class to hold data from the HydroBase well_meas table.

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
// HydroBase_WellMeas.java - Class to hold data from the HydroBase 
//	well_meas table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-13	J. Thomas Sapienza, RTi	Initial version from HBWellMeas.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2005-06-20 	JTS, RTi		Added fields to support new views.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase well_meas table.
*/
public class HydroBase_WellMeas 
extends DMIDataObject {

protected int _meas_num = DMIUtil.MISSING_INT;
protected Date _meas_date = DMIUtil.MISSING_DATE;
protected double _wat_level = DMIUtil.MISSING_DOUBLE;
protected int _structure_num = DMIUtil.MISSING_INT;

// Structure
protected int _wd = DMIUtil.MISSING_INT;
protected int _id = DMIUtil.MISSING_INT;
protected String _str_name = DMIUtil.MISSING_STRING;

// Geoloc
protected String _pm = DMIUtil.MISSING_STRING;
protected int _ts = DMIUtil.MISSING_INT;
protected String _tdir = DMIUtil.MISSING_STRING;
protected String _tsa = DMIUtil.MISSING_STRING;
protected int _rng = DMIUtil.MISSING_INT;
protected String _rdir = DMIUtil.MISSING_STRING;
protected String _rnga = DMIUtil.MISSING_STRING;
protected int _sec = DMIUtil.MISSING_INT;
protected String _seca = DMIUtil.MISSING_STRING;
protected String _q160 = DMIUtil.MISSING_STRING;
protected String _q40 = DMIUtil.MISSING_STRING;
protected String _q10 = DMIUtil.MISSING_STRING;

// View
protected int _well_meas_num = DMIUtil.MISSING_INT;
protected int _well_num = DMIUtil.MISSING_INT;
protected double _wl_depth = DMIUtil.MISSING_DOUBLE;
protected double _wl_elev = DMIUtil.MISSING_DOUBLE;
protected String _meas_by = DMIUtil.MISSING_STRING;
protected Date _modified = DMIUtil.MISSING_DATE;
protected String _user = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_WellMeas() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_meas_date = null;
	_q10 = null;
	_q40 = null;
	_q160 = null;
	_rdir = null;
	_seca = null;
	_str_name = null;
	_tdir = null;
	_meas_by = null;
	_modified = null;
	_user = null;
	
	super.finalize();
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
}

/**
Returns _meas_by
@return _meas_by
*/
public String getMeas_by() {
	return _meas_by;
}

/**
Returns _meas_date
@return _meas_date
*/
public Date getMeas_date() {
	return _meas_date;
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
Returns _pm
@return _pm
*/
public String getPM() {
	return _pm;
}

/**
Returns _q10
@return _q10
*/
public String getQ10() {
	return _q10;
}

/**
Returns _q40
@return _q40
*/
public String getQ40() {
	return _q40;
}

/**
Returns _q160
@return _q160
*/
public String getQ160() {
	return _q160;
}

/**
Returns _rdir
@return _rdir
*/
public String getRdir() {
	return _rdir;
}

/**
Returns _rng
@return _rng
*/
public int getRng() {
	return _rng;
}

/**
Returns _rnga
@return _rnga
*/
public String getRnga() {
	return _rnga;
}

/**
Returns _sec
@return _sec
*/
public int getSec() {
	return _sec;
}

/**
Returns _seca
@return _seca
*/
public String getSeca() {
	return _seca;
}

/**
Returns _str_name
@return _str_name
*/
public String getStr_name() {
	return _str_name;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _tdir
@return _tdir
*/
public String getTdir() {
	return _tdir;
}

/**
Returns _ts
@return ts
*/
public int getTS() {
	return _ts;
}

/**
Returns _tsa
@return _tsa
*/
public String getTsa() {
	return _tsa;
}

/**
Returns _user
@return _user
*/
public String getUser() {
	return _user;
}

/**
Returns _wat_level
@return _wat_level
*/
public double getWat_level() {
	return _wat_level;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Returns _well_meas_num
@return _well_meas_num
*/
public int getWell_meas_num() {
	return _well_meas_num;
}

/**
Returns _well_num
@return _well_num
*/
public int getWell_num() {
	return _well_num;
}

/**
Returns _wl_depth
@return _wl_depth
*/
public double getWl_depth() {
	return _wl_depth;
}

/**
Returns _wl_elev
@return _wl_elev
*/
public double getWl_elev() {
	return _wl_elev;
}

/**
Sets _id
@param id value to put into _id
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _meas_by
@param meas_by
*/
public void setMeas_by(String meas_by) {
	_meas_by = meas_by;
}

/**
Sets _meas_date
@param meas_date value to put into _meas_date
*/
public void setMeas_date(Date meas_date) {
	_meas_date = meas_date;
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
@param modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _pm
@param pm value to put into _pm
*/
public void setPM(String pm) {
	_pm = pm;
}

/**
Sets _q10
@param q10 value to put into _q10
*/
public void setQ10(String q10) {
	_q10 = q10;
}

/**
Sets _q40
@param q40 value to put into _q40
*/
public void setQ40(String q40) {
	_q40 = q40;
}

/**
Sets _q160
@param q160 value to put into _q160
*/
public void setQ160(String q160) {
	_q160 = q160;
}

/**
Sets _rdir
@param rdir value to put into _rdir
*/
public void setRdir(String rdir) {
	_rdir = rdir;
}

/**
Sets _rng
@param rng value to put into _rng
*/
public void setRng(int rng) {
	_rng = rng;
}

/**
Sets _rnga
@param rnga value to put into _rnga
*/
public void setRnga(String rnga) {
	_rnga = rnga;
}

/**
Sets _sec
@param sec value to put into _sec
*/
public void setSec(int sec) {
	_sec = sec;
}

/**
Sets _seca
@param seca value to put into _seca
*/
public void setSeca(String seca) {
	_seca = seca;
}

/**
Sets _str_name
@param str_name value to put into _str_name
*/
public void setStr_name(String str_name) {
	_str_name = str_name;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _tdir
@param tdir value to put into _tdir
*/
public void setTdir(String tdir) {
	_tdir = tdir;
}

/**
Sets _ts
@param ts value to put into _ts
*/
public void setTS(int ts) {
	_ts = ts;
}

/**
Sets _tsa
@param tsa value to put into _tsa
*/
public void setTsa(String tsa) {
	_tsa = tsa;
}

/**
Sets _user
@param user
*/
public void setUser(String user) {
	_user = user;
}

/**
Sets _wat_level
@param wat_level value to put into _wat_level
*/
public void setWat_level(double wat_level) {
	_wat_level = wat_level;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Sets _well_meas_num
@param well_meas_num
*/
public void setWell_meas_num(int well_meas_num) {
	_well_meas_num = well_meas_num;
}

/**
Sets _well_num
@param well_num
*/
public void setWell_num(int well_num) {
	_well_num = well_num;
}

/**
Sets _wl_depth
@param wl_depth
*/
public void setWl_depth(double wl_depth) {
	_wl_depth = wl_depth;
}

/**
Sets _wl_elev
@param wl_elev
*/
public void setWl_elev(double wl_elev) {
	_wl_elev = wl_elev;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_WellMeas {"		+ "\n" + 
		"Meas_num:      " + _meas_num + "\n" + 
		"Meas_date:     " + _meas_date + "\n" + 
		"Wat_level:     " + _wat_level + "\n" + 
		"Structure_num: " + _structure_num + "\n" + 
		"WD:            " + _wd + "\n" + 
		"ID:            " + _id + "\n" + 
		"Str_name:      " + _str_name + "\n" + 
		"PM:            " + _pm + "\n" + 
		"TS:            " + _ts + "\n" + 
		"Tdir:          " + _tdir + "\n" + 
		"Rng:           " + _rng + "\n" + 
		"Rdir:          " + _rdir + "\n" + 
		"Sec:           " + _sec + "\n" + 
		"Seca:          " + _seca + "\n" + 
		"Q160:          " + _q160 + "\n" + 
		"Q40:           " + _q40 + "\n" + 
		"Q10:           " + _q10 + "\n}\n";
}

}
