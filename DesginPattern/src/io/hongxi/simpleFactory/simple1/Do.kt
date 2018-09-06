package io.hongxi.simpleFactory.simple1

import io.hongxi.simpleFactory.encapsulation.Rover

interface Do{

    var x:Int
    var y:Int
    var z:Char

    fun numL(z:Int):Int
    fun numR(z:Int):Int

    val rangeZ:List<Char>
    fun DoResult(): Rover
}