package exercise1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Dolphin!");
//
//        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("dolphin", false);
//        try(EntityManager em = emf.createEntityManager())
//        {
//            Person p1 = new Person("Hanzi");
//            PersonDetail pd1 = new PersonDetail("Algade 3", 4300, "Holbæk", 45);
//            p1.addPersonDetail(pd1);
//            Fee f1 = new Fee(125, LocalDate.of(2023, 8, 25));
//            Fee f2 = new Fee(150, LocalDate.of(2023, 7, 19));
//            p1.addFee(f1);
//            p1.addFee(f2);
//
//            em.getTransaction().begin();
//                em.persist(p1);
//            em.getTransaction().commit();
//
//            //ADD SOME NOTES
//            Query query = em.createQuery("select p FROM Person p WHERE p.id = :id").setParameter("id",1);
//            p1 = (Person) query.getSingleResult();
//            System.out.println(p1);
//
//            p1.addNote(new Note("This is the first note"));
//            p1.addNote(new Note("This is the second note"));
//
//            em.getTransaction().begin();
//            em.merge(p1);
//            em.getTransaction().commit();
//        }
    }
}