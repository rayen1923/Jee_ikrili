package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.dbc;
import Entities.Message;
import IDAO.IMessageDAO;

public class MessageDAO implements IMessageDAO{
	public MessageDAO() {
		
	}

	@Override
	public List<Entities.Message> getMessages(int userId, int receiverId) {
        String query = "SELECT * FROM Messages WHERE (Sender_id = ? AND Receiver_id = ?) OR (Sender_id = ? AND Receiver_id = ?) ORDER BY Timestamp";
        List<Message> messages = new ArrayList<>();

	    try (Connection conn = dbc.createConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	    	stmt.setInt(1, userId);
            stmt.setInt(2, receiverId);
            stmt.setInt(3, receiverId);
            stmt.setInt(4, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("Message_id"),
                    rs.getInt("Sender_id"),
                    rs.getInt("Receiver_id"),
                    rs.getString("Message_text"),
                    rs.getTimestamp("Timestamp")
                );
                messages.add(message);
            }
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
        return messages;
	}

	@Override
	public void saveMessage(Message message) {
        String query = "INSERT INTO Messages (Sender_id, Receiver_id, Message_text) VALUES (?, ?, ?)";

	    try (Connection conn = dbc.createConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	    	stmt.setInt(1, message.getSenderId());
            stmt.setInt(2, message.getReceiverId());
            stmt.setString(3, message.getText());

            stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public List<Integer> getUsersChatWith(int userId) {
	    List<Integer> chatUsers = new ArrayList<>();
	    String query = "SELECT DISTINCT " +
	                   "  CASE " +
	                   "    WHEN Sender_id = ? THEN Receiver_id " +
	                   "    ELSE Sender_id " +
	                   "  END AS ChatUserId " +
	                   "FROM Messages " +
	                   "WHERE Sender_id = ? OR Receiver_id = ?";

	    try (Connection conn = dbc.createConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setInt(1, userId);
	        stmt.setInt(2, userId);
	        stmt.setInt(3, userId);

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            chatUsers.add(rs.getInt("ChatUserId")); // Ensure correct column name
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return chatUsers;
	}
}
