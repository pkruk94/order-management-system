package warehouse;

import base.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {
    Optional<Warehouse> findByCity(String city);

    Optional<Warehouse> findWarehouseWithMaxProductCountFromWarehouses(Long productId, List<Long> warehousesID);
}
