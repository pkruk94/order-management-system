package repository.impl;

import repository.generic.AbstractCrudRepository;
import shop.Shop;
import shop.ShopRepository;
import warehouse.Warehouse;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class ShopRepositoryImpl extends AbstractCrudRepository<Shop, Long> implements ShopRepository {
    @Override
    public Long findProductCountInCorrespondingWarehouses(Long productId, Long shopId) {
        EntityManager em = null;
        EntityTransaction et = null;
        long count = 0L;
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            count = (long) em.createQuery(
                    "select sum(c.quantity) from Shop s join s.warehouses w join w.commodities c join c.product p where p.id = :productId and s.id = :shopId"
            ).executeUpdate();
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
        return count;
    }
}

