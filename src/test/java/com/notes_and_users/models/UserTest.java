package com.notes_and_users.models;


import com.notes_and_users.error.InvalidEmailException;
import com.notes_and_users.models.Note;
import com.notes_and_users.models.User;
import com.notes_and_users.repositories.NoteRepository;
import com.notes_and_users.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


}
