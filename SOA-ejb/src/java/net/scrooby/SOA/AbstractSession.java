package net.scrooby.SOA;

import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@LocalBean
public abstract class AbstractSession {

    @PersistenceContext(unitName = "SOAPU")
    EntityManager em;
    
    public Object find(int id){
        return em.find(Object.class, id);
    }
    
    public void create(Object o){
        em.persist(o);
    }
    
    public void delete(Object o){
        em.remove(o);
    }
    
    public void update(Object o){
        em.merge(o);
    }
}
