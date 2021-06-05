package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.*;
import com.example.Broken_Hammer.entity.OrderDTO;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static com.example.Broken_Hammer.filter.GlobalFilter.getLanguageCookie;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {
    private final CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
    private final UserDAO userDAO = DAOFactory.getUserDAO();
    private final OrderDAO orderDAO = DAOFactory.getOrderDAO();
    private final WorkerDAO workerDAO = DAOFactory.getWorkerDAO();
    private final Logger logger = Logger.getLogger(OrderServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        Cookie langCookie = getLanguageCookie(request);
        HttpSession session = request.getSession();

        OrderDTO order = orderDAO.getAllOrderInfoById(Integer.parseInt(request.getParameter("orderID")), langCookie.getValue());

        Integer userID = (Integer) session.getAttribute("userID");
        request.setAttribute("balance", customerDAO.getBalance(userID));

        String payment = request.getParameter("payment");

        request.setAttribute("expected_worker", workerDAO.getWorkerById(order.getExpectedWorkerID()));
        request.setAttribute("workers_list", userDAO.getWorkers());
        request.setAttribute("payment", payment);
        request.setAttribute("temp_order", order);
        request.getRequestDispatcher("temp_order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userID");
        int orderID = 0;
        try {
             orderID = Integer.parseInt(request.getParameter("orderID"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String status = request.getParameter("status");

        switch (status) {
            case "new_order": {
                orderID = orderDAO.addOrder(userId, request.getParameterMap());
                logger.info("New Order\n" + newOrderParser(request, userId, orderID));
                response.sendRedirect("profile?page=1");
                break;
            }
            case "paid": {
                int price = Integer.parseInt(request.getParameter("price"));
                boolean payment = true;
                try {
                    customerDAO.withdraw(userId, orderID, price);
                    logger.info("Order# " + orderID + ". Successful payment by User# " + userId);
                } catch (SQLException e) {
                    payment = false;
                    logger.error("Order# " + orderID + ". Payment (" + price + "$) was rejected by User# " + userId);
                }
                response.sendRedirect("order?orderID=" + orderID + "&payment=" + payment);
                break;
            }
            case "feedback": {
                int rating = Integer.parseInt(request.getParameter("rating"));
                String comment = request.getParameter("comment");
                int workerID = Integer.parseInt(request.getParameter("workerID"));

                orderDAO.updateFeedback(rating, comment, orderID, workerID);
                logger.info("Feedback\n" + feedbackParser(rating, comment, orderID, workerID, userId));

                response.sendRedirect("order?orderID=" + orderID);
                break;
            }
            case "price": {
                int price = Integer.parseInt(request.getParameter("price"));

                orderDAO.updatePrice(price, orderID);
                logger.info("Price " + price + " is set for Order# " + orderID);
                response.sendRedirect("order?orderID=" + orderID);
                break;
            }
            case "worker": {
                int workerID = Integer.parseInt(request.getParameter("workerID"));

                orderDAO.updateWorker(workerID, orderID);
                logger.info("Worker# " + workerID + " is set for Order# " + orderID);
                response.sendRedirect("order?orderID=" + orderID);
                break;
            }
            case "performing": {
                int performStatus = Integer.parseInt(request.getParameter("perform_status"));
                orderDAO.updateRejectedStatus(performStatus, orderID);

                logger.info("User#" + userId + " updated performance status to ID: " + performStatus);
                if (performStatus == 3) {
                    workerDAO.updateOrdersCounter((Integer) session.getAttribute("userID"));
                    logger.info("Worker# " + userId + " finished performance Order# " + orderID);
                }


                response.sendRedirect("order?orderID=" + orderID);
                break;
            }
        }
    }

    private String newOrderParser(HttpServletRequest request, int userId, int orderId) {
        JSONObject jsonObject = new JSONObject();
        Map<String, String[]> requestParameterMap = request.getParameterMap();

        jsonObject.put("userId", userId);
        jsonObject.put("orderId", orderId);
        jsonObject.put("expected_worker_id", requestParameterMap.get("expected_worker_id")[0]);
        jsonObject.put("description", requestParameterMap.get("description")[0]);
        jsonObject.put("title", requestParameterMap.get("title")[0]);

        return jsonObject.toString(4);
    }

    private String feedbackParser(int rating, String comment, int orderID, int workerID, int userId) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userId", userId);
        jsonObject.put("rating", rating);
        jsonObject.put("comment", comment);
        jsonObject.put("orderID", orderID);
        jsonObject.put("workerID", workerID);

        return jsonObject.toString(4);
    }
}
