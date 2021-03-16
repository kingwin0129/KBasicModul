package kingwin.modul.network.callback;

/**
 * 网络返回基础监听
 * author: kingwin
 * created on: 2021/2/2 4:22 PM
 * description:
 */
public interface BaseNetWorkCallBackListener<T> {


    /**
     * 成功
     * @param t 返回对象
     */
    void onSucceed(T t);


    /**
     * 服务器返回错误
     * @param msg 错误信息
     */
    void onError(String msg);


}
