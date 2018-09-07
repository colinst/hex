package io.hongxi.Builder

class Director(builder:Builder) {

    fun Construct(builder:Builder){
        builder.BuildPartA()
        builder.BuildPartB()
    }
}