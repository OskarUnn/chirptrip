package chirptrip.backend.controller;

import chirptrip.backend.dto.SeatDTO;
import chirptrip.backend.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
@Validated
public class SeatsController {

    private final SeatService seatService;

    @GetMapping("/{flightId}")
    public ResponseEntity<List<SeatDTO>> getSeats(@PathVariable("flightId") String flightId) {
        return ResponseEntity.ok().body(seatService.getFlightSeats(Long.parseLong(flightId)));
    }
}
