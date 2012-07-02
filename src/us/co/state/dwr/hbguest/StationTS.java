
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StationTS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StationTS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="meas_num" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="meas_date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="flagA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flagB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StationTS", propOrder = {
    "measNum",
    "measDate",
    "amt",
    "flagA",
    "flagB"
})
public class StationTS {

    @XmlElement(name = "meas_num")
    protected int measNum;
    @XmlElement(name = "meas_date")
    protected String measDate;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double amt;
    protected String flagA;
    protected String flagB;

    /**
     * Gets the value of the measNum property.
     * 
     */
    public int getMeasNum() {
        return measNum;
    }

    /**
     * Sets the value of the measNum property.
     * 
     */
    public void setMeasNum(int value) {
        this.measNum = value;
    }

    /**
     * Gets the value of the measDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasDate() {
        return measDate;
    }

    /**
     * Sets the value of the measDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasDate(String value) {
        this.measDate = value;
    }

    /**
     * Gets the value of the amt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAmt(Double value) {
        this.amt = value;
    }

    /**
     * Gets the value of the flagA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagA() {
        return flagA;
    }

    /**
     * Sets the value of the flagA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagA(String value) {
        this.flagA = value;
    }

    /**
     * Gets the value of the flagB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagB() {
        return flagB;
    }

    /**
     * Sets the value of the flagB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagB(String value) {
        this.flagB = value;
    }

}
