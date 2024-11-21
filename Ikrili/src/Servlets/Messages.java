package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.MessageDAO;
import DAO.UserDAO;
import Entities.User;
import IDAO.IMessageDAO;
import IDAO.IUserDAO;

@WebServlet("/Messages")
public class Messages extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Messages() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("userId") == null) {
		    response.sendRedirect(request.getContextPath() + "/SignIn");
            return;
        }
        int userId = (int) session.getAttribute("userId");

        IMessageDAO messageDAO = new MessageDAO();
        List<Integer> chatUserIds = messageDAO.getUsersChatWith(userId);

        IUserDAO userDAO = new UserDAO();
        List<User> chatUsers = new ArrayList<>();
        for (int chatUserId : chatUserIds) {
            User user = userDAO.getUserById(chatUserId);
            chatUsers.add(user);
        }

        request.setAttribute("chatUsers", chatUsers); 
		this.getServletContext().getRequestDispatcher("/WEB-INF/messages.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
