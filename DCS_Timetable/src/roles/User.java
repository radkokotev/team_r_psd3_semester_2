package roles;

import data.Course;
import data.Data;
import data.Session;
import data.Student;

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
					"Permissions denied to BookSlotForCourse");
			throw(e);
		}
	}

	@Override
	public void getAllCompulsorySessionsForCourse(String id, String courseTitle) 
			throws PermissionsDeniedException {
		if (isStudent || isAdmin) {
			Data data = Data.getSingleton();
			Course course = data.getCourse(courseTitle);
			for (Session s : course.getSessions()) {
				System.out.println(s.toString());
			}
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to BookSlotForCourse");
			throw(e);
		}	
	}

	@Override
	public void getSessionsForWhichEnrolled(String id, String courseTitle) 
			throws PermissionsDeniedException {
		if (isStudent || isAdmin) {
			Data data = Data.getSingleton();
			Course course = data.getCourse(courseTitle);
			Student student = data.getStudent(id);
			for (Session s : course.getSessions()) {
				if (s.getStudents().contains(student)) {
					System.out.println(s.toString());
				}
			}
		} else {
			PermissionsDeniedException e = new PermissionsDeniedException(
					"Permissions denied to BookSlotForCourse");
			throw(e);
		}	
	}
}
