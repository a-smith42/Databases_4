Drop  Function if exists order_days ;
DELIMITER //
CREATE Function order_days 
(
  order_param		int
)
RETURNS int
Deterministic
BEGIN
  DECLARE days_var int; 
	select (DATEDIFF(shipped_date, order_date))
	into days_var
	from orders
	where order_id = order_param;
 RETURN days_var;
END//
select order_days (45);