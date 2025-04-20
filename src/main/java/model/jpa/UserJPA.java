package model.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import model.entities.User;

public class UserJPA {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GR04_1BT2_622_25A");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User findUserByDni(String dni) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery("SELECT u FROM User u WHERE u.dni = :dni", User.class)
                    .setParameter("dni", dni)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to find the user by DNI");
            return null;
        }
    }

    public boolean create(User user) {
        EntityManager em = getEntityManager();
        System.out.println("Creating user: " + user.getDni() + " " + user.getPassword());
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error when creating user");
            return false;
        } finally {
            em.close();
        }
    }
}
