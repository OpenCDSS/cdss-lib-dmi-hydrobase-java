// ----------------------------------------------------------------------------
// HydroBase_Rolodex.java - Class to hold data from the HydroBase rolodex table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-10	J. Thomas Sapienza, RTi	Initial version from HBRolodex.
// 2003-02-20	JTS, RTi		Commented out X* data.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase rolodex table.
*/
public class HydroBase_Rolodex 
extends DMIDataObject {

protected int _rolodex_num = 		DMIUtil.MISSING_INT;
protected String _last_name = 		DMIUtil.MISSING_STRING;
protected String _first_name = 		DMIUtil.MISSING_STRING;
protected String _middle_name = 	DMIUtil.MISSING_STRING;
protected String _title = 		DMIUtil.MISSING_STRING;
protected String _prefix = 		DMIUtil.MISSING_STRING;
protected String _suffix = 		DMIUtil.MISSING_STRING;
protected String _full_name = 		DMIUtil.MISSING_STRING;
protected String _address1 = 		DMIUtil.MISSING_STRING;
protected String _address2 = 		DMIUtil.MISSING_STRING;
protected String _city = 		DMIUtil.MISSING_STRING;
protected String _st = 			DMIUtil.MISSING_STRING;
protected String _zip = 		DMIUtil.MISSING_STRING;
//protected String _xaoo = 		DMIUtil.MISSING_STRING;
//protected String _xaoo2 = 		DMIUtil.MISSING_STRING;
protected String _org_name = 		DMIUtil.MISSING_STRING;
protected String _bond_co_name =	DMIUtil.MISSING_STRING;
protected String _lic_no = 		DMIUtil.MISSING_STRING;
protected String _lic_type = 		DMIUtil.MISSING_STRING;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_Rolodex() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_last_name = null;
	_first_name = null;
	_middle_name = null;
	_title = null;
	_prefix = null;
	_suffix = null;
	_full_name = null;
	_address1 = null;
	_address2 = null;
	_city = null;
	_st = null;
	_zip = null;
//	_xaoo = null;
//	_xaoo2 = null;
	_org_name = null;
	_bond_co_name = null;
	_lic_no = null;
	_lic_type = null;
	_modified = null;
	
	super.finalize();
}

/**
Returns _address1
@return _address1
*/
public String getAddress1() {
	return _address1;
}

/**
Returns _address2
@return _address2
*/
public String getAddress2() {
	return _address2;
}

/**
Returns _bond_co_name
@return _bond_co_name
*/
public String getBond_co_name() {
	return _bond_co_name;
}

/**
Returns _city
@return _city
*/
public String getCity() {
	return _city;
}

/**
Returns _first_name
@return _first_name
*/
public String getFirst_name() {
	return _first_name;
}

/**
Returns _full_name
@return _full_name
*/
public String getFull_name() {
	return _full_name;
}

/**
Returns _last_name
@return _last_name
*/
public String getLast_name() {
	return _last_name;
}

/**
Returns _lic_no
@return _lic_no
*/
public String getLic_no() {
	return _lic_no;
}

/**
Returns _lic_type
@return _lic_type
*/
public String getLic_type() {
	return _lic_type;
}

/**
Returns _middle_name
@return _middle_name
*/
public String getMiddle_name() {
	return _middle_name;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _org_name
@return _org_name
*/
public String getOrg_name() {
	return _org_name;
}

/**
Returns _prefix
@return _prefix
*/
public String getPrefix() {
	return _prefix;
}

/**
Returns _rolodex_num
@return _rolodex_num
*/
public int getRolodex_num() {
	return _rolodex_num;
}

/**
Returns _st
@return _st
*/
public String getST() {
	return _st;
}

/**
Returns _suffix
@return _suffix
*/
public String getSuffix() {
	return _suffix;
}

/**
Returns _title
@return _title
*/
public String getTitle() {
	return _title;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Returns _xaoo
@return _xaoo
*/
//public String getXaoo() {
//	return _xaoo;
//}

/**
Returns _Xaoo2
@return _Xaoo2
*/
//public String getXaoo2() {
//	return _xaoo2;
//}

/**
Returns _zip
@return _zip
*/
public String getZip() {
	return _zip;
}

/**
Sets _address1
@param address1 value to put into _address1
*/
public void setAddress1(String address1) {
	_address1 = address1;
}

/**
Sets _address2
@param address2 value to put into _address2
*/
public void setAddress2(String address2) {
	_address2 = address2;
}

/**
Sets _bond_co_name
@param bond_co_name value to put into _bond_co_name
*/
public void setBond_co_name(String bond_co_name) {
	_bond_co_name = bond_co_name;
}

/**
Sets _city
@param city value to put into _city
*/
public void setCity(String city) {
	_city = city;
}

/**
Sets _first_name
@param first_name value to put into _first_name
*/
public void setFirst_name(String first_name) {
	_first_name = first_name;
}

/**
Sets _full_name
@param full_name value to put into _full_name
*/
public void setFull_name(String full_name) {
	_full_name = full_name;
}

/**
Sets _last_name
@param last_name value to put into _last_name
*/
public void setLast_name(String last_name) {
	_last_name = last_name;
}

/**
Sets _lic_no
@param lic_no value to put into _lic_no
*/
public void setLic_no(String lic_no) {
	_lic_no = lic_no;
}

/**
Sets _lic_type
@param lic_type value to put into _lic_type
*/
public void setLic_type(String lic_type) {
	_lic_type = lic_type;
}

/**
Sets _middle_name
@param middle_name value to put into _middle_name
*/
public void setMiddle_name(String middle_name) {
	_middle_name = middle_name;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _org_name
@param org_name value to put into _org_name
*/
public void setOrg_name(String org_name) {
	_org_name = org_name;
}

/**
Sets _prefix
@param prefix value to put into _prefix
*/
public void setPrefix(String prefix) {
	_prefix = prefix;
}

/**
Sets _rolodex_num
@param rolodex_num value to put into _rolodex_num
*/
public void setRolodex_num(int rolodex_num) {
	_rolodex_num = rolodex_num;
}

/**
Sets _st
@param st value to put into _st
*/
public void setST(String st) {
	_st = st;
}

/**
Sets _suffix
@param suffix value to put into _suffix
*/
public void setSuffix(String suffix) {
	_suffix = suffix;
}

/**
Sets _title
@param title value to put into _title
*/
public void setTitle(String title) {
	_title = title;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/**
Sets _xaoo
@param xaoo value to put into _xaoo
*/
//public void setXaoo(String xaoo) {
//	_xaoo = xaoo;
//}

/**
Sets _Xaoo2
@param xaoo2 value to put into _Xaoo2
*/
//public void setXaoo2(String xaoo2) {
//	_xaoo2 = xaoo2;
//}

/**
Sets _zip
@param zip value to put into _zip
*/
public void setZip(String zip) {
	_zip = zip;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Rolodex {"		+ "\n" + 
		"Rolodex_num:  " + _rolodex_num + "\n" +
		"Last_name:    " + _last_name + "\n" +
		"First_name:   " + _first_name + "\n" +
		"Middle_name:  " + _middle_name + "\n" +
		"Title:        " + _title + "\n" +
		"Prefix:       " + _prefix + "\n" +
		"Suffic:       " + _suffix + "\n" +
		"Full_name:    " + _full_name + "\n" +
		"Address1:     " + _address1 + "\n" +
		"Address2:     " + _address2 + "\n" +
		"City:         " + _city + "\n" +
		"St:           " + _st + "\n" +
		"Zip:          " + _zip + "\n" +
//		"Xaoo:         " + _xaoo + "\n" +
//		"Xaoo2:        " + _xaoo2 + "\n" +
		"Org_name:     " + _org_name + "\n" +
		"Bond_co_name: " + _bond_co_name + "\n" +
		"Lic_no:       " + _lic_no + "\n" +
		"Lic_type:     " + _lic_type + "\n" +
		"Modified:     " + _modified + "\n" +
		"User_num:     " + _user_num + "\n}\n";
}


}
