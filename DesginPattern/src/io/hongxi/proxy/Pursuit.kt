package io.hongxi.proxy

class Pursuit(val girls: Girl):GiveGift {
    override fun GiveDolls() = println("${girls.name}  送你洋娃娃")
    override fun GiveFlowers()= println("${girls.name}  送你洋鲜花")
    override fun GiveChocolate() = println("${girls.name}  送你巧克力")
}