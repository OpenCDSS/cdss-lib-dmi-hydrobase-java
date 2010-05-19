
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfParcelWells complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfParcelWells">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParcelWells" type="{http://www.dwr.state.co.us/}ParcelWells" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfParcelWells", propOrder = {
    "parcelWells"
})
public class ArrayOfParcelWells {

    @XmlElement(name = "ParcelWells", nillable = true)
    protected List<ParcelWells> parcelWells;

    /**
     * Gets the value of the parcelWells property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parcelWells property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParcelWells().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParcelWells }
     * 
     * 
     */
    public List<ParcelWells> getParcelWells() {
        if (parcelWells == null) {
            parcelWells = new ArrayList<ParcelWells>();
        }
        return this.parcelWells;
    }

}
