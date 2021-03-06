package roles;

import java.util.Calendar;
import java.util.Date;

import data.Course;
import data.Data;
import data.DataInterface;
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

public class User implements AdministratorRole, LecturerRole, StudentRole, TutorRole, MyCampusLogin {
	private boolean isAdmin;
	private boolean isLecturer;
	private boolean isTutor;
	private boolean isStudent;
	private boolean isValidLogin;
	private DataInterface data;
	
	public User(boolean isAdmin, boolean isLecturer, 
			boolean isTutor, boolean isStudent, boolean isValidLogin) {
		this.isAdmin = isAdmin;
		this.isLecturer = isLecturer;
		this.isTutor = isTutor;
		this.isStudent = isStudent;
		this.isValidLogin = isValidLogin;
		//instantiate data object
		this.data = Data.getSingleton();
	}

	@Override
	public void bookSlotForCourse(String id, String sessionTitle, String courseTitle) 
			throws PermissionsDeniedException {
		if (isStudent || isAdmin) {
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
			Course course = data.getCourse(courseTitle);
			String result = "";
			for (Session s : course.getSessions()) {
				if(s.getIsCompulsory()){
				result += s.toString();
				}
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
			Course course = data.getCourse(courseTitle);
			Student student = data.getStudent(id);
			String result = "";
			for (Session s : course.getSessions()) {
				if (s.getIsCompulsory() && s.getStudents().contains(student)) {
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
			Session session = data.getSession(sessionTitle);
			session.setRoom(room);
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to assignRoomToSession");
			throw(e);
		}		
	}
	


	@Override
	public String getInformationForSession(String sessionTitle)
			throws PermissionsDeniedException {
		if (isTutor || isAdmin) {
			Session session = data.getSession(sessionTitle);
			return session.toString();
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to getInformationForSession");
			throw(e);
		}	
	}

	@Override
	public boolean checkCourseTimeSlotClashes(Calendar start1, Calendar end1, Calendar start2, Calendar end2){
		return (start1.getTimeInMillis() >= end2.getTimeInMillis() || end1.getTimeInMillis() <= start2.getTimeInMillis()) ;
	}
	
	@Override
	public void createTimeSlotForSession(Date startTime, Date endTime, String courseTitle,
			String sessionTitle) throws PermissionsDeniedException {
		if(isAdmin){
			Session session = data.getSession(sessionTitle);
			if(session == null) {
				Course course = data.getCourse(courseTitle);
				session = new Session(course, sessionTitle);
				data.assignSessionToCourse(sessionTitle, courseTitle);
			}
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
	public boolean loginMyCampus() {
		return isValidLogin;
	}
	
	public void setData(DataInterface data2){
		data = data2;
	}
	// getter for Data object
	public Data getData(){
		return (Data) data;
	}
	
	public boolean isAdmin(){
		return isAdmin;
	}
	
	public boolean isLecturer(){
		return isLecturer;
	}
	
	public boolean isTutor(){
		return isTutor;
	}
	
	public boolean isStudent(){
		return isStudent;
	}

}
