
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
 *         &lt;element name="GetHBGuestStructureGeolocMeasTypeByWDResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureGeolocMeasType" minOccurs="0"/>
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
    "getHBGuestStructureGeolocMeasTypeByWDResult"
})
@XmlRootElement(name = "GetHBGuestStructureGeolocMeasTypeByWDResponse")
public class GetHBGuestStructureGeolocMeasTypeByWDResponse {

    @XmlElement(name = "GetHBGuestStructureGeolocMeasTypeByWDResult")
    protected ArrayOfStructureGeolocMeasType getHBGuestStructureGeolocMeasTypeByWDResult;

    /**
     * Gets the value of the getHBGuestStructureGeolocMeasTypeByWDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureGeolocMeasType }
     *     
     */
    public ArrayOfStructureGeolocMeasType getGetHBGuestStructureGeolocMeasTypeByWDResult() {
        return getHBGuestStructureGeolocMeasTypeByWDResult;
    }

    /**
     * Sets the value of the getHBGuestStructureGeolocMeasTypeByWDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureGeolocMeasType }
     *     
     */
    public void setGetHBGuestStructureGeolocMeasTypeByWDResult(ArrayOfStructureGeolocMeasType value) {
        this.getHBGuestStructureGeolocMeasTypeByWDResult = value;
    }

}
