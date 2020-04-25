package mapper;

import address.Address;
import dto.address.CreateAddress;
import dto.address.GetAddress;
import dto.producer.CreateProducer;
import dto.producer.GetProducer;
import dto.product.CreateProduct;
import dto.product.GetProduct;
import dto.warranty.CreateWarranty;
import dto.warranty.GetWarranty;
import dto.warranty.UpdateWarranty;
import producer.Producer;
import product.Product;
import warranty.Warranty;

public interface Mapper {
    static Product fromCreateProductDTOtoProductEntity(CreateProduct createProduct) {
        return Product.builder()
                .name(createProduct.getName())
                .price(createProduct.getPrice())
                .productCategory(createProduct.getProductCategory())
                .build();
    }

    static Warranty fromCreateWarrantyDTOtoWarrantyEntity(CreateWarranty createWarranty) {
        return Warranty.builder()
                .completionTimeInDays(createWarranty.getCompletionTimeInDays())
                .durationYears(createWarranty.getDurationYears())
                .fracPriceReturned(createWarranty.getFracPriceReturned())
                .servicesAvailable(createWarranty.getServicesAvailable())
                .build();
    }

    static GetWarranty fromWarrantyEntityToGetWarranty(Warranty warranty) {
        return GetWarranty.builder()
                .completionTimeInDays(warranty.getCompletionTimeInDays())
                .durationYears(warranty.getDurationYears())
                .fracPriceReturned(warranty.getFracPriceReturned())
                .servicesAvailable(warranty.getServicesAvailable())
                .id(warranty.getId())
                .producerId(warranty.getProducer().getId())
                .build();
    }

    static Warranty fromUpdateWarrantyDTOtoWarrantyEntity(UpdateWarranty updateWarranty) {
        return Warranty.builder()
                .servicesAvailable(updateWarranty.getServicesAvailable())
                .fracPriceReturned(updateWarranty.getFracPriceReturned())
                .durationYears(updateWarranty.getDurationYears())
                .completionTimeInDays(updateWarranty.getCompletionTimeInDays())
                .id(updateWarranty.getId())
                .build();
    }

    static GetProduct fromProductEntityToGetProduct(Product product) {
        return GetProduct.builder()
                .warrantyPolicyId(product.getWarrantyPolicy().getId())
                .productCategory(product.getProductCategory())
                .producerId(product.getProducer().getId())
                .price(product.getPrice())
                .name(product.getName())
                .id(product.getId())
                .build();
    }

    static Producer fromCreateProducerToProducerEntity(CreateProducer createProducer) {
        return Producer.builder()
                .name(createProducer.getName())
                .market(createProducer.getMarket())
                .hqAddress(createProducer.getHqAddress())
                .build();
    }

    static GetProducer fromProducerEntityToGetProducer(Producer producer) {
        return GetProducer.builder()
                .id(producer.getId())
                .market(producer.getMarket())
                .name(producer.getName())
                .hqAddress(producer.getHqAddress())
                .build();
    }

    static GetAddress fromAddressEntityToGetAddress(Address address) {
        return GetAddress.builder()
                .addressLine(address.getAddressLine())
                .city(address.getCity())
                .id(address.getId())
                .zipCode(address.getZipCode())
                .build();
    }

    static Address fromCreateAddressToAddressEntity(CreateAddress createAddress) {
        return Address.builder()
                .addressLine(createAddress.getAddressLine())
                .city(createAddress.getCity())
                .zipCode(createAddress.getZipCode())
                .build();
    }
}