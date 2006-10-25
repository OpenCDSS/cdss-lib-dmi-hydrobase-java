//------------------------------------------------------------------------------
// HydroBase_Structure - data structure to hold data from the the HydroBase 
//	structure table
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// Notes:	(1)	This class has no knowledge of the database itself
//			(aside from its own data members), and there is no
//			knowledge of the connection with the database.
//		(2)	This class only holds information from the structure
//			table.  It does not hold data for the Headgate and
//			other tables.
//------------------------------------------------------------------------------
// History:
// 
// 2002-09-19	J. Thomas Sapienza, RTi	Initial version from DWR.DMI.HB.
//					HBStructure.java
// 2002-11-07	Steven A. Malers, RTi	Rework code to be a base class for all
//					Structure-related objects (like
//					HydroBase_StructureGeoloc) and name
//					methods consistent with other DMI work.
// 2003-01-05	SAM, RTi		Update based on changes to the DMI
//					package.
// 2003-02-14	JTS, RTi		Added xtia back in to satisfy an HBDMI
//					query 
// 2003-02-19	JTS, RTi		Added some fields to support older 
//					versions of the database.
// 2003-02-20	JTS, RTi		Commented out X* data
// 2004-01-13	SAM, RTi		Added "common_id" as a data member to
//					use in a work-around for the well data
//					storage problems.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.io.IOException;

import java.util.Date;
import java.util.Vector;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
This class provides storage for data from the HydroBase structure table.
The data and methods in this class correspond to the table entities described
in the HydroBase data dictionary.
*/
public class HydroBase_Structure 
extends DMIDataObject {

// List in the order of the ER diagram (March 2001) - xtia is not included
// because it is do to be phased out...

protected int _structure_num = 	DMIUtil.MISSING_INT;
protected int _wdwater_num = 	DMIUtil.MISSING_INT;
protected int _geoloc_num = 	DMIUtil.MISSING_INT;
protected int _div = 		DMIUtil.MISSING_INT;
protected int _wd = 		DMIUtil.MISSING_INT;
protected int _id = 		DMIUtil.MISSING_INT;
protected String _ciu = 	DMIUtil.MISSING_STRING;
protected String _str_type = 	DMIUtil.MISSING_STRING;
protected String _STRTYPE = 	DMIUtil.MISSING_STRING;
protected String _str_name = 	DMIUtil.MISSING_STRING;
protected float _est_capacity = DMIUtil.MISSING_FLOAT;
protected String _est_unit = 	DMIUtil.MISSING_STRING;
protected float _dcr_capacity = DMIUtil.MISSING_FLOAT;
protected String _dcr_unit = 	DMIUtil.MISSING_STRING;
protected int _transbsn = 	DMIUtil.MISSING_INT;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;
protected String _common_id =	DMIUtil.MISSING_STRING;
						// This is used to store the
						// common identifier that is
						// different from the WDID, in
						// particular for
						// "unpermitted_wells" data.
						// This is used in TSTool to
						// Allow software to display
						// the familiar ID, even though
						// the data have been force fit
						// to connect to structures.
						//
						// REVISIT - remove when
						// HydroBase is redesigned.

// fields used in older version of the database
protected double _xtia = DMIUtil.MISSING_INT;
protected int _aquifer_num = DMIUtil.MISSING_INT;
protected int _stream_num = DMIUtil.MISSING_INT;
protected double _str_mile = DMIUtil.MISSING_DOUBLE;

/**
Construct and initialize to empty strings and missing data.
*/
public HydroBase_Structure() {
	super();
}

/**
Copy constructor to fill in all the values of this object from a corresponding
view.  Used during unit tests.  Note that it does not copy across all data 
members, such as stream_num or str_mile, as these are not read from the 
database for structure.
*/
public HydroBase_Structure(HydroBase_StructureView view) {
	_structure_num = view._structure_num;
	_wdwater_num = view._wdwater_num;
	_geoloc_num = view._geoloc_num;
	_div = view._div;
	_wd = view._wd;
	_id = view._id;
	_ciu = view._ciu;
	_str_type = view._str_type;
	_STRTYPE = view._strtype;
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
}

/**
Finalize before garbage collection.
@exception Throwable if an error occurs.
*/
protected void finalize ()
throws Throwable
{	_ciu = null;
	_common_id = null;
	_str_type = null;
	_STRTYPE = null;
	_str_name = null;
	_est_unit = null;
	_dcr_unit = null;
	_modified = null;
	super.finalize();
}

/**
Returns _aquifer_num
@return _aquifer_num
*/
public int getAquifer_num() {
	return _aquifer_num;
}

/**
Returns _ciu
@return _ciu
*/
public String getCiu() {
	return _ciu;
}

/**
Returns _common_id
@return _common_id
*/
public String getCommon_id() {
	return _common_id;
}

/**
Returns _dcr_capacity
@return _dcr_capacity
*/
public float getDcr_capacity() {
	return _dcr_capacity;
}

/**
Returns _dcr_unit
@return _dcr_unit
*/
public String getDcr_unit() {
	return _dcr_unit;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _est_capacity
@return _est_capacity
*/
public float getEst_capacity() {
	return _est_capacity;
}

/**
Returns _est_unit
@return _est_unit
*/
public String getEst_unit() {
	return _est_unit;
}

/**
Returns _geoloc_num
@return _geoloc_num
*/
public int getGeoloc_num() {
	return _geoloc_num;
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _str_mile
@return _str_mile
*/
public double getStr_mile() {
	return _str_mile;
}

/**
Returns _str_name
@return _str_name
*/
public String getStr_name() {
	return _str_name;
}

/**
Returns _str_type
@return _str_type
*/
public String getStr_type() {
	return _str_type;
}

/**
Returns _stream_num
@return _stream_num
*/
public int getStream_num() {
	return _stream_num;
}

/** 
Returns _STRTYPE
@return _STRTYPE
*/
public String getSTRTYPE() {
	return _STRTYPE;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _transbsn
@return _transbsn
*/
public int getTransbsn() {
	return _transbsn;
}

/**
Returns _user_num 
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Returns _wdwater_num
@return _wdwater_num
*/
public int getWdwater_num() {
	return _wdwater_num;
}

/**
Returns _xtia
@return _xtia
*/
public double getXtia() {
	return _xtia;
}

/**
Sets _aquifer_num
@param aquifer_num value to put into _aquifer_num
*/
public void setAquifer_num(int aquifer_num) {
	_aquifer_num = aquifer_num;
}

/**
Sets _ciu
@param ciu the value to which _ciu will be set
*/
public void setCiu(String ciu) {
	_ciu = ciu;
}

/**
Sets _common_id
@param common_id the value to which _common_id will be set
*/
public void setCommon_id(String common_id) {
	_common_id = common_id;
}

/**
Sets _dcr_capacity
@param dcr_capacity the value to which _dcr_capacity will be set
*/
public void setDcr_capacity(float dcr_capacity) {
	_dcr_capacity = dcr_capacity;
}

/**
Sets _dcr_unit
@param dcr_unit the value to which _dcr_unit will be set
*/
public void setDcr_unit(String dcr_unit) {
	_dcr_unit = dcr_unit;
}

/**
Sets _div
@param div the value to which _div will be set
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _est_capacity
@param est_capacity the value to which _est_capacity will be set
*/
public void setEst_capacity(float est_capacity) {
	_est_capacity = est_capacity;
}

/**
Sets _est_unit
@param est_unit the vlaue tow hich _est_unit will be set
*/
public void setEst_unit(String est_unit) {
	_est_unit = est_unit;
}

/**
Sets _geoloc_num
@param geoloc_num the value to which _geoloc_num will be set
*/
public void setGeoloc_num(int geoloc_num) {
	_geoloc_num = geoloc_num;
}

/**
Sets _id
@param id the value to which _id will be set
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _modified
@param modified the value to which _modified will be set
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _str_mile
@param str_mile the value to put into _str_mile
*/
public void setStr_mile(double str_mile) {
	_str_mile = str_mile;
}

/**
Sets _str_name
@param str_name the value to which _str_name will be set
*/
public void setStr_name(String str_name) {
	_str_name = str_name;
}

/**
Sets _str_type
@param str_type the value to which _str_type will be set
*/
public void setStr_type(String str_type) {
	_str_type = str_type;
}

/**
Sets _stream_num
@param stream_num value to put into _stream_num
*/
public void setStream_num(int stream_num) {
	_stream_num = stream_num;
}

/**
Sets _STRTYPE
@param STRTYPE the value to which _STRTYPE will be set
*/
public void setSTRTYPE(String STRTYPE) {
	_STRTYPE = STRTYPE;
}

/**
Sets _structure_num
@param structure_num the value to which _structure_num will be set
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _transbsn
@param transbsn the value to which _transbsn will be set
*/
public void setTransbsn(int transbsn) {
	_transbsn = transbsn;
}

/**
Sets _user_num
@param user_num the value to which _user_num will be set
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/**
Sets _wd
@param wd the value to which _wd will be set
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Sets _wdwater_num 
@param wdwater_num the value to which _wdwater_num will be set
*/
public void setWdwater_num(int wdwater_num) {
	_wdwater_num = wdwater_num;
}

/*
Sets _xtia
@param xtia value to put into _xtia
*/
public void setXtia (double xtia) {
	_xtia = xtia;
}

public String toString() {
	return "HydroBase_Structure{" 			+ "\n" + 
		"structure_num:" + _structure_num	+ "\n" + 
		"wdwater_num:  " + _wdwater_num		+ "\n" + 
		"geoloc_num:   " + _geoloc_num		+ "\n" + 
		"div:          " + _div 		+ "\n" +
		"wd:           " + _wd 			+ "\n" +
		"id:           " + _id			+ "\n" +
		"ciu:          " + _ciu			+ "\n" +
		"str_type:     " + _str_type		+ "\n" +
		"STRTYPE:      " + _STRTYPE		+ "\n" +
		"str_name:     " + _str_name		+ "\n" +
		"est_capacity: " + _est_capacity	+ "\n" +
		"est_unit:     " + _est_unit		+ "\n" +
		"dcr_capacity: " + _dcr_capacity	+ "\n" +
		"dcr_unit:     " + _dcr_unit		+ "\n" +
		"transbsn:     " + _transbsn		+ "\n" +
		"xtia:         " + _xtia                + "\n" + 
		"modified:     " + _modified		+ "\n" +
		"user_num:     " + _user_num		+ "}";
}

} // End HydroBase_Structure
