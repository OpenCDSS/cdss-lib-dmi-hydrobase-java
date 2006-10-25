// ----------------------------------------------------------------------------
// HydroBase_GeneralComment.java - Class to hold data from the HydroBase 
//	general_comment table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-10	J. Thomas Sapienza, RTi	Initial version from HBGeneralComment.
// 2003-02-19	JTS, RTi		Added fields and methods to handled 
//					data members used in older versions of
//					the database.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase general_comment table.
*/
public class HydroBase_GeneralComment 
extends DMIDataObject {

protected int _structure_num = 	DMIUtil.MISSING_INT;
protected Date _date_entered = 	DMIUtil.MISSING_DATE;
protected String _comment = 	DMIUtil.MISSING_STRING;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;

// older fields
protected int _genl_comm_num = 	DMIUtil.MISSING_INT;
protected String _notes = 	DMIUtil.MISSING_STRING;
/*
This is also an older field, but it will be stored in the _comment
member variable -- though it has get and set methods appropriate to its name.
protected String _genl_comment = DMIUtil.MISSING_STRING;
*/

/**
Constructor.
*/
public HydroBase_GeneralComment() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
*/
protected void finalize()
throws Throwable {
	_comment = null;
	_date_entered = null;
	_modified = null;
	_notes = null;
	super.finalize();
}

/**
Returns _comment.
@return _comment.
*/
public String getComment() {
	return _comment;
}

/**
Returns _date_entered.
@return _date_entered.
*/
public Date getDate_entered() {
	return _date_entered;
}

/**
Returns _genl_comm_num
@return _genl_comm_num
*/
public int getGenl_comm_num() {
	return _genl_comm_num;
}

/**
Returns _genl_comment (which is stored in _comment)
@return _genl_comment (which is stored in _comment)
*/
public String getGenl_comment() {
	return _comment;
}

/**
Returns _modified.
@return _modified.
*/
public Date getModified() {
	return _modified;
}

/**
Returns _notes
@return _notes
*/
public String getNotes() {
	return _notes;
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
Sets _comment.
@param comment value to put into _comment.
*/
public void setComment(String comment) {
	_comment = comment;
}

/**
Sets _date_entered.
@param date_entered value to put into _date_entered.
*/
public void setDate_entered(Date date_entered) {
	_date_entered = date_entered;
}

/**
Sets _genl_comm_num
@param genl_comm_num value to put into _genl_comm_num
*/
public void setGenl_comm_num(int genl_comm_num) {
	_genl_comm_num = genl_comm_num;
}

/**
Sets _genl_comment
@param genl_comment value to put into _comment
*/
public void setGenl_comment(String genl_comment) {
	_comment = genl_comment;
}

/**
Sets _modified.
@param modified value to put into _modified.
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _notes
@param notes value to put into _notes
*/
public void setNotes(String notes) {
	_notes = notes;
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
	return "HydroBase_GeneralComment {"		+ "\n" + 
		"Structure_num: " + _structure_num + "\n" +
		"Date_entered:  " +  _date_entered + "\n" +
		"Comment:       " + _comment + "\n" +
		"Modified:      " + _modified + "\n" +
		"User_num:      " + _user_num + "\n}\n";

	
}

}
