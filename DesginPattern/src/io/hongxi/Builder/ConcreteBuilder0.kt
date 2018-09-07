package io.hongxi.Builder

class ConcreteBuilder0:Builder() {

    val product=Product()

    override fun BuildPartA(){
        product.Add("partA")
    }

    override fun BuildPartB() {
        product.Add("partB")
    }

    override fun GetResult()=product
}