package roles;

public class PermissionsDeniedException extends Exception {
	private static final long serialVersionUID = 3359604191020446704L;
	
	public PermissionsDeniedException(String message) {
		super(message);
	}
}
