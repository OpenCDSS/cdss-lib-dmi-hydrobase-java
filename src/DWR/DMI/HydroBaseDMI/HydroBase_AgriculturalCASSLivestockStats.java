// HydroBase_AgriculturalCASSLivestockStats - Class to hold data from the HydroBase Agricultural_CASS_livestock_stats table.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

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

// ----------------------------------------------------------------------------
// HydroBase_AgriculturalCASSLivestockStats.java - Class to hold data from the
//	HydroBase Agricultural_CASS_livestock_stats table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2006-10-30	Steven A. Malers, RTi	Initial version from
//					HydroBase_AgriculturalCASSCropStats.
// 2006-02-26	SAM, RTi				Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase Agricultural_CASS_livestock_stats table.
*/
public class HydroBase_AgriculturalCASSLivestockStats 
extends DMIDataObject {

protected String _st = 		DMIUtil.MISSING_STRING;
protected String _county = 	DMIUtil.MISSING_STRING;
protected String _commodity = 	DMIUtil.MISSING_STRING;
protected String _type = 	DMIUtil.MISSING_STRING;
protected int _cal_year = 	DMIUtil.MISSING_INT;
protected int _head = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_AgriculturalCASSLivestockStats() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_st = null;
	_county = null;
	_commodity = null;
	_type = null;
	super.finalize();
}

/**
Returns _cal_year.
@return _cal_year.
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _commodity.
@return _commodity.
*/
public String getCommodity() {
	return _commodity;
}

/**
Returns _county.
@return _county.
*/
public String getCounty() {
	return _county;
}

/**
Returns _head.
@return _head.
*/
public int getHead() {
	return _head;
}

/**
Returns _type.
@return _type.
*/
public String getType() {
	return _type;
}

/**
Returns _st.
@return _st.
*/
public String getSt() {
	return _st;
}

/**
Sets _cal_year.
@param cal_year value to put in _cal_year.
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _commodity.
@param commodity value to put in _commodity.
*/
public void setCommodity(String commodity) {
	_commodity = commodity;
}

/**
Sets _county.
@param county value to put in _county.
*/
public void setCounty(String county) {
	_county = county;
}

/**
Sets _head.
@param head value to put in _head.
*/
public void setHead(int head) {
	_head = head;
}

/**
Sets _st.
@param practice value to put in _st.
*/
public void setSt(String st) {
	_st = st;
}

/**
Sets _type.
@param practice value to put in _type.
*/
public void setType(String type) {
	_type = type;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_AgriculturalCASSLivestockStats {"		+ "\n" +
		"St:       '" 	+ _st 		+ "'\n" + 
		"County:   '" 	+ _county 	+ "'\n" + 
		"Commodity:'" 	+ _commodity 	+ "'\n" + 
		"Type: '" 	+ _type 	+ "'\n" + 
		"Cal_year: " 	+ _cal_year 	+ "\n" + 
		"Head:    "	+ _head 	+ "\n";
}

}
