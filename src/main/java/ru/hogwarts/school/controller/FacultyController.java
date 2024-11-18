package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@RequestMapping("/faculty")
@RestController

public class FacultyController {
    private final FacultyService facultyService;
    private final FacultyRepository facultyRepository;

    public FacultyController(FacultyService facultyService, FacultyRepository facultyRepository) {
        this.facultyService = facultyService;
        this.facultyRepository = facultyRepository;
    }
    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.createFaculty(faculty);
    }
    @GetMapping("{id}")
    public Faculty getFaculty(@PathVariable Long id){
        return facultyService.getFacultyById(id);
    }
    @PutMapping("{id}")
    public Faculty uptadeFaculty(@PathVariable Long id, @RequestBody Faculty faculty){
        return facultyService.uptadeFaculty(id, faculty);
    }
    @DeleteMapping("{id}")
    public void deleteFaculty(@PathVariable Long id){
        facultyService.deleteFaculty(id);
    }
    @GetMapping("search")
    public List<Faculty> getFacultiesByNameOrColor(
            @RequestParam String query) {
        return facultyService.getFacultiesByNameOrColor(query, query);
    }
    @GetMapping("{id}/students/get")
    public Collection<Student> getStudentsByFaculty(@PathVariable Long id){
        return facultyService.getFacultyById(id).getStudents();
    }
    @GetMapping("/longest-name")
    public String getLongestFacultyName(){
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("LOL");
    }
}
