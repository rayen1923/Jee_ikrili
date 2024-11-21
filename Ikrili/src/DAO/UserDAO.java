package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.dbc;
import Entities.User;
import IDAO.IUserDAO;

public class UserDAO implements IUserDAO{
	public UserDAO() {
		
	}

	@Override
	public void signup(User user) {
	    String query = "INSERT INTO Users (Name, Mail, Phone, Password, Img_url, Gender) VALUES (?, ?, ?, ?, ?, ?)";
	    try (Connection conn = dbc.createConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, user.getName());
	        stmt.setString(2, user.getMail());
	        stmt.setString(3, user.getPhone());
	        stmt.setString(4, user.getPassword());
	        stmt.setString(5, user.getImg_url());
	        stmt.setString(6, user.getGender());

	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public int signin(String email, String password) {
		String query = "SELECT user_id FROM Users WHERE Mail = ? AND Password = ?";
        try (Connection conn = dbc.createConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
	}

	@Override
	public User getUserById(int userId) {
	    String query = "SELECT * FROM Users WHERE user_id = ?";
	    try (Connection conn = dbc.createConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            User user = new User();
	            user.setUser_id(rs.getInt("user_id"));
	            user.setName(rs.getString("Name"));
	            user.setMail(rs.getString("Mail"));
	            user.setPhone(rs.getString("Phone"));
	            user.setPassword(rs.getString("Password"));
	            user.setImg_url(rs.getString("Img_url"));
	            user.setGender(rs.getString("Gender"));
	            return user;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; 
	}

	@Override
	public void updateUser(User updatedUser) {
	    String query = "UPDATE Users SET Name = ?, Mail = ?, Phone = ?, Password = ?, Img_url = ?, Gender = ? WHERE user_id = ?";
	    try (Connection conn = dbc.createConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, updatedUser.getName());
	        stmt.setString(2, updatedUser.getMail());
	        stmt.setString(3, updatedUser.getPhone());
	        stmt.setString(4, updatedUser.getPassword());
	        stmt.setString(5, updatedUser.getImg_url());
	        stmt.setString(6, updatedUser.getGender());
	        stmt.setInt(7, updatedUser.getUser_id());

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("User updated successfully.");
	        } else {
	            System.out.println("No user found with the given ID.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
