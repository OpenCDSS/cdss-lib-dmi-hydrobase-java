// HydroBase_LocType.java - Class to hold data from the HydroBase loc_type table.

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
Class to store data from the HydroBase loc_type table.
*/
public class HydroBase_LocType
extends DMIDataObject {

protected String _loc_type =		DMIUtil.MISSING_STRING;
protected String _loc_type_desc = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_LocType() {
	super();
}

/**
Returns _loc_type
@return _loc_type
*/
public String getLoc_type() {
	return _loc_type;
}

/**
Returns _loc_type_desc
@return _loc_type_desc
*/
public String getLoc_type_desc() {
	return _loc_type_desc;
}

/**
Sets _loc_type
@param loc_type value to put into _loc_type
*/
public void setLoc_type(String loc_type) {
	_loc_type = loc_type;
}

/**
Sets _loc_type_desc
@param loc_type_desc value to put into _loc_type_desc
*/
public void setLoc_type_desc(String loc_type_desc) {
	_loc_type_desc = loc_type_desc;
}

/**
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_LocType {"		+ "\n" +
		"Loc_type:      " + _loc_type + "\n" +
		"Loc_type_desc: " + _loc_type_desc + "\n}\n";
}

}