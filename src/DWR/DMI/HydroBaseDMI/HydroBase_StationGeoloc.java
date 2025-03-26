// HydroBase_StationGeoloc - Class to hold data from the HydroBase station and geoloc

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

import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase station and geoloc tables.  
*/
public class HydroBase_StationGeoloc 
extends HydroBase_Station {

protected int _geoloc_num = 		DMIUtil.MISSING_INT;
protected double _utm_x = 		DMIUtil.MISSING_DOUBLE;
protected double _utm_y = 		DMIUtil.MISSING_DOUBLE;
protected double _latdecdeg = 		DMIUtil.MISSING_DOUBLE;
protected double _longdecdeg = 		DMIUtil.MISSING_DOUBLE;
protected String _pm = 			DMIUtil.MISSING_STRING;
protected int _ts = 			DMIUtil.MISSING_INT;
protected String _tdir = 		DMIUtil.MISSING_STRING;
protected String _tsa = 		DMIUtil.MISSING_STRING;
protected int  _rng = 			DMIUtil.MISSING_INT;
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
protected int _div = 			DMIUtil.MISSING_INT;
protected int _wd = 			DMIUtil.MISSING_INT;
protected int _id = 			DMIUtil.MISSING_INT;
protected String _county = 		DMIUtil.MISSING_STRING;
protected String _topomap = 		DMIUtil.MISSING_STRING;
protected int _cty = 			DMIUtil.MISSING_INT;
protected String _huc = 		DMIUtil.MISSING_STRING;
protected double _elev = 		DMIUtil.MISSING_DOUBLE;
protected String _loc_type = 		DMIUtil.MISSING_STRING;
protected String _feature_type = 	DMIUtil.MISSING_STRING;
protected int _accuracy = 		DMIUtil.MISSING_INT;
protected String _st = 			DMIUtil.MISSING_STRING;
protected int _aquifer_num = 		DMIUtil.MISSING_INT;
protected int _stream_num = 		DMIUtil.MISSING_INT;
protected double _str_mile = 		DMIUtil.MISSING_DOUBLE;
protected String _loc_description = 	DMIUtil.MISSING_STRING;
protected String _spotter_version = 	DMIUtil.MISSING_STRING;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

// in the Structure/Geoloc join, this field from wd_water is also used.
protected String _strname = 		DMIUtil.MISSING_STRING;
// in the Structure/Geoloc join, these fields from rolodex are also used
protected int _rolodex_num = 		DMIUtil.MISSING_INT;
protected String _full_name = 		DMIUtil.MISSING_STRING;

/**
Construct and initialize to empty strings and missing data.
*/
public HydroBase_StationGeoloc() {
	super();
}

/**
Copy constructor to fill in all the values of this object from a corresponding
view.  Used during unit tests.
*/
public HydroBase_StationGeoloc(HydroBase_StationView view) {
	super();

	if (view == null) {
		return;
	}

	_station_num = view._station_num;
	_geoloc_num = view._geoloc_num;
	_station_name = view._station_name;
	_station_id = view._station_id;
	_nesdis_id = view._nesdis_id;
	_drain_area = view._drain_area;
	_contr_area = view._contr_area;
	_source = view._source;
	_abbrev = view._abbrev;
	_transbsn = view._transbsn;
	_modified = view._modified;
	_user_num = view._user_num;
	_transmnt = view._transmnt;
	_transmit = view._transmit;
	_cooperator_id = view._cooperator_id;

	_utm_x = view._utm_x;
	_utm_y = view._utm_y;
	_latdecdeg = view._latdecdeg;
	_longdecdeg = view._longdecdeg;
	_div = view._div;
	_wd = view._wd;
	_county = view._county;
	_topomap = view._topomap;
	_cty = view._cty;
	_huc = view._huc;
	_elev = view._elev;
	_loc_type = view._loc_type;
	_accuracy = view._accuracy;
	_st = view._st;
}

/**
Returns _accuracy
@return _accuracy
*/
public int getAccuracy() {
	return _accuracy;
}

/**
Returns _aquifer_num
@return _aquifer_num
*/
public int getAquifer_num() {
	return _aquifer_num;
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
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _elev
@return _elev
*/
public double getElev() {
	return _elev;
}

/**
Returns _elev.  Elevation is used instead of elev in older db versions.
@return _elev
*/
public double getElevation() {
	return _elev;
}

/**
Returns _feature_type
@return _feature_type
*/
public String getFeature_type() {
	return _feature_type;
}

/**
Returns _full_name
@return _full_name
*/
public String getFull_name() {
	return _full_name;
}

/**
Returns _geoloc_num
@return _geoloc_num
*/
public int getGeoloc_num() {
	return _geoloc_num;
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
Returns _latdecdeg
@return _latdecdeg
*/
public double getLatdecdeg() {
	return _latdecdeg;
}

/**
Returns _loc_description
@return _loc_description
*/
public String getLoc_description() {
	return _loc_description;
}

/**
Returns _loc_type
@return _loc_type
*/
public String getLoc_type() {
	return _loc_type;
}

/**
Returns _longdecdeg
@return _longdecdeg
*/
public double getLongdecdeg() {
	return _longdecdeg;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
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
Returns _rolodex_num
@return _rolodex_num
*/
public int getRolodex_num() {
	return _rolodex_num;
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
Returns _str_mile
@return _str_mile
*/
public double getStr_mile() {
	return _str_mile;
}

/**
Returns _strname
@return _strname
*/
public String getStrname() {
	return _strname;
}

/**
Returns _stream_num
@return _stream_num
*/
public int getStream_num() {
	return _stream_num;
}

/**
Returns _tdir
@return _tdir
*/
public String getTdir() {
	return _tdir;
}

/**
Returns _topomap
@return _topomap
*/
public String getTopomap() {
	return _topomap;
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
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
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
Sets _accuracy
@param accuracy value to put into _accuracy
*/
public void setAccuracy(int accuracy) {
	_accuracy = accuracy;
}

/**
Sets _aquifer_num
@param aquifer_num value to put into _aquifer_num
*/
public void setAquifer_num(int aquifer_num) {
	_aquifer_num = aquifer_num;
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
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _elev
@param elev value to put into _elev
*/
public void setElev(double elev) {
	_elev = elev;
}

/**
Sets _elev.  Elevation is used instead of elev in older versions of the database
@param elevation value to put into _elev
*/
public void setElevation(double elevation) {
	_elev = elevation;
}

/**
Sets _feature_type
@param feature_type value to put into _feature_type
*/
public void setFeature_type(String feature_type) {
	_feature_type = feature_type;
}

/**
Sets _full_name
@param full_name value to put into _full_name
*/
public void setFull_name(String full_name) {
	_full_name = full_name;
}

/**
Sets _geoloc_num
@param geoloc_num value to put into _geoloc_num
*/
public void setGeoloc_num(int geoloc_num) {
	_geoloc_num = geoloc_num;
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
@param id value to put into _id
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _latdecdeg
@param latdecdeg value to put into _latdecdeg
*/
public void setLatdecdeg(double latdecdeg) {
	_latdecdeg = latdecdeg;
}

/**
Sets _loc_description
@param description value to put into _loc_description
*/
public void setLoc_description(String description) {
	_loc_description = description;
}

/**
Sets _loc_type
@param loc_type value to put into _loc_type
*/
public void setLoc_type(String loc_type) {
	_loc_type = loc_type;
}

/**
Sets _longdecdeg
@param longdecdeg value to put into _longdecdeg
*/
public void setLongdecdeg(double longdecdeg) {
	_longdecdeg = longdecdeg;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
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
Sets _rolodex_num
@param rolodex_num value to put into _rolodex_num
*/
public void setRolodex_num(int rolodex_num) {
	_rolodex_num = rolodex_num;
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
Sets _spotter_version
@param spotter_version value to put into _spotter_version
*/
public void setSpotter_version(String spotter_version) {
	_spotter_version = spotter_version;
}

/**
Sets _st
@param st value to put into _st
*/
public void setST(String st) {
	_st = st;
}

/**
Sets _str_mile
@param str_mile value to put into _str_mile
*/
public void setStr_mile(double str_mile) {
	_str_mile = str_mile;
}

/**
Sets _strname
@param strname value to put into _strname
*/
public void setStrname(String strname) {
	_strname = strname;
}

/**
Sets _stream_num
@param stream_num value to put into _stream_num
*/
public void setStream_num(int stream_num) {
	_stream_num = stream_num;
}

/**
Sets _tdir
@param tdir value to put into _tdir
*/
public void setTdir(String tdir) {
	_tdir = tdir;
}

/**
Sets _topomap
@param topomap value to put into _topomap
*/
public void setTopomap(String topomap) {
	_topomap = topomap;
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
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
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
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Returns a string representation of this object.
@return a string representation of this object.
*/
public String toString() {
	return "HydroBase_Geoloc{" 		+ "\n" + 
		"Geoloc_num:      " + _geoloc_num + "\n" +
		"UTM_X:           " + _utm_x + "\n" +
		"UTM_Y:           " + _utm_y + "\n" +
		"Latdecdeg:       " + _latdecdeg + "\n" +
		"Longdecdeg:      " + _longdecdeg + "\n" +
		"PM:              " + _pm + "\n" +
		"TS:              " + _ts + "\n" +
		"TDir:            " + _tdir + "\n" +
		"TSA:             " + _tsa + "\n" +
		"Rng:             " + _rng + "\n" +
		"RDir:            " + _rdir + "\n" +
		"Rnga:            " + _rnga + "\n" +
		"Sec:             " + _sec + "\n" +
		"Seca:            " + _seca + "\n" +
		"Q160:            " + _q160 + "\n" +
		"Q40:             " +  _q40 + "\n" +
		"Q10:             " +  _q10 + "\n" +
		"Coordsns:        " + _coordsns + "\n" +
		"Coordsns_dir:    " + _coordsns_dir + "\n" +
		"Coordsew:        " + _coordsew + "\n" +
		"Coordsew_dir:    " + _coordsew_dir + "\n" +
		"Div:             " + _div + "\n" +
		"WD:              " + _wd + "\n" +
		"ID:              " + _id + "\n" +
		"County:          " + _county + "\n" +
		"Topomap:         " + _topomap + "\n" +
		"Cty:             " + _cty + "\n" +
		"HUC:             " + _huc + "\n" +
		"Elev:            " + _elev + "\n" +
		"Loc_type:        " + _loc_type + "\n" +
		"Feature_type:    " + _feature_type + "\n" +
		"Accuracy:        " + _accuracy + "\n" +
		"ST:              " + _st + "\n" +
		"Aquifer_num:     " + _aquifer_num + "\n" +
		"Stream_num:      " + _stream_num + "\n" +
		"Str_mile:        " + _str_mile + "\n" +
		"Loc_description: " + _loc_description + "\n" +
		"Spotter_version: " + _spotter_version + "\n" +
		"Modified:        " + _modified + "\n" +
		"User_num:        " + _user_num + "\n}\n";
	
}

}
