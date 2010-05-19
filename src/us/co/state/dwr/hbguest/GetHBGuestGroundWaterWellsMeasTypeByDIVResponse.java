
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
 *         &lt;element name="GetHBGuestGroundWaterWellsMeasTypeByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfGroundWaterWellsMeasType" minOccurs="0"/>
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
    "getHBGuestGroundWaterWellsMeasTypeByDIVResult"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsMeasTypeByDIVResponse")
public class GetHBGuestGroundWaterWellsMeasTypeByDIVResponse {

    @XmlElement(name = "GetHBGuestGroundWaterWellsMeasTypeByDIVResult")
    protected ArrayOfGroundWaterWellsMeasType getHBGuestGroundWaterWellsMeasTypeByDIVResult;

    /**
     * Gets the value of the getHBGuestGroundWaterWellsMeasTypeByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGroundWaterWellsMeasType }
     *     
     */
    public ArrayOfGroundWaterWellsMeasType getGetHBGuestGroundWaterWellsMeasTypeByDIVResult() {
        return getHBGuestGroundWaterWellsMeasTypeByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestGroundWaterWellsMeasTypeByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGroundWaterWellsMeasType }
     *     
     */
    public void setGetHBGuestGroundWaterWellsMeasTypeByDIVResult(ArrayOfGroundWaterWellsMeasType value) {
        this.getHBGuestGroundWaterWellsMeasTypeByDIVResult = value;
    }

}
