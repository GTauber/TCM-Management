package tcm.pb.pbmanagementsystem.service;

import tcm.pb.pbmanagementsystem.model.dto.BookingDto;
import tcm.pb.pbmanagementsystem.model.entity.Booking;

public interface BookingService {

    Booking createBooking(BookingDto bookingDto);
    Booking getBookingById(Long id);
    Booking updateBooking(BookingDto bookingDto);
    void deleteBookingById(Long id);

}
