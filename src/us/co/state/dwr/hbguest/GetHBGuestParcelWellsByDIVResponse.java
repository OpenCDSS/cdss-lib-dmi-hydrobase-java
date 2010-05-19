
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
 *         &lt;element name="GetHBGuestParcelWellsByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfParcelWells" minOccurs="0"/>
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
    "getHBGuestParcelWellsByDIVResult"
})
@XmlRootElement(name = "GetHBGuestParcelWellsByDIVResponse")
public class GetHBGuestParcelWellsByDIVResponse {

    @XmlElement(name = "GetHBGuestParcelWellsByDIVResult")
    protected ArrayOfParcelWells getHBGuestParcelWellsByDIVResult;

    /**
     * Gets the value of the getHBGuestParcelWellsByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfParcelWells }
     *     
     */
    public ArrayOfParcelWells getGetHBGuestParcelWellsByDIVResult() {
        return getHBGuestParcelWellsByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestParcelWellsByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfParcelWells }
     *     
     */
    public void setGetHBGuestParcelWellsByDIVResult(ArrayOfParcelWells value) {
        this.getHBGuestParcelWellsByDIVResult = value;
    }

}
