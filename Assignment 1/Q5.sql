#Q5. List the firstName, lastName of all the patients who had no prescription written for them.  
select firstName, lastName from patients
where pat_id not in (select pat_id from prescriptions);