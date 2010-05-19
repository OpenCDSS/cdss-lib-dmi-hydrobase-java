
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
 *         &lt;element name="GetHBGuestGroundWaterWellsVolcanicsByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfGroundWaterWellsVolcanics" minOccurs="0"/>
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
    "getHBGuestGroundWaterWellsVolcanicsByDIVResult"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsVolcanicsByDIVResponse")
public class GetHBGuestGroundWaterWellsVolcanicsByDIVResponse {

    @XmlElement(name = "GetHBGuestGroundWaterWellsVolcanicsByDIVResult")
    protected ArrayOfGroundWaterWellsVolcanics getHBGuestGroundWaterWellsVolcanicsByDIVResult;

    /**
     * Gets the value of the getHBGuestGroundWaterWellsVolcanicsByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGroundWaterWellsVolcanics }
     *     
     */
    public ArrayOfGroundWaterWellsVolcanics getGetHBGuestGroundWaterWellsVolcanicsByDIVResult() {
        return getHBGuestGroundWaterWellsVolcanicsByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestGroundWaterWellsVolcanicsByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGroundWaterWellsVolcanics }
     *     
     */
    public void setGetHBGuestGroundWaterWellsVolcanicsByDIVResult(ArrayOfGroundWaterWellsVolcanics value) {
        this.getHBGuestGroundWaterWellsVolcanicsByDIVResult = value;
    }

}
