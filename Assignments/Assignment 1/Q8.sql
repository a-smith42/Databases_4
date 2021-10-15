#Q8. Add an attribute “Manufacturer” to the drugs tables.
alter table drugs
add Manufacturer varchar(255);
select * from drugs;