package net.scrooby.SOA;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.TypedQuery;

@Stateless
@LocalBean
public class LoginDetailsSession extends AbstractSession {
    
    @Override
    public LoginDetails find(int id){
        return em.find(LoginDetails.class, id);
    }
    
    public LoginDetails getLoginDetailsByUsername (String username) {
        TypedQuery<LoginDetails> q = em.createQuery("SELECT l FROM LoginDetails l "
                + "WHERE l.username LIKE :username", LoginDetails.class)
            .setParameter("username", username);
        
        if (q.getResultList().isEmpty()) {
            LoginDetails loginDetails = new LoginDetails();
            return loginDetails;
        }
        
        return (LoginDetails) q.getResultList().get(0);
    }
    
    public boolean usernameExists (String username) {
        return em.createQuery("SELECT CAST(COUNT(1) AS BIT) "
                + "FROM LoginDetails l WHERE l.username LIKE :username", boolean.class)
                .setParameter("username", username).getSingleResult();
    }
    
    public boolean loginDetailsCorrect (LoginDetails loginDetails) {
        LoginDetails correctLoginDetails = getLoginDetailsByUsername(loginDetails.getUsername());
        
        if (correctLoginDetails.getId() == null) {
            return false;
        }
        
        return loginDetails.getPassword().equals(correctLoginDetails.getPassword());
    }
}
