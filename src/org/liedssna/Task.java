package org.liedssna;

import java.util.Date;

public class Task {
  private String name;
  private Date date;

  public Task(String name, Date date) {
    this.name = name;
    this.date = date;
  }
  
  public String getName() {
    return name;
  }
  
  public Date getDeadline() {
    return date;
  }
  
  public void setName(String name) {
    this.name = name
  }
  
  public void setDeadline(Date date) {
    this.date = date;
  }
}
