package repository.generic;

import connection.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbstractCrudRepository<T, ID> implements CrudRepository<T, ID> {

    protected final EntityManagerFactory emf = DbConnection.getInstance().getEntityManagerFactory();

    private final Class<T> entityType = (Class<T>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private final Class<ID> idType = (Class<ID>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

    @Override
    public Optional<T> addOrUpdate(T t) {
        EntityManager em = null;
        EntityTransaction et = null;
        Optional<T> element = Optional.empty();
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            element = Optional.ofNullable(em.merge(t));
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
        return element;
    }

    @Override
    public List<T> addOrUpdateMany(List<T> items) {
        EntityManager em = null;
        EntityTransaction et = null;
        List<T> insertedItems = new ArrayList<>();
        int batchSize = 2;
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            for (int i = 0; i < items.size(); i++) {
                insertedItems.add(em.merge(items.get(i)));
                if (i % batchSize == 0) {
                    em.flush();
                    em.clear();
                }
            }
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
        return insertedItems;
    }

    @Override
    public Optional<T> findByID(ID id) {
        EntityManager em = null;
        EntityTransaction et = null;
        Optional<T> element = Optional.empty();
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            element = Optional.ofNullable(em.find(entityType, id));
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        }
        return element;
    }

    @Override
    public List<T> findAllByID(List<ID> ids) {
        EntityManager em = null;
        EntityTransaction et = null;
        List<T> elements = new ArrayList<>();
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            elements = em
                    .createQuery("select e from " + entityType.getSimpleName() + " e where e.id in :ids", entityType)
                    .setParameter("ids", ids)
                    .getResultList();
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
        return elements;
    }

    @Override
    public List<T> findAll() {
        EntityManager em = null;
        EntityTransaction et = null;
        List<T> elements = new ArrayList<>();
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            elements = em
                    .createQuery("select e from " + entityType.getSimpleName() + " e", entityType)
                    .getResultList();
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
        return elements;
    }

    @Override
    public Optional<T> deleteByID(ID id) {
        EntityManager em = null;
        EntityTransaction et = null;
        Optional<T> deletedElement = Optional.empty();
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            deletedElement = Optional.ofNullable(em.find(entityType, id));
            em.remove(em.find(entityType,id));
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
        return deletedElement;
    }

    @Override
    public List<T> deleteAllByID(List<ID> ids) {
        EntityManager em = null;
        EntityTransaction et = null;
        List<T> deletedItems = new ArrayList<>();
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();

            deletedItems = em
                    .createQuery("select  e from " + entityType.getSimpleName() + " e where e.id in :ids", entityType)
                    .setParameter("ids", ids)
                    .getResultList();

            em
                    .createQuery("delete from " + entityType.getSimpleName() + " e where e.id in :ids")
                    .setParameter("ids", ids)
                    .executeUpdate();

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
        return deletedItems;
    }

    @Override
    public boolean deleteAll() {
        EntityManager em = null;
        EntityTransaction et = null;
        boolean result = false;
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em
                    .createQuery("delete from " + entityType.getSimpleName())
                    .executeUpdate();
            et.commit();
            result = true;
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }
}
