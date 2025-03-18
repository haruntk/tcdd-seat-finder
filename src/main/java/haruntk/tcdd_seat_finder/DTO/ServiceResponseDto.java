package haruntk.tcdd_seat_finder.DTO;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Data
public class ServiceResponseDto {
	private List<TrainLeg> trainLegs;
	private int legCount;
	private int roundTripDiscount;
	private long maxRegionalTrainsRoundTripDays;

	public record TrainLeg(List<TrainAvailability> trainAvailabilities, int resultCount) {
	}

	public record TrainAvailability(List<Train> trains, int totalTripTime, double minPrice, boolean connection,
			boolean dayChanged) {
	}

	public record Train(int id, String number, String name, String commercialName, String type, String line,
			boolean reversed, int scheduleId, int departureStationId, int arrivalStationId,
			List<AvailableFareInfo> availableFareInfo, List<TrainSegment> segments // JSON'da "segments" anahtarı
	) {
	}

	public record AvailableFareInfo(List<CabinClass> cabinClasses) {
	}

	public record TrainSegment(long departureTime, // Epoch time (milisaniye)
			long arrivalTime, // Epoch time (milisaniye)
			boolean stops, int duration, int stopDuration, double distance, Segment segment) {
		public LocalDateTime getDepartureTime() {
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(departureTime), ZoneId.systemDefault());
		}

		public LocalDateTime getArrivalTime() {
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(arrivalTime), ZoneId.systemDefault());
		}
	}

	public record CabinClass(CabinClassDetail cabinClass, int availabilityCount) {
	}

	public record CabinClassDetail(int id, String name, String code) {
	}

	public record Segment(int id, String name, Station departureStation, // JSON: "departureStation" içinde; örneğin, {
																			// "id":93, "name":"ESKİŞEHİR", ... }
			Station arrivalStation, // JSON: "arrivalStation"
			int lineId, int lineOrder) {
	}

	public record Station(int id, String name) {
	}
}
