package id.ac.ui.cs.advprog.youkosoproduct.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {
    private Shipment shipment;

    @BeforeEach
    void setUp() {
        shipment = new Shipment();
        shipment.setId(1L);
        shipment.setOrderId(1L);
        shipment.setShipmentMethod("JTE");
        shipment.setTrackingNumber("JTE-123456789012");
        shipment.setShipmentStatus("Shipped");
        shipment.setCreatedAt(LocalDateTime.now());
        shipment.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testId() {
        assertEquals(1L, shipment.getId());
    }

    @Test
    void testOrderId() {
        assertEquals(1L, shipment.getOrderId());
    }

    @Test
    void testShipmentMethod() {
        assertEquals("JTE", shipment.getShipmentMethod());
    }

    @Test
    void testTrackingNumber() {
        assertEquals("JTE-123456789012", shipment.getTrackingNumber());
    }

    @Test
    void testShipmentStatus() {
        assertEquals("Shipped", shipment.getShipmentStatus());
    }

    @Test
    void testCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        shipment.setCreatedAt(now);
        assertEquals(now, shipment.getCreatedAt());
    }

    @Test
    void testUpdatedAt() {
        LocalDateTime now = LocalDateTime.now();
        shipment.setUpdatedAt(now);
        assertEquals(now, shipment.getUpdatedAt());
    }

    @Test
    void testShipmentInitialization() {
        Shipment newShipment = new Shipment();
        assertNotNull(newShipment);
    }

    @Test
    void testValidTrackingNumberJTE() {
        shipment.setShipmentMethod("JTE");
        shipment.setTrackingNumber("JTE-123456789012");

        assertDoesNotThrow(() -> shipment.validateTrackingNumber());
    }

    @Test
    void testValidTrackingNumberGoBek() {
        shipment.setShipmentMethod("Go-bek");
        shipment.setTrackingNumber("GBK-ABC123456789");

        assertDoesNotThrow(() -> shipment.validateTrackingNumber());
    }

    @Test
    void testValidTrackingNumberSiWuzz() {
        shipment.setShipmentMethod("SiWuzz");
        shipment.setTrackingNumber("SWZ-ABCDEFGHIJKL");

        assertDoesNotThrow(() -> shipment.validateTrackingNumber());
    }

    @Test
    void testInvalidTrackingNumberJTE() {
        shipment.setShipmentMethod("JTE");
        shipment.setTrackingNumber("Invalid");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> shipment.validateTrackingNumber());
        assertEquals("Invalid JTE tracking number", exception.getMessage());
    }

    @Test
    void testInvalidTrackingNumberGoBek() {
        shipment.setShipmentMethod("Go-bek");
        shipment.setTrackingNumber("InvalidGoBek123");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> shipment.validateTrackingNumber());
        assertEquals("Invalid Go-bek tracking number", exception.getMessage());
    }

    @Test
    void testInvalidTrackingNumberSiWuzz() {
        shipment.setShipmentMethod("SiWuzz");
        shipment.setTrackingNumber("InvalidSiWuzz123");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> shipment.validateTrackingNumber());
        assertEquals("Invalid SiWuzz tracking number", exception.getMessage());
    }

    @Test
    void testInvalidShipmentMethod() {
        shipment.setShipmentMethod("InvalidMethod");
        shipment.setTrackingNumber("JTE-123456789012");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> shipment.validateTrackingNumber());
        assertEquals("Invalid shipment method", exception.getMessage());
    }


    @AfterEach
    void tearDown() {
        shipment = null;
    }
}
