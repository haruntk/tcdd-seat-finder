package haruntk.tcdd_seat_finder.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import haruntk.tcdd_seat_finder.DTO.AvailableServicesDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;

	public void sendNotification(AvailableServicesDto dto) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("haruntkepenek@gmail.com");
		msg.setTo("hakukpnk@gmail.com");
		msg.setSubject("Available Train Seat");
		msg.setText("Train " + dto.getTrainName() + " has available seats (" + dto.getAvailableSeatCount() + ").\n"
				+ "Departure: " + dto.getDepartureStation() + " at " + dto.getDepartureTime() + "\n" + "Arrival: "
				+ dto.getArrivalStation() + " at " + dto.getArrivalTime());
		
		mailSender.send(msg);
	}

}
