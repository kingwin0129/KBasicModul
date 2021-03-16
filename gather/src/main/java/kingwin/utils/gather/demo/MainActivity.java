package kingwin.utils.gather.demo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import kingwin.utils.gather.KUtilsGuide;
import kingwin.utils.gather.databinding.ActivityMainBinding;


/**
 * <pre>
 *     author: KingWin
 *     blog  : http://blankj.com
 *     time  : 2020/03/19
 *     desc  :扫除荆棘丛生层
 * </pre>
 */
public class MainActivity extends CommonActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent t = getIntent();
        Bundle bund = t.getExtras();
        bund.getString("xx");
    }


    @Override
    public void initView() {
        viewBinding.tvInfo.setText("viewbinding绑定成功");
        viewBinding.tvInfo.setOnClickListener(this);

        viewBinding.tvInfo2.setText("viewbinding案件绑定成功");
        viewBinding.tvInfo2.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onClick(View v) {
        if(v.equals(viewBinding.tvInfo)){
            viewBinding.tvInfo.setText("viewbinding绑定真的成功11");
        }else if(v.equals(viewBinding.tvInfo2)){
            //viewBinding.tvInfo2.setText("viewbinding案件绑定真的成功11");
            //ActivityUtils.startHomeActivity();
            Toast.makeText(KUtilsGuide.getContext(),"xxxx",Toast.LENGTH_LONG).show();

            ActivityUtilsAA.startActivity(TestActivity.class);
        }
    }
}