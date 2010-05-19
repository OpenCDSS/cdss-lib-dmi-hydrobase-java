
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="GetHBGuestGroundWaterWellsDrillersksumByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfGroundWaterWellsDrillersksum" minOccurs="0"/>
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
    "getHBGuestGroundWaterWellsDrillersksumByDIVResult"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsDrillersksumByDIVResponse")
public class GetHBGuestGroundWaterWellsDrillersksumByDIVResponse {

    @XmlElement(name = "GetHBGuestGroundWaterWellsDrillersksumByDIVResult")
    protected ArrayOfGroundWaterWellsDrillersksum getHBGuestGroundWaterWellsDrillersksumByDIVResult;

    /**
     * Gets the value of the getHBGuestGroundWaterWellsDrillersksumByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGroundWaterWellsDrillersksum }
     *     
     */
    public ArrayOfGroundWaterWellsDrillersksum getGetHBGuestGroundWaterWellsDrillersksumByDIVResult() {
        return getHBGuestGroundWaterWellsDrillersksumByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestGroundWaterWellsDrillersksumByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGroundWaterWellsDrillersksum }
     *     
     */
    public void setGetHBGuestGroundWaterWellsDrillersksumByDIVResult(ArrayOfGroundWaterWellsDrillersksum value) {
        this.getHBGuestGroundWaterWellsDrillersksumByDIVResult = value;
    }

}
