<%--suppress ALL --%>

<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 11.10.2015
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.vladbondar.DBConnect" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>DataGrid</title>
  </head>
  <body>
<%
  DBConnect dbConnect = new DBConnect();
  dbConnect.getInfo();
%>
<form action = "check" method ="post">
  <input type="submit" name="DELETE" value="DELETE" size="30"/>
  <input type="submit" name="REFRESH" value="REFRESH" size="30"/>
  <input type="submit" name="EDIT" value="EDIT" size="30"/>

<table border="1">
  <tbody>
  <tr>
    <td>ID</td>
    <td>Name</td>
    <td>Date</td>
    <td>Phone Number</td>
    <td>B</td>
  </tr>
  <form name="main" action="page2.jsp" method="post">
    <%
      ResultSet rs = dbConnect.getRs();
      while (rs.next()){
        int id = rs.getInt(1);%>
  <tr>
      <td><%= id%> </td>
      <td><%= rs.getString(2)%></td>
      <td><%= rs.getDate(3)%></td>
      <td><%= rs.getString(4)%></td>
    <td><input TYPE="checkbox" name="checkBox" VALUE="<%= id%>"/></td>
    </tr>
    <% } %>
    </form>
  </tbody>
</table>
</form>

  <%
    String name = (String) request.getAttribute("Name");
    String date = (String) request.getAttribute("Date");
    String phoneNumber = (String) request.getAttribute("phoneNumber");
    String delete = (String) request.getAttribute("DELETE");
    String edit = (String) request.getAttribute("EDIT");
    if (name == "problem"){
  %>
<script language="JavaScript">alert("Name can not be empty and contain numbers!")</script>
<%
  }
  if (date == "problem"){
%>
<script language="JavaScript">alert("Date pattern ('yyyy-mm-dd')! Date can't be future.")</script>
<%
}else if (phoneNumber == "problem"){
%>
<script language="JavaScript">alert("Phone number patten ('0*********') and can not contain letters")</script>
<%
} else if (delete == "problem"){
%>
<script language="JavaScript">alert("There is nothing to delete! Choose at least one row.")</script>
<%
} else if (edit == "problem"){
%>
<script language="JavaScript">alert("Select only one row to edit.")</script>
<%
  }
%>
  </body>
</html>
