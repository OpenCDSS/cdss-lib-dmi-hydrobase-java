
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
    @XmlElement(name = "gage_height")
    protected double gageHeight;
    @XmlElement(name = "storage_amt")
    protected double storageAmt;
    @XmlElement(name = "fill_amt")
    protected double fillAmt;
    @XmlElement(name = "release_amt")
    protected double releaseAmt;
    @XmlElement(name = "evap_loss_amt")
    protected double evapLossAmt;

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
     */
    public double getGageHeight() {
        return gageHeight;
    }

    /**
     * Sets the value of the gageHeight property.
     * 
     */
    public void setGageHeight(double value) {
        this.gageHeight = value;
    }

    /**
     * Gets the value of the storageAmt property.
     * 
     */
    public double getStorageAmt() {
        return storageAmt;
    }

    /**
     * Sets the value of the storageAmt property.
     * 
     */
    public void setStorageAmt(double value) {
        this.storageAmt = value;
    }

    /**
     * Gets the value of the fillAmt property.
     * 
     */
    public double getFillAmt() {
        return fillAmt;
    }

    /**
     * Sets the value of the fillAmt property.
     * 
     */
    public void setFillAmt(double value) {
        this.fillAmt = value;
    }

    /**
     * Gets the value of the releaseAmt property.
     * 
     */
    public double getReleaseAmt() {
        return releaseAmt;
    }

    /**
     * Sets the value of the releaseAmt property.
     * 
     */
    public void setReleaseAmt(double value) {
        this.releaseAmt = value;
    }

    /**
     * Gets the value of the evapLossAmt property.
     * 
     */
    public double getEvapLossAmt() {
        return evapLossAmt;
    }

    /**
     * Sets the value of the evapLossAmt property.
     * 
     */
    public void setEvapLossAmt(double value) {
        this.evapLossAmt = value;
    }

}
