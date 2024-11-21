package Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import DAO.MessageDAO;
import DAO.UserDAO;
import Entities.Message;
import Entities.User;
import IDAO.IMessageDAO;
import IDAO.IUserDAO;

@WebServlet("/Chat")
public class Chat extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Chat() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("userId") == null) {
		    response.sendRedirect(request.getContextPath() + "/SignIn");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        int receiverId = Integer.parseInt(request.getParameter("receiverId"));

        IMessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getMessages(userId, receiverId);

        IUserDAO userdao = new UserDAO();
        User receiver = userdao.getUserById(receiverId);
        
        request.setAttribute("messages", messages);
        request.setAttribute("receiverId", receiverId);
        request.setAttribute("receiverName", receiver.getName());

		this.getServletContext().getRequestDispatcher("/WEB-INF/chat.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        int senderId = (int) session.getAttribute("userId");
        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        String messageText = request.getParameter("messageText");

        Message message = new Message(senderId, receiverId, messageText);
        IMessageDAO messageDAO = new MessageDAO();
        messageDAO.saveMessage(message);

        response.sendRedirect("Chat?receiverId=" + receiverId);
	}

}
