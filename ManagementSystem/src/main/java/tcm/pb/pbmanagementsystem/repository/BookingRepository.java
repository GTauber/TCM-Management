package tcm.pb.pbmanagementsystem.repository;

import tcm.pb.pbmanagementsystem.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
