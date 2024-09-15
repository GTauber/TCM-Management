package tcm.pb.pbmanagementsystem.converter;

import tcm.pb.pbmanagementsystem.model.dto.BarberDto;
import tcm.pb.pbmanagementsystem.model.entity.Barber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BarberToBarberDtoConverter implements Converter<Barber, BarberDto> {

    @Override
    public BarberDto convert(Barber barber) {
        return new BarberDto(barber.getId(), barber.getName(), barber.getEmail(), barber.getUserType());
    }
}
