package college.pb.managementunitapi;

import college.pb.managementunitapi.model.entity.Store;
import college.pb.managementunitapi.repository.StoreRepository;
import college.pb.managementunitapi.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    private Store store;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        store = Store.builder()
                .id(1L)
                .address("123 Main St")
                .zipCode("12345")
                .city("Sample City")
                .build();
    }

    @Test
    void testCreateStore() {
        when(storeRepository.save(store)).thenReturn(Mono.just(store));

        Mono<Store> result = storeService.createStore(store);
        StepVerifier.create(result)
                .expectNext(store)
                .verifyComplete();
        verify(storeRepository, times(1)).save(store);
    }
    @Test
    void testSample() {
        assertTrue(true); // Simples teste para garantir que está funcionando
    }
}
