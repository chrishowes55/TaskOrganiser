package org.liedssna.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class TaskListMaster {

	private ArrayList<Task> tasks = new ArrayList<Task>();

	public void start() {
		Scanner scanner = new Scanner(System.in);

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
				if (line.substring(0, task.getName().length()).equals(task.getName())) {
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
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		tasks.add(task);
		return 0;
	}
}
