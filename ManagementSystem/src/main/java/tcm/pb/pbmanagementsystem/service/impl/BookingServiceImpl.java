package tcm.pb.pbmanagementsystem.service.impl;

import tcm.pb.pbmanagementsystem.model.dto.BookingDto;
import tcm.pb.pbmanagementsystem.model.entity.Booking;
import tcm.pb.pbmanagementsystem.repository.BookingRepository;
import tcm.pb.pbmanagementsystem.service.BarberService;
import tcm.pb.pbmanagementsystem.service.BookingService;
import tcm.pb.pbmanagementsystem.service.UserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final BarberService barberService;
    private final ConversionService conversionService;

    @Override
    public Booking createBooking(BookingDto bookingDto) {
        log.info("Creating booking: {}", bookingDto);
        var user = userService.getUserById(bookingDto.userId());
        var barber = barberService.getBarberById(bookingDto.barberId());
        var booking = conversionService.convert(bookingDto, Booking.class);
        if (Objects.isNull(barber) || Objects.isNull(user) || Objects.isNull(booking)) {
            throw new IllegalArgumentException("Null itens");
        }
        booking.setUser(Objects.requireNonNull(user));
        booking.setBarber(Objects.requireNonNull(barber));
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long id) {
        log.info("Getting booking by id: {}", id);
        return bookingRepository.findById(id).orElseThrow();
    }

    @Override
    public Booking updateBooking(BookingDto bookingDto) {
        log.info("Updating booking: {}", bookingDto);
        var booking = bookingRepository.findById(bookingDto.id())
            .orElseThrow();
        var user = userService.getUserById(bookingDto.userId());
        var barber = barberService.getBarberById(bookingDto.barberId());
        if (Objects.isNull(barber) || Objects.isNull(user)) {
            throw new IllegalArgumentException("Invalid Barber or User");
        }
        BeanUtils.copyProperties(bookingDto, booking);
        booking.setUser(user);
        booking.setBarber(barber);
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBookingById(Long id) {
        log.info("Deleting booking by id: {}", id);
        bookingRepository.deleteById(id);
    }


}
