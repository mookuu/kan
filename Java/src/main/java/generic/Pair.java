package generic;

/**
 * @description: 泛型可看作普通类的工厂
 * Java中类型变量：
 * E：表示集合的元素类型
 * K，V：表示表的关键字与值的类型
 * T/U/S：表示任意类型
 *
 * E - Element (used extensively by the Java Collections Framework)
 * K - Key
 * N - Number
 * T - Type
 * V - Value
 * S,U,V etc. - 2nd, 3rd, 4th types
 * 泛型特点：
 * 1.虚拟机中没有泛型，只有普通的类和方法，即原始类型
 * 2.所有的类型参数都用它们的限定类型替换
 * 3.桥方法被合成来保持多态
 * 4.为保持类型安全性，必要时插入强制类型转换
 * 泛型用处：
 * 主要目标是允许泛型代码和遗留代码之间能够互操作
 * 约束与局限性：
 * 1. 不能用基本类型实例化类型参数(泛型类型变量)
 * 2. 运行时类型检查只适用于原始类型
 * 3. 不能创建参数化(此处String)类型的数组，即不支持泛型类型的数组
 * 4. 泛型与可变参数，消除泛型数组限制(上述3)，有风险。
 * -> 泛型数组：不安全，包括以下方式消除泛型数组限制
 * a. 声明通配类型数组，然后进行强制类型转换
 * Pair<String>[] table = (Pair<String>[]) new Pair<?>[10];
 * table[0] = new Pair<String>("1","2");
 * table[1] = new Pair<String>("3","4");
 * Object[] objArray = table;
 * // 数组存储只会检查擦除的类型，Pair<String>[] 擦除类型后为Pair[]，可转换为Object[]
 * objArray[0] = new Pair<Employee>();  // --> 类型不匹配也能存入
 * b. 通过可变参数与泛型结合
 * Pair<String> pair1 = new Pair<String>("a", "b");
 * Pair<String> pair2 = new Pair<String>("c", "d");
 * // {@link generic.test.PairTest3#array(Object[])})}
 * Pair<String>[] table2 = array(pair1,pair2);
 * Object[] objArray2 = table2;
 * // 数组存储只会检查擦除的类型，Pair<String>[] 擦除类型后为Pair[]，可转换为Object[]
 * objArray2[0] = new Pair<Employee>(); // --> 类型不匹配也能存入
 * -> 泛型数组列表：安全
 * a. 通过ArrayList<Pair<String>创建范类型集合/容器
 * Collection<Pair<String> coll = new ArrayList<Pair<String>;
 * Pair<String> pair1 = new Pair<String>("a", "b");
 * coll.add(pair1);
 * table2.add(pair3);   // --> 类型不匹配编译错误
 * 5. 不能实例化类型变量/不能实例化泛型<T>实例，即不能用new T(...)
 * 6. 不能实例化泛型<T>数组
 * -> 可实例化泛型类型的数组，即泛型类型确定化，与新建对象如new Employee类似。
 * --> 消除泛型数组限制(上述6)
 * a. 实例化泛型数组实例：通过函数式接口
 * b. 实例化泛型数组实例：通过反射
 * 7. 泛型类中的类型变量在静态上下文中无效，即不能在静态变量或者静态方法中引用类型变量（静态方法中的参数可以为类型变量）
 * 8. 不能抛出或捕获泛型类的实例，泛型类也不能扩展Throwable
 * 9：消除对受查异常的检查，即让编译器认为是一个非受查异常
 *      {@link exception.ExceptionWithGeneric}
 * 10：注意擦除后的冲突
 *      {@link generic.Pair#equals2(Object)}}
 *
 * @author: Kan
 * @date: 2021/3/10 0:24
 */
public class Pair<T> {
    private T first;
    private T second;

    public Pair() {
        first = null;
        second = null;
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public void setFirst(T newValue) {
        first = newValue;
    }

    public void setSecond(T newValue) {
        second = newValue;
    }

    /**
     * Pair<T>的equals(T)方法在类型擦除后和Object的equals(Object)方法发生冲突（因为没有重写）
     * 重写时，equals方法签名的参数类型应该与被重写方法(此处Object的equals)的类型(Object类型)一致
     * 以下为Object equals 方法原型
     * public boolean equals(Object obj) {
     * return (this == obj);
     * }
     */
//    boolean equals(T value) {
//        return first.equals(value) && second.equals(value);
//    }
    public boolean equals2(T value) {
        return first.equals(value) && second.equals(value);
    }

    /**
     * extends表示T应该是绑定类型Comparable的子类型
     * T和绑定类型可以是接口，也可以是类(即该接口/类必须实现了Comparable接口的类)
     *  -> 加入通配符的超类型限定后：该接口/类或其 **超类** 必须实现了Comparable接口的类
     * （此处不用implements）
     * T：类型变量，通配符
     * T可有多个限定/绑定
     * -> T extends Comparable & Serializable
     * -> 上记限定可以有多个接口，但类限定只能有一个。并且类限定必须作为限定列表中的第一个
     * -> 为提高效率，应该将标记接口(没有方法的接口，如Serializable)放在边界列表的末尾
     *
     * @param a
     * @param <T>
     * @return
     */
    public static <T extends Comparable> Pair<T> minmax(T[] a) {
        if (a == null || a.length == 0) {
            return null;
        }
        T min = a[0];
        T max = a[0];
        for (T s : a) {
            if (min.compareTo(s) > 0) {
                min = s;
            }
            if (max.compareTo(s) < 0) {
                max = s;
            }
        }
        return new Pair<>(min, max);
    }

    /**
     * 自定义泛型方法
     * <T>整体：类型参数
     * <T>中的T：类型变量，放在访问修饰符后，返回类型之前
     * 调用泛型方法时，方法名前的尖括号中放入具体类型
     * 如：Pair.<String>getMiddle("john","abc");
     * 如：Pair.<double>getMiddle(3.14,172,0);
     *
     * @param a
     * @param <T>
     * @return
     */
    public static <T> T getMiddle(T... a) {
        return a[a.length / 2];
    }

    /**
     * 泛型类型变量与返回值类型不一致
     * J2SE1.5泛型机制约束：不能拿用标识符<T></>来代表的类型来创建这一类型（可变参数）的实例
     * J2SE1.8之前该函数会报generic array creation错误
     *
     * @param args
     * @param <T>
     */
    public static <T> void voidReturnType(T... args) {
        System.out.println("泛型类型变量与返回类型不一致，返回类型：void");
    }

    /**
     * 对类型变量T设置限定（bound）:原始类型绑定(extends Comparable)，有风险
     * T和绑定类型可以是接口，也可以是类(即该接口/类必须实现了Comparable接口的类)
     *  -> 加入通配符的超类型限定后：该接口/类或其 **超类** 必须实现了Comparable接口的类
     *
     *  public static <T extends Comparable> T min(T[] a) {}
     *   -> 只有实现了Comparable接口的类(如String，LocalDate(超类ChronoLocalDate实现)等)才能调用该方法
     *
     * @param a
     * @param <T>
     * @return
     */
    public static <T extends Comparable> T min(T[] a) {
        if (a == null || a.length == 0) {
            return null;
        }
        T smallest = a[0];
        for (T t : a) {
            if (smallest.compareTo(t) > 0) {
                smallest = t;
            }
        }

        return smallest;
    }

    /**
     * 对类型变量T设置限定（bound）:类型参数绑定(T extends Comparable<T>或T extends Comparable<? extends T>)，有风险
     * T和绑定类型可以是接口，也可以是类(即该接口/类必须实现了Comparable接口的类)
     *  -> 加入通配符的超类型限定后：该接口/类或其 **超类** 必须实现了Comparable接口的类
     *
     *  public static <T extends Comparable> T min(T[] a) {}
     *   -> 只有实现了Comparable接口的类(如String，LocalDate(超类ChronoLocalDate实现)等)才能调用该方法
     *
     * @param a
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> T min2(T[] a) {
        if (a == null || a.length == 0) {
            return null;
        }
        T smallest = a[0];
        for (T t : a) {
            if (smallest.compareTo(t) > 0) {
                smallest = t;
            }
        }

        return smallest;
    }
}
