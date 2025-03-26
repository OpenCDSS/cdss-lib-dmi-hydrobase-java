// HydroBase_CUClimWts - Class to hold data from the HydroBase cu_clim_wts table.

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
Class to store data from the HydroBase cu_clim_wts table.
*/
public class HydroBase_CUClimWts
extends DMIDataObject {

protected String _hydrounit =	DMIUtil.MISSING_STRING;
protected String _county = 	DMIUtil.MISSING_STRING;
protected int _station_num = 	DMIUtil.MISSING_INT;
protected int _cty = 		DMIUtil.MISSING_INT;
protected double _temp_wt = 	DMIUtil.MISSING_DOUBLE;
protected double _pcpn_wt = 	DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_CUClimWts() {
	super();
}

/**
Returns _hydrounit.
@return _hydrounit.
*/
public String getHydrounit () {
	return _hydrounit;
}

/**
Returns _county.
@return _county.
*/
public String getCounty () {
	return _county;
}

/**
Returns _station_num.
@return _station_num.
*/

public int getStation_num () {
	return _station_num;
}

/**
Returns _cty.
@return _cty.
*/

public int getCty () {
	return _cty;
}

/**
Returns _temp_wt.
@return _temp_wt.
*/
public double getTemp_wt () {
	return _temp_wt;
}

/**
Returns _pcpn_wt.
@return _pcpn_wt.
*/
public double getPcpn_wt () {
	return _pcpn_wt;
}

/**
Sets _county.
@param county value to put into _county.
*/
public void setCounty (String county) {
	_county = county;
}

/**
Sets _cty.
@param cty value to put into _cty.
*/
public void setCty (int cty) {
	_cty = cty;
}

/**
Sets _hydrounit.
@param hydrounit value to put into _hydrounit.
*/
public void setHydrounit (String hydrounit) {
	_hydrounit = hydrounit;
}

/**
Sets _pcpn_wt.
@param pcpn_wt value to put into _pcpn_wt.
*/
public void setPcpn_wt (double pcpn_wt) {
	_pcpn_wt = pcpn_wt;
}

/**
Sets _station_num.
@param station_num value to put into _station_num.
*/
public void setStation_num (int station_num) {
	_station_num = station_num;
}

/**
Sets _temp_wt.
@param temp_wt value to put into _temp_wt.
*/
public void setTemp_wt (double temp_wt) {
	_temp_wt = temp_wt;
}

/**
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_CUClimWts {"				+ "\n" +
		"Hydrounit:   '" + _hydrounit 		+ "'\n" +
		"County:      '" + _county 		+ "'\n" +
		"Station_num: " + _station_num 		+ "\n" +
		"Cty:         " + _cty 			+ "\n" +
		"Temp_wt:     " + _temp_wt 		+ "\n" +
		"Pcpn_wt:     " + _pcpn_wt 		+ "\n}\n";
}

}