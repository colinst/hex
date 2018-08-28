package io.hongxi.simpleFactory.encapsulation

class Rover constructor(var x:Long, var y:Long, var z:String){

    val rangez= arrayListOf("e","s","w","n")
    val rangeZ= arrayListOf("E","S","W","N")

    fun numL(z:Int)=if (z==0) 3 else z-1
    fun numR(z:Int)=if (z==3) 0 else z+1

    fun doL(){
        val i1=rangeZ.indexOf(z)
        val i2=rangez.indexOf(z)
        val i= intArrayOf(i1,i2).max()
        val ii=numL(i!!)
        z=rangeZ[ii]
    }

    fun doR(){
        val i1=rangeZ.indexOf(z)
        val i2=rangez.indexOf(z)
        val i= intArrayOf(i1,i2).max()
        val ii=numR(i!!)
        z=rangeZ[ii]
    }

    fun doM(){
        when(z){
            "E" ->  x++
            "S" ->  y--
            "W" ->  x--
            "N" ->  y++
        }
    }
}