// ----------------------------------------------------------------------------
// HydroBase_DailyAmt.java - Class to hold data from the HydroBase 
//	daily_amt table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-18	J. Thomas Sapienza, RTi	Initial version from 
//					HBStructureDailyAmount
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2003-10-15	JTS, RTi		Added wis-specific data members.
// 2003-10-16	JTS, RTI		* Added findNearestDataDay().
//					* Added getAmountForDay().
//					* Added setAmountForDay().
//					* Added getObservationForDay().
//					* Added setObservationForDay().
// 2003-12-09	JTS, RTi		Removed all the setObs*() and
//					setAmt*() methods.
// 2004-06-08	JTS, RTi		Corrected bug caused by the wrong order
//					of parameters to TimeUtil.numDaysInMonth
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Date;
import java.util.Vector;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import RTi.Util.Time.TimeUtil;

/**
Class to store data from the HydroBase daily_amt table.  
This class is the base class for:
HydroBase_DailyWC.
*/
public class HydroBase_DailyAmt 
extends DMIDataObject {

protected int _meas_num = 	DMIUtil.MISSING_INT;
protected int _irr_year = 	DMIUtil.MISSING_INT;
protected int _irr_mon = 	DMIUtil.MISSING_INT;
protected int _structure_num = 	DMIUtil.MISSING_INT;
protected String _quality = 	DMIUtil.MISSING_STRING;
protected int _cal_year = 	DMIUtil.MISSING_INT;
protected int _cal_mon = 	DMIUtil.MISSING_INT;

protected double _amt1 =  DMIUtil.MISSING_DOUBLE;
protected String _obs1 =  DMIUtil.MISSING_STRING;
protected double _amt2 =  DMIUtil.MISSING_DOUBLE;
protected String _obs2 =  DMIUtil.MISSING_STRING;
protected double _amt3 =  DMIUtil.MISSING_DOUBLE;
protected String _obs3 =  DMIUtil.MISSING_STRING;
protected double _amt4 =  DMIUtil.MISSING_DOUBLE;
protected String _obs4 =  DMIUtil.MISSING_STRING;
protected double _amt5 =  DMIUtil.MISSING_DOUBLE;
protected String _obs5 =  DMIUtil.MISSING_STRING;
protected double _amt6 =  DMIUtil.MISSING_DOUBLE;
protected String _obs6 =  DMIUtil.MISSING_STRING;
protected double _amt7 =  DMIUtil.MISSING_DOUBLE;
protected String _obs7 =  DMIUtil.MISSING_STRING;
protected double _amt8 =  DMIUtil.MISSING_DOUBLE;
protected String _obs8 =  DMIUtil.MISSING_STRING;
protected double _amt9 =  DMIUtil.MISSING_DOUBLE;
protected String _obs9 =  DMIUtil.MISSING_STRING;
protected double _amt10 = DMIUtil.MISSING_DOUBLE;
protected String _obs10 = DMIUtil.MISSING_STRING;
protected double _amt11 = DMIUtil.MISSING_DOUBLE;
protected String _obs11 = DMIUtil.MISSING_STRING;
protected double _amt12 = DMIUtil.MISSING_DOUBLE;
protected String _obs12 = DMIUtil.MISSING_STRING;
protected double _amt13 = DMIUtil.MISSING_DOUBLE;
protected String _obs13 = DMIUtil.MISSING_STRING;
protected double _amt14 = DMIUtil.MISSING_DOUBLE;
protected String _obs14 = DMIUtil.MISSING_STRING;
protected double _amt15 = DMIUtil.MISSING_DOUBLE;
protected String _obs15 = DMIUtil.MISSING_STRING;
protected double _amt16 = DMIUtil.MISSING_DOUBLE;
protected String _obs16 = DMIUtil.MISSING_STRING;
protected double _amt17 = DMIUtil.MISSING_DOUBLE;
protected String _obs17 = DMIUtil.MISSING_STRING;
protected double _amt18 = DMIUtil.MISSING_DOUBLE;
protected String _obs18 = DMIUtil.MISSING_STRING;
protected double _amt19 = DMIUtil.MISSING_DOUBLE;
protected String _obs19 = DMIUtil.MISSING_STRING;
protected double _amt20 = DMIUtil.MISSING_DOUBLE;
protected String _obs20 = DMIUtil.MISSING_STRING;
protected double _amt21 = DMIUtil.MISSING_DOUBLE;
protected String _obs21 = DMIUtil.MISSING_STRING;
protected double _amt22 = DMIUtil.MISSING_DOUBLE;
protected String _obs22 = DMIUtil.MISSING_STRING;
protected double _amt23 = DMIUtil.MISSING_DOUBLE;
protected String _obs23 = DMIUtil.MISSING_STRING;
protected double _amt24 = DMIUtil.MISSING_DOUBLE;
protected String _obs24 = DMIUtil.MISSING_STRING;
protected double _amt25 = DMIUtil.MISSING_DOUBLE;
protected String _obs25 = DMIUtil.MISSING_STRING;
protected double _amt26 = DMIUtil.MISSING_DOUBLE;
protected String _obs26 = DMIUtil.MISSING_STRING;
protected double _amt27 = DMIUtil.MISSING_DOUBLE;
protected String _obs27 = DMIUtil.MISSING_STRING;
protected double _amt28 = DMIUtil.MISSING_DOUBLE;
protected String _obs28 = DMIUtil.MISSING_STRING;
protected double _amt29 = DMIUtil.MISSING_DOUBLE;
protected String _obs29 = DMIUtil.MISSING_STRING;
protected double _amt30 = DMIUtil.MISSING_DOUBLE;
protected String _obs30 = DMIUtil.MISSING_STRING;
protected double _amt31 = DMIUtil.MISSING_DOUBLE;
protected String _obs31 = DMIUtil.MISSING_STRING;

protected String _unit = 	DMIUtil.MISSING_STRING;
protected String _func = 	DMIUtil.MISSING_STRING;
protected int _div = 		DMIUtil.MISSING_INT;
protected int _wd = 		DMIUtil.MISSING_INT;
protected int _id = 		DMIUtil.MISSING_INT;
protected Date _modified =	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;

// wis data
protected int _wis_num = 	DMIUtil.MISSING_INT;
protected String _wis_column = 	DMIUtil.MISSING_STRING;
protected boolean _is_edited = 	false;

/**
Constructor.
*/
public HydroBase_DailyAmt() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_quality = null;
	_obs1 = null;
	_obs2 = null;
	_obs3 = null;
	_obs4 = null;
	_obs5 = null;
	_obs6 = null;
	_obs7 = null;
	_obs8 = null;
	_obs9 = null;
	_obs10 = null;
	_obs11 = null;
	_obs12 = null;
	_obs13 = null;
	_obs14 = null;
	_obs15 = null;
	_obs16 = null;
	_obs17 = null;
	_obs18 = null;
	_obs19 = null;
	_obs20 = null;
	_obs21 = null;
	_obs22 = null;
	_obs23 = null;
	_obs24 = null;
	_obs25 = null;
	_obs26 = null;
	_obs27 = null;
	_obs28 = null;
	_obs29 = null;
	_obs30 = null;
	_obs31 = null;
	_unit = null;
	_func = null;
	_modified = null;
	
	super.finalize();
}

/**
Returns the data value stored in the specified day.
@param day the day (1 - 31) for which to return the data amount.
@return the value stored in that day, or DMIUtil.MISSING_DOUBLE if day &lt; 1
or &gt; 31
*/
public double getAmountForDay(int day) {
	if (day < 1 || day > 31) {
		return DMIUtil.MISSING_DOUBLE;
	}

	switch (day) {
		case  1:	return _amt1;
		case  2:	return _amt2;
		case  3:	return _amt3;
		case  4:	return _amt4;
		case  5:	return _amt5;
		case  6:	return _amt6;
		case  7:	return _amt7;
		case  8:	return _amt8;
		case  9:	return _amt9;
		case 10:	return _amt10;
		case 11:	return _amt11;
		case 12:	return _amt12;
		case 13:	return _amt13;
		case 14:	return _amt14;
		case 15:	return _amt15;
		case 16:	return _amt16;
		case 17:	return _amt17;
		case 18:	return _amt18;
		case 19:	return _amt19;
		case 20:	return _amt20;
		case 21:	return _amt21;
		case 22:	return _amt22;
		case 23:	return _amt23;
		case 24:	return _amt24;
		case 25:	return _amt25;
		case 26:	return _amt26;
		case 27:	return _amt27;
		case 28:	return _amt28;
		case 29:	return _amt29;
		case 30:	return _amt30;
		case 31:	return _amt31;
	}
	return DMIUtil.MISSING_DOUBLE;
}

/**
Returns _cal_mon
@return _cal_mon
*/
public int getCal_mon() {
	return _cal_mon;
}

/**
Returns _cal_year
@return _cal_year
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _func
@return _func
*/
public String getFunc() {
	return _func;
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
}

/**
Returns _irr_mon
@return _irr_mon
*/
public int getIrr_mon() {
	return _irr_mon;
}

/**
Returns _irr_year
@return _irr_year
*/
public int getIrr_year() {
	return _irr_year;
}

/**
Returns _meas_num
@return _meas_num
*/
public int getMeas_num() {
	return _meas_num;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns the observation stored in the specified day.
@param day the day (1 - 31) for which to return the observation.
@return the observation stored in that day, or null if day &lt; 1
or &gt; 31
*/
public String getObservationForDay(int day) {
	if (day < 1 || day > 31) {
		return null;
	}

	switch (day) {
		case  1:	return _obs1;
		case  2:	return _obs2;
		case  3:	return _obs3;
		case  4:	return _obs4;
		case  5:	return _obs5;
		case  6:	return _obs6;
		case  7:	return _obs7;
		case  8:	return _obs8;
		case  9:	return _obs9;
		case 10:	return _obs10;
		case 11:	return _obs11;
		case 12:	return _obs12;
		case 13:	return _obs13;
		case 14:	return _obs14;
		case 15:	return _obs15;
		case 16:	return _obs16;
		case 17:	return _obs17;
		case 18:	return _obs18;
		case 19:	return _obs19;
		case 20:	return _obs20;
		case 21:	return _obs21;
		case 22:	return _obs22;
		case 23:	return _obs23;
		case 24:	return _obs24;
		case 25:	return _obs25;
		case 26:	return _obs26;
		case 27:	return _obs27;
		case 28:	return _obs28;
		case 29:	return _obs29;
		case 30:	return _obs30;
		case 31:	return _obs31;
	}
	return null;
}

/**
Returns _quality
@return _quality
*/
public String getQuality() {
	return _quality;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _unit
@return _unit
*/
public String getUnit() {
	return _unit;
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
Returns _wis_column
@return _wis_column
*/
public String getWis_column() {
	return _wis_column;
}

/**
Returns _wis_num
@return _wis_num
*/
public int getWis_num() {
	return _wis_num;
}

/**
Returns _is_edited
@return _is_edited
*/
public boolean isEdited() {
	return _is_edited;
}

/**
Sets _is_edited
@param is_edited value to set _is_edited to
*/
public void isEdited(boolean is_edited) {
	_is_edited = is_edited;
}

/**
Sets the data amount for the specified day.
@param day the day (1 - 31) for which to set the data amount.
@param val the value to store in the day.
*/
public void setAmountForDay(int day, double val) {
	if (day < 1 || day > 31) {
		return;
	}

	switch (day) {
		case  1:	_amt1 = val;	return;
		case  2:	_amt2 = val;	return;
		case  3:	_amt3 = val;	return;
		case  4:	_amt4 = val;	return;
		case  5:	_amt5 = val;	return;
		case  6:	_amt6 = val;	return;
		case  7:	_amt7 = val;	return;
		case  8:	_amt8 = val;	return;
		case  9:	_amt9 = val;	return;
		case 10:	_amt10 = val;	return;
		case 11:	_amt11 = val;	return;
		case 12:	_amt12 = val;	return;
		case 13:	_amt13 = val;	return;
		case 14:	_amt14 = val;	return;
		case 15:	_amt15 = val;	return;
		case 16:	_amt16 = val;	return;
		case 17:	_amt17 = val;	return;
		case 18:	_amt18 = val;	return;
		case 19:	_amt19 = val;	return;
		case 20:	_amt20 = val;	return;
		case 21:	_amt21 = val;	return;
		case 22:	_amt22 = val;	return;
		case 23:	_amt23 = val;	return;
		case 24:	_amt24 = val;	return;
		case 25:	_amt25 = val;	return;
		case 26:	_amt26 = val;	return;
		case 27:	_amt27 = val;	return;
		case 28:	_amt28 = val;	return;
		case 29:	_amt29 = val;	return;
		case 30:	_amt30 = val;	return;
		case 31:	_amt31 = val;	return;
	}
}

/**
Sets _cal_mon
@param cal_mon value to put into _cal_mon
*/
public void setCal_mon(int cal_mon) {
	_cal_mon = cal_mon;
}

/**
Sets _cal_year
@param cal_year value to put into _cal_year
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _func
@param func value to put into _func
*/
public void setFunc(String func) {
	_func = func;
}

/**
Sets _id
@param id value to put into _id
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _irr_mon
@param irr_mon value to put into _irr_mon
*/
public void setIrr_mon(int irr_mon) {
	_irr_mon = irr_mon;
}

/**
Sets _irr_year
@param irr_year value to put into _irr_year
*/
public void setIrr_year(int irr_year) {
	_irr_year = irr_year;
}

/**
Sets _meas_num
@param meas_num value to put into _meas_num
*/
public void setMeas_num(int meas_num) {
	_meas_num = meas_num;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets the observation for the specified day.
@param day the day (1 - 31) for which to set the observation.
@param obs the observation to store in the day.
*/
public void setObservationForDay(int day, String obs) {
	if (day < 1 || day > 31) {
		return;
	}

	switch (day) {
		case  1:	_obs1 = obs;	return;
		case  2:	_obs2 = obs;	return;
		case  3:	_obs3 = obs;	return;
		case  4:	_obs4 = obs;	return;
		case  5:	_obs5 = obs;	return;
		case  6:	_obs6 = obs;	return;
		case  7:	_obs7 = obs;	return;
		case  8:	_obs8 = obs;	return;
		case  9:	_obs9 = obs;	return;
		case 10:	_obs10 = obs;	return;
		case 11:	_obs11 = obs;	return;
		case 12:	_obs12 = obs;	return;
		case 13:	_obs13 = obs;	return;
		case 14:	_obs14 = obs;	return;
		case 15:	_obs15 = obs;	return;
		case 16:	_obs16 = obs;	return;
		case 17:	_obs17 = obs;	return;
		case 18:	_obs18 = obs;	return;
		case 19:	_obs19 = obs;	return;
		case 20:	_obs20 = obs;	return;
		case 21:	_obs21 = obs;	return;
		case 22:	_obs22 = obs;	return;
		case 23:	_obs23 = obs;	return;
		case 24:	_obs24 = obs;	return;
		case 25:	_obs25 = obs;	return;
		case 26:	_obs26 = obs;	return;
		case 27:	_obs27 = obs;	return;
		case 28:	_obs28 = obs;	return;
		case 29:	_obs29 = obs;	return;
		case 30:	_obs30 = obs;	return;
		case 31:	_obs31 = obs;	return;
	}
}

/**
Sets _quality
@param quality value to put into _quality
*/
public void setQuality(String quality) {
	_quality = quality;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _unit
@param unit value to put into _unit
*/
public void setUnit(String unit) {
	_unit = unit;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Sets _wis_column
@param wis_column value to put into _wis_column
*/
public void setWis_column(String wis_column) {
	_wis_column = wis_column;
}

/**
Sets _wis_num
@param wis_num value to put into _wis_num
*/
public void setWis_num(int wis_num) {
	_wis_num = wis_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_DailyAmt {"		+ "\n" + 
		"Meas_num:      " + _meas_num + "\n" + 
		"Irr_year:      " + _irr_year + "\n" + 
		"Irr_mon:       " + _irr_mon + "\n" + 
		"Structure_num: " + _structure_num + "\n" + 
		"Quality:       " + _quality + "\n" + 
		"Cal_year:      " + _cal_year + "\n" + 
		"Cal_mon:       " + _cal_mon + "\n" + 

		"Amt1:          " + _amt1 + "\n" + 
		"Obs1:          " + _obs1 + "\n" + 
		"Amt2:          " + _amt2 + "\n" + 
		"Obs2:          " + _obs2 + "\n" + 
		"Amt3:          " + _amt3 + "\n" + 
		"Obs3:          " + _obs3 + "\n" + 
		"Amt4:          " + _amt4 + "\n" + 
		"Obs4:          " + _obs4 + "\n" + 
		"Amt5:          " + _amt5 + "\n" + 
		"Obs5:          " + _obs5 + "\n" + 
		"Amt6:          " + _amt6 + "\n" + 
		"Obs6:          " + _obs6 + "\n" + 
		"Amt7:          " + _amt7 + "\n" + 
		"Obs7:          " + _obs7 + "\n" + 
		"Amt8:          " + _amt8 + "\n" + 
		"Obs8:          " + _obs8 + "\n" + 
		"Amt9:          " + _amt9 + "\n" + 
		"Obs9:          " + _obs9 + "\n" + 
		"Amt0:          " + _amt10 + "\n" + 
		"Obs10:         " + _obs10 + "\n" + 
		"Amt11:         " + _amt11 + "\n" + 
		"Obs11:         " + _obs11 + "\n" + 
		"Amt12:         " + _amt12 + "\n" + 
		"Obs12:         " + _obs12 + "\n" + 
		"Amt13:         " + _amt13 + "\n" + 
		"Obs13:         " + _obs13 + "\n" + 
		"Amt14:         " + _amt14 + "\n" + 
		"Obs14:         " + _obs14 + "\n" + 
		"Amt15:         " + _amt15 + "\n" + 
		"Obs15:         " + _obs15 + "\n" + 
		"Amt16:         " + _amt16 + "\n" + 
		"Obs16:         " + _obs16 + "\n" + 
		"Amt17:         " + _amt17 + "\n" + 
		"Obs17:         " + _obs17 + "\n" + 
		"Amt18:         " + _amt18 + "\n" + 
		"Obs18:         " + _obs18 + "\n" + 
		"Amt19:         " + _amt19 + "\n" + 
		"Obs19:         " + _obs19 + "\n" + 
		"Amt20:         " + _amt20 + "\n" + 
		"Obs20:         " + _obs20 + "\n" + 
		"Amt21:         " + _amt21 + "\n" + 
		"Obs21:         " + _obs21 + "\n" + 
		"Amt22:         " + _amt22 + "\n" + 
		"Obs22:         " + _obs22 + "\n" + 
		"Amt23:         " + _amt23 + "\n" + 
		"Obs23:         " + _obs23 + "\n" + 
		"Amt24:         " + _amt24 + "\n" + 
		"Obs24:         " + _obs24 + "\n" + 
		"Amt25:         " + _amt25 + "\n" + 
		"Obs25:         " + _obs25 + "\n" + 
		"Amt26:         " + _amt26 + "\n" + 
		"Obs26:         " + _obs26 + "\n" + 
		"Amt27:         " + _amt27 + "\n" + 
		"Obs27:         " + _obs27 + "\n" + 
		"Amt28:         " + _amt28 + "\n" + 
		"Obs28:         " + _obs28 + "\n" + 
		"Amt29:         " + _amt29 + "\n" + 
		"Obs29:         " + _obs29 + "\n" + 
		"Amt30:         " + _amt30 + "\n" + 
		"Obs30:         " + _obs30 + "\n" + 
		"Amt31:         " + _amt31 + "\n" + 
		"Obs31:         " + _obs31 + "\n" + 

		"Unit:          " + _unit + "\n" + 
		"Func:          " + _func + "\n" + 
		"Div:           " + _div + "\n" + 
		"WD:            " + _wd + "\n" + 
		"ID:            " + _id + "\n" + 
		"Modified:      " + _modified + "\n" + 
		"User_num:      " + _user_num + "\n}\n";
}

/**
Given a day, find the day that is nearest in the past that has data.  Data is
any day that has a non-null observation flag.  It is assumed that the record
has the correct month and year of interest.  All records in the Vector are
checked. 
@param records Vector of HydroBase_DailyAmt to check.
@param day Day to find data nearest to.  If data are available on the day, the
day is returned.
@return -1 if no day with data is found.
*/
public static int findNearestDataDay (Vector records, int day) {
	if ((records == null) || (records.size() == 0)) {
		return -1;
	}
	int size = records.size();
	int daymin = 100;
	int day2 = 0;
	HydroBase_DailyAmt record = null;
	for (int i = 0; i < size; i++ ) {
		record = (HydroBase_DailyAmt)records.elementAt(i);
		day2 = record.findNearestDataDay(0, 0, day);
		if (day2 < daymin) {
			daymin = day2;
		}
	}
	record = null;
	if (daymin == 100) {
		return -1;
	}
	else {	
		return daymin;
	}
}

/**
Given a day, find the day that is nearest in the past that has data.  Data is
any day that has a non-null observation flag.  If the month or year are zero,
it is assumed that the record has the correct month and year of interest.
@param year Year to find data nearest to.
@param month Month to find data nearest to.
@param day Day to find data nearest to.  If data are available on the day, the
day is returned.
@return -1 if no day with data is found.
*/
public int findNearestDataDay(int year, int month, int day) {
	int num_days = TimeUtil.numDaysInMonth(_cal_mon, _cal_year);
	if ((year >= 0) && (month >= 0)) {
		// If the record is older than the given time, then start at
		// the last day in the record...
		day = num_days;
	}
	else {	
		if (day > num_days) {
			day = num_days;	// In case leap year is not checked.
		}
	}
	for (int i = day; i >= 1; i--) {
		if (!DMIUtil.isMissing(getAmountForDay(i))) {
			// Have data...
			return i;
		}
	}
	return -1;
}

/**
Returns _amt1
@return _amt1
*/
public double getAmt1() {
	return _amt1;
}

/**
Returns _amt2
@return _amt2
*/
public double getAmt2() {
	return _amt2;
}

/**
Returns _amt3
@return _amt3
*/
public double getAmt3() {
	return _amt3;
}

/**
Returns _amt4
@return _amt4
*/
public double getAmt4() {
	return _amt4;
}

/**
Returns _amt5
@return _amt5
*/
public double getAmt5() {
	return _amt5;
}

/**
Returns _amt6
@return _amt6
*/
public double getAmt6() {
	return _amt6;
}

/**
Returns _amt7
@return _amt7
*/
public double getAmt7() {
	return _amt7;
}

/**
Returns _amt8
@return _amt8
*/
public double getAmt8() {
	return _amt8;
}

/**
Returns _amt9
@return _amt9
*/
public double getAmt9() {
	return _amt9;
}

/**
Returns _amt10
@return _amt10
*/
public double getAmt10() {
	return _amt10;
}

/**
Returns _amt11
@return _amt11
*/
public double getAmt11() {
	return _amt11;
}

/**
Returns _amt12
@return _amt12
*/
public double getAmt12() {
	return _amt12;
}

/**
Returns _amt13
@return _amt13
*/
public double getAmt13() {
	return _amt13;
}

/**
Returns _amt14
@return _amt14
*/
public double getAmt14() {
	return _amt14;
}

/**
Returns _amt15
@return _amt15
*/
public double getAmt15() {
	return _amt15;
}

/**
Returns _amt16
@return _amt16
*/
public double getAmt16() {
	return _amt16;
}

/**
Returns _amt17
@return _amt17
*/
public double getAmt17() {
	return _amt17;
}

/**
Returns _amt18
@return _amt18
*/
public double getAmt18() {
	return _amt18;
}

/**
Returns _amt19
@return _amt19
*/
public double getAmt19() {
	return _amt19;
}

/**
Returns _amt20
@return _amt20
*/
public double getAmt20() {
	return _amt20;
}

/**
Returns _amt21
@return _amt21
*/
public double getAmt21() {
	return _amt21;
}

/**
Returns _amt22
@return _amt22
*/
public double getAmt22() {
	return _amt22;
}

/**
Returns _amt23
@return _amt23
*/
public double getAmt23() {
	return _amt23;
}

/**
Returns _amt24
@return _amt24
*/
public double getAmt24() {
	return _amt24;
}

/**
Returns _amt25
@return _amt25
*/
public double getAmt25() {
	return _amt25;
}

/**
Returns _amt26
@return _amt26
*/
public double getAmt26() {
	return _amt26;
}

/**
Returns _amt27
@return _amt27
*/
public double getAmt27() {
	return _amt27;
}

/**
Returns _amt28
@return _amt28
*/
public double getAmt28() {
	return _amt28;
}

/**
Returns _amt29
@return _amt29
*/
public double getAmt29() {
	return _amt29;
}

/**
Returns _amt30
@return _amt30
*/
public double getAmt30() {
	return _amt30;
}

/**
Returns _amt31
@return _amt31
*/
public double getAmt31() {
	return _amt31;
}

/**
Returns _obs1
@return _obs1
*/
public String getObs1() {
	return _obs1;
}

/**
Returns _obs2
@return _obs2
*/
public String getObs2() {
	return _obs2;
}

/**
Returns _obs3
@return _obs3
*/
public String getObs3() {
	return _obs3;
}

/**
Returns _obs4
@return _obs4
*/
public String getObs4() {
	return _obs4;
}

/**
Returns _obs5
@return _obs5
*/
public String getObs5() {
	return _obs5;
}

/**
Returns _obs6
@return _obs6
*/
public String getObs6() {
	return _obs6;
}

/**
Returns _obs7
@return _obs7
*/
public String getObs7() {
	return _obs7;
}

/**
Returns _obs8
@return _obs8
*/
public String getObs8() {
	return _obs8;
}

/**
Returns _obs9
@return _obs9
*/
public String getObs9() {
	return _obs9;
}

/**
Returns _obs10
@return _obs10
*/
public String getObs10() {
	return _obs10;
}

/**
Returns _obs11
@return _obs11
*/
public String getObs11() {
	return _obs11;
}

/**
Returns _obs12
@return _obs12
*/
public String getObs12() {
	return _obs12;
}

/**
Returns _obs13
@return _obs13
*/
public String getObs13() {
	return _obs13;
}

/**
Returns _obs14
@return _obs14
*/
public String getObs14() {
	return _obs14;
}

/**
Returns _obs15
@return _obs15
*/
public String getObs15() {
	return _obs15;
}

/**
Returns _obs16
@return _obs16
*/
public String getObs16() {
	return _obs16;
}

/**
Returns _obs17
@return _obs17
*/
public String getObs17() {
	return _obs17;
}

/**
Returns _obs18
@return _obs18
*/
public String getObs18() {
	return _obs18;
}

/**
Returns _obs19
@return _obs19
*/
public String getObs19() {
	return _obs19;
}

/**
Returns _obs20
@return _obs20
*/
public String getObs20() {
	return _obs20;
}

/**
Returns _obs21
@return _obs21
*/
public String getObs21() {
	return _obs21;
}

/**
Returns _obs22
@return _obs22
*/
public String getObs22() {
	return _obs22;
}

/**
Returns _obs23
@return _obs23
*/
public String getObs23() {
	return _obs23;
}

/**
Returns _obs24
@return _obs24
*/
public String getObs24() {
	return _obs24;
}

/**
Returns _obs25
@return _obs25
*/
public String getObs25() {
	return _obs25;
}

/**
Returns _obs26
@return _obs26
*/
public String getObs26() {
	return _obs26;
}

/**
Returns _obs27
@return _obs27
*/
public String getObs27() {
	return _obs27;
}

/**
Returns _obs28
@return _obs28
*/
public String getObs28() {
	return _obs28;
}

/**
Returns _obs29
@return _obs29
*/
public String getObs29() {
	return _obs29;
}

/**
Returns _obs30
@return _obs30
*/
public String getObs30() {
	return _obs30;
}

/**
Returns _obs31
@return _obs31
*/
public String getObs31() {
	return _obs31;
}

/**
Sets _amt1
@param amt1 value to put into _amt1
*/
public void setAmt1(double amt1) {
	_amt1 = amt1;
}

/**
Sets _amt2
@param amt2 value to put into _amt2
*/
public void setAmt2(double amt2) {
	_amt2 = amt2;
}

/**
Sets _amt3
@param amt3 value to put into _amt3
*/
public void setAmt3(double amt3) {
	_amt3 = amt3;
}

/**
Sets _amt4
@param amt4 value to put into _amt4
*/
public void setAmt4(double amt4) {
	_amt4 = amt4;
}

/**
Sets _amt5
@param amt5 value to put into _amt5
*/
public void setAmt5(double amt5) {
	_amt5 = amt5;
}

/**
Sets _amt6
@param amt6 value to put into _amt6
*/
public void setAmt6(double amt6) {
	_amt6 = amt6;
}

/**
Sets _amt7
@param amt7 value to put into _amt7
*/
public void setAmt7(double amt7) {
	_amt7 = amt7;
}

/**
Sets _amt8
@param amt8 value to put into _amt8
*/
public void setAmt8(double amt8) {
	_amt8 = amt8;
}

/**
Sets _amt9
@param amt9 value to put into _amt9
*/
public void setAmt9(double amt9) {
	_amt9 = amt9;
}

/**
Sets _amt10
@param amt10 value to put into _amt10
*/
public void setAmt10(double amt10) {
	_amt10 = amt10;
}

/**
Sets _amt11
@param amt11 value to put into _amt11
*/
public void setAmt11(double amt11) {
	_amt11 = amt11;
}

/**
Sets _amt12
@param amt12 value to put into _amt12
*/
public void setAmt12(double amt12) {
	_amt12 = amt12;
}

/**
Sets _amt13
@param amt13 value to put into _amt13
*/
public void setAmt13(double amt13) {
	_amt13 = amt13;
}

/**
Sets _amt14
@param amt14 value to put into _amt14
*/
public void setAmt14(double amt14) {
	_amt14 = amt14;
}

/**
Sets _amt15
@param amt15 value to put into _amt15
*/
public void setAmt15(double amt15) {
	_amt15 = amt15;
}

/**
Sets _amt16
@param amt16 value to put into _amt16
*/
public void setAmt16(double amt16) {
	_amt16 = amt16;
}

/**
Sets _amt17
@param amt17 value to put into _amt17
*/
public void setAmt17(double amt17) {
	_amt17 = amt17;
}

/**
Sets _amt18
@param amt18 value to put into _amt18
*/
public void setAmt18(double amt18) {
	_amt18 = amt18;
}

/**
Sets _amt19
@param amt19 value to put into _amt19
*/
public void setAmt19(double amt19) {
	_amt19 = amt19;
}

/**
Sets _amt20
@param amt20 value to put into _amt20
*/
public void setAmt20(double amt20) {
	_amt20 = amt20;
}

/**
Sets _amt21
@param amt21 value to put into _amt21
*/
public void setAmt21(double amt21) {
	_amt21 = amt21;
}

/**
Sets _amt22
@param amt22 value to put into _amt22
*/
public void setAmt22(double amt22) {
	_amt22 = amt22;
}

/**
Sets _amt23
@param amt23 value to put into _amt23
*/
public void setAmt23(double amt23) {
	_amt23 = amt23;
}

/**
Sets _amt24
@param amt24 value to put into _amt24
*/
public void setAmt24(double amt24) {
	_amt24 = amt24;
}

/**
Sets _amt25
@param amt25 value to put into _amt25
*/
public void setAmt25(double amt25) {
	_amt25 = amt25;
}

/**
Sets _amt26
@param amt26 value to put into _amt26
*/
public void setAmt26(double amt26) {
	_amt26 = amt26;
}

/**
Sets _amt27
@param amt27 value to put into _amt27
*/
public void setAmt27(double amt27) {
	_amt27 = amt27;
}

/**
Sets _amt28
@param amt28 value to put into _amt28
*/
public void setAmt28(double amt28) {
	_amt28 = amt28;
}

/**
Sets _amt29
@param amt29 value to put into _amt29
*/
public void setAmt29(double amt29) {
	_amt29 = amt29;
}

/**
Sets _amt30
@param amt30 value to put into _amt30
*/
public void setAmt30(double amt30) {
	_amt30 = amt30;
}

/**
Sets _amt31
@param amt31 value to put into _amt31
*/
public void setAmt31(double amt31) {
	_amt31 = amt31;
}

/**
Sets _obs1
@param obs1 value to put into _obs1
*/
public void setObs1(String obs1) {
	_obs1 = obs1;
}

/**
Sets _obs2
@param obs2 value to put into _obs2
*/
public void setObs2(String obs2) {
	_obs2 = obs2;
}

/**
Sets _obs3
@param obs3 value to put into _obs3
*/
public void setObs3(String obs3) {
	_obs3 = obs3;
}

/**
Sets _obs4
@param obs4 value to put into _obs4
*/
public void setObs4(String obs4) {
	_obs4 = obs4;
}

/**
Sets _obs5
@param obs5 value to put into _obs5
*/
public void setObs5(String obs5) {
	_obs5 = obs5;
}

/**
Sets _obs6
@param obs6 value to put into _obs6
*/
public void setObs6(String obs6) {
	_obs6 = obs6;
}

/**
Sets _obs7
@param obs7 value to put into _obs7
*/
public void setObs7(String obs7) {
	_obs7 = obs7;
}

/**
Sets _obs8
@param obs8 value to put into _obs8
*/
public void setObs8(String obs8) {
	_obs8 = obs8;
}

/**
Sets _obs9
@param obs9 value to put into _obs9
*/
public void setObs9(String obs9) {
	_obs9 = obs9;
}

/**
Sets _obs10
@param obs10 value to put into _obs10
*/
public void setObs10(String obs10) {
	_obs10 = obs10;
}

/**
Sets _obs11
@param obs11 value to put into _obs11
*/
public void setObs11(String obs11) {
	_obs11 = obs11;
}

/**
Sets _obs12
@param obs12 value to put into _obs12
*/
public void setObs12(String obs12) {
	_obs12 = obs12;
}

/**
Sets _obs13
@param obs13 value to put into _obs13
*/
public void setObs13(String obs13) {
	_obs13 = obs13;
}

/**
Sets _obs14
@param obs14 value to put into _obs14
*/
public void setObs14(String obs14) {
	_obs14 = obs14;
}

/**
Sets _obs15
@param obs15 value to put into _obs15
*/
public void setObs15(String obs15) {
	_obs15 = obs15;
}

/**
Sets _obs16
@param obs16 value to put into _obs16
*/
public void setObs16(String obs16) {
	_obs16 = obs16;
}

/**
Sets _obs17
@param obs17 value to put into _obs17
*/
public void setObs17(String obs17) {
	_obs17 = obs17;
}

/**
Sets _obs18
@param obs18 value to put into _obs18
*/
public void setObs18(String obs18) {
	_obs18 = obs18;
}

/**
Sets _obs19
@param obs19 value to put into _obs19
*/
public void setObs19(String obs19) {
	_obs19 = obs19;
}

/**
Sets _obs20
@param obs20 value to put into _obs20
*/
public void setObs20(String obs20) {
	_obs20 = obs20;
}

/**
Sets _obs21
@param obs21 value to put into _obs21
*/
public void setObs21(String obs21) {
	_obs21 = obs21;
}

/**
Sets _obs22
@param obs22 value to put into _obs22
*/
public void setObs22(String obs22) {
	_obs22 = obs22;
}

/**
Sets _obs23
@param obs23 value to put into _obs23
*/
public void setObs23(String obs23) {
	_obs23 = obs23;
}

/**
Sets _obs24
@param obs24 value to put into _obs24
*/
public void setObs24(String obs24) {
	_obs24 = obs24;
}

/**
Sets _obs25
@param obs25 value to put into _obs25
*/
public void setObs25(String obs25) {
	_obs25 = obs25;
}

/**
Sets _obs26
@param obs26 value to put into _obs26
*/
public void setObs26(String obs26) {
	_obs26 = obs26;
}

/**
Sets _obs27
@param obs27 value to put into _obs27
*/
public void setObs27(String obs27) {
	_obs27 = obs27;
}

/**
Sets _obs28
@param obs28 value to put into _obs28
*/
public void setObs28(String obs28) {
	_obs28 = obs28;
}

/**
Sets _obs29
@param obs29 value to put into _obs29
*/
public void setObs29(String obs29) {
	_obs29 = obs29;
}

/**
Sets _obs30
@param obs30 value to put into _obs30
*/
public void setObs30(String obs30) {
	_obs30 = obs30;
}

/**
Sets _obs31
@param obs31 value to put into _obs31
*/
public void setObs31(String obs31) {
	_obs31 = obs31;
}

}
