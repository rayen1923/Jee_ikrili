package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UserDAO;
import IDAO.IUserDAO;

@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignIn() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("userId") == null) {
		    response.sendRedirect(request.getContextPath() + "/SignIn");
            return;
        }response.getWriter().append("Served at: ").append(request.getContextPath());
		this.getServletContext().getRequestDispatcher("/WEB-INF/signin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
        String password = request.getParameter("password");

        
        IUserDAO userdao = new UserDAO();
        int id = userdao.signin(email,password);
        
        if (id!=-1) {
            HttpSession session = request.getSession();
    	    response.sendRedirect(request.getContextPath() + "/CrudHouses");
            session.setAttribute("userId", id);
        } else {
        	response.sendRedirect(request.getContextPath() + "/SignIn");
        }
	}

}
