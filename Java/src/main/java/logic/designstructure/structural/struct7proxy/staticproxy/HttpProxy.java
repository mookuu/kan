package logic.designstructure.structural.struct7proxy.staticproxy;

import logic.designstructure.structural.struct7proxy.interfaces.HttpUtil;
import logic.designstructure.structural.struct7proxy.interfaces.IHttp;

/**
 * @ClassName HttpProxy
 * @Description 静态代理：将代理对象分装在代理类里实现控制
 * @Author moku
 * @Date 2022/12/22 23:16
 * @Version 1.0
 */
public class HttpProxy implements IHttp {
    private final HttpUtil httpUtil;

    public HttpProxy(HttpUtil httpUtil) {
        this.httpUtil = httpUtil;
    }

    @Override
    public void request(String sendData) {
        System.out.println("发送数据:" + sendData);
        httpUtil.request(sendData);
    }

    @Override
    public void onSuccess(String receivedData) {
        System.out.println("收到数据:" + receivedData);
        httpUtil.onSuccess(receivedData);
    }
}
