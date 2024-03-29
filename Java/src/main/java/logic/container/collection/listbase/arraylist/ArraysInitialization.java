package logic.container.collection.listbase.arraylist;

import lombok.extern.slf4j.Slf4j;
import logic.occupation.Employee;

/**
 * @description: 数组初始化（分配堆内存空间，并为每个数组元素赋初始值）
 * 默认值
 *  基本数据类型整形(byte, short, int, long)：0
 *  基本数据类型浮点型(float,double)：0.0
 *  基本数据类型字符型(char)：'\u0000'空字符
 *  基本数据类型布尔型(boolean)：false
 *  引用类型(类，数组，接口，String)：null
 * @author: Kan
 * @date: 2021/3/1 0:53
 */
@Slf4j
public class ArraysInitialization {
    public static void main(String[] args) {
        // 1.动态初始化：指定数组长度
        int[] intArrayDynamicInitialization = new int[3];
        for (int i = 0; i < intArrayDynamicInitialization.length; i++) {
            intArrayDynamicInitialization[i] = (i + 1) * 10;
        }
        for (int i : intArrayDynamicInitialization) {
            log.info("Initialize arrays dynamic:" + i);
        }
        // 输出数组元素地址 [I@37f8bb67
        System.out.println(intArrayDynamicInitialization);

        // 2.静态初始化（/*匿名数组*/）：数组长度不指定(编译器默认计算)
        int[] intArrayStaticInitialization = new int[]{100, 200, 300};
        for (int i : intArrayStaticInitialization) {
            log.info("Initialize arrays static:" + i);
        }

        // 静态初始化简化形式
        int[] intArraySimpleStaticInitialization = {1000, 2000, 3000};
        for (int i : intArraySimpleStaticInitialization) {
            log.info("Initialize arrays static:" + i);
        }

        Employee[] el = {
                new Employee("e1"),
                new Employee("e2")
        };
    }
}
