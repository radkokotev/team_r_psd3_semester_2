package roles;

/*
Team R
Whole team worked on project (Greblykas,Kotev,Tachev,Turner,Vascila)
Version 1.0
this interface it to represent the student object in order to do his/her tasks which are currently boking slot for a course,
get all compulsory sessions for course and get sessions for which he/she is enrolled into.
*/

public interface StudentRole {
	public void bookSlotForCourse(String id, String sessionTitle, String courseTitle)
			throws PermissionsDeniedException;
	
	public String getAllCompulsorySessionsForCourse(String id, String courseTitle)
			throws PermissionsDeniedException;
	
	public String getSessionsForWhichEnrolled(String id, String courseTitle)
			throws PermissionsDeniedException;
}
