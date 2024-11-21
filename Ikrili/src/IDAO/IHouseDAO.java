package IDAO;

import java.util.ArrayList;

import Entities.House;

public interface IHouseDAO {

	void addHouse(House house);

	ArrayList<House> GetHousesByOwner(int userId);

	ArrayList<House> GetHouses();

	ArrayList<House> GetHouseByID(int houseid);

	void deleteHouseById(int houseId);

	void updateHouse(House house);

	void removeStudentFromHouse(int houseId, int studentId);

	void removeStudentFromHouse(int studentId);

	ArrayList<House> GetHousesByAddress(String searchAddress);

}
