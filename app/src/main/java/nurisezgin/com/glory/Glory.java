package nurisezgin.com.glory;

import android.support.annotation.NonNull;

public final class Glory {

    public static void of(@NonNull String[] permissions) {
        PermissionRequest request = new PermissionRequest()
                .withPermissions(permissions)
                .showRationale("Message");
    }

    public static class Builder {

        private String[] permissions;

    }


}
