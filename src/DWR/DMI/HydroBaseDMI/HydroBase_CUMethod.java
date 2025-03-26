// HydroBase_CUMethod - data structure to hold data from the the HydroBase cu_method table

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
This class provides storage for data from the HydroBase cu_method table.
The data and methods in this class correspond to the table entities described
in the HydroBase data dictionary.
*/
public class HydroBase_CUMethod
extends DMIDataObject {

// Cropchar table data

protected int _method_num = 	DMIUtil.MISSING_INT;
protected int _cropnum = 	DMIUtil.MISSING_INT;
protected String _method_desc =	DMIUtil.MISSING_STRING;

/**
Construct and initialize to empty strings and missing data.
*/
public HydroBase_CUMethod() {
	super();
}

/**
Return _cropnum
@return _cropnum
*/
public int getCropnum() {
	return _cropnum;
}

/**
Return _method_desc
@return _method_desc
*/
public String getMethod_desc() {
	return _method_desc;
}

/**
Return _method_num
@return _method_num
*/
public int getMethod_num() {
	return _method_num;
}

/**
Set _cropnum.
@param cropnum the value for _cropnum.
*/
public void setCropnum (int cropnum) {
	_cropnum = cropnum;
}

/**
Set _method_desc.
@param method_desc the value for _method_desc.
*/
public void setMethod_desc (String method_desc) {
	_method_desc = method_desc;
}

/**
Set _method_num.
@param method_num the value for _method_num.
*/
public void setMethod_num (int method_num) {
	_method_num = method_num;
}

public String toString() {
	return "HydroBase_CUMethod{" 			+ "\n" +
		"Method_num:  " + _method_num + "\n" +
		"Cropnum:     " + _cropnum + "\n" +
		"Method_desc: " + _method_desc + "\n}\n";
}

}