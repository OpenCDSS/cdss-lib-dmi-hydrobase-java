
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
 *         &lt;element name="GetHBGuestStructureDiversionCommentsByMeasNumberResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureDiversionComments" minOccurs="0"/>
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
    "getHBGuestStructureDiversionCommentsByMeasNumberResult"
})
@XmlRootElement(name = "GetHBGuestStructureDiversionCommentsByMeasNumberResponse")
public class GetHBGuestStructureDiversionCommentsByMeasNumberResponse {

    @XmlElement(name = "GetHBGuestStructureDiversionCommentsByMeasNumberResult")
    protected ArrayOfStructureDiversionComments getHBGuestStructureDiversionCommentsByMeasNumberResult;

    /**
     * Gets the value of the getHBGuestStructureDiversionCommentsByMeasNumberResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureDiversionComments }
     *     
     */
    public ArrayOfStructureDiversionComments getGetHBGuestStructureDiversionCommentsByMeasNumberResult() {
        return getHBGuestStructureDiversionCommentsByMeasNumberResult;
    }

    /**
     * Sets the value of the getHBGuestStructureDiversionCommentsByMeasNumberResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureDiversionComments }
     *     
     */
    public void setGetHBGuestStructureDiversionCommentsByMeasNumberResult(ArrayOfStructureDiversionComments value) {
        this.getHBGuestStructureDiversionCommentsByMeasNumberResult = value;
    }

}
