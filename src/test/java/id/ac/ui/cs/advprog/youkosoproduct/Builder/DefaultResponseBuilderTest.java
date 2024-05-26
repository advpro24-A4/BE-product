package id.ac.ui.cs.advprog.youkosoproduct.Builder;
import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.DefaultResponseBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.dto.DefaultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultResponseBuilderTest {

    private DefaultResponseBuilder<String> builder;
    private DefaultResponse<String> response;

    @BeforeEach
    void setUp() {
        builder = new DefaultResponseBuilder<>();
    }

    @Test
    void testBuild_DefaultResponse() {
        int expectedStatusCode = 200;
        String expectedMessage = "Success";
        boolean expectedSuccess = true;
        String expectedData = "Some data";

        response = builder
                .statusCode(expectedStatusCode)
                .message(expectedMessage)
                .success(expectedSuccess)
                .data(expectedData)
                .build();

        assertEquals(expectedStatusCode, response.getStatusCode());
        assertEquals(expectedMessage, response.getMessage());
        assertEquals(expectedSuccess, response.isSuccess());
        assertEquals(expectedData, response.getData());
    }

    @Test
    void testBuild_DefaultResponse_DefaultValues() {
        response = builder.build();

        assertEquals(0, response.getStatusCode());
        assertNull(response.getMessage());
        assertFalse(response.isSuccess());
        assertNull(response.getData());
    }

    @AfterEach
    void tearDown() {
        builder = null;
        response = null;
    }
}
