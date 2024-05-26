package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.dto.NotificationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KafkaServiceTest {

    private KafkaTemplate<String, NotificationDTO> kafkaTemplate;
    private KafkaService kafkaService;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        kafkaService = new KafkaService(kafkaTemplate);
    }

    @Test
    void sendNotification_ValidNotification_SendsToCorrectTopicAndKey() {
        // Arrange
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setUserId("user123");
        notificationDTO.setMessage("Test message");

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<NotificationDTO> valueCaptor = ArgumentCaptor.forClass(NotificationDTO.class);

        // Act
        kafkaService.sendNotification(notificationDTO);

        // Assert
        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), valueCaptor.capture());
        assertEquals("notification", topicCaptor.getValue());
        assertEquals(notificationDTO.getUserId(), keyCaptor.getValue());
        assertEquals(notificationDTO, valueCaptor.getValue());
    }
}
