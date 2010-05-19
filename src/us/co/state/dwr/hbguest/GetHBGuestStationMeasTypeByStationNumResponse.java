
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
 *         &lt;element name="GetHBGuestStationMeasTypeByStationNumResult" type="{http://www.dwr.state.co.us/}ArrayOfStationMeasType" minOccurs="0"/>
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
    "getHBGuestStationMeasTypeByStationNumResult"
})
@XmlRootElement(name = "GetHBGuestStationMeasTypeByStationNumResponse")
public class GetHBGuestStationMeasTypeByStationNumResponse {

    @XmlElement(name = "GetHBGuestStationMeasTypeByStationNumResult")
    protected ArrayOfStationMeasType getHBGuestStationMeasTypeByStationNumResult;

    /**
     * Gets the value of the getHBGuestStationMeasTypeByStationNumResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStationMeasType }
     *     
     */
    public ArrayOfStationMeasType getGetHBGuestStationMeasTypeByStationNumResult() {
        return getHBGuestStationMeasTypeByStationNumResult;
    }

    /**
     * Sets the value of the getHBGuestStationMeasTypeByStationNumResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStationMeasType }
     *     
     */
    public void setGetHBGuestStationMeasTypeByStationNumResult(ArrayOfStationMeasType value) {
        this.getHBGuestStationMeasTypeByStationNumResult = value;
    }

}
