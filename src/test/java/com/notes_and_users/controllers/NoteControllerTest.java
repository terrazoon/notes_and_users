package com.notes_and_users.controllers;


import com.notes_and_users.models.Note;
import com.notes_and_users.models.User;
import com.notes_and_users.repositories.NoteRepository;
import com.notes_and_users.repositories.UserRepository;
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
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NoteControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private NoteRepository mockRepository;
    @MockBean
    private UserRepository mockUserRepository;

    @Before
    public void init() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Note note = new Note(1L, "My Title", "My Text");
        User user = new User(1L, "USER", "myemail@email.com", "password");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        List<Note> noteList = new ArrayList<>();
        noteList.add(note);
        when(mockRepository.findById(1L)).thenReturn(Optional.of(note));
        when(mockRepository.findAll()).thenReturn(noteList);
        when(mockUserRepository.findAll()).thenReturn(userList);
    }

    @WithMockUser("USER")
    @Test
    public void find_login_ok() throws Exception {

        mockMvc.perform(get("/notes/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.title", is("My Title")))
                .andExpect(jsonPath("$.note", is("My Text")));
    }

    @Test
    public void find_nologin_unauthorized() throws Exception {
        mockMvc.perform(get("/notes/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser("ADMIN")
    @Test
    public void find_wrong_user() throws Exception {
        mockMvc.perform(get("/notes/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser("USER")
    @Test
    public void find_nonexistent() throws Exception {
        mockMvc.perform(get("/notes/7111"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser("USER")
    @Test
    public void test_find_all() throws Exception {
        mockMvc.perform(
                get("/notes")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*.title").isNotEmpty());
    }

    @WithMockUser("USER")
    @Test
    public void test_delete_ok() throws Exception {
        mockMvc.perform(delete("/notes/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("UNKNOWN_USER")
    @Test
    public void test_delete_unauthorized() throws Exception {
        mockMvc.perform(delete("/notes/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser("USER")
    @Test
    public void testCreateNote() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/notes")
                .content("{\"id\": 2, \"userId\": 1, \"title\": \"test title\", \"note\": \"test note\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @WithMockUser("UNKNOWN_USER")
    @Test
    public void testCreateNoteUnauthorized() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/notes")
                .content("{\"id\": 2, \"userId\": 1, \"title\": \"test title\", \"note\": \"test note\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }


    @WithMockUser("USER")
    @Test
    public void testUpdateNote() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/notes/1")
                .content("{\"id\": 1, \"userId\": 1, \"title\": \"test title222\", \"note\": \"test note\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @WithMockUser("USER")
    @Test
    public void testUpdateNoteButItCreates() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/notes/2")
                .content("{\"id\": 2, \"userId\": 1, \"title\": \"test title\", \"note\": \"test note\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @WithMockUser("UNKNOWN USER")
    @Test
    public void testUpdateNoteUnauthorized() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/notes/2")
                .content("{\"id\": 2, \"userId\": 1, \"title\": \"test title\", \"note\": \"test note\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        mockMvc.perform( MockMvcRequestBuilders
                .put("/notes/2")
                .content("{\"id\": 2, \"userId\": 1, \"title\": \"change title\", \"note\": \"test note\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }




}
