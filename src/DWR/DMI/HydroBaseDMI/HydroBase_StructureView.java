package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase rolodex, wdwater, irrig summary, 
structure decree summary, geoloc and structure tables.
*/
public class HydroBase_StructureView
extends HydroBase_StructureGeoloc 
implements Comparable {

// new fields in the Structure view
protected String _str_type = 		DMIUtil.MISSING_STRING;
protected String _str_type_desc = 	DMIUtil.MISSING_STRING;
protected String _strtype = 		DMIUtil.MISSING_STRING;

// fields from the rolodex table

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

// WD Water fields

protected int _strno = 			DMIUtil.MISSING_INT;
protected int _strtribto = 		DMIUtil.MISSING_INT;

// Irrig Summary fields

protected double _tia_gis = 		DMIUtil.MISSING_DOUBLE;
protected int _tia_gis_calyear = 	DMIUtil.MISSING_INT;
protected double _tia_div = 		DMIUtil.MISSING_DOUBLE;
protected int _tia_div_calyear = 	DMIUtil.MISSING_INT;
protected double _tia_struct = 		DMIUtil.MISSING_DOUBLE;
protected int _tia_struct_calyear = 	DMIUtil.MISSING_INT;

// structure decree summary fields

protected double _dcr_rate_abs = 	DMIUtil.MISSING_DOUBLE;
protected double _dcr_rate_cond = 	DMIUtil.MISSING_DOUBLE;
protected double _dcr_rate_APEX_abs = 	DMIUtil.MISSING_DOUBLE;
protected double _dcr_rate_APEX_cond = 	DMIUtil.MISSING_DOUBLE;
protected double _dcr_rate_total = 	DMIUtil.MISSING_DOUBLE;
protected double _dcr_vol_abs = 	DMIUtil.MISSING_DOUBLE;
protected double _dcr_vol_cond = 	DMIUtil.MISSING_DOUBLE;
protected double _dcr_vol_APEX_abs = 	DMIUtil.MISSING_DOUBLE;
protected double _dcr_vol_APEX_cond = 	DMIUtil.MISSING_DOUBLE;
protected double _dcr_vol_total = 	DMIUtil.MISSING_DOUBLE;

// Irrig Summary TS fields

protected int _cal_year = 		DMIUtil.MISSING_INT;
protected String _land_use = 		DMIUtil.MISSING_STRING;
protected double _acres_total = 	DMIUtil.MISSING_DOUBLE;
protected double _acres_by_drip = 	DMIUtil.MISSING_DOUBLE;
protected double _acres_by_flood = 	DMIUtil.MISSING_DOUBLE;
protected double _acres_by_furrow = 	DMIUtil.MISSING_DOUBLE;
protected double _acres_by_sprinkler =	DMIUtil.MISSING_DOUBLE;
protected double _acres_by_groundwater= DMIUtil.MISSING_DOUBLE;

// Only used by software (StateDMI) and are not in HydroBase.  Most of the time
// this will not be set.

protected String _structure_id =	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_StructureView() {
	super();
}

public int compareTo(Object o) {
	HydroBase_StructureView s = (HydroBase_StructureView)o;

	if (_wd < s._wd) {
		return -1;
	}
	else if (_wd > s._wd) {
		return 1;
	}

	if (_id < s._id) {
		return -1;
	}
	else if (_id > s._id) {
		return 1;
	}

	int i = _str_name.compareTo(s._str_name);

	return i;
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
	_str_type = null;
	_str_type_desc = null;
	_strtype = null;

	_structure_id = null;
	
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
Returns _acres_by_drip
@return _acres_by_drip
*/
public double getAcres_by_drip() {
	return _acres_by_drip;
}

/**
Returns _acres_by_flood
@return _acres_by_flood
*/
public double getAcres_by_flood() {
	return _acres_by_flood;
}

/**
Returns _acres_by_groundwater
@return _acres_by_groundwater
*/
public double getAcres_by_groundwater() {
	return _acres_by_groundwater;
}

/**
Returns _acres_by_furrow
@return _acres_by_furrow
*/
public double getAcres_by_furrow() {
	return _acres_by_furrow;
}

/**
Returns _acres_by_sprinkler
@return _acres_by_sprinkler
*/
public double getAcres_by_sprinkler() {
	return _acres_by_sprinkler;
}

/**
Returns _acres_total
@return _acres_total
*/
public double getAcres_total() {
	return _acres_total;
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
Returns _cal_year
@return _cal_year
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _city
@return _city
*/
public String getCity() {
	return _city;
}

/**
Returns _dcr_rate_abs
@return _dcr_rate_abs
*/
public double getDcr_rate_abs() {
	return _dcr_rate_abs;
}

/**
Returns _dcr_rate_cond
@return _dcr_rate_cond
*/
public double getDcr_rate_cond() {
	return _dcr_rate_cond;
}

/**
Returns _dcr_rate_APEX_abs
@return _dcr_rate_APEX_abs
*/
public double getDcr_rate_APEX_abs() {
	return _dcr_rate_APEX_abs;
}

/**
Returns _dcr_rate_APEX_cond
@return _dcr_rate_APEX_cond
*/
public double getDcr_rate_APEX_cond() {
	return _dcr_rate_APEX_cond;
}

/**
Returns _dcr_rate_total
@return _dcr_rate_total
*/
public double getDcr_rate_total() {
	return _dcr_rate_total;
}

/**
Returns _dcr_vol_abs
@return _dcr_vol_abs
*/
public double getDcr_vol_abs() {
	return _dcr_vol_abs;
}

/**
Returns _dcr_vol_cond
@return _dcr_vol_cond
*/
public double getDcr_vol_cond() {
	return _dcr_vol_cond;
}

/**
Returns _dcr_vol_APEX_abs
@return _dcr_vol_APEX_abs
*/
public double getDcr_vol_APEX_abs() {
	return _dcr_vol_APEX_abs;
}

/**
Returns _dcr_vol_APEX_cond
@return _dcr_vol_APEX_cond
*/
public double getDcr_vol_APEX_cond() {
	return _dcr_vol_APEX_cond;
}

/**
Returns _dcr_vol_total
@return _dcr_vol_total
*/
public double getDcr_vol_total() {
	return _dcr_vol_total;
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
Returns _land_use
@return _land_use
*/
public String getLand_use() {
	return _land_use;
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
Returns _str_type
@return _str_type
*/
public String getStr_type() {
	return _str_type;
}

/**
Returns _str_type_desc
@return _str_type_desc
*/
public String getStr_type_desc() {
	return _str_type_desc;
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
Returns _strtype
@return _strtype
*/
public String getStrtype() {
	return _strtype;
}

/**
Returns _structure_id
@return _structure_id
*/
public String getStructure_id() {
	return _structure_id;
}

/**
Returns _suffix
@return _suffix
*/
public String getSuffix() {
	return _suffix;
}

/**
Returns _tia_div
@return _tia_div
*/
public double getTia_div() {
	return _tia_div;
}

/**
Returns _tia_div_calyear
@return _tia_div_calyear
*/
public int getTia_div_calyear() {
	return _tia_div_calyear;
}

/**
Returns _tia_gis
@return _tia_gis
*/
public double getTia_gis() {
	return _tia_gis;
}

/**
Returns _tia_gis_calyear
@return _tia_gis_calyear
*/
public int getTia_gis_calyear() {
	return _tia_gis_calyear;
}

/**
Returns _tia_struct
@return _tia_struct
*/
public double getTia_struct() {
	return _tia_struct;
}

/**
Returns _tia_struct_calyear
@return _tia_struct_calyear
*/
public int getTia_struct_calyear() {
	return _tia_struct_calyear;
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
Sets _acres_by_drip
@param acres_by_drip value to put into _acres_by_drip
*/
public void setAcres_by_drip(double acres_by_drip) {
	_acres_by_drip = acres_by_drip;
}

/**
Sets _acres_by_flood
@param acres_by_flood value to put into _acres_by_flood
*/
public void setAcres_by_flood(double acres_by_flood) {
	_acres_by_flood = acres_by_flood;
}

/**
Sets _acres_by_groundwater
@param acres_by_groundwater value to put into _acres_by_groundwater
*/
public void setAcres_by_groundwater(double acres_by_groundwater) {
	_acres_by_groundwater = acres_by_groundwater;
}

/**
Sets _acres_by_furrow
@param acres_by_furrow value to put into _acres_by_furrow
*/
public void setAcres_by_furrow(double acres_by_furrow) {
	_acres_by_furrow = acres_by_furrow;
}

/**
Sets _acres_by_sprinkler
@param acres_by_sprinkler value to put into _acres_by_sprinkler
*/
public void setAcres_by_sprinkler(double acres_by_sprinkler) {
	_acres_by_sprinkler = acres_by_sprinkler;
}

/**
Sets _acres_total
@param acres_total value to put into _acres_total
*/
public void setAcres_total(double acres_total) {
	_acres_total = acres_total;
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
Sets _cal_year
@param cal_year value to put into _cal_year
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _city
@param city value to put into _city
*/
public void setCity(String city) {
	_city = city;
}

/**
Sets _dcr_rate_abs
@param dcr_rate_abs value to put into _dcr_rate_abs
*/
public void setDcr_rate_abs(double dcr_rate_abs) {
	_dcr_rate_abs = dcr_rate_abs;
}

/**
Sets _dcr_rate_cond
@param dcr_rate_cond value to put into _dcr_rate_cond
*/
public void setDcr_rate_cond(double dcr_rate_cond) {
	_dcr_rate_cond = dcr_rate_cond;
}

/**
Sets _dcr_rate_APEX_abs
@param dcr_rate_APEX_abs value to put into _dcr_rate_APEX_abs
*/
public void setDcr_rate_APEX_abs(double dcr_rate_APEX_abs) {
	_dcr_rate_APEX_abs = dcr_rate_APEX_abs;
}

/**
Sets _dcr_rate_APEX_cond
@param dcr_rate_APEX_cond value to put into _dcr_rate_APEX_cond
*/
public void setDcr_rate_APEX_cond(double dcr_rate_APEX_cond) {
	_dcr_rate_APEX_cond = dcr_rate_APEX_cond;
}

/**
Sets _dcr_rate_total
@param dcr_rate_total the value to put into _dcr_rate_total
*/
public void setDcr_rate_total(double dcr_rate_total) {
	_dcr_rate_total = dcr_rate_total;
}

/**
Sets _dcr_vol_abs
@param dcr_vol_abs value to put into _dcr_vol_abs
*/
public void setDcr_vol_abs(double dcr_vol_abs) {
	_dcr_vol_abs = dcr_vol_abs;
}

/**
Sets _dcr_vol_cond
@param dcr_vol_cond value to put into _dcr_vol_cond
*/
public void setDcr_vol_cond(double dcr_vol_cond) {
	_dcr_vol_cond = dcr_vol_cond;
}

/**
Sets _dcr_vol_APEX_abs
@param dcr_vol_APEX_abs value to put into _dcr_vol_APEX_abs
*/
public void setDcr_vol_APEX_abs(double dcr_vol_APEX_abs) {
	_dcr_vol_APEX_abs = dcr_vol_APEX_abs;
}

/**
Sets _dcr_vol_APEX_cond
@param dcr_vol_APEX_cond value to put into _dcr_vol_APEX_cond
*/
public void setDcr_vol_APEX_cond(double dcr_vol_APEX_cond) {
	_dcr_vol_APEX_cond = dcr_vol_APEX_cond;
}

/**
Sets _dcr_vol_total
@param dcr_vol_total the value to put into _dcr_vol_total
*/
public void setDcr_vol_total(double dcr_vol_total) {
	_dcr_vol_total = dcr_vol_total;
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
Sets _land_use
@param land_use value to put into _land_use
*/
public void setLand_use(String land_use) {
	_land_use = land_use;
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
Sets _str_Type
@param str_type the value to put into _str_type
*/
public void setStr_type(String str_type) {
	_str_type = str_type;
}

/**
Sets _str_type_desc
@param str_type_desc the value to put into _str_type_desc
*/
public void setStr_type_desc(String str_type_desc) {
	_str_type_desc = str_type_desc;
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
Sets _strtype
@param strtype the value to put into _strtype
*/
public void setStrtype(String strtype) {
	_strtype = strtype;
}

/**
Sets _structure_id
@param structure_id value to put into _structure_id
*/
public void setStructure_id(String structure_id) {
	_structure_id = structure_id;
}

/**
Sets _suffix
@param suffix value to put into _suffix
*/
public void setSuffix(String suffix) {
	_suffix = suffix;
}

/**
Sets _tia_div
@param tia_div value to put into _tia_div
*/
public void setTia_div(double tia_div) {
	_tia_div = tia_div;
}

/**
Sets _tia_div_calyear
@param tia_div_calyear value to put into _tia_div_calyear
*/
public void setTia_div_calyear(int tia_div_calyear) {
	_tia_div_calyear = tia_div_calyear;
}

/**
Sets _tia_gis
@param tia_gis value to put into _tia_gis
*/
public void setTia_gis(double tia_gis) {
	_tia_gis = tia_gis;
}

/**
Sets _tia_gis_calyear
@param tia_gis_calyear value to put into _tia_gis_calyear
*/
public void setTia_gis_calyear(int tia_gis_calyear) {
	_tia_gis_calyear = tia_gis_calyear;
}

/**
Sets _tia_struct
@param tia_struct value to put into _tia_struct
*/
public void setTia_struct(double tia_struct) {
	_tia_struct = tia_struct;
}

/**
Sets _tia_struct_calyear
@param tia_struct_calyear value to put into _tia_struct_calyear
*/
public void setTia_struct_calyear(int tia_struct_calyear) {
	_tia_struct_calyear = tia_struct_calyear;
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
	return super.toString()
		+ "HydroBase_Rolodex {"		+ "\n" + 
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