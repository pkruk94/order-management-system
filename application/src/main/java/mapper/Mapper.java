package mapper;

import dto.CreateProduct;
import dto.CreateWarranty;
import dto.GetWarranty;
import dto.UpdateWarranty;
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
}