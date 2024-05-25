package id.ac.ui.cs.advprog.youkosoproduct.Builder;
import id.ac.ui.cs.advprog.youkosoproduct.model.Builder.DefaultResponseBuilder;

import id.ac.ui.cs.advprog.youkosoproduct.dto.DefaultResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultResponseBuilderTest {

    @Test
    void testBuild_DefaultResponse() {
        DefaultResponseBuilder<String> builder = new DefaultResponseBuilder<>();
        int expectedStatusCode = 200;
        String expectedMessage = "Success";
        boolean expectedSuccess = true;
        String expectedData = "Some data";

        DefaultResponse<String> response = builder
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
        DefaultResponseBuilder<Integer> builder = new DefaultResponseBuilder<>();

        DefaultResponse<Integer> response = builder.build();

        assertEquals(0, response.getStatusCode());
        assertNull(response.getMessage());
        assertFalse(response.isSuccess());
        assertNull(response.getData());
    }
}
