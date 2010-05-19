
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGroundWaterWellsGeophlogs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGroundWaterWellsGeophlogs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GroundWaterWellsGeophlogs" type="{http://www.dwr.state.co.us/}GroundWaterWellsGeophlogs" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGroundWaterWellsGeophlogs", propOrder = {
    "groundWaterWellsGeophlogs"
})
public class ArrayOfGroundWaterWellsGeophlogs {

    @XmlElement(name = "GroundWaterWellsGeophlogs", nillable = true)
    protected List<GroundWaterWellsGeophlogs> groundWaterWellsGeophlogs;

    /**
     * Gets the value of the groundWaterWellsGeophlogs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groundWaterWellsGeophlogs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroundWaterWellsGeophlogs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GroundWaterWellsGeophlogs }
     * 
     * 
     */
    public List<GroundWaterWellsGeophlogs> getGroundWaterWellsGeophlogs() {
        if (groundWaterWellsGeophlogs == null) {
            groundWaterWellsGeophlogs = new ArrayList<GroundWaterWellsGeophlogs>();
        }
        return this.groundWaterWellsGeophlogs;
    }

}
