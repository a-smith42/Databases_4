#Q3. How many prescriptions did the paediatrician write?
select count(prescription_id) as Num_scripts_paed 
from prescriptions 
where doc_id = (select doc_id from doctors where speciality = 'paediatrician');