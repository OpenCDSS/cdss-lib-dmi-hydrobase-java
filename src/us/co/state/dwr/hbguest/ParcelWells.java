
package us.co.state.dwr.hbguest;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParcelWells complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParcelWells">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="div" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="wd" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="wdid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receipt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permitno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="permitsuf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permitrpl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="well_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="yield" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="yield_apex" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="perm_date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appr_date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tperf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="bperf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="depth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="aquifer1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="use1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="use2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="use3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="well_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="utm_x" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="utm_y" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParcelWells", propOrder = {
    "div",
    "wd",
    "wdid",
    "receipt",
    "permitno",
    "permitsuf",
    "permitrpl",
    "wellName",
    "yield",
    "yieldApex",
    "permDate",
    "apprDate",
    "tperf",
    "bperf",
    "depth",
    "aquifer1",
    "flag",
    "use1",
    "use2",
    "use3",
    "wellId",
    "idType",
    "utmX",
    "utmY"
})
public class ParcelWells {

    protected short div;
    protected short wd;
    protected String wdid;
    protected String receipt;
    protected int permitno;
    protected String permitsuf;
    protected String permitrpl;
    @XmlElement(name = "well_name")
    protected String wellName;
    protected double yield;
    @XmlElement(name = "yield_apex")
    protected double yieldApex;
    @XmlElement(name = "perm_date")
    protected String permDate;
    @XmlElement(name = "appr_date")
    protected String apprDate;
    protected int tperf;
    protected int bperf;
    protected int depth;
    protected String aquifer1;
    protected int flag;
    protected String use1;
    protected String use2;
    protected String use3;
    @XmlElement(name = "well_id")
    protected String wellId;
    @XmlElement(name = "id_type")
    protected String idType;
    @XmlElement(name = "utm_x", required = true)
    protected BigDecimal utmX;
    @XmlElement(name = "utm_y", required = true)
    protected BigDecimal utmY;

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
     */
    public int getPermitno() {
        return permitno;
    }

    /**
     * Sets the value of the permitno property.
     * 
     */
    public void setPermitno(int value) {
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
     * Gets the value of the yield property.
     * 
     */
    public double getYield() {
        return yield;
    }

    /**
     * Sets the value of the yield property.
     * 
     */
    public void setYield(double value) {
        this.yield = value;
    }

    /**
     * Gets the value of the yieldApex property.
     * 
     */
    public double getYieldApex() {
        return yieldApex;
    }

    /**
     * Sets the value of the yieldApex property.
     * 
     */
    public void setYieldApex(double value) {
        this.yieldApex = value;
    }

    /**
     * Gets the value of the permDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermDate() {
        return permDate;
    }

    /**
     * Sets the value of the permDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermDate(String value) {
        this.permDate = value;
    }

    /**
     * Gets the value of the apprDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApprDate() {
        return apprDate;
    }

    /**
     * Sets the value of the apprDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApprDate(String value) {
        this.apprDate = value;
    }

    /**
     * Gets the value of the tperf property.
     * 
     */
    public int getTperf() {
        return tperf;
    }

    /**
     * Sets the value of the tperf property.
     * 
     */
    public void setTperf(int value) {
        this.tperf = value;
    }

    /**
     * Gets the value of the bperf property.
     * 
     */
    public int getBperf() {
        return bperf;
    }

    /**
     * Sets the value of the bperf property.
     * 
     */
    public void setBperf(int value) {
        this.bperf = value;
    }

    /**
     * Gets the value of the depth property.
     * 
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Sets the value of the depth property.
     * 
     */
    public void setDepth(int value) {
        this.depth = value;
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
     * Gets the value of the flag property.
     * 
     */
    public int getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     */
    public void setFlag(int value) {
        this.flag = value;
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
     * Gets the value of the wellId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWellId() {
        return wellId;
    }

    /**
     * Sets the value of the wellId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWellId(String value) {
        this.wellId = value;
    }

    /**
     * Gets the value of the idType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdType() {
        return idType;
    }

    /**
     * Sets the value of the idType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdType(String value) {
        this.idType = value;
    }

    /**
     * Gets the value of the utmX property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUtmX() {
        return utmX;
    }

    /**
     * Sets the value of the utmX property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUtmX(BigDecimal value) {
        this.utmX = value;
    }

    /**
     * Gets the value of the utmY property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUtmY() {
        return utmY;
    }

    /**
     * Sets the value of the utmY property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUtmY(BigDecimal value) {
        this.utmY = value;
    }

}
