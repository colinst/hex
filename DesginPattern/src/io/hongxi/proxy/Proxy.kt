package io.hongxi.proxy

class Proxy(girl: Girl):GiveGift {
    val gg=Pursuit(girl)
    override fun GiveDolls()=gg.GiveDolls()
    override fun GiveFlowers() =gg.GiveFlowers()
    override fun GiveChocolate()=gg.GiveChocolate()
}