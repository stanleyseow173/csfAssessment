package ibf2022.batch3.assessment.csf.orderbackend.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.OrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.PendingOrdersRepository;

@Service
public class OrderingService {

	@Autowired
	private OrdersRepository ordersRepo;

	@Autowired
	private PendingOrdersRepository pendingOrdersRepo;
	

	public static final String pizzaURL = "https://pizza-pricing-production.up.railway.app/order";
	// TODO: Task 5
	// WARNING: DO NOT CHANGE THE METHOD'S SIGNATURE
	public PizzaOrder placeOrder(PizzaOrder order) throws OrderException {

		//System.out.println(">>>>>>>>>>>>>>  Milestone 0   ------------->");

		String url = UriComponentsBuilder.fromUriString(pizzaURL)
						.queryParam("name", order.getName())
						.queryParam("email", order.getEmail())
						.queryParam("sauce", order.getSauce())
						.queryParam("size", order.getSize())
						.queryParam("thickCrust", order.getThickCrust())
						.queryParam("toppings", order.getTopplings())
						.queryParam("comments", order.getComments())
						.toUriString();

		//System.out.println(">>>>>>>>>>>>>>  Milestone 1   ------------->");
		//System.out.println(">>>>>>>>>>>>>>  URI String   ------------->"+url);
		RequestEntity<Void> req = RequestEntity.get(url)
			.accept(MediaType.ALL)
			.build();

		//System.out.println(">>>>>>>>>>>>>>  Milestone 2   ------------->");
		RestTemplate template = new RestTemplate();

		ResponseEntity<String> resp = null;
			resp = template.exchange(url, HttpMethod.POST, req, String.class);

		String respStr = resp.getBody();
		List<String> results = Arrays.asList(respStr.split(","));
		String orderID = results.get(0);
		String dateStr = results.get(1);
		Date orderDate = new Date(Long.parseLong(dateStr));
		System.out.println(">>>>>>>>>>>>>> order id ------------->" + orderID);
		System.out.println(">>>>>>>>>>>>>> order date is now  ------------->" + orderDate.toString());
		String totalStr = results.get(2);
		Float total = Float.parseFloat(totalStr);
		System.out.println(">>>>>>>>>>>>>> float is now  ------------->" + total.toString());
		order.setOrderId(orderID);
		order.setDate(orderDate);
		order.setTotal(total);

		//System.out.println(">>>>>>>>>>>>>>  Milestone 3   ------------->");
		ordersRepo.add(order);
		//System.out.println(">>>>>>>>>>>>>>response from api ------------->"+ resp.getBody());
		pendingOrdersRepo.add(order);
		return order;
	}

	// For Task 6
	// WARNING: Do not change the method's signature or its implemenation
	public List<PizzaOrder> getPendingOrdersByEmail(String email) {
		return ordersRepo.getPendingOrdersByEmail(email);
	}

	// For Task 7
	// WARNING: Do not change the method's signature or its implemenation
	public boolean markOrderDelivered(String orderId) {
		return ordersRepo.markOrderDelivered(orderId) && pendingOrdersRepo.delete(orderId);
	}


}
