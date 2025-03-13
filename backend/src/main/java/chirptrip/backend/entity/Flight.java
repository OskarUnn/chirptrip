package chirptrip.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "flight")
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue
    private Long id;
    private String sourceAirport;
    private String destinationAirport;
    private ZonedDateTime departureTime;
}
