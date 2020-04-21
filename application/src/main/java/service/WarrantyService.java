package service;

import dto.CreateWarranty;
import exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import producer.Producer;
import validation.CreateProductValidator;
import validation.CreateWarrantyValidator;
import warranty.Warranty;
import warranty.WarrantyRepository;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;

    public Long createWarranty(CreateWarranty createWarranty) {
        var createWarrantyValidator = new CreateWarrantyValidator();
        var errors = createWarrantyValidator.validate(createProduct);
        if (createWarrantyValidator.hasErrors()) {
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

    public Long updateWarranty() {
        return null;
    }
}
