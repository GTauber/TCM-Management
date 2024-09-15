package college.pb.managementunitapi.repository;

import college.pb.managementunitapi.model.entity.Store;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends R2dbcRepository<Store, Long> {

}
