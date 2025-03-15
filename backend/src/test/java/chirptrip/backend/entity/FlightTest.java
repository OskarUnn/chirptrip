package chirptrip.backend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {


    @Test
    void distance() {
        double TOLERANCE = 15.0;

        assertTrue(Math.abs(Flight.distance(0, 0, 30, 30) - 4601) < TOLERANCE);
        assertTrue(Math.abs(Flight.distance(-30, -30, 30, 30) - 9203) < TOLERANCE);
        assertTrue(Math.abs(Flight.distance(66, 77, -55, -44) - 16699) < TOLERANCE);
    }
}