
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
 *         &lt;element name="GetHBGuestStationByWDResult" type="{http://www.dwr.state.co.us/}ArrayOfStation" minOccurs="0"/>
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
    "getHBGuestStationByWDResult"
})
@XmlRootElement(name = "GetHBGuestStationByWDResponse")
public class GetHBGuestStationByWDResponse {

    @XmlElement(name = "GetHBGuestStationByWDResult")
    protected ArrayOfStation getHBGuestStationByWDResult;

    /**
     * Gets the value of the getHBGuestStationByWDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStation }
     *     
     */
    public ArrayOfStation getGetHBGuestStationByWDResult() {
        return getHBGuestStationByWDResult;
    }

    /**
     * Sets the value of the getHBGuestStationByWDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStation }
     *     
     */
    public void setGetHBGuestStationByWDResult(ArrayOfStation value) {
        this.getHBGuestStationByWDResult = value;
    }

}
