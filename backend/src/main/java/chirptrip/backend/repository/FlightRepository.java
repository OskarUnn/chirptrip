package chirptrip.backend.repository;

import chirptrip.backend.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f " +
            "JOIN f.source s " +
            "JOIN f.destination d " +
            "WHERE (:source IS NULL OR s.iata = :source OR s.city = :source OR s.country = :source) " +
            "AND (:destination IS NULL OR d.iata = :destination OR d.city = :destination OR d.country = :destination)" +
            "AND (:maxPrice IS NULL OR f.price <= :maxPrice) ")
    List<Flight> findFlights(
            @Param("source") String source,
            @Param("destination") String destination,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("departureStart") ZonedDateTime departureStart,
            @Param("departureEnd") ZonedDateTime departureEnd
    );
}
