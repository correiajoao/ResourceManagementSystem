package model;

import indentifiers.AllocationStatus;

import java.util.Calendar;

public class Allocation {
	int id;
	private AllocationStatus status;
	private Calendar alocationDate;
	private Calendar endDate;
	private User user;
	private Resource resource;
	private Activity activity;

	public Allocation() {
		// TODO Auto-generated constructor stub
	}

	public Allocation(int id, AllocationStatus status, Calendar alocationDate,
			Calendar endDate, User user, Resource resource, Activity activity) {
		this.id = id;
		this.status = status;
		this.alocationDate = alocationDate;
		this.endDate = endDate;
		this.user = user;
		this.resource = resource;
		this.activity = activity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AllocationStatus getStatus() {
		return status;
	}

	public void setStatus(AllocationStatus status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Calendar getAlocationDate() {
		return alocationDate;
	}

	public void setAlocationDate(Calendar alocationDate) {
		this.alocationDate = alocationDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "[Allocation] Id:" + id + ", status:" + status
				+ ", alocationDate:" + alocationDate.getTime() + ", endDate:"
				+ endDate.getTime() + ", resource:{" + resource + "}";
	}

}
