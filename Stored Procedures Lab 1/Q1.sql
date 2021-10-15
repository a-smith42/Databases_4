select 'Doctor' as Type, firstName, lastName from doctors
union 
select 'Patient' as Type, firstName, lastName from patients;