DROP TRIGGER IF EXISTS details_after_delete;
DELIMITER //
CREATE TRIGGER details_after_delete
  after delete ON details
  FOR EACH ROW 
BEGIN
  insert into details_audit values
  (OLD.id, OLD.firstname, OLD.lastname, "DELETED", NOW());
END// 
delete from details where id = 1;

