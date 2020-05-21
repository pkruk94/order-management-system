package shop;

import base.generic.CrudRepository;
import product.Product;

import java.util.List;

public interface ShopRepository extends CrudRepository<Shop, Long> {

    Long findProductCountInCorrespondingWarehouses(Long productId, Long shopId);

}
