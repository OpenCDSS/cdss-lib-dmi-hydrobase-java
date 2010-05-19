
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="GetHBGuestSnowCourseResult" type="{http://www.dwr.state.co.us/}ArrayOfSnowCourse" minOccurs="0"/>
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
    "getHBGuestSnowCourseResult"
})
@XmlRootElement(name = "GetHBGuestSnowCourseResponse")
public class GetHBGuestSnowCourseResponse {

    @XmlElement(name = "GetHBGuestSnowCourseResult")
    protected ArrayOfSnowCourse getHBGuestSnowCourseResult;

    /**
     * Gets the value of the getHBGuestSnowCourseResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSnowCourse }
     *     
     */
    public ArrayOfSnowCourse getGetHBGuestSnowCourseResult() {
        return getHBGuestSnowCourseResult;
    }

    /**
     * Sets the value of the getHBGuestSnowCourseResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSnowCourse }
     *     
     */
    public void setGetHBGuestSnowCourseResult(ArrayOfSnowCourse value) {
        this.getHBGuestSnowCourseResult = value;
    }

}
