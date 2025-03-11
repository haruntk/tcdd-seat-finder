package haruntk.tcdd_seat_finder.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceDto {
    private int departureStationId;
    private int arrivalStationId;
    private LocalDateTime departureTime;
    private String departureStationName;
    private String arrivalStationName;
    private String cabin;
}
