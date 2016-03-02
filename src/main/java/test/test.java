package test;

import model.User;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by NTI-Sistema on 29/06/2015.
 */
public class test {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test1");
        //EntityManager em = emf.createEntityManager();
        /*em.getTransaction().begin();
            User u = new User();
            u.setName("Marcus");
            em.persist(u);
        em.getTransaction().commit();*/
        //em.close();
        emf.close();
    }
}
