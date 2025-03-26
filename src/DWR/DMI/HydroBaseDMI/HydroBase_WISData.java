// HydroBase_WISData - Class to hold data from the HydroBase wis_data table.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2025 Colorado Department of Natural Resources

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

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase wis_data table.
*/
public class HydroBase_WISData 
extends DMIDataObject {

protected int _wis_row = 		DMIUtil.MISSING_INT;
protected int _wis_num = 		DMIUtil.MISSING_INT;
protected Date _set_date = 		DMIUtil.MISSING_DATE;
protected double _point_flow = 		DMIUtil.MISSING_DOUBLE;
protected double _nat_flow = 		DMIUtil.MISSING_DOUBLE;
protected double _delivery_flow = 	DMIUtil.MISSING_DOUBLE;
protected double _gain = 		DMIUtil.MISSING_DOUBLE;
protected double _trib_natural = 	DMIUtil.MISSING_DOUBLE;
protected double _trib_delivery = 	DMIUtil.MISSING_DOUBLE;
protected double _priority_divr = 	DMIUtil.MISSING_DOUBLE;
protected double _delivery_divr = 	DMIUtil.MISSING_DOUBLE;
protected double _release = 		DMIUtil.MISSING_DOUBLE;
protected String _comment = 		DMIUtil.MISSING_STRING;
protected int _dry_river = 		DMIUtil.MISSING_INT;
protected boolean _isEvaluated = 	false;
protected String _row_label = 		DMIUtil.MISSING_STRING;

// non-db fields
protected String _etc = 		DMIUtil.MISSING_STRING;
protected String _gainLoss = 		DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_WISData() {
	super();
}

/**
Returns _comment
@return _comment
*/
public String getComment() {
	return _comment;
}

/**
Returns _delivery_flow
@return _delivery_flow
*/
public double getDelivery_flow() {
	return _delivery_flow;
}

/**
Returns _delivery_divr
@return _delivery_divr
*/
public double getDelivery_divr() {
	return _delivery_divr;
}

/**
Returns _dry_river
@return _dry_river
*/
public int getDry_river() {
	return _dry_river;
}

/**
Returns _etc
@return _etc
*/
public String getEtc() {
	return _etc;
}

/**
Returns _gain
@return _gain
*/
public double getGain() {
	return _gain;
}

/**
Returns _gainLoss.
@return _gainLoss.
*/
public String getGainLoss() {
	return _gainLoss;
}

/**
Returns _nat_flow
@return _nat_flow
*/
public double getNat_flow() {
	return _nat_flow;
}

/**
Returns _row_label
@return _row_label
*/
public String getRow_label() {
	return _row_label;
}

/**
Returns _point_flow
@return _point_flow
*/
public double getPoint_flow() {
	return _point_flow;
}

/**
Returns _priority_divr
@return _priority_divr
*/
public double getPriority_divr() {
	return _priority_divr;
}

/**
Returns _release
@return _release
*/
public double getRelease() {
	return _release;
}

/**
Returns _set_date
@return _set_date
*/
public Date getSet_date() {
	return _set_date;
}

/**
Returns _trib_delivery
@return _trib_delivery
*/
public double getTrib_delivery() {
	return _trib_delivery;
}

/**
Returns _trib_natural
@return _trib_natural
*/
public double getTrib_natural() {
	return _trib_natural;
}

/**
Returns _wis_num
@return _wis_num
*/
public int getWis_num() {
	return _wis_num;
}

/**
Returns _wis_row
@return _wis_row
*/
public int getWis_row() {
	return _wis_row;
}

/**
Returns whether it is evaluated or not.
@return whether it is evaluated or not.
*/
public boolean isEvaluated() {
	return _isEvaluated;
}

/**
Sets _comment
@param comment value to put into _comment
*/
public void setComment(String comment) {
	_comment = comment;
}

/**
Sets _delivery_flow
@param delivery_flow value to put into _delivery_flow
*/
public void setDelivery_flow(double delivery_flow) {
	_delivery_flow = delivery_flow;
}

/**
Sets _delivery_divr
@param delivery_divr value to put into _delivery_divr
*/
public void setDelivery_divr(double delivery_divr) {
	_delivery_divr = delivery_divr;
}

/**
Sets _dry_river
@param dry_river value to put into _dry_river
*/
public void setDry_river(int dry_river) {
	_dry_river = dry_river;
}

/**
Sets _etc
@param etc value to put into _etc
*/
public void setEtc(String etc) {
	_etc = etc;
}

/**
Sets whether it is evaluated or not.
@param b whether it is evaluated or not.
*/
public void setIsEvaluated( boolean b ) {
	_isEvaluated = b;
}

/**
Sets _gain
@param gain value to put into _gain
*/
public void setGain(double gain) {
	_gain = gain;
}

/**
Sets _gainLoss.
@param gainLoss value to put into _gainLoss.
*/
public void setGainLoss(String gainLoss) {
	_gainLoss = gainLoss;
}

/**
Sets _nat_flow
@param nat_flow value to put into _nat_flow
*/
public void setNat_flow(double nat_flow) {
	_nat_flow = nat_flow;
}

/**
Sets _point_flow
@param point_flow value to put into _point_flow
*/
public void setPoint_flow(double point_flow) {
	_point_flow = point_flow;
}

/**
Sets _priority_divr
@param priority_divr value to put into _priority_divr
*/
public void setPriority_divr(double priority_divr) {
	_priority_divr = priority_divr;
}

/**
Sets _release
@param release value to put into _release
*/
public void setRelease(double release) {
	_release = release;
}

/**
Sets _row_label
@param row_label value to put into _row_label
*/
public void setRow_label(String row_label) {
	_row_label = row_label;
}

/**
Sets _set_date
@param set_date value to put into _set_date
*/
public void setSet_date(Date set_date) {
	_set_date = set_date;
}

/**
Sets _trib_delivery
@param trib_delivery value to put into _trib_delivery
*/
public void setTrib_delivery(double trib_delivery) {
	_trib_delivery = trib_delivery;
}

/**
Sets _trib_natural
@param trib_natural value to put into _trib_natural
*/
public void setTrib_natural(double trib_natural) {
	_trib_natural = trib_natural;
}

/**
Sets _wis_num
@param wis_num value to put into _wis_num
*/
public void setWis_num(int wis_num) {
	_wis_num = wis_num;
}

/**
Sets _wis_row
@param wis_row value to put into _wis_row
*/
public void setWis_row(int wis_row) {
	_wis_row = wis_row;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_WISData {"		+ "\n" + 
		"Wis_row:       " + _wis_row + "\n" +
		"Wis_num:       " + _wis_num + "\n" +
		"Set_date:      " + _set_date + "\n" +
		"Point_flow:    " + _point_flow + "\n" +
		"Nat_flow:      " + _nat_flow + "\n" +
		"Delivery_flow: " + _delivery_flow	 + "\n" +
		"Gain:          " + _gain + "\n" +
		"Trib_natural:  " + _trib_natural + "\n" +
		"Trib_delivery: " + _trib_delivery + "\n" +
		"Priority_divr: " + _priority_divr + "\n" +
		"Delivery_divr: " + _delivery_divr + "\n" +
		"Release:       " + _release + "\n" +
		"Comment:       " + _comment + "\n" +
		"Dry_river:     " + _dry_river + "\n" + 
		"Row_label:     " + _row_label + "\n}\n";
}

}
