package roles;

import data.MyCampusCourseImport;

/** 
 * This interface it to represent the lectures role in order to do his/her tasks 
 * which are currently importing my campus teaching session, adding session to 
 * course, specify periodicity for a session and set a session to be mandatory.
*/
public interface LecturerRole {
	
	/**
	 * Importing a course from MyCampus
	 * @param myCampusImport importer instance for a course
	 * @throws PermissionsDeniedException if another user type invokes the method
	 */
	public void importMyCampusTeachingSession(MyCampusCourseImport myCampusImport) 
			throws PermissionsDeniedException;
	
	
	/**
	 * Adding a session to a course
	 * @param sessionTitle title of the session
	 * @param courseTitle title of the course
	 * @throws PermissionsDeniedException  if another user type invokes the method
	 */
	public void addSessionToCourse(String sessionTitle, String courseTitle)
			throws PermissionsDeniedException;
	
	/**
	 * Sets the periodicity of a given session
	 * @param sessionTitle the title of the session
	 * @param periodicity the periodicity
	 * @throws PermissionsDeniedException if another user type invokes the method
	 */
	public void specifyPeriodicityForSession(String sessionTitle, int periodicity)
			throws PermissionsDeniedException;
	
	/**
	 * Set the session to be compulsory or not
	 * @param sessionTitle title of a session
	 * @param isCompulsory if a session is compulsory or not
	 * @throws PermissionsDeniedException if another user type invokes the method
	 */
	public void setSessionToBeCompulsory(String sessionTitle, boolean isCompulsory) 
			throws PermissionsDeniedException;
}
