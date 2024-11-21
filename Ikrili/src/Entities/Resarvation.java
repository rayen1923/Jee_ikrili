package Entities;

import java.sql.Date;

public class Resarvation {
	private int Resarvation_id;
    private Date Resarvation_date;
    private String Type;
    private String Status;
    private Boolean IsRead;
    private int Owner_id;
    private int House_id;
    private int Student_id;

    public Resarvation (int id,Date date,String type,String status,int oid,int hid,int sid){
        Resarvation_id = id;
        Resarvation_date = date;
        Type = type;
        Status = status;
        IsRead = false;
        Owner_id = oid;
        House_id = hid;
        Student_id = sid;
    }

    public Resarvation(int resarvation_id2, Date resarvation_date2, String type2, String status2, int isRead2,
			int owner_id2, int house_id2, int student_id2) {
    	Resarvation_id = resarvation_id2;
        Resarvation_date = resarvation_date2;
        Type = type2;
        Status = status2;
        IsRead = false;
        if(isRead2==1) {
            IsRead = true;
        }
        Owner_id = owner_id2;
        House_id = house_id2;
        Student_id = student_id2;
	}

	public int getHouse_id() {
        return House_id;
    }

    public void setHouse_id(int house_id) {
        House_id = house_id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getStudent_id() {
        return Student_id;
    }

    public void setStudent_id(int student_id) {
        Student_id = student_id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getResarvation_id() {
        return Resarvation_id;
    }

    public void setResarvation_id(int resarvation_id) {
        Resarvation_id = resarvation_id;
    }

    public Date getResarvation_date() {
        return Resarvation_date;
    }

    public void setResarvation_date(Date resarvation_date) {
        Resarvation_date = resarvation_date;
    }

    public int getOwner_id() {
        return Owner_id;
    }

    public void setOwner_id(int owner_id) {
        Owner_id = owner_id;
    }

    public Boolean getRead() {
        return IsRead;
    }

    public void setRead(Boolean read) {
        IsRead = read;
    }
}
