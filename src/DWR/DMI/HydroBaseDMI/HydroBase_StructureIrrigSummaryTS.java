// HydroBase_StructureIrrigSummaryTS - Class to store data from the HydroBase structure and irrig_summary_ts tables.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

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
Class to store data from the HydroBase structure and irrig_summary_ts tables.
*/
public class HydroBase_StructureIrrigSummaryTS
extends HydroBase_Structure {

protected int _cal_year = 		DMIUtil.MISSING_INT;
protected String _land_use = 		DMIUtil.MISSING_STRING;
protected double _acres_total = 	DMIUtil.MISSING_DOUBLE;
protected double _acres_by_drip = 	DMIUtil.MISSING_DOUBLE;
protected double _acres_by_flood = 	DMIUtil.MISSING_DOUBLE;
protected double _acres_by_furrow = 	DMIUtil.MISSING_DOUBLE;
protected double _acres_by_sprinkler =	DMIUtil.MISSING_DOUBLE;

// Data that are used by software only and are not in HydroBase...

protected String _structure_id = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_StructureIrrigSummaryTS() {
	super();
}

/**
Copy constructor to fill in all the values of this object from a corresponding
view.  Used during unit tests.
*/
public HydroBase_StructureIrrigSummaryTS(HydroBase_StructureView view) {
	_structure_num = view._structure_num;
	_wdwater_num = view._wdwater_num;
	_geoloc_num = view._geoloc_num;
	_div = view._div;
	_wd = view._wd;
	_id = view._id;
	_wdid = view._wdid;
	_ciu = view._ciu;
	_str_type = view._str_type;
	_STRTYPE = view._STRTYPE;
	_str_name = view._str_name;
	_est_capacity = view._est_capacity;
	_est_unit = view._est_unit;
	_dcr_capacity = view._dcr_capacity;
	_dcr_unit = view._dcr_unit;
	_transbsn = view._transbsn;
	_modified = view._modified;
	_user_num = view._user_num;
	_common_id = view._common_id;
	_xtia = view._xtia;
	_aquifer_num = view._aquifer_num;
	_stream_num = view._stream_num;
	_str_mile = view._str_mile;
	_cal_year = view._cal_year;
	_land_use = view._land_use;
	_acres_total = view._acres_total;
	_acres_by_drip = view._acres_by_drip;
	_acres_by_flood = view._acres_by_flood;
	_acres_by_furrow = view._acres_by_furrow;
	_acres_by_sprinkler = view._acres_by_sprinkler;
}

/**
Returns _acres_by_drip
@return _acres_by_drip
*/
public double getAcres_by_drip() {
	return _acres_by_drip;
}

/**
Returns _acres_by_flood
@return _acres_by_flood
*/
public double getAcres_by_flood() {
	return _acres_by_flood;
}

/**
Returns _acres_by_furrow
@return _acres_by_furrow
*/
public double getAcres_by_furrow() {
	return _acres_by_furrow;
}

/**
Returns _acres_by_sprinkler
@return _acres_by_sprinkler
*/
public double getAcres_by_sprinkler() {
	return _acres_by_sprinkler;
}

/**
Returns _acres_total
@return _acres_total
*/
public double getAcres_total() {
	return _acres_total;
}

/**
Returns _cal_year
@return _cal_year
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _land_use
@return _land_use
*/
public String getLand_use() {
	return _land_use;
}

/**
Returns _structure_id
@return _structure_id
*/
public String getStructure_id() {
	return _structure_id;
}

/**
Sets _acres_by_drip
@param acres_by_drip value to put into _acres_by_drip
*/
public void setAcres_by_drip(double acres_by_drip) {
	_acres_by_drip = acres_by_drip;
}

/**
Sets _acres_by_flood
@param acres_by_flood value to put into _acres_by_flood
*/
public void setAcres_by_flood(double acres_by_flood) {
	_acres_by_flood = acres_by_flood;
}

/**
Sets _acres_by_furrow
@param acres_by_furrow value to put into _acres_by_furrow
*/
public void setAcres_by_furrow(double acres_by_furrow) {
	_acres_by_furrow = acres_by_furrow;
}

/**
Sets _acres_by_sprinkler
@param acres_by_sprinkler value to put into _acres_by_sprinkler
*/
public void setAcres_by_sprinkler(double acres_by_sprinkler) {
	_acres_by_sprinkler = acres_by_sprinkler;
}

/**
Sets _acres_total
@param acres_total value to put into _acres_total
*/
public void setAcres_total(double acres_total) {
	_acres_total = acres_total;
}

/**
Sets _cal_year
@param cal_year value to put into _cal_year
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _land_use
@param land_use value to put into _land_use
*/
public void setLand_use(String land_use) {
	_land_use = land_use;
}

/**
Sets _structure_id
@param structure_id value to put into _structure_id
*/
public void setStructure_id(String structure_id) {
	_structure_id = structure_id;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_IrrigSummaryTSStructure {"		+ "\n" + 
		"Cal_year:           " + _cal_year + "\n" + 
		"Land_use:           " + _land_use + "\n" + 
		"Acres_total:        " + _acres_total + "\n" + 
		"Acres_by_drip:      " + _acres_by_drip + "\n" + 
		"Acres_by_flood:     " + _acres_by_flood + "\n" + 
		"Acres_by_furrow:    " + _acres_by_furrow + "\n" + 
		"Acres_by_sprinkler: " + _acres_by_sprinkler + "\n}\n" + 
		super.toString();
}

}
