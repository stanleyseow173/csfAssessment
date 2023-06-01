package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Controller
@RequestMapping(path = "/")
public class OrderController {

	// TODO: Task 3 - POST /api/order
	@PostMapping(path = "api/order")
        @ResponseBody
        public ResponseEntity<String> postOrder(@RequestBody String json) {
            System.out.println("jsonstr received by server ====================>" + json);
			//this.marvelSvc.insertComment(id, comment);
            JsonObjectBuilder objBuilder = Json.createObjectBuilder();
            JsonObject returnJson = objBuilder.add("message", "success").build();
            return ResponseEntity.ok(returnJson.toString());
        }


	// TODO: Task 6 - GET /api/orders/<email>


	// TODO: Task 7 - DELETE /api/order/<orderId>

}
