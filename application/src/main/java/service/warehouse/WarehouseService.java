package service.warehouse;

import dto.warehouse.SupplyWarehouse;
import dto.warehouse.TransferProductsFromTo;
import exception.WarehouseServiceException;
import lombok.RequiredArgsConstructor;
import product.Product;
import product.ProductRepository;
import shop.Shop;
import shop.ShopRepository;
import value_object.Money;
import value_object.PercentageValue;
import warehouse.WarehouseRepository;
import warehouse_commodity.WarehouseCommodity;
import warehouse_commodity.WarehouseCommodityRepository;

@RequiredArgsConstructor
public class WarehouseService {

    private ProductRepository productRepository;
    private WarehouseRepository warehouseRepository;
    private ShopRepository shopRepository;
    private WarehouseCommodityRepository warehouseCommodityRepository;

    public boolean supplyWarehouseWithProduct(SupplyWarehouse supplyWarehouse) {
        if (supplyWarehouse == null) {
            throw new WarehouseServiceException("Data package invalid");
        }

        if (supplyWarehouse.getProductId() == null) {
            throw new WarehouseServiceException("Product id cannot be null");
        }

        if (supplyWarehouse.getWarehouseId() == null) {
            throw new WarehouseServiceException("Warehouse id cannot be null");
        }

        Product product = productRepository.findByID(supplyWarehouse.getProductId()).orElseThrow(() -> new WarehouseServiceException("Product could not be found"));
        Shop shop = shopRepository.findByID(supplyWarehouse.getShopId()).orElseThrow(() -> new WarehouseServiceException("Shop could not be found"));

        validatePurchaseAgainstShopBudget(shop.getBudget(), shop.getMaxSinglePurchaseBudgetFraction(), product.getPrice(), supplyWarehouse.getQuantity());

        shop.setBudget(shop.getBudget().subtract(product.getPrice().multiply(supplyWarehouse.getQuantity())));

        WarehouseCommodity warehouseCommodity = warehouseCommodityRepository.findByProductIaAndShopID(supplyWarehouse.getProductId(), supplyWarehouse.getWarehouseId()).orElseThrow(() -> new WarehouseServiceException("Warehouse commodity could not be found"));

        warehouseCommodity.setQuantity(supplyWarehouse.getQuantity() + warehouseCommodity.getQuantity());

        shopRepository.addOrUpdate(shop);
        warehouseCommodityRepository.addOrUpdate(warehouseCommodity);

        return true;
    }

    private void validatePurchaseAgainstShopBudget(Money budget, PercentageValue maxSinglePurchaseBudgetFraction, Money price, int quantity) {
        if (price.multiply(quantity).multiplyByFraction(maxSinglePurchaseBudgetFraction).compare(budget) > 0) {
            throw new WarehouseServiceException("Shop does not have funds for this purchase");
        }
    }

    public boolean transferFromWarehouseToWarehouse(TransferProductsFromTo transferProductsFromTo) {

        return true;
    }

}
