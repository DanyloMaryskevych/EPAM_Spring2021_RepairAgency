package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.*;
import com.example.Broken_Hammer.entity.Order;
import com.example.Broken_Hammer.entity.Worker;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {
    private final CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
    private final UserDAO userDAO = DAOFactory.getUserDAO();
    private final OrderDAO orderDAO = DAOFactory.getOrderDAO();
    private final WorkerDAO workerDAO = DAOFactory.getWorkerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int orderID = Integer.parseInt(request.getParameter("orderID"));

        Order order = orderDAO.getOrderById(orderID);
        order.setId(orderID);

        Worker worker = orderDAO.getWorker(orderID);
        Integer userID = (Integer) session.getAttribute("userID");
        request.setAttribute("balance", customerDAO.getBalance(userID));

        String payment = request.getParameter("payment");

        request.setAttribute("expected_worker", workerDAO.getWorkerById(order.getExpectedWorker()));
        request.setAttribute("workers_list", userDAO.getWorkers());
        request.setAttribute("payment", payment);
        request.setAttribute("temp_order", order);
        request.setAttribute("temp_worker", worker);
        request.getRequestDispatcher("temp_order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        String status = request.getParameter("status");

        switch (status) {
            case "new_order": {
                orderDAO.addOrder(userID, request.getParameterMap());
                response.sendRedirect("profile?page=1");
                break;
            }
            case "paid": {
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
                int workerID = Integer.parseInt(request.getParameter("workerID"));

                orderDAO.updateFeedback(rating, comment, orderID, workerID);

                response.sendRedirect("order?orderID=" + orderID);
                break;
            }
            case "price": {
                int orderID = Integer.parseInt(request.getParameter("orderID"));
                int price = Integer.parseInt(request.getParameter("price"));

                orderDAO.updatePrice(price, orderID);
                response.sendRedirect("order?orderID=" + orderID);
                break;
            }
            case "worker": {
                int orderID = Integer.parseInt(request.getParameter("orderID"));
                int workerID = Integer.parseInt(request.getParameter("workerID"));

                orderDAO.updateWorker(workerID, orderID);
                response.sendRedirect("order?orderID=" + orderID);
                break;
            }
            case "performing": {
                int orderID = Integer.parseInt(request.getParameter("orderID"));
                String performStatus = request.getParameter("perform_status");

                orderDAO.updateRejectedStatus(performStatus, orderID);

                if (performStatus.equals("Done")) workerDAO.updateOrdersCounter((Integer) session.getAttribute("userID"));

                response.sendRedirect("order?orderID=" + orderID);
                break;
            }
        }

    }
}
