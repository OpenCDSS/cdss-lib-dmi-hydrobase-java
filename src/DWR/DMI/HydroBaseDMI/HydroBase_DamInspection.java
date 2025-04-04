// HydroBase_DamInspection - Class to hold data from the HydroBase dam_inspection table.

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
Class to store data from the HydroBase dam_inspection table.
*/
public class HydroBase_DamInspection
extends DMIDataObject {

protected int _structure_num = 		DMIUtil.MISSING_INT;
protected int _dam_inspect_num = 	DMIUtil.MISSING_INT;
protected int _outlet_num = 		DMIUtil.MISSING_INT;
protected int _damid = 			DMIUtil.MISSING_INT;
protected String _inspect_login = 	DMIUtil.MISSING_STRING;
protected Date _inspect_date = 		DMIUtil.MISSING_DATE;
protected String _inspection_type =	DMIUtil.MISSING_STRING;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_DamInspection() {
	super();
}

/**
Returns _dam_inspect_num
@return _dam_inspect_num
*/
public int getDam_inspect_num() {
	return _dam_inspect_num;
}

/**
Returns _damid
@return _damid
*/
public int getDamid() {
	return _damid;
}

/**
Returns _inspect_date
@return _inspect_date
*/
public Date getInspect_date() {
	return _inspect_date;
}

/**
Returns _inspect_login
@return _inspect_login
*/
public String getInspect_login() {
	return _inspect_login;
}

/**
Returns _inspection_type
@return _inspection_type
*/
public String getInspection_type() {
	return _inspection_type;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _outlet_num
@return _outlet_num
*/
public int getOutlet_num() {
	return _outlet_num;
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
Sets _dam_inspect_num
@param dam_inspect_num value to put into _dam_inspect_num
*/
public void setDam_inspect_num(int dam_inspect_num) {
	_dam_inspect_num = dam_inspect_num;
}

/**
Sets _damid
@param damid value to put into _damid
*/
public void setDamid(int damid) {
	_damid = damid;
}

/**
Sets _inspect_date
@param inspect_date value to put into _inspect_date
*/
public void setInspect_date(Date inspect_date) {
	_inspect_date = inspect_date;
}

/**
Sets _inspect_login
@param inspect_login value to put into _inspect_login
*/
public void setInspect_login(String inspect_login) {
	_inspect_login = inspect_login;
}

/**
Sets _inspection_type
@param inspection_type value to put into _inspection_type
*/
public void setInspection_type(String inspection_type) {
	_inspection_type = inspection_type;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _outlet_num
@param outlet_num value to put into _outlet_num
*/
public void setOutlet_num(int outlet_num) {
	_outlet_num = outlet_num;
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
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_DamInspection {"	+ "\n" +
		"Dam_inspect_num: " + _dam_inspect_num + "\n" +
		"Structure_num:   " + _structure_num + "\n" +
		"Outlet_num:      " + _outlet_num + "\n" +
		"Damid:           " + _damid + "\n" +
		"Inspect_login:   '" + _inspect_login + "\n" +
		"Inspect_date:    " + _inspect_date + "\n" +
		"Inspection_type: '" + _inspection_type + "\n" +
		"Modified:        " + _modified + "\n" +
		"User_num:        " + _user_num + "\n}\n";
}

}
