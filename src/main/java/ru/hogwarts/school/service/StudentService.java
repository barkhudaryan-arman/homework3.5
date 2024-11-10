package ru.hogwarts.school.service;

import org.slf4j.LoggerFactory;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class StudentService {
    Logger logger = (Logger) LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student getStudentById(Long studentId) {
        logger.info("Was invoked method for get student by id");
        return studentRepository.findById(studentId)
                .orElse(null);
    }

    public Student uptadeStudent(Long studentId, Student student) {
        logger.info("Was invoked method for update student by id");
        if (studentRepository.existsById(studentId)){
            student.setId(studentId);
            return studentRepository.save(student);
        }
        return null;
    }

    public Student deleteStudent(Long studentId) {
        logger.info("Was invoked method for delete student by id");
        Student student = getStudentById(studentId);
        if (student != null){
            studentRepository.deleteById(studentId);
        }
        return student;
    }
    public List<Student> getStudentsByAgeRange(int min, int max) {
        logger.info("Was invoked method for getStudentsByAgeRange");
        return studentRepository.findByAgeBetween(min, max);
    }
    public long countAllStudents() {
        logger.info("Was invoked method for countAllStudents");
        return studentRepository.count();
    }
    public Double findAverageAge() {
        logger.info("Was invoked method for findAverageAge");
        return studentRepository.findAverageAge();
    }
    public List<Student> findTop5ByOrderByIdDesc(Pageable pageable) {
        logger.info("Was invoked method for findTop5ByOrderByIdDesc");
        return studentRepository.findTop5ByOrderByIdDesc(pageable);
    }
}