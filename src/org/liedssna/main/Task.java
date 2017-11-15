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

	private String singleWord(String init) {
		if (init == null)
			return null;

		final StringBuilder ret = new StringBuilder(init.length());

		for (final String word : init.split(" ")) {
			if (!word.isEmpty()) {
				ret.append(word.substring(0, 1).toUpperCase());
				ret.append(word.substring(1).toLowerCase());
			}
			if (!(ret.length() == init.length()))
				ret.append(" ");
		}
		String result = ret.toString();
		result = result.replaceAll("[^A-Za-z]", "");

		return result;
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
