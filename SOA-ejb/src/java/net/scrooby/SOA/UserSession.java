package net.scrooby.SOA;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import java.util.List;

@Stateless
@LocalBean
public class UserSession extends AbstractSession{
    
    @Override
    public User find(int id){
        return em.find(User.class, id);
    }
    
    public User getByLoginDetails (LoginDetails loginDetails) {
        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.loginDetails = :loginDetails")
                .setParameter("loginDetails", loginDetails).getResultList();
        
        if (users.isEmpty()) {
            User user = new User();
            return user;
        }
        
        return users.get(0);
    }
    
    public boolean emailExists (String email) {
        return em.createQuery("SELECT CAST(COUNT(1) AS BIT) "
                + "FROM User u WHERE u.email LIKE :email", boolean.class)
                .setParameter("email", email).getSingleResult();
    }
}
