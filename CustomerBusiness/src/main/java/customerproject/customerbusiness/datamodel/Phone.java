
package customerproject.customerbusiness.datamodel;

import java.io.Serializable;

/**
 *
 * @author timovaananen
 */
public class Phone extends ContactType implements Serializable {
    
      public Phone(CONTACTTYPE t, String val)
      {
          super(t, val);
      }
    
}
