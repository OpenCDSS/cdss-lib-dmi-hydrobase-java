
package us.co.state.dwr.hbguest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSnowCourse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSnowCourse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SnowCourse" type="{http://www.dwr.state.co.us/}SnowCourse" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSnowCourse", propOrder = {
    "snowCourse"
})
public class ArrayOfSnowCourse {

    @XmlElement(name = "SnowCourse", nillable = true)
    protected List<SnowCourse> snowCourse;

    /**
     * Gets the value of the snowCourse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the snowCourse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSnowCourse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SnowCourse }
     * 
     * 
     */
    public List<SnowCourse> getSnowCourse() {
        if (snowCourse == null) {
            snowCourse = new ArrayList<SnowCourse>();
        }
        return this.snowCourse;
    }

}
