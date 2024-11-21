package Servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import DAO.UserDAO;
import Entities.User;
import IDAO.IUserDAO;

@WebServlet("/SignUp")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class SignUp extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "uploads";

    public SignUp() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String profilePicturePath = null;
        Part profilePicturePart = request.getPart("profilePicture");
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
        String sex = request.getParameter("sex");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        User newUser = new User();
        newUser.setImg_url(profilePicturePath);
        newUser.setName(name);
        newUser.setGender(sex);
        newUser.setMail(email);
        newUser.setPhone(phone);
        newUser.setPassword(password);

        IUserDAO userdao = new UserDAO();
        userdao.signup(newUser);

        response.sendRedirect(request.getContextPath() + "/SignIn");
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
