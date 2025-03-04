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
    if (sesion.getAttribute("user") == null) response.sendRedirect("error_out.jsp");
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
            <br/>
            <br/>
            <a href="menu.jsp">Return</a>
        </form>
        <c:set var="user" value="${sessionScope.user}" />
        <c:if test="${images.isEmpty()}">
            <p> No se han encontrado coincidencias con su busqueda </p>
        </c:if>
        <c:forEach var="imagen" items="${images}">
            <p> Título: ${imagen.get(0)} </p>
            <p> Descripción: ${imagen.get(1)} </p>
            <p> Palabras clave: ${imagen.get(6)} </p>
            <p> Autor: ${imagen.get(2)} </p>
            <img src="uploads/${imagen.get(3)}" alt="${imagen}" style="width:500px;height:300px;">
                
            <c:if test="${user == imagen.get(5)}">
                <form action="eliminarImagen" method="GET">
                    <button type="submit">Eliminar</button>
                    <input id="id" name="id" type="hidden" value="${imagen.get(4)}" />
                    <input id="filename" name="filename" type="hidden" value="${imagen.get(3)}" />
                </form>
                <form action="modificarImagen" method="GET">
                    <button type="submit">Modificar</button>
                    <input id="id" name="id" type="hidden" value="${imagen.get(4)}" />
                    <input id="filename" name="filename" type="hidden" value="${imagen.get(3)}" />
                </form>
            </c:if>
            <br> <br> <br>
        </c:forEach>
    </body>
</html>
