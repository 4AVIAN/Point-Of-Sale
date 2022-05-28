package database;

public class member {
	private String memberID, name, gender;

	public member(String memberID, String name, String gender) {
		super();
		this.memberID = memberID;
		this.name = name;
		this.gender = gender;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
