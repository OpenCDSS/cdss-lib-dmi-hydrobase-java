// ----------------------------------------------------------------------------
// HydroBase_PersonDetails.java - Class to hold data from the HydroBase 
//	person_details table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-10	J. Thomas Sapienza, RTi	Initial version from HBPersonDetails.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase person_details table.
*/
public class HydroBase_PersonDetails 
extends DMIDataObject {

protected int _rolodex_num = 	DMIUtil.MISSING_INT;
protected int _structure_num = 	DMIUtil.MISSING_INT;
protected String _type = 	DMIUtil.MISSING_STRING;
protected String _note = 	DMIUtil.MISSING_STRING;
protected double _amount = 	DMIUtil.MISSING_DOUBLE;
protected String _priority = 	DMIUtil.MISSING_STRING;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;

protected int _id = 		DMIUtil.MISSING_INT;
protected int _wd = 		DMIUtil.MISSING_INT;
protected int _div = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_PersonDetails() {
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
	_note = null;
	_priority = null;
	_type = null;
	
	super.finalize();
}

/**
Returns _amount
@return _amount
*/
public double getAmount(){
	return _amount;
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
Returns _note
@return _note
*/
public String getNote() {
	return _note;
}

/**
Returns _priority
@return _priority
*/
public String getPriority() {
	return _priority;
}

/**
Returns _rolodex_num
@return _rolodex_num
*/
public int getRolodex_num() {
	return _rolodex_num;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _type
@return _type
*/
public String getType() {
	return _type;
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
Sets _amount
@param amount value to put into _amount
*/
public void setAmount(double amount){
	_amount = amount;
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
Sets _note
@param note value to put into _note
*/
public void setNote(String note) {
	_note = note;
}

/**
Sets _priority
@param priority value to put into _priority
*/
public void setPriority(String priority) {
	_priority = priority;
}

/**
Sets _rolodex_num
@param rolodex_num value to put into _rolodex_num
*/
public void setRolodex_num(int rolodex_num) {
	_rolodex_num = rolodex_num;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _type
@param type value to put into _type
*/
public void setType(String type) {
	_type = type;
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
	return "HydroBase_PersonDetails {"		+ "\n" + 
		"Rolodex_num:   " + _rolodex_num + "\n" +
		"Structure_num: " + _structure_num + "\n" +
		"Div:           " + _div + "\n" +
		"ID:            " + _id + "\n" +
		"WD:            " + _wd + "\n" +
		"Type:          " + _type + "\n" +
		"Note:          " + _note + "\n" +
		"Amount:        " + _amount + "\n" +
		"Priority:      " + _priority + "\n" +
		"Modified:      " + _modified + "\n" +
		"User_num:      " + _user_num + "\n}\n";
	
}

}
