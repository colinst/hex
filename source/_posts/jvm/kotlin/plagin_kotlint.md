---
title: kotlin隐性优化 Kotlin's hidden costs

tags: 
    - cost  
    - kotlin  
    - 优化  
    
categories: 
    - jvm   
    - kotlin  
    
date: 2018-08-008 13:00:00
---


越来越多的Java后端逐渐转向kotlin,因其有诸多方便的特性：
空指针安全、方法扩展、函数式编程、大量语法糖等。
我们初步使用时会使得我们的代码信雅达，有很高的可读和可维护性。
但实际工程环境中使用我们会发现kotlin中有不少的隐形开销，我们需要避免这些隐形开销。
<!-- more -->

## Kotlin隐性开销 

### 局部函数 Local functions
有时候我们会用常规语法在别的函数中声明函数，局部内部函数，我们这里叫他局部函数(local function)：
    
    fun someMath(a: Int): Int {
    
        fun sumSquare(b: Int) = (a + b) * (a + b)
        return sumSquare(1) + sumSquare(2)
    }
首先我们要知道，局部函数无法声明为内联函数(inline)，
同时包含该局部函数的主函数也不能声明为内联函数。这样我们就不可避免多一次函数调用。  

在编译之后，局部函数会被转换为Fuction对象，同lambda一样。
这样的话就会有很多非内联函数的限制，我们看到编译后对应的Java代码：
    
    public static final int someMath(final int a) {
       Function1 sumSquare$ = new Function1(1) {
          // $FF: synthetic method
          // $FF: bridge method
          public Object invoke(Object var1) {
             return Integer.valueOf(this.invoke(((Number)var1).intValue()));
          }
    
          public final int invoke(int b) {
             return (a + b) * (a + b);
          }
       };
       return sumSquare$.invoke(1) + sumSquare$.invoke(2);
    }
其实与lambda相比，这里性能损失已经更少了，
局部函数的最终实例是由其所在函数直接调用的，
这样的调用避免了原始类型的强制转换或装箱处理。
我们可以看到字节码：  

    ALOAD 1
    ICONST_1
    INVOKEVIRTUAL be/myapplication/MyClassKt$someMath$1.invoke (I)I
    ALOAD 1
    ICONST_2
    INVOKEVIRTUAL be/myapplication/MyClassKt$someMath$1.invoke (I)I
    IADD
    IRETURN
两次的方法调用都是直接返回int,中间没有任何拆箱操作。  

不过Function在每次方法调用期间仍然要new对象,
我们这样重写可以避免这种情况：

    fun someMath（a：Int）：Int { 
        fun sumSquare（a：Int，b：Int）=（a + b）*（a + b）
    
        return sumSquare（a，1）+ sumSquare（a，2）
    }
这样我们中间依然不会有任何转换或装箱。
与私有函数相比，局部函数的唯一不足是在使用时会生成一个额外的类。

**本地函数是私有函数的替代函数，
具有能够访问外部函数的局部变量的优势。
但同时也伴随着外部函数的每次调用需要创建对象的隐藏成本，
因此使用本地函数时需要添加其主函数参数来优化。**

### 伴生对象 companion object 
Kotlin中没有静态成员，而是通过伴生对象来实现。我们会在伴生对象中声明常量，
可如果姿势不对，会产生额外开销。如以下version常量：
```
    class Demo {
    
        fun getVersion(): Int {
            return Version
        }
        companion object {
            private val Version = 1
        }
    }
```
如果将这一段转化成Java代码，则会复杂很多很多：
```
    public class Demo {
        private static final int Version = 1;
        public static final Demo.Companion Companion = new Demo.Companion();
    
        public final int getVersion() {
            return Companion.access$getVersion$p(Companion);
        }
    
        public static int access$getVersion$cp() {
            return Version;
        }
    
        public static final class Companion {
            private static int access$getVersion$p(Companion companion) {
                return companion.getVersion();
            }
    
            private int getVersion() {
                return Demo.access$getVersion$cp();
            }
        }
    }
```

如果在Java一般会直接读取常量，但上面这种写法会导致其经过下面4个方法拿到常量：  
+调用伴生对象的静态方法    
+调用伴生对象的实例方法    
+调用主类的静态方法    
+读取主类中的静态字段    

解决方案：  
1.基本类型和字符串，可以使用const关键字将常量声明为编译时常量。  
2.对于公共字段，可以使用@JvmField注解。  
3.对于其他类型的常量，最好在主类对象中存储全局常量而不是伴生对象。   


### Lazy()委托属性  lazy()'s Attr
lazy()委托属性可以用于只读属性的惰性加载，
但是在使用lazy()时经常被忽视的地方就是有一个可选的model参数:  
+LazyThreadSafetyMode.SYNCHRONIZED：
    初始化属性时会有双重锁检查，保证该值只在一个线程中计算，并且所有线程会得到相同的值。  
+LazyThreadSafetyMode.PUBLICATION：
    多个线程会同时执行，初始化属性的函数会被多次调用，但是只有第一个返回的值被当做委托属性的值。  
+LazyThreadSafetyMode.NONE：没有双重锁检查，不应该用在多线程下。  

lazy()默认情况下会指定LazyThreadSafetyMode.SYNCHRONIZED，
    这可能会造成不必要线程安全的开销，应该根据实际情况，指定合适的model来避免不需要的同步锁。

### 基本数组 baseArr
在Kotlin中有3种数组类型：  

+IntArray，FloatArray，其他：基本类型数组，被编译成int[]，float[]，其他   
+Array<T>：非空对象数组  
+Array<T?>：可空对象数组  
 我们使用这三种来声明数组，会发现区别：
    
    val a: IntArray = intArrayOf(1)
    val b: Array<Int> = arrayOf(1)
    val c: Array<Int?>=arrayOf(null)
同等Java代码为：
    
    @NotNull
    private final int[] a = new int[]{1};
    @NotNull
    private final Integer[] b = new Integer[]{Integer.valueOf(1)};
    @NotNull
    private final Integer[] c = new Integer[]{(Integer)null};
我们可以看到后两种数组创建时已经进行了自动装箱处理，我们声明非空基本数组时，只使用xxxArray就好，避免自动装箱。

### for循环函数
我们在kotlin的for循环中使用downTo、step、until、reversed等函数辅助简单的使用for循环。
我们单独使用时确实简洁高效，但是相互调用呢？
    
    for (i in 10 downTo 1 step 2) {
                    println(i)
    }
等同的Java代码
    
    public final void loop(){
        IntProgression var10000 = RangesKit.step(RangesKt,downTo(10,1));
        int i=var10000.getFirst();
        int var2=var10000.getLast();
        int var3=var10000.getStep();
        if(var3>0){
            if(i>var2){ return; }
        } else if( i < var2){
            return;
        }
        
        while(i != var2){
            i+= var3;
        }
    }
    
    我们看到第二行已经多创建了两个IntProgresion 临时对象，增了了消耗。

### Kotlin 检查工具

工具简介：  
>[ktlint](https://github.com/shyiko/ktlint):检查kotlin代码风格，要使用需要大量改造。  
 [detekt](https://github.com/arturbosch/detekt):静态分析kotlin代码，但也有缺陷（不能指定 [variant//变种] 检查,只支持控制台输出）。  
 lint //AndroidStuido 自带，可自定义检查规则



























