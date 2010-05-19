
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfStructureAnnuallyTS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfStructureAnnuallyTS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StructureAnnuallyTS" type="{http://www.dwr.state.co.us/}StructureAnnuallyTS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfStructureAnnuallyTS", propOrder = {
    "structureAnnuallyTS"
})
public class ArrayOfStructureAnnuallyTS {

    @XmlElement(name = "StructureAnnuallyTS", nillable = true)
    protected List<StructureAnnuallyTS> structureAnnuallyTS;

    /**
     * Gets the value of the structureAnnuallyTS property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the structureAnnuallyTS property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStructureAnnuallyTS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StructureAnnuallyTS }
     * 
     * 
     */
    public List<StructureAnnuallyTS> getStructureAnnuallyTS() {
        if (structureAnnuallyTS == null) {
            structureAnnuallyTS = new ArrayList<StructureAnnuallyTS>();
        }
        return this.structureAnnuallyTS;
    }

}
