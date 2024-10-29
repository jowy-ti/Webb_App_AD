<%-- 
    Document   : elecciónBusqueda
    Created on : 29 oct 2024, 19:50:40
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
        <h1> Elige una opción de búsqueda </h1>
        <form action="buscarImagen.jsp" method="GET">
            <ul>
                <button name="election" value="id">Id</button>
                <br/> <br/>
                <button name="election" value="title">Título</button>
                <br/> <br/>
                <button name="election" value="date">Fecha</button>
                <br/> <br/>
                <button name="election" value="author">Autor</button>
                <br/> <br/>
                <button name="election" value="keywords">Palabras clave</button>
                <br>
            </ul>
            <br/>
            <br/>
            <a href="menu.jsp">Return</a>
        </form>
    </body>
</html>
