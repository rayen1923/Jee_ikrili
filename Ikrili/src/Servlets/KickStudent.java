package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.HouseDAO;
import IDAO.IHouseDAO;

@WebServlet("/KickStudent")
public class KickStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public KickStudent() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            int houseId = Integer.parseInt(request.getParameter("houseId"));
            int studentId = Integer.parseInt(request.getParameter("studentId"));

            IHouseDAO houseDAO = new HouseDAO();
            houseDAO.removeStudentFromHouse(houseId, studentId);

            response.sendRedirect("CrudHouses");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("CrudHouses?error=Could not remove student");
        }
	}

}
