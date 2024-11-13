package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElse(null);
    }

    public Student uptadeStudent(Long studentId, Student student) {
        if (studentRepository.existsById(studentId)){
            student.setId(studentId);
            return studentRepository.save(student);
        }
        return null;
    }

    public Student deleteStudent(Long studentId) {
        Student student = getStudentById(studentId);
        if (student != null){
            studentRepository.deleteById(studentId);
        }
        return student;
    }
    public List<Student> getStudentsByAgeRange(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }
    public long countAllStudents() {
        return studentRepository.count();
    }
    public Double findAverageAge() {
        return studentRepository.findAverageAge();
    }
    public List<Student> findTop5ByOrderByIdDesc(Pageable pageable) {
        return studentRepository.findTop5ByOrderByIdDesc(pageable);
    }
}