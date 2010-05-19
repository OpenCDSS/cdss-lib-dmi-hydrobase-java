
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
 *         &lt;element name="GetHBGuestStructureAnnuallyTSByMeasNumResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureAnnuallyTS" minOccurs="0"/>
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
    "getHBGuestStructureAnnuallyTSByMeasNumResult"
})
@XmlRootElement(name = "GetHBGuestStructureAnnuallyTSByMeasNumResponse")
public class GetHBGuestStructureAnnuallyTSByMeasNumResponse {

    @XmlElement(name = "GetHBGuestStructureAnnuallyTSByMeasNumResult")
    protected ArrayOfStructureAnnuallyTS getHBGuestStructureAnnuallyTSByMeasNumResult;

    /**
     * Gets the value of the getHBGuestStructureAnnuallyTSByMeasNumResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureAnnuallyTS }
     *     
     */
    public ArrayOfStructureAnnuallyTS getGetHBGuestStructureAnnuallyTSByMeasNumResult() {
        return getHBGuestStructureAnnuallyTSByMeasNumResult;
    }

    /**
     * Sets the value of the getHBGuestStructureAnnuallyTSByMeasNumResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureAnnuallyTS }
     *     
     */
    public void setGetHBGuestStructureAnnuallyTSByMeasNumResult(ArrayOfStructureAnnuallyTS value) {
        this.getHBGuestStructureAnnuallyTSByMeasNumResult = value;
    }

}
