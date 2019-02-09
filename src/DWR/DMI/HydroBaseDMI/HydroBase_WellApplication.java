// HydroBase_WellApplication - Class to hold data from the HydroBase well_application table.

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
// HydroBase_WellApplication.java - Class to hold data from 
//	the HydroBase well_application table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-13	J. Thomas Sapienza, RTi	Initial version from 
//					HBWellApplication
// 2003-02-20	JTS, RTi		Commented out X* data
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase well_application table.
*/
public class HydroBase_WellApplication 
extends DMIDataObject {

protected int _well_app_num = 		DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected int _gw_controller_num = 	DMIUtil.MISSING_INT;
protected int _geoloc_num = 		DMIUtil.MISSING_INT;
protected int _subdiv_num = 		DMIUtil.MISSING_INT;
protected String _receipt = 		DMIUtil.MISSING_STRING;
protected double _pyield = 		DMIUtil.MISSING_DOUBLE;
protected int _pdepth = 		DMIUtil.MISSING_INT;
protected double _pacreft = 		DMIUtil.MISSING_DOUBLE;
protected String _user = 		DMIUtil.MISSING_STRING;
protected String _case_no = 		DMIUtil.MISSING_STRING;
protected int _elev = 			DMIUtil.MISSING_INT;
protected double _area_irr = 		DMIUtil.MISSING_DOUBLE;
protected String _irr_meas = 		DMIUtil.MISSING_STRING;
protected String _comment = 		DMIUtil.MISSING_STRING;
protected int _wellxno = 		DMIUtil.MISSING_INT;
protected String _wellxsuf = 		DMIUtil.MISSING_STRING;
protected String _wellxrpl = 		DMIUtil.MISSING_STRING;
protected String _well_name = 		DMIUtil.MISSING_STRING;
protected String _subdiv_name = 	DMIUtil.MISSING_STRING;
protected String _filing = 		DMIUtil.MISSING_STRING;
protected String _lot = 		DMIUtil.MISSING_STRING;
protected String _block = 		DMIUtil.MISSING_STRING;
protected double _parcel_size = 	DMIUtil.MISSING_DOUBLE;
protected String _parcel_no = 		DMIUtil.MISSING_STRING;
protected String _aquifer1 = 		DMIUtil.MISSING_STRING;
protected String _aquifer2 = 		DMIUtil.MISSING_STRING;
protected String _use1 = 		DMIUtil.MISSING_STRING;
protected String _use2 = 		DMIUtil.MISSING_STRING;
protected String _use3 = 		DMIUtil.MISSING_STRING;
protected String _driller_lic = 	DMIUtil.MISSING_STRING;
protected String _pump_lic = 		DMIUtil.MISSING_STRING;
protected String _actcode = 		DMIUtil.MISSING_STRING;
protected Date _actdate = 		DMIUtil.MISSING_DATE;
protected String _statcode = 		DMIUtil.MISSING_STRING;
protected Date _statdate = 		DMIUtil.MISSING_DATE;
protected String _trancode = 		DMIUtil.MISSING_STRING;
protected Date _trandate = 		DMIUtil.MISSING_DATE;
protected int _permit_type_num = 	DMIUtil.MISSING_INT;
protected int _permitno = 		DMIUtil.MISSING_INT;
protected String _permitsuf = 		DMIUtil.MISSING_STRING;
protected String _permitrpl = 		DMIUtil.MISSING_STRING;
protected String _engineer = 		DMIUtil.MISSING_STRING;
protected String _statute = 		DMIUtil.MISSING_STRING;
protected int _tperf = 			DMIUtil.MISSING_INT;
protected int _bperf = 			DMIUtil.MISSING_INT;
protected int _abreq = 			DMIUtil.MISSING_INT;
protected int _meter = 			DMIUtil.MISSING_INT;
protected int _log = 			DMIUtil.MISSING_INT;
protected String _md = 			DMIUtil.MISSING_STRING;
protected String _basin = 		DMIUtil.MISSING_STRING;
protected int _valid_permit = 		DMIUtil.MISSING_INT;
protected Date _npdate = 		DMIUtil.MISSING_DATE;
protected Date _exdate = 		DMIUtil.MISSING_DATE;
protected Date _noticedate = 		DMIUtil.MISSING_DATE;
protected double _yield = 		DMIUtil.MISSING_DOUBLE;
protected double _acreft = 		DMIUtil.MISSING_DOUBLE;
protected int _depth = 			DMIUtil.MISSING_INT;
protected int _level = 			DMIUtil.MISSING_INT;
protected int _qual = 			DMIUtil.MISSING_INT;
protected String _well_type = 		DMIUtil.MISSING_STRING;
protected int _valid_struc = 		DMIUtil.MISSING_INT;
protected Date _pidate = 		DMIUtil.MISSING_DATE;
protected Date _wadate = 		DMIUtil.MISSING_DATE;
protected Date _sadate = 		DMIUtil.MISSING_DATE;
protected Date _sbudate = 		DMIUtil.MISSING_DATE;
protected Date _abrdate = 		DMIUtil.MISSING_DATE;
protected Date _abcodate = 		DMIUtil.MISSING_DATE;
protected Date _nwcdate = 		DMIUtil.MISSING_DATE;
protected Date _nbudate = 		DMIUtil.MISSING_DATE;
protected Date _wcdate = 		DMIUtil.MISSING_DATE;
protected Date _pcdate = 		DMIUtil.MISSING_DATE;
//protected String _xref_location = 	DMIUtil.MISSING_STRING;
//protected String _xref_city = 	DMIUtil.MISSING_STRING;
//protected int _xref_cty = 		DMIUtil.MISSING_INT;
//protected String _xref_owner_name = 	DMIUtil.MISSING_STRING;
protected int _div = 			DMIUtil.MISSING_INT;
protected int _wd = 			DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_WellApplication() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_receipt = null;
	_user = null;
	_case_no = null;
	_irr_meas = null;
	_comment = null;
	_wellxsuf = null;
	_wellxrpl = null;
	_well_name = null;
	_subdiv_name = null;
	_filing = null;
	_lot = null;
	_block = null;
	_parcel_no = null;
	_aquifer1 = null;
	_aquifer2 = null;
	_use1 = null;
	_use2 = null;
	_use3 = null;
	_driller_lic = null;
	_pump_lic = null;
	_actcode = null;
	_actdate = null;
	_statcode = null;
	_statdate = null;
	_trancode = null;
	_trandate = null;
	_permitsuf = null;
	_permitrpl = null;
	_engineer = null;
	_statute = null;
	_md = null;
	_basin = null;
	_npdate = null;
	_exdate = null;
	_noticedate = null;
	_well_type = null;
	_pidate = null;
	_wadate = null;
	_sadate = null;
	_sbudate = null;
	_abrdate = null;
	_abcodate = null;
	_nwcdate = null;
	_nbudate = null;
	_wcdate = null;
	_pcdate = null;
//	_xref_location = null;
//	_xref_city = null;
//	_xref_owner_name = null;

	super.finalize();
}

/**
Returns _abcodate
@return _abcodate
*/
public Date getAbcodate() {
	return _abcodate;
}

/**
Returns _abrdate
@return _abrdate
*/
public Date getAbrdate() {
	return _abrdate;
}

/**
Returns _abreq
@return _abreq
*/
public int getAbreq() {
	return _abreq;
}

/**
Returns _acreft
@return _acreft
*/
public double getAcreft() {
	return _acreft;
}

/**
Returns _actcode
@return _actcode
*/
public String getActcode() {
	return _actcode;
}

/**
Returns _actdate
@return _actdate
*/
public Date getActdate() {
	return _actdate;
}

/**
Returns _aquifer1
@return _aquifer1
*/
public String getAquifer1() {
	return _aquifer1;
}

/**
Returns _aquifer2
@return _aquifer2
*/
public String getAquifer2() {
	return _aquifer2;
}

/**
Returns _area_irr
@return _area_irr
*/
public double getArea_irr() {
	return _area_irr;
}

/**
Returns _basin
@return _basin
*/
public String getBasin() {
	return _basin;
}

/**
Returns _block
@return _block
*/
public String getBlock() {
	return _block;
}

/**
Returns _bperf
@return _bperf
*/
public int getBperf() {
	return _bperf;
}

/**
Returns _case_no
@return _case_no
*/
public String getCase_no() {
	return _case_no;
}

/**
Returns _comment
@return _comment
*/
public String getComment() {
	return _comment;
}

/**
Returns _depth
@return _depth
*/
public int getDepth() {
	return _depth;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _driller_lic
@return _driller_lic
*/
public String getDriller_lic() {
	return _driller_lic;
}

/**
Returns _elev
@return _elev
*/
public int getElev() {
	return _elev;
}

/**
Returns _engineer
@return _engineer
*/
public String getEngineer() {
	return _engineer;
}

/**
Returns _exdate
@return _exdate
*/
public Date getExdate() {
	return _exdate;
}

/**
Returns _filing
@return _filing
*/
public String getFiling() {
	return _filing;
}

/**
Returns _geoloc_num
@return _geoloc_num
*/
public int getGeoloc_num() {
	return _geoloc_num;
}

/**
Returns _gw_controller_num
@return _gw_controller_num
*/
public int getGw_controller_num() {
	return _gw_controller_num;
}

/**
Returns _irr_meas
@return _irr_meas
*/
public String getIrr_meas() {
	return _irr_meas;
}

/**
Returns _level
@return _level
*/
public int getLevel() {
	return _level;
}

/**
Returns _log
@return _log
*/
public int getLog() {
	return _log;
}

/**
Returns _lot
@return _lot
*/
public String getLot() {
	return _lot;
}

/**
Returns _md
@return _md
*/
public String getMD() {
	return _md;
}

/**
Returns _meter
@return _meter
*/
public int getMeter() {
	return _meter;
}

/**
Returns _nbudate
@return _nbudate
*/
public Date getNbudate() {
	return _nbudate;
}

/**
Returns _noticedate
@return _noticedate
*/
public Date getNoticedate() {
	return _noticedate;
}

/**
Returns _npdate
@return _npdate
*/
public Date getNpdate() {
	return _npdate;
}

/**
Returns _nwcdate
@return _nwcdate
*/
public Date getNwcdate() {
	return _nwcdate;
}

/**
Returns _pacreft
@return _pacreft
*/
public double getPacreft() {
	return _pacreft;
}

/**
Returns _parcel_no
@return _parcel_no
*/
public String getParcel_no() {
	return _parcel_no;
}

/**
Returns _parcel_size
@return _parcel_size
*/
public double getParcel_size() {
	return _parcel_size;
}

/**
Returns _pcdate
@return _pcdate
*/
public Date getPcdate() {
	return _pcdate;
}

/**
Returns _pdepth
@return _pdepth
*/
public int getPdepth() {
	return _pdepth;
}

/**
Returns _permit_type_num
@return _permit_type_num
*/
public int getPermit_type_num() {
	return _permit_type_num;
}

/**
Returns _permitno
@return _permitno
*/
public int getPermitno() {
	return _permitno;
}

/**
Returns _permitsuf
@return _permitsuf
*/
public String getPermitsuf() {
	return _permitsuf;
}

/**
Returns _permitrpl
@return _permitrpl
*/
public String getPermitrpl() {
	return _permitrpl;
}

/**
Returns _pidate
@return _pidate
*/
public Date getPidate() {
	return _pidate;
}

/**
Returns _pump_lic
@return _pump_lic
*/
public String getPump_lic() {
	return _pump_lic;
}

/**
Returns _pyield
@return _pyield
*/
public double getPyield() {
	return _pyield;
}

/**
Returns _qual
@return _qual
*/
public int getQual() {
	return _qual;
}

/**
Returns _receipt
@return _receipt
*/
public String getReceipt() {
	return _receipt;
}

/**
Returns _sadate
@return _sadate
*/
public Date getSadate() {
	return _sadate;
}

/**
Returns _sbudate
@return _sbudate
*/
public Date getSbudate() {
	return _sbudate;
}

/**
Returns _statcode
@return _statcode
*/
public String getStatcode() {
	return _statcode;
}

/**
Returns _statdate
@return _statdate
*/
public Date getStatdate() {
	return _statdate;
}

/**
Returns _statute
@return _statute
*/
public String getStatute() {
	return _statute;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _subdiv_name
@return _subdiv_name
*/
public String getSubdiv_name() {
	return _subdiv_name;
}

/**
Returns _subdiv_num
@return _subdiv_num
*/
public int getSubdiv_num() {
	return _subdiv_num;
}

/**
Returns _tperf
@return _tperf
*/
public int getTperf() {
	return _tperf;
}

/**
Returns _trancode
@return _trancode
*/
public String getTrancode() {
	return _trancode;
}

/**
Returns _trandate
@return _trandate
*/
public Date getTrandate() {
	return _trandate;
}

/**
Returns _use1
@return _use1
*/
public String getUse1() {
	return _use1;
}

/**
Returns _use2
@return _use2
*/
public String getUse2() {
	return _use2;
}

/**
Returns _use3
@return _use3
*/
public String getUse3() {
	return _use3;
}

/**
Returns _user
@return _user
*/
public String getUser() {
	return _user;
}

/**
Returns _valid_permit
@return _valid_permit
*/
public int getValid_permit() {
	return _valid_permit;
}

/**
Returns _valid_struc
@return _valid_struc
*/
public int getValid_struc() {
	return _valid_struc;
}

/**
Returns _wadate
@return _wadate
*/
public Date getWadate() {
	return _wadate;
}

/**
Returns _wcdate
@return _wcdate
*/
public Date getWcdate() {
	return _wcdate;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Returns _well_app_num
@return _well_app_num
*/
public int getWell_app_num() {
	return _well_app_num;
}

/**
Returns _well_name
@return _well_name
*/
public String getWell_name() {
	return _well_name;
}

/**
Returns _well_type
@return _well_type
*/
public String getWell_type() {
	return _well_type;
}

/**
Returns _wellxno
@return _wellxno
*/
public int getWellxno() {
	return _wellxno;
}

/**
Returns _wellxrpl
@return _wellxrpl
*/
public String getWellxrpl() {
	return _wellxrpl;
}

/**
Returns _wellxsuf
@return _wellxsuf
*/
public String getWellxsuf() {
	return _wellxsuf;
}

/**
Returns _xref_city
@return _xref_city
*/
//public String getXref_city() {
//	return _xref_city;
//}

/**
Returns _xref_cty
@return _xref_cty
*/
//public int getXref_cty() {
//	return _xref_cty;
//}

/**
Returns _xref_location
@return _xref_location
*/
//public String getXref_location() {
//	return _xref_location;
//}

/**
Returns _xref_owner_name
@return _xref_owner_name
*/
//public String getXref_owner_name() {
//	return _xref_owner_name;
//}

/**
Returns _yield
@return _yield
*/
public double getYield() {
	return _yield;
}

/**
Sets _abcodate
@param abcodate value to put into _abcodate
*/
public void setAbcodate(Date abcodate) {
	_abcodate = abcodate;
}

/**
Sets _abrdate
@param abrdate value to put into _abrdate
*/
public void setAbrdate(Date abrdate) {
	_abrdate = abrdate;
}

/**
Sets _abreq
@param abreq value to put into _abreq
*/
public void setAbreq(int abreq) {
	_abreq = abreq;
}

/**
Sets _acreft
@param acreft value to put into _acreft
*/
public void setAcreft(double acreft) {
	_acreft = acreft;
}

/**
Sets _actcode
@param actcode value to put into _actcode
*/
public void setActcode(String actcode) {
	_actcode = actcode;
}

/**
Sets _actdate
@param actdate value to put into _actdate
*/
public void setActdate(Date actdate) {
	_actdate = actdate;
}

/**
Sets _aquifer1
@param aquifer1 value to put into _aquifer1
*/
public void setAquifer1(String aquifer1) {
	_aquifer1 = aquifer1;
}

/**
Sets _aquifer2
@param aquifer2 value to put into _aquifer2
*/
public void setAquifer2(String aquifer2) {
	_aquifer2 = aquifer2;
}

/**
Sets _area_irr
@param area_irr value to put into _area_irr
*/
public void setArea_irr(double area_irr) {
	_area_irr = area_irr;
}

/**
Sets _basin
@param basin value to put into _basin
*/
public void setBasin(String basin) {
	_basin = basin;
}

/**
Sets _block
@param block value to put into _block
*/
public void setBlock(String block) {
	_block = block;
}

/**
Sets _bperf
@param bperf value to put into _bperf
*/
public void setBperf(int bperf) {
	_bperf = bperf;
}

/**
Sets _case_no
@param case_no value to put into _case_no
*/
public void setCase_no(String case_no) {
	_case_no = case_no;
}

/**
Sets _comment
@param comment value to put into _comment
*/
public void setComment(String comment) {
	_comment = comment;
}

/**
Sets _depth
@param depth value to put into _depth
*/
public void setDepth(int depth) {
	_depth = depth;
}

/**
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _driller_lic
@param driller_lic value to put into _driller_lic
*/
public void setDriller_lic(String driller_lic) {
	_driller_lic = driller_lic;
}

/**
Sets _elev
@param elev value to put into _elev
*/
public void setElev(int elev) {
	_elev = elev;
}

/**
Sets _engineer
@param engineer value to put into _engineer
*/
public void setEngineer(String engineer) {
	_engineer = engineer;
}

/**
Sets _exdate
@param exdate value to put into _exdate
*/
public void setExdate(Date exdate) {
	_exdate = exdate;
}

/**
Sets _filing
@param filing value to put into _filing
*/
public void setFiling(String filing) {
	_filing = filing;
}

/**
Sets _geoloc_num
@param geoloc_num value to put into _geoloc_num
*/
public void setGeoloc_num(int geoloc_num) {
	_geoloc_num = geoloc_num;
}

/**
Sets _gw_controller_num
@param gw_controller_num value to put into _gw_controller_num
*/
public void setGw_controller_num(int gw_controller_num) {
	_gw_controller_num = gw_controller_num;
}

/**
Sets _irr_meas
@param irr_meas value to put into _irr_meas
*/
public void setIrr_meas(String irr_meas) {
	_irr_meas = irr_meas;
}

/**
Sets _level
@param level value to put into _level
*/
public void setLevel(int level) {
	_level = level;
}

/**
Sets _log
@param log value to put into _log
*/
public void setLog(int log) {
	_log = log;
}

/**
Sets _lot
@param lot value to put into _lot
*/
public void setLot(String lot) {
	_lot = lot;
}

/**
Sets _md
@param md value to put into _md
*/
public void setMD(String md) {
	_md = md;
}

/**
Sets _meter
@param meter value to put into _meter
*/
public void setMeter(int meter) {
	_meter = meter;
}

/**
Sets _nbudate
@param nbudate value to put into _nbudate
*/
public void setNbudate(Date nbudate) {
	_nbudate = nbudate;
}

/**
Sets _noticedate
@param noticedate value to put into _noticedate
*/
public void setNoticedate(Date noticedate) {
	_noticedate = noticedate;
}

/**
Sets _npdate
@param npdate value to put into _npdate
*/
public void setNpdate(Date npdate) {
	_npdate = npdate;
}

/**
Sets _nwcdate
@param nwcdate value to put into _nwcdate
*/
public void setNwcdate(Date nwcdate) {
	_nwcdate = nwcdate;
}

/**
Sets _pacreft
@param pacreft value to put into _pacreft
*/
public void setPacreft(double pacreft) {
	_pacreft = pacreft;
}

/**
Sets _parcel_no
@param parcel_no value to put into _parcel_no
*/
public void setParcel_no(String parcel_no) {
	_parcel_no = parcel_no;
}

/**
Sets _parcel_size
@param parcel_size value to put into _parcel_size
*/
public void setParcel_size(double parcel_size) {
	_parcel_size = parcel_size;
}

/**
Sets _pcdate
@param pcdate value to put into _pcdate
*/
public void setPcdate(Date pcdate) {
	_pcdate = pcdate;
}

/**
Sets _pdepth
@param pdepth value to put into _pdepth
*/
public void setPdepth(int pdepth) {
	_pdepth = pdepth;
}

/**
Sets _permit_type_num
@param permit_type_num value to put into _permit_type_num
*/
public void setPermit_type_num(int permit_type_num) {
	_permit_type_num = permit_type_num;
}

/**
Sets _permitno
@param permitno value to put into _permitno
*/
public void setPermitno(int permitno) {
	_permitno = permitno;
}

/**
Sets _permitsuf
@param permitsuf value to put into _permitsuf
*/
public void setPermitsuf(String permitsuf) {
	_permitsuf = permitsuf;
}

/**
Sets _permitrpl
@param permitrpl value to put into _permitrpl
*/
public void setPermitrpl(String permitrpl) {
	_permitrpl = permitrpl;
}

/**
Sets _pidate
@param pidate value to put into _pidate
*/
public void setPidate(Date pidate) {
	_pidate = pidate;
}

/**
Sets _pump_lic
@param pump_lic value to put into _pump_lic
*/
public void setPump_lic(String pump_lic) {
	_pump_lic = pump_lic;
}

/**
Sets _pyield
@param pyield value to put into _pyield
*/
public void setPyield(double pyield) {
	_pyield = pyield;
}

/**
Sets _qual
@param qual value to put into _qual
*/
public void setQual(int qual) {
	_qual = qual;
}

/**
Sets _receipt
@param receipt value to put into _receipt
*/
public void setReceipt(String receipt) {
	_receipt = receipt;
}

/**
Sets _sadate
@param sadate value to put into _sadate
*/
public void setSadate(Date sadate) {
	_sadate = sadate;
}

/**
Sets _sbudate
@param sbudate value to put into _sbudate
*/
public void setSbudate(Date sbudate) {
	_sbudate = sbudate;
}

/**
Sets _statcode
@param statcode value to put into _statcode
*/
public void setStatcode(String statcode) {
	_statcode = statcode;
}

/**
Sets _statdate
@param statdate value to put into _statdate
*/
public void setStatdate(Date statdate) {
	_statdate = statdate;
}

/**
Sets _statute
@param statute value to put into _statute
*/
public void setStatute(String statute) {
	_statute = statute;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _subdiv_name
@param subdiv_name value to put into _subdiv_name
*/
public void setSubdiv_name(String subdiv_name) {
	_subdiv_name = subdiv_name;
}

/**
Sets _subdiv_num
@param subdiv_num value to put into _subdiv_num
*/
public void setSubdiv_num(int subdiv_num) {
	_subdiv_num = subdiv_num;
}

/**
Sets _tperf
@param tperf value to put into _tperf
*/
public void setTperf(int tperf) {
	_tperf = tperf;
}

/**
Sets _trancode
@param trancode value to put into _trancode
*/
public void setTrancode(String trancode) {
	_trancode = trancode;
}

/**
Sets _trandate
@param trandate value to put into _trandate
*/
public void setTrandate(Date trandate) {
	_trandate = trandate;
}

/**
Sets _use1
@param use1 value to put into _use1
*/
public void setUse1(String use1) {
	_use1 = use1;
}

/**
Sets _use2
@param use2 value to put into _use2
*/
public void setUse2(String use2) {
	_use2 = use2;
}

/**
Sets _use3
@param use3 value to put into _use3
*/
public void setUse3(String use3) {
	_use3 = use3;
}

/**
Sets _user
@param user value to put into _user
*/
public void setUser(String user) {
	_user = user;
}

/**
Sets _valid_permit
@param valid_permit value to put into _valid_permit
*/
public void setValid_permit(int valid_permit) {
	_valid_permit = valid_permit;
}

/**
Sets _valid_struc
@param valid_struc value to put into _valid_struc
*/
public void setValid_struc(int valid_struc) {
	_valid_struc = valid_struc;
}

/**
Sets _wadate
@param wadate value to put into _wadate
*/
public void setWadate(Date wadate) {
	_wadate = wadate;
}

/**
Sets _wcdate
@param wcdate value to put into _wcdate
*/
public void setWcdate(Date wcdate) {
	_wcdate = wcdate;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Sets _well_app_num
@param well_app_num value to put into _well_app_num
*/
public void setWell_app_num(int well_app_num) {
	_well_app_num = well_app_num;
}

/**
Sets _well_name
@param well_name value to put into _well_name
*/
public void setWell_name(String well_name) {
	_well_name = well_name;
}

/**
Sets _well_type
@param well_type value to put into _well_type
*/
public void setWell_type(String well_type) {
	_well_type = well_type;
}

/**
Sets _wellxno
@param wellxno value to put into _wellxno
*/
public void setWellxno(int wellxno) {
	_wellxno = wellxno;
}

/**
Sets _wellxrpl
@param wellxrpl value to put into _wellxrpl
*/
public void setWellxrpl(String wellxrpl) {
	_wellxrpl = wellxrpl;
}

/**
Sets _wellxsuf
@param wellxsuf value to put into _wellxsuf
*/
public void setWellxsuf(String wellxsuf) {
	_wellxsuf = wellxsuf;
}

/**
Sets _xref_city
@param xref_city value to put into _xref_city
*/
//public void setXref_city(String xref_city) {
//	_xref_city = xref_city;
//}

/**
Sets _xref_cty
@param xref_cty value to put into _xref_cty
*/
//public void setXref_cty(int xref_cty) {
//	_xref_cty = xref_cty;
//}

/**
Sets _xref_location
@param xref_location value to put into _xref_location
*/
//public void setXref_location(String xref_location) {
//	_xref_location = xref_location;
//}

/**
Sets _xref_owner_name
@param xref_owner_name value to put into _xref_owner_name
*/
//public void setXref_owner_name(String xref_owner_name) {
//	_xref_owner_name = xref_owner_name;
//}

/**
Sets _yield
@param yield value to put into _yield
*/
public void setYield(double yield) {
	_yield = yield;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
//	return "HydroBase_WellApplication {"		+ "\n" + 
	return "";
}

}
