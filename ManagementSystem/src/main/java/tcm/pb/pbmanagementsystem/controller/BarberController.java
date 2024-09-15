package tcm.pb.pbmanagementsystem.controller;

import tcm.pb.pbmanagementsystem.model.dto.BarberDto;
import tcm.pb.pbmanagementsystem.service.BarberService;
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
@RequestMapping("/v1/barber")
@Slf4j
@RequiredArgsConstructor
public class BarberController {

    private final BarberService barberService;
    private final ConversionService conversionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BarberDto createBarber(@RequestBody BarberDto barber) {
        log.info("Creating barber: {}", barber);
        return conversionService.convert(barberService.createBarber(barber), BarberDto.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BarberDto getBarberById(@PathVariable Long id) {
        return conversionService.convert(barberService.getBarberById(id), BarberDto.class);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BarberDto updateBarber(@RequestBody BarberDto barber) {
        return conversionService.convert(barberService.updateBarber(barber), BarberDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBarberById(@PathVariable Long id) {
        barberService.deleteBarberById(id);
    }

}
