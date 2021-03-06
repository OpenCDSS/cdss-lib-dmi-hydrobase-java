
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="div" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="measType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "div",
    "measType"
})
@XmlRootElement(name = "GetHBGuestStationGeolocMeasTypeByDIV")
public class GetHBGuestStationGeolocMeasTypeByDIV {

    protected int div;
    protected String measType;

    /**
     * Gets the value of the div property.
     * 
     */
    public int getDiv() {
        return div;
    }

    /**
     * Sets the value of the div property.
     * 
     */
    public void setDiv(int value) {
        this.div = value;
    }

    /**
     * Gets the value of the measType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasType() {
        return measType;
    }

    /**
     * Sets the value of the measType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasType(String value) {
        this.measType = value;
    }

}
