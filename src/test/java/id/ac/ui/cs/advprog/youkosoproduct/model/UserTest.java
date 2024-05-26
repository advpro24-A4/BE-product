package id.ac.ui.cs.advprog.youkosoproduct.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        Profile profile = new Profile();
        profile.setName("yaya");
        profile.setUsername("ngokek12");
        profile.setAddress("jl.semangka");
        profile.setBirth_date("1990-01-01");
        profile.setPhone_number("1234567890");

        user = new User();
        user.setId("1");
        user.setEmail("yaya@gmail.com");
        user.setRole("customer");
        user.setProfile(profile);
    }

    @Test
    void testUserId() {
        assertEquals("1", user.getId());
    }

    @Test
    void testUserEmail() {
        assertEquals("yaya@gmail.com", user.getEmail());
    }

    @Test
    void testUserRole() {
        assertEquals("customer", user.getRole());
    }

    @Test
    void testProfileName() {
        assertEquals("yaya", user.getProfile().getName());
    }

    @Test
    void testProfileUsername() {
        assertEquals("ngokek12", user.getProfile().getUsername());
    }

    @Test
    void testProfileAddress() {
        assertEquals("jl.semangka", user.getProfile().getAddress());
    }

    @Test
    void testProfileBirthDate() {
        assertEquals("1990-01-01", user.getProfile().getBirth_date());
    }

    @Test
    void testProfilePhoneNumber() {
        assertEquals("1234567890", user.getProfile().getPhone_number());
    }

    @Test
    void testUserInitialization() {
        User newUser = new User();
        assertNotNull(newUser);
    }

    @Test
    void testSetUserId() {
        user.setId("2");
        assertEquals("2", user.getId());
    }

    @Test
    void testSetUserEmail() {
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testSetUserRole() {
        user.setRole("admin");
        assertEquals("admin", user.getRole());
    }

    @Test
    void testSetProfileName() {
        Profile profile = user.getProfile();
        profile.setName("John");
        assertEquals("John", user.getProfile().getName());
    }

    @Test
    void testSetProfileUsername() {
        Profile profile = user.getProfile();
        profile.setUsername("john_doe");
        assertEquals("john_doe", user.getProfile().getUsername());
    }

    @Test
    void testSetProfileAddress() {
        Profile profile = user.getProfile();
        profile.setAddress("123 Main St");
        assertEquals("123 Main St", user.getProfile().getAddress());
    }

    @Test
    void testSetProfileBirthDate() {
        Profile profile = user.getProfile();
        profile.setBirth_date("1990-02-02");
        assertEquals("1990-02-02", user.getProfile().getBirth_date());
    }

    @Test
    void testSetProfilePhoneNumber() {
        Profile profile = user.getProfile();
        profile.setPhone_number("9876543210");
        assertEquals("9876543210", user.getProfile().getPhone_number());
    }

    @Test
    void testAllArgsConstructor() {
        User existingUser = new User("1", "sukatri@gmail.com", "customer", new Profile("sukatri", "sukapohon", "jl.pohon", "1990-01-02", "1234567891"));
        assertNotNull(existingUser);
    }

    @AfterEach
    void tearDown() {
        user = null;
    }
}
