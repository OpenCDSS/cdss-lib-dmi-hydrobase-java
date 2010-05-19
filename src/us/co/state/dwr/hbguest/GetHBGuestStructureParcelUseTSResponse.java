
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
 *         &lt;element name="GetHBGuestStructureParcelUseTSResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureParcelUseTS" minOccurs="0"/>
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
    "getHBGuestStructureParcelUseTSResult"
})
@XmlRootElement(name = "GetHBGuestStructureParcelUseTSResponse")
public class GetHBGuestStructureParcelUseTSResponse {

    @XmlElement(name = "GetHBGuestStructureParcelUseTSResult")
    protected ArrayOfStructureParcelUseTS getHBGuestStructureParcelUseTSResult;

    /**
     * Gets the value of the getHBGuestStructureParcelUseTSResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureParcelUseTS }
     *     
     */
    public ArrayOfStructureParcelUseTS getGetHBGuestStructureParcelUseTSResult() {
        return getHBGuestStructureParcelUseTSResult;
    }

    /**
     * Sets the value of the getHBGuestStructureParcelUseTSResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureParcelUseTS }
     *     
     */
    public void setGetHBGuestStructureParcelUseTSResult(ArrayOfStructureParcelUseTS value) {
        this.getHBGuestStructureParcelUseTSResult = value;
    }

}
