package org.liedssna.main;

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
	    if (init==null)
	        return null;

	    final StringBuilder ret = new StringBuilder(init.length());

	    for (final String word : init.split(" ")) {
	        if (!word.isEmpty()) {
	            ret.append(word.substring(0, 1).toUpperCase());
	            ret.append(word.substring(1).toLowerCase());
	        }
	        if (!(ret.length()==init.length()))
	            ret.append(" ");
	    }
	    String result = ret.toString();
	    result = result.replaceAll("[^A-Za-z]", "");

	    return result;
	}
}
