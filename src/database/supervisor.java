package database;

public class supervisor {
	private String supervisorID, name, password;

	public supervisor(String supervisorID, String name, String password) {
		super();
		this.supervisorID = supervisorID;
		this.name = name;
		this.password = password;
	}

	public String getSupervisorID() {
		return supervisorID; 
	}

	public void setSupervisorID(String supervisorID) {
		this.supervisorID = supervisorID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
