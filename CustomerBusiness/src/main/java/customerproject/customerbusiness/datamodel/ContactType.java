
package customerproject.customerbusiness.datamodel;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author timovaananen
 */
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name="ContactType")
public class ContactType implements Serializable {
    
    public enum CONTACTTYPE{MOBILE_PHONE, WORK_PHONE, WORK_EMAIL, HOME_EMAIL}
   
    //@XmlElement(name="Type")
    protected CONTACTTYPE contactType;
    //@XmlElement(name="Value")
    protected String value;
    
    public ContactType() {}
    public ContactType(CONTACTTYPE t, String val)
    {
        this.contactType = t;
        this.value = val;
    }
    public CONTACTTYPE getContactType() {
        return contactType;
    }

    public void setType(CONTACTTYPE type) {
        this.contactType = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
