package userinterface;

import indentifiers.ActivityKind;
import indentifiers.AllocationStatus;
import indentifiers.ResourceStatus;
import indentifiers.UserKind;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import model.Activity;
import model.ActivityController;
import model.Allocation;
import model.AllocationController;
import model.Resource;
import model.ResourceController;
import model.User;
import model.UserController;

import persistence.AllocationDatabase;

public class Main {

	public static void main(String[] args) throws ParseException {
		Scanner input = new Scanner(System.in);
		
		UserController userController = new UserController();
		ResourceController resourceController = new ResourceController();
		ActivityController activityController = new ActivityController();
		AllocationController allocationController = new AllocationController();
		
		String login;
		String password;

		do {
			do {
				System.out.println("----------------------------------- Resource manager -----------------------------------");
				System.out.println("Default user: admin, admin.");
				System.out.println();
				
				System.out.println("Login: ");
				login = input.nextLine();

				System.out.println("Password: ");
				password = input.nextLine();
				
			} while (!userController.checkLogin(login, password));
			User user = userController.getUserByLogin(login);

			switch (user.getUserKind()) {
			case STUDENT: {
				int opc;
				do {
					do {
						System.out.println("----------------------------------- MENU -----------------------------------");
						System.out.println();
						System.out.println("1: Register allocation");
						System.out.println("2: Change allocation status");
						System.out.println("3: Queries and reports");
						System.out.println("4: Quit");
						System.out.println();
						System.out.print("Type an entry:"); opc = input.nextInt();
					} while (opc < 1 || opc > 4);

					switch (opc) {
					case 1: {
						System.out.println();
						System.out.println("Choose the resource code:");
						System.out.println(resourceController.showDatabase());
						System.out.print("Type an entry:"); int id = input.nextInt();

						Resource resource = resourceController.getResourceById(id);
						if (resource != null) {
							if (resource.getStatus() != ResourceStatus.ALLOCATED) {
								resourceController.updateStatus(resource);
					
								// Get allocation date
								System.out.println("Type the allocation date: (dd/mm/yyyy hh:mm:ss)");
								input.nextLine();
								String dateAllocation = input.nextLine();

								// Get end allocation date
								System.out.println("Type the end allocation date: (dd/mm/yyyy hh:mm:ss)");
								String dateEnd = input.nextLine();

								int _opc;
								do {
									System.out.println();
									System.out.println("Choose the activity kind:");
									System.out.println("3: Presentation");
									System.out.print("Type an entry:"); _opc = input.nextInt();
								} while (_opc != 3);

								System.out.println("Activity title:");
								input.nextLine();
								String title = input.nextLine();

								System.out.println("Activity description:");
								String description = input.nextLine();

								System.out.println("Activity participants:");
								String participants = input.nextLine();

								System.out.println("Activity material:");
								String material = input.nextLine();

								int activityId = activityController.registerActivity(_opc,title, participants, material,description, user);
								
								if(allocationController.registerAllocation(dateAllocation, dateEnd,user,resource,activityId) != -1){
								System.out.println("==> Process accomplished successful <==");
								}else{
									System.out.println("Error");
									break;
								}
							} else {
								System.out
										.println("Invalid resource: The resource is already allocated");
								break;
							}
						}

						break;
					}
					case 2: {
						System.out.println("Choose an allocation code to confirm:");
						List<Allocation> allocations = allocationController.getAllocationByUser(user);

						if(allocations.size() != 0){
							for (Allocation allocation : allocations) {
								if (allocation.getStatus() == AllocationStatus.ALLOCATED);
								System.out.println(allocation.toString());
							}
							
							System.out.println();
							System.out.print("Type an entry:"); int id = input.nextInt();
							Allocation allocation = allocationController.getAllocationById(id);

							Calendar dateNow = Calendar.getInstance();
							if (dateNow.get(Calendar.DATE) < allocation.getAlocationDate().get(Calendar.DATE)) {
								allocationController.updateStatus(allocation,AllocationStatus.IN_PROGRESS);
								
								System.out.println("==> Process accomplished successful <==");
							} else {
								System.out.println("Invalid operation: The time for confirmation has finished");
							}
						}else{
							System.out.println("There aren't allocations to confirm ");
						}
						
						break;
					}case 3:{
						int _opc;
						do {
							do {
								System.out.println();
								System.out.println("1: Number of users");
								System.out.println("2: Number of resources");
								System.out.println("3: Number of allocations");
								System.out.println("4: Number of activities");
								System.out.println("5: Search users");
								System.out.println("6: Search resources");
								System.out.println("7: Quit");
								System.out.println();
								System.out.print("Type an entry:"); _opc = input.nextInt();
							} while (_opc < 1 || _opc > 7);

							switch (_opc) {
							case 1: {
								System.out.println("Number of users: "+ userController.numberOfUser());
								break;
							}
							case 2: {
								System.out.println("Number of allocations:");
								System.out.println("In allocation process:"+allocationController.numberOfAllocation(AllocationStatus.IN_ALLOCATION_PROCESS));
								System.out.println("In progress:"+allocationController.numberOfAllocation(AllocationStatus.IN_PROGRESS));
								System.out.println("Allocated:"+allocationController.numberOfAllocation(AllocationStatus.ALLOCATED));
								System.out.println("Complete:"+allocationController.numberOfAllocation(AllocationStatus.COMPLETE));
								break;
							}
							case 3: {
								System.out.println("Number of allocations: "+ allocationController.numberOfAllocation());
								break;
							}
							case 4: {
								System.out.println("Number of activities: "+ activityController.numberOfActivity());
								System.out.println("Class:"+activityController.numberOfActivity(ActivityKind.CLASS));
								System.out.println("Laboratory:"+activityController.numberOfActivity(ActivityKind.LABORATORY));
								System.out.println("Presentation:"+activityController.numberOfActivity(ActivityKind.PRESENTATION));
								break;
							}
							case 5: {

								System.out.println("User name:");
								input.nextLine();
								String name = input.nextLine();
								User userAux = userController.getUserByName(name);

								if (userAux != null) {
									System.out.println("----------------------------- Result query -----------------------------");
									System.out.println(userAux.toString());

									List<Allocation> allocations = allocationController.getAllocationByUser(userAux);
									for (Allocation allocation : allocations) {
										System.out.println(allocation.toString());
									}

									List<Activity> activities = activityController.getActivityByUser(userAux);
									for (Activity activity : activities) {
										System.out.println(activity.toString());
									}

								} else {
									System.out.println("Ivalid user: User doesn't exists");
								}

								break;
							}
							case 6: {
								System.out.println("Resource name:");
								input.nextLine();
								String name = input.nextLine();
								Resource resourceAux = resourceController.getResourceByName(name);

								if (resourceAux != null) {
									System.out.println("----------------------------- Result query -----------------------------");
									System.out.println(resourceAux.toString());

									List<Allocation> allocations = AllocationDatabase.getInstance().getAllocationByResource(resourceAux);
									for (Allocation allocation : allocations) {
										System.out.println(allocation.getUser().toString());
									}

									for (Allocation allocation : allocations) {
										System.out.println(allocation.getActivity().toString());
									}

								}

								break;
							}
							default:
								break;
							}
						} while (_opc != 7);
						break;
					}
					default:
						break;
					}
					
				} while (opc != 4);
				break;
			}
			case PROFESSOR: {
				int opc;
				do {
					do {
						System.out.println("----------------------------------- MENU -----------------------------------");
						System.out.println();
						System.out.println("1: Register user");
						System.out.println("2: Register resource");
						System.out.println("3: Register allocation");
						System.out.println("4: Change allocation status");
						System.out.println("5: Queries and reports");
						System.out.println("6: Quit");
						System.out.println();
						System.out.print("Type an entry:"); opc = input.nextInt();
					} while (opc < 1 || opc > 6);

					switch (opc) {
					case 1: {
						System.out.println("User name:");
						input.nextLine();
						String name = input.nextLine();

						System.out.println("User email:");
						String email = input.nextLine();

						System.out.println();
						System.out.println("Choose the user kind:");

						int userKind;
						do {
							System.out.println("----------------------------------- MENU -----------------------------------");
							System.out.println();
							System.out.println("1: Student");
							System.out.println("2: Professor");
							System.out.println("3: Researcher");
							System.out.println("4: Manager");
							System.out.println();
							System.out.print("Type an entry:"); userKind = input.nextInt();
						} while (userKind < 1 || userKind > 4);
						
						System.out.println();
						String loginAux;
						input.nextLine();
						do {
							System.out.println("User login:");
							loginAux = input.nextLine();
						} while (!userController.isValidLogin(loginAux));

						System.out.println("User password:");
						String passwordAux = input.nextLine();

						userController.registerUser(name, email, loginAux , passwordAux, userKind);
						
						System.out.println("==> Process accomplished successful <==");
						break;
					}
					case 2: {
						System.out.println("Resource name:");
						input.nextLine();
						String name = input.nextLine();

						System.out.println("Choose the responsible code of the resource");
						System.out.println(userController.showDatabase());
						System.out.print("Type an entry:"); int id = input.nextInt();
						
						User userAux = userController.getUserById(id);
						if (userAux != null) {
							if (userAux.getUserKind() != UserKind.STUDENT) {
								resourceController.registerResource(name,userAux);
								System.out.println("==> Process accomplished successful <==");
							} else {
								System.out.println("Invalid user: Student may not be responsible from resources");
							}
						} else {
							System.out.println("Invalid user");
						}
					}
						break;
					case 3: {
						System.out.println();
						System.out.println("Choose the resource code:");
						System.out.println(resourceController.showDatabase());
						System.out.print("Type an entry:"); int id = input.nextInt();

						Resource resource = resourceController.getResourceById(id);
						if (resource != null) {
							if (resource.getStatus() != ResourceStatus.ALLOCATED) {
								resourceController.updateStatus(resource);
					
								// Get allocation date
								System.out.println("Type the allocation date: (dd/mm/yyyy hh:mm:ss)");
								input.nextLine();
								String dateAllocation = input.nextLine();

								// Get end allocation date
								System.out.println("Type the end allocation date: (dd/mm/yyyy hh:mm:ss)");
								String dateEnd = input.nextLine();

								int _opc;
								do {
									System.out.println();
									System.out.println("Choose the activity kind:");
									System.out.println("3: Presentation");
									System.out.println();
									System.out.print("Type an entry:"); _opc = input.nextInt();
								} while (_opc != 3);

								System.out.println("Activity title:");
								input.nextLine();
								String title = input.nextLine();

								System.out.println("Activity description:");
								String description = input.nextLine();

								System.out.println("Activity participants:");
								String participants = input.nextLine();

								System.out.println("Activity material:");
								String material = input.nextLine();

								int activityId = activityController.registerActivity(_opc,title, participants, material,description, user);									
								if(allocationController.registerAllocation(dateAllocation, dateEnd,user,resource,activityId) != -1){
									System.out.println("==> Process accomplished successful <==");
									}else{
										System.out.println("Error");
										break;
									}
							} else {
								System.out
										.println("Invalid resource: The resource is already allocated");
								break;
							}
						}

						break;
					}
					case 4: {
						System.out.println("Choose an allocation code to confirm:");
						List<Allocation> allocations = allocationController.getAllocationByUser(user);

						if(allocations.size() != 0){
							for (Allocation allocation : allocations) {
								if (allocation.getStatus() == AllocationStatus.ALLOCATED);
								System.out.println(allocation.toString());
							}
							
							System.out.println();
							System.out.print("Type an entry:"); int id = input.nextInt();
							Allocation allocation = allocationController.getAllocationById(id);

							Calendar dateNow = Calendar.getInstance();
							if (dateNow.get(Calendar.DATE) < allocation.getAlocationDate().get(Calendar.DATE)) {
								allocationController.updateStatus(allocation,AllocationStatus.IN_PROGRESS);
								
								System.out.println("==> Process accomplished successful <==");
							} else {
								System.out.println("Invalid operation: The time for confirmation has finished");
							}
						}else{
							System.out.println("There aren't allocations to confirm ");
						}
						
						break;
					}

					case 5: {
						int _opc;
						do {
							do {
								System.out.println("----------------------------------- MENU -----------------------------------");
								System.out.println();
								System.out.println("1: Number of users");
								System.out.println("2: Number of resources");
								System.out.println("3: Number of allocations");
								System.out.println("4: Number of activities");
								System.out.println("5: Search users");
								System.out.println("6: Search resources");
								System.out.println("7: Quit");
								System.out.println();
								System.out.print("Type an entry:"); _opc = input.nextInt();
							} while (_opc < 1 || _opc > 7);

							switch (_opc) {
							case 1: {
								System.out.println("Number of users: "+ userController.numberOfUser());
								break;
							}
							case 2: {
								System.out.println("Number of allocations:");
								System.out.println("In allocation process:"+allocationController.numberOfAllocation(AllocationStatus.IN_ALLOCATION_PROCESS));
								System.out.println("In progress:"+allocationController.numberOfAllocation(AllocationStatus.IN_PROGRESS));
								System.out.println("Allocated:"+allocationController.numberOfAllocation(AllocationStatus.ALLOCATED));
								System.out.println("Complete:"+allocationController.numberOfAllocation(AllocationStatus.COMPLETE));
								break;
							}
							case 3: {
								System.out.println("Number of allocations: "+ allocationController.numberOfAllocation());
								break;
							}
							case 4: {
								System.out.println("Number of activities: "+ activityController.numberOfActivity());
								System.out.println("Class:"+activityController.numberOfActivity(ActivityKind.CLASS));
								System.out.println("Laboratory:"+activityController.numberOfActivity(ActivityKind.LABORATORY));
								System.out.println("Presentation:"+activityController.numberOfActivity(ActivityKind.PRESENTATION));
								break;
							}
							case 5: {

								System.out.println("User name:");
								input.nextLine();
								String name = input.nextLine();
								User userAux = userController.getUserByName(name);

								if (userAux != null) {
									System.out.println("----------------------------- Result query -----------------------------");
									System.out.println(userAux.toString());

									List<Allocation> allocations = allocationController.getAllocationByUser(userAux);
									for (Allocation allocation : allocations) {
										System.out.println(allocation.toString());
									}

									List<Activity> activities = activityController.getActivityByUser(userAux);
									for (Activity activity : activities) {
										System.out.println(activity.toString());
									}

								} else {
									System.out.println("Ivalid user: User doesn't exists");
								}

								break;
							}
							case 6: {
								System.out.println("Resource name:");
								input.nextLine();
								String name = input.nextLine();
								Resource resourceAux = resourceController.getResourceByName(name);

								if (resourceAux != null) {
									System.out.println("----------------------------- Result query -----------------------------");
									System.out.println(resourceAux.toString());

									List<Allocation> allocations = AllocationDatabase.getInstance().getAllocationByResource(resourceAux);
									for (Allocation allocation : allocations) {
										System.out.println(allocation.getUser().toString());
									}

									for (Allocation allocation : allocations) {
										System.out.println(allocation.getActivity().toString());
									}

								}

								break;
							}
							default:
								break;
							}
						} while (_opc != 7);
						break;
					}

					}
				} while (opc != 6);
				break;
			}
			case RESEARCHER: {
				int opc;
				do {
					do {
						System.out.println("----------------------------------- MENU -----------------------------------");
						System.out.println();
						System.out.println("1: Register allocation");
						System.out.println("2: Change allocation status");
						System.out.println("3: Queries and reports");
						System.out.println("4: Quit");
						System.out.println();
						System.out.print("Type an entry:"); opc = input.nextInt();
					} while (opc < 1 || opc > 4);

					switch (opc) {
					case 1: {
						System.out.println();
						System.out.println("Choose the resource code:");
						System.out.println(resourceController.showDatabase());
						System.out.print("Type an entry:"); int id = input.nextInt();

						Resource resource = resourceController.getResourceById(id);
						if (resource != null) {
							if (resource.getStatus() != ResourceStatus.ALLOCATED) {
								resourceController.updateStatus(resource);
					
								// Get allocation date
								System.out.println("Type the allocation date: (dd/mm/yyyy hh:mm:ss)");
								input.nextLine();
								String dateAllocation = input.nextLine();

								// Get end allocation date
								System.out.println("Type the end allocation date: (dd/mm/yyyy hh:mm:ss)");
								String dateEnd = input.nextLine();

								int _opc;
								do {
									System.out.println();
									System.out.println("Choose the activity kind:");
									System.out.println("3: Presentation");
									System.out.println();
									System.out.print("Type an entry:"); _opc = input.nextInt();
								} while (_opc != 3);

								System.out.println("Activity title:");
								input.nextLine();
								String title = input.nextLine();

								System.out.println("Activity description:");
								String description = input.nextLine();

								System.out.println("Activity participants:");
								String participants = input.nextLine();

								System.out.println("Activity material:");
								String material = input.nextLine();

								int activityId = activityController.registerActivity(_opc,title, participants, material,description, user);
								if(allocationController.registerAllocation(dateAllocation, dateEnd,user,resource,activityId) != -1){
									System.out.println("==> Process accomplished successful <==");
									}else{
										System.out.println("Error");
										break;
									}
							} else {
								System.out
										.println("Invalid resource: The resource is already allocated");
								break;
							}
						}

						break;
					}
					case 2: {
						System.out.println("Choose an allocation code to confirm:");
						List<Allocation> allocations = allocationController.getAllocationByUser(user);

						if(allocations.size() != 0){
							for (Allocation allocation : allocations) {
								if (allocation.getStatus() == AllocationStatus.ALLOCATED);
								System.out.println(allocation.toString());
							}
							
							System.out.println();
							System.out.print("Type an entry:"); int id = input.nextInt();
							Allocation allocation = allocationController.getAllocationById(id);

							Calendar dateNow = Calendar.getInstance();
							if (dateNow.get(Calendar.DATE) < allocation.getAlocationDate().get(Calendar.DATE)) {
								allocationController.updateStatus(allocation,AllocationStatus.IN_PROGRESS);
								
								System.out.println("==> Process accomplished successful <==");
							} else {
								System.out.println("Invalid operation: The time for confirmation has finished");
							}
						}else{
							System.out.println("There aren't allocations to confirm ");
						}
						
						break;
					}case 4:{
						int _opc;
						do {
							do {
								System.out.println("----------------------------------- MENU -----------------------------------");
								System.out.println();
								System.out.println("1: Number of users");
								System.out.println("2: Number of resources");
								System.out.println("3: Number of allocations");
								System.out.println("4: Number of activities");
								System.out.println("5: Search users");
								System.out.println("6: Search resources");
								System.out.println("7: Quit");
								System.out.println();
								System.out.print("Type an entry:"); _opc = input.nextInt();
							} while (_opc < 1 || _opc > 7);

							switch (_opc) {
							case 1: {
								System.out.println("Number of users: "+ userController.numberOfUser());
								break;
							}
							case 2: {
								System.out.println("Number of allocations:");
								System.out.println("In allocation process:"+allocationController.numberOfAllocation(AllocationStatus.IN_ALLOCATION_PROCESS));
								System.out.println("In progress:"+allocationController.numberOfAllocation(AllocationStatus.IN_PROGRESS));
								System.out.println("Allocated:"+allocationController.numberOfAllocation(AllocationStatus.ALLOCATED));
								System.out.println("Complete:"+allocationController.numberOfAllocation(AllocationStatus.COMPLETE));
								break;
							}
							case 3: {
								System.out.println("Number of allocations: "+ allocationController.numberOfAllocation());
								break;
							}
							case 4: {
								System.out.println("Number of activities: "+ activityController.numberOfActivity());
								System.out.println("Class:"+activityController.numberOfActivity(ActivityKind.CLASS));
								System.out.println("Laboratory:"+activityController.numberOfActivity(ActivityKind.LABORATORY));
								System.out.println("Presentation:"+activityController.numberOfActivity(ActivityKind.PRESENTATION));
								break;
							}
							case 5: {

								System.out.println("User name:");
								input.nextLine();
								String name = input.nextLine();
								User userAux = userController.getUserByName(name);

								if (userAux != null) {
									System.out.println("----------------------------- Result query -----------------------------");
									System.out.println(userAux.toString());

									List<Allocation> allocations = allocationController.getAllocationByUser(userAux);
									for (Allocation allocation : allocations) {
										System.out.println(allocation.toString());
									}

									List<Activity> activities = activityController.getActivityByUser(userAux);
									for (Activity activity : activities) {
										System.out.println(activity.toString());
									}

								} else {
									System.out.println("Ivalid user: User doesn't exists");
								}

								break;
							}
							case 6: {
								System.out.println("Resource name:");
								input.nextLine();
								String name = input.nextLine();
								Resource resourceAux = resourceController.getResourceByName(name);

								if (resourceAux != null) {
									System.out.println("----------------------------- Result query -----------------------------");
									System.out.println(resourceAux.toString());

									List<Allocation> allocations = AllocationDatabase.getInstance().getAllocationByResource(resourceAux);
									for (Allocation allocation : allocations) {
										System.out.println(allocation.getUser().toString());
									}

									for (Allocation allocation : allocations) {
										System.out.println(allocation.getActivity().toString());
									}

								}

								break;
							}
							default:
								break;
							}
						} while (_opc != 7);
						break;
					}
					default:
						break;
					}

				} while (opc != 4);
				break;
			}
			case ADMIN: {
				int opc;
				do {
					do {
						System.out.println("----------------------------------- MENU -----------------------------------");
						System.out.println();
						System.out.println("1: Register user");
						System.out.println("2: Register resource");
						System.out.println("3: Register allocation");
						System.out.println("4: Change allocation status");
						System.out.println("5: Queries and reports");
						System.out.println("6: Quit");
						System.out.println();
						System.out.print("Type an entry:"); opc = input.nextInt();
					} while (opc < 1 || opc > 6);

					switch (opc) {
					case 1: {
						System.out.println("User name:");
						input.nextLine();
						String name = input.nextLine();

						System.out.println("User email:");
						String email = input.nextLine();

						System.out.println();
						System.out.println("Choose the user kind:");

						int userKind;
						do {
							System.out.println("1: Student");
							System.out.println("2: Professor");
							System.out.println("3: Researcher");
							System.out.println("4: Manager");
							System.out.println();
							System.out.print("Type an entry:"); userKind = input.nextInt();
						} while (userKind < 1 || userKind > 4);

						System.out.println();
						String loginAux;
						input.nextLine();
						do {
							System.out.println("User login:");
							loginAux = input.nextLine();
						} while (!userController.isValidLogin(loginAux));

						System.out.println("User password:");
						String passwordAux = input.nextLine();

						userController.registerUser(name, email, loginAux , passwordAux, userKind);
						
						System.out.println("==> Process accomplished successful <==");
						break;
					}
					case 2: {
						System.out.println("Resource name:");
						input.nextLine();
						String name = input.nextLine();

						System.out.println("Choose the responsible code of the resource");
						System.out.println(userController.showDatabase());
						System.out.print("Type an entry:"); int id = input.nextInt();
						
						User userAux = userController.getUserById(id);
						if (userAux != null) {
							if (userAux.getUserKind() != UserKind.STUDENT) {
								resourceController.registerResource(name,userAux);
								System.out.println("==> Process accomplished successful <==");
							} else {
								System.out.println("Invalid user: Student may not be responsible from resources");
							}
						} else {
							System.out.println("Invalid user");
						}
					}
						break;
					case 3: {
						System.out.println();
						System.out.println("Choose the resource code:");
						System.out.println(resourceController.showDatabase());
						System.out.print("Type an entry:"); int id = input.nextInt();

						Resource resource = resourceController.getResourceById(id);
						if (resource != null) {
							if (resource.getStatus() != ResourceStatus.ALLOCATED) {
								resourceController.updateStatus(resource);
								
								// Get allocation date
								System.out.println("Type the allocation date: (dd/mm/yyyy hh:mm:ss)");
								input.nextLine();
								String dateAllocation = input.nextLine();

								// Get end allocation date
								System.out.println("Type the end allocation date: (dd/mm/yyyy hh:mm:ss)");
								String dateEnd = input.nextLine();

								int _opc;
								do {
									System.out.println();
									System.out.println("Choose the activity kind:");
									System.out.println("3: Presentation");
									System.out.println();
									System.out.print("Type an entry:"); _opc = input.nextInt();
								} while (_opc != 3);

								System.out.println("Activity title:");
								input.nextLine();
								String title = input.nextLine();

								System.out.println("Activity description:");
								String description = input.nextLine();

								System.out.println("Activity participants:");
								String participants = input.nextLine();

								System.out.println("Activity material:");
								String material = input.nextLine();

								int activityId = activityController.registerActivity(_opc,title, participants, material,description, user);									
								if(allocationController.registerAllocation(dateAllocation, dateEnd,user,resource,activityId) != -1){
									System.out.println("==> Process accomplished successful <==");
									}else{
										System.out.println("Error");
										break;
									}
								System.out.println("==> Process accomplished successful <==");
							} else {
								System.out
										.println("Invalid resource: The resource is already allocated");
								break;
							}
						}

						break;
					}
					case 4: {
						int _opc;

						do {
							System.out.println("----------------------------------- MENU -----------------------------------");
							System.out.println();
							System.out.println("1: Confirm allocation");
							System.out.println("2: Finish allocation");
							System.out.println("3: Quit");
							System.out.println();
							System.out.print("Type an entry:"); _opc = input.nextInt();
						} while (_opc < 1 || _opc > 3);

						switch (_opc) {
						case 1: {
							System.out.println("Choose an allocation code to confirm:");
							List<Allocation> allocations = allocationController.getAllocationByUser(user);

							if(allocations.size() != 0){
								for (Allocation allocation : allocations) {
									if (allocation.getStatus() == AllocationStatus.ALLOCATED);
									System.out.println(allocation.toString());
								}
								System.out.println();
								System.out.print("Type an entry:"); int id = input.nextInt();
								Allocation allocation = allocationController.getAllocationById(id);
	
								Calendar dateNow = Calendar.getInstance();
								if (dateNow.get(Calendar.DATE) < allocation.getAlocationDate().get(Calendar.DATE)) {
									allocationController.updateStatus(allocation,AllocationStatus.IN_PROGRESS);
									
									System.out.println("==> Process accomplished successful <==");
								} else {
									System.out.println("Invalid operation: The time for confirmation has finished");
								}
							}else{
								System.out.println("There aren't allocations to confirm ");
							}
							
							break;
						}
						case 2: {
							System.out.println("Finish allocations");
							List<Allocation> allocations = allocationController.getAllocationList();
							
							if(allocations.size() != 0){
								for (Allocation allocation : allocations) {
									if (allocation.getStatus() == AllocationStatus.IN_PROGRESS) {
										System.out.println(allocation.toString());
									}
								}
								
								System.out.println();
								System.out.print("Type an entry:"); int id = input.nextInt();
								Allocation allocation = AllocationDatabase.getInstance().getAllocationById(id);
	
								if (allocation != null) {
									if (!allocation.getActivity().getDescription().equals("") && !allocation.getActivity().getTitle().equals("") && !allocation.getActivity().getParticipants().equals("")) {
										allocationController.updateStatus(allocation,AllocationStatus.COMPLETE);
									} else {
										System.out.println("Activities should not have empty fields");
									}
								} else {
									System.out.println("Invalid allocation");
								}
							}else{
								System.out.println("There aren't allocations to finish");
							}
							break;
						}

						default:
							break;
						}
						break;
					}

					case 5: {
						int _opc;
						do {
							do {
								System.out.println("----------------------------------- MENU -----------------------------------");
								System.out.println();
								System.out.println("1: Number of users");
								System.out.println("2: Number of resources");
								System.out.println("3: Number of allocations");
								System.out.println("4: Number of activities");
								System.out.println("5: Search users");
								System.out.println("6: Search resources");
								System.out.println("7: Quit");
								System.out.println();
								System.out.print("Type an entry:"); _opc = input.nextInt();
							} while (_opc < 1 || _opc > 7);

							switch (_opc) {
							case 1: {
								System.out.println("Number of users: "+ userController.numberOfUser());
								break;
							}
							case 2: {
								System.out.println("Number of allocations:");
								System.out.println("In allocation process:"+allocationController.numberOfAllocation(AllocationStatus.IN_ALLOCATION_PROCESS));
								System.out.println("In progress:"+allocationController.numberOfAllocation(AllocationStatus.IN_PROGRESS));
								System.out.println("Allocated:"+allocationController.numberOfAllocation(AllocationStatus.ALLOCATED));
								System.out.println("Complete:"+allocationController.numberOfAllocation(AllocationStatus.COMPLETE));
								break;
							}
							case 3: {
								System.out.println("Number of allocations: "+ allocationController.numberOfAllocation());
								break;
							}
							case 4: {
								System.out.println("Number of activities: "+ activityController.numberOfActivity());
								System.out.println("Class:"+activityController.numberOfActivity(ActivityKind.CLASS));
								System.out.println("Laboratory:"+activityController.numberOfActivity(ActivityKind.LABORATORY));
								System.out.println("Presentation:"+activityController.numberOfActivity(ActivityKind.PRESENTATION));
								break;
							}
							case 5: {

								System.out.println("User name:");
								input.nextLine();
								String name = input.nextLine();
								User userAux = userController.getUserByName(name);

								if (userAux != null) {
									System.out.println("----------------------------- Result query -----------------------------");
									System.out.println(userAux.toString());

									List<Allocation> allocations = allocationController.getAllocationByUser(userAux);
									for (Allocation allocation : allocations) {
										System.out.println(allocation.toString());
									}

									List<Activity> activities = activityController.getActivityByUser(userAux);
									for (Activity activity : activities) {
										System.out.println(activity.toString());
									}

								} else {
									System.out.println("Ivalid user: User doesn't exists");
								}

								break;
							}
							case 6: {
								System.out.println("Resource name:");
								input.nextLine();
								String name = input.nextLine();
								Resource resourceAux = resourceController.getResourceByName(name);

								if (resourceAux != null) {
									System.out.println("----------------------------- Result query -----------------------------");
									System.out.println(resourceAux.toString());

									List<Allocation> allocations = AllocationDatabase.getInstance().getAllocationByResource(resourceAux);
									for (Allocation allocation : allocations) {
										System.out.println(allocation.getUser().toString());
									}

									for (Allocation allocation : allocations) {
										System.out.println(allocation.getActivity().toString());
									}

								}

								break;
							}
							default:
								break;
							}
						} while (_opc != 7);
						break;
					}

					}
				} while (opc != 6);
			}
			default:
				break;
			}

			// Erasing input buffer
			input.nextLine();
		} while (!login.equals("quit"));
		input.close();
	}

}
