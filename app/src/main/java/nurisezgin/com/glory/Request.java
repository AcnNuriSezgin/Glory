package nurisezgin.com.glory;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

final class Request implements Parcelable {

    private int requestCode;
    private HashSet<String> permissions;
    private String rationale;

    public Request() { }

    protected Request(Parcel in) {
        requestCode = in.readInt();
        permissions = new HashSet<>(in.createStringArrayList());
        rationale = in.readString();
    }

    public static Request newRequest() {
        return new Request();
    }

    public Request requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public Request withPermissions(String[] permissions) {
        this.permissions = new HashSet<>(Arrays.asList(permissions));
        return this;
    }

    public Request rationale(String rationale) {
        this.rationale = rationale;
        return this;
    }

    public int getCode() {
        return requestCode;
    }

    public String[] getPermissions() {
        return permissions.toArray(new String[permissions.size()]);
    }

    public String getRationale() {
        return rationale;
    }

    public boolean hasRationale() {
        return rationale != null && !rationale.isEmpty();
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(requestCode);
        parcel.writeStringList(new ArrayList<>(permissions));
        parcel.writeString(rationale);
    }
}
