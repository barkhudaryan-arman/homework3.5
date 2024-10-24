package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long facultyId) {
        return facultyRepository.findById(facultyId)
                .orElse(null);
    }

    public Faculty uptadeFaculty(Long facultyId, Faculty faculty) {
        if (facultyRepository.existsById(facultyId)) {
            faculty.setId(facultyId);
            return facultyRepository.save(faculty);
        }
        return null;
    }

    public Faculty deleteFaculty(Long facultyId) {
        Faculty faculty = getFacultyById(facultyId);
        if (faculty != null) {
            facultyRepository.deleteById(facultyId);
        }
        return faculty;
    }
    public List<Faculty> getFacultiesByNameOrColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}



