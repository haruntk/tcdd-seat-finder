package haruntk.tcdd_seat_finder.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AvailableServicesDto {
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private int arrivalStationId;
	private int departureStationId;
    private String departureStation;
    private String arrivalStation;
	private String trainName;
	private String cabinName;
	private int availableSeatCount;
}
