package tcm.pb.pbmanagementsystem.service;

import tcm.pb.pbmanagementsystem.model.dto.BarberDto;
import tcm.pb.pbmanagementsystem.model.entity.Barber;

public interface BarberService {

    Barber createBarber(BarberDto barber);
    Barber getBarberById(Long id);
    Barber updateBarber(BarberDto barber);
    void deleteBarberById(Long id);

}
