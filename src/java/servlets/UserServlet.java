package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

/**
 *
 * @author Swift
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService us = new UserService();
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        List<User> users;

        if (action != null && !action.equals("")) {
            String email = request.getParameter("email");
            if (action.equals("edit")) {
                User user;

                try {
                    user = us.get(email);
                    session.setAttribute("user_selected", user);
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (action.equals("delete")) {
                try {
                    us.delete(email);
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        try {
            users = us.getAll();
            session.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService us = new UserService();
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        List<User> users;

        if (request.getParameter("cancel") != null) {
            session.setAttribute("user_selected", null);
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
            return;
        }

        String email = request.getParameter("email");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String password = request.getParameter("password");
        int roleID = Integer.parseInt(request.getParameter("role"));

        if (email == null || firstName == null || lastName == null || password == null || roleID == 0 || email.equals("") || firstName.equals("") || lastName.equals("") || password.equals("")) {
            request.setAttribute("message", "All fields are required");
            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
            return;
        }

        if (action.equals("add")) {
            try {
                users = us.getAll();
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getEmail().equals(email)) {
                        request.setAttribute("message", "This email is already in use");
                        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                        return;
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                us.insert(email, firstName, lastName, password, roleID);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (request.getParameter("update") != null) {
            try {
                us.update(email, firstName, lastName, password, roleID);
                session.setAttribute("user_selected", null);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            users = us.getAll();
            session.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

}
