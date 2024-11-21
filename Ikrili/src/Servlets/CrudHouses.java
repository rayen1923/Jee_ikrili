package Servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import DAO.HouseDAO;
import Entities.House;
import IDAO.IHouseDAO;

@WebServlet("/CrudHouses")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,
	    maxFileSize = 1024 * 1024 * 10,
	    maxRequestSize = 1024 * 1024 * 50
)
public class CrudHouses extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploads";

    public CrudHouses() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("userId") == null) {
		    response.sendRedirect(request.getContextPath() + "/SignIn");
            return;
        }
	    int userId = (int) session.getAttribute("userId");
	    
		IHouseDAO houseDAO = new HouseDAO();
	    ArrayList<House> houses = houseDAO.GetHousesByOwner(userId);
        request.setAttribute("houses", houses);
	    
		this.getServletContext().getRequestDispatcher("/WEB-INF/crudhouses.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    int userId = (int) session.getAttribute("userId");
	    
	    String adress = request.getParameter("adress");
	    String description = request.getParameter("description");
	    int nbPlace = Integer.parseInt(request.getParameter("nb_place"));
	    float placePrix = Float.parseFloat(request.getParameter("place_prix"));

	    String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
	    File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdirs();
	    }

	    List<String> imagePaths = new ArrayList<>();
	    for (Part part : request.getParts()) {
	        String fileName = extractFileName(part);
	        if (fileName != null && !fileName.isEmpty()) {
	            String filePath = uploadPath + File.separator + fileName;
	            try {
	                part.write(filePath);
	                imagePaths.add(UPLOAD_DIR + "/" + fileName);
	            } catch (IOException e) {
	                e.printStackTrace();
	                throw new ServletException("Error saving file: " + fileName, e);
	            }
	        }
	    }

	    House house = new House();
	    house.setOwner_id(userId);
	    house.setAdress(adress);
	    house.setDescription(description);
	    house.setNb_place(nbPlace);
	    house.setPlace_prix(placePrix);
	    house.setImgs((ArrayList<String>) imagePaths);

	    IHouseDAO houseDAO = new HouseDAO();
	    houseDAO.addHouse(house);

	    response.sendRedirect(request.getContextPath() + "/CrudHouses");
	}


	private String extractFileName(Part part) {
	    String contentDisp = part.getHeader("content-disposition");
	    for (String content : contentDisp.split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(content.indexOf("=") + 2, content.length() - 1);
	        }
	    }
	    return null;
	}
}
