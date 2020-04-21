package service;

import dto.CreateProduct;
import exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import product.ProductRepository;
import validation.CreateProductValidator;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Long addNewProduct(CreateProduct createProduct) {
        var createProductValidator = new CreateProductValidator();
        var errors = createProductValidator.validate(createProduct);
        if (createProductValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProductServiceException("Product validation error [" + errorMessage + " ]");
        }

        var product = Mapper.fromCreateProductDTOtoProductEntity(createProduct);
        return productRepository
                .addOrUpdate(product)
                .orElseThrow(() -> new ProductServiceException("Could not add new product"))
                .getId();
    }

    public Long updateProduct(UpdateProduct updateProduct) {
        return null;
    }
}



    public List<GetProductDto> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(product -> {
                    var getProduct = Mapper.fromProductToGetProduct(product);
                    getProduct.setOrderPositionQuantity(orderPositionRepository.countProducts(product.getId()));
                    return getProduct;
                })
                .collect(Collectors.toList());
    }