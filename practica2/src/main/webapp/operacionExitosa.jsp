<%-- 
    Document   : imagenEliminada
    Created on : 17 oct 2024, 19:19:43
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<% 
    HttpSession sesion = request.getSession(false);
    if (sesion.getAttribute("user") == null) response.sendRedirect("error_out.jsp");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Operación realizada correctamente</title>
    </head>
    <body>
        <p> ${message} </p>
        <br>
        <a href="menu.jsp">Volver al menú</a>
        <c:if test="${registarImagen}">
            <br>
            <a href="registrarImagen.jsp">Seguir registrando imágenes</a>
        </c:if>
    </body>
</html>
