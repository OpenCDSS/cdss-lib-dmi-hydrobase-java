
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
 *         &lt;element name="GetHBGuestGroundWaterWellsAquiferPicksByDIVResult" type="{http://www.dwr.state.co.us/}ArrayOfGroundWaterWellsAquiferPicks" minOccurs="0"/>
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
    "getHBGuestGroundWaterWellsAquiferPicksByDIVResult"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsAquiferPicksByDIVResponse")
public class GetHBGuestGroundWaterWellsAquiferPicksByDIVResponse {

    @XmlElement(name = "GetHBGuestGroundWaterWellsAquiferPicksByDIVResult")
    protected ArrayOfGroundWaterWellsAquiferPicks getHBGuestGroundWaterWellsAquiferPicksByDIVResult;

    /**
     * Gets the value of the getHBGuestGroundWaterWellsAquiferPicksByDIVResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGroundWaterWellsAquiferPicks }
     *     
     */
    public ArrayOfGroundWaterWellsAquiferPicks getGetHBGuestGroundWaterWellsAquiferPicksByDIVResult() {
        return getHBGuestGroundWaterWellsAquiferPicksByDIVResult;
    }

    /**
     * Sets the value of the getHBGuestGroundWaterWellsAquiferPicksByDIVResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGroundWaterWellsAquiferPicks }
     *     
     */
    public void setGetHBGuestGroundWaterWellsAquiferPicksByDIVResult(ArrayOfGroundWaterWellsAquiferPicks value) {
        this.getHBGuestGroundWaterWellsAquiferPicksByDIVResult = value;
    }

}
