package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.CustomerDAO;
import com.example.Broken_Hammer.dao.OrderDAO;
import com.example.Broken_Hammer.dao.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebServlet(name = "UserServlet", value = "/profile")
public class UserServlet extends HttpServlet {
    private CustomerDAO customerDAO;
    private UserDAO userDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
        userDAO = new UserDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        int startPage = Integer.parseInt(request.getParameter("page"));
        int start = (startPage - 1) * OrderDAO.LIMIT;
        int pages = orderDAO.amountOfPages(role, userID);

        if (role.equals(CUSTOMER)) {
            session.setAttribute("balance", customerDAO.getBalance(userID));
            request.setAttribute("workers_list", userDAO.getWorkers());
        }

        request.setAttribute("pages", pages);
        request.setAttribute("orders_list", orderDAO.getOrdersByUserId(role, userID, start));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/orders.jsp");
        dispatcher.forward(request, response);
    }
}
