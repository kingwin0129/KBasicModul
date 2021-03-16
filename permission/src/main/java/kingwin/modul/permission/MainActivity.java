package kingwin.modul.permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements KPermissionCallbacks{

    TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] PERMS = {Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};
                if(!KPermission.hasPermissions(MainActivity.this, PERMS)){
                    KPermission.requestPermissions(MainActivity.this,"为了正常使用App,一定得同意喔",10086,PERMS);
                }
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
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(MainActivity.this,"授权失败",Toast.LENGTH_LONG).show();
//若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
        //这时候，需要跳转到设置界面去，让用户手动开启。
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("权限要求")
                    .setRationale("如果没有请求的权限，这个应用程序可能无法正常工作。进入app settings界面，修改app权限。")
                    .setNegativeButton("拒绝")
                    .setPositiveButton("确认")
                    .build()
                    .show();
        }
    }
}