// HydroBase_RefCIU - Class to hold data from the HydroBase ref_ciu table.

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
Class to store data from the HydroBase meas_type table.
*/
public class HydroBase_RefCIU 
extends DMIDataObject {

protected String _code = 		DMIUtil.MISSING_STRING;
protected String _description = 	DMIUtil.MISSING_STRING;
protected String _rpt_code = 		DMIUtil.MISSING_STRING;
protected String _short_desc = 		DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_RefCIU() {
	super();
}

/**
Returns _code
@return _code
*/
public String getCode() {
	return _code;
}

/**
Returns _description
@return _description
*/
public String getDescription() {
	return _description;
}

/**
Returns _rpt_code
@return _rpt_code
*/
public String getRpt_code() {
	return _rpt_code;
}

/**
Returns _short_desc
@return _short_desc
*/
public String getShort_desc() {
	return _short_desc;
}

/**
Sets _code
@param code value to put into _code
*/
public void setCode(String code) {
	_code = code;
}

/**
Sets _description
@param description value to put into _description
*/
public void setDescription(String description) {
	_description = description;
}

/**
Sets _rpt_code
@param rpt_code value to put into _rpt_code
*/
public void setRpt_code(String rpt_code) {
	_rpt_code = rpt_code;
}

/**
Sets _short_desc
@param short_desc value to put into _short_desc
*/
public void setShort_desc(String short_desc) {
	_short_desc = short_desc;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_RefCIU {"		+ "\n" + 
		"Code:        '" + _code 	+ "'\n" +
		"Description: '" + _description + "'\n" +
		"Rpt_code:    '" + _rpt_code 	+ "'\n}\n";
}

}
