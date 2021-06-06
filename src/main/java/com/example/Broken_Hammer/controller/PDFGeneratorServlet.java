package com.example.Broken_Hammer.controller;

import com.example.Broken_Hammer.dao.DAOFactory;
import com.example.Broken_Hammer.dao.OrderDAO;
import com.example.Broken_Hammer.entity.OrderDTO;
import com.example.Broken_Hammer.helper.PDFGenerator;

import javax.servlet.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PDFGeneratorServlet", value = "/PDFGeneratorServlet")
public class PDFGeneratorServlet extends HttpServlet {
    private final OrderDAO orderDAO = DAOFactory.getOrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> queryMap = PDFGenerator.queryStringParser(request.getParameter("params"));
        assert queryMap != null;

        Map<String, String> filtersMap = new HashMap<>();
        filtersMap.put("performance_status_id", queryMap.get("performance"));
        filtersMap.put("payment_status_id", queryMap.get("payment"));
        filtersMap.put("worker_id", queryMap.get("worker"));

        List<OrderDTO> orderDTOList = orderDAO.getAllOrders(queryMap.get("sort"), queryMap.get("order"), -1, filtersMap, "en");

        PDFGenerator.generatePDF(response, request, getServletContext(), orderDTOList);
    }


}
