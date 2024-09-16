package tcm.pb.pbmanagementsystem.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.MockitoAnnotations.openMocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import tcm.pb.pbmanagementsystem.controller.BookingController;
import tcm.pb.pbmanagementsystem.enums.ServiceType;
import tcm.pb.pbmanagementsystem.model.dto.BookingDto;
import tcm.pb.pbmanagementsystem.model.entity.Booking;
import tcm.pb.pbmanagementsystem.model.entity.User;
import tcm.pb.pbmanagementsystem.model.entity.Barber;
import tcm.pb.pbmanagementsystem.service.BookingService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Booking booking;
    private BookingDto bookingDto;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
        objectMapper = new ObjectMapper();

        booking = Booking.builder()
                .id(1L)
                .user(new User())
                .barber(new Barber())
                .startDatetime(LocalDateTime.now())
                .duration(30)
                .endDatetime(LocalDateTime.now().plusHours(1))
                .services(List.of(ServiceType.HAIR_CUT))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        bookingDto = new BookingDto(
                booking.getId(),
                1L,
                1L,
                booking.getStartDatetime(),
                booking.getDuration(),
                booking.getEndDatetime(),
                booking.getServices()
        );
    }

    @Test
    void createBookingTest() throws Exception {
        when(bookingService.createBooking(any(BookingDto.class))).thenReturn(booking);

        mockMvc.perform(post("/v1/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookingDto.id()))
                .andExpect(jsonPath("$.userId").value(bookingDto.userId()))
                .andExpect(jsonPath("$.barberId").value(bookingDto.barberId()))
                .andExpect(jsonPath("$.startDatetime").value(bookingDto.startDatetime().toString()))
                .andExpect(jsonPath("$.duration").value(bookingDto.duration()))
                .andExpect(jsonPath("$.endDatetime").value(bookingDto.endDatetime().toString()))
                .andExpect(jsonPath("$.services[0]").value(bookingDto.services().get(0).name()));
    }

    @Test
    void getBookingByIdTest() throws Exception {
        when(bookingService.getBookingById(1L)).thenReturn(booking);

        mockMvc.perform(get("/v1/booking/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookingDto.id()))
                .andExpect(jsonPath("$.userId").value(bookingDto.userId()))
                .andExpect(jsonPath("$.barberId").value(bookingDto.barberId()))
                .andExpect(jsonPath("$.startDatetime").value(bookingDto.startDatetime().toString()))
                .andExpect(jsonPath("$.duration").value(bookingDto.duration()))
                .andExpect(jsonPath("$.endDatetime").value(bookingDto.endDatetime().toString()))
                .andExpect(jsonPath("$.services[0]").value(bookingDto.services().get(0).name()));
    }

    @Test
    void updateBookingTest() throws Exception {
        when(bookingService.updateBooking(any(BookingDto.class))).thenReturn(booking);

        mockMvc.perform(put("/v1/booking/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookingDto.id()))
                .andExpect(jsonPath("$.userId").value(bookingDto.userId()))
                .andExpect(jsonPath("$.barberId").value(bookingDto.barberId()))
                .andExpect(jsonPath("$.startDatetime").value(bookingDto.startDatetime().toString()))
                .andExpect(jsonPath("$.duration").value(bookingDto.duration()))
                .andExpect(jsonPath("$.endDatetime").value(bookingDto.endDatetime().toString()))
                .andExpect(jsonPath("$.services[0]").value(bookingDto.services().get(0).name()));
    }


}
