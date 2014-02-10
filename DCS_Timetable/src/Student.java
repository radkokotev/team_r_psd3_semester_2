
public class Student {
	private String id;
	private String fName;
	private String sName;
	
	public Student(String id) {
		this.id = id;
	}
	
	public Student(String id, String fName, String sName) {
		this.id = id;
		this.fName = fName;
		this.sName = sName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}
}
