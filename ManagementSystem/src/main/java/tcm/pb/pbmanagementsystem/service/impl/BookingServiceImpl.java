package tcm.pb.pbmanagementsystem.service.impl;

import tcm.pb.pbmanagementsystem.config.client.NotificationClient;
import tcm.pb.pbmanagementsystem.model.Notification;
import tcm.pb.pbmanagementsystem.model.dto.BookingDto;
import tcm.pb.pbmanagementsystem.model.entity.Booking;
import tcm.pb.pbmanagementsystem.repository.BookingRepository;
import tcm.pb.pbmanagementsystem.service.BarberService;
import tcm.pb.pbmanagementsystem.service.BookingService;
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
    private final BarberService barberService;
    private final ConversionService conversionService;
    private final NotificationClient notificationClient;

    @Override
    public Booking createBooking(BookingDto bookingDto) {
        log.info("Creating booking: {}", bookingDto);
//        var user = userService.getUserById(bookingDto.userId());
        var barber = barberService.getBarberById(bookingDto.barberId());
        var booking = conversionService.convert(bookingDto, Booking.class);
        if (Objects.isNull(barber) || Objects.isNull(bookingDto.userId()) || Objects.isNull(booking)) {
            throw new IllegalArgumentException("Null itens");
        }
        booking.setUserId(Objects.requireNonNull(bookingDto.userId()));
        booking.setBarber(Objects.requireNonNull(barber));
        notificationClient.sendNotification(Notification.builder()
                .message(bookingDto.toString())
                .type("Booking")
            .build());
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
        var barber = barberService.getBarberById(bookingDto.barberId());
        if (Objects.isNull(barber)) {
            throw new IllegalArgumentException("Invalid Barber or User");
        }
        BeanUtils.copyProperties(bookingDto, booking);
        booking.setUserId(bookingDto.userId());
        booking.setBarber(barber);
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBookingById(Long id) {
        log.info("Deleting booking by id: {}", id);
        bookingRepository.deleteById(id);
    }


}
