<%-- 
    Document   : buscarImagen
    Created on : 4 oct 2024, 10:23:22
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%@page import="jakarta.servlet.http.HttpServletRequest;" %>

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
                <label for="id">Id:</label>
                <input type="text" id="id" name="id" />
                <br/>
                <label for="title">Título:</label>
                <input type="text" id="title" name="title" />
                <br/>
                <label for="author">Autor:</label>
                <input type="text" id="author" name="author" />
                <br/>
                <label for="keywords">Palabras clave:</label>
                <input type="text" id="keywords" name="keywords" />
                <br/>
                <label for="date">Fecha:</label>
                <input type="text" id="date" name="date" />
                <br/>
                <button type="submit">Search</button>
                
                <br>
                
            </ul>
            <br/>
            <br/>
            <a href="menu.jsp">Return</a>
        </form>
        <c:set var="user" value="${sessionScope.user}" />

        <c:forEach var="imagen" items="${images}">
            <p> Id: ${imagen.getInt("id")} </p>
            <p> Título: ${imagen.getString("title")} </p>
            <p> Autor: ${imagen.getString("author")} </p> 
            <p> Palabras clave: ${imagen.getString("keywords")} </p>
            <p> Fecha de creación: ${imagen.getString("date")} </p>
                
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
