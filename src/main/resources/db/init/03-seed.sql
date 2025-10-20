USE employees_db;

-- Datos de ejemplo (diversos géneros, edades, puestos y fechas válidas dd-MM-yyyy -> DATE)
-- Recuerda: birth_date es DATE (yyyy-MM-dd)
INSERT INTO employees
(first_name, second_name, last_name_paternal, last_name_maternal, age, gender, birth_date, position) VALUES
('Juan',   'Carlos', 'Hernandez', 'Lopez',   29, 'MALE',   '1996-08-15', 'Backend Engineer'),
('Ana',    NULL,     'Lopez',     'Perez',   28, 'FEMALE', '1997-05-05', 'QA Analyst'),
('Luis',   'Alberto','Gomez',     'Rios',    32, 'MALE',   '1993-03-10', 'DevOps Engineer'),
('Maria',  'Elena',  'Ramirez',   'Soto',    35, 'FEMALE', '1990-11-22', 'Product Owner'),
('Sofia',  NULL,     'Martinez',  'Diaz',    24, 'FEMALE', '2001-01-19', 'UI/UX Designer'),
('Carlos', 'Eduardo','Nunez',     'Vega',    41, 'MALE',   '1984-07-30', 'Tech Lead'),
('Alex',   NULL,     'Cruz',      'Mora',    26, 'OTHER',  '1999-02-14', 'Data Analyst'),
('Diego',  'Armando','Flores',    'Ibarra',  38, 'MALE',   '1987-04-03', 'Scrum Master'),
('Elena',  NULL,     'Aguilar',   'Ponce',   27, 'FEMALE', '1998-09-09', 'Frontend Engineer'),
('Ricardo','Ivan',   'Santos',    'Ruiz',    30, 'MALE',   '1995-12-12', 'SRE Engineer');
