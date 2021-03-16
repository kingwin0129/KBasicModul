package kingwin.utils.gather.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import kingwin.utils.gather.base.BaseActivity;
import kingwin.utils.gather.base.ViewBindingActivity;

/**
 * 根据项目自定公共的Activity的基类
 * 这是项目级的Activity基类 可以根据项目实现自定义规则
 * 由于本项目使用ViewBinding 所以集成框架里的ViewBindingActivity
 * @param <T> ViewBinding类型 视图布局类
 * @author KingWin
 * @since 2021/3/3 11:59 AM
 */
public abstract class CommonActivity<T extends ViewBinding> extends ViewBindingActivity<T> implements View.OnClickListener {


    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
