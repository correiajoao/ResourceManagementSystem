package persistence;

import indentifiers.ActivityKind;

import java.util.ArrayList;
import java.util.List;

import model.Activity;
import model.User;

public class ActivityDatabase {
	int id = 0;
	List<Activity> activityList = null;
	private static ActivityDatabase instance = null;

	public ActivityDatabase() {
		activityList = new ArrayList<Activity>();
	}

	public static ActivityDatabase getInstance() {
		if (instance == null) {
			instance = new ActivityDatabase();
		}
		return instance;
	}

	public int registerActivity(Activity activity) {
		activity.setId(id);
		activityList.add(id++, activity);
		return id-1;
	}

	public Activity getActivityById(int id) {
		return activityList.get(id);
	}
	
	public int numberOfActivity(ActivityKind activityKind){
		int result = 0;
		switch (activityKind) {
		case CLASS:{
			for (Activity activity : activityList) {
				if(activity.getKind() == ActivityKind.CLASS)
					result++;
			}
			return result;
		}case LABORATORY:{
			for (Activity activity : activityList) {
				if(activity.getKind() == ActivityKind.LABORATORY)
					result++;
			}
			return result;
		}case PRESENTATION:{
			for (Activity activity : activityList) {
				if(activity.getKind() == ActivityKind.PRESENTATION)
					result++;
			}
			return result;
		}

		default:
			break;
		}
		return result;
	}
	public int numberOfActivity(){
		return activityList.size();
	}
	
	public List<Activity> getActivityByUser(User user){
		List<Activity> result = new ArrayList<Activity>();
		for (Activity activity : activityList) {
			if(activity.getResponsible().getId() == user.getId()){
				result.add(activity);
			}
		}
		return result;
	}

	
	public String showDatabase() {
		String result = ""; 
		for (Activity activity : activityList) {
			result += "Id:" + activity.getId() + ", Title:" + activity.getTitle() + "\n";
		}
		return result;
	}
}
