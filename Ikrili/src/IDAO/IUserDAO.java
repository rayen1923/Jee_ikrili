package IDAO;

import Entities.User;

public interface IUserDAO {

	void signup(User newUser);

	int signin(String email, String password);

	User getUserById(int userId);

	void updateUser(User updatedUser);

}
