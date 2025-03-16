package chirptrip.backend.controller;

import chirptrip.backend.dto.FlightDTO;
import chirptrip.backend.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
@Validated
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/")
    public ResponseEntity<List<FlightDTO>> getFlights(
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String departureDateStart,
            @RequestParam(required = false) String departureDateEnd) {

        return ResponseEntity.ok().body(flightService.getFilteredFlights(
                source,
                destination,
                maxPrice,
                departureDateStart,
                departureDateEnd));
    }
}
