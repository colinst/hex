---
title: 设计模式233|Design pattern
copyright: true  

tags: 
    - base 
    - architect  
    - Design 
    - pattern 
    
categories: 
    - Base 
    
date: 2018-08-028 13:00:00
---

当朝大学士，总共有3位，朕不得不罢免4位 
别看你们一个个站在干岸上，设计模式你们还记得多少吗--  
<!-- more -->

# 简单工厂模式|Simple factory

## 面向对象

### 活字印刷
例子++

1.可维护；【只改需改之字】  
2.可复用；  
3.可扩展；【可加字】  
4.灵活性；【横排换竖排】  

### 好处
通过封装、继承、多态将程序的耦合性降低  
设计模式使得其更加灵活，容易修改且易于复用  

### 业务封装
火星车程序的分离  
将客户端和服务端分离  

题目：  
一队机器人漫游车将被美国宇航局降落在火星高原上。
漫游车将在这个奇怪的长方形高原上巡游，
以便他们的机载摄像头可以获得周围地形的完整视图，并将其发送回地球。
漫游者的坐标和位置由x和y坐标的组合以及代表四个方向（E, S, W, N）的字母表示。
高原划分为网格以简化导航。比如位置0，0，N，表示漫游车位于左下角并面向北。
为了控制漫游车，美国宇航局发送一串简单的字母。
指令字母是'L'，'R'和'M'。 'L'和'R'使漫游车分别向左或向右旋转90度，而不会从当前地点移动。
'M'表示前进一个网格点，并保持相同的方向。
假设从（x，y）直接向北移动，就到了（x，y + 1）。

INPUT：  
第一行输入是平台的右上角坐标，左下角坐标被假定为0,0。
其余的输入是有关已部署的漫游车的信息。每个漫游车都有两行输入。
第一行给出了漫游车的位置，第二行是告诉漫游车如何探索高原的一系列指令。
位置由两个整数和一个由空格分隔的字母组成，对应于x和y坐标以及漫游车当前的方向。
每个漫游车将按顺序完成，这意味着第二个漫游车在第一个漫游车完成移动之前不会开始移动。

OUTPUT:  
每个漫游车的输出应该是其最终的坐标和位置。
输入输出例子:  
IN:  
5 5  
1 2 N  
LMLMLMLMM  
3 3 E  
MMRMMRMRRM  

OUT：  
1 3 N  
5 1 E  

#### 服务端封装
```
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
```
#### 操作界面封装
```
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
```
### 松紧耦合
紧耦合：  
Rover对象的L,R,M方法写在一起，需求改变时需要需直接改变方法  
松耦合：  
解耦函数架构，将每个方法抽象成功能类


































