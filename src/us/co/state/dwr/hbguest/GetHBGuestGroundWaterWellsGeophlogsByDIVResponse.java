
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
 *         &lt;element name="GetHBGuestGroundWaterWellsGeophlogsByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfGroundWaterWellsGeophlogs" minOccurs="0"/>
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
    "getHBGuestGroundWaterWellsGeophlogsByDIVResult"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsGeophlogsByDIVResponse")
public class GetHBGuestGroundWaterWellsGeophlogsByDIVResponse {

    @XmlElement(name = "GetHBGuestGroundWaterWellsGeophlogsByDIVResult")
    protected ArrayOfGroundWaterWellsGeophlogs getHBGuestGroundWaterWellsGeophlogsByDIVResult;

    /**
     * Gets the value of the getHBGuestGroundWaterWellsGeophlogsByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGroundWaterWellsGeophlogs }
     *     
     */
    public ArrayOfGroundWaterWellsGeophlogs getGetHBGuestGroundWaterWellsGeophlogsByDIVResult() {
        return getHBGuestGroundWaterWellsGeophlogsByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestGroundWaterWellsGeophlogsByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGroundWaterWellsGeophlogs }
     *     
     */
    public void setGetHBGuestGroundWaterWellsGeophlogsByDIVResult(ArrayOfGroundWaterWellsGeophlogs value) {
        this.getHBGuestGroundWaterWellsGeophlogsByDIVResult = value;
    }

}
