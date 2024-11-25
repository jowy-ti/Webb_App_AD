<%-- 
    Document   : eliminarImagen
    Created on : 10 oct 2024, 16:49:05
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
        <h1>Eliminar Imagen</h1>
        
        <form action = "eliminarImagen" method = "POST">

            <img src="uploads/server/${param.filename}" alt="${param.filename}" style="width:500px;height:300px;">

            <br/>
            Esta seguro que esta es la imagen que quiere eliminar?
            <br/>
            <button type="submit">Eliminar</button>
            <br/>
            <a href="buscarImagen.jsp">Return</a>
        </form>
        <c:set var="id" value="${param.id}" scope="session" />   
        <c:set var="creator" value="${param.creator}" scope="session" />   
    </body>
</html>
