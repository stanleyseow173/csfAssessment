package ibf2022.batch3.assessment.csf.orderbackend.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;

public class CreatePizzaOrder {
    
    public static PizzaOrder createPizzaOrder(String json) throws IOException{
    PizzaOrder p = new PizzaOrder();
    if (json != null) {
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            p.setName(o.getString("name"));
            p.setEmail(o.getString("email"));
            p.setSize(o.getInt("size"));
            p.setThickCrust(o.getString("base")=="thick"?true:false);
            p.setSauce(o.getString("sauce"));
            //System.out.println(">>>>>>went through this milestone 0");
            JsonArray toppingJsonArray = o.getJsonArray("toppings");
            //System.out.println(">>>>>>went through this milestone 1");
            List<String> toppings = new ArrayList<>();
            //System.out.println(">>>>>>went through this milestone 2");
            for (JsonString topping : toppingJsonArray.getValuesAs(JsonString.class)){
                toppings.add(topping.getString());
            }
            //System.out.println(">>>>>>went through this milestone 3");
            p.setTopplings(toppings);
            if (o.getString("comments").length()>0){
                p.setComments(o.getString("comments"));
            }else{
                p.setComments("");
            }
            
            
            
            //p.setTopplings(o.getString("name"));
        }
    }
    return p;
    }

    public static PizzaOrder createPizzaOrderThickCrust(String json) throws IOException{
        PizzaOrder p = new PizzaOrder();
        if (json != null) {
            try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
                JsonReader r = Json.createReader(is);
                JsonObject o = r.readObject();
                p.setName(o.getString("name"));
                p.setEmail(o.getString("email"));
                p.setSize(o.getInt("size"));
                p.setThickCrust(o.getBoolean("thickCrust"));
                p.setSauce(o.getString("sauce"));
                //System.out.println(">>>>>>went through this milestone 0");
                JsonArray toppingJsonArray = o.getJsonArray("toppings");
                //System.out.println(">>>>>>went through this milestone 1");
                List<String> toppings = new ArrayList<>();
                //System.out.println(">>>>>>went through this milestone 2");
                for (JsonString topping : toppingJsonArray.getValuesAs(JsonString.class)){
                    toppings.add(topping.getString());
                }
                //System.out.println(">>>>>>went through this milestone 3");
                p.setTopplings(toppings);
                if (o.getString("comments").length()>0){
                    p.setComments(o.getString("comments"));
                }else{
                    p.setComments("");
                }
                System.out.println("problem 0");
                p.setTotal(Float.valueOf(o.getJsonNumber("total").toString()));
                System.out.println("problem 1");
                p.setOrderId(o.getString("_id"));
                System.out.println("problem 2");
                System.out.println("date"+o.getString("date"));
                p.setDate(new Date(Long.parseLong(o.getString("date"))));
                System.out.println("problem 3");
                //p.setTopplings(o.getString("name"));
            }
        }
        return p;
        }
}