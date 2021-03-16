package kingwin.utils.gather.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 基础Activity
 *
 * @author KingWin
 * @since 2021/3/3 11:02 AM
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        initView();
        doBusiness();
    }

    @Override
    public void receiveParams(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return View.NO_ID;
    }

}
