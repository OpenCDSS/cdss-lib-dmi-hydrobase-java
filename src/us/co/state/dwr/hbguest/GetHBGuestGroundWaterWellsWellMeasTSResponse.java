
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
 *         &lt;element name="GetHBGuestGroundWaterWellsWellMeasTSResult" type="{http://www.dwr.state.co.us/}ArrayOfGroundWaterWellsWellMeasTS" minOccurs="0"/>
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
    "getHBGuestGroundWaterWellsWellMeasTSResult"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsWellMeasTSResponse")
public class GetHBGuestGroundWaterWellsWellMeasTSResponse {

    @XmlElement(name = "GetHBGuestGroundWaterWellsWellMeasTSResult")
    protected ArrayOfGroundWaterWellsWellMeasTS getHBGuestGroundWaterWellsWellMeasTSResult;

    /**
     * Gets the value of the getHBGuestGroundWaterWellsWellMeasTSResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGroundWaterWellsWellMeasTS }
     *     
     */
    public ArrayOfGroundWaterWellsWellMeasTS getGetHBGuestGroundWaterWellsWellMeasTSResult() {
        return getHBGuestGroundWaterWellsWellMeasTSResult;
    }

    /**
     * Sets the value of the getHBGuestGroundWaterWellsWellMeasTSResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGroundWaterWellsWellMeasTS }
     *     
     */
    public void setGetHBGuestGroundWaterWellsWellMeasTSResult(ArrayOfGroundWaterWellsWellMeasTS value) {
        this.getHBGuestGroundWaterWellsWellMeasTSResult = value;
    }

}
