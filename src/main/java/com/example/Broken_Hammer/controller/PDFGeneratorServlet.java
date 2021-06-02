package com.example.Broken_Hammer.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.FileOutputStream;
import java.io.IOException;

@WebServlet(name = "PDFGeneratorServlet", value = "/PDFGeneratorServlet")
public class PDFGeneratorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String fileName = "D:\\generatePDF\\orders.pdf";
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();

            Paragraph paragraph = new Paragraph("Test PDF");

            document.add(paragraph);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        response.sendRedirect("admin?page=1&sort=date&order=desc");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
