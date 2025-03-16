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
            "WHERE (:source IS NULL OR s.city = :source) " +
            "AND (:destination IS NULL OR d.city = :destination) ")
    List<Flight> findFlights(@Param("source") String source,
                             @Param("destination") String destination,
                             @Param("maxPrice") BigDecimal maxPrice,
                             @Param("date") ZonedDateTime date);
}
