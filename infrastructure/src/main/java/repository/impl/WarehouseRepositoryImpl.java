package repository.impl;

import repository.generic.AbstractCrudRepository;
import warehouse.Warehouse;
import warehouse.WarehouseRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
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

    @Override
    public Optional<Warehouse> findWarehouseWithMaxProductCountFromWarehouses(Long productId, List<Long> warehousesID) {
        EntityManager em = null;
        EntityTransaction et = null;
        Optional<Warehouse> warehouse = Optional.empty();
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            Query query = em.createNativeQuery("select warehouse_id, warehouse_addresId " +
                    "from (select w.id as warehouse_id, w.address_id as warehouse_addresId, wc.product_id as product_id, wc.quantity as prod_quantity from warehouses w join warehouse_commodities wc on w.id = wc.warehouse_id) t " +
                    "join (select wc.product_id as product_id, max(wc.quantity) as max_prod_quantity from warehouse_commodities wc where wc.product_id = :productId and wc.warehouse_id in :warehousesID) h " +
                    "on t.product_id = h.product_id and t.prod_quantity = h.max_prod_quantity limit 1;", Warehouse.class);
            query.setParameter("productId", productId);
            query.setParameter("warehousesID", warehousesID);
            warehouse = Optional.of((Warehouse) query.getSingleResult());
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

