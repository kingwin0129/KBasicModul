package kingwin.modul.permission;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 权限管理 KPermission权限管理唯一对外接口
 * @author KingWin
 * @since 2021/3/1 1:38 PM
 */
public class KPermission {


    /**
     * 请求权限 Activity
     * @param context Activity上下文
     * @param tips 权限弹窗上的文字提示语，告诉用户，这个权限用途
     * @param requestCode 这次请求权限的唯一标示
     * @param permissions 一些系列的权限
     */
    public static void requestPermissions(AppCompatActivity context, String tips, int requestCode, String[] permissions){
        EasyPermissions.requestPermissions(context,tips,requestCode,permissions);
    }


    /**
     * 请求权限 Fragment
     * @param context Fragment上下文
     * @param tips 权限弹窗上的文字提示语，告诉用户，这个权限用途
     * @param requestCode 这次请求权限的唯一标示
     * @param permissions 一些系列的权限
     */
    public static void requestPermissions(Fragment context, String tips, int requestCode, String[] permissions){
        EasyPermissions.requestPermissions(context,tips,requestCode,permissions);
    }


    /**
     * 响应请求结果
     * @param context 上下文
     * @param requestCode 请求的code
     * @param permissions 一些列的请求权限
     * @param grantResults  用户授权的结果
     */
    public static void onRequestPermissionsResult(Context context,int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, context);
    }


    /**
     * 权限是否被允许
     * @param context 上下文
     * @param permissions 一些列的请求权限
     */
    public static boolean hasPermissions(Context context, String[] permissions){
        return EasyPermissions.hasPermissions(context,permissions);
    }

    /**
     * 勾选’NEVER ASK AGAIN.’或者’不再提示’，且拒绝权限，下次请求权限，弹窗不能弹出，无法让用户授权。这时候，需要跳转到设置界面去，让用户手动开启
     * @param context Activity 上下文
     * @param deniedPermissions 一些列的请求权限
     */
    public static boolean somePermissionPermanentlyDenied(Activity context, List<String> deniedPermissions){
        return EasyPermissions.somePermissionPermanentlyDenied((Activity) context,deniedPermissions);
    }

    /**
     * 勾选’NEVER ASK AGAIN.’或者’不再提示’，且拒绝权限，下次请求权限，弹窗不能弹出，无法让用户授权。这时候，需要跳转到设置界面去，让用户手动开启
     * @param context Fragment 上下文
     * @param deniedPermissions 一些列的请求权限
     */
    public static boolean somePermissionPermanentlyDenied(Fragment context, List<String> deniedPermissions){
        return EasyPermissions.somePermissionPermanentlyDenied((Fragment) context,deniedPermissions);
    }

    public static void skipSetting(Context context){
        new AppSettingsDialog.Builder((Activity) context)
                .setTitle("权限要求")
                .setRationale("如果没有请求的权限，这个应用程序可能无法正常工作。进入app settings界面，修改app权限。")
                .setNegativeButton("拒绝")
                .setPositiveButton("确认")
                .build()
                .show();
    }
}
