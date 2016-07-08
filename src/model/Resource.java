package model;

import indentifiers.ResourceStatus;

public class Resource {
	private int id;
	private String name;
	private User responsible;
	private ResourceStatus status;
	
	public Resource() {
		// TODO Auto-generated constructor stub
	}
	
	public Resource(int id, String name, User responsible, ResourceStatus status) {
		this.id = id;
		this.name = name;
		this.responsible = responsible;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getResponsible() {
		return responsible;
	}

	public void setResponsible(User responsible) {
		this.responsible = responsible;
	}

	public ResourceStatus getStatus() {
		return status;
	}

	public void setStatus(ResourceStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "[Resource] Id:" + id + ", name:" + name + ", responsible:{"
				+ responsible + "}, status:" + status + "";
	}
	
}
