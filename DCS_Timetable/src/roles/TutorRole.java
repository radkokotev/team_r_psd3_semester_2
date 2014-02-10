package roles;

public interface TutorRole {
	public String getInformationForSession(String sessionTitle) 
			throws PermissionsDeniedException;
}
