package com.fordays.masssending.system._entity;



/**
 * TaskTimestamp generated by hbm2java
 */


public class _TaskTimestamp 

  extends org.apache.struts.action.ActionForm implements Cloneable
 {
	private static final long serialVersionUID = 1L;

    // Fields    

     protected long id;
     protected Long taskType;
     protected java.sql.Timestamp taskDate;
     protected Long taskHour;
     protected Long count;
     protected Long status;
     protected String no;

     // Constructors
   
    // Property accessors


    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    


    public Long getTaskType() {
        return this.taskType;
    }
    
    public void setTaskType(Long taskType) {
        this.taskType = taskType;
    }
    


    public java.sql.Timestamp getTaskDate() {
        return this.taskDate;
    }
    
    public void setTaskDate(java.sql.Timestamp taskDate) {
        this.taskDate = taskDate;
    }
    


    public Long getTaskHour() {
        return this.taskHour;
    }
    
    public void setTaskHour(Long taskHour) {
        this.taskHour = taskHour;
    }
    


    public Long getCount() {
        return this.count;
    }
    
    public void setCount(Long count) {
        this.count = count;
    }
    


    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    


    public String getNo() {
        return this.no;
    }
    
    public void setNo(String no) {
        this.no = no;
    }
    




  // The following is extra code specified in the hbm.xml files

  public Object clone()
  {
    Object o = null;
    try
    {
      o = super.clone();
    }
    catch (CloneNotSupportedException e)
    {
      e.printStackTrace();
    }
    return o;
}

private String thisAction="";
 public String getThisAction()
 {
     return thisAction;
 }


public void setThisAction(String thisAction)
 {
     this.thisAction=thisAction;
 }

private int index=0;
 public int getIndex()
 {
     return index;
 }


public void setIndex(int index)
 {
     this.index=index;
 }
 





  // end of extra code specified in the hbm.xml files


}


