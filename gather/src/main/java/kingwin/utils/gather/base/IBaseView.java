package kingwin.utils.gather.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * 基础视图接口
 * @author: KingWin
 * @since 2021/3/3 11:25 AM
 */
public interface IBaseView {


    /**
     * 接收参数
     * @param bundle 参数Bundle
     */
    void receiveParams(@Nullable Bundle bundle);


    /**
     * 获取绑定的视图ID
     * @return 绑定的视图ID
     */
    int bindLayout();


    /**
     * 设置布局
     */
    void setLayout();


    /**
     * 初始化视图
     */
    void initView();


    /**
     * 开始执行业务
     */
    void doBusiness();


}
