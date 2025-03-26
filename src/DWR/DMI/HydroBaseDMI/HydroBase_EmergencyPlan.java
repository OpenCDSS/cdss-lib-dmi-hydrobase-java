// HydroBase_EmergencyPlan - Class to hold data from the HydroBase emergency_plan table.

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
Class to store data from the HydroBase emergency_plan table.
*/
public class HydroBase_EmergencyPlan
extends DMIDataObject {

protected int _emer_plan_num =	DMIUtil.MISSING_INT;
protected int _structure_num = 	DMIUtil.MISSING_INT;
protected String _eplan = 	DMIUtil.MISSING_STRING;
protected Date _ep_date = 	DMIUtil.MISSING_DATE;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_EmergencyPlan() {
	super();
}

/**
Returns _emer_plan_num
@return _emer_plan_num
*/
public int getEmer_plan_num() {
	return _emer_plan_num;
}

/**
Returns _ep_date
@return _ep_date
*/
public Date getEp_date() {
	return _ep_date;
}

/**
Returns _eplan
@return _eplan
*/
public String getEplan() {
	return _eplan;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
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
Sets _emer_plan_num
@param emer_plan_num value to put into _emer_plan_num
*/
public void setEmer_plan_num(int emer_plan_num) {
	_emer_plan_num = emer_plan_num;
}

/**
Sets _ep_date
@param ep_date value to put into _ep_date
*/
public void setEp_date(Date ep_date) {
	_ep_date = ep_date;
}

/**
Sets _eplan
@param eplan value to put into _eplan
*/
public void setEplan(String eplan) {
	_eplan = eplan;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
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
	return "HydroBase_EmergencyPlan {"	+ "\n" +
		"Emer_plan_num: " + _emer_plan_num + "\n" +
		"Structure_num: " + _structure_num + "\n" +
		"Eplan:         '" + _eplan + "'\n" +
		"Ep_date:       " + _ep_date + "\n" +
		"Modified:      " + _modified + "\n" +
		"User_num:      " + _user_num + "\n}\n";
}

}
