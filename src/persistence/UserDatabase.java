package persistence;

import indentifiers.UserKind;

import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDatabase {
	int id = 0;
	List<User> userList = null;
	private static UserDatabase instance = null;

	public UserDatabase() {
		userList = new ArrayList<User>();
		registerUser(new User(0,"José da Silva","email@email.email.com","jose","jose", UserKind.STUDENT));
		registerUser(new User(0,"Carlos da Silva","email@email.email.com","carlos","carlos", UserKind.RESEARCHER));
		registerUser(new User(0,"João da Silva","email@email.email.com","joao","joao", UserKind.PROFESSOR));
		registerUser(new User(0,"Marcos da Silva","email@email.email.com","admin","admin", UserKind.ADMIN));
	}

	public static UserDatabase getInstance() {
		if (instance == null) {
			instance = new UserDatabase();
		}
		return instance;
	}

	public int registerUser(User user) {
		user.setId(id);
		userList.add(id++, user);
		return id-1;
	}

	public User getUserByName(String name) {
		for (User user : userList) {
			if(user.getName().contains(name)){
				return user;
			}
		}
		return null;
	}
	
	public User getUserByLogin(String login) {
		for (User user : userList) {
			if(user.getLogin().contains(login)){
				return user;
			}
		}
		return null;
	}
	
	public User getUserById(int id) {
		try {
			return userList.get(id);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public boolean login(String login, String password){
		for (User user : userList) {
			if(user.getLogin().equals(login) && user.getPassword().equals(password))
				return true;
		}
		return false;
	}
	
	public boolean isValidLogin(String login){
		for (User user : userList) {
			if(user.getLogin().equals(login)){
				System.out.println("Invalid login: Login is already in use");
				return false;
			}
		}
		return true;
	}
	
	public int numberOfUser(){
		return userList.size();
	}
	
	public String showDatabase() {
		String result = "";
		for (User user : userList) {
			result += "Id:"+ user.getId() + ", Name:" + user.getName() + ", Kind:" + user.getUserKind().toString()+"\n";
		}
		return result;
	}
	
}
