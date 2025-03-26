// HydroBase_StructureAKA - Class to hold data from the HydroBase structure_aka table.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2025 Colorado Department of Natural Resources

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

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase structure_aka table.
*/
public class HydroBase_StructureAKA 
extends DMIDataObject {

protected int _structure_aka_num = 	DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected String _structure_aka_name = 	DMIUtil.MISSING_STRING;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

protected int _wd = 			DMIUtil.MISSING_INT;
protected int _id = 			DMIUtil.MISSING_INT;
protected int _div = 			DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_StructureAKA() {
	super();
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _structure_aka_name
@return _structure_aka_name
*/
public String getStructure_aka_name() {
	return _structure_aka_name;
}

/**
Returns _structure_aka_num
@return _structure_aka_num
*/
public int getStructure_aka_num() {
	return _structure_aka_num;
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
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _id
@param id value to put into _id
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _structure_aka_name
@param structure_aka_name value to put into _structure_aka_name
*/
public void setStructure_aka_name(String structure_aka_name) {
	_structure_aka_name = structure_aka_name;
}

/**
Sets _structure_aka_num
@param structure_aka_num value to put into _structure_aka_num
*/
public void setStructure_aka_num(int structure_aka_num) {
	_structure_aka_num = structure_aka_num;
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
	return "HydroBase_StructureAKA {"		+ "\n" + 
		"Structure_aka_num:  " + _structure_aka_num + "\n" + 
		"Structure_num:      " + _structure_num + "\n" + 
		"Structure_aka_name: " + _structure_aka_name + "\n" + 
		"Modified:           " + _modified + "\n" + 
		"User_num:           " + _user_num + "\n" +
		"WD:                 " + _wd + "\n" +
		"ID:                 " + _id + "\n" +
		"Div:                " + _div + "\n}\n";
}

}
