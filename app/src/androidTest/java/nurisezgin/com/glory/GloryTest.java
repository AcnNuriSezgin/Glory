package nurisezgin.com.glory;

import android.Manifest;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static android.os.Build.VERSION_CODES.M;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by nuri on 31.07.2018
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = M)
@RequiresApi(api = M)
public class GloryTest {

    private Context ctx;

    @Before
    public void setUp_() {
        ctx = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void should_CallPermissionRequesterCorrect() {
        Requester mockRequester = mock(Requester.class);

        final String[] permissions = {Manifest.permission.READ_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION};

        Request expected = new Request();
        expected.requestCode(101);
        expected.rationale("Message!!!");
        expected.withPermissions(permissions);

        Glory.builder()
                .context(ctx)
                .requestCode(101)
                .rationale("Message!!!")
                .permissions(permissions)
                .requester(mockRequester)
                .build()
                .request(new CallbackFactory.Callback() {
                    @Override
                    public void onPermissionResult(Response response) {

                    }
                });

        ArgumentCaptor<Request> captor = ArgumentCaptor.forClass(Request.class);
        verify(mockRequester).request(captor.capture());

        Request actual = captor.getValue();
        assertThat(actual, is(same(expected)));
    }

    @Test
    public void should_ObservePermissionRequesterCorrect() {
        Requester mockRequester = mock(Requester.class);
        CallbackFactory.ResultListener mockListener = mock(CallbackFactory.ResultListener.class);

        Glory.builder()
                .context(ctx)
                .requestCode(101)
                .rationale("Message!!!")
                .permissions(new String[]{Manifest.permission.READ_SMS,
                        Manifest.permission.ACCESS_FINE_LOCATION})
                .requester(mockRequester)
                .build()
                .request(CallbackFactory.newCallback(mockListener));

        Glory.bus.accept(new Response());

        verify(mockListener).onPermissionResult(any(Response.class));
    }

    @Test
    public void should_SecondTimeObserveCorrect() {
        Requester mockRequester = mock(Requester.class);

        Glory.builder()
                .context(ctx)
                .requestCode(101)
                .rationale("Message!!!")
                .permissions(new String[]{Manifest.permission.READ_SMS})
                .requester(mockRequester)
                .build()
                .request(CallbackFactory.newCallback(mock(CallbackFactory.ResultListener.class)));

        Glory.bus.accept(new Response());

        CallbackFactory.ResultListener mockListener = mock(CallbackFactory.ResultListener.class);

        Glory.builder()
                .context(ctx)
                .requestCode(101)
                .rationale("Message!!!")
                .permissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION})
                .requester(mockRequester)
                .build()
                .request(CallbackFactory.newCallback(mockListener));

        Glory.bus.accept(new Response());

        verify(mockListener).onPermissionResult(any(Response.class));
    }

    @Test
    public void should_ObserveListenerTriggeredOneTimeCorrect() {
        Requester mockRequester = mock(Requester.class);
        CallbackFactory.ResultListener mockListener = mock(CallbackFactory.ResultListener.class);

        Glory.builder()
                .context(ctx)
                .requestCode(101)
                .rationale("Message!!!")
                .permissions(new String[]{Manifest.permission.READ_SMS,
                        Manifest.permission.ACCESS_FINE_LOCATION})
                .requester(mockRequester)
                .build()
                .request(CallbackFactory.newCallback(mockListener));

        Glory.bus.accept(new Response());
        Glory.bus.accept(new Response());

        verify(mockListener).onPermissionResult(any(Response.class));
    }

    private Matcher<Request> same(Request dest) {
        return new TypeSafeMatcher<Request>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("Objects are not same");
            }

            @Override
            protected boolean matchesSafely(Request src) {
                if (src.getCode() != dest.getCode()) {
                    return false;
                }

                if (!(src.getRationale() != null && src.getRationale().equals(dest.getRationale()))) {
                    return false;
                }

                if (src.getPermissions().length != dest.getPermissions().length) {
                    return false;
                }

                for (int i = 0; i < src.getPermissions().length; i++) {
                    String srcPerm = src.getPermissions()[i];
                    String destPerm = dest.getPermissions()[i];

                    if (!srcPerm.equals(destPerm)) {
                        return false;
                    }
                }

                return true;
            }
        };
    }

}