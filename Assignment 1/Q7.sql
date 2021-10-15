#Q7. Now list all drugs that have been prescribed by the Doctors – your drug should not appear in the list
select drugs.drug_name as prescribed_drugs
from drugs
inner join prescriptions
on drugs.drug_id = prescriptions.drug_id
group by drugs.drug_id;