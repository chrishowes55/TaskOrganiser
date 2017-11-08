package org.liedssna;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Please Create a Task:");
    System.out.println("Date first please (DD/MM/YYYY)");
    String dateValues = scanner.nextLine().replaceAll("[^0-9]","");
    int[] dateArray = makeDateArray(dateValues);
    Calendar cal = new GregorianCalendar(dateArray[2], dateArray[1] - 1, dateArray[0]);
    Date deadline = cal.getTime();
    
    System.out.println("Thank you! Now please enter the name of the Task:");
    String taskName = scanner.nextLine();
    new Task(taskName, deadline);
  }

  private static int[] makeDateArray(String values) {
    String baseNums = "";
    int[] array = new int[3];
    int count = 0;
    for (int i = 0; i < values.length(); i++) {
      if(i == 0 || i == 2 || i == 4 || i == 5 || i == 6) {
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
}
