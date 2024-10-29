package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.awt.print.Pageable;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeBetween(int min, int max);
    @Query(value = "SELECT COUNT(s) FROM Student s")
    long countAllStudents();
    @Query(value = "SELECT AVG(s.age) FROM Student s")
    double findAverageAge();
    @Query(value = "SELECT s FROM Student s ORDER BY s.id DESC")
    List<Student> findTop5ByOrderByIdDesc(Pageable pageable);
}
