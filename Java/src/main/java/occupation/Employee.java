package occupation;


import abstclass.Person;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

/**
 * {@code Employee} display staff info
 *
 * @author kan
 * @version 1.0
 */
public class Employee extends Person implements Comparable<Employee> {
    // 静态域：所有对象共有
    private static int nextId = 1;
    // 实例域
    private int id;

    /**
     * instance field initialization
     */
    private double salary;
    // 不可变类：没有方法能修改对象的状态（对象初始化可以修改，即new对象过程）
    private LocalDate hireDay = LocalDate.now();

    // static initialization block
    static {
        Random generator = new Random();
        /* set nextId to a random number between 0 to 10000 */
        nextId = generator.nextInt(10000);
    }

    // object initialization block: 初始化块
    {
        id = nextId;
        nextId++;
    }

    /**
     * overloading constructor
     *
     * @param salary salary
     * @param year   year
     * @param month  month
     * @param day    day
     */
    public Employee(String name, double salary, int year, int month, int day) {
        super(name);
        this.salary = salary;
        this.hireDay = LocalDate.of(year, month, day);
    }

    /**
     * overloading constructor
     *
     * @param name   name
     * @param salary salary
     */
    public Employee(String name, double salary) {
        super(name);
        this.salary = salary;
    }

    /**
     * overloading constructor
     *
     * @param salary salary
     */
    public Employee(double salary) {
        // calls the Employee(String, double) constructor
        this("Employee #" + nextId, salary);
    }

    /**
     * overloading constructor
     *
     * @param name
     */
    public Employee(String name) {
        super(name);
    }

    /**
     * overloading constructor
     */
    public Employee() {
        super("");
        // name initialized to ""
        // salary not explicitly set--initialized to 0
        // id initialized in initialization block
    }

    @Override
    public String getDescription() {
        return String.format("an employee with a salary of $%.2f", salary);
    }

    /**
     * get id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * get salary
     *
     * @return salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * get hire day
     *
     * @return hire day
     */
    public LocalDate getHireDay() {
        return hireDay;
    }

    /**
     * calculate salary raise by percent
     *
     * @param byPercent bypercent
     */
    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    /**
     * final:子类不能扩展
     *
     *  @Override 在重写抽象方法/接口方法中不是必要的：有助于IDE检查错误；标记重载，有助于了解代码层次结构
     */
    @Override
    public final int compareTo(Employee other) {
        return Double.compare(salary, other.getSalary());
    }

//    @Override
//    public boolean equals(Object otherObject) {
//        // 引用相同对象
//        if (this == otherObject) {
//            return true;
//        }
//
//        // 显示参数为null
//        if (otherObject == null) {
//            return false;
//        }
//
//        // 比较对象是否为属于同一类
//        if (this.getClass() != otherObject.getClass()) {
//            return false;
//        }
//
//        // 强制类型转换
//        Employee other = (Employee) otherObject;
//
//        // equals实现1
//        // Objects.equals:任一对象为null则返回false
//        // TODO: 浮点类型数据相等判断
//        return Objects.equals(this.getName(), other.getName())
//                && salary == other.salary
//                && Objects.equals(hireDay, other.hireDay);
//
//        /**
//         * equals实现2
//        // field: name
//        if (this.getName() == null) {
//            if (other != null) {
//                return false;
//            }
//        } else if (!this.getName().equals(other.getName())) {
//            return false;
//        }
//
//        // field: salary
//        if (salary != other.salary) {
//            return false;
//        }
//
//        // field: hireDay
//        if (hireDay == null) {
//            if (other.hireDay != null) {
//                return false;
//            }
//        } else if (!hireDay.equals(other.hireDay)) {
//            return false;
//        }
//
//        return true;
//        */
//
//    }

//    @Override
//    public int hashCode() {
//        /*return 7 * Objects.hashCode(this.getName())
//                + 11 * Double.hashCode(salary)
//                + 13 * hireDay.hashCode();*/
//        return Objects.hash(this.getName(), salary, hireDay);
//    }

//    @Override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Double.compare(employee.salary, salary) == 0 && Objects.equals(hireDay, employee.hireDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, salary, hireDay);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name=" + super.getName() +
                ", id=" + id +
                ", salary=" + salary +
                ", hireDay=" + hireDay +
                '}';
    }
//    public String toString() {
//        return getClass().getSimpleName()
//                + "[name=" + this.getName()
//                + ",salary=" + salary
//                + ",hireDay=" + hireDay
//                + "]";
//    }
}
