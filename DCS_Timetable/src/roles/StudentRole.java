package roles;

public interface StudentRole {
	public void bookSlotForCourse(String id, String sessionTitle, String courseTitle)
			throws PermissionsDeniedException;
	public void getAllCompulsorySessionsForCourse(String id, String courseTitle)
			throws PermissionsDeniedException;
	public void getSessionsForWhichEnrolled(String id, String courseTitle)
			throws PermissionsDeniedException;
}
