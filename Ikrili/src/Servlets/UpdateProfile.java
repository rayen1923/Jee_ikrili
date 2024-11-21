package Servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import DAO.UserDAO;
import Entities.User;
import IDAO.IUserDAO;

@WebServlet("/UpdateProfile")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, 
    maxFileSize = 1024 * 1024 * 10, 
    maxRequestSize = 1024 * 1024 * 50 
)
public class UpdateProfile extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploads";

    public UpdateProfile() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        IUserDAO userdao = new UserDAO();
        User user = userdao.getUserById(userId);

        if (user != null) {
            request.setAttribute("user", user);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session == null || session.getAttribute("userId") == null) {
    	    response.sendRedirect(request.getContextPath() + "/SignIn"); 
    	    return;
    	}

    	int userId = (int) session.getAttribute("userId");

    	String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String profilePicturePath = null;
        Part profilePicturePart = request.getPart("profileImage");
        if (profilePicturePart != null && profilePicturePart.getSize() > 0) {
            String fileName = extractFileName(profilePicturePart);
            if (fileName != null && !fileName.isEmpty()) {
                profilePicturePath = uploadPath + File.separator + fileName;
                try {
                    profilePicturePart.write(profilePicturePath);
                    profilePicturePath = UPLOAD_DIR + "/" + fileName; 
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ServletException("Error saving profile picture: " + fileName, e);
                }
            }
        }

        String name = request.getParameter("name");
        String mail = request.getParameter("mail");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        IUserDAO userdao = new UserDAO();
        User updatedUser = userdao.getUserById(userId);
        updatedUser.setName(name);
        updatedUser.setMail(mail);
        updatedUser.setPhone(phone);
        updatedUser.setPassword(password);
        if (profilePicturePath != null) {
            updatedUser.setImg_url(profilePicturePath);
        }

        userdao.updateUser(updatedUser);

	    response.sendRedirect(request.getContextPath() + "/UpdateProfile");
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
