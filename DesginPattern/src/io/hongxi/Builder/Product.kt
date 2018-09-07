package io.hongxi.Builder

class Product {
    val parts= MutableList<String>(0,{""})
    fun Add(part:String)=parts.add(part)
    fun Show(){
        println("产品创建")
        for (part in parts)
            print(part+" |")
    }
}