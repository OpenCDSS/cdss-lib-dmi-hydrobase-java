// HydroBase_CourtCase - Class to hold data from the HydroBase courtcase table.

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
Class to store data from the HydroBase courtcase table.
*/
public class HydroBase_CourtCase
extends DMIDataObject {

protected int _case_no_num = 		DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected String _case_no = 		DMIUtil.MISSING_STRING;
protected String _priorno = 		DMIUtil.MISSING_STRING;
protected String _case_no_book = 	DMIUtil.MISSING_STRING;
protected String _case_no_page = 	DMIUtil.MISSING_STRING;
protected String _case_no_comment = 	DMIUtil.MISSING_STRING;
protected String _decree_summary = 	DMIUtil.MISSING_STRING;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_CourtCase() {
	super();
}

/**
Returns _case_no
@return _case_no
*/
public String getCase_no() {
	return _case_no;
}

/**
Returns _case_no_book
@return _case_no_book
*/
public String getCase_no_book() {
	return _case_no_book;
}

/**
Returns _case_no_comment
@return _case_no_comment
*/
public String getCase_no_comment() {
	return _case_no_comment;
}

/**
Returns _case_no_page
@return _case_no_page
*/
public String getCase_no_page() {
	return _case_no_page;
}

/**
Returns _case_no_num
@return _case_no_num
*/
public int getCase_no_num() {
	return _case_no_num;
}

/**
Returns _decree_summary
@return _decree_summary
*/
public String getDecree_summary() {
	return _decree_summary;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _priorno
@return _priorno
*/
public String getPriorno() {
	return _priorno;
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
Sets _case_no
@param case_no value to put into _case_no
*/
public void setCase_no(String case_no) {
	 _case_no = case_no;
}

/**
Sets _case_no_book
@param case_no_book value to put into _case_no_book
*/
public void setCase_no_book(String case_no_book) {
	 _case_no_book = case_no_book;
}

/**
Sets _case_no_comment
@param case_no_comment value to put into _case_no_comment
*/
public void setCase_no_comment(String case_no_comment) {
	 _case_no_comment = case_no_comment;
}

/**
Sets _case_no_page
@param case_no_page value to put into _case_no_page
*/
public void setCase_no_page(String case_no_page) {
	 _case_no_page = case_no_page;
}

/**
Sets _case_no_num
@param case_no_num value to put into _case_no_num
*/
public void setCase_no_num(int case_no_num) {
	 _case_no_num = case_no_num;
}

/**
Sets _decree_summary
@param decree_summary value to put into _decree_summary
*/
public void setDecree_summary(String decree_summary) {
	 _decree_summary = decree_summary;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	 _modified = modified;
}

/**
Sets _priorno
@param priorno value to put into _priorno
*/
public void setPriorno(String priorno) {
	 _priorno = priorno;
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
	return "HydroBase_CourtCase {"				+ "\n" +
		"Case_no_num:     " + _case_no_num + "\n" +
		"Structure_num:   " + _structure_num + "\n" +
		"Case_no:         '" + _case_no + "'\n" +
		"Priorno:         '" + _priorno + "'\n" +
		"Case_no_book:    '" + _case_no_book + "'\n" +
		"Case_no_page:    '" + _case_no_page + "'\n" +
		"Case_no_comment: '" + _case_no_comment + "'\n" +
		"Decree_summary:  '" + _decree_summary + "'\n" +
		"Modified:        " + _modified + "\n" +
		"User_num:        " + _user_num + "\n}\n";
}

}