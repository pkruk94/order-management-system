package service;

import address.AddressRepository;
import dto.address.CreateAddress;
import dto.address.GetAddress;
import dto.address.UpdateAddress;
import dto.product.CreateProduct;
import dto.product.UpdateProduct;
import exception.AddressServiceException;
import exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import producer.Producer;
import validation.AddressValidator;
import validation.UpdateProductValidator;
import warranty.Warranty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AddressCrudService {

    private AddressRepository addressRepository;

    public Long addNewAddress(CreateAddress createAddress) {
        var addressValidator = new AddressValidator();
        var errors = addressValidator.validate(createAddress);
        if (addressValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new AddressServiceException("Address add validation error [" + errorMessage + " ]");
        }

        return addressRepository
                .addOrUpdate(Mapper.fromCreateAddressToAddressEntity(createAddress))
                .orElseThrow(() -> new ProductServiceException("Could not add new product"))
                .getId();
    }

    // TODO add Many that way or warranties?
    // Maybe returne num of added?
    public List<Long> addManyProducts(List<CreateAddress> createAddresses) {
        List<Long> ids = new ArrayList<>();
        for (CreateAddress createAddress : createAddresses) {
            try {
                ids.add(addNewAddress(createAddress));
            } catch (ProductServiceException e) {
                System.err.println(e.getMessage());
            }
        }
        return ids;
    }

    public Long updateAddress(UpdateAddress updateAddress) {

        var address = addressRepository
                .findByID(updateAddress.getId())
                .orElseThrow(() -> new AddressServiceException("Address not found in database"));

        var addressValidator = new AddressValidator();
        var errors = addressValidator.validate(CreateAddress.builder()
                .addressLine(updateAddress.getAddressLine())
                .city(updateAddress.getCity())
                .zipCode(updateAddress.getZipCode())
                .build());

        if (addressValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProductServiceException("Address update validation error [" + errorMessage + " ]");
        }

        address.setAddressLine(updateAddress.getAddressLine());
        address.setCity(updateAddress.getCity());
        address.setZipCode(updateAddress.getZipCode());

        return addressRepository
                .addOrUpdate(address)
                .orElseThrow(() -> new AddressServiceException("Could not update address"))
                .getId();
    }

    public List<Long> updateManyAddresses(List<UpdateAddress> updateAddresses) {
        List<Long> ids = new ArrayList<>();
        for (UpdateAddress updateAddress : updateAddresses) {
            try {
                ids.add(updateAddress(updateAddress));
            } catch (ProductServiceException e) {
                System.err.println(e.getMessage());
            }
        }
        return ids;
    }

    public GetAddress findAddressById(Long id) {
        if (id == null) {
            throw new AddressServiceException("Product id cannot be null");
        }

        return Mapper.fromAddressEntityToGetAddress(
                addressRepository
                        .findByID(id)
                        .orElseThrow(() -> new ProductServiceException("Product could not be found")));
    }

    public List<GetAddress> findAllAddressesByIds(List<Long> ids) {
        if (ids == null) {
            throw new AddressServiceException("Product ids cannot be null");
        }

        return addressRepository
                .findAllByID(ids.stream().filter(Objects::nonNull).collect(Collectors.toList()))
                .stream()
                .map(Mapper::fromAddressEntityToGetAddress)
                .collect(Collectors.toList());
    }

    public List<GetAddress> findAll() {
        return addressRepository.findAll().stream().map(Mapper::fromAddressEntityToGetAddress).collect(Collectors.toList());
    }

    public GetAddress deleteByID(Long id) {
        if (id == null) {
            throw new AddressServiceException("Product id cannot be null");
        }

        return Mapper.fromAddressEntityToGetAddress(addressRepository
                .deleteByID(id)
                .orElseThrow(() -> new AddressServiceException("Problem while deleting id")));
    }

    public List<GetAddress> deleteAllById(List<Long> ids) {
        if (ids == null) {
            throw new AddressServiceException("IDs cannot be null");
        }

        return addressRepository
                .deleteAllByID(ids)
                .stream()
                .map(Mapper::fromAddressEntityToGetAddress)
                .collect(Collectors.toList());
    }

    public boolean deleteAllAddresses() {
        return addressRepository.deleteAll();
    }
}
