package mapper;

import dto.CreateProduct;
import product.Product;

public interface Mapper {
    static Product fromCreateProductDTOtoProductEntity(CreateProduct createProduct) {
        return Product.builder()
                .name(createProduct.getName())
                .price(createProduct.getPrice())
                .productCategory(createProduct.getProductCategory())
                .build();
    }
}