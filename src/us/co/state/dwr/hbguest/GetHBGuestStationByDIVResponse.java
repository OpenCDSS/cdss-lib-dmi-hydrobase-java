
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
 *         &lt;element name="GetHBGuestStationByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfStation" minOccurs="0"/>
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
    "getHBGuestStationByDIVResult"
})
@XmlRootElement(name = "GetHBGuestStationByDIVResponse")
public class GetHBGuestStationByDIVResponse {

    @XmlElement(name = "GetHBGuestStationByDIVResult")
    protected ArrayOfStation getHBGuestStationByDIVResult;

    /**
     * Gets the value of the getHBGuestStationByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStation }
     *     
     */
    public ArrayOfStation getGetHBGuestStationByDIVResult() {
        return getHBGuestStationByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestStationByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStation }
     *     
     */
    public void setGetHBGuestStationByDIVResult(ArrayOfStation value) {
        this.getHBGuestStationByDIVResult = value;
    }

}
