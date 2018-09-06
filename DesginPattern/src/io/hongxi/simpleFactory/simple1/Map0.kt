package io.hongxi.simpleFactory.simple1

import io.hongxi.simpleFactory.encapsulation.Rover


var rover=Rover(3,3,'E')
val command="MMRMMRMRRM"



fun main(args: Array<String>) {

    for ( cmd in command){

        val doo=OperationFactory.createOperate(rover,cmd)
        rover=doo.DoResult()
    }

    println("x=${rover.x} y=${rover.y} z=${rover.z}")
}
