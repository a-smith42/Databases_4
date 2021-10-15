create view names_dept as
select concat(firstName, ' ', lastName) as Name, department from details;