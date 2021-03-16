package kingwin.modul.permission;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 权限操作结果返回接口
 * 如使用 KPermission 则需使用该类用于配套得到操作结果
 * @author KingWin
 * @since 2021/3/1 5:36 PM
 */
public interface KPermissionCallbacks extends EasyPermissions.PermissionCallbacks {

}
