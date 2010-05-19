
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
 *         &lt;element name="GetHBGuestStationTSResult" type="{http://www.dwr.state.co.us/}ArrayOfStationTS" minOccurs="0"/>
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
    "getHBGuestStationTSResult"
})
@XmlRootElement(name = "GetHBGuestStationTSResponse")
public class GetHBGuestStationTSResponse {

    @XmlElement(name = "GetHBGuestStationTSResult")
    protected ArrayOfStationTS getHBGuestStationTSResult;

    /**
     * Gets the value of the getHBGuestStationTSResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStationTS }
     *     
     */
    public ArrayOfStationTS getGetHBGuestStationTSResult() {
        return getHBGuestStationTSResult;
    }

    /**
     * Sets the value of the getHBGuestStationTSResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStationTS }
     *     
     */
    public void setGetHBGuestStationTSResult(ArrayOfStationTS value) {
        this.getHBGuestStationTSResult = value;
    }

}
