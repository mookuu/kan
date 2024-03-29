package logic.generic.wildcard;

import logic.generic.GenericTypeCommon;
import logic.occupation.Employee;
import logic.occupation.Executive;
import logic.occupation.Manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * 通配符的超类型限定:类似消费型函数式接口(只提供，写入泛型对象)Consumer，无返回值
 *   ->超类限定： 对超类进行限定， ？ super表示该通配符类型为超类
 *   ->GenericTypeCommon<? super XX>的继承关系跟GenericTypeCommon<? extends XX>相反
 *  对于类型参数传递：
 *    a. 泛型继承角度来看，只能传递本类型对象或子类型对象，如GenericTypeCommon<Employee>、GenericTypeCommon<Object>
 *    b. 从参数化类型角度来看，只能传递本类或超类对象，如参数化类型Manager的超类Employee、Object对象
 * 与通配符的子类型限定相反：
 *   a. 安全的更改器方法：更改时只能传递类型参数类型或其子类型，如Manager或Executive；与类型参数传递相反
 *   b. 不安全的访问器方法：不能保证返回对象类型，只能赋给Object
 * 用法：
 *   a. 安全的更改器
 *   b. 解除泛型类型参数化对象之间无关联限制，对泛型接口的绑定进行支持
 *   c. 对函数式接口类型进行支持
 */
public class WildcardSupperType {

    /**
     * 用法1: 类似消费型函数式接口(只提供，写入泛型对象)Consumer，无返回值
     * @param m
     * @param result
     */
    public static void minmaxBonus(Manager[] m, GenericTypeCommon<? super Manager> result) {
        if (m.length == 0) {
            return;
        }
        Manager min = m[0];
        Manager max = m[0];
        for (Manager n : m) {
            if (min.getBonus() > n.getBonus()) {
                min = n;
            }
            if (max.getBonus() < n.getBonus()) {
                max = n;
            }
        }

        // TODOOK:消费者接口类似，如何使用？
        //      -> result是一个引用
        result.setFirst(min);
        result.setSecond(max);
    }

    public static void main(String[] args) {
        Manager[] mm = {
                new Manager("m1", 1000, 11, 11, 11),
                new Manager("m2", 1000, 11, 11, 11)
        };
        mm[0].setBonus(1000);
        mm[1].setBonus(2000);

        /**
         * 安全的更改器方法测试
         */
        Employee e1 = new Employee("employee1");
        Employee e2 = new Employee("employee2");
        Manager m1 = new Manager("m2");
        Manager m2 = new Manager("m2");
        Executive ex1 = new Executive("ex2");
        Executive ex2 = new Executive("ex2");
        GenericTypeCommon<Employee> gtEmployee = new GenericTypeCommon<>(e1, e2);
        GenericTypeCommon<Manager> gtManager = new GenericTypeCommon<>(m1, m2);
        GenericTypeCommon<Executive> gtExecutive = new GenericTypeCommon<>(ex1, ex2);
        // 传递超类型
        GenericTypeCommon<? super Manager> gtManager2 = gtEmployee;
        // 传递本类型(无意义，必然成功)
        GenericTypeCommon<? super Manager> gtManager3 = gtManager;
        // 传递子类型：编译错误
//        GenericTypeCommon<? super Manager> gtManager2 = gtExecutive;
//        minmaxBonus(mm, gtExecutive);
        // 传递超类型
        // 用法1: 类似消费型函数式接口(只提供，写入泛型对象)Consumer，无返回值
        minmaxBonus(mm, gtEmployee);

        // 安全的更改器： --> 和通配符类型子类型配合使用，一个更改，一个获取？？？（都是对参数化类型子类型进行操作）
        //   编译器无法知道set方法的具体类型，因此不能接收参数化类型的超类型，只能接收参数化类型或其子类型对象
        //   1. 设置参数化类型的超类型对象：NG
//        gtManager2.setFirst(e1);
        //   2. 设置参数化类型本类型对象：OK
        gtManager2.setFirst(m1);
        //   3. 设置参数化类型的子类型对象：OK
        gtManager2.setFirst(ex1);
        // 不安全的访问器：不能保证返回对象类型 -> 以下编译错误
//        Employee e3 = gtManager2.getFirst();
//        Manager m3 = gtManager2.getFirst();
//        Executive ex3 = gtManager2.getFirst();

        /**
         * 用法2：解除泛型类型参数化对象之间无关联限制，对泛型接口的绑定进行支持
         *
         * 绑定（绑定为什么不用关键字implements相关，绑定更贴近子类的概念）extends Comparable<T>接口时：
         *   表示类型变量T实现了Comparable接口，所以相当于T直接重写(Override)Comparable的compareTo方法，而不是其超类实现该接口。
         *   如{@link Employee#compareTo(Employee)}
         * 使用类型参数限定<T>后，由于参数化类型LocalDate没有重写compareTo方法，
         * 而是其超类重写了该方法且参数化类型为ChronoLocalDate,即Comparable<ChronoLocalDate>
         * ***类型参数限定***(所谓限定,即将类型参数调用元限定为固定类型，如String，LocalDate)要求LocalDate类型的Comparable实现，
         * 即Comparable<LocalDate>，这将导致语法错误
         *   public static <T extends Comparable<T>> T min2(T[] a) {}
         * 需要通过通配符类型超类型限制消除该错误
         */
        LocalDate[] birthdays = {
                LocalDate.of(1906, 12, 9),
                LocalDate.of(1815, 12, 10),
                LocalDate.of(1910, 6, 22),
        };
        LocalDate localDateMin = GenericTypeCommon.min2(birthdays);
        System.out.println("min测试:LocalDate min=" + localDateMin);

        /**
         * 用法3：对函数式接口类型进行支持
         */
        ArrayList<Employee> staff = new ArrayList<>(){{
            add(new Employee("employee1"));
            add(new Employee("employee2"));
            add(new Employee("employee3"));
        }};
        // 断言函数式接口:删除奇数散列码的员工
        Predicate<Object> oddHashCode = obj -> obj.hashCode() %2 != 0;
        // 方法签名：removeIf(Predicate<? super E>)
        //   理解：removeIf方法中的参数化类型(此处定义为泛型类型参数)必须和调用元staff的参数化类型(Employee)一致?描述方法需调整，谁调用谁负责？
        //        遵循通配符限定超类型限定参数传递规则：对于类型参数传递：从参数化类型角度来看，只能传递本类或超类对象
        //        类似：Employee数组的staff[0].compareTo(staff[1])
        staff.removeIf(oddHashCode);
        System.out.println("staff:");
        staff.forEach(x -> System.out.println(x.toString() + ":"+ x.hashCode()));

        // 断言函数式接口:删除偶数散列码的员工
        Predicate<Employee> evenHashCode = obj -> obj.hashCode() %2 == 0;
        // 不遵循通配符限定超类型限定参数传递规则：传递子类型对象(子类型对象只用于更改器方法)
        Predicate<Manager> evenHashCode2 = obj -> obj.hashCode() %2 == 0;
        // TODO: ArrayList clone
        ArrayList<Employee> staff2 = new ArrayList<>(){{
            add(new Employee("employee4"));
            add(new Employee("employee5"));
            add(new Employee("employee6"));
        }};
        staff2.removeIf(evenHashCode);
        // 通配符超类型限定：只能传递本类或超类
//        staff2.removeIf(evenHashCode2);
        System.out.println("staff2:");
        staff2.forEach(x -> System.out.println(x + ":"+ x.hashCode()));
    }
}
