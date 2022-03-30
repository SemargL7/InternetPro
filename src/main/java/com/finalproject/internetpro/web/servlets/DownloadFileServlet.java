package com.finalproject.internetpro.web.servlets;

import com.finalproject.internetpro.entity.Tariff;
import com.finalproject.internetpro.services.impl.ServiceTariffImpl;
import com.finalproject.internetpro.web.Sorting;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/home/download")
public class DownloadFileServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DownloadFileServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tariff> tariffList = ServiceTariffImpl.getInstance().getAll();
        String fileName = "tariffs.txt";

        String str ="";
        Sorting.listSort(tariffList,
                (Boolean) req.getSession().getAttribute("AZ"),
                (Boolean) req.getSession().getAttribute("cost"));

        for (Tariff tariff : tariffList) {
            str += ("\nID:" + (tariff.getId()));
            str += ("\nService:" + (tariff.getService()));
            str += ("\nCost:" + (tariff.getCost()));
            str += ("\nDescription ENG:" + (tariff.getDescription().get(1)));
            str += ("\nDescription UA:" + (tariff.getDescription().get(2)));
            str += ("\n\n");
        }
        try {
            OutputStreamWriter myObj =
                    new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8);

            myObj.write(str);
            myObj.close();
            logger.info("download | file is created");
        } catch (IOException e) {
            logger.error("download | ERROR: " + e.getMessage());
        }

        sendFile(req,resp,fileName);

        logger.info("download | file is sent");

        resp.sendRedirect("/home/tariffsList");
    }

    private void sendFile(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                response.getOutputStream(), StandardCharsets.UTF_8), true);

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        FileInputStream fis = new FileInputStream(fileName);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);

        int i;
        while( (i = reader.read()) != -1 )
        {
            out.write(i);
        }
        fis.close();
        isr.close();
        reader.close();
        out.close();
    }
}
