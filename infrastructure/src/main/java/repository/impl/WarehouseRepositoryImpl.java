package repository.impl;

import repository.generic.AbstractCrudRepository;
import warehouse.Warehouse;
import warehouse.WarehouseRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class WarehouseRepositoryImpl extends AbstractCrudRepository<Warehouse, Long> implements WarehouseRepository {
    @Override
    public Optional<Warehouse> findByCity(String city) {
        EntityManager em = null;
        EntityTransaction et = null;
        Optional<Warehouse> warehouse = Optional.empty();
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            warehouse = Optional.of(em.createQuery("select w from Warehouse w join w.address a where a.city = :city", Warehouse.class)
                    .setParameter("city", city)
                    .getSingleResult());
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return warehouse;
    }
}

