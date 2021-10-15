#Q9. Update the drugs tables to record the manufacturer of Panadol and Calpol as GlaxoSmithKline. 
update drugs set Manufacturer = 'GlaxoSmithKline' where drug_name = 'Panadol' or drug_name = 'Calpol';
select * from drugs;