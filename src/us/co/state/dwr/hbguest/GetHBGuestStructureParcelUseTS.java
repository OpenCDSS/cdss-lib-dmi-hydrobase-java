
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wdid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startYear" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="endYear" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "wdid",
    "startYear",
    "endYear"
})
@XmlRootElement(name = "GetHBGuestStructureParcelUseTS")
public class GetHBGuestStructureParcelUseTS {

    protected String wdid;
    protected short startYear;
    protected short endYear;

    /**
     * Gets the value of the wdid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWdid() {
        return wdid;
    }

    /**
     * Sets the value of the wdid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWdid(String value) {
        this.wdid = value;
    }

    /**
     * Gets the value of the startYear property.
     * 
     */
    public short getStartYear() {
        return startYear;
    }

    /**
     * Sets the value of the startYear property.
     * 
     */
    public void setStartYear(short value) {
        this.startYear = value;
    }

    /**
     * Gets the value of the endYear property.
     * 
     */
    public short getEndYear() {
        return endYear;
    }

    /**
     * Sets the value of the endYear property.
     * 
     */
    public void setEndYear(short value) {
        this.endYear = value;
    }

}
