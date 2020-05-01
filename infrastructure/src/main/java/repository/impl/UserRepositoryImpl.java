package repository.impl;

import repository.generic.AbstractCrudRepository;
import user.User;
import user.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class UserRepositoryImpl extends AbstractCrudRepository<User, Long> implements UserRepository {

    @Override
    public Optional<User> findManagerWithLowestNumOfCustomers() {
        EntityManager em = null;
        EntityTransaction et = null;
        Optional<User> user = Optional.empty();
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            String query = "select w.manager_id from (select u.manager_id, count(u.id) as numOfClients from users u group by u.manager_id having u.manager_id is not null) w" +
                    " join (select min(t.numOfClients) as minNumOfClients from " +
                    "(select u.manager_id, count(u.id) as numOfClients from users u group by u.manager_id having u.manager_id is not null) t) u " +
                    "on w.numOfClients = u.minNumOfClients limit 1;";
            em.createNativeQuery(query);
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
        return user;
    }
}
