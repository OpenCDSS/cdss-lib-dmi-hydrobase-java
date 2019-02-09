// HydroBase_TSProduct - class to store information from the TSProduct table

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

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

// ----------------------------------------------------------------------------
// HydroBase_TSProduct - class to store information from the TSProduct
//	table
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2004-03-08	J. Thomas Sapienza, RTi	Initial version from 
//					RiversideDB_TSProduct
// 2005-02-15	JTS, RTi		Added _user_num
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the Area table
*/
public class HydroBase_TSProduct 
extends DMIDataObject {

protected int _TSProduct_num = 		DMIUtil.MISSING_INT;
protected int _ProductGroup_num = 	DMIUtil.MISSING_INT;
protected String _Identifier = 		DMIUtil.MISSING_STRING;
protected String _Name = 		DMIUtil.MISSING_STRING;
protected String _Comment = 		DMIUtil.MISSING_STRING;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
HydroBase_TSProduct constructor.
*/
public HydroBase_TSProduct () {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
*/
protected void finalize() throws Throwable {
	_Identifier = null;
	_Name = null;
	_Comment = null;
	super.finalize();
}

/**
Returns _Comment
@return _Comment
*/
public String getComment() {
	return _Comment;
}

/**
Returns _Identifier
@return _Identifier
*/
public String getIdentifier() {
	return _Identifier;
}

/**
Returns _Name
@return _Name
*/
public String getName() {
	return _Name;
}

/**
Returns _ProductGroup_num
@return _ProductGroup_num
*/
public int getProductGroup_num() {
	return _ProductGroup_num;
}

/**
Returns _TSProduct_num
@return _TSProduct_num
*/
public int getTSProduct_num() {
	return _TSProduct_num;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Sets _Comment
@param Comment value to put into _Comment
*/
public void setComment(String Comment) {
	 _Comment = Comment;
}

/**
Sets _Identifier
@param Identifier value to put into _Identifier
*/
public void setIdentifier(String Identifier) {
	 _Identifier = Identifier;
}

/**
Sets _Name
@param Name value to put into _Name
*/
public void setName(String Name) {
	 _Name = Name;
}

/**
Sets _ProductGroup_num
@param ProductGroup_num value to put into _ProductGroup_num
*/
public void setProductGroup_num(int ProductGroup_num) {
	 _ProductGroup_num = ProductGroup_num;
}

/**
Sets _TSProduct_num
@param TSProduct_num value to put into _TSProduct_num
*/
public void setTSProduct_num(int TSProduct_num) {
	 _TSProduct_num = TSProduct_num;
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
	return "HydroBase_TSProduct{" 			+ "\n" + 
		"TSProduct_num:    " + _TSProduct_num + "\n" +
		"ProductGroup_num: " + _ProductGroup_num + "\n" +
		"Identifier:       '" + _Identifier + "'\n" + 
		"Name:             '" + _Name + "'\n" + 
		"Comment:          '" + _Comment + "'\n" +
		"User num:          " + _user_num + "\n}\n";
}

}
