
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
 *         &lt;element name="GetHBGuestStructureByWDIDResult" type="{http://www.dwr.state.co.us/}ArrayOfStructure" minOccurs="0"/>
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
    "getHBGuestStructureByWDIDResult"
})
@XmlRootElement(name = "GetHBGuestStructureByWDIDResponse")
public class GetHBGuestStructureByWDIDResponse {

    @XmlElement(name = "GetHBGuestStructureByWDIDResult")
    protected ArrayOfStructure getHBGuestStructureByWDIDResult;

    /**
     * Gets the value of the getHBGuestStructureByWDIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructure }
     *     
     */
    public ArrayOfStructure getGetHBGuestStructureByWDIDResult() {
        return getHBGuestStructureByWDIDResult;
    }

    /**
     * Sets the value of the getHBGuestStructureByWDIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructure }
     *     
     */
    public void setGetHBGuestStructureByWDIDResult(ArrayOfStructure value) {
        this.getHBGuestStructureByWDIDResult = value;
    }

}
