package nurisezgin.com.glory;

import android.os.Build;

public final class DeviceUtil {

    public static boolean isEqualAndGreaterThanApiM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
