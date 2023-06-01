package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ibf2022.batch3.assessment.csf.orderbackend.models.CreatePizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderingService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Controller
@RequestMapping(path = "/")
public class OrderController {

	@Autowired
	private OrderingService orderSvc;

	// TODO: Task 3 - POST /api/order
	@PostMapping(path = "api/order")
        @ResponseBody
        public ResponseEntity<String> postOrder(@RequestBody String json) {
            System.out.println("jsonstr received by server ====================>" + json);
			//String jsonBad = json+"asdasdsadasd";
			try{
				PizzaOrder order = CreatePizzaOrder.createPizzaOrder(json);
				Document d = new Document();
				d.append("name", order.getName());
				d.append("email", order.getEmail());

				order = this.orderSvc.placeOrder(order);
				d.append("orderId", order.getOrderId());
				d.append("date", String.valueOf(order.getDate().getTime()));
				d.append("total", order.getTotal().toString());

				return ResponseEntity.accepted().body(d.toJson().toString());
			}
			catch(Exception ex){
				ex.printStackTrace();
				JsonObjectBuilder objBuilder = Json.createObjectBuilder();
				//System.out.println(ex.getMessage());
				//System.out.println("eMessage first ===================================>"+ex.getMessage());
				JsonObject returnJson = objBuilder.add("error", ex.getMessage()).build();
				//System.out.println("eMessage ===================================>"+returnJson);
				return ResponseEntity.badRequest().body(returnJson.toString());
			}

			//this.marvelSvc.insertComment(id, comment);
            
        }


	// TODO: Task 6 - GET /api/orders/<email>

	@GetMapping(path="api/orders/{email}")
	@ResponseBody
	public ResponseEntity<String> getOrders(@PathVariable String email){

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Milestone 1>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		List<PizzaOrder> orders = new ArrayList<PizzaOrder>();
		orders = this.orderSvc.getPendingOrdersByEmail(email);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Milestone 2>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Milestone 3>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		orders.stream()
			.map(d -> Json.createObjectBuilder()
						.add("orderId",d.getOrderId())
						.add("total",d.getTotal().toString())
						.add("date",d.getDate().toString())
						.build())
			.forEach(arrBuilder::add);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Milestone 4>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return ResponseEntity.ok(arrBuilder.build().toString());
	}


	// TODO: Task 7 - DELETE /api/order/<orderId>
	@DeleteMapping(path="/api/order/{orderId}")
	@ResponseBody
	public ResponseEntity<String> deliveredOrder(@PathVariable String orderId){
		System.out.println("orderID received by server ====================>" + orderId);

		boolean delivered = this.orderSvc.markOrderDelivered(orderId);

		if(delivered){
			return ResponseEntity.ok("");
		}else{
			return ResponseEntity.notFound().build();
		}

	}

}
