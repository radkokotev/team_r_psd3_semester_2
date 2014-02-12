package roles;


import java.util.Date;

/*
Team R
Whole team worked on project (Greblykas,Kotev,Tachev,Turner,Vascila)
version 1.1 (updated with the new user story)
this interface it to represent the administrator to do his/her tasks which are currently assigning a room to a session and
creating a timeslot for a session. It would be implemented in user class.
*/


public interface AdministratorRole {
	public void assignRoomToSession(String room, String SessionTitle) 
			throws PermissionsDeniedException;

// update, necessary for the new user story we were just introduced
	public void createTimeSlotForSession(Date startTime, Date endTime, String courseTitle,
			String sessionTitle) throws PermissionsDeniedException;
}
