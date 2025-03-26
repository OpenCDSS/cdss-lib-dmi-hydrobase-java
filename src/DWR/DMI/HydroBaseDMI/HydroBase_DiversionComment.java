// HydroBase_DiversionComment - Class to hold data from the HydroBase diversion_comment table.

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
Class to store data from the HydroBase diversion_comment table.
*/
public class HydroBase_DiversionComment
extends DMIDataObject {

protected int _meas_num = 		DMIUtil.MISSING_INT;
protected Date _comm_date = 		DMIUtil.MISSING_DATE;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected String _not_used = 		DMIUtil.MISSING_STRING;
protected String _diver_comment =	DMIUtil.MISSING_STRING;
protected float _acres_irrig = 		DMIUtil.MISSING_FLOAT;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

protected int _wd =	 		DMIUtil.MISSING_INT;
protected int _id = 			DMIUtil.MISSING_INT;
protected int _div = 			DMIUtil.MISSING_INT;

protected int _irr_year = 		DMIUtil.MISSING_INT;


/**
Constructor.
*/
public HydroBase_DiversionComment() {
	super();
}

/**
Returns _acres_irrig
@return _acres_irrig
*/
public float getAcres_irrig() {
	return _acres_irrig;
}

/**
Returns _comm_date
@return _comm_date
*/
public Date getComm_date() {
	return _comm_date;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _diver_comment
@return _diver_comment
*/

public String getDiver_comment() {
	return _diver_comment;
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
}

/**
Returns _irr_year
@return _irr_year
*/
public int getIrr_year() {
	return _irr_year;
}

/**
Returns _meas_num
@return _meas_num
*/

public int getMeas_num() {
	return _meas_num;
}

/**
Returns _modified
@return _modified
*/

public Date getModified() {
	return _modified;
}

/**
Returns _not_used
@return _not_used
*/

public String getNot_used() {
	return _not_used;
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
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Sets _acres_irrig
@param acres_irrig value to put into _acres_irrig
*/
public void setAcres_irrig(float acres_irrig) {
	_acres_irrig = acres_irrig;
}

/**
Sets _comm_date
@param comm_date value to put into _comm_date
*/
public void setComm_date(Date comm_date) {
	_comm_date = comm_date;
}

/**
Sets _div
@param div the value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _diver_comment
@param diver_comment value to put into _diver_comment
*/

public void setDiver_comment(String diver_comment) {
	_diver_comment = diver_comment;
}

/**
Sets _id
@param id value to put into _id
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _irr_year
@param irr_year value to put into _irr_year
*/
public void setIrr_year(int irr_year) {
	_irr_year = irr_year;
}

/**
Sets _meas_num
@param meas_num value to put into _meas_num
*/

public void setMeas_num(int meas_num) {
	_meas_num = meas_num;
}

/**
Sets _modified
@param modified value to put into _modified
*/

public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _not_used
@param not_used value to put into _not_used
*/

public void setNot_used(String not_used) {
	_not_used = not_used;
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
	return "HydroBase_DiversionComment {"		+ "\n" +
		"Meas_num:      " + _meas_num + "\n" +
		"Comm_date:     " + _comm_date + "\n" +
		"Structure_num: " + _structure_num + "\n" +
		"Not_used:      '" + _not_used + "'\n" +
		"Diver_comment: '" + _diver_comment + "'\n" +
		"Acres_irrig:   " + _acres_irrig + "\n" +
		"ID:            " + _id + "\n" +
		"WD:            " + _wd + "\n" +
		"Div:           " + _div + "\n" +
		"Modified:      " + _modified + "\n" +
		"User_num:      " + _user_num + "\n}\n";
}

}
