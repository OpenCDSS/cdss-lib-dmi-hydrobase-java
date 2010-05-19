
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
 *         &lt;element name="GetHBGuestGroundWaterWellsVolcanicsByWellNumberResult" type="{http://www.dwr.state.co.us/}ArrayOfGroundWaterWellsVolcanics" minOccurs="0"/>
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
    "getHBGuestGroundWaterWellsVolcanicsByWellNumberResult"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsVolcanicsByWellNumberResponse")
public class GetHBGuestGroundWaterWellsVolcanicsByWellNumberResponse {

    @XmlElement(name = "GetHBGuestGroundWaterWellsVolcanicsByWellNumberResult")
    protected ArrayOfGroundWaterWellsVolcanics getHBGuestGroundWaterWellsVolcanicsByWellNumberResult;

    /**
     * Gets the value of the getHBGuestGroundWaterWellsVolcanicsByWellNumberResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGroundWaterWellsVolcanics }
     *     
     */
    public ArrayOfGroundWaterWellsVolcanics getGetHBGuestGroundWaterWellsVolcanicsByWellNumberResult() {
        return getHBGuestGroundWaterWellsVolcanicsByWellNumberResult;
    }

    /**
     * Sets the value of the getHBGuestGroundWaterWellsVolcanicsByWellNumberResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGroundWaterWellsVolcanics }
     *     
     */
    public void setGetHBGuestGroundWaterWellsVolcanicsByWellNumberResult(ArrayOfGroundWaterWellsVolcanics value) {
        this.getHBGuestGroundWaterWellsVolcanicsByWellNumberResult = value;
    }

}
