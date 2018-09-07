package io.hongxi.Builder

fun main(args: Array<String>) {

    val builder=ConcreteBuilder0()
    val builder1=ConcreteBuilder1()
    val director=Director(builder)

    director.Construct(builder)
    val p0=builder.GetResult()
    p0.Show()

    director.Construct(builder1)
    val p1=builder.GetResult()
    p1.Show()
}