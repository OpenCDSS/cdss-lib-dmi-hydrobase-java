// HydroBase_StructMeasTypeView - Class to hold data from the HydroBase vw_CDSS_Structure view.

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
// HydroBase_StructMeasTypeView.java - Class to hold data from the HydroBase 
//	vw_CDSS_Structure view.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2004-02-03	J. Thomas Sapienza, RTi	Initial version from HydroBase_Rolodex
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

import RTi.TS.TS;

import java.util.Date;

/**
Class to store data from the HydroBase rolodex, wdwater, irrig summary, 
structure decree summary, geoloc and structure tables.
*/
public class HydroBase_StructMeasTypeView
extends HydroBase_StructureView {

protected int _meas_num = 		DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected String _meas_type = 		DMIUtil.MISSING_STRING;
protected String _time_step = 		DMIUtil.MISSING_STRING;
protected int _start_year = 		DMIUtil.MISSING_INT;
protected int _end_year = 		DMIUtil.MISSING_INT;
protected String _identifier = 		DMIUtil.MISSING_STRING;
protected String _transmit = 		DMIUtil.MISSING_STRING;
protected int _meas_count = 		DMIUtil.MISSING_INT;
protected String _data_source = 	DMIUtil.MISSING_STRING;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;
protected String _str_name = 		DMIUtil.MISSING_STRING;
protected int _id = 			DMIUtil.MISSING_INT;
protected int _wd = 			DMIUtil.MISSING_INT;
protected String _data_units = 		DMIUtil.MISSING_STRING;	
protected TS _timeseries = null;		// The time series associated with
					// the HydroBase_StructMeasType, used
					// temporarily in GUIs, etc.
protected String _tsIdentifier = null;	// The time series identifier associated
					// with the time series, used
					// temporarily in GUIs, etc.

/**
Constructor.
*/
public HydroBase_StructMeasTypeView() {
	super();
}

/**
Copy constructor.  
@param h the HydroBase_StructMeasType object to copy.
*/
public HydroBase_StructMeasTypeView(HydroBase_StructMeasTypeView h) {
	setMeas_num(h.getMeas_num());
	setStructure_num(h.getStructure_num());
	setMeas_type(new String(h.getMeas_type()));
	setTime_step(h.getTime_step());
	setStart_year(h.getStart_year());
	setEnd_year(h.getEnd_year());
	setIdentifier(new String(h.getIdentifier()));
	setTransmit(new String(h.getTransmit()));
	setMeas_count(h.getMeas_count());
	setData_source(new String(h.getData_source()));
	Date d = h.getModified();
	if (d != DMIUtil.MISSING_DATE) {	
		setModified((Date)(h.getModified().clone()));
	}
	setUser_num(h.getUser_num());
	setStr_name(new String(h.getStr_name()));
	setID(h.getID());
	setWD(h.getWD());
	if (_timeseries != null) {
		_timeseries = (TS)(h.getTimeSeries().clone());
	}
	_tsIdentifier = h.getTSIdentifier();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_meas_type = null;
	_time_step = null;
	_identifier = null;
	_transmit = null;
	_data_source = null;
	_modified = null;
	_str_name = null;
	_timeseries = null;
	_tsIdentifier = null;
	
	super.finalize();
}

/**
Returns _data_source
@return _data_source
*/
public String getData_source() {
	return _data_source;
}

/**
Returns _data_units
@return _data_units
*/
public String getData_units() {
	return _data_units;
}

/**
Returns _end_year
@return _end_year
*/
public int getEnd_year() {
	return _end_year;
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
}

/**
Returns _identifier
@return _identifier
*/
public String getIdentifier() {
	return _identifier;
}

/**
Returns _meas_count
@return _meas_count
*/
public int getMeas_count() {
	return _meas_count;
}

/**
Returns _meas_num
@return _meas_num
*/
public int getMeas_num() {
	return _meas_num;
}

/**
Returns _meas_type
@return _meas_type
*/
public String getMeas_type() {
	return _meas_type;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _start_year
@return _start_year
*/
public int getStart_year() {
	return _start_year;
}

/**
Returns _str_name
@return _str_name
*/
public String getStr_name() {
	return _str_name;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _time_step
@return _time_step
*/
public String getTime_step() {
	return _time_step;
}

/**
Returns _transmit
@return _transmit
*/
public String getTransmit() {
	return _transmit;
}

/**
Returns the time series.  This method is intended for use by GUIs, etc. -
the time series are not automatically queried for each record.
@return the time series
*/
public TS getTimeSeries() {
	return _timeseries;
}

/**
Returns the time series identifier.
This method is intended for use by GUIs, etc. -
the time series are not automatically queried for each record.
@return the time series identifier.
*/
public String getTSIdentifier() {
	return _tsIdentifier;
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
Sets _data_source
@param data_source value to put into _data_source
*/
public void setData_source(String data_source) {
	_data_source = data_source;
}

/**
Sets _data_units
@param data_units value to put into _data_units
*/
public void setData_units(String data_units) {
	_data_units = data_units;
}

/**
Sets _end_year
@param end_year value to put into _end_year
*/
public void setEnd_year(int end_year) {
	_end_year = end_year;
}

/**
Sets _id
@param id value to put into _id
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _identifier
@param identifier value to put into _identifier
*/
public void setIdentifier(String identifier) {
	_identifier = identifier;
}

/**
Sets _meas_count
@param meas_count value to put into _meas_count
*/
public void setMeas_count(int meas_count) {
	_meas_count = meas_count;
}

/**
Sets _meas_num
@param meas_num value to put into _meas_num
*/
public void setMeas_num(int meas_num) {
	_meas_num = meas_num;
}

/**
Sets _meas_type
@param meas_type value to put into _meas_type
*/
public void setMeas_type(String meas_type) {
	_meas_type = meas_type;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _start_year
@param start_year value to put into _start_year
*/
public void setStart_year(int start_year) {
	_start_year = start_year;
}

/**
Sets _str_name
@param str_name value to put into _str_name
*/
public void setStr_name(String str_name) {
	_str_name = str_name;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _time_step
@param time_step value to put into _time_step
*/
public void setTime_step(String time_step) {
	_time_step = time_step;
}

/**
Sets _transmit
@param transmit value to put into _transmit
*/
public void setTransmit(String transmit) {
	_transmit = transmit;
}

/**
Sets the time series.
This method is intended for use by GUIs, etc. -
the time series are not automatically queried for each record.
@param ts time series to set the internal time series to
*/
public void setTimeSeries(TS ts) {
	_timeseries = ts;
}

/**
Sets the time series identifier.
This method is intended for use by GUIs, etc. -
the time series are not automatically queried for each record.
@param tsIdentifier value to set the time series identifier to.
*/
public void setTSIdentifier(String tsIdentifier) {
	_tsIdentifier = tsIdentifier;
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


}
