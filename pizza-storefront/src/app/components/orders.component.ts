import { AfterViewInit, Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PizzaService } from '../pizza.service';
import { Order } from '../models';
import { Observable, Subject, firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit, AfterViewInit{

  activateRoute = inject(ActivatedRoute)
  pizzaSvc = inject(PizzaService)

  email =''
  orders$!: Observable<Order[]>


  ngOnInit(): void {
    this.email = this.activateRoute.snapshot.params['email']
    this.orders$ = this.pizzaSvc.getOrders(this.email)
    //setTimeout(()=>location.reload(),2000);
    //location.reload
    //this.orders$.subscribe(this.order)
  }

  ngAfterViewInit(): void {
    this.orders$ = this.pizzaSvc.getOrders(this.email)
    //location.reload()
  }

  processClick(event:any){
    console.info(">>event--->",event)
    const v = firstValueFrom(this.pizzaSvc.delivered(event)).then(v=>v)
    this.orders$ = this.pizzaSvc.getOrders(this.email)
    location.reload()
  }
}
