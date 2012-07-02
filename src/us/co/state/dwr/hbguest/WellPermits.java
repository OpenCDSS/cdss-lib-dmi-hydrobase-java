
package us.co.state.dwr.hbguest;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WellPermits complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WellPermits">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="receipt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permitno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="permitsuf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permitrpl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statusdesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currentstatusnum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="wellname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="caseno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WDID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OGCCID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="div" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="wd" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="cty" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="county" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="managementdistrictnum" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="ManagementDistrict" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="designatedbasinnum" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="DesignatedBasin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subdivisionname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lot" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="block" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="county_parcel_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parcel_size" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="pm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ts" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="tdir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rng" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="rdir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sec" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="seca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="q160" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="q40" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coordsns" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="coordsns_dir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coordsew" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="coordsew_dir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UTMx" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="UTMy" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="loc_accuracy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permitted_use1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="use1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permitted_use2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="use2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="special_use" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="use3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aquifer1_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aquifer2_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permitted_area" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="permitted_area_units" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="annual_appropriation" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="date_permit_issued" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_permit_expires" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_well_constructed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_1st_Beneficial_Use" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_pump_installed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_well_plugged" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="elev" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="well_depth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tperf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="bperf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pump_rate" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="static_water_level" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="full_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailing_address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailing_city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailing_state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailing_ZIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_application_received" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="driller_lic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pump_lic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_last_action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="last_action_desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WellPermits", propOrder = {
    "receipt",
    "permitno",
    "permitsuf",
    "permitrpl",
    "statusdesc",
    "currentstatusnum",
    "wellname",
    "caseno",
    "wdid",
    "ogccid",
    "div",
    "wd",
    "cty",
    "county",
    "managementdistrictnum",
    "managementDistrict",
    "designatedbasinnum",
    "designatedBasin",
    "subdivisionname",
    "filing",
    "lot",
    "block",
    "countyParcelId",
    "parcelSize",
    "pm",
    "ts",
    "tdir",
    "rng",
    "rdir",
    "sec",
    "seca",
    "q160",
    "q40",
    "coordsns",
    "coordsnsDir",
    "coordsew",
    "coordsewDir",
    "utMx",
    "utMy",
    "locAccuracy",
    "permittedUse1",
    "use1",
    "permittedUse2",
    "use2",
    "specialUse",
    "use3",
    "aquifer1Name",
    "aquifer2Name",
    "permittedArea",
    "permittedAreaUnits",
    "annualAppropriation",
    "datePermitIssued",
    "datePermitExpires",
    "dateWellConstructed",
    "date1StBeneficialUse",
    "datePumpInstalled",
    "dateWellPlugged",
    "comment",
    "elev",
    "wellDepth",
    "tperf",
    "bperf",
    "pumpRate",
    "staticWaterLevel",
    "fullName",
    "mailingAddress",
    "mailingCity",
    "mailingState",
    "mailingZIP",
    "dateApplicationReceived",
    "drillerLic",
    "pumpLic",
    "dateLastAction",
    "lastActionDesc"
})
public class WellPermits {

    protected String receipt;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer permitno;
    protected String permitsuf;
    protected String permitrpl;
    protected String statusdesc;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer currentstatusnum;
    protected String wellname;
    protected String caseno;
    @XmlElement(name = "WDID")
    protected String wdid;
    @XmlElement(name = "OGCCID")
    protected String ogccid;
    protected short div;
    protected short wd;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short cty;
    protected String county;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short managementdistrictnum;
    @XmlElement(name = "ManagementDistrict")
    protected String managementDistrict;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short designatedbasinnum;
    @XmlElement(name = "DesignatedBasin")
    protected String designatedBasin;
    protected String subdivisionname;
    protected String filing;
    protected String lot;
    protected String block;
    @XmlElement(name = "county_parcel_id")
    protected String countyParcelId;
    @XmlElement(name = "parcel_size", required = true, type = Float.class, nillable = true)
    protected Float parcelSize;
    protected String pm;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal ts;
    protected String tdir;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal rng;
    protected String rdir;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short sec;
    protected String seca;
    protected String q160;
    protected String q40;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short coordsns;
    @XmlElement(name = "coordsns_dir")
    protected String coordsnsDir;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short coordsew;
    @XmlElement(name = "coordsew_dir")
    protected String coordsewDir;
    @XmlElement(name = "UTMx", required = true, nillable = true)
    protected BigDecimal utMx;
    @XmlElement(name = "UTMy", required = true, nillable = true)
    protected BigDecimal utMy;
    @XmlElement(name = "loc_accuracy")
    protected String locAccuracy;
    @XmlElement(name = "permitted_use1")
    protected String permittedUse1;
    protected String use1;
    @XmlElement(name = "permitted_use2")
    protected String permittedUse2;
    protected String use2;
    @XmlElement(name = "special_use")
    protected String specialUse;
    protected String use3;
    @XmlElement(name = "aquifer1_name")
    protected String aquifer1Name;
    @XmlElement(name = "aquifer2_name")
    protected String aquifer2Name;
    @XmlElement(name = "permitted_area", required = true, type = Float.class, nillable = true)
    protected Float permittedArea;
    @XmlElement(name = "permitted_area_units")
    protected String permittedAreaUnits;
    @XmlElement(name = "annual_appropriation", required = true, type = Float.class, nillable = true)
    protected Float annualAppropriation;
    @XmlElement(name = "date_permit_issued")
    protected String datePermitIssued;
    @XmlElement(name = "date_permit_expires")
    protected String datePermitExpires;
    @XmlElement(name = "date_well_constructed")
    protected String dateWellConstructed;
    @XmlElement(name = "date_1st_Beneficial_Use")
    protected String date1StBeneficialUse;
    @XmlElement(name = "date_pump_installed")
    protected String datePumpInstalled;
    @XmlElement(name = "date_well_plugged")
    protected String dateWellPlugged;
    protected String comment;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer elev;
    @XmlElement(name = "well_depth", required = true, type = Integer.class, nillable = true)
    protected Integer wellDepth;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer tperf;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer bperf;
    @XmlElement(name = "pump_rate", required = true, type = Float.class, nillable = true)
    protected Float pumpRate;
    @XmlElement(name = "static_water_level", required = true, type = Integer.class, nillable = true)
    protected Integer staticWaterLevel;
    @XmlElement(name = "full_name")
    protected String fullName;
    @XmlElement(name = "mailing_address")
    protected String mailingAddress;
    @XmlElement(name = "mailing_city")
    protected String mailingCity;
    @XmlElement(name = "mailing_state")
    protected String mailingState;
    @XmlElement(name = "mailing_ZIP")
    protected String mailingZIP;
    @XmlElement(name = "date_application_received")
    protected String dateApplicationReceived;
    @XmlElement(name = "driller_lic")
    protected String drillerLic;
    @XmlElement(name = "pump_lic")
    protected String pumpLic;
    @XmlElement(name = "date_last_action")
    protected String dateLastAction;
    @XmlElement(name = "last_action_desc")
    protected String lastActionDesc;

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
     * Gets the value of the statusdesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusdesc() {
        return statusdesc;
    }

    /**
     * Sets the value of the statusdesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusdesc(String value) {
        this.statusdesc = value;
    }

    /**
     * Gets the value of the currentstatusnum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCurrentstatusnum() {
        return currentstatusnum;
    }

    /**
     * Sets the value of the currentstatusnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCurrentstatusnum(Integer value) {
        this.currentstatusnum = value;
    }

    /**
     * Gets the value of the wellname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWellname() {
        return wellname;
    }

    /**
     * Sets the value of the wellname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWellname(String value) {
        this.wellname = value;
    }

    /**
     * Gets the value of the caseno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaseno() {
        return caseno;
    }

    /**
     * Sets the value of the caseno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaseno(String value) {
        this.caseno = value;
    }

    /**
     * Gets the value of the wdid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWDID() {
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
    public void setWDID(String value) {
        this.wdid = value;
    }

    /**
     * Gets the value of the ogccid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOGCCID() {
        return ogccid;
    }

    /**
     * Sets the value of the ogccid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOGCCID(String value) {
        this.ogccid = value;
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
     * Gets the value of the managementdistrictnum property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getManagementdistrictnum() {
        return managementdistrictnum;
    }

    /**
     * Sets the value of the managementdistrictnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setManagementdistrictnum(Short value) {
        this.managementdistrictnum = value;
    }

    /**
     * Gets the value of the managementDistrict property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManagementDistrict() {
        return managementDistrict;
    }

    /**
     * Sets the value of the managementDistrict property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagementDistrict(String value) {
        this.managementDistrict = value;
    }

    /**
     * Gets the value of the designatedbasinnum property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getDesignatedbasinnum() {
        return designatedbasinnum;
    }

    /**
     * Sets the value of the designatedbasinnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setDesignatedbasinnum(Short value) {
        this.designatedbasinnum = value;
    }

    /**
     * Gets the value of the designatedBasin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesignatedBasin() {
        return designatedBasin;
    }

    /**
     * Sets the value of the designatedBasin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesignatedBasin(String value) {
        this.designatedBasin = value;
    }

    /**
     * Gets the value of the subdivisionname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubdivisionname() {
        return subdivisionname;
    }

    /**
     * Sets the value of the subdivisionname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubdivisionname(String value) {
        this.subdivisionname = value;
    }

    /**
     * Gets the value of the filing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiling() {
        return filing;
    }

    /**
     * Sets the value of the filing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiling(String value) {
        this.filing = value;
    }

    /**
     * Gets the value of the lot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLot() {
        return lot;
    }

    /**
     * Sets the value of the lot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLot(String value) {
        this.lot = value;
    }

    /**
     * Gets the value of the block property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the value of the block property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlock(String value) {
        this.block = value;
    }

    /**
     * Gets the value of the countyParcelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountyParcelId() {
        return countyParcelId;
    }

    /**
     * Sets the value of the countyParcelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountyParcelId(String value) {
        this.countyParcelId = value;
    }

    /**
     * Gets the value of the parcelSize property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getParcelSize() {
        return parcelSize;
    }

    /**
     * Sets the value of the parcelSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setParcelSize(Float value) {
        this.parcelSize = value;
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
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTs() {
        return ts;
    }

    /**
     * Sets the value of the ts property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTs(BigDecimal value) {
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
     * Gets the value of the rng property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRng() {
        return rng;
    }

    /**
     * Sets the value of the rng property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRng(BigDecimal value) {
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
     * Gets the value of the utMx property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUTMx() {
        return utMx;
    }

    /**
     * Sets the value of the utMx property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUTMx(BigDecimal value) {
        this.utMx = value;
    }

    /**
     * Gets the value of the utMy property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUTMy() {
        return utMy;
    }

    /**
     * Sets the value of the utMy property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUTMy(BigDecimal value) {
        this.utMy = value;
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
     * Gets the value of the permittedUse1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermittedUse1() {
        return permittedUse1;
    }

    /**
     * Sets the value of the permittedUse1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermittedUse1(String value) {
        this.permittedUse1 = value;
    }

    /**
     * Gets the value of the use1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUse1() {
        return use1;
    }

    /**
     * Sets the value of the use1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUse1(String value) {
        this.use1 = value;
    }

    /**
     * Gets the value of the permittedUse2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermittedUse2() {
        return permittedUse2;
    }

    /**
     * Sets the value of the permittedUse2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermittedUse2(String value) {
        this.permittedUse2 = value;
    }

    /**
     * Gets the value of the use2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUse2() {
        return use2;
    }

    /**
     * Sets the value of the use2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUse2(String value) {
        this.use2 = value;
    }

    /**
     * Gets the value of the specialUse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialUse() {
        return specialUse;
    }

    /**
     * Sets the value of the specialUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialUse(String value) {
        this.specialUse = value;
    }

    /**
     * Gets the value of the use3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUse3() {
        return use3;
    }

    /**
     * Sets the value of the use3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUse3(String value) {
        this.use3 = value;
    }

    /**
     * Gets the value of the aquifer1Name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAquifer1Name() {
        return aquifer1Name;
    }

    /**
     * Sets the value of the aquifer1Name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAquifer1Name(String value) {
        this.aquifer1Name = value;
    }

    /**
     * Gets the value of the aquifer2Name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAquifer2Name() {
        return aquifer2Name;
    }

    /**
     * Sets the value of the aquifer2Name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAquifer2Name(String value) {
        this.aquifer2Name = value;
    }

    /**
     * Gets the value of the permittedArea property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPermittedArea() {
        return permittedArea;
    }

    /**
     * Sets the value of the permittedArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPermittedArea(Float value) {
        this.permittedArea = value;
    }

    /**
     * Gets the value of the permittedAreaUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermittedAreaUnits() {
        return permittedAreaUnits;
    }

    /**
     * Sets the value of the permittedAreaUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermittedAreaUnits(String value) {
        this.permittedAreaUnits = value;
    }

    /**
     * Gets the value of the annualAppropriation property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getAnnualAppropriation() {
        return annualAppropriation;
    }

    /**
     * Sets the value of the annualAppropriation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setAnnualAppropriation(Float value) {
        this.annualAppropriation = value;
    }

    /**
     * Gets the value of the datePermitIssued property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatePermitIssued() {
        return datePermitIssued;
    }

    /**
     * Sets the value of the datePermitIssued property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatePermitIssued(String value) {
        this.datePermitIssued = value;
    }

    /**
     * Gets the value of the datePermitExpires property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatePermitExpires() {
        return datePermitExpires;
    }

    /**
     * Sets the value of the datePermitExpires property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatePermitExpires(String value) {
        this.datePermitExpires = value;
    }

    /**
     * Gets the value of the dateWellConstructed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateWellConstructed() {
        return dateWellConstructed;
    }

    /**
     * Sets the value of the dateWellConstructed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateWellConstructed(String value) {
        this.dateWellConstructed = value;
    }

    /**
     * Gets the value of the date1StBeneficialUse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate1StBeneficialUse() {
        return date1StBeneficialUse;
    }

    /**
     * Sets the value of the date1StBeneficialUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate1StBeneficialUse(String value) {
        this.date1StBeneficialUse = value;
    }

    /**
     * Gets the value of the datePumpInstalled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatePumpInstalled() {
        return datePumpInstalled;
    }

    /**
     * Sets the value of the datePumpInstalled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatePumpInstalled(String value) {
        this.datePumpInstalled = value;
    }

    /**
     * Gets the value of the dateWellPlugged property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateWellPlugged() {
        return dateWellPlugged;
    }

    /**
     * Sets the value of the dateWellPlugged property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateWellPlugged(String value) {
        this.dateWellPlugged = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the elev property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getElev() {
        return elev;
    }

    /**
     * Sets the value of the elev property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setElev(Integer value) {
        this.elev = value;
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
     * Gets the value of the pumpRate property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPumpRate() {
        return pumpRate;
    }

    /**
     * Sets the value of the pumpRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPumpRate(Float value) {
        this.pumpRate = value;
    }

    /**
     * Gets the value of the staticWaterLevel property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStaticWaterLevel() {
        return staticWaterLevel;
    }

    /**
     * Sets the value of the staticWaterLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStaticWaterLevel(Integer value) {
        this.staticWaterLevel = value;
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
     * Gets the value of the mailingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailingAddress() {
        return mailingAddress;
    }

    /**
     * Sets the value of the mailingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailingAddress(String value) {
        this.mailingAddress = value;
    }

    /**
     * Gets the value of the mailingCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailingCity() {
        return mailingCity;
    }

    /**
     * Sets the value of the mailingCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailingCity(String value) {
        this.mailingCity = value;
    }

    /**
     * Gets the value of the mailingState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailingState() {
        return mailingState;
    }

    /**
     * Sets the value of the mailingState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailingState(String value) {
        this.mailingState = value;
    }

    /**
     * Gets the value of the mailingZIP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailingZIP() {
        return mailingZIP;
    }

    /**
     * Sets the value of the mailingZIP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailingZIP(String value) {
        this.mailingZIP = value;
    }

    /**
     * Gets the value of the dateApplicationReceived property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateApplicationReceived() {
        return dateApplicationReceived;
    }

    /**
     * Sets the value of the dateApplicationReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateApplicationReceived(String value) {
        this.dateApplicationReceived = value;
    }

    /**
     * Gets the value of the drillerLic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrillerLic() {
        return drillerLic;
    }

    /**
     * Sets the value of the drillerLic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrillerLic(String value) {
        this.drillerLic = value;
    }

    /**
     * Gets the value of the pumpLic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPumpLic() {
        return pumpLic;
    }

    /**
     * Sets the value of the pumpLic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPumpLic(String value) {
        this.pumpLic = value;
    }

    /**
     * Gets the value of the dateLastAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateLastAction() {
        return dateLastAction;
    }

    /**
     * Sets the value of the dateLastAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateLastAction(String value) {
        this.dateLastAction = value;
    }

    /**
     * Gets the value of the lastActionDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastActionDesc() {
        return lastActionDesc;
    }

    /**
     * Sets the value of the lastActionDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastActionDesc(String value) {
        this.lastActionDesc = value;
    }

}
