package warehouse;

import base.generic.CrudRepository;

import java.util.Optional;

public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {
    Optional<Warehouse> findByCity(String city);
}
