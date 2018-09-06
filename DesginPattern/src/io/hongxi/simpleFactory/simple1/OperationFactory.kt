package io.hongxi.simpleFactory.simple1

import io.hongxi.simpleFactory.encapsulation.Rover

object OperationFactory{

    fun createOperate(rover: Rover, operator:Char): Do {

        when(operator){
            'L' ->  return DoL(rover.x,rover.y,rover.z)
            'R' ->  return DoR(rover.x,rover.y,rover.z)
            'M' ->  return DoM(rover.x,rover.y,rover.z)
            else ->  return DoM(rover.x,rover.y,rover.z)
        }
    }
}