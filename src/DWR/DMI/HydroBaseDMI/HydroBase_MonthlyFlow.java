// HydroBase_MonthlyFlow - Class to hold data from the HydroBase monthly_flow table.

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
Class to store data from the HydroBase monthly_flow table.
*/
public class HydroBase_MonthlyFlow
extends HydroBase_MonthlyBase {

protected double _max_q_cfs = 	DMIUtil.MISSING_DOUBLE;
protected double _min_q_cfs = 	DMIUtil.MISSING_DOUBLE;
protected double _total_q_af = 	DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_MonthlyFlow() {
	super();
}

/**
Returns _max_q_cfs
@return _max_q_cfs
*/
public double getMax_q_cfs() {
	return _max_q_cfs;
}

/**
Returns _min_q_cfs
@return _min_q_cfs
*/
public double getMin_q_cfs() {
	return _min_q_cfs;
}

/**
Returns _total_q_af
@return _total_q_af
*/
public double getTotal_q_af() {
	return _total_q_af;
}

/**
Sets _max_q_cfs
@param max_q_cfs value to put into _max_q_cfs
*/
public void setMax_q_cfs(double max_q_cfs) {
	_max_q_cfs = max_q_cfs;
}

/**
Sets _min_q_cfs
@param min_q_cfs value to put into _min_q_cfs
*/
public void setMin_q_cfs(double min_q_cfs) {
	_min_q_cfs = min_q_cfs;
}

/**
Sets _total_q_af
@param total_q_af value to put into _total_q_af
*/
public void setTotal_q_af(double total_q_af) {
	_total_q_af = total_q_af;
}

/**
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_MonthlyFlow {"		+ "\n" +
		"Max_q_cfs:  " + _max_q_cfs + "\n" +
		"Min_q_cfs:  " + _min_q_cfs + "\n" +
		"Total_q_af: " + _total_q_af + "\n}\n";
}

}