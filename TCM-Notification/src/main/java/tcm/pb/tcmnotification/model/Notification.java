package tcm.pb.tcmnotification.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {

    private String message;
    private String type;

}
