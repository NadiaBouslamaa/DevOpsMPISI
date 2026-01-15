package com.example.projetmpisi.controller;

import com.example.projetmpisi.entity.User;
import com.example.projetmpisi.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    public void setup() {
        user1 = new User();
        user1.setId(1);
        user1.setName("Alice");
        user1.setEmail("alice@example.com");

        user2 = new User();
        user2.setId(2);
        user2.setName("Bob");
        user2.setEmail("bob@example.com");
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(user1.getId()))
                .andExpect(jsonPath("$[0].name").value(user1.getName()))
                .andExpect(jsonPath("$[1].id").value(user2.getId()))
                .andExpect(jsonPath("$[1].name").value(user2.getName()));
    }

    @Test
    public void testGetUserById_Found() throws Exception {
        when(userService.getUserById(1)).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.name").value(user1.getName()));
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {
        when(userService.getUserById(3)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(user1);

        String json = "{\"name\":\"Alice\",\"email\":\"alice@example.com\"}";

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    public void testUpdateUser_Found() throws Exception {
        when(userService.updateUser(Mockito.eq(1), any(User.class))).thenReturn(user1);

        String json = "{\"name\":\"Alice\",\"email\":\"alice@example.com\"}";

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    public void testUpdateUser_NotFound() throws Exception {
        when(userService.updateUser(Mockito.eq(3), any(User.class))).thenReturn(null);

        String json = "{\"name\":\"Charlie\",\"email\":\"charlie@example.com\"}";

        mockMvc.perform(put("/api/users/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}



