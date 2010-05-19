
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
 *         &lt;element name="GetHBGuestStructureMonthlyTSByWDIDResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureMonthlyTS" minOccurs="0"/>
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
    "getHBGuestStructureMonthlyTSByWDIDResult"
})
@XmlRootElement(name = "GetHBGuestStructureMonthlyTSByWDIDResponse")
public class GetHBGuestStructureMonthlyTSByWDIDResponse {

    @XmlElement(name = "GetHBGuestStructureMonthlyTSByWDIDResult")
    protected ArrayOfStructureMonthlyTS getHBGuestStructureMonthlyTSByWDIDResult;

    /**
     * Gets the value of the getHBGuestStructureMonthlyTSByWDIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureMonthlyTS }
     *     
     */
    public ArrayOfStructureMonthlyTS getGetHBGuestStructureMonthlyTSByWDIDResult() {
        return getHBGuestStructureMonthlyTSByWDIDResult;
    }

    /**
     * Sets the value of the getHBGuestStructureMonthlyTSByWDIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureMonthlyTS }
     *     
     */
    public void setGetHBGuestStructureMonthlyTSByWDIDResult(ArrayOfStructureMonthlyTS value) {
        this.getHBGuestStructureMonthlyTSByWDIDResult = value;
    }

}
