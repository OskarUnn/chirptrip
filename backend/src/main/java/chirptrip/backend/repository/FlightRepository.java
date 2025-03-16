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
            "WHERE (:sourceCity IS NULL OR s.city = :sourceCity) " +
            "AND (:destinationCity IS NULL OR d.city = :destinationCity) ")
    List<Flight> findFlights(@Param("sourceCity") String sourceCity,
                             @Param("destinationCity") String destinationCity,
                             @Param("maxPrice") BigDecimal maxPrice,
                             @Param("date") ZonedDateTime date);
}
