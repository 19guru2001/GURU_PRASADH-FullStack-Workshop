DELIMITER $$

CREATE TRIGGER trg_validate_assignment
BEFORE INSERT ON assignments
FOR EACH ROW
BEGIN
    DECLARE project_count INT;
    DECLARE total_hours INT;

    SELECT COUNT(*)
    INTO project_count
    FROM assignments
    WHERE employee_id = NEW.employee_id;

    IF project_count >= 3 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Employee cannot be assigned to more than 3 projects';
    END IF;

    SELECT IFNULL(SUM(hours_allocated), 0)
    INTO total_hours
    FROM assignments
    WHERE employee_id = NEW.employee_id;

    IF total_hours + NEW.hours_allocated > 2080 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Total allocated hours exceed 2080';
    END IF;
END$$

DELIMITER ;


