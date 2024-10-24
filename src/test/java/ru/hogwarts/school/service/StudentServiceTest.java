package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);
        student.setName("Harry Potter");
        student.setAge(15);
    }

    @Test
    void testCreateStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student createdStudent = studentService.createStudent(student);

        assertNotNull(createdStudent);
        assertEquals(student.getName(), createdStudent.getName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testGetStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student foundStudent = studentService.getStudentById(1L);

        assertNotNull(foundStudent);
        assertEquals(student.getName(), foundStudent.getName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateStudent() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.save(student)).thenReturn(student);

        Student updatedStudent = studentService.uptadeStudent(1L, student);

        assertNotNull(updatedStudent);
        assertEquals(student.getName(), updatedStudent.getName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testDeleteStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student deletedStudent = studentService.deleteStudent(1L);

        assertNotNull(deletedStudent);
        assertEquals(student.getName(), deletedStudent.getName());
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetStudentsByAgeRange() {
        // Arrange
        List<Student> mockStudents = Arrays.asList(
                new Student(1L, "John", 20),
                new Student(2L, "Alice", 22)
        );
        when(studentRepository.findByAgeBetween(18, 25)).thenReturn(mockStudents);

        // Act
        List<Student> students = studentService.getStudentsByAgeRange(18, 25);

        // Assert
        assertNotNull(students);
        assertEquals(2, students.size());
        assertEquals("John", students.get(0).getName());
        assertEquals("Alice", students.get(1).getName());

        // Verify
        verify(studentRepository, times(1)).findByAgeBetween(18, 25);
    }

    @Test
    public void testGetStudentsByAgeRange_EmptyResult() {
        // Arrange
        when(studentRepository.findByAgeBetween(30, 40)).thenReturn(Arrays.asList());

        // Act
        List<Student> students = studentService.getStudentsByAgeRange(30, 40);

        // Assert
        assertTrue(students.isEmpty());

        // Verify
        verify(studentRepository, times(1)).findByAgeBetween(30, 40);
    }
}