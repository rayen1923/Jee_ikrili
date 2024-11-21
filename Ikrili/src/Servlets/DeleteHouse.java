package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.HouseDAO;
import IDAO.IHouseDAO;

@WebServlet("/DeleteHouse")
public class DeleteHouse extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteHouse() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String houseIdStr = request.getParameter("houseId");
        if (houseIdStr != null && !houseIdStr.isEmpty()) {
			int houseId = Integer.parseInt(houseIdStr);
			IHouseDAO houseDAO = new HouseDAO();
			
			houseDAO.deleteHouseById(houseId);
			response.sendRedirect("CrudHouses");
        }
	}
}
