// ----------------------------------------------------------------------------
// HydroBase_EmergencyPlan.java - Class to hold data from the HydroBase 
//	emergency_plan table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-11	J. Thomas Sapienza, RTi	Initial version from
//					HBDamEmergencyPlan.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

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
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_eplan = null;
	_ep_date = null;
	_modified = null;
	
	super.finalize();
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
