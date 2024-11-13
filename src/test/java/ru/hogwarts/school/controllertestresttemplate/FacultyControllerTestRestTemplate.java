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

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class FacultyControllerTestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/faculties";
    }

    @Test
    public void createFaculty_shouldReturnCreatedFaculty() {
        Faculty faculty = new Faculty(4L,"Engineering", "Blue");
        ResponseEntity<Faculty> response = restTemplate.postForEntity(getBaseUrl(), faculty, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Engineering");
    }

    @Test
    public void getFaculty_shouldReturnFaculty() {
        Long facultyId = 1L;
        ResponseEntity<Faculty> response = restTemplate.getForEntity(getBaseUrl() + "/" + facultyId, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(facultyId);
    }

    @Test
    public void updateFaculty_shouldReturnUpdatedFaculty() {
        Long facultyId = 1L;
        Faculty updatedFaculty = new Faculty(3L,"Science", "Green");

        HttpEntity<Faculty> entity = new HttpEntity<>(updatedFaculty);
        ResponseEntity<Faculty> response = restTemplate.exchange(getBaseUrl() + "/" + facultyId, HttpMethod.PUT, entity, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Science");
    }

    @Test
    public void deleteFaculty_shouldReturnNoContent() {
        Long facultyId = 1L;
        ResponseEntity<Void> response = restTemplate.exchange(getBaseUrl() + "/" + facultyId, HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void getFacultiesByNameOrColor_shouldReturnMatchingFaculties() {
        String query = "Engineering";
        ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl() + "/search?query=" + query, List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getStudentsByFaculty_shouldReturnStudentsOfFaculty() {
        Long facultyId = 1L;
        ResponseEntity<Collection> response = restTemplate.getForEntity(getBaseUrl() + "/" + facultyId + "/students/get", Collection.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}