package exercise1;

import config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.Arrays;
import java.util.List;

public class DolphinDAO {
    EntityManagerFactory emf;

    public DolphinDAO(boolean isTest) {
        emf = HibernateConfig.getEntityManagerFactoryConfig("dolphin",isTest);
    }



    public void createNewPerson(Person p) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        }
    }

    public void updatePerson(Person p) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
        }
    }

    public Person getPerson(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Person p = em.find(Person.class, id);
            em.detach(p);
            return p;
        }
    }

    public List<Person> getAll(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<Person> query = em.createQuery("Select p FROM Person p", Person.class);
            List<Person> People = query.getResultList();
            em.detach(People);
            return People;
        }
    }

    public double getTotalFeeAmount(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Person p = em.find(Person.class, id);
            em.detach(p);
            double sum = 0;
            Fee[] fees =p.getFees().toArray(new Fee[p.getFees().size()]);
            for(int i = 0; i<fees.length;i++) {
                sum += fees[i].getAmount();
            }
            return sum;
        }
    }
    public List getNotes(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Person p = em.find(Person.class, id);
            em.detach(p);
            double sum = 0;
            Note[] notes =p.getNotes().toArray(new Note[p.getNotes().size()]);
            return Arrays.stream(notes).toList();
        }
    }

    public void printNotesAndNames() {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<NotesAndNamesDTO> query = em.createQuery(
                "select new exercise1.NotesAndNamesDTO(n.note,p.name,d.age) FROM Person p Join p.notes n JOIN p.personDetail d"
            , NotesAndNamesDTO.class);
            List<NotesAndNamesDTO> result = query.getResultList();
            result.forEach(System.out::println);
        }
    }

    public void close() {
        emf.close();
    }
}
