
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
 *         &lt;element name="wellNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "wellNum"
})
@XmlRootElement(name = "GetHBGuestGroundWaterWellsVolcanicsByWellNumber")
public class GetHBGuestGroundWaterWellsVolcanicsByWellNumber {

    protected int wellNum;

    /**
     * Gets the value of the wellNum property.
     * 
     */
    public int getWellNum() {
        return wellNum;
    }

    /**
     * Sets the value of the wellNum property.
     * 
     */
    public void setWellNum(int value) {
        this.wellNum = value;
    }

}
