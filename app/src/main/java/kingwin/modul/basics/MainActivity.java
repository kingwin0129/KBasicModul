package kingwin.modul.basics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kingwin.modul.logger.KLogger;
import kingwin.modul.network.KNetWork;
import kingwin.modul.network.demo.DMSCallBack;
import kingwin.modul.network.demo.DMSCallBackListener;
import kingwin.modul.network.demo.TestApi;
import kingwin.modul.network.demo.UserParams;
import kingwin.modul.permission.KPermission;
import kingwin.modul.permission.KPermissionCallbacks;

public class MainActivity extends AppCompatActivity implements KPermissionCallbacks {

    TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KLogger.d("基础组件输出，成功组装日志组件");

        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] PERMS = {Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};
                if(!KPermission.hasPermissions(MainActivity.this, PERMS)){
                    KPermission.requestPermissions(MainActivity.this,"为了正常使用App,一定得同意喔",10086,PERMS);
                }else{
                    initData();
                }
            }
        });
    }

    public void initData(){
        TestApi api =  KNetWork.getRetrofit().create(TestApi.class);
        KNetWork.requestApi(api.login(KNetWork.beanToRequestBody(new UserParams("汤福兴","111111"))), new DMSCallBackListener<DMSCallBack<String>>() {

            @Override
            public void onSucceed(DMSCallBack<String> stringDMSCallBack) {
                KNetWork.getCurOkHttpConfig().setToken(stringDMSCallBack.getData());
                tv_info.setText(KNetWork.getCurOkHttpConfig().getToken());

            }
            @Override
            public void onError(String msg) {
                tv_info.setText(msg);
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        KPermission.onRequestPermissionsResult(MainActivity.this,requestCode,permissions,grantResults);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(MainActivity.this,"授权成功",Toast.LENGTH_LONG).show();
        initData();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(MainActivity.this,"授权失败",Toast.LENGTH_LONG).show();
//若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
        //这时候，需要跳转到设置界面去，让用户手动开启。
        if (KPermission.somePermissionPermanentlyDenied(this, perms)) {
            KPermission.skipSetting(MainActivity.this);
        }
    }
}