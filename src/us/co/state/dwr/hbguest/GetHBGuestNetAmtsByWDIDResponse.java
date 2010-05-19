
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
 *         &lt;element name="GetHBGuestNetAmtsByWDIDResult" type="{http://www.dwr.state.co.us/}ArrayOfNetAmts" minOccurs="0"/>
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
    "getHBGuestNetAmtsByWDIDResult"
})
@XmlRootElement(name = "GetHBGuestNetAmtsByWDIDResponse")
public class GetHBGuestNetAmtsByWDIDResponse {

    @XmlElement(name = "GetHBGuestNetAmtsByWDIDResult")
    protected ArrayOfNetAmts getHBGuestNetAmtsByWDIDResult;

    /**
     * Gets the value of the getHBGuestNetAmtsByWDIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfNetAmts }
     *     
     */
    public ArrayOfNetAmts getGetHBGuestNetAmtsByWDIDResult() {
        return getHBGuestNetAmtsByWDIDResult;
    }

    /**
     * Sets the value of the getHBGuestNetAmtsByWDIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfNetAmts }
     *     
     */
    public void setGetHBGuestNetAmtsByWDIDResult(ArrayOfNetAmts value) {
        this.getHBGuestNetAmtsByWDIDResult = value;
    }

}
