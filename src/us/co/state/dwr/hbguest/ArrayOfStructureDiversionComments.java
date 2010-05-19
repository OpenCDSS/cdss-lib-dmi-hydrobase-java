
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfStructureDiversionComments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfStructureDiversionComments">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StructureDiversionComments" type="{http://www.dwr.state.co.us/}StructureDiversionComments" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfStructureDiversionComments", propOrder = {
    "structureDiversionComments"
})
public class ArrayOfStructureDiversionComments {

    @XmlElement(name = "StructureDiversionComments", nillable = true)
    protected List<StructureDiversionComments> structureDiversionComments;

    /**
     * Gets the value of the structureDiversionComments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the structureDiversionComments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStructureDiversionComments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StructureDiversionComments }
     * 
     * 
     */
    public List<StructureDiversionComments> getStructureDiversionComments() {
        if (structureDiversionComments == null) {
            structureDiversionComments = new ArrayList<StructureDiversionComments>();
        }
        return this.structureDiversionComments;
    }

}
