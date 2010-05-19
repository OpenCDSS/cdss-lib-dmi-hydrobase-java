
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StructureAnnuallyTS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StructureAnnuallyTS">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.dwr.state.co.us/}StructureTSCommon">
 *       &lt;sequence>
 *         &lt;element name="irr_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="fdu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ldu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dwc" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="maxq" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="maxq_date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nobs" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="nus" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="ann_amt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructureAnnuallyTS", propOrder = {
    "irrYear",
    "fdu",
    "ldu",
    "dwc",
    "maxq",
    "maxqDate",
    "nobs",
    "nus",
    "annAmt",
    "unit"
})
public class StructureAnnuallyTS
    extends StructureTSCommon
{

    @XmlElement(name = "irr_year")
    protected short irrYear;
    protected String fdu;
    protected String ldu;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short dwc;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double maxq;
    @XmlElement(name = "maxq_date")
    protected String maxqDate;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short nobs;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short nus;
    @XmlElement(name = "ann_amt", required = true, type = Double.class, nillable = true)
    protected Double annAmt;
    protected String unit;

    /**
     * Gets the value of the irrYear property.
     * 
     */
    public short getIrrYear() {
        return irrYear;
    }

    /**
     * Sets the value of the irrYear property.
     * 
     */
    public void setIrrYear(short value) {
        this.irrYear = value;
    }

    /**
     * Gets the value of the fdu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFdu() {
        return fdu;
    }

    /**
     * Sets the value of the fdu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFdu(String value) {
        this.fdu = value;
    }

    /**
     * Gets the value of the ldu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLdu() {
        return ldu;
    }

    /**
     * Sets the value of the ldu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLdu(String value) {
        this.ldu = value;
    }

    /**
     * Gets the value of the dwc property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getDwc() {
        return dwc;
    }

    /**
     * Sets the value of the dwc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setDwc(Short value) {
        this.dwc = value;
    }

    /**
     * Gets the value of the maxq property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaxq() {
        return maxq;
    }

    /**
     * Sets the value of the maxq property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaxq(Double value) {
        this.maxq = value;
    }

    /**
     * Gets the value of the maxqDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxqDate() {
        return maxqDate;
    }

    /**
     * Sets the value of the maxqDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxqDate(String value) {
        this.maxqDate = value;
    }

    /**
     * Gets the value of the nobs property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNobs() {
        return nobs;
    }

    /**
     * Sets the value of the nobs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNobs(Short value) {
        this.nobs = value;
    }

    /**
     * Gets the value of the nus property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNus() {
        return nus;
    }

    /**
     * Sets the value of the nus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNus(Short value) {
        this.nus = value;
    }

    /**
     * Gets the value of the annAmt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAnnAmt() {
        return annAmt;
    }

    /**
     * Sets the value of the annAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAnnAmt(Double value) {
        this.annAmt = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

}
