package shop;

import base.generic.CrudRepository;

public interface ShopRepository extends CrudRepository<Shop, Long> {

    Long findProductCountInCorrespondingWarehouses(Long productId, Long shopId);
}
