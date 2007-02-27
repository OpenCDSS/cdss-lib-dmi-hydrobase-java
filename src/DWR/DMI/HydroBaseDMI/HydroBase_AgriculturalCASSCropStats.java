// ----------------------------------------------------------------------------
// HydroBase_AgriculturalCASSCropStats.java - Class to hold data from the
//	HydroBase Agricultural_CASS_crop_stats table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2004-02-07	Steven A. Malers, RTi	Initial version from HydroBase_Agstats.
//					Because column names have uppercase
//					letters, the names of methods also use
//					the same uppercase letters.
// 2004-02-20	SAM, RTi		Change the name of the class from
//					AgriculturalCropStats because the table
//					name was changed.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase Agricultural_CASS_crop_stats table.
*/
public class HydroBase_AgriculturalCASSCropStats 
extends DMIDataObject {

protected String _st = 		DMIUtil.MISSING_STRING;
protected String _county = 	DMIUtil.MISSING_STRING;
protected String _Commodity = 	DMIUtil.MISSING_STRING;
protected String _Practice = 	DMIUtil.MISSING_STRING;
protected int _cal_year = 	DMIUtil.MISSING_INT;
protected double _Planted = 	DMIUtil.MISSING_DOUBLE;
protected double _PltdHarv = 	DMIUtil.MISSING_DOUBLE;
protected double _Harvested = 	DMIUtil.MISSING_DOUBLE;
protected double _PltdYld = 	DMIUtil.MISSING_DOUBLE;
protected double _Yield = 	DMIUtil.MISSING_DOUBLE;
protected String _YieldUnit = 	DMIUtil.MISSING_STRING;
protected double _Production = 	DMIUtil.MISSING_DOUBLE;
protected String _ProductionUnit = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_AgriculturalCASSCropStats() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_st = null;
	_county = null;
	_Commodity = null;
	_Practice = null;
	_YieldUnit = null;
	_ProductionUnit = null;
	super.finalize();
}

/**
Returns _cal_year.
@return _cal_year.
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _Commodity.
@return _Commodity.
*/
public String getCommodity() {
	return _Commodity;
}

/**
Returns _county.
@return _county.
*/
public String getCounty() {
	return _county;
}

/**
Returns _Harvested.
@return _Harvested.
*/
public double getHarvested() {
	return _Harvested;
}

/**
Returns _Planted.
@return _Planted.
*/
public double getPlanted() {
	return _Planted;
}

/**
Returns _PltdHarv.
@return _PltdHarv.
*/
public double getPltdHarv() {
	return _PltdHarv;
}

/**
Returns _PltdYld.
@return _PltdYld.
*/
public double getPltdYld() {
	return _PltdYld;
}

/**
Returns _Practice.
@return _Practice.
*/
public String getPractice() {
	return _Practice;
}

/**
Returns _Production.
@return _Production.
*/
public double getProduction() {
	return _Production;
}

/**
Returns _ProductionUnit.
@return _ProductionUnit.
*/
public String getProductionUnit() {
	return _ProductionUnit;
}

/**
Returns _st.
@return _st.
*/
public String getSt() {
	return _st;
}

/**
Returns _st.
@return _st.
*/
public String getST() {
	return _st;
}

/**
Returns _Yield.
@return _Yield.
*/
public double getYield() {
	return _Yield;
}

/**
Returns _YieldUnit.
@return _YieldUnit.
*/
public String getYieldUnit() {
	return _YieldUnit;
}

/**
Sets _cal_year.
@param cal_year value to put in _cal_year.
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _Commodity.
@param commodity value to put in _Commodity.
*/
public void setCommodity(String commodity) {
	_Commodity = commodity;
}

/**
Sets _county.
@param county value to put in _county.
*/
public void setCounty(String county) {
	_county = county;
}

/**
Sets _Harvested.
@param harvested value to put in _Harvested.
*/
public void setHarvested(double harvested) {
	_Harvested = harvested;
}

/**
Sets _Planted.
@param planted value to put in _Planted.
*/
public void setPlanted(double planted) {
	_Planted = planted;
}

/**
Sets _PltdHarv.
@param pltd_harv value to put in _PltdHarv.
*/
public void setPltdHarv(double pltd_harv) {
	_PltdHarv = pltd_harv;
}

/**
Sets _plt_dyld.
@param pltd_yld value to put in _PltdYld.
*/
public void setPltdYld(double pltd_yld) {
	_PltdYld = pltd_yld;
}

/**
Sets _Practice.
@param practice value to put in _Practice.
*/
public void setPractice(String practice) {
	_Practice = practice;
}

/**
Sets _Production.
@param production value to put in _Production.
*/
public void setProduction(double production) {
	_Production = production;
}

/**
Sets _ProductionUnit.
@param production_unit value to put in _ProductionUnit.
*/
public void setProductionUnit(String production_unit) {
	_ProductionUnit = production_unit;
}

/**
Sets _st.
@param st value to put in _st.
*/
public void setSt(String st) {
	_st = st;
}

/**
Sets _st.
@param st value to put in _st.
*/
public void setST(String st) {
	_st = st;
}

/**
Sets _Yield.
@param yield value to put in _Yield.
*/
public void setYield(double yield) {
	_Yield = yield;
}

/**
Sets _YieldUnit.
@param yield_unit value to put in _YieldUnit.
*/
public void setYieldUnit(String yield_unit) {
	_YieldUnit = yield_unit;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_AgriculturalCASSCropStats {"		+ "\n" + 
		"St:       '" 	+ _st 		+ "'\n" + 
		"County:   '" 	+ _county 	+ "'\n" + 
		"Commodity:'" 	+ _Commodity 	+ "'\n" + 
		"Practice: '" 	+ _Practice 	+ "'\n" + 
		"Cal_year: " 	+ _cal_year 	+ "\n" + 
		"Planted:  " 	+ _Planted 	+ "\n" + 
		"PltdHarv: "	+ _PltdHarv 	+ "\n" + 
		"Harvested:"	+ _Harvested 	+ "\n" + 
		"PltdYld:  "	+ _PltdYld 	+ "\n" + 
		"Yield:    "	+ _Yield 	+ "\n" + 
		"YieldUnit:"	+ _YieldUnit 	+ "\n" + 
		"Production:"	+ _Production 	+ "\n" + 
		"ProductionUnit:"+ _ProductionUnit 	+ "\n";
}

}
