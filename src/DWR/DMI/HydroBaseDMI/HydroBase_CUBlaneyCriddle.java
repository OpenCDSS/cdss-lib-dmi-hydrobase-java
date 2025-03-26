// HydroBase_CUBlaneyCriddle - data structure to hold data from the HydroBase cu_blaney_criddle table,
// with joins to crop and cu_method

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

/**
This class provides storage for data from the HydroBase cu_blaney_criddle table, with joins to crop and cu_method.
A Blaney-Criddle crop coefficient curve requires data from several tables in order to define the object.
This is different from most other HydroBase objects.
cu_penman_monteith and cu_mod_hargreaves are similar.
The data and methods in this class correspond to the table entities described in the HydroBase data dictionary.
*/
public class HydroBase_CUBlaneyCriddle
extends DMIDataObject {

// Cropchar table data
protected String _curve_type =		DMIUtil.MISSING_STRING;
protected int _curve_value =		DMIUtil.MISSING_INT;
protected double _cropgrowcoeff =	DMIUtil.MISSING_DOUBLE;

// cu_method table
protected String _method_desc =		DMIUtil.MISSING_STRING;

// crop table
protected String _cropname =		DMIUtil.MISSING_STRING;
protected int _cropnum = 		DMIUtil.MISSING_INT;

/**
Construct and initialize to empty strings and missing data.
*/
public HydroBase_CUBlaneyCriddle() {
	super();
}

/**
Return _cropgrowcoeff
@return _cropgrowcoeff
*/
public double getCropgrowcoeff() {
	return _cropgrowcoeff;
}

/**
Return _cropname
@return _cropname
*/
public String getCropname() {
	return _cropname;
}

/**
Returns _cropnum
@return _cropnum
*/
public int getCropnum() {
	return _cropnum;
}

/**
Return _curve_type
@return _curve_type
*/
public String getCurve_type() {
	return _curve_type;
}

/**
Return _curve_value
@return _curve_value
*/
public int getCurve_value() {
	return _curve_value;
}

/**
Return _method_desc
@return _method_desc
*/
public String getMethod_desc() {
	return _method_desc;
}

/**
Set _cropgrowcoeff.
@param cropgrowcoeff the value for _cropgrowcoeff.
*/
public void setCropgrowcoeff (double cropgrowcoeff) {
	_cropgrowcoeff = cropgrowcoeff;
}

/**
Set _cropname.
@param cropname the value for _cropname.
*/
public void setCropname (String cropname) {
	_cropname = cropname;
}

/**
Sets _cropnum
@param cropnum the value for _cropnum
*/
public void setCropnum(int cropnum) {
	_cropnum = cropnum;
}

/**
Set _curve_type.
@param curve_type the value for _curve_type.
*/
public void setCurve_type (String curve_type) {
	_curve_type = curve_type;
}

/**
Set _curve_value.
@param curve_value the value for _curve_value.
*/
public void setCurve_value (int curve_value) {
	_curve_value = curve_value;
}

/**
Set _method_desc.
@param method_desc the value for _method_desc.
*/
public void setMethod_desc (String method_desc) {
	_method_desc = method_desc;
}

public String toString() {
	return "HydroBase_CUBLaneyCriddle {"		+ "\n" +
		"Curve_type:    " + _curve_type + "\n" +
		"Curve_value:   " + _curve_value + "\n" +
		"Cropgrowcoeff: " + _cropgrowcoeff + "\n" +
		"Method_desc:   " + _method_desc + "\n" +
		"Cropname:      " + _cropname + "\n}\n";
}

}