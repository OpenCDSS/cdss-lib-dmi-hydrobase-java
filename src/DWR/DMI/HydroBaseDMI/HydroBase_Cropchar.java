//------------------------------------------------------------------------------
// HydroBase_Cropchar - data structure to hold data from the HydroBase 
//	cropchar table
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// Notes:	(1)	This class has no knowledge of the database itself
//			(aside from its own data members), and there is no
//			knowledge of the connection with the database.
//		(2)	This class only holds information from the cropchar
//			table.
//------------------------------------------------------------------------------
// History:
// 
// 2002-11-25	Steven A. Malers, RTi	Initial version from
//					HBCropChar
// 2003-01-05	SAM, RTi		Update based on changes to the DMI
//					package.
// 2003-02-13	J. Thomas Sapienza, RTi	Added static methods from HBDMI package.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Vector;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
This class provides storage for data from the HydroBase cropchar table.
The data and methods in this class correspond to the table entities described
in the HydroBase data dictionary.
*/
public class HydroBase_Cropchar 
extends DMIDataObject {

// Cropchar table data

protected int _cropnum = 		DMIUtil.MISSING_INT;
protected String _method_desc =		DMIUtil.MISSING_STRING;
protected String _cropname =		DMIUtil.MISSING_STRING;
protected double _tempearlymoisture = 	DMIUtil.MISSING_DOUBLE;
protected double _templatemoisture = 	DMIUtil.MISSING_DOUBLE;
protected double _initialroot = 	DMIUtil.MISSING_DOUBLE;
protected double _maxroot =	 	DMIUtil.MISSING_DOUBLE;
protected double _madlevel =	 	DMIUtil.MISSING_DOUBLE;
protected double _maxappdepth =	 	DMIUtil.MISSING_DOUBLE;
protected int _daystofullcover = 	DMIUtil.MISSING_INT;
protected int _plantingmon = 		DMIUtil.MISSING_INT;
protected int _plantingday = 		DMIUtil.MISSING_INT;
protected int _harvestmon = 		DMIUtil.MISSING_INT;
protected int _harvestday = 		DMIUtil.MISSING_INT;
protected int _daystofirstcut = 	DMIUtil.MISSING_INT;
protected int _daysbetweencuts = 	DMIUtil.MISSING_INT;
protected int _lengthofseason = 	DMIUtil.MISSING_INT;
protected String _springfrostmethod =	DMIUtil.MISSING_STRING;
protected String _fallfrostmethod =	DMIUtil.MISSING_STRING;
protected int _irrig_cropnum =	 	DMIUtil.MISSING_INT;

/**
Construct and initialize to empty strings and missing data.
*/
public HydroBase_Cropchar() {
	super();
}

/**
Converts a frost date flag to a method.
@param flag frost date flag
@return a string method for the frost date flag
*/
public static String convertFrostDateFlagToMethod(int flag) {
	if (flag == 1) {
		return "28-deg";
	} 
	else if (flag == 2) {
		return "32-deg";
	} 
	else {
		return "monthly-mean";
	}
}

/**
Converts a method to a frost date flag.
@param method the method to convert
@return a frost date flag for the method.
*/
public static int convertMethodToFrostDateFlag(String method) {
	if (method.equals("28-deg")) {
	 	return 1;
	} 
	else if (method.equals("32-deg")) {
		return 2;
	} 
	else {
		return 0;
	}
}

/**
Finalize before garbage collection.
@exception Throwable if an error occurs.
*/
protected void finalize ()
throws Throwable {
	_method_desc = null;
	_cropname = null;
	_springfrostmethod = null;
	_fallfrostmethod = null;
	super.finalize();
}

/**
Return _cropname
@return _cropname
*/
public String getCropname() {
	return _cropname;
}

/**
Return _cropnum
@return _cropnum
*/
public int getCropnum() {
	return _cropnum;
}

/**
Return _daysbetweencuts
@return _daysbetweencuts
*/
public int getDaysbetweencuts() {
	return _daysbetweencuts;
}

/**
Return _daystofirstcut
@return _daystofirstcut
*/
public int getDaystofirstcut() {
	return _daystofirstcut;
}

/**
Return _daystofullcover
@return _daystofullcover
*/
public int getDaystofullcover() {
	return _daystofullcover;
}

/**
Return _fallfrostmethod
@return _fallfrostmethod
*/
public String getFallfrostmethod() {
	return _fallfrostmethod;
}

/**
Return _initialroot
@return _initialroot
*/
public double getInitialroot() {
	return _initialroot;
}

/**
Return _irrig_cropnum
@return _irrig_cropnum
*/
public int getIrrig_cropnum() {
	return _irrig_cropnum;
}

/**
Return _lengthofseason
@return _lengthofseason
*/
public int getLengthofseason() {
	return _lengthofseason;
}

/**
Return _madlevel
@return _madlevel
*/
public double getMadlevel() {
	return _madlevel;
}

/**
Return _maxappdepth
@return _maxappdepth
*/
public double getMaxappdepth() {
	return _maxappdepth;
}

/**
Return _maxroot
@return _maxroot
*/
public double getMaxroot() {
	return _maxroot;
}

/**
Return _method_desc
@return _method_desc
*/
public String getMethod_desc() {
	return _method_desc;
}

/**
Return _harvestday
@return _harvestgday
*/
public int getHarvestday() {
	return _harvestday;
}

/**
Return _harvestmon
@return _harvestmon
*/
public int getHarvestmon() {
	return _harvestmon;
}

/**
Return _plantingday
@return _plantingday
*/
public int getPlantingday() {
	return _plantingday;
}

/**
Return _plantingmon
@return _plantingmon
*/
public int getPlantingmon() {
	return _plantingmon;
}

/**
Return _springfrostmethod
@return _springfrostmethod
*/
public String getSpringfrostmethod() {
	return _springfrostmethod;
}

/**
Return _tempearlymoisture
@return _tempearlymoisture
*/
public double getTempearlymoisture() {
	return _tempearlymoisture;
}

/**
Return _templatemoisture
@return _templatemoisture
*/
public double getTemplatemoisture() {
	return _templatemoisture;
}

/**
Set _cropname.
@param cropname the value for _cropname.
*/
public void setCropname ( String cropname )
{	_cropname = cropname;
}

/**
Set _cropnum.
@param cropnum the value for _cropnum.
*/
public void setCropnum ( int cropnum )
{	_cropnum = cropnum;
}

/**
Set _daysbetweencuts.
@param daysbetweencuts the value for _daysbetweencuts.
*/
public void setDaysbetweencuts ( int daysbetweencuts )
{	_daysbetweencuts = daysbetweencuts;
}

/**
Set _daystofirstcut.
@param daystofirstcut the value for _daystofirstcut.
*/
public void setDaystofirstcut ( int daystofirstcut )
{	_daystofirstcut = daystofirstcut;
}

/**
Set _daystofullcover.
@param daystofullcover the value for _daystofullcover.
*/
public void setDaystofullcover ( int daystofullcover )
{	_daystofullcover = daystofullcover;
}

/**
Set _fallfrostmethod.
@param fallfrostmethod the value for _fallfrostmethod.
*/
public void setFallfrostmethod ( String fallfrostmethod )
{	_fallfrostmethod = fallfrostmethod;
}

/**
Set _harvestday.
@param harvestday the value for _harvestday.
*/
public void setHarvestday ( int harvestday )
{	_harvestday = harvestday;
}

/**
Set _harvestmon.
@param harvestmon the value for _harvestmon.
*/
public void setHarvestmon ( int harvestmon )
{	_harvestmon = harvestmon;
}

/**
Set _initialroot.
@param initialroot the value for _initialroot.
*/
public void setInitialroot ( double initialroot )
{	_initialroot = initialroot;
}

/**
Set _irrig_cropnum.
@param irrig_cropnum the value for _irrig_cropnum.
*/
public void setIrrig_cropnum ( int irrig_cropnum )
{	_irrig_cropnum = irrig_cropnum;
}

/**
Set _lengthofseason.
@param lengthofseason the value for _lengthofseason.
*/
public void setLengthofseason ( int lengthofseason )
{	_lengthofseason = lengthofseason;
}

/**
Set _madlevel.
@param madlevel the value for _madlevel.
*/
public void setMadlevel ( double madlevel )
{	_madlevel = madlevel;
}

/**
Set _maxappdepth.
@param maxappdepth the value for _maxappdepth.
*/
public void setMaxappdepth ( double maxappdepth )
{	_maxappdepth = maxappdepth;
}

/**
Set _maxroot.
@param maxroot the value for _maxroot.
*/
public void setMaxroot ( double maxroot )
{	_maxroot = maxroot;
}

/**
Set _method_desc.
@param method_desc the value for _method_desc.
*/
public void setMethod_desc ( String method_desc )
{	_method_desc = method_desc;
}

/**
Set _plantingday.
@param plantingday the value for _plantingday.
*/
public void setPlantingday ( int plantingday )
{	_plantingday = plantingday;
}

/**
Set _plantingmon.
@param plantingmon the value for _plantingmon.
*/
public void setPlantingmon ( int plantingmon )
{	_plantingmon = plantingmon;
}

/**
Set _springfrostmethod.
@param springfrostmethod the value for _springfrostmethod.
*/
public void setSpringfrostmethod ( String springfrostmethod )
{	_springfrostmethod = springfrostmethod;
}

/**
Set _templatemoisture.
@param templatemoisture the value for _templatemoisture.
*/
public void setTemplatemoisture ( double templatemoisture )
{	_templatemoisture = templatemoisture;
}

/**
Set _tempearlymoisture.
@param tempearlymoisture the value for _tempearlymoisture.
*/
public void setTempearlymoisture ( double tempearlymoisture )
{	_tempearlymoisture = tempearlymoisture;
}

public String toString() {
	return "";
/*
	return "HydroBase_Cropchar{" 			+ "\n" + 
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
		"modified:     " + _modified		+ "\n" +
		"user_num:     " + _user_num		+ "}";
*/
}

}
