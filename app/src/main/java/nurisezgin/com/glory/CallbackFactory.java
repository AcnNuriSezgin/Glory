package nurisezgin.com.glory;

import io.reactivex.disposables.Disposable;

/**
 * Created by nuri on 31.07.2018
 */
public final class CallbackFactory {

    public static Callback newCallback(ResultListener listener) {
        return new Callback() {
            @Override
            public void onPermissionResult(Response response) {
                listener.onPermissionResult(response);
            }
        };
    }

    /**
     * Created by nuri on 31.07.2018
     */
    public abstract static class Callback implements Disposable {

        private Disposable disposable;

        Callback() { }

        public abstract void onPermissionResult(Response response);

        void onPermissionRequestHasError(Throwable throwable) {
            throwable.printStackTrace();
        }

        void attachDisposable(Disposable disposable) {
            this.disposable = disposable;
        }

        boolean hasAttachedDisposable() {
            return disposable != null;
        }

        @Override
        public void dispose() {
            if (disposable != null) {
                disposable.dispose();
            }
        }

        @Override
        public boolean isDisposed() {
            return disposable != null ? disposable.isDisposed() : false;
        }
    }

    /**
     * Created by nuri on 31.07.2018
     */
    public interface ResultListener {

        void onPermissionResult(Response response);

    }
}
