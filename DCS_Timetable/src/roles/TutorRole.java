package roles;

/*
Team R
Whole team worked on project (Greblykas,Kotev,Tachev,Turner,Vascila)
Version 1.0
this interface it to represent the tutor who is able to do his/her tasks which is currently only able to get information 
for a session.
*/

public interface TutorRole {
	public String getInformationForSession(String sessionTitle) 
			throws PermissionsDeniedException;
}
