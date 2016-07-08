package model;

import indentifiers.ActivityKind;

import java.util.List;

import persistence.ActivityDatabase;


public class ActivityController {

	public int registerActivity(int _opc, String title, String participants,String material, String description, User user) {
			Activity activity = new Activity(0, _opc,title, participants, material,description, user);
			return ActivityDatabase.getInstance().registerActivity(activity);
	}

	public List<Activity> getActivityByUser(User userAux) {
		return ActivityDatabase.getInstance().getActivityByUser(userAux);
	}
	
	public int numberOfActivity(){
		return ActivityDatabase.getInstance().numberOfActivity();
	}
	
	public int numberOfActivity(ActivityKind kind){
		return ActivityDatabase.getInstance().numberOfActivity(kind);
	}
}
