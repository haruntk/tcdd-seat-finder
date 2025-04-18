package haruntk.tcdd_seat_finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TcddSeatFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcddSeatFinderApplication.class, args);
	}

}
