// HydroBase_NetAmts - Class to hold data from the HydroBase net_amts table

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
// HydroBase_NetAmts.java - Class to hold data from the HydroBase net_amts table
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-13	J. Thomas Sapienza, RTi	Initial version from HBWaterRightNet.
// 2003-02-20	JTS, RTi		Commented out X* data.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2003-02-25	JTS, RTi		Moved the add*() methods to 
//					HydroBase_Report_Cumulative.
//					Added rdir, rnga, tdir, tsa.
// 2005-07-11	JTS, RTi		Added strtype, wr_stream_no.
// 2006-04-17	Steven A. Malers, RTi	Add common_id and set/get methods, for
//					use in StateDMI when storing well
//					permits as water rights.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase net_amts table.  
*/
public class HydroBase_NetAmts 
extends DMIDataObject {

protected int _net_num = DMIUtil.MISSING_INT;
protected int _div = DMIUtil.MISSING_INT;
protected String _tab_trib = DMIUtil.MISSING_STRING;
protected int _wd = DMIUtil.MISSING_INT;
protected int _id = DMIUtil.MISSING_INT;
protected String _wdid = DMIUtil.MISSING_STRING; // Was added in later HydroBase, not in original desing, which used int wd and id
protected String _wr_name = DMIUtil.MISSING_STRING;
protected int _xwr_stream_no = DMIUtil.MISSING_INT;
protected int _wr_stream_no = DMIUtil.MISSING_INT;
protected String _wd_stream_name = DMIUtil.MISSING_STRING;
protected String _xstrtype = DMIUtil.MISSING_STRING;
protected String _strtype = DMIUtil.MISSING_STRING;
protected String _pm = DMIUtil.MISSING_STRING;
protected String _rng = DMIUtil.MISSING_STRING;
protected String _rdir = DMIUtil.MISSING_STRING;
protected String _rnga = DMIUtil.MISSING_STRING;
protected String _ts = DMIUtil.MISSING_STRING;
protected String _tdir = DMIUtil.MISSING_STRING;
protected String _tsa = DMIUtil.MISSING_STRING;
protected int _sec = DMIUtil.MISSING_INT;
protected String _seca = DMIUtil.MISSING_STRING;
protected String _q160 = DMIUtil.MISSING_STRING;
protected String _q40 = DMIUtil.MISSING_STRING;
protected String _q10 = DMIUtil.MISSING_STRING;
protected int _cty = DMIUtil.MISSING_INT;
protected Date _adj_date = DMIUtil.MISSING_DATE;
protected Date _padj_date = DMIUtil.MISSING_DATE;
protected Date _apro_date = DMIUtil.MISSING_DATE;
protected double _admin_no = DMIUtil.MISSING_DOUBLE;
protected int _order_no = DMIUtil.MISSING_INT;
protected String _pri_case_no = DMIUtil.MISSING_STRING;
protected String _adj_type = DMIUtil.MISSING_STRING;
protected String _use = DMIUtil.MISSING_STRING;
protected double _net_rate_abs = DMIUtil.MISSING_DOUBLE;
protected double _net_vol_abs = DMIUtil.MISSING_DOUBLE;
protected double _net_rate_cond = DMIUtil.MISSING_DOUBLE;
protected double _net_vol_cond = DMIUtil.MISSING_DOUBLE;
protected double _net_rate_apex = DMIUtil.MISSING_DOUBLE;
protected double _net_vol_apex = DMIUtil.MISSING_DOUBLE;
protected String _abs = DMIUtil.MISSING_STRING;
protected String _cond = DMIUtil.MISSING_STRING;
protected String _apex = DMIUtil.MISSING_STRING;
protected double _net_abs = DMIUtil.MISSING_DOUBLE;
protected double _net_cond = DMIUtil.MISSING_DOUBLE;
protected double _net_apex = DMIUtil.MISSING_DOUBLE;
protected String _unit = DMIUtil.MISSING_STRING;
protected String _action_comment = DMIUtil.MISSING_STRING;
protected int _right_num = DMIUtil.MISSING_INT;
protected int _structure_num = DMIUtil.MISSING_INT;

/**
 * Collection part ID type, only used with StateDMI.
 * This is either WDID or Receipt and allows water rights created from well permits to be clearly indicated.
 * This is necessary because a lookup the other direction might have duplicates and have ambiguous lookup.
 * Default to WDID.
 */
protected HydroBase_NetAmts_CollectionPartIdType _collectionPartIdType = HydroBase_NetAmts_CollectionPartIdType.WDID;

/**
The common identifier is used to store a formatted WDID or concatenated well
permit number, suffix, replacement, when processing data.  This data member
is currently only used by StateDMI. 
*/
private String _common_id = DMIUtil.MISSING_STRING;

// TODO SAM 2016-10-03 Evaluate moving to a derived class
// The following data are not part of the official HydroBase/StateMod specification but are useful
// to output in order to understand how well rights are determined.
// The data are specific to the State of Colorado due to its complex data model.

/**
 * Well permit receipt.	
 */
private String __xPermitReceipt = "";

/**
 * Well yield GPM
 */
private double __xYieldGPM = Double.NaN;

/**
 * Well yield alternate point/exchange (APEX) GPM
 */
private double __xYieldApexGPM = Double.NaN;

/**
 * Well permit date.
 */
private Date __xPermitDate = null;

/**
 * Well right appropriation date.
 */
private Date __xApproDate = null;

/**
 * Prorated yield based on parcel area
 */
private double __xProratedYield = Double.NaN;

/**
 * Fraction of yield (percent_yield in HydroBase) attributed to number of wells that are split.
 */
private double __xFractionYield = Double.NaN;

/**
 * Fraction of yield attributed to ditch proration for parcel.
 */
private double __xDitchFraction = Double.NaN;

/**
The parcel ID is used when processing well rights and is only used by StateDMI. 
*/
private int _parcelID = DMIUtil.MISSING_INT;

/**
The parcel match class is used when processing well rights and is only used by StateDMI. 
*/
private int _parcelMatchClass = DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_NetAmts() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_abs = null;
	_action_comment = null;
	_xstrtype = null;
	_strtype = null;
	_wr_name = null;
	_wd_stream_name = null;
	_use = null;
	_unit = null;
	_ts = null;
	_tab_trib = null;
	_seca = null;
	_rng = null;
	_rdir = null;
	_rnga = null;
	_tdir = null;
	_tsa = null;
	_q10 = null;
	_q40 = null;
	_q160 = null;
	_pri_case_no = null;
	_pm = null;
	_padj_date = null;
	_cond = null;
	_apro_date = null;
	_apex = null;
	_adj_type = null;
	_adj_date = null;
	
	super.finalize();
}

/**
Returns _abs
@return _abs
*/
public String getAbs() {
	return _abs;
}

/**
Returns _action_comment
@return _action_comment
*/
public String getAction_comment() {
	return _action_comment;
}

/**
Returns _adj_date
@return _adj_date
*/
public Date getAdj_date() {
	return _adj_date;
}

/**
Returns _adj_type
@return _adj_type
*/
public String getAdj_type() {
	return _adj_type;
}

/**
Returns _admin_no
@return _admin_no
*/
public double getAdmin_no() {
	return _admin_no;
}

/**
Returns _apex
@return _apex
*/
public String getApex() {
	return _apex;
}

/**
Returns _apro_date
@return _apro_date
*/
public Date getApro_date() {
	return _apro_date;
}

/**
Returns the collection part ID type ("WDID" or "Receipt")
@return the collection part ID type ("WDID" or "Receipt")
*/
public HydroBase_NetAmts_CollectionPartIdType getCollectionPartIdType() {
	return _collectionPartIdType;
}

/**
Returns _common_id
@return _common_id
*/
public String getCommonID() {
	return _common_id;
}

/**
Returns _cond
@return _cond
*/
public String getCond() {
	return _cond;
}

/**
Returns _cty
@return _cty
*/
public int getCty() {
	return _cty;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _id
@return _id
*/
public int getID() {
	return _id;
}

/**
Returns _net_abs
@return _net_abs
*/
public double getNet_abs() {
	return _net_abs;
}

/**
Returns _net_apex
@return _net_apex
*/
public double getNet_apex() {
	return _net_apex;
}

/**
Returns _net_cond
@return _net_cond
*/
public double getNet_cond() {
	return _net_cond;
}

/**
Returns _net_rate_abs
@return _net_rate_abs
*/
public double getNet_rate_abs() {
	return _net_rate_abs;
}

/**
Returns _net_rate_apex
@return _net_rate_apex
*/
public double getNet_rate_apex() {
	return _net_rate_apex;
}

/**
Returns _net_rate_cond
@return _net_rate_cond
*/
public double getNet_rate_cond() {
	return _net_rate_cond;
}

/**
Returns _net_num
@return _net_num
*/
public int getNet_num() {
	return _net_num;
}

/**
Returns _net_vol_abs
@return _net_vol_abs
*/
public double getNet_vol_abs() {
	return _net_vol_abs;
}

/**
Returns _net_vol_apex
@return _net_vol_apex
*/
public double getNet_vol_apex() {
	return _net_vol_apex;
}

/**
Returns _net_vol_cond
@return _net_vol_cond
*/
public double getNet_vol_cond() {
	return _net_vol_cond;
}

/**
Returns _order_no
@return _order_no
*/
public int getOrder_no() {
	return _order_no;
}

/**
Returns _padj_date
@return _padj_date
*/
public Date getPadj_date() {
	return _padj_date;
}

/**
Returns _parcelID
@return _parcelID
*/
public int getParcelID() {
	return _parcelID;
}

/**
Returns _parcelMatchClass
@return _parcelMatchClass
*/
public int getParcelMatchClass() {
	return _parcelMatchClass;
}

/**
Returns _pm
@return _pm
*/
public String getPM() {
	return _pm;
}

/**
Returns _pri_case_no
@return _pri_case_no
*/
public String getPri_case_no() {
	return _pri_case_no;
}

/**
Returns _q160
@return _q160
*/
public String getQ160() {
	return _q160;
}

/**
Returns _q40
@return _q40
*/
public String getQ40() {
	return _q40;
}

/**
Returns _q10
@return _q10
*/
public String getQ10() {
	return _q10;
}

/**
Returns _rdir
@return _dir
*/
public String getRdir() {
	return _rdir;
}

/**
Returns _right_num
@return _right_num
*/
public int getRight_num() {
	return _right_num;
}

/**
Returns _rng
@return _rng
*/
public String getRng() {
	return _rng;
}

/**
Returns _rnga
@return _rnga
*/
public String getRnga() {
	return _rnga;
}

/**
Returns _sec
@return _sec
*/
public int getSec() {
	return _sec;
}

/**
Returns _seca
@return _seca
*/
public String getSeca() {
	return _seca;
}

/**
Returns _strtype
@return _strtype
*/
public String getStrtype() {
	return _strtype;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _tab_trib
@return _tab_trib
*/
public String getTab_trib() {
	return _tab_trib;
}

/**
Returns _tdir
@return _tdir
*/
public String getTdir() {
	return _tdir;
}

/**
Returns _ts
@return _ts
*/
public String getTS() {
	return _ts;
}

/**
Returns _tsa
@return _tsa
*/
public String getTsa() {
	return _tsa;
}

/**
Returns _unit
@return _unit
*/
public String getUnit() {
	return _unit;
}

/**
Returns _use
@return _use
*/
public String getUse() {
	return _use;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Returns the WDID from HydroBase.
@return _wdid
*/
public String getWDID() {
	return _wdid;
}

/**
Returns _wd_stream_name
@return _wd_stream_name
*/
public String getWd_stream_name() {
	return _wd_stream_name;
}

/**
Returns _wr_stream_no
@return _wr_stream_no
*/
public int getWr_stream_no() {
	return _wr_stream_no;
}

/**
Returns _wr_name
@return _wr_name
*/
public String getWr_name() {
	return _wr_name;
}

/**
Returns _xstrtype
@return _xstrtype
*/
public String getXstrtype() {
	return _xstrtype;
}

/**
Returns _xwr_stream_no
@return _xwr_stream_no
*/
public int getXwr_stream_no() {
	return _xwr_stream_no;
}

/**
 * Return the well permit receipt.	
 */
public String getXPermitReceipt () {
	return __xPermitReceipt;
}

/**
 * Return the well yield GPM.
 */
public double getXYieldGPM () {
	return __xYieldGPM;
}

/**
 * Return the well yield alternate point/exchange (APEX) GPM
 */
public double getXYieldApexGPM () {
	return __xYieldApexGPM;
}

/**
 * Return the well permit date.
 */
public Date getXPermitDate () {
	return __xPermitDate;
}

/**
 * Well right appropriation date.
 */
public Date getXApproDate () {
	return __xApproDate;
}

/**
 * Return the prorated yield based on parcel area
 */
public double getXProratedYield () {
	return __xProratedYield;
}

/**
 * Return the fraction of yield (percent_yield in HydroBase)
 */
public double getXFractionYield () {
	return __xFractionYield;
}

/**
 * Return the fraction of ditch associated with parcel.
 */
public double getXDitchFraction () {
	return __xDitchFraction;
}

/**
Sets _abs
@param abs value to put into _abs
*/
public void setAbs(String abs) {
	_abs = abs;
}

/**
Sets _action_comment
@param action_comment value to put into _action_comment
*/
public void setAction_comment(String action_comment) {
	_action_comment = action_comment;
}

/**
Sets _adj_date
@param adj_date value to put into _adj_date
*/
public void setAdj_date(Date adj_date) {
	_adj_date = adj_date;
}

/**
Sets _adj_type
@param adj_type value to put into _adj_type
*/
public void setAdj_type(String adj_type) {
	_adj_type = adj_type;
}

/**
Sets _admin_no
@param admin_no value to put into _admin_no
*/
public void setAdmin_no(double admin_no) {
	_admin_no = admin_no;
}

/**
Sets _apex
@param apex value to put into _apex
*/
public void setApex(String apex) {
	_apex = apex;
}

/**
Sets _apro_date
@param apro_date value to put into _apro_date
*/
public void setApro_date(Date apro_date) {
	_apro_date = apro_date;
}

/**
Sets the collection part ID type (should be "WDID" or "Receipt").
@param common_id Common identifier for the well right.
*/
public void setCollectionIdPartType (HydroBase_NetAmts_CollectionPartIdType collectionPartIdType) {
	_collectionPartIdType = collectionPartIdType;
}

/**
Sets _common_id for the well right, always the formatted WDID.
TODO SAM 2016-05-29 Could this also be the well receipt?
@param common_id Common identifier for the well right.
*/
public void setCommonID(String common_id) {
	_common_id = common_id;
}

/**
Sets _cond
@param cond value to put into _cond
*/
public void setCond(String cond) {
	_cond = cond;
}

/**
Sets _cty
@param cty value to put into _cty
*/
public void setCty(int cty) {
	_cty = cty;
}

/**
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _id
@param id value to put into _id
*/
public void setID(int id) {
	_id = id;
}

/**
Sets _net_abs
@param net_abs value to put into _net_abs
*/
public void setNet_abs(double net_abs) {
	_net_abs = net_abs;
}

/**
Sets _net_apex
@param net_apex value to put into _net_apex
*/
public void setNet_apex(double net_apex) {
	_net_apex = net_apex;
}

/**
Sets _net_cond
@param net_cond value to put into _net_cond
*/
public void setNet_cond(double net_cond) {
	_net_cond = net_cond;
}

/**
Sets _net_rate_abs
@param net_rate_abs value to put into _net_rate_abs
*/
public void setNet_rate_abs(double net_rate_abs) {
	_net_rate_abs = net_rate_abs;
}

/**
Sets _net_rate_apex
@param net_rate_apex value to put into _net_rate_apex
*/
public void setNet_rate_apex(double net_rate_apex) {
	_net_rate_apex = net_rate_apex;
}

/**
Sets _net_rate_cond
@param net_rate_cond value to put into _net_rate_cond
*/
public void setNet_rate_cond(double net_rate_cond) {
	_net_rate_cond = net_rate_cond;
}

/**
Sets _net_num
@param net_num value to put into _net_num
*/
public void setNet_num(int net_num) {
	_net_num = net_num;
}

/**
Sets _net_vol_abs
@param net_vol_abs value to put into _net_vol_abs
*/
public void setNet_vol_abs(double net_vol_abs) {
	_net_vol_abs = net_vol_abs;
}

/**
Sets _net_vol_apex
@param net_vol_apex value to put into _net_vol_apex
*/
public void setNet_vol_apex(double net_vol_apex) {
	_net_vol_apex = net_vol_apex;
}

/**
Sets _net_vol_cond
@param net_vol_cond value to put into _net_vol_cond
*/
public void setNet_vol_cond(double net_vol_cond) {
	_net_vol_cond = net_vol_cond;
}

/**
Sets _order_no
@param order_no value to put into _order_no
*/
public void setOrder_no(int order_no) {
	_order_no = order_no;
}

/**
Sets _padj_date
@param padj_date value to put into _padj_date
*/
public void setPadj_date(Date padj_date) {
	_padj_date = padj_date;
}

/**
Sets _parcelID
@param parcelID value to put into _parcelID
*/
public void setParcelID(int parcelID) {
	_parcelID = parcelID;
}

/**
Sets _parcelMatchClass
@param parcelMatchClass value to put into _parcelMatchClass
*/
public void setParcelMatchClass(int parcelMatchClass) {
	_parcelMatchClass = parcelMatchClass;
}

/**
Sets _pm
@param pm value to put into _pm
*/
public void setPM(String pm) {
	_pm = pm;
}

/**
Sets _pri_case_no
@param pri_case_no value to put into _pri_case_no
*/
public void setPri_case_no(String pri_case_no) {
	_pri_case_no = pri_case_no;
}

/**
Sets _q160
@param q160 value to put into _q160
*/
public void setQ160(String q160) {
	_q160 = q160;
}

/**
Sets _q40
@param q40 value to put into _q40
*/
public void setQ40(String q40) {
	_q40 = q40;
}

/**
Sets _q10
@param q10 value to put into _q10
*/
public void setQ10(String q10) {
	_q10 = q10;
}

/**
Sets _rdir
@param rdir value to put into _rdir
*/
public void setRdir(String rdir) {
	_rdir = rdir;
}

/**
Sets _right_num
@param right_num value to put into _right_num
*/
public void setRight_num(int right_num) {
	_right_num = right_num;
}

/**
Sets _rng
@param rng value to put into _rng
*/
public void setRng(String rng) {
	_rng = rng;
}

/**
Sets _rnga
@param rnga value to put into _rnga
*/
public void setRnga(String rnga) {
	_rnga = rnga;
}

/**
Sets _sec
@param sec value to put into _sec
*/
public void setSec(int sec) {
	_sec = sec;
}

/**
Sets _seca
@param seca value to put into _seca
*/
public void setSeca(String seca) {
	_seca = seca;
}

/**
Sets _strtype
@param strtype value to put into _strtype
*/
public void setStrtype(String strtype) {
	_strtype = strtype;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _tab_trib
@param tab_trib value to put into _tab_trib
*/
public void setTab_trib(String tab_trib) {
	_tab_trib = tab_trib;
}

/**
Sets _tdir
@param tdir value to put into _tdir
*/
public void setTdir(String tdir) {
	_tdir = tdir;
}

/**
Sets _ts
@param ts value to put into _ts
*/
public void setTS(String ts) {
	_ts = ts;
}

/**
Sets _tsa
@param tsa value to put into _tsa
*/
public void setTsa(String tsa) {
	_tsa = tsa;
}

/**
Sets _unit
@param unit value to put into _unit
*/
public void setUnit(String unit) {
	_unit = unit;
}

/**
Sets _use
@param use value to put into _use
*/
public void setUse(String use) {
	_use = use;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Sets _wdid
@param wdid value to put into _wdid
*/
public void setWDID(String wdid) {
	_wdid = wdid;
}

/**
Sets _wd_stream_name
@param wd_stream_name value to put into _wd_stream_name
*/
public void setWd_stream_name(String wd_stream_name) {
	_wd_stream_name = wd_stream_name;
}

/**
Sets _wr_stream_no
@param wr_stream_no value to put into _wr_stream_no
*/
public void setWr_stream_no(int wr_stream_no) {
	_wr_stream_no = wr_stream_no;
}

/**
Sets _wr_name
@param wr_name value to put into _wr_name
*/
public void setWr_name(String wr_name) {
	_wr_name = wr_name;
}

/**
Sets _xstrtype
@param xstrtype value to put into _xstrtype
*/
public void setXstrtype(String xstrtype) {
	_xstrtype = xstrtype;
}

/**
Sets _xwr_stream_no
@param xwr_stream_no value to put into _xwr_stream_no
*/
public void setXwr_stream_no(int xwr_stream_no) {
	_xwr_stream_no = xwr_stream_no;
}

// The following "X" data members are used with StateDMI when parcel/well/right/permit
// data from WellsWellToParcel data are converted into rights.  These data members
// allow carrying around the data in NetAmount form.
/**
 * Set the well permit receipt.	
 */
public void setXPermitReceipt ( String xPermitReceipt ) {
	__xPermitReceipt = xPermitReceipt;
}

/**
 * Set the well yield GPM.
 */
public void setXYieldGPM ( double xYieldGPM ) {
	__xYieldGPM = xYieldGPM;
}

/**
 * Set the well yield alternate point/exchange (APEX) GPM
 */
public void setXYieldApexGPM ( double xYieldApexGPM ) {
	__xYieldApexGPM = xYieldApexGPM;
}

/**
 * Set the well permit date.
 */
public void setXPermitDate ( Date xPermitDate ) {
	__xPermitDate = xPermitDate;
}

/**
 * Set the well right appropriation date.
 */
public void setXApproDate ( Date xApproDate ) {
	__xApproDate = xApproDate;
}

/**
 * Set the prorated yield based on parcel area
 */
public void setXProratedYield ( double xProratedYield ) {
	__xProratedYield = xProratedYield;
}

/**
 * Set the fraction of yield (percent_yield in HydroBase)
 */
public void setXFractionYield ( double xFractionYield) {
	__xFractionYield = xFractionYield;
}

/**
 * Set the fraction of yield (percent_yield in HydroBase)
 */
public void setXDitchFraction ( double xDitchFraction) {
	__xDitchFraction = xDitchFraction;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_NetAmts {"		+ "\n" + 
		"Net_num:        " + _net_num + "\n" + 
		"Div:            " + _div + "\n" + 
		"Tab_trib:       " + _tab_trib + "\n" + 
		"WD:             " + _wd + "\n" + 
		"ID:             " + _id + "\n" + 
		"Wr_name:        " + _wr_name + "\n" + 
		"Xwr_stream_no:  " + _xwr_stream_no + "\n" + 
		"Wr_stream_no:   " + _wr_stream_no + "\n" + 
		"Wd_stream_name: " + _wd_stream_name + "\n" + 
		"Xstrtype:       " + _xstrtype + "\n" + 
		"Strtype:        " + _strtype + "\n" + 
		"PM:             " + _pm + "\n" + 
		"Rng:            " + _rng + "\n" + 
		"Rdir:           " + _rdir + "\n" + 
		"Rnga:           " + _rnga + "\n" + 
		"TS:             " + _ts + "\n" + 
		"Tdir:           " + _tdir + "\n" + 
		"Tsa:            " + _tsa + "\n" +
		"Sec:            " + _sec + "\n" + 
		"Seca:           " + _seca + "\n" + 
		"Q160:           " + _q160 + "\n" + 
		"Q40:            " + _q40 + "\n" + 
		"Q10:            " + _q10 + "\n" + 
		"Cty:            " + _cty + "\n" + 
		"Adj_date:       " + _adj_date + "\n" + 
		"Padj_date:      " + _padj_date + "\n" + 
		"Apro_date:      " + _apro_date + "\n" + 
		"Admin_no:       " + _admin_no + "\n" + 
		"Order_no:       " + _order_no + "\n" + 
		"Pri_case_no:    " + _pri_case_no + "\n" + 
		"Adj_type:       " + _adj_type + "\n" + 
		"Use:            " + _use + "\n" + 
		"Net_rate_abs:   " + _net_rate_abs + "\n" + 
		"Net_vol_abs:    " + _net_vol_abs + "\n" + 
		"Net_rate_cond:  " + _net_rate_cond + "\n" + 
		"Net_vol_cond:   " + _net_vol_cond + "\n" + 
		"Net_rate_apex:  " + _net_rate_apex + "\n" + 
		"Net_vol_apex:   " + _net_vol_apex + "\n" + 
		"Abs:            " + _abs + "\n" + 
		"Cond:           " + _cond + "\n" + 
		"Apex:           " + _apex + "\n" + 
		"Net_abs:        " + _net_abs + "\n" + 
		"Net_cond:       " + _net_cond + "\n" + 
		"Net_apex:       " + _net_apex + "\n" + 
		"Unit:           " + _unit + "\n" + 
		"Action_comment: " + _action_comment + "\n" + 
		"Right_num:      " + _right_num + "\n" + 
		"Structure_num:  " + _structure_num + "\n}\n";
}

}
