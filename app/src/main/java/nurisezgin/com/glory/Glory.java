package nurisezgin.com.glory;

import android.content.Context;

import java.util.concurrent.atomic.AtomicInteger;

public final class Glory {

    private static AtomicInteger counter = new AtomicInteger();
    private static int MAX_VALUE = 128;
    private Context context;
    private int requestCode;
    private String[] permissions;

    public Glory(Context context, int requestCode, String[] permissions) {
        this.context = context;
        this.permissions = permissions;

        if (requestCode == 0) {
            requestCode = counter.getAndIncrement();
        }
        this.requestCode = requestCode;

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void request() {
        // TODO: 30/07/2018
    }

    public static class Builder {

        private Context context;
        private int requestCode;
        private String[] permissions;

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder permissions(String[] permissions) {
            this.permissions = permissions;
            return this;
        }

        public Glory build() {
            return new Glory(context, requestCode, permissions);
        }
    }


}
