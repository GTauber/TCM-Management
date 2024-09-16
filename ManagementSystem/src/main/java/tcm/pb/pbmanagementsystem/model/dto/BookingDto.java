package tcm.pb.pbmanagementsystem.model.dto;

import tcm.pb.pbmanagementsystem.enums.ServiceType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record BookingDto(Long id, Long userId,
                         Long barberId,
                         @NotNull @FutureOrPresent LocalDateTime startDatetime,
                         @NotNull Integer duration,
                         @NotNull @Future LocalDateTime endDatetime,
                         @NotNull List<ServiceType> services) implements
    Serializable {

}