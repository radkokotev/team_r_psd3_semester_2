package roles;

import data.Course;
import data.Data;
import data.MyCampusCourseImport;
import data.Session;
import data.Student;

/**
 * Provides User class. Private boolean dictates permission level. 
 * Each permission is an interface that is implemented by the class.
 * 
 * @author	Kotev, Greblikas, Turner, Vascila, Tachev
 * @version	1.0
 */

public class User implements AdministratorRole, LecturerRole, StudentRole, TutorRole {
	private boolean isAdmin;
	private boolean isLecturer;
	private boolean isTutor;
	private boolean isStudent;
	
	public User(boolean isAdmin, boolean isLecturer, 
			boolean isTutor, boolean isStudent) {
		this.isAdmin = isAdmin;
		this.isLecturer = isLecturer;
		this.isTutor = isTutor;
		this.isStudent = isStudent;
	}

	@Override
	public void bookSlotForCourse(String id, String sessionTitle, String courseTitle) 
			throws PermissionsDeniedException {
		if (isStudent || isAdmin) {
			Data data = Data.getSingleton();
			data.assignStudenttoSession(id, sessionTitle, courseTitle);
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to bookSlotForCourse");
			throw(e);
		}
	}

	@Override
	public String getAllCompulsorySessionsForCourse(String id, String courseTitle) 
			throws PermissionsDeniedException {
		if (isStudent || isAdmin) {
			Data data = Data.getSingleton();
			Course course = data.getCourse(courseTitle);
			String result = "";
			for (Session s : course.getSessions()) {
				result += s.toString();
			}
			return result;
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to getAllCompulsorySessionsForCourse");
			throw(e);
		}	
	}

	@Override
	public String getSessionsForWhichEnrolled(String id, String courseTitle) 
			throws PermissionsDeniedException {
		if (isStudent || isAdmin) {
			Data data = Data.getSingleton();
			Course course = data.getCourse(courseTitle);
			Student student = data.getStudent(id);
			String result = "";
			for (Session s : course.getSessions()) {
				if (s.getStudents().contains(student)) {
					result += s.toString();
				}
			}
			return result;
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to getSessionsForWhichEnrolled");
			throw(e);
		}	
	}

	@Override
	public void setSessionToBeCompulsory(String sessionTitle, boolean isCompulsory)
			throws PermissionsDeniedException {
		if (isLecturer || isAdmin) {
			Data data = Data.getSingleton();
			Session session = data.getSession(sessionTitle);
			session.setIsCompulsory(isCompulsory);
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to setSessionToBeCompulsory");
			throw(e);
		}
	}
	
	@Override
	public void importMyCampusTeachingSession(
			MyCampusCourseImport myCampusImport)
			throws PermissionsDeniedException {
		if (isLecturer || isAdmin) {
			Data data = Data.getSingleton();
			data.importCourseFromMyCampus(myCampusImport);
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to importMyCampusTeachingSession");
			throw(e);
		}
	}
	
	@Override
	public void addSessionToCourse(String sessionTitle, String courseTitle)
			throws PermissionsDeniedException {
		if (isLecturer || isAdmin) {
			Data data = Data.getSingleton();
			data.assignSessionToCourse(sessionTitle, courseTitle);
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to addSessionToCourse");
			throw(e);
		}		
	}
	

	@Override
	public void specifyPeriodicityForSession(String sessionTitle,
			int periodicity) throws PermissionsDeniedException {
		if (isLecturer || isAdmin) {
			Data data = Data.getSingleton();
			Session session = data.getSession(sessionTitle);
			session.setPeriodicity(periodicity);
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to specifyPeriodicityForSession");
			throw(e);
		}		
	}
	
	@Override
	public void assignRoomToSession(String room, String sessionTitle)
			throws PermissionsDeniedException {
		if (isAdmin) {
			Data data = Data.getSingleton();
			Session session = data.getSession(sessionTitle);
			session.setRoom(room);
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to assignRoomToSession");
			throw(e);
		}		
	}
	
	@Override
	public void createTimeSlotForSession(Date startTime, Date endTime, String sessionTitle){
		throws PermissionsDeniedException {
			if(isAdmin){
				Data data = Data.getSingleton();
				Session session = data.getSession(sessionTitle);
				session.setStartTime(startTime);
				session.setEndTime(endTime);
				}
			else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to createTimeSlotForSession");
			throw(e);
		}
	}

	@Override
	public String getInformationForSession(String sessionTitle)
			throws PermissionsDeniedException {
		if (isTutor || isAdmin) {
			Data data = Data.getSingleton();
			Session session = data.getSession(sessionTitle);
			return session.toString();
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to getInformationForSession");
			throw(e);
		}	
	}
}
