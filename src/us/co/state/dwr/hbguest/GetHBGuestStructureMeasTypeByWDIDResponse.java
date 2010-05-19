
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
 *         &lt;element name="GetHBGuestStructureMeasTypeByWDIDResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureMeasType" minOccurs="0"/>
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
    "getHBGuestStructureMeasTypeByWDIDResult"
})
@XmlRootElement(name = "GetHBGuestStructureMeasTypeByWDIDResponse")
public class GetHBGuestStructureMeasTypeByWDIDResponse {

    @XmlElement(name = "GetHBGuestStructureMeasTypeByWDIDResult")
    protected ArrayOfStructureMeasType getHBGuestStructureMeasTypeByWDIDResult;

    /**
     * Gets the value of the getHBGuestStructureMeasTypeByWDIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureMeasType }
     *     
     */
    public ArrayOfStructureMeasType getGetHBGuestStructureMeasTypeByWDIDResult() {
        return getHBGuestStructureMeasTypeByWDIDResult;
    }

    /**
     * Sets the value of the getHBGuestStructureMeasTypeByWDIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureMeasType }
     *     
     */
    public void setGetHBGuestStructureMeasTypeByWDIDResult(ArrayOfStructureMeasType value) {
        this.getHBGuestStructureMeasTypeByWDIDResult = value;
    }

}
