// HydroBase_WISSheetName - Class to hold data from the HydroBase wis_sheet_name table.

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
// HydroBase_WISSheetName.java - Class to hold data from the HydroBase 
//	wis_sheet_name table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-12	J. Thomas Sapienza, RTi	Initial version from HBWIS.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2005-03-09	JTS, RTi		Initial version from HydroBase_SheetName
// 2005-07-11	JTS, RTi		Added comparable for sorting by name.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase wis_sheet_name table.  
This class is the base class for:
HydroBase_WISSheetNameSpecialData
*/
public class HydroBase_WISSheetName 
extends DMIDataObject 
implements Comparable<HydroBase_WISSheetName> {

protected int _wis_num = 		DMIUtil.MISSING_INT;
protected String _sheet_name = 		DMIUtil.MISSING_STRING;
protected Date _effective_date = 	DMIUtil.MISSING_DATE;
protected int _wd = 			DMIUtil.MISSING_INT;
protected String _gain_method  = 	DMIUtil.MISSING_STRING;
protected String _sheet_type = 		DMIUtil.MISSING_STRING;
protected String _comments = 		DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_WISSheetName() {
	super();
}

/**
Compares HydroBase_WISSheetName objects based on sheet name.
*/
public int compareTo(HydroBase_WISSheetName w) {
	if (_sheet_name == null && w._sheet_name == null) {
		return 0;
	}
	else if (_sheet_name == null) {
		return 1;
	}
	else if (w._sheet_name == null) {
		return -1;
	}

	return _sheet_name.compareTo(w._sheet_name);
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_sheet_name = null;
	_effective_date = null;
	_gain_method = null;
	_sheet_type = null;
	_comments = null;
	
	super.finalize();
}

/**
Returns _comments
@return _comments
*/
public String getComments() {
	return _comments;
}

/**
Returns _effective_date
@return _effective_date
*/
public Date getEffective_date() {
	return _effective_date;
}

/**
Returns _gain_method
@return _gain_method
*/
public String getGain_method() {
	return _gain_method;
}

/**
Returns _sheet_name
@return _sheet_name
*/
public String getSheet_name() {
	return _sheet_name;
}

/**
Returns _sheet_type
@return _sheet_type
*/
public String getSheet_type() {
	return _sheet_type;
}

/**
Returns _wis_num
@return _wis_num
*/
public int getWis_num() {
	return _wis_num;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Sets _comments
@param comments value to put into _comments
*/
public void setComments(String comments) {
	_comments = comments;
}

/**
Sets _effective_date
@param effective_date value to put into _effective_date
*/
public void setEffective_date(Date effective_date) {
	_effective_date = effective_date;
}

/**
Sets _gain_method
@param gain_method value to put into _gain_method
*/
public void setGain_method(String gain_method) {
	_gain_method = gain_method;
}

/**
Sets _sheet_name
@param sheet_name value to put into _sheet_name
*/
public void setSheet_name(String sheet_name) {
	_sheet_name = sheet_name;
}

/**
Sets _sheet_type
@param sheet_type value to put into _sheet_type
*/
public void setSheet_type(String sheet_type) {
	_sheet_type = sheet_type;
}

/**
Sets _wis_num
@param wis_num value to put into _wis_num
*/
public void setWis_num(int wis_num) {
	_wis_num = wis_num;
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
	return "HydroBase_WISSheetName {"		+ "\n" + 
		"Wis_num:        " + _wis_num + "\n" + 
		"Sheet_name:     " + _sheet_name + "\n" + 
		"Effective_date: " + _effective_date + "\n" + 
		"WD:             " + _wd + "\n" + 
		"Gain_method:    " + _gain_method  + "\n" + 
		"Sheet_type:     " + _sheet_type + "\n" + 
		"Comments:       " + _comments + "\n}\n";
}

}
