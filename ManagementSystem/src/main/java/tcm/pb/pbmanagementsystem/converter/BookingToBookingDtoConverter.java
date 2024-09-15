package tcm.pb.pbmanagementsystem.converter;

import tcm.pb.pbmanagementsystem.model.dto.BookingDto;
import tcm.pb.pbmanagementsystem.model.entity.Booking;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookingToBookingDtoConverter implements Converter<Booking, BookingDto> {

    @Override
    public BookingDto convert(Booking booking) {
        return new BookingDto(booking.getId(), booking.getUser().getId(), booking.getBarber().getId(), booking.getStartDatetime(), booking.getDuration(), booking.getEndDatetime(), booking.getServices());
    }
}
