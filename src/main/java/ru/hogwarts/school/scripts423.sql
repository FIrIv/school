SELECT s.name, s.age, f.name
FROM student AS s
         INNER JOIN faculty f ON s.faculty_id = f.id;

SELECT s.name, s.age, f.name, a.data
FROM student AS s
         INNER JOIN faculty f ON s.faculty_id = f.id
         INNER JOIN avatar a ON s.id = a.student_id;