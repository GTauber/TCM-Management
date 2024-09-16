package tcm.pb.pbmanagementsystem.implTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import tcm.pb.pbmanagementsystem.model.dto.BarberDto;
import tcm.pb.pbmanagementsystem.model.entity.Barber;
import tcm.pb.pbmanagementsystem.repository.BarberRepository;
import tcm.pb.pbmanagementsystem.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import tcm.pb.pbmanagementsystem.service.impl.BarberServiceImpl;

import java.util.Optional;

class BarberServiceImplTest {

    @Mock
    private BarberRepository barberRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private BarberServiceImpl barberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBarber() {
        BarberDto barberDto = new BarberDto(1L, "Jane Doe", "jane.doe@example.com",UserType.USER, "password");
        Barber barber = new Barber(1L, 1L, "Jane Doe", "jane.doe@example.com", "password", UserType.USER, null, null);

        when(conversionService.convert(barberDto, Barber.class)).thenReturn(barber);
        when(barberRepository.save(barber)).thenReturn(barber);

        Barber createdBarber = barberService.createBarber(barberDto);

        assertNotNull(createdBarber);
        assertEquals(barberDto.id(), createdBarber.getId());
        assertEquals(barberDto.name(), createdBarber.getName());
        assertEquals(barberDto.email(), createdBarber.getEmail());
        assertEquals(barberDto.password(), createdBarber.getPassword());
    }

    @Test
    void testGetBarberById() {
        Long barberId = 1L;
        Barber barber = new Barber(barberId, 1L, "Jane Doe", "jane.doe@example.com", "password", UserType.USER, null, null);

        when(barberRepository.findById(barberId)).thenReturn(Optional.of(barber));

        Barber foundBarber = barberService.getBarberById(barberId);

        assertNotNull(foundBarber);
        assertEquals(barberId, foundBarber.getId());
    }

    @Test
    void testUpdateBarber() {
        BarberDto barberDto = new BarberDto(1L, "Jane Doe", "jane.doe@example.com", UserType.USER, "password" );
        Barber existingBarber = new Barber(1L, 1L, "Jane Doe", "jane.doe@example.com", "oldPassword", UserType.USER, null, null);

        when(barberRepository.findById(barberDto.id())).thenReturn(Optional.of(existingBarber));
        when(barberRepository.save(any(Barber.class))).thenReturn(existingBarber);

        Barber updatedBarber = barberService.updateBarber(barberDto);

        assertNotNull(updatedBarber);
        assertEquals(barberDto.id(), updatedBarber.getId());
        assertEquals(barberDto.name(), updatedBarber.getName());
        assertEquals(barberDto.email(), updatedBarber.getEmail());
        assertEquals(barberDto.password(), updatedBarber.getPassword());
    }

    @Test
    void testDeleteBarberById() {
        Long barberId = 1L;

        doNothing().when(barberRepository).deleteById(barberId);

        barberService.deleteBarberById(barberId);

        verify(barberRepository, times(1)).deleteById(barberId);
    }
}
