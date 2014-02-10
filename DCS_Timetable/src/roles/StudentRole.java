package roles;

public interface StudentRole {
	public void bookSlotForCourse(String id, String sessionTitle, String courseTitle)
			throws PermissionsDeniedException;
	
	public String getAllCompulsorySessionsForCourse(String id, String courseTitle)
			throws PermissionsDeniedException;
	
	public String getSessionsForWhichEnrolled(String id, String courseTitle)
			throws PermissionsDeniedException;
}
