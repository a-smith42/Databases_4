drop procedure if exists update_department
DELIMITER //
create procedure update_department
(
in emp_id_param INT,
in emp_dept_param VARCHAR(225)
)
begin
  DECLARE sql_error TINYINT DEFAULT FALSE;
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET sql_error = TRUE;
  START TRANSACTION;  
	update details set department = emp_dept_param
    where id = emp_id_param;

  IF sql_error = FALSE THEN
    COMMIT;
  ELSE
    select"rollback";
  END IF;
END //
delimiter ;

call update_department(1, 'Databases Q2b');

select * from details;
