package model;

import indentifiers.UserKind;
import persistence.UserDatabase;

public class UserController {
	
	public boolean checkLogin(String login, String password){
		return UserDatabase.getInstance().login(login, password);
	}
	
	public User getUserByLogin (String login) {	
		return UserDatabase.getInstance().getUserByLogin(login);
	}

	public boolean isValidLogin(String login) {
		return UserDatabase.getInstance().isValidLogin(login);
	}
	
	public void registerUser(String name, String email, String loginAux, String passwordAux, int userKind){	
		UserKind kind = null;
		switch (userKind) {
		case 1: {
			kind = UserKind.STUDENT;
			break;
		}
		case 2: {
			kind = UserKind.PROFESSOR;
			break;
		}
		case 3: {
			kind = UserKind.RESEARCHER;
			break;
		}
		case 4: {
			kind = UserKind.ADMIN;
			break;
		}
		default:
			break;
		}
		
		User user = new User(0, name, email, loginAux,passwordAux, kind);
		UserDatabase.getInstance().registerUser(user);
	}

	public String showDatabase() {
		return UserDatabase.getInstance().showDatabase();
	}

	public User getUserById(int id) {
		return UserDatabase.getInstance().getUserById(id);
	}

	public User getUserByName(String name) {
		return UserDatabase.getInstance().getUserByName(name);
	}
	
	public int numberOfUser(){
		return UserDatabase.getInstance().numberOfUser();
	}
	
}
