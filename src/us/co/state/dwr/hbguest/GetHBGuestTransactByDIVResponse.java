
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
 *         &lt;element name="GetHBGuestTransactByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfTransact" minOccurs="0"/>
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
    "getHBGuestTransactByDIVResult"
})
@XmlRootElement(name = "GetHBGuestTransactByDIVResponse")
public class GetHBGuestTransactByDIVResponse {

    @XmlElement(name = "GetHBGuestTransactByDIVResult")
    protected ArrayOfTransact getHBGuestTransactByDIVResult;

    /**
     * Gets the value of the getHBGuestTransactByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTransact }
     *     
     */
    public ArrayOfTransact getGetHBGuestTransactByDIVResult() {
        return getHBGuestTransactByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestTransactByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTransact }
     *     
     */
    public void setGetHBGuestTransactByDIVResult(ArrayOfTransact value) {
        this.getHBGuestTransactByDIVResult = value;
    }

}
