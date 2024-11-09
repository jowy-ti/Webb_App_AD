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
        <h1>Busqueda</h1>
        <%
            String election = request.getParameter("election");
            request.setAttribute("election", election);
        %>
        <form action="BuscarImagen" method="GET">
            <ul>
                <c:if test="${election == 'id'}">
                <label for="id">Id:</label>
                <input type="text" id="id" name="id" />
                <br/>
                </c:if>
                <c:if test="${election == 'title'}">
                <label for="title">Título:</label>
                <input type="text" id="title" name="title" />
                <br/>
                </c:if>
                <c:if test="${election == 'author'}">
                <label for="author">Autor:</label>
                <input type="text" id="author" name="author" />
                <br/>
                </c:if>
                <c:if test="${election == 'keywords'}">
                <label for="keywords">Palabras clave:</label>
                <input type="text" id="keywords" name="keywords" />
                <br/>
                </c:if>
                <c:if test="${election == 'date'}">
                <label for="date">Fecha:</label>
                <input type="text" id="date" name="date" />
                <br/>
                </c:if>
                <button type="submit">Search</button>
                
                <br>
                
            </ul>
            <br/>
            <br/>
            <a href="eleccionBusqueda.jsp">Return</a>
        </form>
        <c:set var="user" value="${sessionScope.user}" />

        <c:forEach var="imagen" items="${images}">
            <p> Id: ${imagen.getInt("id")} </p>
            <p> Título: ${imagen.getString("title")} </p>
            <p> Autor: ${imagen.getString("author")} </p> 
            <p> Palabras clave: ${imagen.getString("keywords")} </p>
            <p> Fecha de creación: ${imagen.getString("date")} </p>
            <p> Creador: ${imagen.getString("creator")} </p>
            
            <%--
            <c:if test="${user == imagen.getString()}">
                <form action="eliminarImagen" method="POST">
                    <button type="submit">Eliminar</button>    
                    <input id="id" name="id" type="hidden" value="${imagen.get(4)}" />   
                
                    <input id="filename" name="filename" type="hidden" value="${imagen.get(3)}" />
                </form>
                <form action="modificarImagen" method="POST">     
                    <button type="submit">Modificar</button>
                    <input id="id" name="id" type="hidden" value="${imagen.get(4)}" />
                    <input id="filename" name="filename" type="hidden" value="${imagen.get(3)}" />
                </form>
            </c:if>
            <br> <br> <br>
            --%>
        </c:forEach>
    </body>
</html>
