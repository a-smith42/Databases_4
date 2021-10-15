select concat(customers.customer_first_name, ' ', customers.customer_last_name) as Customer_Full_Name
from customers
inner join orders
on customers.customer_id = orders.customer_id
where order_id = 70;
