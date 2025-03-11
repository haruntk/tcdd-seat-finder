package haruntk.tcdd_seat_finder.Controller;

import haruntk.tcdd_seat_finder.DTO.AvailableServicesDto;
import haruntk.tcdd_seat_finder.DTO.ServiceDto;
import haruntk.tcdd_seat_finder.DTO.ServiceResponseDto;
import haruntk.tcdd_seat_finder.Service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainController {
    private final TrainService trainService;

    @GetMapping("/getServices")
    public ResponseEntity<ServiceResponseDto> getTrainServices(@RequestBody ServiceDto serviceDto)
    {
        ServiceResponseDto response = trainService.getTrainServices(serviceDto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getAvailableServices")
    public ResponseEntity<List<AvailableServicesDto>> getAvailableServices(@RequestBody ServiceDto serviceDto)
    {
        List<AvailableServicesDto> response = trainService.getAvailableServices(serviceDto);
        return ResponseEntity.ok(response);
    }

}
