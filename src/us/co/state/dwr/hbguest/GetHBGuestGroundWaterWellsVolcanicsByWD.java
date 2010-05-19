
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="wd" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "wd"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsVolcanicsByWD")
public class GetHBGuestGroundWaterWellsVolcanicsByWD {

    protected int wd;

    /**
     * Gets the value of the wd property.
     * 
     */
    public int getWd() {
        return wd;
    }

    /**
     * Sets the value of the wd property.
     * 
     */
    public void setWd(int value) {
        this.wd = value;
    }

}
