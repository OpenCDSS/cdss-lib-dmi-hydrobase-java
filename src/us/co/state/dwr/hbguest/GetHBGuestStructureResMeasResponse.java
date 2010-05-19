
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
 *         &lt;element name="GetHBGuestStructureResMeasResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureResMeas" minOccurs="0"/>
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
    "getHBGuestStructureResMeasResult"
})
@XmlRootElement(name = "GetHBGuestStructureResMeasResponse")
public class GetHBGuestStructureResMeasResponse {

    @XmlElement(name = "GetHBGuestStructureResMeasResult")
    protected ArrayOfStructureResMeas getHBGuestStructureResMeasResult;

    /**
     * Gets the value of the getHBGuestStructureResMeasResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureResMeas }
     *     
     */
    public ArrayOfStructureResMeas getGetHBGuestStructureResMeasResult() {
        return getHBGuestStructureResMeasResult;
    }

    /**
     * Sets the value of the getHBGuestStructureResMeasResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureResMeas }
     *     
     */
    public void setGetHBGuestStructureResMeasResult(ArrayOfStructureResMeas value) {
        this.getHBGuestStructureResMeasResult = value;
    }

}
