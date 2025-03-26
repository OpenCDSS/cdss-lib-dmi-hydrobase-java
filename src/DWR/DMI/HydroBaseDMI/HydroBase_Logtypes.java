// HydroBase_Logtypes.java - Class to hold data from the HydroBase logtypes table.

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
Class to store data from the HydroBase logtypes table.
*/
public class HydroBase_Logtypes
extends DMIDataObject {

protected int _logid = 		DMIUtil.MISSING_INT;
protected String _logty = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_Logtypes() {
	super();
}

/**
Returns _logid
@return _logid
*/
public int getLogid() {
	return _logid;
}

/**
Returns _logty
@return _logty
*/
public String getLogty() {
	return _logty;
}

/**
Sets _logid
@param logid value to put into _logid
*/
public void setLogid(int logid) {
	_logid = logid;
}

/**
Sets _logty
@param logty value to put into _logty
*/
public void setLogty(String logty) {
	_logty = logty;
}

/**
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Logtypes {"		+ "\n" +
		"Logid: " + _logid + "\n" +
		"Logty: " + _logty + "\n}\n";
}

}