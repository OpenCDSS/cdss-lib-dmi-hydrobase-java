// ----------------------------------------------------------------------------
// HydroBase_StructureDam.java - Class to hold data from the HydroBase dam 
//	and structure tables.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-06	J. Thomas Sapienza, RTi	Initial version from HBDam.
// 2003-02-20	JTS, RTi		Commented out X* data.
// 2003-02-24	JTS, RTi		- Corrected error in finalize() so that 
//					  super.finalize() gets called.
//					- Class renamed from HydroBase_Dam to
//					  HydroBase_DamStructure.
// 2004-02-09	JTS, RTi		Renamed to match the class-naming 
//					style for classes that extend other
//					classes.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the Hydrobase dam and structure tables.
*/
public class HydroBase_StructureDam 
extends HydroBase_Structure {

protected int _structure_num = 		DMIUtil.MISSING_INT;
protected int _res_num = 		DMIUtil.MISSING_INT;
protected String _akanames = 		DMIUtil.MISSING_STRING;
protected int  _staff_num = 		DMIUtil.MISSING_INT;
protected int _agency_num = 		DMIUtil.MISSING_INT;
protected String _owner_status = 	DMIUtil.MISSING_STRING;
protected int _damid = 			DMIUtil.MISSING_INT;
protected String _natid = 		DMIUtil.MISSING_STRING;
protected String _hazard_class = 	DMIUtil.MISSING_STRING;
protected String _aband_code = 		DMIUtil.MISSING_STRING;
protected String _remarks = 		DMIUtil.MISSING_STRING;
protected String _down_town = 		DMIUtil.MISSING_STRING;
protected double _down_town_dist = 	DMIUtil.MISSING_DOUBLE;
protected int _compl_year = 		DMIUtil.MISSING_INT;
protected String _purposes = 		DMIUtil.MISSING_STRING;
protected String _fed_land = 		DMIUtil.MISSING_STRING;
protected String _fed_regs = 		DMIUtil.MISSING_STRING;
protected String _strm_code = 		DMIUtil.MISSING_STRING;
protected String _dam_type = 		DMIUtil.MISSING_STRING;
protected int _length = 		DMIUtil.MISSING_INT;
protected double _height = 		DMIUtil.MISSING_DOUBLE;
protected double _str_height = 		DMIUtil.MISSING_DOUBLE;
protected double _hyd_height = 		DMIUtil.MISSING_DOUBLE;
protected double _crest_width = 	DMIUtil.MISSING_DOUBLE;
protected double _crest_elev = 		DMIUtil.MISSING_DOUBLE;
protected float _spillway_capacity =	DMIUtil.MISSING_FLOAT;
protected float _outlet_capacity = 	DMIUtil.MISSING_FLOAT;
protected String _forestid = 		DMIUtil.MISSING_STRING;
protected String _phase1 = 		DMIUtil.MISSING_STRING;
//protected String _xstream = 		DMIUtil.MISSING_STRING;
//protected int _xstrmno = 		DMIUtil.MISSING_INT;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_StructureDam() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_akanames = null;
	_owner_status = null;
	_natid = null;
	_hazard_class = null;
	_aband_code = null;
	_remarks = null;
	_down_town = null;
	_purposes = null;
	_fed_land = null;
	_fed_regs = null;
	_strm_code = null;
	_dam_type = null;
	_forestid = null;
	_phase1  = null;
//	_xstream  = null;
	_modified = null;
	super.finalize();
}

/**
Returns _aband_code
@return _aband_code
*/
public String 	getAband_code() {
	return _aband_code;
}

/**
Returns _agency_num
@return _agency_num
*/
public int 	getAgency_num() {
	return _agency_num;
}

/**
Returns _akanames
@return _akanames
*/
public String 	getAkanames() {
	return _akanames;
}

/**
Returns _compl_year
@return _compl_year
*/
public int 	getCompl_year() {
	return _compl_year;
}

/**
Returns _crest_elev
@return _crest_elev
*/
public double 	getCrest_elev() {
	return _crest_elev;
}

/**
Returns _crest_width
@return _crest_width
*/
public double 	getCrest_width() {
	return _crest_width;
}

/**
Returns _dam_type
@return _dam_type
*/
public String 	getDam_type() {
	return _dam_type;
}

/**
Returns _damid
@return _damid
*/
public int 	getDamid() {
	return _damid;
}

/**
Returns _down_town
@return _down_town
*/
public String 	getDown_town() {
	return _down_town;
}

/**
Returns _down_town_dist
@return _down_town_dist
*/
public double 	getDown_town_dist() {
	return _down_town_dist;
}

/**
Returns _fed_land
@return _fed_land
*/
public String 	getFed_land() {
	return _fed_land;
}

/**
Returns _fed_regs
@return _fed_regs
*/
public String 	getFed_regs() {
	return _fed_regs;
}

/**
Returns _forestid
@return _forestid
*/
public String 	getForestid() {
	return _forestid;
}

/**
Returns _hazard_class
@return _hazard_class
*/
public String 	getHazard_class() {
	return _hazard_class;
}

/**
Returns _height
@return _height
*/
public double 	getHeight() {
	return _height;
}

/**
Returns _hyd_height
@return _hyd_height
*/
public double 	getHyd_height() {
	return _hyd_height;
}

/**
Returns _length
@return _length
*/
public int 	getLength() {
	return _length;
}

/**
Returns _modified
@return _modified
*/
public Date 	getModified() {
	return _modified;
}

/**
Returns _natid
@return _natid
*/
public String 	getNatid() {
	return _natid;
}

/**
Returns _outlet_capacity
@return _outlet_capacity
*/
public float 	getOutlet_capacity() {
	return _outlet_capacity;
}

/**
Returns _owner_status
@return _owner_status
*/
public String 	getOwner_status() {
	return _owner_status;
}

/**
Returns _phase1
@return _phase1
*/
public String 	getPhase1() {
	return _phase1;
}

/**
Returns _purposes
@return _purposes
*/
public String 	getPurposes() {
	return _purposes;
}

/**
Returns _remarks
@return _remarks
*/
public String 	getRemarks() {
	return _remarks;
}

/**
Returns _res_num
@return _res_num
*/
public int 	getRes_num() {
	return _res_num;
}

/**
Returns _spillway_capacity
@return _spillway_capacity
*/
public float 	getSpillway_capacity() {
	return _spillway_capacity;
}

/**
Returns _staff_num
@return _staff_num
*/
public int  	getStaff_num() {
	return _staff_num;
}

/**
Returns _str_height
@return _str_height
*/
public double 	getStr_height() {
	return _str_height;
}

/**
Returns _strm_code
@return _strm_code
*/
public String 	getStrm_code() {
	return _strm_code;
}

/**
Returns _structure_num
@return _structure_num
*/
public int 	getStructure_num() {
	return _structure_num;
}

/**
Returns _user_num
@return _user_num
*/
public int 	getUser_num() {
	return _user_num;
}

/**
Returns _xstream
@return _xstream
*/
//public String 	getXstream() {
//	return _xstream;
//}

/**
Returns _xstrmno
@return _xstrmno
*/
//public int 	getXstrmno() {
//	return _xstrmno;
//








/**
Sets _aband_code
@param aband_code value to put into _aband_code
*/
public void 	setAband_code(String aband_code) {
	_aband_code = aband_code;
}

/**
Sets _agency_num
@param agency_num value to put into _agency_num
*/
public void 	setAgency_num(int agency_num) {
	_agency_num = agency_num;
}

/**
Sets _akanames
@param akanames value to put into _akanames
*/
public void 	setAkanames(String akanames) {
	_akanames = akanames;
}

/**
Sets _compl_year
@param compl_year value to put into _compl_year
*/
public void 	setCompl_year(int compl_year) {
	_compl_year = compl_year;
}

/**
Sets _crest_elev
@param crest_elev value to put into _crest_elev
*/
public void 	setCrest_elev(double crest_elev) {
	_crest_elev = crest_elev;
}

/**
Sets _crest_width
@param crest_width value to put into _crest_width
*/
public void 	setCrest_width(double crest_width) {
	_crest_width = crest_width;
}

/**
Sets _dam_type
@param dam_type value to put into _dam_type
*/
public void 	setDam_type(String dam_type) {
	_dam_type = dam_type;
}

/**
Sets _damid
@param damid value to put into _damid
*/
public void 	setDamid(int damid) {
	_damid = damid;
}

/**
Sets _down_town
@param down_town value to put into _down_town
*/
public void 	setDown_town(String down_town) {
	_down_town = down_town;
}

/**
Sets _down_town_dist
@param down_town_dist value to put into _down_town_dist
*/
public void 	setDown_town_dist(double down_town_dist) {
	_down_town_dist = down_town_dist;
}

/**
Sets _fed_land
@param fed_land value to put into _fed_land
*/
public void 	setFed_land(String fed_land) {
	_fed_land = fed_land;
}

/**
Sets _fed_regs
@param fed_regs value to put into _fed_regs
*/
public void 	setFed_regs(String fed_regs) {
	_fed_regs = fed_regs;
}

/**
Sets _forestid
@param forestid value to put into _forestid
*/
public void 	setForestid(String forestid) {
	_forestid = forestid;
}

/**
Sets _hazard_class
@param hazard_class value to put into _hazard_class
*/
public void 	setHazard_class(String hazard_class) {
	_hazard_class = hazard_class;
}

/**
Sets _height
@param height value to put into _height
*/
public void 	setHeight(double height) {
	_height = height;
}

/**
Sets _hyd_height
@param hyd_height value to put into _hyd_height
*/
public void 	setHyd_height(double hyd_height) {
	_hyd_height = hyd_height;
}

/**
Sets _length
@param length value to put into _length
*/
public void 	setLength(int length) {
	_length = length;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void 	setModified(Date modified) {
	_modified = modified;
}

/**
Sets _natid
@param natid value to put into _natid
*/
public void 	setNatid(String natid) {
	_natid = natid;
}

/**
Sets _outlet_capacity
@param outlet_capacity value to put into _outlet_capacity
*/
public void 	setOutlet_capacity(float outlet_capacity) {
	_outlet_capacity = outlet_capacity;
}

/**
Sets _owner_status
@param owner_status value to put into _owner_status
*/
public void 	setOwner_status(String owner_status) {
	_owner_status = owner_status;
}

/**
Sets _phase1
@param phase1 value to put into _phase1
*/
public void 	setPhase1(String phase1) {
	_phase1 = phase1;
}

/**
Sets _purposes
@param purposes value to put into _purposes
*/
public void 	setPurposes(String purposes) {
	_purposes = purposes;
}

/**
Sets _remarks
@param remarks value to put into _remarks
*/
public void 	setRemarks(String remarks) {
	_remarks = remarks;
}

/**
Sets _res_num
@param res_num value to put into _res_num
*/
public void 	setRes_num(int res_num) {
	_res_num = res_num;
}

/**
Sets _spillway_capacity
@param staff_num value to put into _spillway_capacity
*/
public void 	setSpillway_capacity(float staff_num) {
	_spillway_capacity = staff_num;
}

/**
Sets _staff_num
@param staff_num value to put into _staff_num
*/
public void  	setStaff_num(int staff_num) {
	_staff_num = staff_num;
}

/**
Sets _str_height
@param str_height value to put into _str_height
*/
public void 	setStr_height(double str_height) {
	_str_height = str_height;
}

/**
Sets _strm_code
@param strm_code value to put into _strm_code
*/
public void 	setStrm_code(String strm_code) {
	_strm_code = strm_code;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void 	setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void 	setUser_num(int user_num) {
	_user_num = user_num;
}

/**
Sets _xstream
@param xstream value to put into _xstream
*/
//public void 	setXstream(String xstream) {
//	_xstream = xstream;
//}

/**
Sets _xstrmno
@param xstrmno value to put into _xstrmno
*/
//public void 	setXstrmno(int xstrmno) {
//	_xstrmno = xstrmno;
//}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	
	return super.toString() + "\n" +
		"HydroBase_StructureDam {"		+ "\n" + 
		"Structure_num:     " + _structure_num + "\n" + 
		"Res_num:            " + _res_num + "\n" + 
		"Akanames:          '" + _akanames + "\n" + 
		"Staff_num:         " + _staff_num + "\n" + 
		"Agency_num:        " + _agency_num + "\n" + 
		
		"Owner_status:      '" + _owner_status + "'\n" + 
		"Damid:             " + _damid + "\n" + 
		"Natid:             '" + _natid + "'\n" + 
		"Hazard_class:      '" + _hazard_class + "'\n" + 
		"Aband_code:        '" + _aband_code + "'\n" + 

		"Remarks:           '" + _remarks + "'\n" + 
		"Down_town:         '" + _down_town + "'\n" + 
		"Down_town_dist:    '" + _down_town_dist + "'\n" + 
		"Compl_year:        " + _compl_year + "\n" + 
		"Purposes:          '" + _purposes + "'\n" + 

		"Fed_land:          '" + _fed_land + "'\n" + 
		"Fed_regs:          '" + _fed_regs + "'\n" + 
		"Strm_code:         '" + _strm_code + "'\n" + 
		"Dam_type:          '" + _dam_type + "'\n" + 
		"Length:            " + _length + "\n" + 

		"Height:            " + _height + "\n" + 
		"Str_height:        " + _str_height + "\n" + 
		"Hyd_height:        " + _hyd_height + "\n" + 
		"Crest_width:       " + _crest_width + "\n" + 
		"Crest_elev:        " + _crest_elev + "\n" + 

		"Spillway_capacity: " + _spillway_capacity + "\n" + 
		"Outlet_capacity:   " + _outlet_capacity + "\n" + 
		"Forestid:          '" + _forestid + "'\n" + 
		"Phase1:            '" + _phase1		 + "'\n" + 
//		"Xstream:           '" + _xstream + "'\n" + 

//		"Xstrmno:           " + _xstrmno + "\n" + 
		"Modified:          " + _modified + "\n" + 
		"User_num:          " + _user_num + "\n}\n";
}

}
