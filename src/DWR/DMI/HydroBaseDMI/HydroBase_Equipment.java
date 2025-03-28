// HydroBase_Equipment - Class to hold data from the HydroBase equipment table.

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
Class to store data from the HydroBase equipment table.
*/
public class HydroBase_Equipment
extends DMIDataObject {

protected int _equip_num = 		DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected Date _date_installed = 	DMIUtil.MISSING_DATE;
protected String _meas_device = 	DMIUtil.MISSING_STRING;
protected String _recorder = 		DMIUtil.MISSING_STRING;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_Equipment() {
	super();
}

/**
Returns _date_installed.
@return _date_installed.
*/
public Date getDate_installed() {
	return _date_installed;
}

/**
Returns _equip_num.
@return _equip_num.
*/
public int getEquip_num() {
	return _equip_num;
}

/**
Returns _meas_device.
@return _meas_device.
*/
public String getMeas_device() {
	return _meas_device;
}

/**
Returns _modified.
@return _modified.
*/
public Date getModified() {
	return _modified;
}

/**
Returns _recorder.
@return _recorder.
*/
public String getRecorder() {
	return _recorder;
}

/**
Returns _structure_num.
@return _structure_num.
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _user_num.
@return _user_num.
*/
public int getUser_num() {
	return _user_num;
}

/**
Sets _date_installed.
@param date_installed value to put into _date_installed.
*/
public void setDate_installed(Date date_installed) {
	_date_installed = date_installed;
}

/**
Sets _equip_num.
@param equip_num value to put into _equip_num.
*/
public void setEquip_num(int equip_num) {
	_equip_num = equip_num;
}

/**
Sets _meas_device.
@param meas_device value to put into _meas_device.
*/
public void setMeas_device(String meas_device) {
	_meas_device = meas_device;
}

/**
Sets _modified.
@param modified value to put into _modified.
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _recorder.
@param recorder value to put into _recorder.
*/
public void setRecorder(String recorder) {
	_recorder = recorder;
}

/**
Sets _structure_num.
@param structure_num value to put into _structure_num.
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _user_num.
@param user_num value to put into _user_num.
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/**
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Equipment {"		+ "\n" +
		"Equip_num:      " + _equip_num + "\n" +
		"Structure_num:  " + _structure_num + "\n" +
		"Date_installed: " + _date_installed + "\n" +
		"Meas_device:    " + _meas_device + "\n" +
		"Recorder:       " + _recorder + "\n" +
		"Modified:       " + _modified + "\n" +
		"User_num:       " + _user_num + "\n}\n";
}

}