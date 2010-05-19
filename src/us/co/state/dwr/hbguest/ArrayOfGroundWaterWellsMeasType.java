
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfGroundWaterWellsMeasType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfGroundWaterWellsMeasType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GroundWaterWellsMeasType" type="{http://www.dwr.state.co.us/}GroundWaterWellsMeasType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfGroundWaterWellsMeasType", propOrder = {
    "groundWaterWellsMeasType"
})
public class ArrayOfGroundWaterWellsMeasType {

    @XmlElement(name = "GroundWaterWellsMeasType", nillable = true)
    protected List<GroundWaterWellsMeasType> groundWaterWellsMeasType;

    /**
     * Gets the value of the groundWaterWellsMeasType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groundWaterWellsMeasType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroundWaterWellsMeasType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GroundWaterWellsMeasType }
     * 
     * 
     */
    public List<GroundWaterWellsMeasType> getGroundWaterWellsMeasType() {
        if (groundWaterWellsMeasType == null) {
            groundWaterWellsMeasType = new ArrayList<GroundWaterWellsMeasType>();
        }
        return this.groundWaterWellsMeasType;
    }

}
