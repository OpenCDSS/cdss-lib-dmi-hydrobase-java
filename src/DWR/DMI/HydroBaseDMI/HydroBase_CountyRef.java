// HydroBase_CountyRef - data structure to hold data from the HydroBase county_ref table

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

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
This class provides storage for data from the HydroBase county_ref table.
The data and methods in this class correspond to the table entities described
in the HydroBase data dictionary.
*/
public class HydroBase_CountyRef 
extends DMIDataObject {

// List in the order of the ER diagram (March 2001).

protected String _fips_cty = DMIUtil.MISSING_STRING;
protected String _fips_st = DMIUtil.MISSING_STRING;
protected int _cty = DMIUtil.MISSING_INT;
protected String _county = DMIUtil.MISSING_STRING;
protected String _st = DMIUtil.MISSING_STRING;
protected String _abbrev = DMIUtil.MISSING_STRING;

/**
Construct and initialize to empty strings and missing data.
*/
public HydroBase_CountyRef() {
	super();
}

/**
Form a countyID from its parts.
@param cty the cty to use
@param id the id to use
@return a countyid
*/
public static String formCountyID(int cty, int id) {
	Integer icty = Integer.valueOf(cty);
	Integer iid = Integer.valueOf(id);
	List<Object> v = new ArrayList<Object>(2);
	v.add(icty);
	v.add(iid);
	return StringUtil.formatString(v, "%02d%05d");
}

/**
Form a countyID from its parts.
@param cty the cty to use
@param id the id to use
@return a countyid
*/
public static String formCountyID(String cty, String id) {
	List<Object> v = new ArrayList<Object>(2);
	v.add(cty);
	v.add(id);
	return StringUtil.formatString(v, "%02d%05d");
}

/**
Return _abbrev
@return _abbrev
*/
public String getAbbrev() {
	return _abbrev;
}

/**
Return _county
@return _county
*/
public String getCounty () {
	return _county;
}

/**
Return _cty
@return _cty
*/
public int getCty() {
	return _cty;
}

/**
Return _fips_cty
@return _fips_cty
*/
public String getFips_cty() {
	return _fips_cty;
}

/**
Return _fips_st
@return _fips_st
*/
public String getFips_st() {
	return _fips_st;
}

/**
Return _st
@return _st
*/
public String getST() {
	return _st;
}

/**
Parse a county id into its parts.  Until told otherwise, a CTYID consists of
a two-digit cty and a five-digit ID part.
@param ctyid the ctyid to parse
@return a list with the cty in the first element and the id in the second
*/
public static List<String> parseCTYID(String ctyid) {
	List<String> v = new Vector<String>(2, 1);
	if (ctyid == null) {
		return null;
	}

	// Make sure that only digits are used
	int length = ctyid.length();
	for (int i = 0; i < length; i++) {
		if (!Character.isDigit(ctyid.charAt(i))) {
			return null;
		}
	}

	// assume the first two characters are the cty and the rest are the
	// id ...
	if (length < 3) {
		v.add(ctyid);
		v.add("0");
	}
	if (length < 7) {
		// could be trouble

		Message.printWarning(2, "HydroBase_CountyRef.parseCTYID",
			"CTYID: " + ctyid + " length is < 7, should be 7!");
	}
	v.add(ctyid.substring(0, 2));
	v.add(ctyid.substring(2));
	return v;
}

/**
Sets _abbrev
@param abbrev the value to which _abbrev will be set
*/
public void setAbbrev(String abbrev) {
	_abbrev = abbrev;
}

/**
Sets _county
@param county the value to which _county will be set
*/
public void setCounty(String county) {
	_county = county;
}

/**
Sets _cty
@param cty the value to which _cty will be set
*/
public void setCty(int cty) {
	_cty = cty;
}

/**
Sets _fips_cty
@param fips_cty the value to which _fips_cty will be set
*/
public void setFips_cty(String fips_cty) {
	_fips_cty = fips_cty;
}

/**
Sets _fips_st
@param fips_st the value to which _fips_st will be set
*/
public void setFips_st(String fips_st) {
	_fips_st = fips_st;
}

/**
Sets _st
@param st the value to which _st will be set
*/
public void setST(String st) {
	_st = st;
}

public String toString() {
	return "HydroBase_CountyRef{" 		+ "\n" + 
		"fips_cty:     " + _fips_cty	+ "\n" + 
		"fips_st:      " + _fips_st	+ "\n" + 
		"cty:          " + _cty		+ "\n" + 
		"county:       " + _county 	+ "\n" +
		"st:           " + _st 		+ "\n" +
		"abbrev:       " + _abbrev	+ "}";
}

}
