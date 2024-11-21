package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Connection.dbc;
import Entities.House;
import Entities.User;
import IDAO.IHouseDAO;

public class HouseDAO implements IHouseDAO {

    @Override
    public void addHouse(House house) {
        String query1 = "INSERT INTO Houses (Adress, Description, Nb_place, Place_prix, Owner_id) VALUES (?, ?, ?, ?, ?)";
        String query2 = "INSERT INTO House_Images (House_id, Img_url) VALUES (?, ?)";

        try (Connection conn = dbc.createConnection();
             PreparedStatement stmt1 = conn.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmt2 = conn.prepareStatement(query2)) {

            stmt1.setString(1, house.getAdress());
            stmt1.setString(2, house.getDescription());
            stmt1.setInt(3, house.getNb_place());
            stmt1.setFloat(4, house.getPlace_prix());
            stmt1.setInt(5, house.getOwner_id());
            stmt1.executeUpdate();

            ResultSet rs = stmt1.getGeneratedKeys();
            int houseId = 0;
            if (rs.next()) {
                houseId = rs.getInt(1);
                System.out.println("Generated House ID: " + houseId);
            } else {
                throw new SQLException("Failed to retrieve house ID.");
            }

            for (String imagePath : house.getImgs()) {
                stmt2.setInt(1, houseId);
                stmt2.setString(2, imagePath);
                stmt2.addBatch();
            }
            int[] updateCounts = stmt2.executeBatch();
            System.out.println("Batch update counts: " + updateCounts.length);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<House> GetHousesByOwner(int userId) {
        ArrayList<House> houses = new ArrayList<>();
        String query = "SELECT h.House_id, h.Adress, h.Description, h.Nb_place, h.Nb_place_oc, " +
                       "h.Place_prix, h.Owner_id, " +
                       "GROUP_CONCAT(DISTINCT i.Img_url) AS images, " +
                       "GROUP_CONCAT(DISTINCT u.User_id, ':', u.Name SEPARATOR ';') AS students " +
                       "FROM Houses h " +
                       "LEFT JOIN House_Images i ON h.House_id = i.House_id " +
                       "LEFT JOIN House_Students hs ON h.House_id = hs.House_id " +
                       "LEFT JOIN Users u ON hs.User_id = u.User_id " +
                       "WHERE h.Owner_id = ? " +
                       "GROUP BY h.House_id";

        try (Connection conn = dbc.createConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int houseId = rs.getInt("House_id");
                String address = rs.getString("Adress");
                String description = rs.getString("Description");
                int nbPlace = rs.getInt("Nb_place");
                int nbPlaceOc = rs.getInt("Nb_place_oc");
                float placePrix = rs.getFloat("Place_prix");
                int ownerId = rs.getInt("Owner_id");

                String imagesStr = rs.getString("images");
                ArrayList<String> images = new ArrayList<>();
                if (imagesStr != null && !imagesStr.isEmpty()) {
                    String[] imgArray = imagesStr.split(",");
                    for (String img : imgArray) {
                        images.add(img.trim());
                    }
                }

                String studentsStr = rs.getString("students");
                ArrayList<User> students = new ArrayList<>();
                if (studentsStr != null && !studentsStr.isEmpty()) {
                    String[] studentArray = studentsStr.split(";");
                    for (String student : studentArray) {
                        String[] studentData = student.split(":");
                        int studentId = Integer.parseInt(studentData[0]);
                        String studentName = studentData[1];
                        students.add(new User(studentId, studentName));
                    }
                }

                House house = new House(houseId, address, description, nbPlace, placePrix, ownerId, images, students);
                house.setNb_place_oc(nbPlaceOc);
                houses.add(house);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return houses;
    }

    @Override
    public ArrayList<House> GetHouses() {
        ArrayList<House> houses = new ArrayList<>();
        String query = "SELECT h.House_id, h.Adress, h.Description, h.Nb_place, h.Nb_place_oc, h.Place_prix, " +
                       "h.Owner_id, " +
                       "COUNT(DISTINCT hs.User_id) AS StudentCount, " +
                       "(SELECT Img_url FROM House_Images WHERE House_id = h.House_id LIMIT 1) AS FirstImage " +
                       "FROM Houses h " +
                       "LEFT JOIN House_Students hs ON h.House_id = hs.House_id " +
                       "GROUP BY h.House_id";

        try (Connection conn = dbc.createConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int houseId = rs.getInt("House_id");
                String address = rs.getString("Adress");
                String description = rs.getString("Description");
                int nbPlace = rs.getInt("Nb_place");
                int nbPlaceOc = rs.getInt("Nb_place_oc");
                float placePrix = rs.getFloat("Place_prix");
                int ownerId = rs.getInt("Owner_id");
                int studentCount = rs.getInt("StudentCount");
                String firstImage = rs.getString("FirstImage");

                House house = new House();
                house.setHouse_id(houseId);
                house.setAdress(address);
                house.setDescription(description);
                house.setNb_place(nbPlace);
                house.setNb_place_oc(nbPlaceOc);
                house.setPlace_prix(placePrix);
                house.setOwner_id(ownerId);

                ArrayList<User> students = new ArrayList<>();
                for (int i = 0; i < studentCount; i++) {
                    students.add(new User()); 
                }
                house.setStudents(students);

                ArrayList<String> images = new ArrayList<>();
                if (firstImage != null) {
                    images.add(firstImage);
                }
                house.setImgs(images);

                houses.add(house);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return houses;
    }

    @Override
    public ArrayList<House> GetHouseByID(int houseId) {
        ArrayList<House> houses = new ArrayList<>();
        String query = "SELECT h.House_id, h.Adress, h.Description, h.Nb_place, h.Nb_place_oc, h.Place_prix, " +
                       "h.Owner_id, " +
                       "GROUP_CONCAT(DISTINCT i.Img_url) AS images, " +
                       "GROUP_CONCAT(DISTINCT u.User_id, ':', u.Name SEPARATOR ';') AS students " +
                       "FROM Houses h " +
                       "LEFT JOIN House_Images i ON h.House_id = i.House_id " +
                       "LEFT JOIN House_Students hs ON h.House_id = hs.House_id " +
                       "LEFT JOIN Users u ON hs.User_id = u.User_id " +
                       "WHERE h.House_id = ? " +
                       "GROUP BY h.House_id";

        try (Connection conn = dbc.createConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, houseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("House_id");
                String address = rs.getString("Adress");
                String description = rs.getString("Description");
                int nbPlace = rs.getInt("Nb_place");
                int nbPlaceOc = rs.getInt("Nb_place_oc");
                float placePrix = rs.getFloat("Place_prix");
                int ownerId = rs.getInt("Owner_id");

                String imagesStr = rs.getString("images");
                ArrayList<String> images = new ArrayList<>();
                if (imagesStr != null && !imagesStr.isEmpty()) {
                    String[] imgArray = imagesStr.split(",");
                    for (String img : imgArray) {
                        images.add(img.trim());
                    }
                }

                String studentsStr = rs.getString("students");
                ArrayList<User> students = new ArrayList<>();
                if (studentsStr != null && !studentsStr.isEmpty()) {
                    String[] studentArray = studentsStr.split(";");
                    for (String student : studentArray) {
                        String[] studentData = student.split(":");
                        int studentId = Integer.parseInt(studentData[0]);
                        String studentName = studentData[1];
                        students.add(new User(studentId, studentName));
                    }
                }

                House house = new House(id, address, description, nbPlace, placePrix, ownerId, images, students);
                house.setNb_place_oc(nbPlaceOc);
                houses.add(house);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return houses;
    }

	@Override
	public void deleteHouseById(int houseId) {
	    String deleteImagesQuery = "DELETE FROM House_Images WHERE House_id = ?";
	    String deleteStudentsQuery = "DELETE FROM House_Students WHERE House_id = ?";
	    String deleteHouseQuery = "DELETE FROM Houses WHERE House_id = ?";

	    try (Connection conn = dbc.createConnection();
	         PreparedStatement stmtImages = conn.prepareStatement(deleteImagesQuery);
	         PreparedStatement stmtStudents = conn.prepareStatement(deleteStudentsQuery);
	         PreparedStatement stmtHouse = conn.prepareStatement(deleteHouseQuery)) {

	        stmtImages.setInt(1, houseId);
	        stmtImages.executeUpdate();

	        stmtStudents.setInt(1, houseId);
	        stmtStudents.executeUpdate();

	        stmtHouse.setInt(1, houseId);
	        stmtHouse.executeUpdate();

	        System.out.println("House with ID " + houseId + " deleted successfully.");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void updateHouse(House house) {
		String query = "UPDATE Houses SET Adress = ?, Description = ?, Nb_place = ?, Place_prix = ? WHERE House_id = ?";

	    try (Connection conn = dbc.createConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, house.getAdress());
	        stmt.setString(2, house.getDescription());
	        stmt.setInt(3, house.getNb_place());
	        stmt.setFloat(4, house.getPlace_prix());
	        stmt.setInt(5, house.getHouse_id());

	        int rowsUpdated = stmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("House updated successfully!");
	        } else {
	            System.out.println("No house found with the given ID.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void removeStudentFromHouse(int houseId, int studentId) {
	    String removeStudentQuery = "DELETE FROM house_students WHERE House_id = ? AND User_id = ?";
	    String updateHouseQuery = "UPDATE houses SET Nb_place_oc = Nb_place_oc - 1 WHERE house_id = ?";

	    try (Connection conn = dbc.createConnection();
	         PreparedStatement removeStmt = conn.prepareStatement(removeStudentQuery);
	         PreparedStatement updateStmt = conn.prepareStatement(updateHouseQuery)) {

	        removeStmt.setInt(1, houseId);
	        removeStmt.setInt(2, studentId);

	        int rowsAffected = removeStmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Student successfully removed from the house.");
	        } else {
	            System.out.println("No student found with the given ID in this house.");
	        }

	        updateStmt.setInt(1, houseId);

	        int updateRowsAffected = updateStmt.executeUpdate();
	        if (updateRowsAffected > 0) {
	            System.out.println("House's occupied places updated successfully.");
	        } else {
	            System.out.println("Failed to update the house occupied places.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void removeStudentFromHouse(int studentId) {
	    String removeStudentQuery = "DELETE FROM House_Students WHERE User_id = ?";
	    String updateHouseQuery = "UPDATE Houses SET Nb_place_oc = Nb_place_oc - 1 " +
	                              "WHERE House_id IN (SELECT House_id FROM House_Students WHERE User_id = ?)";

	    try (Connection conn = dbc.createConnection();
	         PreparedStatement updateStmt = conn.prepareStatement(updateHouseQuery);
	         PreparedStatement removeStmt = conn.prepareStatement(removeStudentQuery)) {

	        updateStmt.setInt(1, studentId);
	        int updatedRows = updateStmt.executeUpdate();
	        System.out.println(updatedRows + " houses updated for occupied places.");

	        removeStmt.setInt(1, studentId);
	        int removedRows = removeStmt.executeUpdate();
	        System.out.println(removedRows + " entries removed from House_Students table for student ID " + studentId);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public ArrayList<House> GetHousesByAddress(String searchAddress) {
		ArrayList<House> houses = new ArrayList<>();
		String query = "SELECT h.House_id, h.Adress, h.Description, h.Nb_place, h.Nb_place_oc, h.Place_prix, " +
	               "h.Owner_id, " +
	               "COUNT(DISTINCT hs.User_id) AS StudentCount, " +
	               "(SELECT Img_url FROM House_Images WHERE House_id = h.House_id LIMIT 1) AS FirstImage " +
	               "FROM Houses h " +
	               "LEFT JOIN House_Students hs ON h.House_id = hs.House_id " +
	               "WHERE h.Adress LIKE ? " +
	               "GROUP BY h.House_id";

        try (Connection conn = dbc.createConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + searchAddress + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int houseId = rs.getInt("House_id");
                String address = rs.getString("Adress");
                String description = rs.getString("Description");
                int nbPlace = rs.getInt("Nb_place");
                int nbPlaceOc = rs.getInt("Nb_place_oc");
                float placePrix = rs.getFloat("Place_prix");
                int ownerId = rs.getInt("Owner_id");
                int studentCount = rs.getInt("StudentCount");
                String firstImage = rs.getString("FirstImage");

                House house = new House();
                house.setHouse_id(houseId);
                house.setAdress(address);
                house.setDescription(description);
                house.setNb_place(nbPlace);
                house.setNb_place_oc(nbPlaceOc);
                house.setPlace_prix(placePrix);
                house.setOwner_id(ownerId);

                ArrayList<User> students = new ArrayList<>();
                for (int i = 0; i < studentCount; i++) {
                    students.add(new User()); 
                }
                house.setStudents(students);

                ArrayList<String> images = new ArrayList<>();
                if (firstImage != null) {
                    images.add(firstImage);
                }
                house.setImgs(images);

                houses.add(house);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return houses;
	}



}
