package train;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClient;

import haruntk.tcdd_seat_finder.DTO.ServiceDto;
import haruntk.tcdd_seat_finder.DTO.ServiceRequestDto;
import haruntk.tcdd_seat_finder.DTO.ServiceResponseDto;
import haruntk.tcdd_seat_finder.Service.TrainService;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class TrainServiceTest {

	@Mock
    private RestClient restClient;
    
    @Mock
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;
    
    @Mock
    private RestClient.RequestBodySpec requestBodySpec;
    
    @Mock
    private RestClient.RequestHeadersSpec<?> requestHeadersSpec;
    
    @Mock
    private RestClient.ResponseSpec responseSpec;
    
    @InjectMocks
    private TrainService trainService;

    @Test
    public void testGetTrainServices() {
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setDepartureStationId(48);
        serviceDto.setDepartureStationName("İSTANBUL(PENDİK)");
        serviceDto.setArrivalStationId(93);
        serviceDto.setArrivalStationName("ESKİŞEHİR");
        serviceDto.setDepartureTime(LocalDateTime.of(2025, 3, 20, 10, 0, 0));
        serviceDto.setArrivalTime(LocalDateTime.of(2025, 3, 20, 14, 0, 0));
        
        ServiceResponseDto expectedResponse = new ServiceResponseDto();

        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(ServiceRequestDto.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(), any(String[].class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(ServiceResponseDto.class)).thenReturn(expectedResponse);

        ServiceResponseDto actualResponse = trainService.getTrainServices(serviceDto);
        
        assertNotNull(actualResponse, "Response null olmamalı");
        assertEquals(expectedResponse, actualResponse, "Beklenen ve dönen yanıt eşleşmiyor");

        verify(restClient).post();
        verify(requestBodyUriSpec).uri(anyString());
        verify(requestBodySpec).retrieve();
        verify(responseSpec).body(ServiceResponseDto.class);
    }
}
