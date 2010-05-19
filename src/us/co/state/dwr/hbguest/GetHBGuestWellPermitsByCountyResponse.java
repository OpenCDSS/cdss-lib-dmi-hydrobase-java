
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
 *         &lt;element name="GetHBGuestWellPermitsByCountyResult" type="{http://www.dwr.state.co.us/}ArrayOfWellPermits" minOccurs="0"/>
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
    "getHBGuestWellPermitsByCountyResult"
})
@XmlRootElement(name = "GetHBGuestWellPermitsByCountyResponse")
public class GetHBGuestWellPermitsByCountyResponse {

    @XmlElement(name = "GetHBGuestWellPermitsByCountyResult")
    protected ArrayOfWellPermits getHBGuestWellPermitsByCountyResult;

    /**
     * Gets the value of the getHBGuestWellPermitsByCountyResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWellPermits }
     *     
     */
    public ArrayOfWellPermits getGetHBGuestWellPermitsByCountyResult() {
        return getHBGuestWellPermitsByCountyResult;
    }

    /**
     * Sets the value of the getHBGuestWellPermitsByCountyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWellPermits }
     *     
     */
    public void setGetHBGuestWellPermitsByCountyResult(ArrayOfWellPermits value) {
        this.getHBGuestWellPermitsByCountyResult = value;
    }

}
