
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StructureMonthlyTS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StructureMonthlyTS">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.dwr.state.co.us/}StructureTSCommon">
 *       &lt;sequence>
 *         &lt;element name="irr_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="cal_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="cal_month" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="amt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructureMonthlyTS", propOrder = {
    "irrYear",
    "calYear",
    "calMonth",
    "amt"
})
public class StructureMonthlyTS
    extends StructureTSCommon
{

    @XmlElement(name = "irr_year")
    protected short irrYear;
    @XmlElement(name = "cal_year")
    protected short calYear;
    @XmlElement(name = "cal_month")
    protected short calMonth;
    protected double amt;

    /**
     * Gets the value of the irrYear property.
     * 
     */
    public short getIrrYear() {
        return irrYear;
    }

    /**
     * Sets the value of the irrYear property.
     * 
     */
    public void setIrrYear(short value) {
        this.irrYear = value;
    }

    /**
     * Gets the value of the calYear property.
     * 
     */
    public short getCalYear() {
        return calYear;
    }

    /**
     * Sets the value of the calYear property.
     * 
     */
    public void setCalYear(short value) {
        this.calYear = value;
    }

    /**
     * Gets the value of the calMonth property.
     * 
     */
    public short getCalMonth() {
        return calMonth;
    }

    /**
     * Sets the value of the calMonth property.
     * 
     */
    public void setCalMonth(short value) {
        this.calMonth = value;
    }

    /**
     * Gets the value of the amt property.
     * 
     */
    public double getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     */
    public void setAmt(double value) {
        this.amt = value;
    }

}
