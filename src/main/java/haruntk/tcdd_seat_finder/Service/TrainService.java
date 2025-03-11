package haruntk.tcdd_seat_finder.Service;

import haruntk.tcdd_seat_finder.DTO.AvailableServicesDto;
import haruntk.tcdd_seat_finder.DTO.ServiceDto;
import haruntk.tcdd_seat_finder.DTO.ServiceRequestDto;
import haruntk.tcdd_seat_finder.DTO.ServiceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TrainService {
    private final String url = "https://web-api-prod-ytp.tcddtasimacilik.gov.tr/tms/train/train-availability?environment=dev&userId=1";
    private final RestClient restClient;
    public ServiceResponseDto getTrainServices(ServiceDto serviceDto)
    {
        ServiceRequestDto requestBody = new ServiceRequestDto();
        requestBody.setPassengerTypeCounts(List.of(new ServiceRequestDto.PassengerTypeCount(0,1)));
        requestBody.setSearchRoutes(List.of(
                new ServiceRequestDto.SearchRoutes(serviceDto.getDepartureStationId(), serviceDto.getDepartureStationName(), serviceDto.getArrivalStationId(), serviceDto.getArrivalStationName(), serviceDto.getDepartureTime())
        ));
        ServiceResponseDto responseBody = restClient.post()
                .uri(url)
                .body(requestBody)
                .header("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlVFFicDhDMmpiakp1cnUzQVk2a0ZnV196U29MQXZIMmJ5bTJ2OUg5THhRIn0.eyJleHAiOjE3MjEzODQ0NzAsImlhdCI6MTcyMTM4NDQxMCwianRpIjoiYWFlNjVkNzgtNmRkZS00ZGY4LWEwZWYtYjRkNzZiYjZlODNjIiwiaXNzIjoiaHR0cDovL3l0cC1wcm9kLW1hc3RlcjEudGNkZHRhc2ltYWNpbGlrLmdvdi50cjo4MDgwL3JlYWxtcy9tYXN0ZXIiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMDAzNDI3MmMtNTc2Yi00OTBlLWJhOTgtNTFkMzc1NWNhYjA3IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoidG1zIiwic2Vzc2lvbl9zdGF0ZSI6IjAwYzM4NTJiLTg1YjEtNDMxNS04OGIwLWQ0MWMxMTcyYzA0MSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1tYXN0ZXIiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgZW1haWwgcHJvZmlsZSIsInNpZCI6IjAwYzM4NTJiLTg1YjEtNDMxNS04OGIwLWQ0MWMxMTcyYzA0MSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoid2ViIiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.AIW_4Qws2wfwxyVg8dgHRT9jB3qNavob2C4mEQIQGl3urzW2jALPx-e51ZwHUb-TXB-X2RPHakonxKnWG6tDIP5aKhiidzXDcr6pDDoYU5DnQhMg1kywyOaMXsjLFjuYN5PAyGUMh6YSOVsg1PzNh-5GrJF44pS47JnB9zk03Pr08napjsZPoRB-5N4GQ49cnx7ePC82Y7YIc-gTew2baqKQPz9_v381Gbm2V38PZDH9KldlcWut7kqQYJFMJ7dkM_entPJn9lFk7R5h5j_06OlQEpWRMQTn9SQ1AYxxmZxBu5XYMKDkn4rzIIVCkdTPJNCt5PvjENjClKFeUA1DOg")
                .header("Unit-Id", "3895")
                .retrieve()
                .body(ServiceResponseDto.class);

        return responseBody;
    }

    public List<AvailableServicesDto> getAvailableServices(ServiceDto serviceDto)
    {
        List<AvailableServicesDto> availableServicesDtos = new ArrayList<AvailableServicesDto>();
        ServiceResponseDto responseBody = getTrainServices(serviceDto);

        for (var leg : responseBody.getTrainLegs()) {
            for (var availability : leg.trainAvailabilities()) {
                for (var train : availability.trains()) {
                    for (var fareInfo : train.availableFareInfo()) {
                        for (var cabinClass : fareInfo.cabinClasses()) {
                            AvailableServicesDto availableServicesDto = new AvailableServicesDto();
                             if (serviceDto.getCabin() == null || serviceDto.getCabin().isEmpty() ||
                                     serviceDto.getCabin().equals(cabinClass.cabinClass().name()) && cabinClass.availabilityCount() > 0)
                             {
                                 availableServicesDto.setAvailableSeatCount(cabinClass.availabilityCount());
                                 availableServicesDto.setDepartureStation(train.name());
                                 availableServicesDto.setCabinName(cabinClass.cabinClass().name());
                                 availableServicesDtos.add(availableServicesDto);
                             }
                        }
                    }
                }
            }
        }
        return availableServicesDtos;
    }
}
