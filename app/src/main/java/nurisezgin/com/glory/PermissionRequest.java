package nurisezgin.com.glory;

import android.os.Parcel;
import android.os.Parcelable;

import com.annimon.stream.Stream;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

final class PermissionRequest implements Parcelable {

    private static AtomicInteger counter = new AtomicInteger();
    private static int MAX_VALUE = 128;
    private int requestCode;
    private List<String> permissions;
    private String rationale;

    public PermissionRequest() {
        requestCode = newCode();
    }

    protected PermissionRequest(Parcel in) {
        requestCode = in.readInt();
        permissions = in.createStringArrayList();
        rationale = in.readString();
    }

    private int newCode() {
        return counter.getAndIncrement() % MAX_VALUE;
    }

    public PermissionRequest withPermission(String permission) {
        permissions.add(permission);
        return this;
    }

    public PermissionRequest withPermissions(String[] permissions) {
        Stream.of(permissions)
                .forEach(perm -> withPermission(perm));

        return this;
    }

    public PermissionRequest showRationale(String rationale) {
        this.rationale = rationale;
        return this;
    }

    public static final Creator<PermissionRequest> CREATOR = new Creator<PermissionRequest>() {
        @Override
        public PermissionRequest createFromParcel(Parcel in) {
            return new PermissionRequest(in);
        }

        @Override
        public PermissionRequest[] newArray(int size) {
            return new PermissionRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(requestCode);
        parcel.writeStringList(permissions);
        parcel.writeString(rationale);
    }
}
