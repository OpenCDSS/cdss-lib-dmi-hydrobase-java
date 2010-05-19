
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfWaterDivision complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfWaterDivision">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WaterDivision" type="{http://www.dwr.state.co.us/}WaterDivision" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfWaterDivision", propOrder = {
    "waterDivision"
})
public class ArrayOfWaterDivision {

    @XmlElement(name = "WaterDivision", nillable = true)
    protected List<WaterDivision> waterDivision;

    /**
     * Gets the value of the waterDivision property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the waterDivision property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWaterDivision().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WaterDivision }
     * 
     * 
     */
    public List<WaterDivision> getWaterDivision() {
        if (waterDivision == null) {
            waterDivision = new ArrayList<WaterDivision>();
        }
        return this.waterDivision;
    }

}
