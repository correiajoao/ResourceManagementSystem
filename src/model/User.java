package model;

import indentifiers.UserKind;

public class User {
	private int id;
	private String name;
	private String email;
	private String login;
	private String password;
	private UserKind userKind;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int id, String name, String email, String login, String password, UserKind userKind) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.email = email;
		this.userKind = userKind;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public UserKind getUserKind() {
		return userKind;
	}

	public void setUserKind(UserKind userKind) {
		this.userKind = userKind;
	}

	@Override
	public String toString() {
		return "[User] id:" + id + ", name:" + name + ", email:" + email
				+ ", login:" + login + ", kind:"
				+ userKind + "";
	}
	
}
