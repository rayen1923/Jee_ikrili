package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ReservationDAO;
import IDAO.IReservationDAO;

@WebServlet("/ResetPlace")
public class ResetPlace extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ResetPlace() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int houseId = Integer.parseInt(request.getParameter("houseId"));
        int ownerId = Integer.parseInt(request.getParameter("ownerId"));
        HttpSession session = request.getSession();
	    int studentId = (int) session.getAttribute("userId");
        
        IReservationDAO reservationDAO = new ReservationDAO();

		boolean success = reservationDAO.ReserverPlace(ownerId ,houseId, studentId);
        if (success) {
    	    response.sendRedirect(request.getContextPath() + "/FindHouse");
        } else {
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
