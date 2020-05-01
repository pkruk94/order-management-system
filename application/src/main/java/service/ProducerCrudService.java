package service;

import dto.producer.CreateProducer;
import dto.producer.GetProducer;
import dto.producer.UpdateProducer;
import exception.ProducerServiceException;
import exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import mapper.Mapper;
import producer.ProducerRepository;
import validation.producer.CreateProducerValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProducerCrudService {

    // TODO for DTO select only the fields we will really use
    // TODO addres for producer
    private final ProducerRepository producerRepository;

    public Long addNewProducer(CreateProducer createProducer) {
        var createProducerValidator = new CreateProducerValidator();
        var errors = createProducerValidator.validate(createProducer);
        if (createProducerValidator.hasErrors()) {
            var errorMessage = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ProducerServiceException("Producer add validation error [" + errorMessage + " ]");
        }

        var producer = Mapper.fromCreateProducerToProducerEntity(createProducer);

        return producerRepository
                .addOrUpdate(producer)
                .orElseThrow(() -> new ProductServiceException("Could not add new product"))
                .getId();
    }

    // TODO add Many that way or warranties?
    public List<Long> addManyProducers(List<CreateProducer> createProducers) {
        List<Long> ids = new ArrayList<>();
        for (CreateProducer createProducer : createProducers) {
            try {
                ids.add(addNewProducer(createProducer));
            } catch (ProductServiceException e) {
                System.err.println(e.getMessage());
            }
        }
        return ids;
    }

    public Long updateProducer(UpdateProducer updateProducer) {

        var producer = producerRepository
                .findByID(updateProducer.getId())
                .orElseThrow(() -> new ProducerServiceException("Producer not found in database"));

        // TODO validate update

//        if (updateProducerValidator.hasErrors()) {
//            var errorMessage = errors
//                    .entrySet()
//                    .stream()
//                    .map(e -> e.getKey() + ": " + e.getValue())
//                    .collect(Collectors.joining(", "));
//            throw new ProductServiceException("Product update validation error [" + errorMessage + " ]");
//        }

        producer.setName(updateProducer.getName());
        producer.setMarket(updateProducer.getMarket());
        producer.setHqAddress(updateProducer.getHqAddress());

        return producerRepository
                .addOrUpdate(producer)
                .orElseThrow(() -> new ProductServiceException("Could not update producer"))
                .getId();
    }

    public List<Long> updateManyProducers(List<UpdateProducer> updateProducers) {
        List<Long> ids = new ArrayList<>();
        for (UpdateProducer updateProducer : updateProducers) {
            try {
                ids.add(updateProducer(updateProducer));
            } catch (ProductServiceException e) {
                System.err.println(e.getMessage());
            }
        }
        return ids;
    }

    public GetProducer findProductById(Long id) {
        if (id == null) {
            throw new ProducerServiceException("Product id cannot be null");
        }

        return Mapper.fromProducerEntityToGetProducer(
                producerRepository
                        .findByID(id)
                        .orElseThrow(() -> new ProductServiceException("Product could not be found")));
    }

    public List<GetProducer> findAllProductsByIds(List<Long> ids) {
        if (ids == null) {
            throw new ProducerServiceException("Product ids cannot be null");
        }

        return producerRepository
                .findAllByID(ids.stream().filter(Objects::nonNull).collect(Collectors.toList()))
                .stream()
                .map(Mapper::fromProducerEntityToGetProducer)
                .collect(Collectors.toList());
    }

    public List<GetProducer> findAll() {
        return producerRepository.findAll().stream().map(Mapper::fromProducerEntityToGetProducer).collect(Collectors.toList());
    }

    public GetProducer deleteByID(Long id) {
        if (id == null) {
            throw new ProducerServiceException("Product id cannot be null");
        }

        return Mapper.fromProducerEntityToGetProducer(producerRepository
                .deleteByID(id)
                .orElseThrow(() -> new ProductServiceException("Problem while deleting id")));
    }

    public List<GetProducer> deleteAllById(List<Long> ids) {
        if (ids == null) {
            throw new ProducerServiceException("IDs cannot be null");
        }

        return producerRepository
                .deleteAllByID(ids)
                .stream()
                .map(Mapper::fromProducerEntityToGetProducer)
                .collect(Collectors.toList());
    }

    public boolean deleteAllProducts() {
        return producerRepository.deleteAll();
    }
}
