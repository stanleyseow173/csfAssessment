package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import ibf2022.batch3.assessment.csf.orderbackend.models.CreatePizzaOrder;
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
		d.append("date", String.valueOf(order.getDate().getTime()));
		d.append("total", order.getTotal());
		d.append("name", order.getName());
		d.append("email", order.getEmail());
		d.append("sauce", order.getSauce());
		d.append("size", order.getSize());
		d.append("thickCrust", order.getThickCrust());
		d.append("comments", order.getComments());
		d.append("toppings", order.getTopplings());
		d.append("delivered", false);

		mongoTemplate.insert(d,"orders");
	}
	
	// TODO: Task 6
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for getPendingOrdersByEmail()

	//db.orders.find({email: 'test@gmail', delivered:false}).sort({"date":-1})

	public List<PizzaOrder> getPendingOrdersByEmail(String email) {
		Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
		query.addCriteria(Criteria.where("delivered").is(false));
		query.with(Sort.by(Sort.Direction.DESC,"date"));

		List<PizzaOrder> orders = new ArrayList<PizzaOrder>();

		System.out.println("==========> went through repo =============>");
		try{
			List<String> ord = mongoTemplate.find(query, Document.class, "orders").stream().map(d -> d.toJson().toString()).toList();
			// {
			// 	try {
			// 		return (PizzaOrder)CreatePizzaOrder.createPizzaOrder(d.toJson().toString());
			// 	} catch (IOException e) {
			// 		// TODO Auto-generated catch block
			// 		e.printStackTrace();
			// 	}
			// 	return null;
			// }
			for (String s: ord){
				orders.add(CreatePizzaOrder.createPizzaOrderThickCrust(s));
				System.out.println("Document is ============>"+s);
			}
			
			

		}catch(Exception e){
			e.printStackTrace();
		}
		return orders;
	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for markOrderDelivered()

// 	db.orders.updateOne(
//    { _id: '181cc8f4b9' },
//    { $set:
//       {
//         "delivered": true
//       }
//    }
// );
	public boolean markOrderDelivered(String orderId) {
		Query query = Query.query(
            Criteria.where("_id").is(orderId)
        );

        Update updateOps = new Update()
            .set("delivered", true);

		UpdateResult result = mongoTemplate.updateFirst(query, updateOps, PizzaOrder.class, "orders");
		
		return result.getModifiedCount()>0;
	}


}
