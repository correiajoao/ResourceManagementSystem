package model;

import indentifiers.ActivityKind;

public class Activity {
	private int id;
	private ActivityKind kind;
	private String title;
	private String participants;
	private String supportMaterial;
	private String description;
	private User responsible;
	
	public Activity() {
		// TODO Auto-generated constructor stub
	}
	
	public Activity(int id, int kind, String title, String participants, String supportMaterial, String description, User responsible) {
		super();
		this.id = id;
		this.participants = participants;
		this.title = title;
		this.supportMaterial = supportMaterial;
		this.description = description;
		this.responsible = responsible;
		
		switch (kind) {
		case 1: this.kind = ActivityKind.CLASS;
		break;
		case 2: this.kind = ActivityKind.LABORATORY;
		break;
		case 3: this.kind = ActivityKind.PRESENTATION;
		break;

		default:
			break;
		}
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ActivityKind getKind() {
		return kind;
	}

	public void setKind(ActivityKind kind) {
		this.kind = kind;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public String getSupportMaterial() {
		return supportMaterial;
	}

	public void setSupportMaterial(String supportMaterial) {
		this.supportMaterial = supportMaterial;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getResponsible() {
		return responsible;
	}

	public void setResponsible(User responsible) {
		this.responsible = responsible;
	}

	@Override
	public String toString() {
		return "[Activity] id:" + id + ", kind:" + kind + ", title:" + title
				+ ", participants:" + participants + ", supportMaterial:"
				+ supportMaterial + ", description:" + description
				+ ", responsible:{" + responsible + "}";
	}
	
	
}
