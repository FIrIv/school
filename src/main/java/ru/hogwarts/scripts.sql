select *
from student;

select name, age
from student
where age>=10 and age<=15;

select s.name
from student as s;

select name
from student
where name like '%а%';

select *
from student
where age<=id;

select age, name
from student
order by age;
