// HydroBase_DamSpillway - Class to hold data from the HydroBase dam_spillway table.

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
// HydroBase_DamSpillway.java - Class to hold data from the HydroBase 
//	dam_spillway table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-06 	J. Thomas Sapienza, RTi	Initial version from HBDamSpillway.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase dam_spillway table.
*/
public class HydroBase_DamSpillway 
extends DMIDataObject {

protected int _dam_spillway_num = 	DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected int _damid = 			DMIUtil.MISSING_INT;
protected String _spillway_name = 	DMIUtil.MISSING_STRING;
protected double _width = 		DMIUtil.MISSING_DOUBLE;
protected double _freeboard = 		DMIUtil.MISSING_DOUBLE;
protected double _wall_side_slope =	DMIUtil.MISSING_DOUBLE;
protected int _capacity = 		DMIUtil.MISSING_INT;
protected String _sply_type = 		DMIUtil.MISSING_STRING;
protected String _sply_code = 		DMIUtil.MISSING_STRING;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_DamSpillway() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_spillway_name = null;
	_sply_type = null;
	_sply_code = null;
	_modified = null;
	
	super.finalize();
}

/**
Returns _capacity
@return _capacity
*/
public int getCapacity() {
	return _capacity;
}

/**
Returns _dam_spillway_num
@return _dam_spillway_num
*/
public int getDam_spillway_num() {
	return _dam_spillway_num;
}

/**
Returns _damid
@return _damid
*/
public int getDamid() {
	return _damid;
}

/**
Returns _freeboard
@return _freeboard
*/
public double getFreeboard() {
	return _freeboard;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _spillway_name
@return _spillway_name
*/
public String getSpillway_name() {
	return _spillway_name;
}

/**
Returns _sply_code
@return _sply_code
*/
public String getSply_code() {
	return _sply_code;
}

/**
Returns _sply_type
@return _sply_type
*/
public String getSply_type() {
	return _sply_type;
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
Returns _wall_side_slope
@return _wall_side_slope
*/
public double getWall_side_slope() {
	return _wall_side_slope;
}

/**
Returns _width
@return _width
*/
public double getWidth() {
	return _width;
}


/**
Sets _capacity
@param capacity value to put into _capacity
*/
public void setCapacity(int capacity) {
	_capacity = capacity;
}

/**
Sets _dam_spillway_num
@param dam_spillway_num value to put into _dam_spillway_num
*/
public void setDam_spillway_num(int dam_spillway_num) {
	_dam_spillway_num = dam_spillway_num;
}

/**
Sets _damid
@param damid value to put into _damid
*/
public void setDamid(int damid) {
	_damid = damid;
}

/**
Sets _freeboard
@param freeboard value to put into _freeboard
*/
public void setFreeboard(double freeboard) {
	_freeboard = freeboard;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _spillway_name
@param spillway_name value to put into _spillway_name
*/
public void setSpillway_name(String spillway_name) {
	_spillway_name = spillway_name;
}

/**
Sets _sply_code
@param sply_code value to put into _sply_code
*/
public void setSply_code(String sply_code) {
	_sply_code = sply_code;
}

/**
Sets _sply_type
@param sply_type value to put into _sply_type
*/
public void setSply_type(String sply_type) {
	_sply_type = sply_type;
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
Sets _wall_side_slope
@param wall_side_slope value to put into _wall_side_slope
*/
public void setWall_side_slope(double wall_side_slope) {
	_wall_side_slope = wall_side_slope;
}

/**
Sets _width
@param width value to put into _width
*/
public void setWidth(double width) {
	_width = width;
}



/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_DamSpillway {"		+ "\n" + 
		"Dam_spillway_num: " + _dam_spillway_num + "\n" + 
		"Structure_num:    " + _structure_num + "\n" + 
		"Damid:            " + _damid + "\n" + 
		"Spillway_name:    '" + _spillway_name + "\n" + 
		"Width:            " + _width + "\n" + 
		"Freeboard:        " + _freeboard + "\n" + 
		"Wall_side_slope:  " + _wall_side_slope + "\n" + 
		"Capacity:         " + _capacity + "\n" + 
		"Sply_type:        '" + _sply_type + "\n" + 
		"Sply_code:        '" + _sply_code + "\n" + 
		"Modified:         " + _modified + "\n" + 
		"User_num:         " + _user_num + "\n}\n";
}

}
