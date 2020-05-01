package service.product;

import dto.product.CreateProduct;
import dto.product.GetProduct;
import dto.product.UpdateProduct;
import exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import producer.Producer;
import producer.ProducerRepository;
import product.ProductRepository;
import validation.product.CreateProductValidator;
import validation.product.UpdateProductValidator;
import warranty.Warranty;
import warranty.WarrantyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductCrudService {

    //TODO check if warranty matches producer

    // TODO finding with filtering/sorting?
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

    // TODO add Many that way or warranties?
    public List<Long> addManyProducts(List<CreateProduct> createProducts) {
        List<Long> ids = new ArrayList<>();
        for (CreateProduct createProduct : createProducts) {
            try {
                ids.add(addNewProduct(createProduct));
            } catch (ProductServiceException e) {
                System.err.println(e.getMessage());
            }
        }
        return ids;
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

    public List<Long> updateManyProducts(List<UpdateProduct> updateProducts) {
        List<Long> ids = new ArrayList<>();
        for (UpdateProduct updateProduct : updateProducts) {
            try {
                ids.add(updateProduct(updateProduct));
            } catch (ProductServiceException e) {
                System.err.println(e.getMessage());
            }
        }
        return ids;
    }

    public GetProduct findProductById(Long id) {
        if (id == null) {
            throw new ProductServiceException("Product id cannot be null");
        }

        return Mapper.fromProductEntityToGetProduct(
                productRepository
                        .findByID(id)
                        .orElseThrow(() -> new ProductServiceException("Product could not be found")));
    }

    public List<GetProduct> findAllProductsByIds(List<Long> ids) {
        if (ids == null) {
            throw new ProductServiceException("Product ids cannot be null");
        }

        return productRepository
                .findAllByID(ids.stream().filter(Objects::nonNull).collect(Collectors.toList()))
                .stream()
                .map(Mapper::fromProductEntityToGetProduct)
                .collect(Collectors.toList());
    }

    public List<GetProduct> findAll() {
        return productRepository.findAll().stream().map(Mapper::fromProductEntityToGetProduct).collect(Collectors.toList());
    }

    public GetProduct deleteByID(Long id) {
        if (id == null) {
            throw new ProductServiceException("Product id cannot be null");
        }

        return Mapper.fromProductEntityToGetProduct(productRepository
                .deleteByID(id)
                .orElseThrow(() -> new ProductServiceException("Problem while deleting id")));
    }

    public List<GetProduct> deleteAllById(List<Long> ids) {
        if (ids == null) {
            throw new ProductServiceException("IDs cannot be null");
        }

        return productRepository
                .deleteAllByID(ids)
                .stream()
                .map(Mapper::fromProductEntityToGetProduct)
                .collect(Collectors.toList());
    }

    public boolean deleteAllProducts() {
        return productRepository.deleteAll();
    }


}