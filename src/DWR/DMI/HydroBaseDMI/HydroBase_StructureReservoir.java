// HydroBase_StructureReservoir - Class to store data from the HydroBase reservoir and structure tables.

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

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase reservoir and structure tables.
*/
public class HydroBase_StructureReservoir 
extends HydroBase_Structure {

protected int _structure_num = 	DMIUtil.MISSING_INT;
protected int _max_storage = 	DMIUtil.MISSING_INT;
protected int _normal_storage = DMIUtil.MISSING_INT;
protected int _surface_area = 	DMIUtil.MISSING_INT;
protected int _drain_area = 	DMIUtil.MISSING_INT;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;

// TODO SAM 2013-04-08 Why are some of the following redundant with the parent class?
protected String _ciu = 	DMIUtil.MISSING_STRING;
protected int _div = 		DMIUtil.MISSING_INT;
protected int _geoloc_num = 	DMIUtil.MISSING_INT;
protected int _id = 		DMIUtil.MISSING_INT;
protected String _str_name = 	DMIUtil.MISSING_STRING;
protected String _str_type = 	DMIUtil.MISSING_STRING;
protected String _STRTYPE = 	DMIUtil.MISSING_STRING;
protected int _transbsn = 	DMIUtil.MISSING_INT;
protected int _wd = 		DMIUtil.MISSING_INT;
protected int _wdwater_num = 	DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_StructureReservoir() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_modified = null;	
	_ciu = null;
	_str_name = null;
	_str_type = null;
	_STRTYPE = null;
	
	super.finalize();
}

/**
Returns _drain_area
@return _drain_area
*/
public int getDrain_area() {
	return _drain_area;
}

/**
Returns _max_storage
@return _max_storage
*/
public int getMax_storage() {
	return _max_storage;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _normal_storage
@return _normal_storage
*/
public int getNormal_storage() {
	return _normal_storage;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _surface_area
@return _surface_area
*/
public int getSurface_area() {
	return _surface_area;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Returns _ciu
@return _ciu
*/
public String getCiu() {
	return _ciu;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _geoloc_num
@return _geoloc_num
*/
public int getGeoloc_num() {
	return _geoloc_num;
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
}

/**
Returns _str_name
@return _str_name
*/
public String getStr_name() {
	return _str_name;
}

/**
Returns _str_type
@return _str_type
*/
public String getStr_type() {
	return _str_type;
}

/**
Returns _STRTYPE
@return _STRTYPE
*/
public String getSTRTYPE() {
	return _STRTYPE;
}

/**
Returns _transbsn
@return _transbsn
*/
public int getTransbsn() {
	return _transbsn;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Returns _wdwater_num
@return _wdwater_num
*/
public int getWdwater_num() {
	return _wdwater_num;
}

/**
Sets _drain_area
@param drain_area value to put into  _drain_area
*/
public void setDrain_area(int drain_area) {
	_drain_area = drain_area;
}

/**
Sets _max_storage
@param max_storage value to put into  _max_storage
*/
public void setMax_storage(int max_storage) {
	_max_storage = max_storage;
}

/**
Sets _modified
@param modified value to put into  _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _normal_storage
@param normal_storage value to put into  _normal_storage
*/
public void setNormal_storage(int normal_storage) {
	_normal_storage = normal_storage;
}

/**
Sets _structure_num
@param structure_num value to put into  _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _surface_area
@param surface_area value to put into  _surface_area
*/
public void setSurface_area(int surface_area) {
	_surface_area = surface_area;
}

/**
Sets _user_num
@param user_num value to put into  _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/**
Sets _ciu
@param ciu value to put into _ciu
*/
public void setCiu(String ciu) {
	_ciu = ciu;
}

/**
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _geoloc_num
@param geoloc_num value to put into _geoloc_num
*/
public void setGeoloc_num(int geoloc_num) {
	_geoloc_num = geoloc_num;
}

/**
Sets _id
@param id value to put into _id
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _str_name
@param str_name value to put into _str_name
*/
public void setStr_name(String str_name) {
	_str_name = str_name;
}

/**
Sets _str_type
@param str_type value to put into _str_type
*/
public void setStr_type(String str_type) {
	_str_type = str_type;
}

/**
Sets _STRTYPE
@param STRTYPE value to put into _STRTYPE
*/
public void setSTRTYPE(String STRTYPE) {
	_STRTYPE = STRTYPE;
}

/**
Sets _transbsn
@param transbsn value to put into _transbsn
*/
public void setTransbsn(int transbsn) {
	_transbsn = transbsn;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Sets _wdwater_num
@param wdwater_num value to put into _wdwater_num
*/
public void setWdwater_num(int wdwater_num) {
	_wdwater_num = wdwater_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_StructureReservoir {"		+ "\n" + 
		"Structure_num:  " + _structure_num + "\n" + 
		"Max_storage:    " + _max_storage + "\n" + 
		"Normal_storage: " + _normal_storage + "\n" + 
		"Surface_area:   " + _surface_area + "\n" + 
		"Drain_area:     " + _drain_area + "\n" + 
		"Modified:       " + _modified + "\n" + 
		"User_num:       " + _user_num + "\n" +
		"Ciu:            " + _ciu + "\n" +
		"Div:            " + _div + "\n" +
		"Geoloc_num:     " + _geoloc_num + "\n" +
		"ID:             " + _id + "\n" +
		"Str_name:       " + _str_name + "\n" +
		"Str_type:       " + _str_type + "\n" +
		"STRTYPE:        " + _STRTYPE + "\n" +
		"Structure_num:  " + _structure_num + "\n" +
		"Transbsn:       " + _transbsn + "\n" +
		"WD:             " + _wd + "\n" +
		"Wdwater_num:    " + _wdwater_num + "\n}\n";
}

}
