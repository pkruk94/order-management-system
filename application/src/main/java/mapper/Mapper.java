package mapper;

import address.Address;
import dto.address.CreateAddress;
import dto.address.GetAddress;
import dto.producer.CreateProducer;
import dto.producer.GetProducer;
import dto.product.CreateProduct;
import dto.product.GetProduct;
import dto.shop.CreateShop;
import dto.shop.GetShop;
import dto.warehouse.CreateWarehouse;
import dto.warehouse.GetWarehouse;
import dto.warranty.CreateWarranty;
import dto.warranty.GetWarranty;
import dto.warranty.UpdateWarranty;
import producer.Producer;
import product.Product;
import shop.Shop;
import value_object.Money;
import value_object.PercentageValue;
import warehouse.Warehouse;
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
                .addressLine(createAddress.getAddressData().getAddressLine())
                .city(createAddress.getAddressData().getCity())
                .zipCode(createAddress.getAddressData().getZipCode())
                .build();
    }

    static Warehouse fromCreateWarehouseToWarehouseEntity(CreateWarehouse createWarehouse) {
        return Warehouse.builder()
                .address(
                        fromCreateAddressToAddressEntity(createWarehouse.getWarehouseAddress())
                )
                .build();
    }

    static GetWarehouse fromWarehouseEntityToGetWarehouse(Warehouse warehouse) {
        return GetWarehouse
                .builder()
                .addressId(warehouse.getAddress().getId())
                .id(warehouse.getId())
                .build();
    }

    static Shop fromCreateShopToShopEntity(CreateShop createShop) {
        return Shop.builder()
                .address(fromCreateAddressToAddressEntity(createShop.getAddress()))
                .budget(new Money(String.valueOf(createShop.getBudget())))
                .name(createShop.getName())
                .maxSinglePurchaseBudgetFraction(new PercentageValue(String.valueOf(createShop.getMaxSinglePurchaseBudgetFraction())))
                .build();
    }

    // TODO conversion from value objects??
    static GetShop fromShopEntityToGetShop(Shop shop) {
        return GetShop.builder()
                .name(shop.getName())
                .money(shop.getBudget().getValue())
                .percentageValue(shop.getMaxSinglePurchaseBudgetFraction().getDecimalValue())
                .userManagerID(shop.getUserManager().getId())
                .addressID(shop.getAddress().getId())
                .build();
    }
}