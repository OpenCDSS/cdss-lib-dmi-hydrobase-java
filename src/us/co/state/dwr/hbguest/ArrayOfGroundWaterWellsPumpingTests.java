
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGroundWaterWellsPumpingTests complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGroundWaterWellsPumpingTests">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GroundWaterWellsPumpingTests" type="{http://www.dwr.state.co.us/}GroundWaterWellsPumpingTests" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGroundWaterWellsPumpingTests", propOrder = {
    "groundWaterWellsPumpingTests"
})
public class ArrayOfGroundWaterWellsPumpingTests {

    @XmlElement(name = "GroundWaterWellsPumpingTests", nillable = true)
    protected List<GroundWaterWellsPumpingTests> groundWaterWellsPumpingTests;

    /**
     * Gets the value of the groundWaterWellsPumpingTests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groundWaterWellsPumpingTests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroundWaterWellsPumpingTests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GroundWaterWellsPumpingTests }
     * 
     * 
     */
    public List<GroundWaterWellsPumpingTests> getGroundWaterWellsPumpingTests() {
        if (groundWaterWellsPumpingTests == null) {
            groundWaterWellsPumpingTests = new ArrayList<GroundWaterWellsPumpingTests>();
        }
        return this.groundWaterWellsPumpingTests;
    }

}
