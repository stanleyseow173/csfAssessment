package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

@Repository
public class PendingOrdersRepository {

	@Autowired
	@Qualifier(value="pending-orders")
	RedisTemplate<String, String> redisTemplate;
	
	// TODO: Task 3
	// WARNING: Do not change the method's signature.
	public void add(PizzaOrder order) {
		Document d = new Document();
		d.append("orderId", order.getOrderId());
		d.append("date", String.valueOf(order.getDate().getTime()));
		d.append("total", order.getTotal());
		d.append("name", order.getName());
		d.append("email", order.getEmail());

		redisTemplate.opsForValue().set(order.getOrderId(),d.toString());
	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	public boolean delete(String orderId) {
		
		return redisTemplate.delete(orderId);
	}

}
