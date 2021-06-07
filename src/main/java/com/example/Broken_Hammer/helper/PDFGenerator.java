package com.example.Broken_Hammer.helper;

import com.example.Broken_Hammer.entity.OrderDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PDFGenerator {
    private static final Logger logger = Logger.getLogger(PDFGenerator.class);

    public static void generatePDF(HttpServletResponse response, HttpServletRequest request, ServletContext context, List<OrderDTO> orderDTOList) {
        try {
            String fileName = "D:\\generatePDF\\" + request.getParameter("fileName") + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

//            Header
            Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 35.0f);
            Paragraph header = new Paragraph("Orders", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setSpacingAfter(25);
            document.add(header);

            PdfPTable table = new PdfPTable(new float[]{5, 15, 12, 10, 18, 20, 7, 13});
            table.setWidthPercentage(95);

            addHeaderToTable(table, "ID");
            addHeaderToTable(table, "Title");
            addHeaderToTable(table, "Date");
            addHeaderToTable(table, "Worker");
            addHeaderToTable(table, "Performance status");
            addHeaderToTable(table, "Payment status");
            addHeaderToTable(table, "Price");
            addHeaderToTable(table, "Customer");

            for (OrderDTO orderDTO : orderDTOList) {
                addDataToTable(table, String.valueOf(orderDTO.getId()));
                addDataToTable(table, orderDTO.getTitle());
                addDataToTable(table, String.valueOf(orderDTO.getDate()));
                addDataToTable(table, orderDTO.getWorkerName());
                addDataToTable(table, orderDTO.getPerformanceStatus());
                addDataToTable(table, orderDTO.getPaymentStatus());
                addDataToTable(table, String.valueOf(orderDTO.getPrice()));
                addDataToTable(table, orderDTO.getCustomerName());
            }

            document.add(table);

            document.close();

            // download file
            File downloadFile = new File(fileName);
            FileInputStream inStream = new FileInputStream(downloadFile);

            // obtains ServletContext
//            context = getServletContext();

            // gets MIME type of the file
            String mimeType = context.getMimeType(fileName);
            if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }

            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            // obtains response's output stream
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void addHeaderToTable(PdfPTable table, String header) {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 13.0f);
        PdfPCell cell = new PdfPCell(new Paragraph(header, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
    }

    private static void addDataToTable(PdfPTable table, String data) {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10.0f);
        PdfPCell cell = new PdfPCell(new Paragraph(data, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    public static Map<String, String> queryStringParser(String queryString) {
        Map<String, String> map = new HashMap<>();

        if (queryString != null && !queryString.equals("")) {
            String[] pairs = queryString.split("&");

            for (String pair : pairs) {
                String[] keyValue = pair.split("=");

                try {
                    map.put(keyValue[0], keyValue[1]);
                } catch (IndexOutOfBoundsException e) {
                    logger.error("Invalid query string!");
                    return null;
                }
            }
        }

        return map;
    }
}
