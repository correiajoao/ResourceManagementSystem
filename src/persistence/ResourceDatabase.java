package persistence;

import indentifiers.ResourceStatus;

import java.util.ArrayList;
import java.util.List;

import model.Resource;

public class ResourceDatabase {
	int id = 0;
	List<Resource> resourceList = null;
	private static ResourceDatabase instance = null;

	public ResourceDatabase() {
		resourceList = new ArrayList<Resource>();
		registerResource(new Resource(0, "Projector", UserDatabase.getInstance().getUserById(1), ResourceStatus.FREE));
		registerResource(new Resource(0, "Laboratory", UserDatabase.getInstance().getUserById(2), ResourceStatus.FREE));
		registerResource(new Resource(0, "Classroom", UserDatabase.getInstance().getUserById(3), ResourceStatus.FREE));
	}

	public static ResourceDatabase getInstance() {
		if (instance == null) {
			instance = new ResourceDatabase();
		}
		return instance;
	}

	public int registerResource(Resource resource) {
		resource.setId(id);
		resourceList.add(id++, resource);
		return id-1;
	}

	public Resource getResourceByName(String name) {
		for (Resource resource : resourceList) {
			if (resource.getName().contains(name)) {
				return resource;
			}
		}
		return null;
	}

	public Resource getResourceById(int id) {
		try {
			return resourceList.get(id);
		} catch (Exception e) {
			return null;
		}
	}

	public void updateStatus(Resource resource) {

		if (resource.getStatus() == ResourceStatus.ALLOCATED) {
			resource.setStatus(ResourceStatus.FREE);
			resourceList.remove(resource.getId());
			resourceList.add(resource.getId(), resource);
		} else {
			resource.setStatus(ResourceStatus.ALLOCATED);
			resourceList.remove(resource.getId());
			resourceList.add(resource.getId(), resource);
		}
	}

	public int numberOfResource() {
		return resourceList.size();
	}

	public String showDatabase() {
		String result = "";
		for (Resource resource : resourceList) {
			result += "Id: "+ resource.getId() +", Name: " + resource.getName() + ", Status: "+ resource.getStatus().toString() + "\n";
		}
		return result;
	}

}
