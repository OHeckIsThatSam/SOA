package net.scrooby.SOA;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import java.util.List;

@Stateless
@LocalBean
public class PaymentSession extends AbstractSession {

    @Override
    public Payment find(int id){
        return em.find(Payment.class, id);
    }
    
    public List<Payment> findUsersUnpaidPayments(User user) {
        return em.createQuery("SELECT p FROM Payment p WHERE p.user = :user AND p.paid = false")
                .setParameter("user", user).getResultList();
    }
    
    public List<Payment> findAllUnpaidPayments() {
        return em.createQuery("SELECT p FROM Payment p WHERE p.paid = false")
                .getResultList();
    }
}
