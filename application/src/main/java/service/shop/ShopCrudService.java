package service.shop;

import dto.shop.CreateShop;
import dto.shop.GetShop;
import dto.shop.UpdateShop;
import exception.ProductServiceException;
import exception.ShopServiceException;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import shop.ShopRepository;
import validation.shop.CreateShopValidator;
import validation.shop.UpdateShopValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ShopCrudService {

    private ShopRepository shopRepository;

    public Long addNewShop(CreateShop createShop) {
        var createShopValidator = new CreateShopValidator();
        var errors = createShopValidator.validate(createShop);
        if (createShopValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProductServiceException("Shop add validation error [" + errorMessage + " ]");
        }

        var shop = Mapper.fromCreateShopToShopEntity(createShop);

        return shopRepository
                .addOrUpdate(shop)
                .orElseThrow(() -> new ShopServiceException("Could not add new shop"))
                .getId();
    }

    // TODO add Many that way or warranties?
    public List<Long> addManyProducts(List<CreateShop> createShops) {
        List<Long> ids = new ArrayList<>();
        for (CreateShop createShop : createShops) {
            try {
                ids.add(addNewShop(createShop));
            } catch (ShopServiceException e) {
                System.err.println(e.getMessage());
            }
        }
        return ids;
    }

    public Long updateShop(UpdateShop updateShop) {

        var shop = shopRepository
                .findByID(updateShop.getId())
                .orElseThrow(() -> new ShopServiceException("Shop not found in database"));

        var updateShopValidator = new UpdateShopValidator();
        var errors = updateShopValidator.validate(updateShop);
        if (updateShopValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProductServiceException("Shop update validation error [" + errorMessage + " ]");
        }

        return shopRepository
                .addOrUpdate(shop)
                .orElseThrow(() -> new ShopServiceException("Could not add new product"))
                .getId();
    }

    public List<Long> updateManyShops(List<UpdateShop> updateShops) {
        List<Long> ids = new ArrayList<>();
        for (UpdateShop updateShop : updateShops) {
            try {
                ids.add(updateShop(updateShop));
            } catch (ProductServiceException e) {
                System.err.println(e.getMessage());
            }
        }
        return ids;
    }

    public GetShop findShopByID(Long id) {
        if (id == null) {
            throw new ShopServiceException("Product id cannot be null");
        }

        return Mapper.fromShopEntityToGetShop(
                shopRepository
                        .findByID(id)
                        .orElseThrow(() -> new ShopServiceException("Shop could not be found")));
    }

    public List<GetShop> findAllShopsByIds(List<Long> ids) {
        if (ids == null) {
            throw new ProductServiceException("Product ids cannot be null");
        }

        return shopRepository
                .findAllByID(ids.stream().filter(Objects::nonNull).collect(Collectors.toList()))
                .stream()
                .map(Mapper::fromShopEntityToGetShop)
                .collect(Collectors.toList());
    }

    public List<GetShop> findAll() {
        return shopRepository.findAll().stream().map(Mapper::fromShopEntityToGetShop).collect(Collectors.toList());
    }

    public GetShop deleteByID(Long id) {
        if (id == null) {
            throw new ShopServiceException("Shop id cannot be null");
        }

        return Mapper.fromShopEntityToGetShop(shopRepository
                .deleteByID(id)
                .orElseThrow(() -> new ShopServiceException("Problem while deleting id")));
    }

    public List<GetShop> deleteAllById(List<Long> ids) {
        if (ids == null) {
            throw new ShopServiceException("IDs cannot be null");
        }

        return shopRepository
                .deleteAllByID(ids)
                .stream()
                .map(Mapper::fromShopEntityToGetShop)
                .collect(Collectors.toList());
    }

    public boolean deleteAllShops() {
        return shopRepository.deleteAll();
    }
}
