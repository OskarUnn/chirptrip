package chirptrip.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private Long id;
    private String sourceIata;
    private String sourceCity;
    private String sourceCountry;
    private String destinationIata;
    private String destinationCity;
    private String destinationCountry;
    private ZonedDateTime departureTime;
    private Duration duration;
    private BigDecimal price;
}
