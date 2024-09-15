package college.pb.managementunitapi.service;

import college.pb.managementunitapi.model.entity.Store;
import college.pb.managementunitapi.model.mapper.StoreMapper;
import college.pb.managementunitapi.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public Mono<Store> createStore(Store store) {
        log.info("Creating store: {}", store);
        return storeRepository.save(store);
    }

    public Mono<Store> getStoreById(Long id) {
        log.info("Getting store by id: {}", id);
        return storeRepository.findById(id);
    }

    public Mono<Store> updateStore(Long storeId, Store updatedStore) {
        return storeRepository.findById(storeId)
            .switchIfEmpty(Mono.error(new RuntimeException("Store not found")))
            .doOnNext(originalStore -> log.info("[Update Request] Updating store id: {}, Store [{}]", originalStore.getId(), originalStore))
            .doOnNext(originalStore -> storeMapper.partialUpdate(updatedStore, originalStore))
            .flatMap(storeRepository::save);
    }

    public Mono<Void> deleteStoreById(Long id) {
        log.info("Deleting store by id: {}", id);
        return storeRepository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Store not found with id: " + id)))
            .flatMap(storeRepository::delete)
            .then();
    }

}
