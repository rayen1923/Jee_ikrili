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
import Entities.House;
import IDAO.IHouseDAO;

@WebServlet("/FindHouse")
public class FindHouse extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FindHouse() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
		if (session == null || session.getAttribute("userId") == null) {
		    response.sendRedirect(request.getContextPath() + "/SignIn");
            return;
        }
		String searchAddress = request.getParameter("searchAddress");

        IHouseDAO houseDAO = new HouseDAO();
        ArrayList<House> houses;

        if (searchAddress != null && !searchAddress.trim().isEmpty()) {
            houses = houseDAO.GetHousesByAddress(searchAddress);
        } else {
            houses = houseDAO.GetHouses();
        }

        request.setAttribute("houses", houses);
        this.getServletContext().getRequestDispatcher("/WEB-INF/findhouse.jsp").forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
