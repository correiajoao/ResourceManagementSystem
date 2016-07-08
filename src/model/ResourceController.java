package model;

import indentifiers.ResourceStatus;
import persistence.ResourceDatabase;


public class ResourceController {

	
	public String showDatabase() {
		return ResourceDatabase.getInstance().showDatabase();
	}
	
	public Resource getResourceById(int id) {
		return ResourceDatabase.getInstance().getResourceById(id);
	}
	
	public void updateStatus(Resource resource) {
		ResourceDatabase.getInstance().updateStatus(resource);
	}

	public int registerResource(String name, User userAux) {
		Resource resource = new Resource(0, name, userAux, ResourceStatus.FREE);	
		return ResourceDatabase.getInstance().registerResource(resource);
	}

	public Resource getResourceByName(String name) {
		return ResourceDatabase.getInstance().getResourceByName(name);
	}
	
	public int numberOfResource(){
		return ResourceDatabase.getInstance().numberOfResource();
	}
}
