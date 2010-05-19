
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfWellPermits complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfWellPermits">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WellPermits" type="{http://www.dwr.state.co.us/}WellPermits" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfWellPermits", propOrder = {
    "wellPermits"
})
public class ArrayOfWellPermits {

    @XmlElement(name = "WellPermits", nillable = true)
    protected List<WellPermits> wellPermits;

    /**
     * Gets the value of the wellPermits property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wellPermits property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWellPermits().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WellPermits }
     * 
     * 
     */
    public List<WellPermits> getWellPermits() {
        if (wellPermits == null) {
            wellPermits = new ArrayList<WellPermits>();
        }
        return this.wellPermits;
    }

}
