package chirptrip.backend.service;

import chirptrip.backend.dto.FlightDTO;
import chirptrip.backend.entity.Flight;
import chirptrip.backend.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final FlightRepository flightRepository;

    public List<FlightDTO> getAllFlightDTOs() {
        return flightRepository.findAll().stream().map(Flight::toDTO).toList();
    }

    public List<FlightDTO> getFilteredFlights(
            String source,
            String destination,
            Double maxPrice,
            String departureDateStart,
            String departureDateEnd) {

        ZonedDateTime departureStart = (departureDateStart != null) ? ZonedDateTime.parse(departureDateStart) : null;
        ZonedDateTime departureEnd = (departureDateEnd != null) ? ZonedDateTime.parse(departureDateEnd) : null;
        BigDecimal maxPriceDecimal = (maxPrice != null) ? BigDecimal.valueOf(maxPrice) : null;

        List<Flight> flights = flightRepository.findFlights(
            source,
            destination,
            maxPriceDecimal,
            departureStart,
            departureEnd
        );
        log.info("Found {} flights.", flights.size());
        return flights.stream().map(Flight::toDTO).toList();
    }
}
