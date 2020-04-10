package repository.generic;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    Optional<T> addOrUpdate(T t);
    List<T> addOrUpdateMany(List<T> items);
    Optional<T> findByID(ID id);
    List<T> findAllByID(List<ID> ids);
    List<T> findAll();
    Optional<T> deleteByID(ID id);
    List<T> deleteAllByID(List<ID> ids);
    boolean deleteAll();

}
