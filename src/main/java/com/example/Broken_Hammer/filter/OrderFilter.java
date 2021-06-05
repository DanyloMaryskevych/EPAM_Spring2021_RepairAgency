package com.example.Broken_Hammer.filter;

import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.OrderDAO;
import com.example.Broken_Hammer.entity.Order;
import com.example.Broken_Hammer.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.Broken_Hammer.Constants.*;

@WebFilter(filterName = "OrderFilter", urlPatterns = {"/order"})
public class OrderFilter implements Filter {
    private final OrderDAO orderDAO = DAOFactory.getOrderDAO();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        HttpSession session = httpServletRequest.getSession();
        Role role = Role.getRoleById((Integer) session.getAttribute(ROLE_ID));

        if (httpServletRequest.getMethod().equalsIgnoreCase("GET")) {
            int orderId = Integer.parseInt(httpServletRequest.getParameter("orderID"));
            Order order = orderDAO.getBasicOrderInfoById(orderId);

            if (order == null) httpServletResponse.sendRedirect(ERROR_PAGE);
            else if (role != Role.ADMIN) {
                int userId = (Integer) session.getAttribute(USER_ID);

                if (role == Role.CUSTOMER && userId != order.getCustomerId()) httpServletResponse.sendRedirect(ERROR_PAGE);
                else if (role == Role.WORKER && userId != order.getWorkerId()) httpServletResponse.sendRedirect(ERROR_PAGE);
                else chain.doFilter(request, response);

            } else chain.doFilter(request, response);

        } else if (httpServletRequest.getMethod().equalsIgnoreCase("POST") && role == Role.CUSTOMER) {
            chain.doFilter(request, response);
        } else httpServletResponse.sendRedirect(ERROR_PAGE);
    }
}
