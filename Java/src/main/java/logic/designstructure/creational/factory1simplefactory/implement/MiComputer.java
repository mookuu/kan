package logic.designstructure.creational.factory1simplefactory.implement;

import logic.designstructure.creational.factory1simplefactory.absclazz.Computer;

/**
 * @ClassName MiComputer
 * @Description 2.定义具体对象类
 * @Author moku
 * @Date 2022/12/18 19:20
 * @Version 1.0
 */
public class MiComputer extends Computer {
    @Override
    public void setOperationSystem() {
        System.out.println("小米笔记本安装Win10系统");
    }
}
