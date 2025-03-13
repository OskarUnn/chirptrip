package chirptrip.backend;

import chirptrip.backend.entity.Flight;
import chirptrip.backend.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.ZonedDateTime;

@SpringBootApplication
public class ChirpTripApplication {

	@Autowired
	private FlightRepository flightRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChirpTripApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		System.out.println("Number of flights: " + flightRepository.findAll().size());

		Flight newFlight = new Flight();
		newFlight.setSourceAirport("TLN");
		newFlight.setDestinationAirport("LON");
		newFlight.setDepartureTime(ZonedDateTime.now());

		flightRepository.save(newFlight);
	}

}
