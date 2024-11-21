package Servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.HouseDAO;
import DAO.UserDAO;
import Entities.House;
import IDAO.IHouseDAO;
import IDAO.IUserDAO;

@WebServlet("/HouseDetails")
public class HouseDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HouseDetails() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("userId") == null) {
		    response.sendRedirect(request.getContextPath() + "/SignIn");
            return;
        }
		IHouseDAO houseDAO = new HouseDAO();
		String HouseId = request.getParameter("houseId");
        int houseid = Integer.parseInt(HouseId);
	    ArrayList<House> houses = houseDAO.GetHouseByID(houseid);
        request.setAttribute("house", houses);
        
        IUserDAO userdao = new UserDAO();
        for (House house : houses) {
            int ownerId = house.getOwner_id();
            Entities.User owner = userdao.getUserById(ownerId);
            if (owner != null) {
                request.setAttribute("owner", owner.getName());
            }
        }
        
		this.getServletContext().getRequestDispatcher("/WEB-INF/housedetails.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
