package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

@Repository
public class OrdersRepository {


	@Autowired
    private MongoTemplate mongoTemplate;
	// TODO: Task 3
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for add()
	// 	db.orders.insert(
	// {
	// 	_id: 'id1',
	// 	date: '27-03-2023',
	// 	total: '50',
	// 	name: 'Seow Sze Howe Stanley',
	// 	email: 'seowszehowestanley@gmail.com',
	// 	sauce: 'chicken',
	// 	size: 2,
	// 	comments: 'want it fast',
	// 	toppings: ['cheese','chilli']
	// });


	public void add(PizzaOrder order) {

		//Mongo Insert
		Document d = new Document();
		d.append("_id", order.getOrderId());
		d.append("date", order.getDate());
		d.append("total", order.getTotal());
		d.append("name", order.getName());
		d.append("email", order.getEmail());
		d.append("sauce", order.getSauce());
		d.append("size", order.getSize());
		d.append("thickCrust", order.getThickCrust());
		d.append("comments", order.getComments());
		d.append("toppings", order.getTopplings());

		mongoTemplate.insert(d,"orders");
	}
	
	// TODO: Task 6
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for getPendingOrdersByEmail()
	public List<PizzaOrder> getPendingOrdersByEmail(String email) {

		return null;
	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for markOrderDelivered()
	public boolean markOrderDelivered(String orderId) {

		return false;
	}


}
