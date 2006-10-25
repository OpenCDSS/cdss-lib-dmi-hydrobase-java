// ----------------------------------------------------------------------------
// HydroBase_WDWater.java - Class to hold data from the HydroBase 
//	wd_water and structure tables.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-12	J. Thomas Sapienza, RTi	Initial version from HBWDWater.
// 2003-04-01	JTS, RTi		Added WD field.
// 2005-02-15	JTS, RTi		Added _id, _div, _str_name
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase wd_water and structure tables.
*/
public class HydroBase_WDWater 
extends DMIDataObject {

protected int _wdwater_num =	DMIUtil.MISSING_INT;
protected int _wd = 		DMIUtil.MISSING_INT;
protected int _strno = 		DMIUtil.MISSING_INT;
protected String _strname = 	DMIUtil.MISSING_STRING;
protected String _admingrp = 	DMIUtil.MISSING_STRING;
protected int _strtribto = 	DMIUtil.MISSING_INT;
//protected int _xstrtribto = 	DMIUtil.MISSING_INT;
protected double _strmile = 	DMIUtil.MISSING_DOUBLE;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;
protected int _div = 		DMIUtil.MISSING_INT;
protected int _id = 		DMIUtil.MISSING_INT;
protected String _str_name = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_WDWater() {
	super();
}

/**
Copy constructor to fill in all the values of this object from a corresponding
object.  If 'true' is passed in, then the object passed in is the result from
doing a stored procedure query and not all the values will be filled (this is
only used during unit testing).
*/
public HydroBase_WDWater(HydroBase_WDWater wd, boolean junit) {
	_wdwater_num = wd._wdwater_num;
	_wd = wd._wd;
	_strno = wd._strno;
	_strname = wd._strname;
	_admingrp = wd._admingrp;
	_strtribto = wd._strtribto;
	_strmile = wd._strmile;
	if (!junit) {
		_modified = wd._modified;
		_user_num = wd._user_num;
		_div = wd._div;
		_id = wd._id;
		_str_name = wd._str_name;
	}
}

/**
Copy constructor.
*/
public HydroBase_WDWater(HydroBase_WDWater wd) {
	_wdwater_num = wd._wdwater_num;
	_wd = wd._wd;
	_strno = wd._strno;
	_strname = wd._strname;
	_admingrp = wd._admingrp;
	_strtribto = wd._strtribto;
	_strmile = wd._strmile;
	_modified = wd._modified;
	_user_num = wd._user_num;
	_div = wd._div;
	_id = wd._id;
	_str_name = wd._str_name;
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_admingrp = null;
	_modified = null;
	_strname = null;
	_str_name = null;
	
	super.finalize();
}

/**
Returns _admingrp
@return _admingrp
*/
public String getAdmingrp() {
	return _admingrp;
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
Returns _str_name
@return _str_name
*/
public String getStr_name() {
	return _str_name;
}

/**
Returns _strmile
@return _strmile
*/
public double getStrmile() {
	return _strmile;
}

/**
Returns _strname
@return _strname
*/
public String getStrname() {
	return _strname;
}

/**
Returns _strno
@return _strno
*/
public int getStrno() {
	return _strno;
}

/**
Returns _strtribto
@return _strtribto
*/
public int getStrtribto() {
	return _strtribto;
}

/**
Returns _wdwater_num
@return _wdwater_num
*/
public int getWdwater_num() {
	return _wdwater_num;
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
Returns _xstrtribto
@return _xstrtribto
*/
//public int getXstrtribto() {
//	return _xstrtribto;
//}

/**
Sets _admingrp
@param admingrp value to put in _admingrp
*/
public void setAdmingrp(String admingrp) {
	_admingrp = admingrp;
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
@param modified value to put in _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _str_name
@param str_name value to put in _strname
*/
public void setStr_name(String str_name) {
	_str_name = str_name;
}

/**
Sets _strmile
@param strmile value to put in _strmile
*/
public void setStrmile(double strmile) {
	_strmile = strmile;
}

/**
Sets _strname
@param strname value to put in _strname
*/
public void setStrname(String strname) {
	_strname = strname;
}

/**
Sets _strno
@param strno value to put in _strno
*/
public void setStrno(int strno) {
	_strno = strno;
}

/**
Sets _strtribto
@param strtribto value to put in _strtribto
*/
public void setStrtribto(int strtribto) {
	_strtribto = strtribto;
}

/**
Sets _wdwater_num
@param wdwater_num value to put in _wdwater_num
*/
public void setWdwater_num(int wdwater_num) {
	_wdwater_num = wdwater_num;
}

/**
Sets _user_num
@param user_num value to put in _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/**
Sets _wd
@param wd value to put in _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Sets _xstrtribto
@param xstrtribto value to put in _xstrtribto
*/
//public void setXstrtribto(int xstrtribto) {
//	_xstrtribto = xstrtribto;
//}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_WDWater {"		+ "\n" + 
		"Wdwater_num:   " + _wdwater_num + "\n" + 
		"WD:            " + _wd + "\n" + 
		"Strno:         " + _strno	 + "\n" + 
		"Strname:       " + _strname + "\n" + 
		"Admingrp:      " + _admingrp + "\n" + 
		"Strtribto:     " + _strtribto + "\n" + 
//		"Xstrtribto:    " + _xstrtribto + "\n" + 
		"Strmile:       " + _strmile + "\n" + 
		"Modified:      " + _modified + "\n" + 
		"User_num:      " + _user_num + "\n}\n";
}

}
