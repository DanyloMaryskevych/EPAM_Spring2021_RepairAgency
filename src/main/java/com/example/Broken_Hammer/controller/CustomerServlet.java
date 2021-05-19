package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.CustomerDAO;
import com.example.Broken_Hammer.dao.OrderDAO;
import com.example.Broken_Hammer.dao.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerServlet", value = "/customer")
public class CustomerServlet extends HttpServlet {
    private CustomerDAO customerDAO;
    private UserDAO userDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
        userDAO = new UserDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("workers_list", userDAO.getWorkers());

        Integer userID = (Integer) session.getAttribute("userID");

//        if (userID == null) response.sendRedirect("login.jsp");
            request.setAttribute("balance", customerDAO.getBalance(userID));
            request.setAttribute("orders_list", orderDAO.getOrdersByCustomersId(userID));

            RequestDispatcher dispatcher = request.getRequestDispatcher("/welcome_customer.jsp");
            dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
