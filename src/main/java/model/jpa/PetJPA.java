package model.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import model.entities.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetJPA {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GR04_1BT2_622_25A");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Pet pet) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(pet);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error when creating pet");
            return false;
        } finally {
            em.close();
        }
    }

    public boolean update(Pet pet) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Pet existingPet = em.find(Pet.class, pet.getIdPet());
            if (existingPet != null) {
                copyAddressProperties(existingPet, pet);
                em.merge(existingPet);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error when updating pet");
            return false;
        } finally {
            em.close();
        }
    }

    public Pet findPetById(int idPet) {
        try (EntityManager em = getEntityManager()) {
            return em.find(Pet.class, idPet);
        } catch (Exception e) {
            System.out.println("Error when searching for pet");
            return null;
        }
    }

    public boolean remove(int idPet) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Pet pet = em.find(Pet.class, idPet);
            if (pet != null) {
                em.remove(pet);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error when removing pet");
            return false;
        } finally {
            em.close();
        }
    }

    private void copyAddressProperties(Pet target, Pet source) {
        target.setName(source.getName());
        target.setSpecies(source.getSpecies());
        target.setBreed(source.getBreed());
        target.setColor(source.getColor());
        target.setOwnerName(source.getOwnerName());
    }

    public List<Pet> getAllPets() {
        List<Pet> products = new ArrayList<>();
        String jpql = "SELECT p FROM Pet p";
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Pet> query = em.createQuery(jpql, Pet.class);
            products = query.getResultList();
        } catch (Exception e) {
            System.err.println("Couldn't fetch all products: " + e.getMessage());
        }
        return products;
    }
}
