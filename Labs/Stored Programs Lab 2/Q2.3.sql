select items.title
from items
inner join order_details
on items.item_id = order_details.item_id
where order_id = 264;
