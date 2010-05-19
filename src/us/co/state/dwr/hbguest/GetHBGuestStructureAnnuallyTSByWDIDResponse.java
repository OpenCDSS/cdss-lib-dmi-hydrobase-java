
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
 *         &lt;element name="GetHBGuestStructureAnnuallyTSByWDIDResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureAnnuallyTS" minOccurs="0"/>
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
    "getHBGuestStructureAnnuallyTSByWDIDResult"
})
@XmlRootElement(name = "GetHBGuestStructureAnnuallyTSByWDIDResponse")
public class GetHBGuestStructureAnnuallyTSByWDIDResponse {

    @XmlElement(name = "GetHBGuestStructureAnnuallyTSByWDIDResult")
    protected ArrayOfStructureAnnuallyTS getHBGuestStructureAnnuallyTSByWDIDResult;

    /**
     * Gets the value of the getHBGuestStructureAnnuallyTSByWDIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureAnnuallyTS }
     *     
     */
    public ArrayOfStructureAnnuallyTS getGetHBGuestStructureAnnuallyTSByWDIDResult() {
        return getHBGuestStructureAnnuallyTSByWDIDResult;
    }

    /**
     * Sets the value of the getHBGuestStructureAnnuallyTSByWDIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureAnnuallyTS }
     *     
     */
    public void setGetHBGuestStructureAnnuallyTSByWDIDResult(ArrayOfStructureAnnuallyTS value) {
        this.getHBGuestStructureAnnuallyTSByWDIDResult = value;
    }

}
