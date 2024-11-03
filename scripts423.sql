SELECT Student.name AS student_name, Student.age, Faculty.name AS faculty_name
FROM Student
JOIN Faculty ON Student.faculty_id = faculty.id
WHERE Faculty.name = 'Хогвартс';

SELECT Student.name, Student.age
FROM Student
WHERE avatar IS NOT NULL;
