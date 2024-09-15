package tcm.pb.pbmanagementsystem.model.converter;

import tcm.pb.pbmanagementsystem.enums.ServiceType;
import jakarta.persistence.Converter;
import jakarta.persistence.AttributeConverter;
import java.util.List;
import java.util.stream.Stream;

@Converter
public class ServicesConverter implements AttributeConverter<List<ServiceType>, String> {

    @Override
    public String convertToDatabaseColumn(List<ServiceType> services) {
        return services.stream().map(ServiceType::name).reduce((a, b) -> a + "," + b).orElse("");
    }

    @Override
    public List<ServiceType> convertToEntityAttribute(String services) {
        return Stream.of(services.split(","))
            .map(ServiceType::valueOf).toList();
    }
}
