package chirptrip.backend.controller;

import chirptrip.backend.dto.SeatDTO;
import chirptrip.backend.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
@Validated
public class SeatsController {

    private final SeatService seatService;

    @GetMapping(value = "/{flightId}")
    public ResponseEntity<List<SeatDTO>> getSeats(@PathVariable("flightId") String flightId) {
        return ResponseEntity.ok().body(seatService.getFlightSeats(Long.parseLong(flightId)));
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeatDTO>> bookSeats(@RequestBody List<SeatDTO> bookedSeats) {
        seatService.bookSeats(bookedSeats);
        return ResponseEntity.ok().body(bookedSeats);
    }
}
