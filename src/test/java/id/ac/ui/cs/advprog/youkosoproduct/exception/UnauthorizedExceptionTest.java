package id.ac.ui.cs.advprog.youkosoproduct.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnauthorizedExceptionTest {

    private UnauthorizedException exception;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testException_MessageOnly() {
        String expectedMessage = "Unauthorized access";

        exception = new UnauthorizedException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getCause());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @AfterEach
    void tearDown() {
        exception = null;
    }
}
