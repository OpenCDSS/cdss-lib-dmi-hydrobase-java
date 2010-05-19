
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
 *         &lt;element name="GetHBGuestWaterDistrictResult" type="{http://www.dwr.state.co.us/}ArrayOfWaterDistrict" minOccurs="0"/>
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
    "getHBGuestWaterDistrictResult"
})
@XmlRootElement(name = "GetHBGuestWaterDistrictResponse")
public class GetHBGuestWaterDistrictResponse {

    @XmlElement(name = "GetHBGuestWaterDistrictResult")
    protected ArrayOfWaterDistrict getHBGuestWaterDistrictResult;

    /**
     * Gets the value of the getHBGuestWaterDistrictResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWaterDistrict }
     *     
     */
    public ArrayOfWaterDistrict getGetHBGuestWaterDistrictResult() {
        return getHBGuestWaterDistrictResult;
    }

    /**
     * Sets the value of the getHBGuestWaterDistrictResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWaterDistrict }
     *     
     */
    public void setGetHBGuestWaterDistrictResult(ArrayOfWaterDistrict value) {
        this.getHBGuestWaterDistrictResult = value;
    }

}
