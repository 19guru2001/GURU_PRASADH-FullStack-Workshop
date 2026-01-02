SELECT
    department,
    COUNT(id) AS employee_count,
    ROUND(AVG(salary), 2) AS avg_salary,
    MAX(salary) AS max_salary
FROM employees
GROUP BY department
HAVING COUNT(id) > 2;