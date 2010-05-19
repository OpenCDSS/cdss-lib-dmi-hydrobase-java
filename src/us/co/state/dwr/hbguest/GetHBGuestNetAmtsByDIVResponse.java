
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
 *         &lt;element name="GetHBGuestNetAmtsByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfNetAmts" minOccurs="0"/>
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
    "getHBGuestNetAmtsByDIVResult"
})
@XmlRootElement(name = "GetHBGuestNetAmtsByDIVResponse")
public class GetHBGuestNetAmtsByDIVResponse {

    @XmlElement(name = "GetHBGuestNetAmtsByDIVResult")
    protected ArrayOfNetAmts getHBGuestNetAmtsByDIVResult;

    /**
     * Gets the value of the getHBGuestNetAmtsByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfNetAmts }
     *     
     */
    public ArrayOfNetAmts getGetHBGuestNetAmtsByDIVResult() {
        return getHBGuestNetAmtsByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestNetAmtsByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfNetAmts }
     *     
     */
    public void setGetHBGuestNetAmtsByDIVResult(ArrayOfNetAmts value) {
        this.getHBGuestNetAmtsByDIVResult = value;
    }

}
