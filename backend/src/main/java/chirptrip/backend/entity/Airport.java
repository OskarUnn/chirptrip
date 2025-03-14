package chirptrip.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@Data
@Entity
@Table(name = "airport")
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    private Long id;
    private String name;
    private String city;
    private String country;
    private String iata;
    private String icao;
    private Double latitude;
    private Double longitude;
    private int altitude;
    private ZoneId timezone;
    private String type;
}
