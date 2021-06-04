package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.CustomerDAO;
import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.OrderDAO;
import com.example.Broken_Hammer.dao.UserDAO;
import com.example.Broken_Hammer.entity.Role;
import com.example.Broken_Hammer.filter.GlobalFilter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebServlet(name = "UserServlet", value = "/profile")
public class UserServlet extends HttpServlet {
    private final CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
    private final UserDAO userDAO = DAOFactory.getUserDAO();
    private final OrderDAO orderDAO = DAOFactory.getOrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cookie langCookie = GlobalFilter.getLanguageCookie(request);

        Integer userID = (Integer) session.getAttribute("userID");
        Role role = Role.getRoleById((Integer) session.getAttribute(ROLE_ID));

        int startPage = Integer.parseInt(request.getParameter("page"));
        int start = (startPage - 1) * OrderDAO.LIMIT;
        int pages = orderDAO.amountOfPages(role, userID);

        if (role == Role.CUSTOMER) {
            session.setAttribute("balance", customerDAO.getBalance(userID));
            request.setAttribute("workers_list", userDAO.getWorkers());
        }

        request.setAttribute("pages", pages);
        request.setAttribute("orders_list", orderDAO.getOrdersByUserId(role, userID, start, langCookie.getValue()));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/orders.jsp");
        dispatcher.forward(request, response);
    }
}
