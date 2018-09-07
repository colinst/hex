package io.hongxi.Builder

class ConcreteBuilder1:Builder() {
    val product=Product()

    override fun BuildPartA(){
        product.Add("partX")
    }

    override fun BuildPartB() {
        product.Add("partY")
    }

    override fun GetResult()=product
}