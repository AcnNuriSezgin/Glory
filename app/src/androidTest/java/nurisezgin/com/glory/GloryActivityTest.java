package nurisezgin.com.glory;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import nurisezgin.com.glory.testutils.DataHolder;

import static android.os.Build.VERSION_CODES.M;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nuri on 31.07.2018
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = M)
@RequiresApi(api = M)
public class GloryActivityTest {

    private static final int PERMISSIONS_DIALOG_DELAY = 3000;
    private static final int DISCARD_BUTTON_INDEX = 0;
    private static final int GRANT_BUTTON_INDEX = 1;

    @Rule
    public ActivityTestRule<GloryActivity> mActivityTestRule
            = new ActivityTestRule<>(GloryActivity.class, true, false);

    private Context ctx;

    @Before
    public void setUp_() {
        ctx = InstrumentationRegistry.getTargetContext();
    }

    private void revokePermission(String permission) {
        if (Build.VERSION.SDK_INT >= M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm revoke " + ctx.getPackageName()
                            + " " + permission);
        }
    }

    @Ignore
    @Test
    public void should_GrantPermissionCorrect() throws InterruptedException {
        final boolean expected = true;

        final String permission = Manifest.permission.READ_SMS;
        revokePermission(permission);

        DataHolder<Boolean> holder = new DataHolder<>();
        CountDownLatch latch = new CountDownLatch(1);

        Glory.builder()
                .context(ctx)
                .permission(permission)
                .build()
                .request(CallbackFactory.newCallback(
                        response -> {
                            holder.offer(response.shouldAllPermissionsAreGranted());
                            latch.countDown();
                        }
                ));

        applyPermissionStatus(true);

        latch.await(PERMISSIONS_DIALOG_DELAY * 2, TimeUnit.MILLISECONDS);
        boolean res = holder.take();

        assertThat(res, is(expected));
    }

    @Ignore
    @Test
    public void should_DiscardPermissionCorrect() throws InterruptedException {
        final boolean expected = Utils.isEqualAndGreaterThanApiM() ? false : true;

        final String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        revokePermission(permission);

        DataHolder<Boolean> holder = new DataHolder<>();
        CountDownLatch latch = new CountDownLatch(1);

        Glory.builder()
                .context(ctx)
                .permission(permission)
                .build()
                .request(CallbackFactory.newCallback(
                        response -> {
                            holder.offer(response.shouldAllPermissionsAreGranted());
                            latch.countDown();
                        }));

        applyPermissionStatus(false);

        latch.await(PERMISSIONS_DIALOG_DELAY * 2, TimeUnit.MILLISECONDS);
        boolean res = holder.take();

        assertThat(res, is(expected));
    }

    public void applyPermissionStatus(boolean isAllow) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(PERMISSIONS_DIALOG_DELAY);
                    UiDevice device = UiDevice.getInstance(getInstrumentation());
                    UiObject allowPermissions = device.findObject(new UiSelector()
                            .clickable(true)
                            .checkable(false)
                            .index(isAllow ? GRANT_BUTTON_INDEX : DISCARD_BUTTON_INDEX));
                    if (allowPermissions.exists()) {
                        allowPermissions.click();
                    }
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}