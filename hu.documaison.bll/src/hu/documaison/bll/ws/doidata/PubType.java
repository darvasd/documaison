//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.22 at 10:38:07 PM CEST 
//


package hu.documaison.bll.ws.doidata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pubType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="pubType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="abstract_only"/>
 *     &lt;enumeration value="full_text"/>
 *     &lt;enumeration value="bibliographic_record"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "pubType")
@XmlEnum
public enum PubType {

    @XmlEnumValue("abstract_only")
    ABSTRACT_ONLY("abstract_only"),
    @XmlEnumValue("full_text")
    FULL_TEXT("full_text"),
    @XmlEnumValue("bibliographic_record")
    BIBLIOGRAPHIC_RECORD("bibliographic_record");
    private final String value;

    PubType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PubType fromValue(String v) {
        for (PubType c: PubType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
