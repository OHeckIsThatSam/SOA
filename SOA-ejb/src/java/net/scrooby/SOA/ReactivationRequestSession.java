package net.scrooby.SOA;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import java.util.List;

@Stateless
@LocalBean
public class ReactivationRequestSession extends AbstractSession {
    
    @Override
    public ReactivationRequest find(int id) {
        return em.find(ReactivationRequest.class, id);
    }
    
    public void delete(ReactivationRequest reacRequest) {
        ReactivationRequest requestToDelete = em.getReference(
                ReactivationRequest.class, reacRequest.getId());
        em.remove(requestToDelete);
    }
    
    public boolean reactivationRequestExisits(User user) {
        return em.createQuery("SELECT CAST(COUNT(1) AS BIT) "
                + "FROM ReactivationRequest r WHERE r.user = :user", boolean.class)
                .setParameter("user", user).getSingleResult();
    }
    
    public List<AuthenticationRequest> getAllRequests() {
        return em.createQuery("SELECT r FROM ReactivationRequest r",
                AuthenticationRequest.class).getResultList();
    }
    
}
