package id.ac.ui.cs.advprog.youkosoproduct.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InternalServerErrorExceptionTest {

    private InternalServerErrorException exception;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testException_MessageOnly() {
        String expectedMessage = "An internal server error occurred";

        exception = new InternalServerErrorException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getCause());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void testException_MessageAndCause() {
        String expectedMessage = "An internal server error occurred";
        Throwable expectedCause = new Throwable("Cause of the error");

        exception = new InternalServerErrorException(expectedMessage, expectedCause);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedCause, exception.getCause());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @AfterEach
    void tearDown() {
        exception = null;
    }
}
