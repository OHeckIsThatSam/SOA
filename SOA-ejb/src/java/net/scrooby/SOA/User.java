package net.scrooby.SOA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;

@Entity
@Table (name = "tblUser")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    private String firstName;
    
    @Column
    private String lastName;
    
    @Column
    private String email;
    
    @Column
    private String phoneNumber;
    
    @Column
    private String address;
    
    @Column
    private Integer strike;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loginId", referencedColumnName = "id")
    private LoginDetails loginDetails;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private AuthenticationRequest authenticationRequest;
    
    @OneToOne (cascade = CascadeType.ALL, mappedBy = "user")
    private ReactivationRequest reactivationRequest;
    
    @ManyToMany
    @JoinTable(
        name = "tblUserRoles", 
        joinColumns = @JoinColumn(name = "userId"), 
        inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Role> roles;
    
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Listing> listings;
    
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "user")
    private List<Payment> payments;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStrike() {
        return strike;
    }

    public void setStrike(Integer strike) {
        this.strike = strike;
    }
    
    public LoginDetails getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(LoginDetails loginDetails) {
        this.loginDetails = loginDetails;
    }

    public AuthenticationRequest getAuthenticationRequest() {
        return authenticationRequest;
    }

    public void setAuthenticationRequest(AuthenticationRequest authenticationRequest) {
        this.authenticationRequest = authenticationRequest;
    }

    public ReactivationRequest getReactivationRequest() {
        return reactivationRequest;
    }

    public void setReactivationRequest(ReactivationRequest reactivationRequest) {
        this.reactivationRequest = reactivationRequest;
    }
    
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
    
    public void addListing(Listing listing) {
        this.listings.add(listing);
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public boolean isUnauthorised() {
        if (roles.isEmpty()) {
            return false;
        }
        
        // If any of the users roles are Unauthorised then return true
        for (int i = 0; i < roles.size(); i++) {
            if ("Unauthorised".equals(roles.get(i).getName())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isDeactivated() {
        if (roles.isEmpty()) {
            return false;
        }
        
        // If any of the users roles are Deactivated then return true
        for (int i = 0; i < roles.size(); i++) {
            if ("Deactivated".equals(roles.get(i).getName())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isAdmin() {
        if (roles.isEmpty()) {
            return false;
        }
        
        // If any of the users roles are Deactivated then return true
        for (int i = 0; i < roles.size(); i++) {
            if ("Admin".equals(roles.get(i).getName())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.scrooby.SOA.User[ id=" + id + " ]";
    }
    
}
