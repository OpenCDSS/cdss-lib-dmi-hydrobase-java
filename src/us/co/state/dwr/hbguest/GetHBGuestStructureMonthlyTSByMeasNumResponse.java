
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
 *         &lt;element name="GetHBGuestStructureMonthlyTSByMeasNumResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureMonthlyTS" minOccurs="0"/>
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
    "getHBGuestStructureMonthlyTSByMeasNumResult"
})
@XmlRootElement(name = "GetHBGuestStructureMonthlyTSByMeasNumResponse")
public class GetHBGuestStructureMonthlyTSByMeasNumResponse {

    @XmlElement(name = "GetHBGuestStructureMonthlyTSByMeasNumResult")
    protected ArrayOfStructureMonthlyTS getHBGuestStructureMonthlyTSByMeasNumResult;

    /**
     * Gets the value of the getHBGuestStructureMonthlyTSByMeasNumResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureMonthlyTS }
     *     
     */
    public ArrayOfStructureMonthlyTS getGetHBGuestStructureMonthlyTSByMeasNumResult() {
        return getHBGuestStructureMonthlyTSByMeasNumResult;
    }

    /**
     * Sets the value of the getHBGuestStructureMonthlyTSByMeasNumResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureMonthlyTS }
     *     
     */
    public void setGetHBGuestStructureMonthlyTSByMeasNumResult(ArrayOfStructureMonthlyTS value) {
        this.getHBGuestStructureMonthlyTSByMeasNumResult = value;
    }

}
