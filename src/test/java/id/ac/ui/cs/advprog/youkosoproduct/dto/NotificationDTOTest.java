package id.ac.ui.cs.advprog.youkosoproduct.dto;

import id.ac.ui.cs.advprog.youkosoproduct.model.enumaration.NotificationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationDTOTest {

    @Test
    void setMessage_getMessage_shouldReturnCorrectValue() {
        // Arrange
        NotificationDTO notificationDTO = new NotificationDTO();
        String message = "Test message";

        // Act
        notificationDTO.setMessage(message);

        // Assert
        assertEquals(message, notificationDTO.getMessage());
    }

    @Test
    void setType_getType_shouldReturnCorrectValue() {
        // Arrange
        NotificationDTO notificationDTO = new NotificationDTO();
        NotificationType type = NotificationType.ORDER;

        // Act
        notificationDTO.setType(type);

        // Assert
        assertEquals(type, notificationDTO.getType());
    }

    @Test
    void setPaymentId_getPaymentId_shouldReturnCorrectValue() {
        // Arrange
        NotificationDTO notificationDTO = new NotificationDTO();
        String paymentId = "123";

        // Act
        notificationDTO.setPaymentId(paymentId);

        // Assert
        assertEquals(paymentId, notificationDTO.getPaymentId());
    }

    @Test
    void setShipmentId_getShipmentId_shouldReturnCorrectValue() {
        // Arrange
        NotificationDTO notificationDTO = new NotificationDTO();
        String shipmentId = "456";

        // Act
        notificationDTO.setShipmentId(shipmentId);

        // Assert
        assertEquals(shipmentId, notificationDTO.getShipmentId());
    }
}
