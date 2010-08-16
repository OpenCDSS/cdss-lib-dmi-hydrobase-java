
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfStructureGeolocMeasType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfStructureGeolocMeasType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StructureGeolocMeasType" type="{http://www.dwr.state.co.us/}StructureGeolocMeasType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfStructureGeolocMeasType", propOrder = {
    "structureGeolocMeasType"
})
public class ArrayOfStructureGeolocMeasType {

    @XmlElement(name = "StructureGeolocMeasType", nillable = true)
    protected List<StructureGeolocMeasType> structureGeolocMeasType;

    /**
     * Gets the value of the structureGeolocMeasType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the structureGeolocMeasType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStructureGeolocMeasType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StructureGeolocMeasType }
     * 
     * 
     */
    public List<StructureGeolocMeasType> getStructureGeolocMeasType() {
        if (structureGeolocMeasType == null) {
            structureGeolocMeasType = new ArrayList<StructureGeolocMeasType>();
        }
        return this.structureGeolocMeasType;
    }

}
