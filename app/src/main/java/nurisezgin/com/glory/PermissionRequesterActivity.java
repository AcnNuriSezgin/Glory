package nurisezgin.com.glory;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.annimon.stream.Stream;

import java.util.concurrent.atomic.AtomicInteger;

public class PermissionRequesterActivity extends AppCompatActivity {

    private AtomicInteger mCounter = new AtomicInteger();

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (shouldPermissionsGranted(grantResults)) {
            // TODO: 30/07/2018 : return as success
        } else {
            // TODO: 30/07/2018 : return as failure
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermission(@NonNull String permission) {
        requestPermissions(new String[]{permission});
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(@NonNull String[] permissions) {
        requestPermissions(permissions, mCounter.getAndIncrement());
    }

    private boolean shouldPermissionsGranted(@NonNull String[] permissions) {
        if (!DeviceUtil.isEqualAndGreaterThanApiM()) {
            return true;
        }

        int[] grantResults = Stream.of(permissions)
                .mapToInt(this::toGrantResult)
                .toArray();

        return shouldPermissionsGranted(grantResults);
    }

    private int toGrantResult(String permission) {
        return ContextCompat.checkSelfPermission(this, permission);
    }

    private boolean shouldPermissionsGranted(int[] grantResults) {
        if (grantResults == null || grantResults.length < 1) {
            return false;
        }

        boolean areGranted = true;

        for (int res : grantResults) {
            boolean isGranted = PackageManager.PERMISSION_GRANTED == res;

            areGranted &= isGranted;
        }

        return areGranted;
    }

}
