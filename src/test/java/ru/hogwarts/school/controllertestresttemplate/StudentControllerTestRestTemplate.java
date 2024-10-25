package ru.hogwarts.school.controllertestresttemplate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class StudentControllerTestRestTemplate {



    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/students";
    }

    @Test
    public void createStudent_shouldReturnCreatedStudent() {
        Student student = new Student(1L,"John Doe", 20);
        ResponseEntity<Student> response = restTemplate.postForEntity(getBaseUrl(), student, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("John Doe");
    }

    @Test
    public void getStudent_shouldReturnStudent() {
        Long studentId = 1L;
        ResponseEntity<Student> response = restTemplate.getForEntity(getBaseUrl() + "/" + studentId, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(studentId);
    }

    @Test
    public void updateStudent_shouldReturnUpdatedStudent() {
        Long studentId = 1L;
        Student updatedStudent = new Student(2L,"Jane Doe", 22);

        HttpEntity<Student> entity = new HttpEntity<>(updatedStudent);
        ResponseEntity<Student> response = restTemplate.exchange(getBaseUrl() + "/" + studentId, HttpMethod.PUT, entity, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Jane Doe");
    }

    @Test
    public void deleteStudent_shouldReturnNoContent() {
        Long studentId = 1L;
        ResponseEntity<Void> response = restTemplate.exchange(getBaseUrl() + "/" + studentId, HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void getStudentsByAgeRange_shouldReturnStudentsWithinAgeRange() {
        int minAge = 18;
        int maxAge = 25;
        ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl() + "/age-range?min=" + minAge + "&max=" + maxAge, List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getFacultyByStudent_shouldReturnStudentFaculty() {
        Long studentId = 1L;
        ResponseEntity<Faculty> response = restTemplate.getForEntity(getBaseUrl() + "/" + studentId + "/faculty/get", Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}