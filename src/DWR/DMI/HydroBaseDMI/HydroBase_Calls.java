// HydroBase_Calls - Class to hold data from the HydroBase calls table.

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
Class to store data from the HydroBase calls table.
*/
public class HydroBase_Calls 
extends DMIDataObject
implements Comparable<HydroBase_Calls> {

protected int _call_num = 		DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected int _wdwater_num = 		DMIUtil.MISSING_INT;
protected int _net_num = 		DMIUtil.MISSING_INT;
protected Date _date_time_set = 	DMIUtil.MISSING_DATE;
protected Date _date_time_released = 	DMIUtil.MISSING_DATE;
protected double _adminno = 		DMIUtil.MISSING_DOUBLE;
protected String _set_comments = 	DMIUtil.MISSING_STRING;
protected String _release_comments = 	DMIUtil.MISSING_STRING;
protected String _districts_affected =	DMIUtil.MISSING_STRING;
protected int _release_call_num = 	DMIUtil.MISSING_INT;
protected String _deleted = 		DMIUtil.MISSING_STRING;
protected int _div = 			DMIUtil.MISSING_INT;
protected Date _archive_date = 		DMIUtil.MISSING_DATE;
protected String _strname = 		DMIUtil.MISSING_STRING;
protected int _strtribto = 		DMIUtil.MISSING_INT;
protected int _wd = 			DMIUtil.MISSING_INT;
protected int _id = 			DMIUtil.MISSING_INT;
protected String _str_name = 		DMIUtil.MISSING_STRING;
protected Date _apro_date = 		DMIUtil.MISSING_DATE;
protected String _dcr_amt = 		DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_Calls() {
	super();
}

/**
Copy constructor.
*/
public HydroBase_Calls(HydroBase_Calls call) {
	_call_num = 		call._call_num;
	_structure_num = 	call._structure_num;
	_wdwater_num = 		call._wdwater_num;
	_net_num = 		call._net_num;
	_date_time_set = 	copyDate(call._date_time_set);
	_date_time_released = 	copyDate(call._date_time_released);
	_adminno = 		call._adminno;
	_set_comments = 	copyString(call._set_comments);
	_release_comments = 	copyString(call._release_comments);
	_districts_affected =	copyString(call._districts_affected);
	_release_call_num = 	call._release_call_num;
	_deleted = 		copyString(call._deleted);
	_div = 			call._div;
	_archive_date = 	copyDate(call._archive_date);
	_strname = 		copyString(call._strname);
	_strtribto = 		call._strtribto;
	_wd = 			call._wd;
	_id = 			call._id;
	_str_name = 		copyString(call._str_name);
	_apro_date = 		copyDate(call._apro_date);
	_dcr_amt = 		copyString(call._dcr_amt);
}

/**
Compares this object with the specified object for order.
@param o the object to compare against.
@return -1 if this object sorts less than the specified object.  0 if the
two sort the same.  1 if this object sorts greater than the specified object.
*/
public int compareTo(HydroBase_Calls c) {
	if (_wd < c._wd) {
		return -1;
	}
	else if (_wd > c._wd) {
		return 1;
	}

	if (_id < c._id) {
		return -1;
	}
	else if (_id > c._id) {
		return 1;
	}

	return 0;
}

/**
Copies a date object for the copy constructor.
*/
private static Date copyDate(Date date) {
	if (date == null) {
		return null;
	}
	return new Date(date.getTime());
}

/**
Copies a String object for the copy constructor.
*/
private static String copyString(String s) {
	if (s == null) {
		return null;
	}
	return new String(s);
}

/**
Returns _adminno
@return _adminno
*/
public double getAdminno() {
	return _adminno;
}

/**
Returns _apro_date
@return _apro_date
*/
public Date getApro_date() {
	return _apro_date;
}

/**
Returns _archive_date
@return _archive_date
*/
public Date getArchive_date() {
	return _archive_date;
}

/**
Returns _call_num
@return _call_num
*/
public int getCall_num() {
	return _call_num;
}

/**
Returns _date_time_released
@return _date_time_released
*/
public Date getDate_time_released() {
	return _date_time_released;
}

/**
Returns _date_time_set
@return _date_time_set
*/
public Date getDate_time_set() {
	return _date_time_set;
}

/**
Returns _dcr_amt
@return _dcr_amt
*/
public String getDcr_amt() {
	return _dcr_amt;
}

/**
Returns _deleted
@return _deleted
*/
public String getDeleted() {
	return _deleted;
}

/**
Returns _districts_affected
@return _districts_affected
*/
public String getDistricts_affected() {
	return _districts_affected;
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
Returns _net_num
@return _net_num
*/
public int getNet_num() {
	return _net_num;
}

/**
Returns _release_call_num
@return _release_call_num
*/
public int getRelease_call_num() {
	return _release_call_num;
}

/**
Returns _release_comments
@return _release_comments
*/
public String getRelease_comments() {
	return _release_comments;
}

/**
Returns _set_comments
@return _set_comments
*/
public String getSet_comments() {
	return _set_comments;
}

/**
Returns _str_name
@return _str_name
*/
public String getStr_name() {
	return _str_name;
}

/**
Returns _strname
@return _strname
*/
public String getStrname() {
	return _strname;
}

/**
Returns _strtribto
@return _strtribto
*/
public int getStrtribto() {
	return _strtribto;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Returns _wdwater_num
@return _wdwater_num
*/
public int getWdwater_num() {
	return _wdwater_num;
}


/**
Sets _adminno
@param adminno value to put into _adminno
*/
public void setAdminno(double adminno) {
	_adminno = adminno;
}

/**
Sets _apro_date
@param apro_date value to put into _apro_date
*/
public void setApro_date(Date apro_date) {
	 _apro_date = apro_date;
}

/**
Sets _archive_date
@param archive_date value to put into _archive_date
*/
public void setArchive_date(Date archive_date) {
	 _archive_date = archive_date;
}

/**
Sets _call_num
@param call_num value to put into _call_num
*/
public void setCall_num(int call_num) {
	 _call_num = call_num;
}

/**
Sets _date_time_released
@param date_time_released value to put into _date_time_released
*/
public void setDate_time_released(Date date_time_released) {
	 _date_time_released = date_time_released;
}

/**
Sets _date_time_set
@param date_time_set value to put into _date_time_set
*/
public void setDate_time_set(Date date_time_set) {
	 _date_time_set = date_time_set;
}

/**
Sets _dcr_amt
@param dcr_amt value to put into _dcr_amt
*/
public void setDcr_amt(String dcr_amt) {
	 _dcr_amt = dcr_amt;
}

/**
Sets _deleted
@param deleted value to put into _deleted
*/
public void setDeleted(String deleted) {
	 _deleted = deleted;
}

/**
Sets _districts_affected
@param districts_affected value to put into _districts_affected
*/
public void setDistricts_affected(String districts_affected) {
	 _districts_affected = districts_affected;
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
Sets _net_num
@param net_num value to put into _net_num
*/
public void setNet_num(int net_num) {
	 _net_num = net_num;
}

/**
Sets _release_call_num
@param release_call_num value to put into _release_call_num
*/
public void setRelease_call_num(int release_call_num) {
	 _release_call_num = release_call_num;
}

/**
Sets _release_comments
@param release_comments value to put into _release_comments
*/
public void setRelease_comments(String release_comments) {
	 _release_comments = release_comments;
}

/**
Sets _set_comments
@param set_comments value to put into _set_comments
*/
public void setSet_comments(String set_comments) {
	 _set_comments = set_comments;
}

/**
Sets _str_name
@param str_name value to put into _str_name
*/
public void setStr_name(String str_name) {
	 _str_name = str_name;
}

/**
Sets _strname
@param strname value to put into _strname
*/
public void setStrname(String strname) {
	 _strname = strname;
}

/**
Sets _strtribto
@param strtribto value to put into _strtribto
*/
public void setStrtribto(int strtribto) {
	 _strtribto = strtribto;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	 _structure_num = structure_num;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	 _wd = wd;
}

/**
Sets _wdwater_num
@param wdwater_num value to put into _wdwater_num
*/
public void setWdwater_num(int wdwater_num) {
	 _wdwater_num = wdwater_num;
}



/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Calls {"			+ "\n" + 
		"Call_num:           " + _call_num + "\n" + 
		"Structure_num:      " + _structure_num + "\n" + 
		"WDWater_num:        " + _wdwater_num + "\n" + 
		"Net_num:            " + _net_num + "\n" + 
		"Date_time_set:      " + _date_time_set + "\n" + 
		"Date_time_released: " + _date_time_released + "\n" + 
		"Adminno:            " + _adminno + "\n" + 
		"Set_comments:       '" + _set_comments + "'\n" + 
		"Release_comments:   '" + _release_comments + "'\n" + 
		"Districts_affected: '" + _districts_affected	+ "'\n" + 
		"Release_call_num:   " + _release_call_num + "\n" + 
		"Deleted:            '" + _deleted + "'\n" + 
		"Div:                " + _div + "\n" + 
		"Archive_date:       " + _archive_date + "\n" + 
		"Strname:            '" + _strname + "'\n" + 
		"Strtribto:          " + _strtribto + "\n" + 
		"WD:                 " + _wd + "\n" + 
		"ID:                 " + _id + "\n" + 
		"Str_name:           '" + _str_name + "'\n" + 
		"Apro_date:          " + _apro_date + "\n" + 
		"Dcr_amt:            '" + _dcr_amt + "'\n}\n";
	
}

}
