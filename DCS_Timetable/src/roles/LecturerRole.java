package roles;

/*
Team R
Whole team worked on project (Greblykas,Kotev,Tachev,Turner,Vascila)
Version 1.0
this interface it to represent the lectures role in order to do his/her tasks which are currently importing my campus
teaching session, adding session to course, specify periodicity for a session and set a session to be mandatory.
*/


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
