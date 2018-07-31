package nurisezgin.com.glory;

import android.content.Context;

import com.jakewharton.rxrelay2.PublishRelay;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.disposables.Disposable;

import static nurisezgin.com.glory.Utils.allOfGranted;
import static nurisezgin.com.glory.Utils.isEqualAndGreaterThanApiM;

public final class Glory {

    private static AtomicInteger counter = new AtomicInteger(1);
    private static final int MAX_VALUE = 128;
    static PublishRelay<Response> bus = PublishRelay.create();

    private Context context;
    private int requestCode;
    private String[] permissions;
    private String rationale;
    private Requester requester;

    public Glory(Context context, int requestCode, String[] permissions,
                 String rationale, Requester requester) {

        this.requester = requester;
        if (permissions == null || permissions.length < 1) {
            throw new IllegalArgumentException("Glory, permissions array is empty or null");
        }

        this.context = context;
        this.permissions = permissions;
        this.rationale = rationale;
        this.requestCode = requestCode;
    }

    public void request(CallbackFactory.Callback callback) {
        if (!isEqualAndGreaterThanApiM() || allOfGranted(context, permissions)) {
            Response response = Response.forAllGranted(permissions);
            callback.onPermissionResult(response);
            return;
        }

        final WeakReference<CallbackFactory.Callback> weakListener = new WeakReference<>(callback);

        Disposable disposable = bus.subscribe(permissionResponse -> {
            CallbackFactory.Callback actualCallback = weakListener.get();
            if (actualCallback != null) {
                actualCallback.onPermissionResult(permissionResponse);
                actualCallback.dispose();
            }
        }, throwable -> {
            CallbackFactory.Callback actualCallback = weakListener.get();
            if (actualCallback != null) {
                actualCallback.onPermissionRequestHasError(throwable);
                actualCallback.dispose();
            }
        }, () -> {
            CallbackFactory.Callback actualCallback = weakListener.get();
            if (actualCallback != null) {
                actualCallback.dispose();
            }
        });

        callback.attachDisposable(disposable);

        Request request = Request.newRequest()
                .withPermissions(permissions)
                .requestCode(requestCode)
                .rationale(rationale);

        requester.request(request);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Context context;
        private int requestCode;
        private String[] permissions;
        private String rationale;
        private Requester requester;

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder permission(String permission) {
            this.permissions = new String[]{permission};
            return this;
        }

        public Builder permissions(String[] permissions) {
            this.permissions = permissions;
            return this;
        }

        public Builder rationale(String rationale) {
            this.rationale = rationale;
            return this;
        }

        public Builder requester(Requester requester) {
            this.requester = requester;
            return this;
        }

        public Glory build() {
            if (requester == null) {
                requester = new Requester.Default(context);
            }

            return new Glory(context, normalizeRequestCode(requestCode),
                    permissions, rationale, requester);
        }

        private int normalizeRequestCode(int requestCode) {
            if (requestCode < 1) {
                requestCode = counter.getAndIncrement();
            }

            if (requestCode > MAX_VALUE) {
                requestCode = requestCode % MAX_VALUE;
            }

            return requestCode;
        }
    }


}
