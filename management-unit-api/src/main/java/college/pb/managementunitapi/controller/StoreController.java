package college.pb.managementunitapi.controller;

import college.pb.managementunitapi.model.dto.StoreDto;
import college.pb.managementunitapi.model.mapper.StoreMapper;
import college.pb.managementunitapi.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/store")
@Slf4j
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreMapper storeMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<StoreDto>> createStore(@RequestBody StoreDto storeDto) {
        log.info("[Request received - Create Store]: {}", storeDto);
        return storeService.createStore(storeMapper.toEntity(storeDto))
                .map(storeMapper::toDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<StoreDto>> getStoreById(@PathVariable Long id) {
        log.info("[Request received - Get Store by Id]: {}", id);
        return storeService.getStoreById(id)
                .map(storeMapper::toDto)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<StoreDto>> updateStore(@PathVariable Long storeId, @RequestBody StoreDto storeDto) {
        log.info("[Request received - Update Store]: {}", storeDto);
        return storeService.updateStore(storeId, storeMapper.toEntity(storeDto))
                .map(storeMapper::toDto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{storeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteStoreById(@PathVariable Long storeId) {
        log.info("[Request received - Delete Store by Id]: {}", storeId);
        return storeService.deleteStoreById(storeId);
    }

}
