package nurisezgin.com.glory;

public class PermissionResponse {

    private String[] grantedPermissions;
    private String[] deniedPermissions;

    public String[] getGrantedPermissions() {
        return grantedPermissions;
    }

    public PermissionResponse grantedPermissions(String[] grantedPermissions) {
        this.grantedPermissions = grantedPermissions;
        return this;
    }

    public String[] getDeniedPermissions() {
        return deniedPermissions;
    }

    public PermissionResponse deniedPermissions(String[] deniedPermissions) {
        this.deniedPermissions = deniedPermissions;
        return this;
    }

    public boolean shouldAllPermissionsAreGranted() {
        return grantedPermissions.length > 0 && deniedPermissions.length == 0;
    }
}
