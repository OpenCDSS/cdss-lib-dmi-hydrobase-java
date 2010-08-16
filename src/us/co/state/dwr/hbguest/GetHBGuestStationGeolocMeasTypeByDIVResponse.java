
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
 *         &lt;element name="GetHBGuestStationGeolocMeasTypeByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfStationGeolocMeasType" minOccurs="0"/>
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
    "getHBGuestStationGeolocMeasTypeByDIVResult"
})
@XmlRootElement(name = "GetHBGuestStationGeolocMeasTypeByDIVResponse")
public class GetHBGuestStationGeolocMeasTypeByDIVResponse {

    @XmlElement(name = "GetHBGuestStationGeolocMeasTypeByDIVResult")
    protected ArrayOfStationGeolocMeasType getHBGuestStationGeolocMeasTypeByDIVResult;

    /**
     * Gets the value of the getHBGuestStationGeolocMeasTypeByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStationGeolocMeasType }
     *     
     */
    public ArrayOfStationGeolocMeasType getGetHBGuestStationGeolocMeasTypeByDIVResult() {
        return getHBGuestStationGeolocMeasTypeByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestStationGeolocMeasTypeByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStationGeolocMeasType }
     *     
     */
    public void setGetHBGuestStationGeolocMeasTypeByDIVResult(ArrayOfStationGeolocMeasType value) {
        this.getHBGuestStationGeolocMeasTypeByDIVResult = value;
    }

}
