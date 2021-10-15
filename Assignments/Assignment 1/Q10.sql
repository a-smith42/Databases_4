#Q10. Write a Stored Procedure for this database.  Explain why your Stored Procedure is useful.
# * * * create procedure to check the costs of all prescriptions combined * * *
drop procedure if exists get_cost;
DELIMITER //
create procedure get_cost()
begin
  DECLARE sql_error TINYINT DEFAULT FALSE;
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET sql_error = TRUE;
  START TRANSACTION;  
	select sum(cost) as Total_Cost 
    from drugs
    inner join prescriptions
    on drugs.drug_id = prescriptions.drug_id;

  IF sql_error = FALSE THEN
    COMMIT;
  ELSE
    select"rollback";
  END IF;
END //
delimiter ;

call get_cost();
