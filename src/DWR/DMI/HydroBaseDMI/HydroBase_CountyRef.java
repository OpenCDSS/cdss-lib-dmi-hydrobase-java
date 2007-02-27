//------------------------------------------------------------------------------
// HydroBase_CountyRef - data structure to hold data from the HydroBase 
//	county_ref table
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// Notes:	(1)	This class has no knowledge of the database itself
//			(aside from its own data members), and there is no
//			knowledge of the connection with the database.
//		(2)	This class only holds information from the county_ref
//			table.
//------------------------------------------------------------------------------
// History:
// 
// 2002-11-08	Steven A. Malers, RTi	Initial version from HBCounty.
// 2003-01-05	SAM, RTi		Update based on changes to the DMI
//					package.
// 2003-02-13	J. Thomas Sapienza, RTi	Added static methods for forming and
//					parsing IDs.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import java.util.Vector;

/**
This class provides storage for data from the HydroBase county_ref table.
The data and methods in this class correspond to the table entities described
in the HydroBase data dictionary.
*/
public class HydroBase_CountyRef 
extends DMIDataObject {

// List in the order of the ER diagram (March 2001)

protected String _fips_cty = 	DMIUtil.MISSING_STRING;
protected String _fips_st = 	DMIUtil.MISSING_STRING;
protected int _cty =	 	DMIUtil.MISSING_INT;
protected String _county = 	DMIUtil.MISSING_STRING;
protected String _st = 		DMIUtil.MISSING_STRING;
protected String _abbrev = 	DMIUtil.MISSING_STRING;

/**
Construct and initialize to empty strings and missing data.
*/
public HydroBase_CountyRef() {
	super();
}

/**
Finalize before garbage collection.
@exception Throwable if an error occurs.
*/
protected void finalize ()
throws Throwable {
	_fips_cty = null;
	_fips_st = null;
	_county = null;
	_st = null;
	_abbrev = null;
	super.finalize();
}

/**
Form a countyID from its parts.
@param cty the cty to use
@param id the id to use
@return a countyid
*/
public static String formCountyID(int cty, int id) {
	Integer icty = new Integer(cty);
	Integer iid = new Integer(id);
	Vector v = new Vector(2, 1);
	v.addElement(icty);
	v.addElement(iid);
	return StringUtil.formatString(v, "%02d%05d");
}

/**
Form a countyID from its parts.
@param cty the cty to use
@param id the id to use
@return a countyid
*/
public static String formCountyID(String cty, String id) {
	Vector v = new Vector(2, 1);
	v.addElement(cty);
	v.addElement(id);
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
@return a Vector with the cty in the first element and the id in the second
*/
public static Vector parseCTYID(String ctyid) {
	Vector v = new Vector(2, 1);
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
		v.addElement(ctyid);
		v.addElement("0");
	}
	if (length < 7) {
		// could be trouble

		Message.printWarning(2, "HydroBase_CountyRef.parseCTYID",
			"CTYID: " + ctyid + " length is < 7, should be 7!");
	}
	v.addElement(ctyid.substring(0, 2));
	v.addElement(ctyid.substring(2));
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
