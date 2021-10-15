#SELECT  order_id, DATEDIFF(order_date, shipped_date) AS days from orders where days > 10;

SELECT  order_id from orders where (DATEDIFF(shipped_date, order_date) > 10);