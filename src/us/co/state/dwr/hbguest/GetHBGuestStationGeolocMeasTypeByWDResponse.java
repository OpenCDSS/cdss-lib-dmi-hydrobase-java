
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
 *         &lt;element name="GetHBGuestStationGeolocMeasTypeByWDResult" type="{http://www.dwr.state.co.us/}ArrayOfStationGeolocMeasType" minOccurs="0"/>
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
    "getHBGuestStationGeolocMeasTypeByWDResult"
})
@XmlRootElement(name = "GetHBGuestStationGeolocMeasTypeByWDResponse")
public class GetHBGuestStationGeolocMeasTypeByWDResponse {

    @XmlElement(name = "GetHBGuestStationGeolocMeasTypeByWDResult")
    protected ArrayOfStationGeolocMeasType getHBGuestStationGeolocMeasTypeByWDResult;

    /**
     * Gets the value of the getHBGuestStationGeolocMeasTypeByWDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStationGeolocMeasType }
     *     
     */
    public ArrayOfStationGeolocMeasType getGetHBGuestStationGeolocMeasTypeByWDResult() {
        return getHBGuestStationGeolocMeasTypeByWDResult;
    }

    /**
     * Sets the value of the getHBGuestStationGeolocMeasTypeByWDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStationGeolocMeasType }
     *     
     */
    public void setGetHBGuestStationGeolocMeasTypeByWDResult(ArrayOfStationGeolocMeasType value) {
        this.getHBGuestStationGeolocMeasTypeByWDResult = value;
    }

}
