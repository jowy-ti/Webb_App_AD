<%-- 
    Document   : menu
    Created on : 21 sept 2024, 17:24:37
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
        <title>JSP Page</title>
    </head>
    <body>
        <center>
            <h1>MENU</h1>
            <a href="registrarImagen.jsp">Registrar imagen</a>
            <br/>
            <a href="eleccionBusqueda.jsp">Buscar imagen</a>
        </center>
    </body>
</html>
