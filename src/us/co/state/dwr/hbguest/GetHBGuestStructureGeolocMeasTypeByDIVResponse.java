
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
 *         &lt;element name="GetHBGuestStructureGeolocMeasTypeByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureGeolocMeasType" minOccurs="0"/>
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
    "getHBGuestStructureGeolocMeasTypeByDIVResult"
})
@XmlRootElement(name = "GetHBGuestStructureGeolocMeasTypeByDIVResponse")
public class GetHBGuestStructureGeolocMeasTypeByDIVResponse {

    @XmlElement(name = "GetHBGuestStructureGeolocMeasTypeByDIVResult")
    protected ArrayOfStructureGeolocMeasType getHBGuestStructureGeolocMeasTypeByDIVResult;

    /**
     * Gets the value of the getHBGuestStructureGeolocMeasTypeByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureGeolocMeasType }
     *     
     */
    public ArrayOfStructureGeolocMeasType getGetHBGuestStructureGeolocMeasTypeByDIVResult() {
        return getHBGuestStructureGeolocMeasTypeByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestStructureGeolocMeasTypeByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureGeolocMeasType }
     *     
     */
    public void setGetHBGuestStructureGeolocMeasTypeByDIVResult(ArrayOfStructureGeolocMeasType value) {
        this.getHBGuestStructureGeolocMeasTypeByDIVResult = value;
    }

}
