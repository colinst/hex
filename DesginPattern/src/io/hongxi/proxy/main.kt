package io.hongxi.proxy

fun main(args: Array<String>) {

    val azhen=Girl("阿珍")
    val daili=Proxy(azhen)

    daili.GiveDolls()
    daili.GiveFlowers()
    daili.GiveChocolate()
}