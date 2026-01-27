SELECT department, name, salary, rank_in_dept
FROM (
    SELECT 
        name,
        department,
        salary,
        DENSE_RANK() OVER (PARTITION BY department ORDER BY salary DESC) AS rank_in_dept
    FROM employees
) AS ranked_employees
WHERE rank_in_dept <= 3;