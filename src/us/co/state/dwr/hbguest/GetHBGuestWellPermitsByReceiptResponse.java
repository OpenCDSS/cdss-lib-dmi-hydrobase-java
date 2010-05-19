
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
 *         &lt;element name="GetHBGuestWellPermitsByReceiptResult" type="{http://www.dwr.state.co.us/}ArrayOfWellPermits" minOccurs="0"/>
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
    "getHBGuestWellPermitsByReceiptResult"
})
@XmlRootElement(name = "GetHBGuestWellPermitsByReceiptResponse")
public class GetHBGuestWellPermitsByReceiptResponse {

    @XmlElement(name = "GetHBGuestWellPermitsByReceiptResult")
    protected ArrayOfWellPermits getHBGuestWellPermitsByReceiptResult;

    /**
     * Gets the value of the getHBGuestWellPermitsByReceiptResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWellPermits }
     *     
     */
    public ArrayOfWellPermits getGetHBGuestWellPermitsByReceiptResult() {
        return getHBGuestWellPermitsByReceiptResult;
    }

    /**
     * Sets the value of the getHBGuestWellPermitsByReceiptResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWellPermits }
     *     
     */
    public void setGetHBGuestWellPermitsByReceiptResult(ArrayOfWellPermits value) {
        this.getHBGuestWellPermitsByReceiptResult = value;
    }

}
