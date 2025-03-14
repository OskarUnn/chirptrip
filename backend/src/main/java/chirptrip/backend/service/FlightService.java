package chirptrip.backend.service;

import chirptrip.backend.dto.FlightDTO;
import chirptrip.backend.entity.Flight;
import chirptrip.backend.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightService {

    private final FlightRepository flightRepository;

    public List<FlightDTO> getAllFlightDTOs() {
        return flightRepository.findAll().stream().map(Flight::toDTO).toList();
    }
}
