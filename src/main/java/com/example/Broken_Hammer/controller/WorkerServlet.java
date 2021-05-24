package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.OrderDAO;
import com.example.Broken_Hammer.dao.WorkerDAO;
import com.example.Broken_Hammer.entity.Worker;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OurWorkersServlet", value = "/worker")
public class WorkerServlet extends HttpServlet {
    private WorkerDAO workerDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() {
        workerDAO = new WorkerDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String sort = request.getParameter("sort_by");
//        List<Worker> workers = workerDAO.getWorkers(sort);
//
//        request.setAttribute("bh_workers_list", workers);
//        request.getRequestDispatcher("/workers.jsp").forward(request, response);
        HttpSession session = request.getSession();

        Integer workerID = (Integer) session.getAttribute("userID");

        int startPage = Integer.parseInt(request.getParameter("page"));
        int start = (startPage - 1) * OrderDAO.LIMIT;
        int pages = orderDAO.amountOfPages("Customer", workerID);

        request.setAttribute("pages", pages);
        request.setAttribute("orders_list", orderDAO.getOrdersByUserId("Worker", workerID, start));

        request.getRequestDispatcher("/orders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
