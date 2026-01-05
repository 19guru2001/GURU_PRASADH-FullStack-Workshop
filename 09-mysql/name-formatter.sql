SELECT
    CONCAT(
        UPPER(SUBSTRING_INDEX(name, ' ', -1)),
        ', ',
        SUBSTRING_INDEX(name, ' ', 1)
    ) AS formatted_name,
    LOWER(
        CONCAT(
            SUBSTRING_INDEX(name, ' ', 1),
            '.',
            SUBSTRING_INDEX(name, ' ', -1),
            '@company.com'
        )
    ) AS email,
    CONCAT(
        UPPER(LEFT(SUBSTRING_INDEX(name, ' ', 1), 1)),
        UPPER(LEFT(SUBSTRING_INDEX(name, ' ', -1), 1))
    ) AS initials
FROM employees;