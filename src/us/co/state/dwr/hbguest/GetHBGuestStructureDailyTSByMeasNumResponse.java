
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
 *         &lt;element name="GetHBGuestStructureDailyTSByMeasNumResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureDailyTS" minOccurs="0"/>
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
    "getHBGuestStructureDailyTSByMeasNumResult"
})
@XmlRootElement(name = "GetHBGuestStructureDailyTSByMeasNumResponse")
public class GetHBGuestStructureDailyTSByMeasNumResponse {

    @XmlElement(name = "GetHBGuestStructureDailyTSByMeasNumResult")
    protected ArrayOfStructureDailyTS getHBGuestStructureDailyTSByMeasNumResult;

    /**
     * Gets the value of the getHBGuestStructureDailyTSByMeasNumResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureDailyTS }
     *     
     */
    public ArrayOfStructureDailyTS getGetHBGuestStructureDailyTSByMeasNumResult() {
        return getHBGuestStructureDailyTSByMeasNumResult;
    }

    /**
     * Sets the value of the getHBGuestStructureDailyTSByMeasNumResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureDailyTS }
     *     
     */
    public void setGetHBGuestStructureDailyTSByMeasNumResult(ArrayOfStructureDailyTS value) {
        this.getHBGuestStructureDailyTSByMeasNumResult = value;
    }

}
