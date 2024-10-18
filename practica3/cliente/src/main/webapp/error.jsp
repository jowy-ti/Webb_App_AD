<%-- 
    Document   : error
    Created on : 13 oct 2024, 12:49:09
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession"%>

<% 
    HttpSession sesion = request.getSession(false);
    if (sesion.getAttribute("user") == null) response.sendRedirect("error_out.jsp");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1>Ha ocurrido un error</h1>
        <p>Lo sentimos, ha habido un problema en la operación que intentabas realizar.</p>
        <a href="menu.jsp">Go back to menu</a>
    </body>
</html>
