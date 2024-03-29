## 抽象类和接口区别

### 相同点

* 两者都不能实例化

* 都可以有抽象方法

### 不同点

区别|抽象类|接口
---|---|---
1.定义|abstract|interface/abstract interface(只能为public或默认)
2.属性|常量、可以为成员变量(静态/类变量、成员变量(private变量最好不用，无法继承导致无法重写))|只能有常量(静态常量成员)
3.方法|可以有抽象方法、普通方法(必须有方法体)|1.8前只能有抽象方法，之后可以有静态方法和默认方法
4.方法权限|1.8前默认protected，1.8默认default，不能为private权限|1.8前必须public，1.8可为default,1.9可为private
5.构造方法|可以有构造方法|不能有构造方法
6.实现|类与类关系只能为单继承，抽象类可继承接口|接口间关系为继承，且接口可多继承接口；一个类可实现多接口
7.业务|抽象方法在业务上像一个模板，有自己功能，同时可以有优化补充的多种形式|像一种规范和要求，实现类要按照要求来进行实现
8.默认|-|接口默认属性：public static final,默认方法：public abstract

### 子类继承问题

* 子类从其父类继承所有成员(字段、方法和嵌套类)

    包括final、private等私有方法，但是不能直接调用这些私有方法

* 构造器、静态初始化块、实例初始化块不能继承

* 构造函数不是成员，所以不被子类继承，但是可以从子类调用父类的构造函数