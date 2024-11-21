package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.HouseDAO;
import IDAO.IHouseDAO;

@WebServlet("/LeaveHouse")
public class LeaveHouse extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LeaveHouse() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("userId") == null) {
	            response.sendRedirect(request.getContextPath() + "/SignIn"); 
	            return;
	        }

	        int userId = (int) session.getAttribute("userId");

	        IHouseDAO houseDAO = new HouseDAO();
	        houseDAO.removeStudentFromHouse(userId);

	        response.sendRedirect("UpdateProfile");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
