package tcm.pb.pbmanagementsystem.implTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import tcm.pb.pbmanagementsystem.enums.UserType;
import tcm.pb.pbmanagementsystem.model.dto.BookingDto;
import tcm.pb.pbmanagementsystem.model.entity.Booking;
import tcm.pb.pbmanagementsystem.model.entity.User;
import tcm.pb.pbmanagementsystem.model.entity.Barber;
import tcm.pb.pbmanagementsystem.repository.BookingRepository;
import tcm.pb.pbmanagementsystem.enums.ServiceType;
import tcm.pb.pbmanagementsystem.service.BarberService;
import tcm.pb.pbmanagementsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import tcm.pb.pbmanagementsystem.service.impl.BookingServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserService userService;

    @Mock
    private BarberService barberService;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking() {
        LocalDateTime startDatetime = LocalDateTime.now().plusHours(1);
        LocalDateTime endDatetime = startDatetime.plusHours(1);
        BookingDto bookingDto = new BookingDto(1L, 1L, 1L, startDatetime, 60, endDatetime, Collections.singletonList(ServiceType.HAIR_CUT));

        User user = new User(1L, 1L, "John Doe", "john.doe@example.com", "password", UserType.USER, null, null);
        Barber barber = new Barber(1L, 1L, "Jane Doe", "jane.doe@example.com", "password", UserType.USER, null, null);
        Booking booking = Booking.builder()
                .id(1L)
                .user(user)
                .barber(barber)
                .startDatetime(startDatetime)
                .duration(60)
                .endDatetime(endDatetime)
                .services(Collections.singletonList(ServiceType.HAIR_CUT))
                .build();

        when(userService.getUserById(bookingDto.userId())).thenReturn(user);
        when(barberService.getBarberById(bookingDto.barberId())).thenReturn(barber);
        when(conversionService.convert(bookingDto, Booking.class)).thenReturn(booking);
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking createdBooking = bookingService.createBooking(bookingDto);

        assertNotNull(createdBooking);
        assertEquals(bookingDto.userId(), createdBooking.getUser().getId());
        assertEquals(bookingDto.barberId(), createdBooking.getBarber().getId());
        assertEquals(bookingDto.startDatetime(), createdBooking.getStartDatetime());
        assertEquals(bookingDto.duration(), createdBooking.getDuration());
        assertEquals(bookingDto.endDatetime(), createdBooking.getEndDatetime());
        assertEquals(bookingDto.services(), createdBooking.getServices());
    }

    @Test
    void testGetBookingById() {
        Long bookingId = 1L;
        LocalDateTime startDatetime = LocalDateTime.now().plusHours(1);
        LocalDateTime endDatetime = startDatetime.plusHours(1);
        User user = new User(1L, 1L, "John Doe", "john.doe@example.com", "password", UserType.USER, null, null);
        Barber barber = new Barber(1L, 1L, "Jane Doe", "jane.doe@example.com", "password", UserType.USER, null, null);
        Booking booking = Booking.builder()
                .id(bookingId)
                .user(user)
                .barber(barber)
                .startDatetime(startDatetime)
                .duration(60)
                .endDatetime(endDatetime)
                .services(Collections.singletonList(ServiceType.HAIR_CUT))
                .build();

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        Booking foundBooking = bookingService.getBookingById(bookingId);

        assertNotNull(foundBooking);
        assertEquals(bookingId, foundBooking.getId());
        assertEquals(user.getId(), foundBooking.getUser().getId());
        assertEquals(barber.getId(), foundBooking.getBarber().getId());
        assertEquals(startDatetime, foundBooking.getStartDatetime());
        assertEquals(60, foundBooking.getDuration());
        assertEquals(endDatetime, foundBooking.getEndDatetime());
        assertEquals(Collections.singletonList(ServiceType.HAIR_CUT), foundBooking.getServices());
    }

    @Test
    void testUpdateBooking() {
        Long bookingId = 1L;
        LocalDateTime startDatetime = LocalDateTime.now().plusHours(1);
        LocalDateTime endDatetime = startDatetime.plusHours(1);
        BookingDto bookingDto = new BookingDto(bookingId, 1L, 1L, startDatetime, 60, endDatetime, Collections.singletonList(ServiceType.HAIR_CUT));
        User user = new User(1L, 1L, "John Doe", "john.doe@example.com", "password", UserType.USER, null, null);
        Barber barber = new Barber(1L, 1L, "Jane Doe", "jane.doe@example.com", "password", UserType.USER, null, null);
        Booking existingBooking = Booking.builder()
                .id(bookingId)
                .user(user)
                .barber(barber)
                .startDatetime(startDatetime)
                .duration(60)
                .endDatetime(endDatetime)
                .services(Collections.singletonList(ServiceType.HAIR_CUT))
                .build();

        when(bookingRepository.findById(bookingDto.id())).thenReturn(Optional.of(existingBooking));
        when(userService.getUserById(bookingDto.userId())).thenReturn(user);
        when(barberService.getBarberById(bookingDto.barberId())).thenReturn(barber);
        when(bookingRepository.save(any(Booking.class))).thenReturn(existingBooking);

        Booking updatedBooking = bookingService.updateBooking(bookingDto);

        assertNotNull(updatedBooking);
        assertEquals(bookingDto.id(), updatedBooking.getId());
        assertEquals(user.getId(), updatedBooking.getUser().getId());
        assertEquals(barber.getId(), updatedBooking.getBarber().getId());
        assertEquals(startDatetime, updatedBooking.getStartDatetime());
        assertEquals(60, updatedBooking.getDuration());
        assertEquals(endDatetime, updatedBooking.getEndDatetime());
        assertEquals(Collections.singletonList(ServiceType.HAIR_CUT), updatedBooking.getServices());
    }

    @Test
    void testDeleteBookingById() {
        Long bookingId = 1L;

        doNothing().when(bookingRepository).deleteById(bookingId);

        bookingService.deleteBookingById(bookingId);

        verify(bookingRepository, times(1)).deleteById(bookingId);
    }
}
