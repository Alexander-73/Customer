package customerproject.customerbusiness.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author timovaananen
 */

public class Customers<VALUE> implements Serializable {
   
    private List<VALUE> customers; 
    
    public Customers()
    {
        this.customers = new ArrayList<VALUE>();
    }
    
    public Customers(List<VALUE> c)
    {
        this.customers = c;
    }

    @XmlAnyElement(lax=true)
    public List<VALUE> getCustomers() {
        return customers;
    }
    
   

   
    
}
