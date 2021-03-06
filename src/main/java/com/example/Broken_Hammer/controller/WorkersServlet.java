package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.WorkerDAO;
import com.example.Broken_Hammer.entity.Worker;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OurWorkersServlet", value = "/workers")
public class WorkersServlet extends HttpServlet {
    private final WorkerDAO workerDAO = DAOFactory.getWorkerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sort = request.getParameter("sort_by");
        List<Worker> workers = workerDAO.getWorkers(sort);

        request.setAttribute("bh_workers_list", workers);
        request.getRequestDispatcher("workers.jsp").forward(request, response);
    }
}
