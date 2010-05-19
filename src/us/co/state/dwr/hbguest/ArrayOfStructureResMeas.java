
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfStructureResMeas complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfStructureResMeas">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StructureResMeas" type="{http://www.dwr.state.co.us/}StructureResMeas" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfStructureResMeas", propOrder = {
    "structureResMeas"
})
public class ArrayOfStructureResMeas {

    @XmlElement(name = "StructureResMeas", nillable = true)
    protected List<StructureResMeas> structureResMeas;

    /**
     * Gets the value of the structureResMeas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the structureResMeas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStructureResMeas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StructureResMeas }
     * 
     * 
     */
    public List<StructureResMeas> getStructureResMeas() {
        if (structureResMeas == null) {
            structureResMeas = new ArrayList<StructureResMeas>();
        }
        return this.structureResMeas;
    }

}
