package repository.impl;

import base.generic.CrudRepository;
import repository.generic.AbstractCrudRepository;
import warehouse.Warehouse;
import warehouse_commodity.WarehouseCommodity;
import warehouse_commodity.WarehouseCommodityRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class WarehouseCommodityRepositoryImpl extends AbstractCrudRepository<WarehouseCommodity, Long> implements WarehouseCommodityRepository {
    @Override
    public Optional<WarehouseCommodity> findByProductID(Long productID, Long warehouseID) {
        EntityManager em = null;
        EntityTransaction et = null;
        Optional<WarehouseCommodity> warehouseCommodity = Optional.empty();
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            warehouseCommodity = Optional.of(em.createQuery("select wc from WarehouseCommodity wc where wc.product.id = :productID and wc.warehouse.id = :warehouseID", WarehouseCommodity.class)
                    .setParameter("productID", productID)
                    .setParameter("warehouseID", warehouseID)
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
        return warehouseCommodity;
    }
}
