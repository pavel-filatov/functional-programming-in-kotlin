package ch02

fun factorial(n: Int): Int {
    tailrec fun go(n: Int, acc: Int): Int =
        if (n <= 1) acc
        else go(n - 1, acc * n)

    return go(n, 1)
}

// Exercise 2.1
fun fib(n: Int): Int {
    tailrec fun go(n: Int, current: Int, last: Int): Int =
        if (n <= 0) last
        else go(n - 1, current + last, current)

    return go(n, 1, 0)
}


fun formatResult(name: String, n: Int, f: (Int) -> Int): String {
    val msg = "The %s of %d is %d."
    return msg.format(name, n, f(n))
}


fun findFirstString(ss: Array<String>, key: String): Int {
    tailrec fun loop(n: Int): Int {
        return when {
            n >= ss.size -> -1
            ss[n] == key -> n
            else -> loop(n + 1)
        }
    }
    return loop(0)
}

fun <A> findFirst(xs: Array<A>, p: (A) -> Boolean): Int {
    tailrec fun loop(n: Int): Int =
        when {
            n >= xs.size -> -1
            p(xs[n]) -> n
            else -> loop(n + 1)
        }
    return loop(0)
}


// Exercise 2.2
val <T> List<T>.tail: List<T>
    get() = drop(1)

val <T> List<T>.head: T
    get() = first()

fun <A> isSorted(aa: List<A>, order: (A, A) -> Boolean): Boolean {
    tailrec fun loop(aaa: List<A>): Boolean =
        if (aaa.size <= 1) true
        else if (!order(aaa.head, aaa.tail.head)) false
        else loop(aaa.tail)

    return loop(aa)
}


fun Int.show() = "The value of A is $this"


fun <A, B, C> partial1(a: A, f: (A, B) -> C): (B) -> C = { b -> f(a, b) }

// Exercise 2.3
fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C = { a -> { b -> f(a, b) } }

// Exercise 2.4
fun <A, B, C> uncurry(f: (A) -> (B) -> C): (A, B) -> C = { a, b -> f(a)(b) }

// Exercise 2.5
fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C = { a -> f(g(a)) }


fun main() {
    (0..10).forEach { println("$it: ${fib(it)}") }

    println(formatResult("factorial", 10, ::factorial))
    println(formatResult("fibonacci", 10, ::fib))
    println(formatResult("absolute", -13) { if (it <= 0) -it else it })

    val ss = arrayOf("first", "second", "third", "fourth")
    println(findFirstString(ss, "first"))
    println(findFirstString(ss, "second"))
    println(findFirstString(ss, "fourth"))
    println(findFirstString(ss, "fifth"))

    val xx = arrayOf(1, 2, 3, 4)
    println(findFirst(ss, { s -> s == "first" }))
    println(findFirst(ss, { s -> s in arrayOf("fifth", "fourth") }))
    println(findFirst(xx, { s -> s == 1 }))
    println(findFirst(xx, { s -> s in arrayOf(6, 5, 2) }))

    println(isSorted(xx.toList(), { a, b -> a >= b }))
    println(isSorted(xx.toList(), { a, b -> a <= b }))


    println(1.show())

    val fibPlus2 = partial1(2, { a, b: Int -> a + fib(b) })
    println(fibPlus2(1))
    println(fibPlus2(3))
    println(fibPlus2(5))
}
