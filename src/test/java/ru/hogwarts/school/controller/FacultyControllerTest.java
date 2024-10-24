package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void createFaculty() throws Exception {
        Faculty faculty = new Faculty(2L, "LALKI", "Red");
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }
    @Test
    void getFaculty() throws Exception {
        Faculty faculty = new Faculty(2L, "LALKI", "Red");


        when(facultyRepository.getById(faculty.getId())).thenReturn(faculty);


        mockMvc.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.id").value(faculty.getId().intValue()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void uptadeFaculty() {
        Faculty faculty = new Faculty(2L, "LALKI", "Red");
        Faculty updatedFaculty = new Faculty(3L, "LOSHKI", "Blue");



    }

    @Test
    void deleteFaculty() {


    }

    @Test
    void getFacultiesByNameOrColor() {
    }

    @Test
    void getStudentsByFaculty() {
    }
}