package college.pb.managementunitapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToController;

import college.pb.managementunitapi.controller.StoreController;
import college.pb.managementunitapi.model.dto.StoreDto;
import college.pb.managementunitapi.model.entity.Store;
import college.pb.managementunitapi.model.mapper.StoreMapper;
import college.pb.managementunitapi.service.StoreService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

class StoreControllerTest {

    @Mock
    private StoreService storeService;

    @Mock
    private StoreMapper storeMapper;

    @InjectMocks
    private StoreController storeController;

    private WebTestClient webTestClient;

    private Store store;
    private StoreDto storeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = bindToController(storeController).build();

        // Initialize mock data
        store = Store.builder()
                .id(1L)
                .uuid("12345-uuid")
                .address("123 Main St")
                .zipCode("12345")
                .city("TestCity")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        storeDto = new StoreDto(
                store.getId(),
                store.getAddress(),
                store.getZipCode(),
                store.getCity()
        );
    }

    @Test
    void createStoreTest() {
        when(storeMapper.toEntity(any(StoreDto.class))).thenReturn(store);
        when(storeService.createStore(any(Store.class))).thenReturn(Mono.just(store));
        when(storeMapper.toDto(any(Store.class))).thenReturn(storeDto);

        webTestClient.post()
                .uri("/v1/store")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(storeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StoreDto.class)
                .isEqualTo(storeDto);
    }

    @Test
    void getStoreByIdTest() {
        when(storeService.getStoreById(1L)).thenReturn(Mono.just(store));
        when(storeMapper.toDto(store)).thenReturn(storeDto);

        webTestClient.get()
                .uri("/v1/store/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StoreDto.class)
                .isEqualTo(storeDto);
    }

    @Test
    void updateStoreTest() {
        when(storeMapper.toEntity(any(StoreDto.class))).thenReturn(store);
        when(storeService.updateStore(eq(1L), any(Store.class))).thenReturn(Mono.just(store));
        when(storeMapper.toDto(store)).thenReturn(storeDto);

        webTestClient.put()
                .uri("/v1/store/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(storeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StoreDto.class)
                .isEqualTo(storeDto);
    }

    @Test
    void deleteStoreByIdTest() {
        when(storeService.deleteStoreById(1L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/v1/store/{id}", 1L)
                .exchange()
                .expectStatus().isNoContent();
    }
}
