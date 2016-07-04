package cn.com.dc.app.client.bean;

public class User {
	private String uid,uname,email,face,mobile,pwd,birthYM;

	public String getPwd() {
		return pwd;
	}

	public String getBirthYM() {
		return birthYM;
	}

	public void setBirthYM(String birthYM) {
		this.birthYM = birthYM;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getGrade() {
		return grade;
	}

	/**
	 * 1为男 0 为女 2为未知
	 * @param grade
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	private int grade;
}
