package persistence;

import indentifiers.AllocationStatus;

import java.util.ArrayList;
import java.util.List;
import model.Allocation;
import model.Resource;
import model.User;

public class AllocationDatabase {
	int id = 0;
	List<Allocation> allocationList = null;
	private static AllocationDatabase instance = null;

	public AllocationDatabase() {
		allocationList = new ArrayList<Allocation>();
	}

	public static AllocationDatabase getInstance() {
		if (instance == null) {
			instance = new AllocationDatabase();
		}
		return instance;
	}

	public int registerAllocation(Allocation allocation) {
		allocation.setId(id);
		allocationList.add(id++, allocation);
		return id-1;
	}
	
	public boolean validateAllocation(User user){
		for (Allocation allocation : allocationList) {
			if(allocation.getUser().getId() == user.getId() && allocation.getStatus() != AllocationStatus.COMPLETE){
				return false;
			}
		}
		return true;
	}
	
	public Allocation getAllocationById(int id) {
		try {
			return allocationList.get(id);
		} catch (Exception e) {
			return null;
		}
	}
	

	public List<Allocation> getAllocationByUser(User user){
		List<Allocation> result = new ArrayList<Allocation>();
		
		for (Allocation allocation : allocationList) {
			if(allocation.getUser().getId() == user.getId()){
				result.add(allocation);
			}
		}
		return result;
	}
	
	public void updateStatus(Allocation allocation, AllocationStatus status){
		allocation.setStatus(status);
		allocationList.remove(allocation.getId());
		allocationList.add(allocation.getId(),allocation);
	}
	
	public List<Allocation> getAllocationByResource(Resource resource){
		List<Allocation> result = new ArrayList<Allocation>();
		
		for (Allocation allocation : allocationList) {
			if(allocation.getResource().getId() == resource.getId()){
				result.add(allocation);
			}
		}
		return result;
	}
	
	public int numberOfAllocation(AllocationStatus allocationStatus){
		int result = 0;
		switch (allocationStatus) {
		case IN_ALLOCATION_PROCESS:{
			for (Allocation allocation : allocationList) {
				if(allocation.getStatus() == AllocationStatus.IN_ALLOCATION_PROCESS)
					result++;
			}
			return result;
		}case IN_PROGRESS:{
			for (Allocation allocation : allocationList) {
				if(allocation.getStatus() == AllocationStatus.IN_PROGRESS)
					result++;
			}
			return result;
		}case ALLOCATED:{
			for (Allocation allocation : allocationList) {
				if(allocation.getStatus() == AllocationStatus.ALLOCATED)
					result++;
			}
			return result;
		}case COMPLETE:{
			for (Allocation allocation : allocationList) {
				if(allocation.getStatus() == AllocationStatus.COMPLETE)
					result++;
			}
			return result;
		}

		default:
			break;
		}
		return result;
	}
	
	public int numberOfAllocation(){
		return allocationList.size();
	}
	
	public String showDatabase() {
		String result ="";
		for (Allocation allocation : allocationList) {
			result += "Id:" + allocation.getId() + ", Resource name:" + allocation.getResource().getName() + ", User name:" + allocation.getUser().getName()+"\n";
		}
		return result;
	}
	public List<Allocation> getAllocationList(){
		return allocationList;
	}
}
