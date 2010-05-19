
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
 *         &lt;element name="GetHBGuestStructureDailyTSByWDIDResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureDailyTS" minOccurs="0"/>
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
    "getHBGuestStructureDailyTSByWDIDResult"
})
@XmlRootElement(name = "GetHBGuestStructureDailyTSByWDIDResponse")
public class GetHBGuestStructureDailyTSByWDIDResponse {

    @XmlElement(name = "GetHBGuestStructureDailyTSByWDIDResult")
    protected ArrayOfStructureDailyTS getHBGuestStructureDailyTSByWDIDResult;

    /**
     * Gets the value of the getHBGuestStructureDailyTSByWDIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureDailyTS }
     *     
     */
    public ArrayOfStructureDailyTS getGetHBGuestStructureDailyTSByWDIDResult() {
        return getHBGuestStructureDailyTSByWDIDResult;
    }

    /**
     * Sets the value of the getHBGuestStructureDailyTSByWDIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureDailyTS }
     *     
     */
    public void setGetHBGuestStructureDailyTSByWDIDResult(ArrayOfStructureDailyTS value) {
        this.getHBGuestStructureDailyTSByWDIDResult = value;
    }

}
