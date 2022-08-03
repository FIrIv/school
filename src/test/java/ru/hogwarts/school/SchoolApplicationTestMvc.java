package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest (controllers = FacultyController.class)
public class SchoolApplicationTestMvc {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    //@InjectMocks
    //private FacultyController facultyController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Opera";
        String color = "Gold";

        Faculty expected = new Faculty();
        expected.setId(id);
        expected.setName(name);
        expected.setColor(color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(expected)).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void readFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Opera";
        String color = "Gold";

        Faculty expected = new Faculty();
        expected.setId(id);
        expected.setName(name);
        expected.setColor(color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.findById(eq(id))).thenReturn(Optional.of(expected));
        when(facultyRepository.findFacultyById(eq(id))).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void readFacultyByColorTest() throws Exception {
        Long id1 = 1L;
        String name1 = "Opera";
        String color = "Gold";
        Faculty expected1 = new Faculty(id1, name1, color);

        Long id2 = 2L;
        String name2 = "Dance";
        Faculty expected2 = new Faculty(id2, name2, color);

        when(facultyRepository.findFacultiesByColorIgnoreCase(eq(color))).thenReturn(List.of(expected1, expected2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter")
                        .queryParam("color", color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expected1, expected2))));
    }

    @Test
    public void readFacultyByNameTest() throws Exception {
        Long id1 = 1L;
        String name = "Opera";
        String color1 = "Gold";
        Faculty expected1 = new Faculty(id1, name, color1);

        Long id2 = 2L;
        String color2 = "Silver";
        Faculty expected2 = new Faculty(id2, name, color2);

        when(facultyRepository.findFacultiesByNameIgnoreCase(eq(name))).thenReturn(List.of(expected1, expected2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter")
                        .queryParam("name", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expected1, expected2))));
    }

    @Test
    public void UpdateTest() throws Exception {
        Long id = 1L;
        String name = "Opera";
        String color = "Gold";
        Faculty expected = new Faculty(id, name, color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(expected)).thenReturn(expected);
        when(facultyRepository.findById(eq(id))).thenReturn(Optional.of(expected));
        when(facultyRepository.findFacultyById(eq(id))).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void deleteTest() throws Exception {
        long id = 1L;
        String name = "Opera";
        String color = "Gold";
        Faculty expected = new Faculty(id, name, color);

        doNothing().when(facultyRepository).deleteById(eq(id));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
