import { Injectable, inject } from "@angular/core";
import { PizzaQuery , Order} from "./models";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class PizzaService {

  http = inject(HttpClient)
  // TODO: Task 3
  // You may add any parameters and return any type from placeOrder() method
  // Do not change the method name
  placeOrder(query: PizzaQuery) {

    return this.http.post<any>('/api/order', query)
  }

  // TODO: Task 5
  // You may add any parameters and return any type from getOrders() method
  // Do not change the method name
  getOrders(email: string) {

    const URL = '/api/orders/' + email

    return this.http.get<Order[]>(`${URL}`)
  }

  // TODO: Task 7
  // You may add any parameters and return any type from delivered() method
  // Do not change the method name
  delivered(orderId: string) {

    const URL = '/api/order/' + orderId

    return this.http.delete(URL)

  }

}
