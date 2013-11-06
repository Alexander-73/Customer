/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerrest;

import customerproject.customerbusiness.entities.User;
import customerproject.customerbusiness.facades.UserManagerLocal;
import customerproject.customerweb.LoginBean;
import java.io.IOException;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 *
 * @author timovaananen
 */
@Stateless
@Path("email")
@ManagedBean
public class RestVerifier {
    
    @EJB
    UserManagerLocal userManager;
    
    String retUrl = "";
    
    @GET
    public String checkEmailVerification(@QueryParam("verification") String ver, @Context final HttpServletRequest request,
                @Context final HttpServletResponse response) throws IOException {
        
        System.out.println("Verification: "+ver);
        
        User user = userManager.getEmailVerification(ver);
        
        if(user == null) {     //error state
           
            System.out.println("Verification not found");
            
            response.sendRedirect("/CustomerWeb/faces/register.xhtml?state=error");
        }
        else {                   //proceed to updating fields
            
            System.out.println("Verification found");
            user.setStartDate(new Date());
            user.setConfirmed(true);
            user.setValidUntil(null);
            user.setVerificationHash(null);
           
            userManager.updateLoginData(user);
 
            response.sendRedirect("/CustomerWeb/faces/login.xhtml?state=success");
           
        }
        
        return "";
 
    }
    
    
}
