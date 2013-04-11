package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

/**
Class to store data from the the HydroBase unpermitted_wells and structure tables.
*/
public class HydroBase_StructureUnpermittedWells 
extends HydroBase_Structure {

protected String _apinumber = 		DMIUtil.MISSING_STRING;
protected String _oiloper = 		DMIUtil.MISSING_STRING;
protected String _aquifer1 = 		DMIUtil.MISSING_STRING;
protected String _aquifer2 = 		DMIUtil.MISSING_STRING;
protected int _tperf = 			DMIUtil.MISSING_INT;
protected int _bperf = 			DMIUtil.MISSING_INT;
protected int _depth = 			DMIUtil.MISSING_INT;
protected int _elev = 			DMIUtil.MISSING_INT;
protected String _comment = 		DMIUtil.MISSING_STRING;
protected String _usgs_id = 		DMIUtil.MISSING_STRING;
protected String _local_well_num = 	DMIUtil.MISSING_STRING;
protected String _usbr_id = 		DMIUtil.MISSING_STRING;
protected String _sp_coord_n = 		DMIUtil.MISSING_STRING;
protected String _sp_coord_e = 		DMIUtil.MISSING_STRING;
protected String _usgs_net = 		DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_StructureUnpermittedWells() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_apinumber = null;
	_oiloper = null;
	_aquifer1 = null;
	_aquifer2 = null;
	_comment = null;
	_usgs_id = null;
	_local_well_num = null;
	_usbr_id = null;
	_sp_coord_n = null;
	_sp_coord_e = null;
	_usgs_net = null;
	
	super.finalize();
}

/**
Returns _apinumber
@return _apinumber
*/
public String getApinumber() {
	return _apinumber;
}

/**
Returns _aquifer1
@return _aquifer1
*/
public String getAquifer1() {
	return _aquifer1;
}

/**
Returns _aquifer2
@return _aquifer2
*/
public String getAquifer2() {
	return _aquifer2;
}

/**
Returns _bperf
@return _bperf
*/
public int getBperf() {
	return _bperf;
}

/**
Returns _comment
@return _comment
*/
public String getComment() {
	return _comment;
}

/**
Returns _depth
@return _depth
*/
public int getDepth() {
	return _depth;
}

/**
Returns _elev
@return _elev
*/
public int getElev() {
	return _elev;
}

/**
Returns _local_well_num
@return _local_well_num
*/
public String getLocal_well_num() {
	return _local_well_num;
}

/**
Returns _oiloper
@return _oiloper
*/
public String getOiloper() {
	return _oiloper;
}

/**
Returns _sp_coord_e
@return _sp_coord_e
*/
public String getSp_coord_e() {
	return _sp_coord_e;
}

/**
Returns _sp_coord_n
@return _sp_coord_n
*/
public String getSp_coord_n() {
	return _sp_coord_n;
}

/**
Returns _tperf
@return _tperf
*/
public int getTperf() {
	return _tperf;
}

/**
Returns _usbr_id
@return _usbr_id
*/
public String getUsbr_id() {
	return _usbr_id;
}

/**
Returns _usgs_id
@return _usgs_id
*/
public String getUsgs_id() {
	return _usgs_id;
}

/**
Returns _usgs_net
@return _usgs_net
*/
public String getUsgs_net() {
	return _usgs_net;
}


/**
Sets _apinumber
@param apinumber value to put into _apinumber
*/
public void setApinumber(String apinumber) {
	_apinumber = apinumber;
}

/**
Sets _aquifer1
@param aquifer1 value to put into _aquifer1
*/
public void setAquifer1(String aquifer1) {
	_aquifer1 = aquifer1;
}

/**
Sets _aquifer2
@param aquifer2 value to put into _aquifer2
*/
public void setAquifer2(String aquifer2) {
	_aquifer2 = aquifer2;
}

/**
Sets _bperf
@param bperf value to put into _bperf
*/
public void setBperf(int bperf) {
	_bperf = bperf;
}

/**
Sets _comment
@param comment value to put into _comment
*/
public void setComment(String comment) {
	_comment = comment;
}

/**
Sets _depth
@param depth value to put into _depth
*/
public void setDepth(int depth) {
	_depth = depth;
}

/**
Sets _elev
@param elev value to put into _elev
*/
public void setElev(int elev) {
	_elev = elev;
}

/**
Sets _local_well_num
@param local_well_num value to put into _local_well_num
*/
public void setLocal_well_num(String local_well_num) {
	_local_well_num = local_well_num;
}

/**
Sets _oiloper
@param oiloper value to put into _oiloper
*/
public void setOiloper(String oiloper) {
	_oiloper = oiloper;
}

/**
Sets _sp_coord_e
@param sp_coord_e value to put into _sp_coord_e
*/
public void setSp_coord_e(String sp_coord_e) {
	_sp_coord_e = sp_coord_e;
}

/**
Sets _sp_coord_n
@param sp_coord_n value to put into _sp_coord_n
*/
public void setSp_coord_n(String sp_coord_n) {
	_sp_coord_n = sp_coord_n;
}

/**
Sets _tperf
@param tperf value to put into _tperf
*/
public void setTperf(int tperf) {
	_tperf = tperf;
}

/**
Sets _usbr_id
@param usbr_id value to put into _usbr_id
*/
public void setUsbr_id(String usbr_id) {
	_usbr_id = usbr_id;
}

/**
Sets _usgs_id
@param usgs_id value to put into _usgs_id
*/
public void setUsgs_id(String usgs_id) {
	_usgs_id = usgs_id;
}

/**
Sets _usgs_net
@param usgs_net value to put into _usgs_net
*/
public void setUsgs_net(String usgs_net) {
	_usgs_net = usgs_net;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_StructureUnpermittedWells {"		+ "\n" + 
		"Apinumber:      " + _apinumber + "\n" + 
		"Oiloper:        " + _oiloper + "\n" + 
		"Aquifer1:       " + _aquifer1 + "\n" + 
		"Aquifer2:       " + _aquifer2 + "\n" + 
		"Tperf:          " + _tperf + "\n" + 
		"Bperf:          " + _bperf + "\n" + 
		"Depth:          " + _depth + "\n" + 
		"Elev:           " + _elev + "\n" + 
		"Comment:        " + _comment + "\n" + 
		"Usgs_id:        " + _usgs_id + "\n" + 
		"Local_well_num: " + _local_well_num + "\n" + 
		"Usbr_id:        " + _usbr_id + "\n" + 
		"Sp_coord_n:     " + _sp_coord_n + "\n" + 
		"Sp_coord_e:     " + _sp_coord_e + "\n" + 
		"Usgs_net:       " + _usgs_net + "\n}\n";
		}
}