//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.22 at 10:38:07 PM CEST 
//


package hu.documaison.bll.ws.doidata;

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
 *         &lt;element ref="{http://www.crossref.org/qrschema/2.0}email_address" minOccurs="0"/>
 *         &lt;element ref="{http://www.crossref.org/qrschema/2.0}doi_batch_id"/>
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
    "emailAddress",
    "doiBatchId"
})
@XmlRootElement(name = "head")
public class Head {

    @XmlElement(name = "email_address")
    protected String emailAddress;
    @XmlElement(name = "doi_batch_id", required = true)
    protected String doiBatchId;

    /**
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the doiBatchId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoiBatchId() {
        return doiBatchId;
    }

    /**
     * Sets the value of the doiBatchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoiBatchId(String value) {
        this.doiBatchId = value;
    }

}