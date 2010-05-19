
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
 *         &lt;element name="stationNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "stationNum"
})
@XmlRootElement(name = "GetHBGuestStationMeasTypeByStationNum")
public class GetHBGuestStationMeasTypeByStationNum {

    protected int stationNum;

    /**
     * Gets the value of the stationNum property.
     * 
     */
    public int getStationNum() {
        return stationNum;
    }

    /**
     * Sets the value of the stationNum property.
     * 
     */
    public void setStationNum(int value) {
        this.stationNum = value;
    }

}
