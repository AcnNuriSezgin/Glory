package nurisezgin.com.glory;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

public final class Utils {

    static boolean isEqualAndGreaterThanApiM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    static boolean allOfGranted(Context context, String[] permissions) {
        final int value = PackageManager.PERMISSION_GRANTED;

        return Stream.of(permissions)
                .allMatch(perm -> checkSelfPermission(context, perm) == value);
    }

    static boolean anyOfNeedRationale(Activity activity, String[] permissions) {
        return Stream.of(permissions)
                .anyMatch(permission -> shouldShowRequestPermissionRationale(activity, permission));
    }

    static String[][] filter(String[] permissions, int[] grantResults) {
        List<String> grantedPerms = new ArrayList<>();
        List<String> deniedPerms = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                grantedPerms.add(permissions[i]);
            } else {
                deniedPerms.add(permissions[i]);
            }
        }

        return new String[][]{grantedPerms.toArray(new String[grantedPerms.size()]),
                deniedPerms.toArray(new String[deniedPerms.size()])};
    }

}
