// ----------------------------------------------------------------------------
// HydroBase_DailyPcpn.java - Class to hold data from the HydroBase 
//	daily_pcpn table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-18	J. Thomas Sapienza, RTi	Initial version from
//					HBStationDayTSRecord.
// 2003-02-20	JTS, RTi		Added methods for the many flag members
//					to support older database versions.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2003-12-08	SAM, RTi		* Add getDay(), getFlag(), setDay(),
//					  setFlag() methods to avoid calling the
//					  specific methods.
//					* Remove the individual getDayN(), etc.
//					  methods since they are no longer
//					  needed - this simplifies the code a
//					  lot.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase daily_pcpn table.
*/
public class HydroBase_DailyPcpn 
extends DMIDataObject {

protected int _meas_num = 	DMIUtil.MISSING_INT;
protected int _cal_year = 	DMIUtil.MISSING_INT;
protected int _cal_mon_num = 	DMIUtil.MISSING_INT;
protected int _station_num = 	DMIUtil.MISSING_INT;
protected String _cal_mon = 	DMIUtil.MISSING_STRING;
protected String _unit = 	DMIUtil.MISSING_STRING;

protected double _day1  =   DMIUtil.MISSING_DOUBLE;
protected String _flag1a =  DMIUtil.MISSING_STRING;
protected String _flag1b =  DMIUtil.MISSING_STRING;
protected double _day2  =   DMIUtil.MISSING_DOUBLE;
protected String _flag2a =  DMIUtil.MISSING_STRING;
protected String _flag2b =  DMIUtil.MISSING_STRING;
protected double _day3  =   DMIUtil.MISSING_DOUBLE;
protected String _flag3a =  DMIUtil.MISSING_STRING;
protected String _flag3b =  DMIUtil.MISSING_STRING;
protected double _day4  =   DMIUtil.MISSING_DOUBLE;
protected String _flag4a =  DMIUtil.MISSING_STRING;
protected String _flag4b =  DMIUtil.MISSING_STRING;
protected double _day5  =   DMIUtil.MISSING_DOUBLE;
protected String _flag5a =  DMIUtil.MISSING_STRING;
protected String _flag5b =  DMIUtil.MISSING_STRING;
protected double _day6  =   DMIUtil.MISSING_DOUBLE;
protected String _flag6a =  DMIUtil.MISSING_STRING;
protected String _flag6b =  DMIUtil.MISSING_STRING;
protected double _day7  =   DMIUtil.MISSING_DOUBLE;
protected String _flag7a =  DMIUtil.MISSING_STRING;
protected String _flag7b =  DMIUtil.MISSING_STRING;
protected double _day8  =   DMIUtil.MISSING_DOUBLE;
protected String _flag8a =  DMIUtil.MISSING_STRING;
protected String _flag8b =  DMIUtil.MISSING_STRING;
protected double _day9  =   DMIUtil.MISSING_DOUBLE;
protected String _flag9a =  DMIUtil.MISSING_STRING;
protected String _flag9b =  DMIUtil.MISSING_STRING;
protected double _day10  =  DMIUtil.MISSING_DOUBLE;
protected String _flag10a = DMIUtil.MISSING_STRING;
protected String _flag10b = DMIUtil.MISSING_STRING;
protected double _day11  =  DMIUtil.MISSING_DOUBLE;
protected String _flag11a = DMIUtil.MISSING_STRING;
protected String _flag11b = DMIUtil.MISSING_STRING;
protected double _day12  =  DMIUtil.MISSING_DOUBLE;
protected String _flag12a = DMIUtil.MISSING_STRING;
protected String _flag12b = DMIUtil.MISSING_STRING;
protected double _day13  =  DMIUtil.MISSING_DOUBLE;
protected String _flag13a = DMIUtil.MISSING_STRING;
protected String _flag13b = DMIUtil.MISSING_STRING;
protected double _day14  =  DMIUtil.MISSING_DOUBLE;
protected String _flag14a = DMIUtil.MISSING_STRING;
protected String _flag14b = DMIUtil.MISSING_STRING;
protected double _day15  =  DMIUtil.MISSING_DOUBLE;
protected String _flag15a = DMIUtil.MISSING_STRING;
protected String _flag15b = DMIUtil.MISSING_STRING;
protected double _day16  =  DMIUtil.MISSING_DOUBLE;
protected String _flag16a = DMIUtil.MISSING_STRING;
protected String _flag16b = DMIUtil.MISSING_STRING;
protected double _day17  =  DMIUtil.MISSING_DOUBLE;
protected String _flag17a = DMIUtil.MISSING_STRING;
protected String _flag17b = DMIUtil.MISSING_STRING;
protected double _day18  =  DMIUtil.MISSING_DOUBLE;
protected String _flag18a = DMIUtil.MISSING_STRING;
protected String _flag18b = DMIUtil.MISSING_STRING;
protected double _day19  =  DMIUtil.MISSING_DOUBLE;
protected String _flag19a = DMIUtil.MISSING_STRING;
protected String _flag19b = DMIUtil.MISSING_STRING;
protected double _day20  =  DMIUtil.MISSING_DOUBLE;
protected String _flag20a = DMIUtil.MISSING_STRING;
protected String _flag20b = DMIUtil.MISSING_STRING;
protected double _day21  =  DMIUtil.MISSING_DOUBLE;
protected String _flag21a = DMIUtil.MISSING_STRING;
protected String _flag21b = DMIUtil.MISSING_STRING;
protected double _day22  =  DMIUtil.MISSING_DOUBLE;
protected String _flag22a = DMIUtil.MISSING_STRING;
protected String _flag22b = DMIUtil.MISSING_STRING;
protected double _day23  =  DMIUtil.MISSING_DOUBLE;
protected String _flag23a = DMIUtil.MISSING_STRING;
protected String _flag23b = DMIUtil.MISSING_STRING;
protected double _day24  =  DMIUtil.MISSING_DOUBLE;
protected String _flag24a = DMIUtil.MISSING_STRING;
protected String _flag24b = DMIUtil.MISSING_STRING;
protected double _day25  =  DMIUtil.MISSING_DOUBLE;
protected String _flag25a = DMIUtil.MISSING_STRING;
protected String _flag25b = DMIUtil.MISSING_STRING;
protected double _day26  =  DMIUtil.MISSING_DOUBLE;
protected String _flag26a = DMIUtil.MISSING_STRING;
protected String _flag26b = DMIUtil.MISSING_STRING;
protected double _day27  =  DMIUtil.MISSING_DOUBLE;
protected String _flag27a = DMIUtil.MISSING_STRING;
protected String _flag27b = DMIUtil.MISSING_STRING;
protected double _day28  =  DMIUtil.MISSING_DOUBLE;
protected String _flag28a = DMIUtil.MISSING_STRING;
protected String _flag28b = DMIUtil.MISSING_STRING;
protected double _day29  =  DMIUtil.MISSING_DOUBLE;
protected String _flag29a = DMIUtil.MISSING_STRING;
protected String _flag29b = DMIUtil.MISSING_STRING;
protected double _day30  =  DMIUtil.MISSING_DOUBLE;
protected String _flag30a = DMIUtil.MISSING_STRING;
protected String _flag30b = DMIUtil.MISSING_STRING;
protected double _day31  =  DMIUtil.MISSING_DOUBLE;
protected String _flag31a = DMIUtil.MISSING_STRING;
protected String _flag31b = DMIUtil.MISSING_STRING;

protected String _data_source =	DMIUtil.MISSING_STRING;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_DailyPcpn() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_cal_mon = null;
	_unit = null;
	_data_source = null;
	_modified = null;

	_flag1a  = null;
	_flag1b  = null;
	_flag2a  = null;
	_flag2b  = null;
	_flag3a  = null;
	_flag3b  = null;
	_flag4a  = null;
	_flag4b  = null;
	_flag5a  = null;
	_flag5b  = null;
	_flag6a  = null;
	_flag6b  = null;
	_flag7a  = null;
	_flag7b  = null;
	_flag8a  = null;
	_flag8b  = null;
	_flag9a  = null;
	_flag9b  = null;
	_flag10a = null;
	_flag10b = null;
	_flag11a = null;
	_flag11b = null;
	_flag12a = null;
	_flag12b = null;
	_flag13a = null;
	_flag13b = null;
	_flag14a = null;
	_flag14b = null;
	_flag15a = null;
	_flag15b = null;
	_flag16a = null;
	_flag16b = null;
	_flag17a = null;
	_flag17b = null;
	_flag18a = null;
	_flag18b = null;
	_flag19a = null;
	_flag19b = null;
	_flag20a = null;
	_flag20b = null;
	_flag21a = null;
	_flag21b = null;
	_flag22a = null;
	_flag22b = null;
	_flag23a = null;
	_flag23b = null;
	_flag24a = null;
	_flag24b = null;
	_flag25a = null;
	_flag25b = null;
	_flag26a = null;
	_flag26b = null;
	_flag27a = null;
	_flag27b = null;
	_flag28a = null;
	_flag28b = null;
	_flag29a = null;
	_flag29b = null;
	_flag30a = null;
	_flag30b = null;
	_flag31a = null;
	_flag31b = null;
	
	super.finalize();
}

/**
Returns _cal_mon
@return _cal_mon
*/
public String getCal_mon() {
	return _cal_mon;
}

/**
Returns _cal_mon_num
@return _cal_mon_num
*/
public int getCal_mon_num() {
	return _cal_mon_num;
}

/**
Returns _cal_year
@return _cal_year
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _data_source
@return _data_source
*/
public String getData_source() {
	return _data_source;
}

/**
Return the data value for the given day.
@return the data value for the given day, or DMIUtil.MISSING_DOUBLE if a bad
day.
@param day Day (1-31).
*/
public double getDay ( int day )
{	if ( day == 1 ) {
		return _day1;
	}
	else if ( day == 2 ) {
		return _day2;
	}
	else if ( day == 3 ) {
		return _day3;
	}
	else if ( day == 4 ) {
		return _day4;
	}
	else if ( day == 5 ) {
		return _day5;
	}
	else if ( day == 6 ) {
		return _day6;
	}
	else if ( day == 7 ) {
		return _day7;
	}
	else if ( day == 8 ) {
		return _day8;
	}
	else if ( day == 9 ) {
		return _day9;
	}
	else if ( day == 10 ) {
		return _day10;
	}
	else if ( day == 11 ) {
		return _day11;
	}
	else if ( day == 12 ) {
		return _day12;
	}
	else if ( day == 13 ) {
		return _day13;
	}
	else if ( day == 14 ) {
		return _day14;
	}
	else if ( day == 15 ) {
		return _day15;
	}
	else if ( day == 16 ) {
		return _day16;
	}
	else if ( day == 17 ) {
		return _day17;
	}
	else if ( day == 18 ) {
		return _day18;
	}
	else if ( day == 19 ) {
		return _day19;
	}
	else if ( day == 20 ) {
		return _day20;
	}
	else if ( day == 21 ) {
		return _day21;
	}
	else if ( day == 22 ) {
		return _day22;
	}
	else if ( day == 23 ) {
		return _day23;
	}
	else if ( day == 24 ) {
		return _day24;
	}
	else if ( day == 25 ) {
		return _day25;
	}
	else if ( day == 26 ) {
		return _day26;
	}
	else if ( day == 27 ) {
		return _day27;
	}
	else if ( day == 28 ) {
		return _day28;
	}
	else if ( day == 29 ) {
		return _day29;
	}
	else if ( day == 30 ) {
		return _day30;
	}
	else if ( day == 31 ) {
		return _day31;
	}
	return DMIUtil.MISSING_DOUBLE;
}

/**
Return the "a" flag for the given day.
@return the "a" flag for the given day, or DMIUtil.MISSING_STRING if a bad
day.
@param day Day (1-31).
*/
public String getFlaga ( int day )
{	if ( day == 1 ) {
		return _flag1a;
	}
	else if ( day == 2 ) {
		return _flag2a;
	}
	else if ( day == 3 ) {
		return _flag3a;
	}
	else if ( day == 4 ) {
		return _flag4a;
	}
	else if ( day == 5 ) {
		return _flag5a;
	}
	else if ( day == 6 ) {
		return _flag6a;
	}
	else if ( day == 7 ) {
		return _flag7a;
	}
	else if ( day == 8 ) {
		return _flag8a;
	}
	else if ( day == 9 ) {
		return _flag9a;
	}
	else if ( day == 10 ) {
		return _flag10a;
	}
	else if ( day == 11 ) {
		return _flag11a;
	}
	else if ( day == 12 ) {
		return _flag12a;
	}
	else if ( day == 13 ) {
		return _flag13a;
	}
	else if ( day == 14 ) {
		return _flag14a;
	}
	else if ( day == 15 ) {
		return _flag15a;
	}
	else if ( day == 16 ) {
		return _flag16a;
	}
	else if ( day == 17 ) {
		return _flag17a;
	}
	else if ( day == 18 ) {
		return _flag18a;
	}
	else if ( day == 19 ) {
		return _flag19a;
	}
	else if ( day == 20 ) {
		return _flag20a;
	}
	else if ( day == 21 ) {
		return _flag21a;
	}
	else if ( day == 22 ) {
		return _flag22a;
	}
	else if ( day == 23 ) {
		return _flag23a;
	}
	else if ( day == 24 ) {
		return _flag24a;
	}
	else if ( day == 25 ) {
		return _flag25a;
	}
	else if ( day == 26 ) {
		return _flag26a;
	}
	else if ( day == 27 ) {
		return _flag27a;
	}
	else if ( day == 28 ) {
		return _flag28a;
	}
	else if ( day == 29 ) {
		return _flag29a;
	}
	else if ( day == 30 ) {
		return _flag30a;
	}
	else if ( day == 31 ) {
		return _flag31a;
	}
	return DMIUtil.MISSING_STRING;
}

/**
Return the "b" flag for the given day.
@return the "b" flag for the given day, or DMIUtil.MISSING_STRING if a bad
day.
@param day Day (1-31).
*/
public String getFlagb ( int day )
{	if ( day == 1 ) {
		return _flag1b;
	}
	else if ( day == 2 ) {
		return _flag2b;
	}
	else if ( day == 3 ) {
		return _flag3b;
	}
	else if ( day == 4 ) {
		return _flag4b;
	}
	else if ( day == 5 ) {
		return _flag5b;
	}
	else if ( day == 6 ) {
		return _flag6b;
	}
	else if ( day == 7 ) {
		return _flag7b;
	}
	else if ( day == 8 ) {
		return _flag8b;
	}
	else if ( day == 9 ) {
		return _flag9b;
	}
	else if ( day == 10 ) {
		return _flag10b;
	}
	else if ( day == 11 ) {
		return _flag11b;
	}
	else if ( day == 12 ) {
		return _flag12b;
	}
	else if ( day == 13 ) {
		return _flag13b;
	}
	else if ( day == 14 ) {
		return _flag14b;
	}
	else if ( day == 15 ) {
		return _flag15b;
	}
	else if ( day == 16 ) {
		return _flag16b;
	}
	else if ( day == 17 ) {
		return _flag17b;
	}
	else if ( day == 18 ) {
		return _flag18b;
	}
	else if ( day == 19 ) {
		return _flag19b;
	}
	else if ( day == 20 ) {
		return _flag20b;
	}
	else if ( day == 21 ) {
		return _flag21b;
	}
	else if ( day == 22 ) {
		return _flag22b;
	}
	else if ( day == 23 ) {
		return _flag23b;
	}
	else if ( day == 24 ) {
		return _flag24b;
	}
	else if ( day == 25 ) {
		return _flag25b;
	}
	else if ( day == 26 ) {
		return _flag26b;
	}
	else if ( day == 27 ) {
		return _flag27b;
	}
	else if ( day == 28 ) {
		return _flag28b;
	}
	else if ( day == 29 ) {
		return _flag29b;
	}
	else if ( day == 30 ) {
		return _flag30b;
	}
	else if ( day == 31 ) {
		return _flag31b;
	}
	return DMIUtil.MISSING_STRING;
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
Returns _station_num
@return _station_num
*/
public int getStation_num() {
	return _station_num;
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
Sets _cal_mon
@param cal_mon value to put into _cal_mon
*/
public void setCal_mon(String cal_mon) {
	_cal_mon = cal_mon;
}

/**
Sets _cal_mon_num
@param cal_mon_num value to put into _cal_mon_num
*/
public void setCal_mon_num(int cal_mon_num) {
	_cal_mon_num = cal_mon_num;
}

/**
Sets _cal_year
@param cal_year value to put into _cal_year
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _data_source
@param data_source value to put into _data_source
*/
public void setData_source(String data_source) {
	_data_source = data_source;
}

/**
Set the data value for the given day.  If the day is invalid, no data value is
set.
@param day Day (1-31).
@param value Value for the day.
*/
public void setDay ( int day, double value )
{	if ( day == 1 ) {
		_day1 = value;
	}
	else if ( day == 2 ) {
		_day2 = value;
	}
	else if ( day == 3 ) {
		_day3 = value;
	}
	else if ( day == 4 ) {
		_day4 = value;
	}
	else if ( day == 5 ) {
		_day5 = value;
	}
	else if ( day == 6 ) {
		_day6 = value;
	}
	else if ( day == 7 ) {
		_day7 = value;
	}
	else if ( day == 8 ) {
		_day8 = value;
	}
	else if ( day == 9 ) {
		_day9 = value;
	}
	else if ( day == 10 ) {
		_day10 = value;
	}
	else if ( day == 11 ) {
		_day11 = value;
	}
	else if ( day == 12 ) {
		_day12 = value;
	}
	else if ( day == 13 ) {
		_day13 = value;
	}
	else if ( day == 14 ) {
		_day14 = value;
	}
	else if ( day == 15 ) {
		_day15 = value;
	}
	else if ( day == 16 ) {
		_day16 = value;
	}
	else if ( day == 17 ) {
		_day17 = value;
	}
	else if ( day == 18 ) {
		_day18 = value;
	}
	else if ( day == 19 ) {
		_day19 = value;
	}
	else if ( day == 20 ) {
		_day20 = value;
	}
	else if ( day == 21 ) {
		_day21 = value;
	}
	else if ( day == 22 ) {
		_day22 = value;
	}
	else if ( day == 23 ) {
		_day23 = value;
	}
	else if ( day == 24 ) {
		_day24 = value;
	}
	else if ( day == 25 ) {
		_day25 = value;
	}
	else if ( day == 26 ) {
		_day26 = value;
	}
	else if ( day == 27 ) {
		_day27 = value;
	}
	else if ( day == 28 ) {
		_day28 = value;
	}
	else if ( day == 29 ) {
		_day29 = value;
	}
	else if ( day == 30 ) {
		_day30 = value;
	}
	else if ( day == 31 ) {
		_day31 = value;
	}
}

/**
Set the "a" flag for the given day.  If the day is invalid, no flag value is
set.
@param day Day (1-31).
@param flaga Value for the "a" flag.
*/
public void setFlaga ( int day, String flaga )
{	if ( day == 1 ) {
		_flag1a = flaga;
	}
	else if ( day == 2 ) {
		_flag2a = flaga;
	}
	else if ( day == 3 ) {
		_flag3a = flaga;
	}
	else if ( day == 4 ) {
		_flag4a = flaga;
	}
	else if ( day == 5 ) {
		_flag5a = flaga;
	}
	else if ( day == 6 ) {
		_flag6a = flaga;
	}
	else if ( day == 7 ) {
		_flag7a = flaga;
	}
	else if ( day == 8 ) {
		_flag8a = flaga;
	}
	else if ( day == 9 ) {
		_flag9a = flaga;
	}
	else if ( day == 10 ) {
		_flag10a = flaga;
	}
	else if ( day == 11 ) {
		_flag11a = flaga;
	}
	else if ( day == 12 ) {
		_flag12a = flaga;
	}
	else if ( day == 13 ) {
		_flag13a = flaga;
	}
	else if ( day == 14 ) {
		_flag14a = flaga;
	}
	else if ( day == 15 ) {
		_flag15a = flaga;
	}
	else if ( day == 16 ) {
		_flag16a = flaga;
	}
	else if ( day == 17 ) {
		_flag17a = flaga;
	}
	else if ( day == 18 ) {
		_flag18a = flaga;
	}
	else if ( day == 19 ) {
		_flag19a = flaga;
	}
	else if ( day == 20 ) {
		_flag20a = flaga;
	}
	else if ( day == 21 ) {
		_flag21a = flaga;
	}
	else if ( day == 22 ) {
		_flag22a = flaga;
	}
	else if ( day == 23 ) {
		_flag23a = flaga;
	}
	else if ( day == 24 ) {
		_flag24a = flaga;
	}
	else if ( day == 25 ) {
		_flag25a = flaga;
	}
	else if ( day == 26 ) {
		_flag26a = flaga;
	}
	else if ( day == 27 ) {
		_flag27a = flaga;
	}
	else if ( day == 28 ) {
		_flag28a = flaga;
	}
	else if ( day == 29 ) {
		_flag29a = flaga;
	}
	else if ( day == 30 ) {
		_flag30a = flaga;
	}
	else if ( day == 31 ) {
		_flag31a = flaga;
	}
}

/**
Set the "b" flag for the given day.  If the day is invalid, no flag value is
set.
@param day Day (1-31).
@param flagb Value for the "b" flag.
*/
public void setFlagb ( int day, String flagb )
{	if ( day == 1 ) {
		_flag1a = flagb;
	}
	else if ( day == 2 ) {
		_flag2a = flagb;
	}
	else if ( day == 3 ) {
		_flag3a = flagb;
	}
	else if ( day == 4 ) {
		_flag4a = flagb;
	}
	else if ( day == 5 ) {
		_flag5a = flagb;
	}
	else if ( day == 6 ) {
		_flag6a = flagb;
	}
	else if ( day == 7 ) {
		_flag7a = flagb;
	}
	else if ( day == 8 ) {
		_flag8a = flagb;
	}
	else if ( day == 9 ) {
		_flag9a = flagb;
	}
	else if ( day == 10 ) {
		_flag10a = flagb;
	}
	else if ( day == 11 ) {
		_flag11a = flagb;
	}
	else if ( day == 12 ) {
		_flag12a = flagb;
	}
	else if ( day == 13 ) {
		_flag13a = flagb;
	}
	else if ( day == 14 ) {
		_flag14a = flagb;
	}
	else if ( day == 15 ) {
		_flag15a = flagb;
	}
	else if ( day == 16 ) {
		_flag16a = flagb;
	}
	else if ( day == 17 ) {
		_flag17a = flagb;
	}
	else if ( day == 18 ) {
		_flag18a = flagb;
	}
	else if ( day == 19 ) {
		_flag19a = flagb;
	}
	else if ( day == 20 ) {
		_flag20a = flagb;
	}
	else if ( day == 21 ) {
		_flag21a = flagb;
	}
	else if ( day == 22 ) {
		_flag22a = flagb;
	}
	else if ( day == 23 ) {
		_flag23a = flagb;
	}
	else if ( day == 24 ) {
		_flag24a = flagb;
	}
	else if ( day == 25 ) {
		_flag25a = flagb;
	}
	else if ( day == 26 ) {
		_flag26a = flagb;
	}
	else if ( day == 27 ) {
		_flag27a = flagb;
	}
	else if ( day == 28 ) {
		_flag28a = flagb;
	}
	else if ( day == 29 ) {
		_flag29a = flagb;
	}
	else if ( day == 30 ) {
		_flag30a = flagb;
	}
	else if ( day == 31 ) {
		_flag31a = flagb;
	}
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
	Sets _station_num
@param station_num value to put into _station_num
*/
public void setStation_num(int station_num) {
	_station_num = station_num;
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
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_DailyPcpn {"		+ "\n" + 
		"Meas_num:    " + _meas_num + "\n" + 
		"Cal_year:    " + _cal_year + "\n" + 
		"Cal_mon_num: " + _cal_mon_num + "\n" + 
		"Station_num: " + _station_num + "\n" + 
		"Cal_mon:     " + _cal_mon + "\n" + 
		"Unit:        " + _unit + "\n" + 

		"Day1:        " + _day1  + "\n" + 
		"Flag1a:       " + _flag1a + "\n" + 
		"Flag1b:       " + _flag1b + "\n" + 
		"Day2:        " + _day2  + "\n" + 
		"Flag2a:       " +_flag2a + "\n" + 
		"Flag2b:       " +_flag2b + "\n" + 
		"Day3:        " + _day3  + "\n" + 
		"Flag3a:       " +_flag3a + "\n" + 
		"Flag3b:       " +_flag3b + "\n" + 
		"Day4:        " + _day4  + "\n" + 
		"Flag4a:       " +_flag4a + "\n" + 
		"Flag4b:       " +_flag4b + "\n" + 
		"Day5:        " + _day5  + "\n" + 
		"Flag5a:       " +_flag5a + "\n" + 
		"Flag5b:       " +_flag5b + "\n" + 
		"Day6:        " + _day6  + "\n" + 
		"Flag6a:       " +_flag6a + "\n" + 
		"Flag6b:       " +_flag6b + "\n" + 
		"Day7:        " + _day7  + "\n" + 
		"Flag7a:       " +_flag7a + "\n" + 
		"Flag7b:       " +_flag7b + "\n" + 
		"Day8:        " + _day8  + "\n" + 
		"Flag8a:       " +_flag8a + "\n" + 
		"Flag8b:       " +_flag8b + "\n" + 
		"Day9:        " + _day9  + "\n" + 
		"Flag9a:       " +_flag9a + "\n" + 
		"Flag9b:       " +_flag9b + "\n" + 
		"Day10:       " + _day10  + "\n" + 
		"Flag10a:      " +_flag10a + "\n" + 
		"Flag10b:      " +_flag10b + "\n" + 
		"Day11:       " + _day11  + "\n" + 
		"Flag11a:      " +_flag11a + "\n" + 
		"Flag11b:      " +_flag11b + "\n" + 
		"Day12:       " + _day12  + "\n" + 
		"Flag12a:      " +_flag12a + "\n" + 
		"Flag12b:      " +_flag12b + "\n" + 
		"Day13:       " + _day13  + "\n" + 
		"Flag13a:      " +_flag13a + "\n" + 
		"Flag13b:      " +_flag13b + "\n" + 
		"Day14:       " + _day14  + "\n" + 
		"Flag14a:      " +_flag14a + "\n" + 
		"Flag14b:      " +_flag14b + "\n" + 
		"Day15:       " + _day15  + "\n" + 
		"Flag15a:      " +_flag15a + "\n" + 
		"Flag15b:      " +_flag15b + "\n" + 
		"Day16:       " + _day16  + "\n" + 
		"Flag16a:      " +_flag16a + "\n" + 
		"Flag16b:      " +_flag16b + "\n" + 
		"Day17:       " + _day17  + "\n" + 
		"Flag17a:      " +_flag17a + "\n" + 
		"Flag17b:      " +_flag17b + "\n" + 
		"Day18:       " + _day18  + "\n" + 
		"Flag18a:      " +_flag18a + "\n" + 
		"Flag18b:      " +_flag18b + "\n" + 
		"Day19:       " + _day19  + "\n" + 
		"Flag19a:      " +_flag19a + "\n" + 
		"Flag19b:      " +_flag19b + "\n" + 
		"Day20:       " + _day20  + "\n" + 
		"Flag20a:      " +_flag20a + "\n" + 
		"Flag20b:      " +_flag20b + "\n" + 
		"Day21:       " + _day21  + "\n" + 
		"Flag21a:      " +_flag21a + "\n" + 
		"Flag21b:      " +_flag21b + "\n" + 
		"Day22:       " + _day22  + "\n" + 
		"Flag22a:      " +_flag22a + "\n" + 
		"Flag22b:      " +_flag22b + "\n" + 
		"Day23:       " + _day23  + "\n" + 
		"Flag23a:      " +_flag23a + "\n" + 
		"Flag23b:      " +_flag23b + "\n" + 
		"Day24:       " + _day24  + "\n" + 
		"Flag24a:      " +_flag24a + "\n" + 
		"Flag24b:      " +_flag24b + "\n" + 
		"Day25:       " + _day25  + "\n" + 
		"Flag25a:      " +_flag25a + "\n" + 
		"Flag25b:      " +_flag25b + "\n" + 
		"Day26:       " + _day26  + "\n" + 
		"Flag26a:      " +_flag26a + "\n" + 
		"Flag26b:      " +_flag26b + "\n" + 
		"Day27:       " + _day27  + "\n" + 
		"Flag27a:      " +_flag27a + "\n" + 
		"Flag27b:      " +_flag27b + "\n" + 
		"Day28:       " + _day28  + "\n" + 
		"Flag28a:      " +_flag28a + "\n" + 
		"Flag28b:      " +_flag28b + "\n" + 
		"Day29:       " + _day29  + "\n" + 
		"Flag29a:      " +_flag29a + "\n" + 
		"Flag29b:      " +_flag29b + "\n" + 
		"Day30:       " + _day30  + "\n" + 
		"Flag30a:      " +_flag30a + "\n" + 
		"Flag30b:      " +_flag30b + "\n" + 
		"Day31:       " + _day31  + "\n" + 
		"Flag31a:      " +_flag31a + "\n" + 
		"Flag31b:      " +_flag31b + "\n" + 

		"Data_source: " + _data_source + "\n" + 
		"Modified:    " + _modified + "\n" + 
		"User_num:    " + _user_num + "\n}\n";
}

}
