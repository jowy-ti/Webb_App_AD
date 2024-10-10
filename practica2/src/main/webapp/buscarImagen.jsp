<%-- 
    Document   : buscarImagen
    Created on : 4 oct 2024, 10:23:22
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<% 
    HttpSession sesion = request.getSession(false);
    if (sesion.getAttribute("user") == null) response.sendRedirect("/practica2/error_out.jsp");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Busqueda a partir de título y autor</h1>
        <form action="BuscarImagen" method="GET">
            <ul>
                <label for="title">Título:</label>
                <input type="text" id="title" name="title" />
                
                <label for="author">Autor:</label>
                <input type="text" id="author" name="author" />
                
                <label for="keywords">Palabras clave:</label>
                <input type="text" id="keywords" name="keywords" />
                
                <button type="submit">Search</button>
                
                <br>
            </ul>
        </form>
        <c:set var="session_images" value="${sessionScope.session_images}" />
            <c:forEach var="imagen" items="${filenames}">
                <img src="uploads/${imagen}" alt="${imagen}" style="width:500px;height:300px;"></li>
                <c:if test="${session_images.contains(imagen)}">
                    <a href="/practica2/login.jsp">Eliminar</a>
                    <a href="/practica2/login.jsp">Modificar</a>
                </c:if>
                <br> <br> <br>
            </c:forEach>
    </body>
</html>
