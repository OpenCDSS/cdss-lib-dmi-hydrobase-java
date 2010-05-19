
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WaterDivision complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WaterDivision">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="div" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="div_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WaterDivision", propOrder = {
    "div",
    "divName"
})
public class WaterDivision {

    protected short div;
    @XmlElement(name = "div_name")
    protected String divName;

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
     * Gets the value of the divName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDivName() {
        return divName;
    }

    /**
     * Sets the value of the divName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDivName(String value) {
        this.divName = value;
    }

}
