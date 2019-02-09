// HydroBase_Transact - Class to hold data from the HydroBase transact table.

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
// HydroBase_Transact.java - Class to hold data from the HydroBase 
//	transact table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-13	J. Thomas Sapienza, RTi	Initial version from 
//					HBWaterRightTransaction.
// 2003-02-20	JTS, RTi		Commented out X* data
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2005-02-23	JTS, RTi		Added rdir, rnga, tdir, tsa
// 2005-07-11	JTS, RTi		Added strtype, wr_stream_no.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase transact table.
*/
public class HydroBase_Transact 
extends DMIDataObject {

protected int _trans_num = 		DMIUtil.MISSING_INT;
protected int _div = 			DMIUtil.MISSING_INT;
protected String _tab_trib = 		DMIUtil.MISSING_STRING;
protected int _wd = 			DMIUtil.MISSING_INT;
protected int _id = 			DMIUtil.MISSING_INT;
protected String _wr_name = 		DMIUtil.MISSING_STRING;
protected int _xwr_stream_no = 		DMIUtil.MISSING_INT;
protected int _wr_stream_no = 		DMIUtil.MISSING_INT;
protected String _wd_stream_name = 	DMIUtil.MISSING_STRING;
protected String _xstrtype = 		DMIUtil.MISSING_STRING;
protected String _strtype = 		DMIUtil.MISSING_STRING;
protected String _pm = 			DMIUtil.MISSING_STRING;
protected String _ts = 			DMIUtil.MISSING_STRING;
protected String _tdir = 		DMIUtil.MISSING_STRING;
protected String _tsa = 		DMIUtil.MISSING_STRING;
protected String _rng = 		DMIUtil.MISSING_STRING;
protected String _rdir = 		DMIUtil.MISSING_STRING;
protected String _rnga = 		DMIUtil.MISSING_STRING;
protected int _sec = 			DMIUtil.MISSING_INT;
protected String _seca = 		DMIUtil.MISSING_STRING;
protected String _q160 = 		DMIUtil.MISSING_STRING;
protected String _q40 = 		DMIUtil.MISSING_STRING;
protected String _q10 = 		DMIUtil.MISSING_STRING;
protected int _cty = 			DMIUtil.MISSING_INT;
protected Date _adj_date = 		DMIUtil.MISSING_DATE;
protected Date _padj_date = 		DMIUtil.MISSING_DATE;
protected Date _apro_date = 		DMIUtil.MISSING_DATE;
protected double _admin_no = 		DMIUtil.MISSING_DOUBLE;
protected int _order_no = 		DMIUtil.MISSING_INT;
protected String _adj_type = 		DMIUtil.MISSING_STRING;
protected String _use = 		DMIUtil.MISSING_STRING;
protected double _rate_amt = 		DMIUtil.MISSING_DOUBLE;
protected double _vol_amt = 		DMIUtil.MISSING_DOUBLE;
protected String _status_type = 	DMIUtil.MISSING_STRING;
protected String _assoc_type = 		DMIUtil.MISSING_STRING;
protected String _transfer_type = 	DMIUtil.MISSING_STRING;
protected String _aband = 		DMIUtil.MISSING_STRING;
protected String _aug_role = 		DMIUtil.MISSING_STRING;
protected String _prior_no = 		DMIUtil.MISSING_STRING;
protected String _case_no = 		DMIUtil.MISSING_STRING;
protected String _last_due_dil = 	DMIUtil.MISSING_STRING;
protected String _action_comment = 	DMIUtil.MISSING_STRING;
protected Date _action_update = 	DMIUtil.MISSING_DATE;
protected int _assoc_wd = 		DMIUtil.MISSING_INT;
protected int _assoc_id = 		DMIUtil.MISSING_INT;
protected int _plan_wd = 		DMIUtil.MISSING_INT;
protected int _plan_id = 		DMIUtil.MISSING_INT;
protected int _tran_wd = 		DMIUtil.MISSING_INT;
protected int _tran_id = 		DMIUtil.MISSING_INT;
protected int _right_num = 		DMIUtil.MISSING_INT;
protected int _wr_action_num = 		DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_Transact() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_tab_trib = null;
	_wr_name = null;
	_wd_stream_name = null;
	_xstrtype = null;
	_strtype = null;
	_pm = null;
	_ts = null;
	_tdir = null;
	_tsa = null;
	_rng = null;
	_rdir = null;
	_rnga = null;
	_seca = null;
	_q160 = null;
	_q40 = null;
	_q10 = null;
	_adj_date = null;
	_padj_date = null;
	_apro_date = null;
	_adj_type = null;
	_use = null;
	_status_type = null;
	_assoc_type = null;
	_transfer_type = null;
	_aband = null;
	_aug_role = null;
	_prior_no = null;
	_case_no = null;
	_last_due_dil = null;
	_action_comment = null;
	_action_update = null;
	
	super.finalize();
}

/**
Returns _aband
@return _aband
*/
public String getAband() {
	return _aband;
}

/**
Returns _action_comment
@return _action_comment
*/
public String getAction_comment() {
	return _action_comment;
}

/**
Returns _action_update
@return _action_update
*/
public Date getAction_update() {
	return _action_update;
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
Returns _apro_date
@return _apro_date
*/
public Date getApro_date() {
	return _apro_date;
}

/**
Returns _assoc_id
@return _assoc_id
*/
public int getAssoc_id() {
	return _assoc_id;
}

/**
Returns _assoc_type
@return _assoc_type
*/
public String getAssoc_type() {
	return _assoc_type;
}

/**
Returns _assoc_wd
@return _assoc_wd
*/
public int getAssoc_wd() {
	return _assoc_wd;
}

/**
Returns _aug_role
@return _aug_role
*/
public String getAug_role() {
	return _aug_role;
}

/**
Returns _case_no
@return _case_no
*/
public String getCase_no() {
	return _case_no;
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
Returns _last_due_dil
@return _last_due_dil
*/
public String getLast_due_dil() {
	return _last_due_dil;
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
Returns _plan_id
@return _plan_id
*/
public int getPlan_id() {
	return _plan_id;
}

/**
Returns _plan_wd
@return _plan_wd
*/
public int getPlan_wd() {
	return _plan_wd;
}

/**
Returns _pm
@return _pm
*/
public String getPM() {
	return _pm;
}

/**
Returns _prior_no
@return _prior_no
*/
public String getPrior_no() {
	return _prior_no;
}

/**
Returns _q10
@return _q10
*/
public String getQ10() {
	return _q10;
}

/**
Returns _q40
@return _q40
*/
public String getQ40() {
	return _q40;
}

/**
Returns _q160
@return _q160
*/
public String getQ160() {
	return _q160;
}

/**
Returns _rate_amt
@return _rate_amt
*/
public double getRate_amt() {
	return _rate_amt;
}

/**
Returns _rdir
@return _rdir
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
Returns _status_type
@return _status_type
*/
public String getStatus_type() {
	return _status_type;
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
Returns _tran_id
@return _tran_id
*/
public int getTran_id() {
	return _tran_id;
}

/**
Returns _tran_wd
@return _tran_wd
*/
public int getTran_wd() {
	return _tran_wd;
}

/**
Returns _trans_num
@return _trans_num
*/
protected int getTrans_num() {
	return _trans_num;
}

/**
Returns _transfer_type
@return _transfer_type
*/
protected String getTransfer_type() {
	return _transfer_type;
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
Returns _use
@return _use
*/
public String getUse() {
	return _use;
}

/**
Returns _vol_amt
@return _vol_amt
*/
public double getVol_amt() {
	return _vol_amt;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
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
Returns _wr_action_num
@return _wr_action_num
*/
public int getWr_action_num() {
	return _wr_action_num;
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
Sets _aband
@param aband value to put into _aband
*/
public void setAband(String aband) {
	_aband = aband;
}

/**
Sets _action_comment
@param action_comment value to put into _action_comment
*/
public void setAction_comment(String action_comment) {
	_action_comment = action_comment;
}

/**
Sets _action_update
@param action_update value to put into _action_update
*/
public void setAction_update(Date action_update) {
	_action_update = action_update;
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
Sets _apro_date
@param apro_date value to put into _apro_date
*/
public void setApro_date(Date apro_date) {
	_apro_date = apro_date;
}

/**
Sets _assoc_id
@param assoc_id value to put into _assoc_id
*/
public void setAssoc_id(int assoc_id) {
	_assoc_id = assoc_id;
}

/**
Sets _assoc_type
@param assoc_type value to put into _assoc_type
*/
public void setAssoc_type(String assoc_type) {
	_assoc_type = assoc_type;
}

/**
Sets _assoc_wd
@param assoc_wd value to put into _assoc_wd
*/
public void setAssoc_wd(int assoc_wd) {
	_assoc_wd = assoc_wd;
}

/**
Sets _aug_role
@param aug_role value to put into _aug_role
*/
public void setAug_role(String aug_role) {
	_aug_role = aug_role;
}

/**
Sets _case_no
@param case_no value to put into _case_no
*/
public void setCase_no(String case_no) {
	_case_no = case_no;
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
Sets _last_due_dil
@param last_due_dil value to put into _last_due_dil
*/
public void setLast_due_dil(String last_due_dil) {
	_last_due_dil = last_due_dil;
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
Sets _plan_id
@param plan_id value to put into _plan_id
*/
public void setPlan_id(int plan_id) {
	_plan_id = plan_id;
}

/**
Sets _plan_wd
@param plan_wd value to put into _plan_wd
*/
public void setPlan_wd(int plan_wd) {
	_plan_wd = plan_wd;
}

/**
Sets _pm
@param pm value to put into _pm
*/
public void setPM(String pm) {
	_pm = pm;
}

/**
Sets _prior_no
@param prior_no value to put into _prior_no
*/
public void setPrior_no(String prior_no) {
	_prior_no = prior_no;
}

/**
Sets _q10
@param q10 value to put into _q10
*/
public void setQ10(String q10) {
	_q10 = q10;
}

/**
Sets _q40
@param q40 value to put into _q40
*/
public void setQ40(String q40) {
	_q40 = q40;
}

/**
Sets _q160
@param q160 value to put into _q160
*/
public void setQ160(String q160) {
	_q160 = q160;
}

/**
Sets _rate_amt
@param rate_amt value to put into _rate_amt
*/
public void setRate_amt(double rate_amt) {
	_rate_amt = rate_amt;
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
@param rnga value to put into _rng
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
Sets _status_type
@param status_type value to put into _status_type
*/
public void setStatus_type(String status_type) {
	_status_type = status_type;
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
@param tdir value to put into _tdir;
*/
public void setTdir(String tdir) {
	_tdir = tdir;
}

/**
Sets _tran_id
@param tran_id value to put into _tran_id
*/
public void setTran_id(int tran_id) {
	_tran_id = tran_id;
}

/**
Sets _tran_wd
@param tran_wd value to put into _tran_wd
*/
public void setTran_wd(int tran_wd) {
	_tran_wd = tran_wd;
}

/**
Sets _trans_num
@param trans_num value to put into _trans_num
*/
public void setTrans_num(int trans_num) {
	_trans_num = trans_num;
}

/**
Sets _transfer_type
@param transfer_type value to put into _transfer_type
*/
public void setTransfer_type(String transfer_type) {
	_transfer_type = transfer_type;
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
Sets _use
@param use value to put into _use
*/
public void setUse(String use) {
	_use = use;
}

/**
Sets _vol_amt
@param vol_amt value to put into _vol_amt
*/
public void setVol_amt(double vol_amt) {
	_vol_amt = vol_amt;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
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
Sets _wr_action_num
@param wr_action_num value to put into _wr_action_num
*/
public void setWr_action_num(int wr_action_num) {
	_wr_action_num = wr_action_num;
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

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Transact {"		+ "\n" + 
		"Trans_num:      " + _trans_num + "\n" + 
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
		"TS:             " + _ts + "\n" + 
		"Rng:            " + _rng + "\n" + 
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
		"Adj_type:       " + _adj_type + "\n" + 
		"Use:            " + _use + "\n" + 
		"Rate_amt:       " + _rate_amt + "\n" + 
		"Vol_amt:        " + _vol_amt + "\n" + 
		"Status_type:    " + _status_type + "\n" + 
		"Assoc_type:     " + _assoc_type + "\n" + 
		"Aband:          " + _aband + "\n" + 
		"Aug_role:       " + _aug_role + "\n" + 
		"Prior_no:       " + _prior_no + "\n" + 
		"Case_no:        " + _case_no + "\n" + 
		"Last_due_dil:   " + _last_due_dil + "\n" + 
		"Action_comment: " + _action_comment + "\n" + 
		"Action_update:  " + _action_update + "\n" + 
		"Assoc_wd:       " + _assoc_wd + "\n" + 
		"Assoc_id:       " + _assoc_id + "\n" + 
		"Plan_wd:        " + _plan_wd + "\n" + 
		"Plan_id:        " + _plan_id + "\n" + 
		"Tran_wd:        " + _tran_wd + "\n" + 
		"Tran_id:        " + _tran_id + "\n" + 
		"Right_num:      " + _right_num + "\n" + 
		"Wr_action_num:  " + _wr_action_num + "\n" + 
		"Structure_num:  " + _structure_num + "\n}\n";
}

}
