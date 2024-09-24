package net.scrooby.SOA;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import java.util.List;

@Stateless
@LocalBean
public class RoleSession extends AbstractSession {
    
    @Override
    public Role find(int id){
        return em.find(Role.class, id);
    }

    public List<Role> findByName(String name) {
       return em.createQuery("SELECT r FROM Role r WHERE r.name LIKE :name", Role.class)
               .setParameter("name", name).getResultList();
    }
}
