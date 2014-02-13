package roles;


import java.util.Date;

/**
 * Whole team worked on project version 1.1 (updated with the new user story)
 * this interface it to represent the administrator to do his/her tasks which 
 * are currently assigning a room to a session and creating a time slot for a 
 * session. It would be implemented in user class.
*/
public interface AdministratorRole {
	/**
	 * Assigns a room to a session
	 * @param room the room details
	 * @param SessionTitle title of the session
	 * @throws PermissionsDeniedException if another user type invokes the method
	 */
	public void assignRoomToSession(String room, String SessionTitle) 
			throws PermissionsDeniedException;

	/**
	 * Assigns a time slot for a session
	 * @param startTime the starting time of a session
	 * @param endTime the end time of a session
	 * @param courseTitle the title of the course
	 * @param sessionTitle the title of the session
	 * @throws PermissionsDeniedException if another user type invokes the method
	 */
	public void createTimeSlotForSession(Date startTime, Date endTime, 
			String courseTitle,	String sessionTitle) throws PermissionsDeniedException;
}
