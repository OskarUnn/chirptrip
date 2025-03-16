package chirptrip.backend.entity;

import chirptrip.backend.dto.SeatDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "seat")
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    private boolean occupied = false;

    private int seatRow;
    private int seatColumn;

    public SeatDTO toDTO() {
        return new SeatDTO(id, seatRow, seatColumn, occupied);
    }
}
