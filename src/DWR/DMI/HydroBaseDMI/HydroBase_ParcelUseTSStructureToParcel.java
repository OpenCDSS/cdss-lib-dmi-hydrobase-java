// HydroBase_ParcelUseTSStructureToParcel.java - class to hold data from the HydroBase parcel_use_ts and structure_to_parcel tables

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

// ----------------------------------------------------------------------------
// HydroBase_ParcelUseTSStructureToParcel.java - class to hold data from the
//		HydroBase parcel_use_ts and structure_to_parcel tables
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2004-09-21	Steven A. Malers, RTi	Split out of HydroBase_ParcelUseTS,
//					which was mistakenly a join of two
//					tables.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase parcel_use_ts, and structure_to_parcel tables.
*/
public class HydroBase_ParcelUseTSStructureToParcel extends HydroBase_ParcelUseTS
implements Comparable<HydroBase_ParcelUseTSStructureToParcel>
{

// structure_to_parcel fields
protected int _structure_num = DMIUtil.MISSING_INT;
// 2020-08-24 note that primary_flag is not used in vw_CDSS_ParcelUseTSStructureToParcel in HydroBase 20200720
protected int _primary_flag = DMIUtil.MISSING_INT;
// _percent_irrig is actually a fraction 0.0 to 1.0
protected double _percent_irrig = DMIUtil.MISSING_DOUBLE;

// TODO SAM 2010-01-18 Figure out whether can be set in original query.
// Used by StateDMI - not set during the original query but filled in with a secondary query

protected int __structureWD = DMIUtil.MISSING_INT;
protected int __structureID = DMIUtil.MISSING_INT;
protected String __structureName = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_ParcelUseTSStructureToParcel ()
{	super();
}

/**
Compare two objects for sorting.  The comparison is done by calendar year, division,
and parcel id.
*/
public int compareTo ( HydroBase_ParcelUseTSStructureToParcel o2 )
{
	if ( this.getCal_year() > o2.getCal_year() ) {
		return 1;
	}
	else if ( this.getCal_year() < o2.getCal_year() ) {
		return -1;
	}
	if ( this.getDiv() > o2.getDiv() ) {
		return 1;
	}
	else if ( this.getDiv() < o2.getDiv() ) {
		return -1;
	}
	if ( this.getParcel_id() > o2.getParcel_id() ) {
		return 1;
	}
	else if ( this.getParcel_id() < o2.getParcel_id() ) {
		return -1;
	}
	// Fall through... they are the same, at least to this level of comparison
	return 0;
}

/**
Cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
*/
protected void finalize()
throws Throwable
{	super.finalize();
}

/**
Returns _percent_irrig
@return _percent_irrig
*/
public double getPercent_irrig() {
	return _percent_irrig;
}

/**
Returns _primary_flag
@return _primary_flag
*/
public int getPrimary_flag() {
	return _primary_flag;
}

/**
Returns __structureID
@return __structureID
*/
public int getStructureID() {
	return __structureID;
}

/**
Returns __structureName
@return __structureName
*/
public String getStructureName() {
	return __structureName;
}

/**
Returns __structureWD
@return __structureWD
*/
public int getStructureWD() {
	return __structureWD;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Sets _percent_irrig
@param percent_irrig value to put into _percent_irrig
*/
public void setPercent_irrig(double percent_irrig) {
	_percent_irrig = percent_irrig;
}

/**
Sets _primary_flag
@param primary_flag value to put into _primary_flag
*/
public void setPrimary_flag(int primary_flag) {
	_primary_flag = primary_flag;
}

/**
Sets __structureID
@param structureID structure identifier (no leading WD).
*/
public void setStructureID(int structureID) {
	__structureID = structureID;
}

/**
Sets __structureName
@param structureName structure name.
*/
public void setStructureName(String structureName) {
	__structureName = structureName;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets __structureWD
@param structureWD structure water district.
*/
public void setStructureWD(int structureWD) {
	__structureWD = structureWD;
}

/** 
Return a string representation of this object.
@return a string representation of this object.
*/
public String toString() {
	return "HydroBase_ParcelUseTSStructureToParcel {\n" + 
		"Parcel_num:         " + _parcel_num + "\n" + 
		"Div:                " + _div + "\n" + 
		"Cal_year:           " + _cal_year + "\n" + 
		"Parcel_id:          " + _parcel_id + "\n" + 
		"Perimeter:          " + _perimeter + "\n" + 
		"Area:               " + _area + "\n" + 
		"Land_use:           " + _land_use + "\n" + 
		"Irrig_type:         " + _irrig_type + "\n" +
		"Structure_num:      " + _structure_num + "\n" +
		"Primary_flag:       " + _primary_flag + "\n" +
		"Percent_irrig:      " + _percent_irrig + "\n}";
}

}
