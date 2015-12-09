package com.vladbondar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Влад on 11.10.2015.
 */
public class DBServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResultSet rs = new DBConnect().getInfo();
        session.setAttribute("rs", rs);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * If REFRESH button clicked on index.jsp, page refreshed.
         */
        if(request.getParameter("REFRESH") != null ) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        /**
         * If DELETE button clicked on index.jsp and if some rows checked with checkBox
         * checkboxes, deletes that rows and sending user back to index.jsp.
         * @param checkBox - checked checkboxes
         */
        if(request.getParameter("DELETE") != null){
            String[] rows = request.getParameterValues("checkBox");
            if(rows != null){
                DBConnect connect = new DBConnect();
                for (int i = 0; i < rows.length; i++) {
                    connect.deleteFromDataBaseByID(Integer.parseInt(rows[i]));
                }
            } else{
                request.setAttribute("DELETE","problem");
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        /**
         * If EDIT button clicked on index.jsp send checked checkboxes to page2.jsp.
         * @param checkBox - checked checkboxes
         * @param toEdit - gives page2.jsp knowledge that user came from EDIT button
         */
        if(request.getParameter("EDIT")!=null){
            String[] rows = request.getParameterValues("checkBox");
            if(rows == null || rows.length == 1){
                request.setAttribute("checkBox", rows);
                request.setAttribute("toEdit", "yes");
                request.getRequestDispatcher("page2.jsp").forward(request, response);
            } else{
                request.setAttribute("EDIT", "problem");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
        /**
         * If CANCEL button clicked on page2.jsp
         * redirects user to index.jsp without saving changes.
         */
        if(request.getParameter("CANCEL") != null){
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        /**
         * If ADD button clicked on page2.jsp
         * adding new row and redirects user to index.jsp.
         * Checking for all possible mismatches in fields,
         * if mismatch found, notice user.
         */
        if(request.getParameter("ADD") != null){
            String name = request.getParameter("Name");
            String date = request.getParameter("Date");
            String phoneNumber = request.getParameter("phoneNumber");
            if(!nameValidation(name)) {
                request.setAttribute("Name", "problem");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            if(!dateValidation(date)){
                request.setAttribute("Date", "problem");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            if(!phoneNumberValidation(phoneNumber)){
                request.setAttribute("phoneNumber", "problem");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                DBConnect connect = new DBConnect();
                connect.addToDataBase(name, date, phoneNumber);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
        /**
         * If SAVE button clicked on page2.jsp
         * saves changes to row and redirects user to index.jsp.
         * Checking for all possible mismatches in fields,
         * if mismatch found, notice user
         */
        if(request.getParameter("SAVE") != null){
            String id = request.getParameter("ID");
            String name = request.getParameter("Name");
            String date = request.getParameter("Date");
            String phoneNumber = request.getParameter("phoneNumber");
            if(!nameValidation(name)) {
                request.setAttribute("Name", "problem");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            if(!dateValidation(date)){
                request.setAttribute("Date", "problem");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            if(!phoneNumberValidation(phoneNumber)){
                request.setAttribute("phoneNumber", "problem");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                DBConnect connect = new DBConnect();
                connect.updateValueByID(id, name, date, phoneNumber);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
    }

    private boolean nameValidation(String name){
        if(name == null || name.length() == 0 || name.matches("[1-9]"))
            return false;
        else return true;
    }

    private boolean dateValidation(String date){
        if(date == null) return false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        sdf.setLenient(false);
        try{
            Date parseDate = sdf.parse(date);
            Date current = new Date();
            if (current.getTime() < parseDate.getTime()) return false;
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private boolean phoneNumberValidation(String phoneNumber){
        if(phoneNumber == null || phoneNumber.matches("[a-z][A-Z]")
                || phoneNumber.length() != 10 || !phoneNumber.startsWith("0"))
            return false;
        else return true;
    }
}
