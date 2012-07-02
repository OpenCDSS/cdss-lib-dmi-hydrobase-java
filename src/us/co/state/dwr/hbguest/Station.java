
package us.co.state.dwr.hbguest;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Station complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Station">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="station_num" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="station_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="station_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cooperator_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nesdis_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="abbrev" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="drain_area" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="contr_area" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="transbsn" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="UTM_x" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="UTM_y" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="latdecdeg" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="longdecdeg" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="div" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="wd" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="county" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="topomap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cty" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="huc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="elev" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="loc_type_desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accuracy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="st" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Station", propOrder = {
    "stationNum",
    "stationName",
    "stationId",
    "cooperatorId",
    "nesdisId",
    "source",
    "abbrev",
    "drainArea",
    "contrArea",
    "transbsn",
    "utmx",
    "utmy",
    "latdecdeg",
    "longdecdeg",
    "div",
    "wd",
    "county",
    "topomap",
    "cty",
    "huc",
    "elev",
    "locTypeDesc",
    "accuracy",
    "st"
})
@XmlSeeAlso({
    StationGeolocMeasType.class
})
public class Station {

    @XmlElement(name = "station_num")
    protected int stationNum;
    @XmlElement(name = "station_name")
    protected String stationName;
    @XmlElement(name = "station_id")
    protected String stationId;
    @XmlElement(name = "cooperator_id")
    protected String cooperatorId;
    @XmlElement(name = "nesdis_id")
    protected String nesdisId;
    protected String source;
    protected String abbrev;
    @XmlElement(name = "drain_area", required = true, type = Float.class, nillable = true)
    protected Float drainArea;
    @XmlElement(name = "contr_area", required = true, type = Float.class, nillable = true)
    protected Float contrArea;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short transbsn;
    @XmlElement(name = "UTM_x", required = true, nillable = true)
    protected BigDecimal utmx;
    @XmlElement(name = "UTM_y", required = true, nillable = true)
    protected BigDecimal utmy;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal latdecdeg;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal longdecdeg;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short div;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short wd;
    protected String county;
    protected String topomap;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short cty;
    protected String huc;
    @XmlElement(required = true, type = Float.class, nillable = true)
    protected Float elev;
    @XmlElement(name = "loc_type_desc")
    protected String locTypeDesc;
    protected String accuracy;
    protected String st;

    /**
     * Gets the value of the stationNum property.
     * 
     */
    public int getStationNum() {
        return stationNum;
    }

    /**
     * Sets the value of the stationNum property.
     * 
     */
    public void setStationNum(int value) {
        this.stationNum = value;
    }

    /**
     * Gets the value of the stationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * Sets the value of the stationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationName(String value) {
        this.stationName = value;
    }

    /**
     * Gets the value of the stationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * Sets the value of the stationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationId(String value) {
        this.stationId = value;
    }

    /**
     * Gets the value of the cooperatorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCooperatorId() {
        return cooperatorId;
    }

    /**
     * Sets the value of the cooperatorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCooperatorId(String value) {
        this.cooperatorId = value;
    }

    /**
     * Gets the value of the nesdisId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNesdisId() {
        return nesdisId;
    }

    /**
     * Sets the value of the nesdisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNesdisId(String value) {
        this.nesdisId = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the abbrev property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbbrev() {
        return abbrev;
    }

    /**
     * Sets the value of the abbrev property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbbrev(String value) {
        this.abbrev = value;
    }

    /**
     * Gets the value of the drainArea property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getDrainArea() {
        return drainArea;
    }

    /**
     * Sets the value of the drainArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setDrainArea(Float value) {
        this.drainArea = value;
    }

    /**
     * Gets the value of the contrArea property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getContrArea() {
        return contrArea;
    }

    /**
     * Sets the value of the contrArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setContrArea(Float value) {
        this.contrArea = value;
    }

    /**
     * Gets the value of the transbsn property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getTransbsn() {
        return transbsn;
    }

    /**
     * Sets the value of the transbsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setTransbsn(Short value) {
        this.transbsn = value;
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
     * Gets the value of the div property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getDiv() {
        return div;
    }

    /**
     * Sets the value of the div property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setDiv(Short value) {
        this.div = value;
    }

    /**
     * Gets the value of the wd property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getWd() {
        return wd;
    }

    /**
     * Sets the value of the wd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setWd(Short value) {
        this.wd = value;
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
     * Gets the value of the cty property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCty() {
        return cty;
    }

    /**
     * Sets the value of the cty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCty(Short value) {
        this.cty = value;
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
     *     {@link Float }
     *     
     */
    public Float getElev() {
        return elev;
    }

    /**
     * Sets the value of the elev property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setElev(Float value) {
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

}
