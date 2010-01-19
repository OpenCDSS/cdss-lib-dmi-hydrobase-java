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
public class HydroBase_ParcelUseTSStructureToParcel extends HydroBase_ParcelUseTS {

// structure_to_parcel fields
protected int _structure_num = DMIUtil.MISSING_INT;
protected int _primary_flag = DMIUtil.MISSING_INT;
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