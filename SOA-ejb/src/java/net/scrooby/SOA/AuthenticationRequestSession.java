package net.scrooby.SOA;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import java.util.List;

@Stateless
@LocalBean
public class AuthenticationRequestSession extends AbstractSession {

    @Override
    public AuthenticationRequest find(int id){
        return em.find(AuthenticationRequest.class, id);
    }
    
    public void delete(AuthenticationRequest authRequest) {
        AuthenticationRequest requestToDelete = em.getReference(
                AuthenticationRequest.class, authRequest.getId());
        em.remove(requestToDelete);
    }
    
    public boolean reactivationRequestExisits(User user){
        return em.createQuery("SELECT CAST(COUNT(1) AS BIT) "
                + "FROM AuthenticationRequest a WHERE a.user = :user", boolean.class)
                .setParameter("user", user).getSingleResult();
    }
    
    public List<AuthenticationRequest> getAllRequests(){
        return em.createQuery("SELECT a FROM AuthenticationRequest a",
                AuthenticationRequest.class).getResultList();
    }
}
