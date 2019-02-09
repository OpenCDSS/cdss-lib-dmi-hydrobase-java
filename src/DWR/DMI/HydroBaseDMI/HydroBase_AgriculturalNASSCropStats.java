// HydroBase_AgriculturalNASSCropStats - Class to hold data from the HydroBase Agricultural_NASS_crop_stats table.

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
// HydroBase_AgriculturalNASSCropStats.java - Class to hold data from the
//	HydroBase Agricultural_NASS_crop_stats table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2004-02-20	Steven A. Malers, RTi	Initial version - copy and modify
//					HydroBase_AgriculturalCASSCropStats.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase Agricultural_NASS_crop_stats table.
*/
public class HydroBase_AgriculturalNASSCropStats 
extends DMIDataObject {

protected String _st = 		DMIUtil.MISSING_STRING;
protected String _county = 	DMIUtil.MISSING_STRING;
protected String _Commodity = 	DMIUtil.MISSING_STRING;
protected int _cal_year = 	DMIUtil.MISSING_INT;
protected double _ag_amt = 	DMIUtil.MISSING_DOUBLE;
protected double _PltdHarv = 	DMIUtil.MISSING_DOUBLE;
protected String _flag = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_AgriculturalNASSCropStats() {
	super();
}

/**
Cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_st = null;
	_county = null;
	_Commodity = null;
	_flag = null;
	super.finalize();
}

/**
Returns _ag_amt.
@return _ag_amt.
*/
public double getAg_amt() {
	return _ag_amt;
}

/**
Returns _cal_year.
@return _cal_year.
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _Commodity.
@return _Commodity.
*/
public String getCommodity() {
	return _Commodity;
}

/**
Returns _county.
@return _county.
*/
public String getCounty() {
	return _county;
}

/**
Returns _flag.
@return _flag.
*/
public String getFlag() {
	return _flag;
}

/**
Returns _st.
@return _st.
*/
public String getSt() {
	return _st;
}

/**
Returns _st.
@return _st.
*/
public String getST() {
	return _st;
}

/**
Sets _ag_amt.
@param ag_amt value to put in _ag_amt.
*/
public void setAg_amt(double ag_amt) {
	_ag_amt = ag_amt;
}

/**
Sets _cal_year.
@param cal_year value to put in _cal_year.
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _Commodity.
@param commodity value to put in _Commodity.
*/
public void setCommodity(String commodity) {
	_Commodity = commodity;
}

/**
Sets _county.
@param county value to put in _county.
*/
public void setCounty(String county) {
	_county = county;
}

/**
Sets _flag.
@param flag value to put in _flag.
*/
public void setFlag(String flag) {
	_flag = flag;
}

/**
Sets _st.
@param st value to put in _st.
*/
public void setSt(String st) {
	_st = st;
}

/**
Sets _st.
@param st value to put in _st.
*/
public void setST(String st) {
	_st = st;
}

/** 
Return a string representation of this object.
@return a string representation of this object.
*/
public String toString() {
	return "HydroBase_AgriculturalNASSCropStats {"		+ "\n" + 
		"St:       '" 	+ _st 		+ "'\n" + 
		"County:   '" 	+ _county 	+ "'\n" + 
		"Commodity:'" 	+ _Commodity 	+ "'\n" + 
		"Cal_year: " 	+ _cal_year 	+ "\n" + 
		"Ag_amt:"	+ _ag_amt 	+ "\n" + 
		"Flag:"		+ _flag 	+ "\n";
}

}
