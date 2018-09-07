package io.hongxi.Prototype

data class Model(

        val valInt :Int=0,
        var varInt: Int=1,

        val valString:String="",
        var varString:String="str"

):Cloneable{
    override fun clone()=Model(valInt,varInt,valString,varString)
}