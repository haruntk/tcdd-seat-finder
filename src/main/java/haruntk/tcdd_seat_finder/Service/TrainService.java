package haruntk.tcdd_seat_finder.Service;

import haruntk.tcdd_seat_finder.DTO.AvailableServicesDto;
import haruntk.tcdd_seat_finder.DTO.ServiceDto;
import haruntk.tcdd_seat_finder.DTO.ServiceRequestDto;
import haruntk.tcdd_seat_finder.DTO.ServiceResponseDto;
import haruntk.tcdd_seat_finder.DTO.ServiceResponseDto.CabinClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TrainService {
	private final String url = "https://web-api-prod-ytp.tcddtasimacilik.gov.tr/tms/train/train-availability?environment=dev&userId=1";
	private final RestClient restClient;
	private final EmailService mailService;

	@org.springframework.beans.factory.annotation.Value("${tcdd.api.token}")
	private String apiToken;

	public ServiceResponseDto getTrainServices(ServiceDto serviceDto) {
		ServiceRequestDto requestBody = new ServiceRequestDto();
		requestBody.setPassengerTypeCounts(List.of(new ServiceRequestDto.PassengerTypeCount(0, 1)));
		requestBody.setSearchRoutes(List.of(new ServiceRequestDto.SearchRoutes(serviceDto.getDepartureStationId(),
				serviceDto.getDepartureStationName(), serviceDto.getArrivalStationId(),
				serviceDto.getArrivalStationName(), serviceDto.getDepartureTime(), serviceDto.getArrivalTime())));

		ServiceResponseDto responseBody = restClient.post().uri(url).body(requestBody)
				.header("Authorization", apiToken)
				.header("Unit-Id", "3895")
				.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36 Edg/147.0.0.0")
				.header("Accept", "application/json, text/plain, */*")
				.header("Accept-Language", "tr")
				.header("Origin", "https://ebilet.tcddtasimacilik.gov.tr")
				.header("Referer", "https://ebilet.tcddtasimacilik.gov.tr/")
				.header("Connection", "keep-alive")
				.header("Sec-Fetch-Dest", "empty")
				.header("Sec-Fetch-Mode", "cors")
				.header("Sec-Fetch-Site", "same-site")
				.header("Sec-Ch-Ua", "\"Microsoft Edge\";v=\"147\", \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"147\"")
				.header("Sec-Ch-Ua-Mobile", "?0")
				.header("Sec-Ch-Ua-Platform", "\"Windows\"")
				.retrieve().body(ServiceResponseDto.class);

		return responseBody;
	}

	public List<AvailableServicesDto> getAvailableServices(ServiceDto serviceDto) {
		List<AvailableServicesDto> availableServicesDtos = new ArrayList<>();
		ServiceResponseDto responseBody = getTrainServices(serviceDto);

		LocalDateTime requestedDepartureTime = serviceDto.getDepartureTime();
		LocalDateTime requestedArrivalTime = serviceDto.getArrivalTime();

		for (ServiceResponseDto.TrainLeg leg : responseBody.getTrainLegs()) {
			for (ServiceResponseDto.TrainAvailability availability : leg.trainAvailabilities()) {
				for (ServiceResponseDto.Train train : availability.trains()) {

					String requiredCabin = serviceDto.getCabin();
					String cabin = "";
					int availableSeat = 0;
					if (train.availableFareInfo() != null) {
						for (ServiceResponseDto.AvailableFareInfo fareInfo : train.availableFareInfo()) {
							for (ServiceResponseDto.CabinClass cabinClass : fareInfo.cabinClasses()) {
								if (cabinClass.cabinClass().name().equalsIgnoreCase(requiredCabin)) {
									cabin = cabinClass.cabinClass().name();
									availableSeat = cabinClass.availabilityCount();
									break;
								}
							}
							if (!cabin.isEmpty() || availableSeat <= 0) {
								break;
							}
						}
					}
					if (cabin.isEmpty() || availableSeat <= 0) {
						continue;
					}

					List<ServiceResponseDto.TrainSegment> segments = train.segments();
					if (segments == null || segments.isEmpty()) {
						continue;
					}
					ServiceResponseDto.TrainSegment firstSegment = segments.get(0);
					ServiceResponseDto.TrainSegment lastSegment = segments.get(segments.size() - 1);

					ServiceResponseDto.Segment firstNested = firstSegment.segment();
					ServiceResponseDto.Segment lastNested = lastSegment.segment();
					if (firstNested == null || lastNested == null) {
						continue;
					}

					if (firstNested.departureStation().id() != serviceDto.getDepartureStationId()
							|| lastNested.arrivalStation().id() != serviceDto.getArrivalStationId()) {
						continue;
					}

					LocalDateTime combinedDepartureTime = firstSegment.getDepartureTime();
					LocalDateTime combinedArrivalTime = lastSegment.getArrivalTime();
					if (combinedDepartureTime.isBefore(requestedDepartureTime)
							|| combinedArrivalTime.isAfter(requestedArrivalTime)) {
						continue;
					}

					String trainName = train.name();
					String departureStationName = firstNested.departureStation().name();
					String arrivalStationName = lastNested.arrivalStation().name();

					AvailableServicesDto dto = new AvailableServicesDto();
					dto.setDepartureStationId(firstNested.departureStation().id());
					dto.setDepartureStation(departureStationName);
					dto.setArrivalStationId(lastNested.arrivalStation().id());
					dto.setArrivalStation(arrivalStationName);
					dto.setDepartureTime(combinedDepartureTime);
					dto.setArrivalTime(combinedArrivalTime);
					dto.setCabinName(cabin);
					dto.setTrainName(trainName);
					dto.setAvailableSeatCount(availableSeat);

					availableServicesDtos.add(dto);
					mailService.sendNotification(dto);
				}
			}
		}

		return availableServicesDtos;
	}

}
