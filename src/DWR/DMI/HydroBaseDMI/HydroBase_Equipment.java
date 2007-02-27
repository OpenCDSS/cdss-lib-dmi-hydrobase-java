// ----------------------------------------------------------------------------
// HydroBase_Equipment.java - Class to hold data from the HydroBase 
//	equipment table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-10	J. Thomas Sapienza, RTi	Initial version from HBEquipment.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

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
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_date_installed = null;
	_modified = null;
	_meas_device = null;
	_recorder = null;
	super.finalize();
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
