package roles;

/**
 * this interface it to represent the student object in order to do his/her tasks 
 * which are currently booking slot for a course, get all compulsory sessions for 
 * course and get sessions for which he/she is enrolled into.
 */
public interface StudentRole {
	/**
	 * Self assignment to a session
	 * @param id matriculation number
	 * @param sessionTitle title of a session
	 * @param courseTitle title of a course
	 * @throws PermissionsDeniedException if another user type invokes the method
	 */
	public void bookSlotForCourse(String id, String sessionTitle, String courseTitle)
			throws PermissionsDeniedException;
	
	/**
	 * Gets all compulsory sessions for this student
	 * @param id matriculation number
	 * @param courseTitle title of the course
	 * @return details for all compulsory sessions that the student is to take
	 * @throws PermissionsDeniedException  if another user type invokes the method
	 */
	public String getAllCompulsorySessionsForCourse(String id, String courseTitle)
			throws PermissionsDeniedException;
	
	/**
	 * Gets all the sessions the student is already enrolled into
	 * @param id matriculation number
	 * @param courseTitle course title
	 * @return details for all sessions of the student
	 * @throws PermissionsDeniedException  if another user type invokes the method
	 */
	public String getSessionsForWhichEnrolled(String id, String courseTitle)
			throws PermissionsDeniedException;
}
