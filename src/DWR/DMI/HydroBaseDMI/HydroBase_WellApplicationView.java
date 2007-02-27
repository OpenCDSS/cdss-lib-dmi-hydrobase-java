// ----------------------------------------------------------------------------
// HydroBase_WellApplicationView.java - Class to hold data from 
//	the HydroBase well_application view.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2005-02-08	J. Thomas Sapienza, RTi	Initial version.
// 2005-02-11	JTS, RTi		Added latdecdeg and longdecdeg.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase well_application table.
*/
public class HydroBase_WellApplicationView
extends HydroBase_WellApplication {

// from Rolodex
protected String _full_name = 		DMIUtil.MISSING_STRING;
protected String _address1 = 		DMIUtil.MISSING_STRING;
protected String _address2 = 		DMIUtil.MISSING_STRING;
protected String _city = 		DMIUtil.MISSING_STRING;
protected String _st = 			DMIUtil.MISSING_STRING;
protected String _zip = 		DMIUtil.MISSING_STRING;

// from Contact
protected String _phone_number = 	DMIUtil.MISSING_STRING;

// from Geoloc
protected String _pm = 			DMIUtil.MISSING_STRING;
protected int _ts = 			DMIUtil.MISSING_INT;
protected String _tdir = 		DMIUtil.MISSING_STRING;
protected String _tsa = 		DMIUtil.MISSING_STRING;
protected int  _rng = 			DMIUtil.MISSING_INT;
protected int _cty = 			DMIUtil.MISSING_INT;
protected String _rdir = 		DMIUtil.MISSING_STRING;
protected String _rnga = 		DMIUtil.MISSING_STRING;
protected int _sec = 			DMIUtil.MISSING_INT;
protected String _seca = 		DMIUtil.MISSING_STRING;
protected String _q160 = 		DMIUtil.MISSING_STRING;
protected String _q40 = 		DMIUtil.MISSING_STRING;
protected String _q10 = 		DMIUtil.MISSING_STRING;
protected int _coordsns = 		DMIUtil.MISSING_INT;
protected String _coordsns_dir =	DMIUtil.MISSING_STRING;
protected int _coordsew = 		DMIUtil.MISSING_INT;
protected String _coordsew_dir = 	DMIUtil.MISSING_STRING;
protected double _utm_x = 		DMIUtil.MISSING_DOUBLE;
protected double _utm_y = 		DMIUtil.MISSING_DOUBLE;
protected double _latdecdeg = 		DMIUtil.MISSING_DOUBLE;
protected double _longdecdeg = 		DMIUtil.MISSING_DOUBLE;

// from Ref_loc_accuracy
protected String _loc_source = 		DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_WellApplicationView() {
	super();
}

/**
Cleans up variables when the class is disposed of.  
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_full_name = null;
	_address1 = null;
	_address2 = null;
	_city = null;
	_phone_number = null;
	_st = null;
	_zip = null;

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
Returns _city
@return _city
*/
public String getCity() {
	return _city;
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
Returns _cty
@return _cty
*/
public int getCty() {
	return _cty;
}

/**
Returns _full_name
@return _full_name
*/
public String getFull_name() {
	return _full_name;
}

/**
Returns _latdecdeg
@return _latdecdeg
*/
public double getLatdecdeg() {
	return _latdecdeg;
}

/**
Returns _loc_source
@return _loc_source
*/
public String getLoc_source() {
	return _loc_source;
}

/**
Returns _longdecdeg
@return _longdecdeg
*/
public double getLongdecdeg() {
	return _longdecdeg;
}

/**
Returns _phone_number
@return _phone_number
*/
public String getPhone_number() {
	return _phone_number;
}

/**
Returns _pm
@return _pm
*/
public String getPM() {
	return _pm;
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
Returns _tdir
@return _tdir
*/
public String getTdir() {
	return _tdir;
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
Sets _city
@param city value to put into _city
*/
public void setCity(String city) {
	_city = city;
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
Sets _cty
@param cty value to put into _cty
*/
public void setCty(int cty) {
	_cty = cty;
}

/**
Sets _full_name
@param full_name value to put into _full_name
*/
public void setFull_name(String full_name) {
	_full_name = full_name;
}

/**
Sets _latdecdeg
@param latdecdeg value to put into _latdecdeg
*/
public void setLatdecdeg(double latdecdeg) {
	_latdecdeg = latdecdeg;
}

/**
Sets _loc_source
@param loc_source value to put into _loc_source
*/
public void setLoc_source(String loc_source) {
	_loc_source = loc_source;
}

/**
Sets _longdecdeg
@param longdecdeg value to put into _longdecdeg
*/
public void setLongdecdeg(double longdecdeg) {
	_longdecdeg = longdecdeg;
}

/**
Sets _phone_number
@param phone_number value to put into _phone_number
*/
public void setPhone_number(String phone_number) {
	 _phone_number = phone_number;
}

/**
Sets _pm
@param pm value to put into _pm
*/
public void setPM(String pm) {
	_pm = pm;
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
Sets _st
@param st value to put into _st
*/
public void setST(String st) {
	_st = st;
}

/**
Sets _tdir
@param tdir value to put into _tdir
*/
public void setTdir(String tdir) {
	_tdir = tdir;
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
Sets _zip
@param zip value to put into _zip
*/
public void setZip(String zip) {
	_zip = zip;
}


}
