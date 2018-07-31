package nurisezgin.com.glory;

public class Response {

    private int requestCode;
    private String[] grantedPermissions;
    private String[] deniedPermissions;

    public static Response newResponse(int requestCode, String[][] perms) {
        return new Response()
                .requestCode(requestCode)
                .grantedPermissions(perms[0])
                .deniedPermissions(perms[1]);
    }

    public static Response forAllGranted(String[] perms) {
        Response response = new Response();
        response.grantedPermissions = perms;
        response.deniedPermissions = new String[0];
        return response;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public Response requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public String[] getGrantedPermissions() {
        return grantedPermissions;
    }

    public Response grantedPermissions(String[] grantedPermissions) {
        this.grantedPermissions = grantedPermissions;
        return this;
    }

    public String[] getDeniedPermissions() {
        return deniedPermissions;
    }

    public Response deniedPermissions(String[] deniedPermissions) {
        this.deniedPermissions = deniedPermissions;
        return this;
    }

    public boolean shouldAllPermissionsAreGranted() {
        return grantedPermissions.length > 0 && deniedPermissions.length == 0;
    }
}
