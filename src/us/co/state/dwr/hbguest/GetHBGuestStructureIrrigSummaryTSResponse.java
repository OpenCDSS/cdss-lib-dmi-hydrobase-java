
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
 *         &lt;element name="GetHBGuestStructureIrrigSummaryTSResult" type="{http://www.dwr.state.co.us/}ArrayOfStructureIrrigSummaryTS" minOccurs="0"/>
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
    "getHBGuestStructureIrrigSummaryTSResult"
})
@XmlRootElement(name = "GetHBGuestStructureIrrigSummaryTSResponse")
public class GetHBGuestStructureIrrigSummaryTSResponse {

    @XmlElement(name = "GetHBGuestStructureIrrigSummaryTSResult")
    protected ArrayOfStructureIrrigSummaryTS getHBGuestStructureIrrigSummaryTSResult;

    /**
     * Gets the value of the getHBGuestStructureIrrigSummaryTSResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfStructureIrrigSummaryTS }
     *     
     */
    public ArrayOfStructureIrrigSummaryTS getGetHBGuestStructureIrrigSummaryTSResult() {
        return getHBGuestStructureIrrigSummaryTSResult;
    }

    /**
     * Sets the value of the getHBGuestStructureIrrigSummaryTSResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfStructureIrrigSummaryTS }
     *     
     */
    public void setGetHBGuestStructureIrrigSummaryTSResult(ArrayOfStructureIrrigSummaryTS value) {
        this.getHBGuestStructureIrrigSummaryTSResult = value;
    }

}
