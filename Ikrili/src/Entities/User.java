package Entities;

public class User {
	private int User_id;
    private String Name;
    private String Gender;
    private String Mail;
    private String Phone;
    private String Password;
    private String Img_url;
    
    public User() {
		
	}
    
	public User(int user_id, String name, String mail, String phone, String password, String img_url) {
		User_id = user_id;
		Name = name;
		Mail = mail;
		Phone = phone;
		Password = password;
		Img_url = img_url;
	}

	public User(int studentId, String studentName) {
		User_id = studentId;
		Name = studentName;
	}

	public int getUser_id() {
		return User_id;
	}

	public void setUser_id(int user_id) {
		User_id = user_id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getImg_url() {
		return Img_url;
	}

	public void setImg_url(String img_url) {
		Img_url = img_url;
	}
	
	public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
