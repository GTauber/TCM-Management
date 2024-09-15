package tcm.pb.pbmanagementsystem.converter;

import tcm.pb.pbmanagementsystem.model.dto.BarberDto;
import tcm.pb.pbmanagementsystem.model.entity.Barber;
import jakarta.annotation.Nonnull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BarberDtoToBarberConverter implements Converter<BarberDto, Barber> {

    @Override
    public Barber convert(@Nonnull BarberDto barberDto) {
        var barber = new Barber();
        BeanUtils.copyProperties(barberDto, barber);
        return barber;
    }
}
