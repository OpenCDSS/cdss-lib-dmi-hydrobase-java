// HydroBase_MonthlyTemp - Class to hold data from the HydroBase monthly_temp table.

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
Class to store data from the HydroBase monthly_temp table.
*/
public class HydroBase_MonthlyTemp
extends HydroBase_MonthlyBase {

protected double _avg_max_t =	DMIUtil.MISSING_DOUBLE;
protected double _avg_min_t = 	DMIUtil.MISSING_DOUBLE;
protected double _mean_t = 	DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_MonthlyTemp() {
	super();
}

/**
Returns _avg_max_t
@return _avg_max_t
*/
public double getAvg_max_t() {
	return _avg_max_t;
}

/**
Returns _avg_min_t
@return _avg_min_t
*/
public double getAvg_min_t() {
	return _avg_min_t;
}

/**
Returns _mean_t
@return _mean_t
*/
public double getMean_t() {
	return _mean_t;
}

/**
Sets _avg_max_t
@param avg_max_t value to put into _avg_max_t
*/
public void setAvg_max_t(double avg_max_t) {
	_avg_max_t = avg_max_t;
}

/**
Sets _avg_min_t
@param avg_min_t value to put into _avg_min_t
*/
public void setAvg_min_t(double avg_min_t) {
	_avg_min_t = avg_min_t;
}

/**
Sets _mean_t
@param mean_t value to put into _mean_t
*/
public void setMean_t(double mean_t) {
	_mean_t = mean_t;
}

/**
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_MonthlyTemp {"		+ "\n" +
		"Avg_max_t: " + _avg_max_t + "\n" +
		"Avg_min_t: " + _avg_min_t + "\n" +
		"Mean_t:    " + _mean_t + "\n}\n";
}

}