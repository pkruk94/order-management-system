package mapper;

import dto.CreateProduct;
import offered_commodity.OfferedCommodity;
import order_position.OrderPosition;
import producer.Producer;
import product.Product;
import product.ProductCategory;
import value_object.Money;
import warehouse_commodity.WarehouseCommodity;
import warranty.Warranty;

import javax.persistence.*;
import java.util.List;

public interface Mapper {
    static Product fromCreateProductDTOtoProductEntity(CreateProduct createProduct) {
        return Product.builder()
                .name(createProduct.getName())
                .price(createProduct.getPrice())
                .productCategory(createProduct.getProductCategory())
                .build();
    }
}