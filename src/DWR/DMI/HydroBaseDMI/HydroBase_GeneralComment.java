// HydroBase_GeneralComment - Class to hold data from the HydroBase general_comment table.

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
This is also an older field, but it will be stored in the _comment member variable,
although it has get and set methods appropriate to its name.
*/

//protected String _genl_comment = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_GeneralComment() {
	super();
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