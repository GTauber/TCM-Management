package tcm.pb.pbmanagementsystem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ServiceType {

    HAIR_CUT(30), SHADE(60), BEARD(20), COLOR(50), BROWN(20);

    private final Integer duration;

}
