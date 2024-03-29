## POM标签

* compile
  
    默认的scope，表示dependency都可以在生命周期中使用。而且，这些dependencies会传递到依赖的项目中。适用于所有阶段，会随着项目一起发布。

* provided

    跟compile相似，但是表明了dependency由JDK或者容器提供，例如Servlet API和一些Java EE APIs。这个scope只能作用在编译和测试时，同时没有传递性。

* runtime

    表示dependency不作用在编译时，但会作用在运行和测试时，如JDBC驱动，适用运行和测试阶段。

* test

    表示dependency作用在测试时，不作用在运行时。 只在测试时使用，用于编译和运行测试代码。不会随项目发布。
  
* system
  
    跟provided相似，但是在系统中要以外部JAR包的形式提供，maven不会在repository查找它。
  
* import
  
    * 这个标签就是引入该dependency的pom中定义的所有dependency定义

    * import标签值只能在dependencyManagement标签下使用！并且仅用于type为"pom"的dependency，其意义为引入该dependency的pom中定义的所有dependency定义
