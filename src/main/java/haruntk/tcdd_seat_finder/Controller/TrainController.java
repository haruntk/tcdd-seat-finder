package haruntk.tcdd_seat_finder.Controller;

import haruntk.tcdd_seat_finder.DTO.AvailableServicesDto;
import haruntk.tcdd_seat_finder.DTO.ServiceDto;
import haruntk.tcdd_seat_finder.DTO.ServiceResponseDto;
import haruntk.tcdd_seat_finder.Service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrainController {
	private final TrainService trainService;

	@PostMapping("/getServices")
	public ResponseEntity<ServiceResponseDto> getTrainServices(@RequestBody ServiceDto serviceDto) {
		ServiceResponseDto response = trainService.getTrainServices(serviceDto);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/getAvailableServices")
	public ResponseEntity<List<AvailableServicesDto>> getAvailableServices(@RequestBody ServiceDto serviceDto) {

		List<AvailableServicesDto> response = trainService.getAvailableServices(serviceDto);
		return ResponseEntity.ok(response);
	}
}
