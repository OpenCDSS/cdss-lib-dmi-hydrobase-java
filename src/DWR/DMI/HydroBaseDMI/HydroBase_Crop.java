//------------------------------------------------------------------------------
// HydroBase_Crop - Class to store data from the HydroBase crop table.
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
// 2002-09-26	J. Thomas Sapienza, RTi	Initial version from HBCrop.
// 2003-01-05	SAM, RTi		Update based on changes to the DMI
//					package.
// 2006-05-25	JTS, RTi		Deprecated old and badly-named method
//					names.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Vector;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
This class provides storage for data from the HydroBase crop table.
The data and methods in this class correspond to the table entities described
in the HydroBase data dictionary.  Use the
HBCropQuery class to query instances of this class.
*/
public class HydroBase_Crop 
extends DMIDataObject {

protected String _id = 			DMIUtil.MISSING_STRING;
protected String _landuse = 		DMIUtil.MISSING_STRING;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected int _year = 			DMIUtil.MISSING_INT;
protected double _acres_total = 	DMIUtil.MISSING_DOUBLE;

/**
Construct and initialize to empty strings and missing data from HBData.
*/
public HydroBase_Crop() {
	super();
}

/**
Finalize before garbage collection.
@exception Throwable if an error occurs.
*/
protected void finalize ()
throws Throwable {
	_id = null;
	_landuse = null;
	super.finalize();
}

/**
@deprecated use getAcres_total().
*/
public double getacres_total() {
	return _acres_total;
}

public double getAcres_total() {
	return _acres_total;
}

public String getID() {
	return _id;
}

/**
@deprecated use getLanduse().
*/
public String getlanduse() {
	return _landuse;
}

public String getLanduse() {
	return _landuse;
}

/**
@deprecated use getStructure_num().
*/
public int getstructure_num() {
	return _structure_num;
}

public int getStructure_num() {
	return _structure_num;
}

/**
@deprecated use getYear().
*/
public int getyear() {
	return _year;
}

public int getYear() {
	return _year;
}

/**
@deprecated use setAcres_total().
*/
public void setacres_total(double acres_total) {
	_acres_total = acres_total;
}

public void setAcres_total(double acres_total) {
	_acres_total = acres_total;
}

public void setID(String id) {
	_id = id;
}

/**
@deprecated use setLanduse().
*/
public void setlanduse(String landuse) {
	_landuse = landuse;
}

public void setLanduse(String landuse) {
	_landuse = landuse;
}

/**
@deprecated use setStructure_num().
*/
public void setstructure_num(int structure_num) {
	_structure_num = structure_num;
}

public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
@deprecated use setYear().
*/
public void setyear(int year) {
	_year = year;
}

public void setYear(int year) {
	_year = year;
}


public String toString() {
	return "HydroBase_Crop{" 			+ "\n" + 
		"id:            " + _id		+ "\n" + 
		"structure_num: " + _structure_num	+ "\n" + 
		"landuse:       " + _landuse		+ "\n" + 
		"year:          " + _year 		+ "\n" +
		"acres_total:   " + _acres_total	+ "};";
}

}
