package roles;

import java.util.Date;

public interface AdministratorRole {
	public void assignRoomToSession(String room, String SessionTitle) 
			throws PermissionsDeniedException;

// update, necessary for the new user story we were just introduced
	public void createTimeSlotForSession(Date startTime, Date endTime, String SessionTitle)
			throws PermissionsDeniedException;
}
