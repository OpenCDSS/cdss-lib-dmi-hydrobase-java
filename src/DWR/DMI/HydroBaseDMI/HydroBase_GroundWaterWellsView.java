// ----------------------------------------------------------------------------
// HydroBase_GroundWaterWellsView.java - Class to hold data from the HydroBase 
//	pump_test view table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2005-06-20	J. Thomas Sapienza, RTi	Initial version to hold groundwaterwell
//					data as a baseclass.
// 2005-11-15	JTS, RTi		Added DSS_aquifer1, DSS_aquifer2, and
//					DSS_aquifer_comment.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase pump_test table.
*/
public class HydroBase_GroundWaterWellsView
extends DMIDataObject {

// from Structure
protected int _div = 		DMIUtil.MISSING_INT;
protected int _wd = 		DMIUtil.MISSING_INT;
protected int _id = 		DMIUtil.MISSING_INT;
protected String _str_name = 	DMIUtil.MISSING_STRING;

// from Geoloc
protected String _pm = 		DMIUtil.MISSING_STRING;
protected int _ts = 		DMIUtil.MISSING_INT;
protected String _tdir = 	DMIUtil.MISSING_STRING;
protected String _tsa = 	DMIUtil.MISSING_STRING;
protected int  _rng = 		DMIUtil.MISSING_INT;
protected String _rdir = 	DMIUtil.MISSING_STRING;
protected String _rnga = 	DMIUtil.MISSING_STRING;
protected int _sec = 		DMIUtil.MISSING_INT;
protected String _seca = 	DMIUtil.MISSING_STRING;
protected String _q160 = 	DMIUtil.MISSING_STRING;
protected String _q40 = 	DMIUtil.MISSING_STRING;
protected String _q10 = 	DMIUtil.MISSING_STRING;

// From new groundwater well data ...
protected int _well_num = 	DMIUtil.MISSING_INT;
protected double _utm_x = 		DMIUtil.MISSING_DOUBLE;
protected double _utm_y = 		DMIUtil.MISSING_DOUBLE;
protected double _latdecdeg = 		DMIUtil.MISSING_DOUBLE;
protected double _longdecdeg = 		DMIUtil.MISSING_DOUBLE;
protected String _county = 		DMIUtil.MISSING_STRING;
protected String _st = 			DMIUtil.MISSING_STRING;
protected String _topomap = 		DMIUtil.MISSING_STRING;
protected int _cty = 			DMIUtil.MISSING_INT;
protected String _huc = 		DMIUtil.MISSING_STRING;
protected double _elev = 		DMIUtil.MISSING_DOUBLE;
protected int _elev_accuracy = 		DMIUtil.MISSING_INT;
protected String _loc_type = 		DMIUtil.MISSING_STRING;
protected int _coordsns = 		DMIUtil.MISSING_INT;
protected String _coordsns_dir =	DMIUtil.MISSING_STRING;
protected int _coordsew = 		DMIUtil.MISSING_INT;
protected String _coordsew_dir = 	DMIUtil.MISSING_STRING;

protected String _well_name = 		DMIUtil.MISSING_STRING;
protected String _receipt = 		DMIUtil.MISSING_STRING;
protected int _permitno = 		DMIUtil.MISSING_INT;
protected String _permitsuf = 		DMIUtil.MISSING_STRING;
protected String _permitrpl = 		DMIUtil.MISSING_STRING;
protected String _md = 			DMIUtil.MISSING_STRING;
protected String _basin = 		DMIUtil.MISSING_STRING;
protected String _aquifer1 = 		DMIUtil.MISSING_STRING;
protected String _aquifer2 = 		DMIUtil.MISSING_STRING;
protected int _tperf = 			DMIUtil.MISSING_INT;
protected int _bperf = 			DMIUtil.MISSING_INT;
protected double _yield = 		DMIUtil.MISSING_DOUBLE;
protected String _aquifer_comment = 	DMIUtil.MISSING_STRING;
protected double _bedrock_elev = 	DMIUtil.MISSING_DOUBLE;
protected double _sat_1965 = 		DMIUtil.MISSING_DOUBLE;
protected String _remarks1 = 		DMIUtil.MISSING_STRING;
protected String _remarks2 = 		DMIUtil.MISSING_STRING;
protected String _data_source = 	DMIUtil.MISSING_STRING;
protected String _data_source_id = 	DMIUtil.MISSING_STRING;
protected String _data_units =		DMIUtil.MISSING_STRING;
protected String _locnum = 		DMIUtil.MISSING_STRING;
protected String _site_id = 		DMIUtil.MISSING_STRING;
protected int _well_depth = 		DMIUtil.MISSING_INT;
protected int _accuracy = 		DMIUtil.MISSING_INT;
protected int _stream_num = 		DMIUtil.MISSING_INT;
protected double _str_mile = 		DMIUtil.MISSING_DOUBLE;
protected String _spotter_version = 	DMIUtil.MISSING_STRING;
protected int _start_year = 		DMIUtil.MISSING_INT;
protected int _end_year = 		DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected String _usgs_id = 		DMIUtil.MISSING_STRING;
protected String _usbr_id = 		DMIUtil.MISSING_STRING;
protected int _well_meas_num = 		DMIUtil.MISSING_INT;
protected String _meas_type = 		DMIUtil.MISSING_STRING;
protected String _time_step = 		DMIUtil.MISSING_STRING;
protected int _meas_count = 		DMIUtil.MISSING_INT;
protected String _identifier = 		DMIUtil.MISSING_STRING;
protected int _log_depth = 		DMIUtil.MISSING_INT;
protected String _log_type = 		DMIUtil.MISSING_STRING;
protected int _log_swl = 		DMIUtil.MISSING_INT;
protected Date _log_date = 		DMIUtil.MISSING_DATE;

protected String _DSS_aquifer1 = 	DMIUtil.MISSING_STRING;
protected String _DSS_aquifer2 = 	DMIUtil.MISSING_STRING;
protected String _DSS_aquifer_comment = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_GroundWaterWellsView() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_str_name = null;
	_pm = null;
	_tdir = null;
	_tsa = null;
	_rdir = null;
	_rnga = null;
	_seca = null;
	_q160 = null;
	_q40 = null;
	_q10 = null;
	_county = null;
	_st = null;
	_topomap = null;
	_huc = null;
	_loc_type = null;
	_coordsew_dir = null;
	_coordsns_dir = null;
	_well_name = null;
	_receipt = null;
	_permitsuf = null;
	_permitrpl = null;	
	_md = null;	
	_basin = null;
	_aquifer1 = null;
	_aquifer2 = null;	
	_aquifer_comment = null;
	_remarks1 = null;
	_remarks2 = null;
	_data_source = null;
	_data_source_id = null;
	_data_units = null;
	_locnum = null;
	_site_id = null;
	_spotter_version = null;	
	_usgs_id = null;
	_usbr_id = null;
	_meas_type = null;
	_time_step = null;
	_identifier = null;
	_log_type = null;
	_log_date = null;
	_DSS_aquifer1 = null;
	_DSS_aquifer2 = null;
	_DSS_aquifer_comment = null;
	super.finalize();
}

/**
Returns _accuracy
@return _accuracy
*/
public int getAccuracy() {
	return _accuracy;
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
Returns _aquifer_comment
@return _aquifer_comment
*/
public String getAquifer_comment() {
	return _aquifer_comment;
}

/**
Returns _basin
@return _basin
*/
public String getBasin() {
	return _basin;
}

/**
Returns _bedrock_elev
@return _bedrock_elev
*/
public double getBedrock_elev() {
	return _bedrock_elev;
}

/**
Returns _bperf
@return _bperf
*/
public int getBperf() {
	return _bperf;
}

/**
Returns _coordsew
@return _coordsew
*/
public int getCoordsew() {
	return _coordsew;
}

/**
Returns _coordsew_dir
@return _coordsew_dir
*/
public String getCoordsew_dir() {
	return _coordsew_dir;
}

/**
Returns _coordsns
@return _coordsns
*/
public int getCoordsns() {
	return _coordsns;
}

/**
Returns _coordsns_dir
@return _coordsns_dir
*/
public String getCoordsns_dir() {
	return _coordsns_dir;
}

/**
Returns _county
@return _county
*/
public String getCounty() {
	return _county;
}

/**
Returns _cty
@return _cty
*/
public int getCty() {
	return _cty;
}

/**
Returns _data_source
@return _data_source
*/
public String getData_source() {
	return _data_source;
}

/**
Returns _data_source_id
@return _data_source_id
*/
public String getData_source_id() {
	return _data_source_id;
}

/**
Returns _data_units
@return _data_units
*/
public String getData_units() {
	return _data_units;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _DSS_aquifer1
@return _DSS_aquifer1
*/
public String getDSS_aquifer1() {
	return _DSS_aquifer1;
}

/**
Returns _DSS_aquifer2
@return _DSS_aquifer2
*/
public String getDSS_aquifer2() {
	return _DSS_aquifer2;
}

/**
Returns _DSS_aquifer_comment
@return _DSS_aquifer_comment
*/
public String getDSS_aquifer_comment() {
	return _DSS_aquifer_comment;
}

/**
Returns _elev
@return _elev
*/
public double getElev() {
	return _elev;
}

/**
Returns _elev_accuracy
@return _elev_accuracy
*/
public double getElev_accuracy() {
	return _elev_accuracy;
}

/**
Returns _end_year
@return _end_year
*/
public int getEnd_year() {
	return _end_year;
}

/**
Returns _huc
@return _huc
*/
public String getHUC() {
	return _huc;
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
}

/**
Returns _identifier
@return _identifier
*/
public String getIdentifier() {
	return _identifier;
}

/**
Returns _latdecdeg
@return _latdecdeg
*/
public double getLatdecdeg() {
	return _latdecdeg;
}

/**
Returns _accuracy
@return _accuracy
*/
public int getLoc_accuracy() {
	return _accuracy;
}

/**
Returns _locnum
@return _locnum
*/
public String getLoc_num() {
	return _locnum;
}

/**
Returns _loc_type
@return _loc_type
*/
public String getLoc_type() {
	return _loc_type;
}

/**
Returns _log_date
@return _log_date
*/
public Date getLog_date() {
	return _log_date;
}

/**
Returns _log_depth
@return _log_depth
*/
public int getLog_depth() {
	return _log_depth;
}

/**
Returns _log_swl
@return _log_swl
*/
public int getLog_swl() {
	return _log_swl;
}

/**
Returns _log_type
@return _log_type
*/
public String getLog_type() {
	return _log_type;
}

/**
Returns _longdecdeg
@return _longdecdeg
*/
public double getLongdecdeg() {
	return _longdecdeg;
}

/**
Returns _md
@return _md
*/
public String getMD() {
	return _md;
}

/**
Returns _meas_count
@return _meas_count
*/
public int getMeas_count() {
	return _meas_count;
}

/**
Returns _meas_type
@return _meas_type
*/
public String getMeas_type() {
	return _meas_type;
}

/**
Returns _pm
@return _pm
*/
public String getPM() {
	return _pm;
}

/**
Returns _permitno
@return _permitno
*/
public int getPermitno() {
	return _permitno;
}

/**
Returns _permitsuf
@return _permitsuf
*/
public String getPermitsuf() {
	return _permitsuf;
}

/**
Returns _permitrpl
@return _permitrpl
*/
public String getPermitrpl() {
	return _permitrpl;
}

/**
Returns _q10
@return _q10
*/
public String getQ10() {
	return _q10;
}

/**
Returns _q40
@return _q40
*/
public String getQ40() {
	return _q40;
}

/**
Returns _q160
@return _q160
*/
public String getQ160() {
	return _q160;
}

/**
Returns _rdir
@return _rdir
*/
public String getRdir() {
	return _rdir;
}

/**
Returns _receipt
@return _receipt
*/
public String getReceipt() {
	return _receipt;
}

/**
Returns _remarks1
@return _remarks1
*/
public String getRemarks1() {
	return _remarks1;
}

/**
Returns _remarks2
@return _remarks2
*/
public String getRemarks2() {
	return _remarks2;
}

/**
Returns _rng
@return _rng
*/
public int getRng() {
	return _rng;
}

/**
Returns _rnga
@return _rnga
*/
public String getRnga() {
	return _rnga;
}

/**
Returns _sec
@return _sec
*/
public int getSec() {
	return _sec;
}

/**
Returns _seca
@return _seca
*/
public String getSeca() {
	return _seca;
}

/**
Returns _sat_1965
@return _sat_1965
*/
public double getSat_1965() {
	return _sat_1965;
}

/**
Returns _site_id
@return _site_id
*/
public String getSite_id() {
	return _site_id;
}

/**
Returns _spotter_version
@return _spotter_version
*/
public String getSpotter_version() {
	return _spotter_version;
}

/**
Returns _st
@return _st
*/
public String getST() {
	return _st;
}

/**
Returns _start_year
@return _start_year
*/
public int getStart_year() {
	return _start_year;
}

/**
Returns _str_mile
@return _str_mile
*/
public double getStr_mile() {
	return _str_mile;
}

/**
Returns _str_name
@return _str_name
*/
public String getStr_name() {
	return _str_name;
}

/**
Returns _stream_num
@return _stream_num
*/
public int getStream_num() {
	return _stream_num;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _tdir
@return _tdir
*/
public String getTdir() {
	return _tdir;
}

/**
Returns _time_step
@return _time_step
*/
public String getTime_step() {
	return _time_step;
}

/**
Returns _topomap
@return _topomap
*/
public String getTopomap() {
	return _topomap;
}

/**
Returns _tperf
@return _tperf
*/
public int getTperf() {
	return _tperf;
}

/**
Returns _tsa
@return _tsa
*/
public String getTsa() {
	return _tsa;
}

/**
Returns _ts
@return _ts
*/
public int getTS() {
	return _ts;
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
Returns _utm_x
@return _utm_x
*/
public double getUtm_x() {
	return _utm_x;
}

/**
Returns _utm_y
@return _utm_y
*/
public double getUtm_y() {
	return _utm_y;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Returns _well_depth
@return _well_depth
*/
public int getWell_depth() {
	return _well_depth;
}

/**
Returns _well_meas_num
@return _well_mean_num
*/
public int getWell_meas_num() {
	return _well_meas_num;
}

/**
Returns _well_name
@return _well_name
*/
public String getWell_name() {
	return _well_name;
}

/**
Returns _well_num
@return _well_num
*/
public int getWell_num() {
	return _well_num;
}

/**
Returns _yield
@return _yield
*/
public double getYield() {
	return _yield;
}

/**
Sets _accuracy
@param accuracy value to put into _accuracy
*/
public void setAccuracy(int accuracy) {
	_accuracy = accuracy;
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
Sets _aquifer_comment
@param aquifer_comment value to put into _aquifer_comment
*/
public void setAquifer_comment(String aquifer_comment) {
	_aquifer_comment = aquifer_comment;
}

/**
Sets _basin
@param basin value to put into _basin
*/
public void setBasin(String basin) {
	_basin = basin;
}

/**
Sets _bedrock_elev
@param bedrock_elev value to put into _bedrock_elev
*/
public void setBedrock_elev(double bedrock_elev) {
	_bedrock_elev = bedrock_elev;
}

/**
Sets _bperf
@param bperf value to put into _bperf
*/
public void setBperf(int bperf) {
	_bperf = bperf;
}

/**
Sets _coordsew
@param coordsew value to put into _coordsew
*/
public void setCoordsew(int coordsew) {
	_coordsew = coordsew;
}

/**
Sets _coordsew_dir
@param coordsew_dir value to put into _coordsew_dir
*/
public void setCoordsew_dir(String coordsew_dir) {
	_coordsew_dir = coordsew_dir;
}

/**
Sets _coordsns
@param coordsns value to put into _coordsns
*/
public void setCoordsns(int coordsns) {
	_coordsns = coordsns;
}

/**
Sets _coordsns_dir
@param coordsns_dir value to put into _coordsns_dir
*/
public void setCoordsns_dir(String coordsns_dir) {
	_coordsns_dir = coordsns_dir;
}

/**
Sets _county
@param county value to put into _county
*/
public void setCounty(String county) {
	_county = county;
}

/**
Sets _cty
@param cty value to put into _cty
*/
public void setCty(int cty) {
	_cty = cty;
}

/**
Sets _data_source
@param data_source value to put into _data_source
*/
public void setData_source(String data_source) {
	_data_source = data_source;
}

/**
Sets _data_source_id
@param data_source_id value to put into _data_source_id
*/
public void setData_source_id(String data_source_id) {
	_data_source_id = data_source_id;
}

/**
Sets _data_units
@param _data_units
*/
public void setData_units(String data_units) {
	_data_units = data_units;
}

/**
Sets _div
@param div the value to which _div will be set.
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _DSS_aquifer1
@param DSS_aquifer1
*/
public void setDSS_aquifer1(String DSS_aquifer1) {
	_DSS_aquifer1 = DSS_aquifer1;
}

/**
Sets _DSS_aquifer2
@param DSS_aquifer2
*/
public void setDSS_aquifer2(String DSS_aquifer2) {
	_DSS_aquifer2 = DSS_aquifer2;
}

/**
Sets _DSS_aquifer_comment
@param DSS_aquifer_comment
*/
public void setDSS_aquifer_comment(String DSS_aquifer_comment) {
	_DSS_aquifer_comment = DSS_aquifer_comment;
}

/**
Sets _elev
@param elev value to put into _elev
*/
public void setElev(double elev) {
	_elev = elev;
}

/**
Sets _elev_accuracy
@param elev_accuracy value to put into _elev_accuracy
*/
public void setElev_accuracy(int elev_accuracy) {
	_elev_accuracy = elev_accuracy;
}

/**
Sets _end_year
@param end_year value to put into _end_year
*/
public void setEnd_year(int end_year) {
	_end_year = end_year;
}

/**
Sets _huc
@param huc value to put into _huc
*/
public void setHUC(String huc) {
	_huc = huc;
}

/**
Sets _id
@param id the value to which _id will be set
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _identifier
@param identifier value to put into _identifier
*/
public void setIdentifier(String identifier) {
	_identifier = identifier;
}

/**
Sets _latdecdeg
@param latdecdeg value to put into _latdecdeg
*/
public void setLatdecdeg(double latdecdeg) {
	_latdecdeg = latdecdeg;
}

/**
Sets _accuracy
@param accuracy value to put into _accuracy
*/
public void setLoc_accuracy(int accuracy) {
	_accuracy = accuracy;
}

/**
Sets _locnum
@param locnum value to put into _locnum
*/
public void setLocnum(String locnum) {
	_locnum = locnum;
}

/**
Sets _loc_type
@param loc_type value to put into _loc_type
*/
public void setLoc_type(String loc_type) {
	_loc_type = loc_type;
}

/**
Sets _log_date
@param log_date
*/
public void setLog_date(Date log_date) {
	_log_date = log_date;
}

/**
Sets _log_depth
@param log_depth
*/
public void setLog_depth(int log_depth) {
	_log_depth = log_depth;
}

/**
Sets _log_swl
@param log_swl
*/
public void setLog_swl(int log_swl) {
	_log_swl = log_swl;
}

/**
Sets _log_type
@param log_type
*/
public void setLog_type(String log_type) {
	_log_type = log_type;
}

/**
Sets _longdecdeg
@param longdecdeg value to put into _longdecdeg
*/
public void setLongdecdeg(double longdecdeg) {
	_longdecdeg = longdecdeg;
}

/**
Sets _md
@param md value to put into _md
*/
public void setMD(String md) {
	_md = md;
}

/**
Sets _meas_count
@param meas_count value to put into _meas_count
*/
public void setMeas_count(int meas_count) {
	_meas_count = meas_count;
}

/**
Sets _meas_type
@param meas_type value to put into _meas_type
*/
public void setMeas_type(String meas_type) {
	_meas_type = meas_type;
}

/**
Sets _pm
@param pm value to put into _pm
*/
public void setPM(String pm) {
	_pm = pm;
}

/**
Sets _permitno
@param permitno value to put into _permitno
*/
public void setPermitno(int permitno) {
	_permitno = permitno;
}

/**
Sets _permitsuf
@param permitsuf value to put into _permitsuf
*/
public void setPermitsuf(String permitsuf) {
	_permitsuf = permitsuf;
}

/**
Sets _permitrpl
@param permitrpl value to put into _permitrpl
*/
public void setPermitrpl(String permitrpl) {
	_permitrpl = permitrpl;
}

/**
Sets _q10
@param q10 value to put into _q10
*/
public void setQ10(String q10) {
	_q10 = q10;
}

/**
Sets _q40
@param q40 value to put into _q40
*/
public void setQ40(String q40) {
	_q40 = q40;
}

/**
Sets _q160
@param q160 value to put into _q160
*/
public void setQ160(String q160) {
	_q160 = q160;
}

/**
Sets _rdir
@param rdir value to put into _rdir
*/
public void setRdir(String rdir) {
	_rdir = rdir;
}

/**
Sets _receipt
@param receipt value to put into _receipt
*/
public void setReceipt(String receipt) {
	_receipt = receipt;
}

/**
Sets _remarks1
@param remarks1 value to put into _remarks1
*/
public void setRemarks1(String remarks1) {
	_remarks1 = remarks1;
}

/**
Sets _remarks2
@param remarks2 value to put into _remarks2
*/
public void setRemarks2(String remarks2) {
	_remarks2 = remarks2;
}

/**
Sets _rng
@param rng value to put into _rng
*/
public void setRng(int rng) {
	_rng = rng;
}

/**
Sets _rnga
@param rnga value to put into _rnga
*/
public void setRnga(String rnga) {
	_rnga = rnga;
}

/**
Sets _sec
@param sec value to put into _sec
*/
public void setSec(int sec) {
	_sec = sec;
}

/**
Sets _seca
@param seca value to put into _seca
*/
public void setSeca(String seca) {
	_seca = seca;
}

/**
Sets _sat_1965
@param sat_1965 value to put into _sat_1965
*/
public void setSat_1965(double sat_1965) {
	_sat_1965 = sat_1965;
}

/**
Sets _site_id
@param site_id value to put into _site_id
*/
public void setSite_id(String site_id) {
	_site_id = site_id;
}

/**
Sets _spotter_version
@param spotter_version value to put into _spotter_version
*/
public void setSpotter_version(String spotter_version) {
	_spotter_version = spotter_version;
}

/**
Sets _st
@param _st
*/
public void setST(String st) {
	_st = st;
}

/**
Sets _start_year
@param start_year value to put into _start_year
*/
public void setStart_year(int start_year) {
	_start_year = start_year;
}

/**
Sets _str_mile
@param str_mile value to put into _str_mile
*/
public void setStr_mile(double str_mile) {
	_str_mile = str_mile;
}

/**
Sets _str_name
@param str_name the value to which _str_name will be set
*/
public void setStr_name(String str_name) {
	_str_name = str_name;
}

/**
Sets _stream_num
@param stream_num value to put into _stream_num
*/
public void setStream_num(int stream_num) {
	_stream_num = stream_num;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _tdir
@param tdir value to put into _tdir
*/
public void setTdir(String tdir) {
	_tdir = tdir;
}

/**
Sets _time_step
@param time_step value to put into _time_step
*/
public void setTime_step(String time_step) {
	_time_step = time_step;
}

/**
Sets _topomap
@param topomap value to put into _topomap
*/
public void setTopomap(String topomap) {
	_topomap = topomap;
}

/**
Sets _tperf
@param tperf value to put into _tperf
*/
public void setTperf(int tperf) {
	_tperf = tperf;
}

/**
Sets _tsa
@param tsa value to put into _tsa
*/
public void setTsa(String tsa) {
	_tsa = tsa;
}

/**
Sets _ts
@param ts value to put into _ts
*/
public void setTS(int ts) {
	_ts = ts;
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
Sets _utm_x
@param utm_x value to put into _utm_x
*/
public void setUtm_x(double utm_x) {
	_utm_x = utm_x;
}

/**
Sets _utm_y
@param utm_y value to put into _utm_y
*/
public void setUtm_y(double utm_y) {
	_utm_y = utm_y;
}

/**
Sets _wd
@param wd the value to which _wd will be set
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Sets _well_dept
@param well_depth value to put into _well_depth
*/
public void setWell_depth(int well_depth) {
	_well_depth = well_depth;
}

/**
Sets _well_meas_num
@param well_meas_num value to put into _well_meas_num
*/
public void setWell_meas_num(int well_meas_num) {	
	_well_meas_num = well_meas_num;
}

/**
Sets _well_name
@param well_name value to put into _well_name
*/
public void setWell_name(String well_name) {
	_well_name = well_name;
}

/**
Sets _well_num
@param well_num the value to which _well_num will be set.
*/
public void setWell_num(int well_num) {
	_well_num = well_num;
}

/**
Sets _yield
@param yield value to put into _yield
*/
public void setYield(double yield) {
	_yield = yield;
}

}
