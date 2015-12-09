
<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 04.12.2015
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.vladbondar.DBConnect" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<%
  String[] rows = (String[]) request.getAttribute("checkBox");
  if (rows == null){
%>
<form method="post">

  <input type="submit" name="ADD" value="ADD" size="30">
  <input type="submit" name="CANCEL" value="CANCEL" size="30">
  <table border="1">
    <col width="200">
    <col width="205">
    <col width="205">
    <tr>
      <th>Name</th>
      <th>Date</th>
      <th>phoneNumber</th>
    </tr>
  </table>
  <input type="text" size="30" name="Name" value="">
  <input type="text" size="30" name="Date" value="">
  <input type="text" size="30" name="phoneNumber" value="">

</form>
<%
  } else if(rows.length == 1){
    DBConnect connect = new DBConnect();
    ResultSet rs = connect.getOneValue(Integer.parseInt(rows[0]));
%>
<form method="post">
  <input type="submit" name="SAVE" value="SAVE" size="30">
  <input type="submit" name="CANCEL" value="CANCEL" size="30">
  <%
    while(rs.next()){
    int id = rs.getInt("ID");
  %>
  <table border="1">
    <col width="200">
    <col width="205">
    <col width="205">
    <tr>
      <th>Name</th>
      <th>Date</th>
      <th>phoneNumber</th>
    </tr>
  </table>
  <input type="hidden" name="ID" value="<%= id%>">
  <input type="text" size="30" name="Name" value="<%= rs.getString("Name")%>">
  <input type="text" size="30" name="Date" value="<%= rs.getDate("Date")%>">
  <input type="text" size="30" name="phoneNumber" value="<%= rs.getString("phoneNumber")%>">
  <% }%>
  </form>
    <% } %>
</body>
</html>
