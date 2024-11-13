package ru.hogwarts.school.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;

@RequestMapping("/student")
@RestController

public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("{id}")
    public Student uptadeStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.uptadeStudent(id, student);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("age-range")
    public List<Student> getStudentsByAgeRange(
            @RequestParam int min,
            @RequestParam int max) {
        return studentService.getStudentsByAgeRange(min, max);
    }
    @GetMapping("{id}/faculty/get")
    public Faculty getFacultyByStudent(@PathVariable Long id){
        return studentService.getStudentById(id).getFaculty();
    }
    @GetMapping("/count")
    public long getStudentCount(){
        return studentService.countAllStudents();
    }
    @GetMapping("/average-age")
    public Double getAverageAge(){
        return studentService.findAverageAge();
    }
    @GetMapping("/last-five")
    public List<Student> getLastFiveStudents(){
        Pageable topFive = (Pageable) PageRequest.of(0,5);
        return studentService.findTop5ByOrderByIdDesc(topFive);
    }
}