package IDAO;

import java.util.List;

import Entities.Resarvation;

public interface IReservationDAO {

	boolean ReserverPlace(int ownerId,int houseId, int studentId);

	boolean ReserverAll(int ownerId, int houseId, int studentId);

	List<Resarvation> getUserReservations(Integer userId);

	void updateStatus(int reservationId, String string);

	Resarvation getReservationById(int reservationId);

	boolean addToHouseStudents(int houseId, int studentId);

}
