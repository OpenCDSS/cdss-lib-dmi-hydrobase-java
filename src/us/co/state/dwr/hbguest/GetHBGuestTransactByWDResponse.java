
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
 *         &lt;element name="GetHBGuestTransactByWDResult" type="{http://www.dwr.state.co.us/}ArrayOfTransact" minOccurs="0"/>
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
    "getHBGuestTransactByWDResult"
})
@XmlRootElement(name = "GetHBGuestTransactByWDResponse")
public class GetHBGuestTransactByWDResponse {

    @XmlElement(name = "GetHBGuestTransactByWDResult")
    protected ArrayOfTransact getHBGuestTransactByWDResult;

    /**
     * Gets the value of the getHBGuestTransactByWDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTransact }
     *     
     */
    public ArrayOfTransact getGetHBGuestTransactByWDResult() {
        return getHBGuestTransactByWDResult;
    }

    /**
     * Sets the value of the getHBGuestTransactByWDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTransact }
     *     
     */
    public void setGetHBGuestTransactByWDResult(ArrayOfTransact value) {
        this.getHBGuestTransactByWDResult = value;
    }

}
