
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StructureResMeas complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StructureResMeas">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wdid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gage_height" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="storage_amt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="fill_amt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="release_amt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="evap_loss_amt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructureResMeas", propOrder = {
    "wdid",
    "dateTime",
    "gageHeight",
    "storageAmt",
    "fillAmt",
    "releaseAmt",
    "evapLossAmt"
})
public class StructureResMeas {

    protected String wdid;
    @XmlElement(name = "date_time")
    protected String dateTime;
    @XmlElement(name = "gage_height", required = true, type = Double.class, nillable = true)
    protected Double gageHeight;
    @XmlElement(name = "storage_amt", required = true, type = Double.class, nillable = true)
    protected Double storageAmt;
    @XmlElement(name = "fill_amt", required = true, type = Double.class, nillable = true)
    protected Double fillAmt;
    @XmlElement(name = "release_amt", required = true, type = Double.class, nillable = true)
    protected Double releaseAmt;
    @XmlElement(name = "evap_loss_amt", required = true, type = Double.class, nillable = true)
    protected Double evapLossAmt;

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
     * Gets the value of the dateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateTime(String value) {
        this.dateTime = value;
    }

    /**
     * Gets the value of the gageHeight property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getGageHeight() {
        return gageHeight;
    }

    /**
     * Sets the value of the gageHeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setGageHeight(Double value) {
        this.gageHeight = value;
    }

    /**
     * Gets the value of the storageAmt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getStorageAmt() {
        return storageAmt;
    }

    /**
     * Sets the value of the storageAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setStorageAmt(Double value) {
        this.storageAmt = value;
    }

    /**
     * Gets the value of the fillAmt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFillAmt() {
        return fillAmt;
    }

    /**
     * Sets the value of the fillAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFillAmt(Double value) {
        this.fillAmt = value;
    }

    /**
     * Gets the value of the releaseAmt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getReleaseAmt() {
        return releaseAmt;
    }

    /**
     * Sets the value of the releaseAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setReleaseAmt(Double value) {
        this.releaseAmt = value;
    }

    /**
     * Gets the value of the evapLossAmt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEvapLossAmt() {
        return evapLossAmt;
    }

    /**
     * Sets the value of the evapLossAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEvapLossAmt(Double value) {
        this.evapLossAmt = value;
    }

}
