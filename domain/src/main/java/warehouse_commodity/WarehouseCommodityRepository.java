package warehouse_commodity;

import base.generic.CrudRepository;

import java.util.Optional;

public interface WarehouseCommodityRepository extends CrudRepository<WarehouseCommodity, Long> {
    Optional<WarehouseCommodity> findByProductID(Long productID, Long warehouseID);
}
