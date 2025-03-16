package chirptrip.backend.service;

import chirptrip.backend.entity.Airport;
import chirptrip.backend.entity.Flight;
import chirptrip.backend.repository.AirportRepository;
import chirptrip.backend.repository.FlightRepository;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    @Value("${openflights.data.airports}")
    private String airportsFilePath;

    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;
    private final SeatService seatService;

    @Override
    public void run(String... args) throws Exception {

        if (airportRepository.count() == 0) {
            importAirports();
        }

        if (flightRepository.count() == 0) {
            generateFlights();
        }
    }

    private void importAirports() throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader(airportsFilePath))) {
            List<Airport> airports = new ArrayList<>();
            String[] data;
            while ((data = reader.readNext()) != null) {

                // Discard incomplete values
                if (Arrays.asList(data).contains("N")) {
                    continue;
                }

                Airport airport = getAirportFromCSV(data);
                airports.add(airport);
            }
            airportRepository.saveAll(airports);
            log.info("Loaded in data of {} airports from Openflights dataset.", airports.size());
        }
    }

    private static Airport getAirportFromCSV(String[] data) {
        Airport airport = new Airport();
        airport.setId(Long.parseLong(data[0]));
        airport.setName(data[1]);
        airport.setCity(data[2]);
        airport.setCountry(data[3]);
        airport.setIata(data[4]);
        airport.setIcao(data[5]);
        airport.setLatitude(Double.parseDouble(data[6]));
        airport.setLongitude(Double.parseDouble(data[7]));
        airport.setAltitude(Integer.parseInt(data[8]));
        airport.setTimezone(ZoneId.of(data[11]));
        airport.setType(data[12]);
        return airport;
    }

    private void generateFlights() {
        List<Airport> airports = airportRepository.findAll();

        // Create flights for only ~Europe
        airports = airports.stream().filter(airport ->
                airport.getLatitude() > 35
                && airport.getLatitude() < 72
                && airport.getLongitude() > -28
                && airport.getLongitude() < 40
        ).toList();

        if (airports.size() < 2) {
            throw new IllegalStateException("Not enough airports to generate flights");
        }

        Random random = new Random(12345678L);
        List<Flight> flights = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Airport source = airports.get(random.nextInt(airports.size()));
            Airport destination;

            do {
                destination = airports.get(random.nextInt(airports.size()));
            } while (source.getId().equals(destination.getId()));

            Flight newFlight = new Flight();
            newFlight.setSource(source);
            newFlight.setDestination(destination);

            ZonedDateTime departureTime = ZonedDateTime.now(source.getTimezone())
                    .withMinute(0).withSecond(0).withNano(0)
                    .plusDays(random.nextInt(7))
                    .plusHours(random.nextInt(1, 24));
            newFlight.setDepartureTime(departureTime);

            // Generate a price for the flight based on the flight's distance and some randomness
            double randomPercent = 0.2;
            double randomPrice = random.nextDouble(1 - randomPercent, 1 + randomPercent) * (newFlight.getDistance() * 0.05);
            newFlight.setPrice(randomPrice);

            flights.add(newFlight);
        }

        flightRepository.saveAll(flights);
        log.info("Initialized database with {} flights.", flights.size());

        flights.forEach(seatService::createSeatsForFlight);
    }
}
