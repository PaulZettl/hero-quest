package io.everyonecodes.project_module.controller;

import io.everyonecodes.project_module.entity.Dungeon;
import io.everyonecodes.project_module.exception.GlobalExceptionHandler;
import io.everyonecodes.project_module.service.DungeonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({DungeonController.class, GlobalExceptionHandler.class})
public class DungeonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DungeonService service;

    @Test
    void getAllDungeons_valid() throws Exception {
        Dungeon fakeDungeon1 = new Dungeon();
        fakeDungeon1.setId(1L);
        fakeDungeon1.setName("Test1");
        Dungeon fakeDungeon2 = new Dungeon();
        fakeDungeon2.setId(2L);
        fakeDungeon2.setName("Test2");
        Dungeon fakeDungeon3 = new Dungeon();
        fakeDungeon3.setId(3L);
        fakeDungeon3.setName("Test3");

        List<Dungeon> fakeDungeons = List.of(fakeDungeon1, fakeDungeon2, fakeDungeon3);
        when(service.getAll()).thenReturn(fakeDungeons);
        mockMvc.perform(get("/dungeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Test1")))
                .andExpect(jsonPath("$[1].name", is("Test2")))
                .andExpect(jsonPath("$[2].name", is("Test3")));
    }

    @Test
    void getAllDungeons_emptyList() throws Exception {
        List<Dungeon> fakeDungeons = List.of();
        when(service.getAll()).thenReturn(fakeDungeons);
        mockMvc.perform(get("/dungeon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
