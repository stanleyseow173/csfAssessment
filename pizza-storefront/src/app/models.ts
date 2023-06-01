export interface PizzaQuery {
    name: string
    email: string
    size: string
    base: string
    sauce: string
    toppings: string[]
    comments: string
    delivered: boolean
  }

  export interface Order{
    orderId: string
    total: string
    date: string
  }