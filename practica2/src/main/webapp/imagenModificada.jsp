<%-- 
    Document   : imagenModificada
    Created on : 13 oct 2024, 16:46:38
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
        <title>Modified Image</title>
    </head>
    <body>
        <h1>La imagen se ha modificado correctamente</h1>
        <br/>
        <a href="menu.jsp">Go back to menu</a>
    </body>
</html>
