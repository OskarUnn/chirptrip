package chirptrip.backend.service;

import chirptrip.backend.dto.SeatDTO;
import chirptrip.backend.entity.Flight;
import chirptrip.backend.entity.Seat;
import chirptrip.backend.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public List<SeatDTO> getFlightSeats(Long flightId) {
        return seatRepository.findAllByFlightId(flightId).stream().map(Seat::toDTO).toList();
    }

    public void createSeatsForFlight(Flight flight) {
        List<Seat> seats = new ArrayList<>();
        Random random = new Random(flight.getId());

        int ROWS = 20;
        int COLUMNS = 6;

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                Seat seat = new Seat();
                seat.setFlight(flight);
                seat.setSeatRow(row);
                seat.setSeatColumn(column);
                seat.setOccupied(random.nextBoolean());

                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);
    }
}
