package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.HouseDAO;
import Entities.House;
import IDAO.IHouseDAO;

@WebServlet("/UpdateHouse")
public class UpdateHouse extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateHouse() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            int houseId = Integer.parseInt(request.getParameter("houseId"));
            String address = request.getParameter("adress");
            String description = request.getParameter("description");
            int nbPlace = Integer.parseInt(request.getParameter("nb_place"));
            float pricePerPlace = Float.parseFloat(request.getParameter("place_prix"));

            IHouseDAO houseDAO = new HouseDAO();
            House house = houseDAO.GetHouseByID(houseId).get(0);

            house.setAdress(address);
            house.setDescription(description);
            house.setNb_place(nbPlace);
            house.setPlace_prix(pricePerPlace);

            houseDAO.updateHouse(house);

            response.sendRedirect("CrudHouses");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("CrudHouses");
        }
	}

}
