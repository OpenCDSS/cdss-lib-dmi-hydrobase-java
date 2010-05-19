
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
 *         &lt;element name="GetHBGuestGroundWaterWellsMeasTypeByWDResult" type="{http://www.dwr.state.co.us/}ArrayOfGroundWaterWellsMeasType" minOccurs="0"/>
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
    "getHBGuestGroundWaterWellsMeasTypeByWDResult"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsMeasTypeByWDResponse")
public class GetHBGuestGroundWaterWellsMeasTypeByWDResponse {

    @XmlElement(name = "GetHBGuestGroundWaterWellsMeasTypeByWDResult")
    protected ArrayOfGroundWaterWellsMeasType getHBGuestGroundWaterWellsMeasTypeByWDResult;

    /**
     * Gets the value of the getHBGuestGroundWaterWellsMeasTypeByWDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGroundWaterWellsMeasType }
     *     
     */
    public ArrayOfGroundWaterWellsMeasType getGetHBGuestGroundWaterWellsMeasTypeByWDResult() {
        return getHBGuestGroundWaterWellsMeasTypeByWDResult;
    }

    /**
     * Sets the value of the getHBGuestGroundWaterWellsMeasTypeByWDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGroundWaterWellsMeasType }
     *     
     */
    public void setGetHBGuestGroundWaterWellsMeasTypeByWDResult(ArrayOfGroundWaterWellsMeasType value) {
        this.getHBGuestGroundWaterWellsMeasTypeByWDResult = value;
    }

}
