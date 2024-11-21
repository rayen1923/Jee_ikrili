package IDAO;

import java.util.List;

public interface IMessageDAO {

	List<Entities.Message> getMessages(int userId, int receiverId);

	void saveMessage(Entities.Message message);

	List<Integer> getUsersChatWith(int userId);

}
