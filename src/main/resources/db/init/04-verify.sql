USE employees_db;

-- Verifica que la carga se realizó correctamente
SELECT COUNT(*) AS total_employees FROM employees;
SELECT * FROM employees ORDER BY id LIMIT 5;
