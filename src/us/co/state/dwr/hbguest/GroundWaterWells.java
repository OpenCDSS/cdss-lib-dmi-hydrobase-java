
package us.co.state.dwr.hbguest;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroundWaterWells complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroundWaterWells">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="well_num" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="well_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="div" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="wd" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="receipt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permitno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="permitsuf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permitrpl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locnum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Site_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="basin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="md" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="well_depth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="aquifer1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aquifer2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aquifer_comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tperf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="bperf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="yield" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="bedrock_elev" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="sat_1965" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="remarks1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remarks2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data_source_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data_source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *         &lt;element name="county" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="topomap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cty" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="huc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="elev" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="elev_accuracy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loc_type_desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loc_accuracy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stream_num" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="str_mile" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="spotter_version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DSS_aquifer1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DSS_aquifer2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DSS_aquifer_comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroundWaterWells", propOrder = {
    "wellNum",
    "wellName",
    "div",
    "wd",
    "id",
    "receipt",
    "permitno",
    "permitsuf",
    "permitrpl",
    "locnum",
    "siteID",
    "basin",
    "md",
    "wellDepth",
    "aquifer1",
    "aquifer2",
    "aquiferComment",
    "tperf",
    "bperf",
    "yield",
    "bedrockElev",
    "sat1965",
    "remarks1",
    "remarks2",
    "dataSourceId",
    "dataSource",
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
    "county",
    "topomap",
    "cty",
    "huc",
    "elev",
    "elevAccuracy",
    "locTypeDesc",
    "locAccuracy",
    "streamNum",
    "strMile",
    "spotterVersion",
    "dssAquifer1",
    "dssAquifer2",
    "dssAquiferComment"
})
@XmlSeeAlso({
    GroundWaterWellsAquiferPicks.class,
    GroundWaterWellsDrillersksum.class,
    GroundWaterWellsMeasType.class,
    GroundWaterWellsPumpingTests.class,
    GroundWaterWellsGeophlogs.class,
    GroundWaterWellsWellMeasTS.class,
    GroundWaterWellsVolcanics.class
})
public class GroundWaterWells {

    @XmlElement(name = "well_num")
    protected int wellNum;
    @XmlElement(name = "well_name")
    protected String wellName;
    protected short div;
    protected short wd;
    protected int id;
    protected String receipt;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer permitno;
    protected String permitsuf;
    protected String permitrpl;
    protected String locnum;
    @XmlElement(name = "Site_ID")
    protected String siteID;
    protected String basin;
    protected String md;
    @XmlElement(name = "well_depth", required = true, type = Integer.class, nillable = true)
    protected Integer wellDepth;
    protected String aquifer1;
    protected String aquifer2;
    @XmlElement(name = "aquifer_comment")
    protected String aquiferComment;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer tperf;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer bperf;
    @XmlElement(required = true, type = Float.class, nillable = true)
    protected Float yield;
    @XmlElement(name = "bedrock_elev", required = true, type = Float.class, nillable = true)
    protected Float bedrockElev;
    @XmlElement(name = "sat_1965", required = true, type = Float.class, nillable = true)
    protected Float sat1965;
    protected String remarks1;
    protected String remarks2;
    @XmlElement(name = "data_source_id")
    protected String dataSourceId;
    @XmlElement(name = "data_source")
    protected String dataSource;
    @XmlElement(name = "UTM_x", required = true, nillable = true)
    protected BigDecimal utmx;
    @XmlElement(name = "UTM_y", required = true, nillable = true)
    protected BigDecimal utmy;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal latdecdeg;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal longdecdeg;
    protected String pm;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short ts;
    protected String tdir;
    protected String tsa;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short rng;
    protected String rdir;
    protected String rnga;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short sec;
    protected String seca;
    protected String q160;
    protected String q40;
    protected String q10;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short coordsns;
    @XmlElement(name = "coordsns_dir")
    protected String coordsnsDir;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short coordsew;
    @XmlElement(name = "coordsew_dir")
    protected String coordsewDir;
    protected String county;
    protected String topomap;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short cty;
    protected String huc;
    @XmlElement(required = true, type = Float.class, nillable = true)
    protected Float elev;
    @XmlElement(name = "elev_accuracy")
    protected String elevAccuracy;
    @XmlElement(name = "loc_type_desc")
    protected String locTypeDesc;
    @XmlElement(name = "loc_accuracy")
    protected String locAccuracy;
    @XmlElement(name = "stream_num", required = true, type = Integer.class, nillable = true)
    protected Integer streamNum;
    @XmlElement(name = "str_mile", required = true, type = Float.class, nillable = true)
    protected Float strMile;
    @XmlElement(name = "spotter_version")
    protected String spotterVersion;
    @XmlElement(name = "DSS_aquifer1")
    protected String dssAquifer1;
    @XmlElement(name = "DSS_aquifer2")
    protected String dssAquifer2;
    @XmlElement(name = "DSS_aquifer_comment")
    protected String dssAquiferComment;

    /**
     * Gets the value of the wellNum property.
     * 
     */
    public int getWellNum() {
        return wellNum;
    }

    /**
     * Sets the value of the wellNum property.
     * 
     */
    public void setWellNum(int value) {
        this.wellNum = value;
    }

    /**
     * Gets the value of the wellName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWellName() {
        return wellName;
    }

    /**
     * Sets the value of the wellName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWellName(String value) {
        this.wellName = value;
    }

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
     * Gets the value of the receipt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceipt() {
        return receipt;
    }

    /**
     * Sets the value of the receipt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceipt(String value) {
        this.receipt = value;
    }

    /**
     * Gets the value of the permitno property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPermitno() {
        return permitno;
    }

    /**
     * Sets the value of the permitno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPermitno(Integer value) {
        this.permitno = value;
    }

    /**
     * Gets the value of the permitsuf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermitsuf() {
        return permitsuf;
    }

    /**
     * Sets the value of the permitsuf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermitsuf(String value) {
        this.permitsuf = value;
    }

    /**
     * Gets the value of the permitrpl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermitrpl() {
        return permitrpl;
    }

    /**
     * Sets the value of the permitrpl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermitrpl(String value) {
        this.permitrpl = value;
    }

    /**
     * Gets the value of the locnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocnum() {
        return locnum;
    }

    /**
     * Sets the value of the locnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocnum(String value) {
        this.locnum = value;
    }

    /**
     * Gets the value of the siteID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteID() {
        return siteID;
    }

    /**
     * Sets the value of the siteID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteID(String value) {
        this.siteID = value;
    }

    /**
     * Gets the value of the basin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBasin() {
        return basin;
    }

    /**
     * Sets the value of the basin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBasin(String value) {
        this.basin = value;
    }

    /**
     * Gets the value of the md property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMd() {
        return md;
    }

    /**
     * Sets the value of the md property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMd(String value) {
        this.md = value;
    }

    /**
     * Gets the value of the wellDepth property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWellDepth() {
        return wellDepth;
    }

    /**
     * Sets the value of the wellDepth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWellDepth(Integer value) {
        this.wellDepth = value;
    }

    /**
     * Gets the value of the aquifer1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAquifer1() {
        return aquifer1;
    }

    /**
     * Sets the value of the aquifer1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAquifer1(String value) {
        this.aquifer1 = value;
    }

    /**
     * Gets the value of the aquifer2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAquifer2() {
        return aquifer2;
    }

    /**
     * Sets the value of the aquifer2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAquifer2(String value) {
        this.aquifer2 = value;
    }

    /**
     * Gets the value of the aquiferComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAquiferComment() {
        return aquiferComment;
    }

    /**
     * Sets the value of the aquiferComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAquiferComment(String value) {
        this.aquiferComment = value;
    }

    /**
     * Gets the value of the tperf property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTperf() {
        return tperf;
    }

    /**
     * Sets the value of the tperf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTperf(Integer value) {
        this.tperf = value;
    }

    /**
     * Gets the value of the bperf property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBperf() {
        return bperf;
    }

    /**
     * Sets the value of the bperf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBperf(Integer value) {
        this.bperf = value;
    }

    /**
     * Gets the value of the yield property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getYield() {
        return yield;
    }

    /**
     * Sets the value of the yield property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setYield(Float value) {
        this.yield = value;
    }

    /**
     * Gets the value of the bedrockElev property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getBedrockElev() {
        return bedrockElev;
    }

    /**
     * Sets the value of the bedrockElev property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setBedrockElev(Float value) {
        this.bedrockElev = value;
    }

    /**
     * Gets the value of the sat1965 property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getSat1965() {
        return sat1965;
    }

    /**
     * Sets the value of the sat1965 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setSat1965(Float value) {
        this.sat1965 = value;
    }

    /**
     * Gets the value of the remarks1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks1() {
        return remarks1;
    }

    /**
     * Sets the value of the remarks1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks1(String value) {
        this.remarks1 = value;
    }

    /**
     * Gets the value of the remarks2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks2() {
        return remarks2;
    }

    /**
     * Sets the value of the remarks2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks2(String value) {
        this.remarks2 = value;
    }

    /**
     * Gets the value of the dataSourceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSourceId() {
        return dataSourceId;
    }

    /**
     * Sets the value of the dataSourceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSourceId(String value) {
        this.dataSourceId = value;
    }

    /**
     * Gets the value of the dataSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * Sets the value of the dataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSource(String value) {
        this.dataSource = value;
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
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getTs() {
        return ts;
    }

    /**
     * Sets the value of the ts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setTs(Short value) {
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
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getRng() {
        return rng;
    }

    /**
     * Sets the value of the rng property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setRng(Short value) {
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
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getSec() {
        return sec;
    }

    /**
     * Sets the value of the sec property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setSec(Short value) {
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
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCoordsns() {
        return coordsns;
    }

    /**
     * Sets the value of the coordsns property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCoordsns(Short value) {
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
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCoordsew() {
        return coordsew;
    }

    /**
     * Sets the value of the coordsew property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCoordsew(Short value) {
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
     * Gets the value of the elevAccuracy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElevAccuracy() {
        return elevAccuracy;
    }

    /**
     * Sets the value of the elevAccuracy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElevAccuracy(String value) {
        this.elevAccuracy = value;
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
     * Gets the value of the locAccuracy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocAccuracy() {
        return locAccuracy;
    }

    /**
     * Sets the value of the locAccuracy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocAccuracy(String value) {
        this.locAccuracy = value;
    }

    /**
     * Gets the value of the streamNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStreamNum() {
        return streamNum;
    }

    /**
     * Sets the value of the streamNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStreamNum(Integer value) {
        this.streamNum = value;
    }

    /**
     * Gets the value of the strMile property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getStrMile() {
        return strMile;
    }

    /**
     * Sets the value of the strMile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setStrMile(Float value) {
        this.strMile = value;
    }

    /**
     * Gets the value of the spotterVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpotterVersion() {
        return spotterVersion;
    }

    /**
     * Sets the value of the spotterVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpotterVersion(String value) {
        this.spotterVersion = value;
    }

    /**
     * Gets the value of the dssAquifer1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSSAquifer1() {
        return dssAquifer1;
    }

    /**
     * Sets the value of the dssAquifer1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSSAquifer1(String value) {
        this.dssAquifer1 = value;
    }

    /**
     * Gets the value of the dssAquifer2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSSAquifer2() {
        return dssAquifer2;
    }

    /**
     * Sets the value of the dssAquifer2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSSAquifer2(String value) {
        this.dssAquifer2 = value;
    }

    /**
     * Gets the value of the dssAquiferComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSSAquiferComment() {
        return dssAquiferComment;
    }

    /**
     * Sets the value of the dssAquiferComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSSAquiferComment(String value) {
        this.dssAquiferComment = value;
    }

}
