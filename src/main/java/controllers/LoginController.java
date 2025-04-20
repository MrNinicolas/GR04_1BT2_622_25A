package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.entities.User;
import model.service.UserService;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.router(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.router(req, resp);
    }

    private void router(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String route = (req.getParameter("route") == null) ? "login" : req.getParameter("route");

        switch (route) {
            case "enter":
                this.enter(resp);
                break;
            case "login":
                this.login(req, resp);
                break;
            case "logOut":
                this.logout(req, resp);
                break;
            default:
                throw new IllegalArgumentException("Unknown route: " + route);
        }
    }

    private void enter(HttpServletResponse resp) throws IOException {
        resp.sendRedirect("jsp/LOGIN.jsp");
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dni = req.getParameter("txtDni");
        String password = req.getParameter("txtPassword");

        UserService userService = new UserService();
        User user = userService.authenticate(dni, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            req.setAttribute("messageType", "info");
            req.setAttribute("message", "Login successful! Welcome!");
            resp.sendRedirect("ManagePetsController?route=list");
        } else {
            req.setAttribute("messageType", "error");
            req.setAttribute("message", "Invalid credentials. Please try again.");
            req.getRequestDispatcher("jsp/LOGIN.jsp").forward(req, resp);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();
        }
        req.getRequestDispatcher("jsp/LOGIN.jsp").forward(req, resp);
    }
}
