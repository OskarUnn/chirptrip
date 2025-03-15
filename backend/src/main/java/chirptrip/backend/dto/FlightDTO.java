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
    private String sourceCityName;
    private String destinationCityName;
    private ZonedDateTime departureTime;
    private Duration duration;
    private BigDecimal price;
}
