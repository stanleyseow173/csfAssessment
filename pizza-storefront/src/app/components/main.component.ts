import { Component, OnInit, inject } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PizzaService } from '../pizza.service';
import { PizzaQuery } from '../models';
import { firstValueFrom } from 'rxjs';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PIZZA_TOPPINGS: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{

  pizzaSize = SIZES[0]

  fb = inject(FormBuilder)
  router = inject(Router)

  form!: FormGroup



  constructor(private pizzaSvc: PizzaService) { }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  process() {
    const query = this.form.value as PizzaQuery
    console.info('>>> query: ', query)
    //const queryParams: Params =  { units: query.units }
    //this.router.navigate([ '/weather', query.city ], { queryParams: queryParams })
    const v = firstValueFrom(this.pizzaSvc.placeOrder(query)).then(v =>v)
    console.info(">>return from controller", v)
  }

  private createForm() {
    return this.fb.group({
      name: this.fb.control<string>('', [ Validators.required]),
      email: this.fb.control<string>('', [ Validators.required ]),
      size: this.fb.control<number>(1, [ Validators.required ]), //check this
      base: this.fb.control<string>('', [ Validators.required ]),
      sauce: this.fb.control<string>('', [ Validators.required ]),
      toppings: new FormArray([],[Validators.required]),
      comments: this.fb.control<string>('')
    })
  }

  onCheckChange(event: any) {
    const formArray: FormArray = this.form.get('toppings') as FormArray;
  
    /* Selected */
    if(event.target.checked){
      // Add a new control in the arrayForm
      formArray.push(new FormControl(event.target.value));
    }
    /* unselected */
    // else{
    //   // find the unselected element
    //   let i: number = 0;
  
    //   formArray.controls.forEach((ctrl: FormControl) => {
    //     if(ctrl.value == event.target.value) {
    //       // Remove the unselected element from the arrayForm
    //       formArray.removeAt(i);
    //       return;
    //     }
  
    //     i++;
    //   });
    // }
  }
}
