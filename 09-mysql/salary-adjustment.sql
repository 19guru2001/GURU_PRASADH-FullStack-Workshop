CREATE PROCEDURE AdjustDepartmentSalary(
    IN dept VARCHAR(50),
    IN percent DECIMAL(5,2),
    OUT affected_count INT
)
BEGIN
    IF percent < 0 THEN
        SET affected_count = 0;
    ELSE
        UPDATE employees
        SET salary = salary + (salary * percent / 100)
        WHERE department = dept;

        SET affected_count = ROW_COUNT();
    END IF;
END$$

DELIMITER ;

CALL AdjustDepartmentSalary('IT', 10, @count);
SELECT @count AS affected_employees;

