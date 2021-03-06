package org.liedssna.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class TaskListMaster {

	public void start() {
		Scanner scanner = new Scanner(System.in);

		boolean running = true;

		while (running) {
			System.out.println("What would you like to do? Enter 0 to exit, 1 for Task Creation, 2 for Task Deletion, or 3 for a list of tasks");
			int input = Integer.parseInt(scanner.nextLine());

			switch (input) {
			case 0:
				running = false;
				break;

			case 1:
				System.out.println("Please Create a Task:");
				System.out.println("Date first please (DD/MM/YYYY)");
				String dateValues = scanner.nextLine().replaceAll("[^0-9]", "");
				int[] dateArray = makeDateArray(dateValues);
				Calendar cal = new GregorianCalendar(dateArray[2], dateArray[1] - 1, dateArray[0]);

				System.out.println("Thank you! Now please enter the name of the Task:");
				String taskName = scanner.nextLine();
				int worked = onTaskCreation(new Task(taskName, cal));
				if (worked == 1) {
					System.out.println("Please try again: That name is taken");
				}
				break;

			case 2:
				System.out.println("Enter the name of the task you wish to delete");
				String name = scanner.next();
				onTaskDeletion(findTask(name));
				break;
				
			case 3:
				System.out.println(getListOfTasks());
				break;

			default:
				System.out.println("Invalid choice");
				break;
			}
		}

		scanner.close();
	}

	private int[] makeDateArray(String values) {
		String baseNums = "";
		int[] array = new int[3];
		int count = 0;
		for (int i = 0; i < values.length(); i++) {
			if (i == 0 || i == 2 || i == 4 || i == 5 || i == 6) {
				baseNums += values.charAt(i);
			} else {
				baseNums += values.charAt(i);
				array[count] = Integer.parseInt(baseNums);
				baseNums = "";
				count++;
			}
		}
		return array;
	}

	private int onTaskCreation(Task task) {
		File file = new File("/tmp/taskOrganiser/task.txt");
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				if (line.substring(0, line.indexOf("_")).equals(task.getName())) {
					return 1; // already a task with that name
				}
			}

			PrintWriter out = new PrintWriter(new FileWriter(file, true));

			out.append(task.getName() + "_" + task.getDeadline().get(Calendar.DAY_OF_MONTH) + "_"
					+ (task.getDeadline().get(Calendar.MONTH) + 1) + "_" + task.getDeadline().get(Calendar.YEAR)
					+ "\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	private Task findTask(String name) {
		File file = new File("/tmp/taskOrganiser/task.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				if (line.length() > name.length() && line.substring(0, name.length() - 1).equals(name)) {
					String month; // gets defined later
					// read day
					String day = line.substring(name.length() + 1, (name.length() + 3) + 1 /* endIndex + 1 */);
					String origMonth = line.substring(name.length() + 4, (name.length() + 5) + 1);
					// month has no 0
					if (origMonth.contains("_")) {
						month = "0" + origMonth.replaceAll("[^0-9]", "");
					} else {
						month = origMonth;
					}
					String year = line.substring(name.length() + 6);

					String full = (day + month + year).replaceAll("[^0-9]", "");

					int[] date = makeDateArray(full);

					return new Task(name, new GregorianCalendar(date[2], date[1], date[0]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void onTaskDeletion(Task task) {
		if (task != null && task.exists()) {
			System.out.println("Task isn't null and exists");
			File file = new File("/tmp/taskOrganiser/task.txt");
			File tempFile = new File("/tmp/taskOrganiser/tempTask.txt");
			BufferedReader br = null;
			try {
				System.out.println("In try");
				br = new BufferedReader(new FileReader(file));
				for (String line = br.readLine(); line != null; line = br.readLine()) {
					System.out.println("for");
					if (!line.substring(0, line.indexOf("_")).equals(task.getName())) {
						System.out.println("for if");
						BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
						String trimmedLine = line.trim();
					    bw.write(trimmedLine + System.getProperty("line.separator"));
						bw.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				System.out.println("finally");
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				tempFile.renameTo(file);
			}
		}
	}
	
	public String getListOfTasks() {
		File file = new File("/tmp/taskOrganiser/task.txt");
		if (!file.exists()) {
			return "The requested file does not exist";
		}
		BufferedReader br = null;
		try {
			StringBuilder sb = new StringBuilder();
			br = new BufferedReader(new FileReader(file));
			for (String line = br.readLine(); line!=null; line = br.readLine()) {
				String[] stuff = line.split("_");
				sb.append("Name: " + toNormal(stuff[0]) + " Deadline: " + stuff[1] + "/" + stuff[2] + "/" + stuff[3] + "\n");
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "Something went wrong :-(";
	}
	
	private String toNormal(String input) {
		String result = "";
		for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (i == 0) {
				result = result + currentChar;
			}
            else if (currentChar == Character.toUpperCase(currentChar)) {
                result = result + " " + currentChar;
            }
            else {
                result = result + currentChar;
            }
        }
		return result;
	}
}
