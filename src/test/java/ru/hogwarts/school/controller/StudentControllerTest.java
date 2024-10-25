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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService StudentService;
    @MockBean
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentService studentService;


    @Test
    void createStudent() throws Exception {
        Student student = new Student(1L,"Arman",23);
        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.color").value(student.getAge()));
    }

    @Test
    void getStudent() throws Exception {
        Student student = new Student(1L,"Arman",23);
        when(studentService.getStudentById(student.getId())).thenReturn(student);

        mockMvc.perform(get("/student/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.color").value(student.getAge()));
    }

    @Test
    void uptadeStudent() throws Exception {
        Student student = new Student(1L,"Arman",23);
        Student uptadetStudent = new Student(3L,"Max",24);

        when(studentService.uptadeStudent(any(Long.class), any(Student.class))).thenReturn(uptadetStudent);
        mockMvc.perform(put("/student/" + uptadetStudent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(uptadetStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(uptadetStudent.getName()))
                .andExpect(jsonPath("$.color").value(uptadetStudent.getId()));
    }

    @Test
    void deleteStudent() throws Exception {
        Student student = new Student(1L,"Arman",23);
        when(studentService.deleteStudent(student.getId())).thenReturn(student);
        mockMvc.perform(get("/student/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.color").value(student.getAge()));
    }

    @Test
    void getStudentsByAgeRange() {
        Student student1 = new Student(1L, "Arman", 23);
        Student student2 = new Student(2L, "Sako", 25);
        List<Student> expectedStudents = Arrays.asList(student1, student2);
        when(studentRepository.findByAgeBetween(20, 30)).thenReturn(expectedStudents);

        List<Student> result = studentService.getStudentsByAgeRange(20, 30);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(student1));
        assertTrue(result.contains(student2));

        verify(studentRepository, times(1)).findByAgeBetween(20, 30);
    }

    @Test
    void getFacultyByStudent() throws Exception {
        Student student = new Student(1L,"Arman",23);
        List<Faculty> faculties = List.of(new Faculty(1L,"Faculty1","Green"));
        new Faculty(1L,"Faculty2","Red");
        when(studentService.getStudentById(student.getId())).thenReturn(student);
        mockMvc.perform(get("/student/" + student.getId() + "/faculty/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(faculties.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(faculties.get(1).getName()));
    }
}