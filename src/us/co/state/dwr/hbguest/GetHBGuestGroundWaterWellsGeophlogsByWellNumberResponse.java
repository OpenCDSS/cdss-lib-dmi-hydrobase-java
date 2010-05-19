
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
 *         &lt;element name="GetHBGuestGroundWaterWellsGeophlogsByWellNumberResult" type="{http://www.dwr.state.co.us/}ArrayOfGroundWaterWellsGeophlogs" minOccurs="0"/>
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
    "getHBGuestGroundWaterWellsGeophlogsByWellNumberResult"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsGeophlogsByWellNumberResponse")
public class GetHBGuestGroundWaterWellsGeophlogsByWellNumberResponse {

    @XmlElement(name = "GetHBGuestGroundWaterWellsGeophlogsByWellNumberResult")
    protected ArrayOfGroundWaterWellsGeophlogs getHBGuestGroundWaterWellsGeophlogsByWellNumberResult;

    /**
     * Gets the value of the getHBGuestGroundWaterWellsGeophlogsByWellNumberResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGroundWaterWellsGeophlogs }
     *     
     */
    public ArrayOfGroundWaterWellsGeophlogs getGetHBGuestGroundWaterWellsGeophlogsByWellNumberResult() {
        return getHBGuestGroundWaterWellsGeophlogsByWellNumberResult;
    }

    /**
     * Sets the value of the getHBGuestGroundWaterWellsGeophlogsByWellNumberResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGroundWaterWellsGeophlogs }
     *     
     */
    public void setGetHBGuestGroundWaterWellsGeophlogsByWellNumberResult(ArrayOfGroundWaterWellsGeophlogs value) {
        this.getHBGuestGroundWaterWellsGeophlogsByWellNumberResult = value;
    }

}
