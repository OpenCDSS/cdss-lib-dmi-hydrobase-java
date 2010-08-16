
package us.co.state.dwr.hbguest;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Structure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Structure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="div" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="wd" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="wdid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ciu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ciu_description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="str_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transbsn" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="xtia" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="str_type_desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UTM_x" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="UTM_y" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="latdecdeg" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="longdecdeg" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="pm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ts" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="tdir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rng" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="rdir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rnga" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sec" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="seca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="q160" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="q40" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="q10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coordsns" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="coordsns_dir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coordsew" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="coordsew_dir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accuracy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="county" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="topomap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="huc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="elev" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="loc_type_desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="str_mile" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="strno" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="strname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strtribto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="full_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="address1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="address2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="st" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tia_gis" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="tia_gis_calyear" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="tia_div" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="tia_div_calyear" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="tia_struct" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="tia_struct_calyear" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="dcr_rate_abs" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dcr_rate_cond" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dcr_rate_APEX_abs" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dcr_rate_APEX_cond" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dcr_vol_abs" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dcr_vol_cond" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dcr_vol_APEX_abs" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dcr_vol_APEX_cond" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dcr_rate_total" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dcr_vol_total" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Structure", propOrder = {
    "div",
    "wd",
    "id",
    "wdid",
    "ciu",
    "ciuDescription",
    "strtype",
    "strName",
    "transbsn",
    "xtia",
    "strTypeDesc",
    "utmx",
    "utmy",
    "latdecdeg",
    "longdecdeg",
    "pm",
    "ts",
    "tdir",
    "tsa",
    "rng",
    "rdir",
    "rnga",
    "sec",
    "seca",
    "q160",
    "q40",
    "q10",
    "coordsns",
    "coordsnsDir",
    "coordsew",
    "coordsewDir",
    "accuracy",
    "county",
    "topomap",
    "huc",
    "elev",
    "locTypeDesc",
    "strMile",
    "strno",
    "strname",
    "strtribto",
    "fullName",
    "address1",
    "address2",
    "city",
    "st",
    "zip",
    "tiaGis",
    "tiaGisCalyear",
    "tiaDiv",
    "tiaDivCalyear",
    "tiaStruct",
    "tiaStructCalyear",
    "dcrRateAbs",
    "dcrRateCond",
    "dcrRateAPEXAbs",
    "dcrRateAPEXCond",
    "dcrVolAbs",
    "dcrVolCond",
    "dcrVolAPEXAbs",
    "dcrVolAPEXCond",
    "dcrRateTotal",
    "dcrVolTotal"
})
@XmlSeeAlso({
    StructureGeolocMeasType.class
})
public class Structure {

    protected short div;
    protected short wd;
    protected int id;
    protected String wdid;
    protected String ciu;
    @XmlElement(name = "ciu_description")
    protected String ciuDescription;
    protected String strtype;
    @XmlElement(name = "str_name")
    protected String strName;
    protected short transbsn;
    @XmlElement(required = true)
    protected BigDecimal xtia;
    @XmlElement(name = "str_type_desc")
    protected String strTypeDesc;
    @XmlElement(name = "UTM_x", required = true)
    protected BigDecimal utmx;
    @XmlElement(name = "UTM_y", required = true)
    protected BigDecimal utmy;
    @XmlElement(required = true)
    protected BigDecimal latdecdeg;
    @XmlElement(required = true)
    protected BigDecimal longdecdeg;
    protected String pm;
    protected short ts;
    protected String tdir;
    protected String tsa;
    protected short rng;
    protected String rdir;
    protected String rnga;
    protected short sec;
    protected String seca;
    protected String q160;
    protected String q40;
    protected String q10;
    protected short coordsns;
    @XmlElement(name = "coordsns_dir")
    protected String coordsnsDir;
    protected short coordsew;
    @XmlElement(name = "coordsew_dir")
    protected String coordsewDir;
    protected String accuracy;
    protected String county;
    protected String topomap;
    protected String huc;
    @XmlElement(required = true)
    protected BigDecimal elev;
    @XmlElement(name = "loc_type_desc")
    protected String locTypeDesc;
    @XmlElement(name = "str_mile", required = true)
    protected BigDecimal strMile;
    protected short strno;
    protected String strname;
    protected int strtribto;
    @XmlElement(name = "full_name")
    protected String fullName;
    protected String address1;
    protected String address2;
    protected String city;
    protected String st;
    protected String zip;
    @XmlElement(name = "tia_gis", required = true)
    protected BigDecimal tiaGis;
    @XmlElement(name = "tia_gis_calyear")
    protected short tiaGisCalyear;
    @XmlElement(name = "tia_div", required = true)
    protected BigDecimal tiaDiv;
    @XmlElement(name = "tia_div_calyear")
    protected short tiaDivCalyear;
    @XmlElement(name = "tia_struct", required = true)
    protected BigDecimal tiaStruct;
    @XmlElement(name = "tia_struct_calyear")
    protected short tiaStructCalyear;
    @XmlElement(name = "dcr_rate_abs", required = true)
    protected BigDecimal dcrRateAbs;
    @XmlElement(name = "dcr_rate_cond", required = true)
    protected BigDecimal dcrRateCond;
    @XmlElement(name = "dcr_rate_APEX_abs", required = true)
    protected BigDecimal dcrRateAPEXAbs;
    @XmlElement(name = "dcr_rate_APEX_cond", required = true)
    protected BigDecimal dcrRateAPEXCond;
    @XmlElement(name = "dcr_vol_abs", required = true)
    protected BigDecimal dcrVolAbs;
    @XmlElement(name = "dcr_vol_cond", required = true)
    protected BigDecimal dcrVolCond;
    @XmlElement(name = "dcr_vol_APEX_abs", required = true)
    protected BigDecimal dcrVolAPEXAbs;
    @XmlElement(name = "dcr_vol_APEX_cond", required = true)
    protected BigDecimal dcrVolAPEXCond;
    @XmlElement(name = "dcr_rate_total", required = true)
    protected BigDecimal dcrRateTotal;
    @XmlElement(name = "dcr_vol_total", required = true)
    protected BigDecimal dcrVolTotal;

    /**
     * Gets the value of the div property.
     * 
     */
    public short getDiv() {
        return div;
    }

    /**
     * Sets the value of the div property.
     * 
     */
    public void setDiv(short value) {
        this.div = value;
    }

    /**
     * Gets the value of the wd property.
     * 
     */
    public short getWd() {
        return wd;
    }

    /**
     * Sets the value of the wd property.
     * 
     */
    public void setWd(short value) {
        this.wd = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the wdid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWdid() {
        return wdid;
    }

    /**
     * Sets the value of the wdid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWdid(String value) {
        this.wdid = value;
    }

    /**
     * Gets the value of the ciu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCiu() {
        return ciu;
    }

    /**
     * Sets the value of the ciu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCiu(String value) {
        this.ciu = value;
    }

    /**
     * Gets the value of the ciuDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCiuDescription() {
        return ciuDescription;
    }

    /**
     * Sets the value of the ciuDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCiuDescription(String value) {
        this.ciuDescription = value;
    }

    /**
     * Gets the value of the strtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrtype() {
        return strtype;
    }

    /**
     * Sets the value of the strtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrtype(String value) {
        this.strtype = value;
    }

    /**
     * Gets the value of the strName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrName() {
        return strName;
    }

    /**
     * Sets the value of the strName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrName(String value) {
        this.strName = value;
    }

    /**
     * Gets the value of the transbsn property.
     * 
     */
    public short getTransbsn() {
        return transbsn;
    }

    /**
     * Sets the value of the transbsn property.
     * 
     */
    public void setTransbsn(short value) {
        this.transbsn = value;
    }

    /**
     * Gets the value of the xtia property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getXtia() {
        return xtia;
    }

    /**
     * Sets the value of the xtia property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setXtia(BigDecimal value) {
        this.xtia = value;
    }

    /**
     * Gets the value of the strTypeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrTypeDesc() {
        return strTypeDesc;
    }

    /**
     * Sets the value of the strTypeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrTypeDesc(String value) {
        this.strTypeDesc = value;
    }

    /**
     * Gets the value of the utmx property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUTMX() {
        return utmx;
    }

    /**
     * Sets the value of the utmx property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUTMX(BigDecimal value) {
        this.utmx = value;
    }

    /**
     * Gets the value of the utmy property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUTMY() {
        return utmy;
    }

    /**
     * Sets the value of the utmy property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUTMY(BigDecimal value) {
        this.utmy = value;
    }

    /**
     * Gets the value of the latdecdeg property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLatdecdeg() {
        return latdecdeg;
    }

    /**
     * Sets the value of the latdecdeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLatdecdeg(BigDecimal value) {
        this.latdecdeg = value;
    }

    /**
     * Gets the value of the longdecdeg property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLongdecdeg() {
        return longdecdeg;
    }

    /**
     * Sets the value of the longdecdeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLongdecdeg(BigDecimal value) {
        this.longdecdeg = value;
    }

    /**
     * Gets the value of the pm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPm() {
        return pm;
    }

    /**
     * Sets the value of the pm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPm(String value) {
        this.pm = value;
    }

    /**
     * Gets the value of the ts property.
     * 
     */
    public short getTs() {
        return ts;
    }

    /**
     * Sets the value of the ts property.
     * 
     */
    public void setTs(short value) {
        this.ts = value;
    }

    /**
     * Gets the value of the tdir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTdir() {
        return tdir;
    }

    /**
     * Sets the value of the tdir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTdir(String value) {
        this.tdir = value;
    }

    /**
     * Gets the value of the tsa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsa() {
        return tsa;
    }

    /**
     * Sets the value of the tsa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsa(String value) {
        this.tsa = value;
    }

    /**
     * Gets the value of the rng property.
     * 
     */
    public short getRng() {
        return rng;
    }

    /**
     * Sets the value of the rng property.
     * 
     */
    public void setRng(short value) {
        this.rng = value;
    }

    /**
     * Gets the value of the rdir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRdir() {
        return rdir;
    }

    /**
     * Sets the value of the rdir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRdir(String value) {
        this.rdir = value;
    }

    /**
     * Gets the value of the rnga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRnga() {
        return rnga;
    }

    /**
     * Sets the value of the rnga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRnga(String value) {
        this.rnga = value;
    }

    /**
     * Gets the value of the sec property.
     * 
     */
    public short getSec() {
        return sec;
    }

    /**
     * Sets the value of the sec property.
     * 
     */
    public void setSec(short value) {
        this.sec = value;
    }

    /**
     * Gets the value of the seca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeca() {
        return seca;
    }

    /**
     * Sets the value of the seca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeca(String value) {
        this.seca = value;
    }

    /**
     * Gets the value of the q160 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQ160() {
        return q160;
    }

    /**
     * Sets the value of the q160 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQ160(String value) {
        this.q160 = value;
    }

    /**
     * Gets the value of the q40 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQ40() {
        return q40;
    }

    /**
     * Sets the value of the q40 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQ40(String value) {
        this.q40 = value;
    }

    /**
     * Gets the value of the q10 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQ10() {
        return q10;
    }

    /**
     * Sets the value of the q10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQ10(String value) {
        this.q10 = value;
    }

    /**
     * Gets the value of the coordsns property.
     * 
     */
    public short getCoordsns() {
        return coordsns;
    }

    /**
     * Sets the value of the coordsns property.
     * 
     */
    public void setCoordsns(short value) {
        this.coordsns = value;
    }

    /**
     * Gets the value of the coordsnsDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoordsnsDir() {
        return coordsnsDir;
    }

    /**
     * Sets the value of the coordsnsDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoordsnsDir(String value) {
        this.coordsnsDir = value;
    }

    /**
     * Gets the value of the coordsew property.
     * 
     */
    public short getCoordsew() {
        return coordsew;
    }

    /**
     * Sets the value of the coordsew property.
     * 
     */
    public void setCoordsew(short value) {
        this.coordsew = value;
    }

    /**
     * Gets the value of the coordsewDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoordsewDir() {
        return coordsewDir;
    }

    /**
     * Sets the value of the coordsewDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoordsewDir(String value) {
        this.coordsewDir = value;
    }

    /**
     * Gets the value of the accuracy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the value of the accuracy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccuracy(String value) {
        this.accuracy = value;
    }

    /**
     * Gets the value of the county property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCounty() {
        return county;
    }

    /**
     * Sets the value of the county property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCounty(String value) {
        this.county = value;
    }

    /**
     * Gets the value of the topomap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTopomap() {
        return topomap;
    }

    /**
     * Sets the value of the topomap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTopomap(String value) {
        this.topomap = value;
    }

    /**
     * Gets the value of the huc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHuc() {
        return huc;
    }

    /**
     * Sets the value of the huc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHuc(String value) {
        this.huc = value;
    }

    /**
     * Gets the value of the elev property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getElev() {
        return elev;
    }

    /**
     * Sets the value of the elev property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setElev(BigDecimal value) {
        this.elev = value;
    }

    /**
     * Gets the value of the locTypeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocTypeDesc() {
        return locTypeDesc;
    }

    /**
     * Sets the value of the locTypeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocTypeDesc(String value) {
        this.locTypeDesc = value;
    }

    /**
     * Gets the value of the strMile property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStrMile() {
        return strMile;
    }

    /**
     * Sets the value of the strMile property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStrMile(BigDecimal value) {
        this.strMile = value;
    }

    /**
     * Gets the value of the strno property.
     * 
     */
    public short getStrno() {
        return strno;
    }

    /**
     * Sets the value of the strno property.
     * 
     */
    public void setStrno(short value) {
        this.strno = value;
    }

    /**
     * Gets the value of the strname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrname() {
        return strname;
    }

    /**
     * Sets the value of the strname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrname(String value) {
        this.strname = value;
    }

    /**
     * Gets the value of the strtribto property.
     * 
     */
    public int getStrtribto() {
        return strtribto;
    }

    /**
     * Sets the value of the strtribto property.
     * 
     */
    public void setStrtribto(int value) {
        this.strtribto = value;
    }

    /**
     * Gets the value of the fullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the value of the fullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullName(String value) {
        this.fullName = value;
    }

    /**
     * Gets the value of the address1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the value of the address1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress1(String value) {
        this.address1 = value;
    }

    /**
     * Gets the value of the address2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the value of the address2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress2(String value) {
        this.address2 = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the st property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSt() {
        return st;
    }

    /**
     * Sets the value of the st property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSt(String value) {
        this.st = value;
    }

    /**
     * Gets the value of the zip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the value of the zip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * Gets the value of the tiaGis property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTiaGis() {
        return tiaGis;
    }

    /**
     * Sets the value of the tiaGis property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTiaGis(BigDecimal value) {
        this.tiaGis = value;
    }

    /**
     * Gets the value of the tiaGisCalyear property.
     * 
     */
    public short getTiaGisCalyear() {
        return tiaGisCalyear;
    }

    /**
     * Sets the value of the tiaGisCalyear property.
     * 
     */
    public void setTiaGisCalyear(short value) {
        this.tiaGisCalyear = value;
    }

    /**
     * Gets the value of the tiaDiv property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTiaDiv() {
        return tiaDiv;
    }

    /**
     * Sets the value of the tiaDiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTiaDiv(BigDecimal value) {
        this.tiaDiv = value;
    }

    /**
     * Gets the value of the tiaDivCalyear property.
     * 
     */
    public short getTiaDivCalyear() {
        return tiaDivCalyear;
    }

    /**
     * Sets the value of the tiaDivCalyear property.
     * 
     */
    public void setTiaDivCalyear(short value) {
        this.tiaDivCalyear = value;
    }

    /**
     * Gets the value of the tiaStruct property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTiaStruct() {
        return tiaStruct;
    }

    /**
     * Sets the value of the tiaStruct property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTiaStruct(BigDecimal value) {
        this.tiaStruct = value;
    }

    /**
     * Gets the value of the tiaStructCalyear property.
     * 
     */
    public short getTiaStructCalyear() {
        return tiaStructCalyear;
    }

    /**
     * Sets the value of the tiaStructCalyear property.
     * 
     */
    public void setTiaStructCalyear(short value) {
        this.tiaStructCalyear = value;
    }

    /**
     * Gets the value of the dcrRateAbs property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDcrRateAbs() {
        return dcrRateAbs;
    }

    /**
     * Sets the value of the dcrRateAbs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDcrRateAbs(BigDecimal value) {
        this.dcrRateAbs = value;
    }

    /**
     * Gets the value of the dcrRateCond property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDcrRateCond() {
        return dcrRateCond;
    }

    /**
     * Sets the value of the dcrRateCond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDcrRateCond(BigDecimal value) {
        this.dcrRateCond = value;
    }

    /**
     * Gets the value of the dcrRateAPEXAbs property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDcrRateAPEXAbs() {
        return dcrRateAPEXAbs;
    }

    /**
     * Sets the value of the dcrRateAPEXAbs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDcrRateAPEXAbs(BigDecimal value) {
        this.dcrRateAPEXAbs = value;
    }

    /**
     * Gets the value of the dcrRateAPEXCond property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDcrRateAPEXCond() {
        return dcrRateAPEXCond;
    }

    /**
     * Sets the value of the dcrRateAPEXCond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDcrRateAPEXCond(BigDecimal value) {
        this.dcrRateAPEXCond = value;
    }

    /**
     * Gets the value of the dcrVolAbs property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDcrVolAbs() {
        return dcrVolAbs;
    }

    /**
     * Sets the value of the dcrVolAbs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDcrVolAbs(BigDecimal value) {
        this.dcrVolAbs = value;
    }

    /**
     * Gets the value of the dcrVolCond property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDcrVolCond() {
        return dcrVolCond;
    }

    /**
     * Sets the value of the dcrVolCond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDcrVolCond(BigDecimal value) {
        this.dcrVolCond = value;
    }

    /**
     * Gets the value of the dcrVolAPEXAbs property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDcrVolAPEXAbs() {
        return dcrVolAPEXAbs;
    }

    /**
     * Sets the value of the dcrVolAPEXAbs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDcrVolAPEXAbs(BigDecimal value) {
        this.dcrVolAPEXAbs = value;
    }

    /**
     * Gets the value of the dcrVolAPEXCond property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDcrVolAPEXCond() {
        return dcrVolAPEXCond;
    }

    /**
     * Sets the value of the dcrVolAPEXCond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDcrVolAPEXCond(BigDecimal value) {
        this.dcrVolAPEXCond = value;
    }

    /**
     * Gets the value of the dcrRateTotal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDcrRateTotal() {
        return dcrRateTotal;
    }

    /**
     * Sets the value of the dcrRateTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDcrRateTotal(BigDecimal value) {
        this.dcrRateTotal = value;
    }

    /**
     * Gets the value of the dcrVolTotal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDcrVolTotal() {
        return dcrVolTotal;
    }

    /**
     * Sets the value of the dcrVolTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDcrVolTotal(BigDecimal value) {
        this.dcrVolTotal = value;
    }

}
