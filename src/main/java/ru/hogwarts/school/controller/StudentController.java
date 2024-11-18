package ru.hogwarts.school.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/students")
@RestController

public class StudentController {
    private final List<String> students = List.of("Harry", "Ron", "Hermiona", "Lol", "Kek", "Cheburek")
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
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
    @GetMapping("/names-starting-with-a")
    public List<String> getStudentsByNameStartingWithA(){
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }
    @GetMapping("/new-average-age")
    public double getNewAverageAge() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }
    @GetMapping("/print-parallel")
    public ResponseEntity<Void> printStudentsParallel() {
        System.out.println(students.get(0));
        System.out.println(students.get(1));

        Thread thread1 = new Thread(() -> {
            System.out.println(students.get(2));
            System.out.println(students.get(3));
        });

        Thread thread2 = new Thread(() -> {
            System.out.println(students.get(4));
            System.out.println(students.get(5));
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread execution was interrupted", e);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/print-synchronized")
    public ResponseEntity<Void> printStudentsSynchronized() {
        printSynchronized(students.get(0));
        printSynchronized(students.get(1));

        Thread thread1 = new Thread(() -> {
            printSynchronized(students.get(2));
            printSynchronized(students.get(3));
        });

        Thread thread2 = new Thread(() -> {
            printSynchronized(students.get(4));
            printSynchronized(students.get(5));
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread execution was interrupted", e);
        }

        return ResponseEntity.ok().build();
    }

    private synchronized void printSynchronized(String student) {
        System.out.println(student);
    }
}