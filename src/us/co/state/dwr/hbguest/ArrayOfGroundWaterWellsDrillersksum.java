
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGroundWaterWellsDrillersksum complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGroundWaterWellsDrillersksum">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GroundWaterWellsDrillersksum" type="{http://www.dwr.state.co.us/}GroundWaterWellsDrillersksum" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGroundWaterWellsDrillersksum", propOrder = {
    "groundWaterWellsDrillersksum"
})
public class ArrayOfGroundWaterWellsDrillersksum {

    @XmlElement(name = "GroundWaterWellsDrillersksum", nillable = true)
    protected List<GroundWaterWellsDrillersksum> groundWaterWellsDrillersksum;

    /**
     * Gets the value of the groundWaterWellsDrillersksum property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groundWaterWellsDrillersksum property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroundWaterWellsDrillersksum().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GroundWaterWellsDrillersksum }
     * 
     * 
     */
    public List<GroundWaterWellsDrillersksum> getGroundWaterWellsDrillersksum() {
        if (groundWaterWellsDrillersksum == null) {
            groundWaterWellsDrillersksum = new ArrayList<GroundWaterWellsDrillersksum>();
        }
        return this.groundWaterWellsDrillersksum;
    }

}
