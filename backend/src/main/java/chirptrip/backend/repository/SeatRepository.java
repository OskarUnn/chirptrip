package chirptrip.backend.repository;

import chirptrip.backend.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("SELECT s FROM Seat s WHERE s.flight.id = :flightId")
    List<Seat> findAllByFlightId(@Param("flightId") Long flightId);
}
