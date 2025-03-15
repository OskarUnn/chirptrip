package chirptrip.backend.entity;

import chirptrip.backend.dto.FlightDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "flight")
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private Airport source;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Airport destination;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime departureTime;

    public FlightDTO toDTO() {
        return new FlightDTO(id,
                source.getCity(),
                destination.getCity(),
                departureTime,
                getDuration(),
                getPrice()
        );
    }

    public BigDecimal getPrice() {
        return BigDecimal.valueOf(getDistance() * 0.1).setScale(2, RoundingMode.HALF_UP);
    }

    public Duration getDuration() {
        double SPEED = 900.0; // Cruise speed of a Boeing 747 in km/h
        double hours = getDistance() / SPEED;
        long minutes = (long) (hours * 60);
        return Duration.ofMinutes(minutes);
    }

    public double getDistance() {
        return distance(source.getLatitude(), source.getLongitude(), destination.getLatitude(), destination.getLongitude());
    }

    // https://www.geodatasource.com/developers/java
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344; // For kilometers
            return (dist);
        }
    }
}
