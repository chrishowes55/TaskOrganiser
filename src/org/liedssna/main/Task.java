package org.liedssna.main;

import java.util.Date;

public class Task {
  private String name;
  private Date date;

  public Task(String name, Date date) {
    this.name = singleWord(name);
    this.date = date;
  }
  
  public String getName() {
    return name;
  }
  
  public Date getDeadline() {
    return date;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setDeadline(Date date) {
    this.date = date;
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
