package haruntk.tcdd_seat_finder.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import haruntk.tcdd_seat_finder.DTO.AvailableServicesDto;
import haruntk.tcdd_seat_finder.DTO.ServiceDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScheduledServiceRunner {

    private final ServiceLoader serviceLoader;
    private final TrainService trainService;

    @Scheduled(fixedRate = 300000)
    public void scheduledServiceChecker() {
        System.out.println(">>> Scheduled görev çalıştı: " + LocalDateTime.now());

        ServiceDto serviceDto = serviceLoader.loadServiceDtoFromJson();

        if (serviceDto == null) {
            System.out.println("ServiceDto yüklenemedi, işlem sonlandırılıyor.");
            return;
        }
        List<AvailableServicesDto> availableServices = trainService.getAvailableServices(serviceDto);
        System.out.println("Bulunan servis sayısı: " + availableServices.size());
    }
}