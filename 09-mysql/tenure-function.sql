CREATE FUNCTION GetTenureCategory(hire_date DATE)
RETURNS VARCHAR(20)
DETERMINISTIC
BEGIN
    DECLARE years_worked INT;
    SET years_worked = TIMESTAMPDIFF(YEAR, hire_date, CURDATE());

    IF years_worked > 5 THEN
        RETURN 'Veteran';
    ELSEIF years_worked BETWEEN 2 AND 5 THEN
        RETURN 'Experienced';
    ELSE
        RETURN 'New Hire';
    END IF;
END$$


SELECT
    name,
    hire_date,
    GetTenureCategory(hire_date) AS tenure_category
FROM employees;
