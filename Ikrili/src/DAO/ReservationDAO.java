package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import Connection.dbc;
import Entities.Resarvation;
import IDAO.IReservationDAO;

public class ReservationDAO implements IReservationDAO {

    @Override
    public boolean ReserverPlace(int ownerId, int houseId, int studentId) {
        String query = "INSERT INTO reservations (Resarvation_date, Type, Status, IsRead, Owner_id, House_id, Student_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbc.createConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setDate(1, new Date(System.currentTimeMillis()));
            stmt.setString(2, "one place");
            stmt.setString(3, "Pending");
            stmt.setBoolean(4, false);
            stmt.setInt(5, ownerId);
            stmt.setInt(6, houseId);
            stmt.setInt(7, studentId);

            int rowsInserted = stmt.executeUpdate();
            
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public boolean ReserverAll(int ownerId, int houseId, int studentId) {
		String query = "INSERT INTO reservations (Resarvation_date, Type, Status, IsRead, Owner_id, House_id, Student_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbc.createConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setDate(1, new Date(System.currentTimeMillis()));
            stmt.setString(2, "All places");
            stmt.setString(3, "Pending");
            stmt.setBoolean(4, false);
            stmt.setInt(5, ownerId);
            stmt.setInt(6, houseId);
            stmt.setInt(7, studentId);

            int rowsInserted = stmt.executeUpdate();
            
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public List<Resarvation> getUserReservations(Integer userId) {
		String query = "SELECT * FROM reservations WHERE Owner_id  = ?";
		List<Resarvation> ress = new ArrayList<>();
		try (Connection conn = dbc.createConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	int Resarvation_id = rs.getInt("Resarvation_id");
            	Date Resarvation_date = rs.getDate("Resarvation_date");
            	String 	Type = rs.getString("Type");
            	String 	Status = rs.getString("Status");
            	int IsRead = rs.getInt("IsRead");
            	int Owner_id = rs.getInt("Owner_id");
            	int House_id = rs.getInt("House_id");
            	int Student_id = rs.getInt("Student_id");
            	
            	Resarvation res = new Resarvation(Resarvation_id,Resarvation_date,Type,Status,IsRead,Owner_id,House_id,Student_id);
            	ress.add(res);
            }
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		return ress;
	}

	@Override
	public void updateStatus(int reservationId, String status) {
	    String queryUpdateReservation = "UPDATE reservations SET Status = ?, IsRead = ? WHERE Resarvation_id = ?";
	    String queryFetchReservation = "SELECT Type, House_id FROM reservations WHERE Resarvation_id = ?";
	    String queryUpdateHouse = "UPDATE houses SET Nb_place_oc = ? WHERE House_id = ?";

	    try (Connection conn = dbc.createConnection();
	         PreparedStatement psReservation = conn.prepareStatement(queryUpdateReservation);
	         PreparedStatement psFetch = conn.prepareStatement(queryFetchReservation);
	         PreparedStatement psUpdateHouse = conn.prepareStatement(queryUpdateHouse)) {
	        
	        psReservation.setString(1, status);
	        psReservation.setInt(2, 1);
	        psReservation.setInt(3, reservationId);
	        psReservation.executeUpdate();

	        psFetch.setInt(1, reservationId);
	        ResultSet rs = psFetch.executeQuery();
	        
	        if (rs.next()) {
	            String type = rs.getString("Type");
	            int houseId = rs.getInt("House_id");

	            String queryGetCurrentNbPlace = "SELECT Nb_place, Nb_place_oc FROM houses WHERE House_id = ?";
	            try (PreparedStatement psGetNbPlace = conn.prepareStatement(queryGetCurrentNbPlace)) {
	                psGetNbPlace.setInt(1, houseId);
	                ResultSet rsHouse = psGetNbPlace.executeQuery();

	                if (rsHouse.next()) {
	                    int nbPlace = rsHouse.getInt("Nb_place");
	                    int nbPlaceOc = rsHouse.getInt("Nb_place_oc");
	                    
	                    if ("All places".equalsIgnoreCase(type)) {
	                        nbPlaceOc = nbPlace;
	                    } else if ("one place".equalsIgnoreCase(type)) {
	                        nbPlaceOc++;
	                    }
	                    
	                    psUpdateHouse.setInt(1, nbPlaceOc);
	                    psUpdateHouse.setInt(2, houseId);
	                    psUpdateHouse.executeUpdate();
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public Resarvation getReservationById(int reservationId) {
		String query = "SELECT * FROM reservations WHERE Resarvation_id = ?";
	    try (Connection conn = dbc.createConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setInt(1, reservationId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return new Resarvation(
	                rs.getInt("Resarvation_id"),
	                rs.getDate("Resarvation_date"),
	                rs.getString("Type"),
	                rs.getString("Status"),
	                rs.getInt("IsRead"),
	                rs.getInt("Owner_id"),
	                rs.getInt("House_id"),
	                rs.getInt("Student_id")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	@Override
	public boolean addToHouseStudents(int houseId, int studentId) {
		String query = "INSERT INTO House_Students (House_id, User_id) VALUES (?, ?)";
	    try (Connection conn = dbc.createConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setInt(1, houseId);
	        stmt.setInt(2, studentId);
	        int rowsInserted = stmt.executeUpdate();
	        
	        return rowsInserted > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
