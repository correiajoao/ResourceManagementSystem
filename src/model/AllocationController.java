package model;

import indentifiers.AllocationStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import persistence.ActivityDatabase;
import persistence.AllocationDatabase;

public class AllocationController {
	
	public int registerAllocation(String dateAllocation, String dateEnd, User user, Resource resource, int activityId) throws ParseException{
	if(AllocationDatabase.getInstance().validateAllocation(user)){
		Calendar calendarAllocation = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		calendarAllocation.setTime(sdf.parse(dateAllocation));
	
		Calendar calendarEnd = Calendar.getInstance();
		SimpleDateFormat _sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		calendarEnd.setTime(_sdf.parse(dateEnd));
		
			Activity activity = ActivityDatabase.getInstance().getActivityById(activityId);
			return AllocationDatabase.getInstance().registerAllocation(new Allocation(0, AllocationStatus.ALLOCATED, calendarAllocation, calendarEnd, user, resource, activity));
		}else{
			System.out.println("You already has an allocation in progress");
			return -1;
		}
	}
	
	public List<Allocation> getAllocationByUser(User user){
		return AllocationDatabase.getInstance().getAllocationByUser(user); 
	}

	public Allocation getAllocationById(int id) {
		return AllocationDatabase.getInstance().getAllocationById(id);
	}

	public void updateStatus(Allocation allocation, AllocationStatus status) {
		AllocationDatabase.getInstance().updateStatus(allocation, status);
	}
	
	public List<Allocation> getAllocationList(){
		return AllocationDatabase.getInstance().getAllocationList();
	}
	
	public int numberOfAllocation(){
		return AllocationDatabase.getInstance().numberOfAllocation();
	}
	
	public int numberOfAllocation(AllocationStatus allocationStatus){
		return AllocationDatabase.getInstance().numberOfAllocation(allocationStatus);
	}
}
