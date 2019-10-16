package nurisezgin.com.glory;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import static nurisezgin.com.glory.Utils.anyOfNeedRationale;
import static nurisezgin.com.glory.Utils.filter;
import static nurisezgin.com.glory.Utils.isEqualAndGreaterThanApiM;

public class GloryActivity extends Activity {

    public static final String REQ_KEY = "perm_req";
    private Request request;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntent().hasExtra(REQ_KEY) || !isEqualAndGreaterThanApiM()) {
            finish();
            return;
        }

        request = getIntent().getParcelableExtra(REQ_KEY);
        requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length < 1) {
            return;
        }

        String[][] groupedPerms = filter(permissions, grantResults);
        Response response = Response.newResponse(request.getCode(), groupedPerms);
        Glory.bus.accept(response);

        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions() {
        if (request.hasRationale() && anyOfNeedRationale(this, request.getPermissions())) {
            new AlertDialog.Builder(this)
                    .setMessage(request.getRationale())
                    .setNeutralButton(android.R.string.ok, (dialog, which) -> {
                        dialog.dismiss();
                        actualRequestPermission();
                    })
                    .create()
                    .show();
        } else {
            actualRequestPermission();
        }
    }

    private void actualRequestPermission() {
        ActivityCompat.requestPermissions(this,
                request.getPermissions(),
                request.getCode());
    }

}
