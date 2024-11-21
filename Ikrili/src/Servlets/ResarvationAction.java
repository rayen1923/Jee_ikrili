package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ReservationDAO;
import Entities.Resarvation;
import IDAO.IReservationDAO;

@WebServlet("/ResarvationAction")
public class ResarvationAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ResarvationAction() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
        this.getServletContext().getRequestDispatcher("/WEB-INF/notification.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");
	    int reservationId = Integer.parseInt(request.getParameter("reservationId"));
	    IReservationDAO reservationDAO = new ReservationDAO();
	    
	    try {
	        if ("accept".equalsIgnoreCase(action)) {
	            reservationDAO.updateStatus(reservationId, "Accepted");
	            
	            Resarvation reservation = reservationDAO.getReservationById(reservationId);
	            int studentId = reservation.getStudent_id();
	            int houseId = reservation.getHouse_id();
	            
	            boolean added = reservationDAO.addToHouseStudents(houseId, studentId);
	            if (!added) {
	                throw new Exception("Failed to add student to House_Students.");
	            }
	        } else if ("reject".equalsIgnoreCase(action)) {
	            reservationDAO.updateStatus(reservationId, "Rejected");
	        }
	        
			response.sendRedirect("Notification");
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing the request.");
	    }
	}
}
