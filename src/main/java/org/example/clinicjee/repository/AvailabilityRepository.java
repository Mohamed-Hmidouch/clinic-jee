package org.example.clinicjee.repository;

import org.example.clinicjee.config.JPAUtil;
import org.example.clinicjee.domain.Availability;
import org.example.clinicjee.domain.enums.JourSemaine;
import org.example.clinicjee.repository.interfaces.AvailabilityRepositoryInterface;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.List;



public class AvailabilityRepository implements AvailabilityRepositoryInterface {



    @Override
    public Optional<Availability> findByDoctorIdAndJour(Long doctorId, JourSemaine jour) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
            List<Availability> availabilities = em.createQuery("SELECT a FROM Availability a WHERE a.doctor.id = :doctorId AND a.jour = :jour",Availability.class)
            .setParameter("doctorId",doctorId)
            .setParameter("jour", jour)
            .setMaxResults(1)
            .getResultList();
            return availabilities.isEmpty() ? Optional.empty() : Optional.of(availabilities.get(0));
        }catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }finally{
            em.close();
        }
    }
    public Availability save(Availability availability)
    {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (availability.getId() == null) {
                em.persist(availability);
            } else {
                availability = em.merge(availability);
            }
            em.getTransaction().commit();
            return availability;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde d'availivalty", e);
        } finally {
            em.close();
        }  
    }

    public  Availability update(Availability availability)
    {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            availability = em.merge(availability);
            em.getTransaction().commit();
            return availability;
        } catch (Exception e) {
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde d'availivalty", e);
        }finally{
            em.close();
        }
    }
}