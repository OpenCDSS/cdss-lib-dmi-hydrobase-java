package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase Irrig_Summary table.  
*/
public class HydroBase_StructureIrrigSummary 
extends HydroBase_Structure {

protected int _structure_num = 		DMIUtil.MISSING_INT;
protected double _tia_gis = 		DMIUtil.MISSING_DOUBLE;
protected int _tia_gis_calyear = 	DMIUtil.MISSING_INT;
protected double _tia_div = 		DMIUtil.MISSING_DOUBLE;
protected int _tia_div_calyear = 	DMIUtil.MISSING_INT;
protected double _tia_struct = 		DMIUtil.MISSING_DOUBLE;
protected int _tia_struct_calyear =	DMIUtil.MISSING_INT;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_StructureIrrigSummary() {
	super();
}

/**
Copy constructor to fill in all the values of this object from a corresponding
view.  Used during unit tests.
*/
public HydroBase_StructureIrrigSummary(HydroBase_StructureView view) {
	_structure_num = view._structure_num;
	_wdwater_num = view._wdwater_num;
	_geoloc_num = view._geoloc_num;
	_div = view._div;
	_wd = view._wd;
	_id = view._id;
	_wdid = view._wdid;
	_ciu = view._ciu;
	_str_type = view._str_type;
	_STRTYPE = view._STRTYPE;
	_str_name = view._str_name;
	_est_capacity = view._est_capacity;
	_est_unit = view._est_unit;
	_dcr_capacity = view._dcr_capacity;
	_dcr_unit = view._dcr_unit;
	_transbsn = view._transbsn;
	_modified = view._modified;
	_user_num = view._user_num;
	_common_id = view._common_id;
	_xtia = view._xtia;
	_aquifer_num = view._aquifer_num;
	_stream_num = view._stream_num;
	_str_mile = view._str_mile;
	_structure_num = view._structure_num;
	_tia_gis = view._tia_gis;
	_tia_gis_calyear = view._tia_gis_calyear;
	_tia_div = view._tia_div;
	_tia_div_calyear = view._tia_div_calyear;
	_tia_struct = view._tia_struct;
	_tia_struct_calyear = view._tia_struct_calyear;
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_modified = null;	
	super.finalize();
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
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
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
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
	return "HydroBase_StructureIrrigSummary {"		+ "\n" + 
		"Structure_num:       " + _structure_num + "\n" + 
		"Tia_gis:             " + _tia_gis + "\n" + 
		"Tia_gis_calyear:     " + _tia_gis_calyear + "\n" + 
		"Tia_div:             " + _tia_div + "\n" + 
		"Tia_div_calyear:     " + _tia_div_calyear + "\n" + 
		"Tia_struct:          " + _tia_struct + "\n" + 
		"Tia_struct_calyear: " + _tia_struct_calyear + "\n" + 
		"Modified:            " + _modified + "\n" + 
		"User_num:            " + _user_num + "\n}\n";
}

}