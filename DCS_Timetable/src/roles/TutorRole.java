package roles;

/**
 * This interface it to represent the tutor who is able to do his/her 
 * tasks which is currently only able to get information for a session.
 */
public interface TutorRole {
	/**
	 * gets the information for a single session
	 * @param sessionTitle title of a session
	 * @return he details for the session
	 * @throws PermissionsDeniedException if another user type invokes the method
	 */
	public String getInformationForSession(String sessionTitle) 
			throws PermissionsDeniedException;
}
