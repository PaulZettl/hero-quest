package io.everyonecodes.project_module.controller;

import io.everyonecodes.project_module.entity.Hero;
import io.everyonecodes.project_module.exception.GlobalExceptionHandler;
import io.everyonecodes.project_module.service.HeroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest({HeroController.class, GlobalExceptionHandler.class})
public class HeroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HeroService service;

    @Test
    void getAllHeroes_valid() throws Exception {
        Hero fakeHero1 = new Hero();
        fakeHero1.setId(1L);
        fakeHero1.setName("Test1");
        Hero fakeHero2 = new Hero();
        fakeHero2.setId(2L);
        fakeHero2.setName("Test2");
        Hero fakeHero3 = new Hero();
        fakeHero3.setId(3L);
        fakeHero3.setName("Test3");

        List<Hero> fakeHeroes = List.of(fakeHero1, fakeHero2, fakeHero3);
        when(service.getAll()).thenReturn(fakeHeroes);
        mockMvc.perform(get("/hero"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Test1")))
                .andExpect(jsonPath("$[1].name", is("Test2")))
                .andExpect(jsonPath("$[2].name", is("Test3")));
    }

    @Test
    void getAllHeroes_emptyList() throws Exception {
        List<Hero> fakeHeroes = List.of();
        when(service.getAll()).thenReturn(fakeHeroes);
        mockMvc.perform(get("/hero"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getHeroById_valid() throws Exception {
        Hero fakeHero = new Hero();
        fakeHero.setId(1L);
        fakeHero.setName("Testing");
        when(service.getById(1L)).thenReturn(Optional.of(fakeHero));
        mockMvc.perform(get("/hero/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Testing"));
    }

    @Test
    void getHeroById_idNotFound() throws Exception {
        when(service.getById(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/hero/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createHero_valid() throws Exception {
        Hero inputHero = new Hero();
        inputHero.setName("New Hero");
        Hero savedHero = new Hero();
        savedHero.setId(5L);
        savedHero.setName("New Hero");
        when(service.create(any(Hero.class))).thenReturn(savedHero);
        mockMvc.perform(post("/hero")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputHero)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value("New Hero"));
    }

    @Test
    void createHero_duplicateName() throws Exception {
        Hero duplicateHero = new Hero();
        duplicateHero.setName("ExistingHero");

        // Duplicate hero name error
        Throwable fakePostgresError = new SQLException("duplicate key value violates unique constraint \"hero_name_key\"");
        when(service.create(any(Hero.class)))
                .thenThrow(new org.springframework.dao.DataIntegrityViolationException("Spring DB Error", fakePostgresError));

        mockMvc.perform(post("/hero")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateHero)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("A hero with this name already exists."));
    }

    @Test
    void deleteHero() throws Exception {
        Long heroId = 1L;
        mockMvc.perform(delete("/hero/1"))
                .andExpect(status().isNoContent());
        verify(service).deleteById(heroId); // checks if the repository was asked to delete
    }

    @Test
    void updateHero_valid() throws Exception {
        Hero updatedHero = new Hero();
        updatedHero.setId(1L);
        updatedHero.setName("Test");
        when(service.update(any(Hero.class))).thenReturn(Optional.of(updatedHero));
        mockMvc.perform(put("/hero/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedHero)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    void updateHero_idNotFound() throws Exception {
        Hero updatedHero = new Hero();
        updatedHero.setId(99L);
        updatedHero.setName("Test");
        when(service.update(any(Hero.class))).thenReturn(Optional.empty());
        mockMvc.perform(put("/hero/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedHero)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateHero_duplicateName() throws Exception {
        Hero updatedHero = new Hero();
        updatedHero.setId(1L);
        updatedHero.setName("ExistingName");

        // Duplicate hero name error
        Throwable fakePostgresError = new SQLException("duplicate key value violates unique constraint \"hero_name_key\"");
        when(service.update(any(Hero.class)))
                .thenThrow(new org.springframework.dao.DataIntegrityViolationException("Spring DB Error", fakePostgresError));

        mockMvc.perform(put("/hero/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedHero)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("A hero with this name already exists."));
    }

    @Test
    void getCompletedDungeonIds_valid() throws Exception {
        Hero testHero = new Hero();
        testHero.setId(1L);
        when(service.getById(1L)).thenReturn(Optional.of(testHero));
        when(service.getAllCompletedDungeonIdsByHeroId(1L)).thenReturn(List.of(1L, 2L, 3L));
        mockMvc.perform(get("/hero/1/completedDungeons"))
                .andExpect(status().isOk())
                .andExpect(content().json("[1, 2, 3]"));
    }

    @Test
    void getCompletedDungeonIds_heroNotFound() throws Exception {
        when(service.getById(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/hero/99/completedDungeons"))
                .andExpect(status().isNotFound());
    }
}
