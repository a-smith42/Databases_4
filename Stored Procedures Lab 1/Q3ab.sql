drop procedure if exists dblabintro.update_rate_position;

DELIMITER //
create procedure update_rate_position
(
in emp_rate_param INT,
in emp_pos_param VARCHAR(225),
out update_count INT
)
begin
  DECLARE sql_error TINYINT DEFAULT FALSE;
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET sql_error = TRUE;
  START TRANSACTION;  
	update details set rate = rate + emp_rate_param
    where position = emp_pos_param;

  IF sql_error = FALSE THEN
	select count(*) from details where position = emp_pos_param into update_count;
    COMMIT;
  ELSE
  set update_count = 0;
    ROLLBACK;
  END IF;
END //
delimiter ;

#call update_rate_position(20, 'Researcher', @update_count);
call update_rate_position(5.50, 'Lecturer', @update_count);

select @update_count;