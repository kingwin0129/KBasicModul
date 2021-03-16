package kingwin.modul.network.callback;

/**
 * 基础网络层返回对象
 * author: kingwin
 * created on: 2021/2/2 5:09 PM
 * description:
 */
public abstract class BaseNetWorkCallBack {


    /**
     * 是否成功
     * @return true=>成功 false=>失败
     */
    public abstract boolean isSucceed();


    /**
     * 返回内容
     * @return 内容
     */
    public abstract String getMessage();
}
