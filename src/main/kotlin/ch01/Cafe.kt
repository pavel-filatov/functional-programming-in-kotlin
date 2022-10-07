package ch01


data class Coffee(val price: Int)

class CreditCard

data class Charge(val cc: CreditCard, val amount: Int) {
    fun combine(other: Charge): Charge {
        if (cc == other.cc) {
            return Charge(cc, amount + other.amount)
        } else throw Exception("Impossible to combine charges from different cards.")
    }
}

// How to extend the functionality :o
fun List<Charge>.coalesce(): List<Charge> {
    return this.groupBy { it.cc }.values.map { it.reduce { l, r -> l.combine(r) } }
}

class Cafe {

    fun buyCoffee(cc: CreditCard, price: Int): Pair<Coffee, Charge> {
        val coffee = Coffee(price)
        return Pair(coffee, Charge(cc, coffee.price))
    }

    fun buyCoffees(cc: CreditCard, n: Int, price: Int): Pair<List<Coffee>, List<Charge>> {
        val orders = List(n) { buyCoffee(cc, price) }
        val (coffees, charges) = orders.unzip()
        return Pair(coffees, charges.coalesce())
    }
}

fun main() {
    print(Cafe().buyCoffees(CreditCard(), 10, 1))
}