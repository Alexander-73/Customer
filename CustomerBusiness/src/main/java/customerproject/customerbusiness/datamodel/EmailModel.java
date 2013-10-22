
package customerproject.customerbusiness.datamodel;

import java.io.Serializable;


/**
 *
 * @author timovaananen
 */

public class EmailModel extends ContactType implements Serializable {
    
   
    private String email;

    public EmailModel(CONTACTTYPE ct, String val)
    {
        super(ct, val);
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
