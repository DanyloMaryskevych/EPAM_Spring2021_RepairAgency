package com.example.Broken_Hammer.filter;

import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.OrderDAO;
import com.example.Broken_Hammer.entity.Order;
import com.example.Broken_Hammer.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebFilter(filterName = "OrderFilter")
public class OrderFilter implements Filter {
    private final OrderDAO orderDAO = DAOFactory.getOrderDAO();
    private final Logger logger = Logger.getLogger(OrderFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        HttpSession session = httpServletRequest.getSession();
        Role role = Role.getRoleById((Integer) session.getAttribute(ROLE_ID));
        int userId = (Integer) session.getAttribute(USER_ID);

        if (httpServletRequest.getMethod().equalsIgnoreCase("GET")) {

            // Check if parameter orderID exists
            int orderId;
            try {
                orderId = Integer.parseInt(httpServletRequest.getParameter("orderID"));
            } catch (Exception e) {
                logger.error("Missing orderID parameter!");
                httpServletResponse.sendRedirect(ERROR_PAGE);
                return;
            }

            // Check if order with current orderID exists
            Order order = orderDAO.getBasicOrderInfoById(orderId);

            if (order == null) {
                logger.error("Order# " + orderId + " does not exist!");
                httpServletResponse.sendRedirect(ERROR_PAGE);
            } else if (role != Role.ADMIN) {

                // Check if user has access to order
                if ((role == Role.CUSTOMER && userId != order.getCustomerId()) || (role == Role.WORKER && userId != order.getWorkerId())) {
                    logger.error("User# " + userId + " trying to access Order# " + orderId + ". No permission!");
                    httpServletResponse.sendRedirect(ERROR_PAGE);
                } else chain.doFilter(request, response);

            } else chain.doFilter(request, response);

        } else chain.doFilter(request, response);

    }
}
