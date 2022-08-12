-- liquibase formatted sql

-- changeSet firiv:1

CREATE INDEX student_name ON student (name);

-- changeSet firiv:2

CREATE INDEX faculties_namecolor ON faculty (name, color);
