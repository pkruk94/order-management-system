package service;

import base.BaseEntity;
import dto.warranty.CreateWarranty;
import dto.warranty.GetWarranty;
import dto.warranty.UpdateWarranty;
import exception.ProductServiceException;
import exception.WarrantyServiceException;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import producer.ProducerRepository;
import validation.CreateWarrantyValidator;
import validation.UpdateWarrantyValidator;
import value_object.PercentageValue;
import warranty.Warranty;
import warranty.WarrantyRepository;
import warranty.WarrantyServices;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WarrantyCrudService {

    private final WarrantyRepository warrantyRepository;
    private final ProducerRepository producerRepository;

    public Long createWarranty(CreateWarranty createWarranty) {
        var createWarrantyValidator = new CreateWarrantyValidator();
        var errors = createWarrantyValidator.validate(createWarranty);
        if (createWarrantyValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProductServiceException("Warranty add validation error [" + errorMessage + " ]");
        }

        var producer = producerRepository
                .findByID(createWarranty.getProducerId())
                .orElseThrow(() -> new WarrantyServiceException("Warranty producer not found in database"));

        var warranty = Mapper.fromCreateWarrantyDTOtoWarrantyEntity(createWarranty);
        warranty.setProducer(producer);

        return warrantyRepository
                .addOrUpdate(warranty)
                .orElseThrow(() -> new WarrantyServiceException("Could not save new warranty"))
                .getId();
    }

    // TODO find only for valid, dont break?
    // ADD logging in such case
    public List<Long> createWarranty(List<CreateWarranty> createWarranties) {
        var createWarrantyValidator = new CreateWarrantyValidator();
        for (CreateWarranty createWarranty : createWarranties) {
            var errors = createWarrantyValidator.validate(createWarranty);
            if (createWarrantyValidator.hasErrors()) {
                var errorMessage = errors
                        .entrySet()
                        .stream()
                        .map(e -> e.getKey() + ": " + e.getValue())
                        .collect(Collectors.joining(", "));
                throw new ProductServiceException("Warranty add validation error [" + errorMessage + " ]");
            }
        }

        List<Warranty> warranties = createWarranties
                .stream()
                .map(createWarranty -> {
                    var warranty = Mapper.fromCreateWarrantyDTOtoWarrantyEntity(createWarranty);
                    warranty.setProducer(
                            producerRepository
                                    .findByID(createWarranty.getProducerId())
                                    .orElseThrow(() -> new WarrantyServiceException("Warranty producer not found in database")));
                    return warranty;
                })
                .collect(Collectors.toList());


        return warrantyRepository
                .addOrUpdateMany(warranties)
                .stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    public Long updateWarranty(UpdateWarranty updateWarranty) {

        var warranty = warrantyRepository
                .findByID(updateWarranty.getId())
                .orElseThrow(() -> new WarrantyServiceException("Warranty could not be found"));

        var updateWarrantyValidator = new UpdateWarrantyValidator();
        var errors = updateWarrantyValidator.validate(updateWarranty);
        if (updateWarrantyValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProductServiceException("Warranty update validation error [" + errorMessage + " ]");
        }

        var producer = producerRepository
                .findByID(updateWarranty.getProducerId())
                .orElseThrow(() -> new WarrantyServiceException("Warranty producer not found in database"));

        warranty.setCompletionTimeInDays(updateWarranty.getCompletionTimeInDays());
        warranty.setDurationYears(updateWarranty.getDurationYears());
        warranty.setFracPriceReturned(updateWarranty.getFracPriceReturned());
        warranty.setServicesAvailable(updateWarranty.getServicesAvailable());
        warranty.setProducer(producer);

        return warrantyRepository
                .addOrUpdate(warranty)
                .orElseThrow(() -> new WarrantyServiceException("Could not update warranty"))
                .getId();
    }

    public List<Long> updateWarranty(List<UpdateWarranty> updateWarranties) {
        var updateWarrantyValidator = new UpdateWarrantyValidator();
        for (UpdateWarranty updateWarranty : updateWarranties) {
            var errors = updateWarrantyValidator.validate(updateWarranty);
            if (updateWarrantyValidator.hasErrors()) {
                var errorMessage = errors
                        .entrySet()
                        .stream()
                        .map(e -> e.getKey() + ": " + e.getValue())
                        .collect(Collectors.joining(", "));
                throw new ProductServiceException("Warranty add validation error [" + errorMessage + " ]");
            }
        }

        List<Warranty> warranties = updateWarranties
                .stream()
                .map(updateWarranty -> {
                    var warranty = Mapper.fromUpdateWarrantyDTOtoWarrantyEntity(updateWarranty);
                    warranty.setProducer(
                            producerRepository
                                    .findByID(updateWarranty.getProducerId())
                                    .orElseThrow(() -> new WarrantyServiceException("Warranty producer not found in database")));
                    return warranty;
                })
                .collect(Collectors.toList());


        return warrantyRepository
                .addOrUpdateMany(warranties)
                .stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    public GetWarranty findWarrantyByID(Long warrantyID) {

        if (warrantyID == null) {
            throw new WarrantyServiceException("Warranty ID cannot be null");
        }

        return Mapper.fromWarrantyEntityToGetWarranty(warrantyRepository
                .findByID(warrantyID)
                .orElseThrow(() -> new WarrantyServiceException("Could not find warranty by id")));
    }

    public List<GetWarranty> findWarrantiesByIDs(List<Long> warrantyIDs) {
        if (warrantyIDs == null) {
            throw new WarrantyServiceException("Warranty list cannot be null");
        }

        // TODO logging when deleting null?
        return warrantyRepository
                .findAllByID(warrantyIDs.stream().filter(Objects::nonNull).collect(Collectors.toList()))
                .stream()
                .map(Mapper::fromWarrantyEntityToGetWarranty)
                .collect(Collectors.toList());
    }

    public List<GetWarranty> findAll() {
        return warrantyRepository.findAll().stream().map(Mapper::fromWarrantyEntityToGetWarranty).collect(Collectors.toList());
    }

    public GetWarranty deleteWarrantyById(Long id) {
        if (id == null) {
            throw new WarrantyServiceException("ID of warranty to be deleted cannot be null");
        }

        return Mapper.fromWarrantyEntityToGetWarranty(
                warrantyRepository
                        .deleteByID(id).orElseThrow(() -> new WarrantyServiceException("Could not delete or warranty does not exist")));
    }

    public List<GetWarranty> deleteWarrantiesByIDs(List<Long> warrantyIDs) {
        if (warrantyIDs == null) {
            throw new WarrantyServiceException("Warranty list cannot be null");
        }

        return warrantyRepository
                .deleteAllByID(warrantyIDs.stream().filter(Objects::nonNull).collect(Collectors.toList()))
                .stream()
                .map(Mapper::fromWarrantyEntityToGetWarranty)
                .collect(Collectors.toList());
    }

    public boolean deleteAll() {
        return warrantyRepository.deleteAll();
    }
}

