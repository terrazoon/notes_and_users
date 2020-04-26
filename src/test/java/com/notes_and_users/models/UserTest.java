package com.notes_and_users.models;

import com.notes_and_users.error.InvalidEmailException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserTest {

    @Test
    public void testCreateUser() {
        User user = new User("A new user", "validemail@email.com", "password");
        Assert.assertNotEquals("password", user.getPassword());
        Assert.assertEquals("A new user", user.getName());
        Assert.assertEquals("validemail@email.com", user.getEmail());
    }

    @Test
    public void testCreateUserBadEmail() {
        try {
            User user = new User("name", "bademail", "password");
            Assert.assertFalse(false);
        } catch (InvalidEmailException ime) {
            Assert.assertTrue(true);
        } catch (Throwable t) {
            Assert.assertFalse(false);
        }
    }

    @Test
    public void testCreateUserBadEmailOtherConstructor() {
        try {
            User user = new User(1L, "name", "bademail", "password");
            Assert.assertFalse(false);
        } catch (InvalidEmailException ime) {
            Assert.assertTrue(true);
        } catch (Throwable t) {
            Assert.assertFalse(false);
        }
    }

    @Test
    public void testPasswordHash() {
        User user1 = new User(1L, "n", "b@b.com", "password");
        User user2 = new User(2L, "o", "a@b.com", "password");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Assert.assertTrue(encoder.matches("password", user1.getPassword()));
        Assert.assertTrue(encoder.matches("password", user2.getPassword()));
    }
}
