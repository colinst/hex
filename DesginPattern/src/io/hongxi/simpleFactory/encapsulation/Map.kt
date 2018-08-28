package io.hongxi.simpleFactory.encapsulation

import java.util.*

val Rovers= MutableList(0,{ Rover(1, 1, "N") })
val sc = Scanner(System.`in`)

var boderX=0L
var boderY=0L
val rangeZ= arrayListOf("E","S","W","N")

fun InputSize(){
    println("please input map size:'x y'")
    val mapSize = sc.nextLine()
    WashSize(mapSize)
}
fun RoverSet(){
    InputLocation()
    InputCommand()
}
fun InputLocation(){
    println("please input io.hongxi.simpleFactory.encapsulation.Rover's location:'x y z',example:'1 1 N','2 2 W'..  z in ['E','S','W','N']")
    val location = sc.nextLine()
    WashLocation(location)
}
fun InputCommand(){
    println("please input command,example:'LMLMLMLMM'..  char in ['L','R','M']")
    val command = sc.nextLine()
    for (cmd in command)
        when(cmd){
            'l' -> Rovers.last().doL()
            'L' -> Rovers.last().doL()
            'r' -> Rovers.last().doR()
            'R' -> Rovers.last().doR()
            'm' -> Rovers.last().doM()
            'M' -> Rovers.last().doM()
        }

    println("enter to add rovers more,else input 'end' to end")
    val cmd = sc.nextLine()
    if (cmd=="END"|| cmd=="end"){
        doResult()
        return
    }else
        RoverSet()
}

fun WashSize(mapSize:String){

    val list=mapSize.split(' ').toMutableList()
    list.removeAll(Array(99,{""}))

    if (list.size!=2){
        System.err.println("wrong input,please input again:'x y'")
        InputSize()
        return
    }
    try {
        boderX =list[0].toLong()
        boderY =list[1].toLong()
    } catch (e:Exception){
        System.err.println("wrong input,please input again:'x y'")
        InputSize()
        return
    }
    if (boderX <1 || boderY <1){
        System.err.println("wrong input,please input again:'x y'")
        InputSize()
        return
    }

    println("mapSize=$list")
    RoverSet()
}
fun WashLocation(location:String){
    val list=location.split(' ').toMutableList()
    list.removeAll(Array(99,{""}))

    val x:Long
    val y:Long
    val z:String
    if (list.size!=3){
        System.err.println("wrong input,please input again:'x y z',example:'1 1 N','2 2 W'..  z in ['E','S','W','N']")
        InputLocation()
        return
    }
    try {
        x=list[0].toLong()
        y=list[1].toLong()
        z=list[2]
    } catch (e:Exception){
        System.err.println("wrong input,please input again:'x y z',example:'1 1 N','2 2 W'..  z in ['E','S','W','N']")
        InputLocation()
        return
    }
    if (x !in 0..boderX || y !in 0..boderY || z !in rangeZ){
        System.err.println("wrong input,please make x in 0..$boderX, y in 0..$boderY and z in ['E','S','W','N']")
        InputLocation()
        return
    }

    Rovers.add(Rover(x, y, z))
}

fun doResult(){
    for (rover in Rovers)
        println("${rover.x} ${rover.y} ${rover.z}")
}


fun main(args: Array<String>) {
    InputSize()
    /**
    Input
    5 5
    1 2 N
    LMLMLMLMM
    3 3 E
    MMRMMRMRRM
    OutPut：
    1 3 N
    5 1 E*/
}

