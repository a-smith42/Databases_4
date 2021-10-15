#Q4. What are the names of the doctors who wrote the prescriptions for patient 6?
select doctors.firstName, doctors.lastName
from doctors
inner join prescriptions
on doctors.doc_id = prescriptions.doc_id
where prescriptions.pat_id = 6;