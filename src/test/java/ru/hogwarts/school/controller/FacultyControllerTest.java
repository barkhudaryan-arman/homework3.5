package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @MockBean
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

        when(facultyService.getFacultyById(faculty.getId())).thenReturn(faculty);

        mockMvc.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.id").value(faculty.getId().intValue()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void uptadeFaculty() throws Exception {
        Faculty faculty = new Faculty(2L, "LALKI", "Red");
        Faculty updatetFaculty = new Faculty(3L, "LOSHKI", "Blue");

        when(facultyService.uptadeFaculty(any(Long.class), any(Faculty.class))).thenReturn(updatetFaculty);
        mockMvc.perform(put("/faculty/" + updatetFaculty.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatetFaculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(updatetFaculty.getName()))
                .andExpect(jsonPath("$.color").value(updatetFaculty.getColor()));
    }

    @Test
    void deleteFaculty() throws Exception {
        Faculty faculty = new Faculty(2L, "LALKI", "Red");

        when(facultyService.deleteFaculty(faculty.getId())).thenReturn(faculty);
        mockMvc.perform(delete("/faculty/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.id").value(faculty.getId().intValue()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void getFacultiesByNameOrColor() throws Exception {
        Faculty faculty = new Faculty(2L, "LALKI", "Red");
        when(facultyService.getFacultiesByNameOrColor("LALKI", "Red")).thenReturn(List.of(faculty));
        mockMvc.perform(get("/faculty/search")
                        .param("query", "LALKI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value(faculty.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()));
    }

    @Test
    void getStudentsByFaculty() throws Exception {
        Faculty faculty = new Faculty(2L, "LALKI", "Red");
        List<Student> students = List.of(new Student(1L,"Student1",18));
        new Student(2L,"Student2",18);
        faculty.setStudents(students);
        when(facultyService.getFacultyById(faculty.getId())).thenReturn(faculty);
        mockMvc.perform(get("/faculty/" + faculty.getId() + "/students/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(students.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(students.get(1).getName()));
    }
}