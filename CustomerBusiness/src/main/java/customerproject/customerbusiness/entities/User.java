/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerbusiness.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author timovaananen
 */
@Entity
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByIdCustomer", query = "SELECT u FROM User u WHERE u.idUser = :idUser"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByUsernamePassword", query = "SELECT u FROM User u WHERE u.username = :username and u.password = :password and u.confirmed = true"),
    @NamedQuery(name = "User.findByUsernameEmail", query = "SELECT u FROM User u WHERE u.username = :username and u.email = :email"),
    @NamedQuery(name = "User.findExpiredEmailRegistrations", query = "SELECT u FROM User u WHERE u.confirmed = false and u.validUntil < :date"),
    @NamedQuery(name = "User.findEmailVerification", query = "SELECT u FROM User u WHERE u.verificationHash = :hash")
})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCustomer")
    private Integer idUser;
    @Basic(optional = false)
    @Size(min = 1, max = 40)
    @NotNull
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Size(min = 1, max = 32)
    @NotNull
    @Column(name = "password")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="lastLogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name="loggedIn")
    private boolean loggedIn;
    private boolean confirmed;
    @Column(name="validuntil")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil;
    @Column(name="verificationhash")
    @Size(min = 1, max = 255)
    private String verificationHash;
    @Column(name="ip")
    private String ipAddress;

  
    public User() {
    }

    public User(Integer idUser) {
        this.idUser = idUser;
    }

    public User(Integer idUser, String username, String password) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "customerproject.customerbusiness.entities.User[ idCustomer=" + idUser + " ]";
    }
    
     public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
     public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }
    
     public String getVerificationHash() {
        return verificationHash;
    }

    public void setVerificationHash(String verificationHash) {
        this.verificationHash = verificationHash;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
 
    
}
