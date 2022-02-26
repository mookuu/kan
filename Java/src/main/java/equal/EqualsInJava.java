package equal;

/**
 *
 * ==：比较变量(存放于栈中)内存中存放的对象的(堆)内存地址，
 *     用于比较两个对象的地址是否相同，即是否指向同一个对象。比较的是真正意义上的指针操作。
 *     特点：1.比较操作符两端的操作数是否是同一对象
 *          2.两边操作数必须是同一类型(父子类之间亦可)才能通过编译
 *          3.比较的是地址，如果是具体的阿拉伯数字，值相等则为true
 *            如(int a = 10;
 *               long b = 10L;
 *               double c = 10.0)均相等(true)，均指向值为10的栈
 * equals：java.lang.Object类方法，用于比较两个对象内容是否相等。
 *         所有类均继承自java.lang.Object，因此equals适用于所有对象。
 *         如果对象未对equals方法进行覆写(如StringBuffer)，则仍然调用Object中equals方法，该方法返回的时==的判断结果。
 * 直接量：以String s = "abc";形式的赋值在java中叫直接量，存放在常量池中，而非new一样保存在堆中
 * 字符串拘留池(string interning pool)：公共语言运行库会自动维护一个名为“拘留池”(intern pool) 的表，
 *      它包含程序中以编程方式声明或创建的每个唯一的字符串的一个引用。因此，具有特定值的字符串的实例在系统中只有一个。
 * 字符串拘留：直接量形式的字符串在JVM内部发生字符串拘留(String intern)，
 *           即声明直接量字符串后，JVM先在常量池中寻找值相等的对象，如有，则将其赋予当前引用；
 *           如没有，则新建值。这种现象叫字符串拘留
 *           以直接量形式声明的字符串，只要值相等，则多个引用均指向同一对象。
 * @author moku
 */
public class EqualsInJava {

    public static void main(String[] args) {

        // 字符串拘留
        String a = "ab";
        String b = "ab";
        // new对象，c,d为不同引用，对象的内容一样
        String c = new String("ab");
        String d = new String("ab");
        StringBuffer sb = new StringBuffer("ab");
        StringBuffer sb2 = new StringBuffer("ab");

        // true
        System.out.println("a == b: " + (a == b));
        // false
        System.out.println("c == d: " + (c == d));
        // true
        // String类覆写equals方法，引用对象内容相等则为true
        System.out.println("c.equals(d): " + c.equals(d));
        int e2 = 42;
        // true
        System.out.println("42 == 42.0 :" + (42 == 42.0));
        // true: 匿名常量也存放于栈中
        System.out.println("e2 == 42 :" + (e2 == 42.0));
        // false
        // StringBuffer类未覆写equals方法
        System.out.println("sb.equals(sb2): " + sb.equals(sb2));

        // 基本类型只能用==进行比较，因为基本类型没有equals方法
        int e = 2;
        int f = 3;
        int g = 2;
        // false
        System.out.println("e == f: " + (e == f));
        // true：指向相同的栈空间(该空间存放2)
        System.out.println("e == f: " + (e == g));
        //
        Integer n1 = new Integer(30);
        Integer n2 = new Integer(30);
        Integer n3 = 30;
        Integer n4 = 30;
        // false
        System.out.println("n1 == n2: " + (n1 == n2));
        // true：Integer类覆写equals方法
        System.out.println("n1.equals(n2): " + n1.equals(n2));
        // false
        System.out.println("n1 == n3: " + (n1 == n3));
        // true：栈内存中指向同一空间
        System.out.println("n3 == n4: " + (n3 == n4));
        // true
        System.out.println("n1.equals(n3): " + n1.equals(n3));

        Value v1 = new Value();
        Value v2 = new Value();
        v1.i = v2.i = 100;
        // false:Value类未覆写Object类的equals方法，故比较的是两个对象的地址
        System.out.println("v1.equals(v2):" + v1.equals(v2));
        // false:==比较两个对象的地址
        System.out.println("v1 == v2:" + (v1 == v2));
    }
}

class Value
{
    int i;
}