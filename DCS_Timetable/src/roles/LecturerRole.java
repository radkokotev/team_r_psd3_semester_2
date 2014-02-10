package roles;

import data.MyCampusCourseImport;

public interface LecturerRole {
	public void importMyCampusTeachingSession(MyCampusCourseImport myCampusImport) 
			throws PermissionsDeniedException;
	
	public void addSessionToCourse(String sessionTitle, String courseTitle)
			throws PermissionsDeniedException;
	
	public void specifyPeriodicityForSession(String sessionTitle, int periodicity)
			throws PermissionsDeniedException;
	
	public void setSessionToBeCompulsory(String sessionTitle, boolean isCompulsory) 
			throws PermissionsDeniedException;
}
