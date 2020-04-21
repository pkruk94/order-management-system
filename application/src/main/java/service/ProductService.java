package service;

import dto.CreateProduct;
import lombok.RequiredArgsConstructor;
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
            throw new
        }
    }
}



    public Long createProduct(CreateProductDto createProductDto) {
        var createProductValidator = new CreateProductValidator();
        var errors = createProductValidator.validate(createProductDto);
        if (createProductValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProductServiceException("Product validation error [ " + errorMessage + " ]");
        }

        var product = Mapper.fromCreateProductToProduct(createProductDto);
        return productRepository
                .addOrUpdate(product)
                .orElseThrow(() -> new ProductServiceException("cannot insert product"))
                .getId();
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