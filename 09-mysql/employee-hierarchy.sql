select e.name as emp_name, m.name as man_name
from employees as e 
join employees as m 
on e.manager_id=m.id;