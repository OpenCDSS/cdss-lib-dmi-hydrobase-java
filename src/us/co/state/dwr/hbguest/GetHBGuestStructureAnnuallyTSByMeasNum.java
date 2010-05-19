
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
 *         &lt;element name="measNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "measNum",
    "startYear",
    "endYear"
})
@XmlRootElement(name = "GetHBGuestStructureAnnuallyTSByMeasNum")
public class GetHBGuestStructureAnnuallyTSByMeasNum {

    protected int measNum;
    protected short startYear;
    protected short endYear;

    /**
     * Gets the value of the measNum property.
     * 
     */
    public int getMeasNum() {
        return measNum;
    }

    /**
     * Sets the value of the measNum property.
     * 
     */
    public void setMeasNum(int value) {
        this.measNum = value;
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
