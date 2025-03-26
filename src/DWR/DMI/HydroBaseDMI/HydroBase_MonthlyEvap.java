// HydroBase_MonthlyEvap - Class to hold data from the HydroBase monthly_evap table.

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
Class to store data from the HydroBase monthly_evap table.
*/
public class HydroBase_MonthlyEvap
extends HydroBase_MonthlyBase {

protected double _total_evap = DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_MonthlyEvap() {
	super();
}

/**
Returns _total_evap
@return _total_evap
*/
public double getTotal_evap() {
	return _total_evap;
}

/**
Sets _total_evap
@param total_evap value to put into _total_evap
*/
public void setTotal_evap(double total_evap) {
	_total_evap = total_evap;
}

/**
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_MonthlyEvap {"		+ "\n" +
		"Total_evap: " + _total_evap + "\n}\n"
		+ super.toString();
}

}