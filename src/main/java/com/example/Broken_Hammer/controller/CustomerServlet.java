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
    public void init() {
        customerDAO = new CustomerDAO();
        userDAO = new UserDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("workers_list", userDAO.getWorkers());

        Integer userID = (Integer) session.getAttribute("userID");
        int limit = 7;

        request.setAttribute("balance", customerDAO.getBalance(userID));
        int startPage = Integer.parseInt(request.getParameter("page"));
        int start = (startPage - 1) * limit;
        int pages = orderDAO.amountOfPages(limit, userID);

        request.setAttribute("pages", pages);
        request.setAttribute("orders_list", orderDAO.getOrdersByCustomersId(userID, start, limit));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/welcome_customer.jsp");
        dispatcher.forward(request, response);
    }
}
