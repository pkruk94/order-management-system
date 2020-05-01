package service.warehouse;

import dto.warehouse.CreateWarehouse;
import dto.warehouse.GetWarehouse;
import dto.warehouse.UpdateWarehouse;
import exception.ProductServiceException;
import exception.WarehouseServiceException;
import exception.WarrantyServiceException;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import service.address.AddressCrudService;
import validation.address.AddressValidator;
import warehouse.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WarehouseCrudService {

    private WarehouseRepository warehouseRepository;
    private AddressCrudService addressCrudService;

    public Long addNewWarehouse(CreateWarehouse createWarehouse) {

        if (createWarehouse.getWarehouseAddress() == null) {
            throw new WarehouseServiceException("Warehouse must have an address");
        }

        var createAddressValidator = new AddressValidator();
        var errors = createAddressValidator.validate(createWarehouse.getWarehouseAddress().getAddressData());
        if (createAddressValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProductServiceException("Warehouse add validation error [" + errorMessage + " ]");
        }

        var warehouse = Mapper.fromCreateWarehouseToWarehouseEntity(createWarehouse);

        if (warehouseRepository.findByCity(createWarehouse.getWarehouseAddress().getAddressData().getCity()).isPresent()) {
            throw new WarehouseServiceException("Warehouse already exists in this city!");
        }

        return warehouseRepository
                .addOrUpdate(warehouse)
                .orElseThrow(() -> new ProductServiceException("Could not add new warehouse"))
                .getId();
    }

    // TODO add Many that way or warranties?
    public List<Long> addManyWarehouses(List<CreateWarehouse> createWarehouses) {
        List<Long> ids = new ArrayList<>();
        for (CreateWarehouse createWarehouse : createWarehouses) {
            try {
                ids.add(addNewWarehouse(createWarehouse));
            } catch (ProductServiceException e) {
                System.err.println(e.getMessage());
            }
        }
        return ids;
    }

    // TODO - refactor - just use updateAddress?
    public Long updateWarehouse(UpdateWarehouse updateWarehouse) {

        var warehouse = warehouseRepository
                .findByID(updateWarehouse.getId())
                .orElseThrow(() -> new WarrantyServiceException("Warehouse not found in database"));

        var updateWarehouseValidator = new AddressValidator();

        var errors = updateWarehouseValidator.validate(updateWarehouse.getUpdateAddress().getAddressData());

        if (updateWarehouseValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProductServiceException("Warehouse update validation error [" + errorMessage + " ]");
        }

        if (warehouseRepository.findByCity(updateWarehouse.getUpdateAddress().getAddressData().getCity()).isPresent()) {
            throw new WarehouseServiceException("Warehouse exists in this city");
        }

        addressCrudService.updateAddress(updateWarehouse.getUpdateAddress());

        return warehouseRepository
                .addOrUpdate(warehouse)
                .orElseThrow(() -> new WarehouseServiceException("Could not update warehouse"))
                .getId();
    }

    public List<Long> updateManyWarehouses(List<UpdateWarehouse> updateWarehouses) {
        List<Long> ids = new ArrayList<>();
        for (UpdateWarehouse updateWarehouse : updateWarehouses) {
            try {
                ids.add(updateWarehouse(updateWarehouse));
            } catch (ProductServiceException e) {
                System.err.println(e.getMessage());
            }
        }
        return ids;
    }

    public GetWarehouse findWarehouseById(Long id) {
        if (id == null) {
            throw new WarehouseServiceException("Warehouse id cannot be null");
        }

        return Mapper.fromWarehouseEntityToGetWarehouse(
                warehouseRepository
                        .findByID(id)
                        .orElseThrow(() -> new WarehouseServiceException("Warehouse could not be found")));
    }

    public List<GetWarehouse> findAllWarehousesByIds(List<Long> ids) {
        if (ids == null) {
            throw new WarehouseServiceException("Warehouse ids cannot be null");
        }

        return warehouseRepository
                .findAllByID(ids.stream().filter(Objects::nonNull).collect(Collectors.toList()))
                .stream()
                .map(Mapper::fromWarehouseEntityToGetWarehouse)
                .collect(Collectors.toList());
    }

    public List<GetWarehouse> findAll() {
        return warehouseRepository.findAll().stream().map(Mapper::fromWarehouseEntityToGetWarehouse).collect(Collectors.toList());
    }

    public GetWarehouse deleteByID(Long id) {
        if (id == null) {
            throw new WarehouseServiceException("Product id cannot be null");
        }

        return Mapper.fromWarehouseEntityToGetWarehouse(warehouseRepository
                .deleteByID(id)
                .orElseThrow(() -> new WarehouseServiceException("Problem while deleting id")));
    }

    public List<GetWarehouse> deleteAllById(List<Long> ids) {
        if (ids == null) {
            throw new WarehouseServiceException("IDs cannot be null");
        }

        return warehouseRepository
                .deleteAllByID(ids)
                .stream()
                .map(Mapper::fromWarehouseEntityToGetWarehouse)
                .collect(Collectors.toList());
    }

    public boolean deleteAllWarehouses() {
        return warehouseRepository.deleteAll();
    }
}
