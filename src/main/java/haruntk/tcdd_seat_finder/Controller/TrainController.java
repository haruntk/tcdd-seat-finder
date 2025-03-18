package haruntk.tcdd_seat_finder.Controller;

import haruntk.tcdd_seat_finder.DTO.AvailableServicesDto;
import haruntk.tcdd_seat_finder.DTO.ServiceDto;
import haruntk.tcdd_seat_finder.DTO.ServiceResponseDto;
import haruntk.tcdd_seat_finder.Service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrainController {
    private final TrainService trainService;

    @Operation(summary = "Get Train Services", description = "Fetch train services based on given parameters.")
    @PostMapping("/getServices")
    public ResponseEntity<ServiceResponseDto> getTrainServices(@RequestBody ServiceDto serviceDto) {
        ServiceResponseDto response = trainService.getTrainServices(serviceDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Available Train Services", description = "Fetch available train services based on given parameters with pagination.")
    @PostMapping("/getAvailableServices")
    public ResponseEntity<Page<AvailableServicesDto>> getAvailableServices(
            @RequestBody ServiceDto serviceDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<AvailableServicesDto> response = trainService.getAvailableServices(serviceDto, pageable);
        return ResponseEntity.ok(response);
    }
}
