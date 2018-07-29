package nurisezgin.com.glory;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public final class Glory {

    static final PublishSubject<Boolean> publishSubject = PublishSubject.create();

    public static Disposable requestPermission(String permission) {
        return publishSubject.subscribe(result -> { }, throwable -> throwable.printStackTrace());
    }

    public static void requestPermissions(String[] permissions) {

    }

}
