// HydroBase_Agstats - Class to hold data from the HydroBase Ag_stats table.

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
// Agstats.java - Class to hold data from the HydroBase Ag_stats table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-05	J. Thomas Sapienza, RTi	Initial version from HB_Agstats.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase Ag_stats table.
*/
public class HydroBase_Agstats 
extends DMIDataObject {

protected String _county = 	DMIUtil.MISSING_STRING;
protected String _type = 	DMIUtil.MISSING_STRING;
protected int _cal_year = 	DMIUtil.MISSING_INT;
protected String _st = 		DMIUtil.MISSING_STRING;
protected String _units = 	DMIUtil.MISSING_STRING;
protected double _ag_amt = 	DMIUtil.MISSING_DOUBLE;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_Agstats() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_county = null;
	_type = null;
	_st = null;
	_units = null;
	_modified = null;
	
	super.finalize();
}

/**
Returns _ag_amt.
@return _ag_amt.
*/
public double getAg_amt() {
	return _ag_amt;
}

/**
Returns _cal_year.
@return _cal_year.
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _county.
@return _county.
*/
public String getCounty() {
	return _county;
}

/**
Returns _modified.
@return _modified.
*/
public Date getModified() {
	return _modified;
}

/**
Returns _st.
@return _st.
*/
public String getST() {
	return _st;
}

/**
Returns _type.
@return _type.
*/
public String getType() {
	return _type;
}

/**
Returns _units.
@return _units.
*/
public String getUnits() {
	return _units;
}

/**
Returns _user_num.
@return _user_num.
*/
public int getUser_num() {
	return _user_num;
}


/**
Sets _ag_amt.
@param ag_amt value to put in _ag_amt.
*/
public void setAg_amt(double ag_amt) {
	_ag_amt = ag_amt;
}

/**
Sets _cal_year.
@param cal_year value to put in _cal_year.
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _county.
@param county value to put in _county.
*/
public void setCounty(String county) {
	_county = county;
}

/**
Sets _modified.
@param modified value to put in _modified.
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _st.
@param st value to put in _st.
*/
public void setST(String st) {
	_st = st;
}

/**
Sets _type.
@param type value to put in _type.
*/
public void setType(String type) {
	_type = type;
}

/**
Sets _units.
@param units value to put in _units.
*/
public void setUnits(String units) {
	_units = units;
}

/**
Returns _user_num.
@return _user_num.
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Agstats {"		+ "\n" + 
		"County:   '" 	+ _county 	+ "'\n" + 
		"Type:     '" 	+ _type 	+ "'\n" + 
		"Cal_year: " 	+ _cal_year 	+ "\n" + 
		"ST:       '" 	+ _st 		+ "'\n" + 
		"Units:    '" 	+ _units 	+ "'\n" + 
		"Ag_amt:   "	+ _ag_amt 	+ "\n" + 
		"Modified: " 	+ _modified 	+ "\n" + 
		"User_num: " 	+ _user_num 	+ "\n}\n";
}

}
