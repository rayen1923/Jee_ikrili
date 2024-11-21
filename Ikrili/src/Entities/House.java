package Entities;

import java.util.ArrayList;

public class House {
    private int House_id;
    private String Adress;
    private String Description;
    private int Nb_place;
    private int Nb_place_oc;
    private float Place_prix;
    private int Owner_id;
	private ArrayList<String> Imgs;
    private ArrayList<User> Students;
    
    public House() {
		
	}
    
	public House(int house_id, String adress, String description, int nb_place, float place_prix,int owner_id, ArrayList<String> imgs, ArrayList<User> students) {
		House_id = house_id;
		Adress = adress;
		Description = description;
		Nb_place = nb_place;
		Nb_place_oc = 0;
		Place_prix = place_prix;
		Owner_id = owner_id;
		Imgs = imgs;
		Students = students;
	}

	public int getHouse_id() {
		return House_id;
	}

	public void setHouse_id(int house_id) {
		House_id = house_id;
	}

	public String getAdress() {
		return Adress;
	}

	public void setAdress(String adress) {
		Adress = adress;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getNb_place() {
		return Nb_place;
	}

	public void setNb_place(int nb_place) {
		Nb_place = nb_place;
	}

	public int getNb_place_oc() {
		return Nb_place_oc;
	}

	public void setNb_place_oc(int nb_place_oc) {
		Nb_place_oc = nb_place_oc;
	}

	public float getPlace_prix() {
		return Place_prix;
	}

	public void setPlace_prix(float place_prix) {
		Place_prix = place_prix;
	}

	public int getOwner_id() {
		return Owner_id;
	}

	public void setOwner_id(int owner_id) {
		Owner_id = owner_id;
	}


	public ArrayList<String> getImgs() {
	    return Imgs;
	}

	public void setImgs(ArrayList<String> imgs) {
	    Imgs = imgs;
	}

	public ArrayList<User> getStudents() {
		return Students;
	}

	public void setStudents(ArrayList<User> students) {
		Students = students;
	}
}
