package tcm.pb.pbmanagementsystem.service.impl;

import tcm.pb.pbmanagementsystem.model.dto.BarberDto;
import tcm.pb.pbmanagementsystem.model.entity.Barber;
import tcm.pb.pbmanagementsystem.repository.BarberRepository;
import tcm.pb.pbmanagementsystem.service.BarberService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BarberServiceImpl implements BarberService {

    private final BarberRepository barberRepository;
    private final ConversionService conversionService;

    @Override
    public Barber createBarber(BarberDto barber) {
        log.info("Creating barber: {}", barber);
        return barberRepository.save(Objects.requireNonNull(conversionService.convert(barber, Barber.class)));
    }

    @Override
    public Barber getBarberById(Long id) {
        return barberRepository.findById(id).orElseThrow();
    }

    @Override
    public Barber updateBarber(BarberDto barber) {
        log.info("Updating barber: {}", barber);
        var existingBarber = barberRepository.findById(barber.id())
            .orElseThrow();
        BeanUtils.copyProperties(barber, existingBarber);
        return barberRepository.save(existingBarber);
    }

    @Override
    public void deleteBarberById(Long id) {
        log.info("Deleting barber by id: {}", id);
        barberRepository.deleteById(id);
    }
}
