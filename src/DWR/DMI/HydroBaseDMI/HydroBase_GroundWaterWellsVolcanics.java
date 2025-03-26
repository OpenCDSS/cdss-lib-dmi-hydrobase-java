// HydroBase_GroundWaterWellsVolcanics - Class to hold data from the HydroBase volcanics table.

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

import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase volcanics table.
*/
public class HydroBase_GroundWaterWellsVolcanics
extends HydroBase_GroundWaterWellsView {

protected int _well_num =	DMIUtil.MISSING_INT;
protected int _voltop = 	DMIUtil.MISSING_INT;
protected int _volbase = 	DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_GroundWaterWellsVolcanics() {
	super();
}

/**
Returns _well_num
@return _well_num
*/
public int getWell_num() {
	return _well_num;
}

/**
Returns _voltop
@return _voltop
*/
public int getVoltop() {
	return _voltop;
}

/**
Returns _volbase
@return _volbase
*/
public int getVolbase() {
	return _volbase;
}

/**
Sets _well_num
@param well_num value to put into _well_num
*/
public void setWell_num(int well_num) {
	_well_num = well_num;
}

/**
Sets _voltop
@param voltop value to put into _voltop
*/
public void setVoltop(int voltop) {
	_voltop = voltop;
}

/**
Sets _volbase
@param volbase value to put into _volbase
*/
public void setVolbase(int volbase) {
	_volbase = volbase;
}

/**
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_GroundWaterWellsVolcanics {"		+ "\n" +
		"Well_num: " + _well_num + "\n" +
		"Voltop:        " + _voltop + "\n" +
		"Volbase:       " + _volbase + "\n}\n";
}

}
