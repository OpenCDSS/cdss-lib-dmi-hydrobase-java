
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WaterDistrict complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WaterDistrict">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="div" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="wd" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="wd_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WaterDistrict", propOrder = {
    "div",
    "wd",
    "wdName"
})
public class WaterDistrict {

    protected short div;
    protected short wd;
    @XmlElement(name = "wd_name")
    protected String wdName;

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
     * Gets the value of the wdName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWdName() {
        return wdName;
    }

    /**
     * Sets the value of the wdName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWdName(String value) {
        this.wdName = value;
    }

}
