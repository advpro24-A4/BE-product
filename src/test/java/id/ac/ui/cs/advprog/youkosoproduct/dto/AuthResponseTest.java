package id.ac.ui.cs.advprog.youkosoproduct.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthResponseTest {

    @Test
    void setMessage_getMessage_shouldReturnCorrectValue() {
        AuthResponse authResponse = new AuthResponse();
        String message = "Test message";
        authResponse.setMessage(message);
        assertEquals(message, authResponse.getMessage());
    }
}
