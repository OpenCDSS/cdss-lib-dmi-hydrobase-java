// ----------------------------------------------------------------------------
// HydroBase_GroundWaterWellsVolcanics.java - Class to hold data from the 
//	HydroBase volcanics table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-17	J. Thomas Sapienza, RTi	Initial version.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2005-06-29	JTS, RTi		* Changed structure_num to well_num.
//					* Now extends 
//					  HydroBase_GroundWaterWellsView
//					* Renamed from 
//					  HydroBase_Volcanics.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

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
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	super.finalize();
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
