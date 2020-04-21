package service;

import dto.CreateProduct;
import dto.UpdateProduct;
import exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import producer.Producer;
import producer.ProducerRepository;
import product.ProductRepository;
import validation.CreateProductValidator;
import validation.UpdateProductValidator;
import warranty.Warranty;
import warranty.WarrantyRepository;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductService {

//    List<T> addOrUpdateMany(List<T> items);
//    Optional<T> findByID(ID id);
//    List<T> findAllByID(List<ID> ids);
//    List<T> findAll();
//    Optional<T> deleteByID(ID id);
//    List<T> deleteAllByID(List<ID> ids);
//    boolean deleteAll();

    //TODO check if warranty matches producer
    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;
    private final WarrantyRepository warrantyRepository;

    public Long addNewProduct(CreateProduct createProduct) {
        var createProductValidator = new CreateProductValidator();
        var errors = createProductValidator.validate(createProduct);
        if (createProductValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProductServiceException("Product add validation error [" + errorMessage + " ]");
        }

        var product = Mapper.fromCreateProductDTOtoProductEntity(createProduct);

        Producer producer = producerRepository.
                findByID(createProduct.getProducerId())
                .orElseThrow(() -> new ProductServiceException("Producer could not be fetched from DB"));

        Warranty warrantyPolicy = warrantyRepository
                .findByID(createProduct.getWarrantyPolicyId())
                .orElseThrow(() -> new ProductServiceException("Warranty policy could not be fetched from DB"));

        product.setProducer(producer);
        product.setWarrantyPolicy(warrantyPolicy);

        return productRepository
                .addOrUpdate(product)
                .orElseThrow(() -> new ProductServiceException("Could not add new product"))
                .getId();
    }

    public Long updateProduct(UpdateProduct updateProduct) {

        var product = productRepository
                .findByID(updateProduct.getProductId())
                .orElseThrow(() -> new ProductServiceException("Product not found in database"));

        var updateProductValidator = new UpdateProductValidator();
        var errors = updateProductValidator.validate(updateProduct);
        if (updateProductValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProductServiceException("Product update validation error [" + errorMessage + " ]");
        }

        product.setName(updateProduct.getName());
        product.setPrice(updateProduct.getPrice());
        product.setProductCategory(updateProduct.getProductCategory());

        if (!product.getProducer().getId().equals(updateProduct.getProducerId())) {
            product
                    .setProducer(producerRepository.
                            findByID(updateProduct.getProducerId())
                            .orElseThrow(() -> new ProductServiceException("Producer could not be fetched from DB")));
        }

        if (!product.getWarrantyPolicy().getId().equals(updateProduct.getWarrantyPolicyId())) {
            product
                    .setWarrantyPolicy(
                            warrantyRepository
                                    .findByID(updateProduct.getWarrantyPolicyId())
                                    .orElseThrow(() -> new ProductServiceException("Warranty policy could not be fetched from DB"))
                    );
        }

        return productRepository
                .addOrUpdate(product)
                .orElseThrow(() -> new ProductServiceException("Could not add new product"))
                .getId();
    }


}