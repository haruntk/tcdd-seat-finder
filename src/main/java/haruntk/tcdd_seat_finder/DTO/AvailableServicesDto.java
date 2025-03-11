package haruntk.tcdd_seat_finder.DTO;

import lombok.Data;

@Data
public class AvailableServicesDto {
    private int availableSeatCount;
    private String departureStation;
    private String cabinName;
}
