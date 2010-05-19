
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
 *         &lt;element name="GetHBGuestGroundWaterWellsPumpingTestsByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfGroundWaterWellsPumpingTests" minOccurs="0"/>
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
    "getHBGuestGroundWaterWellsPumpingTestsByDIVResult"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsPumpingTestsByDIVResponse")
public class GetHBGuestGroundWaterWellsPumpingTestsByDIVResponse {

    @XmlElement(name = "GetHBGuestGroundWaterWellsPumpingTestsByDIVResult")
    protected ArrayOfGroundWaterWellsPumpingTests getHBGuestGroundWaterWellsPumpingTestsByDIVResult;

    /**
     * Gets the value of the getHBGuestGroundWaterWellsPumpingTestsByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGroundWaterWellsPumpingTests }
     *     
     */
    public ArrayOfGroundWaterWellsPumpingTests getGetHBGuestGroundWaterWellsPumpingTestsByDIVResult() {
        return getHBGuestGroundWaterWellsPumpingTestsByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestGroundWaterWellsPumpingTestsByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGroundWaterWellsPumpingTests }
     *     
     */
    public void setGetHBGuestGroundWaterWellsPumpingTestsByDIVResult(ArrayOfGroundWaterWellsPumpingTests value) {
        this.getHBGuestGroundWaterWellsPumpingTestsByDIVResult = value;
    }

}
