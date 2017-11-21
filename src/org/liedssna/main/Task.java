package org.liedssna.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class Task {
	private String name;
	private Calendar cal;

	public Task(String name, Calendar cal) {
		this.name = singleWord(name);
		this.cal = cal;
	}

	public String getName() {
		return name;
	}

	public Calendar getDeadline() {
		return cal;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDeadline(Calendar cal) {
		this.cal = cal;
	}

	private String singleWord(String input) {
		input = input.toLowerCase();
		if(input != null) {
			char firstChar = input.charAt(0);
			String result = "";
	        result += Character.toUpperCase(firstChar);
	        for (int i = 1; i < input.length(); i++) {
	            char currentChar = input.charAt(i);
	            char previousChar = input.charAt(i - 1);
	            if (previousChar == ' ') {
	                result = result + Character.toUpperCase(currentChar);
	            } else {
	                result = result + currentChar;
	            }
	        }
	        result = result.replaceAll("[^A-Z,a-z]", "");
	        return result;
		}
		return "ThatWasNotAGoodName";
	}
	
	public boolean exists() {
		File file = new File("/tmp/taskOrganiser/task.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				if (line.substring(0, this.getName().length()).equals(this.getName())) {
					return true; // exists
				}
			}
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
		return false;
	}

}
