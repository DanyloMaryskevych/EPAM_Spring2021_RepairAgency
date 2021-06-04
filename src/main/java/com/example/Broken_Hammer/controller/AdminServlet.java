package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.CustomerDAO;
import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.OrderDAO;
import com.example.Broken_Hammer.dao.UserDAO;
import com.example.Broken_Hammer.filter.GlobalFilter;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {
    private final OrderDAO orderDAO = DAOFactory.getOrderDAO();
    private final UserDAO userDAO = DAOFactory.getUserDAO();
    private final CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
    private final Logger logger = Logger.getRootLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie langCookie = GlobalFilter.getLanguageCookie(request);

        int startPage = Integer.parseInt(request.getParameter("page"));
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");

        Map<String, String> filtersMap = new HashMap<>();
        filtersMap.put("performance_status_id", request.getParameter("performance"));
        filtersMap.put("payment_status_id", request.getParameter("payment"));
        filtersMap.put("worker_id", request.getParameter("worker"));

        int start = (startPage - 1) * OrderDAO.LIMIT;
        int pages = orderDAO.amountOfPagesForAdmin(filtersMap);

        request.setAttribute("workers_list", userDAO.getWorkers());
        request.setAttribute("sort_param", sort);
        request.setAttribute("order_param", order);
        request.setAttribute("pages", pages);
        request.setAttribute("orders_list", orderDAO.getAllOrders(sort, order, start, filtersMap, langCookie.getValue()));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/orders.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        customerDAO.deposit(Integer.parseInt(request.getParameter("customerID")),
                            Integer.parseInt(request.getParameter("deposit")));

        response.sendRedirect("admin?page=1&sort=date&order=desc");
    }
}
