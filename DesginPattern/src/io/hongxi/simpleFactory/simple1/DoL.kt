package io.hongxi.simpleFactory.simple1

import io.hongxi.simpleFactory.encapsulation.Rover

data class DoL(override var x:Int,
                override var y:Int,
                override var z:Char) :Do{

    override fun numL(z:Int)=if (z==0) 3 else z-1
    override fun numR(z:Int)=if (z==3) 0 else z+1

    override val rangeZ=arrayListOf('E','S','W','N')
    override fun DoResult(): Rover {

        val i=rangeZ.indexOf(z)
        val ii=numL(i)
        z=rangeZ[ii]
        return Rover(x,y,z)
    }
}