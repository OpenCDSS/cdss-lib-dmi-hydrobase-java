
package us.co.state.dwr.hbguest;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NetAmts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NetAmts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="div" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="tab_trib" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wd" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="wdid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wr_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wr_stream_no" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="wd_stream_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rng" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="rdir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rnga" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ts" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="tdir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sec" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="seca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="q160" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="q40" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="q10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="county" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adj_date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="padj_date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apro_date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="admin_no" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="order_no" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="pri_case_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adj_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="use_types" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="net_rate_abs" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="net_vol_abs" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="net_rate_cond" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="net_vol_cond" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="net_rate_apex" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="net_vol_apex" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="action_comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NetAmts", propOrder = {
    "div",
    "tabTrib",
    "wd",
    "id",
    "wdid",
    "wrName",
    "wrStreamNo",
    "wdStreamName",
    "strtype",
    "pm",
    "rng",
    "rdir",
    "rnga",
    "ts",
    "tdir",
    "tsa",
    "sec",
    "seca",
    "q160",
    "q40",
    "q10",
    "county",
    "adjDate",
    "padjDate",
    "aproDate",
    "adminNo",
    "orderNo",
    "priCaseNo",
    "adjType",
    "useTypes",
    "netRateAbs",
    "netVolAbs",
    "netRateCond",
    "netVolCond",
    "netRateApex",
    "netVolApex",
    "actionComment"
})
public class NetAmts {

    protected short div;
    @XmlElement(name = "tab_trib")
    protected String tabTrib;
    protected short wd;
    protected int id;
    protected String wdid;
    @XmlElement(name = "wr_name")
    protected String wrName;
    @XmlElement(name = "wr_stream_no", required = true, type = Short.class, nillable = true)
    protected Short wrStreamNo;
    @XmlElement(name = "wd_stream_name")
    protected String wdStreamName;
    protected String strtype;
    protected String pm;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short rng;
    protected String rdir;
    protected String rnga;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short ts;
    protected String tdir;
    protected String tsa;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short sec;
    protected String seca;
    protected String q160;
    protected String q40;
    protected String q10;
    protected String county;
    @XmlElement(name = "adj_date")
    protected String adjDate;
    @XmlElement(name = "padj_date")
    protected String padjDate;
    @XmlElement(name = "apro_date")
    protected String aproDate;
    @XmlElement(name = "admin_no", required = true, nillable = true)
    protected BigDecimal adminNo;
    @XmlElement(name = "order_no", required = true, type = Short.class, nillable = true)
    protected Short orderNo;
    @XmlElement(name = "pri_case_no")
    protected String priCaseNo;
    @XmlElement(name = "adj_type")
    protected String adjType;
    @XmlElement(name = "use_types")
    protected String useTypes;
    @XmlElement(name = "net_rate_abs", required = true, nillable = true)
    protected BigDecimal netRateAbs;
    @XmlElement(name = "net_vol_abs", required = true, nillable = true)
    protected BigDecimal netVolAbs;
    @XmlElement(name = "net_rate_cond", required = true, nillable = true)
    protected BigDecimal netRateCond;
    @XmlElement(name = "net_vol_cond", required = true, nillable = true)
    protected BigDecimal netVolCond;
    @XmlElement(name = "net_rate_apex", required = true, nillable = true)
    protected BigDecimal netRateApex;
    @XmlElement(name = "net_vol_apex", required = true, nillable = true)
    protected BigDecimal netVolApex;
    @XmlElement(name = "action_comment")
    protected String actionComment;

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
     * Gets the value of the tabTrib property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTabTrib() {
        return tabTrib;
    }

    /**
     * Sets the value of the tabTrib property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTabTrib(String value) {
        this.tabTrib = value;
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
     * Gets the value of the wrName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWrName() {
        return wrName;
    }

    /**
     * Sets the value of the wrName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWrName(String value) {
        this.wrName = value;
    }

    /**
     * Gets the value of the wrStreamNo property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getWrStreamNo() {
        return wrStreamNo;
    }

    /**
     * Sets the value of the wrStreamNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setWrStreamNo(Short value) {
        this.wrStreamNo = value;
    }

    /**
     * Gets the value of the wdStreamName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWdStreamName() {
        return wdStreamName;
    }

    /**
     * Sets the value of the wdStreamName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWdStreamName(String value) {
        this.wdStreamName = value;
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
     * Gets the value of the adjDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdjDate() {
        return adjDate;
    }

    /**
     * Sets the value of the adjDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdjDate(String value) {
        this.adjDate = value;
    }

    /**
     * Gets the value of the padjDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPadjDate() {
        return padjDate;
    }

    /**
     * Sets the value of the padjDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPadjDate(String value) {
        this.padjDate = value;
    }

    /**
     * Gets the value of the aproDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAproDate() {
        return aproDate;
    }

    /**
     * Sets the value of the aproDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAproDate(String value) {
        this.aproDate = value;
    }

    /**
     * Gets the value of the adminNo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAdminNo() {
        return adminNo;
    }

    /**
     * Sets the value of the adminNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAdminNo(BigDecimal value) {
        this.adminNo = value;
    }

    /**
     * Gets the value of the orderNo property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getOrderNo() {
        return orderNo;
    }

    /**
     * Sets the value of the orderNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setOrderNo(Short value) {
        this.orderNo = value;
    }

    /**
     * Gets the value of the priCaseNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriCaseNo() {
        return priCaseNo;
    }

    /**
     * Sets the value of the priCaseNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriCaseNo(String value) {
        this.priCaseNo = value;
    }

    /**
     * Gets the value of the adjType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdjType() {
        return adjType;
    }

    /**
     * Sets the value of the adjType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdjType(String value) {
        this.adjType = value;
    }

    /**
     * Gets the value of the useTypes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseTypes() {
        return useTypes;
    }

    /**
     * Sets the value of the useTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseTypes(String value) {
        this.useTypes = value;
    }

    /**
     * Gets the value of the netRateAbs property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetRateAbs() {
        return netRateAbs;
    }

    /**
     * Sets the value of the netRateAbs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetRateAbs(BigDecimal value) {
        this.netRateAbs = value;
    }

    /**
     * Gets the value of the netVolAbs property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetVolAbs() {
        return netVolAbs;
    }

    /**
     * Sets the value of the netVolAbs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetVolAbs(BigDecimal value) {
        this.netVolAbs = value;
    }

    /**
     * Gets the value of the netRateCond property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetRateCond() {
        return netRateCond;
    }

    /**
     * Sets the value of the netRateCond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetRateCond(BigDecimal value) {
        this.netRateCond = value;
    }

    /**
     * Gets the value of the netVolCond property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetVolCond() {
        return netVolCond;
    }

    /**
     * Sets the value of the netVolCond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetVolCond(BigDecimal value) {
        this.netVolCond = value;
    }

    /**
     * Gets the value of the netRateApex property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetRateApex() {
        return netRateApex;
    }

    /**
     * Sets the value of the netRateApex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetRateApex(BigDecimal value) {
        this.netRateApex = value;
    }

    /**
     * Gets the value of the netVolApex property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNetVolApex() {
        return netVolApex;
    }

    /**
     * Sets the value of the netVolApex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNetVolApex(BigDecimal value) {
        this.netVolApex = value;
    }

    /**
     * Gets the value of the actionComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionComment() {
        return actionComment;
    }

    /**
     * Sets the value of the actionComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionComment(String value) {
        this.actionComment = value;
    }

}
