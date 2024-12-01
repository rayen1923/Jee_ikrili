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

import DAO.HouseDAO;
import DAO.ReservationDAO;
import DAO.UserDAO;
import Entities.House;
import Entities.Resarvation;
import IDAO.IHouseDAO;
import IDAO.IReservationDAO;
import IDAO.IUserDAO;

@WebServlet("/Notification")
public class Notification extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Notification() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("userId") == null) {
		    response.sendRedirect(request.getContextPath() + "/SignIn");
            return;
        }
		Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId != null) {
            IReservationDAO reservationDAO = new ReservationDAO();

            List<Resarvation> reservations = reservationDAO.getUserReservations(userId);
            
            IUserDAO userdao = new UserDAO();
            for (Resarvation reservation : reservations) {
                int ownerId = reservation.getOwner_id();
                Entities.User sender = userdao.getUserById(ownerId);
                if (sender != null) {
                    request.setAttribute("sender", sender.getName());
                }
            }
            
            IHouseDAO housedao = new HouseDAO();
            for (Resarvation reservation : reservations) {
                ArrayList<House> houses = housedao.GetHouseByID(reservation.getHouse_id());
                for (House house : houses) {
                	request.setAttribute("adress", house.getAdress());
                }
            }
            request.setAttribute("reservations", reservations);

            this.getServletContext().getRequestDispatcher("/WEB-INF/notification.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
