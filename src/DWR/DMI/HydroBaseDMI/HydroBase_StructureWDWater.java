// HydroBase_StructureWDWater - Class to store data from the HydroBase wd_water and structure tables.

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

import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase wd_water and structure tables.
*/
public class HydroBase_StructureWDWater 
extends HydroBase_Structure {

protected int _wdwater_num = 	DMIUtil.MISSING_INT;
protected int _strno = 		DMIUtil.MISSING_INT;
protected String _strname = 	DMIUtil.MISSING_STRING;
protected String _admingrp = 	DMIUtil.MISSING_STRING;
protected int _strtribto = 	DMIUtil.MISSING_INT;
//protected int _xstrtribto = 	DMIUtil.MISSING_INT;
protected double _strmile = 	DMIUtil.MISSING_DOUBLE;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;

// structure
protected int _div = DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_StructureWDWater() {
	super();
}

/**
Copy constructor to fill in all the values of this object from a corresponding
view.  Used during unit tests.
*/
public HydroBase_StructureWDWater(HydroBase_StructureView view) {
	if (view == null) {
		return;
	}
	_structure_num = view._structure_num;
	_wdwater_num = view._wdwater_num;
	_geoloc_num = view._geoloc_num;
	_div = view._div;
	_wd = view._wd;
	_id = view._id;
	_wdid = view._wdid;
	_ciu = view._ciu;
	_str_type = view._str_type;
	_STRTYPE = view._strtype;
	_str_name = view._str_name;
	_est_capacity = view._est_capacity;
	_est_unit = view._est_unit;
	_dcr_capacity = view._dcr_capacity;
	_dcr_unit = view._dcr_unit;
	_transbsn = view._transbsn;
	_modified = view._modified;
	_user_num = view._user_num;
	_common_id = view._common_id;
	_xtia = view._xtia;
	_aquifer_num = view._aquifer_num;
	_wdwater_num = view._wdwater_num;
	_strno = view._strno;
	_strname = view._strname;
//	_admingrp = view._admingrp;
	_strtribto = view._strtribto;
//	_strmile = view._strmile;
	_modified = view._modified;
	_user_num = view._user_num;
}

/**
Returns _admingrp
@return _admingrp
*/
public String getAdmingrp() {
	return _admingrp;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _strmile
@return _strmile
*/
public double getStrmile() {
	return _strmile;
}

/**
Returns _strname
@return _strname
*/
public String getStrname() {
	return _strname;
}

/**
Returns _strno
@return _strno
*/
public int getStrno() {
	return _strno;
}

/**
Returns _strtribto
@return _strtribto
*/
public int getStrtribto() {
	return _strtribto;
}

/**
Returns _wdwater_num
@return _wdwater_num
*/
public int getWdwater_num() {
	return _wdwater_num;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Returns _xstrtribto
@return _xstrtribto
*/
//public int getXstrtribto() {
//	return _xstrtribto;
//}

/**
Sets _admingrp
@param admingrp value to put in _admingrp
*/
public void setAdmingrp(String admingrp) {
	_admingrp = admingrp;
}

/**
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _modified
@param modified value to put in _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _strmile
@param strmile value to put in _strmile
*/
public void setStrmile(double strmile) {
	_strmile = strmile;
}

/**
Sets _strname
@param strname value to put in _strname
*/
public void setStrname(String strname) {
	_strname = strname;
}

/**
Sets _strno
@param strno value to put in _strno
*/
public void setStrno(int strno) {
	_strno = strno;
}

/**
Sets _strtribto
@param strtribto value to put in _strtribto
*/
public void setStrtribto(int strtribto) {
	_strtribto = strtribto;
}

/**
Sets _wdwater_num
@param wdwater_num value to put in _wdwater_num
*/
public void setWdwater_num(int wdwater_num) {
	_wdwater_num = wdwater_num;
}

/**
Sets _user_num
@param user_num value to put in _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/**
Sets _xstrtribto
@param xstrtribto value to put in _xstrtribto
*/
//public void setXstrtribto(int xstrtribto) {
//	_xstrtribto = xstrtribto;
//}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_StructureWDWater {"		+ "\n" + 
		"Wdwater_num:   " + _wdwater_num + "\n" + 
		"Strno:         " + _strno	 + "\n" + 
		"Strname:       " + _strname + "\n" + 
		"Admingrp:      " + _admingrp + "\n" + 
		"Strtribto:     " + _strtribto + "\n" + 
//		"Xstrtribto:    " + _xstrtribto + "\n" + 
		"Strmile:       " + _strmile + "\n" + 
		"Modified:      " + _modified + "\n" + 
		"User_num:      " + _user_num + "\n" +
		"Div:           " + _div + "\n}\n";
}

}
