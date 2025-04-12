package haruntk.tcdd_seat_finder.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import haruntk.tcdd_seat_finder.DTO.ServiceDto;

@Service
public class ServiceLoader {

    public ServiceDto loadServiceDtoFromJson() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("service-information.json");

            if (inputStream == null) {
                System.err.println("JSON dosyası bulunamadı.");
                return null;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.readValue(inputStream, ServiceDto.class);

        } catch (IOException e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            return null;
        }
    }
}

