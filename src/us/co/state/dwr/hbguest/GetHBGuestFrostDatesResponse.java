
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
 *         &lt;element name="GetHBGuestFrostDatesResult" type="{http://www.dwr.state.co.us/}ArrayOfFrostDates" minOccurs="0"/>
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
    "getHBGuestFrostDatesResult"
})
@XmlRootElement(name = "GetHBGuestFrostDatesResponse")
public class GetHBGuestFrostDatesResponse {

    @XmlElement(name = "GetHBGuestFrostDatesResult")
    protected ArrayOfFrostDates getHBGuestFrostDatesResult;

    /**
     * Gets the value of the getHBGuestFrostDatesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFrostDates }
     *     
     */
    public ArrayOfFrostDates getGetHBGuestFrostDatesResult() {
        return getHBGuestFrostDatesResult;
    }

    /**
     * Sets the value of the getHBGuestFrostDatesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFrostDates }
     *     
     */
    public void setGetHBGuestFrostDatesResult(ArrayOfFrostDates value) {
        this.getHBGuestFrostDatesResult = value;
    }

}
