package roles;

public interface AdministratorRole {
	public void assignRoomToSession(String room, String SessionTitle) 
			throws PermissionsDeniedException;
}
