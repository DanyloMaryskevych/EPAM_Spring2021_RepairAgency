package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.CustomerDAO;
import com.example.Broken_Hammer.dao.OrderDAO;
import com.example.Broken_Hammer.entity.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int orderID = Integer.parseInt(request.getParameter("orderID"));

        Order order = orderDAO.getOrderForCustomerById(orderID);
        order.setId(orderID);

        String worker = orderDAO.getWorker(orderID);
        Integer userID = (Integer) session.getAttribute("userID");
        request.setAttribute("balance", customerDAO.getBalance(userID));

        String payment = request.getParameter("payment");

        request.setAttribute("payment", payment);
        request.setAttribute("temp_order", order);
        request.setAttribute("temp_worker", worker);
        request.getRequestDispatcher("order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        String status = request.getParameter("status");

        switch (status) {
            case "new_order" : {
                orderDAO.addOrder(userID, request.getParameterMap());
                response.sendRedirect("customer");
                break;
            }
            case "paid" : {
                int orderID = Integer.parseInt(request.getParameter("orderID"));
                int price = Integer.parseInt(request.getParameter("price"));
                boolean payment = true;
                try {
                    customerDAO.withdraw(userID, orderID, price);
                } catch (SQLException e) {
                    payment = false;
                }
                response.sendRedirect("order?orderID=" + orderID + "&payment=" + payment);
                break;
            }
            case "feedback": {
                int rating = Integer.parseInt(request.getParameter("rating"));
                String comment = request.getParameter("comment");
                int orderID = Integer.parseInt(request.getParameter("orderID"));

                orderDAO.updateFeedback(rating, comment, orderID);

                response.sendRedirect("order?orderID=" + orderID);
                break;
            }
        }

    }
}
