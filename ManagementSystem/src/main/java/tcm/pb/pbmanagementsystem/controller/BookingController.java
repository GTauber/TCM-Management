package tcm.pb.pbmanagementsystem.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import tcm.pb.pbmanagementsystem.model.dto.BookingDto;
import tcm.pb.pbmanagementsystem.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/booking")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookingController {

    private final BookingService bookingService;
    private final ConversionService conversionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createBooking(@RequestBody BookingDto bookingDto) {
        log.info("Received request to create new Booking: [{}]", bookingDto);
        return conversionService.convert(bookingService.createBooking(bookingDto), BookingDto.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookingDto getBookingById(@PathVariable long id) {
        log.info("Received request to get Booking by id: [{}]", id);
        return conversionService.convert(bookingService.getBookingById(id), BookingDto.class);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BookingDto updateBooking(@RequestBody BookingDto bookingDto) {
        log.info("Received request to update Booking: [{}]", bookingDto);
        return conversionService.convert(bookingService.updateBooking(bookingDto), BookingDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookingById(@PathVariable long id) {
        log.info("Received request to delete Booking by id: [{}]", id);
        bookingService.deleteBookingById(id);
    }

}
