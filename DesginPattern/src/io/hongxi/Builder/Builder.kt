package io.hongxi.Builder

abstract class Builder {
    abstract fun BuildPartA()
    abstract fun BuildPartB()
    abstract fun GetResult():Product
}