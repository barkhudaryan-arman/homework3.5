package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyService facultyService;

    private Faculty faculty;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Gryffindor");
        faculty.setColor("Red");
    }

    @Test
    void testCreateFaculty() {
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Faculty createdFaculty = facultyService.createFaculty(faculty);

        assertNotNull(createdFaculty);
        assertEquals(faculty.getName(), createdFaculty.getName());
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    void testGetFacultyById() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        Faculty foundFaculty = facultyService.getFacultyById(1L);

        assertNotNull(foundFaculty);
        assertEquals(faculty.getName(), foundFaculty.getName());
        verify(facultyRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateFaculty() {
        when(facultyRepository.existsById(1L)).thenReturn(true);
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Faculty updatedFaculty = facultyService.uptadeFaculty(1L, faculty);

        assertNotNull(updatedFaculty);
        assertEquals(faculty.getName(), updatedFaculty.getName());
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    void testDeleteFaculty() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        Faculty deletedFaculty = facultyService.deleteFaculty(1L);

        assertNotNull(deletedFaculty);
        assertEquals(faculty.getName(), deletedFaculty.getName());
        verify(facultyRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetFacultiesByNameOrColor() {
        // Arrange
        List<Faculty> mockFaculties = Arrays.asList(
                new Faculty(1L, "Engineering", "Blue"),
                new Faculty(2L, "Arts", "Red")
        );
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("engineering", "engineering"))
                .thenReturn(mockFaculties);

        // Act
        List<Faculty> faculties = facultyService.getFacultiesByNameOrColor("engineering", "engineering");

        // Assert
        assertNotNull(faculties);
        assertEquals(2, faculties.size());
        assertEquals("Engineering", faculties.get(0).getName());
        assertEquals("Arts", faculties.get(1).getName());

        // Verify
        verify(facultyRepository, times(1))
                .findByNameIgnoreCaseOrColorIgnoreCase("engineering", "engineering");
    }

    @Test
    public void testGetFacultiesByNameOrColor_EmptyResult() {
        // Arrange
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("unknown", "unknown"))
                .thenReturn(Arrays.asList());

        // Act
        List<Faculty> faculties = facultyService.getFacultiesByNameOrColor("unknown", "unknown");

        // Assert
        assertTrue(faculties.isEmpty());

        // Verify
        verify(facultyRepository, times(1))
                .findByNameIgnoreCaseOrColorIgnoreCase("unknown", "unknown");
    }
}
