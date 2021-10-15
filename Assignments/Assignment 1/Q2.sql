#Q2. What is the name and price of the most expensive drug in the drugs table?
select drug_name, cost from drugs where cost = (select max(cost) from drugs);