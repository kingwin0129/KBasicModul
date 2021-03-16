package kingwin.utils.gather.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * 特定用于使用ViewBinding的Activity基类
 * 通过获取ViewBinding泛型实现设置布局
 * 该类预设OnClick监听
 * @param <T> ViewBinding类型 视图布局类
 * @author KingWin
 * @since 2021/3/3 5:58 PM
 */
public abstract class ViewBindingActivity<T extends ViewBinding> extends BaseActivity implements View.OnClickListener {

    protected T viewBinding;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @CallSuper
    @Override
    public void setLayout() {
        //获取当前泛型
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        ///获取泛型第一位参数 这里指的就是 T extends ViewBinding
        Class cls = (Class) type.getActualTypeArguments()[0];
        try {
            // 获取LayoutInflater类里inflate方法
            Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class);
            // 执行inflate方法并传入对应参数
            viewBinding = (T) inflate.invoke(null, getLayoutInflater());
            // 设置布局内容
            setContentView(viewBinding.getRoot());
        } catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
