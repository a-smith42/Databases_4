Drop  Function if exists average_age_per_position;
DELIMITER //
CREATE Function average_age_per_position
(
  pos_param         Varchar(225)
)
RETURNS int
Deterministic
BEGIN
  DECLARE age_var int; 
	select avg(age)
	into age_var
	from details
	where position = pos_param;
 RETURN age_var;
END//
select average_age_per_position("Lecturer");